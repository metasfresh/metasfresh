/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoice.service;

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.location.CountryId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

/**
 * The delivery location a sales invoice line was taxed with when it was created, with the ship-from country and the warehouse its tax code used.
 */
@Value
public class TaxLocationAndFromCountry
{
	@NonNull BPartnerLocationAndCaptureId location;

	/**
	 * {@code null} = not known; the re-tax then uses its default ship-from country.
	 */
	@Nullable CountryId fromCountryId;

	/**
	 * {@code null} = the tax code passed no warehouse.
	 */
	@Nullable WarehouseId warehouseId;
}
