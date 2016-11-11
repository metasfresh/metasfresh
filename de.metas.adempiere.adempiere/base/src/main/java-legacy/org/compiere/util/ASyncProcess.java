/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import org.compiere.process.ProcessInfo;

import de.metas.logging.LogManager;

/**
 *  Async Process Interface.
 *  <p>
 *  The Process implements the methods.
 *  The Worker is started like
 *  <code> MyWorker.start()</code>
 *  <p>
 *  The worker's run method basically executes
 *  <pre>
 *      process.lockUI(pi);
 *      process.executeAsync(pi);
 *      process.unlockUI(pi);
 *  </pre>
 *  The isUILocked() method is used internally (not called by worker).
 *
 *  @author     Jorg Janke
 *  @version    $Id: ASyncProcess.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public interface ASyncProcess
{
	default void onProcessInitError(final ProcessInfo pi)
	{
		final Throwable cause = pi.getThrowable();
		if (cause != null)
		{
			LogManager.getLogger(ASyncProcess.class).warn("Process initialization failed: {}", pi, cause);
		}
		else
		{
			LogManager.getLogger(ASyncProcess.class).warn("Process initialization failed: {}", pi);
		}
	}
	/**
	 *  Lock User Interface.
	 *  Called from the Worker before processing
	 *  @param pi process info
	 */
	public void lockUI (ProcessInfo pi);

	/**
	 *  Unlock User Interface.
	 *  Called from the Worker when processing is done
	 *  @param pi result of execute ASync call
	 */
	public void unlockUI (ProcessInfo pi);
}   //  ASyncProcess
