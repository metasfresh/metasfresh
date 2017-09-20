/**
 * 
 */
package org.compiere.apps.form;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.apache.commons.io.FileUtils;
import org.compiere.apps.form.fileimport.FileImportReader;
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

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testMultipleLinesFieldFile()
	{
		final URL url = getClass().getResource("/multiplelines.csv");
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
		}
		catch (IOException e)
		{
			logger.warn(e.getMessage());
		}
	}
	
	@Test
	public void testRegularLinesFieldFile()
	{
		final URL url = getClass().getResource("/regularlines.csv");
		Assert.assertNotNull("url null", url);
		final File file = FileUtils.toFile(url);
		Assert.assertNotNull("file null", file);

		final Charset charset = Charset.forName("UTF-8");
		try
		{
			final List<String> lines = FileImportReader.readMultiLines(file, charset);

			Assert.assertNotNull("lines null", lines);
			Assert.assertFalse(lines.isEmpty());
			Assert.assertTrue(lines.size() == 3);
		}
		catch (IOException e)
		{
			logger.warn(e.getMessage());
		}
	}
}
