package org.adempiere.util.net;

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


import java.net.InetAddress;
import java.net.UnknownHostException;

public final class NetUtils
{
	private NetUtils()
	{
	}

	/**
	 * 
	 * @return never returns <code>null</code>
	 */
	public static IHostIdentifier getLocalHost()
	{
		InetAddress lh;
		try
		{
			lh = InetAddress.getLocalHost();
		}
		catch (UnknownHostException e)
		{
			// fallback
			lh = InetAddress.getLoopbackAddress();
		}
		
		final String hostAddress = lh.getHostAddress();
		final String hostName = lh.getHostName();

		return new IHostIdentifier()
		{
			@Override
			public String getIP()
			{
				return hostAddress;
			}

			@Override
			public String getHostName()
			{
				return hostName;
			}

			@Override
			public String toString()
			{
				return ((hostName != null) ? hostName : "") + "/" + hostAddress;
			};
		};
	}

}
