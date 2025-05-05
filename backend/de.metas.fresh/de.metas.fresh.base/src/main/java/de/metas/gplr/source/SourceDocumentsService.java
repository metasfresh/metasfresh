package de.metas.gplr.source;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyRate;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.MeansOfTransportation;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.engine.DocStatus;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.forex.ForexContract;
import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractRef;
import de.metas.forex.ForexContractService;
import de.metas.gplr.source.model.SourceBPartnerInfo;
import de.metas.gplr.source.model.SourceCurrencyInfo;
import de.metas.gplr.source.model.SourceDocuments;
import de.metas.gplr.source.model.SourceIncotermsAndLocation;
import de.metas.gplr.source.model.SourceInvoice;
import de.metas.gplr.source.model.SourceInvoiceLine;
import de.metas.gplr.source.model.SourceOrder;
import de.metas.gplr.source.model.SourceOrderCost;
import de.metas.gplr.source.model.SourceOrderLine;
import de.metas.gplr.source.model.SourceShipment;
import de.metas.gplr.source.model.SourceShipperInfo;
import de.metas.gplr.source.model.SourceTaxInfo;
import de.metas.gplr.source.model.SourceUserInfo;
import de.metas.gplr.source.model.SourceWarehouseInfo;
import de.metas.i18n.BooleanWithReason;
import de.metas.incoterms.IncotermsId;
import de.metas.incoterms.repository.IncotermsRepository;
import de.metas.inout.IInOutBL;
import de.metas.inout.location.adapter.DocumentDeliveryLocationAdapter;
import de.metas.inout.location.adapter.DocumentLocationAdapter;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.impl.InvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.location.ILocationBL;
import de.metas.location.LocationId;
import de.metas.money.CurrencyId;
import de.metas.money.MoneyService;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.costs.OrderCost;
import de.metas.order.costs.OrderCostDetail;
import de.metas.order.costs.OrderCostService;
import de.metas.order.costs.OrderCostType;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.paymentinstructions.PaymentInstructionsId;
import de.metas.payment.paymentinstructions.PaymentInstructionsService;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.sectionCode.SectionCodeService;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SourceDocumentsService
{
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	@NonNull private final ILocationBL locationBL = Services.get(ILocationBL.class);
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	@NonNull private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	@NonNull private final PaymentInstructionsService paymentInstructionsService;
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	@NonNull private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	@NonNull final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@NonNull private final SectionCodeService sectionCodeService;
	@NonNull private final IDocumentLocationBL documentLocationBL;
	@NonNull private final MoneyService moneyService;
	@NonNull private final OrderCostService orderCostService;
	@NonNull private final IncotermsRepository incotermsRepository;
	@NonNull private final ForexContractService forexContractService;
	@NonNull private final DeliveryPlanningService deliveryPlanningService;

	public SourceDocumentsService(
			@NonNull final PaymentInstructionsService paymentInstructionsService,
			@NonNull final SectionCodeService sectionCodeService,
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final MoneyService moneyService,
			@NonNull final OrderCostService orderCostService,
			@NonNull final IncotermsRepository incotermsRepository,
			@NonNull final ForexContractService forexContractService,
			@NonNull final DeliveryPlanningService deliveryPlanningService)
	{
		this.paymentInstructionsService = paymentInstructionsService;
		this.sectionCodeService = sectionCodeService;
		this.documentLocationBL = documentLocationBL;
		this.moneyService = moneyService;
		this.orderCostService = orderCostService;
		this.incotermsRepository = incotermsRepository;
		this.forexContractService = forexContractService;
		this.deliveryPlanningService = deliveryPlanningService;
	}

	public BooleanWithReason checkEligible(final InvoiceId invoiceId)
	{
		return checkEligible(invoiceBL.getById(invoiceId));
	}

	private static BooleanWithReason checkEligible(final I_C_Invoice invoice)
	{
		if (!invoice.isSOTrx())
		{
			return BooleanWithReason.falseBecause("Invoice shall be a sales invoice");
		}

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(invoice.getDocStatus());
		if (!docStatus.isCompleted() && !docStatus.isReversed())
		{
			return BooleanWithReason.falseBecause("Invoice shall be Completed or Reversed");
		}

		return BooleanWithReason.TRUE;
	}

	public SourceDocuments getByInvoiceId(final InvoiceId invoiceId)
	{
		final SourceInvoice salesInvoice = getSalesInvoice(invoiceId);
		final OrderId salesOrderId = salesInvoice.getOrderId();
		final I_C_Order salesOrder = orderBL.getById(salesOrderId);
		final List<I_C_Order> purchaseOrders = orderBL.getPurchaseOrdersBySalesOrderId(salesOrderId)
				.stream()
				.filter(purchaseOrder -> DocStatus.ofNullableCodeOrUnknown(purchaseOrder.getDocStatus()).isCompleted())
				.toList();
		final Set<OrderId> purchaseOrderIds = purchaseOrders.stream()
				.map(purchaseOrder -> OrderId.ofRepoId(purchaseOrder.getC_Order_ID()))
				.collect(ImmutableSet.toImmutableSet());
		final boolean isBackToBack = !purchaseOrderIds.isEmpty();

		final HashSet<OrderId> allOrderIds = new HashSet<>();
		allOrderIds.add(salesOrderId);
		allOrderIds.addAll(purchaseOrderIds);

		final ImmutableListMultimap<OrderId, SourceOrderCost> orderCostsByOrderId = orderCostService.getByOrderIds(allOrderIds)
				.stream()
				.map(this::toSourceOrderCost)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						SourceOrderCost::getOrderId,
						orderCost -> orderCost
				));

		final ImmutableListMultimap<OrderId, SourceOrderLine> linesByOrderId = orderBL.getLinesByOrderIds(allOrderIds)
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						orderLine -> OrderId.ofRepoId(orderLine.getC_Order_ID()),
						orderLine -> toSourceOrderLine(orderLine, SOTrx.ofBoolean(orderLine.getC_Order_ID() == salesOrderId.getRepoId()))
				));

		final Function<I_C_Order, SourceOrder> toOrderAndLines = order -> {
			final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
			return SourceOrder.builder()
					.documentNo(order.getDocumentNo())
					.bpartner(getBPartnerInfo(order))
					.frameContractNo(extractFrameContractNo(linesByOrderId.get(orderId)))
					.sectionCode(SectionCodeId.optionalOfRepoId(order.getM_SectionCode_ID()).map(sectionCodeService::getById))
					.currencyInfo(extractCurrencyInfo(order))
					.dateOrdered(order.getDateOrdered().toInstant())
					.poReference(StringUtils.trimBlankToNull(order.getPOReference()))
					.paymentTerm(paymentTermRepository.getById(PaymentTermId.ofRepoId(order.getC_PaymentTerm_ID())))
					.paymentInstructions(PaymentInstructionsId.optionalOfRepoId(order.getC_PaymentInstruction_ID())
							.map(paymentInstructionsService::getById)
							.orElse(null))
					.incotermsAndLocation(extractIncotermsAndLocation(order))
					.lines(linesByOrderId.get(orderId))
					.orderCosts(orderCostsByOrderId.get(orderId))
					.build();
		};

		return SourceDocuments.builder()
				.salesInvoice(salesInvoice)
				.salesOrder(toOrderAndLines.apply(salesOrder))
				.shipments(getSourceShipments(salesOrderId, isBackToBack))
				.purchaseOrders(purchaseOrders.stream()
						.map(toOrderAndLines)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private SourceCurrencyInfo extractCurrencyInfo(final I_C_Order order)
	{
		final CurrencyConversionContext conversionCtx = orderBL.getCurrencyConversionContext(order);
		final CurrencyId baseCurrencyId = moneyService.getBaseCurrencyId(ClientAndOrgId.ofClientAndOrg(order.getAD_Client_ID(), order.getAD_Org_ID()));
		final CurrencyId foreignCurrencyId = CurrencyId.ofRepoId(order.getC_Currency_ID());
		final CurrencyCode foreignCurrency = moneyService.getCurrencyCodeByCurrencyId(foreignCurrencyId);
		final CurrencyCode localCurrency;
		final CurrencyRate currencyRate;
		final String forexContractNo;

		final ImmutableList<ForexContract> forexContracts = forexContractService.getContractsByOrderId(OrderId.ofRepoId(order.getC_Order_ID()));
		final ForexContract forexContract = forexContracts.size() == 1 ? forexContracts.get(0) : null; // TODO: what to do when an order is assigned to multiple FEC contacts?
		if (forexContract == null)
		{
			forexContractNo = null;
			localCurrency = moneyService.getCurrencyCodeByCurrencyId(baseCurrencyId);
			currencyRate = moneyService.getCurrencyRate(foreignCurrency, localCurrency, conversionCtx);
		}
		else
		{
			forexContractNo = forexContract.getDocumentNo();
			final CurrencyId localCurrencyId = forexContract.getLocalCurrencyIdByForeignCurrencyId(foreignCurrencyId);
			if (!CurrencyId.equals(localCurrencyId, baseCurrencyId))
			{
				throw new AdempiereException("FEC local currency is not matching base/accounting currency")
						.appendParametersToMessage()
						.setParameter("baseCurrencyId", baseCurrencyId)
						.setParameter("localCurrencyId", localCurrencyId)
						.setParameter("forexContract", forexContract)
						.setParameter("order", order);
			}
			localCurrency = moneyService.getCurrencyCodeByCurrencyId(localCurrencyId);
			currencyRate = CurrencyRate.builder()
					.fromCurrencyId(foreignCurrencyId)
					.fromCurrencyPrecision(moneyService.getStdPrecision(foreignCurrencyId))
					.toCurrencyId(localCurrencyId)
					.toCurrencyPrecision(moneyService.getStdPrecision(localCurrencyId))
					.conversionRate(forexContract.getCurrencyRate(foreignCurrencyId, localCurrencyId))
					.conversionDate(conversionCtx.getConversionDate())
					.conversionTypeId(conversionCtx.getConversionTypeId())
					.build();
		}

		return SourceCurrencyInfo.builder()
				.currencyRate(currencyRate)
				.foreignCurrencyCode(foreignCurrency)
				.localCurrencyCode(localCurrency)
				.fecDocumentNo(forexContractNo)
				.build();
	}

	@Nullable
	private String extractFrameContractNo(final List<SourceOrderLine> orderLines)
	{
		final Set<FlatrateTermId> contractIds = orderLines.stream()
				.map(SourceOrderLine::getContractId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		if (contractIds.size() != 1)
		{
			return null;
		}

		final FlatrateTermId contractId = contractIds.iterator().next();

		return flatrateBL.getById(contractId).getDocumentNo();
	}

	private SourceBPartnerInfo getBPartnerInfo(final I_C_Order order)
	{
		return OrderDocumentLocationAdapterFactory.locationAdapter(order)
				.toPlainDocumentLocation(documentLocationBL)
				.map(this::toBPartnerInfo)
				.orElseThrow(() -> new AdempiereException("Failed extracting document location from " + order));
	}

	private SourceOrderLine toSourceOrderLine(@NonNull final I_C_OrderLine orderLine, @NonNull final SOTrx soTrx)
	{
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final I_M_Product product = productBL.getById(productId);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(orderLine.getM_AttributeSetInstance_ID());

		final CurrencyCode currency = moneyService.getCurrencyCodeByCurrencyId(CurrencyId.ofRepoId(orderLine.getC_Currency_ID()));

		return SourceOrderLine.builder()
				.id(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()))
				.lineNo(orderLine.getLine())
				.productId(productId)
				.productCode(product.getValue())
				.productName(product.getName())
				.qtyEntered(orderBL.getQtyEntered(orderLine))
				.priceFC(Amount.of(orderLine.getPriceActual(), currency))
				.cogsLC(soTrx.isSales() ? getCOGS_LC(orderLine) : null)
				.batchNo(attributeSetInstanceBL.getAttributeValueOrNull(AttributeConstants.HU_BatchNo, asiId))
				.lineNetAmtFC(Amount.of(orderLine.getLineNetAmt(), currency))
				.taxAmtFC(Amount.of(orderLine.getTaxAmtInfo(), currency))
				.tax(extractSourceTaxInfo(orderLine))
				.contractId(FlatrateTermId.ofRepoIdOrNull(orderLine.getC_Flatrate_Term_ID()))
				.build();
	}

	private SourceTaxInfo extractSourceTaxInfo(final I_C_OrderLine orderLine)
	{
		final TaxId taxId = TaxId.ofRepoId(orderLine.getC_Tax_ID());
		return getSourceTaxInfo(taxId);
	}

	private SourceTaxInfo getSourceTaxInfo(final TaxId taxId)
	{
		final Tax tax = taxBL.getTaxById(taxId);
		return SourceTaxInfo.builder()
				.vatCode(tax.getTaxCode())
				.rate(tax.getRate())
				.build();
	}

	private SourceOrderCost toSourceOrderCost(final OrderCost orderCost)
	{
		final OrderCostType costType = orderCostService.getCostTypeById(orderCost.getCostTypeId());
		return SourceOrderCost.builder()
				.orderId(orderCost.getOrderId())
				.costTypeName(costType.getCode() + " " + costType.getName())
				.costAmountFC(orderCost.getCostAmount().toAmount(moneyService::getCurrencyCodeByCurrencyId))
				.vendor(orderCost.getBpartnerId() != null ? prepareBPartnerInfo(orderCost.getBpartnerId()).build() : null)
				.basedOnOrderLineIds(orderCost.getDetails()
						.stream()
						.map(OrderCostDetail::getOrderLineId)
						.collect(ImmutableSet.toImmutableSet()))
				.createdOrderLineId(orderCost.getCreatedOrderLineId())
				.build();
	}

	@Nullable
	private SourceIncotermsAndLocation extractIncotermsAndLocation(final I_C_Order order)
	{
		final IncotermsId incotermsId = IncotermsId.ofRepoIdOrNull(order.getC_Incoterms_ID());
		if (incotermsId == null)
		{
			return null;
		}

		return SourceIncotermsAndLocation.builder()
				.incoterms(incotermsRepository.getById(incotermsId))
				.location(order.getIncotermLocation())
				.build();
	}

	private SourceInvoice getSalesInvoice(final InvoiceId invoiceId)
	{
		final I_C_Invoice salesInvoice = invoiceBL.getById(invoiceId);
		checkEligible(salesInvoice).assertTrue();
		return toSourceInvoice(salesInvoice);
	}

	private SourceInvoice toSourceInvoice(@NonNull final I_C_Invoice invoice)
	{
		final OrgId orgId = OrgId.ofRepoId(invoice.getAD_Org_ID());
		final CurrencyId currencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());
		final CurrencyCode currencyCode = moneyService.getCurrencyCodeByCurrencyId(currencyId);

		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		return SourceInvoice.builder()
				.id(invoiceId)
				.orderId(OrderId.ofRepoId(invoice.getC_Order_ID()))
				.orgId(orgId)
				.documentNo(invoice.getDocumentNo())
				.docTypeName(docTypeBL.getById(DocTypeId.ofRepoId(invoice.getC_DocType_ID())).getName())
				.currencyInfo(extractCurrencyInfo(invoice))
				.createdBy(getUserInfo(invoice.getCreatedBy()))
				.created(LocalDateAndOrgId.ofTimestamp(invoice.getCreated(), orgId, orgDAO::getTimeZone))
				.dateInvoiced(LocalDateAndOrgId.ofTimestamp(invoice.getDateInvoiced(), orgId, orgDAO::getTimeZone))
				.sapProductHierarchy(getSapProductHierarchy(invoiceId))
				.paymentTerm(paymentTermRepository.getById(PaymentTermId.ofRepoId(invoice.getC_PaymentTerm_ID())))
				.paymentInstructions(PaymentInstructionsId.optionalOfRepoId(invoice.getC_PaymentInstruction_ID())
						.map(paymentInstructionsService::getById)
						.orElse(null))
				.dueDate(LocalDateAndOrgId.ofNullableTimestamp(invoice.getDueDate(), orgId, orgDAO::getTimeZone))
				.invoiceAdditionalText(StringUtils.trimBlankToNull(invoice.getInvoiceAdditionalText()))
				.linesNetAmtFC(Amount.of(invoice.getTotalLines(), currencyCode))
				.taxAmtFC(Amount.of(invoice.getGrandTotal().subtract(invoice.getTotalLines()), currencyCode))
				.lines(invoiceBL.getLines(invoiceId)
						.stream()
						.map(invoiceLine -> toSourceInvoiceLine(invoiceLine, currencyCode))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private SourceInvoiceLine toSourceInvoiceLine(final I_C_InvoiceLine invoiceLine, final CurrencyCode currencyCode)
	{
		return SourceInvoiceLine.builder()
				.id(InvoiceAndLineId.ofRepoId(invoiceLine.getC_Invoice_ID(), invoiceLine.getC_InvoiceLine_ID()))
				.priceFC(Amount.of(invoiceLine.getPriceActual(), currencyCode))
				.lineNetAmtFC(Amount.of(invoiceLine.getLineNetAmt(), currencyCode))
				.taxAmtFC(Amount.of(invoiceLine.getTaxAmtInfo(), currencyCode))
				.tax(getSourceTaxInfo(TaxId.ofRepoId(invoiceLine.getC_Tax_ID())))
				.orderLineId(OrderLineId.ofRepoIdOrNull(invoiceLine.getC_OrderLine_ID()))
				.build();
	}

	@Nullable
	private String getSapProductHierarchy(final InvoiceId invoiceId)
	{
		final ImmutableSet<ProductId> productIds = invoiceBL.getLines(invoiceId)
				.stream()
				.map(line -> ProductId.ofRepoIdOrNull(line.getM_Product_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableSet<String> sapProductHierarchies = productBL.getByIds(productIds)
				.stream()
				.map(product -> StringUtils.trimBlankToNull(product.getSAP_ProductHierarchy()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		return sapProductHierarchies.size() == 1
				? sapProductHierarchies.iterator().next()
				: null;
	}

	private SourceCurrencyInfo extractCurrencyInfo(final I_C_Invoice invoice)
	{
		final CurrencyId invoiceCurrencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());
		final CurrencyId baseCurrencyId = moneyService.getBaseCurrencyId(ClientAndOrgId.ofClientAndOrg(invoice.getAD_Client_ID(), invoice.getAD_Org_ID()));
		final CurrencyConversionContext conversionCtx = invoiceBL.getCurrencyConversionCtx(invoice);

		final CurrencyRate currencyRate;
		final CurrencyCode localCurrency;
		final CurrencyCode foreignCurrency;
		final String forexContractNo;

		final ForexContractRef forexContractRef = InvoiceDAO.extractForeignContractRef(invoice);
		if (forexContractRef == null)
		{
			forexContractNo = null;

			localCurrency = moneyService.getCurrencyCodeByCurrencyId(baseCurrencyId);
			foreignCurrency = moneyService.getCurrencyCodeByCurrencyId(invoiceCurrencyId);

			currencyRate = moneyService.getCurrencyRate(foreignCurrency, localCurrency, conversionCtx);
		}
		else
		{
			if (!CurrencyId.equals(forexContractRef.getOrderCurrencyId(), invoiceCurrencyId))
			{
				throw new AdempiereException("FEC contract and invoice currency does not match")
						.appendParametersToMessage()
						.setParameter("forexContractRef", forexContractRef)
						.setParameter("invoiceCurrencyId", invoiceCurrencyId)
						.setParameter("invoice", invoice);
			}
			if (!CurrencyId.equals(forexContractRef.getLocalCurrencyId(), baseCurrencyId))
			{
				throw new AdempiereException("FEC local currency is not matching base/accounting currency")
						.appendParametersToMessage()
						.setParameter("forexContractRef", forexContractRef)
						.setParameter("baseCurrencyId", baseCurrencyId)
						.setParameter("invoice", invoice);
			}

			final ForexContractId forexContractId = forexContractRef.getForexContractId();
			forexContractNo = forexContractId != null
					? forexContractService.getById(forexContractId).getDocumentNo()
					: null;

			final CurrencyId foreignCurrencyId = forexContractRef.getForeignCurrencyId();
			foreignCurrency = moneyService.getCurrencyCodeByCurrencyId(foreignCurrencyId);
			final CurrencyId localCurrencyId = forexContractRef.getLocalCurrencyId();
			localCurrency = moneyService.getCurrencyCodeByCurrencyId(localCurrencyId);
			currencyRate = CurrencyRate.builder()
					.fromCurrencyId(foreignCurrencyId)
					.fromCurrencyPrecision(moneyService.getStdPrecision(foreignCurrencyId))
					.toCurrencyId(localCurrencyId)
					.toCurrencyPrecision(moneyService.getStdPrecision(localCurrencyId))
					.conversionRate(forexContractRef.getCurrencyRate(foreignCurrencyId, localCurrencyId))
					.conversionDate(conversionCtx.getConversionDate())
					.conversionTypeId(conversionCtx.getConversionTypeId())
					.build();
		}

		return SourceCurrencyInfo.builder()
				.currencyRate(currencyRate)
				.foreignCurrencyCode(foreignCurrency)
				.localCurrencyCode(localCurrency)
				.fecDocumentNo(forexContractNo)
				.build();
	}

	private ImmutableList<SourceShipment> getSourceShipments(final OrderId salesOrderId, final boolean isBackToBack)
	{
		return inOutBL.getByOrderId(salesOrderId)
				.stream()
				.filter(shipment -> DocStatus.ofNullableCodeOrUnknown(shipment.getDocStatus()).isCompletedOrClosed())
				.map(shipment -> toSourceShipment(shipment, isBackToBack))
				.collect(ImmutableList.toImmutableList());
	}

	private SourceShipment toSourceShipment(final I_M_InOut shipment, final boolean isBackToBack)
	{
		final OrgId orgId = OrgId.ofRepoId(shipment.getAD_Org_ID());

		return SourceShipment.builder()
				.orgId(orgId)
				.documentNo(shipment.getDocumentNo())
				.movementDate(LocalDateAndOrgId.ofTimestamp(shipment.getMovementDate(), orgId, orgDAO::getTimeZone))
				.shipFrom(getWarehouseInfo(WarehouseId.ofRepoId(shipment.getM_Warehouse_ID())))
				.shipTo(getShipTo(shipment))
				.isBackToBack(isBackToBack)
				.incoterms(extractIncotermsAndLocation(shipment))
				.shipper(extractShipperInfo(shipment))
				.build();
	}

	private SourceWarehouseInfo getWarehouseInfo(final WarehouseId warehouseId)
	{
		final I_M_Warehouse warehouse = warehouseBL.getById(warehouseId);

		return SourceWarehouseInfo.builder()
				.warehouseId(warehouseId)
				.warehouseCode(warehouse.getValue())
				.warehouseName(warehouse.getName())
				.warehouseExternalId(warehouse.getExternalId())
				.build();
	}

	@Nullable
	private SourceIncotermsAndLocation extractIncotermsAndLocation(final I_M_InOut shipment)
	{
		final IncotermsId incotermsId = IncotermsId.ofRepoIdOrNull(shipment.getC_Incoterms_ID());
		if (incotermsId == null)
		{
			return null;
		}

		return SourceIncotermsAndLocation.builder()
				.incoterms(incotermsRepository.getById(incotermsId))
				.location(shipment.getIncotermLocation())
				.build();
	}

	@Nullable
	private SourceShipperInfo extractShipperInfo(final I_M_InOut shipment)
	{
		final ShipperId shipperId = ShipperId.ofRepoIdOrNull(shipment.getM_Shipper_ID());
		if (shipperId != null)
		{
			return SourceShipperInfo.builder()
					.name(shipperDAO.getShipperName(shipperId))
					.build();
		}

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoIdOrNull(shipment.getM_Delivery_Planning_ID());
		if (deliveryPlanningId != null)
		{
			final MeansOfTransportation meansOfTransportation = deliveryPlanningService.getMeansOfTransportationByDeliveryPlanningId(deliveryPlanningId).orElse(null);
			if (meansOfTransportation != null)
			{
				return SourceShipperInfo.builder()
						.name(meansOfTransportation.toDisplayableString())
						.build();
			}
		}

		return null;
	}

	private SourceBPartnerInfo getShipTo(final I_M_InOut shipment)
	{
		final DocumentDeliveryLocationAdapter deliveryLocationAdapter = InOutDocumentLocationAdapterFactory.deliveryLocationAdapter(shipment);
		final DocumentLocation documentLocation;
		final boolean isDropShip = deliveryLocationAdapter.isDropShip();
		if (isDropShip)
		{
			documentLocation = deliveryLocationAdapter.toPlainDocumentLocation(documentLocationBL).orElse(null);
		}
		else
		{
			final DocumentLocationAdapter documentLocationAdapter = InOutDocumentLocationAdapterFactory.locationAdapter(shipment);
			documentLocation = documentLocationAdapter.toPlainDocumentLocation(documentLocationBL).orElse(null);
		}

		if (documentLocation == null)
		{
			throw new AdempiereException("Failed extracting Ship To location from " + shipment);
		}

		return toBPartnerInfo(documentLocation)
				.withDropShip(isDropShip);
	}

	private SourceBPartnerInfo toBPartnerInfo(final DocumentLocation documentLocation)
	{
		final LocationId locationId = documentLocation.getLocationId();
		if (locationId == null)
		{
			throw new AdempiereException("No location set for " + documentLocation);
		}

		final String vatId = Optional.ofNullable(documentLocation.getBpartnerLocationId()).flatMap(bpartnerBL::getVATTaxId).orElse(null);

		return prepareBPartnerInfo(documentLocation.getBpartnerId())
				.countryCode(locationBL.getCountryCodeByLocationId(locationId))
				.vatId(vatId)
				.build();
	}

	private SourceBPartnerInfo.SourceBPartnerInfoBuilder prepareBPartnerInfo(final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = bpartnerBL.getById(bpartnerId);
		return SourceBPartnerInfo.builder()
				.code(bpartner.getValue())
				.name(bpartner.getName());
	}

	private SourceUserInfo getUserInfo(final int userRepoId)
	{
		final UserId userId = UserId.ofRepoIdOrSystem(userRepoId);
		final I_AD_User user = userBL.getById(userId);
		return SourceUserInfo.builder()
				.firstName(StringUtils.trimBlankToNull(user.getFirstname()))
				.lastName(StringUtils.trimBlankToNull(user.getLastname()))
				.build();
	}

	private Amount getCOGS_LC(@NonNull final I_C_OrderLine salesOrderLine)
	{
		final OrderLineId salesOrderLineId = OrderLineId.ofRepoId(salesOrderLine.getC_OrderLine_ID());
		final AcctSchemaId acctSchemaId = acctSchemaBL.getAcctSchemaIdByClientAndOrg(
				ClientId.ofRepoId(salesOrderLine.getAD_Client_ID()),
				OrgId.ofRepoId(salesOrderLine.getAD_Org_ID()));

		return inoutBL.getCOGSBySalesOrderId(salesOrderLineId, acctSchemaId)
				.toAmount(moneyService::getCurrencyCodeByCurrencyId);
	}

}
