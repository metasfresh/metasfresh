/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.adempiere.webui.session.ServerContext;

import net.sf.cglib.proxy.InvocationHandler;

/**
 * Intercaptor for Server context properties that delegate to the threadlocal instance
 * @author Low Heng Sin
 *
 */
public class ServerContextCallback implements InvocationHandler, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6708635918931322152L;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		final ServerContext context = ServerContext.getCurrentInstance();
		
		//optimize for the 2 most common access
		if (method.getName().equals("getProperty"))
		{
			Class<?>[] types = method.getParameterTypes();
			if (types != null && types.length == 1 && types[0] == String.class &&
				args != null && args.length == 1 && args[0] instanceof String)
			{
				return context.getProperty((String)args[0]);
			}
			else if (types != null && types.length == 2 && types[0] == String.class &&
					types[1] == String.class && args != null && args[0] instanceof String &&
					args[1] instanceof String)
			{
				return context.getProperty((String)args[0], (String)args[1]);
			}
		}
		
		final Method m = context.getClass().getMethod(method.getName(), method.getParameterTypes());
		return m.invoke(context, args);
	}

}
