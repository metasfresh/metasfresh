package de.metas.ui.web.upload;

import de.metas.common.util.time.SystemTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class WebuiImageServiceTest
{
	@BeforeEach
	public void beforeEach()
	{
		SystemTime.resetTimeSource();
	}

	@AfterEach
	public void afterEach()
	{
		de.metas.common.util.time.SystemTime.resetTimeSource();
	}

	@Nested
	public class normalizeUploadFilename
	{
		@Test
		public void nullName()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2020-10-20T21:22:23+01:00");
			assertThat(WebuiImageService.normalizeUploadFilename(null, "image/png"))
					.isEqualTo("2020-10-20_212223.png");
		}

		@Test
		public void blobName()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2020-10-20T21:22:23+01:00");
			assertThat(WebuiImageService.normalizeUploadFilename("blob", "image/png"))
					.isEqualTo("2020-10-20_212223.png");
		}

		@Test
		public void regularName()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2020-10-20T21:22:23+01:00");
			assertThat(WebuiImageService.normalizeUploadFilename("some_name", "image/png"))
					.isEqualTo("some_name.png");
		}

		@Test
		public void regularNameWithExtension()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2020-10-20T21:22:23+01:00");
			assertThat(WebuiImageService.normalizeUploadFilename("some_name.ext", "image/png"))
					.isEqualTo("some_name.png");
		}
	}
}
