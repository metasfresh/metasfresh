/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2023 metas GmbH
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

import com.google.common.collect.ImmutableList;
import de.metas.process.PInstanceId;
import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_OrderCandidate_PP_Order;
import org.eevolution.model.I_PP_OrderLine_Candidate;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;

import java.util.Iterator;
import java.util.Set;

public interface IPPOrderCandidateDAO extends ISingletonService
{
	@NonNull
	I_PP_Order_Candidate getById(@NonNull final PPOrderCandidateId ppOrderCandidateId);

	@NonNull ImmutableList<I_PP_Order_Candidate> getByIds(@NonNull Set<PPOrderCandidateId> ppOrderCandidateIds);

	void save(@NonNull final I_PP_Order_Candidate candidateRecord);

	@NonNull
	ImmutableList<I_PP_OrderLine_Candidate> getLinesByCandidateId(@NonNull final PPOrderCandidateId ppOrderCandidateId);

	void saveLine(@NonNull final I_PP_OrderLine_Candidate ppOrderLineCandidate);

	@NonNull
	Iterator<I_PP_Order_Candidate> retrieveOCForSelection(@NonNull final PInstanceId pinstanceId);

	void createProductionOrderAllocation(
			@NonNull final PPOrderCandidateId candidateId,
			@NonNull final I_PP_Order orderRecord,
			@NonNull final Quantity qtyToAllocate);

	@NonNull
	ImmutableList<I_PP_OrderCandidate_PP_Order> getOrderAllocations(@NonNull final PPOrderCandidateId ppOrderCandidateId);

	@NonNull ImmutableList<I_PP_Order_Candidate> getByOrderId(@NonNull PPOrderId ppOrderId);

	@NonNull
	ImmutableList<I_PP_Order_Candidate> getByProductBOMId(@NonNull final ProductBOMId productBOMId);

	void deletePPOrderCandidates(@NonNull final DeletePPOrderCandidatesQuery deletePPOrderCandidatesQuery);

	void markAsProcessed(@NonNull final I_PP_Order_Candidate candidate);

	void closeCandidate(@NonNull PPOrderCandidateId ppOrderCandidateId);

	void deleteLines(@NonNull PPOrderCandidateId ppOrderCandidateId);
}
