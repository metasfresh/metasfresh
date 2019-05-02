package de.metas.dataentry.rest_api.dto;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.layout.DataEntryListValue;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class JsonDataEntryField
{
	DataEntryFieldId id;

	String caption;
	String description;

	FieldType type;
	boolean mandatory;

	/** empty, unless type=list */
	List<DataEntryListValue> listValues;

	Object value;
}
