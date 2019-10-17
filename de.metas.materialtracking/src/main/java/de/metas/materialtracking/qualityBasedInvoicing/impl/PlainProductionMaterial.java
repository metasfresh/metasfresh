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

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.BOMComponentType;

import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.qualityBasedInvoicing.ProductionMaterialType;
import de.metas.util.Check;
import lombok.NonNull;

/* package */final class PlainProductionMaterial extends AbstractProductionMaterial
{
	private final I_M_Product product;
	private final BigDecimal qty;
	private final I_C_UOM uom;
	private final ProductionMaterialType type;
	private BigDecimal qtyDeliveredPercOfRaw = BigDecimal.ZERO;
	private BigDecimal qtyDeliveredAvg = BigDecimal.ZERO;
	private final IHandlingUnitsInfo handlingUnitsInfo = null;

	public PlainProductionMaterial(
			@NonNull final ProductionMaterialType type,
			@NonNull final I_M_Product product,
			@NonNull final BigDecimal qty,
			@NonNull final I_C_UOM uom)
	{
		this.product = product;
		this.qty = qty;
		this.uom = uom;
		this.type = type;
	}

	@Override
	public String toString()
	{
		return "PlainProductionMaterial ["
				+ "product=" + product.getName()
				+ ", qty=" + qty + " " + uom.getUOMSymbol()
				+ ", type=" + type
				+ "]";
	}

	@Override
	public I_M_Product getM_Product()
	{
		return product;
	}

	@Override
	public BigDecimal getQty()
	{
		return qty;
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	@Override
	public ProductionMaterialType getType()
	{
		return type;
	}

	@Override
	public void setQM_QtyDeliveredPercOfRaw(final BigDecimal qtyDeliveredPercOfRaw)
	{
		Check.assumeNotNull(qtyDeliveredPercOfRaw, "qtyDeliveredPercOfRaw not null");
		this.qtyDeliveredPercOfRaw = qtyDeliveredPercOfRaw;
	}

	@Override
	public BigDecimal getQM_QtyDeliveredPercOfRaw()
	{
		return qtyDeliveredPercOfRaw;
	}

	@Override
	public void setQM_QtyDeliveredAvg(final BigDecimal qtyDeliveredAvg)
	{
		Check.assumeNotNull(qtyDeliveredAvg, "qtyDeliveredAvg not null");
		this.qtyDeliveredAvg = qtyDeliveredAvg;
	}

	@Override
	public BigDecimal getQM_QtyDeliveredAvg()
	{
		return qtyDeliveredAvg;
	}

	@Override
	public String getVariantGroup()
	{
		return null;
	}

	@Override
	public BOMComponentType getComponentType()
	{
		return null;
	}

	@Override
	public IHandlingUnitsInfo getHandlingUnitsInfo()
	{
		return handlingUnitsInfo;
	}

	@Override
	public I_M_Product getMainComponentProduct()
	{
		return null;
	}
}
