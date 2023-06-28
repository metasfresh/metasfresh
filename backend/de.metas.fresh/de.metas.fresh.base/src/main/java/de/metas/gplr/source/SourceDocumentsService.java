package de.metas.gplr.source;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyConversionContext;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
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
import de.metas.gplr.source.model.SourceOrder;
import de.metas.gplr.source.model.SourceOrderCost;
import de.metas.gplr.source.model.SourceOrderLine;
import de.metas.gplr.source.model.SourceShipment;
import de.metas.gplr.source.model.SourceShipperInfo;
import de.metas.gplr.source.model.SourceUserInfo;
import de.metas.gplr.source.model.SourceWarehouseInfo;
import de.metas.incoterms.IncotermsId;
import de.metas.incoterms.repository.IncotermsRepository;
import de.metas.inout.IInOutBL;
import de.metas.inout.location.adapter.DocumentDeliveryLocationAdapter;
import de.metas.inout.location.adapter.DocumentLocationAdapter;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.impl.InvoiceDAO;
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
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.sectionCode.SectionCodeService;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

@Service
public class SourceDocumentsService
{
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	@NonNull private final ILocationBL locationBL = Services.get(ILocationBL.class);
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	@NonNull private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	@NonNull final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);

	@NonNull private final SectionCodeService sectionCodeService;
	@NonNull private final IDocumentLocationBL documentLocationBL;
	@NonNull private final MoneyService moneyService;
	@NonNull private final OrderCostService orderCostService;
	@NonNull private final IncotermsRepository incotermsRepository;
	@NonNull private final ForexContractService forexContractService;

	public SourceDocumentsService(
			@NonNull final SectionCodeService sectionCodeService,
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final MoneyService moneyService,
			@NonNull final OrderCostService orderCostService,
			@NonNull final IncotermsRepository incotermsRepository,
			@NonNull final ForexContractService forexContractService)
	{
		this.sectionCodeService = sectionCodeService;
		this.documentLocationBL = documentLocationBL;
		this.moneyService = moneyService;
		this.orderCostService = orderCostService;
		this.incotermsRepository = incotermsRepository;
		this.forexContractService = forexContractService;
	}

	public SourceDocuments getByInvoiceId(final InvoiceId invoiceId)
	{
		final SourceInvoice salesInvoice = getSalesInvoice(invoiceId);
		final OrderId salesOrderId = salesInvoice.getOrderId();
		final I_C_Order salesOrder = orderBL.getById(salesOrderId);
		final Set<OrderId> purchaseOrderIds = orderBL.getPurchaseOrderIdsBySalesOrderId(salesOrderId);
		final List<I_C_Order> purchaseOrders = orderBL.getByIds(purchaseOrderIds);

		final HashSet<OrderId> allOrderIds = new HashSet<>();
		allOrderIds.add(salesOrderId);
		allOrderIds.addAll(purchaseOrderIds);

		final ImmutableListMultimap<OrderId, SourceOrderLine> linesByOrderId = orderBL.getLinesByOrderIds(allOrderIds)
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						orderLine -> OrderId.ofRepoId(orderLine.getC_Order_ID()),
						this::toSourceOrderLine
				));

		final Function<I_C_Order, SourceOrder> toOrderAndLines = order -> {
			final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
			return SourceOrder.builder()
					.documentNo(order.getDocumentNo())
					.bpartner(getBPartnerInfo(order))
					.frameContractNo(extractFrameContractNo(order))
					.sectionCode(SectionCodeId.optionalOfRepoId(order.getM_SectionCode_ID()).map(sectionCodeService::getById))
					.currencyInfo(extractCurrencyInfo(order))
					.dateOrdered(order.getDateOrdered().toInstant())
					.poReference(StringUtils.trimBlankToNull(order.getPOReference()))
					.paymentTerm(paymentTermRepository.getById(PaymentTermId.ofRepoId(order.getC_PaymentTerm_ID())))
					.incotermsAndLocation(extractIncotermsAndLocation(order))
					.lines(linesByOrderId.get(orderId))
					.orderCosts(orderCostService.getByOrderId(orderId)
							.stream()
							.map(this::toSourceOrderCost)
							.collect(ImmutableList.toImmutableList()))
					.build();
		};

		return SourceDocuments.builder()
				.salesInvoice(salesInvoice)
				.salesOrder(toOrderAndLines.apply(salesOrder))
				.shipments(getSourceShipments(salesOrderId))
				.purchaseOrders(purchaseOrders.stream()
						.map(toOrderAndLines)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private SourceCurrencyInfo extractCurrencyInfo(final I_C_Order order)
	{
		final CurrencyConversionContext conversionCtx = orderBL.getCurrencyConversionContext(order);

		final CurrencyId foreignCurrencyId = CurrencyId.ofRepoId(order.getC_Currency_ID());
		CurrencyCode foreignCurrency = moneyService.getCurrencyCodeByCurrencyId(foreignCurrencyId);
		final CurrencyCode localCurrency;
		final BigDecimal currencyRate;
		final String forexContractNo;

		final ImmutableList<ForexContract> forexContracts = forexContractService.getContractsByOrderId(OrderId.ofRepoId(order.getC_Order_ID()));
		// TODO: what to do when an order is assigned to multiple FEC contacts?
		ForexContract forexContract = forexContracts.size() == 1 ? forexContracts.get(0) : null;
		if (forexContract == null)
		{
			forexContractNo = null;

			final CurrencyId localCurrencyId = acctSchemaBL.getAcctCurrencyId(ClientId.ofRepoId(order.getAD_Client_ID()), OrgId.ofRepoId(order.getAD_Org_ID()));
			localCurrency = moneyService.getCurrencyCodeByCurrencyId(localCurrencyId);

			currencyRate = moneyService.getCurrencyRate(foreignCurrency, localCurrency, conversionCtx).getConversionRate();
		}
		else
		{
			forexContractNo = forexContract.getDocumentNo();
			final CurrencyId localCurrencyId = forexContract.getLocalCurrencyIdByForeignCurrencyId(foreignCurrencyId);
			localCurrency = moneyService.getCurrencyCodeByCurrencyId(localCurrencyId);
			currencyRate = forexContract.getCurrencyRate(foreignCurrencyId, localCurrencyId);
		}

		return SourceCurrencyInfo.builder()
				.currencyRate(currencyRate)
				.foreignCurrencyCode(foreignCurrency)
				.localCurrencyCode(localCurrency)
				.fecDocumentNo(forexContractNo)
				.conversionCtx(conversionCtx)
				.build();
	}

	@Nullable
	private String extractFrameContractNo(final I_C_Order order)
	{
		final OrderId frameAgreementOrderId = OrderId.ofRepoIdOrNull(order.getC_FrameAgreement_Order_ID());
		if (frameAgreementOrderId == null)
		{
			return null;
		}

		return orderBL.getById(frameAgreementOrderId).getDocumentNo();
	}

	private SourceBPartnerInfo getBPartnerInfo(final I_C_Order order)
	{
		return OrderDocumentLocationAdapterFactory.locationAdapter(order)
				.toPlainDocumentLocation(documentLocationBL)
				.map(this::toBPartnerInfo)
				.orElseThrow(() -> new AdempiereException("Failed extracting document location from " + order));
	}

	private SourceOrderLine toSourceOrderLine(final I_C_OrderLine orderLine)
	{
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final I_M_Product product = productBL.getById(productId);

		return SourceOrderLine.builder()
				.id(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()))
				.lineNo(orderLine.getLine())
				.productId(productId)
				.productCode(product.getValue())
				.productName(product.getName())
				.qtyEntered(orderBL.getQtyEntered(orderLine))
				.build();
	}

	private SourceOrderCost toSourceOrderCost(final OrderCost orderCost)
	{
		return SourceOrderCost.builder()
				.costTypeName(orderCostService.getCostTypeById(orderCost.getCostTypeId()).getName())
				.costAmountFC(orderCost.getCostAmount().toAmount(moneyService::getCurrencyCodeByCurrencyId))
				.vendor(orderCost.getBpartnerId() != null ? prepareBPartnerInfo(orderCost.getBpartnerId()).build() : null)
				.basedOnOrderLineIds(orderCost.getDetails()
						.stream()
						.map(OrderCostDetail::getOrderLineId)
						.collect(ImmutableSet.toImmutableSet()))
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
		final I_C_Invoice invoice = invoiceBL.getById(invoiceId);
		final I_C_Invoice salesInvoice;
		if (invoice.isSOTrx())
		{
			salesInvoice = invoice;
		}
		else
		{
			final OrderId purchaseOrderId = OrderId.ofRepoIdOrNull(invoice.getC_Order_ID());
			if (purchaseOrderId == null)
			{
				throw new AdempiereException("Cannot determine purchase order");
			}

			//
			// Determine Sales Order
			final Set<OrderId> salesOrderIds = orderBL.getSalesOrderIdsByPurchaseOrderId(purchaseOrderId);
			if (salesOrderIds.isEmpty())
			{
				throw new AdempiereException("No Sales Order found");
			}
			else if (salesOrderIds.size() != 1)
			{
				throw new AdempiereException("More than one Sales Order found");
			}
			final OrderId salesOrderId = salesOrderIds.iterator().next();

			//
			// Determine Sales Invoice
			final List<? extends I_C_Invoice> salesInvoices = invoiceBL.getByOrderId(salesOrderId);
			if (salesInvoices.isEmpty())
			{
				throw new AdempiereException("No Sales Invoice found for " + salesOrderId);
			}
			else if (salesInvoices.size() != 1)
			{
				throw new AdempiereException("More than one Sales Invoice found for " + salesOrderId);
			}
			salesInvoice = salesInvoices.iterator().next();
		}

		//
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
				.dueDate(LocalDateAndOrgId.ofNullableTimestamp(invoice.getDueDate(), orgId, orgDAO::getTimeZone))
				.descriptionBottom(StringUtils.trimBlankToNull(invoice.getDescriptionBottom()))
				.linesNetAmtFC(Amount.of(invoice.getTotalLines(), currencyCode))
				.taxAmtFC(Amount.of(invoice.getGrandTotal().subtract(invoice.getTotalLines()), currencyCode))
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
		final CurrencyConversionContext conversionCtx = invoiceBL.getCurrencyConversionCtx(invoice);
		final CurrencyId invoiceCurrencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());

		final BigDecimal currencyRate;
		final CurrencyCode localCurrency;
		final CurrencyCode foreignCurrency;
		final String forexContractNo;

		final ForexContractRef forexContractRef = InvoiceDAO.extractForeignContractRef(invoice);
		if (forexContractRef == null)
		{
			forexContractNo = null;

			final CurrencyId localCurrencyId = acctSchemaBL.getAcctCurrencyId(ClientId.ofRepoId(invoice.getAD_Client_ID()), OrgId.ofRepoId(invoice.getAD_Org_ID()));
			localCurrency = moneyService.getCurrencyCodeByCurrencyId(localCurrencyId);
			foreignCurrency = moneyService.getCurrencyCodeByCurrencyId(invoiceCurrencyId);

			currencyRate = moneyService.getCurrencyRate(foreignCurrency, localCurrency, conversionCtx).getConversionRate();
		}
		else
		{
			if (CurrencyId.equals(forexContractRef.getOrderCurrencyId(), invoiceCurrencyId))
			{
				throw new AdempiereException("FEC contract and invoice currency does not match")
						.appendParametersToMessage()
						.setParameter("forexContractRef", forexContractRef)
						.setParameter("invoiceCurrencyId", invoiceCurrencyId);
			}

			final ForexContractId forexContractId = forexContractRef.getForexContractId();
			forexContractNo = forexContractId != null
					? forexContractService.getById(forexContractId).getDocumentNo()
					: null;

			final CurrencyId foreignCurrencyId = forexContractRef.getForeignCurrencyId();
			foreignCurrency = moneyService.getCurrencyCodeByCurrencyId(foreignCurrencyId);
			final CurrencyId localCurrencyId = forexContractRef.getLocalCurrencyId();
			localCurrency = moneyService.getCurrencyCodeByCurrencyId(localCurrencyId);
			currencyRate = forexContractRef.getCurrencyRate(foreignCurrencyId, localCurrencyId);
		}

		return SourceCurrencyInfo.builder()
				.currencyRate(currencyRate)
				.foreignCurrencyCode(foreignCurrency)
				.localCurrencyCode(localCurrency)
				.fecDocumentNo(forexContractNo)
				.conversionCtx(conversionCtx)
				.build();
	}

	private ImmutableList<SourceShipment> getSourceShipments(final OrderId salesOrderId)
	{
		return inOutBL.getByOrderId(salesOrderId)
				.stream()
				.map(this::toSourceShipment)
				.collect(ImmutableList.toImmutableList());
	}

	private SourceShipment toSourceShipment(final I_M_InOut shipment)
	{
		final OrgId orgId = OrgId.ofRepoId(shipment.getAD_Org_ID());

		return SourceShipment.builder()
				.orgId(orgId)
				.documentNo(shipment.getDocumentNo())
				.movementDate(LocalDateAndOrgId.ofTimestamp(shipment.getMovementDate(), orgId, orgDAO::getTimeZone))
				.shipFrom(getWarehouseInfo(WarehouseId.ofRepoId(shipment.getM_Warehouse_ID())))
				.shipTo(getShipTo(shipment))
				.incoterms(extractIncotermsAndLocation(shipment))
				.shipper(extractShipperIdAndName(shipment))
				.build();
	}

	private SourceWarehouseInfo getWarehouseInfo(final WarehouseId warehouseId)
	{
		final I_M_Warehouse warehouse = warehouseBL.getById(warehouseId);

		return SourceWarehouseInfo.builder()
				.warehouseId(warehouseId)
				.warehouseCode(warehouse.getValue())
				.warehouseName(warehouse.getName())
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
	private SourceShipperInfo extractShipperIdAndName(final I_M_InOut shipment)
	{
		final ShipperId shipperId = ShipperId.ofRepoIdOrNull(shipment.getM_Shipper_ID());
		if (shipperId != null)
		{
			return SourceShipperInfo.builder()
					.shipperId(shipperId)
					.name(shipperDAO.getShipperName(shipperId))
					.build();
		}

		// TODO get the M_MeansOfTransportation_ID (via M_Delivery_Planning.M_MeansOfTransportation_ID? ask Ruxi)

		return null;
	}

	private SourceBPartnerInfo getShipTo(final I_M_InOut shipment)
	{
		final DocumentDeliveryLocationAdapter deliveryLocationAdapter = InOutDocumentLocationAdapterFactory.deliveryLocationAdapter(shipment);
		final DocumentLocation documentLocation;
		if (deliveryLocationAdapter.isDropShip())
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

		return toBPartnerInfo(documentLocation);
	}

	private SourceBPartnerInfo toBPartnerInfo(final DocumentLocation documentLocation)
	{
		final LocationId locationId = documentLocation.getLocationId();
		if (locationId == null)
		{
			throw new AdempiereException("No location set for " + documentLocation);
		}

		return prepareBPartnerInfo(documentLocation.getBpartnerId())
				.countryCode(locationBL.getCountryCodeByLocationId(locationId))
				.build();
	}

	private SourceBPartnerInfo.SourceBPartnerInfoBuilder prepareBPartnerInfo(final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = bpartnerBL.getById(bpartnerId);
		return SourceBPartnerInfo.builder()
				.code(bpartner.getValue())
				.name(bpartner.getName())
				.vatId(bpartner.getVATaxID());
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
}
