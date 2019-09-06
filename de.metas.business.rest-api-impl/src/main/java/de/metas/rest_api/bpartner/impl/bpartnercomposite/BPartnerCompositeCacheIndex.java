package de.metas.rest_api.bpartner.impl.bpartnercomposite;

import static de.metas.util.Check.isEmpty;

import java.util.Collection;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.cache.CacheIndexDataAdapter;
import de.metas.interfaces.I_C_BPartner;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.utils.JsonConverters;
import de.metas.util.rest.ExternalId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

final class BPartnerCompositeCacheIndex
		implements CacheIndexDataAdapter<BPartnerId/* RK */, BPartnerCompositeLookupKey/* CK */, BPartnerComposite/* V */>
{
	@Override
	public BPartnerId extractDataItemId(@NonNull final BPartnerComposite dataItem)
	{
		return dataItem.getBpartner().getId();
	}

	@Override
	public ImmutableSet<TableRecordReference> extractRecordRefs(final BPartnerComposite dataItem)
	{
		final ImmutableSet.Builder<TableRecordReference> recordRefs = ImmutableSet.builder();

		recordRefs.add(TableRecordReference.of(I_C_BPartner.Table_Name, dataItem.getBpartner().getId()));

		for (final BPartnerLocation location : dataItem.getLocations())
		{
			recordRefs.add(TableRecordReference.of(I_C_BPartner_Location.Table_Name, location.getId()));
		}

		for (final BPartnerContact contact : dataItem.getContacts())
		{
			recordRefs.add(TableRecordReference.of(I_AD_User.Table_Name, contact.getId()));
		}

		return recordRefs.build();
	}

	@Override
	public Collection<BPartnerCompositeLookupKey> extractCacheKeys(@NonNull final BPartnerComposite dataItem)
	{
		final ImmutableList.Builder<BPartnerCompositeLookupKey> result = ImmutableList.builder();

		final String value = dataItem.getBpartner().getValue();
		if (!isEmpty(value, true))
		{
			result.add(BPartnerCompositeLookupKey.ofCode(value));
		}

		final ImmutableSet<GLN> locationGlns = dataItem.extractLocationGlns();
		for (final GLN locationGln : locationGlns)
		{
			result.add(BPartnerCompositeLookupKey.ofGln(locationGln));
		}

		final ExternalId externalId = dataItem.getBpartner().getExternalId();
		if (externalId != null)
		{
			result.add(BPartnerCompositeLookupKey.ofJsonExternalId(JsonConverters.toJsonOrNull(externalId)));
		}

		final MetasfreshId metasfreshId = MetasfreshId.ofOrNull(dataItem.getBpartner().getId());
		if (metasfreshId != null)
		{
			result.add(BPartnerCompositeLookupKey.ofMetasfreshId(metasfreshId));
		}

		return result.build();
	}
}
