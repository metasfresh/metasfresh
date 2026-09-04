package de.metas.deliveryplanning.webui.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningReceiptInfo;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.DeliveryPlanningShipmentInfo;
import de.metas.deliveryplanning.receipt.CreateReceiptFromReceiptScheduleRequest;
import de.metas.deliveryplanning.receipt.CreateReceiptFromReceiptScheduleResult;
import de.metas.deliveryplanning.receipt.ReceiptFromReceiptScheduleService;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsRequest;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.QtyToDeliverMap;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.order.DeliveryRule;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_ShipmentSchedule;

import static de.metas.deliveryplanning.DeliveryPlanningService.MSG_M_Delivery_Planning_BlockedPartner;
import static de.metas.deliveryplanning.DeliveryPlanningService.MSG_M_Delivery_Planning_Closed;
import static de.metas.deliveryplanning.DeliveryPlanningService.MSG_M_Delivery_Planning_PurchaseOrderFullyDelivered;
import static de.metas.deliveryplanning.DeliveryPlanningService.MSG_M_Delivery_Planning_SalesOrderFullyDelivered;

/**
 * Package-private and deliberately NOT final: the same-package generate processes hold it in a
 * package-visible {@code helper} field that their unit tests replace with a stub, so that only the
 * process' own {@code doIt()} logic is exercised and not the heavy production generation chain
 * (async batch + {@code ShipmentService} + real HU allocation). Subclassing outside this package is
 * impossible anyway, package-private being the actual encapsulation boundary here.
 */
class DeliveryPlanningGenerateProcessesHelper
{
	public static DeliveryPlanningGenerateProcessesHelper newInstance()
	{
		return DeliveryPlanningGenerateProcessesHelper.builder()
				.deliveryPlanningService(SpringContextHolder.instance.getBean(DeliveryPlanningService.class))
				.shipmentService(SpringContextHolder.instance.getBean(ShipmentService.class))
				.orderBL(Services.get(IOrderBL.class))
				.huReceiptScheduleBL(Services.get(IHUReceiptScheduleBL.class))
				.shipmentScheduleBL(Services.get(IShipmentScheduleBL.class))
				.shipmentScheduleEffectiveBL(Services.get(IShipmentScheduleEffectiveBL.class))
				.inOutDAO(Services.get(IInOutDAO.class))
				.asyncBatchBL(Services.get(IAsyncBatchBL.class))
				.msgBL(Services.get(IMsgBL.class))
				.sysConfigBL(Services.get(ISysConfigBL.class))
				.receiptFromReceiptScheduleService(SpringContextHolder.instance.getBean(ReceiptFromReceiptScheduleService.class))
				.build();
	}

	/**
	 * Mandatory-and-positive guard for a process quantity parameter: returns the value if it is non-null and
	 * strictly positive, else throws {@link FillMandatoryException} for {@code parameterName} (same contract the
	 * removed {@code FillMandatoryException.assertPositive} had).
	 */
	@NonNull
	static BigDecimal assumePositive(@Nullable final BigDecimal value, @NonNull final String parameterName)
	{
		if (value != null && value.signum() > 0)
		{
			return value;
		}
		throw new FillMandatoryException(parameterName);
	}

	private final DeliveryPlanningService deliveryPlanningService;
	private final ShipmentService shipmentService;
	private final IOrderBL orderBL;
	private final IHUReceiptScheduleBL huReceiptScheduleBL;
	private final IShipmentScheduleBL shipmentScheduleBL;
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL;
	private final IInOutDAO inOutDAO;
	private final IAsyncBatchBL asyncBatchBL;
	private final IMsgBL msgBL;
	private final ISysConfigBL sysConfigBL;

	/**
	 * The ONE receive path - shared with the receipt-logistics window's actions, which receive the very same
	 * schedules from a grid that unions planned and unplanned rows. See
	 * {@link CreateReceiptFromReceiptScheduleRequest} for why the planning id must travel inside the request.
	 */
	private final ReceiptFromReceiptScheduleService receiptFromReceiptScheduleService;

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private final HashMap<DeliveryPlanningId, Optional<DeliveryPlanningReceiptInfo>> receiptInfos = new HashMap<>();
	private final HashMap<DeliveryPlanningId, Optional<DeliveryPlanningShipmentInfo>> shipmentInfos = new HashMap<>();

	private final HashMap<OrderAndLineId, Optional<DeliveryPlanningShipmentInfo>> shipmentInfosByPurchaseOrderLineId = new HashMap<>();

	/** cached lot-number drawn once from the doc-type sequence for this generate run */
	private Optional<String> lotNumberFromSeq = Optional.empty();

	private static final AdMessageKey MSG_ERROR_GOODS_ISSUE_QUANTITY = AdMessageKey.of("GoodsIssueQuantityParameterError");
	private static final AdMessageKey MESSAGE_ClearanceStatusInfo_Receipt = AdMessageKey.of("ClearanceStatusInfo.Receipt");
	private static final String SYSCONFIG_PREVENT_RECEIPT_IF_MISSING_DELIVERY_INSTRUCTIONS = "de.metas.deliveryplanning.webui.process.PreventReceiptIfMissingDeliveryInstructions";

	@Builder
	private DeliveryPlanningGenerateProcessesHelper(
			@NonNull final DeliveryPlanningService deliveryPlanningService,
			@NonNull final ShipmentService shipmentService,
			@NonNull final IOrderBL orderBL,
			@NonNull final IHUReceiptScheduleBL huReceiptScheduleBL,
			@NonNull final IShipmentScheduleBL shipmentScheduleBL,
			@NonNull final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL,
			@NonNull final IInOutDAO inOutDAO,
			@NonNull final IAsyncBatchBL asyncBatchBL,
			@NonNull final IMsgBL msgBL,
			@NonNull final ISysConfigBL sysConfigBL,
			@NonNull final ReceiptFromReceiptScheduleService receiptFromReceiptScheduleService)
	{
		this.deliveryPlanningService = deliveryPlanningService;
		this.shipmentService = shipmentService;
		this.orderBL = orderBL;
		this.huReceiptScheduleBL = huReceiptScheduleBL;
		this.shipmentScheduleBL = shipmentScheduleBL;
		this.shipmentScheduleEffectiveBL = shipmentScheduleEffectiveBL;
		this.inOutDAO = inOutDAO;
		this.asyncBatchBL = asyncBatchBL;
		this.msgBL = msgBL;
		this.sysConfigBL = sysConfigBL;
		this.receiptFromReceiptScheduleService = receiptFromReceiptScheduleService;
	}

	/**
	 * Invariant: every caller has already established that this planning HAS a receipt (its direction is
	 * Incoming or Dropship), so a miss is a programmer error.
	 */
	public DeliveryPlanningReceiptInfo getReceiptInfo(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return Check.assumeNotEmpty(getReceiptInfoIfHasReceipt(deliveryPlanningId),
				"Expected {} to have a receipt", deliveryPlanningId);
	}

	public Optional<DeliveryPlanningReceiptInfo> getReceiptInfoIfHasReceipt(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return receiptInfos.computeIfAbsent(deliveryPlanningId, deliveryPlanningService::getReceiptInfoIfHasReceipt);
	}

	/** The shipment twin of {@link #getReceiptInfo(DeliveryPlanningId)} - same invariant, same reasoning. */
	public DeliveryPlanningShipmentInfo getShipmentInfo(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return Check.assumeNotEmpty(getShipmentInfoIfOutgoingType(deliveryPlanningId),
				"Expected {} to be an outgoing delivery planning", deliveryPlanningId);
	}

	public Optional<DeliveryPlanningShipmentInfo> getShipmentInfoIfOutgoingType(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return shipmentInfos.computeIfAbsent(deliveryPlanningId, deliveryPlanningService::getShipmentInfoIfOutgoingType);
	}

	public Optional<DeliveryPlanningShipmentInfo> getB2BShipmentInfo(@NonNull final DeliveryPlanningReceiptInfo receiptInfo)
	{
		if (!receiptInfo.isDropship())
		{
			return Optional.empty();
		}

		final OrderAndLineId purchaseOrderAndLineId = receiptInfo.getPurchaseOrderAndLineId();
		if (purchaseOrderAndLineId == null)
		{
			return Optional.empty();
		}

		return shipmentInfosByPurchaseOrderLineId.computeIfAbsent(purchaseOrderAndLineId, this::retrieveSingleB2BShipmentInfoByPurchaseOrderLineId);
	}

	@NonNull
	private Optional<DeliveryPlanningShipmentInfo> retrieveSingleB2BShipmentInfoByPurchaseOrderLineId(@NonNull final OrderAndLineId purchaseOrderAndLineId)
	{
		final Set<OrderAndLineId> salesOrderLineIds = orderDAO.retrieveSOLineIdsByPOLineId(purchaseOrderAndLineId.getOrderLineId())
				.stream()
				.map(soLineId -> OrderAndLineId.ofRepoIds(orderDAO.getOrderLineById(soLineId).getC_Order_ID(), soLineId.getRepoId()))
				.collect(ImmutableSet.toImmutableSet());
		if (salesOrderLineIds.isEmpty())
		{
			return Optional.empty();
		}

		final List<DeliveryPlanningShipmentInfo> shipmentInfos = deliveryPlanningService.getShipmentInfosByOrderLineIds(salesOrderLineIds)
				.stream()
				.filter(shipmentInfo -> checkEligibleToCreateShipment(shipmentInfo).isAccepted())
				.collect(ImmutableList.toImmutableList());
		if (shipmentInfos.size() != 1)
		{
			return Optional.empty();
		}

		return Optional.of(shipmentInfos.get(0));
	}

	public ProcessPreconditionsResolution checkEligibleToCreateShipment(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		if (deliveryPlanningService.isClosed(deliveryPlanningId))
		{
			return ProcessPreconditionsResolution.reject(MSG_M_Delivery_Planning_Closed, deliveryPlanningId.getRepoId());
		}

		final Optional<DeliveryPlanningShipmentInfo> optionalShipmentInfo = getShipmentInfoIfOutgoingType(deliveryPlanningId);
		if (!optionalShipmentInfo.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not an outgoing delivery planning");
		}

		return checkEligibleToCreateShipment(optionalShipmentInfo.get());
	}

	private ProcessPreconditionsResolution checkEligibleToCreateShipment(@NonNull final DeliveryPlanningShipmentInfo shipmentInfo)
	{
		final boolean existsBlockedPartnerDeliveryPlannings = deliveryPlanningService.hasBlockedBPartner(shipmentInfo.getDeliveryPlanningId());
		if (existsBlockedPartnerDeliveryPlannings)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_M_Delivery_Planning_BlockedPartner));
		}

		if (shipmentScheduleBL.getById(shipmentInfo.getShipmentScheduleId()).isProcessed())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_M_Delivery_Planning_SalesOrderFullyDelivered));
		}
		if (shipmentInfo.isShipped())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Already shipped");
		}
		if (shipmentInfo.getSalesOrderId() == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not an order based delivery planning");
		}

		if (!deliveryPlanningService.hasCompleteDeliveryInstruction(shipmentInfo.getDeliveryPlanningId()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No completed delivery instruction");
		}

		return ProcessPreconditionsResolution.accept();
	}

	public ProcessPreconditionsResolution checkEligibleToCreateReceipt(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		if (deliveryPlanningService.isClosed(deliveryPlanningId))
		{
			return ProcessPreconditionsResolution.reject(MSG_M_Delivery_Planning_Closed, deliveryPlanningId.getRepoId());
		}

		final Optional<DeliveryPlanningReceiptInfo> optionalDeliveryPlanningReceipt = getReceiptInfoIfHasReceipt(deliveryPlanningId);
		if (!optionalDeliveryPlanningReceipt.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("The delivery planning has no receipt");
		}

		return checkEligibleToCreateReceipt(optionalDeliveryPlanningReceipt.get());
	}

	private ProcessPreconditionsResolution checkEligibleToCreateReceipt(@NonNull final DeliveryPlanningReceiptInfo receiptInfo)
	{
		final DeliveryPlanningId deliveryPlanningId = receiptInfo.getDeliveryPlanningId();
		final boolean existsBlockedPartnerDeliveryPlannings = deliveryPlanningService.hasBlockedBPartner(deliveryPlanningId);
		if (existsBlockedPartnerDeliveryPlannings)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_M_Delivery_Planning_BlockedPartner));
		}

		if (huReceiptScheduleBL.getById(receiptInfo.getReceiptScheduleId()).isProcessed())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_M_Delivery_Planning_PurchaseOrderFullyDelivered));
		}
		if (receiptInfo.isReceived())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Already received");
		}
		if (receiptInfo.getPurchaseOrderId() == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not an order based delivery planning");
		}

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(Env.getClientId(), receiptInfo.getOrgId());

		final boolean preventReceiptIfMissingDeliveryInstructions = sysConfigBL.getBooleanValue(SYSCONFIG_PREVENT_RECEIPT_IF_MISSING_DELIVERY_INSTRUCTIONS, false, clientAndOrgId);
		if (preventReceiptIfMissingDeliveryInstructions && !deliveryPlanningService.hasCompleteDeliveryInstruction(deliveryPlanningId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No completed delivery instruction");
		}

		return ProcessPreconditionsResolution.accept();
	}

	/**
	 * Write-back for {@code M_Delivery_Planning_GenerateReceipt#doIt()}: routes the qty override onto the
	 * planning's {@code PlannedDischargeQuantity} through the service, so the process never reaches for the
	 * repository directly (one collaborator per aggregate per class - service-injection.md).
	 */
	public void writeBackPlannedDischargeQuantity(@NonNull final DeliveryPlanningId deliveryPlanningId, @NonNull final Quantity quantity)
	{
		deliveryPlanningService.setPlannedDischargeQuantity(deliveryPlanningId, quantity);
	}

	/** The load-side sibling of {@link #writeBackPlannedDischargeQuantity}, used by {@code M_Delivery_Planning_GenerateShipment#doIt()}. */
	public void writeBackPlannedLoadedQuantity(@NonNull final DeliveryPlanningId deliveryPlanningId, @NonNull final Quantity quantity)
	{
		deliveryPlanningService.setPlannedLoadedQuantity(deliveryPlanningId, quantity);
	}

	public void generateShipment(final DeliveryPlanningGenerateShipmentRequest request)
	{
		final DeliveryPlanningId deliveryPlanningId = request.getDeliveryPlanningId();
		deliveryPlanningService.validateDeliveryPlanning(deliveryPlanningId);

		final DeliveryPlanningShipmentInfo shipmentInfo = deliveryPlanningService.getShipmentInfo(deliveryPlanningId);
		if (shipmentInfo.isShipped())
		{
			throw new AdempiereException("Already shipped");
		}

		final ShipmentScheduleId shipmentScheduleId = shipmentInfo.getShipmentScheduleId();
		final BigDecimal qtyOnHand = getQtyOnHandByShipmentScheduleId(shipmentScheduleId).toBigDecimal();

		final DeliveryRule deliveryRule = getDeliveryRuleByShipmentScheduleId(shipmentScheduleId);
		final InOutId b2bReceiptId = request.getB2bReceiptId();
		if (request.getQtyToShipBD().compareTo(qtyOnHand) > 0 && !deliveryRule.isForce() && b2bReceiptId == null)
		{
			throw new AdempiereException(MSG_ERROR_GOODS_ISSUE_QUANTITY);
		}

		final ProductId productId = getProductIdByShipmentScheduleId(shipmentScheduleId);
		final StockQtyAndUOMQty qtyToShip = StockQtyAndUOMQtys.ofQtyInStockUOM(request.getQtyToShipBD(), productId);

		//
		// Generate the shipment via the standard ShipmentService. M_Delivery_Planning_ID travels WITH the
		// request (GenerateShipmentsRequest#deliveryPlanningId) so the shipment carries it while still a
		// draft: the shipment is completed inside the generation workpackage, and interceptor/M_InOut
		// #afterComplete - which derives the planning's delivered state, its actual quantities, its
		// Processed flag and the shipment back-link - only fires when the FK is already set at that moment.
		// Only the B2B receipt<->shipment link is still done after generation; nothing reads it during
		// generation.
		//
		// A caller-supplied partial qty must be shipped, so we use the qtysToDeliverOverride-capable
		// ShipmentService.generateShipments(...) path with waitForShipments=true (synchronous: the async batch
		// blocks until the work packages are processed), then resolve the created shipment(s) from the schedule.
		//
		// Honor the DP DeliveryDate as the shipment movement date: this generation path is enqueued with
		// isShipDateToday=false, so the WP processor's CalculateShippingDateRule is NONE and
		// InOutProducerFromShipmentScheduleWithHU.calculateShipmentDate(schedule, NONE) dates the shipment to
		// IShipmentScheduleEffectiveBL.getDeliveryDate(schedule) = coalesce(DeliveryDate_Override, DeliveryDate).
		// So we pin the schedule's DeliveryDate_Override to the DP DeliveryDate before generation; this is the
		// branch-supported way to choose the shipment date decoupled, with no shared shipment-chain change.
		setDeliveryDateOverrideOnSchedule(shipmentScheduleId, request.getDeliveryDate());

		final AsyncBatchId asyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_ShipmentSchedule);
		final ShipmentScheduleAndJobScheduleIdSet scheduleIds = ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(ImmutableSet.of(shipmentScheduleId));

		shipmentService.generateShipments(GenerateShipmentsRequest.builder()
				.asyncBatchId(asyncBatchId)
				.scheduleIds(scheduleIds)
				.scheduleToExternalInfo(com.google.common.collect.ImmutableMap.of())
				.scheduleToQuantityToDeliverOverride(QtyToDeliverMap.of(shipmentScheduleId, qtyToShip))
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				.isCompleteShipment(Boolean.TRUE)
				.waitForShipments(true)
				.deliveryPlanningId(deliveryPlanningId.getRepoId())
				.build());

		final Set<InOutId> shipmentIds = shipmentService.retrieveInOutIdsByScheduleIds(ImmutableSet.of(shipmentScheduleId));
		if (shipmentIds.isEmpty())
		{
			throw new AdempiereException("Could not ship")
					.setParameter("shipmentScheduleId", shipmentScheduleId);
		}

		linkB2BReceiptToShipments(shipmentIds, b2bReceiptId);
	}

	/**
	 * Sets the bidirectional B2B receipt&lt;-&gt;shipment link ({@code B2B_InOut_ID} on both sides) after
	 * generation. Unlike {@code M_Delivery_Planning_ID} — which has to be on the draft before completion and
	 * therefore travels with {@link GenerateShipmentsRequest} — nothing reads {@code B2B_InOut_ID} during
	 * generation or on completion, so setting it here is faithful.
	 */
	private void linkB2BReceiptToShipments(
			@NonNull final Set<InOutId> shipmentIds,
			@javax.annotation.Nullable final InOutId b2bReceiptId)
	{
		if (b2bReceiptId == null)
		{
			return;
		}

		final I_M_InOut b2bReceipt = inOutDAO.getById(b2bReceiptId, I_M_InOut.class);

		for (final InOutId shipmentId : shipmentIds)
		{
			final I_M_InOut shipment = inOutDAO.getById(shipmentId, I_M_InOut.class);
			shipment.setB2B_InOut_ID(b2bReceipt.getM_InOut_ID());
			InterfaceWrapperHelper.save(shipment);

			// back-link the receipt to the (single) B2B shipment, mirroring the coupled generation path
			b2bReceipt.setB2B_InOut_ID(shipment.getM_InOut_ID());
			InterfaceWrapperHelper.save(b2bReceipt);
		}
	}

	public BigDecimal getQtyToDeliverByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return shipmentScheduleEffectiveBL.getQtyToDeliverBD(shipmentScheduleBL.getById(shipmentScheduleId));
	}

	public Quantity getQtyOnHandByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return shipmentScheduleEffectiveBL.getQtyOnHand(shipmentScheduleBL.getById(shipmentScheduleId));
	}

	private DeliveryRule getDeliveryRuleByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return shipmentScheduleEffectiveBL.getDeliveryRule(shipmentScheduleBL.getById(shipmentScheduleId));
	}

	private ProductId getProductIdByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getById(shipmentScheduleId);
		return ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
	}

	/**
	 * Pins the DP {@code DeliveryDate} onto the shipment schedule's {@code DeliveryDate_Override} so the
	 * subsequent shipment generation (enqueued with {@code isShipDateToday=false} → shipping-date rule NONE)
	 * dates the shipment to it — see {@code InOutProducerFromShipmentScheduleWithHU.calculateShipmentDate} +
	 * {@code IShipmentScheduleEffectiveBL.getDeliveryDate} (coalesce override-then-plain).
	 */
	private void setDeliveryDateOverrideOnSchedule(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final LocalDate deliveryDate)
	{
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getById(shipmentScheduleId);
		shipmentSchedule.setDeliveryDate_Override(TimeUtil.asTimestamp(deliveryDate));
		InterfaceWrapperHelper.save(shipmentSchedule);
	}

	public DeliveryPlanningGenerateReceiptResult generateReceipt(final DeliveryPlanningGenerateReceiptRequest request)
	{
		final DeliveryPlanningId deliveryPlanningId = request.getDeliveryPlanningId();
		deliveryPlanningService.validateDeliveryPlanning(deliveryPlanningId);

		final DeliveryPlanningReceiptInfo receiptInfo = getReceiptInfo(deliveryPlanningId);
		if (receiptInfo.isReceived())
		{
			throw new AdempiereException("Already received");
		}

		final ReceiptScheduleId receiptScheduleId = receiptInfo.getReceiptScheduleId();
		final I_M_ReceiptSchedule receiptSchedule = huReceiptScheduleBL.getById(receiptScheduleId);
		final Quantity qtyToReceive = Quantitys.of(request.getQtyToReceiveBD(), UomId.ofRepoId(receiptSchedule.getC_UOM_ID()));

		final HuId vhuId = receiptFromReceiptScheduleService.createPlanningVHU(receiptScheduleId, qtyToReceive);
		if (vhuId == null)
		{
			throw new AdempiereException("Failed receiving"); // shall not happen
		}

		// The planning id travels WITH the request rather than being written onto the finished receipt: the
		// service COMPLETES the receipt before returning, and interceptor/M_InOut#afterComplete - which derives
		// the planning's delivered state, its actual discharge quantity, its Processed flag and the receipt
		// back-link - only fires when the FK is already set at that moment.
		final CreateReceiptFromReceiptScheduleResult result = receiptFromReceiptScheduleService.createReceipt(
				CreateReceiptFromReceiptScheduleRequest.builder()
						.receiptScheduleId(receiptScheduleId)
						.deliveryPlanningId(deliveryPlanningId)
						.huIdsToReceive(ImmutableSet.of(vhuId))
						.movementDateRule(ReceiptMovementDateRule.fixedDate(request.getReceiptDate()))
						.build());

		return DeliveryPlanningGenerateReceiptResult.builder()
				.receiptId(result.getReceiptId())
				.receivedVHUId(vhuId)
				.productId(result.getProductId())
				.qty(qtyToReceive)
				.build();
	}
}
