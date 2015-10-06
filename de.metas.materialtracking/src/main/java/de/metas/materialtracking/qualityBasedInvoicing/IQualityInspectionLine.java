package de.metas.materialtracking.qualityBasedInvoicing;

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

public interface IQualityInspectionLine
{
	void setPercentage(BigDecimal percentage);

	BigDecimal getPercentage();

	void setQtyProjected(BigDecimal qtyProjected);

	BigDecimal getQtyProjected();

	void setQty(BigDecimal qty);

	BigDecimal getQty();

	void setC_UOM(I_C_UOM uom);

	I_C_UOM getC_UOM();

	void setName(String name);

	String getName();

	void setM_Product(I_M_Product product);

	I_M_Product getM_Product();

	void setProductionMaterialType(String productionMaterialType);

	String getProductionMaterialType();

	void setQualityInspectionLineType(QualityInspectionLineType qualityInspectionLineType);

	QualityInspectionLineType getQualityInspectionLineType();

	boolean isNegateQtyInReport();

	void setNegateQtyInReport(boolean negateQtyInReport);

	void setVariantGroup(String variantGroup);

	String getVariantGroup();

	void setComponentType(String componentType);

	String getComponentType();

	void setHandlingUnitsInfo(final IHandlingUnitsInfo handlingUnitsInfo);

	IHandlingUnitsInfo getHandlingUnitsInfo();

	void setHandlingUnitsInfoProjected(final IHandlingUnitsInfo handlingUnitsInfo);

	IHandlingUnitsInfo getHandlingUnitsInfoProjected();

}
