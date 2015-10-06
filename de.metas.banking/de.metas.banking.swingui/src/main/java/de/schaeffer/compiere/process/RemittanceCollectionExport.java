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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.compiere.apps.AEnv;
import org.compiere.model.MInvoice;
import org.compiere.model.X_C_DocType;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.Env;

import de.metas.banking.service.IBankingBL;
import de.schaeffer.compiere.forms.RemittanceExportWindow;

public class RemittanceCollectionExport extends SvrProcess {

	

	@Override
	protected String doIt() throws Exception {
		
		Properties ctx = Env.getCtx();
		
		final IBankingBL bankingBL = Services.get(IBankingBL.class);
		bankingBL.createInvoicesForRecurrentPayments(get_TrxName());
		
		int[] vendorInvIds = MInvoice.getAllIDs(MInvoice.Table_Name, 
				MInvoice.COLUMNNAME_IsPaid + " = 'N' AND " +
				//metas: removed use of C_Doctype_ID because it can be different any any system
					MInvoice.COLUMNNAME_C_DocTypeTarget_ID + " in (select C_DocType_ID from C_DocType where DocBaseType='"
				+ X_C_DocType.DOCBASETYPE_APInvoice	+ "') AND "	+
				// metas: end
				MInvoice.COLUMNNAME_AD_Org_ID + " = " + Env.getAD_Org_ID(ctx) + " AND " +
				MInvoice.COLUMNNAME_AD_Client_ID + " = " + Env.getAD_Client_ID(ctx) + " AND " +
				MInvoice.COLUMNNAME_C_Invoice_ID + " NOT IN (SELECT C_Invoice_ID FROM c_directdebitline " +
				" WHERE ad_client_id = " + Env.getAD_Client_ID(ctx) + ")",
				get_TrxName());

		int[] recurrentIds = MInvoice.getAllIDs(MInvoice.Table_Name, 
				MInvoice.COLUMNNAME_IsPaid + " = 'N' AND " +
				//metas: removed use of C_Doctype_ID because it can be different any any system
								MInvoice.COLUMNNAME_C_DocTypeTarget_ID + " in (select C_DocType_ID from C_DocType where DocBaseType='"
								+ Constants.DOCBASETYPE_AVIinvoice	+ "') AND "	+
				// metas: end
				MInvoice.COLUMNNAME_AD_Org_ID + " = " + Env.getAD_Org_ID(ctx) + " AND " +
				MInvoice.COLUMNNAME_AD_Client_ID + " = " + Env.getAD_Client_ID(ctx) + " AND " +
				MInvoice.COLUMNNAME_C_Invoice_ID + " NOT IN (SELECT C_Invoice_ID FROM c_directdebitline " +
						" WHERE ad_client_id = " + Env.getAD_Client_ID(ctx) + ")",
				get_TrxName());
				
		List<MInvoice> vendorList = new ArrayList<MInvoice>();
		List<MInvoice> recurrentList = new ArrayList<MInvoice>();
		
		for (int id : vendorInvIds) {
			vendorList.add(new MInvoice(ctx, id, get_TrxName()));
		}
		
		for (int id : recurrentIds) {
			recurrentList.add(new MInvoice(ctx, id, get_TrxName()));
		}
		
		RemittanceExportWindow window = new RemittanceExportWindow(vendorList, recurrentList, get_TrxName(), Env.getWindow(0));

		AEnv.positionCenterScreen(window);
		window.setVisible(true);

		if (window.getErrorMessage() != null) {
			throw new AdempiereSystemError(window.getErrorMessage());
		}

		log.warning("Exit");
		
		return null;
	}

	@Override
	protected void prepare() {
		
		
		
	}
	
}
