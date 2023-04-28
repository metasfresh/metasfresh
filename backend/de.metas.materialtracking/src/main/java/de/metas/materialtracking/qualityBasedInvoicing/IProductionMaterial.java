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

import de.metas.materialtracking.IHandlingUnitsInfo;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.BOMComponentType;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public interface IProductionMaterial
{
	I_M_Product getM_Product();

	BigDecimal getQty();

	/**
	 * {@link #getQty()} and converts it to given <code>uomTo</code>.
	 */
	BigDecimal getQty(I_C_UOM uomTo);

	I_C_UOM getC_UOM();

	ProductionMaterialType getType();

	void setQM_QtyDeliveredPercOfRaw(BigDecimal qtyDeliveredPercOfRaw);

	BigDecimal getQM_QtyDeliveredPercOfRaw();

	void setQM_QtyDeliveredAvg(BigDecimal qtyDeliveredAvg);

	BigDecimal getQM_QtyDeliveredAvg();

	/**
	 * {@link #getQM_QtyDeliveredAvg()} and converts it to given <code>uomTo</code>.
	 */
	BigDecimal getQM_QtyDeliveredAvg(I_C_UOM uomTo);

	String getVariantGroup();

	/**
	 * In case this is a variant for a raw material (i.e. {@link #getComponentType()} is Variant and {@link #getVariantGroup()} is not null), this method will return the main product which is
	 * substituted by this line's {@link #getM_Product()}.
	 *
	 * @return substituted product or null
	 */
	I_M_Product getMainComponentProduct();

	@Nullable
	BOMComponentType getComponentType();

	boolean isByProduct();

	IHandlingUnitsInfo getHandlingUnitsInfo();
}
