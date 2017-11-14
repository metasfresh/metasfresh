package de.metas.material.dispo.client.repository;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.util.Check;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
public class AvailableStockResult
{
	private final List<Group> groups;

	@Builder
	private AvailableStockResult(@Singular final List<Group> groups)
	{
		Check.assumeNotEmpty(groups, "groups is not empty");
		this.groups = groups;
	}

	private Group getSingleGroup()
	{
		if (groups.size() > 1)
		{
			throw new AdempiereException("Not a single group: " + this);
		}
		return groups.get(0);
	}

	public Quantity getSingleQuantity()
	{
		return getSingleGroup().getQty();
	}

	@Value
	public static class Group
	{
		private final int productId;
		private final ImmutableAttributeSet attributes;
		private final Quantity qty;

		@Builder
		public Group(
				final int productId,
				@Nullable final IAttributeSet attributes,
				@NonNull final Quantity qty)
		{
			Check.assume(productId > 0, "productId > 0");
			
			this.productId = productId;
			this.attributes = attributes != null ? ImmutableAttributeSet.copyOf(attributes) : ImmutableAttributeSet.EMPTY;
			this.qty = qty;
		}
	}
}
