package org.eevolution.api;

import de.metas.material.planning.pporder.PPOrderTargetPlanningStatus;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.model.X_PP_Order;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@RequiredArgsConstructor
@Getter
public enum PPOrderPlanningStatus implements ReferenceListAwareEnum
{
	PLANNING(X_PP_Order.PLANNINGSTATUS_Planning), //
	REVIEW(X_PP_Order.PLANNINGSTATUS_Review), //
	COMPLETE(X_PP_Order.PLANNINGSTATUS_Complete), //
	;

	public static final int AD_REFERENCE_ID = X_PP_Order.PLANNINGSTATUS_AD_Reference_ID;

	private static final ReferenceListAwareEnums.ValuesIndex<PPOrderPlanningStatus> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@Nullable
	public static PPOrderPlanningStatus ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public static PPOrderPlanningStatus ofCode(@NonNull final String code) {return index.ofCode(code);}

	public static PPOrderPlanningStatus of(@NonNull PPOrderTargetPlanningStatus targetPlanningStatus)
	{
		switch (targetPlanningStatus)
		{
			case REVIEW:
				return REVIEW;
			case COMPLETE:
				return COMPLETE;
			default:
				throw new AdempiereException("Unknown target: " + targetPlanningStatus);
		}
	}

	public boolean isReview() {return REVIEW.equals(this);}

	public boolean isComplete() {return COMPLETE.equals(this);}

	public static boolean equals(@Nullable PPOrderPlanningStatus status1, @Nullable PPOrderPlanningStatus status2)
	{
		return Objects.equals(status1, status2);
	}
}
