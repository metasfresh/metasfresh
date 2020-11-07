package de.metas.bpartner.composite.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.business
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
@ToString
final class BPartnerCompositeCacheById
{
	private static final Logger logger = LogManager.getLogger(BPartnerCompositeCacheById.class);

	private final CCache<BPartnerId, BPartnerComposite> cache = CCache
			.<BPartnerId, BPartnerComposite> builder()
			.cacheName("BPartnerComposite_by_Id")
			.additionalTableNameToResetFor(I_C_BPartner.Table_Name)
			.additionalTableNameToResetFor(I_C_BPartner_Location.Table_Name)
			.additionalTableNameToResetFor(I_AD_User.Table_Name)
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(500)
			.invalidationKeysMapper(this::extractBPartnerIds)
			.build();

	public Collection<BPartnerComposite> getAllOrLoad(
			@NonNull final Collection<BPartnerId> bpartnerIds,
			@NonNull final Function<Set<BPartnerId>, Map<BPartnerId, BPartnerComposite>> loader)
	{
		return cache.getAllOrLoad(bpartnerIds, loader);
	}

	private Collection<BPartnerId> extractBPartnerIds(@NonNull final TableRecordReference recordRef)
	{
		final ImmutableList<BPartnerId> result;
		if (I_C_BPartner.Table_Name.equals(recordRef.getTableName()))
		{
			result = ImmutableList.of(BPartnerId.ofRepoId(recordRef.getRecord_ID()));
		}
		else if (I_C_BPartner_Location.Table_Name.equals(recordRef.getTableName()))
		{
			final I_C_BPartner_Location bpartnerLocationRecord = recordRef.getModel(I_C_BPartner_Location.class);
			if (bpartnerLocationRecord == null) // can happen while we are in the process of storing a bpartner with locations and contacts
			{
				result = ImmutableList.of();
			}
			else
			{
				result = ImmutableList.of(BPartnerId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID()));
			}
		}
		else if (I_AD_User.Table_Name.equals(recordRef.getTableName()))
		{
			final I_AD_User userRecord = recordRef.getModel(I_AD_User.class);
			if (userRecord == null) // can happen while we are in the process of storing a bpartner with locations and contacts
			{
				result = ImmutableList.of();
			}
			else
			{
				final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(userRecord.getC_BPartner_ID());
				result = bpartnerId != null ? ImmutableList.of(bpartnerId) : ImmutableList.of();
			}
		}
		else
		{
			throw new AdempiereException("Given recordRef has unexpected tableName=" + recordRef.getTableName() + "; recordRef=" + recordRef);
		}

		logger.debug("extractBPartnerIds for recordRef={} returns result={}", recordRef, result);
		return result;
	}

}
