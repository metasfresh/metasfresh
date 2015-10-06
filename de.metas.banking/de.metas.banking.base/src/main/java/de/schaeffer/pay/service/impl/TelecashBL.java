package de.schaeffer.pay.service.impl;

/*
 * #%L
 * de.metas.banking.base
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.NumberFormat;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.POWrapper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_C_Currency;
import org.compiere.model.MPayment;
import org.compiere.model.MSysConfig;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import de.firstdata.common.utils.IO;
import de.firstdata.ipgapi.client.IPGApiClient;
import de.firstdata.ipgapi.client.IPGApiResult;
import de.firstdata.ipgapi.client.action.IPGApiActionFactory;
import de.firstdata.ipgapi.client.action.Validate;
import de.firstdata.ipgapi.client.exception.ClientException;
import de.firstdata.ipgapi.client.exception.InternalException;
import de.firstdata.ipgapi.client.exception.MerchantException;
import de.firstdata.ipgapi.client.exception.ProcessingException;
import de.firstdata.ipgapi.client.exception.ServerException;
import de.firstdata.ipgapi.client.transaction.Amount;
import de.firstdata.ipgapi.client.transaction.IPGApiTransactionFactory;
import de.firstdata.ipgapi.client.transaction.creditcard.CCSaleTransaction;
import de.firstdata.ipgapi.client.transaction.creditcard.CreditCard;
import de.firstdata.ipgapi.schema.ipgapi.Error;
import de.firstdata.ipgapi.schema.ipgapi.IPGApiActionResponse;
import de.schaeffer.pay.exception.TelecashRuntimeException;
import de.schaeffer.pay.exception.TelecashUserException;
import de.schaeffer.pay.service.ITelecashBL;

public class TelecashBL implements ITelecashBL {

	protected static final String TELECASH_TRUSTSTORE_PW = "IPGAPI";
	protected static final String TELECASH_TRUSTSTORE_RESOURCE = "/de/schaeffer/pay/telecash/truststore.jks";
	/**
	 * @deprecated
	 * ISO 4217: EUR = 978
	 */
	public static final String ISO_4217_NUM_EURO = "978";

	String performSale(final TelecashData connectionData,
			final String ccNumber, final String expMM, final String expYY,
			final String cardCodeValue, final String customerName,
			final BigDecimal payAmt, final String currencyIso4217)
			throws TelecashUserException {

		final CreditCard cC = mkCreditCard(ccNumber, expMM, expYY,
				cardCodeValue);

		try {

			final IPGApiResult result = performSaleWithParams(connectionData,
					cC, customerName, payAmt, currencyIso4217);
			return result.getOrderId();

		} catch (ProcessingException e) {
			throw TelecashUserException.validationFailure(e.getErrorMessage());
		}
	}

	public String validateCard(final TelecashData connectionData,
			final String ccNumber, final String expMM, final String expYY,
			final String cardCodeValue) {

		final CreditCard cC = mkCreditCard(ccNumber, expMM, expYY,
				cardCodeValue);

		return validate(connectionData, cC);
	}

	@Override
	public boolean performSale(final MPayment paymentPO) {

		final I_C_Currency currency = paymentPO.getC_Currency();
		final de.metas.adempiere.model.I_C_Currency currencyW = POWrapper.create(currency, de.metas.adempiere.model.I_C_Currency.class);
		
		final String iso4217Numeric = currencyW.getISO_4217Numeric();
		if (Check.isEmpty(iso4217Numeric, true)) 
		{
			Object[] args = new Object[]{currencyW.getISO_Code()};
			String msg = Services.get(IMsgBL.class).getMsg(Env.getCtx(), "TELECASH_CURRENCY_NOT_SUPPORTED", args);
			throw new AdempiereException(msg);
		}

		final int scale = currency.getStdPrecision();

		final BigDecimal payAmt = paymentPO.getPayAmt();

		final BigDecimal payAmtToUse = payAmt.setScale(scale, RoundingMode.HALF_UP);

		final String customerName = paymentPO.getC_BPartner().getName();

		final CreditCard cC = mkCreditCard(paymentPO);

		boolean success;

		final TelecashData connectionData = getConnectionData(paymentPO
				.getAD_Client_ID(), paymentPO.getAD_Org_ID());

		try
		{
			// metas: c.ghita@metas.ro : start
			final String docNo = paymentPO.getC_Invoice() != null ? paymentPO.getC_Invoice().getDocumentNo()
					: paymentPO.getC_Order().getDocumentNo();
	
			final IPGApiResult result = 
					performSaleWithParams(connectionData, cC, customerName, payAmtToUse, iso4217Numeric, docNo);

			paymentPO.setOrig_TrxID(result.getProcessorReferenceNumber());
			paymentPO.setR_RespMsg(result.getProcessorResponseMessage());
			paymentPO.saveEx();

			// Notes:
			// -MPayment.setR_PnRef would also call setDocumentNo
			// -beforeSave uses the R_pnRef as DocumentNo is there is none yet
			paymentPO.setR_PnRef(result.getOrderId());

			success = true;

		} catch (ProcessingException e) {

			paymentPO.setR_RespMsg(e.getErrorMessage());
			success = false;
		}

		return success;
	}

	IPGApiResult performSaleWithParams(final TelecashData connectionData,
			final CreditCard cC, final String customerName,
			final BigDecimal amt, final String currencyIso4217Num)
			throws ProcessingException 
	{
		return performSaleWithParams(connectionData, cC, customerName, 
						amt, currencyIso4217Num, null);
	}
	
	/*
	 * metas: c.ghita@metas.ro : add parameter document no
	 */
	IPGApiResult performSaleWithParams(final TelecashData connectionData,
			final CreditCard cC, final String customerName,
			final BigDecimal amt, final String currencyIso4217Num, final String DocumentNo)
			throws ProcessingException {

		System.out.println("Amt=" + amt);

		final Amount amount = new Amount(amt, currencyIso4217Num);

		try {

			//metas: US035: c.ghita@metas.ro : start
			if (Util.isEmpty(connectionData.url, true))
			{
				Object[] args = new Object[]{"TELECASH_URL"};
				String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
				throw new AdempiereException(msg);
			}
			if (Util.isEmpty(connectionData.storeId, true))
			{
				Object[] args = new Object[]{"TELECASH_STORE_ID"};
				String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
				throw new AdempiereException(msg);
			}
			if (Util.isEmpty(connectionData.password, true))
			{
				Object[] args = new Object[]{"TELECASH_PASSWORD"};
				String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
				throw new AdempiereException(msg);
			}
			if (connectionData.keyStore.length < 1)
			{
				Object[] args = new Object[]{"TELECASH_KEY_RESSOURCE"};
				String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
				throw new AdempiereException(msg);
			}
			if (Util.isEmpty( connectionData.keyStorePW, true))
			{
				Object[] args = new Object[]{"TELECASH_KEY_PASSWORD"};
				String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
				throw new AdempiereException(msg);
			}
			//metas: US035: c.ghita@metas.ro : end
			
			final CCSaleTransaction transaction = IPGApiTransactionFactory
					.createSaleTransactionCredit(amount, cC);
			// some transactions may include further information e.g. the
			// customer
			transaction.setName(customerName);

			final IPGApiClient client = mkClient(connectionData);
			
			//metas: c.ghita@metas.ro : start
			//reference number should be date-time document number (invoice)
			if (DocumentNo != null)
			{
				Timestamp now = new Timestamp(System.currentTimeMillis());
				String refNo =  now + "_" + DocumentNo;
				transaction.setOrderId(refNo);
			}
			//metas: c.ghita@metas.ro : end
			return client.commitTransaction(transaction);
			

		} catch (ProcessingException e) {
			throw e;
		} catch (IOException e) {
			throw new TelecashRuntimeException(e);
		} catch (JAXBException e) {
			throw new TelecashRuntimeException(e);
		} catch (InternalException e) {
			throw new TelecashRuntimeException(e);
		} catch (MerchantException e) {
			throw new TelecashRuntimeException(e);
		} catch (ServerException e) {
			throw new TelecashRuntimeException(e);
		} catch (ClientException e) {
			throw new TelecashRuntimeException(e);
		} catch (SOAPException e) {
			throw new TelecashRuntimeException(e);
		}
	}

	private IPGApiClient mkClient(final TelecashData connectionData) throws IOException, JAXBException
	{
		return new IPGApiClient(
				connectionData.url,
				connectionData.storeId, 
				connectionData.password,
				connectionData.trustStore,
				connectionData.trustStorePW,
				connectionData.keyStore, 
				connectionData.keyStorePW);
	}

	CreditCard mkCreditCard(final MPayment payment) {

		final NumberFormat fmt = NumberFormat.getInstance();
		fmt.setMaximumIntegerDigits(2);
		fmt.setMinimumIntegerDigits(2);

		final String ccNumber = payment.getVolatileCreditCardNumber(); // metas 00036

		final String expMM = fmt.format(payment.getCreditCardExpMM());
		final String expYY = fmt.format(payment.getCreditCardExpYY());
		final String cardCodeValue = payment.getVolatileCreditCardVV();  // metas 00036

		final CreditCard cC = mkCreditCard(ccNumber, expMM, expYY, cardCodeValue);

		return cC;
	}

	private CreditCard mkCreditCard(final String ccNumber, final String expMM,
			final String expYY, final String cardCodeValue) {

		final String ccNumWithOutSpaces = ccNumber.replace(" ", "");
		final String ccvWithOutSpaces = cardCodeValue.replace(" ", "");

		final CreditCard cC = new CreditCard(ccNumWithOutSpaces, expMM, expYY, ccvWithOutSpaces);
		return cC;
	}

	@Override
	public String validateCard(final int adClientId, final int adOrgId,
			final String ccNumber, final String expMM, final String expYY,
			final String cardCodeValue) {

		final TelecashData connectionData = getConnectionData(adClientId,
				adOrgId);

		final CreditCard cC = mkCreditCard(ccNumber, expMM, expYY,
				cardCodeValue);

		return validate(connectionData, cC);
	}

	@Override
	public void validateCard(final MPayment payment)
			throws TelecashUserException {

		final CreditCard cC = mkCreditCard(payment);

		final TelecashData connectionData = getConnectionData(payment
				.getAD_Client_ID(), payment.getAD_Org_ID());

		final String errorMsg = validate(connectionData, cC);

		if (!Util.isEmpty(errorMsg)) {
			throw TelecashUserException.validationFailure(errorMsg);
		}
	}

	private String validate(final TelecashData connectionData,
			final CreditCard cC) {

		try {
			
			//metas: US035: c.ghita@metas.ro : start
			if (Util.isEmpty(connectionData.url, true))
			{
				Object[] args = new Object[]{"TELECASH_URL"};
				String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
				throw new AdempiereException(msg);
			}
			if (Util.isEmpty(connectionData.storeId, true))
			{
				Object[] args = new Object[]{"TELECASH_STORE_ID"};
				String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
				throw new AdempiereException(msg);
			}
			if (Util.isEmpty(connectionData.password, true))
			{
				Object[] args = new Object[]{"TELECASH_PASSWORD"};
				String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
				throw new AdempiereException(msg);
			}
			if (connectionData.keyStore.length < 1)
			{
				Object[] args = new Object[]{"TELECASH_KEY_RESSOURCE"};
				String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
				throw new AdempiereException(msg);
			}
			if (Util.isEmpty( connectionData.keyStorePW, true))
			{
				Object[] args = new Object[]{"TELECASH_KEY_PASSWORD"};
				String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
				throw new AdempiereException(msg);
			}
			//metas: US035: c.ghita@metas.ro : end
			
			final IPGApiClient client = mkClient(connectionData);

			final Validate validate = IPGApiActionFactory.createValidate(cC);
			final IPGApiActionResponse response = client.commitAction(validate);

			if (response.isSuccessfully()) {
				return "";
			}

			final StringBuilder sb = new StringBuilder();
			boolean first = true;
			for (final Error error : response.getError()) {
				if (first) {
					first = false;
				} else {
					sb.append("\n");
				}
				sb.append(error.getErrorMessage());
				sb.append(" (Fehlercode ");
				sb.append(error.getCode());
				sb.append(")");
			}

			return sb.toString();

		} catch (IOException e) {
			throw new TelecashRuntimeException(e);
		} catch (JAXBException e) {
			throw new TelecashRuntimeException(e);
		} catch (MerchantException e) {
			throw new TelecashRuntimeException(e);
		} catch (ProcessingException e) {
			throw new TelecashRuntimeException(e);
		} catch (SOAPException e) {
			throw new TelecashRuntimeException(e);
		} catch (ServerException e) {
			throw new TelecashRuntimeException(e);
		}
	}

	/**
	 * getBytes reads a resource and returns a byte array
	 * 
	 * @param resource
	 *            the resource to read
	 * @return the resource as byte array
	 */
	public static byte[] getBytes(final String resource) throws IOException {
		final InputStream input = IO.class.getResourceAsStream(resource);
		if (input == null) {
			throw new IOException(resource);
		}
		try {
			final byte[] bytes = new byte[input.available()];
			input.read(bytes);
			return bytes;
		} finally {
			try {
				input.close();
			} catch (IOException e) {

			}
		}
	}

	static final TelecashData getConnectionData(final int adClientId,
			final int adOrgId) {

		final String url = MSysConfig.getValue("TELECASH_URL", adClientId,
				adOrgId);
		final String storeId = MSysConfig.getValue("TELECASH_STORE_ID",
				adClientId, adOrgId);
		final String password = MSysConfig.getValue("TELECASH_PASSWORD",
				adClientId, adOrgId);
		final String keyRessource = MSysConfig.getValue(
				"TELECASH_KEY_RESSOURCE", adClientId, adOrgId);
		final String keyPassword = MSysConfig.getValue("TELECASH_KEY_PASSWORD",
				adClientId, adOrgId);

		//metas: US035: c.ghita@metas.ro : start
		if (Util.isEmpty(url, true))
		{
			Object[] args = new Object[]{"TELECASH_URL"};
			String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
			throw new AdempiereException(msg);
		}
		if (Util.isEmpty(storeId, true))
		{
			Object[] args = new Object[]{"TELECASH_STORE_ID"};
			String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
			throw new AdempiereException(msg);
		}
		if (Util.isEmpty(password, true))
		{
			Object[] args = new Object[]{"TELECASH_PASSWORD"};
			String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
			throw new AdempiereException(msg);
		}
		if (Util.isEmpty(keyRessource, true))
		{
			Object[] args = new Object[]{"TELECASH_KEY_RESSOURCE"};
			String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
			throw new AdempiereException(msg);
		}
		if (Util.isEmpty(keyPassword, true))
		{
			Object[] args = new Object[]{"TELECASH_KEY_PASSWORD"};
			String msg = Msg.getMsg(Env.getCtx(), "TELECASH_ERROR", args);
			throw new AdempiereException(msg);
		}
		//metas: US035: c.ghita@metas.ro : end
		
		return new TelecashData(url, storeId, password, TELECASH_TRUSTSTORE_RESOURCE, TELECASH_TRUSTSTORE_PW, keyRessource, keyPassword);
	}

	public static final class TelecashData 
	{
	
		final String url;
		final String storeId;
		final String password;
		byte[] trustStore;
		String trustStorePW;
		final byte[] keyStore;
		final String keyStorePW;

		/**
		 * 
		 * @param url
		 * @param storeId
		 * @param password
		 * @param keyStoreResource
		 *            /de/schaeffer/pay/telecash/WS12022222583._.1.ks
		 * @param keyStorePW
		 */
		public TelecashData(
				final String url, 
				final String storeId,
				final String password, 
				final String trustStoreResource,
				final String trustStorePW,
				final String keyStoreResource,
				final String keyStorePW) 
		{
			this.url = url;
			this.storeId = storeId;
			this.password = password;

			this.trustStorePW = trustStorePW;
			this.keyStorePW = keyStorePW;

			trustStore = IO.getBytes(trustStoreResource);
			keyStore = IO.getBytes(keyStoreResource);
		}
	}
}
