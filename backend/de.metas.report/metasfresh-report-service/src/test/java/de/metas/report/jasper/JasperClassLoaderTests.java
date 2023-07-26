package de.metas.report.jasper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Test;
/*
 * #%L
 * de.metas.report.jasper.server.base
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

import de.metas.report.jasper.JasperClassLoader;

public class JasperClassLoaderTests
{
	@Test
	public void isJarInJarURL() throws Exception
	{
		assertTrue(JasperClassLoader.isJarInJarURL(new URL("file:///opt/metasfresh/metasfresh-server.jar!/lib/spring-beans-4.2.5.RELEASE.jar")));
		assertTrue(JasperClassLoader.isJarInJarURL(
				new URL("jar:file:/opt/metasfresh/metasfresh_server.jar!/lib/spring-beans-4.2.5.RELEASE.jar!/org/springframework/beans/factory/xml/spring-beans-2.0.xsd")));
		assertFalse(JasperClassLoader.isJarInJarURL(new URL("file:///opt/metasfresh/reports/de/metas/docs/direct_costing/report.jasper")));
	}
}
