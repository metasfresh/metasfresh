package org.adempiere.util;

/*
 * #%L
 * ADempiere ERP - Base
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

import org.compiere.util.Env;
import org.compiere.util.Evaluatee;

/**
 * Wraps a given {@link Properties} context to {@link Evaluatee}
 * 
 * @author tsa
 * 
 */
public class EvaluateeCtx implements Evaluatee
{
	private final Properties ctx;
	private final int windowNo;
	private final boolean onlyWindow;

	public EvaluateeCtx(final Properties ctx, final int windowNo, final boolean onlyWindow)
	{
		Check.assumeNotNull(ctx, "ctx not null");
		this.ctx = ctx;
		this.windowNo = windowNo;
		this.onlyWindow = onlyWindow;
	}

	@Override
	public String get_ValueAsString(String variableName)
	{
		return Env.getContext(ctx, windowNo, variableName, onlyWindow);
	}

	public int getWindowNo()
	{
		return windowNo;
	}
}
