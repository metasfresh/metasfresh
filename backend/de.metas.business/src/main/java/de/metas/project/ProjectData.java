/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.project;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.status.RStatusId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class ProjectData
{
	@NonNull
	OrgId orgId;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	String name;

	@NonNull
	String value;

	@Builder.Default
	boolean active = true;

	@Nullable
	BPartnerLocationId bPartnerLocationId;

	@Nullable
	PriceListVersionId priceListVersionId;

	@Nullable
	WarehouseId warehouseId;

	@Nullable
	BPartnerContactId contactId;

	@Nullable
	String description;

	@Nullable
	ProjectId projectParentId;

	@Nullable
	ProjectTypeId projectTypeId;

	@Nullable
	ProjectCategory projectCategory;

	@NonNull
	RequestStatusCategoryId requestStatusCategoryId;

	@Nullable
	RStatusId projectStatusId;

	@Nullable
	BPartnerId bPartnerId;

	@Nullable
	UserId salesRepId;

	@Nullable
	LocalDate dateContract;

	@Nullable
	LocalDate dateFinish;
}
