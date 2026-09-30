/*
 * #%L
 * de-metas-camel-scriptedadapter
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

package de.metas.camel.externalsystems.scriptedadapter.filename;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImportFileNameResolverTest
{
	@Test
	void resolve_blankPattern_returnsIncomingNameUnchanged()
	{
		assertThat(ImportFileNameResolver.resolve("", "259-1000218.pdf")).isEqualTo("259-1000218.pdf");
		assertThat(ImportFileNameResolver.resolve(null, "259-1000218.pdf")).isEqualTo("259-1000218.pdf");
	}

	@Test
	void resolve_filenameAndTimestampPlaceholders_keepsSourceExtension()
	{
		final String result = ImportFileNameResolver.resolve("{filename}_{timestamp}", "259-1000218.pdf");
		assertThat(result).matches("^259-1000218_\\d{8}_\\d{6}\\.pdf$");
	}

	@Test
	void resolve_patternAlreadyEndsWithSourceExtension_doesNotDoubleIt()
	{
		final String result = ImportFileNameResolver.resolve("{filename}.PDF", "259-1000218.pdf");
		assertThat(result).isEqualTo("259-1000218.PDF");
	}

	@Test
	void resolve_extensionlessSource_yieldsExtensionlessResult()
	{
		final String result = ImportFileNameResolver.resolve("{filename}_{timestamp}", "259-1000218");
		assertThat(result).matches("^259-1000218_\\d{8}_\\d{6}$");
	}

	@Test
	void resolve_multipleDotsInSource_splitsOnLastDotOnly()
	{
		final String result = ImportFileNameResolver.resolve("{filename}_{timestamp}", "259-1000218.v2.pdf");
		assertThat(result).matches("^259-1000218\\.v2_\\d{8}_\\d{6}\\.pdf$");
	}
}
