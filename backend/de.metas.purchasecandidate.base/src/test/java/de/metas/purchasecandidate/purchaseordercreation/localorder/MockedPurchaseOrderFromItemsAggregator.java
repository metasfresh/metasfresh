package de.metas.purchasecandidate.purchaseordercreation.localorder;

import java.util.ArrayList;
import java.util.Iterator;

import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.util.collections.MapReduceAggregator;
import lombok.Getter;
import lombok.Value;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class MockedPurchaseOrderFromItemsAggregator extends PurchaseOrderFromItemsAggregator
{
	@Getter
	private final ArrayList<AddAllCall> addAllCalls = new ArrayList<>();

	@Override
	public MapReduceAggregator<PurchaseOrderFromItemFactory, PurchaseOrderItem> addAll(final Iterator<PurchaseOrderItem> items)
	{
		addAllCalls.add(AddAllCall.of(items));
		return this;
	}

	@Value(staticConstructor = "of")
	public static class AddAllCall
	{
		private Iterator<PurchaseOrderItem> items;
	}
}
