package de.metas.rest_api.bpartner.impl.bpartnercomposite;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.cache.CacheIndexDataAdapter;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.OrgId;
import de.metas.rest_api.common.MetasfreshId;
import de.metas.rest_api.utils.BPartnerCompositeLookupKey;
import de.metas.rest_api.utils.OrgAndBPartnerCompositeLookupKey;
import de.metas.rest_api.utils.JsonConverters;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import java.util.Collection;

import static de.metas.util.Check.isEmpty;

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
		implements CacheIndexDataAdapter<BPartnerId/* RK */, OrgAndBPartnerCompositeLookupKey/* CK */, BPartnerComposite/* V */>
{
	private static final Logger logger = LogManager.getLogger(BPartnerCompositeCacheIndex.class);

	@Override
	public BPartnerId extractDataItemId(@NonNull final BPartnerComposite dataItem)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(I_C_BPartner.Table_Name, dataItem.getBpartner() == null ? null : dataItem.getBpartner().getId()))
		{
			final BPartnerId result = dataItem.getBpartner().getId();
			logger.debug("extractDataItemId - result={}", result);
			return result;
		}
	}

	@Override
	public ImmutableSet<TableRecordReference> extractRecordRefs(final BPartnerComposite dataItem)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(I_C_BPartner.Table_Name, dataItem.getBpartner() == null ? null : dataItem.getBpartner().getId()))
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

			final ImmutableSet<TableRecordReference> result = recordRefs.build();
			logger.debug("extractRecordRefs - extracted record refs for given bpartnerComposite: {}", result);

			return result;
		}
	}

	@Override
	public Collection<OrgAndBPartnerCompositeLookupKey> extractCacheKeys(@NonNull final BPartnerComposite dataItem)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(I_C_BPartner.Table_Name, dataItem.getBpartner() == null ? null : dataItem.getBpartner().getId()))
		{
			final OrgId orgId = dataItem.getOrgId();
			final ImmutableList.Builder<OrgAndBPartnerCompositeLookupKey> cacheKeys = ImmutableList.builder();

			final String value = dataItem.getBpartner().getValue();
			if (!isEmpty(value, true))
			{
				cacheKeys.add(OrgAndBPartnerCompositeLookupKey.of(
						BPartnerCompositeLookupKey.ofCode(value),
						orgId));
			}

			final ImmutableSet<GLN> locationGlns = dataItem.extractLocationGlns();
			for (final GLN locationGln : locationGlns)
			{
				cacheKeys.add(OrgAndBPartnerCompositeLookupKey.of(
						BPartnerCompositeLookupKey.ofGln(locationGln),
						orgId));
			}

			final ExternalId externalId = dataItem.getBpartner().getExternalId();
			if (externalId != null)
			{
				cacheKeys.add(OrgAndBPartnerCompositeLookupKey.of(
						BPartnerCompositeLookupKey.ofJsonExternalId(JsonConverters.toJsonOrNull(externalId)),
						orgId));
			}

			final MetasfreshId metasfreshId = MetasfreshId.ofOrNull(dataItem.getBpartner().getId());
			if (metasfreshId != null)
			{
				cacheKeys.add(
						OrgAndBPartnerCompositeLookupKey.of(
								BPartnerCompositeLookupKey.ofMetasfreshId(metasfreshId),
								orgId));
			}

			final ImmutableList<OrgAndBPartnerCompositeLookupKey> result = cacheKeys.build();
			logger.debug("extractCacheKeys - extracted cache keys for given bpartnerComposite: {}", result);
			return result;
		}
	}
}
