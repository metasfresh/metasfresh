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

package de.metas.common.rest_api.v1;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class JsonUnsettableInvoiceRule extends JsonUnsettableValue
{
	public static final JsonUnsettableInvoiceRule EMPTY = new JsonUnsettableInvoiceRule(null, null);

	@ApiModelProperty(position = 10, required = false, //
			value = "Optional, to override the invoiceRule as derived from metasfresh for the respective invoice candidate's property.\n"
					+ "To unset an existing candiate's override value, you can:\n"
					+ "- either use `SyncAdvice.IfExists.UPDATE_REMOVE` and set this property to `null`"
					+ "- or (preferred) use `\"unsetValue\" : true`")
	JsonInvoiceRule value;

	@ApiModelProperty(position = 20, required = false, //
			value = "Optional property to *explicitly* unset a candidate's override property.\n"
					+ "If set to `true`, it takes precedence to a possibly set `value`")
	Boolean unsetValue;


}
