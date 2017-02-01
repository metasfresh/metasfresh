package de.metas.process;

import java.util.Properties;

import org.compiere.util.Env;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link IProcessDefaultParametersProvider}'s parameter.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IProcessDefaultParameter
{
	String getColumnName();

	//
	// Private methods
	//@formatter:off
	Properties getCtx();
	int getWindowNo();
	//@formatter:on

	default int getContextAsInt(final String name)
	{
		return Env.getContextAsInt(getCtx(), getWindowNo(), name);
	}

}
