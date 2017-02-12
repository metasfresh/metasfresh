package de.metas.order.process;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.Mutable;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.process.DocAction;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.model.I_C_Order;
import de.metas.order.process.impl.CreatePOFromSOsAggregationKeyBuilder;
import de.metas.order.process.impl.CreatePOFromSOsAggregator;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Creates pruchase order(s) from sales order(s).
 * This process is to replace the old org.compiere.process.OrderPOCreate.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task http://dewiki908/mediawiki/index.php/09557_Wrong_aggregation_on_OrderPOCreate_%28109614894753%29
 */
public class C_Order_CreatePOFromSOs
		extends JavaProcess
		implements IProcessPrecondition
{

	private Timestamp p_DatePromised_From;

	private Timestamp p_DatePromised_To;

	private int p_C_BPartner_ID;

	private int p_Vendor_ID;

	private int p_C_Order_ID;

	private boolean p_IsDropShip;

	private String p_poReference;

	private final IC_Order_CreatePOFromSOsDAO orderCreatePOFromSOsDAO = Services.get(IC_Order_CreatePOFromSOsDAO.class);

	private final IC_Order_CreatePOFromSOsBL orderCreatePOFromSOsBL = Services.get(IC_Order_CreatePOFromSOsBL.class);

	/**
	 * @task http://dewiki908/mediawiki/index.php/07228_Create_bestellung_from_auftrag_more_than_once_%28100300573628%29
	 */
	private final boolean p_allowMultiplePOOrders = true;

	@Override
	protected void prepare()
	{
		final IRangeAwareParams params = getParameterAsIParams();

		p_DatePromised_From = params.getParameterAsTimestamp("DatePromised_From");
		p_DatePromised_To = params.getParameterAsTimestamp("DatePromised_To");
		p_C_BPartner_ID = params.getParameterAsInt("C_BPartner_ID");
		p_Vendor_ID = params.getParameterAsInt("Vendor_ID");
		p_C_Order_ID = params.getParameterAsInt("C_Order_ID");
		p_IsDropShip = params.getParameterAsBool("IsDropShip");
		p_poReference = params.getParameterAsString("POReference");
	}

	@Override
	protected String doIt() throws Exception
	{
		final Mutable<Integer> purchaseOrderLineCount = new Mutable<Integer>(0);

		final Iterator<I_C_Order> it = orderCreatePOFromSOsDAO.createSalesOrderIterator(
				this,
				p_allowMultiplePOOrders,
				p_C_Order_ID,
				p_C_BPartner_ID,
				p_Vendor_ID,
				p_poReference,
				p_DatePromised_From,
				p_DatePromised_To);

		final String purchaseQtySource = orderCreatePOFromSOsBL.getConfigPurchaseQtySource();
		final CreatePOFromSOsAggregator workpackageAggregator = new CreatePOFromSOsAggregator(this,
				p_IsDropShip,
				purchaseQtySource);

		workpackageAggregator.setItemAggregationKeyBuilder(new CreatePOFromSOsAggregationKeyBuilder(p_Vendor_ID, this));
		workpackageAggregator.setGroupsBufferSize(100);

		for (final I_C_Order salesOrder : IteratorUtils.asIterable(it))
		{
			final List<I_C_OrderLine> salesOrderLines = orderCreatePOFromSOsDAO.retrieveOrderLines(salesOrder,
					p_allowMultiplePOOrders,
					purchaseQtySource);
			for (final I_C_OrderLine salesOrderLine : salesOrderLines)
			{
				workpackageAggregator.add(salesOrderLine);
				purchaseOrderLineCount.setValue(purchaseOrderLineCount.getValue() + 1);
			}
		}
		workpackageAggregator.closeAllGroups();

		return "Success";
	}

	/**
	 * @return <code>true</code> if the given gridTab is a completed sales order.
	 */
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!I_C_Order.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.reject();
		}

		final I_C_Order order = context.getSelectedModel(I_C_Order.class);
		return ProcessPreconditionsResolution.acceptIf(order.isSOTrx()
				&& DocAction.STATUS_Completed.equals(order.getDocStatus()));
	}

}
