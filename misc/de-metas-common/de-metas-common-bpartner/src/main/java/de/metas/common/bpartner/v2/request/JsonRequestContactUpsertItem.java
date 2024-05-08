/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaContact;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.CONTACT_IDENTIFIER_DOC;

@Value
@Builder(toBuilder = true)
@ApiModel(description = "Contains an external id and the actual bpartner to insert or update. The response will contain the given external id.")
public class JsonRequestContactUpsertItem
{
	@ApiModelProperty(allowEmptyValue = false, position = 10, //
			value = CONTACT_IDENTIFIER_DOC + "\n"//
					+ "If the identifier is an `<AD_User_ID>`, then it is assumed that the resource exists in metasfresh.\n"
					+ "If a new contact is created and the actual contact has no different identifier, then this identifier is stored within the newly created contact.") //
	@NonNull
	String contactIdentifier;

	@ApiModelProperty(allowEmptyValue = false, //
			position = 20, value = "The contact to upsert. Note that its `externalId` is ignored in favor of this upsertRequest's `externalId`")
	@NonNull
	JsonRequestContact contact;

	@ApiModelProperty(allowEmptyValue = false, //
			position = 30, value = "The alberta contact to upsert. Note that its `externalId` is ignored in favor of this upsertRequest's `externalId`")
	@Nullable
	JsonAlbertaContact jsonAlbertaContact;

	@JsonCreator
	public JsonRequestContactUpsertItem(
			final @NonNull @JsonProperty("contactIdentifier") String contactIdentifier,
			final @NonNull @JsonProperty("contact") JsonRequestContact contact,
			final @Nullable @JsonProperty("jsonAlbertaContact") JsonAlbertaContact jsonAlbertaContact)
	{
		this.contactIdentifier = contactIdentifier;
		this.contact = contact;
		this.jsonAlbertaContact = jsonAlbertaContact;
	}
}
