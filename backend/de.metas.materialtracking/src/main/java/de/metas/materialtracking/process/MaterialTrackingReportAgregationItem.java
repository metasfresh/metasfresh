package de.metas.materialtracking.process;

import java.math.BigDecimal;

import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * A pojo used in creating Items for the CreateMaterialTrackingReportLineFromMaterialTrackingRefAggregator.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MaterialTrackingReportAgregationItem
{
	/**
	 *
	 * @param ppOrder can be <code>null</code>. If <code>null</code>, then the item is counted as an <code>M_InOut</code> material receipt.<br>
	 *            If not <code>null</code> then it is counted as a <code>PP_Order</code> material issue.
	 * @param inOutLine may not be <code>null</code>
	 * @param materialTracking
	 * @param qty the qty in the product's "internal" UOM
	 */
	public MaterialTrackingReportAgregationItem(
			final I_PP_Order ppOrder,
			final I_M_InOutLine inOutLine,
			final I_M_Material_Tracking materialTracking,
			final BigDecimal qty)
	{
		this.ppOrder = ppOrder;
		this.inOutLine = inOutLine;
		this.materialTracking = materialTracking;
		this.qty = qty;
	}

	/**
	 * Will be set only if we deal with an issue item
	 */
	private final I_PP_Order ppOrder;

	/**
	 * Will always be set. It is either taken from the material tracking ref ( receipt side) or the issued inout lines of the pp_Order (issued side)
	 */
	private final I_M_InOutLine inOutLine;

	/**
	 * Useful for tracking, troubleshooting
	 */
	private final I_M_Material_Tracking materialTracking;

	/**
	 * The significant qty for the item. It can either be qtyIssued if the pp_Order is set, or qtyReceived if it isn't
	 */
	BigDecimal qty;

	public BigDecimal getQty()
	{
		return qty;
	}

	public I_PP_Order getPPOrder()
	{
		return ppOrder;
	}

	public I_M_Material_Tracking getMaterialTracking()
	{
		return materialTracking;
	}

	public I_M_InOutLine getInOutLine()
	{
		return inOutLine;
	}
}
