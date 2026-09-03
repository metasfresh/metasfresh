/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipping;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.ILUQtyProvider;
import de.metas.handlingunits.IPackageWeightProvider;
import de.metas.handlingunits.ITUDistributionProvider;
import de.metas.handlingunits.impl.ShipperTransportationQuery;
import de.metas.i18n.AdMessageKey;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.report.ReportResultData;
import de.metas.report.server.ReportConstants;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.mpackage.PackageId;
import de.metas.shipping.mpackage.Package;
import de.metas.sscc18.ISSCC18CodeBL;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderToShipperTransportationService
{
	@VisibleForTesting
	static final AdMessageKey MSG_NoLUPackingConfigForOrderLines = AdMessageKey.of("NoLUPackingConfigForOrderLines");

	@VisibleForTesting
	static final AdMessageKey MSG_WrongTransportDirectionForPurchaseOrder = AdMessageKey.of("WrongTransportDirectionForPurchaseOrder");

	/**
	 * Order in which assigned purchase orders are processed, so the "first order" that seeds the transport order's default dates is
	 * deterministic: earliest {@code PreparationDate} (the departure date the header's ETD is seeded from) first, ties broken by
	 * {@code DatePromised} (which seeds ETA) and then by {@code C_Order_ID}; missing dates sort last.
	 */
	private static final Comparator<I_C_Order> SEED_ORDER_COMPARATOR = Comparator
			.comparing(I_C_Order::getPreparationDate, Comparator.nullsLast(Comparator.naturalOrder()))
			.thenComparing(I_C_Order::getDatePromised, Comparator.nullsLast(Comparator.naturalOrder()))
			.thenComparingInt(I_C_Order::getC_Order_ID);

	@NonNull private final PurchaseOrderToShipperTransportationRepository repo;

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	private final ISSCC18CodeBL sscc18CodeBL = Services.get(ISSCC18CodeBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ILUQtyProvider qtyProvider;
	private final ITUDistributionProvider tuDistributionProvider;
	private final IPackageWeightProvider packageWeightProvider;

	@VisibleForTesting
	public static PurchaseOrderToShipperTransportationService newInstanceForUnitTesting()
	{
		return newInstanceForUnitTesting((o, ol) -> 1);
	}

	@VisibleForTesting
	public static PurchaseOrderToShipperTransportationService newInstanceForUnitTesting(@NonNull final ILUQtyProvider luQtyProvider)
	{
		Adempiere.assertUnitTestMode();

		final ITUDistributionProvider tuProvider = (order, orderLine, totalTU, luCount) -> {
			final ArrayList<BigDecimal> tuDistribution = new ArrayList<>(luCount);
			for (int i = 0; i < luCount; i++)
			{
				tuDistribution.add(BigDecimal.ZERO);
			}
			return tuDistribution;
		};
		final IPackageWeightProvider weightProvider = (order, orderLine, tuQtyForPackage) -> null;
		return new PurchaseOrderToShipperTransportationService(
				PurchaseOrderToShipperTransportationRepository.newInstanceForUnitTesting(),
				luQtyProvider,
				tuProvider,
				weightProvider);
	}

	private static final String AD_PROCESS_VALUE_C_Order_SSCC_Print_Jasper = "C_Order_SSCC_Print_Jasper";

	public void addPurchaseOrdersToShipperTransportation(@NonNull final ShipperTransportationId shipperTransportationId, @NonNull final Collection<OrderId> orderIds)
	{
		final ImmutableSet<OrderId> availableOrderIds = queryBL
				.createQueryBuilder(I_C_Order.class)
				.addInArrayFilter(I_C_Order.COLUMNNAME_DocStatus, DocStatus.Completed, DocStatus.Closed)
				.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, orderIds)
				.create()
				.idsAsSet(OrderId::ofRepoId);
		final Set<OrderId> alreadyAssignedOrderIds = repo.getBy(ShippingPackageQuery.builder()
						.orderIds(availableOrderIds)
						.build())
				.stream()
				.map(sp -> OrderId.ofRepoId(sp.getC_Order_ID()))
				.collect(Collectors.toSet());
		final ImmutableList<OrderId> validPurchaseOrdersIds = availableOrderIds
				.stream()
				.filter(orderId -> !alreadyAssignedOrderIds.contains(orderId))
				.collect(ImmutableList.toImmutableList());

		if (validPurchaseOrdersIds.isEmpty())
		{
			Loggables.addLog("No purchase orders found for shipper transportation with ID: {}", shipperTransportationId);
		}

		// Process earliest-promised first (see SEED_ORDER_COMPARATOR) so the "first order" that seeds the transport order's default
		// dates is deterministic even when several purchase orders are assigned in a single call (the DB/set encounter order is not meaningful).
		orderDAO.getByIds(validPurchaseOrdersIds)
				.stream()
				.sorted(SEED_ORDER_COMPARATOR)
				.forEach(order -> {
					Loggables.addLog("Adding purchase order with ID: {} to shipper transportation with ID: {}", order.getC_Order_ID(), shipperTransportationId);
					addPurchaseOrderToShipperTransportation(order, shipperTransportationId);
				});
	}

	public void addOrderLinesToShipperTransportation(@NonNull final ShipperTransportationId shipperTransportationId, @NonNull final Set<OrderLineId> orderLineIds)
	{
		final ImmutableListMultimap<I_C_Order, I_C_OrderLine> orderToLinesMap = orderDAO.getOrderToLinesMap(orderLineIds);

		final I_M_ShipperTransportation shipperTransportation = shipperTransportationDAO.getById(shipperTransportationId);
		orderToLinesMap.keySet()
				.stream()
				// Process earliest-promised first (see SEED_ORDER_COMPARATOR) so the "first order" that seeds the transport order's
				// default dates is deterministic even when a single call carries lines from several purchase orders (the multimap key
				// order is not meaningful). Mirrors the ordering applied in addPurchaseOrdersToShipperTransportation().
				.sorted(SEED_ORDER_COMPARATOR)
				.forEach(order -> addPurchaseOrderLines(shipperTransportation, order, orderToLinesMap.get(order)));
	}

	public void addPurchaseOrderToShipperTransportation(final @NonNull OrderId purchaseOrderId, final @NonNull ShipperTransportationId shipperTransportationId)
	{
		addPurchaseOrderToShipperTransportation(orderDAO.getById(purchaseOrderId), shipperTransportationId);
	}

	private void addPurchaseOrderToShipperTransportation(@NonNull final I_C_Order order,
														 @NonNull final ShipperTransportationId shipperTransportationId)
	{
		final I_M_ShipperTransportation shipperTransportationRecord = shipperTransportationDAO.getById(shipperTransportationId);
		final ShipperId shipperId = ShipperId.ofRepoId(shipperTransportationRecord.getM_Shipper_ID());

		if (order.getM_Shipper_ID() > 0 && shipperId.getRepoId() != order.getM_Shipper_ID())
		{
			Loggables.addLog("Ignoring C_Order.M_Shipper_ID={} of C_Order_ID={}, because M_ShipperTransportation_ID={} takes precedence", order.getM_Shipper_ID(), order.getC_Order_ID(), ShipperTransportationId.toRepoId(shipperTransportationId));
		}

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order);
		addPurchaseOrderLines(shipperTransportationRecord, order, orderLines);
	}

	private void addPurchaseOrderLines(final @NonNull I_M_ShipperTransportation shipperTransportation, final @NonNull I_C_Order order, @NonNull final List<I_C_OrderLine> orderLines)
	{
		assertTransportOrderAcceptsPurchaseDocument(shipperTransportation, order);

		final ShipperTransportationId shipperTransportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());
		// Detect BEFORE any package is created whether this is the very first purchase order assigned to the transport order.
		final boolean isFirstOrderOnTransportation = shipperTransportationDAO.retrieveOrderIds(shipperTransportationId).isEmpty();

		final List<I_C_OrderLine> orderLinesWithLUQty = orderLines.stream()
				.filter(orderBL::isLUQtySet)
				.collect(Collectors.toList());

		final List<I_C_OrderLine> orderLinesWithoutLUQty = getUnassignedOrderLines(orderLines.stream()
				.filter(ol -> !orderLinesWithLUQty.contains(ol) && !ol.isPackagingMaterial())
				.collect(Collectors.toList()));

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bPartnerId, order.getC_BPartner_Location_ID());
		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		// Build an immutable base request and derive per-package builders via toBuilder() to avoid state bleeding across builds
		final PurchaseShippingPackageCreateRequest baseRequest = PurchaseShippingPackageCreateRequest.builder()
				.orderId(orderId)
				.datePromised(order.getDatePromised().toInstant())
				.shipperTransportationId(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()))
				.shiperId(ShipperId.ofRepoId(shipperTransportation.getM_Shipper_ID()))
				.bPartnerLocationId(bPartnerLocationId)
				.orgId(orgId)
				.build();

		final List<I_C_OrderLine> skippedLines = new ArrayList<>();
		int addedCount = 0;

		for (final I_C_OrderLine ol : orderLinesWithoutLUQty)
		{
			final int requiredLUCount = qtyProvider.getRequiredLUCount(order, ol);
			if (requiredLUCount <= 0)
			{
				skippedLines.add(ol);
				continue;
			}

			addPurchaseOrderLineToShipperTransportationId(baseRequest, order, ol, requiredLUCount);
			addedCount++;
		}
		for (final I_C_OrderLine ol : orderLinesWithLUQty)
		{
			final int qtyLUs = ol.getQtyLU().intValueExact();
			addPurchaseOrderLineToShipperTransportationId(baseRequest, order, ol, qtyLUs);
			addedCount++;
		}

		if (!skippedLines.isEmpty())
		{
			final String skippedLineNos = skippedLines.stream()
					.map(ol -> String.valueOf(ol.getLine()))
					.collect(Collectors.joining(", "));

			if (addedCount == 0)
			{
				throw new AdempiereException(MSG_NoLUPackingConfigForOrderLines, skippedLineNos)
						.markAsUserValidationError();
			}
			else
			{
				Loggables.addLog("Skipped {} PO line(s) with no LU packing configuration (lines: {})",
						skippedLines.size(), skippedLineNos);
			}
		}

		// addedCount > 0: a first order whose every line lacks LU packing config already throws above
		// (MSG_NoLUPackingConfigForOrderLines), so on the normal first-order path this guard is a defensive no-op.
		// It also keeps date-defaulting from firing should a future change let an all-lines-skipped call return without throwing.
		if (isFirstOrderOnTransportation && addedCount > 0)
		{
			applyDefaultDatesFromFirstOrder(shipperTransportation, order);
		}
	}

	/**
	 * A purchase order/line is a RECEIPT-side document: it may only join a transport order whose direction is
	 * {@link TransportDirection#isIncomingOrDropship()} (Incoming or Dropship). Blocks the state where an
	 * Outgoing-only (pure sales/shipment) transport order silently ends up carrying purchase shipping packages
	 * with no receipt ever able to link to it.
	 */
	private void assertTransportOrderAcceptsPurchaseDocument(@NonNull final I_M_ShipperTransportation shipperTransportation, @NonNull final I_C_Order order)
	{
		final TransportDirection direction = TransportDirection.ofCode(shipperTransportation.getTransportDirection());
		if (!direction.isIncomingOrDropship())
		{
			throw new AdempiereException(MSG_WrongTransportDirectionForPurchaseOrder, order.getDocumentNo(), shipperTransportation.getDocumentNo())
					.markAsUserValidationError();
		}
	}

	/**
	 * Defaults the transport order's date fields from the first assigned purchase order (each value stays user-overridable afterwards):
	 * <ul>
	 *     <li>ETA = the purchase order's {@code DatePromised} (the promised arrival date)</li>
	 *     <li>ETD = the purchase order's {@code PreparationDate} (its ready/provisioning date), taken as already calculated on the
	 *         order &mdash; by tour planning, or its {@code DatePromised} minus the vendor transport days fallback. We do NOT re-derive
	 *         it here, so the transport-days rule stays in exactly one place ({@code OrderDeliveryDayBL}). Left unset when the PO has none.</li>
	 *     <li>ATD = ETD</li>
	 *     <li>ATA = ETA</li>
	 *     <li>B/L date = ATD</li>
	 * </ul>
	 * Only applies to purchase (inbound) transport orders; the sales flow is left untouched.
	 */
	private void applyDefaultDatesFromFirstOrder(@NonNull final I_M_ShipperTransportation shipperTransportation, @NonNull final I_C_Order order)
	{
		// isOutgoing(), NOT hasShipment(): Dropship carries a shipment too, but must fall through to the
		// purchase-side defaults below.
		if (TransportDirection.ofCode(shipperTransportation.getTransportDirection()).isOutgoing())
		{
			return; // sales behaviour on the transport order must keep working unchanged
		}

		// ETA = the PO's promised (arrival) date; guaranteed non-null here (the caller already dereferences it when building the base
		// package request, and this runs only for completed/closed purchase orders). ETD = the PO's ready/provisioning date, taken
		// straight from its PreparationDate exactly as already calculated on the order (may be null); we never recompute it.
		final Timestamp eta = order.getDatePromised();
		final Timestamp etd = order.getPreparationDate();

		// Fill each field ONLY if the user has not already set it: these are defaults, so a value entered before the first PO
		// was assigned must be kept. (After assignment every value stays freely editable as well.)
		boolean changed = false;
		if (etd != null && shipperTransportation.getETD() == null)
		{
			shipperTransportation.setETD(etd);
			changed = true;
		}
		if (shipperTransportation.getETA() == null)
		{
			shipperTransportation.setETA(eta);
			changed = true;
		}
		// ATD/ATA/B-L date derive from the transport order's ETD/ETA FIELDS (read after the fills above), not from the raw PO
		// dates: if the user pre-set ETD/ETA to something other than the PO default, ATD/ATA/B-L date must follow that value.
		// Each is filled only when its source field is non-null, so an unset ETD (PO without a PreparationDate) never triggers a
		// pointless null-to-null write on ATD/B-L date.
		if (shipperTransportation.getETD() != null && shipperTransportation.getATD() == null)
		{
			shipperTransportation.setATD(shipperTransportation.getETD()); // ATD = ETD
			changed = true;
		}
		if (shipperTransportation.getETA() != null && shipperTransportation.getATA() == null)
		{
			shipperTransportation.setATA(shipperTransportation.getETA()); // ATA = ETA
			changed = true;
		}
		if (shipperTransportation.getATD() != null && shipperTransportation.getBLDate() == null)
		{
			shipperTransportation.setBLDate(shipperTransportation.getATD()); // B/L date = ATD
			changed = true;
		}

		if (changed)
		{
			shipperTransportationDAO.save(shipperTransportation);
		}
	}

	private List<I_C_OrderLine> getUnassignedOrderLines(final List<I_C_OrderLine> orderLines)
	{
		final ImmutableMap<OrderAndLineId, I_C_OrderLine> orderAndLineIdToPoMap = orderLines.stream()
				.collect(ImmutableMap.toImmutableMap(ol -> OrderAndLineId.ofRepoIds(ol.getC_Order_ID(), ol.getC_OrderLine_ID()), Function.identity()));

		final Set<OrderLineId> orderLineIds = orderAndLineIdToPoMap.keySet()
				.stream()
				.map(OrderAndLineId::getOrderLineId)
				.collect(Collectors.toSet());

		final Collection<OrderAndLineId> assignedOrderAndLineIds = repo.getBy(ShippingPackageQuery.builder().orderLineIds(orderLineIds).build())
				.stream()
				.map(sp -> OrderAndLineId.ofRepoIdsOrNull(sp.getC_Order_ID(), sp.getC_OrderLine_ID()))
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

		return orderAndLineIdToPoMap.keySet()
				.stream()
				.filter(olId -> !assignedOrderAndLineIds.contains(olId))
				.map(orderAndLineIdToPoMap::get)
				.collect(Collectors.toList());
	}

	private void addPurchaseOrderLineToShipperTransportationId(@NonNull final PurchaseShippingPackageCreateRequest baseRequest,
															   @NonNull final I_C_Order order,
															   @NonNull final I_C_OrderLine ol,
															   final int qtyLUs)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(ol.getC_OrderLine_ID());

		// For most flows this is a NOOP as the packages should have been deleted beforehand. Keeping this as a safety net.
		deletePackagesForOrderLine(orderLineId);

		final OrgId orgId = OrgId.ofRepoId(ol.getAD_Org_ID());

		final BigDecimal totalTU = ol.getQtyEnteredTU();
		final List<BigDecimal> tuDistribution = tuDistributionProvider.distributeTuUsingLutu(order, ol, totalTU, qtyLUs);
		Check.assume(tuDistribution.size() == qtyLUs, "tuDistribution.size() == qtyLUs");

		for (int i = 0; i < qtyLUs; i++)
		{
			final BigDecimal tuQtyForPackage = tuDistribution.get(i);
			final BigDecimal grossWeightInKg = packageWeightProvider.computeGrossWeightInKg(order, ol, tuQtyForPackage);

			repo.addPurchaseOrderToShipperTransportation(
					baseRequest.toBuilder()
							.orderLineId(orderLineId)
							.sscc(sscc18CodeBL.generate(orgId))
							.tuQty(tuQtyForPackage)
							.grossWeightInKg(grossWeightInKg)
							.build());
		}
	}

	private void deletePackagesForOrderLine(final OrderLineId orderLineId)
	{
		final ImmutableList<Package> existingPackages = repo.getPackagesBy(ShippingPackageQuery.builder().orderLineId(orderLineId).build());
		if (!existingPackages.isEmpty())
		{
			repo.deleteFromShipperTransportation(existingPackages.stream().map(Package::getId).collect(ImmutableList.toImmutableList()));
		}
	}

	public boolean anyMatch(@NonNull final ShippingPackageQuery query)
	{
		return repo.anyMatch(query);
	}

	@Nullable
	public ReportResultData printSSCC18_Labels(@NonNull final OrderId orderId)
	{
		//
		// Create the process info based on AD_Process and AD_PInstance
		final ProcessExecutionResult result = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_ProcessByValue(AD_PROCESS_VALUE_C_Order_SSCC_Print_Jasper)
				//
				// Parameter: REPORT_SQL_QUERY: provide a different report query which will select from our datasource instead of using the standard query (which is M_HU_ID based).
				.addParameter(ReportConstants.REPORT_PARAM_SQL_QUERY, "select * from report.fresh_C_Order_SSCC_Label_Report"
						+ " where C_Order_ID=" + orderId.getRepoId() + " "
						+ " order by M_Package_ID")
				//
				// Execute the actual printing process
				.buildAndPrepareExecution()
				.onErrorThrowException()
				.executeSync()
				.getResult();

		return result.getReportData();
	}

	public boolean hasProcessedShipperTransportation(@NonNull final OrderId orderId)
	{
		return shipperTransportationDAO.anyMatch(ShipperTransportationQuery.builder()
				.orderId(orderId)
				.processed(true)
				.build());
	}

	public boolean deleteShippingPackagesForOrderIfPossible(@NonNull final OrderId orderId)
	{
		if (hasProcessedShipperTransportation(orderId))
		{
			return false;
		}
		repo.deleteBy(ShippingPackageQuery.builder().orderId(orderId).build());
		return true;
	}

	public void syncShippingPackagesFromOrder(@NonNull final I_C_Order order)
	{
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		final Collection<I_M_ShippingPackage> shippingPackages = repo.getBy(ShippingPackageQuery.builder().orderId(orderId).build());
		if (shippingPackages.isEmpty())
		{
			return;
		}

		// Collect current order line IDs to detect removed lines
		final ImmutableSet<Integer> currentOrderLineIds = orderDAO.retrieveOrderLines(order)
				.stream()
				.map(I_C_OrderLine::getC_OrderLine_ID)
				.collect(ImmutableSet.toImmutableSet());

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bPartnerId, order.getC_BPartner_Location_ID());

		final ImmutableSet.Builder<PackageId> packageIdsToDelete = ImmutableSet.builder();
		for (final I_M_ShippingPackage sp : shippingPackages)
		{
			// Collect packages for order lines that no longer exist (deleted during reactivation)
			if (sp.getC_OrderLine_ID() > 0 && !currentOrderLineIds.contains(sp.getC_OrderLine_ID()))
			{
				packageIdsToDelete.add(PackageId.ofRepoId(sp.getM_Package_ID()));
				continue;
			}

			// Sync header-level fields to both M_ShippingPackage and M_Package
			repo.updateShippingPackageAndPackage(sp, bPartnerId, bPartnerLocationId, order.getDatePromised());
		}

		// Batch-delete packages for removed order lines
		final ImmutableSet<PackageId> toDelete = packageIdsToDelete.build();
		if (!toDelete.isEmpty())
		{
			repo.deleteFromShipperTransportation(toDelete);
		}
	}

	public void deleteShippingPackagesForOrderLines(@NonNull final Collection<OrderLineId> orderLineIds, @NonNull final AdMessageKey failureMessage)
	{
		final boolean isDeletePossible = !shipperTransportationDAO.anyMatch(ShipperTransportationQuery.builder()
				.orderLineIds(orderLineIds)
				.processed(true)
				.build());
		if (isDeletePossible)
		{
			repo.deleteBy(ShippingPackageQuery.builder().orderLineIds(orderLineIds).build());
		}
		else
		{
			throw new AdempiereException(failureMessage);
		}
	}

	public void deleteShippingPackagesForOrders(@NonNull final Collection<OrderId> orderIds, @NonNull final AdMessageKey failureMessage)
	{
		final boolean isDeletePossible = !shipperTransportationDAO.anyMatch(ShipperTransportationQuery.builder()
				.orderIds(orderIds)
				.processed(true)
				.build());
		if (isDeletePossible)
		{
			repo.deleteBy(ShippingPackageQuery.builder().orderIds(orderIds).build());
		}
		else
		{
			throw new AdempiereException(failureMessage);
		}
	}
}
