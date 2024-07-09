package org.eevolution.api;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.eevolution.model.X_PP_Cost_Collector;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2018 metas GmbH
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

@RequiredArgsConstructor
@Getter
public enum CostCollectorType implements ReferenceListAwareEnum
{
	MaterialReceipt(X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt),
	ComponentIssue(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue),
	UsageVariance(X_PP_Cost_Collector.COSTCOLLECTORTYPE_UsegeVariance),
	MethodChangeVariance(X_PP_Cost_Collector.COSTCOLLECTORTYPE_MethodChangeVariance),
	RateVariance(X_PP_Cost_Collector.COSTCOLLECTORTYPE_RateVariance),
	MixVariance(X_PP_Cost_Collector.COSTCOLLECTORTYPE_MixVariance),
	ActivityControl(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ActivityControl),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<CostCollectorType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@Nullable
	public static CostCollectorType ofNullableCode(@Nullable final String code) {return index.ofNullableCodeOrName(code);}

	public static CostCollectorType ofCode(@NonNull final String code) {return index.ofCodeOrName(code);}

	public boolean isMaterial(@Nullable final PPOrderBOMLineId orderBOMLineId)
	{
		return isMaterialReceipt()
				|| isAnyComponentIssueOrCoProduct(orderBOMLineId);
	}

	public boolean isMaterialReceipt()
	{
		return this == MaterialReceipt;
	}

	public boolean isMaterialReceiptOrCoProduct()
	{
		return isMaterialReceipt() || isCoOrByProductReceipt();
	}

	public boolean isComponentIssue()
	{
		return this == ComponentIssue;
	}

	public boolean isAnyComponentIssueOrCoProduct(@Nullable final PPOrderBOMLineId orderBOMLineId)
	{
		return isAnyComponentIssue(orderBOMLineId)
				|| isCoOrByProductReceipt();
	}

	public boolean isAnyComponentIssue(@Nullable final PPOrderBOMLineId orderBOMLineId)
	{
		return isComponentIssue()
				|| isMaterialMethodChangeVariance(orderBOMLineId);
	}

	public boolean isActivityControl()
	{
		return this == ActivityControl;
	}

	public boolean isVariance()
	{
		return this == MethodChangeVariance
				|| this == UsageVariance
				|| this == RateVariance
				|| this == MixVariance;
	}

	public boolean isCoOrByProductReceipt()
	{
		return this == MixVariance;
	}

	public boolean isUsageVariance()
	{
		return this == UsageVariance;
	}

	public boolean isMaterialUsageVariance(@Nullable final PPOrderBOMLineId orderBOMLineId)
	{
		return this == UsageVariance && orderBOMLineId != null;
	}

	public boolean isResourceUsageVariance(@Nullable final PPOrderRoutingActivityId activityId)
	{
		return this == UsageVariance && activityId != null;
	}

	public boolean isRateVariance()
	{
		return this == RateVariance;
	}

	public boolean isMethodChangeVariance()
	{
		return this == MethodChangeVariance;
	}

	public boolean isMaterialMethodChangeVariance(@Nullable final PPOrderBOMLineId orderBOMLineId)
	{
		return this == MethodChangeVariance && orderBOMLineId != null;
	}
}
