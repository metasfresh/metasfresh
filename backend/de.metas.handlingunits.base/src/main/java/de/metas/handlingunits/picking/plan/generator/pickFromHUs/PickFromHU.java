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

package de.metas.handlingunits.picking.plan.generator.pickFromHUs;

import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
public class PickFromHU
{
	@NonNull HuId topLevelHUId;
	@With
	boolean huReservedForThisLine;
	@NonNull LocatorId locatorId;

	//
	// Relevant HU Attributes:
	@NonNull String huCode;
	@Nullable LocalDate expiringDate;
	@Nullable String serialNo;
	@Nullable String lotNumber;
	@Nullable String repackNumber;

	@With
	@NonNull AlternativePickFromKeys alternatives;

	@Builder
	private PickFromHU(
			@NonNull final HuId topLevelHUId,
			final boolean huReservedForThisLine,
			@NonNull final LocatorId locatorId,
			@Nullable final String huCode,
			@Nullable final LocalDate expiringDate,
			@Nullable final String serialNo,
			@Nullable final String lotNumber,
			@Nullable final String repackNumber,
			@Nullable final AlternativePickFromKeys alternatives)
	{
		this.topLevelHUId = topLevelHUId;
		this.huReservedForThisLine = huReservedForThisLine;
		this.locatorId = locatorId;
		this.huCode = huCode != null ? huCode : topLevelHUId.toHUValue();
		this.expiringDate = expiringDate;
		this.serialNo = serialNo;
		this.lotNumber = lotNumber;
		this.repackNumber = repackNumber;
		this.alternatives = alternatives != null ? alternatives : AlternativePickFromKeys.EMPTY;
	}

	public PickFromHU withHuReservedForThisLine()
	{
		return withHuReservedForThisLine(true);
	}
}
