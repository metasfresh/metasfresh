/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.leichmehl;

import de.metas.externalsystem.model.X_LeichMehl_PluFile_Config;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

public enum ReplacementSource implements ReferenceListAwareEnum
{
	Product(X_LeichMehl_PluFile_Config.REPLACEMENTSOURCE_Product),
	PPOrder(X_LeichMehl_PluFile_Config.REPLACEMENTSOURCE_PPOrder),
	BOMLine(X_LeichMehl_PluFile_Config.REPLACEMENTSOURCE_BOMLine);

	@Getter
	private final String code;

	ReplacementSource(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static ReplacementSource ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	@NonNull
	public static ReplacementSource ofCode(@NonNull final String code)
	{
		final ReplacementSource type = index.ofCode(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ReplacementSource.class + " found for code: " + code);
		}
		return type;
	}

	private static final ReferenceListAwareEnums.ValuesIndex<ReplacementSource> index = ReferenceListAwareEnums.index(values());
}
