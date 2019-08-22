package org.adempiere.ad.table;

import java.util.List;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.KeyNamePair;

import com.google.common.collect.ImmutableListMultimap;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public interface LogEntriesRepository
{
	ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> getLogEntriesForRecordReferences(LogEntriesQuery logEntriesQuery);

	@Value
	@Builder
	public static class LogEntriesQuery
	{
		public static LogEntriesQuery of(@NonNull final TableRecordReference tableRecordReference)
		{
			return LogEntriesQuery.builder()
					.tableRecordReference(tableRecordReference)
					.followLocationIdChanges(false)
					.build();
		}

		@Singular
		List<TableRecordReference> tableRecordReferences;

		/**
		 * If {@code true} and the respective record's change logs contain changes to {@code C_Location} records,
		 * then those records are loaded (all of them in one DB-access).
		 * Then, instead of adding {@link RecordChangeLogEntry}s for the different {@code C_Location_ID} values,
		 * the {@code C_Location} records are compared with each other and {@link RecordChangeLogEntry} are derived from their differences.
		 * Then those @{@code C_Location}-derived {@link RecordChangeLogEntry} added to the respective record's change log.
		 *
		 * If you don't want this to happen e.g. for {@code C_BPartner_Location}, you can set {@code IsAllowLogging='N'} for {@code C_BPartner_Location.C_Location_ID}.
		 *
		 * Also note that {@link RecordChangeLogEntry} are <b>not</b> derived for the {@code C_Location}-columns:
		 * <li>{@code Created}, but note that the {@code C_Location} record's {@code Created} value is used in the {@link RecordChangeLogEntry}
		 * <li>{@code CreatedBy} same are {@code Created}
 		 * <li>{@code Updated} and {@code UpdatedBy}: since {@code C_Location} is immutable, they don't matter anyways
		 * <li>{@code C_Location_ID}
  		 * <li>Every {@code C_Location}-column with {@code IsAllowLogging='N'}
  		 *
  		 * Note: if you have e.g. {@code IsAllowLogging='N'} for {@code C_BPartner_Location.C_Location_ID}, and {@link followLocationIdChanges=false}, then the changelog contains the different locations, i.e.
  		 * {@link KeyNamePair}s with the {@code C_Location_ID} and the locations rendered address string..
		 */
		@Default
		boolean followLocationIdChanges = false;
	}
}
