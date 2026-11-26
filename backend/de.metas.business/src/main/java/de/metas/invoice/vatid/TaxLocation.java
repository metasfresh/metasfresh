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

package de.metas.invoice.vatid;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.sql.Timestamp;

/**
 * Where, by whom and when a line is taxed: the delivery partner location, the org, the tax date
 * and the ship-from country and warehouse the line's tax code was determined with.
 */
@Value
public class TaxLocation
{
	@NonNull BPartnerLocationId bpartnerLocationId;
	@NonNull OrgId orgId;
	@NonNull Timestamp taxDate;

	/**
	 * The ship-from country the line was taxed with; {@code null} = not known (a re-tax then keeps its default).
	 */
	@Nullable CountryId fromCountryId;

	/**
	 * The warehouse the line's tax code passed to the tax query (e.g. for the warehouse drop-ship rule); {@code null} = none.
	 */
	@Nullable WarehouseId warehouseId;

	public TaxLocation(
			@NonNull final BPartnerLocationId bpartnerLocationId,
			@NonNull final OrgId orgId,
			@NonNull final Timestamp taxDate,
			@Nullable final CountryId fromCountryId)
	{
		this(bpartnerLocationId, orgId, taxDate, fromCountryId, null);
	}

	public TaxLocation(
			@NonNull final BPartnerLocationId bpartnerLocationId,
			@NonNull final OrgId orgId,
			@NonNull final Timestamp taxDate,
			@Nullable final CountryId fromCountryId,
			@Nullable final WarehouseId warehouseId)
	{
		this.bpartnerLocationId = bpartnerLocationId;
		this.orgId = orgId;
		this.taxDate = taxDate;
		this.fromCountryId = fromCountryId;
		this.warehouseId = warehouseId;
	}
}
