package de.metas.pricing.rules.scale_price;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.metas.pricing.ProductPriceId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

@Service
public class ScalePriceService
{
	private final ScalePriceRepository scalePriceRepository;
	// private final MoneyService moneyService;

	public ScalePriceService(
			@NonNull final ScalePriceRepository scalePriceRepository
	// ,@NonNull final MoneyService moneyService
	)
	{
		this.scalePriceRepository = scalePriceRepository;

	}

	public Optional<Map<BigDecimal, BigDecimal>> getScalePrice(@NonNull final ProductPriceId productPriceId)
	{
		final Map<BigDecimal, BigDecimal> scales = scalePriceRepository.getScales(productPriceId);

		return Optional.of(scales);
	}

}
