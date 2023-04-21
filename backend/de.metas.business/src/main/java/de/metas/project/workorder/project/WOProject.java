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

package de.metas.project.workorder.project;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.status.RStatusId;
import de.metas.user.UserId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
public class WOProject
{
	@NonNull
	ProjectId projectId;

	@NonNull
	OrgId orgId;

	@NonNull
	ClientId clientId;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	String name;

	@NonNull
	String value;

	@NonNull
	ProjectTypeId projectTypeId;

	@NonNull
	Boolean isActive;

	@Nullable
	ExternalId externalId;

	@Nullable
	PriceListVersionId priceListVersionId;

	@Nullable
	String description;

	@Nullable
	ProjectId projectParentId;

	@Nullable
	String projectReferenceExt;

	@Nullable
	BPartnerId bPartnerId;

	@Nullable
	UserId salesRepId;

	@Nullable
	Instant dateContract;

	@Nullable
	Instant dateFinish;

	@Nullable
	Instant dateOfProvisionByBPartner;

	@Nullable
	String bpartnerDepartment;

	@Nullable
	String woOwner;

	@Nullable
	String poReference;

	@Nullable
	Instant bpartnerTargetDate;

	@Nullable
	Instant woProjectCreatedDate;

	@Nullable
	UserId specialistConsultantID;

	@Nullable
	InternalPriority internalPriority;

	@Nullable
	RStatusId statusId;

	@NonNull
	public Optional<CalendarDateRange> getCalendarDateRange()
	{
		if (dateContract == null || dateFinish == null)
		{
			return Optional.empty();
		}

		return Optional.of(CalendarDateRange.builder()
								   .startDate(dateContract)
								   .endDate(dateFinish)
								   .build());
	}

	@NonNull
	public Optional<String> getExternalIdAsString()
	{
		return Optional.ofNullable(externalId)
				.map(ExternalId::getValue);
	}
}
