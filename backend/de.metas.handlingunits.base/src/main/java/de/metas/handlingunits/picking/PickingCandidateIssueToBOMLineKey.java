package de.metas.handlingunits.picking;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_Picking_Candidate_IssueToOrder;
import org.eevolution.api.PPOrderBOMLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Value
@Builder
final class PickingCandidateIssueToBOMLineKey
{
	public static PickingCandidateIssueToBOMLineKey of(final PickingCandidateIssueToBOMLine issue)
	{
		return new PickingCandidateIssueToBOMLineKey(issue.getIssueToOrderBOMLineId(), issue.getIssueFromHUId());
	}

	public static PickingCandidateIssueToBOMLineKey of(final I_M_Picking_Candidate_IssueToOrder record)
	{
		return new PickingCandidateIssueToBOMLineKey(
				PPOrderBOMLineId.ofRepoId(record.getPP_Order_BOMLine_ID()),
				HuId.ofRepoId(record.getM_HU_ID()));
	}

	@NonNull
	PPOrderBOMLineId issueToOrderBOMLineId;

	@NonNull
	HuId issueFromHUId;
}
