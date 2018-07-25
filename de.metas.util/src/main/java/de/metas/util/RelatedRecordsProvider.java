package de.metas.util;

import java.util.List;

import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * Useful as a building block in scenarios where stuff needs to be done with records and their respective "downstream" records (e.g. orders and invoices).
 * See https://github.com/metasfresh/metasfresh/issues/4403 for a use case.
 * Note that without this approach, issue #4403 would probably have been implemented in the form of one large piece of spaghetti code.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface RelatedRecordsProvider
{
	/** Specifies for which sort of input records a given implementation's {@link #provideRelatedRecords(List)} shall be invoked. */
	SourceRecordsKey getSourceRecordsKey();

	/** For the given request's records, (e.g. I_C_Order records), return the referencing records that this handler can find (e.g. I_C_Invoices) */
	IPair<SourceRecordsKey, List<ITableRecordReference>> provideRelatedRecords(@NonNull final List<ITableRecordReference> records);

	/**
	 * Used to map a {@link RelatedRecordsProvider} implementation to its respective input.
	 * Can in future be extended to e.g. also contain SOTrx/POTrx or other matching properties.
	 */
	@Value
	public class SourceRecordsKey
	{
		public static SourceRecordsKey of(@NonNull final String tableName)
		{
			return new SourceRecordsKey(tableName);
		}

		String tableName;

		private SourceRecordsKey(@NonNull final String tableName)
		{
			this.tableName = tableName;
		}
	}
}
