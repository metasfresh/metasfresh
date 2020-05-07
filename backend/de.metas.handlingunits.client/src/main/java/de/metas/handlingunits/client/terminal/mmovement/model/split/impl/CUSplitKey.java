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

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.helper.HUTerminalHelper;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

/**
 * Customer Unit Key (aka Product)
 *
 * @author tsa
 *
 */
public class CUSplitKey extends TerminalKey
{
	private final ProductId productId;
	private final BigDecimal qty;
	private final I_C_UOM uom;

	private final String id;
	private final KeyNamePair value;

	private final String productName;
	private String name;

	public CUSplitKey(final ITerminalContext terminalContext,
			@NonNull final ProductId productId,
			@NonNull final BigDecimal qty,
			@NonNull final I_C_UOM uom)
	{
		super(terminalContext);

		this.productId = productId;
		this.qty = qty;
		this.uom = uom;
		id = getClass().getName() + "-" + productId.getRepoId();

		productName = Services.get(IProductBL.class).getProductName(productId);
		value = KeyNamePair.of(productId, productName);

		updateName();
	}

	private void updateName()
	{
		final String nameOld = name;

		final String productName = HUTerminalHelper.truncateName(this.productName);

		final DecimalFormat qtyFormat = DisplayType.getNumberFormat(DisplayType.Quantity);
		final String qtyStr = qtyFormat.format(qty)
				+ " " + uom.getUOMSymbol();

		name = "<center>"
				+ StringUtils.maskHTML(productName)
				+ "<br>"
				+ "- " + StringUtils.maskHTML(qtyStr) + " -"
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

	public ProductId getProductId()
	{
		return productId;
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
