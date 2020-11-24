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

import de.metas.util.FileUtil;

public class FileUtilTest
{
	@Test
	public void test_changeFileExtension_Common() throws Exception
	{
		Assert.assertEquals("test.pdf", FileUtil.changeFileExtension("test.json", "pdf"));
		Assert.assertEquals("test-no-extension.pdf", FileUtil.changeFileExtension("test-no-extension", "pdf"));
		Assert.assertEquals("test", FileUtil.changeFileExtension("test.json", null));
		Assert.assertEquals("test", FileUtil.changeFileExtension("test.json", ""));
		Assert.assertEquals("test", FileUtil.changeFileExtension("test.json", "    "));
		Assert.assertEquals(".test.pdf", FileUtil.changeFileExtension(".test", "pdf"));
	}

	@Test
	public void test_changeFileExtension_ExtensionStartsWithDot()
	{
		Assert.assertEquals(
				"@PREFIX@de/metas/reports/pricelist/report_TabularView.jrxml",
				FileUtil.changeFileExtension("@PREFIX@de/metas/reports/pricelist/report_TabularView.jasper", ".jrxml"));
	}

	@Test
	public void test_getFileExtension() throws Exception
	{
		Assert.assertEquals("pdf", FileUtil.getFileExtension("test.pdf"));
		Assert.assertEquals("pdf", FileUtil.getFileExtension("test.json.pdf"));
		Assert.assertEquals(null, FileUtil.getFileExtension("test"));
	}

}
