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

import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import lombok.Builder;
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
}
