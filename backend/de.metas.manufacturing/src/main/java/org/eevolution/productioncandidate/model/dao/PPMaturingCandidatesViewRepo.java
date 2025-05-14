/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.model.dao;

import de.metas.handlingunits.HuId;
import de.metas.material.maturing.MaturingConfigId;
import de.metas.material.maturing.MaturingConfigLineId;
import de.metas.material.planning.ProductPlanningId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_PP_Maturing_Candidates_v;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Objects;
import java.util.stream.Stream;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PPMaturingCandidatesViewRepo
{
	IQueryBL queryBL = Services.get(IQueryBL.class);

	public Stream<PPMaturingCandidateV> streamValidCandidates()
	{
		return queryBL.createQueryBuilder(I_PP_Maturing_Candidates_v.class)
				.create()
				.iterateAndStream()
				.filter(this::isValidCandidate)
				.map(PPMaturingCandidatesViewRepo::toPPMaturingCandidateV);
	}

	private boolean isValidCandidate(final @NonNull I_PP_Maturing_Candidates_v ppMaturingCandidatesV)
	{
		return ppMaturingCandidatesV.getDateStartSchedule() != null;
	}

	private static PPMaturingCandidateV toPPMaturingCandidateV(@NonNull final I_PP_Maturing_Candidates_v ppMaturingCandidatesV)
	{
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(ppMaturingCandidatesV.getAD_Client_ID(), ppMaturingCandidatesV.getAD_Org_ID());
		final PPOrderCandidateId ppOrderCandidateId = PPOrderCandidateId.ofRepoIdOrNull(ppMaturingCandidatesV.getPP_Order_Candidate_ID());
		final MaturingConfigId maturingConfigId = MaturingConfigId.ofRepoId(ppMaturingCandidatesV.getM_Maturing_Configuration_ID());
		final MaturingConfigLineId maturingConfigLineId = MaturingConfigLineId.ofRepoId(ppMaturingCandidatesV.getM_Maturing_Configuration_Line_ID());
		final ProductBOMVersionsId productBOMVersionsId = ProductBOMVersionsId.ofRepoId(ppMaturingCandidatesV.getPP_Product_BOMVersions_ID());
		final ProductId productId = ProductId.ofRepoId(ppMaturingCandidatesV.getM_Product_ID());
		final ProductId issueProductId = ProductId.ofRepoId(ppMaturingCandidatesV.getIssue_M_Product_ID());
		final WarehouseId warehouseId = WarehouseId.ofRepoId(ppMaturingCandidatesV.getM_Warehouse_ID());
		final ProductPlanningId productPlanningId = ProductPlanningId.ofRepoId(ppMaturingCandidatesV.getPP_Product_Planning_ID());
		final Quantity quantity = Quantitys.of(ppMaturingCandidatesV.getQty(), UomId.ofRepoId(ppMaturingCandidatesV.getC_UOM_ID()));
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNone(ppMaturingCandidatesV.getM_AttributeSetInstance_ID());
		final Instant dateStartSchedule = Objects.requireNonNull(ppMaturingCandidatesV.getDateStartSchedule()).toInstant();
		final HuId huId = HuId.ofRepoId(ppMaturingCandidatesV.getM_HU_ID());

		return PPMaturingCandidateV.builder()
				.clientAndOrgId(clientAndOrgId)
				.ppOrderCandidateId(ppOrderCandidateId)
				.maturingConfigId(maturingConfigId)
				.maturingConfigLineId(maturingConfigLineId)
				.productBOMVersionsId(productBOMVersionsId)
				.productId(productId)
				.issueProductId(issueProductId)
				.warehouseId(warehouseId)
				.productPlanningId(productPlanningId)
				.qtyRequired(quantity)
				.attributeSetInstanceId(attributeSetInstanceId)
				.dateStartSchedule(dateStartSchedule)
				.issueHuId(huId)
				.build();
	}

	public int deleteStaleCandidates()
	{
		final IQuery<I_PP_Maturing_Candidates_v> ppMaturingCandidatesVQuery = queryBL.createQueryBuilder(I_PP_Maturing_Candidates_v.class)
				.create();
		return queryBL.createQueryBuilder(I_PP_Order_Candidate.class)
				.addNotEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_Processed, true)
				.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_IsMaturing, true)
				.addNotInSubQueryFilter(I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID, I_PP_Maturing_Candidates_v.COLUMNNAME_PP_Order_Candidate_ID, ppMaturingCandidatesVQuery)
				.create()
				.delete();
	}

}
