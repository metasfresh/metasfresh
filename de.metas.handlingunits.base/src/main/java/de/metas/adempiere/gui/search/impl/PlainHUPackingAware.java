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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;

import de.metas.adempiere.gui.search.IHUPackingAware;
import lombok.ToString;

/**
 * Plain POJO implementation of {@link IHUPackingAware}
 *
 * @author tsa
 *
 */
@ToString
public class PlainHUPackingAware implements IHUPackingAware
{
	private int productId = -1;
	private int asiId = -1;
	private BigDecimal qty;
	private int uomId;
	private int huPiItemProductId;
	private BigDecimal qtyPacks;

	private int partnerId;
	private Timestamp dateOrdered;

	private boolean inDispute = false;

	@Override
	public int getM_Product_ID()
	{
		return productId;
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		this.productId = productId;
	}

	@Override
	public int getC_UOM_ID()
	{
		return uomId;
	}

	@Override
	public void setC_UOM_ID(final int uomId)
	{
		this.uomId = uomId;
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
	public int getM_HU_PI_Item_Product_ID()
	{
		return huPiItemProductId;
	}

	@Override
	public void setM_HU_PI_Item_Product_ID(final int huPiItemProductId)
	{
		this.huPiItemProductId = huPiItemProductId;
	}

	@Override
	public BigDecimal getQtyTU()
	{
		return qtyPacks;
	}

	@Override
	public void setQtyTU(final BigDecimal qtyPacks)
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
	public int getC_BPartner_ID()
	{
		return partnerId;
	}

	@Override
	public void setC_BPartner_ID(final int partnerId)
	{
		this.partnerId = partnerId;
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
}
