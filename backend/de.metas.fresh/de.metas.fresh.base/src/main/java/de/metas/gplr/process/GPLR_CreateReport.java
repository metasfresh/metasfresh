package de.metas.gplr.process;

import de.metas.gplr.report.GPLRReportService;
import de.metas.gplr.report.model.GPLRReport;
import de.metas.i18n.BooleanWithReason;
import de.metas.invoice.InvoiceId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;

import java.util.Optional;

public class GPLR_CreateReport extends JavaProcess implements IProcessPrecondition
{
	private final GPLRReportService gplrReportService = SpringContextHolder.instance.getBean(GPLRReportService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final InvoiceId invoiceId = getInvoiceId(context).orElse(null);
		if (invoiceId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Cannot determine invoice ID");
		}

		final BooleanWithReason eligible = gplrReportService.checkEligibleToGenerateReport(invoiceId);
		if (eligible.isFalse())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(eligible.getReason());
		}

		return ProcessPreconditionsResolution.accept();
	}

	private static Optional<InvoiceId> getInvoiceId(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return Optional.empty();
		}

		final TableRecordReference recordRef = TableRecordReference.of(context.getTableName(), context.getSingleSelectedRecordId());
		return getInvoiceId(recordRef);
	}

	private static Optional<InvoiceId> getInvoiceId(final @NonNull TableRecordReference recordRef)
	{
		if (I_C_Invoice.Table_Name.equals(recordRef.getTableName()))
		{
			return InvoiceId.optionalOfRepoId(recordRef.getRecord_ID());
		}
		else
		{
			return Optional.empty();
		}
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final InvoiceId invoiceId = getInvoiceId(getRecordRef())
				.orElseThrow(() -> new AdempiereException("Cannot determine invoice ID")); // shall not happen

		final GPLRReport report = gplrReportService.createReport(invoiceId);
		log.debug("Generated report: {}", report);

		return "GPLR created";
	}
}
