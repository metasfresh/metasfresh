package de.metas.procurement.webui.model;

import com.google.common.base.MoreObjects;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;





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
public class Product extends AbstractEntity
{
	public static Product build(final String name, final String packingInfo)
	{
		return new Product(name, packingInfo);
	}

	public static final Comparator<Product> COMPARATOR_Id = (o1, o2) -> {
		final Long id1 = o1.getId() != null ? o1.getId() : 0;
		final Long id2 = o2.getId() != null ? o2.getId() : 0;
		return id1.compareTo(id2);
	};

	private String name;
	@Nullable
	private String packingInfo;
	private boolean shared;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = ProductTrl.COLUMNNAME_Record)
	@MapKey(name = "language")
	private Map<String, ProductTrl> translations;

	public Product()
	{
	}

	public Product(@NonNull final String name, @Nullable final String packingInfo)
	{
		this.name = name;
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

	public void setPackingInfo(@Nullable final String packingInfo)
	{
		this.packingInfo = packingInfo;
	}

	@Nullable
	public String getPackingInfo(final Locale locale)
	{
		return packingInfo;
	}

	public void setShared(final boolean shared)
	{
		this.shared = shared;
	}

	public boolean isShared()
	{
		return shared;
	}
}
