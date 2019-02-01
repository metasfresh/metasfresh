package de.metas.inout.api;

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


import java.sql.Timestamp;

import de.metas.util.ISingletonService;

public interface IMaterialBalanceDetailBL extends ISingletonService
{
	/**
	 * Find if there are any M_Material_Balance_Config entries that fit the lines of the inout.
	 * Add the lines to the found balance entries.
	 * In case a fitting balance entry was not found, it means the inout line is not important in balancing
	 * 
	 * @param inout
	 */
	void addInOutToBalance(de.metas.inout.model.I_M_InOut inout);

	/**
	 * Reset all the Material Balance Details of the given config that are older than the given date
	 *
	 * @param config
	 * @param resetDate
	 */
	void resetMaterialDetailsForConfigAndDate(MaterialBalanceConfig config, Timestamp resetDate);

	/**
	 * Reset all the Material Balance Details that are older than the given date
	 * 
	 * @param resetDate
	 */
	void resetAllMaterialDetailsForDate(Timestamp resetDate);

}
