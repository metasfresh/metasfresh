/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.i18n;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

@Value
@Builder
public class ADMessageAndParams
{
	public static ADMessageAndParams of(@NonNull final AdMessageKey adMessage, @Nullable final Object... params)
	{
		final List<Object> paramsList = params != null && params.length > 0
				? Arrays.asList(params)
				: ImmutableList.of();

		return new ADMessageAndParams(adMessage, paramsList);
	}

	@NonNull
	AdMessageKey adMessage;
	@Singular
	List<Object> params;
}
