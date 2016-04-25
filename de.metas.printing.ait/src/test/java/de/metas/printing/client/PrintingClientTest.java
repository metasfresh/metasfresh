package de.metas.printing.client;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;
import java.util.UUID;

import javax.jms.JMSException;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.camel.test.SwitchResponder;
import de.metas.esb.printing.test.AbstractPrintingCamelTest;
import de.metas.printing.client.encoder.IBeanEnconder;
import de.metas.printing.client.endpoint.LoginFailedPrintConnectionEndpointException;
import de.metas.printing.client.endpoint.PrintConnectionEndpointException;
import de.metas.printing.client.endpoint.RestHttpPrintConnectionEndpoint;
import de.metas.printing.esb.api.LoginRequest;
import de.metas.printing.esb.api.LoginResponse;
import de.metas.printing.esb.api.PrintJobInstructionsConfirm;
import de.metas.printing.esb.api.PrintJobInstructionsStatusEnum;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrinterHWList;
import de.metas.printing.esb.base.inout.bean.PRTCPrintPackageTypeConverter;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintJobInstructionsConfirmType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageDataType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageType;
import de.metas.printing.esb.base.jaxb.generated.PRTLoginRequestType;
import de.metas.printing.model.I_AD_User_Login;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.X_C_Print_Job_Instructions;
import de.metas.printing.test.integration.AD2EsbConverter;

@SuppressWarnings("PMD.SignatureDeclareThrowsException")
public class PrintingClientTest extends AbstractPrintingCamelTest
{
	/**
	 * Rest Client to be used
	 */
	private RestHttpPrintConnectionEndpoint restClient;

	@Before
	public void setupPrintingClient()
	{
		final Context ctx = Context.getContext();
		ctx.setProperty(Context.CTX_SessionId, Integer.toString(Math.abs(RandomUtils.nextInt())));
		ctx.setProperty(RestHttpPrintConnectionEndpoint.CTX_ServerUrl, restServerURL);

		restClient = new RestHttpPrintConnectionEndpoint();
	}

	@Before
	public void setupPrintingEngine()
	{
		Adempiere.enableUnitTestMode();
	}

	@Test
	public void testLogin_OK()
	{
		final String username = "test_user";
		final String password = "test_password";
		final int response_AD_Session_ID = 12345;
		final boolean expectException = false;
		testLogin(username, password, response_AD_Session_ID, expectException);
	}

	@Test(expected = LoginFailedPrintConnectionEndpointException.class)
	public void testLogin_Failed()
	{
		final String username = "test_user";
		final String password = "test_password";
		final int response_AD_Session_ID = -1;
		final boolean expectException = true;

		try
		{
			testLogin(username, password, response_AD_Session_ID, expectException);
		}
		finally
		{
			// outJMSADEP.getFailures().clear();
		}
	}

	private final void testLogin(final String username, final String password, final int response_AD_Session_ID, final boolean expectException)
	{
		final int testLogin_response_AD_Session_ID = response_AD_Session_ID;
		final LoginRequestResponder responder = new LoginRequestResponder()
		{
			@Override
			protected void validateRequest(final PRTLoginRequestType xmlRequest)
			{
				Assert.assertEquals("Invalid UserName: " + xmlRequest, username, xmlRequest.getUserName());
				Assert.assertEquals("Invalid Password: " + xmlRequest, password, xmlRequest.getPassword());
			}

			@Override
			protected I_AD_User_Login createAD_User_Login_Response(final PRTLoginRequestType xmlRequest)
			{
				final Properties ctx = Env.getCtx();
				final String trxName = ITrx.TRXNAME_None;
				final I_AD_User_Login loginResponse = InterfaceWrapperHelper.create(ctx, I_AD_User_Login.class, trxName);
				loginResponse.setUserName(xmlRequest.getUserName());
				// loginRequest.setPassword(xmlRequest.getPassword()); // ADempiere is not copying the password, so neighter us
				loginResponse.setAD_Session_ID(testLogin_response_AD_Session_ID);
				loginResponse.setHostKey(xmlRequest.getHostKey());
				// NOTE: no need to save
				return loginResponse;
			};
		};

		outJMSADEP.setExpectedMessageCount(1);
		outJMSADEP.setReporter(responder);

		// Make sure we are not logged in
		final Context context = Context.getContext();
		context.setProperty(Context.CTX_SessionId, ""); // workaround, else SessionId won't be found when parsing the REST URL

		//
		// Ask for login
		final LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername(username);
		loginRequest.setPassword(password);
		loginRequest.setHostKey("DummyHostKey-" + UUID.randomUUID());
		final LoginResponse loginResponse = restClient.login(loginRequest);

		//
		// Validate response
		Assert.assertEquals("Invalid UserName: " + loginResponse, username, loginResponse.getUsername());
		Assert.assertEquals("Invalid SessionId: " + loginResponse, String.valueOf(response_AD_Session_ID), loginResponse.getSessionId());
		Assert.assertEquals("Invalid HostKey: " + loginResponse, loginRequest.getHostKey(), loginResponse.getHostKey());
	}

	@Test
	public void testAddPrinterHW()
	{
		outJMSADEP.setExpectedMessageCount(1);

		final PrinterHWList printerHWList = Context.getContext()
				.getInstance(Context.CTX_BeanEncoder, IBeanEnconder.class)
				.decodeStream(getClass().getResourceAsStream("/add_printer_hw.json"), PrinterHWList.class);

		restClient.addPrinterHW(printerHWList);

		Assert.assertTrue(true); // nothing failed, no error thrown
	}

	@Test
	public void testGetNextPrintPackage() throws Exception
	{
		final GetNextPrintPackageResponder responder = new GetNextPrintPackageResponder();

		outJMSADEP.setExpectedMessageCount(1);
		outJMSADEP.setReporter(responder);

		//
		// Ask the client to fetch the next print package
		PrintPackage printPackageActual = null;
		try
		{
			printPackageActual = restClient.getNextPrintPackage();
		}
		catch (final Exception e)
		{
			fail(e);
		}
		logger.info("printPackageActual=" + printPackageActual);

		final PrintPackage printPackageExpected = new PRTCPrintPackageTypeConverter().createPackageTypeResponse(responder.lastXmlResponse);
		assertEquals("Invalid response package", printPackageExpected, printPackageActual);

		Assert.assertTrue(true); // nothing failed, no error thrown
	}

	/**
	 * If the jms endpoint throw an exception, then we still expect a "normal" print package to be returned, because it shall not be the printing lcient's problem if e.g. metasfresh is down for
	 * maintainance.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetNextPrintPackage_JMSException() throws Exception
	{

		outJMSADEP.setExpectedMessageCount(1);
		outJMSADEP.whenAnyExchangeReceived(new
				Processor()
				{
					@Override
					public void process(Exchange exchange) throws Exception
					{
						throw new JMSException("simulating that metasfresh is down for maintainence");
					}
				});

		//
		// Ask the client to fetch the next print package
		PrintPackage printPackageActual = null;
		try
		{
			printPackageActual = restClient.getNextPrintPackage();
		}
		catch (final Exception e)
		{
			fail(e);
		}
		logger.info("printPackageActual=" + printPackageActual);

		// final PrintPackage printPackageExpected = new PRTCPrintPackageTypeConverter().createPackageTypeResponse(responder.lastXmlResponse);
		// assertEquals("Invalid response package", printPackageExpected, printPackageActual);

		Assert.assertTrue(true); // nothing failed, no error thrown
	}

	/**
	 * We expect a {@link PrintConnectionEndpointException} to be thrown by the {@link RestHttpPrintConnectionEndpoint} in the case that the transaction-ID of the response does not match the
	 * transaction-ID of the respective request.
	 */
	@Test(expected = PrintConnectionEndpointException.class)
	public void testGetNextPrintPackage_invalid_transactionId_on_response()
	{
		final GetNextPrintPackageResponder responder = new GetNextPrintPackageResponder()
		{
			@Override
			protected PRTCPrintPackageType createResponse(final PRTCPrintPackageType xmlRequest)
			{
				final PRTCPrintPackageType xmlResponse = super.createResponse(xmlRequest);
				xmlResponse.setTransactionID("invalid transaction ID");
				return xmlResponse;
			}
		};

		outJMSADEP.setReporter(responder);
		outJMSADEP.setExpectedMessageCount(1);

		//
		// Ask the client to fetch the next print package
		// ... but here PrintConnectionEndpointException shall be thrown
		restClient.getNextPrintPackage();

		Assert.fail("GetNextPrintPackage should fail");
	}

	/**
	 * Test: when camel does not answer or returns a package without infos, null shall be returned
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetNextPrintPackage_no_camel_response()
	{
		outJMSADEP.setExpectedMessageCount(1);
		final PrintPackage printPackage = restClient.getNextPrintPackage();
		assertNull("No result shall be returned", printPackage);
	}

	@Test
	public void testGetNextPrintPackage_NULL_response()
	{
		outJMSADEP.setReporter(new GetNextPrintPackageResponder()
		{
			@Override
			protected String createResponseString(final PRTCPrintPackageType xmlRequest)
			{
				return null;
			}
		});

		outJMSADEP.setExpectedMessageCount(1);
		final PrintPackage printPackage = restClient.getNextPrintPackage();
		assertNull("No result shall be returned", printPackage);
	}

	@Test(expected = PrintConnectionEndpointException.class)
	public void testGetNextPrintPackage_invalid_response_type()
	{
		final GetNextPrintPackageResponder responder = new GetNextPrintPackageResponder()
		{
			@Override
			protected String createResponseString(final PRTCPrintPackageType xmlRequest)
			{
				return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><UnknownElement></UnknownElement>";
			}
		};
		outJMSADEP.setExpectedMessageCount(1);
		outJMSADEP.setReporter(responder);

		restClient.getNextPrintPackage(); // shall throw exception

		Assert.fail("GetNextPrintPackage should fail");
	}

	@Test
	public void testGetPrintPackageData() throws Exception
	{
		final GetPrintPackageDataResponder responder = new GetPrintPackageDataResponder();
		responder.transactionIdExpected = UUID.randomUUID().toString(); // Just a dummy transaction ID
		responder.responseData = createTestPDFData(); // data to be used in response

		outJMSADEP.setExpectedMessageCount(1);
		outJMSADEP.setReporter(responder);

		final PrintPackage jsonRequest = new PrintPackage();
		jsonRequest.setTransactionId(responder.transactionIdExpected);

		InputStream dataActualStream = null;
		try
		{
			dataActualStream = restClient.getPrintPackageData(jsonRequest);
		}
		catch (final Exception e)
		{
			fail(e);
		}

		assertNotNull("Received stream is null", dataActualStream);
		final byte[] dataActual = IOUtils.toByteArray(dataActualStream);

		assertArrayEquals("Invalid data received", responder.responseData, dataActual);
	}

	@Test
	public void testGetNextPrintPackageAndData() throws IOException
	{
		final GetNextPrintPackageResponder getNextPrintPackageResponder = new GetNextPrintPackageResponder();
		final GetPrintPackageDataResponder getPrintPackageDataResponder = new GetPrintPackageDataResponder();

		new SwitchResponder(outJMSADEP)
				.addResponder(PRTCPrintPackageType.class, getNextPrintPackageResponder)
				.addResponder(PRTCPrintPackageDataType.class, getPrintPackageDataResponder);
		outJMSADEP.setExpectedMessageCount(2);

		//
		// Get Next Print Package and validate
		final PrintPackage printPackageActual = restClient.getNextPrintPackage();
		final PrintPackage printPackageExpected = new PRTCPrintPackageTypeConverter().createPackageTypeResponse(getNextPrintPackageResponder.lastXmlResponse);
		assertEquals("Invalid response package", printPackageExpected, printPackageActual);

		//
		// Get Print Package Data and validate
		getPrintPackageDataResponder.responseData = createTestPDFData(); // data to be used in response
		getPrintPackageDataResponder.transactionIdExpected = printPackageActual.getTransactionId();
		final InputStream dataStreamActual = restClient.getPrintPackageData(printPackageActual);
		assertNotNull("Received stream is null", dataStreamActual);
		final byte[] dataActual = IOUtils.toByteArray(dataStreamActual);

		assertArrayEquals("Invalid data received", getPrintPackageDataResponder.responseData, dataActual);
	}

	private byte[] createTestPDFData() throws IOException
	{
		return IOUtils.toByteArray(getClass().getResourceAsStream("/document01.pdf"));
	}

	@Test
	public void testSendPrintPackageResponse() throws Exception
	{
		// Send through a print package message
		final GetNextPrintPackageResponder responder = new GetNextPrintPackageResponder();

		outJMSADEP.setExpectedMessageCount(1 + 1);
		outJMSADEP.setReporter(responder);

		PrintPackage printPackage = null;
		try
		{
			printPackage = restClient.getNextPrintPackage();
		}
		catch (final Exception e)
		{
			fail(e);
		}
		logger.info("printPackageActual=" + printPackage);

		// Drop all received exchanges
		final Collection<Exchange> allExchanges = outJMSADEP.getReceivedExchanges();
		outJMSADEP.getReceivedExchanges().removeAll(allExchanges);

		// outJMSADEP.setExpectedMessageCount(1);
		outJMSADEP.setReporter(new AbstractPrintingClientResponder<PRTCPrintJobInstructionsConfirmType, Object>()
		{
			@Override
			protected void validateRequest(final PRTCPrintJobInstructionsConfirmType xmlRequest)
			{
				// nothing
			}

			@Override
			protected Object createResponse(final PRTCPrintJobInstructionsConfirmType xmlRequest)
			{
				new AD2EsbConverter().toADempiere(xmlRequest);

				return null;
			}
		});

		final int printJobInstructionsId = Integer.parseInt(printPackage.getPrintJobInstructionsID());
		// NOTE: there is no printJobInstructions because it's simulated
		// final I_C_Print_Job_Instructions printJobInstructionsBefore = InterfaceWrapperHelper.create(Env.getCtx(), printJobInstructionsId, I_C_Print_Job_Instructions.class, ITrx.TRXNAME_None);

		// Make the response
		final PrintJobInstructionsConfirm response = new PrintJobInstructionsConfirm();
		response.setPrintJobInstructionsID(printPackage.getPrintJobInstructionsID());
		response.setStatus(PrintJobInstructionsStatusEnum.Gedruckt);
		response.setErrorMsg(null);

		// Send the response and check that there are no errors
		restClient.sendPrintPackageResponse(printPackage, response);

		final I_C_Print_Job_Instructions printJobInstructionsAfter = InterfaceWrapperHelper.create(Env.getCtx(), printJobInstructionsId, I_C_Print_Job_Instructions.class, ITrx.TRXNAME_None);
		Assert.assertNotNull("C_Print_Job_Instructions(after) shall not be null", printJobInstructionsAfter);
		Assert.assertEquals("Invalid Status on C_Print_Job_Instructions(after)", X_C_Print_Job_Instructions.STATUS_Done, printJobInstructionsAfter.getStatus());

		Assert.assertTrue(true); // nothing failed, no error thrown
	}
}
