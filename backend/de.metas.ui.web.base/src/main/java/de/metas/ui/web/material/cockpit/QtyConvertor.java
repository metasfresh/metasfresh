/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.material.cockpit;

import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.NonNull;

import javax.annotation.Nullable;

public interface QtyConvertor
{
	@NonNull
	static QtyConvertor getNoOp(@NonNull final UomId uomId)
	{
		return new QtyConvertor()
		{
			@Nullable
			@Override
			public Quantity convert(@Nullable final Quantity quantity)
			{
				return quantity;
			}

			@Override
			@NonNull
			public UomId getTargetUomId()
			{
				return uomId;
			}
		};
	}

	@Nullable
	Quantity convert(@Nullable final Quantity quantity);

	@NonNull
	UomId getTargetUomId();
}
