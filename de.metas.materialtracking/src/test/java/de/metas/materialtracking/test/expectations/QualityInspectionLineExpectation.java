package de.metas.materialtracking.test.expectations;

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
import org.eevolution.model.X_PP_Order_Report;
import org.junit.Assert;

import de.metas.materialtracking.PPOrderReportUtil;
import de.metas.materialtracking.model.I_PP_Order_Report;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLine;
import de.metas.materialtracking.qualityBasedInvoicing.QualityInspectionLineType;
import de.metas.util.Check;

public class QualityInspectionLineExpectation extends AbstractExpectation
{
	private final QualityInspectionLineType qualityInspectionLineType;
	private String productionMaterialType;
	private boolean productionMaterialTypeSet = false;
	private I_M_Product product;
	private boolean productSet = false;
	private I_C_UOM uom;
	private boolean uomSet = false;
	private BigDecimal qty;
	private BigDecimal qtyProjected;
	private BigDecimal percentage;

	private HandlingUnitsInfoExpectation<QualityInspectionLineExpectation> handlingUnitsInfoExpectation = null;
	private HandlingUnitsInfoExpectation<QualityInspectionLineExpectation> handlingUnitsInfoProjectedExpectation = null;

	/* package */QualityInspectionLineExpectation(final QualityInspectionLineType qualityInspectionLineType)
	{
		super();

		Check.assumeNotNull(qualityInspectionLineType, "qualityInspectionLineType not null");
		this.qualityInspectionLineType = qualityInspectionLineType;
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("QualityInspectionLineExpectation [");
		builder.append("qualityInspectionLineType=").append(qualityInspectionLineType);
		builder.append(", productionMaterialType=").append(productionMaterialType);
		builder.append(", productionMaterialTypeSet=").append(productionMaterialTypeSet);
		builder.append(", product=").append(product == null ? null : product.getValue());
		builder.append(", uom=").append(uom == null ? null : uom.getUOMSymbol());
		builder.append(", qty=").append(qty);
		builder.append(", qtyProjected=").append(qtyProjected);
		builder.append(", percentage=").append(percentage);
		builder.append("]");
		return builder.toString();
	}

	public QualityInspectionLineExpectation assertExpected(final I_PP_Order_Report line)
	{
		Assert.assertNotNull("line not null", line);

		final String prefix = "Invalid \"" + PPOrderReportUtil.toString(line) + "\" - ";

		if (productionMaterialTypeSet)
		{
			Assert.assertEquals(prefix + "ProductionMaterialType", this.productionMaterialType, line.getProductionMaterialType());
		}
		if (productSet)
		{
			assertModelEquals(prefix + "Product", this.product, line.getM_Product());
		}
		if (uomSet)
		{
			assertModelEquals(prefix + "UOM", this.uom, line.getC_UOM());
		}
		assertEquals(prefix + "Qty", this.qty, line.getQty());
		assertEquals(prefix + "QtyProjected", this.qtyProjected, line.getQtyProjected());
		assertEquals(prefix + "Percentage", this.percentage, line.getPercentage());

		return this;
	}

	public QualityInspectionLineExpectation assertExpected(final IQualityInspectionLine line)
	{
		Assert.assertNotNull("line not null", line);

		final String prefix = "Invalid \"" + line + "\" - ";

		Assert.assertEquals(prefix + "Type", this.qualityInspectionLineType, line.getQualityInspectionLineType());

		if (productionMaterialTypeSet)
		{
			Assert.assertEquals(prefix + "ProductionMaterialType", this.productionMaterialType, line.getProductionMaterialType());
		}
		if (productSet)
		{
			assertModelEquals(prefix + "Product", this.product, line.getM_Product());
		}
		if (uomSet)
		{
			assertModelEquals(prefix + "UOM", this.uom, line.getC_UOM());
		}

		// Qty check
		{
			BigDecimal qtyActual = line.getQty();
			if (line.isNegateQtyInReport())
			{
				qtyActual = qtyActual.negate();
			}
			assertEquals(prefix + "Qty", this.qty, qtyActual);
		}

		assertEquals(prefix + "QtyProjected", this.qtyProjected, line.getQtyProjected());
		assertEquals(prefix + "Percentage", this.percentage, line.getPercentage());

		if (handlingUnitsInfoExpectation != null)
		{
			handlingUnitsInfoExpectation.assertExpected(line.getHandlingUnitsInfo());
		}

		if (handlingUnitsInfoProjectedExpectation != null)
		{
			handlingUnitsInfoProjectedExpectation.assertExpected(line.getHandlingUnitsInfoProjected());
		}

		return this;
	}

	public QualityInspectionLineType getQualityInspectionLineType()
	{
		return qualityInspectionLineType;
	}

	public QualityInspectionLineExpectation qty(final BigDecimal qty)
	{
		this.qty = qty;
		return this;
	}

	public BigDecimal getQty()
	{
		return this.qty;
	}

	public QualityInspectionLineExpectation qtyProjected(final BigDecimal qtyProjected)
	{
		this.qtyProjected = qtyProjected;
		return this;
	}

	public BigDecimal getQtyProjected()
	{
		return this.qtyProjected;
	}

	public QualityInspectionLineExpectation percentage(final BigDecimal percentage)
	{
		this.percentage = percentage;
		return this;
	}

	public BigDecimal getPercentage()
	{
		return this.percentage;
	}

	public QualityInspectionLineExpectation product(final I_M_Product product)
	{
		this.product = product;
		this.productSet = true;
		return this;
	}

	public I_M_Product getProduct()
	{
		return this.product;
	}

	public QualityInspectionLineExpectation uom(final I_C_UOM uom)
	{
		this.uom = uom;
		this.uomSet = true;
		return this;
	}

	public I_C_UOM getUOM()
	{
		return this.uom;
	}

	/**
	 * 
	 * @param productionMaterialType see {@link X_PP_Order_Report#PRODUCTIONMATERIALTYPE_AD_Reference_ID}
	 * @return this
	 */
	public QualityInspectionLineExpectation productionMaterialType(final String productionMaterialType)
	{
		this.productionMaterialType = productionMaterialType;
		this.productionMaterialTypeSet = true;
		return this;
	}

	public String getProductionMaterialType()
	{
		return this.productionMaterialType;
	}

	public HandlingUnitsInfoExpectation<QualityInspectionLineExpectation> handlingUnitsInfoExpectation()
	{
		if (handlingUnitsInfoExpectation == null)
		{
			handlingUnitsInfoExpectation = new HandlingUnitsInfoExpectation<QualityInspectionLineExpectation>(this);
		}
		return handlingUnitsInfoExpectation;
	}

	public HandlingUnitsInfoExpectation<QualityInspectionLineExpectation> getHandlingUnitsInfoExpectation()
	{
		return handlingUnitsInfoExpectation;
	}

	public HandlingUnitsInfoExpectation<QualityInspectionLineExpectation> handlingUnitsInfoProjectedExpectation()
	{
		if (handlingUnitsInfoProjectedExpectation == null)
		{
			handlingUnitsInfoProjectedExpectation = new HandlingUnitsInfoExpectation<QualityInspectionLineExpectation>(this);
		}
		return handlingUnitsInfoProjectedExpectation;
	}

	public HandlingUnitsInfoExpectation<QualityInspectionLineExpectation> getHandlingUnitsInfoProjectedExpectation()
	{
		return handlingUnitsInfoProjectedExpectation;
	}

	public HandlingUnitsInfoExpectation<QualityInspectionLineExpectation> getHandlingUnitsInfoProjectedExpectationNotNull()
	{
		Check.assumeNotNull(handlingUnitsInfoProjectedExpectation, "handlingUnitsInfoProjectedExpectation not null");
		return handlingUnitsInfoProjectedExpectation;
	}
}
