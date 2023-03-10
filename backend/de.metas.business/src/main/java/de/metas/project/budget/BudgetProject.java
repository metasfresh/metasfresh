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

package de.metas.project.budget;

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.user.UserId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
public class BudgetProject
{
	@NonNull
	ProjectId projectId;

	@Nullable
	ExternalId externalId;
	
	@NonNull
	String name;

	@NonNull
	OrgId orgId;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	ProjectTypeId projectTypeId;

	@NonNull
	String value;

	@Builder.Default
	boolean isActive = true;

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
	LocalDate dateContract;

	@Nullable
	LocalDate dateFinish;

	@Nullable
	String bpartnerDepartment;

	@Nullable
	UserId specialistConsultantID;

	@Nullable
	InternalPriority internalPriority;

	@NonNull
	public Optional<String> getExternalIdAsString()
	{
		return Optional.ofNullable(externalId)
				.map(ExternalId::getValue);
	}
}
