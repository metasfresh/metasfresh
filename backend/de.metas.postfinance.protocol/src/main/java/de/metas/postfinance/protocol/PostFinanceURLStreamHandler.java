/*
 * #%L
 * de.metas.postfinance.protocol
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

package de.metas.postfinance.protocol;

import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class PostFinanceURLStreamHandler extends URLStreamHandler
{
	final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private static final String SYS_CONFIG_SERVICE_URL = "de.metas.postfinance.PostFinanceURLStreamHandler.B2BServiceURL";
	private static final String SYS_CONFIG_SERVICE_URL_DEACTIVATED_VALUE = "-";

	@Override
	protected URLConnection openConnection(final URL u) throws IOException
	{
		//TODO: add SysConfig with value like file:/opt/metasfresh/config/B2BService.xml that can be mounted on the Server (local testing example: "file:/C:/work-metas/.testfiles/B2BService.xml")
		//TODO: add README to explain config with B2BService.xml on test and prod Servers

		final String externalURL = sysConfigBL.getValue(SYS_CONFIG_SERVICE_URL, SYS_CONFIG_SERVICE_URL_DEACTIVATED_VALUE);
		Check.assumeNotNull(externalURL, "SysConfig value shouldn't be null");
		if(!externalURL.equals(SYS_CONFIG_SERVICE_URL_DEACTIVATED_VALUE))
		{
			try
			{
				final URL externalServiceFile = new URL(externalURL);
				return externalServiceFile.openConnection();
			}
			catch (final MalformedURLException malformedURLException)
			{
				throw new AdempiereException("SysConfig de.metas.postfinance.PostFinanceURLStreamHandler.B2BServiceURL not set properly", malformedURLException); //TODO improve Message
			}
		}

		final URL localFallbackServiceFile = ClassLoader.getSystemClassLoader().getResource(u.getPath());
		Check.assumeNotNull(localFallbackServiceFile, "Fallback B2BService.xml should be present");
		return localFallbackServiceFile.openConnection();

	}
}
