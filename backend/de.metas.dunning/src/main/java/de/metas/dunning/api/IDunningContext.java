package de.metas.dunning.api;

/*
 * #%L
 * de.metas.dunning
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


import java.util.Date;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxRunConfig;

import de.metas.dunning.interfaces.I_C_DunningLevel;

/**
 * Dunning running context
 * 
 * @author tsa
 * 
 */
public interface IDunningContext
{
	Properties getCtx();

	String getTrxName();

	ITrxRunConfig getTrxRunnerConfig();
	
	I_C_DunningLevel getC_DunningLevel();

	IDunningConfig getDunningConfig();

	Date getDunningDate();

	void setProperty(String propertyName, Object value);

	<T> T getProperty(String propertyName);

	<T> T getProperty(String propertyName, T defaultValue);

	boolean isProperty(String propertyName);

	boolean isProperty(String propertyName, boolean defaultValue);
}
