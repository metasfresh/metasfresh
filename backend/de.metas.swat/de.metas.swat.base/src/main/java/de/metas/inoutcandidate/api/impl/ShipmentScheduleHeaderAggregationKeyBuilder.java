package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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


import de.metas.util.agg.key.AbstractHeaderAggregationKeyBuilder;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * Shipment Schedule header aggregation key builder.
 * <p>
 * Note that most (but not necesarily all!!) of the magic is happening in {@link ShipmentScheduleHeaderAggregationKeyBuilder}.
 */
public class ShipmentScheduleHeaderAggregationKeyBuilder extends AbstractHeaderAggregationKeyBuilder<I_M_ShipmentSchedule>
{
	public static final String REGISTRATION_KEY = I_M_ShipmentSchedule.Table_Name;

	public ShipmentScheduleHeaderAggregationKeyBuilder()
	{
		super(REGISTRATION_KEY);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
