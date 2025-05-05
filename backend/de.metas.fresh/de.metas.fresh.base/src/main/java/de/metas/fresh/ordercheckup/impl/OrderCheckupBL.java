package de.metas.fresh.ordercheckup.impl;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.X_C_Order_MFGWarehouse_Report;
import de.metas.fresh.ordercheckup.IOrderCheckupBL;
import de.metas.fresh.ordercheckup.IOrderCheckupDAO;
import de.metas.fresh.ordercheckup.model.I_C_BPartner;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.pporder.IPPRoutingRepository;
import de.metas.material.planning.pporder.PPRouting;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.organization.OrgId;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.resource.Resource;
import de.metas.resource.ResourceService;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ts
 * @implSpec <a href="http://dewiki908/mediawiki/index.php/09028_Produktionsauftrag-Bestellkontrolle_automatisch_ausdrucken_%28106402701484%29">task</a>
 */
public class OrderCheckupBL implements IOrderCheckupBL
{
	private static final Logger logger = LogManager.getLogger(OrderCheckupBL.class);
	public static final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);

	final IOrderCheckupDAO orderCheckupDAO = Services.get(IOrderCheckupDAO.class);
	final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	final IOrderBL orderBL = Services.get(IOrderBL.class);

	final IMsgBL msgBL = Services.get(IMsgBL.class);
	final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	private static final String SYSCONFIG_ORDERCHECKUP_CREATE_AND_ROUTE_JASPER_REPORTS_ON_SALES_ORDER_COMPLETE = "de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.OnSalesOrderComplete";

	// used for document type X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant
	private static final String SYSCONFIG_ORDERCHECKUP_COPIES = "de.metas.fresh.ordercheckup.Copies";

	// used for document type X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Warehouse
	private static final String SYSCONFIG_ORDERCHECKUP_BARCOE_COPIES = "de.metas.fresh.ordercheckup_barcode.Copies";

	private static final String SYSCONFIG_FAIL_IF_WAREHOUSE_HAS_NO_PLANT = "de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant";

	private static final AdMessageKey MSG_ORDER_WAREHOUSE_HAS_NO_PLANT = AdMessageKey.of("de.metas.fresh.ordercheckup.OrderWarehouseHasNoPlant");

	@Override
	public void generateReportsIfEligible(@NonNull final I_C_Order order)
	{
		// Make sure the order is eligible for reporting
		if (!isEligibleForReporting(order))
		{
			return;
		}

		//
		// Void all previous reports, because we will generate them again.
		voidReports(order);

		//
		// Iterate all order lines and add those lines to corresponding "per workflow" reports.
		final Map<ArrayKey, OrderCheckupBuilder> reportBuilders = new HashMap<>();
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order, I_C_OrderLine.class);
		for (final I_C_OrderLine orderLine : orderLines)
		{
			//
			// Retrieve the product data planning which defines how the order line product will be manufactured
			final ProductPlanning mfgProductPlanning = getMfgProductPlanning(orderLine).orElse(null);
			if (mfgProductPlanning == null)
			{
				logger.info("Skip order line because no manufacturing product planning was found for it: {}", orderLine);
				continue;
			}

			//
			// Retrieve the manufacturing workflow
			if (mfgProductPlanning.getWorkflowId() == null)
			{
				logger.info("Skip order line because no manufacturing workflow was found for it: {}", orderLine);
				continue;
			}

			final ResourceId plantId = mfgProductPlanning.getPlantId();

			final PPRoutingId routingId = mfgProductPlanning.getWorkflowId();
			final PPRouting routing = Services.get(IPPRoutingRepository.class).getById(routingId);

			//
			// Add order line to per Manufacturing warehouse report
			{
				final String documentType = X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Warehouse;
				final UserId responsibleUserId = routing != null ? routing.getUserInChargeId() : null;
				final ArrayKey reportBuilderKey = Util.mkKey(order.getC_Order_ID(), documentType, responsibleUserId);
				OrderCheckupBuilder reportBuilder = reportBuilders.get(reportBuilderKey);
				if (reportBuilder == null)
				{
					final WarehouseId warehouseId = mfgProductPlanning.getWarehouseId();
					reportBuilder = OrderCheckupBuilder.newBuilder()
							.setC_Order(order)
							.setDocumentType(documentType)
							.setWarehouseId(warehouseId)
							.setPlantId(plantId)
							.setReponsibleUserId(responsibleUserId);
					reportBuilders.put(reportBuilderKey, reportBuilder);
				}
				reportBuilder.addOrderLine(orderLine);
			}
		}
		//
		// Iterate all created report builders and actually build them
		for (final OrderCheckupBuilder reportBuilder : reportBuilders.values())
		{
			reportBuilder.build();
		}

		//
		// Create the reports for transportation (and plant manager)
		// task 09508: we actually want it on the report for transportation, no matter if there is manufactoring PP_Product_Planning record.
		{
			// make sure the user knows if the master data is not OK, but also give them a chance to disable the error-exception in urgent cases.
			final I_M_Warehouse warehouse = Services.get(IWarehouseDAO.class).getById(WarehouseId.ofRepoIdOrNull(order.getM_Warehouse_ID()));
			final ResourceId plantId = ResourceId.ofRepoIdOrNull(warehouse.getPP_Plant_ID());

			if (plantId == null)
			{
				final boolean throwIt = sysConfigBL.getBooleanValue(SYSCONFIG_FAIL_IF_WAREHOUSE_HAS_NO_PLANT, true);

				new AdempiereException(
						msgBL.getMsg(
								InterfaceWrapperHelper.getCtx(order),
								MSG_ORDER_WAREHOUSE_HAS_NO_PLANT,
								new Object[] {
										warehouse.getValue() + " - " + warehouse.getName(),
										SYSCONFIG_FAIL_IF_WAREHOUSE_HAS_NO_PLANT }))
						.throwOrLogWarning(throwIt, logger);
			}
			else
			{
				final ResourceService resourceService = SpringContextHolder.instance.getBean(ResourceService.class);
				final Resource plant = resourceService.getResourceById(plantId);
				final UserId responsibleUserId = plant.getResponsibleId();

				final OrderCheckupBuilder reportBuilder = OrderCheckupBuilder.newBuilder()
						.setC_Order(order)
						.setDocumentType(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant)
						.setWarehouseId(null) // no warehouse because we are aggregating on plant level
						.setPlantId(plantId)
						.setReponsibleUserId(responsibleUserId);
				for (final I_C_OrderLine orderLine : orderLines)
				{
					// Don't add the packing materials
					if (orderLine.isPackagingMaterial())
					{
						continue;
					}
					reportBuilder.addOrderLine(orderLine);
				}
				reportBuilder.build();
			}
		}
	}

	private Optional<ProductPlanning> getMfgProductPlanning(final I_C_OrderLine orderLine)
	{
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final OrgId orgId = OrgId.ofRepoIdOrAny(orderLine.getAD_Org_ID());
		return productPlanningDAO.retrieveManufacturingOrTradingPlanning(productId, orgId);
	}

	@Override
	public boolean isEligibleForReporting(@NonNull final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			logger.debug("C_Order_ID {} is not a sales order; nothing to do", order.getC_Order_ID());
			return false; // nothing to do
		}

		// NOTE: don't check the DocStatus because if the method is called from AFTER_COMPLETE, the status is not set yet.
		// More, it does not matter, because we could report anytime.

		return true;
	}

	@Override
	public final boolean isGenerateReportsOnOrderComplete(@NonNull final I_C_Order order)
	{
		if (!isEligibleForReporting(order))
		{
			return false; // nothing to do; log messages were already created in isEligibleForReporting
		}

		final boolean sysConfigValueIsTrue = sysConfigBL.getBooleanValue(
				SYSCONFIG_ORDERCHECKUP_CREATE_AND_ROUTE_JASPER_REPORTS_ON_SALES_ORDER_COMPLETE,
				false, // by default, do nothing. This needs to set up and tested by the customer to make sense
				order.getAD_Client_ID(),
				order.getAD_Org_ID());

		if (!sysConfigValueIsTrue)
		{
			logger.debug("AD_SysConfig {} is *not* set to 'Y' for AD_Client_ID={} and AD_Org_ID={}; nothing to do for C_Order_ID {}.",
					SYSCONFIG_ORDERCHECKUP_CREATE_AND_ROUTE_JASPER_REPORTS_ON_SALES_ORDER_COMPLETE,
					order.getAD_Client_ID(),
					order.getAD_Org_ID(),
					order.getC_Order_ID());
			return false; // nothing to do
		}

		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(orderBL.getBPartner(order), I_C_BPartner.class);
		if (bpartner.isDisableOrderCheckup())
		{
			logger.debug("C_BPartner {} has IsDisableOrderCheckup='Y'; nothing to do for C_Order_ID {}.",
					bpartner.getValue(),
					order.getC_Order_ID());
			return false; // nothing to do
		}

		return true;
	}

	@Override
	public void voidReports(@NonNull final I_C_Order order)
	{
		final List<I_C_Order_MFGWarehouse_Report> reports = orderCheckupDAO.retrieveAllReports(order);
		for (final I_C_Order_MFGWarehouse_Report report : reports)
		{
			report.setIsActive(false);
			InterfaceWrapperHelper.save(report);
		}
	}

	@Override
	public int getNumberOfCopies(@NonNull final I_C_Printing_Queue queueItem, @NonNull final I_AD_Archive printOut)
	{
		final I_C_Order_MFGWarehouse_Report report = getReportOrNull(printOut);

		if (report != null && report.getDocumentType().equals(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Warehouse))
		{
			return Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_ORDERCHECKUP_BARCOE_COPIES, 1, queueItem.getAD_Client_ID(), queueItem.getAD_Org_ID());
		}

		return Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_ORDERCHECKUP_COPIES, 1, queueItem.getAD_Client_ID(), queueItem.getAD_Org_ID());
	}

	@Nullable
	@Override
	public final I_C_Order_MFGWarehouse_Report getReportOrNull(@NonNull final I_AD_Archive printOut)
	{
		if (!tableDAO.isTableId(I_C_Order_MFGWarehouse_Report.Table_Name, printOut.getAD_Table_ID()))
		{
			return null;
		}

		final I_C_Order_MFGWarehouse_Report report = archiveDAO.retrieveReferencedModel(printOut, I_C_Order_MFGWarehouse_Report.class);
		if (report == null)
		{
			//noinspection ThrowableNotThrown
			new AdempiereException("No report was found for " + printOut)
					.throwIfDeveloperModeOrLogWarningElse(logger);
		}

		return report;
	}
}
