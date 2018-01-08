package de.metas.material.planning.ddorder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.PlainAttributeSetInstanceAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.planning.ErrorCodes;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.exception.MrpException;
import de.metas.quantity.Quantity;
import lombok.NonNull;

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
	private final ModelProductDescriptorExtractor productDescriptorFactory;

	public DDOrderPojoSupplier(@NonNull final ModelProductDescriptorExtractor productDescriptorFactory)
	{
		this.productDescriptorFactory = productDescriptorFactory;
	}

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

		final IMaterialPlanningContext mrpContext = request.getMrpContext();

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
		DDOrder.DDOrderBuilder ddOrderBuilder = null;

		Quantity qtyToSupplyRemaining = request.getQtyToSupply();
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
				continue;
			}

			//
			// Get the warehouse in transit
			final int warehouseInTrasitId = DDOrderUtil.retrieveInTransitWarehouseId(ctx, warehouseFrom.getAD_Org_ID());
			if (warehouseInTrasitId <= 0)
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
				final int orgBPartnerId = DDOrderUtil.retrieveOrgBPartnerId(ctx, locatorTo.getAD_Org_ID());
				if (orgBPartnerId <= 0)
				{
					// DRP-020: Target Org has no BP linked to it
					final I_AD_Org locatorToOrg = InterfaceWrapperHelper.create(Env.getCtx(), locatorTo.getAD_Org_ID(), I_AD_Org.class, ITrx.TRXNAME_None);
					mrpNotesCollector.newMRPNoteBuilder(mrpContext, "DRP-020")
							.addParameter(I_AD_Org.COLUMNNAME_AD_Org_ID, locatorToOrg.getName())
							.collect();
					//
					continue;
				}

				//
				// Try to find some DD_Order with Shipper , Business Partner and Doc Status = Draft
				// Consolidate the demand in a single order for each Shipper , Business Partner , DemandDateStartSchedule
				ddOrderBuilder = DDOrder.builder()
						.orgId(warehouseTo.getAD_Org_ID())
						.plantId(plant.getS_Resource_ID())
						.productPlanningId(productPlanningData.getPP_Product_Planning_ID())
						.datePromised(supplyDateFinishSchedule)
						.shipperId(networkLine.getM_Shipper_ID())
						.advisedToCreateDDrder(productPlanningData.isCreatePlan());

				builders.add(ddOrderBuilder);

				M_Shipper_ID = networkLine.getM_Shipper_ID();
			}

			//
			// Crate DD order line
			final Quantity qtyToMove = Quantity.of(
					calculateQtyToMove(qtyToSupplyRemaining.getQty(), networkLine.getPercent()),
					qtyToSupplyRemaining.getUOM());

			final DDOrderLine ddOrderLine = createDD_OrderLine(networkLine, qtyToMove, supplyDateFinishSchedule, request);
			ddOrderBuilder.line(ddOrderLine);

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

	@VisibleForTesting
	/* package */final BigDecimal calculateQtyToMove(
			@NonNull final BigDecimal qtyToMoveRequested,
			@NonNull final BigDecimal networkLineTransferPercent)
	{
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

		return qtyToMoveRequested.multiply(networkLineTransferPercentMultiplier);
	}

	private DDOrderLine createDD_OrderLine(
			@Nullable final I_DD_NetworkDistributionLine networkLine,
			@NonNull final Quantity qtyToMove,
			@NonNull final Timestamp supplyDateFinishSchedule,
			@NonNull final IMaterialRequest request)
	{
		final IMaterialPlanningContext mrpContext = request.getMrpContext();

		final PlainAttributeSetInstanceAware asiAware = PlainAttributeSetInstanceAware
				.forProductIdAndAttributeSetInstanceId(
						mrpContext.getM_Product_ID(),
						mrpContext.getM_AttributeSetInstance_ID());
		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(asiAware);

		final int durationDays = DDOrderUtil.calculateDurationDays(mrpContext.getProductPlanning(), networkLine);

		final BigDecimal qtyToMoveInProductUOM = Services.get(IUOMConversionBL.class).convertToProductUOM(qtyToMove, asiAware.getM_Product());

		final DDOrderLine ddOrderline = DDOrderLine.builder()
				.salesOrderLineId(request.getMrpDemandOrderLineSOId())
				.bPartnerId(request.getMrpDemandBPartnerId())
				.productDescriptor(productDescriptor)
				.qty(qtyToMoveInProductUOM)
				.networkDistributionLineId(networkLine.getDD_NetworkDistributionLine_ID())
				.durationDays(durationDays)
				.build();

		return ddOrderline;
	}

}
