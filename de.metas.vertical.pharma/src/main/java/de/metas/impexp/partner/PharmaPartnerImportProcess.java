package de.metas.impexp.partner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.product.MProductImportTableSqlUpdater;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_BPartner;

import de.metas.vertical.pharma.model.I_I_Pharma_BPartner;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.X_I_Pharma_BPartner;
import de.metas.vertical.pharma.model.X_I_Pharma_Product;
import lombok.NonNull;

public class PharmaPartnerImportProcess extends AbstractImportProcess<I_I_Pharma_BPartner>
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
		MProductImportTableSqlUpdater.builder()
				.whereClause(whereClause)
				.ctx(getCtx())
				.tableName(getImportTableName())
				.valueName(I_I_Pharma_Product.COLUMNNAME_A00PZN)
				.updateIPharmaProduct();
	}

	@Override
	protected ImportRecordResult importRecord(final IMutable<Object> state,
			final I_I_Pharma_BPartner importRecord,
			final boolean isInsertOnly) throws Exception
	{
		final I_C_BPartner existentBPartner = null; //productDAO.retrieveProductByValue(importRecord.getb00adrnr());

		final String operationCode = importRecord.getb00ssatz();
		if (DEACTIVATE_OPERATION_CODE.equals(operationCode) && existentBPartner != null)
		{
			deactivatePartner(existentBPartner);
			return ImportRecordResult.Updated;
		}
		else if (!DEACTIVATE_OPERATION_CODE.equals(operationCode))
		{
			final I_C_BPartner bpartner;
			final boolean newBPartner = existentBPartner == null || importRecord.getC_BPartner_ID() <= 0;

			if (!newBPartner && isInsertOnly)
			{
				// #4994 do not update entry
				return ImportRecordResult.Nothing;
			}
			if (newBPartner)
			{
				bpartner = createBPartner(importRecord);
			}
			else
			{
				bpartner = updateBPartner(importRecord, existentBPartner);
			}

			importRecord.setC_BPartner_ID(bpartner.getC_BPartner_ID());

			return newBPartner ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
		}

		return ImportRecordResult.Nothing;
	}

	private I_C_BPartner updateBPartner(I_I_Pharma_BPartner importRecord, I_C_BPartner existentBPartner)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private I_C_BPartner createBPartner(I_I_Pharma_BPartner importRecord)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private void deactivatePartner(@NonNull final I_C_BPartner partner)
	{
		partner.setIsActive(false);
		InterfaceWrapperHelper.save(partner);
	}

	@Override
	protected void markImported(final I_I_Pharma_BPartner importRecord)
	{
		importRecord.setI_IsImported(X_I_Pharma_Product.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}
}
