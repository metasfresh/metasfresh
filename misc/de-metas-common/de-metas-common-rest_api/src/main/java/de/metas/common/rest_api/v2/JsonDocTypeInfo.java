/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.rest_api.v2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@ApiModel(description = "Specifies a document type that needs to be present in metasfresh in order to be looked up.")
public class JsonDocTypeInfo
{
	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "This translates to <code>C_DocType.DocBaseType</code>.\n")
	private String docBaseType;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_DocType.DocSubType</code>.\n"
					+ "An empty value means that the matching <code>C_DocType</code> record's <code>DocSubType</code> needs to be <code>null</code>")
	private String docSubType;

	@JsonCreator
	@Builder
	private JsonDocTypeInfo(
			@JsonProperty("docBaseType") @NonNull final String docBaseType,
			@JsonProperty("docSubType") @Nullable final String docSubType)
	{
		this.docBaseType = docBaseType;
		this.docSubType = docSubType;
	}
}
