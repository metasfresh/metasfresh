package de.metas.shipping;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.impl.CreateShipperTransportationRequest;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
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
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Service
@RequiredArgsConstructor
public class PurchaseOrderToShipperTransportationService
{
	@NonNull private final PurchaseOrderToShipperTransportationRepository repo;

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	private final ISSCC18CodeBL sscc18CodeBL = Services.get(ISSCC18CodeBL.class);

	public static PurchaseOrderToShipperTransportationService newInstanceForUnitTesting()
	{
		return new PurchaseOrderToShipperTransportationService(new PurchaseOrderToShipperTransportationRepository());
	}

	private static final String AD_PROCESS_VALUE_C_Order_SSCC_Print = "C_Order_SSCC_Print";

	public void addPurchaseOrdersToShipperTransportation(@NonNull final ShipperTransportationId shipperTransportationId, @NonNull final IQueryFilter<I_C_Order> queryFilter)
	{
		final ImmutableList<OrderId> validPurchaseOrdersIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order.class)
				.addInArrayFilter(I_C_Order.COLUMNNAME_DocStatus, DocStatus.Completed, DocStatus.Closed)
				.filter(queryFilter)
				.create()
				.idsAsSet(OrderId::ofRepoId)
				.stream()
				.filter(repo::purchaseOrderNotInShipperTransportation)
				.collect(ImmutableList.toImmutableList());

		for (final OrderId purchaseOrderId : validPurchaseOrdersIds)
		{
			addPurchaseOrderToShipperTransportation(purchaseOrderId, shipperTransportationId);
		}
	}

	public void addPurchaseOrderToCurrentShipperTransportation(final @NonNull I_C_Order purchaseOrder)
	{
		final ShipperId shipperId = ShipperId.ofRepoIdOrNull(purchaseOrder.getM_Shipper_ID());
		if (shipperId == null)
		{
			return;
		}
		final ShipperTransportationId shipperTransportationId = shipperTransportationDAO.getOrCreate(CreateShipperTransportationRequest.builder()
				.shipperId(shipperId)
				.orgId(OrgId.ofRepoId(purchaseOrder.getAD_Org_ID()))
				.assignAnonymouslyPickedHUs(true)
				.shipDate(TimeUtil.asLocalDate(purchaseOrder.getDatePromised()))
				.shipperBPartnerAndLocationId(BPartnerLocationId.ofRepoId(BPartnerId.ofRepoId(purchaseOrder.getC_BPartner_ID()), purchaseOrder.getC_BPartner_Location_ID()))
				.build());
		addPurchaseOrderToShipperTransportation(purchaseOrder, shipperTransportationId);
	}

	public void addPurchaseOrderToShipperTransportation(final @NonNull OrderId purchaseOrderId, final @Nullable ShipperTransportationId shipperTransportationId)
	{
		addPurchaseOrderToShipperTransportation(orderDAO.getById(purchaseOrderId), shipperTransportationId);
	}

	private void addPurchaseOrderToShipperTransportation(final @NonNull org.compiere.model.I_C_Order order, final @Nullable ShipperTransportationId shipperTransportationId)
	{
		final ShipperId shipperId = ShipperId.ofRepoIdOrNull(order.getM_Shipper_ID());
		if (shipperId == null)
		{
			return;
		}
		final ShipperTransportationId shipperTransportationIdToUse = shipperTransportationId != null
				? shipperTransportationId
				: shipperTransportationDAO.getOrCreate(CreateShipperTransportationRequest.builder()
				.shipperId(shipperId)
				.orgId(OrgId.ofRepoId(order.getAD_Org_ID()))
				.shipDate(TimeUtil.asLocalDate(order.getDatePromised()))
				.assignAnonymouslyPickedHUs(true)
				.shipperBPartnerAndLocationId(BPartnerLocationId.ofRepoId(BPartnerId.ofRepoId(order.getC_BPartner_ID()), order.getC_BPartner_Location_ID()))
				.build());

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order);
		final List<I_C_OrderLine> orderLinesWithLUQty = orderLines.stream()
				.filter(orderBL::isLUQtySet)
				.collect(Collectors.toList());
		final boolean isOrderLinesWithoutLUQtyExist = orderLines.stream()
				.anyMatch(ol -> !orderLinesWithLUQty.contains(ol) && !ol.isPackagingMaterial());

		final I_M_ShipperTransportation shipperTransportation = shipperTransportationDAO.getById(shipperTransportationIdToUse);

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bPartnerId, order.getC_BPartner_Location_ID());
		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());
		final PurchaseShippingPackageCreateRequest.PurchaseShippingPackageCreateRequestBuilder requestTemplate = PurchaseShippingPackageCreateRequest.builder()
				.orderId(OrderId.ofRepoId(order.getC_Order_ID()))
				.datePromised(order.getDatePromised().toInstant())
				.shipperTransportationId(shipperTransportationIdToUse)
				.shiperId(ShipperId.ofRepoId(shipperTransportation.getM_Shipper_ID()))
				.bPartnerLocationId(bPartnerLocationId)
				.orgId(orgId);
		if (isOrderLinesWithoutLUQtyExist)
		{
			//create a generic package for all order lines without LUQty set on them
			repo.addPurchaseOrderToShipperTransportation(requestTemplate
					// .sscc(sscc18CodeBL.generate(orgId)) //No requirements currently ask for this
					.build());
		}
		orderLinesWithLUQty
				.forEach(ol -> addPurchaseOrderLineToShipperTransportationId(requestTemplate, ol));
	}

	private void addPurchaseOrderLineToShipperTransportationId(@NonNull final PurchaseShippingPackageCreateRequest.PurchaseShippingPackageCreateRequestBuilder requestTemplate, @NonNull final I_C_OrderLine ol)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(ol.getC_OrderLine_ID());
		final ImmutableList<Package> existingPackages = repo.getPackagesByOrderLineIds(Collections.singleton(orderLineId));
		final int qtyLUs = ol.getQtyLU().intValueExact();

		final int existingPackagesCount = existingPackages.size();
		if (existingPackagesCount > qtyLUs)
		{
			final ImmutableList<PackageId> packageIdsToRemove = existingPackages.subList(qtyLUs - 1, existingPackages.size() - 1)
					.stream()
					.map(Package::getId)
					.collect(ImmutableList.toImmutableList());
			repo.removeFromShipperTransportation(packageIdsToRemove);
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

	@Nullable
	public ReportResultData printSSCC18_Labels(
			@NonNull final Properties ctx,
			@NonNull final OrderId orderId)
	{
		final ImmutableList<PackageId> packageIDs = repo.getPackageIDsByOrderId(orderId);

		Check.assumeNotEmpty(packageIDs, "packageIDs not empty");

		//
		// Create the process info based on AD_Process and AD_PInstance
		final ProcessExecutionResult result = ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_ProcessByValue(AD_PROCESS_VALUE_C_Order_SSCC_Print)
				//
				// Parameter: REPORT_SQL_QUERY: provide a different report query which will select from our datasource instead of using the standard query (which is M_HU_ID based).
				.addParameter(ReportConstants.REPORT_PARAM_SQL_QUERY, "select * from report.fresh_M_Package_SSCC_Label_Report"
						+ " where AD_PInstance_ID=" + ReportConstants.REPORT_PARAM_SQL_QUERY_AD_PInstance_ID_Placeholder + " "
						+ " order by C_OrderLine_ID")
				//
				// Execute the actual printing process
				.buildAndPrepareExecution()
				.onErrorThrowException()
				// Create a selection with the M_Package_IDs that we need to print.
				// The report will fetch it from selection.
				.callBefore(pi -> DB.createT_Selection(pi.getPinstanceId(), packageIDs, ITrx.TRXNAME_ThreadInherited))
				.executeSync()
				.getResult();

		return result.getReportData();
	}

}
