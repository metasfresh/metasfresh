/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.issue;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.serviceprovider.effortcontrol.EffortTarget;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.milestone.MilestoneId;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Data
@Builder(toBuilder = true)
public class IssueEntity
{
	@Nullable
	private IssueId issueId;

	@Nullable
	private UserId assigneeId;

	@Nullable
	private ExternalProjectReferenceId externalProjectReferenceId;

	@Nullable
	private ProjectId projectId;

	@NonNull
	private OrgId orgId;

	@NonNull
	private ClientId clientId;

	@Nullable
	private MilestoneId milestoneId;

	@NonNull
	private UomId effortUomId;

	@Nullable
	private IssueId parentIssueId;

	@Nullable
	@Setter(AccessLevel.NONE)
	private BigDecimal estimatedEffort;

	@Nullable
	@Setter(AccessLevel.NONE)
	private BigDecimal budgetedEffort;

	@Nullable
	private BigDecimal roughEstimation;

	@Nullable
	private LocalDate plannedUATDate;

	@NonNull
	private Effort issueEffort;

	@NonNull
	private Effort aggregatedEffort;

	@Nullable
	private Quantity invoicableChildEffort;

	@Nullable
	private BigDecimal invoiceableHours;

	@NonNull
	private String name;

	@Nullable
	private String description;

	@NonNull
	private String searchKey;

	@NonNull
	private IssueType type;

	private boolean isEffortIssue;

	@Nullable
	private BigDecimal externalIssueNo;

	@Nullable
	private Status status;

	@Nullable
	private String deliveryPlatform;

	@Nullable
	private String externalIssueURL;

	@Nullable
	private Instant latestActivityOnSubIssues;

	@Nullable
	private Instant latestActivityOnIssue;

	private final boolean processed;

	@Nullable
	private LocalDate deliveredDate;

	@Nullable
	private Instant processedTimestamp;

	@Nullable
	private Instant externallyUpdatedAt;

	@Nullable
	private ActivityId costCenterActivityId;

	@Nullable
	private String invoicingErrorMsg;

	boolean isInvoicingError;

	public void setEstimatedEffortIfNotSet(@Nullable final BigDecimal estimatedEffort)
	{
		if (this.estimatedEffort == null || this.estimatedEffort.signum() == 0)
		{
			this.estimatedEffort = estimatedEffort;
		}
	}

	public void addAggregatedEffort(@Nullable final Effort effort)
	{
		this.aggregatedEffort = aggregatedEffort.addNullSafe(effort);
	}

	public void addIssueEffort(@Nullable final Effort effort)
	{
		this.issueEffort = issueEffort.addNullSafe(effort);
	}

	public void addInvoiceableChildEffort(@Nullable final Quantity augent)
	{
		this.invoicableChildEffort = Quantitys.addNullSafe(
				UOMConversionContext.of((ProductId)null),
				invoicableChildEffort,
				augent);
	}

	@Nullable
	public Instant getLatestActivity()
	{
		return Stream.of(latestActivityOnIssue, latestActivityOnSubIssues)
				.filter(Objects::nonNull)
				.max(Instant::compareTo)
				.orElse(null);
	}

	@NonNull
	public Optional<EffortTarget> getEffortTarget()
	{
		if (costCenterActivityId == null || projectId == null)
		{
			return Optional.empty();
		}

		return Optional.of(EffortTarget.builder()
								   .costCenterId(costCenterActivityId)
								   .orgId(orgId)
								   .projectId(projectId)
								   .build());
	}

	@NonNull
	public Status getStatusOrNew()
	{
		return Optional.ofNullable(status).orElse(Status.NEW);
	}

	public boolean isInvoiced()
	{
		return Status.INVOICED.equals(status);
	}
}
