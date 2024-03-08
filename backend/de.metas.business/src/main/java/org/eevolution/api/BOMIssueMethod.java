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

package org.eevolution.api;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.eevolution.model.X_PP_Product_BOMLine;

import javax.annotation.Nullable;

public enum BOMIssueMethod implements ReferenceListAwareEnum
{
	Master(X_PP_Product_BOMLine.ISSUEMETHOD_Issue), //
	Engineering(X_PP_Product_BOMLine.ISSUEMETHOD_Backflush), //
	Manufacturing(X_PP_Product_BOMLine.ISSUEMETHOD_FloorStock), //
	Planning(X_PP_Product_BOMLine.ISSUEMETHOD_IssueOnlyForReceived), //
	;

	@Getter
	private final String code;

	BOMIssueMethod(@NonNull final String code)
	{
		this.code = code;
	}

	@NonNull
	public static BOMIssueMethod ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@NonNull
	public static BOMIssueMethod ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<BOMIssueMethod> index = ReferenceListAwareEnums.index(values());
}
