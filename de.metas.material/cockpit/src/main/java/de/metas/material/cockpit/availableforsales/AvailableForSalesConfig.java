package de.metas.material.cockpit.availableforsales;

import javax.annotation.Nullable;

import de.metas.util.Check;
import de.metas.util.ColorId;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class AvailableForSalesConfig
{
	boolean featureEnabled;

	ColorId insufficientQtyAvailableForSalesColorId;

	int shipmentDateLookAheadHours;

	int salesOrderLookBehindHours;

	@Builder
	private AvailableForSalesConfig(
			@Nullable ColorId insufficientQtyAvailableForSalesColorId,
			int shipmentDateLookAheadHours,
			int salesOrderLookBehindHours,
			boolean featureEnabled)
	{
		this.featureEnabled = featureEnabled;
		this.insufficientQtyAvailableForSalesColorId = insufficientQtyAvailableForSalesColorId;
		this.shipmentDateLookAheadHours = shipmentDateLookAheadHours;
		this.salesOrderLookBehindHours = salesOrderLookBehindHours;

		if (featureEnabled)
		{
			Check.assumeNotNull(insufficientQtyAvailableForSalesColorId, "If the feature is enabled, then insufficientQtyAvailableForSalesColorId may not be null");
			Check.assume(shipmentDateLookAheadHours >= 0, "If the feature is enabled, then shipmentDateLookAheadHours={} may not be < 0", shipmentDateLookAheadHours);
			Check.assume(salesOrderLookBehindHours >= 0, "If the feature is enabled, then shipmentDateLookAheadHours={} may not be < 0", salesOrderLookBehindHours);
		}
	}

}
