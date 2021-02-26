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

package org.adempiere.archive.api;

import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

public enum ArchiveAction implements ReferenceListAwareEnum
{
	PRINT("print"), // X_C_Doc_Outbound_Log_Line.ACTION_Print
	EMAIL("eMail"), // X_C_Doc_Outbound_Log_Line.ACTION_EMail
	PDF_EXPORT("pdf"), // X_C_Doc_Outbound_Log_Line.ACTION_PdfExport
	ATTACHMENT_STORED("attachmentStored"); // X_C_Doc_Outbound_Log_Line.ACTION_AttachmentStored

	private static final ReferenceListAwareEnums.ValuesIndex<ArchiveAction> index = ReferenceListAwareEnums.index(values());

	@Getter
	String code;

	private ArchiveAction(@NonNull final String code)
	{
		Check.assumeNotEmpty(code, "code not empty");
		this.code = code;
	}

	public static ArchiveAction ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
}
