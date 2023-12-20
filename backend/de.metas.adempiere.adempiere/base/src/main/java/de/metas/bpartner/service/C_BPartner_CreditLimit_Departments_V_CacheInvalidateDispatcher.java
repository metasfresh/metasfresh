/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.bpartner.service;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CacheMgt;
import de.metas.cache.ICacheResetListener;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_CreditLimit_Department_Lines_V;
import org.compiere.model.I_C_BPartner_CreditLimit_Departments_V;
import org.compiere.model.I_C_BPartner_Stats;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class C_BPartner_CreditLimit_Departments_V_CacheInvalidateDispatcher implements ICacheResetListener
{
	private final CacheMgt cacheMgt = CacheMgt.get();
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	@PostConstruct
	private void postConstruct()
	{
		cacheMgt.addCacheResetListener(I_C_BPartner_Stats.Table_Name, this);
	}

	@Override
	public long reset(final CacheInvalidateMultiRequest multiRequest)
	{
		final Set<Integer> targetViewIds = extractViewIds(multiRequest);

		trxManager.accumulateAndProcessAfterCommit(
				"C_BPartner_CreditLimit_Departments_V_CacheInvalidateDispatcher.targetViewIds",
				targetViewIds,
				this::invalidate_C_BPartner_CreditLimit_Departments_V
		);

		return 1;
	}

	private Set<Integer> extractViewIds(final CacheInvalidateMultiRequest multiRequest)
	{
		final TableRecordReferenceSet rootRecords = multiRequest.getRootRecords();

		if (!rootRecords.matchesTableName(I_C_BPartner.Table_Name))
		{
			// nothing to do
			return Collections.emptySet();
		}

		return rootRecords.streamIds(I_C_BPartner.Table_Name, BPartnerId::ofRepoId)
				.flatMap(partnerId -> extractPartnersToInvalidate(partnerId).stream())
				.collect(ImmutableSet.toImmutableSet());

	}

	private Set<Integer> extractPartnersToInvalidate(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bPartner = bpartnerDAO.getById(bpartnerId);
		final BPartnerId sectionGroupPartnerId = BPartnerId.ofRepoIdOrNull(bPartner.getSection_Group_Partner_ID());
		if (sectionGroupPartnerId == null)
		{
			return Collections.emptySet();
		}

		return bpartnerDAO.retrieveForSectionGroupPartner(sectionGroupPartnerId);
	}

	private void invalidate_C_BPartner_CreditLimit_Departments_V(@NonNull final List<Integer> ids)
	{
		if (ids.isEmpty())
		{
			return;
		}
		cacheMgt.reset(CacheInvalidateMultiRequest.fromTableNameAndRecordIds(I_C_BPartner_CreditLimit_Departments_V.Table_Name, ids));
		cacheMgt.reset(CacheInvalidateMultiRequest.fromTableNameAndRecordIds(I_C_BPartner_CreditLimit_Department_Lines_V.Table_Name, ids));
		cacheMgt.reset(CacheInvalidateMultiRequest.fromTableNameAndRecordIds(I_C_BPartner.Table_Name, ids));
	}
}