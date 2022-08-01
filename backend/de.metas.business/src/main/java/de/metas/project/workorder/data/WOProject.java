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

package de.metas.project.workorder.data;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Value
public class WOProject
{
	@Nullable
	@Getter
	ProjectId projectId;

	@NonNull
	OrgId orgId;

	@Nullable
	CurrencyId currencyId;

	@Nullable
	String name;

	@Nullable
	String value;

	@Nullable
	Boolean isActive;

	@Nullable
	PriceListVersionId priceListVersionId;

	@Nullable
	String description;

	@Nullable
	ProjectId projectParentId;

	@Nullable
	ProjectTypeId projectTypeId;

	@Nullable
	String projectReferenceExt;

	@Nullable
	BPartnerId bPartnerId;

	@Nullable
	UserId salesRepId;

	@Nullable
	LocalDate dateContract;

	@Nullable
	LocalDate dateFinish;

	@Nullable
	String specialistConsultantId;

	@Nullable
	Instant dateOfProvisionByBPartner;

	@NonNull
	List<WOProjectStep> projectSteps;

	@NonNull
	List<WOProjectObjectUnderTest> projectObjectsUnderTest;

	@Builder
	public WOProject(
			@Nullable final ProjectId projectId,
			@NonNull final OrgId orgId,
			@Nullable final CurrencyId currencyId,
			@Nullable final String name,
			@Nullable final String value,
			@Nullable final Boolean isActive,
			@Nullable final PriceListVersionId priceListVersionId,
			@Nullable final String description,
			@Nullable final ProjectId projectParentId,
			@Nullable final ProjectTypeId projectTypeId,
			@Nullable final String projectReferenceExt,
			@Nullable final BPartnerId bPartnerId,
			@Nullable final UserId salesRepId,
			@Nullable final LocalDate dateContract,
			@Nullable final LocalDate dateFinish,
			@Nullable final String specialistConsultantId,
			@Nullable final Instant dateOfProvisionByBPartner,
			@Nullable final List<WOProjectStep> projectSteps,
			@Nullable final List<WOProjectObjectUnderTest> projectObjectsUnderTest)
	{
		this.projectId = projectId;
		this.orgId = orgId;
		this.currencyId = currencyId;
		this.name = name;
		this.value = value;
		this.isActive = isActive;
		this.priceListVersionId = priceListVersionId;
		this.description = description;
		this.projectParentId = projectParentId;
		this.projectTypeId = projectTypeId;
		this.projectReferenceExt = projectReferenceExt;
		this.bPartnerId = bPartnerId;
		this.salesRepId = salesRepId;
		this.dateContract = dateContract;
		this.dateFinish = dateFinish;
		this.specialistConsultantId = specialistConsultantId;
		this.dateOfProvisionByBPartner = dateOfProvisionByBPartner;
		this.projectSteps = CoalesceUtil.coalesce(projectSteps, ImmutableList.of());
		this.projectObjectsUnderTest = CoalesceUtil.coalesce(projectObjectsUnderTest, ImmutableList.of());
	}

	@NonNull
	public ProjectId getProjectIdNonNull()
	{
		if (projectId == null)
		{
			throw new AdempiereException("WOProjectStepId cannot be null at this stage!");
		}
		return projectId;
	}

	@NonNull
	public String getNameNonNull()
	{
		if (name == null)
		{
			throw new AdempiereException("WOProject Name property cannot be null at this stage!");
		}
		return name;
	}

	@NonNull
	public String getValueNonNull()
	{
		if (value == null)
		{
			throw new AdempiereException("WOProject Value property cannot be null at this stage!");
		}
		return value;
	}
}
