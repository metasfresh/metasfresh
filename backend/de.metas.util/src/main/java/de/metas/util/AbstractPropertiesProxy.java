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


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * An {@link Properties} wrapper which delegates all calls to a delegate.
 * 
 * @author tsa
 * 
 */
public abstract class AbstractPropertiesProxy extends Properties
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2712889587568931719L;

	public AbstractPropertiesProxy()
	{
		// super() // no need to call, we are delegating everything
	}

	protected abstract Properties getDelegate();

	@Override
	public Object setProperty(String key, String value)
	{
		return getDelegate().setProperty(key, value);
	}

	@Override
	public void load(Reader reader) throws IOException
	{
		getDelegate().load(reader);
	}

	@Override
	public int size()
	{
		return getDelegate().size();
	}

	@Override
	public boolean isEmpty()
	{
		return getDelegate().isEmpty();
	}

	@Override
	public Enumeration<Object> keys()
	{
		return getDelegate().keys();
	}

	@Override
	public Enumeration<Object> elements()
	{
		return getDelegate().elements();
	}

	@Override
	public boolean contains(Object value)
	{
		return getDelegate().contains(value);
	}

	@Override
	public boolean containsValue(Object value)
	{
		return getDelegate().containsValue(value);
	}

	@Override
	public boolean containsKey(Object key)
	{
		return getDelegate().containsKey(key);
	}

	@Override
	public Object get(Object key)
	{
		return getDelegate().get(key);
	}

	@Override
	public void load(InputStream inStream) throws IOException
	{
		getDelegate().load(inStream);
	}

	@Override
	public Object put(Object key, Object value)
	{
		return getDelegate().put(key, value);
	}

	@Override
	public Object remove(Object key)
	{
		return getDelegate().remove(key);
	}

	@Override
	public void putAll(Map<? extends Object, ? extends Object> t)
	{
		getDelegate().putAll(t);
	}

	@Override
	public void clear()
	{
		getDelegate().clear();
	}

	@Override
	public Object clone()
	{
		return getDelegate().clone();
	}

	@Override
	public String toString()
	{
		return getDelegate().toString();
	}

	@Override
	public Set<Object> keySet()
	{
		return getDelegate().keySet();
	}

	@Override
	public Set<java.util.Map.Entry<Object, Object>> entrySet()
	{
		return getDelegate().entrySet();
	}

	@Override
	public Collection<Object> values()
	{
		return getDelegate().values();
	}

	@Override
	public boolean equals(Object o)
	{
		return getDelegate().equals(o);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void save(OutputStream out, String comments)
	{
		getDelegate().save(out, comments);
	}

	@Override
	public int hashCode()
	{
		return getDelegate().hashCode();
	}

	@Override
	public void store(Writer writer, String comments) throws IOException
	{
		getDelegate().store(writer, comments);
	}

	@Override
	public void store(OutputStream out, String comments) throws IOException
	{
		getDelegate().store(out, comments);
	}

	@Override
	public void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException
	{
		getDelegate().loadFromXML(in);
	}

	@Override
	public void storeToXML(OutputStream os, String comment) throws IOException
	{
		getDelegate().storeToXML(os, comment);
	}

	@Override
	public void storeToXML(OutputStream os, String comment, String encoding) throws IOException
	{
		getDelegate().storeToXML(os, comment, encoding);
	}

	@Override
	public String getProperty(String key)
	{
		return getDelegate().getProperty(key);
	}

	@Override
	public String getProperty(String key, String defaultValue)
	{
		return getDelegate().getProperty(key, defaultValue);
	}

	@Override
	public Enumeration<?> propertyNames()
	{
		return getDelegate().propertyNames();
	}

	@Override
	public Set<String> stringPropertyNames()
	{
		return getDelegate().stringPropertyNames();
	}

	@Override
	public void list(PrintStream out)
	{
		getDelegate().list(out);
	}

	@Override
	public void list(PrintWriter out)
	{
		getDelegate().list(out);
	}
}
