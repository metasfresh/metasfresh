package de.metas.banking.payment.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.TimeUtil;

import de.metas.banking.model.I_I_Datev_Payment;
import de.metas.banking.model.X_I_Datev_Payment;
import de.metas.bpartner.BPartnerId;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.organization.OrgId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Import {@link I_I_Datev_Payment} to {@link I_C_Payment}.
 *
 */
public class DatevPaymentImportProcess extends SimpleImportProcessTemplate<I_I_Datev_Payment>
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
		final ImportRecordsSelection selection = getImportRecordsSelection();

		CPaymentImportTableSqlUpdater.updatePaymentImportTable(selection);
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
		final LocalDate date = TimeUtil.asLocalDate(importRecord.getDateTrx());

		final IPaymentBL paymentsService = Services.get(IPaymentBL.class);
		return paymentsService.newBuilderOfInvoice(importRecord.getC_Invoice())
				//.receipt(importRecord.isReceipt())
				.adOrgId(OrgId.ofRepoId(importRecord.getAD_Org_ID()))
				.bpartnerId(BPartnerId.ofRepoId(importRecord.getC_BPartner_ID()))
				.payAmt(importRecord.getPayAmt())
				.discountAmt(importRecord.getDiscountAmt())
				.tenderType(TenderType.DirectDeposit)
				.dateAcct(date)
				.dateTrx(date)
				.description("Import for debitorId/creditorId" + importRecord.getBPartnerValue())
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
