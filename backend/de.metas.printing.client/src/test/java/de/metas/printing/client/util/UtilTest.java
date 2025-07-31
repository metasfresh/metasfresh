package de.metas.printing.client.util;

/*
 * #%L
 * de.metas.printing.esb.client
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilTest
{
	@Test
	public void test_changeFileExtension() throws Exception
	{
		Assertions.assertEquals("test.pdf", Util.changeFileExtension("test.json", "pdf"));
		Assertions.assertEquals("test-no-extension.pdf", Util.changeFileExtension("test-no-extension", "pdf"));
		Assertions.assertEquals("test", Util.changeFileExtension("test.json", null));
		Assertions.assertEquals("test", Util.changeFileExtension("test.json", ""));
		Assertions.assertEquals("test", Util.changeFileExtension("test.json", "    "));
		Assertions.assertEquals(".test.pdf", Util.changeFileExtension(".test", "pdf"));
	}

	@Test
	public void test_getFileExtension() throws Exception
	{
		Assertions.assertEquals("pdf", Util.getFileExtension("test.pdf"));
		Assertions.assertEquals("pdf", Util.getFileExtension("test.json.pdf"));
		Assertions.assertEquals(null, Util.getFileExtension("test"));
	}
}
