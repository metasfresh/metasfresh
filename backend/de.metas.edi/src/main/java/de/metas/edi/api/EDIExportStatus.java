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

import com.google.common.collect.ImmutableSet;
import de.metas.esb.edi.model.X_EDI_Desadv;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@Getter
@RequiredArgsConstructor
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

	private static final ImmutableSet<EDIExportStatus> SENT_OR_IN_PROGRESS = ImmutableSet.of(Enqueued, SendingStarted, Sent);

	private final String code;

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

	public boolean isPendingOrError() {return isPending() || isError();}
	public boolean isErrorOrInvalid() {return isError() || isInvalid();}
	public boolean isPending() {return Pending == this;}
	public boolean isError() {return Error == this;}
	public boolean isInvalid() {return Invalid == this;}
	public boolean isSent() {return Sent == this;}
	public boolean isDontSend() {return DontSend == this;}
	public boolean isEnqueued() {return Enqueued == this;}
	public boolean isSendingStarted() {return SendingStarted == this;}
	public boolean isInProgressOrSend() {return isInProgressOrSend(this);}
	public static boolean isInProgressOrSend(@Nullable final EDIExportStatus ediExportStatus){return SENT_OR_IN_PROGRESS.contains(ediExportStatus);}
	public boolean isProcessing() {return isSendingStarted() || isEnqueued();}
	public boolean isProcessed() {return isSent() || isDontSend();}
}
