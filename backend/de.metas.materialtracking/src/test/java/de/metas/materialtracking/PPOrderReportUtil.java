package de.metas.materialtracking;

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


import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.materialtracking.model.I_PP_Order_Report;

public final class PPOrderReportUtil
{
	private PPOrderReportUtil()
	{
		super();
	}

	public static String toString(final I_PP_Order_Report reportLine)
	{
		if (reportLine == null)
		{
			return "";
		}

		final StringBuilder sb = new StringBuilder();

		final I_M_Product product = reportLine.getM_Product();
		final I_C_UOM uom = reportLine.getC_UOM();

		sb.append("SeqNo=").append(reportLine.getSeqNo());
		sb.append(", Type=").append(reportLine.getProductionMaterialType());
		sb.append(", Product=").append(product == null ? "-" : product.getName());
		sb.append(", Name=").append(reportLine.getName());
		sb.append(", UOM=").append(uom == null ? "-" : uom.getUOMSymbol());
		sb.append(", Qty=").append(reportLine.getQty());
		sb.append(", QtyProjected=").append(reportLine.getQtyProjected());
		sb.append(", Perc=").append(reportLine.getPercentage());

		return sb.toString();
	}

}
