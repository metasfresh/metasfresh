/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.rest_api.v2.invoice;

import de.metas.common.rest_api.v2.SwaggerDocConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonInvoiceReviewUpsertItem
{
	@NonNull
	@ApiModelProperty(position = 10,
			dataType = "java.lang.String",
			value = SwaggerDocConstants.INVOICE_IDENTIFIER_DOC)
	String invoiceIdentifier;

	@ApiModelProperty(position = 20, value = "Optional, to specify the `AD_Org_ID`.\n"
			+ "This property needs to be set to the `AD_Org.Value` of an organisation that the invoking user is allowed to access\n"
			+ "or the invoking user needs to belong to an organisation, which is then used.")
	@Nullable
	String orgCode;

	@Nullable
	@ApiModelProperty(position = 13)
	Map<String, Object> extendedProps;
}
