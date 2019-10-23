/**
 * 
 */
package org.compiere.util;

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


/**
 * @author tsa
 *
 */
public interface TrxRunnable2 extends TrxRunnable
{
	/** Value to be returned by {@link #doCatch(Throwable)} */
	boolean ROLLBACK = true;
	/** Value to be returned by {@link #doCatch(Throwable)} */
	boolean DONT_ROLLBACK = false;
	
	@Override
	public void run(String localTrxName) throws Exception;

	/**
	 * Method called when {@link #run(String)} throws an exception. In this method you can handle this exception or throw another exception. If an exception is thrown or method returns true, the
	 * transaction will be rollback.
	 * 
	 * Please note, this method is called before transaction is rolled-back or savepoint is released
	 * 
	 * @param e exception
	 * @return true if transaction should be rollback
	 */
	public boolean doCatch(Throwable e) throws Throwable;

	/**
	 * Method called after {@link #run(String)} runs.
	 * 
	 * Please note, this method is called AFTER transaction is rolled-back or savepoint is released
	 */
	public void doFinally();

}
