/*
 * #%L
 * de.metas.externalreference
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalreference;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExternalIdentifierTest
{
	@Test
	void ofOrNull_shouldReturnNull_whenInputIsNull()
	{
		// when
		final ExternalIdentifier result = ExternalIdentifier.ofOrNull(null);

		// then
		assertNull(result);
	}

	@Test
	void ofOrNull_shouldReturnNull_whenInputIsEmpty()
	{
		// when
		final ExternalIdentifier result = ExternalIdentifier.ofOrNull("");

		// then
		assertNull(result);
	}

	@Test
	void ofOrNull_shouldReturnNull_whenInputIsBlank()
	{
		// when
		final ExternalIdentifier result = ExternalIdentifier.ofOrNull("   ");

		// then
		assertNull(result);
	}

	@Test
	void ofOrNull_shouldReturnExternalIdentifier_whenInputIsMetasfreshId()
	{
		// given
		final String rawId = "12345";

		// when
		final ExternalIdentifier result = ExternalIdentifier.ofOrNull(rawId);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getType()).isEqualTo(ExternalIdentifier.Type.METASFRESH_ID);
		assertThat(result.getRawValue()).isEqualTo(rawId);
		assertThat(result.getExternalReferenceValueAndSystem()).isNull();
	}

	@Test
	void ofOrNull_shouldReturnExternalIdentifier_whenInputIsExternalReference()
	{
		// given
		final String rawId = "ext-system-value123";

		// when
		final ExternalIdentifier result = ExternalIdentifier.ofOrNull(rawId);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getType()).isEqualTo(ExternalIdentifier.Type.EXTERNAL_REFERENCE);
		assertThat(result.getRawValue()).isEqualTo(rawId);
		assertThat(result.getExternalReferenceValueAndSystem()).isNotNull();
		assertThat(result.getExternalReferenceValueAndSystem().getExternalSystem()).isEqualTo("system");
		assertThat(result.getExternalReferenceValueAndSystem().getValue()).isEqualTo("value123");
	}

	@Test
	void ofOrNull_shouldReturnExternalIdentifier_whenInputIsGLN()
	{
		// given
		final String rawId = "gln-1234567890123";

		// when
		final ExternalIdentifier result = ExternalIdentifier.ofOrNull(rawId);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getType()).isEqualTo(ExternalIdentifier.Type.GLN);
		assertThat(result.getRawValue()).isEqualTo(rawId);
		assertThat(result.getExternalReferenceValueAndSystem()).isNull();
	}

	@Test
	void ofOrNull_shouldReturnExternalIdentifier_whenInputIsGTIN()
	{
		// given
		final String rawId = "gtin-1234567890123";

		// when
		final ExternalIdentifier result = ExternalIdentifier.ofOrNull(rawId);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getType()).isEqualTo(ExternalIdentifier.Type.GTIN);
		assertThat(result.getRawValue()).isEqualTo(rawId);
		assertThat(result.getExternalReferenceValueAndSystem()).isNull();
	}

	@Test
	void ofOrNull_shouldReturnExternalIdentifier_whenInputIsValue()
	{
		// given
		final String rawId = "val-ABC123";

		// when
		final ExternalIdentifier result = ExternalIdentifier.ofOrNull(rawId);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getType()).isEqualTo(ExternalIdentifier.Type.VALUE);
		assertThat(result.getRawValue()).isEqualTo(rawId);
		assertThat(result.getExternalReferenceValueAndSystem()).isNull();
	}

	@Test
	void ofOrNull_shouldThrowException_whenInputHasUnknownFormat()
	{
		// given
		final String rawId = "unknown-format-id";

		// when/then
		assertThrows(AdempiereException.class, () -> ExternalIdentifier.ofOrNull(rawId));
	}
}
