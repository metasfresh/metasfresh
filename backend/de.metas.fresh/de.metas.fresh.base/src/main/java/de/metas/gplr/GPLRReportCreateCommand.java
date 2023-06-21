package de.metas.gplr;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.department.Department;
import de.metas.department.DepartmentService;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.forex.ForexContractRef;
import de.metas.gplr.model.BPartnerName;
import de.metas.gplr.model.ForexInfo;
import de.metas.gplr.model.GPLRReport;
import de.metas.gplr.model.GPLRReportSalesOrder;
import de.metas.gplr.model.GPLRReportShipment;
import de.metas.gplr.model.GPLRReportSource;
import de.metas.gplr.model.IncotermsInfo;
import de.metas.gplr.model.PaymentTermName;
import de.metas.gplr.model.SectionCodeAndName;
import de.metas.gplr.source.GPLRSourceDocuments;
import de.metas.i18n.Language;
import de.metas.incoterms.Incoterms;
import de.metas.incoterms.IncotermsId;
import de.metas.incoterms.repository.IncotermsRepository;
import de.metas.invoice.service.impl.InvoiceDAO;
import de.metas.money.MoneyService;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.sectionCode.SectionCodeService;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

final class GPLRReportCreateCommand
{
	//
	// Services
	@NonNull private final DepartmentService departmentService;
	@NonNull private final SectionCodeService sectionCodeService;
	@NonNull private final IDocTypeBL docTypeBL;
	@NonNull private final IUserBL userBL;
	@NonNull private final IOrgDAO orgDAO;
	@NonNull private final IPaymentTermRepository paymentTermRepository;
	@NonNull private final MoneyService moneyService;
	@NonNull private final IBPartnerBL bpartnerBL;
	@NonNull private final IncotermsRepository incotermsRepository;

	private static final Joiner JOINER_SPACE_SEPARATED = Joiner.on(" ").skipNulls();

	//
	// Params
	@NonNull private final GPLRSourceDocuments source;

	//
	// State
	private Instant reportDate;

	@Builder
	private GPLRReportCreateCommand(
			final @NonNull DepartmentService departmentService,
			final @NonNull SectionCodeService sectionCodeService,
			final @NonNull IDocTypeBL docTypeBL,
			final @NonNull IUserBL userBL,
			final @NonNull IOrgDAO orgDAO,
			final @NonNull IPaymentTermRepository paymentTermRepository,
			final @NonNull MoneyService moneyService,
			final @NonNull IBPartnerBL bpartnerBL,
			final @NonNull IncotermsRepository incotermsRepository,
			final @NonNull GPLRSourceDocuments source)
	{
		this.departmentService = departmentService;
		this.sectionCodeService = sectionCodeService;
		this.docTypeBL = docTypeBL;
		this.userBL = userBL;
		this.orgDAO = orgDAO;
		this.paymentTermRepository = paymentTermRepository;
		this.moneyService = moneyService;
		this.bpartnerBL = bpartnerBL;
		this.incotermsRepository = incotermsRepository;
		//
		this.source = source;
	}

	public void execute()
	{
		this.reportDate = SystemTime.asInstant();

		final GPLRReport report = GPLRReport.builder()
				.created(reportDate)
				.source(createGPLRReportSource())
				.salesOrder(createGPLRReportSalesOrder())
				// .shipments(source.getShipments()
				// 		.stream()
				// 		.map(this::createGPLRReportShipment)
				// 		.collect(ImmutableList.toImmutableList()))
				.shipments(ImmutableList.of()) // TODO
				.purchaseOrders(ImmutableList.of())  // TODO
				.lineItems(ImmutableList.of())  // TODO
				.charges(ImmutableList.of())  // TODO
				.otherNotes(ImmutableList.of())  // TODO
				.build();

		System.out.println(report);
	}

	private GPLRReportSource createGPLRReportSource()
	{
		final I_C_Invoice salesInvoice = source.getSalesInvoice();
		final OrgId orgId = OrgId.ofRepoId(salesInvoice.getAD_Org_ID());

		return GPLRReportSource.builder()
				.departmentName(getDepartment().map(Department::getName).orElse(null))
				.sectionCode(getSectionCode().orElse(null))
				.documentNo(salesInvoice.getDocumentNo())
				.invoiceDocTypeName(getDocTypeName(salesInvoice))
				.createdByName(getUserNameById(salesInvoice.getCreatedBy()))
				.documentDate(LocalDateAndOrgId.ofTimestamp(salesInvoice.getDateInvoiced(), orgId, orgDAO::getTimeZone))
				.created(LocalDateAndOrgId.ofTimestamp(salesInvoice.getCreated(), orgId, orgDAO::getTimeZone))
				.product("TODO") // TODO find out how to set it here since the SAP_ProductHierarchy is on M_Product which is not on document header level!
				.paymentTerm(PaymentTermName.of(paymentTermRepository.getById(PaymentTermId.ofRepoId(salesInvoice.getC_PaymentTerm_ID()))))
				.dueDate(LocalDateAndOrgId.ofNullableTimestamp(salesInvoice.getDueDate(), orgId, orgDAO::getTimeZone))
				.forexInfo(getForexInfo(salesInvoice))
				.build();
	}

	private Optional<SectionCodeAndName> getSectionCode()
	{
		return getSectionCodeId()
				.map(sectionCodeService::getById)
				.map(SectionCodeAndName::of);
	}

	private Optional<SectionCodeId> getSectionCodeId()
	{
		final I_C_Order salesOrder = source.getSalesOrder();
		return SectionCodeId.optionalOfRepoId(salesOrder.getM_SectionCode_ID());
	}

	private Optional<Department> getDepartment()
	{
		final I_C_Order salesOrder = source.getSalesOrder();
		final Instant dateOrdered = salesOrder.getDateOrdered().toInstant();
		return getSectionCodeId()
				.flatMap(sectionCodeId -> departmentService.getDepartmentBySectionCodeId(sectionCodeId, dateOrdered));
	}

	private String getUserNameById(final int userRepoId)
	{
		final UserId userId = UserId.ofRepoIdOrNull(userRepoId);
		if (userId == null)
		{
			// shall not happen
			return "";
		}

		final I_AD_User user = userBL.getById(userId);
		return IUserBL.buildContactName(user.getFirstname(), user.getLastname());
	}

	private String getDocTypeName(final I_C_Invoice salesInvoice)
	{
		final DocTypeId invoiceDocTypeId = DocTypeId.ofRepoId(salesInvoice.getC_DocType_ID());
		return docTypeBL.getNameById(invoiceDocTypeId).translate(Language.getBaseAD_Language());
	}

	private ForexInfo getForexInfo(final I_C_Invoice salesInvoice)
	{
		final ForexContractRef forexContractRef = InvoiceDAO.extractForeignContractRef(salesInvoice);
		if (forexContractRef == null)
		{
			return null;
		}

		return ForexInfo.builder()
				.foreignCurrency(moneyService.getCurrencyCodeByCurrencyId(forexContractRef.getForeignCurrencyId()))
				.currencyRate(forexContractRef.getCurrencyRate())
				.build();
	}

	private GPLRReportSalesOrder createGPLRReportSalesOrder()
	{
		final I_C_Order salesOrder = source.getSalesOrder();

		return GPLRReportSalesOrder.builder()
				.documentNo(salesOrder.getDocumentNo())
				.customer(getBPartnerName(BPartnerId.ofRepoId(salesOrder.getC_BPartner_ID())))
				.frameContractNo(null) // TODO figure out how to fetch it... is it C_Order.C_FrameAgreement_Order_ID.DocumentNo?
				.poReference(salesOrder.getPOReference())
				.incoterms(getIncotermsInfo(salesOrder))
				.build();
	}

	private BPartnerName getBPartnerName(final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = bpartnerBL.getById(bpartnerId);
		return BPartnerName.builder()
				.code(bpartner.getValue())
				.name(bpartner.getName())
				.vatId(bpartner.getVATaxID())
				.build();
	}

	@Nullable
	private IncotermsInfo getIncotermsInfo(final I_C_Order order)
	{
		final IncotermsId incotermsId = IncotermsId.ofRepoIdOrNull(order.getC_Incoterms_ID());
		if (incotermsId == null)
		{
			return null;
		}

		final Incoterms incoterms = incotermsRepository.getById(incotermsId);

		return IncotermsInfo.builder()
				.code(incoterms.getValue())
				.location(order.getIncotermLocation())
				.build();
	}

	private GPLRReportShipment createGPLRReportShipment(final I_M_InOut shipment)
	{
		// TODO

		return null;
	}
}
