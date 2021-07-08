package de.metas.organization;

import java.time.ZoneId;

import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.calendar.CalendarId;
import de.metas.pricing.PricingSystemId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import de.metas.workflow.WFResponsibleId;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Builder
@Value
public class OrgInfo
{
	@NonNull
	ClientId clientId;
	@NonNull
	OrgId orgId;

	OrgId parentOrgId;

	UserId supervisorId;
	CalendarId calendarId;
	PricingSystemId pricingSystemId;

	WarehouseId warehouseId;
	WarehouseId purchaseWarehouseId;
	WarehouseId dropShipWarehouseId;

	@NonNull
	StoreCreditCardNumberMode storeCreditCardNumberMode;

	int logoImageId;
	@Nullable
	WFResponsibleId workflowResponsibleId;
	BPartnerLocationId orgBPartnerLocationId;
	String reportsPathPrefix;
	ZoneId timeZone;
}
