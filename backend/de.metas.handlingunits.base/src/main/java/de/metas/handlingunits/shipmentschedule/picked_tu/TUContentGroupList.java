package de.metas.handlingunits.shipmentschedule.picked_tu;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import com.google.common.collect.ImmutableList;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.model.I_C_UOM;

import java.util.List;

/**
 * The content of a TU: its {@link TUContentGroup}s, in the order the TU lists its CUs, which follows pick order.
 * May be empty (no CU of the product is left to hand out).
 */
@EqualsAndHashCode
@ToString
public final class TUContentGroupList
{
	public static final TUContentGroupList EMPTY = new TUContentGroupList(ImmutableList.of());

	private final ImmutableList<TUContentGroup> groups;

	private TUContentGroupList(@NonNull final ImmutableList<TUContentGroup> groups)
	{
		// the groups are added up and handed out against each other, so they must count in one UOM: the stocking UOM of the product
		// whose CUs they are made of (TUContentCU.ofStorage)
		Check.assume(groups.stream().map(group -> group.stockingUOM().getC_UOM_ID()).distinct().count() <= 1,
				"All content groups of a TU are in the same UOM: {}", groups);
		this.groups = groups;
	}

	public static TUContentGroupList of(@NonNull final List<TUContentGroup> groups)
	{
		return groups.isEmpty() ? EMPTY : new TUContentGroupList(ImmutableList.copyOf(groups));
	}

	public int size()
	{
		return groups.size();
	}

	public boolean isEmpty()
	{
		return groups.isEmpty();
	}

	public TUContentGroup get(final int index)
	{
		return groups.get(index);
	}

	public boolean hasSeveralGroups()
	{
		return groups.size() > 1;
	}

	/**
	 * @return the quantity of all CUs of all groups; the list must not be empty
	 */
	public Quantity totalQty()
	{
		return groups.stream().map(TUContentGroup::totalQty).reduce(Quantity::add).get();
	}

	/**
	 * @return the stocking UOM of the content's product; the list must not be empty
	 */
	public I_C_UOM stockingUOM()
	{
		return groups.get(0).stockingUOM();
	}
}
