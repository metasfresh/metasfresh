/*
 * #%L
 * de-metas-common-filemaker
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.filemaker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Value;

@Value
public class FMPXMLRESULT
{
	@JacksonXmlProperty(localName = "ERRORCODE")
	String errorCode;

	@JacksonXmlProperty(localName = "PRODUCT")
	PRODUCT product;

	@JacksonXmlProperty(localName = "DATABASE")
	DATABASE database;

	@JacksonXmlProperty(localName = "METADATA")
	METADATA metadata;

	@JacksonXmlProperty(localName = "RESULTSET")
	RESULTSET resultset;

	@Builder
	@JsonCreator
	public FMPXMLRESULT(
			@JacksonXmlProperty(localName = "ERRORCODE") final String errorCode,
			@JacksonXmlProperty(localName = "PRODUCT") final PRODUCT product,
			@JacksonXmlProperty(localName = "DATABASE") final DATABASE database,
			@JacksonXmlProperty(localName = "METADATA") final METADATA metadata,
			@JacksonXmlProperty(localName = "RESULTSET") final RESULTSET resultset)
	{
		this.errorCode = errorCode;
		this.product = product;
		this.database = database;
		this.metadata = metadata;
		this.resultset = resultset;
	}

	@JsonIgnore
	public boolean isEmpty()
	{
		return metadata == null
				|| metadata.getFields() == null
				|| metadata.getFields().isEmpty()
				|| resultset == null
				|| resultset.getRows() == null
				|| resultset.getRows().isEmpty();
	}
}
