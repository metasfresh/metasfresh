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

package de.metas.ui.web.handlingunits.report;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
<<<<<<< HEAD
=======
import de.metas.handlingunits.HuUnitType;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import lombok.NonNull;
>>>>>>> 205807b4eda (Show Process M_HU_Report_QRCode in HU Editor only on active HUs (#18503))
import org.adempiere.exceptions.AdempiereException;

import java.util.stream.Stream;

public interface HUReportAwareViewRow
{
	String getHUUnitTypeOrNull();

	default String getHUUnitType()
	{
		final String unitType = getHUUnitTypeOrNull();
		if (unitType == null)
		{
			throw new AdempiereException("Cannot get HU_UnitType from " + this);
		}
		return unitType;
	}

	HuId getHuId();

	BPartnerId getBpartnerId();

	boolean isTopLevel();

	Stream<HUReportAwareViewRow> streamIncludedHUReportAwareRows();

	default boolean applies(@NonNull final ProcessDescriptor processDescriptor)
	{
		return true;
	}
}
