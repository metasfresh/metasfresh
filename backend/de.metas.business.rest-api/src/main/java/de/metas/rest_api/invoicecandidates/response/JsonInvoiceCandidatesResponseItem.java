package de.metas.rest_api.invoicecandidates.response;

import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.rest_api.utils.MetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
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
public class JsonInvoiceCandidatesResponseItem
{
	@ApiModelProperty(position = 10, dataType = "java.lang.String")
	JsonExternalId externalHeaderId;

	@ApiModelProperty(position = 20, dataType = "java.lang.String")
	JsonExternalId externalLineId;

	@ApiModelProperty(position = 30, dataType = "java.lang.Long", value = "The metasfresh-ID of the upserted record")
	@NonNull
	MetasfreshId metasfreshId;
}
