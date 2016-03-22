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


import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MBPBankAccount;
import org.compiere.model.MDirectDebit;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrg;
import org.compiere.model.MSysConfig;
import org.compiere.model.X_C_DirectDebitLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.jdtaus.banking.Textschluessel;
import org.jdtaus.banking.TextschluesselVerzeichnis;
import org.jdtaus.banking.dtaus.Transaction;
import org.jdtaus.core.container.ContainerFactory;

import de.schaeffer.compiere.constants.Constants;
import de.schaeffer.compiere.tools.DtaHelper;


public class DirectDebitExport extends SvrProcess {

	private Timestamp dateFrom = null;
	private Timestamp dateTo = null;
	private String trxName = null;
	private Trx trx;
	private boolean success = true;
	private MDirectDebit directDebit;
	private Properties ctx = null;
	
	private String kto = null;
	private String blz = null;
	
	private List<Transaction> getTransactions() throws ParseException {
		
		kto = MSysConfig.getValue(Constants.DIRECT_DEBIT_ACCOUNT_NO, null, Env.getAD_Client_ID(ctx));
		blz = MSysConfig.getValue(Constants.DIRECT_DEBIT_ROUTING_NO, null, Env.getAD_Client_ID(ctx));
		
		List<Transaction> transactionList = new ArrayList<Transaction>();
		
        final TextschluesselVerzeichnis textschluesselVerzeichnis =
            (TextschluesselVerzeichnis) ContainerFactory.getContainer().
            getObject( TextschluesselVerzeichnis.class.getName() );
		
        final Textschluessel textschluessel =
            textschluesselVerzeichnis.getTextschluessel( 5, 0, new java.util.Date() );
		
		StringBuffer sql = new StringBuffer();

		log.debug("Load Invoice data from: " + dateFrom + " till: "+dateTo);
				
		sql = sql.append("SELECT b.c_bp_bankaccount_id, i.c_invoice_id " +
				"FROM c_invoice i " + 
				"INNER JOIN C_Order o ON (i.C_Order_ID = o.C_Order_ID) " +
				"INNER JOIN C_bp_bankaccount b ON (o.c_bp_bankaccount_id = b.c_bp_bankaccount_id) " + 
				"INNER JOIN C_bank ba ON (ba.c_bank_id = b.c_bank_id) " +
				"WHERE i.paymentrule = 'D' AND i.ispaid = 'N' AND isSoTrx = 'Y' ");
		if (dateFrom != null) {
			sql = sql.append("AND i.created >= ? ");
		}
		if (dateTo != null) {
			sql = sql.append("AND i.created <= ? ");
		}
		log.debug(sql.toString());
		
		PreparedStatement pstmt = null;
		try {

			pstmt = DB.prepareStatement(sql.toString(), null);
			int i = 1;
			if (dateFrom != null) {
				pstmt.setTimestamp(i++, dateFrom);
			}
			if (dateTo != null) {
				pstmt.setTimestamp(i++, dateTo);
			}

			ResultSet rs = pstmt.executeQuery();
			
	        directDebit = new MDirectDebit(Env.getCtx(), 0, trxName);
	        directDebit.setAD_Org_ID(Env.getAD_Org_ID(ctx));
	        directDebit.setIsRemittance(false);
	        
	        success = directDebit.save(trxName);
	        
	        log.debug("MDirectDebit created (ID: " + directDebit.get_ID() + ")");
	        
			while (rs.next() && success) {
				
				MInvoice invoice = new MInvoice(ctx, rs.getInt("c_invoice_id"), null);
				MBPBankAccount bpBankAccount = new MBPBankAccount(ctx, rs.getInt("C_bp_bankaccount_id"), null);
				
				transactionList.add(DtaHelper.buildTransaction(
						textschluessel,
						"VZ1", 
						"RE NR" + invoice.getDocumentNo(),
						invoice.getC_Currency().getISO_Code(),
						invoice.getOpenAmt(),
						new MOrg (Env.getCtx(), Env.getAD_Org_ID(Env.getCtx()), null).getName().toUpperCase(),
						new BigInteger(kto),
						new BigInteger(blz),
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
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (SQLException e) {
			success = false;
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pstmt = null;
		}
		if (success) {
			return transactionList;
		} else {
			return null;
		}
	}
	
	@Override
	protected String doIt() throws Exception {
    
		log.debug("Start DirectDebitExportProcess");
		
    	trx = Trx.get(Trx.createTrxName("DTACreateProcess_"), true); 
    	trx.start();
    	trxName = trx.getTrxName();
    	log.debug("New Transaction started: " + trxName);
    	
    	List<Transaction> transactionList = getTransactions();
    	
    	if (transactionList == null) {
    		trx.rollback();
			trx.close();
			return "Es gab einen Fehler beim zusammenstellen der Daten";
    	} else {    	
    		DtaHelper.createFile(getTransactions(), directDebit, false, trxName);
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

			if (name.equals("DateFrom")) {
				dateFrom = (Timestamp) para[i].getParameter();
			} else if (name.equals("DateTo")) {
				dateTo = (Timestamp) para[i].getParameter();
			} else {
				log.error("Unknown Parameter: " + name);
			}
		}
		ctx = Env.getCtx();
	}
}
