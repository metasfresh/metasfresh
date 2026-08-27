/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.cache.CacheMgt;
import de.metas.common.util.time.SystemTime;
import de.metas.deliveryplanning.DeliveryPlanningList.AdmissibilityField;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.incoterms.IncotermsId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.location.CountryId;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipping.ShipperId;
import de.metas.shipping.Shipper;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DeliveryPlanningService
{
	public static final AdMessageKey MSG_M_Delivery_Planning_AllClosed = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.AllClosed");
	public static final AdMessageKey MSG_M_Delivery_Planning_AllOpen = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.AllOpen");

	/**
	 * "Closed means finished, nothing touches it" (AC14): the rejection thrown by a process that acts on a single
	 * closed planning (the goods-movement generators), and the per-row skip report of {@link #cancelDelivery}.
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_Closed = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.Closed");

	/**
	 * Refuses to CLOSE a planning that is currently allocated to a COMPLETED delivery instruction, naming
	 * Re-Activate as the way out - the instruction has to go back to a draft before the planning inside it can be
	 * closed.
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_CloseOnCompletedInstruction = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.CloseOnCompletedInstruction");

	public static final AdMessageKey MSG_M_Delivery_Planning_AtLeastOnePerOrderLine = AdMessageKey.of("de.metas.deliveryplanning.M_Delivery_Planning_AtLeastOnePerOrderLine");

	private static final AdMessageKey MSG_M_Delivery_Planning_AlreadyReferenced = AdMessageKey.of("de.metas.deliveryplanning.M_Delivery_Planning_AlreadyReferenced");

	public static final AdMessageKey MSG_M_Delivery_Planning_NoForwarder = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.NoForwarder");
	public static final AdMessageKey MSG_M_Delivery_Planning_AllHaveReleaseNo = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.AllHaveReleaseNo");
	public static final AdMessageKey MSG_M_Delivery_Planning_WhithOutReleaseNo = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.WhithOutReleaseNo");
	public static final AdMessageKey MSG_M_Delivery_Planning_BlockedPartner = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.NoBlockedPartner");
	public static final AdMessageKey MSG_M_Delivery_Planning_SalesOrderFullyDelivered = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.SalesOrderFullyDelivered");
	public static final AdMessageKey MSG_M_Delivery_Planning_PurchaseOrderFullyDelivered = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.PurchaseOrderFullyDelivered");
	public static final String PARAM_AdditionalLines = "AdditionalLines";

	/**
	 * One message for the whole selection, naming EVERY field the selection disagrees on - never the first one
	 * found, and never one message per field: a planner who deselects the odd forwarder only to be told about the
	 * delivery address is being sent back and forth for information we already had.
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_IncompatibleSelection = AdMessageKey.of("de.metas.deliveryplanning.CombineIntoDeliveryInstruction.IncompatibleSelection");
	public static final AdMessageKey MSG_M_Delivery_Planning_ClosedPlannings = AdMessageKey.of("de.metas.deliveryplanning.CombineIntoDeliveryInstruction.ClosedPlannings");
	public static final AdMessageKey MSG_M_Delivery_Planning_AlreadyOnDeliveryInstruction = AdMessageKey.of("de.metas.deliveryplanning.CombineIntoDeliveryInstruction.AlreadyOnDeliveryInstruction");

	/**
	 * Named "completed" rather than "not a draft" because that is the only state a planner can meet here: voiding
	 * an instruction deactivates its allocations, so a planning still carrying an ACTIVE allocation to a non-draft
	 * instruction is on a completed one.
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_OnCompletedDeliveryInstruction = AdMessageKey.of("de.metas.deliveryplanning.DeliveryInstruction.OnCompletedInstruction");
	public static final AdMessageKey MSG_M_Delivery_Planning_TargetInstructionNotDraft = AdMessageKey.of("de.metas.deliveryplanning.AddToDeliveryInstruction.TargetNotDraft");
	public static final AdMessageKey MSG_M_Delivery_Planning_NotOnDeliveryInstruction = AdMessageKey.of("de.metas.deliveryplanning.RemoveFromDeliveryInstruction.NotOnDeliveryInstruction");

	/**
	 * Distinct from {@link #MSG_M_Delivery_Planning_ClosedPlannings}: that one rejects a SELECTION naming a closed
	 * planning before it is put on an instruction (Combine / Add to); this one rejects COMPLETING an instruction
	 * that already holds a planning closed AFTER it was allocated - a different moment, a different sentence.
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_ClosedAllocatedPlannings = AdMessageKey.of("de.metas.deliveryplanning.CompleteDeliveryInstruction.ClosedAllocatedPlannings");

	/**
	 * Before allocations existed, a 1:1 header FK made "zero plannings" unreachable by construction for a delivery
	 * instruction. {@code M_Delivery_Planning_RemoveFromDeliveryInstruction} can now take the last planning off a
	 * drafted instruction, making this state reachable for the first time - and the reports resolving through the
	 * allocation would print a blank document for it. Scoped to an actual delivery instruction (via
	 * {@link ShipperTransportationDocSubTypeGuard}, never by direction/{@code IsSOTrx}/allocations) so a transport
	 * order, which legitimately never has allocations, stays unaffected.
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_EmptyDeliveryInstruction = AdMessageKey.of("de.metas.deliveryplanning.CompleteDeliveryInstruction.EmptyDeliveryInstruction");

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	/**
	 * The task-C5 mechanism that tells the two {@code M_ShipperTransportation} document roles apart - reused here
	 * rather than a second ad-hoc distinction.
	 */
	private final ShipperTransportationDocSubTypeGuard shipperTransportationDocSubTypeGuard;
	private final ShipperRepository shipperRepository;
	private final DeliveryPlanningRepository deliveryPlanningRepository;
	private final DeliveryStatusColorPaletteService deliveryStatusColorPaletteService;

	private final DimensionService dimensionService;

	private final MeansOfTransportationService meansOfTransportationService;

	final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);

	public DeliveryPlanningService(
			@NonNull final ShipperRepository shipperRepository,
			@NonNull final DeliveryPlanningRepository deliveryPlanningRepository,
			@NonNull final DeliveryStatusColorPaletteService deliveryStatusColorPaletteService,
			@NonNull final DimensionService dimensionService,
			@NonNull final MeansOfTransportationService meansOfTransportationService,
			@NonNull final ShipperTransportationDocSubTypeGuard shipperTransportationDocSubTypeGuard)
	{
		this.shipperRepository = shipperRepository;
		this.deliveryPlanningRepository = deliveryPlanningRepository;
		this.deliveryStatusColorPaletteService = deliveryStatusColorPaletteService;
		this.dimensionService = dimensionService;
		this.meansOfTransportationService = meansOfTransportationService;
		this.shipperTransportationDocSubTypeGuard = shipperTransportationDocSubTypeGuard;
	}

	/**
	 * Gate for per-shipper auto-creation.
	 *
	 * <p>Returns {@code true} only when:
	 * <ol>
	 *     <li>a non-null {@code shipperId} is given</li>
	 *     <li>the resolved shipper exists and is active</li>
	 *     <li>the resolved shipper's {@code IsCreateDeliveryPlanning} flag is {@code true}</li>
	 * </ol>
	 */
	public boolean isAutoCreateEnabled(@Nullable final ShipperId shipperId)
	{
		if (shipperId == null)
		{
			return false; // no shipper → skip
		}
		return shipperRepository.findById(shipperId)
				.map(Shipper::isCreateDeliveryPlanning)
				.orElse(false); // inactive or missing shipper → skip
	}

	private DeliveryStatusColorPalette getColorPalette()
	{
		return deliveryStatusColorPaletteService.get();
	}

	public void generateIncomingDeliveryPlanning(final I_M_ReceiptSchedule receiptScheduleRecord)
	{
		GenerateIncomingDeliveryPlanningCommand.builder()
				.deliveryPlanningRepository(deliveryPlanningRepository)
				.receiptSchedule(receiptScheduleRecord)
				.colorPalette(getColorPalette())
				.dimensionService(dimensionService)
				.build()
				.execute();
	}

	public void generateOutgoingDeliveryPlanning(final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		GenerateOutgoingDeliveryPlanningCommand.builder()
				.deliveryPlanningRepository(deliveryPlanningRepository)
				.shipmentSchedule(shipmentScheduleRecord)
				.colorPalette(getColorPalette())
				.dimensionService(dimensionService)
				.build()
				.execute();
	}

	public void validateDeletion(final I_M_Delivery_Planning deliveryPlanning)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(deliveryPlanning.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			// nothing to do: delivery planning is not based on any order line
			return;
		}

		final boolean otherDeliveryPlanningsExistForOrderLine = deliveryPlanningRepository.isOtherDeliveryPlanningsExistForOrderLine(orderLineId, DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID()));

		if (!otherDeliveryPlanningsExistForOrderLine)
		{
			throw new AdempiereException(MSG_M_Delivery_Planning_AtLeastOnePerOrderLine);
		}

		if (!Check.isBlank(deliveryPlanning.getReleaseNo()))
		{
			throw new AdempiereException(MSG_M_Delivery_Planning_AlreadyReferenced);
		}

	}

	private DeliveryPlanningCreateRequest createRequest(@NonNull final DeliveryPlanningId deliveryPlanningId, @NonNull final Quantity plannedLoadedQty)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningRepository.getById(deliveryPlanningId);
		final OrgId orgId = OrgId.ofRepoId(deliveryPlanningRecord.getAD_Org_ID());

		final ProductId productId = ProductId.ofRepoId(deliveryPlanningRecord.getM_Product_ID());
		final I_C_UOM uomToUse = getUomOrStockUom(deliveryPlanningRecord, productId);

		final Dimension dimension = dimensionService.getFromRecord(deliveryPlanningRecord);

		return DeliveryPlanningCreateRequest.builder()
				.orgId(orgId)
				.clientId(ClientId.ofRepoId(deliveryPlanningRecord.getAD_Client_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoIdOrNull(deliveryPlanningRecord.getM_ShipmentSchedule_ID()))
				.receiptScheduleId(ReceiptScheduleId.ofRepoIdOrNull(deliveryPlanningRecord.getM_ReceiptSchedule_ID()))
				.orderId(OrderId.ofRepoIdOrNull(deliveryPlanningRecord.getC_Order_ID()))
				.orderLineId(OrderLineId.ofRepoIdOrNull(deliveryPlanningRecord.getC_OrderLine_ID()))
				.productId(productId)
				.partnerId(BPartnerId.ofRepoId(deliveryPlanningRecord.getC_BPartner_ID()))
				.bPartnerLocationId(BPartnerLocationId.ofRepoId(deliveryPlanningRecord.getC_BPartner_ID(), deliveryPlanningRecord.getC_BPartner_Location_ID()))
				.incotermsId(IncotermsId.ofRepoIdOrNull(deliveryPlanningRecord.getC_Incoterms_ID()))
				.incotermLocation(deliveryPlanningRecord.getIncotermLocation())
				.warehouseId(WarehouseId.ofRepoId(deliveryPlanningRecord.getM_Warehouse_ID()))
				.transportDirection(DeliveryPlanningRepository.extractTransportDirection(deliveryPlanningRecord))
				.orderStatus(OrderStatus.ofNullableCode(deliveryPlanningRecord.getOrderStatus()))
				.meansOfTransportationId(MeansOfTransportationId.ofRepoIdOrNull(deliveryPlanningRecord.getM_MeansOfTransportation_ID()))
				.qtyOrdered(Quantity.of(deliveryPlanningRecord.getQtyOrdered(), uomToUse))
				.qtyTotalOpen(Quantity.of(deliveryPlanningRecord.getQtyTotalOpen(), uomToUse))
				.actualLoadedQty(Quantity.of(deliveryPlanningRecord.getActualLoadQty(), uomToUse))

				.plannedLoadedQty(plannedLoadedQty)
				.plannedDischargeQty(Quantity.of(deliveryPlanningRecord.getPlannedDischargeQuantity(), uomToUse))
				.actualDischargeQty(Quantity.of(deliveryPlanningRecord.getActualDischargeQuantity(), uomToUse))

				.uom(uomToUse)
				.plannedLoadingDate(TimeUtil.asInstant(deliveryPlanningRecord.getETD()))
				.actualLoadingDate(TimeUtil.asInstant(deliveryPlanningRecord.getATD()))
				.plannedDeliveryDate(TimeUtil.asInstant(deliveryPlanningRecord.getETA()))
				.actualDeliveryDate(TimeUtil.asInstant(deliveryPlanningRecord.getATA()))
				.loadingTime(deliveryPlanningRecord.getLoadingTime())
				.deliveryTime(deliveryPlanningRecord.getDeliveryTime())
				.wayBillNo(deliveryPlanningRecord.getWayBillNo())
				.batch(deliveryPlanningRecord.getBatch())
				.originCountryId(CountryId.ofRepoIdOrNull(deliveryPlanningRecord.getC_OriginCountry_ID()))
				.destinationCountryId(CountryId.ofRepoIdOrNull(deliveryPlanningRecord.getC_DestinationCountry_ID()))
				.shipperId(ShipperId.ofRepoIdOrNull(deliveryPlanningRecord.getM_Shipper_ID()))
				.transportDetails(deliveryPlanningRecord.getTransportDetails())
				.dimension(dimension)
				.build();
	}

	/**
	 * The delivery planning's own UOM, or the product's stock UOM when it has none.
	 */
	private I_C_UOM getUomOrStockUom(@NonNull final I_M_Delivery_Planning deliveryPlanningRecord, @NonNull final ProductId productId)
	{
		final I_C_UOM uomOfRecord = uomDAO.getByIdOrNull(deliveryPlanningRecord.getC_UOM_ID());
		return uomOfRecord != null ? uomOfRecord : productBL.getStockUOM(productId);
	}

	public void createAdditionalDeliveryPlannings(@NonNull final DeliveryPlanningId deliveryPlanningId, final int additionalLines)
	{
		validateDeliveryPlanning(deliveryPlanningId);

		Check.assumeGreaterThanZero(additionalLines, PARAM_AdditionalLines);

		final Quantity openQty = getOpenQty(deliveryPlanningId);

		final Quantity fraction = openQty.divide(BigDecimal.valueOf(additionalLines + 1), 0, RoundingMode.DOWN);

		final Quantity remainder = openQty.subtract(fraction.multiply(additionalLines + 1));
		deliveryPlanningRepository.setPlannedLoadedQuantity(deliveryPlanningId, fraction.add(remainder));

		for (int i = 0; i < additionalLines; i++)
		{
			final DeliveryPlanningCreateRequest request = createRequest(deliveryPlanningId, fraction);

			deliveryPlanningRepository.generateDeliveryPlanning(request);
		}
	}

	private Quantity getOpenQty(final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningRepository.getById(deliveryPlanningId);
		final I_C_UOM uom = uomDAO.getById(deliveryPlanningRecord.getC_UOM_ID());

		final Quantity qtyOrdered = Quantity.of(deliveryPlanningRecord.getQtyOrdered(), uom);

		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(deliveryPlanningRecord.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			// the delivery planning has no order line => remaining open qty is 0
			return Quantity.zero(uom);
		}

		Quantity openQty = qtyOrdered;

		final Quantity plannedLoadedQtySum = deliveryPlanningRepository.retrieveForOrderLine(orderLineId)
				.filter(deliveryPlanning -> deliveryPlanningId.getRepoId() != deliveryPlanning.getM_Delivery_Planning_ID())
				.map(DeliveryPlanningService::extractPlannedLoadedQuantity)
				.reduce(Quantity::add)
				.orElse(null);
		if (plannedLoadedQtySum != null && !plannedLoadedQtySum.isZero())
		{
			openQty = openQty.subtract(plannedLoadedQtySum);
		}

		return openQty.toZeroIfNegative();
	}

	private static Quantity extractPlannedLoadedQuantity(final I_M_Delivery_Planning deliveryPlanning)
	{
		final UomId uomId = UomId.ofRepoId(deliveryPlanning.getC_UOM_ID());
		return Quantitys.of(deliveryPlanning.getPlannedLoadedQuantity(), uomId);
	}

	public void deleteForReceiptSchedule(@NonNull final ReceiptScheduleId receiptScheduleId)
	{
		deliveryPlanningRepository.deleteForReceiptSchedule(receiptScheduleId);
	}

	public void deleteForShipmentSchedule(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		deliveryPlanningRepository.deleteForShipmentSchedule(shipmentScheduleId);
	}

	public boolean isClosed(final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningRepository.getById(deliveryPlanningId);
		return deliveryPlanningRecord.isClosed();
	}

	public void closeSelectedDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		validateDeliveryPlannings(selectedDeliveryPlanningsFilter);
		deliveryPlanningRepository.closeSelectedDeliveryPlannings(selectedDeliveryPlanningsFilter);
	}

	public void reOpenSelectedDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		validateDeliveryPlannings(selectedDeliveryPlanningsFilter);
		deliveryPlanningRepository.reOpenSelectedDeliveryPlannings(selectedDeliveryPlanningsFilter);
	}

	public boolean isExistsNoShipperDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return deliveryPlanningRepository.isExistNoShipperDeliveryPlannings(selectedDeliveryPlanningsFilter);
	}

	/**
	 * Generates ONE delivery instruction for the given planning.
	 * <p>
	 * Mirrors how {@link #combine(IQueryFilter, boolean)} takes the same {@code complete} flag: completion is
	 * optional, not automatic, so a name promising completion unconditionally would be a lie.
	 *
	 * @param complete complete the instruction right away instead of leaving it a draft. {@link #regenerateDeliveryInstructions}
	 * 		is the one caller that always passes {@code true} - regenerate always completes.
	 */
	public void generateDeliveryInstruction(@NonNull final DeliveryInstructionCreateRequest deliveryInstructionRequest, final boolean complete)
	{
		final DeliveryInstructionUserNotificationsProducer deliveryInstructionUserNotificationsProducer = DeliveryInstructionUserNotificationsProducer.newInstance();

		final DeliveryPlanningId deliveryPlanningId = deliveryInstructionRequest.getDeliveryPlanningId();

		final I_M_ShipperTransportation deliveryInstruction = deliveryPlanningRepository.generateDeliveryInstruction(deliveryInstructionRequest);

		if (complete)
		{
			docActionBL.processEx(deliveryInstruction, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}

		deliveryInstructionUserNotificationsProducer
				.notifyGenerated(deliveryInstruction);

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(ImmutableSet.of(deliveryPlanningId), deliveryInstruction);

		CacheMgt.get().reset(I_M_Delivery_Planning.Table_Name, deliveryPlanningId.getRepoId());

	}

	public boolean isExistDeliveryPlanningsWithoutReleaseNo(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return deliveryPlanningRepository.isExistDeliveryPlanningsWithoutReleaseNo(selectedDeliveryPlanningsFilter);
	}

	public boolean isExistDeliveryPlanningsWithReleaseNo(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return deliveryPlanningRepository.isExistDeliveryPlanningsWithReleaseNo(selectedDeliveryPlanningsFilter);
	}

	/**
	 * Loads the selected delivery plannings ONCE, so that every precondition and every rule of the aggregation
	 * processes can be answered against the returned in-memory list instead of firing its own query.
	 */
	public DeliveryPlanningList getBySelection(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final ImmutableList.Builder<I_M_Delivery_Planning> recordsCollector = ImmutableList.builder();

		final Iterator<I_M_Delivery_Planning> records = deliveryPlanningRepository.extractDeliveryPlannings(selectedDeliveryPlanningsFilter);
		while (records.hasNext())
		{
			recordsCollector.add(records.next());
		}
		final ImmutableList<I_M_Delivery_Planning> deliveryPlanningRecords = recordsCollector.build();

		return toDeliveryPlanningList(
				deliveryPlanningRecords,
				deliveryPlanningRepository.getAllocatedInstructionIds(
						deliveryPlanningRecords.stream()
								.map(record -> DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID()))
								.collect(ImmutableSet.toImmutableSet())));
	}

	/**
	 * The delivery plannings a delivery instruction currently holds, as the same in-memory list a selection is
	 * judged as - so the add-to rule can be answered against what the instruction would hold AFTERWARDS.
	 * <p>
	 * Two batch loads and not a single per-row one: the ids of the instruction's ACTIVE allocations, then the
	 * records behind them. Which instruction each of them sits on is not queried a second time - it is the one that
	 * was asked for.
	 */
	private DeliveryPlanningList getAllocatedTo(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final ImmutableSet<DeliveryPlanningId> allocatedIds = deliveryPlanningRepository.getAllocatedPlanningIds(deliveryInstructionId);
		if (allocatedIds.isEmpty())
		{
			return DeliveryPlanningList.EMPTY;
		}

		return toDeliveryPlanningList(
				deliveryPlanningRepository.getByIds(allocatedIds),
				Maps.toMap(allocatedIds, ignoredDeliveryPlanningId -> deliveryInstructionId));
	}

	/**
	 * The given records as the in-memory list every aggregation rule is answered against, with the addresses they
	 * are read from batch-loaded ONCE for the whole collection.
	 *
	 * @param allocatedInstructionIds the delivery instruction each planning is currently allocated to, absent for
	 * 		one that is on none. Handed in rather than queried here, because a caller that already knows it - having
	 * 		asked for exactly the plannings of ONE instruction - would otherwise pay for a round trip to be told
	 * 		what it just asked for.
	 */
	private DeliveryPlanningList toDeliveryPlanningList(
			@NonNull final ImmutableList<I_M_Delivery_Planning> deliveryPlanningRecords,
			@NonNull final Map<DeliveryPlanningId, ShipperTransportationId> allocatedInstructionIds)
	{
		final DeliveryPlanningAddresses addresses = loadAddresses(deliveryPlanningRecords);

		return deliveryPlanningRecords.stream()
				.map(record -> toDeliveryPlanning(record, addresses, allocatedInstructionIds))
				.collect(DeliveryPlanningList.collect());
	}

	private static DeliveryPlanning toDeliveryPlanning(
			@NonNull final I_M_Delivery_Planning record,
			@NonNull final DeliveryPlanningAddresses addresses,
			@NonNull final Map<DeliveryPlanningId, ShipperTransportationId> allocatedInstructionIds)
	{
		final TransportDirection transportDirection = DeliveryPlanningRepository.extractTransportDirection(record);
		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());

		return DeliveryPlanning.builder()
				.id(deliveryPlanningId)
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.type(transportDirection)
				.shipperId(ShipperId.ofRepoIdOrNull(record.getM_Shipper_ID()))
				.incotermsId(IncotermsId.ofRepoIdOrNull(record.getC_Incoterms_ID()))
				.incotermLocation(record.getIncotermLocation())
				.meansOfTransportationId(MeansOfTransportationId.ofRepoIdOrNull(record.getM_MeansOfTransportation_ID()))
				.loadingLocationId(extractShipFromLocationIdOrNull(record, transportDirection, addresses))
				.deliveryLocationId(extractShipToLocationIdOrNull(record, transportDirection, addresses))
				.etd(TimeUtil.asInstant(record.getETD()))
				.closed(record.isClosed())
				.deliveryInstructionId(allocatedInstructionIds.get(deliveryPlanningId))
				.build();
	}

	/**
	 * Batch-loads every record the ship-from and ship-to addresses of the given delivery plannings are read from,
	 * so that resolving those addresses costs one round trip per collaborator instead of one per planning.
	 */
	private DeliveryPlanningAddresses loadAddresses(@NonNull final Collection<I_M_Delivery_Planning> records)
	{
		final ImmutableSet.Builder<ReceiptScheduleId> receiptScheduleIds = ImmutableSet.builder();
		final ImmutableSet.Builder<ShipmentScheduleId> shipmentScheduleIds = ImmutableSet.builder();
		final ImmutableSet.Builder<WarehouseId> warehouseIds = ImmutableSet.builder();

		for (final I_M_Delivery_Planning record : records)
		{
			addIfNotNull(receiptScheduleIds, ReceiptScheduleId.ofRepoIdOrNull(record.getM_ReceiptSchedule_ID()));
			addIfNotNull(shipmentScheduleIds, ShipmentScheduleId.ofRepoIdOrNull(record.getM_ShipmentSchedule_ID()));
			addIfNotNull(warehouseIds, WarehouseId.ofRepoIdOrNull(record.getM_Warehouse_ID()));
		}

		return DeliveryPlanningAddresses.builder()
				.receiptSchedules(receiptScheduleDAO.getByIds(receiptScheduleIds.build()))
				.shipmentSchedules(shipmentScheduleBL.getByIds(shipmentScheduleIds.build()))
				// getByIds resolves out of trx only, unlike getById: warehouses are master data, so none is ever
				// created in the same transaction as the delivery plannings read here
				.warehouses(Maps.uniqueIndex(
						warehouseDAO.getByIds(warehouseIds.build()),
						warehouse -> WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID())))
				.build();
	}

	private static <T> void addIfNotNull(@NonNull final ImmutableSet.Builder<T> collector, @Nullable final T element)
	{
		if (element != null)
		{
			collector.add(element);
		}
	}

	private DeliveryInstructionCreateRequest createDeliveryInstructionRequest(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningRepository.getById(deliveryPlanningId);

		if (deliveryPlanningRecord.getM_Shipper_ID() == 0)
		{
			throw new AdempiereException("Cannot create M_ShipperTransportation if M_Shipper_ID is missing")
					.appendParametersToMessage()
					.setParameter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningId.getRepoId());
		}

		final OrgId orgId = OrgId.ofRepoId(deliveryPlanningRecord.getAD_Org_ID());

		final TransportDirection transportDirection = DeliveryPlanningRepository.extractTransportDirection(deliveryPlanningRecord);

		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(DocBaseType.ShipperTransportation)
				.docSubType(DocSubType.DeliveryInstruction)
				.adClientId(deliveryPlanningRecord.getAD_Client_ID())
				.adOrgId(deliveryPlanningRecord.getAD_Org_ID())
				.build();

		final DocTypeId docTypeId = docTypeDAO.getDocTypeIdOrNull(docTypeQuery);
		if (docTypeId == null)
		{
			throw new DocTypeNotFoundException(docTypeQuery);
		}

		final ProductId productId = ProductId.ofRepoId(deliveryPlanningRecord.getM_Product_ID());
		final I_C_UOM uomToUse = getUomOrStockUom(deliveryPlanningRecord, productId);

		final BPartnerLocationId deliveryPlanningLocationId = BPartnerLocationId.ofRepoId(deliveryPlanningRecord.getC_BPartner_ID(), deliveryPlanningRecord.getC_BPartner_Location_ID());
		final boolean hasReceipt = transportDirection.hasReceipt();
		final DeliveryPlanningAddresses addresses = loadAddresses(ImmutableList.of(deliveryPlanningRecord));
		final BPartnerLocationId shipFrom = extractShipFromLocationId(deliveryPlanningRecord, transportDirection, addresses);
		final BPartnerLocationId shipTo = extractShipToLocationId(deliveryPlanningRecord, transportDirection, addresses);

		final Dimension deliveryPlanningDimension = dimensionService.getFromRecord(deliveryPlanningRecord);

		return DeliveryInstructionCreateRequest.builder()
				.orgId(orgId)
				.clientId(ClientId.ofRepoId(deliveryPlanningRecord.getAD_Client_ID()))

				.shipperBPartnerId(BPartnerId.ofRepoId(deliveryPlanningRecord.getC_BPartner_ID()))
				.shipperLocationId(deliveryPlanningLocationId)
				.incotermsId(IncotermsId.ofRepoIdOrNull(deliveryPlanningRecord.getC_Incoterms_ID()))
				.incotermLocation(deliveryPlanningRecord.getIncotermLocation())
				.meansOfTransportationId(MeansOfTransportationId.ofRepoIdOrNull(deliveryPlanningRecord.getM_MeansOfTransportation_ID()))
				.loadingPartnerLocationId(shipFrom)
				.loadingDate(TimeUtil.asInstant(deliveryPlanningRecord.getETD()))
				.atd(TimeUtil.asInstant(deliveryPlanningRecord.getATD()))
				.loadingTime(deliveryPlanningRecord.getLoadingTime())
				.deliveryPartnerLocationId(shipTo)
				.deliveryDate(TimeUtil.asInstant(deliveryPlanningRecord.getETA()))
				.ata(TimeUtil.asInstant(deliveryPlanningRecord.getATA()))
				.deliveryTime(deliveryPlanningRecord.getDeliveryTime())

				.dateDoc(SystemTime.asInstant())
				.docTypeId(docTypeId)

				.shipperId(ShipperId.ofRepoId(deliveryPlanningRecord.getM_Shipper_ID()))

				.productId(productId)
				.isToBeFetched(hasReceipt)
				//.locatorId() : Not yet decided where to take it from. TODO in a future CR
				.batchNo(deliveryPlanningRecord.getBatch())
				.qtyLoaded(Quantity.of(deliveryPlanningRecord.getPlannedLoadedQuantity(), uomToUse))
				.qtyDischarged(Quantity.of(deliveryPlanningRecord.getPlannedDischargeQuantity(), uomToUse))
				.orderLineId(OrderLineId.ofRepoIdOrNull(deliveryPlanningRecord.getC_OrderLine_ID()))
				.deliveryPlanningId(deliveryPlanningId)
				.dimension(deliveryPlanningDimension)
				.build();
	}

	/**
	 * @return {@code null} when the record the loading address is read from is not set. A selection-wide
	 * precondition runs on every selection change, so it must report "this planning has no loading address"
	 * rather than throw.
	 */
	@Nullable
	private static BPartnerLocationId extractShipFromLocationIdOrNull(
			@NonNull final I_M_Delivery_Planning deliveryPlanningRecord,
			@NonNull final TransportDirection transportDirection,
			@NonNull final DeliveryPlanningAddresses addresses)
	{
		if (transportDirection.hasReceipt())
		{
			final ReceiptScheduleId receiptScheduleId = ReceiptScheduleId.ofRepoIdOrNull(deliveryPlanningRecord.getM_ReceiptSchedule_ID());
			return receiptScheduleId != null ? addresses.getReceiptScheduleLocationId(receiptScheduleId) : null;
		}

		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(deliveryPlanningRecord.getM_Warehouse_ID());
		return warehouseId != null ? addresses.getWarehouseLocationId(warehouseId) : null;
	}

	/**
	 * @return {@code null} when the record the delivery address is read from is not set - see
	 * {@link #extractShipFromLocationIdOrNull(I_M_Delivery_Planning, TransportDirection, DeliveryPlanningAddresses)}.
	 */
	@Nullable
	private static BPartnerLocationId extractShipToLocationIdOrNull(
			@NonNull final I_M_Delivery_Planning deliveryPlanningRecord,
			@NonNull final TransportDirection transportDirection,
			@NonNull final DeliveryPlanningAddresses addresses)
	{
		if (DeliveryPlanningRepository.hasOwnShipment(transportDirection))
		{
			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoIdOrNull(deliveryPlanningRecord.getM_ShipmentSchedule_ID());
			return shipmentScheduleId != null ? addresses.getShipmentScheduleLocationId(shipmentScheduleId) : null;
		}

		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(deliveryPlanningRecord.getM_Warehouse_ID());
		return warehouseId != null ? addresses.getWarehouseLocationId(warehouseId) : null;
	}

	private static BPartnerLocationId extractShipFromLocationId(
			@NonNull final I_M_Delivery_Planning deliveryPlanningRecord,
			@NonNull final TransportDirection transportDirection,
			@NonNull final DeliveryPlanningAddresses addresses)
	{
		final BPartnerLocationId loadingLocationId = extractShipFromLocationIdOrNull(deliveryPlanningRecord, transportDirection, addresses);
		if (loadingLocationId == null)
		{
			throw new AdempiereException("Cannot determine the loading address")
					.appendParametersToMessage()
					.setParameter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningRecord.getM_Delivery_Planning_ID());
		}
		return loadingLocationId;
	}

	private static BPartnerLocationId extractShipToLocationId(
			@NonNull final I_M_Delivery_Planning deliveryPlanningRecord,
			@NonNull final TransportDirection transportDirection,
			@NonNull final DeliveryPlanningAddresses addresses)
	{
		final BPartnerLocationId deliveryLocationId = extractShipToLocationIdOrNull(deliveryPlanningRecord, transportDirection, addresses);
		if (deliveryLocationId == null)
		{
			throw new AdempiereException("Cannot determine the delivery address")
					.appendParametersToMessage()
					.setParameter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningRecord.getM_Delivery_Planning_ID());
		}
		return deliveryLocationId;
	}

	/**
	 * The records the ship-from and ship-to addresses of a set of delivery plannings are read from.
	 */
	@Builder
	private static final class DeliveryPlanningAddresses
	{
		@NonNull private final Map<ReceiptScheduleId, I_M_ReceiptSchedule> receiptSchedules;
		@NonNull private final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedules;
		@NonNull private final Map<WarehouseId, I_M_Warehouse> warehouses;

		BPartnerLocationId getReceiptScheduleLocationId(@NonNull final ReceiptScheduleId receiptScheduleId)
		{
			final I_M_ReceiptSchedule receiptSchedule = getOrThrow(receiptSchedules, receiptScheduleId, I_M_ReceiptSchedule.Table_Name);
			return BPartnerLocationId.ofRepoId(receiptSchedule.getC_BPartner_ID(), receiptSchedule.getC_BPartner_Location_ID());
		}

		BPartnerLocationId getShipmentScheduleLocationId(@NonNull final ShipmentScheduleId shipmentScheduleId)
		{
			final I_M_ShipmentSchedule shipmentSchedule = getOrThrow(shipmentSchedules, shipmentScheduleId, I_M_ShipmentSchedule.Table_Name);
			return BPartnerLocationId.ofRepoId(shipmentSchedule.getC_BPartner_ID(), shipmentSchedule.getC_BPartner_Location_ID());
		}

		BPartnerLocationId getWarehouseLocationId(@NonNull final WarehouseId warehouseId)
		{
			final I_M_Warehouse warehouse = getOrThrow(warehouses, warehouseId, I_M_Warehouse.Table_Name);
			return BPartnerLocationId.ofRepoId(warehouse.getC_BPartner_ID(), warehouse.getC_BPartner_Location_ID());
		}

		/**
		 * A resolved id with no matching row is a dangling reference, not an absent address - throw rather than
		 * read as "no address", which is reserved for a genuinely unset id.
		 */
		private static <ID extends RepoIdAware, T> T getOrThrow(
				@NonNull final Map<ID, T> recordsById,
				@NonNull final ID id,
				@NonNull final String tableName)
		{
			final T record = recordsById.get(id);
			if (record == null)
			{
				throw new AdempiereException("No " + tableName + " found")
						.appendParametersToMessage()
						.setParameter(tableName + "_ID", id.getRepoId());
			}
			return record;
		}
	}

	/**
	 * Generates one delivery instruction PER selected planning - as opposed to {@link #combine}, which puts the
	 * whole selection on ONE instruction.
	 *
	 * @param complete complete every generated instruction right away instead of leaving it a draft - a draft is
	 * 		the default, same as {@link #combine(IQueryFilter, boolean)}.
	 */
	public void generateDeliveryInstructions(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter, final boolean complete)
	{
		final ICompositeQueryFilter<I_M_Delivery_Planning> deliveryPlanningsSuitableForInstruction = deliveryPlanningRepository
				.excludeUnsuitableForInstruction(selectedDeliveryPlanningsFilter);

		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = deliveryPlanningRepository.extractDeliveryPlannings(deliveryPlanningsSuitableForInstruction);
		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();

			final DeliveryInstructionCreateRequest deliveryInstructionRequest = createDeliveryInstructionRequest(DeliveryPlanningId.ofRepoId(deliveryPlanningRecord.getM_Delivery_Planning_ID()));

			generateDeliveryInstruction(deliveryInstructionRequest, complete);
		}
	}

	/**
	 * Why this selection cannot be combined into ONE delivery instruction, or empty when it can.
	 * <p>
	 * Lives here rather than in the process's {@code checkPreconditionsApplicable} for two reasons: a cucumber
	 * step drives the same rule the WebUI drives, and the reason the disabled button shows is by construction the
	 * same sentence {@link #combine(IQueryFilter, boolean)} throws.
	 * <p>
	 * Row eligibility is checked before cross-row compatibility, so a planner resolves "this row cannot go at
	 * all" before "these rows cannot go together".
	 */
	public Optional<ITranslatableString> getCombineRejectionReason(@NonNull final DeliveryPlanningList selectedDeliveryPlannings)
	{
		if (!selectedDeliveryPlannings.withoutShipper().isEmpty())
		{
			// the delivery instruction header cannot exist without a forwarder
			return Optional.of(TranslatableStrings.adMessage(MSG_M_Delivery_Planning_NoForwarder));
		}

		if (selectedDeliveryPlannings.anyClosed())
		{
			return Optional.of(TranslatableStrings.adMessage(
					MSG_M_Delivery_Planning_ClosedPlannings,
					toIdList(selectedDeliveryPlannings.closedOnes())));
		}

		if (selectedDeliveryPlannings.anyAllocated())
		{
			// rejected here, and not left to the single-active-allocation unique index: the index would abort the
			// whole transaction with a constraint violation instead of naming the plannings that are in the way
			return Optional.of(TranslatableStrings.adMessage(
					MSG_M_Delivery_Planning_AlreadyOnDeliveryInstruction,
					toIdList(selectedDeliveryPlannings.allocatedOnes())));
		}

		final ImmutableSet<AdmissibilityField> mismatches = selectedDeliveryPlannings.admissibilityMismatches();
		if (!mismatches.isEmpty())
		{
			return Optional.of(incompatibleMessage(mismatches));
		}

		return Optional.empty();
	}

	/**
	 * The one rejection that names EVERY field the plannings disagree on, rather than reporting one field at a time
	 * and making the planner fix them one action at a time.
	 * <p>
	 * Shared by both actions that put plannings on a delivery instruction: they write the same document under the
	 * same header, so they owe the planner the same sentence.
	 */
	private static ITranslatableString incompatibleMessage(@NonNull final Set<AdmissibilityField> mismatches)
	{
		final ITranslatableString differingFields = mismatches.stream()
				.map(field -> TranslatableStrings.adMessage(field.getLabel()))
				.collect(TranslatableStrings.joining(", "));

		return TranslatableStrings.adMessage(MSG_M_Delivery_Planning_IncompatibleSelection, differingFields);
	}

	private static String toIdList(@NonNull final DeliveryPlanningList deliveryPlannings)
	{
		return toIdList(deliveryPlannings.getIdsInAllocationOrder());
	}

	private static String toIdList(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		return deliveryPlanningIds.stream()
				.map(deliveryPlanningId -> String.valueOf(deliveryPlanningId.getRepoId()))
				.collect(Collectors.joining(", "));
	}

	/**
	 * Why the given delivery instruction cannot be completed, or empty when it can.
	 * <p>
	 * Two rules:
	 * <ul>
	 * <li>none of its currently allocated plannings may be closed. A planning is closed after it was allocated
	 * to say "stop processing this cargo" - completing the instruction anyway would freight exactly what the
	 * planner already called off.</li>
	 * <li>a delivery instruction may not be completed with zero active allocations - completing it anyway would
	 * freight nothing while printing a document whose report resolves everything through the (missing)
	 * allocation. Before allocations existed a 1:1 header FK made this unreachable by construction; removing the
	 * last planning off a drafted instruction now makes it reachable.</li>
	 * </ul>
	 * A transport order, which legitimately never has allocations, stays a no-op for BOTH rules - told apart from
	 * a delivery instruction via {@link ShipperTransportationDocSubTypeGuard} (task C5's mechanism), never by
	 * direction, {@code IsSOTrx}, or the presence of allocations.
	 */
	public Optional<ITranslatableString> getCompleteRejectionReason(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final ImmutableSet<DeliveryPlanningId> allocatedPlanningIds = deliveryPlanningRepository.getAllocatedPlanningIds(deliveryInstructionId);
		if (allocatedPlanningIds.isEmpty())
		{
			final I_M_ShipperTransportation deliveryInstruction = deliveryPlanningRepository.getInstructionById(deliveryInstructionId);
			if (shipperTransportationDocSubTypeGuard.isDeliveryInstruction(deliveryInstruction))
			{
				return Optional.of(TranslatableStrings.adMessage(MSG_M_Delivery_Planning_EmptyDeliveryInstruction));
			}
			return Optional.empty();
		}

		final ImmutableList<DeliveryPlanningId> closedPlanningIds = deliveryPlanningRepository.getByIds(allocatedPlanningIds).stream()
				.filter(I_M_Delivery_Planning::isClosed)
				.map(record -> DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID()))
				.collect(ImmutableList.toImmutableList());
		if (closedPlanningIds.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(TranslatableStrings.adMessage(MSG_M_Delivery_Planning_ClosedAllocatedPlannings, toIdList(closedPlanningIds)));
	}

	/**
	 * Why the given planning's {@code IsClosed} change to CLOSED must be refused, or empty when it may proceed.
	 * <p>
	 * Refused only when the planning is currently allocated to a COMPLETED delivery instruction - told apart from a
	 * draft one via the instruction's {@code DocStatus} directly, the same as {@link #getCompleteRejectionReason}.
	 */
	public Optional<ITranslatableString> getCloseRejectionReason(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		if (deliveryPlanningRepository.hasCompleteDeliveryInstruction(deliveryPlanningId))
		{
			return Optional.of(TranslatableStrings.adMessage(MSG_M_Delivery_Planning_CloseOnCompletedInstruction, deliveryPlanningId.getRepoId()));
		}
		return Optional.empty();
	}

	/**
	 * Reacts to a planning's {@code IsClosed} flipping to {@code true} (the {@code M_Delivery_Planning}
	 * AFTER_CHANGE(IsClosed) interceptor): refused outright via {@link #getCloseRejectionReason} when the planning
	 * is on a completed instruction; otherwise its allocation and shipping package are removed via the SAME
	 * primitive {@link #removeFrom} uses - closing says "stop processing this cargo", and remaining allocated to a
	 * draft instruction contradicts that. A planning on no instruction is left alone.
	 */
	public void onDeliveryPlanningClosed(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		getCloseRejectionReason(deliveryPlanningId)
				.ifPresent(reason -> {throw new AdempiereException(reason);});

		final ImmutableSet<DeliveryPlanningId> allocatedIds = deliveryPlanningRepository.getAllocatedInstructionIds(ImmutableList.of(deliveryPlanningId)).keySet();
		if (allocatedIds.isEmpty())
		{
			return;
		}

		deliveryPlanningRepository.deleteAllocations(allocatedIds);
		deliveryPlanningRepository.clearInstructionReference(allocatedIds);
	}

	/**
	 * Combines the selected delivery plannings into ONE delivery instruction: each planning gets its own
	 * allocation, its own shipping package and its own {@code ReleaseNo}, and the instruction lists them all.
	 * <p>
	 * All-or-nothing: {@link #getCombineRejectionReason(DeliveryPlanningList)} is evaluated first and throws for
	 * the whole selection, so no planning is left half-moved and no orphaned package survives.
	 * <p>
	 * The instruction's header is seeded from the FIRST planning in allocation order (earliest departure, then
	 * planning id) rather than from whichever row the query returned first - the plannings agree on every header
	 * field the admissibility rule covers, but not on the dates, so which one seeds them has to be decided rather
	 * than inherited from the encounter order.
	 *
	 * @param complete complete the instruction right away instead of leaving it a draft. A draft is the default:
	 * 		a combined instruction is assembled over days, so the planner says when it is final.
	 * @return the one delivery instruction that was created
	 */
	public ShipperTransportationId combine(
			@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter,
			final boolean complete)
	{
		final DeliveryPlanningList selectedDeliveryPlannings = getBySelection(selectedDeliveryPlanningsFilter);
		if (selectedDeliveryPlannings.isEmpty())
		{
			// an invariant, not a user-facing rejection: the process's precondition already refuses an empty
			// selection, and every rejection a planner can actually provoke is a translated message below
			throw new AdempiereException("No delivery planning selected");
		}

		getCombineRejectionReason(selectedDeliveryPlannings)
				.ifPresent(reason -> {throw new AdempiereException(reason);});

		final ImmutableList<DeliveryPlanningId> deliveryPlanningIds = selectedDeliveryPlannings.getIdsInAllocationOrder();

		// the header, plus the seed planning's allocation and shipping package (LineNo 10)
		final I_M_ShipperTransportation deliveryInstruction = deliveryPlanningRepository.generateDeliveryInstruction(
				createDeliveryInstructionRequest(deliveryPlanningIds.get(0)));
		final ShipperTransportationId deliveryInstructionId = ShipperTransportationId.ofRepoId(deliveryInstruction.getM_ShipperTransportation_ID());

		// the remaining plannings, handed over ALREADY SORTED because createAllocations numbers in the given order
		final ImmutableList<DeliveryPlanningAllocCreateRequest> furtherAllocations =
				createAllocCreateRequests(deliveryPlanningIds.subList(1, deliveryPlanningIds.size()));
		if (!furtherAllocations.isEmpty())
		{
			deliveryPlanningRepository.createAllocations(deliveryInstructionId, furtherAllocations);
		}

		if (complete)
		{
			docActionBL.processEx(deliveryInstruction, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}

		// one instruction, so one notification - which is also what tells the planner Combine ran and not Generate
		DeliveryInstructionUserNotificationsProducer.newInstance().notifyGenerated(deliveryInstruction);

		// every planning gets its OWN ReleaseNo, stamped from the instruction it now sits on
		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(deliveryPlanningIds, deliveryInstruction);

		return deliveryInstructionId;
	}

	/**
	 * The allocation requests for the given delivery plannings, in the order the ids were given - which is the
	 * order {@code createAllocations} hands out the {@code LineNo}s in.
	 * <p>
	 * Takes the whole collection rather than one id at a time deliberately: the records are batch-loaded ONCE, so
	 * a selection of N plannings costs one round trip instead of N, on a synchronous action the planner waits on.
	 * There is no single-id counterpart on purpose - offering one is how the per-row load got reintroduced on a
	 * second and then a third call site after it had already been removed from {@code getBySelection}.
	 */
	private ImmutableList<DeliveryPlanningAllocCreateRequest> createAllocCreateRequests(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		return deliveryPlanningRepository.getByIds(deliveryPlanningIds)
				.stream()
				.map(this::createAllocCreateRequest)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Takes the already-loaded record, not its id: the id-taking form would load, and this runs once per row of a
	 * whole selection.
	 */
	private DeliveryPlanningAllocCreateRequest createAllocCreateRequest(@NonNull final I_M_Delivery_Planning deliveryPlanningRecord)
	{
		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(deliveryPlanningRecord.getM_Delivery_Planning_ID());

		final ProductId productId = ProductId.ofRepoId(deliveryPlanningRecord.getM_Product_ID());
		final I_C_UOM uomToUse = getUomOrStockUom(deliveryPlanningRecord, productId);

		return DeliveryPlanningAllocCreateRequest.builder()
				.deliveryPlanningId(deliveryPlanningId)
				.productId(productId)
				.qtyLoaded(Quantity.of(deliveryPlanningRecord.getPlannedLoadedQuantity(), uomToUse))
				.qtyDischarged(Quantity.of(deliveryPlanningRecord.getPlannedDischargeQuantity(), uomToUse))
				.batchNo(deliveryPlanningRecord.getBatch())
				.orderLineId(OrderLineId.ofRepoIdOrNull(deliveryPlanningRecord.getC_OrderLine_ID()))
				.toBeFetched(DeliveryPlanningRepository.extractTransportDirection(deliveryPlanningRecord).hasReceipt())
				.build();
	}

	/**
	 * Why this selection cannot be added to a delivery instruction, or empty when it can.
	 * <p>
	 * Lives here rather than in the process's {@code checkPreconditionsApplicable} for the same two reasons
	 * {@link #getCombineRejectionReason(DeliveryPlanningList)} does: a cucumber step drives the rule the WebUI
	 * drives, and the reason on the disabled button is by construction the sentence {@link #addTo} throws.
	 * <p>
	 * Row eligibility first, then the target: a planner resolves "this row cannot go at all" before "it cannot go
	 * THERE". The target-side rules are the last two, and both need the plannings the target already holds to be
	 * read - which is why they cost nothing on the selection-change path, where there is no target yet.
	 *
	 * @param targetDeliveryInstructionId the instruction the planner picked, or {@code null} when the parameter
	 * 		dialog has not been shown yet - the precondition can only judge the selection, so it passes {@code null}
	 * 		and the target-side rules are evaluated when {@code addTo} runs.
	 */
	public Optional<ITranslatableString> getAddToRejectionReason(
			@NonNull final DeliveryPlanningList selectedDeliveryPlannings,
			@Nullable final ShipperTransportationId targetDeliveryInstructionId)
	{
		if (selectedDeliveryPlannings.anyClosed())
		{
			return Optional.of(TranslatableStrings.adMessage(
					MSG_M_Delivery_Planning_ClosedPlannings,
					toIdList(selectedDeliveryPlannings.closedOnes())));
		}

		// refused outright: not partially performed, and not silently skipping the offending rows either
		final DeliveryPlanningList onCompletedInstruction = onNonDraftInstruction(selectedDeliveryPlannings);
		if (!onCompletedInstruction.isEmpty())
		{
			return Optional.of(TranslatableStrings.adMessage(
					MSG_M_Delivery_Planning_OnCompletedDeliveryInstruction,
					toIdList(onCompletedInstruction)));
		}

		if (!selectedDeliveryPlannings.getSingleType().isPresent())
		{
			// the target picker offers the instructions of ONE direction, so a selection spanning two has no
			// target list to be offered at all
			return Optional.of(incompatibleMessage(ImmutableSet.of(AdmissibilityField.Direction)));
		}

		if (targetDeliveryInstructionId == null)
		{
			// the parameter dialog has not been shown yet, so the two target-side rules below cannot be evaluated;
			// they are, when addTo runs with the instruction the planner picked
			return Optional.empty();
		}

		if (!deliveryPlanningRepository.getDeliveryInstructionDocStatus(targetDeliveryInstructionId).isDrafted())
		{
			return Optional.of(TranslatableStrings.adMessage(MSG_M_Delivery_Planning_TargetInstructionNotDraft));
		}

		// The SAME admissibility rule Combine applies, over what the instruction would hold AFTERWARDS rather than
		// over the selection alone: the header holds one forwarder, one incoterm, one incoterm location, one means
		// of transportation and one loading and delivery address, so a planning whose own differ would end up under
		// a document that does not describe its cargo - wrong on the printed paperwork, wrong at the forwarder
		// handover, wrong at the pickup and delivery address. Judging the selection by itself is not enough, and
		// neither is the picker's direction filter: without this, a selection Combine refuses can be put on the
		// very instruction Combine created, one add-to at a time.
		final ImmutableSet<AdmissibilityField> mismatches = getAllocatedTo(targetDeliveryInstructionId)
				// a planning that is already on the target is in BOTH lists and is counted ONCE, so it is never
				// compared against itself and reported as differing from itself
				.union(selectedDeliveryPlannings)
				.admissibilityMismatches();
		if (!mismatches.isEmpty())
		{
			return Optional.of(incompatibleMessage(mismatches));
		}

		return Optional.empty();
	}

	/**
	 * Why nothing can be removed from a delivery instruction for this selection, or empty when it can.
	 * <p>
	 * Deliberately does NOT reject a closed planning: closing a planning says "stop processing this", which is
	 * exactly the situation in which taking it off the truck is the right correction.
	 */
	public Optional<ITranslatableString> getRemoveFromRejectionReason(@NonNull final DeliveryPlanningList selectedDeliveryPlannings)
	{
		final DeliveryPlanningList allocated = selectedDeliveryPlannings.allocatedOnes();
		if (allocated.isEmpty())
		{
			return Optional.of(TranslatableStrings.adMessage(
					MSG_M_Delivery_Planning_NotOnDeliveryInstruction,
					toIdList(selectedDeliveryPlannings)));
		}

		final DeliveryPlanningList onCompletedInstruction = onNonDraftInstruction(allocated);
		if (!onCompletedInstruction.isEmpty())
		{
			return Optional.of(TranslatableStrings.adMessage(
					MSG_M_Delivery_Planning_OnCompletedDeliveryInstruction,
					toIdList(onCompletedInstruction)));
		}

		return Optional.empty();
	}

	/**
	 * The plannings of the given selection whose delivery instruction is no longer a draft - which is what forbids
	 * both moving them off it and removing them from it.
	 */
	private DeliveryPlanningList onNonDraftInstruction(@NonNull final DeliveryPlanningList selectedDeliveryPlannings)
	{
		final ImmutableSet<ShipperTransportationId> deliveryInstructionIds = selectedDeliveryPlannings.stream()
				.map(DeliveryPlanning::getDeliveryInstructionId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableMap<ShipperTransportationId, DocStatus> docStatuses = deliveryPlanningRepository.getDeliveryInstructionDocStatuses(deliveryInstructionIds);

		return selectedDeliveryPlannings.stream()
				.filter(deliveryPlanning -> deliveryPlanning.getDeliveryInstructionId() != null)
				// an instruction the query did not return cannot be shown to be a draft, so it counts as one that
				// is not - the safe direction for a rule whose job is to forbid
				.filter(deliveryPlanning -> !docStatuses.getOrDefault(deliveryPlanning.getDeliveryInstructionId(), DocStatus.Unknown).isDrafted())
				.collect(DeliveryPlanningList.collect());
	}

	/**
	 * Puts the selected delivery plannings on the given DRAFT delivery instruction, taking each off whatever draft
	 * instruction it was on before.
	 * <p>
	 * All-or-nothing: the rejection is evaluated for the whole selection before anything is written, and
	 * the writes then run in one transaction, so a failure part-way leaves no planning moved, no shipping package
	 * orphaned and no {@code ReleaseNo} re-stamped. Per planning the order is delete-then-create, so the
	 * single-active-allocation index never sees two.
	 * <p>
	 * A planning already on the target is left alone: there is nothing to move, and its {@code ReleaseNo} already
	 * names that instruction.
	 */
	public void addTo(
			@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter,
			@NonNull final ShipperTransportationId targetDeliveryInstructionId)
	{
		final DeliveryPlanningList selectedDeliveryPlannings = getBySelection(selectedDeliveryPlanningsFilter);
		if (selectedDeliveryPlannings.isEmpty())
		{
			// an invariant, not a user-facing rejection: the process's precondition already refuses an empty
			// selection, and every rejection a planner can actually provoke is a translated message
			throw new AdempiereException("No delivery planning selected");
		}

		getAddToRejectionReason(selectedDeliveryPlannings, targetDeliveryInstructionId)
				.ifPresent(reason -> {throw new AdempiereException(reason);});

		// in allocation order, so the LineNos the target hands out continue in a decided order rather than the
		// query's encounter order
		final ImmutableList<DeliveryPlanningId> deliveryPlanningIds = selectedDeliveryPlannings.stream()
				.filter(deliveryPlanning -> !targetDeliveryInstructionId.equals(deliveryPlanning.getDeliveryInstructionId()))
				.map(DeliveryPlanning::getId)
				.collect(ImmutableList.toImmutableList());
		if (deliveryPlanningIds.isEmpty())
		{
			return;
		}

		trxManager.runInThreadInheritedTrx(() -> {
			final ImmutableList<DeliveryPlanningAllocCreateRequest> allocations = createAllocCreateRequests(deliveryPlanningIds);

			// the source allocation and its package are DELETED, not deactivated, so the target's insert has no
			// active row left to collide with on either partial unique index
			deliveryPlanningRepository.deleteAllocations(deliveryPlanningIds);
			deliveryPlanningRepository.createAllocations(targetDeliveryInstructionId, allocations);

			// re-stamped from the target: the old release number named a document the cargo has left
			deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(deliveryPlanningIds, targetDeliveryInstructionId);
		});
	}

	/**
	 * Takes the selected delivery plannings off the DRAFT delivery instruction they are on: allocation and
	 * shipping package are deleted, and the planning loses its {@code ReleaseNo}, so it can be planned again.
	 * <p>
	 * The instruction itself and its other plannings are untouched - which is the reason removal is not
	 * void-and-regenerate: a regenerated instruction is a new document, so it would re-stamp the release number of
	 * every planning that did not move.
	 * <p>
	 * Selected plannings that are on no instruction are skipped rather than failed: they are already in the state
	 * the planner asked for.
	 */
	public void removeFrom(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final DeliveryPlanningList selectedDeliveryPlannings = getBySelection(selectedDeliveryPlanningsFilter);
		if (selectedDeliveryPlannings.isEmpty())
		{
			throw new AdempiereException("No delivery planning selected");
		}

		getRemoveFromRejectionReason(selectedDeliveryPlannings)
				.ifPresent(reason -> {throw new AdempiereException(reason);});

		final ImmutableList<DeliveryPlanningId> deliveryPlanningIds = selectedDeliveryPlannings.allocatedOnes().getIdsInAllocationOrder();

		trxManager.runInThreadInheritedTrx(() -> {
			deliveryPlanningRepository.deleteAllocations(deliveryPlanningIds);
			deliveryPlanningRepository.clearInstructionReference(deliveryPlanningIds);
		});
	}

	/**
	 * Unlinks every planning currently allocated to the instruction (deactivating the allocations) and
	 * invalidates their invoice candidates - the same batch load {@link #invalidateInvoiceCandidatesFor(ShipperTransportationId)}
	 * uses, but the affected ids are resolved BEFORE the deactivation, not after: the invalidation is
	 * deferred to after-commit, and by then the deactivation this same call performs has already made
	 * {@link DeliveryPlanningRepository#getAllocatedPlanningIds(ShipperTransportationId)} come back empty.
	 * Re-deriving the ids inside the deferred closure would silently invalidate nothing.
	 */
	public void unlinkDeliveryPlannings(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final ImmutableSet<DeliveryPlanningId> allocatedPlanningIds = deliveryPlanningRepository.getAllocatedPlanningIds(deliveryInstructionId);

		deliveryPlanningRepository.unlinkDeliveryPlannings(deliveryInstructionId);

		if (!allocatedPlanningIds.isEmpty())
		{
			trxManager.runAfterCommit(() -> deliveryPlanningRepository.getByIds(allocatedPlanningIds)
					.forEach(this::invalidateInvoiceCandidatesFor));
		}
	}

	public void regenerateDeliveryInstructions(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final ICompositeQueryFilter<I_M_Delivery_Planning> dpFilter = deliveryPlanningRepository
				.excludeDeliveryPlanningsWithoutInstruction(selectedDeliveryPlanningsFilter);

		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = deliveryPlanningRepository.extractDeliveryPlannings(dpFilter);
		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();

			// first void the existent delivery instructions
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(deliveryPlanningRecord.getM_Delivery_Planning_ID());
			voidLinkedDeliveryInstructions(deliveryPlanningId);

			// then generate a new one, always completed - regenerate has no draft-then-complete option
			final DeliveryInstructionCreateRequest deliveryInstructionRequest = createDeliveryInstructionRequest(deliveryPlanningId);
			generateDeliveryInstruction(deliveryInstructionRequest, true);
		}
	}

	private void voidLinkedDeliveryInstructions(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final Iterator<I_M_ShipperTransportation> deliveryInstructionsIterator = deliveryPlanningRepository.retrieveForDeliveryPlanning(deliveryPlanningId);
		while (deliveryInstructionsIterator.hasNext())
		{
			final I_M_ShipperTransportation deliveryInstructionRecord = deliveryInstructionsIterator.next();

			docActionBL.processEx(deliveryInstructionRecord, IDocument.ACTION_Void, IDocument.STATUS_Voided);
		}
	}

	/**
	 * Cancels every selected planning that carries a {@code ReleaseNo}, per row: a closed one is left untouched and
	 * named in {@link DeliveryPlanningCancelResult#getSkippedClosedIds()} rather than aborting the whole selection
	 * (AC14) - the open ones are still voided and cancelled.
	 */
	public DeliveryPlanningCancelResult cancelDelivery(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final ICompositeQueryFilter<I_M_Delivery_Planning> dpFilter = deliveryPlanningRepository
				.excludeDeliveryPlanningsWithoutReleaseNo(selectedDeliveryPlanningsFilter);

		final ImmutableList.Builder<DeliveryPlanningId> cancelledIds = ImmutableList.builder();
		final ImmutableList.Builder<DeliveryPlanningId> skippedClosedIds = ImmutableList.builder();

		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = deliveryPlanningRepository.extractDeliveryPlannings(dpFilter);

		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(deliveryPlanningRecord.getM_Delivery_Planning_ID());

			if (deliveryPlanningRecord.isClosed())
			{
				skippedClosedIds.add(deliveryPlanningId);
				continue;
			}

			// first void the existent delivery instructions
			voidLinkedDeliveryInstructions(deliveryPlanningId);

			// re-read: the void's unlink cascade may have cleared ReleaseNo/M_ShipperTransportation_ID on this
			// same row, and the pre-void record in hand here must not overwrite that with stale values
			deliveryPlanningRepository.cancelDeliveryPlanning(deliveryPlanningRepository.getById(deliveryPlanningId));
			cancelledIds.add(deliveryPlanningId);
		}

		return DeliveryPlanningCancelResult.builder()
				.cancelledIds(cancelledIds.build())
				.skippedClosedIds(skippedClosedIds.build())
				.build();
	}

	public Optional<DeliveryPlanningReceiptInfo> getReceiptInfoIfHasReceipt(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryPlanningRepository.getReceiptInfoIfHasReceipt(deliveryPlanningId);
	}

	public DeliveryPlanningReceiptInfo getReceiptInfo(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryPlanningRepository.getReceiptInfoIfHasReceipt(deliveryPlanningId)
				.orElseThrow(() -> new AdempiereException("Expected the delivery planning to have a receipt"));
	}

	public void updateReceiptInfoById(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Consumer<DeliveryPlanningReceiptInfo> updater)
	{
		final DeliveryStatusColorPalette colorPalette = getColorPalette();
		deliveryPlanningRepository.updateReceiptInfoById(
				deliveryPlanningId,
				receiptInfo -> {
					updater.accept(receiptInfo);
					receiptInfo.updateReceivedStatusColor(colorPalette);
				});
	}

	public Optional<DeliveryPlanningShipmentInfo> getShipmentInfoIfOutgoingType(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryPlanningRepository.getShipmentInfoIfOutgoingType(deliveryPlanningId);
	}

	public DeliveryPlanningShipmentInfo getShipmentInfo(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryPlanningRepository.getShipmentInfoIfOutgoingType(deliveryPlanningId)
				.orElseThrow(() -> new AdempiereException("Expected to be an outgoing delivery planning"));
	}

	public void updateShipmentInfoById(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Consumer<DeliveryPlanningShipmentInfo> updater)
	{
		final DeliveryStatusColorPalette colorPalette = getColorPalette();
		deliveryPlanningRepository.updateShipmentInfoById(
				deliveryPlanningId,
				shipmentInfo -> {
					updater.accept(shipmentInfo);
					shipmentInfo.updateShippedStatusColor(colorPalette);
				});
	}

	public <T> T getShipmentOrReceiptInfo(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Function<DeliveryPlanningReceiptInfo, T> receiptInfoMapper,
			@NonNull final Function<DeliveryPlanningShipmentInfo, T> shipmentInfoMapper)
	{
		return deliveryPlanningRepository.getShipmentOrReceiptInfo(deliveryPlanningId, receiptInfoMapper, shipmentInfoMapper);
	}

	public List<DeliveryPlanningShipmentInfo> getShipmentInfosByOrderLineIds(final Set<OrderAndLineId> salesOrderLineIds)
	{
		return deliveryPlanningRepository.getShipmentInfosByOrderLineIds(salesOrderLineIds);
	}

	public boolean hasCompleteDeliveryInstruction(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryPlanningRepository.hasCompleteDeliveryInstruction(deliveryPlanningId);
	}

	public boolean isExistsBlockedPartnerDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return false;
	}

	public boolean hasBlockedBPartner(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return false;
	}

	public void validateDeliveryPlanning(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		if (hasBlockedBPartner(deliveryPlanningId))
		{
			throw new AdempiereException(MSG_M_Delivery_Planning_BlockedPartner);
		}
	}

	public void invalidateInvoiceCandidatesFor(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		Optional.ofNullable(OrderLineId.ofRepoIdOrNull(deliveryPlanning.getC_OrderLine_ID()))
				.map(orderLineBL::getOrderLineById)
				.ifPresent(invoiceCandidateHandlerBL::invalidateCandidatesFor);
	}

	public void invalidateInvoiceCandidatesFor(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		invalidateInvoiceCandidatesFor(deliveryPlanningRepository.getById(deliveryPlanningId));
	}

	/**
	 * Invalidates the invoice candidates of EVERY planning currently allocated to the given instruction, in ONE
	 * batch load - not the legacy single {@code M_ShipperTransportation.M_Delivery_Planning_ID}
	 * header FK the interceptor used to read, which silently skipped every allocation but the first on an
	 * aggregated instruction, and not a per-planning loop either: that would be a fifth N+1 in this exact code
	 * family, after four earlier fixes.
	 * <p>
	 * A transport order, or an instruction with no allocations, is a no-op: {@code getAllocatedPlanningIds} comes
	 * back empty and the batch load never runs.
	 */
	public void invalidateInvoiceCandidatesFor(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final ImmutableSet<DeliveryPlanningId> allocatedPlanningIds = deliveryPlanningRepository.getAllocatedPlanningIds(deliveryInstructionId);
		if (allocatedPlanningIds.isEmpty())
		{
			return;
		}

		deliveryPlanningRepository.getByIds(allocatedPlanningIds)
				.forEach(this::invalidateInvoiceCandidatesFor);
	}

	private void validateDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		if (isExistsBlockedPartnerDeliveryPlannings(selectedDeliveryPlanningsFilter))
		{
			throw new AdempiereException(MSG_M_Delivery_Planning_BlockedPartner);
		}
	}

	public Optional<MeansOfTransportation> getMeansOfTransportationByDeliveryPlanningId(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_Delivery_Planning deliveryPlanning = deliveryPlanningRepository.getById(deliveryPlanningId);
		return MeansOfTransportationId.optionalOfRepoId(deliveryPlanning.getM_MeansOfTransportation_ID())
				.map(meansOfTransportationService::getById);
	}
}
