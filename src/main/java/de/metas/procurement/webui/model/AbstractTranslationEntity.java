package de.metas.procurement.webui.model;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.google.gwt.thirdparty.guava.common.base.Objects.ToStringHelper;

/*
 * #%L
 * de.metas.procurement.webui
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

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class AbstractTranslationEntity<T extends AbstractEntity> extends AbstractEntity
{
	public static final String COLUMNNAME_Record = "record";
	@ManyToOne
	@NotNull
	private T record;

	public static final String COLUMNNAME_Language = "language";
	@NotNull
	private String language;

	@Override
	protected void toString(final ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("language", language)
				.add("record", record);
	}

	public T getRecord()
	{
		return record;
	}

	public void setRecord(T record)
	{
		this.record = record;
	}

	public final String getLanguage()
	{
		return language;
	}

	public final void setLanguage(final String language)
	{
		this.language = language;
	}
}
