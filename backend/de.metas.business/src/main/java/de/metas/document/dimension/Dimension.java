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

import de.metas.common.util.CoalesceUtil;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.sectionCode.SectionCodeId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
public class Dimension implements Comparable<Dimension>
{
	@With
	@Nullable
	ProjectId projectId;

	int campaignId;

	@With
	@Nullable
	ActivityId activityId;

	@With
	@Nullable
	OrderId orderId;

	@With
	@Nullable
	SectionCodeId sectionCodeId;

	@With
	@Nullable
	ProductId productId;


	// todo propagation for these 2
	int user1_ID;
	int user2_ID;

	int userElement1Id;
	int userElement2Id;

	@Nullable
	String userElementString1;
	@Nullable
	String userElementString2;
	@Nullable
	String userElementString3;
	@Nullable
	String userElementString4;
	@Nullable
	String userElementString5;
	@Nullable
	String userElementString6;
	@Nullable
	String userElementString7;

	public static boolean equals(@Nullable final Dimension d1, @Nullable final Dimension d2)
	{

		return Objects.equals(d1, d2);
	}

	@Override
	public int compareTo(@Nullable final Dimension o)
	{
		return this.equals(o) ? 0 : -1;
	}

	public Dimension fallbackTo(@NonNull final Dimension other)
	{
		return builder()
				.projectId(CoalesceUtil.coalesce(this.projectId, other.projectId))
				.campaignId(CoalesceUtil.firstGreaterThanZero(this.campaignId, other.campaignId))
				.activityId(CoalesceUtil.coalesce(this.activityId, other.activityId))
				.orderId(CoalesceUtil.coalesce(this.orderId, other.orderId))
				.sectionCodeId(CoalesceUtil.coalesce(this.sectionCodeId, other.sectionCodeId))
				.productId(CoalesceUtil.coalesce(this.productId, other.productId))
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
				.build();
	}
}
