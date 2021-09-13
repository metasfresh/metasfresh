package de.metas.util;

/*
 * #%L
 * de.metas.util
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import lombok.NonNull;
import org.adempiere.util.lang.ITableRecordReference;

/**
 * Interface implementations can be passed to business logic to perform high-level logging. Use {@link Loggables} to get an instance.
 * <p>
 * NOTE: The signature of this interface {@link #addLog(String, Object...)} method is chosen so that all classes like de.metas.process.JavaProcess subclasses can implement it without further code changes.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface ILoggable
{
	/**
	 * Add a log message and return the loggable.
	 */
	ILoggable addLog(String msg, Object... msgParameters);

	/**
	 * Flush any buffered logs
	 */
	default void flush()
	{
	}

	/**
	 * Do nothing by default.
	 */
	default ILoggable addTableRecordReferenceLog(final ITableRecordReference recordRef, final String type, final String trxName)
	{
		// Adding a log message turned out to be *really* expensive for e.g. some workpackages with a very noticable performance impact 
		// this.addLog("addTableRecordReferenceLog called on {} with args: ad_table_id: {}, record_id: {}, type: {}. trxName: {}",
		// 			this.getClass().getSimpleName(), recordRef.getAD_Table_ID(), recordRef.getRecord_ID(), type, trxName);
		return this;
	}
}
