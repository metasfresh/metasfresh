/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.order.compensationGroup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.ImmutableMap;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.X_C_Order_CompensationGroup;

import javax.annotation.Nullable;
import java.util.Comparator;

@AllArgsConstructor
public enum GroupCompensationOrderBy implements ReferenceListAwareEnum
{
	CompensationGroupFirst(X_C_Order_CompensationGroup.COMPENSATIONGROUPORDERBY_CompensationGroupFirst),
	CompensationGroupLast(X_C_Order_CompensationGroup.COMPENSATIONGROUPORDERBY_CompensationGroupLast),
	;

	@Getter
	String code;

	@JsonCreator
	@NonNull
	public static GroupCompensationOrderBy ofCode(@NonNull final String code)
	{
		final GroupCompensationOrderBy orderBy = typesByCode.get(code);
		if (orderBy == null)
		{
			throw new AdempiereException("No " + GroupCompensationOrderBy.class + " found for code: " + code);
		}

		return orderBy;
	}

	@Nullable
	public static GroupCompensationOrderBy ofCodeOrNull(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return null;
		}

		return ofCode(code);
	}

	private static final ImmutableMap<String, GroupCompensationOrderBy> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	@NonNull
	public Comparator<I_C_OrderLine> getComparator()
	{
		if (this == CompensationGroupFirst)
		{
			return (line1, line2) -> Boolean.compare(line2.isGroupCompensationLine(), line1.isGroupCompensationLine());
		}
		else
		{
			return (line1, line2) -> Boolean.compare(line1.isGroupCompensationLine(), line2.isGroupCompensationLine());
		}
	}
}
