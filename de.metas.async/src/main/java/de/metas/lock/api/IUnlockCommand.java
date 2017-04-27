package de.metas.lock.api;

/*
 * #%L
 * de.metas.async
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


import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Future;

import org.adempiere.util.lang.ITableRecordReference;

/**
 * Lock release command
 * 
 * @author tsa
 *
 */
public interface IUnlockCommand
{
	/**
	 * @return how many records were unlocked
	 */
	int release();

	/**
	 * Release the locks after given transaction is commited.
	 * 
	 * @param trxName
	 * @return how many records were unlocked
	 */
	Future<Integer> releaseAfterTrxCommit(String trxName);

	IUnlockCommand setOwner(LockOwner owner);

	/** @return lock owner; never returns null */
	LockOwner getOwner();

	IUnlockCommand setRecordByModel(Object model);

	IUnlockCommand setRecordsByModels(Collection<?> models);

	IUnlockCommand setRecordByTableRecordId(int tableId, int recordId);
	
	IUnlockCommand setRecordByTableRecordId(String tableName, int recordId);

	Iterator<ITableRecordReference> getRecordsToUnlockIterator();

	IUnlockCommand setRecordsBySelection(Class<?> modelClass, int adPIstanceId);

	int getSelectionToUnlock_AD_Table_ID();

	int getSelectionToUnlock_AD_PInstance_ID();


}
