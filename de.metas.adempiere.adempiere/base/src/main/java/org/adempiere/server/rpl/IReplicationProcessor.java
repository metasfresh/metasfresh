/**
 *
 */
package org.adempiere.server.rpl;

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


import org.compiere.model.I_IMP_Processor;

/**
 * Interface to be passed to implementors of {@link IImportProcessor}. Behind this interface there is a core server thread that awakes in fixed intervals and makes sure that the import processed
 * configured in {@link I_IMP_Processor} is run. Note that <code>IImportProcessor</code> implementors can start their own threads.
 *
 * @author tsa
 *
 */
public interface IReplicationProcessor
{

	public org.compiere.model.I_IMP_Processor getMImportProcessor();

	/**
	 * This method is supposed to be used by an implementor of {@link IImportProcessor} to indicate whether that implementor is still running.
	 *
	 * If an <code>IImportProcessor</code> sets this to <code>false</code>, then the this replication processor's server thread will create and start a new <code>IImportProcessor</code> instance.
	 * Therefore it is important to set this to false also in the case of exceptions.
	 *
	 * @param isProcessRunning
	 */
	public void setProcessRunning(boolean isProcessRunning);

	public boolean isProcessRunning();

	public String getServerInfo();
}
