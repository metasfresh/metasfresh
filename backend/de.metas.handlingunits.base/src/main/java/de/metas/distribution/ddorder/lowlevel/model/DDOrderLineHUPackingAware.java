package de.metas.distribution.ddorder.lowlevel.model;

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

import de.metas.adempiere.gui.search.impl.PlainHUPackingAware;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;

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

	public DDOrderLineHUPackingAware(@NonNull final I_DD_OrderLine ddOrderLine)
	{
		Check.assumeNotNull(ddOrderLine, "orderLine not null");
		this.ddOrderLine = ddOrderLine;
	}

	@Override
	public int getM_Product_ID()
	{
		return ddOrderLine.getM_Product_ID();
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

		final ProductId productId = ProductId.ofRepoIdOrNull(getM_Product_ID());
		final I_C_UOM uom = Services.get(IUOMDAO.class).getById(getC_UOM_ID());
		final BigDecimal qtyOrdered = Services.get(IUOMConversionBL.class).convertToProductUOM(productId, uom, qty);
		ddOrderLine.setQtyOrdered(qtyOrdered);

		values.setQty(qty);
	}

	@Override
	public BigDecimal getQty()
	{
		return ddOrderLine.getQtyEntered();
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		return ddOrderLine.getM_HU_PI_Item_Product_ID();
	}

	@Override
	public void setM_HU_PI_Item_Product_ID(final int huPiItemProductId)
	{
		ddOrderLine.setM_HU_PI_Item_Product_ID(huPiItemProductId);

		values.setM_HU_PI_Item_Product_ID(huPiItemProductId);
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
	public int getC_UOM_ID()
	{
		return ddOrderLine.getC_UOM_ID();
	}

	@Override
	public void setC_UOM_ID(final int uomId)
	{
		values.setC_UOM_ID(uomId);

		// NOTE: uom is mandatory
		// we assume orderLine's UOM is correct
		if (uomId > 0)
		{
			ddOrderLine.setC_UOM_ID(uomId);
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
	public void setC_BPartner_ID(final int partnerId)
	{
		// nothing
	}

	@Override
	public int getC_BPartner_ID()
	{
		return ddOrderLine.getDD_Order().getC_BPartner_ID();
	}
}
