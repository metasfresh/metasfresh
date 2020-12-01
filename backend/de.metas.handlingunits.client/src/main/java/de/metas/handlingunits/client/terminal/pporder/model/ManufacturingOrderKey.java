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

import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderQuantities;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.model.I_PP_Order;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;

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
		value = KeyNamePair.of(orderId, documentNo);
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
			final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID());
			I_C_DocType docType = docTypeId != null
					? Services.get(IDocTypeDAO.class).getById(docTypeId)
					: null;
			if (docType == null)
			{
				final DocTypeId docTypeTargetId = DocTypeId.ofRepoIdOrNull(order.getC_DocTypeTarget_ID());
				docType = docTypeTargetId != null
						? Services.get(IDocTypeDAO.class).getById(docTypeTargetId)
						: null;
			}
			
			if(docType != null)
			{
				final I_C_DocType docTypeTrl = InterfaceWrapperHelper.translate(docType, I_C_DocType.class);
				sb.append(" - ");
				sb.append(StringUtils.maskHTML(docTypeTrl.getName()));
			}
		}

		//
		// Product Name
		{
			final ProductId productId = ProductId.ofRepoId(order.getM_Product_ID());
			final String productName = Services.get(IProductBL.class).getProductName(productId);
			sb.append("<br>");
			sb.append(StringUtils.maskHTML(productName));
		}

		//
		// Qty Delivered / Qty Ordered
		{
			final PPOrderQuantities qtys = Services.get(IPPOrderBOMBL.class).getQuantities(order);
			final BigDecimal qtyDelivered = NumberUtils.stripTrailingDecimalZeros(qtys.getQtyReceived().toBigDecimal().setScale(2, RoundingMode.HALF_UP));
			final BigDecimal qtyOrdered = NumberUtils.stripTrailingDecimalZeros(qtys.getQtyRequiredToProduce().toBigDecimal().setScale(2, RoundingMode.HALF_UP));

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
			sb.append(StringUtils.maskHTML(bpartnerName));
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
			sb.append(StringUtils.maskHTML(preparationDateStr));
		}

		//
		// Production Date
		final Timestamp productionDate = order.getDatePromised();
		if (productionDate != null)
		{
			final String productionDateStr = TimeUtil.formatDate(productionDate, "dd.MM.yyyy HH:mm");

			sb.append("<br>");
			sb.append(StringUtils.maskHTML(productionDateStr));
		}

		//
		// Workcenter/Workstation resource
		final PPOrderId orderId = PPOrderId.ofRepoId(order.getPP_Order_ID());
		final String resourceName = Services.get(IPPOrderRoutingRepository.class).retrieveResourceNameForFirstNode(orderId);
		if (!Check.isEmpty(resourceName, true))
		{
			sb.append("<br>");
			sb.append(StringUtils.maskHTML(resourceName));
		}

		//
		// Center everything and return
		sb.insert(0, "<center>").append("</center>");
		return sb.toString();
	}

	private final I_C_BPartner getC_BPartner()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());
		return bpartnerId != null
				? Services.get(IBPartnerDAO.class).getById(bpartnerId)
				: null;
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
