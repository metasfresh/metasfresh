package de.metas.pricing.limit.impl;

import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import de.metas.location.CountryId;
import org.compiere.model.I_C_PriceLimit_Restriction;

import com.google.common.collect.ImmutableSet;

import de.metas.cache.CCache;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.limit.IPriceLimitRestrictionsRepository;
import de.metas.pricing.limit.PriceLimitRestrictions;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
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

public class PriceLimitRestrictionsRepository implements IPriceLimitRestrictionsRepository
{
	private final CCache<Integer, Optional<PriceLimitRestrictions>> cache = CCache.newCache(I_C_PriceLimit_Restriction.Table_Name, 1, CCache.EXPIREMINUTES_Never);

	@Override
	public Optional<PriceLimitRestrictions> get()
	{
		return cache.getOrLoad(0, this::retrievePriceLimitRestrictions);
	}

	private Optional<PriceLimitRestrictions> retrievePriceLimitRestrictions()
	{
		final I_C_PriceLimit_Restriction priceLimitRestrictionsRecord = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_PriceLimit_Restriction.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.firstOnly(I_C_PriceLimit_Restriction.class);
		if (priceLimitRestrictionsRecord == null)
		{
			return Optional.empty();
		}

		final PriceLimitRestrictions priceLimitRestrictions = PriceLimitRestrictions.builder()
				.basePricingSystemId(PricingSystemId.ofRepoId(priceLimitRestrictionsRecord.getBase_PricingSystem_ID()))
				.priceAddAmt(priceLimitRestrictionsRecord.getStd_AddAmt())
				.discountPercent(priceLimitRestrictionsRecord.getDiscount())
				.build();
		return Optional.of(priceLimitRestrictions);
	}

	@Override
	public Set<CountryId> getPriceCountryIds()
	{
		return get()
				.map(this::getPriceCountryIds)
				.orElseGet(ImmutableSet::of);
	}

	private final Set<CountryId> getPriceCountryIds(final PriceLimitRestrictions priceLimitRestrictions)
	{
		final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
		return priceListsRepo.retrieveCountryIdsByPricingSystem(priceLimitRestrictions.getBasePricingSystemId());
	}

}
