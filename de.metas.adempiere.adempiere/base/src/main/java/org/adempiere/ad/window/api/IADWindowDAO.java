package org.adempiere.ad.window.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Window;

public interface IADWindowDAO extends ISingletonService
{

	/**
	 * Loads the given window (cached) and returns its name. Uses {@link org.compiere.util.Env#getCtx()} to get the context.
	 * 
	 * @param adWindowId
	 * @return the name for the given <code>AD_Window_ID</code> or <code>null</code> if the given ID is less or equal zero.
	 */
	String retrieveWindowName(int adWindowId);

	/**
	 * Loads and returns the given window (cached).
	 * 
	 * @param ctx
	 * @param adWindowId whe window's <code>AD_Window_ID</code>.
	 * @return
	 */
	I_AD_Window retrieveWindow(Properties ctx, int adWindowId);

}
