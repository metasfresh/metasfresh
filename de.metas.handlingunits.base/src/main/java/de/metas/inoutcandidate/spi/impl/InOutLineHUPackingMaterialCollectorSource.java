package de.metas.inoutcandidate.spi.impl;

import de.metas.handlingunits.model.I_M_InOutLine;
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
public class InOutLineHUPackingMaterialCollectorSource implements IHUPackingMaterialCollectorSource
{
	public static InOutLineHUPackingMaterialCollectorSource of(final I_M_InOutLine inoutLine)
	{
		return builder()
				.inoutLine(inoutLine)
				.collectHUPipToSource(true)
				.build();
	}

	private final int productId;
	private final int recordId;
	private final I_M_InOutLine inoutLine;
	private final boolean collectHUPipToSource;

	@Builder
	private InOutLineHUPackingMaterialCollectorSource(@NonNull final I_M_InOutLine inoutLine, final boolean collectHUPipToSource)
	{
		productId = inoutLine.getM_Product_ID();
		recordId = inoutLine.getM_InOutLine_ID();
		this.inoutLine = inoutLine;
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

	public I_M_InOutLine getM_InOutLine()
	{
		return inoutLine;
	}
}
