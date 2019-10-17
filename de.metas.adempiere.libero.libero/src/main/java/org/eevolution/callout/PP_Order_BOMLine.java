package org.eevolution.callout;

import java.math.BigDecimal;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.uom.LegacyUOMConversionUtils;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Manufacturing order BOM Line callout
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial version developed by Victor Perez, Teo Sarca under ADempiere project
 */
@Callout(I_PP_Order_BOMLine.class)
public class PP_Order_BOMLine
{
	/** Calculates and sets QtyRequired from QtyEntered and C_UOM_ID */
	@CalloutMethod(columnNames = { I_PP_Order_BOMLine.COLUMNNAME_QtyEntered, I_PP_Order_BOMLine.COLUMNNAME_C_UOM_ID })
	private void updateQtyRequired(final I_PP_Order_BOMLine bomLine)
	{
		final BigDecimal qtyEntered = bomLine.getQtyEntered();
		final int productId = bomLine.getM_Product_ID();
		final int uomToId = bomLine.getC_UOM_ID();

		BigDecimal qtyRequiered;
		if (productId <= 0 || uomToId <= 0)
		{
			qtyRequiered = qtyEntered;
		}
		else
		{
			qtyRequiered = LegacyUOMConversionUtils.convertToProductUOM(Env.getCtx(), productId, uomToId, qtyEntered);
			if (qtyRequiered == null)
			{
				qtyRequiered = qtyEntered;
			}
		}

		bomLine.setQtyRequiered(qtyRequiered);
	}

}
