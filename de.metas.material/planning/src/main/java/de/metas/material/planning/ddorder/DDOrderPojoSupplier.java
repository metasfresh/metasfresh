package de.metas.material.planning.ddorder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.material.planning.ErrorCodes;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.exception.MrpException;

/*
 * #%L
 * metasfresh-mrp
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class DDOrderPojoSupplier
{

	/**
	 *
	 * @param request
	 * @param mrpNotesCollector
	 * @return
	 */
	public List<DDOrder> supplyPojos(
			final IMaterialRequest request,
			final IMRPNotesCollector mrpNotesCollector)
	{

		final List<DDOrder.DDOrderBuilder> builders = new ArrayList<>();

		final IMaterialPlanningContext mrpContext = request.getMRPContext();

		final Properties ctx = mrpContext.getCtx();
		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();
		final I_AD_Org org = mrpContext.getAD_Org();
		final I_S_Resource plant = mrpContext.getPlant();

		// TODO vpj-cd I need to create logic for DRP-040 Shipment Due Action Notice
		// Indicates that a shipment for a Order Distribution is due.
		// Action should be taken at the source warehouse to ensure that the order is received on time.

		// TODO vpj-cd I need to create logic for DRP-050 Shipment Pas Due Action Notice
		// Indicates that a shipment for a Order Distribution is past due. You should either delay the orders created the requirement for the product
		// or expedite them when the product does arrive.

		if (productPlanningData.getDD_NetworkDistribution_ID() <= 0)
		{
			// Indicates that the Product Planning Data for this product does not specify a valid network distribution.
			mrpNotesCollector.newMRPNoteBuilder(mrpContext, ErrorCodes.ERR_DRP_060_NoSourceOfSupply)
					.collect();

			return ImmutableList.of();
		}

		final I_DD_NetworkDistribution network = productPlanningData.getDD_NetworkDistribution();
		final List<I_DD_NetworkDistributionLine> networkLines = Services.get(IDistributionNetworkDAO.class)
				.retrieveNetworkLinesByTargetWarehouse(network, productPlanningData.getM_Warehouse_ID());
		if (networkLines.isEmpty())
		{
			// No network lines were found for our target warehouse
			final I_M_Warehouse warehouseTo = productPlanningData.getM_Warehouse();
			mrpNotesCollector.newMRPNoteBuilder(mrpContext, ErrorCodes.ERR_DRP_060_NoSourceOfSupply)
					.setComment("@NotFound@ @DD_NetworkDistributionLine_ID@")
					.addParameter(I_DD_NetworkDistribution.COLUMNNAME_DD_NetworkDistribution_ID, network == null ? "?" : network.getName())
					.addParameter("M_Warehouse_Dest_ID", warehouseTo == null ? "?" : warehouseTo.getName())
					.collect();

			return ImmutableList.of();
		}

		final Timestamp supplyDateFinishSchedule = TimeUtil.asTimestamp(request.getDemandDate());

		int M_Shipper_ID = -1;
		// I_DD_Order order = null;
		DDOrder.DDOrderBuilder orderBuilder = null;

		BigDecimal qtyToSupplyRemaining = request.getQtyToSupply();
		for (final I_DD_NetworkDistributionLine networkLine : networkLines)
		{
			//
			// Check: if we created DD Orders for all qty that needed to be supplied, stop here
			if (qtyToSupplyRemaining.signum() <= 0)
			{
				break;
			}

			// get supply source warehouse and locator
			final I_M_Warehouse warehouseFrom = networkLine.getM_WarehouseSource();
			final I_M_Locator locatorFrom = Services.get(IWarehouseBL.class).getDefaultLocator(warehouseFrom);

			// get supply target warehouse and locator
			final I_M_Warehouse warehouseTo = networkLine.getM_Warehouse();
			final I_M_Locator locatorTo = Services.get(IWarehouseBL.class).getDefaultLocator(warehouseTo);

			if (locatorFrom == null || locatorTo == null)
			{
				mrpNotesCollector.newMRPNoteBuilder(mrpContext, "DRP-001")// FIXME: DRP-001 error code does not exist
						.addParameter(I_DD_NetworkDistribution.COLUMNNAME_DD_NetworkDistribution_ID, network == null ? "?" : network.getName())
						.addParameter(I_DD_NetworkDistributionLine.COLUMNNAME_M_WarehouseSource_ID, warehouseFrom.getName())
						.addParameter(I_DD_NetworkDistributionLine.COLUMNNAME_M_Warehouse_ID, warehouseTo.getName())
						.setComment("No locators found for source or target warehouse")
						.collect();
				//
				continue;
			}

			//
			// Get the warehouse in transit
			final I_M_Warehouse warehouseInTrasit = retrieveInTransitWarehouse(ctx, warehouseFrom.getAD_Org_ID());
			if (warehouseInTrasit == null)
			{
				// DRP-010: Do not exist Transit Warehouse to this Organization
				mrpNotesCollector.newMRPNoteBuilder(mrpContext, ErrorCodes.ERR_DRP_010_InTransitWarehouseNotFound)
						.addParameter(I_AD_Org.COLUMNNAME_AD_Org_ID, org.getName())
						.collect();
				//
				continue;
			}

			//
			// DRP-030: Do not exist Shipper for Create Distribution Order
			if (networkLine.getM_Shipper_ID() <= 0)
			{
				mrpNotesCollector.newMRPNoteBuilder(mrpContext, "DRP-030")
						.addParameter(I_DD_NetworkDistribution.COLUMNNAME_DD_NetworkDistribution_ID, network == null ? "?" : network.getName())
						.addParameter(I_DD_NetworkDistributionLine.COLUMNNAME_DD_NetworkDistributionLine_ID, networkLine)
						.collect();
				//
				continue;
			}

			if (M_Shipper_ID != networkLine.getM_Shipper_ID()) // this is also the case on our first iteration since we initialized M_Shipper_ID := -1
			{
				// Org Must be linked to BPartner
				final I_AD_Org locatorToOrg = locatorTo.getAD_Org();
				final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
				final I_C_BPartner orgBPartner = bpartnerOrgBL.retrieveLinkedBPartner(locatorToOrg);
				if (orgBPartner == null)
				{
					// DRP-020: Target Org has no BP linked to it
					mrpNotesCollector.newMRPNoteBuilder(mrpContext, "DRP-020")
							.addParameter(I_AD_Org.COLUMNNAME_AD_Org_ID, locatorToOrg.getName())
							.collect();
					//
					continue;
				}

				final I_C_BPartner_Location orgBPLocation = bpartnerOrgBL.retrieveOrgBPLocation(mrpContext.getCtx(), locatorToOrg.getAD_Org_ID(), ITrx.TRXNAME_None);

				//
				// Try found some DD_Order with Shipper , Business Partner and Doc Status = Draft
				// Consolidate the demand in a single order for each Shipper , Business Partner , DemandDateStartSchedule
				orderBuilder = getDDOrderFromCache(org,
						plant,
						warehouseInTrasit,
						networkLine.getM_Shipper_ID(),
						orgBPartner.getC_BPartner_ID(),
						request.getDemandDate());

				if (orderBuilder == null)
				{
					orderBuilder = DDOrder.builder()
							.orgId(warehouseTo.getAD_Org_ID())
							.plantId(plant.getS_Resource_ID())
							.bPartnerId(orgBPartner.getC_BPartner_ID())
							.bPartnerLocationId(orgBPLocation.getC_BPartner_Location_ID())
							.plannerId(productPlanningData.getPlanner_ID())
							.inTransitWarehouseId(warehouseInTrasit.getM_Warehouse_ID())
							.dateOrdered(mrpContext.getDate())
							.datePromised(supplyDateFinishSchedule)
							.shipperId(networkLine.getM_Shipper_ID());

					builders.add(orderBuilder);
					addToCache(orderBuilder);
				}
				M_Shipper_ID = networkLine.getM_Shipper_ID();
			}

			//
			// Crate DD order line
			final BigDecimal qtyToMove = calculateQtyToMove(qtyToSupplyRemaining, networkLine.getPercent());
			final DDOrderLine ddOrderLine = createDD_OrderLine(mrpContext, networkLine, locatorFrom, locatorTo, qtyToMove, supplyDateFinishSchedule, request);
			orderBuilder.ddOrderLine(ddOrderLine);

			qtyToSupplyRemaining = qtyToSupplyRemaining.subtract(qtyToMove);
		} // end of the for-loop over networkLines

		//
		// Check: remaining qtyToSupply shall be ZERO
		if (qtyToSupplyRemaining.signum() != 0)
		{
			// TODO: introduce DRP-XXX notice
			throw new MrpException("Cannot create DD Order for required Qty To Supply.")
					.setParameter("QtyToSupply", request.getQtyToSupply())
					.setParameter("QtyToSupply (remaining)", qtyToSupplyRemaining)
					.setParameter("@DD_NetworkDistribution_ID@", network)
					.setParameter("@DD_NetworkDistributionLine_ID@", networkLines)
					.setParameter("MRPContext", mrpContext);
		}

		return builders.stream()
				.map(b -> b.build())
				.collect(Collectors.toList());
	}

	private I_M_Warehouse retrieveInTransitWarehouse(final Properties ctx, final int adOrgId)
	{
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
		return warehouseDAO.retrieveWarehouseInTransitForOrg(ctx, adOrgId);
	}

	private DDOrder.DDOrderBuilder getDDOrderFromCache(final I_AD_Org org,
			final I_S_Resource plant,
			final I_M_Warehouse warehouseInTrasit,
			final int shipperId,
			final int bpartnerId,
			final Date demandDate)
	{
		// TODO implement
		return null;
	}

	private void addToCache(final DDOrder.DDOrderBuilder order)
	{
		// TODO: implement
		// DD_Order_ID = order.getDD_Order_ID();
		// String key = network_line.getM_Shipper_ID() + "#" + C_BPartner_ID + "#" + DemandDateStartSchedule + "DR";
		// dd_order_id_cache.put(key, DD_Order_ID);
	}

	/* package */final BigDecimal calculateQtyToMove(final BigDecimal qtyToMoveRequested, final BigDecimal networkLineTransferPercent)
	{
		Check.assumeNotNull(qtyToMoveRequested, "qtyToMoveRequested not null");
		Check.assumeNotNull(networkLineTransferPercent, "networkLineTransferPercent not null");

		if (networkLineTransferPercent.signum() == 0)
		{
			return BigDecimal.ZERO;
		}
		else if (networkLineTransferPercent.signum() < 0)
		{
			throw new MrpException("NetworkLine's TransferPercent shall not be negative")
					.setParameter("QtyToMove", qtyToMoveRequested)
					.setParameter("Transfer Percent", networkLineTransferPercent);
		}
		else if (networkLineTransferPercent.compareTo(Env.ONEHUNDRED) == 0)
		{
			return qtyToMoveRequested;
		}
		final BigDecimal networkLineTransferPercentMultiplier = networkLineTransferPercent.divide(Env.ONEHUNDRED, 4, RoundingMode.HALF_UP);

		final BigDecimal qtyToMove = qtyToMoveRequested.multiply(networkLineTransferPercentMultiplier);
		return qtyToMove;
	}

	private DDOrderLine createDD_OrderLine(
			final IMaterialPlanningContext mrpContext,
			final I_DD_NetworkDistributionLine networkLine,
			final I_M_Locator locatorFrom,
			final I_M_Locator locatorTo,
			final BigDecimal qtyToMove,
			final Timestamp supplyDateFinishSchedule,
			final IMaterialRequest request)
	{
		final I_M_Product product = mrpContext.getM_Product();

		final int durationDays = calculateDurationDays(mrpContext, networkLine);

		final DDOrderLine ddOrderline = DDOrderLine.builder()
				.demandBPartnerId(request.getMRPDemandBPartnerId())
				.salesOrderLineId(request.getMRPDemandOrderLineSOId())
				.fromLocatorId(locatorFrom.getM_Locator_ID())
				.toLocatorId(locatorTo.getM_Locator_ID())
				.productId(product.getM_Product_ID())
				.qty(qtyToMove)
				.allowPush(networkLine.isDD_AllowPush())
				.keepTargetPlant(networkLine.isKeepTargetPlant())
				.durationDays(durationDays)
				.build();

		return ddOrderline;
	}

	private int calculateDurationDays(final IMaterialPlanningContext mrpContext, final I_DD_NetworkDistributionLine networkLine)
	{
		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();

		//
		// Leadtime
		final int leadtimeDays = productPlanningData.getDeliveryTime_Promised().intValueExact();
		Check.assume(leadtimeDays >= 0, MrpException.class, "leadtimeDays >= 0");

		//
		// Transfer time
		int transferTime = networkLine.getTransfertTime().intValueExact();
		if (transferTime <= 0)
		{
			transferTime = productPlanningData.getTransfertTime().intValueExact();
		}
		Check.assume(transferTime >= 0, MrpException.class, "transferTime >= 0");

		final int durationTotalDays = leadtimeDays + transferTime;
		return durationTotalDays;
	}
}
