/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.picking.job.model;

import de.metas.ad_reference.ReferenceId;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

import static de.metas.picking.model.X_PickingProfile_Filter.FILTERTYPE_AD_Reference_ID;
import static de.metas.picking.model.X_PickingProfile_Filter.FILTERTYPE_Customer;
import static de.metas.picking.model.X_PickingProfile_Filter.FILTERTYPE_DeliveryDate;
import static de.metas.picking.model.X_PickingProfile_Filter.FILTERTYPE_HandoverLocation;

@AllArgsConstructor
@Getter
public enum PickingJobFacetGroup implements ReferenceListAwareEnum
{
	CUSTOMER(FILTERTYPE_Customer),
	DELIVERY_DATE(FILTERTYPE_DeliveryDate),
	HANDOVER_LOCATION(FILTERTYPE_HandoverLocation),
	;

	public static final ReferenceId PICKING_JOB_FILTER_OPTION_REFERENCE_ID = ReferenceId.ofRepoId(FILTERTYPE_AD_Reference_ID);
	private static final ReferenceListAwareEnums.ValuesIndex<PickingJobFacetGroup> index = ReferenceListAwareEnums.index(values());

	private final String code;

	@NonNull
	public static PickingJobFacetGroup ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static boolean equals(@Nullable PickingJobFacetGroup group1, @Nullable PickingJobFacetGroup group2) {return Objects.equals(group1, group2);}
}
