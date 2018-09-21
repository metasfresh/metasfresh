package de.metas.adempiere.service;

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


import java.util.List;
import java.util.Properties;

import de.metas.util.ISingletonService;

public interface IParametersDAO extends ISingletonService
{
	public interface IParameterPO
	{
		String getName();

		void setName(String name);

		String getParamValue();

		void setParamValue(final String paramValue);

		String getDisplayName();

		void setDisplayName(String displayName);

		String getDescription();

		void setDescription(String description);

		int getAD_Reference_ID();

		void setAD_Reference_ID(int adReferenceId);

		int getSeqNo();

		void setSeqNo(int seqNo);

	}

	IParameterPO newParameterPO(final Object parent, String parameterTable);

	List<IParameterPO> retrieveParamPOs(Object parent, String parameterTable);

	List<IParameterPO> retrieveParamPOs(Properties ctx, String parentTable, int parentId, String parameterTable, String trxName);
}
