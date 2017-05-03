package de.metas.handlingunits.process.api;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.util.ISingletonService;

public interface IMHUProcessBL extends ISingletonService
{

	/**
	 * This method checks in the table M_HU_Process to see if the given process fits the given HU unit type
	 *
	 * @param adProcessId
	 * @param selectedHUUnitType
	 * @return true if the process (or report) was defined for the selected HU unit type, false otherwise
	 */
	boolean processFitsType(int adProcessId, String selectedHUUnitType);
}
