/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.dimension;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.common.util.CoalesceUtil;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Value
@Builder(toBuilder = true)
public class Dimension implements Comparable<Dimension>
{
	public static Dimension EMPTY = builder().build();

	@With @Nullable ProjectId projectId;
	int campaignId;
	@With @Nullable ActivityId activityId;
	@With @Nullable OrderId salesOrderId;
	@With @Nullable SectionCodeId sectionCodeId;
	@With @Nullable ProductId productId;
	@Nullable BPartnerId bpartnerId2;

	// todo propagation for these 2
	int user1_ID;
	int user2_ID;

	int userElement1Id;
	int userElement2Id;

	@Nullable String userElementString1;
	@Nullable String userElementString2;
	@Nullable String userElementString3;
	@Nullable String userElementString4;
	@Nullable String userElementString5;
	@Nullable String userElementString6;
	@Nullable String userElementString7;
	@Nullable BigDecimal userElementNumber1;
	@Nullable BigDecimal userElementNumber2;
	@Nullable Instant userElementDate1;
	@Nullable Instant userElementDate2;
	@Nullable YearAndCalendarId harvestingYearAndCalendarId;

	public static boolean equals(@Nullable final Dimension d1, @Nullable final Dimension d2)
	{
		return Objects.equals(d1, d2);
	}

	@Override
	public int compareTo(@Nullable final Dimension other)
	{
		if (other == null)
		{
			return -1;
		}
		else if (this.equals(other))
		{
			return 0;
		}
		else
		{
			return this.toString().compareTo(other.toString());
		}
	}

	public Dimension fallbackTo(@NonNull final Dimension other)
	{
		final Dimension newDimension = builder()
				.projectId(CoalesceUtil.coalesce(this.projectId, other.projectId))
				.campaignId(CoalesceUtil.firstGreaterThanZero(this.campaignId, other.campaignId))
				.activityId(CoalesceUtil.coalesce(this.activityId, other.activityId))
				.salesOrderId(CoalesceUtil.coalesce(this.salesOrderId, other.salesOrderId))
				.sectionCodeId(CoalesceUtil.coalesce(this.sectionCodeId, other.sectionCodeId))
				.productId(CoalesceUtil.coalesce(this.productId, other.productId))
				.bpartnerId2(CoalesceUtil.coalesce(this.bpartnerId2, other.bpartnerId2))
				.user1_ID(CoalesceUtil.firstGreaterThanZero(this.user1_ID, other.user1_ID))
				.user2_ID(CoalesceUtil.firstGreaterThanZero(this.user2_ID, other.user2_ID))
				.userElement1Id(CoalesceUtil.firstGreaterThanZero(this.userElement1Id, other.userElement1Id))
				.userElement2Id(CoalesceUtil.firstGreaterThanZero(this.userElement2Id, other.userElement2Id))
				.userElementString1(CoalesceUtil.coalesce(this.userElementString1, other.userElementString1))
				.userElementString2(CoalesceUtil.coalesce(this.userElementString2, other.userElementString2))
				.userElementString3(CoalesceUtil.coalesce(this.userElementString3, other.userElementString3))
				.userElementString4(CoalesceUtil.coalesce(this.userElementString4, other.userElementString4))
				.userElementString5(CoalesceUtil.coalesce(this.userElementString5, other.userElementString5))
				.userElementString6(CoalesceUtil.coalesce(this.userElementString6, other.userElementString6))
				.userElementString7(CoalesceUtil.coalesce(this.userElementString7, other.userElementString7))
				.userElementNumber1(CoalesceUtil.coalesce(this.userElementNumber1, other.userElementNumber1))
				.userElementNumber2(CoalesceUtil.coalesce(this.userElementNumber2, other.userElementNumber2))
				.harvestingYearAndCalendarId(CoalesceUtil.coalesce(this.harvestingYearAndCalendarId, other.harvestingYearAndCalendarId))
				.build();

		if (newDimension.equals(this))
		{
			return this;
		}
		else if (newDimension.equals(other))
		{
			return other;
		}
		else
		{
			return newDimension;
		}
	}

	public static Dimension extractCommonDimension(@NonNull final Collection<Dimension> collection)
	{
		if (collection.isEmpty())
		{
			return EMPTY;
		}
		else if (collection.size() == 1)
		{
			return collection.iterator().next();
		}

		@Nullable HashSet<ProjectId> projectId = null;
		@Nullable HashSet<Integer> campaignId = null;
		@Nullable HashSet<ActivityId> activityId = null;
		@Nullable HashSet<OrderId> salesOrderId = null;
		@Nullable HashSet<SectionCodeId> sectionCodeId = null;
		@Nullable HashSet<ProductId> productId = null;
		@Nullable HashSet<BPartnerId> bpartnerId2 = null;
		@Nullable HashSet<Integer> user1_ID = null;
		@Nullable HashSet<Integer> user2_ID = null;
		@Nullable HashSet<Integer> userElement1Id = null;
		@Nullable HashSet<Integer> userElement2Id = null;
		@Nullable HashSet<BigDecimal> userElementNumber1 = null;
		@Nullable HashSet<BigDecimal> userElementNumber2 = null;
		@Nullable HashSet<String> userElementString1 = null;
		@Nullable HashSet<String> userElementString2 = null;
		@Nullable HashSet<String> userElementString3 = null;
		@Nullable HashSet<String> userElementString4 = null;
		@Nullable HashSet<String> userElementString5 = null;
		@Nullable HashSet<String> userElementString6 = null;
		@Nullable HashSet<String> userElementString7 = null;
		@Nullable HashSet<Instant> userElementDate1 = null;
		@Nullable HashSet<Instant> userElementDate2 = null;
		@Nullable HashSet<YearAndCalendarId> yearAndCalendarId = null;

		for (final Dimension dimension : collection)
		{
			projectId = collectValueIfNotNull(projectId, dimension.getProjectId());
			campaignId = collectValueIfPositive(campaignId, dimension.getCampaignId());
			activityId = collectValueIfNotNull(activityId, dimension.getActivityId());
			salesOrderId = collectValueIfNotNull(salesOrderId, dimension.getSalesOrderId());
			sectionCodeId = collectValueIfNotNull(sectionCodeId, dimension.getSectionCodeId());
			productId = collectValueIfNotNull(productId, dimension.getProductId());
			bpartnerId2 = collectValueIfNotNull(bpartnerId2, dimension.getBpartnerId2());
			user1_ID = collectValueIfPositive(user1_ID, dimension.getUser1_ID());
			user2_ID = collectValueIfPositive(user2_ID, dimension.getUser2_ID());
			userElement1Id = collectValueIfPositive(userElement1Id, dimension.getUserElement1Id());
			userElement2Id = collectValueIfPositive(userElement2Id, dimension.getUserElement2Id());
			userElementNumber1 = collectValueIfNotNull(userElementNumber1, dimension.getUserElementNumber1());
			userElementNumber2 = collectValueIfNotNull(userElementNumber2, dimension.getUserElementNumber2());
			userElementString1 = collectValueIfNotNull(userElementString1, dimension.getUserElementString1());
			userElementString2 = collectValueIfNotNull(userElementString2, dimension.getUserElementString2());
			userElementString3 = collectValueIfNotNull(userElementString3, dimension.getUserElementString3());
			userElementString4 = collectValueIfNotNull(userElementString4, dimension.getUserElementString4());
			userElementString5 = collectValueIfNotNull(userElementString5, dimension.getUserElementString5());
			userElementString6 = collectValueIfNotNull(userElementString6, dimension.getUserElementString6());
			userElementString7 = collectValueIfNotNull(userElementString7, dimension.getUserElementString7());
			userElementDate1 = collectValueIfNotNull(userElementDate1, dimension.getUserElementDate1());
			userElementDate2 = collectValueIfNotNull(userElementDate2, dimension.getUserElementDate2());
			yearAndCalendarId = collectValueIfNotNull(yearAndCalendarId, dimension.getHarvestingYearAndCalendarId());
		}

		return builder()
				.projectId(CollectionUtils.singleElementOrNull(projectId))
				.campaignId(singleElementOrZero(campaignId))
				.activityId(CollectionUtils.singleElementOrNull(activityId))
				.salesOrderId(CollectionUtils.singleElementOrNull(salesOrderId))
				.sectionCodeId(CollectionUtils.singleElementOrNull(sectionCodeId))
				.productId(CollectionUtils.singleElementOrNull(productId))
				.bpartnerId2(CollectionUtils.singleElementOrNull(bpartnerId2))
				.user1_ID(singleElementOrZero(user1_ID))
				.user2_ID(singleElementOrZero(user2_ID))
				.userElement1Id(singleElementOrZero(userElement1Id))
				.userElement2Id(singleElementOrZero(userElement2Id))
				.userElementNumber1(CollectionUtils.singleElementOrNull(userElementNumber1))
				.userElementNumber2(CollectionUtils.singleElementOrNull(userElementNumber2))
				.userElementString1(CollectionUtils.singleElementOrNull(userElementString1))
				.userElementString2(CollectionUtils.singleElementOrNull(userElementString2))
				.userElementString3(CollectionUtils.singleElementOrNull(userElementString3))
				.userElementString4(CollectionUtils.singleElementOrNull(userElementString4))
				.userElementString5(CollectionUtils.singleElementOrNull(userElementString5))
				.userElementString6(CollectionUtils.singleElementOrNull(userElementString6))
				.userElementString7(CollectionUtils.singleElementOrNull(userElementString7))
				.userElementDate1(CollectionUtils.singleElementOrNull(userElementDate1))
				.userElementDate2(CollectionUtils.singleElementOrNull(userElementDate2))
				.harvestingYearAndCalendarId(CollectionUtils.singleElementOrNull(yearAndCalendarId))
				.build();
	}

	private static <T> HashSet<T> collectValueIfNotNull(@Nullable HashSet<T> collector, @Nullable T value)
	{
		if (value == null)
		{
			return collector;
		}

		if (collector == null)
		{
			collector = new HashSet<>();
		}
		collector.add(value);

		return collector;
	}

	private static HashSet<Integer> collectValueIfPositive(@Nullable HashSet<Integer> collector, int value)
	{
		if (value <= 0)
		{
			return collector;
		}

		if (collector == null)
		{
			collector = new HashSet<>();
		}
		collector.add(value);

		return collector;
	}

	private static int singleElementOrZero(@Nullable HashSet<Integer> collection)
	{
		if (collection == null)
		{
			return 0;
		}
		if (collection.size() != 1)
		{
			return 0;
		}

		final Integer element = collection.iterator().next();
		return element != null ? element : 0;
	}

}
