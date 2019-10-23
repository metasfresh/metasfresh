/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                           *
 * http://www.adempiere.org                                            *
 *                                                                     *
 * Copyright (C) Trifon Trifonov.                                      *
 * Copyright (C) Contributors                                          *
 *                                                                     *
 * This program is free software; you can redistribute it and/or       *
 * modify it under the terms of the GNU General Public License         *
 * as published by the Free Software Foundation; either version 2      *
 * of the License, or (at your option) any later version.              *
 *                                                                     *
 * This program is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
 * GNU General Public License for more details.                        *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with this program; if not, write to the Free Software         *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
 * MA 02110-1301, USA.                                                 *
 *                                                                     *
 * Contributors:                                                       *
 * - Trifon Trifonov (trifonnt@users.sourceforge.net)                  *
 *                                                                     *
 * Sponsors:                                                           *
 * - E-evolution (http://www.e-evolution.com)                          *
 ***********************************************************************/
package org.adempiere.server.rpl;

import java.util.Properties;

import org.compiere.model.I_IMP_Processor;

/**
 * Interface for Import processor
 *
 * Implementors may start their own threads.
 *
 * @author Trifon Trifonov
 *
 */
public interface IImportProcessor
{

	/**
	 * @param ctx
	 * @param replicationProcessor
	 *            the instance that starts this <code>IImportProcessor</code>. This <code>IImportProcessor</code> is
	 *            responsible for indicating it's status by calling {@link IReplicationProcessor#setProcessRunning(boolean)}.
	 *
	 * @param trxName
	 *
	 * @throws Exception
	 */
	public void start(Properties ctx, IReplicationProcessor replicationProcessor, String trxName) throws Exception;

	/**
	 *
	 * @throws Exception
	 */
	public void stop() throws Exception;

	public void createInitialParameters(I_IMP_Processor processor);
}
