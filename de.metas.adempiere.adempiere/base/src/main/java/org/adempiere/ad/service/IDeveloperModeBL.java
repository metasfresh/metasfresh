package org.adempiere.ad.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import de.metas.util.ISingletonService;

/**
 * Developer Model BL
 * 
 * @author tsa
 * @see http://dewiki908/mediawiki/index.php/02664:_Introduce_ADempiere_Developer_Mode_%282012040510000121%29
 */
public interface IDeveloperModeBL extends ISingletonService
{
	public interface ContextRunnable
	{
		public void run(Properties sysCtx);
	}

	/**
	 * Checks if developer mode is enabled.
	 * 
	 * This method NEVER fails. In case there is an internal failure, it will return false and and error will be logged.
	 * 
	 * @return true if developer mode is enabled
	 */
	boolean isEnabled();

	/**
	 * Execute {@link ContextRunnable} in SysAdm context
	 * 
	 * @param runnable
	 */
	void executeAsSystem(ContextRunnable runnable);
}
