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
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.MBPBankAccount;
import org.compiere.model.MDirectDebit;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrg;
import org.compiere.model.X_C_DirectDebitLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.jdtaus.banking.Textschluessel;
import org.jdtaus.banking.TextschluesselVerzeichnis;
import org.jdtaus.banking.dtaus.Transaction;
import org.jdtaus.core.container.ContainerFactory;

import de.schaeffer.compiere.tools.DtaHelper;

public class RemittanceExport extends SvrProcess {

	private int c_invoice_id = 0;

	private String trxName = null;

	private Trx trx;

	private boolean success = true;

	private MDirectDebit directDebit;

	private Properties ctx = null;
	
	private int c_bp_bankaccount_own_id = 0;
	private int c_bp_bankaccount_to_id = 0;
	
	private List<Transaction> getTransactions() throws ParseException {

		System.out.println(c_bp_bankaccount_own_id);
		System.out.println(c_bp_bankaccount_to_id);
		
		List<Transaction> transactionList = new ArrayList<Transaction>();

		final TextschluesselVerzeichnis textschluesselVerzeichnis = (TextschluesselVerzeichnis) ContainerFactory
				.getContainer().getObject(TextschluesselVerzeichnis.class.getName());

		final Textschluessel textschluessel = textschluesselVerzeichnis.getTextschluessel(51, 0,
				new java.util.Date());

		directDebit = new MDirectDebit(Env.getCtx(), 0, trxName);
		directDebit.setAD_Org_ID(Env.getAD_Org_ID(ctx));
		directDebit.setIsRemittance(true);
			
		success = directDebit.save(trxName);

		log.fine("MDirectDebit created (ID: " + directDebit.get_ID() + ")");

		MInvoice invoice = new MInvoice(ctx, c_invoice_id, null);
		I_C_BP_BankAccount bankAccountOwn = InterfaceWrapperHelper.create(getCtx(), c_bp_bankaccount_own_id, I_C_BP_BankAccount.class, null);
		MBPBankAccount bpBankAccount = new MBPBankAccount(ctx, c_bp_bankaccount_to_id, null);

		transactionList.add(DtaHelper.buildTransaction(
			textschluessel, 
			"VZ1", 
			"RE NR"	+ invoice.getDocumentNo(), 
			invoice.getC_Currency().getISO_Code(), 
			invoice.getOpenAmt(), 
			new MOrg(Env.getCtx(), Env.getAD_Org_ID(Env.getCtx()), null).getName().toUpperCase(), 
			new BigInteger(bankAccountOwn.getAccountNo()),
			new BigInteger(bankAccountOwn.getC_Bank().getRoutingNo()),
			invoice.getC_BPartner().getName().toUpperCase(), 
			new BigInteger(bpBankAccount.getAccountNo()), 
			new BigInteger(bpBankAccount.getRoutingNo())
		));

		if (success) {
			X_C_DirectDebitLine ddl = new X_C_DirectDebitLine(Env.getCtx(), 0, trxName);
			ddl.setAD_Org_ID(Env.getAD_Org_ID(ctx));
			ddl.setC_Invoice_ID(invoice.get_ID());
			ddl.setC_DirectDebit_ID(directDebit.get_ID());
			if (success) {
				success = ddl.save(trxName);
			}
		}

		if (success) {
			return transactionList;
		} else {
			return null;
		}
	}

	@Override
	protected String doIt() throws Exception {

		log.fine("Start DirectDebitExportProcess");

		trx = Trx.get(Trx.createTrxName("DTACreateProcess_"), true);
		trx.start();
		trxName = trx.getTrxName();
		log.fine("New Transaction started: " + trxName);

		List<Transaction> transactionList = getTransactions();

		if (transactionList == null) {
			trx.rollback();
			trx.close();
			return "Es gab einen Fehler beim zusammenstellen der Daten";
		} else {
			DtaHelper.createFile(getTransactions(), directDebit, true, trxName);
			if (directDebit == null) {
				trx.rollback();
				trx.close();
				return "Es gab einen Fehler bei der Erstellung der Datei";
			} else {
				trx.commit();
				trx.close();
				return "Datei erstellt, Datens�tze erzeugt";
			}
		}
	}

	@Override
	protected void prepare() {
		final ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++) {

			final String name = para[i].getParameterName();

			if (name.equals("C_Invoice_ID")) {
				if (para[i].getParameter() instanceof Integer)
					c_invoice_id = ((Integer) para[i].getParameter()).intValue();
				else if (para[i].getParameter() instanceof BigDecimal)
					c_invoice_id = ((BigDecimal) para[i].getParameter()).intValue(); 
				else {
					log.log(Level.SEVERE, "Unknown type: " + name);
				}
			} else if (name.equals("C_BP_BankAccount_Own_ID")) {
				if (para[i].getParameter() instanceof Integer)
					c_bp_bankaccount_own_id = ((Integer) para[i].getParameter()).intValue();
				else if (para[i].getParameter() instanceof BigDecimal)
					c_bp_bankaccount_own_id = ((BigDecimal) para[i].getParameter()).intValue(); 
				else {
					log.log(Level.SEVERE, "Unknown type: " + name);
				}
			} else if (name.equals("C_BP_BankAccount_ID")) {
				if (para[i].getParameter() instanceof Integer)
					c_bp_bankaccount_to_id = ((Integer) para[i].getParameter()).intValue();
				else if (para[i].getParameter() instanceof BigDecimal)
					c_bp_bankaccount_to_id = ((BigDecimal) para[i].getParameter()).intValue(); 
				else {
					log.log(Level.SEVERE, "Unknown type: " + name);
				}
			}else {
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
		}
		ctx = Env.getCtx();
	}
}