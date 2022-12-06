/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.bpartner;

import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

/**
 * If a request-item is coming with an identifier such as {@code ext-1234}, then this service makes sure that the item itself has the respective property such as {@code "externalId" : "1234"} set.
 */
@Service
public class JsonRequestConsolidateService
{
	/**
	 * Puts the request's identifier string also into the request's item.
	 * E.g. if the identifier string is val-123 and the request's item's value is empty, then it puts item's value = "123"
	 */
	public void consolidateWithIdentifier(@NonNull final JsonRequestBPartnerUpsertItem requestItem)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(requestItem.getBpartnerIdentifier());
		final JsonRequestComposite bpartnerComposite = requestItem.getBpartnerComposite();

		consolidateBPartnerWithIdentifier(externalIdentifier, bpartnerComposite.getBpartner());

		consolidateWithIdentifier(bpartnerComposite.getContactsNotNull());

		consolidateWithIdentifier(bpartnerComposite.getLocationsNotNull());

		// note that bank-accounts and creditLimits don't need this treatment yet because they don't have the identifier stuff etc
	}

	public void consolidateWithIdentifier(@NonNull final JsonRequestContactUpsert contacts)
	{
		for (final JsonRequestContactUpsertItem contactRequestItem : contacts.getRequestItems())
		{
			consolidateWithIdentifier(contactRequestItem);
		}
	}

	public void consolidateWithIdentifier(@NonNull final JsonRequestLocationUpsert locations)
	{
		for (final JsonRequestLocationUpsertItem locationRequestItem : locations.getRequestItems())
		{
			consolidateWithIdentifier(locationRequestItem);
		}
	}

	public static void consolidateWithOrg(@NonNull final JsonRequestBPartnerUpsertItem requestItem, @Nullable final String orgCode)
	{
		if (Check.isBlank(orgCode))
		{
			//nothing to consolidate
			return;
		}

		final String requestItemOrgCode = requestItem.getBpartnerComposite().getOrgCode();

		if (Check.isBlank(requestItemOrgCode))
		{
			requestItem.getBpartnerComposite().setOrgCode(orgCode);
			return;
		}

		if (!orgCode.equals(requestItemOrgCode))
		{
			throw new AdempiereException("Path parameter orgCode: " + orgCode + " and JsonRequestComposite.OrgCode: " + requestItemOrgCode + " don't match!")
					.appendParametersToMessage()
					.setParameter(requestItem.getBpartnerIdentifier(), "BPartnerIdentifier");
		}
	}

	/**
	 * @return the identifier string which the given {@code jsonBPartner} will have *after* if was synched and persistes in metasfresh.
	 */
	private void consolidateBPartnerWithIdentifier(
			@NonNull final ExternalIdentifier identifierString,
			@Nullable final JsonRequestBPartner jsonBPartner)
	{
		if (jsonBPartner == null)
		{
			return;
		}

		switch (identifierString.getType())
		{
			case METASFRESH_ID:
				// nothing to do; the JsonRequestBPartner has no metasfresh-ID to consolidate with
				break;
			case EXTERNAL_REFERENCE:
				// nothing to do
				break;
			case GLN:
				// GLN-identifierString is valid for bPartner-lookup, but we can't consolidate the given jsonBPartner with it
				break;
			default:
				throw new AdempiereException("Unexpected IdentifierString.Type=" + identifierString.getType())
						.appendParametersToMessage()
						.setParameter("identifierString", identifierString)
						.setParameter("jsonRequestBPartner", jsonBPartner);
		}
	}

	private void consolidateWithIdentifier(@NonNull final JsonRequestLocationUpsertItem requestItem)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(requestItem.getLocationIdentifier());
		final JsonRequestLocation jsonLocation = requestItem.getLocation();

		switch (externalIdentifier.getType())
		{
			case METASFRESH_ID:
				// nothing to do; the JsonRequestLocationUpsertItem has no metasfresh-ID to consolidate with
				break;
			case EXTERNAL_REFERENCE:
				// nothing to do
				break;
			case GLN:
				if (jsonLocation.isGlnSet())
				{
					jsonLocation.setGln(externalIdentifier.asGLN().getCode());
				}
				break;
			default:
				throw new AdempiereException("Unexpected IdentifierString.Type=" + externalIdentifier.getType())
						.appendParametersToMessage()
						.setParameter("externalIdentifier", externalIdentifier)
						.setParameter("jsonRequestLocationUpsertItem", requestItem);
		}
	}

	private void consolidateWithIdentifier(@NonNull final JsonRequestContactUpsertItem requestItem)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(requestItem.getContactIdentifier());
		switch (externalIdentifier.getType())
		{
			case METASFRESH_ID:
				// nothing to do; the JsonRequestContactUpsertItem has no metasfresh-ID to consolidate with
				break;
			case EXTERNAL_REFERENCE:
				// nothing to do
				break;
			default:
				throw new AdempiereException("Unexpected IdentifierString.Type=" + externalIdentifier.getType())
						.appendParametersToMessage()
						.setParameter("externalIdentifier", externalIdentifier)
						.setParameter("jsonRequestContactUpsertItem", requestItem);
		}
	}
}
