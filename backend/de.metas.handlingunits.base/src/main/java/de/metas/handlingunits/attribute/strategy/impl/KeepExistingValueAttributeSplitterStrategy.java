package de.metas.handlingunits.attribute.strategy.impl;

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

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitRequest;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitResult;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.util.Check;
import lombok.NoArgsConstructor;

/**
 * TOPD splitter that propagates the parent's value to a child only when the child has no value yet.
 * If the child already carries a value it is left unchanged.
 *
 * Designed for categorical attributes like Country of Origin where each TU may hold a distinct
 * batch with its own origin. A mixed-origin LU must not silently overwrite per-TU origins.
 */
@NoArgsConstructor
public final class KeepExistingValueAttributeSplitterStrategy implements IAttributeSplitterStrategy
{

	@Override
	public IAttributeSplitResult split(final IAttributeSplitRequest request)
	{
		final IAttributeStorage childStorage = request.getAttributeStorageCurrent();
		final Object childValue = childStorage.getValue(request.getM_Attribute());

		final Object splitValue = Check.isEmpty(childValue)
				? request.getValueToSplit()  // child has no value yet — propagate from parent
				: childValue;                // child already has a value — keep it

		return new AttributeSplitResult(splitValue, request.getValueToSplit());
	}

	@Override
	public Object recalculateRemainingValue(final IAttributeSplitResult result, final Object valueSet)
	{
		return result.getRemainingValue();
	}
}
