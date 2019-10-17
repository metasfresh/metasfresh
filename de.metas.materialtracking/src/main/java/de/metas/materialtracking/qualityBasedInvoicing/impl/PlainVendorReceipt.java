package de.metas.materialtracking.qualityBasedInvoicing.impl;

/*
 * #%L
 * de.metas.materialtracking
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
import java.util.Collections;
import java.util.List;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;

import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.util.Check;

/**
 * Plain implementation of {@link IVendorReceipt}.
 *
 * NOTE: getter methods will throw an exception if that value was not previously set because we consider all values as being mandatory.
 */
public class PlainVendorReceipt implements IVendorReceipt<Object>
{
	private I_M_Product product;
	private BigDecimal qtyReceived;
	private I_C_UOM qtyReceivedUOM;
	private IHandlingUnitsInfo handlingUnitsInfo;
	private I_M_PriceList_Version plv;

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("PlainVendorReceipt [");
		sb.append("product=").append(product == null ? "-" : product.getValue());
		sb.append(", qtyReceived=").append(qtyReceived).append(qtyReceivedUOM == null ? "" : qtyReceivedUOM.getUOMSymbol());
		sb.append(", handlingUnitsInfo=").append(handlingUnitsInfo);
		sb.append("]");
		return sb.toString();
	}

	@Override
	public I_M_Product getM_Product()
	{
		Check.assumeNotNull(product, "product not null");
		return product;
	}

	public void setM_Product(final I_M_Product product)
	{
		this.product = product;
	}

	@Override
	public BigDecimal getQtyReceived()
	{
		Check.assumeNotNull(qtyReceived, "qtyReceived not null");
		return qtyReceived;
	}

	public void setQtyReceived(final BigDecimal qtyReceived)
	{
		this.qtyReceived = qtyReceived;
	}

	@Override
	public I_C_UOM getQtyReceivedUOM()
	{
		Check.assumeNotNull(qtyReceivedUOM, "qtyReceivedUOM not null");
		return qtyReceivedUOM;
	}

	public void setQtyReceivedUOM(final I_C_UOM qtyReceivedUOM)
	{
		this.qtyReceivedUOM = qtyReceivedUOM;
	}

	@Override
	public IHandlingUnitsInfo getHandlingUnitsInfo()
	{
		return handlingUnitsInfo;
	}

	public void setHandlingUnitsInfo(final IHandlingUnitsInfo handlingUnitsInfo)
	{
		this.handlingUnitsInfo = handlingUnitsInfo;
	}

	/**
	 * This method does nothing!
	 */
	@Override
	public void add(final Object IGNORED)
	{
	}

	/**
	 * This method returns the empty list.
	 */
	@Override
	public List<Object> getModels()
	{
		return Collections.emptyList();
	}

	@Override public I_M_PriceList_Version getPLV()
	{
		return plv;
	}

	public void setPlv(I_M_PriceList_Version plv)
	{
		this.plv = plv;
	}
}
