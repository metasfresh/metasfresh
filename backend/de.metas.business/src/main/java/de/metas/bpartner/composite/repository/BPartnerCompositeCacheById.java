package de.metas.bpartner.composite.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_User_Assigned_Role;
import org.compiere.model.I_C_User_Role;
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

import javax.annotation.Nullable;

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
	private final CCache<BPartnerId, BPartnerComposite> cache;

	BPartnerCompositeCacheById(@NonNull final IUserDAO userDAO)
	{
		cache = CCache
				.<BPartnerId, BPartnerComposite>builder()
				.cacheName("BPartnerComposite_by_Id")
				.additionalTableNameToResetFor(I_C_BPartner.Table_Name)
				.additionalTableNameToResetFor(I_C_BPartner_Location.Table_Name)
				.additionalTableNameToResetFor(I_AD_User.Table_Name)
				.additionalTableNameToResetFor(I_C_User_Assigned_Role.Table_Name)
				.additionalTableNameToResetFor(I_C_User_Role.Table_Name)
				.cacheMapType(CacheMapType.LRU)
				.initialCapacity(500)
				.invalidationKeysMapper(new BPartnerCompositeCachingKeysMapper(userDAO))
				.build();
		
	}

	public Collection<BPartnerComposite> getAllOrLoad(
			@NonNull final Collection<BPartnerId> bpartnerIds,
			@NonNull final Function<Set<BPartnerId>, Map<BPartnerId, BPartnerComposite>> loader)
	{
		return cache.getAllOrLoad(bpartnerIds, loader);
	}
}
