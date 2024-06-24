/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.pricing.rules.campaign_price.impexp;

import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_Campaign_Price;
import org.compiere.model.I_I_Campaign_Price;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_Campaign_Price;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.load;

public class CampaignPriceImportProcess extends SimpleImportProcessTemplate<I_I_Campaign_Price>
{
	private static final Logger log = LogManager.getLogger(CampaignPriceImportProcess.class);

	@Override
	public Class<I_I_Campaign_Price> getImportModelClass()
	{
		return I_I_Campaign_Price.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Campaign_Price.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_Campaign_Price.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		CampaignPriceImportTableSqlUpdater.builder()
				.ctx(getCtx())
				.selection(getImportRecordsSelection())
				.updateCampaignPrice();
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Campaign_Price.COLUMNNAME_I_Campaign_Price_ID;
	}

	@Override
	protected I_I_Campaign_Price retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_Campaign_Price(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(final @NonNull IMutable<Object> stateHolder, final @NonNull I_I_Campaign_Price importRecord, final boolean isInsertOnly) throws Exception
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final int I_Campaign_Price_ID = importRecord.getI_Campaign_Price_ID();
		final int C_Campaign_Price_ID = importRecord.getC_Campaign_Price_ID();
		final boolean newCampaignPrice = C_Campaign_Price_ID <= 0;
		log.debug("I_Campaign_Price_ID=" + I_Campaign_Price_ID + ", C_Campaign_Price_ID=" + C_Campaign_Price_ID);

		if (!newCampaignPrice && isInsertOnly)
		{
			return ImportRecordResult.Nothing;
		}

		if (newCampaignPrice)
		{
			final I_C_Campaign_Price campaignPriceRecord = createCampaignPrice(importRecord);
			InterfaceWrapperHelper.save(campaignPriceRecord);
			importRecord.setM_Product_ID(campaignPriceRecord.getM_Product_ID());
			log.trace("Insert C_Campaign_Price ");
		}
		else
		{
			final String sqlt = DB.convertSqlToNative("UPDATE C_Campaign_Price "
					+ "SET (priceStd, validFrom, validTo, InvoicableQtyBasedOn, updatedBy, updated) = "
					+ " (SELECT priceStd, validFrom, validTo, InvoicableQtyBasedOn,UpdatedBy, now() "
					+ " FROM I_Campaign_Price WHERE I_Campaign_Price_ID=" + I_Campaign_Price_ID + ") "
					+ " WHERE C_Campaign_Price_ID=" + C_Campaign_Price_ID);
			PreparedStatement pstmt_updateCampaignPrice = null;
			try
			{
				pstmt_updateCampaignPrice = DB.prepareStatement(sqlt, trxName);
				final int no = pstmt_updateCampaignPrice.executeUpdate();
				log.trace("Update CampaignPrice = " + no);
			}
			catch (final SQLException ex)
			{
				throw new DBException("Update CampaignPrice: " + ex, ex);
			}
			finally
			{
				DB.close(pstmt_updateCampaignPrice);
			}
		}

		final I_C_Campaign_Price productRecord = load(importRecord.getC_Campaign_Price_ID(), I_C_Campaign_Price.class);
		ModelValidationEngine.get().fireImportValidate(this, importRecord, productRecord, IImportInterceptor.TIMING_AFTER_IMPORT);

		return newCampaignPrice ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}

	private I_C_Campaign_Price createCampaignPrice(final I_I_Campaign_Price importRecord)
	{
		final I_C_Campaign_Price campaignPrice = InterfaceWrapperHelper.newInstance(I_C_Campaign_Price.class);
		campaignPrice.setAD_Org_ID(importRecord.getAD_Org_ID());

		campaignPrice.setM_Product_ID(importRecord.getM_Product_ID());
		campaignPrice.setC_BPartner_ID(importRecord.getC_BPartner_ID());
		campaignPrice.setC_BP_Group_ID(importRecord.getC_BP_Group_ID());
		campaignPrice.setM_PricingSystem_ID(importRecord.getM_PricingSystem_ID());
		campaignPrice.setC_Country_ID(importRecord.getC_Country_ID());
		campaignPrice.setC_Currency_ID(importRecord.getC_Currency_ID());
		campaignPrice.setC_UOM_ID(importRecord.getC_UOM_ID());
		campaignPrice.setC_TaxCategory_ID(importRecord.getC_TaxCategory_ID());
		campaignPrice.setPriceStd(importRecord.getPriceStd());
		campaignPrice.setValidFrom(importRecord.getValidFrom());
		campaignPrice.setValidTo(importRecord.getValidTo());
		campaignPrice.setInvoicableQtyBasedOn(importRecord.getInvoicableQtyBasedOn());

		//campaignPrice.setDescription("Created via import");

		return campaignPrice;
	}

	@Override
	protected void markImported(@NonNull final I_I_Campaign_Price importRecord)
	{
		importRecord.setI_IsImported(X_I_Campaign_Price.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}
}
