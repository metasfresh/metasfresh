package de.metas.banking.payment.impexp;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_I_DiscountSchema;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.logging.LogManager;
import de.metas.pricing.impexp.DiscountSchemaImportProcess;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * A helper class for {@link DiscountSchemaImportProcess} that performs the "dirty" but efficient SQL updates on the {@link I_I_DiscountSchema} table.
 * Those updates complements the data from existing metasfresh records and flag those import records that can't yet be imported.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class CPaymentImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(CPaymentImportTableSqlUpdater.class);

	public void updatePaymentImportTable(@NonNull final ImportRecordsSelection selection)
	{
		dbUpdateBPartners(selection);
		dbUpdateInvoices(selection);
		dbUpdateIsReceipt(selection);
		dbUpdateErrorMessages(selection);
	}

	private void dbUpdateBPartners(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE I_Datev_Payment i ")
				.append("SET C_BPartner_ID=(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp ")
				.append(" WHERE (i.BPartnerValue=bp.Debtorid OR i.BPartnerValue=bp.CreditorId) AND i.AD_Client_ID=bp.AD_Client_ID AND i.AD_Org_ID=bp.AD_Org_ID ) ")
				.append("WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NOT NULL ")
				.append("AND I_IsImported<>'Y'  ")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndSaveErrorOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateInvoices(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE I_Datev_Payment i ")
				.append("SET C_Invoice_ID = (SELECT C_Invoice_ID FROM C_Invoice inv ")
				.append("WHERE i.InvoiceDocumentNo = inv.DocumentNo ")
				.append("AND round((i.PayAmt+i.DiscountAmt),2) = round(inv.GrandTotal,2) AND i.DateTrx = inv.DateInvoiced ")
				.append("AND i.AD_Client_ID=inv.AD_Client_ID AND i.AD_Org_ID=inv.AD_Org_ID ) ")
				.append("WHERE C_Invoice_ID IS NULL AND InvoiceDocumentNo IS NOT NULL ")
				.append("AND I_IsImported<>'Y'  ")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndSaveErrorOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateIsReceipt(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE I_Datev_Payment i ")
				.append("SET IsReceipt = (CASE WHEN TransactionCode='H' THEN 'N' ELSE 'Y' END) ")
				.append("WHERE I_IsImported<>'Y'  ")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndSaveErrorOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}


	private void dbUpdateErrorMessages(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE I_Datev_Payment ")
				.append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No BPartner, ' ")
				.append("WHERE C_BPartner_ID IS NULL ")
				.append("AND I_IsImported<>'Y' ")
				.append(selection.toSqlWhereClause());
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No BPartner = {}", no);
		}

		sql = new StringBuilder("UPDATE I_Datev_Payment ")
				.append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Invoice, ' ")
				.append("WHERE C_Invoice_ID IS NULL ")
				.append("AND I_IsImported<>'Y' ")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No Invoice = {}", no);
		}

	}
}
