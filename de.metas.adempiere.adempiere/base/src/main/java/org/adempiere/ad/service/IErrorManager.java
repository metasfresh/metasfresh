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

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Issue;

/**
 * System Error Manager. This subsystem is responsible with error logging, AD_Issue creation etc.
 * 
 * @author tsa
 * 
 */
public interface IErrorManager extends ISingletonService
{

	/**
	 * Creates, saves and returns and {@link I_AD_Issue} based on given {@link Throwable} object.
	 * 
	 * @param name issue name; if null then default issue name will be used (i.e. Error)
	 * @param t throwable
	 * @return created issue
	 */
	I_AD_Issue createIssue(String name, Throwable t);

	default I_AD_Issue createIssue(Throwable t)
	{
		final String name = null;
		return createIssue(name, t);
	}

}
