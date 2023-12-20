package de.metas.handlingunits.inventory.draftlinescreator;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class ShortageAndOverageStrategy implements HUsForInventoryStrategy
{
	IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@NonNull
	HuForInventoryLineFactory huForInventoryLineFactory;
	@NonNull
	I_M_HU huRecord;
	@NonNull
	BigDecimal shortageOverage;

	@Builder
	private ShortageAndOverageStrategy(
			@NonNull final HuForInventoryLineFactory huForInventoryLineFactory,
			@NonNull final I_M_HU huRecord,
			@NonNull final BigDecimal shortageOverage)
	{
		this.huForInventoryLineFactory = huForInventoryLineFactory;
		this.huRecord = huRecord;
		this.shortageOverage = shortageOverage;
	}

	@Override
	public Stream<HuForInventoryLine> streamHus()
	{
		return huForInventoryLineFactory.ofHURecordWithQtyAdjustment(huRecord, shortageOverage);
	}
}
