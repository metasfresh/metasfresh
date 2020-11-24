package de.metas.ui.web.material.adapter;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.common.util.CoalesceUtil;
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
public class AvailableToPromiseResultForWebui
{
	private final List<Group> groups;

	@Builder
	private AvailableToPromiseResultForWebui(@Singular final List<Group> groups)
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
		public enum Type
		{
			ATTRIBUTE_SET, OTHER_STORAGE_KEYS, ALL_STORAGE_KEYS
		}

		ProductId productId;
		Quantity qty;
		Type type;

		ImmutableAttributeSet attributes;

		@Builder
		public Group(
				@NonNull final Group.Type type,
				@NonNull final ProductId productId,
				@NonNull final Quantity qty,
				@Nullable final ImmutableAttributeSet attributes)
		{
			this.type = type;
			this.productId = productId;
			this.qty = qty;
			this.attributes = CoalesceUtil.coalesce(attributes, ImmutableAttributeSet.EMPTY);
		}

	}

}
