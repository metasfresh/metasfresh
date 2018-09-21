package de.metas.handlingunits.client.terminal.mmovement.model.split.impl;

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
import java.text.DecimalFormat;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.DisplayType;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.helper.HUTerminalHelper;
import de.metas.util.Check;

/**
 * Customer Unit Key (aka Product)
 *
 * @author tsa
 *
 */
public class CUSplitKey extends TerminalKey
{
	private final I_M_Product product;
	private final BigDecimal qty;
	private final I_C_UOM uom;

	private final String id;
	private final KeyNamePair value;

	private String name;

	public CUSplitKey(final ITerminalContext terminalContext, final I_M_Product product, final BigDecimal qty, final I_C_UOM uom)
	{
		super(terminalContext);

		Check.assumeNotNull(product, "product not null");
		this.product = product;

		Check.assumeNotNull(qty, "qty not null");
		this.qty = qty;

		Check.assumeNotNull(uom, "uom not null");
		this.uom = uom;

		final int productId = product.getM_Product_ID();
		id = getClass().getName() + "-" + productId;
		value = new KeyNamePair(productId, product.getName());

		updateName();
	}

	private void updateName()
	{
		final String nameOld = name;

		final String productName = HUTerminalHelper.truncateName(product.getName());

		final DecimalFormat qtyFormat = DisplayType.getNumberFormat(DisplayType.Quantity);
		final String qtyStr = qtyFormat.format(qty)
				+ " " + uom.getUOMSymbol();

		name = "<center>"
				+ Util.maskHTML(productName)
				+ "<br>"
				+ "- " + Util.maskHTML(qtyStr) + " -"
				+ "</center>";

		listeners.firePropertyChange(PROPERTY_Name, nameOld, name);
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Object getName()
	{
		return name;
	}

	@Override
	public String getTableName()
	{
		return I_M_Product.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public I_M_Product getM_Product()
	{
		return product;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

	public I_C_UOM getC_UOM()
	{
		return uom;
	}
}
