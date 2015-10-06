package de.metas.materialtracking.model;

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

public interface IPPOrderQualityFields
{
	//@formatter:off
	String COLUMNNAME_QM_QtyDeliveredPercOfRaw = "QM_QtyDeliveredPercOfRaw";
	BigDecimal getQM_QtyDeliveredPercOfRaw();
	void setQM_QtyDeliveredPercOfRaw(final BigDecimal QM_QtyDeliveredPercOfRaw);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_QM_QtyDeliveredAvg = "QM_QtyDeliveredAvg";
	BigDecimal getQM_QtyDeliveredAvg();
	void setQM_QtyDeliveredAvg(final BigDecimal QM_QtyDeliveredAvg);
	//@formatter:on
}
