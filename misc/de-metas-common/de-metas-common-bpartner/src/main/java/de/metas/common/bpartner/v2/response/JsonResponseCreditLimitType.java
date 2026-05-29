/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class JsonResponseCreditLimitType
{
	public static final String METASFRESH_ID = "metasfreshId";
	public static final String NAME = "name";
	public static final String SEQNO = "seqNo";
	public static final String AUTOAPPROVAL = "autoApproval";

	@ApiModelProperty( //
			required = true, //
			dataType = "java.lang.Integer", //
			value = "This translates to `C_CreditLimit_Type.C_CreditLimit_Type_ID`.")
	@JsonProperty(METASFRESH_ID)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId metasfreshId;

	@ApiModelProperty(value = "This translates to `C_CreditLimit_Type.Name`.")
	@JsonProperty(NAME)
	String name;

	@ApiModelProperty(value = "This translates to `C_CreditLimit_Type.IsAutoApproval`.")
	@JsonProperty(AUTOAPPROVAL)
	boolean autoApproval;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonResponseCreditLimitType(
			@JsonProperty(METASFRESH_ID) @NonNull final JsonMetasfreshId metasfreshId,
			@JsonProperty(NAME)  @NonNull final String name,
			@JsonProperty(AUTOAPPROVAL) final boolean autoApproval)
	{
		this.metasfreshId = metasfreshId;
		this.name = name;
		this.autoApproval = autoApproval;
	}
}
