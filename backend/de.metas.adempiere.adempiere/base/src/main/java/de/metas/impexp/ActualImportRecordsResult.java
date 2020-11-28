package de.metas.impexp;

import java.util.OptionalInt;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.error.AdIssueId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Value
@Builder
public class ActualImportRecordsResult
{
	/** target table name, where the records were imported (e.g. C_BPartner) */
	@NonNull
	String targetTableName;
	/** import table name, FROM where the records are imported (e.g. I_BPartner) */
	@NonNull
	String importTableName;

	@NonNull
	OptionalInt countImportRecordsConsidered;
	@NonNull
	OptionalInt countInsertsIntoTargetTable;
	@NonNull
	OptionalInt countUpdatesIntoTargetTable;

	@NonNull
	@Singular
	ImmutableList<Error> errors;

	public String getCountInsertsIntoTargetTableString()
	{
		return counterToString(getCountInsertsIntoTargetTable());
	}

	public String getCountUpdatesIntoTargetTableString()
	{
		return counterToString(getCountUpdatesIntoTargetTable());
	}

	private static String counterToString(final OptionalInt counter)
	{
		return counter.isPresent() ? String.valueOf(counter.getAsInt()) : "N/A";
	}

	public boolean hasErrors()
	{
		return !getErrors().isEmpty();
	}

	public int getCountErrors()
	{
		return getErrors().size();
	}

	@Value
	@Builder
	public static class Error
	{
		@NonNull
		private String message;

		@NonNull
		AdIssueId adIssueId;
		@Nullable
		transient Throwable exception;

		private int affectedImportRecordsCount;
	}
}
