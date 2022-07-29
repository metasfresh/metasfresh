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

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
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

	@Singular
	List<WOProjectStep> projectSteps;

	List<WOProjectObjectUnderTest> projectObjectsUnderTest;

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
