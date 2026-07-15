/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.externalsystem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.externalsystem.model.X_ExternalSystem_ScriptedExportConversion_Status;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum ExternalSystemExportStatus implements ReferenceListAwareEnum
{
	Pending(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Pending),
	Enqueued(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Enqueued),
	SendingStarted(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_SendingStarted),
	Sent(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Sent),
	Error(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Error),
	Invalid(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Invalid),
	DontSend(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_DontSend),
	;

	@NonNull private static final ReferenceListAwareEnums.ValuesIndex<ExternalSystemExportStatus> index = ReferenceListAwareEnums.index(values());

	public static final int AD_Reference_ID = X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_AD_Reference_ID;

	@NonNull private final String code;

	@JsonCreator
	@NonNull
	public static ExternalSystemExportStatus ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static ExternalSystemExportStatus ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	public static Optional<ExternalSystemExportStatus> optionalOfNullableCode(@Nullable final String code) {return index.optionalOfNullableCode(code);}

	@JsonValue
	public String toJson() {return code;}

	// ---- single-value predicates ----
	public boolean isPending() {return Pending == this;}
	public boolean isEnqueued() {return Enqueued == this;}
	public boolean isSendingStarted() {return SendingStarted == this;}
	public boolean isSent() {return Sent == this;}
	public boolean isError() {return Error == this;}
	public boolean isInvalid() {return Invalid == this;}
	public boolean isDontSend() {return DontSend == this;}

	// ---- compound predicates (mirrors EDIExportStatus) ----
	/** Enqueued OR SendingStarted */
	public boolean isProcessing() {return isEnqueued() || isSendingStarted();}

	/** Sent OR DontSend */
	public boolean isProcessed() {return isSent() || isDontSend();}

	/** Pending OR Error */
	public boolean isPendingOrError() {return isPending() || isError();}

	/** Error OR Invalid */
	public boolean isErrorOrInvalid() {return isError() || isInvalid();}

	/** Enqueued OR SendingStarted OR Sent */
	public boolean isInProgressOrSend() {return isEnqueued() || isSendingStarted() || isSent();}
}
