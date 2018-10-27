package de.metas.util;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
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


import org.junit.Assert;
import org.junit.Test;

import de.metas.util.FileUtils;

public class FileUtilsTest
{
	@Test
	public void test_changeFileExtension_Common() throws Exception
	{
		Assert.assertEquals("test.pdf", FileUtils.changeFileExtension("test.json", "pdf"));
		Assert.assertEquals("test-no-extension.pdf", FileUtils.changeFileExtension("test-no-extension", "pdf"));
		Assert.assertEquals("test", FileUtils.changeFileExtension("test.json", null));
		Assert.assertEquals("test", FileUtils.changeFileExtension("test.json", ""));
		Assert.assertEquals("test", FileUtils.changeFileExtension("test.json", "    "));
		Assert.assertEquals(".test.pdf", FileUtils.changeFileExtension(".test", "pdf"));
	}

	@Test
	public void test_changeFileExtension_ExtensionStartsWithDot()
	{
		Assert.assertEquals(
				"@PREFIX@de/metas/reports/pricelist/report_TabularView.jrxml",
				FileUtils.changeFileExtension("@PREFIX@de/metas/reports/pricelist/report_TabularView.jasper", ".jrxml"));
	}

	@Test
	public void test_getFileExtension() throws Exception
	{
		Assert.assertEquals("pdf", FileUtils.getFileExtension("test.pdf"));
		Assert.assertEquals("pdf", FileUtils.getFileExtension("test.json.pdf"));
		Assert.assertEquals(null, FileUtils.getFileExtension("test"));
	}

}
