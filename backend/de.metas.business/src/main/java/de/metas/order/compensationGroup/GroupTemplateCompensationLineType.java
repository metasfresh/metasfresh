/*
 * #%L
 * de.metas.business
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

package de.metas.order.compensationGroup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.order.model.X_C_CompensationGroup_SchemaLine;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode
public class GroupTemplateCompensationLineType
{
	public static GroupTemplateCompensationLineType REVENUE_BREAKS = new GroupTemplateCompensationLineType(X_C_CompensationGroup_SchemaLine.TYPE_Revenue);
	public static GroupTemplateCompensationLineType CONTRACT = new GroupTemplateCompensationLineType(X_C_CompensationGroup_SchemaLine.TYPE_Flatrate);

	private static final ConcurrentHashMap<String, GroupTemplateCompensationLineType> typesByCode = new ConcurrentHashMap<>();

	static
	{
		typesByCode.put(REVENUE_BREAKS.getCode(), REVENUE_BREAKS);
		typesByCode.put(CONTRACT.getCode(), CONTRACT);
	}

	@JsonCreator
	public static GroupTemplateCompensationLineType ofCode(@NonNull final String code)
	{
		final GroupTemplateCompensationLineType type = ofNullableCode(code);
		if (type == null)
		{
			throw new AdempiereException("Invalid code: `" + code + "`");
		}

		return type;
	}

	@Nullable
	public static GroupTemplateCompensationLineType ofNullableCode(@Nullable final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		return codeNorm != null
				? typesByCode.computeIfAbsent(code, GroupTemplateCompensationLineType::new)
				: null;
	}

	private final String code;

	private GroupTemplateCompensationLineType(@NonNull final String code) {this.code = code;}

	@Override
	@Deprecated
	public String toString()
	{
		return getCode();
	}

	@JsonValue
	public String getCode()
	{
		return code;
	}
}
