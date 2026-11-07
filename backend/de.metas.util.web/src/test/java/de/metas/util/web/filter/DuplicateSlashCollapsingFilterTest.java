/*
 * #%L
 * de.metas.util.web
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

package de.metas.util.web.filter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DuplicateSlashCollapsingFilterTest
{
	@Test
	void collapseDuplicateSlashes()
	{
		assertThat(DuplicateSlashCollapsingFilter.collapseDuplicateSlashes("/api//v2///version/")).isEqualTo("/api/v2/version/");
		assertThat(DuplicateSlashCollapsingFilter.collapseDuplicateSlashes("/api/v2/version")).isEqualTo("/api/v2/version");
		assertThat(DuplicateSlashCollapsingFilter.collapseDuplicateSlashes(null)).isNull();
	}

	@Test
	void collapseDuplicateSlashes_leavesEncodedSlashesAlone()
	{
		assertThat(DuplicateSlashCollapsingFilter.collapseDuplicateSlashes("/api//v2/x/%2F%2F/y")).isEqualTo("/api/v2/x/%2F%2F/y");
	}

	@Test
	void collapseDuplicateSlashesInURL_keepsSchemeSeparator()
	{
		assertThat(DuplicateSlashCollapsingFilter.collapseDuplicateSlashesInURL(new StringBuffer("https://host:8080/api//v2//version")).toString())
				.isEqualTo("https://host:8080/api/v2/version");
		assertThat(DuplicateSlashCollapsingFilter.collapseDuplicateSlashesInURL(new StringBuffer("https://host")).toString())
				.isEqualTo("https://host");
	}
}
