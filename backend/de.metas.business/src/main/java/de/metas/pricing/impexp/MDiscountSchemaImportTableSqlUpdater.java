package de.metas.pricing.impexp;

import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_ErrorMsg;
import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_IsImported;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_I_DiscountSchema;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.logging.LogManager;
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
public class MDiscountSchemaImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(MDiscountSchemaImportTableSqlUpdater.class);

	public void updateDiscountSchemaImportTable(@NonNull final ImportRecordsSelection selection)
	{
		dbUpdateBPartners(selection);
		dbUpdateDiscountSchema(selection);
		dbUpdateProducts(selection);
		dbUpdateC_PaymentTerms(selection);
		dbUpdateM_PricingSystems(selection);
		dbUpdateDiscountSchemaBreaks(selection);

		dbUpdateErrorMessages(selection);
	}

	private void dbUpdateBPartners(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE I_DiscountSchema i ")
				.append("SET C_BPartner_ID=(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp ")
				.append(" WHERE i.BPartner_Value=bp.Value AND i.AD_Client_ID=bp.AD_Client_ID) ")
				.append("WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL ")
				.append("AND I_IsImported<>'Y'  ")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateDiscountSchema(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE I_DiscountSchema i ")
				.append("SET M_DiscountSchema_ID=(SELECT M_DiscountSchema_ID FROM C_BPartner bp ")
				.append(" WHERE i.C_BPartner_ID=bp.C_BPartner_ID AND bp.M_DiscountSchema_ID > 0 ")
				.append(" AND i.AD_Client_ID=bp.AD_Client_ID) ")
				.append("WHERE M_DiscountSchema_ID IS NULL AND C_BPartner_ID IS NOT NULL ")
				.append("AND I_IsImported<>'Y'  ")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateProducts(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE I_DiscountSchema i ")
				.append("SET M_Product_ID=(SELECT MAX(M_Product_ID) FROM M_Product p ")
				.append(" WHERE i.ProductValue=p.Value AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE M_Product_ID IS NULL AND ProductValue IS NOT NULL ")
				.append("AND I_IsImported<>'Y'  ")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateC_PaymentTerms(final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_DiscountSchema i ")
				.append("SET C_PaymentTerm_ID=(SELECT C_PaymentTerm_ID FROM C_PaymentTerm pt ")
				.append("WHERE i.PaymentTermValue=pt.Value AND pt.AD_Client_ID IN (0, i.AD_Client_ID)) ")
				.append("WHERE C_PaymentTerm_ID IS NULL AND PaymentTermValue IS NOT NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y' ")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set C_PaymentTerm={}", no);
	}

	private void dbUpdateM_PricingSystems(final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_DiscountSchema i ")
				.append("SET Base_PricingSystem_ID=(SELECT M_PricingSystem_ID FROM M_PricingSystem p ")
				.append("WHERE i.Base_PricingSystem_Value=p.Value AND p.AD_Client_ID IN (0, i.AD_Client_ID)) ")
				.append("WHERE Base_PricingSystem_ID IS NULL AND Base_PricingSystem_Value IS NOT NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y' ")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set C_PaymentTerm={}", no);
	}

	private void dbUpdateDiscountSchemaBreaks(final ImportRecordsSelection selection)
	{
		StringBuilder sql = new StringBuilder("UPDATE I_DiscountSchema i SET M_DiscountSchemaBreak_ID = dsb.M_DiscountSchemaBreak_ID ")
				.append("FROM M_DiscountSchemaBreak dsb ")
				.append("WHERE  dsb.M_DiscountSchema_ID = i.M_DiscountSchema_ID ")
				.append("AND dsb.M_Product_ID = i.M_Product_ID ")
				.append("AND dsb.c_paymentterm_id = i.c_paymentterm_id ")
				.append("AND coalesce(dsb.breakdiscount, 0) = coalesce(i.breakdiscount,0) ")
				.append("AND coalesce(dsb.breakvalue, 0) = coalesce(i.breakvalue,0) ")
				.append("AND dsb.PriceBase = i.PriceBase ")
				.append("AND dsb.Base_PricingSystem_ID = i.Base_PricingSystem_ID ")
				.append("AND coalesce(dsb.PriceStdFixed, 0) = coalesce(i.PriceStdFixed, 0) ")
				.append("AND coalesce(dsb.PricingSystemSurchargeAmt, 0) = coalesce(i.PricingSystemSurchargeAmt,0) ")
				.append("AND coalesce(dsb.C_Currency_ID,0) = coalesce(i.C_Currency_ID,0) ")
				.append("AND i.M_DiscountSchemaBreak_ID IS NULL ")
				.append("AND I_IsImported <> 'Y' ")
				.append("AND dsb.IsActive = 'Y' ")
				.append(selection.toSqlWhereClause("i"));

		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateErrorMessages(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE I_DiscountSchema ")
				.append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No BPartner, ' ")
				.append("WHERE C_BPartner_ID IS NULL ")
				.append("AND I_IsImported<>'Y' ")
				.append(selection.toSqlWhereClause());
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No BPartner = {}", no);
		}

		sql = new StringBuilder("UPDATE I_DiscountSchema ")
				.append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Product, ' ")
				.append("WHERE M_Product_ID IS NULL ")
				.append("AND I_IsImported<>'Y' ")
				.append(selection.toSqlWhereClause());
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No Product = {}", no);
		}

		//
		sql = new StringBuilder("UPDATE I_DiscountSchema i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid C_PaymentTerm, ' "
				+ "WHERE C_PaymentTerm_ID IS NULL AND PaymentTermValue IS NOT NULL AND PaymentTermValue <> '0' "
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("Invalid C_PaymentTerm={}", no);
		}

		sql = new StringBuilder("UPDATE I_DiscountSchema i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Base_PricingSystem, ' "
				+ "WHERE Base_PricingSystem_ID IS NULL AND Base_PricingSystem_Value IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("Invalid Base_PricingSystem_ID={}", no);
		}

	}
}
