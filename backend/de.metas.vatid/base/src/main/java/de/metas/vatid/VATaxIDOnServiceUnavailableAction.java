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
import org.compiere.model.X_VATaxID_Config;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * {@code VATaxID_Config.OnServiceUnavailable} ({@code AD_Reference_ID=542126}): what a VAT-ID's status
 * becomes once VIES (or the issuing member state) cannot be reached <em>and</em> the last successful
 * check is older than the configured {@code RecheckAfterDays}.
 *
 * <p>This is deliberately a fail-open/fail-closed choice, not a raw flag: {@link #ServiceUnavailable} is
 * fail-open (applying it produces {@link VATaxIDStatus#ServiceUnavailable}, which keeps the VAT-ID
 * counting as holding a tax certificate), while {@link #Invalid} is fail-closed (produces
 * {@link VATaxIDStatus#Invalid}, no tax certificate). {@link #toVATaxIDStatus()} is the single mapping
 * from this configuration choice to the resulting status; the tax-certificate predicate itself stays
 * owned by {@link VATaxIDStatus#hasTaxCertificate()} — callers needing it call
 * {@code action.toVATaxIDStatus().hasTaxCertificate()} rather than a second copy of that logic here.
 */
@RequiredArgsConstructor
@Getter
public enum VATaxIDOnServiceUnavailableAction implements ReferenceListAwareEnum
{
	ServiceUnavailable(X_VATaxID_Config.ONSERVICEUNAVAILABLE_ServiceUnavailable),
	Invalid(X_VATaxID_Config.ONSERVICEUNAVAILABLE_Invalid),
	;

	@NonNull private static final ReferenceListAwareEnums.ValuesIndex<VATaxIDOnServiceUnavailableAction> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@JsonCreator
	@NonNull
	public static VATaxIDOnServiceUnavailableAction ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static VATaxIDOnServiceUnavailableAction ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	public static Optional<VATaxIDOnServiceUnavailableAction> optionalOfNullableCode(@Nullable final String code) {return index.optionalOfNullableCode(code);}

	@JsonValue
	public String toJson() {return code;}

	/**
	 * The {@link VATaxIDStatus} this configuration choice produces once applied; see the class javadoc.
	 */
	@NonNull
	public VATaxIDStatus toVATaxIDStatus() {return this == ServiceUnavailable ? VATaxIDStatus.ServiceUnavailable : VATaxIDStatus.Invalid;}
}
