package org.compiere.util;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class MimeTypeTest
{
	@Test
	public void getMimeType()
	{
		assertThat(MimeType.getMimeType("report.pdf")).isEqualTo(MimeType.TYPE_PDF);
		assertThat(MimeType.getMimeType("report.txt")).isEqualTo(MimeType.TYPE_TextPlain);
		assertThat(MimeType.getMimeType("report.unknown-extension")).isEqualTo(MimeType.TYPE_BINARY);
		assertThat(MimeType.getMimeType("report")).isEqualTo(MimeType.TYPE_BINARY);
	}

	@Test
	public void getMediaType()
	{
		assertThat(MimeType.getMediaType("report.pdf")).isEqualTo(MediaType.APPLICATION_PDF);
		assertThat(MimeType.getMediaType("report.txt")).isEqualTo(MediaType.TEXT_PLAIN);
		assertThat(MimeType.getMediaType("report.unknown-extension")).isEqualTo(MediaType.APPLICATION_OCTET_STREAM);
		assertThat(MimeType.getMediaType("report")).isEqualTo(MediaType.APPLICATION_OCTET_STREAM);
	}

	@Test
	public void getExtensionByMimeType()
	{
		assertThat(MimeType.getExtensionByType(MimeType.TYPE_PDF)).isEqualTo(".pdf");
		assertThat(MimeType.getExtensionByType(MimeType.TYPE_TextPlain)).isEqualTo(".txt");
		assertThat(MimeType.getExtensionByType(MimeType.TYPE_IMAGE_JPEG)).isEqualTo(".jpg");
		assertThat(MimeType.getExtensionByType("some unknown MIME type")).isEmpty();
	}

	@Test
	public void getExtensionByTypeWithoutDot()
	{
		assertThat(MimeType.getExtensionByTypeWithoutDot(MimeType.TYPE_PDF)).isEqualTo("pdf");
		assertThat(MimeType.getExtensionByTypeWithoutDot(MimeType.TYPE_TextPlain)).isEqualTo("txt");
		assertThat(MimeType.getExtensionByTypeWithoutDot(MimeType.TYPE_IMAGE_JPEG)).isEqualTo("jpg");
		assertThat(MimeType.getExtensionByTypeWithoutDot("some unknown MIME type")).isEmpty();
	}
}
