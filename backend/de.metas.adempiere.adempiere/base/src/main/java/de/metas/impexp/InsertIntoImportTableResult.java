package de.metas.impexp;

import java.net.URI;
import java.time.Duration;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.impexp.config.DataImportConfigId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.With;

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

@Value
@Builder
public final class InsertIntoImportTableResult
{
	//
	// Configuration
	@Nullable
	@With
	URI fromResource;
	String toImportTableName;
	@NonNull
	String importFormatName;
	@Nullable
	DataImportConfigId dataImportConfigId;

	//
	// Result
	@NonNull
	Duration duration;
	@NonNull
	DataImportRunId dataImportRunId;
	int countTotalRows;
	int countValidRows;
	@NonNull
	@Singular
	ImmutableList<Error> errors;

	public boolean hasErrors()
	{
		return !getErrors().isEmpty();
	}

	public int getCountErrors()
	{
		return getErrors().size();
	}

	@Builder
	@Value
	public static class Error
	{
		String message;

		int lineNo;

		@NonNull
		String lineContent;
	}
}
