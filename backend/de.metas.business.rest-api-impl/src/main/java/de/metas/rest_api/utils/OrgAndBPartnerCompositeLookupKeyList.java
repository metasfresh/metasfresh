/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.GLN;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.Objects;

@Value
public class OrgAndBPartnerCompositeLookupKeyList
{
	public static OrgAndBPartnerCompositeLookupKeyList of(
			@NonNull final OrgId orgId, @NonNull final ImmutableList<BPartnerCompositeLookupKey> compositeLookupKeys)
	{
		return new OrgAndBPartnerCompositeLookupKeyList(orgId, compositeLookupKeys);
	}

	/**
	 * @param orgId look for the given org or *
	 */
	public static OrgAndBPartnerCompositeLookupKeyList ofIdentifierString(
			@NonNull final OrgId orgId, @NonNull final IdentifierString identifierString)
	{
		return ofSingleLookupKey(
				orgId, BPartnerCompositeLookupKey.ofIdentifierString(identifierString)
		);
	}

	public static OrgAndBPartnerCompositeLookupKeyList ofMetasfreshId(
			@NonNull final OrgId orgId, @NonNull final MetasfreshId metasfreshId)
	{
		return ofSingleLookupKey(
				orgId, BPartnerCompositeLookupKey.ofMetasfreshId(metasfreshId)
		);
	}

	public static OrgAndBPartnerCompositeLookupKeyList ofGLN(
			@NonNull final OrgId orgId, @NonNull final GLN gln)
	{
		return ofSingleLookupKey(
				orgId, BPartnerCompositeLookupKey.ofGln(gln)
		);
	}

	public static OrgAndBPartnerCompositeLookupKeyList ofSingleLookupKey(
			@NonNull final OrgId orgId, @NonNull final BPartnerCompositeLookupKey bpartnerCompositeLookupKey)
	{
		return new OrgAndBPartnerCompositeLookupKeyList(
				orgId, ImmutableList.of(bpartnerCompositeLookupKey)
		);
	}

	ImmutableSet<BPartnerCompositeLookupKey> compositeLookupKeys;

	OrgId orgId;

	private OrgAndBPartnerCompositeLookupKeyList(
			@NonNull final OrgId orgId, @NonNull final Collection<BPartnerCompositeLookupKey> compositeLookupKeys)
	{
		this.compositeLookupKeys = ImmutableSet.copyOf(compositeLookupKeys);
		this.orgId = orgId;
	}

	public static OrgAndBPartnerCompositeLookupKeyList ofSingleLookupKeys(
			@NonNull final Collection<OrgAndBPartnerCompositeLookupKey> singleQueryLookupKeys)
	{
		if (singleQueryLookupKeys.isEmpty())
		{
			throw new AdempiereException("singleQueryLookupKeys - given collection may not be empty");
		}

		final ImmutableList.Builder<BPartnerCompositeLookupKey> result = ImmutableList.builder();
		OrgId orgId = null;
		for (final OrgAndBPartnerCompositeLookupKey singleQueryLookupKey : singleQueryLookupKeys)
		{
			result.add(singleQueryLookupKey.getCompositeLookupKey());

			if (orgId == null)
			{
				orgId = singleQueryLookupKey.getOrgId();
			}
			else
			{
				if (!Objects.equals(singleQueryLookupKey.getOrgId(), orgId))
				{
					throw new AdempiereException("ofSingleLookupKeys - all singleQueryLookupKeys need to have an equal orgId")
							.appendParametersToMessage()
							.setParameter("singleQueryLookupKeys", singleQueryLookupKeys);
				}
			}
		}
		return new OrgAndBPartnerCompositeLookupKeyList(orgId, result.build());
	}

	/**
	 * @param other list to add to the union. Its orgId has to be equal to this instance's orgId or has to be {@link OrgId#ANY}.  
	 * 
	 * @return a list taht contains both this instances's lookup keys and the given {@code other}'s lookup keys.
	 */
	public OrgAndBPartnerCompositeLookupKeyList union(@NonNull final OrgAndBPartnerCompositeLookupKeyList other)
	{
		final OrgId otherOrgId = other.getOrgId();
		if (!Objects.equals(otherOrgId, this.orgId) && !otherOrgId.isAny())
		{
			throw new AdempiereException("union - other BPartnerCompositeLookupKeysWithOrg needs to have the same orgId")
					.appendParametersToMessage()
					.setParameter("this", this)
					.setParameter("other", other);
		}

		return new OrgAndBPartnerCompositeLookupKeyList(
				this.orgId, ImmutableSet.<BPartnerCompositeLookupKey>builder().addAll(compositeLookupKeys).addAll(other.getCompositeLookupKeys()).build()
		);
	}

	public ImmutableList<OrgAndBPartnerCompositeLookupKey> asSingeKeys()
	{
		return compositeLookupKeys.stream()
				.map(lookupKey -> OrgAndBPartnerCompositeLookupKey.of(lookupKey, orgId))
				.collect(ImmutableList.toImmutableList());
	}
}
