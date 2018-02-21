package de.metas.adempiere.gui.search.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.handlingunits.model.I_DD_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * Wraps an {@link I_DD_OrderLine} and makes it behave like an {@link IHUPackingAware}.
 *
 * @author al
 */
public class DDOrderLineHUPackingAware implements IHUPackingAware
{
	private final I_DD_OrderLine ddOrderLine;

	/**
	 * Plain delegate for fields which are not available in order line
	 */
	private final PlainHUPackingAware values = new PlainHUPackingAware();

	public DDOrderLineHUPackingAware(final I_DD_OrderLine ddOrderLine)
	{
		super();

		Check.assumeNotNull(ddOrderLine, "orderLine not null");
		this.ddOrderLine = ddOrderLine;
	}

	@Override
	public int getM_Product_ID()
	{
		return ddOrderLine.getM_Product_ID();
	}

	@Override
	public I_M_Product getM_Product()
	{
		return ddOrderLine.getM_Product();
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		ddOrderLine.setM_Product_ID(productId);

		values.setM_Product_ID(productId);
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		ddOrderLine.setQtyEntered(qty);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ddOrderLine);
		final BigDecimal qtyOrdered = Services.get(IUOMConversionBL.class).convertToProductUOM(ctx, getM_Product(), getC_UOM(), qty);
		ddOrderLine.setQtyOrdered(qtyOrdered);

		values.setQty(qty);
	}

	@Override
	public BigDecimal getQty()
	{
		return ddOrderLine.getQtyEntered();
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return ddOrderLine.getM_HU_PI_Item_Product();
	}

	@Override
	public void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product huPiItemProduct)
	{
		ddOrderLine.setM_HU_PI_Item_Product(huPiItemProduct);

		values.setM_HU_PI_Item_Product(huPiItemProduct);
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return ddOrderLine.getM_AttributeSetInstance_ID();
	}

	@Override
	public void setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		ddOrderLine.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);

		values.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return ddOrderLine.getC_UOM();
	}

	@Override
	public void setC_UOM(final I_C_UOM uom)
	{
		values.setC_UOM(uom);

		// NOTE: uom is mandatory
		// we assume orderLine's UOM is correct
		if (uom != null)
		{
			ddOrderLine.setC_UOM(uom);
		}
	}

	@Override
	public BigDecimal getQtyTU()
	{
		return ddOrderLine.getQtyEnteredTU();
	}

	@Override
	public void setQtyTU(final BigDecimal qtyPacks)
	{
		ddOrderLine.setQtyEnteredTU(qtyPacks);
		values.setQtyTU(qtyPacks);
	}

	@Override
	public void setDateOrdered(final Timestamp dateOrdered)
	{
		ddOrderLine.setDateOrdered(dateOrdered);
		values.setDateOrdered(dateOrdered);
	}

	@Override
	public Timestamp getDateOrdered()
	{
		return ddOrderLine.getDateOrdered();
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		return ddOrderLine.getM_HU_PI_Item_Product_ID();
	}

	@Override
	public boolean isInDispute()
	{
		// order line has no IsInDispute flag
		return values.isInDispute();
	}

	@Override
	public void setInDispute(final boolean inDispute)
	{
		values.setInDispute(inDispute);
	}

	@Override
	public void setC_BPartner(final I_C_BPartner partner)
	{
		// nothing
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return ddOrderLine.getDD_Order().getC_BPartner();
	}
}
