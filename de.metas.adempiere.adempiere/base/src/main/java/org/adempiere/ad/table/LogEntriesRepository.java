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

/**
 * Please update the technical note of the {@code C_Location} table in application dictionary if you move or rename this class.
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
		 * If {@code true} and the respective {@link TableRecordReference}'s change logs contain changes to FK columns that reference {@code C_Location},
		 * then those {@code C_Location} records are loaded (all of them in one DB-access).
		 *
		 * Then, instead of adding {@link RecordChangeLogEntry}s for the different {@code C_Location_ID} values,
		 * the actual {@code C_Location} records are compared with each other and {@link RecordChangeLogEntry} are derived from their differences.
		 *
		 * Then those @{@code C_Location}-derived {@link RecordChangeLogEntry}s are added to the respective {@link TableRecordReference}'s change log.
		 * <p/>
		 * If you don't want this to happen e.g. for {@code C_BPartner_Location}, you can set {@code IsAllowLogging='N'} for the column {@code C_BPartner_Location.C_Location_ID}.<br/>
		 * If you don't want this to happen in general, you can set {@code IsChangeLog='N'} for the entire {@code C_Location} table.
		 * <p/>
		 * Note that {@link RecordChangeLogEntry}s are <b>not</b> derived for these {@code C_Location}-columns:
		 * <li>{@code Created}, but note that the {@code C_Location} record's {@code Created} value is used in the {@link RecordChangeLogEntry}
		 * <li>{@code CreatedBy} same as {@code Created}
 		 * <li>{@code Updated} and {@code UpdatedBy}: since {@code C_Location} is immutable, they don't matter anyways
 		 * <li>{@code IsActive}, because it's generally not accessible via UI and we don't want it to be confused with e.g. {@code C_BPartner_Location.IsActive}.
		 * <li>{@code C_Location_ID}
  		 * <li>Every {@code C_Location}-column with {@code IsAllowLogging='N'}
  		 * <p/>
  		 * Also note: if you have e.g. {@code IsAllowLogging='Y'} for {@code C_BPartner_Location.C_Location_ID}, and {@link followLocationIdChanges=false},
  		 * then the change log contains the different location ID,
  		 * i.e. {@link KeyNamePair}s with the {@code C_Location_ID} and the locations rendered address string.
		 */
		@Default
		boolean followLocationIdChanges = false;
	}
}
