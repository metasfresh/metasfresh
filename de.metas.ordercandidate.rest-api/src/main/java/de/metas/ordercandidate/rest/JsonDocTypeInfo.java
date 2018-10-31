package de.metas.ordercandidate.rest;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
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
@Data
@Builder
@ApiModel(description = "Specifies a document type that needs to be present in metasfresh in order to be looked up.")
public class JsonDocTypeInfo
{
	@NonNull
	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "This translates to <code>C_DocType.DocBaseType</code>.\n")
	private String docBaseType;

	@Nullable
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_DocType.DocSubType</code>.\n"
					+ "An empty value means that the matching <code>C_DocType</code> record's <code>DocSubType</code> needs to be <code>null</code>")
	private String docSubType;
}
