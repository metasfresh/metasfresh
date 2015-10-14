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

import de.metas.materialtracking.IHandlingUnitsInfo;

/**
 * Builder class used to create {@link IQualityInspectionLine}s.
 *
 * @author tsa
 *
 */
public interface IQualityInspectionLineBuilder
{
	IQualityInspectionLine create();

	IQualityInspectionLineBuilder setQualityInspectionLineType(QualityInspectionLineType qualityInspectionLineType);

	IQualityInspectionLineBuilder setProductionMaterial(final IProductionMaterial productionMaterial);

	IQualityInspectionLineBuilder setQty(final BigDecimal qty);

	/**
	 *
	 * @param negateQty true if we shall negate the Qty from produced report line. Only the "Qty" will be negated and not the other quantities.
	 * @return
	 */
	IQualityInspectionLineBuilder setNegateQty(boolean negateQty);

	IQualityInspectionLineBuilder setQtyProjected(final BigDecimal qtyProjected);

	IQualityInspectionLineBuilder setC_UOM(final I_C_UOM uom);

	IQualityInspectionLineBuilder setPercentage(final BigDecimal percentage);

	IQualityInspectionLineBuilder setName(String name);

	IQualityInspectionLineBuilder setHandlingUnitsInfo(IHandlingUnitsInfo handlingUnitsInfo);

	IQualityInspectionLineBuilder setHandlingUnitsInfoProjected(IHandlingUnitsInfo handlingUnitsInfo);
}
