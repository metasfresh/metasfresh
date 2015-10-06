/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.io.Serializable;
import java.util.logging.Level;

import org.compiere.util.CLogMgt;

/**
 * 	PayPal Payment Processor Services Interface
 *	
 *  @author Jorg Janke
 *  @version $Id: PP_PayPal.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class PP_PayPal extends PaymentProcessor
	implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6199700578420668764L;
	/**	Status					*/
	private boolean			m_ok = false;
	/** PayPal Caller Service	*/
//	private CallerServices 	m_caller; 

	
	/**
	 * 	Process CC
	 *	@return true if processed
	 *	@throws IllegalArgumentException
	 */
	public boolean processCC ()
		throws IllegalArgumentException
	{
		return m_ok;
	}	//	processCC

	/**
	 * 	Is Processed OK
	 *	@return true if ok
	 */
	public boolean isProcessedOK ()
	{
		return m_ok;
	}	//	isProcessedOK

/**    
    public void initialize() throws PayPalException 
    {
    	APIProfile profile = ProfileFactory.createAPIProfile();
    	profile.setAPIUsername("sdk-seller_api1.sdk.com");
    	profile.setAPIPassword("12345678");
    	profile.setCertificateFile("../Cert/sdk-seller.p12");
    	profile.setPrivateKeyPassword("password");
    	profile.setEnvironment("sandbox");
    	//
    	m_caller = new CallerServices();			
    	m_caller.setAPIProfile(profile);
    }	//	initialize

    
    public void transactionSearch() throws PayPalException 
    {
    	System.out.println("\n########## Transaction Search ##########\n");
		TransactionSearchRequestType request = new TransactionSearchRequestType();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2005,2,25);
		request.setStartDate(calendar);
		request.setTransactionID("4KT33454GG2130806");
	
		TransactionSearchResponseType response = 
			(TransactionSearchResponseType) caller.call("TransactionSearch", request);
		System.out.println("Operation Ack: " + response.getAck());
		System.out.println("---------- Results ----------");
				
		// Check to see if any transactions were found
		PaymentTransactionSearchResultType[] ts = response.getPaymentTransactions();
		if (ts != null) 
		{					
			System.out.println("Found " + ts.length + " records");
					
			// Display the results of the first transaction returned
			for (int i = 0; i < ts.length; i++) 
			{	
				System.out.println("\nTransaction ID: " + ts[i].getTransactionID());
				System.out.println("Payer Name: " + ts[i].getPayerDisplayName());
				System.out.println("Gross Amount: " + ts[i].getGrossAmount().getCurrencyID() + " " + ts[i].getGrossAmount().get_value());
				System.out.println("Fee Amount: " + ts[i].getFeeAmount().getCurrencyID() + " " + ts[i].getFeeAmount().get_value());
				System.out.println("Net Amount: " + ts[i].getNetAmount().getCurrencyID() + " " + ts[i].getNetAmount().get_value());
			}
		}
		else 
		{
			System.out.println("Found 0 transaction");
		}
    }	//	transactionSearch
   
    
    public void getTransactionDetails() throws PayPalException 
    {
    	System.out.println("\n########## Get Transaction Details ##########\n");
		GetTransactionDetailsRequestType request = new GetTransactionDetailsRequestType();
 	  	request.setTransactionID("7J110007888511720");
 	  	
 	  	GetTransactionDetailsResponseType response = (GetTransactionDetailsResponseType) caller.call("GetTransactionDetails", request);
 	  	System.out.println("Operation Ack: " + response.getAck());
 	  	System.out.println("---------- Results ----------");
 	  	
 	  	PaymentTransactionType ts = response.getPaymentTransactionDetails();
 	  	System.out.println("\nTransaction ID: " + ts.getPaymentInfo().getTransactionID());
 	  	System.out.println("Payer Name: " + ts.getPayerInfo().getPayer());
 	  	System.out.println("Receiver Name: " + ts.getReceiverInfo().getReceiver());
 	  	System.out.println("Gross Amount: " + ts.getPaymentInfo().getGrossAmount().getCurrencyID() + " " + ts.getPaymentInfo().getGrossAmount().get_value());
    }	//	getTransactionDetails
    
    
    public void directPayment() throws PayPalException 
    {
    	System.out.println("\n########## Do Direct Payment ##########\n");
    	
    	DoDirectPaymentRequestType request = new DoDirectPaymentRequestType();
		DoDirectPaymentRequestDetailsType details = new DoDirectPaymentRequestDetailsType();

		CreditCardDetailsType creditCard = new CreditCardDetailsType();
		creditCard.setCreditCardNumber("4721930402892796");
		creditCard.setCreditCardType(CreditCardTypeType.Visa);
		creditCard.setCVV2("000");
		creditCard.setExpMonth(11);
		creditCard.setExpYear(2007);
		
		PayerInfoType cardOwner = new PayerInfoType();
		cardOwner.setPayerCountry(CountryCodeType.US);
		
		AddressType address = new AddressType();
		address.setPostalCode("95101");
		address.setStateOrProvince("CA");
		address.setStreet1("123 Main St");
		address.setCountryName("US");
		address.setCountry(CountryCodeType.US);
		address.setCityName("San Jose");
		cardOwner.setAddress(address);
			
		PersonNameType payerName = new PersonNameType();
		payerName.setFirstName("SDK");
		payerName.setLastName("Buyer");
		cardOwner.setPayerName(payerName);
		
		creditCard.setCardOwner(cardOwner);
		details.setCreditCard(creditCard);
		
		details.setIPAddress("12.36.5.78");
		details.setMerchantSessionId("456977");
		details.setPaymentAction(PaymentActionCodeType.Sale);

		PaymentDetailsType payment = new PaymentDetailsType();
		
		BasicAmountType orderTotal = new BasicAmountType();
		orderTotal.setCurrencyID(CurrencyCodeType.USD);
		orderTotal.set_value("20.00");
		payment.setOrderTotal(orderTotal);
		
		details.setPaymentDetails(payment);
		request.setDoDirectPaymentRequestDetails(details);
		
		DoDirectPaymentResponseType response = (DoDirectPaymentResponseType) caller.call("DoDirectPayment", request);
    	
    	System.out.println("Operation Ack: " + response.getAck());
 	  	System.out.println("---------- Results ----------");
 	  	System.out.println("\nTransaction ID: " + response.getTransactionID());
 	  	System.out.println("CVV2: " + response.getCVV2Code());
 	  	System.out.println("AVS: " + response.getAVSCode());
 	  	System.out.println("Gross Amount: " + response.getAmount().getCurrencyID() 
			+ " " + response.getAmount().get_value());
    }	//	directPayment
**/

	/**
	 * 	Test
	 *	@param args ifnored
	 */
	public static void main (String[] args)
	{
		CLogMgt.initialize(true);
		CLogMgt.setLevel(Level.ALL);
		PP_PayPal pp = new PP_PayPal();
		pp.processCC();
		pp.isProcessedOK();
		
	}	//	main
	
}	//	PP_PayPal
