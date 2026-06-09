/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.material.process;

import de.metas.material.cockpit.QtyDemandQtySupply;
import de.metas.material.cockpit.QtyDemandQtySupplyId;
import de.metas.material.cockpit.QtyDemandSupplyRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidatesQuery;

import java.util.List;
import java.util.stream.Collectors;

public class QtyDemand_QtySupply_V_to_PP_Order_Candidate extends JavaProcess implements IProcessPrecondition
{
	private final QtyDemandSupplyRepository demandSupplyRepository = SpringContextHolder.instance.getBean(QtyDemandSupplyRepository.class);
	private final PPOrderCandidateDAO ppOrderCandidateDAO = SpringContextHolder.instance.getBean(PPOrderCandidateDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final QtyDemandQtySupply currentRow = demandSupplyRepository.getById(QtyDemandQtySupplyId.ofRepoId(getRecord_ID()));
		final PPOrderCandidatesQuery ppOrderCandidatesQuery = PPOrderCandidatesQuery.builder()
				.warehouseId(currentRow.getWarehouseId())
				.orgId(currentRow.getOrgId())
				.productId(currentRow.getProductId())
				.attributesKey(currentRow.getAttributesKey())
				.onlyNonZeroQty(true)
				.build();

		final List<TableRecordReference> recordReferences = ppOrderCandidateDAO.listIdsByQuery(ppOrderCandidatesQuery)
				.stream()
				.map(id -> TableRecordReference.of(I_PP_Order_Candidate.Table_Name, id))
				.collect(Collectors.toList());
		getResult().setRecordsToOpen(recordReferences);
		return MSG_OK;
	}

}
