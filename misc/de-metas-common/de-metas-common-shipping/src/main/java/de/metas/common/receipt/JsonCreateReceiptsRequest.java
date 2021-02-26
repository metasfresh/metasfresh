/*
 * #%L
 * de-metas-common-shipping
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

package de.metas.common.receipt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.ImmutableList;
import de.metas.common.MeasurableRequest;
import de.metas.common.customerreturns.JsonCreateCustomerReturnInfo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Objects;

@Value
@JsonDeserialize(builder = JsonCreateReceiptsRequest.JsonCreateReceiptsRequestBuilder.class)
public class JsonCreateReceiptsRequest extends MeasurableRequest
{
	@NonNull
	@JsonProperty("receiptList")
	List<JsonCreateReceiptInfo> jsonCreateReceiptInfoList;

	@NonNull
	@JsonProperty("returnList")
	List<JsonCreateCustomerReturnInfo> jsonCreateCustomerReturnInfoList;

	@Builder
	@JsonIgnoreProperties(ignoreUnknown = true)
	public JsonCreateReceiptsRequest(@JsonProperty("receiptList") final List<JsonCreateReceiptInfo> jsonCreateReceiptInfoList,
			@JsonProperty("returnList") final List<JsonCreateCustomerReturnInfo> jsonCreateCustomerReturnInfoList)
	{
		this.jsonCreateReceiptInfoList = jsonCreateReceiptInfoList != null
				? jsonCreateReceiptInfoList.stream().filter(Objects::nonNull).collect(ImmutableList.toImmutableList())
				: ImmutableList.of();

		this.jsonCreateCustomerReturnInfoList = jsonCreateCustomerReturnInfoList != null
				? jsonCreateCustomerReturnInfoList.stream().filter(Objects::nonNull).collect(ImmutableList.toImmutableList())
				: ImmutableList.of();
	}

	@JsonIgnore
	public int getSize()
	{
		return jsonCreateReceiptInfoList.size() + jsonCreateCustomerReturnInfoList.size();
	}
}
