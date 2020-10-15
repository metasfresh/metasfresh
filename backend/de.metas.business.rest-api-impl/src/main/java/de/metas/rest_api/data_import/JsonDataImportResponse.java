package de.metas.rest_api.data_import;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;

import de.metas.common.rest_api.JsonErrorItem;
import de.metas.impexp.DataImportRunId;
import de.metas.impexp.config.DataImportConfigId;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api-impl
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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonDataImportResponse
{
	@Value
	@Builder
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	public static class JsonInsertIntoImportTable
	{
		String fromResource;
		String toImportTableName;
		String importFormatName;
		DataImportConfigId dataImportConfigId;

		String duration;
		DataImportRunId dataImportRunId;
		int countTotalRows;
		int countValidRows;

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		List<JsonErrorItem> errors;
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	JsonInsertIntoImportTable insertIntoImportTable;

	@Value
	@Builder
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	public static class JsonImportRecordsValidation
	{
		String importTableName;
		String duration;
		int countImportRecordsDeleted;
		int countErrors;
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	JsonImportRecordsValidation importRecordsValidation;

	@Value
	@Builder
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	public static class JsonActualImport
	{
		String importTableName;
		String targetTableName;
		int countImportRecordsConsidered;
		int countInsertsIntoTargetTable;
		int countUpdatesIntoTargetTable;

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		List<JsonErrorItem> errors;
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	JsonActualImport actualImport;

	@Value
	@Builder
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	public static class JsonActualImportAsync
	{
		int workpackageId;
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	JsonActualImportAsync actualImportAsync;
}
