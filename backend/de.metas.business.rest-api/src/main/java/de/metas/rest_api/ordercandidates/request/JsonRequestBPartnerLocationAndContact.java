package de.metas.rest_api.ordercandidates.request;

import static de.metas.common.rest_api.SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC;
import static de.metas.util.Check.isEmpty;
import static de.metas.common.util.CoalesceUtil.coalesce;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.bpartner.request.JsonRequestBPartner;
import de.metas.common.bpartner.request.JsonRequestContact;
import de.metas.common.bpartner.request.JsonRequestLocation;
import de.metas.common.rest_api.SyncAdvise;
import de.metas.util.Check;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
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

@ApiModel(description = "A BPartner with one contact (optional) and one location.\n"
		+ "Can be used multiple times in each order line candidate, for billTo-partner, shipTo-partner etc.\n" //
		+ "Note that given the respective use-case, either `bpartner.code`, `bpartner.externalId` or `location.gln` might be `null`, but not all at once.")
@Value
public class JsonRequestBPartnerLocationAndContact
{
	@ApiModelProperty(required = true)
	JsonRequestBPartner bpartner;

	@ApiModelProperty(required = true, value = "This model object is also used in the bpartner-Rest endpoint."
			+ "However, the location's \"inner\"/own sync advise is not only applied to the location's own fields, but also to the whole location.\n"
			+ "Therefore, you can e.g. create a missing location by specifying `IfNotExists/CREATE` within this location")
	JsonRequestLocation location;

	@ApiModelProperty(value = "This model object is also used in the bpartner-Rest endpoint."
			+ "However, the contact's \"inner\"/own sync advise is not only applied to the contact's own fields, but also to the whole contact.\n"
			+ "Therefore, you can e.g. create a missing contact by specifying `IfNotExists/CREATE` within this contact")
	JsonRequestContact contact;

	@ApiModelProperty(value = "Specifies how to lookup the BPartner in metasfresh. If not set and there are multiple possibilities, then:\n"
			+ "* `ExternalId` is prefered\n"
			+ "* if there is none, then `GLN` is used\n"
			+ "* there is also no `GLN`, then `code` is used")
	BPartnerLookupAdvise bpartnerLookupAdvise;

	@ApiModelProperty(value = READ_ONLY_SYNC_ADVISE_DOC)
	SyncAdvise syncAdvise;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonRequestBPartnerLocationAndContact(
			@JsonProperty("bpartner") final JsonRequestBPartner bpartner,
			@JsonProperty("location") final JsonRequestLocation location,
			@JsonProperty("contact") final JsonRequestContact contact,
			@JsonProperty("syncAdvise") final SyncAdvise syncAdvise,
			@JsonProperty("bpartnerLookupAdvise") final BPartnerLookupAdvise bpartnerLookupAdvise)
	{
		this.bpartner = bpartner;
		this.location = location;
		this.contact = contact;
		this.syncAdvise = coalesce(syncAdvise, SyncAdvise.READ_ONLY);
		this.bpartnerLookupAdvise = bpartnerLookupAdvise;

		// validate
		final boolean hasBPartnerCode = bpartner != null && !isEmpty(bpartner.getCode(), true);
		final boolean hasBPartnerExternalId = bpartner != null && bpartner.getExternalId() != null;
		final boolean hasBPartnerLocationGln = location != null && !isEmpty(location.getGln(), true);

		final boolean lokupValuesAreOk = hasBPartnerCode || hasBPartnerExternalId || hasBPartnerLocationGln;
		Check.errorUnless(
				lokupValuesAreOk,
				"At least one of bpartner.code, bpartner.externalId or location.gln needs to be non-empty; this={}", this);
		if (bpartnerLookupAdvise != null)
		{
			switch (bpartnerLookupAdvise)
			{
				case GLN:
					Check.errorUnless(hasBPartnerLocationGln, "With bpartnerLookupAdvise={}, location.gln may not be empty; this={}", bpartnerLookupAdvise, this);
					break;
				case ExternalId:
					Check.errorUnless(hasBPartnerExternalId, "With bpartnerLookupAdvise={}, bpartner.externalId may not be empty; this={}", bpartnerLookupAdvise, this);
					break;
				case Code:
					Check.errorUnless(hasBPartnerCode, "With bpartnerLookupAdvise={}, bpartner.code may not be empty; this={}", bpartnerLookupAdvise, this);
					break;
				default:
					Check.fail("Unsupported bpartnerLookupAdvise={}; this={}", bpartnerLookupAdvise, this);
			}
		}
	}
}
