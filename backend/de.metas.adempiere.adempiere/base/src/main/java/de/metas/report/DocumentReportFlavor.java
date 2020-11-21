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

package de.metas.report;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_DocType_PrintOptions;

import javax.annotation.Nullable;

public enum DocumentReportFlavor implements ReferenceListAwareEnum
{
	EMAIL(X_C_DocType_PrintOptions.DOCUMENTFLAVOR_EMail),
	PRINT(X_C_DocType_PrintOptions.DOCUMENTFLAVOR_Print);

	private static final ReferenceListAwareEnums.ValuesIndex<DocumentReportFlavor> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	DocumentReportFlavor(@NonNull final String code)
	{
		this.code = code;
	}

	public static DocumentReportFlavor ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static DocumentReportFlavor ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	@Nullable
	public static String toCode(@Nullable final DocumentReportFlavor flavor)
	{
		return flavor != null ? flavor.getCode() : null;
	}
}
