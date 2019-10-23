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
package org.adempiere.context;

import java.util.Properties;

import org.adempiere.util.lang.IAutoCloseable;

/**
 * 
 * @author Low Heng Sin
 *
 */
public interface ContextProvider
{
	/**
	 * Initialize ourself if necessary. If this method is called on an already initialized instance, it shall do nothing.
	 */
	void init();
	
	void reset();

	/**
	 * Gets current context.
	 * 
	 * Depends on implementation, it could always return the same context, or it can return a context based on current thread, user session etc.
	 * 
	 * @return current context.
	 */
	Properties getContext();

	/**
	 * Temporary replace current context with the given one.
	 * 
	 * This method will return an {@link IAutoCloseable} to be used in try-with-resources and which will restore the context back.
	 * 
	 * @param ctx
	 * @return auto-closeable used to put back the original context
	 */
	IAutoCloseable switchContext(Properties ctx);
}
