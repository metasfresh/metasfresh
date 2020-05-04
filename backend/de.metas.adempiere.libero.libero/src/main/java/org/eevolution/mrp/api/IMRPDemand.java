package org.eevolution.mrp.api;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.util.Date;
import java.util.List;

import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.impl.MRPYield;

/**
 * MRP demand data type. It consists of a given MRP Demand Qty, Date when the demand is scheduled to be available, Yield and aggregated MRP records that compose that demand.
 * 
 * @author tsa
 *
 */
public interface IMRPDemand
{
	/**
	 * 
	 * @return date when which demand is needed; never null
	 */
	Date getDateStartSchedule();

	/**
	 * 
	 * @return demanded Quantity (without yield)
	 */
	BigDecimal getMRPDemandsQty();

	/**
	 * 
	 * @return Yield
	 */
	MRPYield getYield();

	/**
	 * 
	 * @return MRP records which compose our demand
	 */
	List<I_PP_MRP> getMRPDemandRecords();

	boolean hasDemandsRecords();

}
