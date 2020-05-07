package de.metas.data.export.api;

/*
 * #%L
 * de.metas.swat.base
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


import java.io.Closeable;
import java.util.Iterator;
import java.util.List;

/**
 * Export Data Source
 * 
 * @author tsa
 * 
 */
public interface IExportDataSource extends Iterator<List<Object>>, Closeable
{
	/**
	 * Data row's field names.
	 * 
	 * NOTE: returned list will have the same size as the row list returned by {@link #next()}.
	 * 
	 * @return list of field names
	 */
	List<String> getFieldNames();

	/**
	 * Method to be called before actual retrieving methods. When implementing this method, please keep in mind:
	 * <ul>
	 * <li>the only purpose of this method is to be called synchronously right before the actual export
	 * <li>if the method was not called, the actual retrieve methods will call it
	 * <li>this method can be called more then one time, but only the first time it will actually do the work
	 * </ul>
	 */
	void prepare();

	@Override
	boolean hasNext();

	/**
	 * Retrieve next data row
	 */
	@Override
	List<Object> next();

	/**
	 * @throws UnsupportedOperationException always because removing is not supported
	 */
	@Override
	void remove();

	/**
	 * Close data source
	 */
	@Override
	void close();

	/**
	 * @return number of records in this data source
	 */
	int size();
}
