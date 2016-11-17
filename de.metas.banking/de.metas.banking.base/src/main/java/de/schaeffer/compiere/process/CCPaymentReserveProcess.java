package de.schaeffer.compiere.process;

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


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.SimpleTimeZone;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.db.CConnection;
import org.compiere.model.MBPBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MOrder;
import org.compiere.model.MOrg;
import org.compiere.model.MPayment;
import org.compiere.model.MSysConfig;
import org.compiere.model.PO;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.banking.model.I_C_Payment;
import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.process.JavaProcess;
import de.schaeffer.compiere.constants.Constants;

/**
 * Create and reserve payments for credit card orders with a promised date of
 * actual date + 2 days or less.
 *
 * @author Karsten Thiemann, kt@schaeffer-ag.de
 *
 */
@Deprecated
public class CCPaymentReserveProcess extends JavaProcess {

	private int TIME_BEFORE_PROMISED = 2; // 2 days



	private StringBuffer errorMessages = new StringBuffer(2000);
	private StringBuffer errorMessagesToReturn = new StringBuffer(2000);
	
	@Override
	protected String doIt() throws Exception {


		Properties ctx = getCtx();
		ctx.setProperty("#AD_Client_ID", getAD_Client_ID()+"");
		int[] orgs = PO.getAllIDs(MOrg.Table_Name, "AD_Client_ID=" + getAD_Client_ID(), get_TrxName());
		ctx.setProperty("#AD_Org_ID", orgs[0]+"");

		TIME_BEFORE_PROMISED = MSysConfig.getIntValue(Constants.TIME_BEFORE_PROMISED_CC, 1, getAD_Client_ID());

		int bankaccoutId = MSysConfig.getIntValue(Constants.CC_PAYMENT_BANKACCOUNT_ID, 0,
				getAD_Client_ID());
		if (bankaccoutId==0) {
			throw new AdempiereSystemError("No CC_PAYMENT_BANKACCOUNT_ID found - check System Config");
		}
		int reservedPayments = 0;



		String sql = "SELECT o.C_Order_ID, o.DocumentNo,"
				+ " COALESCE(o.Bill_BPartner_ID, o.C_BPartner_ID) as C_BPartner_ID, "
				+ " o.C_Currency_ID, o.GrandTotal, o.C_BP_BankAccount_ID, o.Paymentrule"
				+ " FROM C_Order o WHERE "
				+ " o.XX_IsCommercialApproved='Y'" + " AND o.IsSOTrx='Y'"
				+ " AND o.DocStatus IN ('CO', 'CL')"
				+ " AND o.Paymentrule IN ('K')" // TODO nur cc oder auch direct debit? ,'D'
				+ " AND NOT EXISTS (SELECT p.c_payment_id FROM c_payment p WHERE "
				+ "(p.c_order_id=o.c_order_id OR p.c_invoice_id IN "
				+ " (SELECT i.c_invoice_id from c_invoice i WHERE i.c_invoice_id=p.c_invoice_id AND i.c_order_id=o.c_order_id))"
				+ " AND p.docstatus IN('CO','CL','DR') AND p.xx_ccpaymentstate IN ('CA', 'RE', 'PU'))"
				+ " AND o.DatePromised > '2009-06-13' AND ad_client_id = " + getAD_Client_ID();

		sql += " AND o.DatePromised <= date_trunc('day'::text, ?::timestamp without time zone)";



		log.debug(sql);
		final Timestamp time = getReserveDate();
		log.debug(time.toString());
		final PreparedStatement pstmt_getCCOrders = DB.prepareStatement(sql, get_TrxName());
		//try to find existing (draft) payment - reuse it (if a reservation has failed)
		sql = "SELECT C_Payment_ID FROM C_Payment WHERE DocStatus='DR' AND C_Order_ID=? and ad_client_id = " + getAD_Client_ID();
		final PreparedStatement pstmt_getPayment = DB.prepareStatement(sql, get_TrxName());
		try {

			pstmt_getCCOrders.setTimestamp(1, time);

			final ResultSet rs = pstmt_getCCOrders.executeQuery();


			while (rs.next()) {
				final int orderId = rs.getInt("C_Order_ID");
				final int bpartnerId = rs.getInt("C_BPartner_ID");
				final int currencyId = rs.getInt("C_Currency_ID");
				final int bankaccountId = rs.getInt("C_BP_BankAccount_ID");
				final String paymentrule = rs.getString("Paymentrule");
				final String documentNo = rs.getString("DocumentNo");
				BigDecimal grandTotal = rs.getBigDecimal("GrandTotal");
				if (bankaccountId == 0) {
					// Error - no creditcard info!
					if (MSysConfig.getValue(Constants.LOCATION_NAME, Constants.SAG, Env.getAD_Client_ID(ctx)).equals(Constants.SAG)) {
						/*errorMessages = errorMessages.append("Fehler in Auftrag ").append(documentNo)
						.append(". Es ist keine Kreditkarte ausgew�hlt!").append(")\n");*/

							errorMessages = errorMessages.append("#######").append(" <-> ").append(documentNo)
							.append("\tIm Auftrag ist keine Kreditkarte ausgew�hlt.\n");



					}else {
						/*errorMessages = errorMessages.append("Error in order ").append(documentNo)
						.append(". No creditcard selected!").append(")\n");*/
						errorMessages = errorMessages.append("#######").append(" <-> ").append(documentNo)
						.append("\tNo creditcard selected!\n");

					}

					log.error("No C_BP_BankAccount_ID for C_Order: " + orderId
							+ " Please check the mandatory logic!");
					continue;

				}
				//try to find a draft payment for the order (dont create double payments..)
				int paymentId = 0;
				pstmt_getPayment.setInt(1, orderId);
				final ResultSet rs2 = pstmt_getPayment.executeQuery();
				if (rs2.next()) {
					paymentId = rs2.getInt("C_Payment_ID");
				}
				rs2.close();
				
				final MPayment paymentPO = new MPayment(ctx, paymentId, get_TrxName());
				final I_C_Payment payment = InterfaceWrapperHelper.create(paymentPO, I_C_Payment.class);
				
				payment.setC_BP_BankAccount_ID(bankaccoutId);
				paymentPO.setAmount(currencyId, grandTotal);
				payment.setC_BPartner_ID(bpartnerId);
				payment.setC_Order_ID(orderId);
				
				if (MOrder.PAYMENTRULE_CreditCard.equals(paymentrule)) {
					//we have a credit card payment - set tendertype, isonline and creditcard data
					paymentPO.setTenderType(MPayment.TENDERTYPE_CreditCard);
					paymentPO.setIsOnline(true);
					MBPBankAccount cc = new MBPBankAccount(ctx, bankaccountId, get_TrxName());
					if(cc.get_ID()!= bankaccountId) {
						//creditcard not found
						if (MSysConfig.getValue(Constants.LOCATION_NAME, Constants.SAG, Env.getAD_Client_ID(ctx)).equals(Constants.SAG)) {
							/*errorMessages = errorMessages.append("Fehler in Auftrag ").append(documentNo)
							.append(".\nReferenzierte Kreditkarte wurde nicht gefunden (ID: ").append(bankaccountId)
							.append(")\n");*/
							errorMessages = errorMessages.append(paymentPO.getDocumentNo()).append(" <-> ").append(documentNo)
							.append("\tReferenzierte Kreditkarte wurde nicht gefunden, ID: ").append(bankaccountId).append("\n");
						} else {
							/*errorMessages = errorMessages.append("Error in order ").append(documentNo)
							.append(".\nCreditcard was not found (ID: ").append(bankaccountId)
							.append(")\n");*/
							errorMessages = errorMessages.append(paymentPO.getDocumentNo()).append(" <-> ").append(documentNo)
							.append("\tCreditcard not found, ID: ").append(bankaccountId).append("\n");

						}

						log.error("creditcard not found: " + bankaccountId);
						continue;
					} else {
						//payment: set credit card and payment processor
						if(cc.getCreditCardType()==null || cc.getCreditCardNumber()==null
//								|| cc.getCreditCardVV()==null
								) {
							//missing creditcard data
							if (MSysConfig.getValue(Constants.LOCATION_NAME, Constants.SAG, Env.getAD_Client_ID(ctx)).equals(Constants.SAG)) {
								/*errorMessages = errorMessages.append("Fehler in Auftrag ").append(documentNo)
								.append(". Fehlende Angaben (Nummer oder Typ) in der Kreditkarte (ID: ").append(bankaccountId)
								.append(")\n");*/
								errorMessages = errorMessages.append(paymentPO.getDocumentNo()).append(" <-> ").append(documentNo)
								.append("\tFehlende Angaben (Nummer oder Typ) in der Kreditkarte, ID: ").append(bankaccountId).append("\n");
							} else {
								/*errorMessages = errorMessages.append("Error in order ").append(documentNo)
								.append(". Missing data (number or type) in creditcard (ID: ").append(bankaccountId)
								.append(")\n");*/
								errorMessages = errorMessages.append(paymentPO.getDocumentNo()).append(" <-> ").append(documentNo)
								.append("\tMissing data (number or type) in creditcard, ID: ").append(bankaccountId).append("\n");

							}
							log.error("missing data for creditcard: " + cc.get_ID());
							continue;
						} else {
							if(!paymentPO.setCreditCard(MPayment.TRXTYPE_Authorization, cc.getCreditCardType(),
								cc.getCreditCardNumber(), cc.getCreditCardVV(), cc.getCreditCardExpMM(),
								cc.getCreditCardExpYY())) {
								
								payment.setA_Street(cc.getA_Street());
								payment.setA_Zip(cc.getA_Zip());
								payment.setA_Name(cc.getA_Name());
								payment.setA_Country(cc.getA_Country());
								payment.setA_City(cc.getA_City());
								payment.setA_State(cc.getA_State());
													
								//unable to set credit card
								if (MSysConfig.getValue(Constants.LOCATION_NAME, Constants.SAG, Env.getAD_Client_ID(ctx)).equals(Constants.SAG)) {
									/*errorMessages = errorMessages.append("Fehler in Auftrag ").append(documentNo)
									.append(". Fehlende/falsche Angaben in der Kreditkarte (ID: ").append(bankaccountId); */
									errorMessages = errorMessages.append(paymentPO.getDocumentNo()).append(" <-> ").append(documentNo)
									.append("\tFehlende/falsche Angaben in der Kreditkarte, ID: ").append(bankaccountId).append("\n");
								} else {
									/*errorMessages = errorMessages.append("Error in order ").append(documentNo)
									.append(". Missing or Wrong creditcard data (ID: ").append(bankaccountId);*/
									errorMessages = errorMessages.append(paymentPO.getDocumentNo()).append(" <-> ").append(documentNo)
									.append("\tMissing or wrong creditcard data, ID: ").append(bankaccountId).append("\n");

								}
								log.error("unable to set creditcard: " + cc.get_ID() + " for order: " + orderId);
								continue;

							}
							if(!paymentPO.setPaymentProcessor()){
								//no payment processor found
								/*errorMessages = errorMessages.append("Fehler in Auftrag ").append(documentNo)
								.append(". Kein Payment Prozessor gefunden f�r Kreditkarte (ID: ").append(bankaccountId)
								.append(")\n"); */
								errorMessages = errorMessages.append(paymentPO.getDocumentNo()).append(" <-> ").append(documentNo)
								.append("\tKein Payment Prozessor gefunden f�r Kreditkarten-ID: ").append(bankaccountId).append("\n");

								log.error("no payment processor found for creditcard: " + cc.get_ID());
								continue;

							}
						}
					}
				}

				//save changes
				if (!paymentPO.save(get_TrxName())) {
					/*errorMessages = errorMessages.append("Cannot save MPayment for order: ")
					.append(documentNo).append("\n"); */
					errorMessages = errorMessages.append(paymentPO.getDocumentNo()).append(" <-> ").append(documentNo)
					.append("\tCannot save MPayment\n");
					log.error("Cannot save MPayment for order: " + documentNo);
					continue;
				}
				//set payment reference in order
				MOrder order = new MOrder(ctx, orderId, get_TrxName());
				order.setC_Payment_ID(paymentPO.get_ID());
				order.save(get_TrxName());
				log.warn("before is online");
				//process the payment (online)
				if(paymentPO.isOnline()) {

					if(!paymentPO.processOnline()) {
						payment.setCCPaymentState(I_C_Payment.CCPAYMENTSTATE_Error);
						log.error("Unable to process online: " + paymentPO.getErrorMessage());
						if (MSysConfig.getValue(Constants.LOCATION_NAME, Constants.SAG, Env.getAD_Client_ID(ctx)).equals(Constants.SAG)) {
/*							errorMessages = errorMessages.append("Fehler bei Onlinebuchung in Zahlung: ")
							.append(payment.getDocumentNo()).append(" f�r Auftrag ").append(documentNo).append(". ")
							.append(payment.getErrorMessage()).append("\n"); */

								errorMessages = errorMessages.append(paymentPO.getDocumentNo()).append(" <-> ").append(documentNo)
								.append("\tFehler bei Onlinebuchung: ").append(paymentPO.getErrorMessage()).append("\n");

						} else {
							/*errorMessages = errorMessages.append("Error while online process payment: ")
							.append(payment.getDocumentNo()).append(" Order ").append(documentNo).append(". ")
							.append(payment.getErrorMessage()).append("\n");*/
							errorMessages = errorMessages.append(paymentPO.getDocumentNo()).append(" <-> ").append(documentNo)
							.append("\tError while posting: ").append(paymentPO.getErrorMessage()).append("\n");

						}

					} else {
						//authorization was sucessful - next step will be capture
						paymentPO.setTrxType(MPayment.TRXTYPE_DelayedCapture);
						payment.setCCPaymentState(I_C_Payment.CCPAYMENTSTATE_Reserved);
						reservedPayments++;

					}
				}
				paymentPO.save(get_TrxName());
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			throw new AdempiereSystemError("SQLException: " + e.getLocalizedMessage());
		} finally {
			pstmt_getCCOrders.close();
			pstmt_getPayment.close();
		}
		//TODO: email an warenannahme mit errorMessages
		if (errorMessages.length()>0) {
			sendEmail();
		}


		errorMessagesToReturn.append(errorMessages.toString().replaceAll("<", "&lt;").replaceAll("\n", "<br>"));
		/*errorMessages = errorMessages.insert(1, "Die folgenden Fehler traten auf:\n\n" +
				"Zahlung <-> Auftrag\tFehlermeldung\n--------------------------------------------------------------------------------\n");*/
		log.info(errorMessagesToReturn.toString());
		log.info(errorMessages.toString());

		if (MSysConfig.getValue(Constants.LOCATION_NAME, Constants.SAG, Env.getAD_Client_ID(ctx)).equals(Constants.SAG)) {
			return "Reservierte Zahlungen: " + reservedPayments + "<br>Zahlung &&lt;-> Auftrag Fehlermeldung<br>--------------------------------------------------------------------------------<br>" + errorMessagesToReturn.toString();
		} else {
			return "reserved payments: " + reservedPayments + "<br>Payment &&lt;-> Order Error<br>--------------------------------------------------------------------------------<br>" + errorMessagesToReturn.toString();
		}


	}

	/**
	 * Calculates the reserve date.
	 *
	 * @param deliveryTime
	 * @return
	 */
	private Timestamp getReserveDate() {
		final SimpleTimeZone mez = new SimpleTimeZone(+1 * 60 * 60 * 1000, "ECT");
		mez.setStartRule(Calendar.MARCH, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		mez.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		final Calendar cal = GregorianCalendar.getInstance(mez);
		// wenn nach x Uhr, einen Tag aufschlagen
		// cal.add(Calendar.DAY_OF_MONTH, 1);

		for (int i = 0; i < TIME_BEFORE_PROMISED; i++) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			final int weekday = cal.get(Calendar.DAY_OF_WEEK);
			// wenn das promisedDate auf das Wochenende f�llt, auf Montag
			// verschieben
			if (weekday == 0) { // Sonntag
				cal.add(Calendar.DAY_OF_MONTH, 1);
			} else if (weekday == 7) { // Samstag
				cal.add(Calendar.DAY_OF_MONTH, 2);
			}
		}
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * Send error messages to recipient defined in the system config.
	 */
	private void sendEmail(){

		final String liveSystemServerName = MSysConfig.getValue(Constants.LIVESYSTEM_SERVERNAME,
				"", Env.getAD_Client_ID(Env.getCtx()));
		if ("".equals(liveSystemServerName)) {
			log.error("LIVESYSTEM_SERVERNAME not found, check the System Configuration window");
			return;
		}
		final String adresses = MSysConfig.getValue(Constants.CCPAYMENT_REVIEW_MAILS, "", Env
				.getAD_Client_ID(Env.getCtx()));
		String[] emailAddresses = adresses.split(";");

		// if we are not on the server send all mails to test mailadresses
		final CConnection cc = CConnection.get();
		if (!cc.getAppsHost().equalsIgnoreCase(liveSystemServerName)) {
			emailAddresses = MSysConfig.getValue(Constants.TEST_MAILADDRESS, "", Env
					.getAD_Client_ID(Env.getCtx())).split(";");
		}
		final Properties ctx = Env.getCtx();
		final MClient client = MClient.get(ctx);

		if (client.getSMTPHost() == null || client.getSMTPHost().length() == 0) {
			log.error("No SMTP Host found");
			return;
		}

		for (int i = 0; i < emailAddresses.length; i++) {

			EMail email = null;

			if (MSysConfig.getValue(Constants.LOCATION_NAME, Constants.SAG, Env.getAD_Client_ID(ctx)).equals(Constants.SAG)) {
				email = client.createEMail(emailAddresses[i], "Fehler bei Kreditkartenreservierung",
					"Die folgenden Fehler traten auf:\n\n" +
				"Zahlung <-> Auftrag\tFehlermeldung\n--------------------------------------------------------------------------------\n" + errorMessages.toString(), false);
			} else {
				email = client.createEMail(emailAddresses[i], "Creditcard reservation error",
						"The following errors occured:\n\n" +
				"Payment <-> Order  \tFehlermeldung\n--------------------------------------------------------------------------------\n" + errorMessages.toString(), false);

			}

			if (!email.isValid()) {
				log.error("Unable to send mail (not valid) - " + email);
			}
			
			final EMailSentStatus emailSentStatus = email.send();
			final boolean ok = emailSentStatus.isSentOK();
			//
			if (ok) {
				log.debug("sucessfully sent to " + emailAddresses[i]);
			} else {
				log.error("Unable to send mail (not sent) - " + emailAddresses[i]);
			}
		}

	}

	/**
	 * Post process actions (outside trx). Please note that at this point the
	 * transaction is committed so you can't rollback. This method is useful if
	 * you need to do some custom work when the process complete the work (e.g.
	 * open some windows).
	 *
	 * @param success
	 *            true if the process was success
	 * @since 3.1.4
	 */
	@Override
	protected void postProcess(boolean success) {

	}

}
