package de.metas.bpartner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_BPartner;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.common.util.CoalesceUtil;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public enum ShipmentAllocationBestBeforePolicy implements ReferenceListAwareEnum
{
	Expiring_First(X_C_BPartner.SHIPMENTALLOCATION_BESTBEFORE_POLICY_Expiring_First),

	Newest_First(X_C_BPartner.SHIPMENTALLOCATION_BESTBEFORE_POLICY_Newest_First);

	public static final int AD_REFERENCE_ID = X_C_BPartner.SHIPMENTALLOCATION_BESTBEFORE_POLICY_AD_Reference_ID;

	@Getter
	private final String code;

	ShipmentAllocationBestBeforePolicy(@NonNull final String code)
	{
		this.code = code;
	}

	public static ShipmentAllocationBestBeforePolicy ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static Optional<ShipmentAllocationBestBeforePolicy> optionalOfNullableCode(@Nullable final String code)
	{
		return Optional.ofNullable(ofNullableCode(code));
	}

	public static ShipmentAllocationBestBeforePolicy ofCode(@NonNull final String code)
	{
		final ShipmentAllocationBestBeforePolicy type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ShipmentAllocationBestBeforePolicy.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, ShipmentAllocationBestBeforePolicy> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), ShipmentAllocationBestBeforePolicy::getCode);

	public <T> Comparator<T> comparator(@NonNull final Function<T, LocalDate> bestBeforeDateExtractor)
	{
		return (value1, value2) -> {
			final LocalDate bestBefore1 = bestBeforeDateExtractor.apply(value1);
			final LocalDate bestBefore2 = bestBeforeDateExtractor.apply(value2);
			return compareBestBeforeDates(bestBefore1, bestBefore2);
		};
	}

	private int compareBestBeforeDates(@Nullable final LocalDate bestBefore1, @Nullable final LocalDate bestBefore2)
	{
		if (this == Expiring_First)
		{
			final LocalDate bestBefore1Effective = CoalesceUtil.coalesce(bestBefore1, LocalDate.MAX);
			final LocalDate bestBefore2Effective = CoalesceUtil.coalesce(bestBefore2, LocalDate.MAX);
			return bestBefore1Effective.compareTo(bestBefore2Effective);
		}
		else if (this == Newest_First)
		{
			final LocalDate bestBefore1Effective = CoalesceUtil.coalesce(bestBefore1, LocalDate.MIN);
			final LocalDate bestBefore2Effective = CoalesceUtil.coalesce(bestBefore2, LocalDate.MIN);
			return -1 * bestBefore1Effective.compareTo(bestBefore2Effective);
		}
		else
		{
			throw new AdempiereException("Unknown policy: " + this);
		}
	}

}
