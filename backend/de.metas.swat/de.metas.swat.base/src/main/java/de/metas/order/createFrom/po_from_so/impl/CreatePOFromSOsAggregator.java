package de.metas.order.createFrom.po_from_so.impl;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.engine.IDocument;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.createFrom.po_from_so.PurchaseTypeEnum;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.MapReduceAggregator;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.compiere.model.X_C_DocType.DOCSUBTYPE_Mediated;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * Created new purchase orders for sales order lines and contains one instance of {@link CreatePOLineFromSOLinesAggregator} for each created purchase order line.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class CreatePOFromSOsAggregator extends MapReduceAggregator<I_C_Order, I_C_OrderLine>
{
	private static final String MSG_PURCHASE_ORDER_CREATED = "de.metas.order.C_Order_CreatePOFromSOs.PurchaseOrderCreated";
	private final IContextAware context;
	private final boolean p_IsDropShip;
	private static final Logger logger = LogManager.getLogger(CreatePOFromSOsAggregator.class);

	@NonNull
	private final PurchaseTypeEnum p_TypeOfPurchase;
	private final String purchaseQtySource;

	private final I_C_Order dummyOrder;

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	final Map<String, CreatePOLineFromSOLinesAggregator> orderKey2OrderLineAggregator = new HashMap<>();

	@NonNull
	final Map<I_C_Order, List<I_C_OrderLine>> skippedSalesOrderLinesByOrder = new HashMap<>();

	public CreatePOFromSOsAggregator(
			final IContextAware context,
			final String purchaseQtySource,
			@NonNull final PurchaseTypeEnum p_TypeOfPurchase)
	{
		this.context = context;
		this.p_IsDropShip = p_TypeOfPurchase.equals(PurchaseTypeEnum.DROPSHIP);
		this.p_TypeOfPurchase = p_TypeOfPurchase;
		this.purchaseQtySource = purchaseQtySource;

		dummyOrder = InterfaceWrapperHelper.newInstance(I_C_Order.class, context);
		dummyOrder.setDocumentNo(CreatePOFromSOsAggregationKeyBuilder.KEY_SKIP);
	}

	@NonNull
	public Optional<String> getSkippedLinesMessage()
	{
		if (skippedSalesOrderLinesByOrder.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(skippedSalesOrderLinesByOrder.entrySet()
				.stream()
				.map(entry -> {
					final I_C_Order salesOrder = entry.getKey();
					final List<I_C_OrderLine> salesOrderLines = entry.getValue();

					return salesOrderLines.stream()
							.map(orderLine -> salesOrder.getDocumentNo() + "-" + orderLine.getLine())
							.collect(Collectors.joining(", "));
				})
				.collect(Collectors.joining(", ")));
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * Creates a new purchase order.
	 */
	@Override
	protected I_C_Order createGroup(final Object vendorBPartnerValue, final I_C_OrderLine salesOrderLine)
	{
		if (CreatePOFromSOsAggregationKeyBuilder.KEY_SKIP.equals(vendorBPartnerValue))
		{
			return dummyOrder;
			// nothing to do;
		}

		final I_C_Order salesOrder = salesOrderLine.getC_Order();

		final I_C_BPartner vendor = bpartnerDAO.retrieveBPartnerByValue(context.getCtx(), (String)vendorBPartnerValue);
		if (vendor == null)
		{
			throw new AdempiereException("No vendor found for Value=" + vendorBPartnerValue + " or vendor is not active.");
		}

		final I_C_Order purchaseOrder = createPurchaseOrder(vendor, salesOrder);

		final String msg = msgBL.getMsg(context.getCtx(),
				MSG_PURCHASE_ORDER_CREATED,
				new Object[] { purchaseOrder.getDocumentNo() });
		Loggables.addLog(msg);

		return purchaseOrder;
	}

	@Override
	protected void closeGroup(final I_C_Order purchaseOrder)
	{
		final CreatePOLineFromSOLinesAggregator orderLineAggregator = getCreateLineAggregator(purchaseOrder);
		orderLineAggregator.closeAllGroups();

		try
		{
			if (orderLineAggregator.getItemsCount() <= 0)
			{
				InterfaceWrapperHelper.delete(purchaseOrder);
			}
			else
			{
				// task 09802: Make docAction COmplete
				purchaseOrder.setDocAction(IDocument.ACTION_Complete);

				InterfaceWrapperHelper.save(purchaseOrder);
			}
		}
		catch (final Throwable t)
		{
			Loggables.addLog("@Error@: " + t);
			throw AdempiereException.wrapIfNeeded(t);
		}
	}

	@Override
	protected void addItemToGroup(final I_C_Order purchaseOrder, final I_C_OrderLine salesOrderLine)
	{
		final I_C_Order salesOrder = salesOrderLine.getC_Order();
		final CreatePOLineFromSOLinesAggregator orderLineAggregator = getCreateLineAggregator(purchaseOrder);
		if (orderLineAggregator.getPurchaseOrder() == dummyOrder)
		{
			collectSkippedLine(salesOrder, salesOrderLine);

			Loggables.withLogger(logger, Level.DEBUG).addLog("Skipped sales order line: {}", salesOrderLine.getC_OrderLine_ID());
			return;// nothing to do
		}

		orderLineAggregator.add(salesOrderLine);

		if (purchaseOrder.getLink_Order_ID() > 0 &&
				purchaseOrder.getLink_Order_ID() != salesOrder.getC_Order_ID())
		{
			purchaseOrder.setLink_Order(null);
		}
	}

	private CreatePOLineFromSOLinesAggregator getCreateLineAggregator(final I_C_Order pruchaseOrder)
	{
		CreatePOLineFromSOLinesAggregator orderLinesAggregator = orderKey2OrderLineAggregator.get(pruchaseOrder.getDocumentNo());
		if (orderLinesAggregator == null)
		{
			orderLinesAggregator = new CreatePOLineFromSOLinesAggregator(pruchaseOrder, purchaseQtySource, p_TypeOfPurchase);
			orderLinesAggregator.setItemAggregationKeyBuilder(CreatePOLineFromSOLinesAggregationKeyBuilder.INSTANCE);
			orderLinesAggregator.setGroupsBufferSize(100);

			orderKey2OrderLineAggregator.put(pruchaseOrder.getDocumentNo(), orderLinesAggregator);
		}
		return orderLinesAggregator;
	}

	private I_C_Order createPurchaseOrder(
			@NonNull final I_C_BPartner vendor,
			@NonNull final I_C_Order salesOrder)
	{

		final I_C_Order purchaseOrder = InterfaceWrapperHelper.newInstance(I_C_Order.class, context);

		// services
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(salesOrder);

		final OrgId orgId = OrgId.ofRepoId(salesOrder.getAD_Org_ID());

		purchaseOrder.setAD_Org_ID(orgId.getRepoId());
		purchaseOrder.setLink_Order_ID(salesOrder.getC_Order_ID());
		purchaseOrder.setIsSOTrx(false);

		if (PurchaseTypeEnum.MEDIATED.equals(p_TypeOfPurchase))
		{
			orderBL.setPODocTypeTargetId(purchaseOrder, DOCSUBTYPE_Mediated);
		}
		else
		{
			orderBL.setDefaultDocTypeTargetId(purchaseOrder);
		}

		//
		purchaseOrder.setDescription(salesOrder.getDescription());

		if (salesOrder.getPOReference() == null)
		{
			purchaseOrder.setPOReference(salesOrder.getDocumentNo());
		}
		else
		{
			purchaseOrder.setPOReference(salesOrder.getPOReference());
		}
		purchaseOrder.setPriorityRule(salesOrder.getPriorityRule());
		purchaseOrder.setM_Warehouse_ID(findWareousePOId(salesOrder));

		// 08812: Make sure the users are correctly set

		orderBL.setBPartner(purchaseOrder, vendor);
		orderBL.setBill_User_ID(purchaseOrder);

		//
		// SalesRep:
		// * let it to be set from BPartner (this was done above, by orderBL.setBPartner method)
		// * if not set use it from context
		if (purchaseOrder.getSalesRep_ID() <= 0)
		{
			purchaseOrder.setSalesRep_ID(Env.getContextAsInt(ctx, Env.CTXNAME_SalesRep_ID));
		}
		if (purchaseOrder.getSalesRep_ID() <= 0)
		{
			purchaseOrder.setSalesRep_ID(Env.getAD_User_ID(ctx));
		}

		// FW dropship ad
		if (PurchaseTypeEnum.MEDIATED.equals(p_TypeOfPurchase)
				&& salesOrder.isDropShip() && salesOrder.getDropShip_BPartner_ID() != 0)
		{
			purchaseOrder.setIsDropShip(true);
			OrderDocumentLocationAdapterFactory
					.deliveryLocationAdapter(purchaseOrder)
					.setFromDeliveryLocation(salesOrder);
		}

		// Drop Ship
		if (p_IsDropShip)
		{
			purchaseOrder.setIsDropShip(p_IsDropShip);

			if (salesOrder.isDropShip() && salesOrder.getDropShip_BPartner_ID() != 0)
			{
				OrderDocumentLocationAdapterFactory
						.deliveryLocationAdapter(purchaseOrder)
						.setFromDeliveryLocation(salesOrder);
			}
			else
			{
				OrderDocumentLocationAdapterFactory
						.deliveryLocationAdapter(purchaseOrder)
						.setFromShipLocation(salesOrder);
			}

			// get default drop ship warehouse
			final WarehouseId dropshipWarehouseId = orgDAO.getOrgDropshipWarehouseId(orgId);
			if (dropshipWarehouseId != null)
			{
				purchaseOrder.setM_Warehouse_ID(dropshipWarehouseId.getRepoId());
			}
			else
			{
				Loggables.addLog("@Missing@ @AD_OrgInfo@ @DropShip_Warehouse_ID@");
			}
		}

		// References
		purchaseOrder.setC_Activity_ID(salesOrder.getC_Activity_ID());
		purchaseOrder.setC_Campaign_ID(salesOrder.getC_Campaign_ID());
		purchaseOrder.setC_Project_ID(salesOrder.getC_Project_ID());
		purchaseOrder.setUser1_ID(salesOrder.getUser1_ID());
		purchaseOrder.setUser2_ID(salesOrder.getUser2_ID());
		purchaseOrder.setC_Currency_ID(salesOrder.getC_Currency_ID());
		//

		InterfaceWrapperHelper.save(purchaseOrder);
		return purchaseOrder;
	}    // createPOForVendor

	private int findWareousePOId(final I_C_Order salesOrder)
	{
		final WarehouseId warehousePOId = orgsRepo.getOrgPOWarehouseId(OrgId.ofRepoId(salesOrder.getAD_Org_ID()));
		if (warehousePOId != null)
		{
			return warehousePOId.getRepoId();
		}

		return salesOrder.getM_Warehouse_ID();
	}

	private void collectSkippedLine(@NonNull final I_C_Order salesOrder, @NonNull final I_C_OrderLine salesOrderLine)
	{
		final List<I_C_OrderLine> skippedOrderLinesFromCurrentOrder = new ArrayList<>();
		skippedOrderLinesFromCurrentOrder.add(salesOrderLine);
		skippedSalesOrderLinesByOrder.merge(salesOrder, skippedOrderLinesFromCurrentOrder, (oldList, newList) -> {
			oldList.addAll(newList);
			return oldList;
		});
	}
}
