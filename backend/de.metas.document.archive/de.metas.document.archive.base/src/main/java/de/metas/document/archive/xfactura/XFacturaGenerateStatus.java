/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.document.archive.xfactura;

import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ReferenceId;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
@AllArgsConstructor
public enum XFacturaGenerateStatus implements ReferenceListAwareEnum
{
	OK("O"),
	ERROR("E"),
	DO_NOT_GENERATE("D"),
	GENERATE("G"),
	NOT_GENERATED("N");

	private static final ReferenceListAwareEnums.ValuesIndex<XFacturaGenerateStatus> index = ReferenceListAwareEnums.index(values());
	public static final ReferenceId AD_Reference_ID = ReferenceId.ofRepoId(1); //TODO add refList

	@NonNull
	private final String code;

	@NonNull
	public static XFacturaGenerateStatus ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@NonNull
	public List<XFacturaGenerateStatus> getAvailableXFacturaGenerateStatuses()
	{
		switch (this)
		{
			case ERROR:
				return ImmutableList.of(NOT_GENERATED, DO_NOT_GENERATE);
			case DO_NOT_GENERATE:
				return ImmutableList.of(NOT_GENERATED);
			default:
				return ImmutableList.of();
		}
	}
}
