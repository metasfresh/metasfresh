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
import org.eevolution.model.X_PP_Order_Report;

import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterial;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLine;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLineBuilder;
import de.metas.materialtracking.qualityBasedInvoicing.ProductionMaterialType;
import de.metas.materialtracking.qualityBasedInvoicing.QualityInspectionLineType;
import de.metas.util.Check;

/* package */class QualityInspectionLineBuilder implements IQualityInspectionLineBuilder
{
	private QualityInspectionLineType _qualityInspectionLineType;
	private IProductionMaterial _productionMaterial;
	private BigDecimal _qty;
	private boolean _qtySet = false;
	private boolean _negateQty = false;
	private I_C_UOM _uom;
	private boolean _uomSet = false;
	private BigDecimal _percentage;
	private BigDecimal _qtyProjected;
	private boolean _qtyProjectedSet = false;
	private String _name;
	private IHandlingUnitsInfo _handlingUnitsInfo;
	private boolean _handlingUnitsInfoSet = false;
	private IHandlingUnitsInfo _handlingUnitsInfoProjected;
	private boolean _handlingUnitsInfoProjectedSet = false;

	public QualityInspectionLineBuilder()
	{
		super();
	}

	@Override
	public final IQualityInspectionLine create()
	{
		final IQualityInspectionLine line = new QualityInspectionLine();

		//
		// Populate the line
		line.setQualityInspectionLineType(getQualityInspectionLineType());
		line.setProductionMaterialType(getProductionMaterialType());
		line.setM_Product(getM_Product());
		line.setName(getName());
		line.setQty(getQtyToSet());
		line.setNegateQtyInReport(isNegateQty());
		line.setC_UOM(getC_UOM());
		line.setPercentage(getPercentage());
		line.setQtyProjected(getQtyProjected());
		line.setHandlingUnitsInfo(getHandlingUnitsInfoToSet());
		line.setHandlingUnitsInfoProjected(getHandlingUnitsInfoProjectedToSet());

		//
		// Populate the report line from Production material
		final IProductionMaterial productionMaterial = getProductionMaterialOrNull();
		if (productionMaterial != null)
		{
			line.setComponentType(BOMComponentType.toCodeOrNull(productionMaterial.getComponentType()));
			line.setVariantGroup(productionMaterial.getVariantGroup());
		}
		else
		{
			line.setComponentType(null);
			line.setVariantGroup(null);
		}

		//
		// After line created
		afterLineCreated(line);

		//
		// Return created & saved report line
		return line;
	}

	/**
	 * Method called after {@link IQualityInspectionLine} was created.
	 *
	 * @param line
	 */
	protected void afterLineCreated(final IQualityInspectionLine line)
	{
		// nothing on this level
	}

	@Override
	public final IQualityInspectionLineBuilder setQualityInspectionLineType(final QualityInspectionLineType qualityInspectionLineType)
	{
		_qualityInspectionLineType = qualityInspectionLineType;
		return this;
	}

	protected final QualityInspectionLineType getQualityInspectionLineType()
	{
		Check.assumeNotNull(_qualityInspectionLineType, "_qualityInspectionLineType not null");
		return _qualityInspectionLineType;
	}

	@Override
	public final IQualityInspectionLineBuilder setProductionMaterial(final IProductionMaterial productionMaterial)
	{
		_productionMaterial = productionMaterial;
		return this;
	}

	/**
	 *
	 * @return production material or null
	 */
	protected final IProductionMaterial getProductionMaterialOrNull()
	{
		return _productionMaterial;
	}

	protected final IProductionMaterial getProductionMaterial()
	{
		Check.assumeNotNull(_productionMaterial, "_productionMaterial not null");
		return _productionMaterial;
	}

	/**
	 *
	 * @return one of {@link X_PP_Order_Report#PRODUCTIONMATERIALTYPE_AD_Reference_ID}
	 */
	private String getProductionMaterialType()
	{
		final IProductionMaterial productionMaterial = getProductionMaterialOrNull();
		if (productionMaterial != null)
		{
			return toPP_Order_Report_ProductionMaterialType(productionMaterial.getType());
		}
		else
		{
			return null; // N/A
		}
	}

	/**
	 * @param type
	 * @return X_PP_Order_Report.PRODUCTIONMATERIALTYPE_
	 */
	private static final String toPP_Order_Report_ProductionMaterialType(final ProductionMaterialType type)
	{
		if (type == null)
		{
			return null;
		}
		else if (type == ProductionMaterialType.RAW)
		{
			return X_PP_Order_Report.PRODUCTIONMATERIALTYPE_RawMaterial;
		}
		else if (type == ProductionMaterialType.PRODUCED)
		{
			return X_PP_Order_Report.PRODUCTIONMATERIALTYPE_ProducedMaterial;
		}
		else if (type == ProductionMaterialType.SCRAP)
		{
			return X_PP_Order_Report.PRODUCTIONMATERIALTYPE_Scrap;
		}
		else
		{
			throw new IllegalArgumentException("Unknown type: " + type);
		}
	}

	private I_M_Product getM_Product()
	{
		final IProductionMaterial productionMaterial = getProductionMaterialOrNull();
		if (productionMaterial != null)
		{
			return productionMaterial.getM_Product();
		}
		else
		{
			return null; // N/A
		}
	}

	@Override
	public final IQualityInspectionLineBuilder setQty(final BigDecimal qty)
	{
		_qty = qty;
		_qtySet = true;
		return this;
	}

	/**
	 * Gets Quantity to set in report.
	 *
	 * NOTE: returned value is not affected by {@link #isNegateQty()}.
	 *
	 * @return quantity
	 */
	protected final BigDecimal getQtyToSet()
	{
		BigDecimal qty;
		if (_qtySet)
		{
			qty = _qty;
		}
		else
		{
			final IProductionMaterial productionMaterial = getProductionMaterial();
			qty = productionMaterial.getQty();
		}

		return qty;
	}

	@Override
	public final IQualityInspectionLineBuilder setNegateQty(final boolean negateQty)
	{
		_negateQty = negateQty;
		return this;
	}

	protected final boolean isNegateQty()
	{
		return _negateQty;
	}

	@Override
	public final IQualityInspectionLineBuilder setQtyProjected(final BigDecimal qtyProjected)
	{
		_qtyProjected = qtyProjected;
		_qtyProjectedSet = true;
		return this;
	}

	protected boolean isQtyProjectedSet()
	{
		return _qtyProjectedSet;
	}

	protected BigDecimal getQtyProjected()
	{
		return _qtyProjected;
	}

	@Override
	public final IQualityInspectionLineBuilder setC_UOM(final I_C_UOM uom)
	{
		_uom = uom;
		_uomSet = true;
		return this;
	}

	protected final I_C_UOM getC_UOM()
	{
		if (_uomSet)
		{
			return _uom;
		}
		else
		{
			final IProductionMaterial productionMaterial = getProductionMaterial();
			return productionMaterial.getC_UOM();
		}
	}

	@Override
	public final IQualityInspectionLineBuilder setPercentage(final BigDecimal percentage)
	{
		_percentage = percentage;
		return this;
	}

	private BigDecimal getPercentage()
	{
		return _percentage;
	}

	@Override
	public final IQualityInspectionLineBuilder setName(final String name)
	{
		_name = name;
		return this;
	}

	protected final String getName()
	{
		return _name;
	}

	private IHandlingUnitsInfo getHandlingUnitsInfoToSet()
	{
		if (_handlingUnitsInfoSet)
		{
			return _handlingUnitsInfo;
		}

		return null;
	}

	@Override
	public IQualityInspectionLineBuilder setHandlingUnitsInfo(final IHandlingUnitsInfo handlingUnitsInfo)
	{
		_handlingUnitsInfo = handlingUnitsInfo;
		_handlingUnitsInfoSet = true;
		return this;
	}

	private IHandlingUnitsInfo getHandlingUnitsInfoProjectedToSet()
	{
		if (_handlingUnitsInfoProjectedSet)
		{
			return _handlingUnitsInfoProjected;
		}

		return null;
	}

	@Override
	public IQualityInspectionLineBuilder setHandlingUnitsInfoProjected(final IHandlingUnitsInfo handlingUnitsInfo)
	{
		_handlingUnitsInfoProjected = handlingUnitsInfo;
		_handlingUnitsInfoProjectedSet = true;
		return this;
	}

}
