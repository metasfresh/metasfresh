package de.metas.async.api;

/*
 * #%L
 * de.metas.async
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
import org.adempiere.util.api.IParams;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Param;

public interface IWorkpackageParamDAO extends ISingletonService
{
	/**
	 * Retrieve workpackage parameters.
	 * 
	 * @param workpackage
	 * @return workpackage parameters
	 */
	IParams retrieveWorkpackageParams(I_C_Queue_WorkPackage workpackage);

	/**
	 * Sets given <code>parameterValue</code> to {@link I_C_Queue_WorkPackage_Param}.
	 * 
	 * This method will figure out exactly where the value will be set, based on <code>parameterValue</code>'s class.
	 * 
	 * @param workpackageParam
	 * @param parameterValue
	 */
	void setParameterValue(I_C_Queue_WorkPackage_Param workpackageParam, Object parameterValue);
}
