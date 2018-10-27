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
import java.math.RoundingMode;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;

import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.qualityBasedInvoicing.IInvoicingItem;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingBL;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.util.Check;
import de.metas.util.Services;

public class QualityBasedInvoicingBL implements IQualityBasedInvoicingBL
{
	@Override
	public boolean isQualityInspection(final I_PP_Order ppOrder)
	{
		final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);
		return materialTrackingPPOrderBL.isQualityInspection(ppOrder);
	}

	@Override
	public void assumeQualityInspectionOrder(final I_PP_Order ppOrder)
	{
		Check.assumeNotNull(ppOrder, "ppOrder not null");
		Check.assume(isQualityInspection(ppOrder), "Order shall be a quality inspection order: {}", ppOrder);
	}

	@Override
	public IInvoicingItem createPlainInvoicingItem(final I_M_Product product, final BigDecimal qty, final I_C_UOM uom)
	{
		return new InvoicingItem(product, qty, uom);
	}

	@Override
	public BigDecimal calculateProjectedQty(final BigDecimal fullRawQty,
			final BigDecimal sampleRawQty,
			final BigDecimal sampleProducedQty,
			final I_C_UOM sampleProducedQtyUOM)
	{
		final BigDecimal factor;
		if (sampleRawQty.signum() == 0)
		{
			factor = BigDecimal.ZERO;
		}
		else
		{
			factor = fullRawQty.divide(sampleRawQty, 12, RoundingMode.HALF_UP);
		}

		final BigDecimal projectedProducedQty = sampleProducedQty
				.multiply(factor)
				.setScale(sampleProducedQtyUOM.getStdPrecision(), RoundingMode.HALF_UP);
		return projectedProducedQty;
	}

	@Override
	public boolean isLastInspection(final IQualityInspectionOrder qiOrder)
	{
		return qiOrder.getInspectionNumber() == qiOrder.getQualityBasedConfig().getOverallNumberOfInvoicings();
	}

}
