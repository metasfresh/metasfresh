package de.metas.elasticsearch.types;

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

/**
 * Elasticsearch mapping data type.
 *
 * See https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-types.html
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public enum ESDataType
{
	// String
	String("string")

	// Numeric datatypes
	, Long("long"), Integer("integer"), Short("short"), Byte("byte"), Double("double"), Float("float")

	// Date datatype
	, Date("date")

	// Boolean datatype
	, Boolean("boolean")

	// Binary datatype
	, Binary("binary")

	// Object datatype - for single JSON objects
	, Object("object")

	//
	;

	private final String esType;

	ESDataType(final String esType)
	{
		this.esType = esType;
	}

	public String getEsTypeAsString()
	{
		return esType;
	}
}
