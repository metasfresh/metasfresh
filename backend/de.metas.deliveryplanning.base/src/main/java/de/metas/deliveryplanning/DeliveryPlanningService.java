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
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.deliveryplanning.DeliveryPlanningList.AggregationKeyField;
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
import de.metas.interfaces.I_C_OrderLine;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.location.CountryId;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.shipping.Shipper;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.TransportDirection;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.ShippingPackageId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryPlanningService
{
	public static final AdMessageKey MSG_M_Delivery_Planning_AllClosed = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.AllClosed");

	/** Rejects acting on a closed planning; also the per-row skip report of {@link #cancelDelivery}. */
	public static final AdMessageKey MSG_M_Delivery_Planning_Closed = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.Closed");

	/**
	 * The per-row report of {@link #cancelDelivery} for a planning that was still allocated when the cancel ran:
	 * it IS cancelled (voided, closed, cancelled order status) same as any other row, but its planned figures are
	 * committed cargo and are named here instead of being silently left as they were.
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_CancelAllocated = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.CancelAllocated");

	/** The mirror of {@link #MSG_M_Delivery_Planning_Closed}: rejects RE-OPENING a planning that is still open. */
	public static final AdMessageKey MSG_M_Delivery_Planning_Open = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.Open");

	public static final AdMessageKey MSG_M_Delivery_Planning_AtLeastOnePerOrderLine = AdMessageKey.of("de.metas.deliveryplanning.M_Delivery_Planning_AtLeastOnePerOrderLine");

	public static final AdMessageKey MSG_M_Delivery_Planning_AlreadyReferenced = AdMessageKey.of("de.metas.deliveryplanning.M_Delivery_Planning_AlreadyReferenced");

	/** A delivery instruction is cancelled or closed, never deleted. Raised on the package leg, where deletes arrive. */
	public static final AdMessageKey MSG_M_ShippingPackage_Allocated = AdMessageKey.of("de.metas.deliveryplanning.M_ShippingPackage.Allocated");

	public static final AdMessageKey MSG_M_Delivery_Planning_NoForwarder = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.NoForwarder");
	public static final AdMessageKey MSG_M_Delivery_Planning_AllHaveReleaseNo = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.AllHaveReleaseNo");
	public static final AdMessageKey MSG_M_Delivery_Planning_WhithOutReleaseNo = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.WhithOutReleaseNo");
	public static final AdMessageKey MSG_M_Delivery_Planning_BlockedPartner = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.NoBlockedPartner");
	public static final AdMessageKey MSG_M_Delivery_Planning_SalesOrderFullyDelivered = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.SalesOrderFullyDelivered");
	public static final AdMessageKey MSG_M_Delivery_Planning_PurchaseOrderFullyDelivered = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.PurchaseOrderFullyDelivered");
	public static final String PARAM_AdditionalLines = "AdditionalLines";

	/** One message for the whole selection, naming EVERY field it disagrees on - never the first one found. */
	public static final AdMessageKey MSG_M_Delivery_Planning_IncompatibleSelection = AdMessageKey.of("de.metas.deliveryplanning.CombineIntoDeliveryInstruction.IncompatibleSelection");
	public static final AdMessageKey MSG_M_Delivery_Planning_ClosedPlannings = AdMessageKey.of("de.metas.deliveryplanning.CombineIntoDeliveryInstruction.ClosedPlannings");
	public static final AdMessageKey MSG_M_Delivery_Planning_AlreadyOnDeliveryInstruction = AdMessageKey.of("de.metas.deliveryplanning.CombineIntoDeliveryInstruction.AlreadyOnDeliveryInstruction");

	/**
	 * Says "completed" rather than "not a draft" because that is the only state reachable here: voiding an
	 * instruction deactivates its allocations, so an active allocation to a non-draft instruction means a completed one.
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_OnCompletedDeliveryInstruction = AdMessageKey.of("de.metas.deliveryplanning.DeliveryInstruction.OnCompletedInstruction");
	public static final AdMessageKey MSG_M_Delivery_Planning_TargetInstructionNotDraft = AdMessageKey.of("de.metas.deliveryplanning.AddToDeliveryInstruction.TargetNotDraft");

	/**
	 * Refuses ADDING a planning that already sits on an instruction, naming Move as the action that applies.
	 * Distinct from {@link #MSG_M_Delivery_Planning_AlreadyOnDeliveryInstruction}, which refuses COMBINING it into a
	 * new instruction.
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_AlreadyOnDeliveryInstruction_UseMove = AdMessageKey.of("de.metas.deliveryplanning.AddToDeliveryInstruction.AlreadyOnDeliveryInstruction");

	/** Refuses taking a planning off an instruction it is not on. Shared by Remove from and Move to. */
	public static final AdMessageKey MSG_M_Delivery_Planning_NotOnDeliveryInstruction = AdMessageKey.of("de.metas.deliveryplanning.RemoveFromDeliveryInstruction.NotOnDeliveryInstruction");

	/**
	 * Rejects COMPLETING an instruction that holds a planning closed after it was allocated. Distinct from
	 * {@link #MSG_M_Delivery_Planning_ClosedPlannings}, which rejects a selection before it is put on an instruction.
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_ClosedAllocatedPlannings = AdMessageKey.of("de.metas.deliveryplanning.CompleteDeliveryInstruction.ClosedAllocatedPlannings");

	/**
	 * Rejects RE-ACTIVATING an instruction that holds a planning closed after it was allocated - the sibling of
	 * {@link #MSG_M_Delivery_Planning_ClosedAllocatedPlannings}, on the other document action. Closed says "I am
	 * done with this cargo, leave it alone", so re-opening the document it rides on for editing is refused too.
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_ReActivateClosedAllocatedPlannings = AdMessageKey.of("de.metas.deliveryplanning.ReActivateDeliveryInstruction.ClosedAllocatedPlannings");

	/**
	 * Rejects VOIDING an instruction that holds a planning closed after it was allocated - the third sibling of
	 * {@link #MSG_M_Delivery_Planning_ClosedAllocatedPlannings}, and the one with the sharpest reason: voiding
	 * RELEASES every allocation, so it would deactivate the closed planning's allocation, drop its release number
	 * and reset its dates - a change to the very record that said "leave me alone".
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_VoidClosedAllocatedPlannings = AdMessageKey.of("de.metas.deliveryplanning.VoidDeliveryInstruction.ClosedAllocatedPlannings");

	/**
	 * Refuses completing a delivery instruction that holds no planning, which the reports would print as a blank
	 * document. Scoped to an actual delivery instruction via {@link ShipperTransportationDocSubTypeGuard}, so a
	 * transport order - which never has allocations - stays unaffected.
	 */
	public static final AdMessageKey MSG_M_Delivery_Planning_EmptyDeliveryInstruction = AdMessageKey.of("de.metas.deliveryplanning.CompleteDeliveryInstruction.EmptyDeliveryInstruction");

	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull private final ShipperRepository shipperRepository;
	@NonNull private final DeliveryPlanningRepository deliveryPlanningRepository;
	@NonNull private final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository;
	@NonNull private final DeliveryInstructionService deliveryInstructionService;
	@NonNull private final DeliveryStatusColorPaletteService deliveryStatusColorPaletteService;
	@NonNull private final DimensionService dimensionService;
	@NonNull private final MeansOfTransportationService meansOfTransportationService;

	/** Tells the two {@code M_ShipperTransportation} document roles apart by {@code C_DocType.DocSubType}. */
	@NonNull private final ShipperTransportationDocSubTypeGuard shipperTransportationDocSubTypeGuard;

	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	@NonNull private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);


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
	}

	/**
	 * Refuses to delete a planning that an ACTIVE {@code M_Delivery_Planning_Alloc} still points at -
	 * unconditionally, regardless of who is deleting it. Asked of the allocation table rather than of
	 * {@code ReleaseNo}, which only mirrors it; see
	 * {@link DeliveryPlanningAllocRepository#hasActiveAllocation(DeliveryPlanningId)}.
	 * <p>
	 * The allocation's shipping package is mandatory-FKed to a still-live instruction, so letting the delete through
	 * would strand that instruction's cargo. A RETIRED allocation is the opposite case and must NOT be refused; it
	 * is removed explicitly by {@link #deleteAllocationsFor(DeliveryPlanningId)}.
	 */
	public void assertNotCurrentlyAllocated(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		if (deliveryPlanningAllocRepository.hasActiveAllocation(DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID())))
		{
			throw new AdempiereException(MSG_M_Delivery_Planning_AlreadyReferenced);
		}
	}

	/**
	 * The allocation-side cleanup a delete of the given planning owes: its retired allocation rows go with it.
	 * <p>
	 * Called from the planning's {@code TYPE_BEFORE_DELETE} interceptor immediately after
	 * {@link #assertNotCurrentlyAllocated} has refused the live case, so only retired history is ever removed.
	 */
	public void deleteAllocationsFor(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		deliveryPlanningAllocRepository.deleteAllocationsFor(ImmutableList.of(deliveryPlanningId));
	}

	/**
	 * Refuses to delete a shipping package that any {@code M_Delivery_Planning_Alloc} points at - active or
	 * retired - naming the delivery instruction to cancel or close instead.
	 * <p>
	 * Deleting an instruction is reachable - both tables carry {@code IsDeleteable='Y'} - and without this guard it
	 * fails on a raw constraint violation naming nothing the operator can act on. Guarding the PACKAGE also guards
	 * the instruction, because an instruction delete force-deletes its packages and so reaches this guard first; a
	 * second guard on the instruction itself would be unreachable.
	 * <p>
	 * Scoped by construction rather than by a filter: only delivery planning creates allocations, so the
	 * transport-order and handling-units packages sharing {@code M_ShippingPackage} never match.
	 */
	public void assertShippingPackageNotAllocated(@NonNull final I_M_ShippingPackage shippingPackage)
	{
		final ShippingPackageId shippingPackageId = ShippingPackageId.ofRepoIdOrNull(shippingPackage.getM_ShippingPackage_ID());
		if (shippingPackageId == null)
		{
			// not saved yet, so nothing can point at it
			return;
		}

		deliveryPlanningAllocRepository.getInstructionIdByShippingPackageId(shippingPackageId)
				.ifPresent(deliveryInstructionId -> {
					final String documentNo = deliveryInstructionService.getById(deliveryInstructionId).getDocumentNo();
					throw new AdempiereException(TranslatableStrings.adMessage(MSG_M_ShippingPackage_Allocated, documentNo));
				});
	}

	private DeliveryPlanningCreateRequest createRequest(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Quantity plannedLoadedQty,
			@NonNull final Quantity plannedDischargeQty)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningRepository.getById(deliveryPlanningId);
		final OrgId orgId = OrgId.ofRepoId(deliveryPlanningRecord.getAD_Org_ID());

		final ProductId productId = ProductId.ofRepoId(deliveryPlanningRecord.getM_Product_ID());
		final I_C_UOM uomToUse = getUomOrStockUom(deliveryPlanningRecord, productId);

		final Dimension dimension = dimensionService.getFromRecord(deliveryPlanningRecord);

		final TransportDirection transportDirection = DeliveryPlanningRepository.extractTransportDirection(deliveryPlanningRecord);

		// D22/Task Q7c: a split-created planning is a CREATED planning, not a copy of the target - it is
		// seeded exactly as GenerateIncomingDeliveryPlanningCommand seeds a fresh one, never by copying the
		// target's actuals (that fabricated a received/loaded quantity nothing was ever received or loaded
		// against, and multiplied it across every sibling). Inbound/dropship: ActualLoadQty starts equal to
		// this NEW planning's own planned load, because nothing ever reports the vendor's load - same rule
		// as at creation, kept in step afterwards by the interceptor in interceptor/M_Delivery_Planning.java.
		// Outgoing is unspecified by this task, so it keeps the zero a fresh planning has always started
		// with. ActualDischargeQuantity always starts empty - the receipt owns it, and the new planning has
		// received nothing.
		final Quantity actualLoadedQty = transportDirection.isIncomingOrDropship()
				? plannedLoadedQty
				: Quantity.zero(uomToUse);

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
				.transportDirection(transportDirection)
				.orderStatus(OrderStatus.ofNullableCode(deliveryPlanningRecord.getOrderStatus()))
				.meansOfTransportationId(MeansOfTransportationId.ofRepoIdOrNull(deliveryPlanningRecord.getM_MeansOfTransportation_ID()))
				.qtyOrdered(Quantity.of(deliveryPlanningRecord.getQtyOrdered(), uomToUse))
				.qtyTotalOpen(Quantity.of(deliveryPlanningRecord.getQtyTotalOpen(), uomToUse))
				.actualLoadedQty(actualLoadedQty)

				.plannedLoadedQty(plannedLoadedQty)
				.plannedDischargeQty(plannedDischargeQty)
				.actualDischargeQty(Quantity.zero(uomToUse))

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

		// Quantity allocated to a delivery instruction is committed cargo (D8/AC12/TC12): once the target is
		// allocated, its own planned figures are a FIXED POINT of the split - never rewritten as a side effect -
		// and the new plannings share only what the order line still has uncommitted overall. Q3's divide (target
		// gets a share too, from its own current figure) is the single exception to that rule, reserved for the
		// unallocated case.
		final boolean targetIsAllocated = deliveryPlanningAllocRepository.hasActiveAllocation(deliveryPlanningId);

		final Quantity openQty = getOpenQty(deliveryPlanningId, targetIsAllocated);

		final Quantity newPlanningLoadedQty;
		final Quantity newPlanningLoadedQtyRemainder;
		final Quantity newPlanningDischargeQty;
		final Quantity newPlanningDischargeQtyRemainder;

		if (targetIsAllocated)
		{
			// The target's own planned figures are untouched (D8) - no repository write here. The new plannings
			// split whatever remains uncommitted on the order line as a whole, floored at 0 by getOpenQty; "nothing
			// remains" still creates the requested plannings, carrying 0, rather than refusing or erroring.
			newPlanningLoadedQty = openQty.divide(BigDecimal.valueOf(additionalLines), 0, RoundingMode.DOWN);
			// The target is untouchable here (unlike the unallocated branch below, which folds its remainder back
			// into the target), so the DOWN-rounding remainder would otherwise vanish - e.g. openQty=10 over 3 new
			// plannings gives 3+3+3=9, one unit silently lost off the order line. Handed to the LAST planning
			// created by the loop below (fix round 1, Task Q5).
			newPlanningLoadedQtyRemainder = openQty.subtract(newPlanningLoadedQty.multiply(additionalLines));

			// The discharge pair follows the SAME pool rule as load (Task Q8) - a discharge pool exists exactly
			// like the load one (Q3's "no order-line-relative pool on the discharge side" is superseded). The
			// target's own discharge figure is committed cargo and stays untouched, same as its load figure; the
			// new plannings share what remains, DOWN-rounded with the remainder on the last one, same as load.
			final Quantity openDischargeQty = getPlannedDischargeQty(deliveryPlanningId, true);
			newPlanningDischargeQty = openDischargeQty.divide(BigDecimal.valueOf(additionalLines), 0, RoundingMode.DOWN);
			newPlanningDischargeQtyRemainder = openDischargeQty.subtract(newPlanningDischargeQty.multiply(additionalLines));
		}
		else
		{
			final Quantity fraction = openQty.divide(BigDecimal.valueOf(additionalLines + 1), 0, RoundingMode.DOWN);

			final Quantity remainder = openQty.subtract(fraction.multiply(additionalLines + 1));
			// Two round-trips (getById+save each): DeliveryPlanningRepository has no single-record "set several
			// columns at once" method, and adding one just to merge these two writes would widen its API for a
			// non-hot-path call - left as-is per review (Task Q3, fix round 1).
			deliveryPlanningRepository.setPlannedLoadedQuantity(deliveryPlanningId, fraction.add(remainder));
			newPlanningLoadedQty = fraction;

			final Quantity dischargeQty = getPlannedDischargeQty(deliveryPlanningId, false);
			final Quantity dischargeFraction = dischargeQty.divide(BigDecimal.valueOf(additionalLines + 1), 0, RoundingMode.DOWN);
			final Quantity dischargeRemainder = dischargeQty.subtract(dischargeFraction.multiply(additionalLines + 1));
			deliveryPlanningRepository.setPlannedDischargeQuantity(deliveryPlanningId, dischargeFraction.add(dischargeRemainder));
			newPlanningDischargeQty = dischargeFraction;
			// Unallocated: the target itself absorbs the DOWN-rounding remainder above, so every new planning gets
			// the plain fraction and there is nothing left over to hand to any of them here.
			newPlanningLoadedQtyRemainder = Quantity.zero(openQty.getUOM());
			newPlanningDischargeQtyRemainder = Quantity.zero(openQty.getUOM());
		}

		for (int i = 0; i < additionalLines; i++)
		{
			// The last planning created carries the allocated branch's remainder (zero on the unallocated branch)
			// so the new plannings' figures still sum to the distributed pool - both ends, not just load
			// (Task Q8): with more than one additional line, a dropped discharge remainder would be exactly as
			// invisible as the load-side defect fix round 1 caught.
			final boolean isLastNewPlanning = i == additionalLines - 1;
			final Quantity loadedQtyForThisPlanning = isLastNewPlanning
					? newPlanningLoadedQty.add(newPlanningLoadedQtyRemainder)
					: newPlanningLoadedQty;
			final Quantity dischargeQtyForThisPlanning = isLastNewPlanning
					? newPlanningDischargeQty.add(newPlanningDischargeQtyRemainder)
					: newPlanningDischargeQty;

			final DeliveryPlanningCreateRequest request = createRequest(deliveryPlanningId, loadedQtyForThisPlanning, dischargeQtyForThisPlanning);

			deliveryPlanningRepository.generateDeliveryPlanning(request);
		}
	}

	/**
	 * The discharge-pair sibling of {@link #getOpenQty}: the order line's remaining distributable pool for the
	 * DISCHARGE pair, following the SAME rule (owner, 2026-09-02, "The distributable pool") - a split
	 * distributes what is left of the order line, and a sibling consumes its effective quantity: its actual once
	 * one is recorded, otherwise its planned figure.
	 * <p>
	 * Supersedes Task Q3's comment here claiming "there is no order-line-relative pool on the discharge side" -
	 * a discharge pool exists, exactly like the load one; that reasoning was correct under Q3's instructions and
	 * is superseded by this per-end rule (Task Q8).
	 */
	private Quantity getPlannedDischargeQty(final DeliveryPlanningId deliveryPlanningId, final boolean targetIsAllocated)
	{
		return resolveDistributablePool(deliveryPlanningId, targetIsAllocated, DeliveryPlanningList.PoolEnd.DISCHARGE);
	}

	/**
	 * The order line's remaining distributable pool for {@code deliveryPlanningId}'s split (D8/AC12), for the
	 * LOAD pair: {@code QtyOrdered} minus what every OTHER planning of the line already claims, minus the
	 * target's own claim TOO once it is allocated - committed cargo is excluded from what a split may hand out,
	 * same as any other planning's share. Unallocated, the target is excluded from the sum instead, exactly as
	 * before Task Q5: its own share is still up for redistribution, which is what lets
	 * {@link #createAdditionalDeliveryPlannings} give it a slice of this same pool.
	 * <p>
	 * {@code targetIsAllocated} is handed in rather than queried here so the caller - which already needs the same
	 * fact to decide whether to rewrite the target's own figure - pays for {@link
	 * DeliveryPlanningAllocRepository#hasActiveAllocation} once, not twice.
	 */
	private Quantity getOpenQty(final DeliveryPlanningId deliveryPlanningId, final boolean targetIsAllocated)
	{
		return resolveDistributablePool(deliveryPlanningId, targetIsAllocated, DeliveryPlanningList.PoolEnd.LOAD);
	}

	/**
	 * The ONE pool rule (owner, 2026-09-02, "The distributable pool"), shared by {@link #getOpenQty} (load) and
	 * {@link #getPlannedDischargeQty} (discharge): the arithmetic itself lives in
	 * {@link DeliveryPlanningList#openPlanQty}, loaded once per call via
	 * {@link DeliveryPlanningRepository#getByOrderLineId} - unit-tested there without a database. Floored at 0
	 * HERE, not in the shared calculation: a negative pool is not distributable (D16), so the clamp belongs to
	 * this split-facing use of the figure, never to a display column that may legitimately show a negative
	 * (over-planned/over-delivered signals the line's state, per D16).
	 */
	private Quantity resolveDistributablePool(
			final DeliveryPlanningId deliveryPlanningId,
			final boolean targetIsAllocated,
			final DeliveryPlanningList.PoolEnd end)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningRepository.getById(deliveryPlanningId);
		final I_C_UOM uom = uomDAO.getById(deliveryPlanningRecord.getC_UOM_ID());

		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(deliveryPlanningRecord.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			// the delivery planning has no order line => nothing to distribute
			return Quantity.zero(uom);
		}

		final DeliveryPlanningList orderLinePlannings = deliveryPlanningRepository.getByOrderLineId(orderLineId);
		final DeliveryPlanningId excludePlanningId = targetIsAllocated ? null : deliveryPlanningId;

		return orderLinePlannings.openPlanQty(excludePlanningId, end).toZeroIfNegative();
	}

	/**
	 * Keeps {@code QtyTotalOpen}/{@code QtyTotalOpenPlanned} live (Task Q8) for the order line the given planning
	 * sits on - called from the {@code M_Delivery_Planning} interceptor on every write path that changes a
	 * planned/actual figure or adds a planning to the line, so every such path recomputes through this ONE
	 * choke point rather than each caller repeating the arithmetic.
	 */
	public void recomputeOpenQuantitiesForOrderLine(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(deliveryPlanning.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			// not based on any order line -> QtyTotalOpen/QtyTotalOpenPlanned have nothing to be computed from
			return;
		}
		deliveryPlanningRepository.recomputeOpenQuantitiesForOrderLine(orderLineId);
	}

	/**
	 * Pushes a quantity change on ONE planning out to the delivery instruction line(s) that mirror it, so an
	 * already-open Lieferanweisungen document refreshes its Versandpaket row with no manual reload
	 * (Task Q14, TC11). See {@link DeliveryInstructionLineCacheInvalidation} for why the generic
	 * {@code AD_SQLColumn_SourceTableColumn} invalidation cannot reach that row.
	 */
	public void invalidateDeliveryInstructionLinesFor(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryInstructionService.invalidateDeliveryInstructionLinesFor(
				DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID()));
	}

	/**
	 * Write-back for the generate-receipt process: a receipt reads/occupies the discharge end, so the qty the
	 * operator confirmed at generation time becomes the planning's new {@code PlannedDischargeQuantity} (spec
	 * direction rule, Task Q12). Kept as a thin passthrough on the service so the generate processes reach this
	 * repository write through their one existing collaborator, never the repository directly.
	 */
	public void setPlannedDischargeQuantity(@NonNull final DeliveryPlanningId deliveryPlanningId, @NonNull final Quantity quantity)
	{
		deliveryPlanningRepository.setPlannedDischargeQuantity(deliveryPlanningId, quantity);
	}

	/**
	 * Write-back for the generate-shipment process: the load-side sibling of
	 * {@link #setPlannedDischargeQuantity} - a shipment reads/occupies the load end.
	 */
	public void setPlannedLoadedQuantity(@NonNull final DeliveryPlanningId deliveryPlanningId, @NonNull final Quantity quantity)
	{
		deliveryPlanningRepository.setPlannedLoadedQuantity(deliveryPlanningId, quantity);
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

		// thread-inherited, not a new isolated trx: closeSelectedDeliveryPlannings runs @RunOutOfTrx (no ambient
		// trx exists here), so this BINDS one for the whole write loop rather than letting each row's save()
		// commit on its own - the repository refuses an already-closed row over the WHOLE selection before it
		// writes any of them, and that all-or-nothing needs one transaction to roll back into.
		trxManager.runInThreadInheritedTrx(() -> deliveryPlanningRepository.closeSelectedDeliveryPlannings(
				selectedDeliveryPlanningsFilter,
				MSG_M_Delivery_Planning_Closed));
	}

	public void reOpenSelectedDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		validateDeliveryPlannings(selectedDeliveryPlanningsFilter);
		deliveryPlanningRepository.reOpenSelectedDeliveryPlannings(selectedDeliveryPlanningsFilter, MSG_M_Delivery_Planning_Open);
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

		final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionService.generateDeliveryInstruction(deliveryInstructionRequest);

		if (complete)
		{
			docActionBL.processEx(deliveryInstruction, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}

		deliveryInstructionUserNotificationsProducer
				.notifyGenerated(deliveryInstruction);

		// No explicit CacheMgt reset here: the saveRecord below already invalidates this record's caches, and defers
		// the broadcast to commit, which a manual reset would not.
		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(ImmutableSet.of(deliveryPlanningId), deliveryInstruction);
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
				deliveryPlanningAllocRepository.getAllocationsByPlanningId(
						deliveryPlanningRecords.stream()
								.map(record -> DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID()))
								.collect(ImmutableSet.toImmutableSet())));
	}

	/**
	 * The delivery plannings a delivery instruction currently holds, as the same in-memory list a selection is
	 * judged as - so the add-to rule can be answered against what the instruction would hold AFTERWARDS.
	 * <p>
	 * Two batch loads and not a single per-row one: the instruction's ACTIVE allocations, then the planning
	 * records behind them. The allocations are carried over from the first load rather than queried a second
	 * time - they are exactly what was asked for.
	 */
	private DeliveryPlanningList getAllocatedTo(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final ImmutableList<DeliveryPlanningAlloc> allocations = deliveryPlanningAllocRepository.getAllocationsOfInstruction(deliveryInstructionId);
		if (allocations.isEmpty())
		{
			return DeliveryPlanningList.EMPTY;
		}

		final ImmutableSet<DeliveryPlanningId> allocatedIds = allocations.stream()
				.map(DeliveryPlanningAlloc::getDeliveryPlanningId)
				.collect(ImmutableSet.toImmutableSet());

		return toDeliveryPlanningList(
				deliveryPlanningRepository.getByIds(allocatedIds),
				Multimaps.index(allocations, DeliveryPlanningAlloc::getDeliveryPlanningId));
	}

	/**
	 * The given records as the in-memory list every aggregation rule is answered against, with the addresses they
	 * are read from batch-loaded ONCE for the whole collection.
	 *
	 * @param allocationsByPlanningId the ACTIVE allocations of each planning, empty for one that is on no
	 * 		instruction. Handed in rather than queried here, because a caller that already knows them - having
	 * 		asked for exactly the plannings of ONE instruction - would otherwise pay for a round trip to be told
	 * 		what it just asked for.
	 */
	private DeliveryPlanningList toDeliveryPlanningList(
			@NonNull final ImmutableList<I_M_Delivery_Planning> deliveryPlanningRecords,
			@NonNull final ImmutableListMultimap<DeliveryPlanningId, DeliveryPlanningAlloc> allocationsByPlanningId)
	{
		final DeliveryPlanningAddresses addresses = loadAddresses(deliveryPlanningRecords);

		return deliveryPlanningRecords.stream()
				.map(record -> toDeliveryPlanning(record, addresses, allocationsByPlanningId))
				.collect(DeliveryPlanningList.collect());
	}

	private static DeliveryPlanning toDeliveryPlanning(
			@NonNull final I_M_Delivery_Planning record,
			@NonNull final DeliveryPlanningAddresses addresses,
			@NonNull final ImmutableListMultimap<DeliveryPlanningId, DeliveryPlanningAlloc> allocationsByPlanningId)
	{
		final TransportDirection transportDirection = DeliveryPlanningRepository.extractTransportDirection(record);
		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());

		return DeliveryPlanning.builder()
				.id(deliveryPlanningId)
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.transportDirection(transportDirection)
				.shipperId(ShipperId.ofRepoIdOrNull(record.getM_Shipper_ID()))
				.incotermsId(IncotermsId.ofRepoIdOrNull(record.getC_Incoterms_ID()))
				.incotermLocation(record.getIncotermLocation())
				.meansOfTransportationId(MeansOfTransportationId.ofRepoIdOrNull(record.getM_MeansOfTransportation_ID()))
				.loadingLocationId(extractShipFromLocationIdOrNull(record, transportDirection, addresses))
				.deliveryLocationId(extractShipToLocationIdOrNull(record, transportDirection, addresses))
				.etd(TimeUtil.asInstant(record.getETD()))
				.closed(record.isClosed())
				.allocations(allocationsByPlanningId.get(deliveryPlanningId))
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
		final boolean isInbound = transportDirection.isIncomingOrDropship();
		final DeliveryPlanningAddresses addresses = loadAddresses(ImmutableList.of(deliveryPlanningRecord));
		final BPartnerLocationId shipFrom = extractShipFromLocationId(deliveryPlanningRecord, transportDirection, addresses);
		final BPartnerLocationId shipTo = extractShipToLocationId(deliveryPlanningRecord, transportDirection, addresses);

		final Dimension deliveryPlanningDimension = dimensionService.getFromRecord(deliveryPlanningRecord);

		// resolved HERE, not in the repository that persists them: an unset actual defaults to the matching
		// estimate, exactly as the instruction's own fields derive ATD/ATA from ETD/ETA after a later add
		// (see #resolveInstructionDatesForAllocation) - the same rule, applied at the point of creation.
		final Timestamp atd = deriveActualIfEmpty(deliveryPlanningRecord.getATD(), deliveryPlanningRecord.getETD());
		final Timestamp ata = deriveActualIfEmpty(deliveryPlanningRecord.getATA(), deliveryPlanningRecord.getETA());

		return DeliveryInstructionCreateRequest.builder()
				.orgId(orgId)
				.clientId(ClientId.ofRepoId(deliveryPlanningRecord.getAD_Client_ID()))
				.transportDirection(transportDirection)

				.shipperBPartnerId(BPartnerId.ofRepoId(deliveryPlanningRecord.getC_BPartner_ID()))
				.shipperLocationId(deliveryPlanningLocationId)
				.incotermsId(IncotermsId.ofRepoIdOrNull(deliveryPlanningRecord.getC_Incoterms_ID()))
				.incotermLocation(deliveryPlanningRecord.getIncotermLocation())
				.meansOfTransportationId(MeansOfTransportationId.ofRepoIdOrNull(deliveryPlanningRecord.getM_MeansOfTransportation_ID()))
				.loadingPartnerLocationId(shipFrom)
				.loadingDate(TimeUtil.asInstant(deliveryPlanningRecord.getETD()))
				.atd(TimeUtil.asInstant(atd))
				.loadingTime(deliveryPlanningRecord.getLoadingTime())
				.deliveryPartnerLocationId(shipTo)
				.deliveryDate(TimeUtil.asInstant(deliveryPlanningRecord.getETA()))
				.ata(TimeUtil.asInstant(ata))
				.deliveryTime(deliveryPlanningRecord.getDeliveryTime())

				.dateDoc(SystemTime.asInstant())
				.docTypeId(docTypeId)

				.shipperId(ShipperId.ofRepoId(deliveryPlanningRecord.getM_Shipper_ID()))

				.productId(productId)
				.isToBeFetched(isInbound)
				//.locatorId() : Not yet decided where to take it from. TODO in a future CR
				.batchNo(deliveryPlanningRecord.getBatch())
				.qtyLoaded(Quantity.of(deliveryPlanningRecord.getPlannedLoadedQuantity(), uomToUse))
				.qtyDischarged(Quantity.of(deliveryPlanningRecord.getPlannedDischargeQuantity(), uomToUse))
				.orderLineId(OrderLineId.ofRepoIdOrNull(deliveryPlanningRecord.getC_OrderLine_ID()))
				.orderId(OrderId.ofRepoIdOrNull(deliveryPlanningRecord.getC_Order_ID()))
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
			// NOT a Check.assume*: this is reachable by a planner, not a programmer error -
			// M_Delivery_Planning.M_Warehouse_ID is not mandatory in the dictionary, so a planning with
			// neither a warehouse nor a schedule to read the address from lands here. It is therefore a
			// user-facing rejection that is still missing its AD_Message - flagged, not converted.
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
			// NOT a Check.assume*, for the same reason as the loading address above: planner-reachable,
			// so it needs an AD_Message rather than an assertion.
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
			return Check.assumeNotNull(recordsById.get(id), "No {} found for {}_ID={}", tableName, tableName, id.getRepoId());
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

		final ImmutableSet<AggregationKeyField> mismatches = selectedDeliveryPlannings.aggregationKeyViolations();
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
	private static ITranslatableString incompatibleMessage(@NonNull final Set<AggregationKeyField> mismatches)
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
	 * Why the given selection cannot be CLOSED, or empty when it can.
	 * <p>
	 * ALL-or-nothing, and deliberately so: {@link #closeSelectedDeliveryPlannings} refuses the whole selection as
	 * soon as one of its rows is already closed, and every sibling action that acts on a selection - Combine, Add,
	 * Move, Remove - does the same. The precondition has to say it the same way, or a mixed selection would offer
	 * the button and then abort the batch, leaving the open rows unclosed. The rejection names the closed rows, so
	 * the planner can deselect exactly those.
	 */
	public Optional<ITranslatableString> getCloseRejectionReason(@NonNull final DeliveryPlanningList selectedDeliveryPlannings)
	{
		if (selectedDeliveryPlannings.anyClosed())
		{
			return Optional.of(TranslatableStrings.adMessage(
					MSG_M_Delivery_Planning_Closed,
					toIdList(selectedDeliveryPlannings.closedOnes())));
		}

		return Optional.empty();
	}

	/**
	 * Why the given selection cannot be RE-OPENED, or empty when it can. The mirror of
	 * {@link #getCloseRejectionReason(DeliveryPlanningList)}, for the same reason:
	 * {@link #reOpenSelectedDeliveryPlannings} refuses the whole selection as soon as one of its rows is still
	 * open, so a precondition that merely asked whether ANY row was closed would offer the button on a mixed
	 * selection and then abort the batch, re-opening nothing. The rejection names the open rows, so the planner
	 * can deselect exactly those.
	 */
	public Optional<ITranslatableString> getReOpenRejectionReason(@NonNull final DeliveryPlanningList selectedDeliveryPlannings)
	{
		if (selectedDeliveryPlannings.anyOpen())
		{
			return Optional.of(TranslatableStrings.adMessage(
					MSG_M_Delivery_Planning_Open,
					toIdList(selectedDeliveryPlannings.openOnes())));
		}

		return Optional.empty();
	}

	/**
	 * Why the given delivery instruction cannot be completed, or empty when it can.
	 * <p>
	 * Two rules:
	 * <ul>
	 * <li>none of its currently allocated plannings may be closed. A planning is closed after it was allocated
	 * to say "stop processing this cargo" - completing the instruction anyway would freight exactly what the
	 * planner already called off.</li>
	 * <li>a delivery instruction may not be completed with zero active allocations - it would freight nothing while
	 * printing a document whose report resolves everything through the missing allocation.</li>
	 * </ul>
	 * A transport order, which never has allocations, is a no-op for BOTH rules; it is told apart from a delivery
	 * instruction via {@link ShipperTransportationDocSubTypeGuard}, never by direction or by the presence of
	 * allocations.
	 */
	public Optional<ITranslatableString> getCompleteRejectionReason(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final ImmutableSet<DeliveryPlanningId> allocatedPlanningIds = deliveryPlanningAllocRepository.getAllocatedPlanningIds(deliveryInstructionId);
		if (allocatedPlanningIds.isEmpty())
		{
			final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionService.getById(deliveryInstructionId);
			if (shipperTransportationDocSubTypeGuard.isDeliveryInstruction(deliveryInstruction))
			{
				return Optional.of(TranslatableStrings.adMessage(MSG_M_Delivery_Planning_EmptyDeliveryInstruction));
			}
			return Optional.empty();
		}

		return closedAllocatedPlanningsRejection(allocatedPlanningIds, MSG_M_Delivery_Planning_ClosedAllocatedPlannings);
	}

	/**
	 * Why the given delivery instruction cannot be RE-ACTIVATED, or empty when it can.
	 * <p>
	 * One rule, the sibling of the closed half of {@link #getCompleteRejectionReason}: none of its currently
	 * allocated plannings may be closed. Closed is a terminal indicator - "I am done, do not touch this any more" -
	 * so re-opening the document that carries the cargo for editing is refused just as completing it is.
	 * <p>
	 * The empty-instruction rule is deliberately NOT repeated here: it exists so a completed instruction never
	 * prints a blank document, and re-activating one prints nothing. A transport order, which never has
	 * allocations, is a no-op for the same reason it is on the complete leg - it has no allocated plannings to be
	 * closed.
	 */
	public Optional<ITranslatableString> getReActivateRejectionReason(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return closedAllocatedPlanningsRejection(
				deliveryPlanningAllocRepository.getAllocatedPlanningIds(deliveryInstructionId),
				MSG_M_Delivery_Planning_ReActivateClosedAllocatedPlannings);
	}

	/**
	 * Why the given delivery instruction cannot be VOIDED, or empty when it can.
	 * <p>
	 * The same one rule as {@link #getReActivateRejectionReason}, on the action that has the most to change: voiding
	 * runs {@link #unlinkDeliveryPlannings}, which deactivates every allocation, clears every release number and
	 * resets every planning's dates. On a closed planning that is exactly the mutation closing forbids, so the void
	 * is refused until the planner re-opens it.
	 * <p>
	 * Reached by all three paths a void arrives on - the planner's Void button on the completed instruction,
	 * {@link #regenerateDeliveryInstructions} and {@link #cancelDelivery} - because each goes through the document
	 * engine's {@code ACTION_Void}, and the guard hangs off {@code TIMING_BEFORE_VOID} rather than off any one
	 * caller. A transport order, which never has allocations, is a no-op here as it is on the other two actions.
	 */
	public Optional<ITranslatableString> getVoidRejectionReason(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return closedAllocatedPlanningsRejection(
				deliveryPlanningAllocRepository.getAllocatedPlanningIds(deliveryInstructionId),
				MSG_M_Delivery_Planning_VoidClosedAllocatedPlannings);
	}

	/**
	 * The one rule Complete and Re-Activate share: the rejection names EVERY closed planning among the given
	 * allocated ones, never just the first, so the planner re-opens them all in one pass.
	 *
	 * @param adMessageKey the action-specific sentence; the condition behind it is the same for both.
	 */
	private Optional<ITranslatableString> closedAllocatedPlanningsRejection(
			@NonNull final ImmutableSet<DeliveryPlanningId> allocatedPlanningIds,
			@NonNull final AdMessageKey adMessageKey)
	{
		if (allocatedPlanningIds.isEmpty())
		{
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

		return Optional.of(TranslatableStrings.adMessage(adMessageKey, toIdList(closedPlanningIds)));
	}

	/**
	 * Combines the selected delivery plannings into ONE delivery instruction: each planning gets its own
	 * allocation, its own shipping package and its own {@code ReleaseNo}, and the instruction lists them all.
	 * <p>
	 * All-or-nothing: {@link #getCombineRejectionReason(DeliveryPlanningList)} is evaluated first and throws for
	 * the whole selection, so no planning is left half-moved and no orphaned package survives.
	 * <p>
	 * The header is seeded from the FIRST planning in allocation order (earliest departure, then planning id): the
	 * plannings agree on every header field the admissibility rule covers, but not on the dates, so the seed has to
	 * be decided rather than inherited from a query's encounter order.
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
		// an invariant, not a user-facing rejection: the process's precondition already refuses an empty
		// selection, and every rejection a planner can actually provoke is a translated message below.
		// Check.assume is what states an invariant - an untranslated AdempiereException literal would be
		// indistinguishable from a rejection that merely forgot its AD_Message.
		Check.assume(!selectedDeliveryPlannings.isEmpty(), "No delivery planning selected");

		getCombineRejectionReason(selectedDeliveryPlannings)
				.ifPresent(reason -> {throw new AdempiereException(reason);});

		final ImmutableList<DeliveryPlanningId> deliveryPlanningIds = selectedDeliveryPlannings.getIdsInAllocationOrder();

		// the header, plus the seed planning's allocation and shipping package
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionService.generateDeliveryInstruction(
				createDeliveryInstructionRequest(deliveryPlanningIds.get(0)));
		final ShipperTransportationId deliveryInstructionId = ShipperTransportationId.ofRepoId(deliveryInstruction.getM_ShipperTransportation_ID());

		// the remaining plannings, handed over ALREADY SORTED because createAllocations saves in the given order
		final ImmutableList<DeliveryPlanningAllocCreateRequest> furtherAllocations =
				createAllocCreateRequests(deliveryPlanningIds.subList(1, deliveryPlanningIds.size()));
		if (!furtherAllocations.isEmpty())
		{
			// the seed header ALREADY resolved (generateDeliveryInstruction just built it), so the record this
			// method holds is reused rather than reloaded to resolve the further plannings' dates against it
			final DeliveryInstructionDates resolvedDates = resolveInstructionDatesForAllocation(deliveryInstruction, furtherAllocations);
			deliveryInstructionService.createAllocations(deliveryInstruction, furtherAllocations, resolvedDates);
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
	 * The allocation requests for the given delivery plannings, in the order the ids were given.
	 * <p>
	 * Takes the whole collection rather than one id at a time so the records are batch-loaded once. There is no
	 * single-id counterpart, and adding one reintroduces a per-row load.
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
				.shippingPackage(DeliveryPlanningAllocCreateRequest.ShippingPackageData.builder()
						.productId(productId)
						.uomId(UomId.ofRepoId(uomToUse.getC_UOM_ID()))
						.batchNo(deliveryPlanningRecord.getBatch())
						.orderLineId(OrderLineId.ofRepoIdOrNull(deliveryPlanningRecord.getC_OrderLine_ID()))
						.orderId(OrderId.ofRepoIdOrNull(deliveryPlanningRecord.getC_Order_ID()))
						.toBeFetched(DeliveryPlanningRepository.extractTransportDirection(deliveryPlanningRecord).isIncomingOrDropship())
						.build())
				// the planning's own dates, so the instruction's fill-if-empty defaulting needs no second load
				.headerDateCandidate(DeliveryPlanningAllocCreateRequest.HeaderDateCandidate.builder()
						.etd(deliveryPlanningRecord.getETD())
						.eta(deliveryPlanningRecord.getETA())
						.loadingTime(deliveryPlanningRecord.getLoadingTime())
						.deliveryTime(deliveryPlanningRecord.getDeliveryTime())
						.build())
				.build();
	}

	/**
	 * Why this selection cannot be ADDED to a delivery instruction, or empty when it can.
	 * <p>
	 * Lives here rather than in the process's {@code checkPreconditionsApplicable}, so the reason on the disabled
	 * button is by construction the sentence {@link #addTo} throws, and a cucumber step drives the same rule.
	 * <p>
	 * Add is the action for a planning on NO instruction yet. One already on a draft is refused, and the rejection
	 * names Move instead of relocating it silently - taking a load off another instruction changes that document too.
	 *
	 * @param targetDeliveryInstructionId the instruction the planner picked, or {@code null} when the parameter
	 * 		dialog has not been shown yet - the precondition can only judge the selection, so it passes {@code null}
	 * 		and the target-side rules are evaluated when {@link #addTo} runs.
	 */
	public Optional<ITranslatableString> getAddToRejectionReason(
			@NonNull final DeliveryPlanningList selectedDeliveryPlannings,
			@Nullable final ShipperTransportationId targetDeliveryInstructionId)
	{
		return getPutOnDeliveryInstructionRejectionReason(
				selectedDeliveryPlannings,
				targetDeliveryInstructionId,
				() -> {
					// exactly the complement of Move's guard below, so a selection is offered exactly one of the
					// two actions and the planner never has to guess which one their rows are in the state for
					final DeliveryPlanningList allocated = selectedDeliveryPlannings.allocatedOnes();
					return allocated.isEmpty()
							? Optional.empty()
							: Optional.of(TranslatableStrings.adMessage(
							MSG_M_Delivery_Planning_AlreadyOnDeliveryInstruction_UseMove,
							toIdList(allocated)));
				});
	}

	/**
	 * Why this selection cannot be MOVED to another delivery instruction, or empty when it can.
	 * <p>
	 * The mirror image of {@link #getAddToRejectionReason(DeliveryPlanningList, ShipperTransportationId)}: same
	 * rules on the rows and on the target, opposite allocation guard. Move is the action for a planning that IS on
	 * a draft instruction; one that is on none has nothing to move off, and Add is what applies to it.
	 *
	 * @param targetDeliveryInstructionId the instruction the planner picked, or {@code null} when the parameter
	 * 		dialog has not been shown yet - same contract as the add-to counterpart.
	 */
	public Optional<ITranslatableString> getMoveToRejectionReason(
			@NonNull final DeliveryPlanningList selectedDeliveryPlannings,
			@Nullable final ShipperTransportationId targetDeliveryInstructionId)
	{
		return getPutOnDeliveryInstructionRejectionReason(
				selectedDeliveryPlannings,
				targetDeliveryInstructionId,
				() -> {
					// ANY unallocated row refuses the WHOLE selection, matching the all-or-nothing the action
					// itself is - and making the pair of preconditions mutually exclusive rather than merely
					// usually-disjoint
					final DeliveryPlanningList unallocated = selectedDeliveryPlannings.unallocatedOnes();
					return unallocated.isEmpty()
							? Optional.empty()
							: Optional.of(TranslatableStrings.adMessage(
							MSG_M_Delivery_Planning_NotOnDeliveryInstruction,
							toIdList(unallocated)));
				});
	}

	/**
	 * Everything Add to and Move to refuse for the same reason - which is everything except the allocation state
	 * the two actions are the two halves of.
	 * <p>
	 * Row eligibility first, then the target, so a planner resolves "this row cannot go at all" before "it cannot go
	 * THERE". The two target-side rules come last, and both read the plannings the target already holds - so they
	 * cost nothing on the selection-change path, which has no target yet.
	 * <p>
	 * The completed-instruction rule is evaluated BEFORE the allocation guard: a planning on a completed instruction
	 * is allocated, so the guard would otherwise answer for it and send the planner to a Move that refuses it too.
	 *
	 * @param allocationStateGuard the one rule the two actions do not share: Add refuses an allocated planning,
	 * 		Move refuses an unallocated one.
	 */
	private Optional<ITranslatableString> getPutOnDeliveryInstructionRejectionReason(
			@NonNull final DeliveryPlanningList selectedDeliveryPlannings,
			@Nullable final ShipperTransportationId targetDeliveryInstructionId,
			@NonNull final Supplier<Optional<ITranslatableString>> allocationStateGuard)
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

		final Optional<ITranslatableString> allocationStateRejection = allocationStateGuard.get();
		if (allocationStateRejection.isPresent())
		{
			return allocationStateRejection;
		}

		// The selection's own consistency, judged before any target is known: a selection that disagrees with
		// itself cannot go onto ANY single instruction, so it is refused while the planner is still on the grid
		// rather than after they have picked a target. Direction is one of these fields, so the value the target
		// picker correlates on is covered here too.
		final ImmutableSet<AggregationKeyField> selectionMismatches = selectedDeliveryPlannings.aggregationKeyViolations();
		if (!selectionMismatches.isEmpty())
		{
			return Optional.of(incompatibleMessage(selectionMismatches));
		}

		if (targetDeliveryInstructionId == null)
		{
			// the parameter dialog has not been shown yet, so the two target-side rules below cannot be evaluated;
			// they are, when the action runs with the instruction the planner picked
			return Optional.empty();
		}

		if (!deliveryInstructionService.getDocStatus(targetDeliveryInstructionId).isDrafted())
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
		final ImmutableSet<AggregationKeyField> mismatches = getAllocatedTo(targetDeliveryInstructionId)
				// a planning that is already on the target is in BOTH lists and is counted ONCE, so it is never
				// compared against itself and reported as differing from itself
				.union(selectedDeliveryPlannings)
				.aggregationKeyViolations();
		if (!mismatches.isEmpty())
		{
			return Optional.of(incompatibleMessage(mismatches));
		}

		return Optional.empty();
	}

	/**
	 * Why nothing can be removed from a delivery instruction for this selection, or empty when it can.
	 * <p>
	 * A closed planning is refused, like everywhere else: removal deactivates its allocation, drops its release
	 * number and resets its dates, which is precisely the mutation closing forbids. The planner re-opens it first
	 * and removes it then - two deliberate steps rather than one that quietly undoes the other.
	 * <p>
	 * Row eligibility first - closed, then not-allocated, then on-a-completed-instruction - so a planner resolves
	 * "this row cannot go at all" before "it cannot come off THAT instruction".
	 */
	public Optional<ITranslatableString> getRemoveFromRejectionReason(@NonNull final DeliveryPlanningList selectedDeliveryPlannings)
	{
		if (selectedDeliveryPlannings.anyClosed())
		{
			return Optional.of(TranslatableStrings.adMessage(
					MSG_M_Delivery_Planning_ClosedPlannings,
					toIdList(selectedDeliveryPlannings.closedOnes())));
		}

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
	 * The plannings of the given selection that sit on AT LEAST ONE delivery instruction which is no longer a
	 * draft - which is what forbids both moving them off it and removing them from it.
	 * <p>
	 * ANY and not ALL: both callers use this to FORBID, and a planning on one draft plus one completed instruction
	 * cannot be taken off the completed one at all, so the whole action must be refused. Requiring every
	 * instruction to be non-draft would let that planning through and then fail - or silently alter - a completed
	 * document.
	 */
	private DeliveryPlanningList onNonDraftInstruction(@NonNull final DeliveryPlanningList selectedDeliveryPlannings)
	{
		final ImmutableSet<ShipperTransportationId> deliveryInstructionIds = selectedDeliveryPlannings.stream()
				.map(DeliveryPlanning::getDeliveryInstructionIds)
				.flatMap(Collection::stream)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableMap<ShipperTransportationId, DocStatus> docStatuses = deliveryInstructionService.getDocStatuses(deliveryInstructionIds);

		return selectedDeliveryPlannings.stream()
				.filter(DeliveryPlanning::isAllocated)
				// an instruction the query did not return cannot be shown to be a draft, so it counts as one that
				// is not - the safe direction for a rule whose job is to forbid
				.filter(deliveryPlanning -> deliveryPlanning.getDeliveryInstructionIds().stream()
						.anyMatch(id -> !docStatuses.getOrDefault(id, DocStatus.Unknown).isDrafted()))
				.collect(DeliveryPlanningList.collect());
	}

	/**
	 * Puts the selected delivery plannings on the given DRAFT delivery instruction. Only plannings that are on NO
	 * instruction yet: one that is already allocated is refused and the planner is pointed at {@link #moveTo},
	 * because taking a load off another instruction changes that document too.
	 * <p>
	 * All-or-nothing: the rejection is evaluated for the whole selection before anything is written, and the writes
	 * then run in one transaction, so a failure part-way leaves no planning allocated and no {@code ReleaseNo}
	 * stamped.
	 */
	public void addTo(
			@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter,
			@NonNull final ShipperTransportationId targetDeliveryInstructionId)
	{
		putOnDeliveryInstruction(
				selectedDeliveryPlanningsFilter,
				targetDeliveryInstructionId,
				this::getAddToRejectionReason,
				// nothing to release: the guard above refused every allocated planning
				false);
	}

	/**
	 * Moves the selected delivery plannings from the DRAFT delivery instruction they are on to the given one: the
	 * source allocation and its shipping package are released, the planning's dates return to their order-derived
	 * origin, and a new allocation is created on the target.
	 * <p>
	 * All-or-nothing, as {@link #addTo} is, and for a sharper reason: a move touches TWO documents, so a failure
	 * part-way would leave a load on neither. A planning already on the target is left alone rather than refused -
	 * there is nothing to move - so a selection overlapping the target's own rows still moves the rest.
	 */
	public void moveTo(
			@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter,
			@NonNull final ShipperTransportationId targetDeliveryInstructionId)
	{
		putOnDeliveryInstruction(
				selectedDeliveryPlanningsFilter,
				targetDeliveryInstructionId,
				this::getMoveToRejectionReason,
				true);
	}

	/**
	 * The one implementation behind {@link #addTo} and {@link #moveTo}: resolve the target, build the allocation
	 * requests, resolve the instruction's dates, create the allocations, re-stamp the release numbers. The two
	 * actions differ in exactly two things - the guard they are rejected by, and whether a source allocation is
	 * released first.
	 * <p>
	 * Deactivate, THEN reset, THEN build the allocation requests - the order matters. A planning still allocated to
	 * the SOURCE carries the source instruction's dates, so a snapshot taken before the reset would leak them into
	 * an empty draft TARGET's fill-if-empty defaulting. Resetting first makes the snapshot the planning's own
	 * order-derived dates again. The reset's persisted value is transient - the target's sync-down overwrites the
	 * row shortly after - but the REQUEST built from it is what the target's defaulting reads.
	 *
	 * @param releaseSourceAllocation deactivate the allocation the planning is on today and reset its dates before
	 * 		creating the new one. True for a move; false for an add, whose guard has already refused every planning
	 * 		that has one.
	 */
	private void putOnDeliveryInstruction(
			@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter,
			@NonNull final ShipperTransportationId targetDeliveryInstructionId,
			@NonNull final BiFunction<DeliveryPlanningList, ShipperTransportationId, Optional<ITranslatableString>> rejectionReason,
			final boolean releaseSourceAllocation)
	{
		final DeliveryPlanningList selectedDeliveryPlannings = getBySelection(selectedDeliveryPlanningsFilter);
		// an invariant, not a user-facing rejection - see combine() above
		Check.assume(!selectedDeliveryPlannings.isEmpty(), "No delivery planning selected");

		rejectionReason.apply(selectedDeliveryPlannings, targetDeliveryInstructionId)
				.ifPresent(reason -> {throw new AdempiereException(reason);});

		// in allocation order, so the target's allocations are created in a decided order rather than the
		// query's encounter order
		final ImmutableList<DeliveryPlanningId> deliveryPlanningIds = selectedDeliveryPlannings.stream()
				// already on the target = nothing to do; with several legs that means NONE of its allocations
				// names the target. Reachable only for a move - an add's guard has refused every allocated row
				.filter(deliveryPlanning -> !deliveryPlanning.getDeliveryInstructionIds().contains(targetDeliveryInstructionId))
				.map(DeliveryPlanning::getId)
				.collect(ImmutableList.toImmutableList());
		if (deliveryPlanningIds.isEmpty())
		{
			return;
		}

		// No trxManager wrapper: the only callers are the two JavaProcesses without @RunOutOfTrx, so an ambient
		// thread-inherited transaction already spans the whole call and rolls the writes below back together.
		// runInThreadInheritedTrx would only add a savepoint inside that transaction; it earns its keep
		// exclusively where the caller arrives OUT of transaction (see closeSelectedDeliveryPlannings above,
		// whose process IS @RunOutOfTrx).

		if (releaseSourceAllocation)
		{
			// the source allocation and its package are DEACTIVATED, not deleted, so the record of what was once
			// planned survives - the target's insert still finds no ACTIVE row to collide with on either partial
			// unique index, since both are declared WHERE IsActive='Y'
			final ImmutableSet<DeliveryPlanningId> deactivatedIds = deliveryInstructionService.deactivateAllocations(deliveryPlanningIds, SystemTime.asInstant());
			resetDatesFromOrderAndSchedule(deactivatedIds);
		}

		// built AFTER any reset above: reads the just-reset, order-derived dates - never the source
		// instruction's, which the sync-down would still have on these rows before the reset ran
		final ImmutableList<DeliveryPlanningAllocCreateRequest> allocations = createAllocCreateRequests(deliveryPlanningIds);

		final I_M_ShipperTransportation targetInstruction = deliveryInstructionService.getById(targetDeliveryInstructionId);
		final DeliveryInstructionDates resolvedDates = resolveInstructionDatesForAllocation(targetInstruction, allocations);
		deliveryInstructionService.createAllocations(targetInstruction, allocations, resolvedDates);

		// stamped from the target: on a move the old release number named a document the cargo has left
		deliveryInstructionService.updateDeliveryPlanningsFromInstruction(deliveryPlanningIds, targetDeliveryInstructionId);
	}

	/**
	 * Takes the selected delivery plannings off the DRAFT delivery instruction they are on: allocation and
	 * shipping package are deactivated, and the planning loses its {@code ReleaseNo}, so it can be planned again.
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
		// an invariant, not a user-facing rejection - see combine() above
		Check.assume(!selectedDeliveryPlannings.isEmpty(), "No delivery planning selected");

		getRemoveFromRejectionReason(selectedDeliveryPlannings)
				.ifPresent(reason -> {throw new AdempiereException(reason);});

		final ImmutableList<DeliveryPlanningId> deliveryPlanningIds = selectedDeliveryPlannings.allocatedOnes().getIdsInAllocationOrder();

		// No trxManager wrapper - same reason as in addTo: the only caller,
		// M_Delivery_Planning_RemoveFromDeliveryInstruction.doIt(), is a JavaProcess without @RunOutOfTrx and
		// therefore already runs inside a transaction.
		final ImmutableSet<DeliveryPlanningId> deactivatedIds = deliveryInstructionService.deactivateAllocations(deliveryPlanningIds, SystemTime.asInstant());
		resetDatesFromOrderAndSchedule(deactivatedIds);
		deliveryPlanningRepository.clearInstructionReference(deliveryPlanningIds);
	}

	/**
	 * Unlinks every planning currently allocated to the instruction (deactivating the allocations), resets their
	 * dates and invalidates their invoice candidates - the same batch load
	 * {@link #invalidateInvoiceCandidatesFor(ShipperTransportationId)} uses, but reading the affected ids from
	 * {@link DeliveryInstructionService#unlinkDeliveryPlannings}'s OWN return value rather than a second, separate
	 * query: the same pattern the other two retirement paths (remove-from and the source half of a move)
	 * already follow - deactivate once, use what it reports it deactivated. Re-deriving the ids from
	 * {@link DeliveryPlanningAllocRepository#getAllocatedPlanningIds(ShipperTransportationId)} AFTER the deactivation
	 * would come back empty, which is exactly why the invalidation below is deferred to after-commit against the
	 * ids captured HERE rather than re-resolved inside that deferred closure.
	 */
	public void unlinkDeliveryPlannings(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final ImmutableSet<DeliveryPlanningId> allocatedPlanningIds =
				deliveryInstructionService.unlinkDeliveryPlannings(deliveryInstructionId, SystemTime.asInstant());
		resetDatesFromOrderAndSchedule(allocatedPlanningIds);

		if (!allocatedPlanningIds.isEmpty())
		{
			trxManager.runAfterCommit(() -> deliveryPlanningRepository.getByIds(allocatedPlanningIds)
					.forEach(this::invalidateInvoiceCandidatesFor));
		}
	}

	/**
	 * Reacts to the instruction's own dates changing (the {@code M_ShipperTransportation} AFTER_CHANGE
	 * {@code ETD}/{@code ETA}/{@code ATD}/{@code ATA}/{@code LoadingTime}/{@code DeliveryTime} interceptor):
	 * pushes them down onto every planning CURRENTLY allocated to it.
	 * <p>
	 * One-way and unconditional, the same as the initial "add conforms to the instruction" stamp - both go
	 * through {@link DeliveryPlanningRepository#updateDeliveryPlanningsFromInstruction(Collection, I_M_ShipperTransportation)}.
	 * A planning that is no longer allocated is not among {@code getAllocatedPlanningIds} and is therefore left
	 * alone - it is on its way to (or has already had) its own reset, not this sync.
	 */
	public void syncDatesToAllocatedPlannings(@NonNull final I_M_ShipperTransportation deliveryInstruction)
	{
		final ShipperTransportationId deliveryInstructionId = ShipperTransportationId.ofRepoId(deliveryInstruction.getM_ShipperTransportation_ID());
		final ImmutableSet<DeliveryPlanningId> allocatedPlanningIds = deliveryPlanningAllocRepository.getAllocatedPlanningIds(deliveryInstructionId);
		if (allocatedPlanningIds.isEmpty())
		{
			return;
		}

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(allocatedPlanningIds, deliveryInstruction);
	}

	/**
	 * The delivery instruction's fill-if-empty defaulting: each of {@code ETD}/{@code ETA}/{@code LoadingTime}/
	 * {@code DeliveryTime} is filled from the first request carrying one, but only while the instruction's own
	 * field is still empty. Every field is guarded individually so a value the planner entered before the add
	 * survives.
	 * <p>
	 * A pure function: it reads and decides, {@link DeliveryInstructionService#createAllocations} writes.
	 * {@code ATD}/{@code ATA} are then derived from the FILLED {@code ETD}/{@code ETA} rather than from the
	 * planning, so a planner-set departure propagates into the actual. {@code BLDate} belongs to the
	 * transport-order flow and is not touched.
	 * <p>
	 * Accepted limitation: a date the planner CLEARED reads as empty and is refilled by the next add.
	 */
	static DeliveryInstructionDates resolveInstructionDatesForAllocation(
			@NonNull final I_M_ShipperTransportation deliveryInstructionRecord,
			@NonNull final List<DeliveryPlanningAllocCreateRequest> requests)
	{
		Timestamp etd = deliveryInstructionRecord.getETD();
		Timestamp eta = deliveryInstructionRecord.getETA();
		String loadingTime = deliveryInstructionRecord.getLoadingTime();
		String deliveryTime = deliveryInstructionRecord.getDeliveryTime();

		for (final DeliveryPlanningAllocCreateRequest request : requests)
		{
			final DeliveryPlanningAllocCreateRequest.HeaderDateCandidate candidate = request.getHeaderDateCandidate();
			if (etd == null && candidate.getEtd() != null)
			{
				etd = candidate.getEtd();
			}
			if (eta == null && candidate.getEta() != null)
			{
				eta = candidate.getEta();
			}
			if (Check.isBlank(loadingTime) && !Check.isBlank(candidate.getLoadingTime()))
			{
				loadingTime = candidate.getLoadingTime();
			}
			if (Check.isBlank(deliveryTime) && !Check.isBlank(candidate.getDeliveryTime()))
			{
				deliveryTime = candidate.getDeliveryTime();
			}
		}

		return DeliveryInstructionDates.builder()
				.etd(etd)
				.eta(eta)
				.atd(deriveActualIfEmpty(deliveryInstructionRecord.getATD(), etd))
				.ata(deriveActualIfEmpty(deliveryInstructionRecord.getATA(), eta))
				.loadingTime(loadingTime)
				.deliveryTime(deliveryTime)
				.build();
	}

	/** An unset actual (planner never confirmed one) defaults to the matching estimate; a real actual is kept. */
	private static Timestamp deriveActualIfEmpty(@Nullable final Timestamp actual, @Nullable final Timestamp estimated)
	{
		return CoalesceUtil.coalesce(actual, estimated);
	}

	/**
	 * Recomputes the given plannings' dates from the order and its schedule, the way the Generate commands derive
	 * them for a new planning. Called the moment an allocation becomes inactive (remove-from, the source half of a
	 * move, void), so a planning never keeps showing another document's dates.
	 * <p>
	 * A RECOMPUTE, not a restore: while allocated, the sync-down has already overwritten the planning's own dates,
	 * so there is no pre-allocation value to bring back - add-then-remove is not a byte-exact round trip.
	 * {@code LoadingTime}/{@code DeliveryTime} have no order-derived source and are therefore cleared.
	 * <p>
	 * Writing {@code ATD} also drives the pre-existing {@code AFTER_CHANGE(ATD)} interceptor, which reads that
	 * row's order line once per row. That read rides the already-per-row write loop rather than multiplying it.
	 */
	void resetDatesFromOrderAndSchedule(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return;
		}

		final ImmutableList<I_M_Delivery_Planning> deliveryPlanningRecords = deliveryPlanningRepository.getByIds(deliveryPlanningIds);

		final ImmutableSet.Builder<OrderId> orderIds = ImmutableSet.builder();
		final ImmutableSet.Builder<OrderLineId> orderLineIds = ImmutableSet.builder();
		final ImmutableSet.Builder<ReceiptScheduleId> receiptScheduleIds = ImmutableSet.builder();
		final ImmutableSet.Builder<ShipmentScheduleId> shipmentScheduleIds = ImmutableSet.builder();

		for (final I_M_Delivery_Planning record : deliveryPlanningRecords)
		{
			addIfNotNull(orderIds, OrderId.ofRepoIdOrNull(record.getC_Order_ID()));
			addIfNotNull(orderLineIds, OrderLineId.ofRepoIdOrNull(record.getC_OrderLine_ID()));
			if (hasReceiptOrUnknown(record))
			{
				addIfNotNull(receiptScheduleIds, ReceiptScheduleId.ofRepoIdOrNull(record.getM_ReceiptSchedule_ID()));
			}
			else
			{
				addIfNotNull(shipmentScheduleIds, ShipmentScheduleId.ofRepoIdOrNull(record.getM_ShipmentSchedule_ID()));
			}
		}

		final ImmutableMap<OrderId, I_C_Order> ordersById = Maps.uniqueIndex(
				orderBL.getByIds(orderIds.build()),
				order -> OrderId.ofRepoId(order.getC_Order_ID()));
		final ImmutableMap<OrderLineId, I_C_OrderLine> orderLinesById = Maps.uniqueIndex(
				orderLineBL.getByIds(orderLineIds.build()),
				orderLine -> OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));
		final Map<ReceiptScheduleId, I_M_ReceiptSchedule> receiptSchedulesById = receiptScheduleDAO.getByIds(receiptScheduleIds.build());
		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesById = shipmentScheduleBL.getByIds(shipmentScheduleIds.build());

		final ImmutableMap.Builder<DeliveryPlanningId, DeliveryInstructionDates> resolvedDatesByPlanningId = ImmutableMap.builder();
		for (final I_M_Delivery_Planning record : deliveryPlanningRecords)
		{
			resolvedDatesByPlanningId.put(
					DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID()),
					resolveResetDates(record, ordersById, orderLinesById, receiptSchedulesById, shipmentSchedulesById));
		}

		deliveryPlanningRepository.writePlanningDates(deliveryPlanningRecords, resolvedDatesByPlanningId.build());
	}

	private static DeliveryInstructionDates resolveResetDates(
			@NonNull final I_M_Delivery_Planning record,
			@NonNull final Map<OrderId, I_C_Order> ordersById,
			@NonNull final Map<OrderLineId, I_C_OrderLine> orderLinesById,
			@NonNull final Map<ReceiptScheduleId, I_M_ReceiptSchedule> receiptSchedulesById,
			@NonNull final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesById)
	{
		final I_C_Order order = ordersById.get(OrderId.ofRepoIdOrNull(record.getC_Order_ID()));
		final I_C_OrderLine orderLine = orderLinesById.get(OrderLineId.ofRepoIdOrNull(record.getC_OrderLine_ID()));
		final boolean hasReceipt = hasReceiptOrUnknown(record);

		// the Outgoing command's own effective-delivery-date computation (coalesce(Override, plain)); the
		// Incoming command has no override field and reads the receipt schedule's MovementDate directly instead
		final Timestamp deliveryDateEffective;
		if (hasReceipt)
		{
			final I_M_ReceiptSchedule receiptSchedule = receiptSchedulesById.get(ReceiptScheduleId.ofRepoIdOrNull(record.getM_ReceiptSchedule_ID()));
			deliveryDateEffective = receiptSchedule != null ? receiptSchedule.getMovementDate() : null;
		}
		else
		{
			final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulesById.get(ShipmentScheduleId.ofRepoIdOrNull(record.getM_ShipmentSchedule_ID()));
			deliveryDateEffective = shipmentSchedule != null
					? CoalesceUtil.coalesce(shipmentSchedule.getDeliveryDate_Override(), shipmentSchedule.getDeliveryDate())
					: null;
		}

		// the Outgoing command's own fallback for an unset delivery date; the Incoming command has none
		final Timestamp eta = deliveryDateEffective == null && !hasReceipt && orderLine != null
				? orderLine.getDatePromised()
				: deliveryDateEffective;

		final Timestamp ata = orderLine != null ? CoalesceUtil.coalesce(orderLine.getDateDelivered(), deliveryDateEffective) : null;

		final Timestamp etd = order != null ? order.getPreparationDate() : null;

		return DeliveryInstructionDates.builder()
				.etd(etd)
				.atd(etd)
				.eta(eta)
				.ata(ata)
				.loadingTime(null)
				.deliveryTime(null)
				.build();
	}


	/**
	 * A blank {@code TransportDirection} - the shape an unset one takes, the column being NOT NULL - is read as "not a
	 * receipt" (the shipment-schedule branch) rather than throwing: the date reset runs for EVERY deallocated planning
	 * and has no admissibility gate of its own.
	 */
	private static boolean hasReceiptOrUnknown(@NonNull final I_M_Delivery_Planning record)
	{
		final TransportDirection transportDirection = TransportDirection.ofNullableCode(record.getTransportDirection());
		return transportDirection != null && transportDirection.hasReceipt();
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
		final Iterator<I_M_ShipperTransportation> deliveryInstructionsIterator = deliveryInstructionService.retrieveForDeliveryPlanning(deliveryPlanningId);
		while (deliveryInstructionsIterator.hasNext())
		{
			final I_M_ShipperTransportation deliveryInstructionRecord = deliveryInstructionsIterator.next();

			docActionBL.processEx(deliveryInstructionRecord, IDocument.ACTION_Void, IDocument.STATUS_Voided);
		}
	}

	/**
	 * Cancels every selected planning that carries a {@code ReleaseNo}, per row: a closed one is left untouched and
	 * named in {@link DeliveryPlanningCancelResult#getSkippedClosedIds()} rather than aborting the whole selection
	 * - the open ones are still voided and cancelled.
	 * <p>
	 * That per-row skip holds only while the closed planning sits on an instruction of its own. Cancelling an open
	 * planning voids the instruction it rides on ({@link #voidLinkedDeliveryInstructions}), and
	 * {@link #getVoidRejectionReason} refuses to void an instruction that carries a closed allocated planning - so
	 * when a selected open planning SHARES its instruction with a closed one, the whole cancel aborts and nothing
	 * is cancelled. That is the normal case under aggregation, and it is deliberate: the closed planning's cargo
	 * would otherwise be released along with the open one's.
	 * <p>
	 * An open planning still allocated to a delivery instruction when this runs (D8/D19 - the same
	 * committed-cargo rule the split applies, via {@link DeliveryPlanningAllocRepository#hasActiveAllocation}) is
	 * fully cancelled the same as any other row - voided, closed, cancelled order status - but its
	 * {@code PlannedLoadedQuantity}/{@code PlannedDischargeQuantity} are left untouched and it is named in
	 * {@link DeliveryPlanningCancelResult#getSkippedAllocatedIds()} instead of being silently rewritten.
	 * <p>
	 * The allocation state is snapshotted for the WHOLE selection BEFORE any row is voided - not read per row
	 * right before that row's own void. Two selected plannings sharing one instruction (the aggregation case
	 * this method's own Javadoc above already describes) both go through {@link #voidLinkedDeliveryInstructions}
	 * once each is reached in the loop, and voiding the shared instruction deactivates BOTH plannings'
	 * allocations at once (the AFTER_VOID unlink cascade). A per-row "check right before voiding THIS row" would
	 * therefore see the second-processed sibling as already unallocated - a side effect of iteration order the
	 * first-processed row's void introduced - and zero its planned figures despite it being just as committed
	 * as the first. The batch snapshot fixes what cancel actually found before it touched anything.
	 */
	public DeliveryPlanningCancelResult cancelDelivery(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final ICompositeQueryFilter<I_M_Delivery_Planning> dpFilter = deliveryPlanningRepository
				.excludeDeliveryPlanningsWithoutReleaseNo(selectedDeliveryPlanningsFilter);

		final ImmutableList<I_M_Delivery_Planning> selectedDeliveryPlannings = ImmutableList.copyOf(
				deliveryPlanningRepository.extractDeliveryPlannings(dpFilter));

		final ImmutableList<DeliveryPlanningId> selectedDeliveryPlanningIds = selectedDeliveryPlannings.stream()
				.map(record -> DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID()))
				.collect(ImmutableList.toImmutableList());

		// snapshot, taken before the loop below voids anything - see the Javadoc above
		final ImmutableSet<DeliveryPlanningId> allocatedIds = deliveryPlanningAllocRepository
				.getAllocationsByPlanningId(selectedDeliveryPlanningIds)
				.keySet();

		final ImmutableList.Builder<DeliveryPlanningId> cancelledIds = ImmutableList.builder();
		final ImmutableList.Builder<DeliveryPlanningId> skippedClosedIds = ImmutableList.builder();
		final ImmutableList.Builder<DeliveryPlanningId> skippedAllocatedIds = ImmutableList.builder();

		for (final I_M_Delivery_Planning deliveryPlanningRecord : selectedDeliveryPlannings)
		{
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(deliveryPlanningRecord.getM_Delivery_Planning_ID());

			// Reached by a planning closed WHILE allocated: closing sets the flag and nothing else, so such a row
			// keeps its ReleaseNo and survives the excludeDeliveryPlanningsWithoutReleaseNo filter above. A
			// planning that was never allocated has no ReleaseNo and is dropped by that filter instead.
			if (deliveryPlanningRecord.isClosed())
			{
				skippedClosedIds.add(deliveryPlanningId);
				continue;
			}

			final boolean wasAllocated = allocatedIds.contains(deliveryPlanningId);

			// first void the existent delivery instructions
			voidLinkedDeliveryInstructions(deliveryPlanningId);

			// re-read: the void's unlink cascade may have cleared ReleaseNo/M_ShipperTransportation_ID on this
			// same row, and the pre-void record in hand here must not overwrite that with stale values
			deliveryPlanningRepository.cancelDeliveryPlanning(deliveryPlanningRepository.getById(deliveryPlanningId), !wasAllocated);
			cancelledIds.add(deliveryPlanningId);
			if (wasAllocated)
			{
				skippedAllocatedIds.add(deliveryPlanningId);
			}
		}

		return DeliveryPlanningCancelResult.builder()
				.cancelledIds(cancelledIds.build())
				.skippedClosedIds(skippedClosedIds.build())
				.skippedAllocatedIds(skippedAllocatedIds.build())
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
		return deliveryInstructionService.hasCompleteDeliveryInstruction(deliveryPlanningId);
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
	 * batch load. Resolving through the allocations rather than a single header FK is what covers an aggregated
	 * instruction's second and later plannings; a per-planning loop would be an N+1 over the allocations.
	 * <p>
	 * A transport order, or an instruction with no allocations, is a no-op: {@code getAllocatedPlanningIds} comes
	 * back empty and the batch load never runs.
	 */
	public void invalidateInvoiceCandidatesFor(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final ImmutableSet<DeliveryPlanningId> allocatedPlanningIds = deliveryPlanningAllocRepository.getAllocatedPlanningIds(deliveryInstructionId);
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
