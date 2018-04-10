/**
 * 
 */
package org.compiere.apps.form.fileimport;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.apache.commons.io.FileUtils;
import org.compiere.impexp.FileImportReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class FileImportReadingTest
{
	private static final transient Logger logger = LogManager.getLogger(FileImportReadingTest.class);
	private static final String packagePath = "/org/compiere/apps/form/fileimport";

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testMultipleLinesFieldFile()
	{
		final URL url = getClass().getResource(packagePath + "/multiplelines.csv");
		Assert.assertNotNull("url null", url);
		final File file = FileUtils.toFile(url);
		Assert.assertNotNull("file null", file);

		//
		final Charset charset = Charset.forName("UTF-8");
		try
		{
			final List<String> lines = FileImportReader.readMultiLines(file, charset);

			Assert.assertNotNull("lines null", lines);
			Assert.assertFalse(lines.isEmpty());
			Assert.assertTrue(lines.size() == 2);
			lines.forEach(l -> Assert.assertTrue(l.startsWith("G00")));
			Assert.assertTrue(lines.get(0).endsWith("70"));
			Assert.assertTrue(lines.get(1).endsWith("80"));
		}
		catch (IOException e)
		{
			logger.warn(e.getMessage());
		}
	}

	@Test
	public void testRegularLinesFieldFile() throws IOException
	{
		final URL url = getClass().getResource(packagePath + "/regularlines.csv");
		Assert.assertNotNull("url null", url);
		final File file = FileUtils.toFile(url);
		Assert.assertNotNull("file null", file);

		final Charset charset = Charset.forName("UTF-8");
		final List<String> lines = FileImportReader.readRegularLines(file, charset);

		Assert.assertNotNull("lines null", lines);
		Assert.assertFalse(lines.isEmpty());
		Assert.assertTrue(lines.size() == 3);
		lines.forEach(l -> Assert.assertTrue(l.startsWith("G00")));
		Assert.assertTrue(lines.get(0).endsWith("80"));
		Assert.assertTrue(lines.get(1).endsWith("90"));
		Assert.assertTrue(lines.get(2).endsWith("100"));
	}
}
