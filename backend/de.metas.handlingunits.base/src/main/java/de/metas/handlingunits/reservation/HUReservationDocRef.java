/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.reservation;

import de.metas.common.util.CoalesceUtil;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.project.ProjectId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class HUReservationDocRef
{
	@Nullable OrderLineId salesOrderLineId;
	@Nullable ProjectId projectId;
	@Nullable PickingJobStepId pickingJobStepId;
	@Nullable DDOrderLineId ddOrderLineId;

	public static HUReservationDocRef ofSalesOrderLineId(@NonNull final OrderLineId salesOrderLineId) {return HUReservationDocRef.builder().salesOrderLineId(salesOrderLineId).build();}

	public static HUReservationDocRef ofSalesOrderLineId(@NonNull final OrderAndLineId salesOrderLineId) {return ofSalesOrderLineId(salesOrderLineId.getOrderLineId());}

	public static HUReservationDocRef ofProjectId(@NonNull final ProjectId projectId) {return HUReservationDocRef.builder().projectId(projectId).build();}

	public static HUReservationDocRef ofPickingJobStepId(@NonNull final PickingJobStepId pickingJobStepId) {return HUReservationDocRef.builder().pickingJobStepId(pickingJobStepId).build();}

	public static HUReservationDocRef ofDDOrderLineId(@NonNull final DDOrderLineId ddOrderLineId) {return HUReservationDocRef.builder().ddOrderLineId(ddOrderLineId).build();}

	@Builder
	private HUReservationDocRef(
			@Nullable final OrderLineId salesOrderLineId,
			@Nullable final ProjectId projectId,
			@Nullable final PickingJobStepId pickingJobStepId,
			@Nullable DDOrderLineId ddOrderLineId)
	{
		if (CoalesceUtil.countNotNulls(salesOrderLineId, projectId, pickingJobStepId, ddOrderLineId) != 1)
		{
			throw new AdempiereException("One and only one document shall be set")
					.appendParametersToMessage()
					.setParameter("salesOrderLineId", salesOrderLineId)
					.setParameter("projectId", projectId)
					.setParameter("pickingJobStepId", pickingJobStepId)
					.setParameter("ddOrderLineId", ddOrderLineId);
		}

		this.salesOrderLineId = salesOrderLineId;
		this.projectId = projectId;
		this.pickingJobStepId = pickingJobStepId;
		this.ddOrderLineId = ddOrderLineId;
	}

	public static boolean equals(@Nullable final HUReservationDocRef ref1, @Nullable final HUReservationDocRef ref2) {return Objects.equals(ref1, ref2);}

	public interface CaseMappingFunction<R>
	{
		R salesOrderLineId(@NonNull OrderLineId salesOrderLineId);

		R projectId(@NonNull ProjectId projectId);

		R pickingJobStepId(@NonNull PickingJobStepId pickingJobStepId);

		R ddOrderLineId(@NonNull DDOrderLineId ddOrderLineId);
	}

	public <R> R map(@NonNull final CaseMappingFunction<R> mappingFunction)
	{
		if (salesOrderLineId != null)
		{
			return mappingFunction.salesOrderLineId(salesOrderLineId);
		}
		else if (projectId != null)
		{
			return mappingFunction.projectId(projectId);
		}
		else if (pickingJobStepId != null)
		{
			return mappingFunction.pickingJobStepId(pickingJobStepId);
		}
		else if (ddOrderLineId != null)
		{
			return mappingFunction.ddOrderLineId(ddOrderLineId);
		}
		else
		{
			throw new IllegalStateException("Unknown state: " + this);
		}
	}
}
