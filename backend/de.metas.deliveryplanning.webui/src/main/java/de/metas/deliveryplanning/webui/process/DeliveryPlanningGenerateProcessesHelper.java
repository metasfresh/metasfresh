package de.metas.deliveryplanning.webui.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningReceiptInfo;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.DeliveryPlanningShipmentInfo;
import de.metas.forex.ForexContractRef;
import de.metas.forex.ForexContractService;
import de.metas.forex.process.utils.ForexContracts;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

final class DeliveryPlanningGenerateProcessesHelper
{
	public static DeliveryPlanningGenerateProcessesHelper newInstance()
	{
		return DeliveryPlanningGenerateProcessesHelper.builder()
				.deliveryPlanningService(SpringContextHolder.instance.getBean(DeliveryPlanningService.class))
				.orderBL(Services.get(IOrderBL.class))
				.huReceiptScheduleBL(Services.get(IHUReceiptScheduleBL.class))
				.forexContractService(SpringContextHolder.instance.getBean(ForexContractService.class))
				.forexContractLookup(LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_ForeignExchangeContract.Table_Name))
				.build();
	}

	private final DeliveryPlanningService deliveryPlanningService;
	private final IOrderBL orderBL;
	private final IHUReceiptScheduleBL huReceiptScheduleBL;
	private final ForexContractService forexContractService;
	private final LookupDataSource forexContractLookup;

	private final HashMap<DeliveryPlanningId, Optional<DeliveryPlanningReceiptInfo>> receiptInfos = new HashMap<>();
	private final HashMap<DeliveryPlanningId, Optional<DeliveryPlanningShipmentInfo>> shipmentInfos = new HashMap<>();

	private final HashMap<OrderId, ForexContracts> forexContractsByOrderId = new HashMap<>();

	private final HashMap<OrderAndLineId, Optional<DeliveryPlanningShipmentInfo>> shipmentInfosByPurchaseOrderLineId = new HashMap<>();

	@Builder
	private DeliveryPlanningGenerateProcessesHelper(
			@NonNull final DeliveryPlanningService deliveryPlanningService,
			@NonNull final IOrderBL orderBL,
			@NonNull final IHUReceiptScheduleBL huReceiptScheduleBL,
			@NonNull final ForexContractService forexContractService,
			@NonNull final LookupDataSource forexContractLookup)
	{
		this.deliveryPlanningService = deliveryPlanningService;
		this.orderBL = orderBL;
		this.huReceiptScheduleBL = huReceiptScheduleBL;
		this.forexContractService = forexContractService;
		this.forexContractLookup = forexContractLookup;
	}

	public DeliveryPlanningReceiptInfo getReceiptInfo(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return getReceiptInfoIfIncomingType(deliveryPlanningId)
				.orElseThrow(() -> new AdempiereException("Expected to be an incoming delivery planning"));
	}

	public Optional<DeliveryPlanningReceiptInfo> getReceiptInfoIfIncomingType(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return receiptInfos.computeIfAbsent(deliveryPlanningId, deliveryPlanningService::getReceiptInfoIfIncomingType);
	}

	public ForexContracts getReceiptContracts(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final DeliveryPlanningReceiptInfo receiptInfo = getReceiptInfo(deliveryPlanningId);
		final OrderId purchaseOrderId = receiptInfo.getPurchaseOrderId();
		if (purchaseOrderId == null)
		{
			throw new AdempiereException("Not a purchase order based delivery planning");
		}

		return getForexContractsByOrderId(purchaseOrderId);
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

	public ForexContracts getShipmentContracts(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final DeliveryPlanningShipmentInfo shipmentInfo = getShipmentInfo(deliveryPlanningId);
		final OrderId salesOrderId = shipmentInfo.getSalesOrderId();
		if (salesOrderId == null)
		{
			throw new AdempiereException("Not a sales order based delivery planning");
		}

		return getForexContractsByOrderId(salesOrderId);
	}

	private ForexContracts getForexContractsByOrderId(@NonNull final OrderId orderId)
	{
		return forexContractsByOrderId.computeIfAbsent(orderId, this::retrieveForexContractsByOrderId);
	}

	private ForexContracts retrieveForexContractsByOrderId(@NonNull final OrderId orderId)
	{
		return ForexContracts.builder()
				.orderCurrencyId(orderBL.getCurrencyId(orderId))
				.forexContracts(forexContractService.getContractsByOrderId(orderId))
				.build();
	}

	public LookupValuesList toLookupValuesList(@Nullable final ForexContracts contracts)
	{
		return contracts != null && !contracts.isEmpty()
				? forexContractLookup.findByIdsOrdered(contracts.getForexContractIds())
				: LookupValuesList.EMPTY;
	}

	public Optional<DeliveryPlanningShipmentInfo> getB2BShipmentInfo(@NonNull final DeliveryPlanningReceiptInfo receiptInfo)
	{
		if (!receiptInfo.isB2B())
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
		final Set<OrderAndLineId> salesOrderLineIds = orderBL.getSOLineIdsByPOLineId(purchaseOrderAndLineId);
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

	public Optional<ForexContracts> getB2BShipmentContracts(@NonNull final DeliveryPlanningReceiptInfo receiptInfo)
	{
		final DeliveryPlanningShipmentInfo shipmentInfo = getB2BShipmentInfo(receiptInfo).orElse(null);
		if (shipmentInfo == null)
		{
			return Optional.empty();
		}

		final OrderId salesOrderId = shipmentInfo.getSalesOrderId();
		if (salesOrderId == null)
		{
			return Optional.empty();
		}

		return Optional.of(getForexContractsByOrderId(salesOrderId));
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

	private ProcessPreconditionsResolution checkEligibleToCreateShipment(final DeliveryPlanningShipmentInfo shipmentInfo)
	{
		if (shipmentInfo.isShipped())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Already shipped");
		}
		if (shipmentInfo.getSalesOrderId() == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not an order based delivery planning");
		}

		return ProcessPreconditionsResolution.accept();
	}

	public void generateShipment(DeliveryPlanningGenerateShipmentRequest request)
	{
		final DeliveryPlanningId deliveryPlanningId = request.getDeliveryPlanningId();
		final DeliveryPlanningShipmentInfo shipmentInfo = deliveryPlanningService.getShipmentInfo(deliveryPlanningId);
		if (shipmentInfo.isShipped())
		{
			throw new AdempiereException("Already shipped");
		}

		final ShipmentScheduleId shipmentScheduleId = shipmentInfo.getShipmentScheduleId();

		final ShipmentScheduleEnqueuer.Result result = new ShipmentScheduleEnqueuer()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.createWorkpackages(
						ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.builder()
								.adPInstanceId(request.getAdPInstanceId())
								.queryFilters(Services.get(IQueryBL.class).createCompositeQueryFilter(I_M_ShipmentSchedule.class)
										.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId))
								.quantityType(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
								.completeShipments(true)
								.fixedShipmentDate(request.getDeliveryDate())
								.qtysToDeliverOverride(ImmutableMap.<ShipmentScheduleId, BigDecimal>builder()
										.put(shipmentScheduleId, request.getQtyToShipBD())
										.build())
								.forexContractRef(request.getForexContractRef())
								.deliveryPlanningId(deliveryPlanningId)
								.build());

		if (result.getWorkpackageEnqueuedCount() <= 0)
		{
			throw new AdempiereException("Could not ship")
					.setParameter("result", result);
		}
	}

	public DeliveryPlanningGenerateReceiptResult generateReceipt(DeliveryPlanningGenerateReceiptRequest request)
	{
		final DeliveryPlanningId deliveryPlanningId = request.getDeliveryPlanningId();
		final DeliveryPlanningReceiptInfo receiptInfo = getReceiptInfo(deliveryPlanningId);
		if (receiptInfo.isReceived())
		{
			throw new AdempiereException("Already received");
		}

		final I_M_ReceiptSchedule receiptSchedule = huReceiptScheduleBL.getById(receiptInfo.getReceiptScheduleId());
		final Quantity qtyToReceive = Quantitys.create(request.getQtyToReceiveBD(), UomId.ofRepoId(receiptSchedule.getC_UOM_ID()));

		final ForexContractRef forexContractRef = request.getForexContractRef();

		final I_M_HU vhu = huReceiptScheduleBL.createPlanningVHU(receiptSchedule, qtyToReceive);
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
						.receiptSchedule(receiptSchedule)
						.selectedHuId(vhuId)
						.forexContractRef(forexContractRef)
						.deliveryPlanningId(deliveryPlanningId)
						.build());

		result.getSingleInOutId(); // make sure we received

		return DeliveryPlanningGenerateReceiptResult.builder()
				.receivedVHUId(vhuId)
				.productId(ProductId.ofRepoId(receiptSchedule.getM_Product_ID()))
				.qty(qtyToReceive)
				.build();
	}

}
