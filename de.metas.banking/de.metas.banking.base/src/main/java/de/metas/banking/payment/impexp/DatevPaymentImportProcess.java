package de.metas.banking.payment.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_C_DocType;

import de.metas.banking.model.I_I_Datev_Payment;
import de.metas.banking.model.X_I_Datev_Payment;
import de.metas.payment.api.DefaultPaymentBuilder.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Import {@link I_I_Datev_Payment} to {@link I_C_Payment}.
 *
 */
public class DatevPaymentImportProcess extends AbstractImportProcess<I_I_Datev_Payment>
{
	@Override
	public Class<I_I_Datev_Payment> getImportModelClass()
	{
		return I_I_Datev_Payment.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Datev_Payment.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_Payment.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		CPaymentImportTableSqlUpdater.updatePaymentImportTable(getWhereClause());
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Datev_Payment.COLUMNNAME_C_BPartner_ID;
	}

	@Override
	protected I_I_Datev_Payment retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_Datev_Payment(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(@NonNull final IMutable<Object> state,
			@NonNull final I_I_Datev_Payment importRecord,
			final boolean isInsertOnly) throws Exception
	{
		return importDatevPayment(importRecord, isInsertOnly);
	}

	private ImportRecordResult importDatevPayment(@NonNull final I_I_Datev_Payment importRecord, final boolean isInsertOnly)
	{
		final ImportRecordResult schemaImportResult;

		final boolean paymentExists = importRecord.getC_Payment_ID() > 0;

		if (paymentExists && isInsertOnly)
		{
			// do not update
			return ImportRecordResult.Nothing;
		}

		final I_C_Payment payment;
		if (!paymentExists)
		{
			payment = createNewPayment(importRecord);
			schemaImportResult = ImportRecordResult.Inserted;
		}
		else
		{
			payment = importRecord.getC_Payment();
			schemaImportResult = ImportRecordResult.Updated;
		}

		ModelValidationEngine.get().fireImportValidate(this, importRecord, payment,
				IImportInterceptor.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(payment);

		importRecord.setC_Payment_ID(payment.getC_Payment_ID());
		InterfaceWrapperHelper.save(importRecord);

		return schemaImportResult;
	}

	private I_C_Payment createNewPayment(@NonNull final I_I_Datev_Payment importRecord)
	{
		return Services.get(IPaymentBL.class).newBuilder(importRecord).setAD_Org_ID(importRecord.getAD_Org_ID())
				.setC_BPartner_ID(importRecord.getC_BPartner_ID())
				.setDocbaseType(importRecord.isReceipt() ? X_C_DocType.DOCBASETYPE_ARReceipt
						: X_C_DocType.DOCBASETYPE_APPayment)
				.setPayAmt(importRecord.getPayAmt())
				.setDiscountAmt(importRecord.getDiscountAmt())
				.setTenderType(TenderType.ACH)
				.setDateAcct(importRecord.getDateTrx())
				.setDateTrx(importRecord.getDateTrx())
				.setDescription("Import for debitorId/creditorId" + importRecord.getBPartnerValue())
				.setC_Invoice(importRecord.getC_Invoice())
				.createAndProcess();
	}

	@Override
	protected void markImported(@NonNull final I_I_Datev_Payment importRecord)
	{
		importRecord.setI_IsImported(X_I_Datev_Payment.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}
}
