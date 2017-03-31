package de.metas.dlm.partitioner;

import org.adempiere.util.lang.ITableRecordReference;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Implementors can be registered using {@link IIterateResult#registerHandler(IIterateResultListener)} and will then be notified about every new record that is added to the result.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IIterateResultHandler
{
	public enum AddResult
	{
		ADDED_CONTINUE, NOT_ADDED_CONTINUE, STOP;
	}

	/**
	 * Notified if a record is added to the result. Tells the caller result if there is anything special to do about the record.
	 *
	 * @param tableRecordReference
	 * @param preliminaryResult the default result to return if the handler doesn't really have anything to say.
	 * @return
	 */
	AddResult onRecordAdded(ITableRecordReference tableRecordReference, AddResult preliminaryResult);
}
