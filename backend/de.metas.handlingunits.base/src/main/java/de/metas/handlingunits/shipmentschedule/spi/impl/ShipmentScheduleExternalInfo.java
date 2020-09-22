/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.shipmentschedule.spi.impl;

import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class ShipmentScheduleExternalInfo
{
	@Nullable
	String documentNo;

	@Nullable
	List<PackageInfo> packageInfoList;

	@Builder
	public ShipmentScheduleExternalInfo(@Nullable final String documentNo, @Nullable final List<PackageInfo> packageInfoList)
	{
		if (Check.isBlank(documentNo) && Check.isEmpty(packageInfoList))
		{
			throw new AdempiereException("Empty ShipmentScheduleExternalInfo are not allowed!");
		}

		this.documentNo = documentNo;
		this.packageInfoList = packageInfoList;
	}
}
