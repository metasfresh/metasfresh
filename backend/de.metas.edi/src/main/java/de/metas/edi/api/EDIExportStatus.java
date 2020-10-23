/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api;

import de.metas.esb.edi.model.X_EDI_Desadv;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

public enum EDIExportStatus implements ReferenceListAwareEnum
{
	Invalid(X_EDI_Desadv.EDI_EXPORTSTATUS_Invalid),
	Pending(X_EDI_Desadv.EDI_EXPORTSTATUS_Pending),
	Sent(X_EDI_Desadv.EDI_EXPORTSTATUS_Sent),
	SendingStarted(X_EDI_Desadv.EDI_EXPORTSTATUS_SendingStarted),
	Error(X_EDI_Desadv.EDI_EXPORTSTATUS_Error),
	Enqueued(X_EDI_Desadv.EDI_EXPORTSTATUS_Enqueued),
	DontSend(X_EDI_Desadv.EDI_EXPORTSTATUS_DontSend),
	;
	@Getter
	private final String code;

	EDIExportStatus(final String code)
	{
		this.code = code;
	}

	public static EDIExportStatus ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static EDIExportStatus ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<EDIExportStatus> index = ReferenceListAwareEnums.index(values());

	public static final int AD_Reference_ID = X_EDI_Desadv.EDI_EXPORTSTATUS_AD_Reference_ID;
}
