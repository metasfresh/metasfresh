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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SftpFilenameResolverTest
{
	@Test
	void resolve_withDocumentnoAndTimestamp()
	{
		final String pattern = "DESADV_{documentno}_{timestamp}.json";
		final Map<String, String> variables = Map.of("documentno", "12345");
		final String result = SftpFilenameResolver.resolve(pattern, variables);
		// {timestamp} should be replaced with current timestamp in yyyyMMdd_HHmmss format
		assertThat(result).startsWith("DESADV_12345_").endsWith(".json");
		assertThat(result).doesNotContain("{documentno}").doesNotContain("{timestamp}");
	}

	@Test
	void resolve_withNoPlaceholders()
	{
		final String result = SftpFilenameResolver.resolve("fixed-name.json", Map.of());
		assertThat(result).isEqualTo("fixed-name.json");
	}

	@Test
	void resolve_withUnknownPlaceholder_leavesItAsIs()
	{
		final String result = SftpFilenameResolver.resolve("file_{unknown}.json", Map.of());
		assertThat(result).isEqualTo("file_{unknown}.json");
	}
}
