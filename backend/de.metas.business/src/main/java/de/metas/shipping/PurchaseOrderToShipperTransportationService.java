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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.ILUQtyProvider;
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
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.mpackage.Package;
import de.metas.shipping.mpackage.PackageId;
import de.metas.sscc18.ISSCC18CodeBL;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderToShipperTransportationService
{
	private static final AdMessageKey MSG_RECEIPT_SCHEDULE_ASSIGNED_TO_PROCESSED_TRANSPORTATION_ORDER = AdMessageKey.of("ReceiptScheduleAssignedToProcessedTransportationOrder");
	@NonNull private final PurchaseOrderToShipperTransportationRepository repo;

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	private final ISSCC18CodeBL sscc18CodeBL = Services.get(ISSCC18CodeBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ILUQtyProvider qtyProvider;

	public static PurchaseOrderToShipperTransportationService newInstanceForUnitTesting()
	{
		final ILUQtyProvider luQtyProvider = (o, ol) -> 1;
		return new PurchaseOrderToShipperTransportationService(PurchaseOrderToShipperTransportationRepository.newInstanceForUnitTesting(), luQtyProvider);
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

		for (final OrderId purchaseOrderId : validPurchaseOrdersIds)
		{
			Loggables.addLog("Adding purchase order with ID: {} to shipper transportation with ID: {}", purchaseOrderId, shipperTransportationId);
			addPurchaseOrderToShipperTransportation(purchaseOrderId, shipperTransportationId);
		}
	}

	private boolean isOrderNotOnShipperTransportation(@NonNull final OrderId orderId)
	{
		return !repo.anyMatch(ShippingPackageQuery.builder().orderId(orderId).build());
	}

	public void addOrderLinesToShipperTransportation(@NonNull final ShipperTransportationId shipperTransportationId, @NonNull final Collection<OrderAndLineId> orderAndLineIds)
	{
		final ImmutableListMultimap<I_C_Order, I_C_OrderLine> orderToLinesMap = orderDAO.getOrderToLinesMap(orderAndLineIds);

		orderToLinesMap.keySet()
				.forEach(order -> addPurchaseOrderLines(shipperTransportationDAO.getById(shipperTransportationId), order, orderToLinesMap.get(order)));
	}

	public void addPurchaseOrderToShipperTransportation(final @NonNull OrderId purchaseOrderId, final @NonNull ShipperTransportationId shipperTransportationId)
	{
		addPurchaseOrderToShipperTransportation(orderDAO.getById(purchaseOrderId), shipperTransportationId);
	}

	private void addPurchaseOrderToShipperTransportation(@NonNull final org.compiere.model.I_C_Order order,
														 @NonNull final ShipperTransportationId shipperTransportationId)
	{
		final I_M_ShipperTransportation shipperTransportationRecord = shipperTransportationDAO.getById(shipperTransportationId);
		final ShipperId shipperId = ShipperId.ofRepoId(shipperTransportationRecord.getM_Shipper_ID());

		if (order.getM_Shipper_ID() > 0 && shipperId.getRepoId() != order.getM_Shipper_ID())
		{
			Loggables.addLog("Ignoring C_Order.M_Shipper_ID={} of C_Order_ID={}, because M_ShipperTransportation_ID={} takes precedence", order.getM_Shipper_ID(), order.getM_Shipper_ID(), ShipperTransportationId.toRepoId(shipperTransportationId));
		}

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order);
		addPurchaseOrderLines(shipperTransportationRecord, order, orderLines);
	}

	private void addPurchaseOrderLines(final @NonNull I_M_ShipperTransportation shipperTransportation, final org.compiere.model.@NonNull I_C_Order order, @NonNull final List<I_C_OrderLine> orderLines)
	{
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
		final PurchaseShippingPackageCreateRequest.PurchaseShippingPackageCreateRequestBuilder requestTemplate = PurchaseShippingPackageCreateRequest.builder()
				.orderId(orderId)
				.datePromised(order.getDatePromised().toInstant())
				.shipperTransportationId(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()))
				.shiperId(ShipperId.ofRepoId(shipperTransportation.getM_Shipper_ID()))
				.bPartnerLocationId(bPartnerLocationId)
				.orgId(orgId);

		for (final I_C_OrderLine ol : orderLinesWithoutLUQty)
		{
			final int requiredLUCount = qtyProvider.getRequiredLUCount(order, ol);
			for (int i = 0; i < requiredLUCount; i++)
			{
				repo.addPurchaseOrderToShipperTransportation(requestTemplate
						.orderLineId(OrderLineId.ofRepoId(ol.getC_OrderLine_ID()))
						.sscc(sscc18CodeBL.generate(orgId))
						.build());
			}
		}
		for (final I_C_OrderLine ol : orderLinesWithLUQty)
		{
			addPurchaseOrderLineToShipperTransportationId(requestTemplate, ol);
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
				.map(sp -> OrderAndLineId.ofRepoIds(sp.getC_Order_ID(), sp.getC_OrderLine_ID()))
				.collect(Collectors.toSet());
		return orderAndLineIdToPoMap.keySet()
				.stream()
				.filter(olId -> !assignedOrderAndLineIds.contains(olId))
				.map(orderAndLineIdToPoMap::get)
				.collect(Collectors.toList());
	}

	private void addPurchaseOrderLineToShipperTransportationId(@NonNull final PurchaseShippingPackageCreateRequest.PurchaseShippingPackageCreateRequestBuilder requestTemplate, @NonNull final I_C_OrderLine ol)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(ol.getC_OrderLine_ID());
		final ImmutableList<Package> existingPackages = repo.getPackagesBy(ShippingPackageQuery.builder().orderLineId(orderLineId).build());
		final int qtyLUs = ol.getQtyLU().intValueExact();

		final int existingPackagesCount = existingPackages.size();
		if (existingPackagesCount > qtyLUs)
		{
			final ImmutableList<PackageId> packageIdsToRemove = existingPackages.subList(qtyLUs - 1, existingPackages.size() - 1)
					.stream()
					.map(Package::getId)
					.collect(ImmutableList.toImmutableList());
			repo.deleteFromShipperTransportation(packageIdsToRemove);
		}
		else if (existingPackagesCount < qtyLUs)
		{
			requestTemplate.orderLineId(orderLineId);
			final OrgId orgId = OrgId.ofRepoId(ol.getAD_Org_ID());

			for (int i = 0; i < qtyLUs - existingPackagesCount; i++)
			{
				repo.addPurchaseOrderToShipperTransportation(requestTemplate
						.sscc(sscc18CodeBL.generate(orgId))
						.build());
			}
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

	public boolean deleteShippingPackagesForOrderIfPossible(@NonNull final OrderId orderId)
	{
		final boolean isDeletePossible = !shipperTransportationDAO.anyMatch(ShipperTransportationQuery.builder()
				.orderId(orderId)
				.processed(true)
				.build());
		if (isDeletePossible)
		{
			repo.deleteBy(ShippingPackageQuery.builder().orderId(orderId).build());
		}

		return isDeletePossible;
	}

	public void deleteShippingPackagesForOrderLines(@NonNull final Collection<OrderLineId> orderLineIds)
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
			throw new AdempiereException(MSG_RECEIPT_SCHEDULE_ASSIGNED_TO_PROCESSED_TRANSPORTATION_ORDER);
		}
	}
}
