package de.metas.impexp.bpartner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;

import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.vertical.pharma.model.I_C_BPartner;
import de.metas.vertical.pharma.model.I_I_Pharma_BPartner;
import de.metas.vertical.pharma.model.X_I_Pharma_BPartner;
import de.metas.vertical.pharma.model.X_I_Pharma_Product;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class IFABPartnerImportProcess extends SimpleImportProcessTemplate<I_I_Pharma_BPartner>
{
	private final String DEACTIVATE_OPERATION_CODE = "2";

	@Override
	public Class<I_I_Pharma_BPartner> getImportModelClass()
	{
		return I_I_Pharma_BPartner.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Pharma_BPartner.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return org.compiere.model.I_C_BPartner.Table_Name;
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Pharma_BPartner.COLUMNNAME_b00gdat;
	}

	@Override
	protected I_I_Pharma_BPartner retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_Pharma_BPartner(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final String whereClause = getWhereClause();
		IFABPartnerImportTableSqlUpdater.updateBPartnerImportTable(whereClause);
	}

	@Override
	protected ImportRecordResult importRecord(@NonNull final IMutable<Object> state,@NonNull final I_I_Pharma_BPartner importRecord, final boolean isInsertOnly)
	{
		final I_C_BPartner existentBPartner = IFABPartnerImportHelper.fetchManufacturer(importRecord.getb00adrnr());

		final String operationCode = importRecord.getb00ssatz();
		if (DEACTIVATE_OPERATION_CODE.equals(operationCode) && existentBPartner != null)
		{
			deactivatePartner(existentBPartner);
			return ImportRecordResult.Updated;
		}
		else if (!DEACTIVATE_OPERATION_CODE.equals(operationCode))
		{
			//
			// Get previous values
			IFABPartnerContext context = (IFABPartnerContext)state.getValue();
			if (context == null)
			{
				context = new IFABPartnerContext();
				state.setValue(context);
			}
			final I_I_Pharma_BPartner previousImportRecord = context.getPreviousImportRecord();
			final int previousBPartnerId = context.getPreviousC_BPartner_ID();
			final String previousBPValue = context.getPreviousBPValue();
			context.setPreviousImportRecord(importRecord); // set it early in case this method fails

			final ImportRecordResult bpartnerImportResult;

			// create a new BPartner or update the existing one
			final boolean firstImportRecordOrNewBPartner = previousImportRecord == null || !Objects.equals(importRecord.getb00adrnr(), previousBPValue);
			if (firstImportRecordOrNewBPartner)
			{
				// create a new list because we are passing to a new partner
				context.clearPreviousRecordsForSameBP();
				bpartnerImportResult = importOrUpdateBPartner(importRecord, isInsertOnly);
			}

			// Same BPValue like previous line
			else
			{
				if (previousBPartnerId <= 0)
				{
					bpartnerImportResult = importOrUpdateBPartner(importRecord, isInsertOnly);
				}
				else if (importRecord.getC_BPartner_ID() <= 0 || importRecord.getC_BPartner_ID() == previousBPartnerId)
				{
					bpartnerImportResult = doNothingAndUsePreviousPartner(importRecord, previousImportRecord);
				}
				else
				{
					throw new AdempiereException("Same BPValue as previous line but not same BPartner linked");
				}
			}

			IFABPartnerLocationImportHelper.importRecord(importRecord, context.getPreviousImportRecordsForSameBP());

			context.collectImportRecordForSameBP(importRecord);

			return bpartnerImportResult;
		}

		return ImportRecordResult.Nothing;
	}

	private void deactivatePartner(@NonNull final I_C_BPartner partner)
	{
		partner.setIsActive(false);
		InterfaceWrapperHelper.save(partner);
	}

	private ImportRecordResult importOrUpdateBPartner(@NonNull final I_I_Pharma_BPartner importRecord, final boolean isInsertOnly)
	{
		final boolean bpartnerExists = importRecord.getC_BPartner_ID() > 0;

		if (isInsertOnly && bpartnerExists)
		{
			return ImportRecordResult.Nothing;
		}

		IFABPartnerImportHelper.importRecord(importRecord);

		return bpartnerExists ? ImportRecordResult.Updated : ImportRecordResult.Inserted;
	}

	private ImportRecordResult doNothingAndUsePreviousPartner(@NonNull final I_I_Pharma_BPartner importRecord, @NonNull final I_I_Pharma_BPartner previousImportRecord)
	{
		importRecord.setC_BPartner(previousImportRecord.getC_BPartner());
		return ImportRecordResult.Nothing;
	}

	@Override
	protected void markImported(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		importRecord.setI_IsImported(X_I_Pharma_Product.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}
}
