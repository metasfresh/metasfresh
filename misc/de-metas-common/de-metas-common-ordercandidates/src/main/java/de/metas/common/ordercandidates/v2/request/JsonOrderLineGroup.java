/*
 * #%L
 * de-metas-common-ordercandidates
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

package de.metas.common.ordercandidates.v2.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
public class JsonOrderLineGroup
{
	@Schema(description = "All JsonOLCandCreateRequests with the same ExternalHeaderId and the same groupId shall belong to the same bundle (compensation-group)")
	@JsonInclude(NON_NULL)
	String groupKey;
	@Schema(description = "If true, marks the associated as the \"main\" product. Should only be set to true for non-stocked products.")
	@JsonInclude(NON_NULL)
	boolean isGroupMainItem;

	@Schema(description = "Translates to C_OLCand.GroupCompensationDiscountPercentage")
	@JsonInclude(NON_NULL)
	BigDecimal discount;

	@Schema(description = "It is taken into consideration when C_OLCand.Line is renumbered. Translates to C_OLCand.CompensationGroupOrderBy")
	@JsonInclude(NON_NULL)
	JsonGroupCompensationOrderBy ordering;

	@Builder
	public JsonOrderLineGroup(
			@JsonProperty("groupKey") final @Nullable String groupKey,
			@JsonProperty("groupMainItem") final boolean isGroupMainItem,
			@JsonProperty("discount") final @Nullable BigDecimal discount,
			@JsonProperty("ordering") final @Nullable JsonGroupCompensationOrderBy ordering)
	{

		if (!isGroupMainItem && discount != null)
		{
			throw new RuntimeException("Discount can only be set for the group's main item! (i.e. groupMainItem = true ) ");
		}

		this.groupKey = groupKey;
		this.isGroupMainItem = isGroupMainItem;
		this.discount = discount;
		this.ordering = ordering;
	}
}
