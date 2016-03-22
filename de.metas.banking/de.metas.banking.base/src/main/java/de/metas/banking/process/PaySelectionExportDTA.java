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
package de.metas.banking.process;

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


import java.io.File;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.MAttachment;
import org.compiere.model.MPaySelection;
import org.compiere.model.X_C_PaySelectionCheck;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.jdtaus.banking.dtaus.Transaction;

import de.metas.banking.service.IBankingBL;

/**
 * Create Checks from Payment Selection Line
 * 
 * @author Jorg Janke
 * @version $Id: PaySelectionCreateCheck.java,v 1.2 2006/07/30 00:51:01 jjanke
 *          Exp $
 */
public class PaySelectionExportDTA extends SvrProcess {



	private String fileName;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	protected void prepare() {

		final ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++) {

			final String name = para[i].getParameterName();

			if (name.equals("FileName")) {
				
				fileName = (String) para[i].getParameter();
				
			} else {
				log.error("Unknown Parameter: " + name);
			}
		}
	}

	/**
	 * Perform process.
	 * 
	 * @return Message (clear text)
	 * @throws Exception
	 *             if not successful
	 */
	protected String doIt() throws Exception {

		log.info("C_PaySelection_ID=" + getRecord_ID());

		final MPaySelection psel = new MPaySelection(getCtx(), getRecord_ID(),
				get_TrxName());

		if (!psel.isProcessed())
			// TODO -> AD_Message
			throw new AdempiereException("Vorbereitete Zahlungen fehlen");

		final IBankingBL bankingBL = Services.get(IBankingBL.class);

		final Map<String, List<Transaction>> orders = bankingBL.createOrders(psel);
		final List<Transaction> remittanceOrders = orders.get(X_C_PaySelectionCheck.PAYMENTRULE_DirectDeposit);

		final List<Transaction> debitOrders = orders.get(X_C_PaySelectionCheck.PAYMENTRULE_DirectDebit) ;

		if (!remittanceOrders.isEmpty()) {

			final File outputFile = insertFileANmePrefix(fileName, "r_");
			bankingBL.createDtaFile(remittanceOrders, outputFile);
			attachDta(outputFile);
		}
		if (!debitOrders.isEmpty()) {

			final File outputFile = insertFileANmePrefix(fileName, "d_");
			bankingBL.createDtaFile(debitOrders, outputFile);
			attachDta(outputFile);
		}

		psel
				.set_ValueOfColumn("LastExportBy", getProcessInfo()
						.getAD_User_ID());
		psel.set_ValueOfColumn("LastExport", SystemTime.asTimestamp());
		psel.saveEx();

		return "@Success@";
	} // doIt

	private void attachDta(final File outputFile) {
		
		MAttachment attachment = MAttachment.get(getCtx(),
				MPaySelection.Table_ID, getRecord_ID());
		if (attachment == null) {
			attachment = new MAttachment(getCtx(), MPaySelection.Table_ID,
					getRecord_ID(), get_TrxName());
			attachment.setTitle(outputFile.getName());
			attachment.saveEx();
		}
		attachment.setTitle(outputFile.getName());
		attachment.addEntry(outputFile);
		attachment.saveEx();
	}

	private File insertFileANmePrefix(final String file, final String prefix) {
		final File debitFile = new File(file);
		final String path = debitFile.getParent();
		final String fileName = debitFile.getName();
		final File outputFile = new File(path, prefix + fileName);
		return outputFile;
	}

} // PaySelectionCreateCheck
