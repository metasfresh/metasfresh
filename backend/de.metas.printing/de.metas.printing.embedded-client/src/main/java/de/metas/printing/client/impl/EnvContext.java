package de.metas.printing.client.impl;

/*
 * #%L
 * de.metas.printing.embedded-client
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

import de.metas.printing.client.Context;
import de.metas.printing.client.IContext;

/**
 * An {@link IContext} implementation which is forwarding the property requests to global context.
 * 
 * NOTE: to be used only in stand alone (swing) client.
 * 
 * @author tsa
 * 
 */
public class EnvContext implements IContext
{

	@Override
	public String getProperty(String name)
	{
		if (Context.CTX_SessionId.equals(name))
		{
			final Properties ctx = Env.getCtx();
			return Env.getContext(ctx, Env.CTXNAME_AD_Session_ID);
		}

		return null;
	}

}
