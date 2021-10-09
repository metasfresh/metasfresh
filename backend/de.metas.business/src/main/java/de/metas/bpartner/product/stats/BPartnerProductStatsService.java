package de.metas.bpartner.product.stats;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import lombok.NonNull;

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

@Service
public class BPartnerProductStatsService
{
	private final BPartnerProductStatsRepository statsRepo;

	public BPartnerProductStatsService(
			@NonNull final BPartnerProductStatsRepository statsRepo)
	{
		this.statsRepo = statsRepo;
	}

	public ImmutableMap<ProductId, BPartnerProductStats> getByPartnerAndProducts(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Set<ProductId> productIds)
	{
		return statsRepo.getByPartnerAndProducts(bpartnerId, productIds);
	}

	public List<BPartnerProductStats> getByProductIds(@NonNull final Set<ProductId> productIds, @Nullable final CurrencyId currencyId)
	{
		return statsRepo.getByProductIds(productIds, currencyId);
	}
}
