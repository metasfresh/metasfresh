package de.metas.printing.client.endpoint;

/*
 * #%L
 * de.metas.printing.esb.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;

import de.metas.printing.client.Context;
import de.metas.printing.client.IPrintConnectionEndpoint;
import de.metas.printing.client.encoder.IBeanEnconder;
import de.metas.printing.client.util.MapFormat;
import de.metas.printing.client.util.Util;
import de.metas.printing.esb.api.LoginRequest;
import de.metas.printing.esb.api.LoginResponse;
import de.metas.printing.esb.api.PRTRestServiceConstants;
import de.metas.printing.esb.api.PrintJobInstructionsConfirm;
import de.metas.printing.esb.api.PrintJobInstructionsStatusEnum;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrintPackageInfo;
import de.metas.printing.esb.api.PrinterHWList;

/**
 * Endpoint that queries the printing system via http.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class RestHttpPrintConnectionEndpoint implements IPrintConnectionEndpoint
{
	private static final String DATA_ENCODING_BINARY = "binary";

	private static final String DATA_ENCODING_BASE64 = "base64";

	private final transient Logger log = Logger.getLogger(getClass().getName());

	public static final String CTX_ROOT = "de.metas.printing.client.endpoint.RestHttpPrintConnectionEndpoint";
	public static final String CTX_ServerUrl = CTX_ROOT + ".ServerUrl";
	public static final String CTX_DataEncoding = CTX_ROOT + ".dataEncoding";

	public static final String CTX_SocketTimeoutMillis = CTX_ROOT + ".SocketTimeoutMillis";
	public static final int DEFAULT_SocketTimeoutMillis = 10 * 300000; // 5 min

	private final Context _ctx;
	private String _sessionId = null;
	private final String serverUrl;
	private final IBeanEnconder beanEncoder;

	private final HttpClient httpclient;

	private final String dataEncoding;

	public RestHttpPrintConnectionEndpoint()
	{
		_ctx = Context.getContext();
		_sessionId = _ctx.getProperty(Context.CTX_SessionId);

		serverUrl = _ctx.getProperty(CTX_ServerUrl);
		if (serverUrl == null || serverUrl.isEmpty())
		{
			throw new PrintConnectionEndpointException("Config " + CTX_ServerUrl + " not found");
		}

		dataEncoding = getDataEncoding();

		beanEncoder = _ctx.getInstance(Context.CTX_BeanEncoder, IBeanEnconder.class);

		//
		// HTTP Client connection
		final int socketTimeout = Context.getContext().getPropertyAsInt(CTX_SocketTimeoutMillis, DEFAULT_SocketTimeoutMillis);
		log.log(Level.FINEST, "socketTimeout: {}", socketTimeout);

		httpclient = new HttpClient();
		httpclient.getParams().setSoTimeout(socketTimeout);
	}

	private String getDataEncoding()
	{
		final String configValue = _ctx.getProperty(CTX_DataEncoding);
		if (configValue == null)
		{
			return DATA_ENCODING_BASE64;
		}
		else if (DATA_ENCODING_BASE64.equalsIgnoreCase(configValue.trim()))
		{
			return DATA_ENCODING_BASE64;
		}
		else if (DATA_ENCODING_BINARY.equalsIgnoreCase(configValue.trim()))
		{
			return DATA_ENCODING_BINARY;
		}

		log.warning("Unknown/unexpected value " + CTX_DataEncoding + "=" + configValue + "; falling back to " + DATA_ENCODING_BASE64);
		return DATA_ENCODING_BASE64;
	}

	private final Context getContext()
	{
		return _ctx;
	}

	private final String getSessionId()
	{
		if (_sessionId == null)
		{
			final Context ctx = Context.getContext();
			_sessionId = ctx.getProperty(Context.CTX_SessionId);
			if (_sessionId == null || _sessionId.isEmpty())
			{
				throw new PrintConnectionEndpointException("Config " + Context.CTX_SessionId + " not found");
			}
		}

		return _sessionId;
	}

	private int executeHttpPost(final PostMethod httpPost) throws HttpException, IOException
	{
		final int result = httpclient.executeMethod(httpPost);
		log.log(Level.FINEST, "Result code: {}", result);

		// final DefaultMethodRetryHandler retryHandler = new DefaultMethodRetryHandler();
		// retryHandler.setRetryCount(3);
		// httpPost.setMethodRetryHandler(retryHandler);

		return result;
	}

	@Override
	public void addPrinterHW(final PrinterHWList printerHWList)
	{
		final byte[] data = beanEncoder.encode(printerHWList);

		final URL url = getURL(PRTRestServiceConstants.PATH_AddPrinterHW);
		final PostMethod httpPost = new PostMethod(url.toString());
		addApiTokenIfAvailable(httpPost);

		final RequestEntity entity = new ByteArrayRequestEntity(data, beanEncoder.getContentType());
		httpPost.setRequestEntity(entity);

		int result = -1;
		try
		{
			result = executeHttpPost(httpPost);
		}
		catch (final Exception e)
		{
			throw new PrintConnectionEndpointException("Cannot POST to " + url, e);
		}

		if (result != 200)
		{
			throw new PrintConnectionEndpointException("Error " + result + " while posting on " + url);
		}
	}

	@Override
	public PrintPackage getNextPrintPackage()
	{
		final String transactionId = createTransactionId();

		final Map<String, String> params = createInitialUrlParams();
		params.put(PRTRestServiceConstants.PARAM_TransactionId, transactionId);

		final URL url = getURL(PRTRestServiceConstants.PATH_GetNextPrintPackage, params);

		final PostMethod httpPost = new PostMethod(url.toString());
		addApiTokenIfAvailable(httpPost);

		int result = -1;
		InputStream in = null;
		try
		{
			result = executeHttpPost(httpPost);
			in = httpPost.getResponseBodyAsStream();
			if (result != 200)
			{
				final String errorMsg = in == null ? "code " + result : Util.toString(in);
				throw new PrintConnectionEndpointException("Error " + result + " while posting on " + url + ": " + errorMsg);
			}

			final PrintPackage printPackage = beanEncoder.decodeStream(in, PrintPackage.class);

			final List<PrintPackageInfo> printPackageInfos = printPackage.getPrintPackageInfos();
			if (printPackageInfos == null || printPackageInfos.isEmpty())
			{
				// no next package available
				return null;
			}

			// Validate response:
			if (!transactionId.equals(printPackage.getTransactionId()))
			{
				throw new PrintConnectionEndpointException("Received a package from another transaction (expected=" + transactionId + ", actual=" + printPackage.getTransactionId());
			}

			return printPackage;
		}
		catch (final Exception e)
		{
			throw e instanceof PrintConnectionEndpointException ? (PrintConnectionEndpointException)e : new PrintConnectionEndpointException("Cannot POST to " + url, e);
		}
		finally
		{
			Util.close(in);
			in = null;
		}
	}

	@Override
	public InputStream getPrintPackageData(final PrintPackage printPackage)
	{
		final String transactionId = printPackage.getTransactionId();

		final Map<String, String> params = createInitialUrlParams();
		params.put(PRTRestServiceConstants.PARAM_TransactionId, transactionId);

		final URL url = getURL(PRTRestServiceConstants.PATH_GetPrintPackageData, params);

		final PostMethod httpPost = new PostMethod(url.toString());
		addApiTokenIfAvailable(httpPost);

		int result = -1;
		try
		{
			result = executeHttpPost(httpPost);

			if (result != 200)
			{
				final String errorMsg = httpPost.getResponseBodyAsString();
				throw new PrintConnectionEndpointException("Error " + result + " while posting on " + url + ": " + errorMsg);
			}

			final File file = mkFile(printPackage);

			final FileOutputStream fileOutputStream = new FileOutputStream(file);
			final InputStream dataBase64Stream = httpPost.getResponseBodyAsStream();

			ByteStreams.copy(dataBase64Stream, fileOutputStream);
			dataBase64Stream.close();
			fileOutputStream.close();

			final FileInputStream fileInputStream = new FileInputStream(file);
			if (DATA_ENCODING_BASE64.equals(dataEncoding))
			{
				// This is a Base64-encoded byte stream
				// The byte stream itself is the document received from the ESB
				// task 05011: work with a stream instead of byte[] to avoid problems with large amounts of data
				return BaseEncoding
						.base64()
						.withSeparator("\r\n", 76)
						.decodingStream(new InputStreamReader(fileInputStream));
			}
			else
			{
				return fileInputStream;
			}
		}
		catch (final Exception e)
		{
			throw e instanceof PrintConnectionEndpointException ? (PrintConnectionEndpointException)e : new PrintConnectionEndpointException("Cannot POST to " + url, e);
		}
	}

	@Override
	public void sendPrintPackageResponse(
			final PrintPackage printPackage,
			final PrintJobInstructionsConfirm response)
	{
		final byte[] data = beanEncoder.encode(response);

		final String transactionId = printPackage.getTransactionId();

		final Map<String, String> params = createInitialUrlParams();
		params.put(PRTRestServiceConstants.PARAM_TransactionId, transactionId);

		final URL url = getURL(PRTRestServiceConstants.PATH_SendPrintPackageResponse, params);

		final PostMethod httpPost = new PostMethod(url.toString());
		addApiTokenIfAvailable(httpPost);

		final RequestEntity entity = new ByteArrayRequestEntity(data, beanEncoder.getContentType());
		httpPost.setRequestEntity(entity);

		int result = -1;
		try
		{
			result = executeHttpPost(httpPost);

			if (result != 200)
			{
				final String errorMsg = httpPost.getResponseBodyAsString();
				throw new PrintConnectionEndpointException("Error " + result + " while posting on " + url + ": " + errorMsg);
			}
		}
		catch (final Exception e)
		{
			throw e instanceof PrintConnectionEndpointException ? (PrintConnectionEndpointException)e : new PrintConnectionEndpointException("Cannot POST to " + url, e);
		}
		if (PrintJobInstructionsStatusEnum.Gedruckt.equals(response.getStatus()))
		{
			final File dataFiletoDelete = mkFile(printPackage);
			try
			{
				Files.delete(dataFiletoDelete.toPath());
			}
			catch (final IOException e)
			{
				log.log(Level.SEVERE, "IOException while trying to delete data file " + dataFiletoDelete, e);
			}

		}
	}

	/**
	 * Create a file that corresponds to the given <code>printPackageInfo</code> and assumes data encoding.
	 */
	private File mkFile(final PrintPackage printPackageInfo)
	{
		final String fileEnding;
		if (DATA_ENCODING_BASE64.equals(dataEncoding))
		{
			fileEnding = "base64encoded_pdf";
		}
		else
		{
			fileEnding = "pdf";
		}
		final File file = new File("PrintJobInstructionsID_" + printPackageInfo.getPrintJobInstructionsID() + "." + fileEnding);
		return file;
	}

	// protected because we want to make them testable
	protected String createTransactionId()
	{
		final String transactionId = UUID.randomUUID().toString();
		return transactionId;
	}

	// protected because we want to make them testable
	protected Map<String, String> createInitialUrlParams()
	{
		final Map<String, String> params = new HashMap<>();
		params.put(PRTRestServiceConstants.PARAM_SessionId, getSessionId());
		return params;
	}

	private URL getURL(final String relativePath)
	{
		final Map<String, String> params = createInitialUrlParams();
		return getURL(relativePath, params);
	}

	private URL getURL(final String relativePath, final Map<String, String> params)
	{
		final StringBuilder urlStrBuf = new StringBuilder();
		urlStrBuf.append(serverUrl);
		if (!serverUrl.endsWith("/") && !relativePath.startsWith("/"))
		{
			urlStrBuf.append("/");
		}
		urlStrBuf.append(relativePath);

		final String urlStr = MapFormat.format(urlStrBuf.toString(), params);

		try
		{
			return new URL(urlStr);
		}
		catch (final MalformedURLException e)
		{
			throw new RuntimeException("Invalid URL " + urlStr, e);
		}
	}

	@Override
	public String toString()
	{
		return "RestHttpPrintConnectionEndpoint [sessionId=" + _sessionId + ", serverUrl=" + serverUrl + ", beanEncoder=" + beanEncoder + "]";
	}

	@Override
	public LoginResponse login(final LoginRequest loginRequest)
	{
		final Context ctx = getContext();

		// Make sure we are not already logged in
		final String sessionIdOld = ctx.getProperty(Context.CTX_SessionId);
		if (sessionIdOld != null && !sessionIdOld.trim().isEmpty())
		{
			throw new LoginFailedPrintConnectionEndpointException("Already logged in (SessionId:" + sessionIdOld + ")");
		}

		if (loginRequest == null)
		{
			throw new LoginFailedPrintConnectionEndpointException("LoginRequest is null");
		}
//		final String username = loginRequest.getUsername();
//		if (username == null || username.trim().isEmpty())
//		{
//			throw new LoginFailedPrintConnectionEndpointException("Invalid user name: " + username);
//		}

		//
		// Send login request and get the response
		final LoginResponse loginResponse = sendLoginRequest(loginRequest);

//		//
//		// Validate the response
//		// Username
//		if (!username.equals(loginResponse.getUsername()))
//		{
//			throw new LoginFailedPrintConnectionEndpointException("Login failed. Got invalid user name."
//					+ "\nRequest: " + loginRequest
//					+ "\nResponse: " + loginResponse);
//		}
		// SessionId
		final String response_sessionId = loginResponse.getSessionId();
		try
		{
			assertValidSessionId(response_sessionId);
		}
		catch (final Exception e)
		{
			throw new LoginFailedPrintConnectionEndpointException("Login failed - " + e.getLocalizedMessage()
					+ "\nRequest: " + loginRequest
					+ "\nResponse: " + loginResponse);
		}

		return loginResponse;
	}

	protected final void assertValidSessionId(final String sessionId)
	{
		if (sessionId == null || sessionId.trim().isEmpty())
		{
			throw new RuntimeException("SessionId is empty");
		}
		try
		{
			final int sessionIdInt = Integer.parseInt(sessionId.trim());
			if (sessionIdInt <= 0)
			{
				throw new RuntimeException("SessionId '" + sessionId + "' is not valid");
			}
		}
		catch (final NumberFormatException e)
		{
			throw new RuntimeException("SessionId '" + sessionId + "' is not valid");
		}

	}

	protected LoginResponse sendLoginRequest(final LoginRequest loginRequest) throws LoginFailedPrintConnectionEndpointException
	{
		final byte[] data = beanEncoder.encode(loginRequest);

		final Map<String, String> params = new HashMap<>();
		params.put(Context.CTX_SessionId, "0"); // no session

		final URL url = getURL(PRTRestServiceConstants.PATH_Login, params);

		final PostMethod httpPost = new PostMethod(url.toString());
		addApiTokenIfAvailable(httpPost);

		final RequestEntity entity = new ByteArrayRequestEntity(data, beanEncoder.getContentType());
		httpPost.setRequestEntity(entity);

		int result = -1;
		InputStream in = null;
		try
		{
			result = executeHttpPost(httpPost);
			in = httpPost.getResponseBodyAsStream();
			if (result != 200)
			{
				final String errorMsg = in == null ? "code " + result : Util.toString(in);
				throw new PrintConnectionEndpointException("Error " + result + " while posting on " + url + ": " + errorMsg);
			}

			final LoginResponse loginResponse = beanEncoder.decodeStream(in, LoginResponse.class);
			return loginResponse;
		}
		catch (final Exception e)
		{
			throw e instanceof LoginFailedPrintConnectionEndpointException ? (LoginFailedPrintConnectionEndpointException)e
					: new LoginFailedPrintConnectionEndpointException("Cannot POST to " + url, e);
		}
		finally
		{
			Util.close(in);
			in = null;
		}
	}

	private void addApiTokenIfAvailable(final PostMethod httpPost)
	{
		final String apiToken = getContext().getProperty(Context.CTX_Login_ApiToken);

		if (apiToken != null && apiToken.trim().length() > 0)
		{
			httpPost.setRequestHeader("Authorization", apiToken.trim());
		}
	}
}
