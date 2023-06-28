package de.metas.gplr.report;

import de.metas.department.DepartmentService;
import de.metas.gplr.report.model.GPLRReport;
import de.metas.gplr.report.repository.GPLRReportRepository;
import de.metas.gplr.source.SourceDocumentsService;
import de.metas.gplr.source.model.SourceDocuments;
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
	@NonNull private final GPLRReportRepository gplrReportRepository;
	@NonNull private final SourceDocumentsService sourceDocumentsService;
	@NonNull private final DepartmentService departmentService;
	@NonNull private final MoneyService moneyService;

	public GPLRReportService(
			final @NonNull GPLRReportRepository gplrReportRepository,
			final @NonNull SourceDocumentsService sourceDocumentsService,
			final @NonNull DepartmentService departmentService,
			final @NonNull MoneyService moneyService)
	{
		this.gplrReportRepository = gplrReportRepository;
		this.sourceDocumentsService = sourceDocumentsService;
		this.departmentService = departmentService;
		this.moneyService = moneyService;
	}

	public boolean isReportGeneratedFor(final InvoiceId invoiceId)
	{
		return gplrReportRepository.isReportGeneratedForInvoice(invoiceId);
	}

	public GPLRReport createReport(@NonNull final InvoiceId invoiceId)
	{
		return createReport(sourceDocumentsService.getByInvoiceId(invoiceId));
	}

	private GPLRReport createReport(@NonNull final SourceDocuments source)
	{
		if (isReportGeneratedFor(source.getSalesInvoiceId()))
		{
			throw new AdempiereException("Report was already generated");
		}

		return GPLRReportCreateCommand.builder()
				.gplrReportRepository(gplrReportRepository)
				.departmentService(departmentService)
				.moneyService(moneyService)
				//
				.source(source)
				//
				.build()
				.execute();
	}

}
