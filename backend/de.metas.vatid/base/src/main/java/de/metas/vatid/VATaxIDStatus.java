/*
 * #%L
 * de.metas.vatid
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.vatid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_VATaxID_CheckLog;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Status of a VAT-ID's online check via the VIES service ({@code AD_Reference_ID=542125}).
 *
 * <p>{@code AD_Column} 593175 ({@code VATaxID_CheckLog.VATaxIDStatus}) is wired to this
 * {@code AD_Reference}, so the codes below are sourced from the generated
 * {@code X_VATaxID_CheckLog.VATAXIDSTATUS_*} constants rather than hand-kept.
 *
 * <p>{@link #hasTaxCertificate()} implements the "counts as holding a tax certificate" predicate from
 * the feature's requirements: every status keeps today's presence-only treatment except {@link #Invalid},
 * which is the only status an explicit negative VIES answer can produce.
 */
@RequiredArgsConstructor
@Getter
public enum VATaxIDStatus implements ReferenceListAwareEnum
{
	NotChecked(X_VATaxID_CheckLog.VATAXIDSTATUS_NotChecked),
	RequestSent(X_VATaxID_CheckLog.VATAXIDSTATUS_RequestSent),
	Valid(X_VATaxID_CheckLog.VATAXIDSTATUS_Valid),
	Invalid(X_VATaxID_CheckLog.VATAXIDSTATUS_Invalid),
	NotSupported(X_VATaxID_CheckLog.VATAXIDSTATUS_NotSupported),
	ServiceUnavailable(X_VATaxID_CheckLog.VATAXIDSTATUS_ServiceUnavailable),
	;

	@NonNull private static final ReferenceListAwareEnums.ValuesIndex<VATaxIDStatus> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@JsonCreator
	@NonNull
	public static VATaxIDStatus ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static VATaxIDStatus ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	public static Optional<VATaxIDStatus> optionalOfNullableCode(@Nullable final String code) {return index.optionalOfNullableCode(code);}

	@JsonValue
	public String toJson() {return code;}

	/**
	 * "Counts as holding a tax certificate" per the feature's requirements: {@link #Invalid} is the only
	 * status for which this is {@code false} — only an explicit negative VIES answer removes the tax
	 * certificate; every other status (including {@link #NotSupported}, where VIES cannot answer at all)
	 * keeps today's presence-only treatment.
	 */
	public boolean hasTaxCertificate() {return this != Invalid;}
}
