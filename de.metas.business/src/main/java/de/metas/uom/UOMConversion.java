package de.metas.uom;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Builder
@Value
public class UOMConversion
{
	UomId fromUomId;
	UomId toUomId;
	BigDecimal fromToMultiplier;
	BigDecimal toFromMultiplier;

	@Builder
	private UOMConversion(
			@NonNull final UomId fromUomId,
			@NonNull final UomId toUomId,
			@NonNull final BigDecimal fromToMultiplier,
			@NonNull final BigDecimal toFromMultiplier)
	{
		Check.assume(fromToMultiplier.signum() != 0, "invalid fromToMultiplier: {}", fromToMultiplier);
		Check.assume(toFromMultiplier.signum() != 0, "invalid toFromMultiplier: {}", toFromMultiplier);

		this.fromUomId = fromUomId;
		this.toUomId = toUomId;
		this.fromToMultiplier = fromToMultiplier;
		this.toFromMultiplier = toFromMultiplier;
	}

	public static UOMConversion one(@NonNull final UomId uomId)
	{
		return builder()
				.fromUomId(uomId)
				.toUomId(uomId)
				.fromToMultiplier(BigDecimal.ONE)
				.toFromMultiplier(BigDecimal.ONE)
				.build();
	}

	public UOMConversion invert()
	{
		if (fromUomId.equals(toUomId))
		{
			return this;
		}

		return UOMConversion.builder()
				.fromUomId(toUomId)
				.toUomId(fromUomId)
				.fromToMultiplier(toFromMultiplier)
				.toFromMultiplier(fromToMultiplier)
				.build();
	}

	public boolean isOne()
	{
		return fromToMultiplier.compareTo(BigDecimal.ONE) == 0
				&& toFromMultiplier.compareTo(BigDecimal.ONE) == 0;
	}

	public BigDecimal convert(@NonNull final BigDecimal qty)
	{
		return convert(qty, fromUomId, toUomId);
	}

	public BigDecimal convert(
			@NonNull final BigDecimal qty,
			@NonNull final UomId fromUomId,
			@NonNull final UomId toUomId)
	{
		if (qty.signum() == 0)
		{
			return qty;
		}

		//
		// Direct
		if (this.fromUomId.equals(fromUomId)
				&& this.toUomId.equals(toUomId))
		{
			return qty.multiply(fromToMultiplier);
		}
		//
		// Reversed
		else if (this.fromUomId.equals(toUomId)
				&& this.toUomId.equals(fromUomId))
		{
			return qty.multiply(toFromMultiplier);
		}
		//
		// N/A
		else
		{
			throw new AdempiereException("UOMs not match. Cannot convert " + qty + ", " + fromUomId + "->" + toUomId + " using " + this);
		}
	}
}
