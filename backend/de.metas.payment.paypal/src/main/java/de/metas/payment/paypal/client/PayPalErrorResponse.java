package de.metas.payment.paypal.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/*
 * #%L
 * de.metas.payment.paypal
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Data
public class PayPalErrorResponse
{
	@JsonProperty("name")
	String errorCode;

	@JsonProperty("details")
	List<Detail> details;

	@JsonProperty("message")
	String message;

	@JsonProperty("debug_id")
	String debug_id;

	@JsonProperty("links")
	List<Link> links;

	public static final String ERROR_CODE_UKNOWN = "METASFRESH_UNKNOWN";
	public static final String ERROR_CODE_RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";

	public boolean isResourceNotFound()
	{
		return ERROR_CODE_RESOURCE_NOT_FOUND.equals(getErrorCode());
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@Data
	private static class Detail
	{
		String issue;
		String description;
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@Data
	private static class Link
	{
		String href;
		String rel;
		String method;
	}
}
