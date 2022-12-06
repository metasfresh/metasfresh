/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.ad.migration.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JSONMigrationScriptsInfo
{
	boolean enabled;

	String migrationScriptDirectory;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "The file name of the SQL script to which the system currently records.")
	String currentScript;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "The file names of recorded SQL scripts.")
	List<String> scripts;
}
