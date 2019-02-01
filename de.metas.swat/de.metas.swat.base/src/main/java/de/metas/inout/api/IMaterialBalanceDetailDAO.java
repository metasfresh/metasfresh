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
import java.util.List;

import org.compiere.model.I_M_InOutLine;

import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_Material_Balance_Detail;
import de.metas.util.ISingletonService;

public interface IMaterialBalanceDetailDAO extends ISingletonService
{

	/**
	 * Creates a new M_Material_Balance_Detail entry for the given line and balanceConfig
	 *
	 * @param line
	 * @param balanceConfig
	 */
	void addLineToBalance(I_M_InOutLine line, MaterialBalanceConfig balanceConfig);

	/**
	 * In case the lines of the given inout are contained by any Material Movement Balance Detail entry, delete those entries
	 * 
	 * @param inout
	 */
	void removeInOutFromBalance(I_M_InOut inout);

	/**
	 * Retrieve all the Material Balance Detail entries that contain the given inout
	 * 
	 * @param inout
	 * @return
	 */
	List<I_M_Material_Balance_Detail> retrieveBalanceDetailsForInOut(I_M_InOut inout);

	/**
	 * @param config:  in case it's null, the details of all the configs are reset!
	 * @param resetDate
	 * @return the Material Balance Details older than resetDate, with the given config
	 */
	List<I_M_Material_Balance_Detail> retrieveDetailsForConfigAndDate(MaterialBalanceConfig config, Timestamp resetDate);

	/**
	 * Delete directly all the Material Balance Detail entries that contain the given inout
	 * 
	 * @param inout
	 */
	void deleteBalanceDetailsForInOut(I_M_InOut inout);

}
