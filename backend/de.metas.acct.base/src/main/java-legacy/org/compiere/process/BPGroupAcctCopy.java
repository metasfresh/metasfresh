/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.process;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_BP_Customer_Acct;
import org.compiere.model.I_C_BP_Vendor_Acct;
import org.compiere.util.DB;

import de.metas.acct.api.AcctSchemaId;
import de.metas.bpartner.BPGroupId;
import de.metas.cache.CacheMgt;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

/**
 * Copy BP Group default Accounts
 * 
 * @author Jorg Janke
 * @version $Id: BPGroupAcctCopy.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class BPGroupAcctCopy extends JavaProcess
{
	@Param(parameterName = "C_BP_Group_ID", mandatory = true)
	private BPGroupId fromBPGroupId;

	@Param(parameterName = "C_AcctSchema_ID", mandatory = true)
	private AcctSchemaId fromAcctSchemaId;

	@Param(parameterName = "C_BP_Group_To_ID", mandatory = true)
	private BPGroupId toBPGroupId;

	@Param(parameterName = "C_AcctSchema_To_ID", mandatory = true)
	private AcctSchemaId toAcctSchemaId;

	@Override
	protected String doIt()
	{
		int updatedTotal = 0;
		int createdTotal = 0;

		//
		// Update existing Customers
		{
			final String sql = DB.convertSqlToNative("UPDATE C_BP_Customer_Acct ca "
					+ "SET (C_Receivable_Acct,C_Receivable_Services_Acct,C_PrePayment_Acct)="
					+ " (SELECT C_Receivable_Acct,C_Receivable_Services_Acct,C_PrePayment_Acct "
					+ " FROM C_BP_Group_Acct"
					+ " WHERE C_BP_Group_ID=" + fromBPGroupId.getRepoId()
					+ " AND C_AcctSchema_ID=" + fromAcctSchemaId.getRepoId()
					+ "), Updated=now(), UpdatedBy=0 "
					+ "WHERE ca.C_AcctSchema_ID=" + toAcctSchemaId.getRepoId()
					+ " AND EXISTS (SELECT 1 FROM C_BPartner p WHERE p.C_BPartner_ID=ca.C_BPartner_ID AND p.C_BP_Group_ID=" + toBPGroupId.getRepoId() + ")");
			final int updated = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
			addLog(0, null, BigDecimal.valueOf(updated), "@Updated@ @C_BPartner_ID@ @IsCustomer@");
			updatedTotal += updated;
		}

		//
		// Insert new Customer
		{
			final String sql = "INSERT INTO C_BP_Customer_Acct "
					+ "(C_BPartner_ID,"
					+ " C_AcctSchema_ID,"
					+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
					+ " C_Receivable_Acct, C_Receivable_Services_Acct, C_PrePayment_Acct) "
					//
					+ " SELECT p.C_BPartner_ID, "
					+ toAcctSchemaId.getRepoId() + " AS C_AcctSchema_ID,"
					+ " p.AD_Client_ID, p.AD_Org_ID, 'Y', now(), 0, now(), 0,"
					+ " acct.C_Receivable_Acct, acct.C_Receivable_Services_Acct, acct.C_PrePayment_Acct "
					+ " FROM C_BPartner p"
					+ " INNER JOIN C_BP_Group_Acct acct ON (acct.C_BP_Group_ID=" + fromBPGroupId.getRepoId() + " AND acct.C_AcctSchema_ID=" + fromAcctSchemaId.getRepoId() + ")"
					+ " WHERE 1=1"
					+ " AND p.C_BP_Group_ID=" + toBPGroupId.getRepoId()
					+ " AND NOT EXISTS (SELECT 1 FROM C_BP_Customer_Acct ca WHERE ca.C_BPartner_ID=p.C_BPartner_ID AND ca.C_AcctSchema_ID=" + toAcctSchemaId.getRepoId() + ")";
			final int created = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
			addLog(0, null, BigDecimal.valueOf(created), "@Created@ @C_BPartner_ID@ @IsCustomer@");
			createdTotal += created;
		}

		//
		// Update existing Vendors
		{
			final String sql = DB.convertSqlToNative("UPDATE C_BP_Vendor_Acct va "
					+ "SET (V_Liability_Acct,V_Liability_Services_Acct,V_PrePayment_Acct)="
					+ " (SELECT V_Liability_Acct,V_Liability_Services_Acct,V_PrePayment_Acct "
					+ " FROM C_BP_Group_Acct"
					+ " WHERE C_BP_Group_ID=" + fromBPGroupId.getRepoId()
					+ " AND C_AcctSchema_ID=" + fromAcctSchemaId.getRepoId()
					+ "), Updated=now(), UpdatedBy=0 "
					+ " WHERE va.C_AcctSchema_ID=" + toAcctSchemaId.getRepoId()
					+ " AND EXISTS (SELECT 1 FROM C_BPartner p WHERE p.C_BPartner_ID=va.C_BPartner_ID AND p.C_BP_Group_ID=" + toBPGroupId.getRepoId() + ")");
			final int updated = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
			addLog(0, null, BigDecimal.valueOf(updated), "@Updated@ @C_BPartner_ID@ @IsVendor@");
			updatedTotal += updated;
		}

		//
		// Insert new Vendors
		{
			final String sql = "INSERT INTO C_BP_Vendor_Acct "
					+ "(C_BPartner_ID,"
					+ " C_AcctSchema_ID,"
					+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
					+ " V_Liability_Acct, V_Liability_Services_Acct, V_PrePayment_Acct) "
					//
					+ " SELECT p.C_BPartner_ID,"
					+ toAcctSchemaId.getRepoId() + " AS C_AcctSchema_ID,"
					+ " p.AD_Client_ID, p.AD_Org_ID, 'Y', now(), 0, now(), 0,"
					+ " acct.V_Liability_Acct, acct.V_Liability_Services_Acct, acct.V_PrePayment_Acct "
					+ " FROM C_BPartner p"
					+ " INNER JOIN C_BP_Group_Acct acct ON (acct.C_BP_Group_ID=" + fromBPGroupId.getRepoId() + " AND acct.C_AcctSchema_ID=" + fromAcctSchemaId.getRepoId() + ")"
					+ " WHERE 1=1"
					+ " AND p.C_BP_Group_ID=" + toBPGroupId.getRepoId()
					+ " AND NOT EXISTS (SELECT 1 FROM C_BP_Vendor_Acct va WHERE va.C_BPartner_ID=p.C_BPartner_ID AND va.C_AcctSchema_ID=" + toAcctSchemaId.getRepoId() + ")";
			final int created = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
			addLog(0, null, BigDecimal.valueOf(created), "@Created@ @C_BPartner_ID@ @IsVendor@");
			createdTotal += created;
		}

		return "@Created@=" + createdTotal + ", @Updated@=" + updatedTotal;
	}	// doIt

	@Override
	protected void postProcess(final boolean success)
	{
		if (success)
		{
			final CacheMgt cacheMgt = CacheMgt.get();
			cacheMgt.reset(I_C_BP_Customer_Acct.Table_Name);
			cacheMgt.reset(I_C_BP_Vendor_Acct.Table_Name);
		}
	}

}	// BPGroupAcctCopy
