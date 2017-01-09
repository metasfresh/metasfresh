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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * Plain POJO implementation of {@link IHUPackingAware}
 *
 * @author tsa
 *
 */
public class PlainHUPackingAware implements IHUPackingAware
{
	private int productId = -1;
	private int asiId = -1;
	private BigDecimal qty;
	private I_C_UOM uom;
	private I_M_HU_PI_Item_Product huPiItemProduct;
	private BigDecimal qtyPacks;

	private I_C_BPartner partner;
	private Timestamp dateOrdered;

	private boolean inDispute = false;

	@Override
	public int getM_Product_ID()
	{
		return productId;
	}

	@Override
	public I_M_Product getM_Product()
	{
		final int productId = getM_Product_ID();
		if (productId <= 0)
		{
			return null;
		}

		// NOTE: we assume M_Product is cached
		final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, ITrx.TRXNAME_None);
		return product;
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		this.productId = productId;
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	@Override
	public void setC_UOM(final I_C_UOM uom)
	{
		this.uom = uom;
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		this.qty = qty;
	}

	@Override
	public BigDecimal getQty()
	{
		return qty;
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return huPiItemProduct;
	}

	@Override
	public void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product huPiItemProduct)
	{
		this.huPiItemProduct = huPiItemProduct;
	}

	@Override
	public BigDecimal getQtyPacks()
	{
		return qtyPacks;
	}

	@Override
	public void setQtyPacks(final BigDecimal qtyPacks)
	{
		this.qtyPacks = qtyPacks;
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return asiId;
	}

	@Override
	public void setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		asiId = M_AttributeSetInstance_ID;
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return partner;
	}

	@Override
	public void setC_BPartner(final I_C_BPartner partner)
	{
		this.partner = partner;
	}

	@Override
	public void setDateOrdered(final Timestamp dateOrdered)
	{
		this.dateOrdered = dateOrdered;
	}

	@Override
	public Timestamp getDateOrdered()
	{
		return dateOrdered;
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		return huPiItemProduct.getM_HU_PI_Item_Product_ID();
	}

	@Override
	public boolean isInDispute()
	{
		return inDispute;
	}

	@Override
	public void setInDispute(final boolean inDispute)
	{
		this.inDispute = inDispute;
	}

	@Override
	public String toString()
	{
		return String.format("PlainHUPackingAware [productId=%s, asiId=%s, qty=%s, uom=%s, huPiItemProduct=%s, qtyPacks=%s, partner=%s, dateOrdered=%s, inDispute=%s]", productId, asiId, qty, uom,
				huPiItemProduct, qtyPacks, partner, dateOrdered, inDispute);
	}
}
