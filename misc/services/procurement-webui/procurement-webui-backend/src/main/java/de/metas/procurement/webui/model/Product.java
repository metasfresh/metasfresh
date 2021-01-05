package de.metas.procurement.webui.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;





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
@Table(name = "product")
@SuppressWarnings("serial")
public class Product extends AbstractEntity
{
	public static final Product build(final String name, final String packingInfo)
	{
		return new Product(name, packingInfo);
	}

	public static final String PROPERTYNAME_Id = "id";
	public static final Comparator<Product> COMPARATOR_Id = new Comparator<Product>()
	{
		@Override
		public int compare(final Product o1, final Product o2)
		{
			final Long id1 = Optional.fromNullable(o1.getId()).or(Long.valueOf(0));
			final Long id2 = Optional.fromNullable(o2.getId()).or(Long.valueOf(0));
			return id1.compareTo(id2);
		}
	};

	public static final String PROPERTYNAME_Name = "name";

	public static final Comparator<Product> comparatorByName(final Locale locale)
	{
		return new Comparator<Product>()
		{
			@Override
			public int compare(final Product o1, final Product o2)
			{
				final String name1 = o1 == null ? "" : o1.getName(locale);
				final String name2 = o2 == null ? "" : o2.getName(locale);
				return name1.compareTo(name2);
			}
		};
	}

	private String name;
	private String packingInfo;
	private boolean shared;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = ProductTrl.COLUMNNAME_Record)
	@MapKey(name = "language")
	private Map<String, ProductTrl> translations;

	public Product()
	{
		super();
	}

	public Product(final String name, final String packingInfo)
	{
		super();
		this.name = Preconditions.checkNotNull(name);
		this.packingInfo = packingInfo;
	}

	@Override
	protected void toString(final MoreObjects.ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("name", name)
				.add("packingInfo", packingInfo)
				.add("shared", shared);
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getName(final Locale locale)
	{
		final ProductTrl productTrl = TranslationHelper.getTranslation(translations, locale);
		if (productTrl != null)
		{
			return productTrl.getName();
		}

		return name;
	}

	public void setPackingInfo(final String packingInfo)
	{
		this.packingInfo = packingInfo;
	}

	public String getPackingInfo(final Locale locale)
	{
		// TODO: implement
		return packingInfo;
	}

	public void setShared(boolean shared)
	{
		this.shared = shared;
	}

	public boolean isShared()
	{
		return shared;
	}
}
