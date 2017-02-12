package org.compiere.util;

import org.junit.Assert;
import org.junit.Test;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MimeTypeTest
{
	@Test
	public void test_getMimeType()
	{
		test_getMimeType(MimeType.TYPE_PDF, "report.pdf");
		test_getMimeType(MimeType.TYPE_TextPlain, "report.txt");
		test_getMimeType(MimeType.TYPE_BINARY, "report.unknown-extension");
	}
	
	private static final void test_getMimeType(final String expectedMimeType, final String fileName)
	{
		final String actualMimeType = MimeType.getMimeType(fileName);
		Assert.assertEquals("Invalid result for fileName="+fileName, expectedMimeType, actualMimeType);
	}
	
	@Test
	public void test_getExtensionByMimeType()
	{
		test_getExtensionByMimeType(".pdf", MimeType.TYPE_PDF);
		test_getExtensionByMimeType(".txt", MimeType.TYPE_TextPlain);
	}
	
	private void test_getExtensionByMimeType(final String expectedExt, final String mimeType)
	{
		String actualExt = MimeType.getExtensionByType(mimeType);
		Assert.assertEquals("Invalid result for mimeType="+mimeType, expectedExt, actualExt);
	}

}
