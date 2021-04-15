/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.picking.packageable.filters;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.service.ClientId;

import java.util.Optional;

/**
 * Gets HU by SSCC18 attribute and matches rows which have HU's products
 * <p>
 * Interesting detail: currently only matches HUs that have a C_BPartner set
 *
 * @author tsa
 * Task http://dewiki908/mediawiki/index.php/06821_Kommissionier_Terminal_Extension_%28104171338645%29
 */
class SSCC18ProductBarcodeFilterDataFactory implements ProductBarcodeFilterDataFactory
{
	@Override
	public Optional<ProductBarcodeFilterData> createData(
			final @NonNull ProductBarcodeFilterServicesFacade services,
			final @NonNull String barcode,
			final @NonNull ClientId clientId)
	{
		Check.assumeNotEmpty(barcode, "barcode not empty");
		final String sscc18 = barcode.trim();

		final I_M_HU hu = services.getHUBySSCC18(sscc18).orElse(null);
		return services.createDataFromHU(sscc18, hu);
	}
}
