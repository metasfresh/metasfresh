package de.metas.ui.web.devtools;

import lombok.Builder;
import lombok.ToString;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

/*
 * #%L
 * metasfresh-webui-api
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Builder
@ToString
public class JSONMigrationScriptsInfo
{
	private boolean enabled;

	private String migrationScriptDirectory;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "The file name of the SQL script to which the system currently records.")
	private String currentScript;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "The file names of recorded SQL scripts.")
	private List<String> scripts;
}
