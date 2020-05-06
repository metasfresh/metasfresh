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

import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLine;
import de.metas.materialtracking.qualityBasedInvoicing.QualityInspectionLineType;

/* package */class QualityInspectionLine implements IQualityInspectionLine
{
	private QualityInspectionLineType qualityInspectionLineType;
	private String productionMaterialType;
	private I_M_Product product;
	private String name;
	private I_C_UOM uom;
	private BigDecimal qty;
	private boolean negateQtyInReport;
	private BigDecimal qtyProjected;
	private BigDecimal percentage;
	private String componentType;
	private String variantGroup;
	private IHandlingUnitsInfo handlingUnitsInfo;
	private IHandlingUnitsInfo handlingUnitsInfoProjected;

	public QualityInspectionLine()
	{
		super();
	}

	@Override
	public String toString()
	{
		return "QualityInspectionLine ["
				+ "qualityInspectionLineType=" + qualityInspectionLineType
				+ ", productionMaterialType=" + productionMaterialType
				+ ", product=" + (product == null ? "-" : product.getName())
				+ ", name=" + name
				+ ", uom=" + (uom == null ? "-" : uom.getUOMSymbol())
				+ ", qty=" + qty
				+ ", qtyProjected=" + qtyProjected
				+ ", percentage=" + percentage
				+ ", negateQtyInReport=" + negateQtyInReport
				+ ", componentType=" + componentType
				+ ", variantGroup=" + variantGroup
				+ ", handlingUnitsInfo=" + handlingUnitsInfo
				+ ", handlingUnitsInfoProjected=" + handlingUnitsInfoProjected
				+ "]";
	}

	@Override
	public QualityInspectionLineType getQualityInspectionLineType()
	{
		return qualityInspectionLineType;
	}

	@Override
	public void setQualityInspectionLineType(final QualityInspectionLineType qualityInspectionLineType)
	{
		this.qualityInspectionLineType = qualityInspectionLineType;
	}

	@Override
	public String getProductionMaterialType()
	{
		return productionMaterialType;
	}

	@Override
	public void setProductionMaterialType(final String productionMaterialType)
	{
		this.productionMaterialType = productionMaterialType;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return product;
	}

	@Override
	public void setM_Product(final I_M_Product product)
	{
		this.product = product;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(final String name)
	{
		this.name = name;
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
	public BigDecimal getQty()
	{
		return qty;
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		this.qty = qty;
	}

	@Override
	public BigDecimal getQtyProjected()
	{
		return qtyProjected;
	}

	@Override
	public void setQtyProjected(final BigDecimal qtyProjected)
	{
		this.qtyProjected = qtyProjected;
	}

	@Override
	public BigDecimal getPercentage()
	{
		return percentage;
	}

	@Override
	public void setPercentage(final BigDecimal percentage)
	{
		this.percentage = percentage;
	}

	@Override
	public boolean isNegateQtyInReport()
	{
		return negateQtyInReport;
	}

	@Override
	public void setNegateQtyInReport(final boolean negateQtyInReport)
	{
		this.negateQtyInReport = negateQtyInReport;
	}

	@Override
	public String getComponentType()
	{
		return componentType;
	}

	@Override
	public void setComponentType(final String componentType)
	{
		this.componentType = componentType;
	}

	@Override
	public String getVariantGroup()
	{
		return variantGroup;
	}

	@Override
	public void setVariantGroup(final String variantGroup)
	{
		this.variantGroup = variantGroup;
	}

	@Override
	public IHandlingUnitsInfo getHandlingUnitsInfo()
	{
		return handlingUnitsInfo;
	}

	@Override
	public void setHandlingUnitsInfo(final IHandlingUnitsInfo handlingUnitsInfo)
	{
		this.handlingUnitsInfo = handlingUnitsInfo;
	}

	@Override
	public void setHandlingUnitsInfoProjected(final IHandlingUnitsInfo handlingUnitsInfo)
	{
		handlingUnitsInfoProjected = handlingUnitsInfo;
	}

	@Override
	public IHandlingUnitsInfo getHandlingUnitsInfoProjected()
	{
		return handlingUnitsInfoProjected;
	}
}
