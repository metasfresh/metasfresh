/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.snapshot.impl;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.BooleanWithReason;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

class CompositeSnapshotHandlers<ParentModelType>
{
	private final ImmutableList<AbstractSnapshotHandler<?, ?, ParentModelType>> handlers;

	@Builder
	private CompositeSnapshotHandlers(
			@Singular @NonNull final ImmutableList<AbstractSnapshotHandler<?, ?, ParentModelType>> handlers)
	{
		Check.assumeNotEmpty(handlers, "handlers");
		this.handlers = handlers;
	}

	public void restoreModelsFromSnapshotsByParent(final ParentModelType parentModel)
	{
		handlers.forEach(handler -> handler.restoreModelsFromSnapshotsByParent(parentModel));
	}

	public BooleanWithReason computeHasChangesByParent(@NonNull final ParentModelType parentModel)
	{
		return handlers.stream()
				.map(handler -> handler.computeHasChangesByParent(parentModel))
				.filter(BooleanWithReason::isTrue)
				.findFirst()
				.orElse(BooleanWithReason.FALSE);
	}

}
