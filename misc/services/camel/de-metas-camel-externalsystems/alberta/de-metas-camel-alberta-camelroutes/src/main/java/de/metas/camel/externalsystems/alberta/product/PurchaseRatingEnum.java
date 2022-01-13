/*
 * #%L
 * de-metas-camel-alberta-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.alberta.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum PurchaseRatingEnum
{
	A("A", BigDecimal.valueOf(0)),
	B("B", BigDecimal.valueOf(1)),
	C("C", BigDecimal.valueOf(2)),
	D("D", BigDecimal.valueOf(3)),
	E("E", BigDecimal.valueOf(4)),
	F("F", BigDecimal.valueOf(5)),
	G("G", BigDecimal.valueOf(6));

	private final String code;
	private final BigDecimal value;

	@NonNull
	public static Optional<PurchaseRatingEnum> ofCodeOrNull(@Nullable final String code)
	{
		if (StringUtils.isEmpty(code))
		{
			return Optional.empty();
		}

		return Arrays.stream(values())
				.filter(rating -> rating.getCode().equals(code))
				.findFirst();
	}

	@Nullable
	public static BigDecimal getValueByCodeOrNull(@Nullable final String code)
	{
		return ofCodeOrNull(code)
				.map(PurchaseRatingEnum::getValue)
				.orElse(null);
	}
}
