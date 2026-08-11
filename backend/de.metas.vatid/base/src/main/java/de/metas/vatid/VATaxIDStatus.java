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

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Status of a VAT-ID's online check via the VIES service ({@code AD_Reference_ID=542125}).
 *
 * <p>No {@code AD_Column} is wired to the backing {@code AD_Reference} yet, so this enum has no
 * generated {@code X_*} model class to source its codes from; the constants below are hand-kept and
 * must stay byte-for-byte identical to the {@code AD_Ref_List.Value} column of the corresponding
 * migration (see {@code 5818210_sys_VATaxIDStatus_ReferenceList.sql}).
 *
 * <p>{@link #hasTaxCertificate()} implements the "counts as holding a tax certificate" predicate from
 * the feature's requirements: every status keeps today's presence-only treatment except {@link #Invalid},
 * which is the only status an explicit negative VIES answer can produce.
 */
@RequiredArgsConstructor
@Getter
public enum VATaxIDStatus implements ReferenceListAwareEnum
{
	NotChecked(VATaxIDStatus.VALUE_NotChecked),
	RequestSent(VATaxIDStatus.VALUE_RequestSent),
	Valid(VATaxIDStatus.VALUE_Valid),
	Invalid(VATaxIDStatus.VALUE_Invalid),
	NotSupported(VATaxIDStatus.VALUE_NotSupported),
	ServiceUnavailable(VATaxIDStatus.VALUE_ServiceUnavailable),
	;

	// TODO model shall be generated: once an AD_Column is wired to AD_Reference_ID=542125, replace
	// these with the generated X_<Table>.VATAXIDSTATUS_* constants (must match AD_Ref_List.Value exactly).
	private static final String VALUE_NotChecked = "NotChecked";
	private static final String VALUE_RequestSent = "RequestSent";
	private static final String VALUE_Valid = "Valid";
	private static final String VALUE_Invalid = "Invalid";
	private static final String VALUE_NotSupported = "NotSupported";
	private static final String VALUE_ServiceUnavailable = "ServiceUnavailable";

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
