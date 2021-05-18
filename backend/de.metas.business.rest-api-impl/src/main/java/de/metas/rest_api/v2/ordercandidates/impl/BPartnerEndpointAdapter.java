package de.metas.rest_api.v2.ordercandidates.impl;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.BPartnerInfo.BPartnerInfoBuilder;
import de.metas.common.bpartner.v2.response.JsonResponseBPartner;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.ordercandidates.v2.request.JsonRequestBPartnerLocationAndContact;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.logging.LogManager;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.v2.bpartner.BpartnerRestController;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nullable;
import java.util.Optional;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
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

final class BPartnerEndpointAdapter
{
	private final BpartnerRestController bpartnerRestController;

	public BPartnerEndpointAdapter(@NonNull final BpartnerRestController bpartnerRestController)
	{
		this.bpartnerRestController = bpartnerRestController;
	}

	@NonNull
	public Optional<BPartnerInfo> getBPartnerInfo(
			@Nullable final JsonRequestBPartnerLocationAndContact jsonBPartnerInfo,
			@Nullable final String orgCode)
	{
		if (jsonBPartnerInfo == null)
		{
			return Optional.empty();
		}

		final ResponseEntity<JsonResponseComposite> response = bpartnerRestController
				.retrieveBPartner(orgCode, jsonBPartnerInfo.getBPartnerIdentifier());

		final MetasfreshId bPartnerId = Optional.ofNullable(response)
				.map(ResponseEntity::getBody)
				.map(JsonResponseComposite::getBpartner)
				.map(JsonResponseBPartner::getMetasfreshId)
				.map(JsonMetasfreshId::getValue)
				.map(MetasfreshId::of)
				.orElseThrow(() -> new AdempiereException("No BPartner found for the given identifier!")
						.appendParametersToMessage()
						.setParameter("BPartnerIdentifier", jsonBPartnerInfo.getBPartnerIdentifier()));

		final ResponseEntity<JsonResponseLocation> locationResponse = bpartnerRestController
				.retrieveBPartnerLocation(orgCode, jsonBPartnerInfo.getBPartnerIdentifier(), jsonBPartnerInfo.getBPartnerLocationIdentifier());

		final JsonResponseLocation jsonResponseLocation = Optional.ofNullable(locationResponse.getBody())
				.orElseThrow(() -> new AdempiereException("No BPartnerLocation found for the given identifier!")
						.appendParametersToMessage()
						.setParameter("BPartnerIdentifier", jsonBPartnerInfo.getBPartnerIdentifier())
						.setParameter("BPartnerLocationIdentifier", jsonBPartnerInfo.getBPartnerLocationIdentifier()));

		final JsonResponseContact jsonResponseContact = Optional.ofNullable(jsonBPartnerInfo.getContactIdentifier())
				.map(contactIdentifier -> bpartnerRestController.retrieveBPartnerContact(orgCode, jsonBPartnerInfo.getBPartnerIdentifier(), contactIdentifier))
				.map(ResponseEntity::getBody)
				.orElse(null);

		final BPartnerInfo bPartnerInfo = asBPartnerInfo(bPartnerId, jsonResponseLocation, jsonResponseContact);

		return Optional.of(bPartnerInfo);
	}

	@NonNull
	private BPartnerInfo asBPartnerInfo(
			@NonNull final MetasfreshId bPartnerMetasfreshId,
			@NonNull final JsonResponseLocation jsonResponseLocation,
			@Nullable final JsonResponseContact jsonResponseContact)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bPartnerMetasfreshId.getValue());
		final BPartnerInfoBuilder result = BPartnerInfo
				.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId.getRepoId(), jsonResponseLocation.getMetasfreshId().getValue()));

		if (jsonResponseContact != null)
		{
			result.contactId(BPartnerContactId.ofRepoId(bpartnerId.getRepoId(), jsonResponseContact.getMetasfreshId().getValue()));
		}

		return result.build();
	}

	@NonNull
	public JsonResponseBPartner getJsonBPartnerById(@Nullable final String orgCode, @NonNull final BPartnerId bpartnerId)
	{
		final ResponseEntity<JsonResponseComposite> bpartner = bpartnerRestController.retrieveBPartner(
				orgCode,
				Integer.toString(bpartnerId.getRepoId()));

		return Optional.ofNullable(bpartner.getBody())
				.map(JsonResponseComposite::getBpartner)
				.orElseThrow(() -> new AdempiereException("No BPartner found for the given bPartnerIdentifier!")
						.appendParametersToMessage()
						.setParameter("BPartnerIdentifier", bpartnerId));
	}

	@NonNull
	public JsonResponseLocation getJsonBPartnerLocationById(@Nullable final String orgCode, @NonNull final BPartnerLocationId bpartnerLocationId)
	{
		final ResponseEntity<JsonResponseLocation> location = bpartnerRestController.retrieveBPartnerLocation(
				orgCode,
				Integer.toString(bpartnerLocationId.getBpartnerId().getRepoId()),
				Integer.toString(bpartnerLocationId.getRepoId()));

		return Optional.ofNullable(location.getBody())
				.orElseThrow(() -> new AdempiereException("No BPartnerLocation found for the given bPartnerIdentifier,BPartnerLocationIdentifier!")
						.appendParametersToMessage()
						.setParameter("BPartnerIdentifier", bpartnerLocationId.getBpartnerId().getRepoId())
						.setParameter("BPartnerLocationIdentifier", bpartnerLocationId.getRepoId()));
	}

	@NonNull
	public JsonResponseContact getJsonBPartnerContactById(@Nullable final String orgCode, @NonNull final BPartnerContactId bpartnerContactId)
	{
		final ResponseEntity<JsonResponseContact> contact = bpartnerRestController.retrieveBPartnerContact(
				orgCode,
				Integer.toString(bpartnerContactId.getBpartnerId().getRepoId()),
				Integer.toString(bpartnerContactId.getRepoId()));

		return Optional.ofNullable(contact.getBody())
				.orElseThrow(() -> new AdempiereException("No BPartnerContact found for the given bPartnerIdentifier,BPartnerContactId!")
						.appendParametersToMessage()
						.setParameter("BPartnerIdentifier", bpartnerContactId.getBpartnerId().getRepoId())
						.setParameter("bpartnerContactIdentifier", bpartnerContactId.getRepoId()));
	}

	@NonNull
	public JsonResponseBPartner getJsonBPartnerByExternalIdentifier(@Nullable final String orgCode, @NonNull final String externalIdentifier)
	{
		final ResponseEntity<JsonResponseComposite> bpartner = bpartnerRestController.retrieveBPartner(orgCode, externalIdentifier);

		return Optional.ofNullable(bpartner.getBody())
				.map(JsonResponseComposite::getBpartner)
				.orElseThrow(() -> new AdempiereException("No BPartner found for the given external identifier!")
						.appendParametersToMessage()
						.setParameter("BPartnerIdentifier", externalIdentifier));
	}
}
