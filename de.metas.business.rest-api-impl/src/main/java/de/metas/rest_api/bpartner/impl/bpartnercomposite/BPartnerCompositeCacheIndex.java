package de.metas.rest_api.bpartner.impl.bpartnercomposite;

import static de.metas.util.Check.isEmpty;

import java.util.Collection;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.cache.CacheIndexDataAdapter;
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
	public Collection<BPartnerCompositeLookupKey> extractCKs(@NonNull final BPartnerComposite record)
	{
		final ImmutableList.Builder<BPartnerCompositeLookupKey> result = ImmutableList.builder();

		final String value = record.getBpartner().getValue();
		if (!isEmpty(value, true))
		{
			result.add(BPartnerCompositeLookupKey.ofCode(value));
		}

		final ImmutableSet<GLN> locationGlns = record.extractLocationGlns();
		for (final GLN locationGln : locationGlns)
		{
			result.add(BPartnerCompositeLookupKey.ofGln(locationGln));
		}

		final ExternalId externalId = record.getBpartner().getExternalId();
		if (externalId != null)
		{
			result.add(BPartnerCompositeLookupKey.ofJsonExternalId(JsonConverters.toJsonOrNull(externalId)));
		}

		final MetasfreshId metasfreshId = MetasfreshId.ofOrNull(record.getBpartner().getId());
		if (metasfreshId != null)
		{
			result.add(BPartnerCompositeLookupKey.ofMetasfreshId(metasfreshId));
		}

		return result.build();
	}

	@Override
	public BPartnerId extractRK(@NonNull final BPartnerComposite record)
	{
		return record.getBpartner().getId();
	}

	@Override
	public BPartnerId extractRK(@NonNull final TableRecordReference recordRef)
	{
		return BPartnerId.ofRepoId(recordRef.getRecord_ID());
	}
}
