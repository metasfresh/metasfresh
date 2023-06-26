package de.metas.gplr.source;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
public class GPLRSourceDocumentsService
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
	@NonNull private final SectionCodeService sectionCodeService;
	@NonNull private final IDocumentLocationBL documentLocationBL;
	@NonNull private final MoneyService moneyService;
	@NonNull private final OrderCostService orderCostService;
	@NonNull private final IncotermsRepository incotermsRepository;

	public GPLRSourceDocumentsService(
			@NonNull final SectionCodeService sectionCodeService,
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final MoneyService moneyService,
			@NonNull final OrderCostService orderCostService,
			@NonNull final IncotermsRepository incotermsRepository)
	{
		this.sectionCodeService = sectionCodeService;
		this.documentLocationBL = documentLocationBL;
		this.moneyService = moneyService;
		this.orderCostService = orderCostService;
		this.incotermsRepository = incotermsRepository;
	}

	public GPLRSourceDocuments getByInvoiceId(final InvoiceId invoiceId)
	{
		final SourceInvoice salesInvoice = getSalesInvoice(invoiceId);
		final OrderId salesOrderId = salesInvoice.getOrderId();
		final I_C_Order salesOrder = orderBL.getById(salesOrderId);
		final Set<OrderId> purchaseOrderIds = orderBL.getPurchaseOrderIdsBySalesOrderId(salesOrderId);
		final List<I_C_Order> purchaseOrders = orderBL.getByIds(purchaseOrderIds);

		final HashSet<OrderId> allOrderIds = new HashSet<>();
		allOrderIds.add(salesOrderId);
		allOrderIds.addAll(purchaseOrderIds);

		final ImmutableListMultimap<OrderId, SourceOrderLine> linesByOrderId =
				orderBL.getLinesByOrderIds(allOrderIds)
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
					.sectionCode(SectionCodeId.optionalOfRepoId(order.getM_SectionCode_ID()).map(sectionCodeService::getById))
					.currencyCode(moneyService.getCurrencyCodeByCurrencyId(CurrencyId.ofRepoId(order.getC_Currency_ID())))
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

		return GPLRSourceDocuments.builder()
				.salesInvoice(salesInvoice)
				.salesOrder(toOrderAndLines.apply(salesOrder))
				.shipments(getSourceShipments(salesOrderId))
				.purchaseOrders(purchaseOrders.stream()
						.map(toOrderAndLines)
						.collect(ImmutableList.toImmutableList()))
				.build();
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
				.costAmount(orderCost.getCostAmount().toAmount(moneyService::getCurrencyCodeByCurrencyId))
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
		if (invoice.isSOTrx())
		{
			return toSourceInvoice(invoice);
		}
		else
		{
			throw new UnsupportedOperationException("starting from purchase invoice not yet implemented"); // TODO
		}
	}

	private SourceInvoice toSourceInvoice(@NonNull final I_C_Invoice invoice)
	{
		final OrgId orgId = OrgId.ofRepoId(invoice.getAD_Org_ID());
		final CurrencyId currencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());
		final CurrencyCode currencyCode = moneyService.getCurrencyCodeByCurrencyId(currencyId);

		return SourceInvoice.builder()
				.id(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.orderId(OrderId.ofRepoId(invoice.getC_Order_ID()))
				.orgId(orgId)
				.documentNo(invoice.getDocumentNo())
				.docTypeName(docTypeBL.getById(DocTypeId.ofRepoId(invoice.getC_DocType_ID())).getName())
				.forexContractRef(InvoiceDAO.extractForeignContractRef(invoice))
				.currencyConversionCtx(invoiceBL.getCurrencyConversionCtx(invoice))
				.createdBy(getUserInfo(invoice.getCreatedBy()))
				.created(LocalDateAndOrgId.ofTimestamp(invoice.getCreated(), orgId, orgDAO::getTimeZone))
				.dateInvoiced(LocalDateAndOrgId.ofTimestamp(invoice.getDateInvoiced(), orgId, orgDAO::getTimeZone))
				.paymentTerm(paymentTermRepository.getById(PaymentTermId.ofRepoId(invoice.getC_PaymentTerm_ID())))
				.dueDate(LocalDateAndOrgId.ofNullableTimestamp(invoice.getDueDate(), orgId, orgDAO::getTimeZone))
				.descriptionBottom(StringUtils.trimBlankToNull(invoice.getDescriptionBottom()))
				.linesNetAmt(Amount.of(invoice.getTotalLines(), currencyCode))
				.taxAmt(Amount.of(invoice.getGrandTotal().subtract(invoice.getTotalLines()), currencyCode)) // TODO shall we consider reverse charge taxes too? in that case we shall sum up the ReverseChargeTaxAmt and TaxAmt from C_Invoice_Tax
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
