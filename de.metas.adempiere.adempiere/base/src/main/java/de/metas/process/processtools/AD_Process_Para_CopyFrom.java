package de.metas.process.processtools;

import org.adempiere.exceptions.FillMandatoryException;

import de.metas.process.IADProcessDAO;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class AD_Process_Para_CopyFrom extends JavaProcess
{
	private final transient IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private static final String PARAM_From_Process_ID = "From_Process_ID";
	@Param(parameterName = PARAM_From_Process_ID, mandatory = true)
	private int p_From_Process_ID;

	@Override
	protected String doIt() throws Exception
	{
		if (p_From_Process_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_From_Process_ID);
		}

		final int targetProcessId = getRecord_ID();
		adProcessDAO.copyProcessParameters(targetProcessId, p_From_Process_ID);

		return MSG_OK;
	}
}
