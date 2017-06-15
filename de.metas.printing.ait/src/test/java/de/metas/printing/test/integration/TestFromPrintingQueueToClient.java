package de.metas.printing.test.integration;

/*
 * #%L
 * de.metas.printing.ait
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.camel.test.SwitchResponder;
import de.metas.printing.api.util.PdfCollator;
import de.metas.printing.client.AbstractPrintingClientResponder;
import de.metas.printing.client.Context;
import de.metas.printing.client.GetNextPrintPackageResponder;
import de.metas.printing.client.GetPrintPackageDataResponder;
import de.metas.printing.client.LoginRequestResponder;
import de.metas.printing.client.endpoint.LoginFailedPrintConnectionEndpointException;
import de.metas.printing.client.endpoint.RestHttpPrintConnectionEndpoint;
import de.metas.printing.esb.api.LoginRequest;
import de.metas.printing.esb.api.LoginResponse;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrintPackageInfo;
import de.metas.printing.esb.base.jaxb.generated.PRTADPrinterHWType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintJobInstructionsConfirmType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageDataType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageType;
import de.metas.printing.esb.base.jaxb.generated.PRTLoginRequestType;
import de.metas.printing.model.I_C_PrintPackageData;
import de.metas.printing.model.I_C_Print_Package;

@SuppressWarnings("PMD.SignatureDeclareThrowsException")
public class TestFromPrintingQueueToClient extends de.metas.esb.printing.test.AbstractPrintingCamelTest
{
	/**
	 * Rest Client to be used
	 */
	protected RestHttpPrintConnectionEndpoint restClient;

	private de.metas.printing.api.impl.Helper printingCoreHelper;

	private AD2EsbConverter adempiere;

	protected SwitchResponder adempiereResponders;
	protected LoginRequestResponder loginResponder;

	@Before
	public void setupAll()
	{
		setupPrintingCore();
		setupPrintingClient();
		setupCamelRoutes();
	}

	public void setupPrintingClient()
	{
		final MFSession session = Services.get(ISessionBL.class).getCurrentSession(adempiere.getCtx());

		final Context ctx = Context.getContext();
		ctx.setProperty(Context.CTX_SessionId, Integer.toString(session.getAD_Session_ID()));
		ctx.setProperty(RestHttpPrintConnectionEndpoint.CTX_ServerUrl, restServerURL);

		restClient = new RestHttpPrintConnectionEndpoint();
	}

	public void setupPrintingCore()
	{
		printingCoreHelper = new de.metas.printing.api.impl.Helper(testName);
		printingCoreHelper.setup();

		adempiere = new AD2EsbConverter();
	}

	public void setupCamelRoutes()
	{
		this.loginResponder = new LoginRequestResponder()
		{
			@Override
			protected void init()
			{
				setAdempiere(adempiere);
			}
		};

		adempiereResponders = new SwitchResponder(outJMSADEP)
				.addResponder(PRTLoginRequestType.class, loginResponder)
				.addResponder(PRTADPrinterHWType.class, new AbstractPrintingClientResponder<PRTADPrinterHWType, Object>()
				{
					@Override
					protected void validateRequest(final PRTADPrinterHWType xmlRequest)
					{
						// nothing
					}

					@Override
					protected Object createResponse(final PRTADPrinterHWType xmlRequest)
					{
						adempiere.toADempiere(xmlRequest);

						return null;
					}
				})
				.addResponder(PRTCPrintPackageType.class, new GetNextPrintPackageResponder()
				{
					@Override
					protected PRTCPrintPackageType createResponse(final PRTCPrintPackageType xmlRequest)
					{
						final I_C_Print_Package printPackageRequest = adempiere.toADempiere(xmlRequest);

						final I_C_Print_Package printPackageResponse = printingCoreHelper.createPrintPackageResponse(printPackageRequest);
						if (printPackageResponse == null)
						{
							return null;
						}

						final PRTCPrintPackageType xmlResponse = adempiere.toXml(printPackageResponse);
						return xmlResponse;
					}
				})
				.addResponder(PRTCPrintPackageDataType.class, new GetPrintPackageDataResponder()
				{
					@Override
					protected void init()
					{
						transactionIdExpected = TRANSACTION_ID_NOT_AVAILABLE;
					}

					@Override
					protected PRTCPrintPackageDataType createResponse(final PRTCPrintPackageDataType xmlRequest)
					{
						final I_C_Print_Package printPackage = adempiere.toADempiere(xmlRequest);
						final I_C_PrintPackageData printPackageData = printingCoreHelper.getDAO().getPrintPackageData(printPackage);
						final PRTCPrintPackageDataType xmlResponse = adempiere.toXml(printPackageData);
						return xmlResponse;
					}
				})
				.addResponder(PRTCPrintJobInstructionsConfirmType.class, new AbstractPrintingClientResponder<PRTCPrintJobInstructionsConfirmType, Object>()
				{
					@Override
					protected void validateRequest(final PRTCPrintJobInstructionsConfirmType xmlRequest)
					{
						// nothing
					}

					@Override
					protected Object createResponse(final PRTCPrintJobInstructionsConfirmType xmlRequest)
					{
						adempiere.toADempiere(xmlRequest);

						return null;
					}
				});
	}

	@Test
	public void test_StandardUseCase() throws Exception
	{
		//
		// Setup routings
		printingCoreHelper.createPrinterRouting("printer01", "tray01", -1, -1, -1); // default routing (applies to any request)

		//
		// Create printing queue
		printingCoreHelper.addToPrintQueue("01", -1, -1); // AD_Org_ID=N/A, C_DocType_ID=N/A
		printingCoreHelper.addToPrintQueue("02", -1, -1); // AD_Org_ID=N/A, C_DocType_ID=N/A
		printingCoreHelper.addToPrintQueue("03", -1, -1); // AD_Org_ID=N/A, C_DocType_ID=N/A

		//
		// Setup expected result
		final byte[] pdfDataExpected = new PdfCollator()
				.addPages(printingCoreHelper.getPdf("01"), 1, 20)
				.addPages(printingCoreHelper.getPdf("02"), 1, 20)
				.addPages(printingCoreHelper.getPdf("03"), 1, 20)
				.toByteArray();

		// Create Print Jobs from Queue
		final int printJobsCountExpected = 1;
		final int printJobsCountActual = printingCoreHelper.createAllPrintJobs();
		Assert.assertEquals("Invalid Print Jobs count", printJobsCountExpected, printJobsCountActual);

		//
		// Get next print package
		PrintPackage printPackage = null;
		try
		{
			printPackage = restClient.getNextPrintPackage();
		}
		catch (final Exception e)
		{
			fail(e);
		}
		logger.info("printPackage=" + printPackage);

		//
		// Validate print package infos
		final List<PrintPackageInfo> printPackageInfos = printPackage.getPrintPackageInfos();
		Assert.assertEquals("Invalid infos count for " + printPackage, 1, printPackageInfos.size());
		Assert.assertEquals("Invalid PageFrom for " + printPackageInfos.get(0), 1, printPackageInfos.get(0).getPageFrom());
		Assert.assertEquals("Invalid PageTo for " + printPackageInfos.get(0), 60, printPackageInfos.get(0).getPageTo());

		//
		// Get next print package data
		InputStream dataStreamActual = null;
		try
		{
			dataStreamActual = restClient.getPrintPackageData(printPackage);
		}
		catch (final Exception e)
		{
			fail(e);
		}
		final byte[] dataActual = IOUtils.toByteArray(dataStreamActual);
		printingCoreHelper.assertEqualsPDF(pdfDataExpected, dataActual);
	}

	@Test
	public void test_ClientLogin_OK()
	{
		final Context ctx = Context.getContext();
		ctx.setProperty(Context.CTX_SessionId, ""); // make sure is not logged in

		final int response_AD_Session_ID = 987654321;

		loginResponder.response_AD_Session_ID = response_AD_Session_ID;

		//
		// Create the Login Request
		final LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("testLogin_username");
		loginRequest.setPassword("testLogin_password");
		loginRequest.setHostKey("testLogin_hostKey_" + UUID.randomUUID());

		//
		// Do login
		final LoginResponse loginResponse = restClient.login(loginRequest);

		//
		// Validate response
		Assert.assertEquals("Invalid UserName: " + loginResponse, loginRequest.getUsername(), loginResponse.getUsername());
		Assert.assertEquals("Invalid SessionId: " + loginResponse, String.valueOf(response_AD_Session_ID), loginResponse.getSessionId());
		Assert.assertEquals("Invalid HostKey: " + loginResponse, loginRequest.getHostKey(), loginResponse.getHostKey());

		//
		// Validate the context
	}

	@Test(expected = LoginFailedPrintConnectionEndpointException.class)
	public void test_ClientLogin_ButAlreadyLoggedIn()
	{
		// Make sure we are already logged in
		// i.e. SessionId is set in context
		final Context ctx = Context.getContext();
		final String sessionId = ctx.getProperty(Context.CTX_SessionId);
		Assert.assertFalse("Session ID is set in context: " + sessionId, Check.isEmpty(sessionId, true));

		//
		// Create the Login Request
		final LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("testLogin_username");
		loginRequest.setPassword("testLogin_password");
		loginRequest.setHostKey("testLogin_hostKey_" + UUID.randomUUID());

		//
		// Do login
		final LoginResponse loginResponse = restClient.login(loginRequest);
		Assert.fail("We shall get no response but we got: " + loginResponse);
	}

	/**
	 * Test: try to login but server is returning no AD_Session_ID.
	 * 
	 * Same as {@link #test_ClientLogin_OK()}.
	 */
	@Test(expected = LoginFailedPrintConnectionEndpointException.class)
	public void test_ClientLogin_ServerIsReturningNoSessionId()
	{
		final Context ctx = Context.getContext();
		ctx.setProperty(Context.CTX_SessionId, ""); // make sure is not logged in

		final int response_AD_Session_ID = -1;

		loginResponder.response_AD_Session_ID = response_AD_Session_ID;

		//
		// Create the Login Request
		final LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("testLogin_username");
		loginRequest.setPassword("testLogin_password");
		loginRequest.setHostKey("testLogin_hostKey_" + UUID.randomUUID());

		//
		// Do login
		// NOTE:
		// * We expect exception because there was no session Id returned
		// * we don't expect any exception in camel routers because the error is thrown on client side validation
		restClient.login(loginRequest);
	}

	/**
	 * Test: try to login but while creating the response (inside camel routes), an exception is thrown.
	 */
	@Test(expected = LoginFailedPrintConnectionEndpointException.class)
	public void test_ClientLogin_CamelServerThrowsException()
	{
		final Context ctx = Context.getContext();
		ctx.setProperty(Context.CTX_SessionId, ""); // make sure is not logged in

		loginResponder.response_Exception = new RuntimeException("Fail test");

		//
		// Create the Login Request
		final LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("testLogin_username");
		loginRequest.setPassword("testLogin_password");
		loginRequest.setHostKey("testLogin_hostKey_" + UUID.randomUUID());

		//
		// Do login
		// NOTE:
		// * We expect exception  because camel route failed and there was a response with NULL SessionId returned
		// * we expect exception in camel routers
		try
		{
			restClient.login(loginRequest);
		}
		finally
		{
			// Make sure we got our exception in failures list
			assertCamelError(loginResponder.response_Exception);
		}

	}
}
