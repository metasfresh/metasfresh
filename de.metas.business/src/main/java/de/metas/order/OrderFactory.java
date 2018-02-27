package de.metas.order;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;

/*
 * #%L
 * de.metas.business
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

/**
 * Factory class used to create(and complete) sales or purchase orders.
 * 
 * This is a general purpose class to be used by other more specific factories.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class OrderFactory
{
	public static final OrderFactory newPurchaseOrder()
	{
		return new OrderFactory()
				.soTrx(false);
	}

	public static final OrderFactory newSalesOrder()
	{
		return new OrderFactory()
				.soTrx(true);
	}

	private final I_C_Order order;
	private boolean built = false;

	private List<OrderLineBuilder> orderLineBuilders = new ArrayList<>();

	private OrderFactory()
	{
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();

		order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setDocStatus(IDocument.STATUS_Drafted);
		order.setDocAction(IDocument.ACTION_Complete);
	}

	public I_C_Order createAndComplete()
	{
		createDraft();

		Services.get(IDocumentBL.class).processEx(order, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		return order;
	}

	public I_C_Order createDraft()
	{
		assertNotBuilt();
		built = true;

		if (orderLineBuilders.isEmpty())
		{
			throw new AdempiereException("no lines");
		}

		if (order.getC_DocTypeTarget_ID() <= 0)
		{
			final IOrderBL orderBL = Services.get(IOrderBL.class);
			orderBL.setDocTypeTargetId(order);
		}

		save(order);

		orderLineBuilders.forEach(OrderLineBuilder::build);

		return order;
	}

	private void assertNotBuilt()
	{
		if (built)
		{
			throw new AdempiereException("Already built: " + this);
		}
	}

	/* package */I_C_Order getC_Order()
	{
		return order;
	}

	public OrderLineBuilder newOrderLine()
	{
		final OrderLineBuilder orderLineBuilder = new OrderLineBuilder(this);
		orderLineBuilders.add(orderLineBuilder);
		return orderLineBuilder;
	}

	public Optional<OrderLineBuilder> orderLineByProductAndUom(final int productId, final int uomId)
	{
		return orderLineBuilders.stream()
				.filter(orderLine -> orderLine.getProductId() == productId && orderLine.getUomId() == uomId)
				.findFirst();
	}

	private OrderFactory soTrx(final boolean isSOTrx)
	{
		order.setIsSOTrx(isSOTrx);
		return this;
	}

	public OrderFactory deliveryRule(final String deliveryRule)
	{
		assertNotBuilt();
		order.setDeliveryRule(deliveryRule);
		return this;
	}

	public OrderFactory deliveryViaRule(final String deliveryViaRule)
	{
		assertNotBuilt();
		order.setDeliveryViaRule(deliveryViaRule);
		return this;
	}

	public OrderFactory shipperId(final int shipperId)
	{
		assertNotBuilt();
		order.setM_Shipper_ID(shipperId);
		return this;
	}

	public OrderFactory freightCostRule(final String freightCostRule)
	{
		assertNotBuilt();
		order.setFreightCostRule(freightCostRule);
		return this;
	}

	public OrderFactory paymentRule(final String paymentRule)
	{
		assertNotBuilt();
		order.setPaymentRule(paymentRule);
		return this;
	}

	public OrderFactory paymentTermId(final int paymentTermId)
	{
		assertNotBuilt();
		order.setC_PaymentTerm_ID(paymentTermId);
		return this;
	}

	public OrderFactory invoiceRule(final String invoiceRule)
	{
		assertNotBuilt();
		order.setInvoiceRule(invoiceRule);
		return this;
	}

	public OrderFactory docType(final int docTypeTargetId)
	{
		assertNotBuilt();
		
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		orderBL.setDocTypeTargetIdAndUpdateDescription(order, docTypeTargetId);
		
		return this;
	}

	public OrderFactory warehouseId(final int warehouseId)
	{
		assertNotBuilt();
		order.setM_Warehouse_ID(warehouseId);
		return this;
	}

	public OrderFactory orgId(final int orgId)
	{
		assertNotBuilt();
		order.setAD_Org_ID(orgId);
		return this;
	}

	public OrderFactory datePromised(final Date datePromised)
	{
		assertNotBuilt();
		order.setDatePromised(TimeUtil.asTimestamp(datePromised));
		return this;
	}

	public OrderFactory shipBPartner(final int bpartnerId, final int bpartnerLocationId, final int contactId)
	{
		assertNotBuilt();
		order.setC_BPartner_ID(bpartnerId);
		order.setC_BPartner_Location_ID(bpartnerLocationId);
		order.setAD_User_ID(contactId);
		return this;
	}

	public OrderFactory shipBPartner(final int bpartnerId)
	{
		final int bpartnerLocationId = -1;
		final int contactId = -1;
		shipBPartner(bpartnerId, bpartnerLocationId, contactId);
		return this;
	}

	public OrderFactory billBPartner(final int bpartnerId, final int bpartnerLocationId, final int contactId)
	{
		assertNotBuilt();
		order.setBill_BPartner_ID(bpartnerId);
		order.setBill_Location_ID(bpartnerLocationId);
		order.setBill_User_ID(contactId);
		return this;
	}

	public OrderFactory dropShipBPartner(final int bpartnerId, final int bpartnerLocationId, final int contactId)
	{
		assertNotBuilt();
		order.setDropShip_BPartner_ID(bpartnerId);
		order.setDropShip_Location_ID(bpartnerLocationId);
		order.setDropShip_User_ID(contactId);
		return this;
	}

	public OrderFactory handOverBPartner(final int bpartnerId, final int bpartnerLocationId, final int contactId)
	{
		assertNotBuilt();
		order.setHandOver_Partner_ID(bpartnerId);
		order.setHandOver_Location_ID(bpartnerLocationId);
		order.setIsUseHandOver_Location(bpartnerLocationId > 0);
		return this;
	}

	public OrderFactory pricingSystemId(final int pricingSystemId)
	{
		assertNotBuilt();
		order.setM_PricingSystem_ID(pricingSystemId);
		return this;
	}

	public OrderFactory poReference(final String poReference)
	{
		assertNotBuilt();
		order.setPOReference(poReference);
		return this;
	}
}
