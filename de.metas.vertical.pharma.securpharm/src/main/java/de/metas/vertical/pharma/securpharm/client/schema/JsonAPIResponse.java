/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.client.schema;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonAPIResponse
{
	@JsonProperty("res")
	@Getter(AccessLevel.NONE)
	private JsonResult res;

	@JsonProperty("prod")
	private JsonProduct prod;

	@JsonProperty("pack")
	private JsonPackage pack;

	@JsonProperty("tx")
	@Getter(AccessLevel.NONE)
	private JsonTransaction tx;

	public String getResultCode()
	{
		return res != null ? res.getCode() : null;
	}

	public String getResultMessage()
	{
		return res != null ? res.getMessage() : null;
	}

	public boolean isTransactionSet()
	{
		return tx != null;
	}

	public String getServerTransactionId()
	{
		return tx != null ? tx.getServerTransactionId() : null;
	}
}
