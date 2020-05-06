package de.metas.order.compensationGroup;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.compiere.model.X_C_OrderLine;

import de.metas.util.GuavaCollectors;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public enum GroupCompensationAmtType
{
	Percent(X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent), //
	PriceAndQty(X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_PriceAndQty);

	@Getter
	private final String adRefListValue;

	private GroupCompensationAmtType(final String adRefListValue)
	{
		this.adRefListValue = adRefListValue;
	}

	public static GroupCompensationAmtType ofAD_Ref_List_Value(@NonNull final String adRefListValue)
	{
		final GroupCompensationAmtType type = adRefListValue2type.get(adRefListValue);
		if (type == null)
		{
			throw new NoSuchElementException(adRefListValue);
		}
		return type;
	}

	private static final Map<String, GroupCompensationAmtType> adRefListValue2type = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(GroupCompensationAmtType::getAdRefListValue));

}
