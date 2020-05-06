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
import org.eevolution.model.I_PP_Order;

import de.metas.util.ISingletonService;

public interface IQualityBasedInvoicingBL extends ISingletonService
{
	/**
	 * Just to make sure that here we start counting with the number one, not zero.
	 */
	int INSPECTIONNUMBER_First = 1;

	/**
	 *
	 * @param ppOrder
	 * @return <code>true</code> if the given <code>ppOrder</code> is a qulity inspection, <code>false</code> if not
	 */
	boolean isQualityInspection(I_PP_Order ppOrder);

	/**
	 * Assume given manufacturing order is a quality inspection order
	 *
	 * @param ppOrder
	 */
	void assumeQualityInspectionOrder(I_PP_Order ppOrder);

	/**
	 *
	 * @param product an instance with the given parameters and type = {@link ProductionMaterialType#PLAIN}.
	 * @param qty
	 * @param uom
	 * @return
	 */
	IInvoicingItem createPlainInvoicingItem(I_M_Product product, BigDecimal qty, I_C_UOM uom);

	/**
	 *
	 * NOTE: fullRawQty and sampleRawQty shall be in the same UOM
	 *
	 * @param fullRawQty
	 * @param sampleRawQty
	 * @param sampleProducedQty
	 * @param sampleProducedQtyUOM
	 * @return sampleProducedQty * (fullRawQty / sampleRawQty), rounded to given UOM precision
	 */
	BigDecimal calculateProjectedQty(BigDecimal fullRawQty, BigDecimal sampleRawQty, BigDecimal sampleProducedQty, I_C_UOM sampleProducedQtyUOM);

	/**
	 * 
	 * @param qiOrder
	 * @return <code>true</code> if the given <code>qiOrder</code> is the last one according to its own config and its own number.
	 */
	boolean isLastInspection(IQualityInspectionOrder qiOrder);
}
