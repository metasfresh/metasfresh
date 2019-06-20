package de.metas.rest_api.ordercandidates;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.BPARTER_SYNC_ADVISE_DOC;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.SyncAdvise;
import de.metas.rest_api.bpartner.JsonBPartner;
import de.metas.rest_api.bpartner.JsonBPartnerLocation;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.util.Check;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
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

@ApiModel(description = "A BPartner with one contact (optional) and one location. Used in many scenarios multiple times in each order line candidate.<br>" //
		+ "Note that given the respective use-case, either `bpartner.code`, `bpartner.externalId` or `location.gln` might be `null`, but not both at once.")
@Value
public final class JsonBPartnerInfo
{
	JsonBPartner bpartner;

	JsonBPartnerLocation location;

	@JsonInclude(Include.NON_NULL)
	JsonContact contact;

	@ApiModelProperty(required = false, value = BPARTER_SYNC_ADVISE_DOC)
	SyncAdvise syncAdvise;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonBPartnerInfo(
			@JsonProperty("bpartner") @NonNull final JsonBPartner bpartner,
			@JsonProperty("location") final JsonBPartnerLocation location,
			@JsonProperty("contact") final JsonContact contact,
			@JsonProperty("syncAdvise") final SyncAdvise syncAdvise)
	{
		this.bpartner = bpartner;
		this.location = location;
		this.contact = contact;
		this.syncAdvise = coalesce(syncAdvise, SyncAdvise.READ_ONLY);

		final boolean lokupValuesAreOk = !isEmpty(bpartner.getCode(), true)
				|| bpartner.getExternalId() != null
				|| !isEmpty(location.getGln(), true);
		Check.errorUnless(
				lokupValuesAreOk,
				"At least one of bpartner.code, bpartner.externalId or location.gln needs to be non-empty; this={}", this);
	}
}
