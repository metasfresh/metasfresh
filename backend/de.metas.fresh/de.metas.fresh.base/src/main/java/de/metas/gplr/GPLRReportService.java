package de.metas.gplr;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.department.DepartmentService;
import de.metas.document.IDocTypeBL;
import de.metas.gplr.source.GPLRSourceDocuments;
import de.metas.gplr.source.GPLRSourceDocumentsService;
import de.metas.incoterms.repository.IncotermsRepository;
import de.metas.invoice.InvoiceId;
import de.metas.money.MoneyService;
import de.metas.organization.IOrgDAO;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.sectionCode.SectionCodeService;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

@Service
public class GPLRReportService
{
	//
	// Services
	@NonNull private final GPLRSourceDocumentsService sourceDocumentsService;
	@NonNull private final DepartmentService departmentService;
	@NonNull private final SectionCodeService sectionCodeService;
	@NonNull private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	@NonNull private final MoneyService moneyService;
	@NonNull private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	@NonNull private final IncotermsRepository incotermsRepository;

	public GPLRReportService(
			final @NonNull GPLRSourceDocumentsService sourceDocumentsService,
			final @NonNull DepartmentService departmentService,
			final @NonNull SectionCodeService sectionCodeService,
			final @NonNull MoneyService moneyService,
			final @NonNull IncotermsRepository incotermsRepository)
	{
		this.sourceDocumentsService = sourceDocumentsService;
		this.departmentService = departmentService;
		this.sectionCodeService = sectionCodeService;
		this.moneyService = moneyService;
		this.incotermsRepository = incotermsRepository;
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

	private void createReport(@NonNull final GPLRSourceDocuments source)
	{
		if (isReportGeneratedFor(source.getSalesInvoiceId()))
		{
			throw new AdempiereException("Report was already generated");
		}

		GPLRReportCreateCommand.builder()
				.departmentService(departmentService)
				.sectionCodeService(sectionCodeService)
				.docTypeBL(docTypeBL)
				.userBL(userBL)
				.orgDAO(orgDAO)
				.paymentTermRepository(paymentTermRepository)
				.moneyService(moneyService)
				.bpartnerBL(bpartnerBL)
				.incotermsRepository(incotermsRepository)
				//
				.source(source)
				//
				.build()
				.execute();
	}

}
