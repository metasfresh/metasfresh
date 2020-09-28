package de.metas.impexp;

import java.time.Duration;
import java.util.OptionalInt;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

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
public class ValidateImportRecordsResult
{
	@NonNull
	String importTableName;

	@NonNull
	@Default
	@With
	Duration duration = Duration.ZERO;

	int countImportRecordsDeleted;

	@NonNull
	OptionalInt countImportRecordsWithValidationErrors;

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

		return "" + errorsCount + " row(s) have validation errors";
	}
}
