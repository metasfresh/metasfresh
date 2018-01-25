package de.metas.handlingunits.movement.spi.impl;

import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

@EqualsAndHashCode(of= {"productId", "recordId"})
public class MovementLineHUPackingMaterialCollectorSource implements IHUPackingMaterialCollectorSource
{
	public static final MovementLineHUPackingMaterialCollectorSource of(final I_M_MovementLine movementLine)
	{
		return builder()
				.movementLine(movementLine)
				.collectHUPipToSource(true)
				.build();
	}

	private final int productId;
	private final int recordId;
	private final boolean collectHUPipToSource;

	@Builder
	private MovementLineHUPackingMaterialCollectorSource(@NonNull final I_M_MovementLine movementLine, final boolean collectHUPipToSource)
	{
		productId = movementLine.getM_Product_ID();
		recordId = movementLine.getM_MovementLine_ID();
		this.collectHUPipToSource = collectHUPipToSource;
	}

	@Override
	public int getM_Product_ID()
	{
		return productId;
	}

	@Override
	public int getRecord_ID()
	{
		return recordId;
	}

	@Override
	public boolean isCollectHUPipToSource()
	{
		return collectHUPipToSource;
	}
}
