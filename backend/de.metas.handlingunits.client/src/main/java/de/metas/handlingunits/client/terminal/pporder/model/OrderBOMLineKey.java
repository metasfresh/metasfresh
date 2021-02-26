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

import java.awt.Color;

import de.metas.material.planning.pporder.OrderBOMLineQuantities;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.eevolution.api.BOMComponentType;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;

/**
 * {@link I_PP_Order_BOMLine}'s Key
 *
 * @author cg
 * @author tsa
 */
/* package */final class OrderBOMLineKey extends TerminalKey
{
	private static final Color COLOR_NothingIssued = Color.WHITE;
	private static final Color COLOR_PartialIssued = Color.YELLOW;
	private static final Color COLOR_FullyIssued = Color.GREEN;

	//
	// Services
	private final transient IPPOrderBOMBL ppOrderBOMLineBL = Services.get(IPPOrderBOMBL.class);

	//
	private final String id;
	private final String productName;
	private final boolean coProduct;
	private final Quantity qtyRequired;
	private final Quantity qtyDelivered;
	private final KeyNamePair value;
	private final I_PP_Order_BOMLine orderBOMLine;

	/**
	 * Key's Name/Label
	 */
	private String name = null;
	private Color color = null;

	public OrderBOMLineKey(final ITerminalContext terminalContext, final I_PP_Order_BOMLine orderBOMLine)
	{
		super(terminalContext);

		Check.assumeNotNull(orderBOMLine, "orderBOMLine not null");
		this.orderBOMLine = orderBOMLine;

		id = I_PP_Order_BOMLine.COLUMNNAME_PP_Order_BOMLine_ID + "=" + orderBOMLine.getPP_Order_BOMLine_ID();
		productName = orderBOMLine.getM_Product().getName();

		final int orderBOMLineId = orderBOMLine.getPP_Order_BOMLine_ID();
		value = KeyNamePair.of(orderBOMLineId, productName);

		coProduct = PPOrderUtil.isCoOrByProduct(orderBOMLine);
		final OrderBOMLineQuantities bomQuantities = Services.get(IPPOrderBOMBL.class).getQuantities(orderBOMLine);
		qtyRequired = bomQuantities.getQtyRequired();
		qtyDelivered = bomQuantities.getQtyIssuedOrReceived();

		final Quantity qtyOpen;
		if (coProduct)
		{
			qtyOpen = ppOrderBOMLineBL.getQtyToReceive(orderBOMLine);
		}
		else
		{
			qtyOpen = ppOrderBOMLineBL.getQtyToIssue(orderBOMLine);
		}

		//
		// BOM Line Status Color
		if (qtyDelivered.signum() == 0)
		{
			color = COLOR_NothingIssued;
		}
		else if (qtyOpen.signum() <= 0)
		{
			color = COLOR_FullyIssued;
		}
		else
		{
			color = COLOR_PartialIssued;
		}
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Object getName()
	{
		if (name == null)
		{
			name = createName();
		}

		return name;
	}

	private final String createName()
	{
		final StringBuilder sb = new StringBuilder();

		//
		// Line: Qty Delivered / Qty Required
		Quantity qtyDeliveredToDisplay = qtyDelivered;
		Quantity qtyRequiredToDisplay = qtyRequired;
		if (coProduct)
		{
			qtyDeliveredToDisplay = OrderBOMLineQuantities.adjustCoProductQty(qtyDelivered);
			qtyRequiredToDisplay = OrderBOMLineQuantities.adjustCoProductQty(qtyRequired);
		}
		sb.append(NumberUtils.stripTrailingDecimalZeros(qtyDeliveredToDisplay.toBigDecimal()))
				.append("/")
				.append(NumberUtils.stripTrailingDecimalZeros(qtyRequiredToDisplay.toBigDecimal()));

		//
		// Line(s): Product Name
		sb.append("<br>").append("x");
		sb.append("<br>").append(StringUtils.maskHTML(productName));

		//
		// Line: Component Type / Variant Group
		sb.append("<br>");
		{
			// Component Type
			final String componentType = orderBOMLine.getComponentType();
			final String componentTypeName = Services.get(IADReferenceDAO.class)
					.retrieveListNameTrl(Env.getCtx(), BOMComponentType.AD_REFERENCE_ID, componentType);
			sb.append(StringUtils.maskHTML(componentTypeName));

			// Variant Group
			final String variantGroup = orderBOMLine.getVariantGroup();
			if (!Check.isEmpty(variantGroup, true))
			{
				sb.append(", ");
				sb.append(StringUtils.maskHTML(variantGroup.trim()));
			}
		}

		//
		// Line: ASI (08074)
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(orderBOMLine.getM_AttributeSetInstance_ID());
		if (asiId.isRegular())
		{
			final String asiDescription = Services.get(IAttributeSetInstanceBL.class).getASIDescriptionById(asiId);

			sb.append("<br>");
			sb.append(StringUtils.maskHTML(asiDescription));
		}

		//
		// Center text and return
		sb.insert(0, "<center>").append("</center>");
		return sb.toString();
	}

	@Override
	public String getTableName()
	{
		return I_PP_Order_BOMLine.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public I_PP_Order_BOMLine getPP_Order_BOMLine()
	{
		return orderBOMLine;
	}

	/**
	 * @return status color
	 */
	public Color getColor()
	{
		return color;
	}

	public boolean isForIssuing()
	{
		return !coProduct;
	}

	public boolean isForReceiving()
	{
		return coProduct;
	}
}
