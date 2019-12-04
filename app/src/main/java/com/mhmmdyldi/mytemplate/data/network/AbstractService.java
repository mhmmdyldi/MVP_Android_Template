package com.mhmmdyldi.mytemplate.data.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ir.mobiro.android.ashpazsho.BuildConfig;
import ir.mobiro.android.ashpazsho.core.async.MyCallBack;
import ir.mobiro.android.ashpazsho.managers.AuthorizationManager;
import ir.mobiro.android.ashpazsho.managers.RequestManager;
import ir.mobiro.android.ashpazsho.services.retrofit.RetrofitAuthorizationService;
import ir.mobiro.android.ashpazsho.webapi.requestdto.AuthorizationRequestDto;
import ir.mobiro.android.ashpazsho.webapi.responsedto.AuthorizationDto;
import ir.mobiro.android.ashpazsho.webapi.responsedto.ErrorDto;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AbstractService<S> {

	public static final String API_BASE_URL = BuildConfig.API_BASE_URL;
	public static final String DOWNLOAD_PATH_PRE = "app/service3/";
	public static final String DOWNLOAD_PATH_POST = "/newer";
	private static final String AUTHORIZATION_KEY = "Authorization";
	private static final String AUTHORIZATION_VALUE = "Token %s";
	public AuthorizationManager authorizationManager;
	protected S service;
	private Class<S> serviceType;

	public AbstractService(AuthorizationManager authorizationManager, Class<S> serviceType) {
		this.serviceType = serviceType;
		this.authorizationManager = authorizationManager;
		service = createService();
	}

	protected <R> void performRequest(Call<R> call, Object tag, MyCallBack<R, ErrorDto>
			myCallBack) {
		RequestManager.performRequest(call, myCallBack, tag);
	}

	private S createService() {
		Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory
				(GsonConverterFactory.create());
		OkHttpClient client = createOkHttpClient();
		return retrofitBuilder.client(client).build().create(serviceType);
	}

	public S createService(String anotherBaseUrl, boolean needHttps) {

		Retrofit.Builder retrofitBuilder;
		if (needHttps) {
			retrofitBuilder = new Retrofit.Builder()
					.baseUrl(anotherBaseUrl)
					.addConverterFactory(GsonConverterFactory.create());
		} else {
			retrofitBuilder = new Retrofit.Builder()
					.baseUrl(anotherBaseUrl)
					.client(getUnsafeOkHttpClient())
					.addConverterFactory(GsonConverterFactory.create());
		}

		OkHttpClient client = createOkHttpClient();
		return retrofitBuilder.client(client).build().create(serviceType);
	}

	private RetrofitAuthorizationService createAuthorizationService() {
		Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory
				(GsonConverterFactory.create());
		return retrofitBuilder.build().create(RetrofitAuthorizationService.class);
	}

	@NonNull
	private OkHttpClient createOkHttpClient() {
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		httpClient.addInterceptor(new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request originalRequest = chain.request();

				// Nothing to add to intercepted request if:
				// a) Authorization value is empty because user is not logged in yet
				// b) There is already a header with updated Authorization value

				if (TextUtils.isEmpty(authorizationManager.getAccessToken()) ||
						alreadyHasAuthorizationHeader(originalRequest)) {
					return chain.proceed(originalRequest);
				}

				// Request customization: add request headers
				Request.Builder requestBuilder = originalRequest.newBuilder().header(AUTHORIZATION_KEY,
						String.format(AUTHORIZATION_VALUE, authorizationManager.getAccessToken())).method
						(originalRequest.method(), originalRequest.body());

				Request request = requestBuilder.build();
				return chain.proceed(request);
			}
		});
		httpClient.authenticator(new TokenAuthenticator());
		return httpClient.build();
	}

	private static OkHttpClient getUnsafeOkHttpClient() {
		try {
			// Create a trust manager that does not validate certificate chains
			final TrustManager[] trustAllCerts = new TrustManager[]{
					new X509TrustManager() {
						@Override
						public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String
								authType) throws CertificateException {
						}

						@Override
						public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String
								authType) throws CertificateException {
						}

						@Override
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return new java.security.cert.X509Certificate[]{};
						}
					}
			};

			// Install the all-trusting trust manager
			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			// Create an ssl socket factory with our all-trusting manager
			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
			builder.hostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			OkHttpClient okHttpClient = builder.build();
			return okHttpClient;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean alreadyHasAuthorizationHeader(Request request) {
		return !TextUtils.isEmpty(request.header(AUTHORIZATION_KEY));
	}

	private class TokenAuthenticator implements Authenticator {

		@Override
		public Request authenticate(Route route, Response response) throws IOException {

			AuthorizationRequestDto authorizationRequestDto = new AuthorizationRequestDto
					(authorizationManager.getRefreshToken());
			authorizationManager.updateAccessToken(null);

			Call<AuthorizationDto> authorizeCall = createAuthorizationService().authorize
					(authorizationRequestDto);
			AuthorizationDto tokenDto = authorizeCall.execute().body();

			if (tokenDto != null) {
				String newAccessToken = tokenDto.getToken();
				if (!TextUtils.isEmpty(newAccessToken)) {
					authorizationManager.updateAccessToken(newAccessToken);
					return response.request().newBuilder().header(AUTHORIZATION_KEY, String.format
							(AUTHORIZATION_VALUE, newAccessToken)).build();
				}
			}

			return null;
		}
	}
}
