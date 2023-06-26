package de.metas.gplr;

import de.metas.department.DepartmentService;
import de.metas.gplr.source.SourceDocuments;
import de.metas.gplr.source.SourceDocumentsService;
import de.metas.invoice.InvoiceId;
import de.metas.money.MoneyService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

@Service
public class GPLRReportService
{
	//
	// Services
	@NonNull private final SourceDocumentsService sourceDocumentsService;
	@NonNull private final DepartmentService departmentService;
	@NonNull private final MoneyService moneyService;

	public GPLRReportService(
			final @NonNull SourceDocumentsService sourceDocumentsService,
			final @NonNull DepartmentService departmentService,
			final @NonNull MoneyService moneyService)
	{
		this.sourceDocumentsService = sourceDocumentsService;
		this.departmentService = departmentService;
		this.moneyService = moneyService;
	}

	public boolean isReportGeneratedFor(final InvoiceId invoiceId)
	{
		// TODO implement
		return false;
	}

	public void createReport(@NonNull final InvoiceId invoiceId)
	{
		createReport(sourceDocumentsService.getByInvoiceId(invoiceId));
	}

	private void createReport(@NonNull final SourceDocuments source)
	{
		if (isReportGeneratedFor(source.getSalesInvoiceId()))
		{
			throw new AdempiereException("Report was already generated");
		}

		GPLRReportCreateCommand.builder()
				.departmentService(departmentService)
				.moneyService(moneyService)
				//
				.source(source)
				//
				.build()
				.execute();
	}

}
