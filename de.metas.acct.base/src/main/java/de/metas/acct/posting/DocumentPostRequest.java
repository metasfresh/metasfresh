package de.metas.acct.posting;

import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import de.metas.user.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.acct.base
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
@Value
public class DocumentPostRequest
{
	@JsonProperty("record")
	TableRecordReference record;
	@JsonProperty("clientId")
	ClientId clientId;
	@JsonProperty("force")
	boolean force;
	@JsonProperty("responseRequired")
	boolean responseRequired;
	@JsonProperty("onErrorNotifyUserId")
	UserId onErrorNotifyUserId;

	@Builder(toBuilder = true)
	@JsonCreator
	private DocumentPostRequest(
			@JsonProperty("record") @NonNull final TableRecordReference record,
			@JsonProperty("clientId") @NonNull final ClientId clientId,
			@JsonProperty("force") final boolean force,
			@JsonProperty("responseRequired") final boolean responseRequired,
			@JsonProperty("onErrorNotifyUserId") final UserId onErrorNotifyUserId)
	{
		this.record = record;
		this.clientId = clientId;
		this.force = force;
		this.responseRequired = responseRequired;
		this.onErrorNotifyUserId = onErrorNotifyUserId;
	}

	public DocumentPostRequest withResponseRequired()
	{
		if (this.responseRequired)
		{
			return this;
		}

		return toBuilder().responseRequired(true).build();
	}

	public DocumentPostRequest withResponseNotRequired()
	{
		if (!this.responseRequired)
		{
			return this;
		}

		return toBuilder().responseRequired(false).build();
	}

}
