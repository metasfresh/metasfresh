package de.metas.impexp;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.time.Duration;
import java.util.OptionalInt;

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

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Value
@Builder
public class ValidateImportRecordsResult
{
	@NonNull String importTableName;
	@NonNull @Default @With Duration duration = Duration.ZERO;
	int countImportRecordsDeleted;
	@NonNull OptionalInt countImportRecordsWithValidationErrors;

	public String getSummary()
	{
		final StringBuilder sb = new StringBuilder();
		if (countImportRecordsDeleted > 0)
		{
			if (sb.length() > 0) {sb.append("; ");}
			sb.append(countImportRecordsDeleted).append(" record(s) deleted");
		}
		if (countImportRecordsWithValidationErrors.isPresent() && countImportRecordsWithValidationErrors.getAsInt() > 0)
		{
			if (sb.length() > 0) {sb.append("; ");}
			sb.append(countImportRecordsWithValidationErrors.getAsInt()).append(" validation errors encountered");
		}

		return sb.toString();
	}

	public boolean hasErrors()
	{
		return countImportRecordsWithValidationErrors.orElse(-1) > 0;
	}

	public String getErrorMessage()
	{
		int errorsCount = countImportRecordsWithValidationErrors.orElse(-1);
		if (errorsCount <= 0)
		{
			throw new IllegalStateException("no errors expected");
		}

		return errorsCount + " row(s) have validation errors";
	}
}
