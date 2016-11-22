package de.metas.elasticsearch.denormalizers;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

/*
 * #%L
 * de.metas.business
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

public interface IESDenormalizer
{
	/**
	 * Denormalize given value.
	 * 
	 * @param value
	 * @return denormalized value in an Elasticsearch compatible format
	 */
	Object denormalize(Object value);

	/**
	 * 
	 * @param builder
	 * @param fieldName
	 *            <ul>
	 *            <li>field name on which the mapping shall be added
	 *            <li>or <code>null</code> in case the mapping is for ROOT (i.e. the index type).
	 *            </ul>
	 * @throws IOException
	 */
	void appendMapping(final XContentBuilder builder, final String fieldName) throws IOException;
}
