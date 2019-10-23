package de.metas.dataentry;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public enum FieldType
{
	SUB_TAB_ID(Integer.class),
	PARENT_LINK_ID(Integer.class),

	CREATED_UPDATED_INFO(String.class),

	TEXT(String.class),

	LONG_TEXT(String.class),

	NUMBER(BigDecimal.class),

	DATE(LocalDate.class),

	LIST(DataEntryListValueId.class),

	YESNO(Boolean.class);

	@Getter
	private final Class<?> clazz;

	private FieldType(Class<?> clazz)
	{
		this.clazz = clazz;
	}
}
