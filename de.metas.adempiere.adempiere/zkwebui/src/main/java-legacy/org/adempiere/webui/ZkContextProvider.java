/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2007 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *
 * Copyright (C) 2007 Low Heng Sin hengsin@avantz.com
 * _____________________________________________
 *****************************************************************************/
package org.adempiere.webui;

import java.util.Properties;

import net.sf.cglib.proxy.Enhancer;

import org.adempiere.context.ContextProvider;
import org.adempiere.context.ThreadLocalContextProvider;
import org.adempiere.util.lang.IAutoCloseable;

/**
 * This context provider effectively provides a threadlocal context.
 * 
 * @author Low Heng Sin
 * @see {@link ServerContextCallback}.
 * @deprecated This implementation is no longer used and it will be dropped. See {@link ThreadLocalContextProvider}.
 */
@Deprecated
public class ZkContextProvider implements ContextProvider
{
	private final static ServerContextCallback callback = new ServerContextCallback();
	private final static Properties context = (Properties)Enhancer.create(Properties.class, callback);

	/**
	 * Get server context proxy
	 */
	@Override
	public Properties getContext()
	{
		return context;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void init()
	{
		// nothing to do
	}

	@Override
	public IAutoCloseable switchContext(Properties ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void reset()
	{
		// NOTE: this is the old logic. We need to re-evaluate it when we activate ZK back.
		getContext().clear();
	}
}
