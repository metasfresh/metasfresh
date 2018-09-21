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


import java.util.Properties;

/**
 * Mutable implementation of {@link AbstractPropertiesProxy} where you can set the delegated {@link Properties} by using {@link #setDelegate(Properties)}.
 * 
 * @author tsa
 * 
 */
public class PropertiesProxy extends AbstractPropertiesProxy
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6024673003929192592L;
	private Properties delegate = null;

	public PropertiesProxy(final Properties delegate)
	{
		super();
		setDelegate(delegate);
	}

	@Override
	protected Properties getDelegate()
	{
		return delegate;
	}

	public void setDelegate(final Properties delegate)
	{
		Check.assumeNotNull(delegate, "delegate not null");
		this.delegate = delegate;
	}

}
