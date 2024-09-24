/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.doctype;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_DocType;

import javax.annotation.Nullable;

public enum CopyDocumentNote implements ReferenceListAwareEnum
{
	CopuDocumentNote(X_C_DocType.COPYDOCUMENTNOTE_CopyDocumentNoteToDocument),
	CopyDocumentNoteFromOrder(X_C_DocType.COPYDOCUMENTNOTE_CopyDocumentNoteFromOrder);

	@Getter
	private final String code;

	CopyDocumentNote(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static CopyDocumentNote ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public static CopyDocumentNote ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<CopyDocumentNote> index = ReferenceListAwareEnums.index(values());

	public boolean isCopyDocumentNoteFromOrder()
	{
		return this == CopyDocumentNoteFromOrder;
	}
}
