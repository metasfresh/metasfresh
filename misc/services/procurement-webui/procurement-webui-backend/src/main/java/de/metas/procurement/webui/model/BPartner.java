package de.metas.procurement.webui.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import lombok.NonNull;

import static com.google.common.base.MoreObjects.*;



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

@Entity
@Table(name = "bpartner")
@SuppressWarnings("serial")
public class BPartner extends AbstractEntity
{
	public static final BPartner build(final String name)
	{
		final BPartner bpartner = new BPartner();
		bpartner.name = name;
		return bpartner;
	}

	@NonNull
	private String name;

	public BPartner()
	{
		super();
	}

	@Override
	protected void toString(final ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("name", name);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
