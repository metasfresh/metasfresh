/*
 * #%L
 * de.metas.workflow.rest-api
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workflow.rest_api.model;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocumentNoFilter;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetId;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
public class WorkflowLaunchersQuery
{
	@NonNull MobileApplicationId applicationId;
	@NonNull UserId userId;
	@Nullable ScannedCode filterByQRCode;
	@Nullable DocumentNoFilter filterByDocumentNo;
	boolean filterByQtyAvailableAtPickFromLocator;
	@Nullable ImmutableSet<WorkflowLaunchersFacetId> facetIds;

	@Nullable QueryLimit limit;
	@NonNull @Default @With Duration maxStaleAccepted = Duration.ZERO;

	public Optional<QueryLimit> getLimit() {return Optional.ofNullable(limit);}

	public void assertNoFilterByDocumentNo()
	{
		if (filterByDocumentNo != null)
		{
			throw new AdempiereException("Filtering by DocumentNo is not supported");
		}
	}

	public void assertNoFilterByQRCode()
	{
		if (filterByQRCode != null)
		{
			throw new AdempiereException("Filtering by QR Code is not supported");
		}
	}

	public void assertNoFacetIds()
	{
		if (facetIds != null && !facetIds.isEmpty())
		{
			throw new AdempiereException("Filtering by facets is not supported");
		}
	}
}
