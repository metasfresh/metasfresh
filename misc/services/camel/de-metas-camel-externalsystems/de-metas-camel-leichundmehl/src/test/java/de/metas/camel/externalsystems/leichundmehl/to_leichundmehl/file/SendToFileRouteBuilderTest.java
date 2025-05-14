/*
 * #%L
 * de-metas-camel-leichundmehl
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.file;

import de.metas.common.util.time.SystemTime;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SendToFileRouteBuilderTest {

	@Test
	void extractFilename_with_ext()
	{
		SystemTime.setFixedTimeSource("2024-12-12T13:13:13+01:00[Europe/Berlin]");
		final String result = SendToFileRouteBuilder.extractFilename("file.name.PLU");
		assertThat(result).isEqualTo("file.name_1734005593000.PLU");
		SystemTime.resetTimeSource();
	}

	@Test
	void extractFilename_without_ext()
	{
		SystemTime.setFixedTimeSource("2024-12-12T13:13:13+01:00[Europe/Berlin]");
		final String result = SendToFileRouteBuilder.extractFilename("filename");
		assertThat(result).isEqualTo("filename_1734005593000");
		SystemTime.resetTimeSource();
	}
}