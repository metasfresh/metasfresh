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

import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.quantity.Quantity;
import org.adempiere.model.IModelWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.BOMComponentType;

import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.model.I_PP_Order_BOMLine;
import de.metas.materialtracking.qualityBasedInvoicing.ProductionMaterialType;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/* package */class PPOrderBOMLineProductionMaterial extends AbstractProductionMaterial implements IModelWrapper
{
	// services
	private final IHandlingUnitsInfoFactory handlingUnitsInfoFactory = Services.get(IHandlingUnitsInfoFactory.class);
	private final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);

	private final I_PP_Order_BOMLine ppOrderBOMLine;
	private final boolean isCoOrByProduct;
	private final ProductionMaterialType type;

	private IHandlingUnitsInfo _handlingUnitsInfo;
	private boolean _handlingUnitsInfoLoaded = false;

	private final I_M_Product mainComponentProduct;

	public PPOrderBOMLineProductionMaterial(
			@NonNull final org.eevolution.model.I_PP_Order_BOMLine ppOrderBOMLine,
			final I_M_Product mainComponentProduct)
	{
		this.ppOrderBOMLine = InterfaceWrapperHelper.create(ppOrderBOMLine, I_PP_Order_BOMLine.class);
		this.mainComponentProduct = mainComponentProduct;

		isCoOrByProduct = PPOrderUtil.isCoOrByProduct(ppOrderBOMLine);

		if (isCoOrByProduct)
		{
			type = ProductionMaterialType.PRODUCED;
		}
		else
		{
			type = ProductionMaterialType.RAW;
		}

		Check.assumeNotNull(this.getC_UOM(), "getC_UOM() does not return null for {}", this);
		Check.assumeNotNull(this.getM_Product(), "getM_Product() does not return null for {}", this);
	}

	@Override
	public final I_M_Product getM_Product()
	{
		return ppOrderBOMLine.getM_Product();
	}

	@Override
	public BigDecimal getQty()
	{
		Quantity qtyDelivered = orderBOMBL.getQuantities(ppOrderBOMLine)
				.getQtyIssuedOrReceivedActual();
		if (isCoOrByProduct)
		{
			qtyDelivered = qtyDelivered.negate();
		}

		return qtyDelivered.toBigDecimal();
	}

	@Override
	public final I_C_UOM getC_UOM()
	{
		return orderBOMBL.getBOMLineUOM(ppOrderBOMLine);
	}

	@Override
	public ProductionMaterialType getType()
	{
		return type;
	}

	@Override
	public void setQM_QtyDeliveredPercOfRaw(final BigDecimal qtyDeliveredPercOfRaw)
	{
		ppOrderBOMLine.setQM_QtyDeliveredPercOfRaw(qtyDeliveredPercOfRaw);
	}

	@Override
	public BigDecimal getQM_QtyDeliveredPercOfRaw()
	{
		return ppOrderBOMLine.getQM_QtyDeliveredPercOfRaw();
	}

	@Override
	public void setQM_QtyDeliveredAvg(final BigDecimal qtyDeliveredAvg)
	{
		ppOrderBOMLine.setQM_QtyDeliveredAvg(qtyDeliveredAvg);
	}

	@Override
	public BigDecimal getQM_QtyDeliveredAvg()
	{
		return ppOrderBOMLine.getQM_QtyDeliveredAvg();
	}

	@Override
	public Object getModel()
	{
		return ppOrderBOMLine;
	}

	@Override
	public String getVariantGroup()
	{
		return ppOrderBOMLine.getVariantGroup();
	}

	@Override
	public BOMComponentType getComponentType()
	{
		return BOMComponentType.ofCode(ppOrderBOMLine.getComponentType());
	}

	@Override
	public IHandlingUnitsInfo getHandlingUnitsInfo()
	{
		if (!_handlingUnitsInfoLoaded)
		{
			_handlingUnitsInfo = handlingUnitsInfoFactory.createFromModel(ppOrderBOMLine);
			_handlingUnitsInfoLoaded = true;
		}
		return _handlingUnitsInfo;
	}

	@Override
	public I_M_Product getMainComponentProduct()
	{
		return mainComponentProduct;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
