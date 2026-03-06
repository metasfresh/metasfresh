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

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ArchiveEmailSentStatus implements ReferenceListAwareEnum
{
	Success("Email_Success"), // X_C_Doc_Outbound_Log_Line.STATUS_Email_Success
	Failure("Email_Failure");// X_C_Doc_Outbound_Log_Line.STATUS_Email_Failure

	@Getter
	private final String code;

	public boolean isSuccess() {return Success.equals(this);}

	private static final ReferenceListAwareEnums.ValuesIndex<ArchiveEmailSentStatus> index = ReferenceListAwareEnums.index(values());

	public static ArchiveEmailSentStatus ofCode(@NonNull final String code) {return index.ofCode(code);}
}
