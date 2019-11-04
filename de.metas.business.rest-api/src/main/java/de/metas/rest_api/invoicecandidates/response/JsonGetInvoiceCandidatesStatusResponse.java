package de.metas.rest_api.invoicecandidates.response;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.utils.JsonError;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public class JsonGetInvoiceCandidatesStatusResponse
{
	@JsonProperty("result")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JsonGetInvoiceCandidatesStatusResult result;

	@JsonProperty("error")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JsonError error;

	public static JsonGetInvoiceCandidatesStatusResponse ok(@NonNull final JsonGetInvoiceCandidatesStatusResult result)
	{
		final JsonError error = null;
		return new JsonGetInvoiceCandidatesStatusResponse(result, error);
	}

	public static JsonGetInvoiceCandidatesStatusResponse error(@NonNull final JsonError error)
	{
		final JsonGetInvoiceCandidatesStatusResult result = null;
		return new JsonGetInvoiceCandidatesStatusResponse(result, error);
	}

	@JsonCreator
	private JsonGetInvoiceCandidatesStatusResponse(
			@JsonProperty("result") @Nullable final JsonGetInvoiceCandidatesStatusResult result,
			@JsonProperty("error") @Nullable final JsonError error)
	{
		this.error = error;
		if (error == null)
		{
			this.result = result;
		}
		else
		{
			Check.assume(result == null, "No iCands shall be provided when error");
			this.result = null;
		}
	}

	public boolean isError()
	{
		return error != null;
	}

	public JsonError getError()
	{
		if (error == null)
		{
			throw new IllegalStateException("Not an error result: " + this);
		}
		return error;
	}

	public JsonGetInvoiceCandidatesStatusResult getResult()
	{
		if (error != null)
		{
			throw new IllegalStateException("Not a successful result: " + this, error.getThrowable());
		}
		return result;
	}

}
