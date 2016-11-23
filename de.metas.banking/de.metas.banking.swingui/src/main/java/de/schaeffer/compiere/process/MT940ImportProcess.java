package de.schaeffer.compiere.process;

/*
 * #%L
 * de.metas.banking.swingui
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


import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.compiere.model.I_C_Invoice;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;

import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Order;
import de.metas.banking.misc.ImportBankstatementCtrl;
import de.schaeffer.compiere.mt940.Bankstatement;
import de.schaeffer.compiere.mt940.Parser;

public class MT940ImportProcess extends JavaProcess {

	private static Logger log = LogManager.getLogger(MT940ImportProcess.class);

	@Override
	protected String doIt() throws Exception {

		log.debug("Start MT940ImportProcess");

		FileDialog fc = new FileDialog(new Frame(), "�ffnen");
		// default path for the statements
		fc.setDirectory("C:\\");
		fc.setVisible(true);

		log.info("Open Bankstatement: " + fc.getDirectory() + fc.getFile());

		String buf = "";

		if (fc.getFile() != null) {
			log.debug("returnVal == JFileChooser.APPROVE_OPTION");
			String filePath = fc.getDirectory();
			String fileName = fc.getFile();
			log.debug("Filestuff loaded");
			try {

				FileInputStream fis = new FileInputStream(filePath + fileName);
				InputStreamReader isr = new InputStreamReader(fis,
						"windows-1252");
				BufferedReader input = new BufferedReader(isr);

				while (input.ready()) {
					buf += input.readLine();
				}
				input.close();
				isr.close();
				fis.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			log.debug("File loaded");
		}

		final Bankstatement statement = Parser.parseMT940String(buf);
		log.debug("file parsed");

		final String whereClauseInvoice = Env.getUserRolePermissions().getOrgWhere(false)
				+ " AND ( paymentrule not in ('B','K','') AND ispaid = 'N' )";

		final List<MInvoice> invoiceList = new Query(getCtx(),
				I_C_Invoice.Table_Name, whereClauseInvoice, get_TrxName())
				.setOnlyActiveRecords(true).setClient_ID().list();

		final String whereClauseOrder = Env.getUserRolePermissions().getOrgWhere(false)
				+ " AND ( paymentrule not in ('B','K','') AND DocStatus='WP' )";

		final List<MOrder> orderList = new Query(getCtx(),
				I_C_Order.Table_Name, whereClauseOrder, get_TrxName())
				.setOnlyActiveRecords(true).setClient_ID().list();

		new ImportBankstatementCtrl(statement, invoiceList, orderList);
		return null;

	}

	@Override
	protected void prepare() {
		// TODO add a process parameter for the input file (with reference type
		// "file"), instead displaying the dialog in doIt
		// nothing to do
	}
}
