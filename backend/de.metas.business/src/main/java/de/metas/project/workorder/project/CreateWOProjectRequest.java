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
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectType;
import de.metas.user.UserId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
public class CreateWOProjectRequest
{
	@NonNull
	OrgId orgId;

	@NonNull
	CurrencyId currencyId;

	@Nullable
	ExternalId externalId;
	
	@NonNull
	String name;

	@NonNull
	String value;

	@NonNull
	ProjectType projectType;

	boolean isActive;

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
	UserId specialistConsultantId;

	@Builder
	public CreateWOProjectRequest(
			@NonNull final OrgId orgId,
			@NonNull final CurrencyId currencyId,
			@Nullable final ExternalId externalId, 
			@NonNull final String name,
			@NonNull final String value,
			@NonNull final ProjectType projectType,
			@Nullable final Boolean isActive,
			@Nullable final PriceListVersionId priceListVersionId,
			@Nullable final String description,
			@Nullable final ProjectId projectParentId,
			@Nullable final String projectReferenceExt,
			@Nullable final BPartnerId bPartnerId,
			@Nullable final UserId salesRepId,
			@Nullable final Instant dateContract,
			@Nullable final Instant dateFinish,
			@Nullable final Instant dateOfProvisionByBPartner,
			@Nullable final String bpartnerDepartment,
			@Nullable final String woOwner,
			@Nullable final String poReference,
			@Nullable final Instant bpartnerTargetDate,
			@Nullable final Instant woProjectCreatedDate,
			@Nullable final UserId specialistConsultantId)
	{
		this.orgId = orgId;
		this.currencyId = currencyId;
		this.externalId = externalId;
		this.name = name;
		this.value = value;
		this.projectType = projectType;
		this.priceListVersionId = priceListVersionId;
		this.description = description;
		this.projectParentId = projectParentId;
		this.projectReferenceExt = projectReferenceExt;
		this.bPartnerId = bPartnerId;
		this.salesRepId = salesRepId;
		this.dateContract = dateContract;
		this.dateFinish = dateFinish;
		this.dateOfProvisionByBPartner = dateOfProvisionByBPartner;
		this.bpartnerDepartment = bpartnerDepartment;
		this.woOwner = woOwner;
		this.poReference = poReference;
		this.bpartnerTargetDate = bpartnerTargetDate;
		this.woProjectCreatedDate = woProjectCreatedDate;
		this.specialistConsultantId = specialistConsultantId;
		this.isActive = isActive == null || isActive;
	}
}
