package de.metas.deliveryplanning.webui.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.time.SystemTime;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningReceiptInfo;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.DeliveryPlanningShipmentInfo;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.handlingunits.ClearanceStatus;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.HUAttributeUpdateRequest;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsRequest;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.QtyToDeliverMap;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.order.DeliveryRule;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.InstantAndOrgId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductDAO;
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
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.mm.attributes.api.LotNoContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Product;
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
import static de.metas.deliveryplanning.DeliveryPlanningService.MSG_M_Delivery_Planning_PurchaseOrderFullyDelivered;
import static de.metas.deliveryplanning.DeliveryPlanningService.MSG_M_Delivery_Planning_SalesOrderFullyDelivered;

final class DeliveryPlanningGenerateProcessesHelper
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

	// stateless singletons used by the planning-VHU attribute copy (mirrors ReceiptScheduleBasedProcess)
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final ILotNumberBL lotNumberBL = Services.get(ILotNumberBL.class);
	private final IBPartnerOrgBL partnerOrgBL = Services.get(IBPartnerOrgBL.class);
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
			@NonNull final ISysConfigBL sysConfigBL)
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
	}

	public DeliveryPlanningReceiptInfo getReceiptInfo(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return getReceiptInfoIfHasReceipt(deliveryPlanningId)
				.orElseThrow(() -> new AdempiereException("Expected the delivery planning to have a receipt"));
	}

	public Optional<DeliveryPlanningReceiptInfo> getReceiptInfoIfHasReceipt(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return receiptInfos.computeIfAbsent(deliveryPlanningId, deliveryPlanningService::getReceiptInfoIfHasReceipt);
	}

	public DeliveryPlanningShipmentInfo getShipmentInfo(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return getShipmentInfoIfOutgoingType(deliveryPlanningId)
				.orElseThrow(() -> new AdempiereException("Expected to be an outgoing delivery planning"));
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
		// Generate the shipment via the standard ShipmentService (unchanged shared shipment chain), then set
		// M_Delivery_Planning_ID + the B2B receipt<->shipment link AFTER generation. Nothing in shipment
		// generation reads M_Delivery_Planning_ID; it is only a back-link + a report source, so a
		// post-generation stamp is behaviorally faithful.
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
				.build());

		final Set<InOutId> shipmentIds = shipmentService.retrieveInOutIdsByScheduleIds(ImmutableSet.of(shipmentScheduleId));
		if (shipmentIds.isEmpty())
		{
			throw new AdempiereException("Could not ship")
					.setParameter("shipmentScheduleId", shipmentScheduleId);
		}

		linkShipmentsToDeliveryPlanning(shipmentIds, deliveryPlanningId, b2bReceiptId);
	}

	/**
	 * Post-generation linkage. Stamps {@code M_Delivery_Planning_ID} on each generated shipment and, for the
	 * B2B flow, sets the bidirectional receipt<->shipment link ({@code B2B_InOut_ID} on both sides) — done here
	 * (after generation) so the shared shipment chain stays untouched.
	 */
	private void linkShipmentsToDeliveryPlanning(
			@NonNull final Set<InOutId> shipmentIds,
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@javax.annotation.Nullable final InOutId b2bReceiptId)
	{
		final I_M_InOut b2bReceipt = b2bReceiptId != null ? inOutDAO.getById(b2bReceiptId, I_M_InOut.class) : null;

		for (final InOutId shipmentId : shipmentIds)
		{
			final I_M_InOut shipment = inOutDAO.getById(shipmentId, I_M_InOut.class);
			shipment.setM_Delivery_Planning_ID(deliveryPlanningId.getRepoId());

			if (b2bReceipt != null)
			{
				shipment.setB2B_InOut_ID(b2bReceipt.getM_InOut_ID());
			}
			InterfaceWrapperHelper.save(shipment);

			if (b2bReceipt != null)
			{
				// back-link the receipt to the (single) B2B shipment, mirroring the coupled generation path
				b2bReceipt.setB2B_InOut_ID(shipment.getM_InOut_ID());
				InterfaceWrapperHelper.save(b2bReceipt);
			}
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

		final I_M_ReceiptSchedule receiptSchedule = huReceiptScheduleBL.getById(receiptInfo.getReceiptScheduleId());
		final Quantity qtyToReceive = Quantitys.of(request.getQtyToReceiveBD(), UomId.ofRepoId(receiptSchedule.getC_UOM_ID()));

		final I_M_HU vhu = createPlanningVHU(receiptSchedule, qtyToReceive);
		if (vhu == null)
		{
			throw new AdempiereException("Failed receiving"); // shall not happen
		}
		final HuId vhuId = HuId.ofRepoId(vhu.getM_HU_ID());

		final InOutGenerateResult result = huReceiptScheduleBL.processReceiptSchedules(
				IHUReceiptScheduleBL.CreateReceiptsParameters.builder()
						.commitEachReceiptIndividually(false)
						.movementDateRule(ReceiptMovementDateRule.fixedDate(request.getReceiptDate()))
						.ctx(Env.getCtx())
						.destinationLocatorIdOrNull(null) // use receipt schedules' destination-warehouse settings
						.printReceiptLabels(true)
						.receiptSchedules(ImmutableList.of(receiptSchedule))
						.selectedHuIds(ImmutableSet.of(vhuId))
						.build());

		//
		// Post-generation linkage: the created receipt is obtained synchronously from the generate result
		// (receipt generation is not async), so M_Delivery_Planning_ID is stamped here rather than threaded
		// into the shared receipt chain.
		final I_M_InOut receipt = result.getSingleInOut(I_M_InOut.class);
		final InOutId receiptId = InOutId.ofRepoId(receipt.getM_InOut_ID());
		receipt.setM_Delivery_Planning_ID(deliveryPlanningId.getRepoId());
		InterfaceWrapperHelper.save(receipt);

		return DeliveryPlanningGenerateReceiptResult.builder()
				.receiptId(receiptId)
				.receivedVHUId(vhuId)
				.productId(ProductId.ofRepoId(receiptSchedule.getM_Product_ID()))
				.qty(qtyToReceive)
				.build();
	}

	/**
	 * Creates a single planning VHU carrying {@code qtyToReceive} for the given receipt schedule, using the
	 * branch-native allocation primitives (mirrors {@code WEBUI_M_ReceiptSchedule_ReceiveCUs.createPlanningVHU},
	 * but with a caller-supplied qty instead of the schedule's remaining qty-to-move).
	 */
	@javax.annotation.Nullable
	private I_M_HU createPlanningVHU(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final Quantity qtyToReceive)
	{
		if (qtyToReceive.signum() <= 0)
		{
			return null;
		}

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID());
		final IMutableHUContext huContextInitial = Services.get(IHUContextFactory.class).createMutableHUContextForProcessing(Env.getCtx(), clientAndOrgId);

		final I_M_Product product = productDAO.getById(receiptSchedule.getM_Product_ID());
		final ClearanceStatus clearanceStatus = ClearanceStatus.ofNullableCode(product.getHUClearanceStatus());
		final ClearanceStatusInfo clearanceStatusInfo;
		if (clearanceStatus != null)
		{
			final String language = partnerOrgBL.getOrgLanguageOrLoggedInUserLanguage(clientAndOrgId.getOrgId());
			clearanceStatusInfo = ClearanceStatusInfo.builder()
					.clearanceStatus(clearanceStatus)
					.clearanceNote(TranslatableStrings.adMessage(MESSAGE_ClearanceStatusInfo_Receipt).translate(language))
					.clearanceDate(InstantAndOrgId.ofInstant(SystemTime.asInstant(), clientAndOrgId.getOrgId()))
					.build();
		}
		else
		{
			clearanceStatusInfo = null;
		}

		final IAllocationRequest allocationRequest = AllocationUtils.builder()
				.setHUContext(huContextInitial)
				.setDateAsToday()
				.setProduct(product)
				.setQuantity(qtyToReceive)
				.setFromReferencedModel(receiptSchedule)
				.setForceQtyAllocation(true)
				.setClearanceStatusInfo(clearanceStatusInfo)
				.create();

		// make sure the attributes are initialized (task 09717)
		huReceiptScheduleBL.setInitialAttributeValueDefaults(allocationRequest, ImmutableList.of(receiptSchedule));

		final IAllocationSource allocationSource = huReceiptScheduleBL.createAllocationSource(receiptSchedule);
		final HUProducerDestination huProducer = HUProducerDestination.ofVirtualPI();

		HULoader.of(allocationSource, huProducer)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false)
				.load(allocationRequest);

		final List<I_M_HU> hus = huProducer.getCreatedHUs();
		if (hus == null || hus.size() != 1)
		{
			throw new HUException("One and only one VHU was expected but we got: " + hus);
		}
		final I_M_HU vhu = hus.get(0);

		updatePlanningVHUAttributes(vhu, receiptSchedule);

		return vhu;
	}

	/**
	 * Copies the receipt schedule's Lot number, Best-Before-Date and Vendor attributes onto the freshly
	 * created planning VHU. Mirrors {@code ReceiptScheduleBasedProcess.updateAttributes} — the only place this
	 * logic exists on this branch is that process base class ({@code de.metas.ui.web.base}), which is not
	 * reachable as a plain API here, so the three steps are reproduced.
	 */
	private void updatePlanningVHUAttributes(
			@NonNull final I_M_HU vhu,
			@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final IAttributeStorageFactory attributeStorageFactory = Services.get(IAttributeStorageFactoryService.class).createHUAttributeStorageFactory();
		final IAttributeStorage huAttributes = attributeStorageFactory.getAttributeStorage(vhu);

		setAttributeLotNumber(vhu, receiptSchedule, huAttributes);
		setAttributeBBD(receiptSchedule, huAttributes);
		setVendorValueFromReceiptSchedule(receiptSchedule, huAttributes);
	}

	private void setAttributeLotNumber(
			@NonNull final I_M_HU hu,
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final IAttributeStorage huAttributes)
	{
		if (huAttributes.hasAttribute(AttributeConstants.ATTR_LotNumber)
				&& Check.isBlank(huAttributes.getValueAsString(AttributeConstants.ATTR_LotNumber))
				&& huAttributesBL.isAutomaticallySetLotNumber())
		{
			huAttributesBL.updateHUAttributeRecursive(HuId.ofRepoId(hu.getM_HU_ID()), HUAttributeUpdateRequest.builder()
					.attributeCode(AttributeConstants.ATTR_LotNumber)
					.attributeValue(hu.getValue())
					.build());
		}
		else
		{
			final String lotNumber = getOrLoadLotNoFromSeq(receiptSchedule);
			if (Check.isNotBlank(lotNumber))
			{
				huAttributesBL.updateHUAttributeRecursive(HuId.ofRepoId(hu.getM_HU_ID()), HUAttributeUpdateRequest.builder()
						.attributeCode(AttributeConstants.ATTR_LotNumber)
						.attributeValue(lotNumber)
						.build());
			}
		}
	}

	@javax.annotation.Nullable
	private String getOrLoadLotNoFromSeq(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		if (!lotNumberFromSeq.isPresent())
		{
			final I_C_DocType docType = docTypeDAO.getById(DocTypeId.ofRepoId(receiptSchedule.getC_DocType_ID()));
			final DocSequenceId lotNoSequenceId = DocSequenceId.ofRepoIdOrNull(docType.getLotNo_Sequence_ID());
			if (lotNoSequenceId != null)
			{
				lotNumberFromSeq = lotNumberBL.getAndIncrementLotNo(LotNoContext.builder()
						.sequenceId(lotNoSequenceId)
						.clientId(ClientId.ofRepoId(receiptSchedule.getAD_Client_ID()))
						.build());
			}
		}
		return lotNumberFromSeq.orElse(null);
	}

	private void setAttributeBBD(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final IAttributeStorage huAttributes)
	{
		if (huAttributes.hasAttribute(AttributeConstants.ATTR_BestBeforeDate)
				&& huAttributes.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate) == null
				&& huAttributesBL.isAutomaticallySetBestBeforeDate()
				&& receiptSchedule.getMovementDate() != null)
		{
			final LocalDate bestBeforeDate = computeBestBeforeDate(
					ProductId.ofRepoId(receiptSchedule.getM_Product_ID()),
					TimeUtil.asLocalDate(receiptSchedule.getMovementDate()));
			if (bestBeforeDate != null)
			{
				huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate);
				huAttributes.saveChangesIfNeeded();
			}
		}
	}

	private void setVendorValueFromReceiptSchedule(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final IAttributeStorage huAttributes)
	{
		if (huAttributes.hasAttribute(AttributeConstants.ATTR_Vendor_BPartner_ID)
				&& huAttributes.getValueAsInt(AttributeConstants.ATTR_Vendor_BPartner_ID) > -1)
		{
			final int bpId = receiptSchedule.getC_BPartner_ID();
			if (bpId > 0)
			{
				huAttributes.setValue(AttributeConstants.ATTR_Vendor_BPartner_ID, bpId);
				huAttributes.setSaveOnChange(true);
				huAttributes.saveChangesIfNeeded();
			}
		}
	}

	@javax.annotation.Nullable
	private LocalDate computeBestBeforeDate(@NonNull final ProductId productId, @NonNull final LocalDate datePromised)
	{
		final int guaranteeDaysMin = productDAO.getProductGuaranteeDaysMinFallbackProductCategory(productId);
		if (guaranteeDaysMin <= 0)
		{
			return null;
		}
		return datePromised.plusDays(guaranteeDaysMin);
	}

}
