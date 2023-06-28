package de.metas.gplr.report;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.department.DepartmentService;
import de.metas.gplr.report.model.GPLRReport;
import de.metas.gplr.report.model.GPLRReportId;
import de.metas.gplr.report.repository.GPLRReportRepository;
import de.metas.gplr.source.SourceDocumentsService;
import de.metas.gplr.source.model.SourceDocuments;
import de.metas.invoice.InvoiceId;
import de.metas.money.MoneyService;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.PInstanceRequest;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.report.client.ReportsClient;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.banking.model.I_C_Invoice;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_GPLR_Report;
import org.compiere.util.Env;
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
	@NonNull private final AttachmentEntryService attachmentService;
	@NonNull private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);

	private static final AdProcessId JASPER_PROCESS_ID = AdProcessId.ofRepoId(999999); // TODO implement and set the right process ID
	private static final String JASPER_PROCESS_PARAM_GPLR_Report_ID = I_GPLR_Report.COLUMNNAME_GPLR_Report_ID;

	public GPLRReportService(
			final @NonNull GPLRReportRepository gplrReportRepository,
			final @NonNull SourceDocumentsService sourceDocumentsService,
			final @NonNull DepartmentService departmentService,
			final @NonNull MoneyService moneyService,
			final @NonNull AttachmentEntryService attachmentService)
	{
		this.gplrReportRepository = gplrReportRepository;
		this.sourceDocumentsService = sourceDocumentsService;
		this.departmentService = departmentService;
		this.moneyService = moneyService;
		this.attachmentService = attachmentService;
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

		final GPLRReport report = GPLRReportCreateCommand.builder()
				.gplrReportRepository(gplrReportRepository)
				.departmentService(departmentService)
				.currencyCodeConverter(moneyService)
				//
				.source(source)
				//
				.build()
				.execute();

		final ReportResult pdf = createPDF(report.getIdNotNull());

		attachmentService.createNewAttachment(
				TableRecordReference.of(I_C_Invoice.Table_Name, report.getSourceDocument().getSalesInvoiceId()),
				AttachmentEntryCreateRequest.fromReport(pdf)
		);

		return report;
	}

	private ReportResult createPDF(@NonNull final GPLRReportId gplrReportId)
	{
		try
		{
			final PInstanceId pinstanceId = adPInstanceDAO.createADPinstanceAndADPInstancePara(
					PInstanceRequest.builder()
							.processId(JASPER_PROCESS_ID)
							.processParams(ImmutableList.of(
									ProcessInfoParameter.of(JASPER_PROCESS_PARAM_GPLR_Report_ID, gplrReportId)
							))
							.build());

			final ProcessInfo reportProcessInfo = ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setAD_Process_ID(JASPER_PROCESS_ID)
					.setPInstanceId(pinstanceId)
					.setJRDesiredOutputType(OutputType.PDF)
					.build();

			return ReportsClient.get().report(reportProcessInfo);
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed generating GPLR Report PDF", ex)
					.setParameter("gplrReportId", gplrReportId);
		}
	}

}
