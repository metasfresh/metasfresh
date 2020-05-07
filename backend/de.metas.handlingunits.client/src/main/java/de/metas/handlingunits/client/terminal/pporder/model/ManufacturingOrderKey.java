package de.metas.handlingunits.client.terminal.pporder.model;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_S_Resource;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.eevolution.api.IPPOrderWorkflowDAO;
import org.eevolution.model.I_PP_Order;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * @author cg
 */
public class ManufacturingOrderKey extends TerminalKey
{
	private final String id;
	private final String documentNo;
	private final KeyNamePair value;
	private final I_PP_Order order;
	private String name = null;

	public ManufacturingOrderKey(final ITerminalContext terminalContext, final I_PP_Order order)
	{
		super(terminalContext);

		Check.assumeNotNull(order, "order not null");

		id = "PP_Order#" + order.getPP_Order_ID();
		documentNo = order.getDocumentNo();
		this.order = order;
		final int orderId = order.getPP_Order_ID();
		value = new KeyNamePair(orderId, documentNo);
	}

	@Override
	public final String getId()
	{
		return id;
	}

	public final void reload()
	{
		InterfaceWrapperHelper.refresh(order);
		updateName();
	}

	private final void updateName()
	{
		final String nameOld = name;
		name = createName();

		listeners.firePropertyChange(PROPERTY_Name, nameOld, name);
	}

	private final String createName()
	{
		final StringBuilder sb = new StringBuilder();

		//
		// DocumentNo
		{
			final String documentNo = order.getDocumentNo();
			sb.append(documentNo);
		}
		
		//
		// Document Type
		{
			I_C_DocType docType = order.getC_DocType();
			if (docType == null || docType.getC_DocType_ID() <= 0)
			{
				docType = order.getC_DocTypeTarget();
			}
			if(docType != null)
			{
				final I_C_DocType docTypeTrl = InterfaceWrapperHelper.translate(docType, I_C_DocType.class);
				sb.append(" - ");
				sb.append(Util.maskHTML(docTypeTrl.getName()));
			}
		}

		//
		// Product Name
		{
			final String productName = order.getM_Product().getName();
			sb.append("<br>");
			sb.append(Util.maskHTML(productName));
		}

		//
		// Qty Delivered / Qty Ordered
		{
			final BigDecimal qtyDelivered = NumberUtils.stripTrailingDecimalZeros(order.getQtyDelivered().setScale(2, RoundingMode.HALF_UP));
			final BigDecimal qtyOrdered = NumberUtils.stripTrailingDecimalZeros(order.getQtyOrdered().setScale(2, RoundingMode.HALF_UP));

			sb.append("<br>");
			sb.append(qtyDelivered);
			sb.append("/");
			sb.append(qtyOrdered);
		}

		//
		// BPartner
		final I_C_BPartner partner = getC_BPartner();
		if (partner != null)
		{
			final String bpartnerName = partner.getName();

			sb.append("<br>");
			sb.append(Util.maskHTML(bpartnerName));
		}

		// Promised Date
		//
		// Production Date

		Timestamp preparationDate = null;

		final I_C_OrderLine salesOrderLine = order.getC_OrderLine();
		if (salesOrderLine != null)
		{
			preparationDate = salesOrderLine.getC_Order().getPreparationDate();
		}
		if (preparationDate != null)
		{
			final String preparationDateStr = TimeUtil.formatDate(preparationDate, "dd.MM.yyyy HH:mm");

			sb.append("<br>");
			sb.append(Util.maskHTML(preparationDateStr));
		}

		//
		// Production Date
		final Timestamp productionDate = order.getDatePromised();
		if (productionDate != null)
		{
			final String productionDateStr = TimeUtil.formatDate(productionDate, "dd.MM.yyyy HH:mm");

			sb.append("<br>");
			sb.append(Util.maskHTML(productionDateStr));
		}

		//
		// Workcenter/Workstation resource
		final I_S_Resource resource = Services.get(IPPOrderWorkflowDAO.class).retrieveResourceForFirstNode(order);
		if (resource != null)
		{
			final String resourceName = resource.getName();

			sb.append("<br>");
			sb.append(Util.maskHTML(resourceName));
		}

		//
		// Center everything and return
		sb.insert(0, "<center>").append("</center>");
		return sb.toString();
	}

	private final I_C_BPartner getC_BPartner()
	{
		return order.getC_BPartner();
	}

	@Override
	public final Object getName()
	{
		if (name == null)
		{
			updateName();
		}
		return name;
	}

	@Override
	public final String getTableName()
	{
		return I_PP_Order.Table_Name;
	}

	@Override
	public final KeyNamePair getValue()
	{
		return value;
	}

	public final I_PP_Order getPP_Order()
	{
		return order;
	}
}
