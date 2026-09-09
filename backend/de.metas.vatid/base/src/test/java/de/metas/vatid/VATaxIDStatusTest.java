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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link VATaxIDStatus}.
 *
 * Covers: every enum value round-trips through its code (matching the six
 * {@code AD_Ref_List.Value} rows created by {@code 5818210_sys_VATaxIDStatus_ReferenceList.sql}), and
 * {@link VATaxIDStatus#hasTaxCertificate()} is {@code false} for {@link VATaxIDStatus#Invalid} only.
 */
class VATaxIDStatusTest
{
	@ParameterizedTest
	@EnumSource(VATaxIDStatus.class)
	void codeRoundTrips(final VATaxIDStatus status)
	{
		assertThat(VATaxIDStatus.ofCode(status.getCode())).isEqualTo(status);
	}

	@Test
	void everySixValuesHaveTheirContractCode()
	{
		assertThat(VATaxIDStatus.NotChecked.getCode()).isEqualTo("NotChecked");
		assertThat(VATaxIDStatus.RequestSent.getCode()).isEqualTo("RequestSent");
		assertThat(VATaxIDStatus.Valid.getCode()).isEqualTo("Valid");
		assertThat(VATaxIDStatus.Invalid.getCode()).isEqualTo("Invalid");
		assertThat(VATaxIDStatus.NotSupported.getCode()).isEqualTo("NotSupported");
		assertThat(VATaxIDStatus.ServiceUnavailable.getCode()).isEqualTo("ServiceUnavailable");
	}

	@Test
	void invalid_isTheOnlyStatusWithoutTaxCertificate()
	{
		for (final VATaxIDStatus status : VATaxIDStatus.values())
		{
			final boolean expected = status != VATaxIDStatus.Invalid;
			assertThat(status.hasTaxCertificate()).as("hasTaxCertificate() for %s", status).isEqualTo(expected);
		}
	}

	@Test
	void invalid_hasNoTaxCertificate()
	{
		assertThat(VATaxIDStatus.Invalid.hasTaxCertificate()).isFalse();
	}

	@ParameterizedTest
	@EnumSource(value = VATaxIDStatus.class, names = "Invalid", mode = EnumSource.Mode.EXCLUDE)
	void everyOtherStatus_hasTaxCertificate(final VATaxIDStatus status)
	{
		assertThat(status.hasTaxCertificate()).isTrue();
	}
}
