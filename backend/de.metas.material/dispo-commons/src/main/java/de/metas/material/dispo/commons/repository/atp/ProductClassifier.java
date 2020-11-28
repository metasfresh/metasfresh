package de.metas.material.dispo.commons.repository.atp;

import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

@EqualsAndHashCode(doNotUseGetters = true)
public final class ProductClassifier
{
	public static ProductClassifier any()
	{
		return ANY;
	}

	public static ProductClassifier specific(final int productId)
	{
		Check.assumeGreaterThanZero(productId, "productId");
		return new ProductClassifier(productId);
	}

	private static final ProductClassifier ANY = new ProductClassifier(-1);

	private int productId;

	private ProductClassifier(final int productId)
	{
		this.productId = productId > 0 ? productId : -1;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(productId > 0 ? productId : "ANY")
				.toString();
	}

	public boolean isMatching(@Nullable final int productId)
	{
		return this.productId <= 0
				|| this.productId == productId;
	}

	public boolean isAny()
	{
		return this.productId <= 0;
	}

	public int getProductId()
	{
		return productId;
	}
}
