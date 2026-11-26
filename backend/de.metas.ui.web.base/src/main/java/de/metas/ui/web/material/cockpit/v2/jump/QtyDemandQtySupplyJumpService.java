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

package de.metas.ui.web.material.cockpit.v2.jump;

import de.metas.inoutcandidate.ShipmentScheduleQuery;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.ReceiptScheduleQuery;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.QtyDemandQtySupply;
import de.metas.material.cockpit.QtyDemandQtySupplyId;
import de.metas.material.cockpit.QtyDemandSupplyRepository;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidatesQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Backs the {@link QtyDemandQtySupplyJumpProcess} "jump to ..." actions on {@code QtyDemand_QtySupply_V}: the
 * single Spring collaborator through which those {@code JavaProcess} subclasses reach the repositories/DAOs of
 * the target streams (shipment schedules, production order candidates, receipt schedules), so no process holds a
 * {@code @Repository} field directly.
 * <p>
 * Each stream exposes one {@code hasXxxToOpen}/{@code findXxxToOpen} pair, both built from the same
 * query-builder method, so the precondition probe and the actual jump can never disagree about what "empty" means.
 */
@Service
@RequiredArgsConstructor
public class QtyDemandQtySupplyJumpService
{
	@NonNull private final QtyDemandSupplyRepository qtyDemandSupplyRepository;
	@NonNull private final ShipmentScheduleRepository shipmentScheduleRepository;
	@NonNull private final PPOrderCandidateDAO ppOrderCandidateDAO;

	@NonNull private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);

	public QtyDemandQtySupply getRow(@NonNull final QtyDemandQtySupplyId id)
	{
		return qtyDemandSupplyRepository.getById(id);
	}

	public boolean hasShipmentSchedulesToOpen(@NonNull final QtyDemandQtySupply row)
	{
		return shipmentScheduleRepository.existsByQuery(toShipmentScheduleQuery(row));
	}

	public List<TableRecordReference> findShipmentSchedulesToOpen(@NonNull final QtyDemandQtySupply row)
	{
		return shipmentScheduleRepository.getIdsByQuery(toShipmentScheduleQuery(row))
				.stream()
				.map(id -> TableRecordReference.of(I_M_ShipmentSchedule.Table_Name, id))
				.collect(Collectors.toList());
	}

	private static ShipmentScheduleQuery toShipmentScheduleQuery(@NonNull final QtyDemandQtySupply row)
	{
		return ShipmentScheduleQuery.builder()
				.warehouseId(row.getWarehouseId())
				.orgId(row.getOrgId())
				.productId(row.getProductId())
				.attributesKey(row.getAttributesKey())
				.onlyNonZeroReservedQty(true)
				.build();
	}

	public boolean hasPPOrderCandidatesToOpen(@NonNull final QtyDemandQtySupply row)
	{
		return ppOrderCandidateDAO.existsByQuery(toPPOrderCandidatesQuery(row));
	}

	public List<TableRecordReference> findPPOrderCandidatesToOpen(@NonNull final QtyDemandQtySupply row)
	{
		return ppOrderCandidateDAO.listIdsByQuery(toPPOrderCandidatesQuery(row))
				.stream()
				.map(id -> TableRecordReference.of(I_PP_Order_Candidate.Table_Name, id))
				.collect(Collectors.toList());
	}

	private static PPOrderCandidatesQuery toPPOrderCandidatesQuery(@NonNull final QtyDemandQtySupply row)
	{
		return PPOrderCandidatesQuery.builder()
				.warehouseId(row.getWarehouseId())
				.orgId(row.getOrgId())
				.productId(row.getProductId())
				.attributesKey(row.getAttributesKey())
				.onlyNonZeroQty(true)
				.build();
	}

	public boolean hasReceiptSchedulesToOpen(@NonNull final QtyDemandQtySupply row)
	{
		return receiptScheduleDAO.existsByQuery(toReceiptScheduleQuery(row));
	}

	public List<TableRecordReference> findReceiptSchedulesToOpen(@NonNull final QtyDemandQtySupply row)
	{
		return receiptScheduleDAO.listIdsByQuery(toReceiptScheduleQuery(row))
				.stream()
				.map(id -> TableRecordReference.of(I_M_ReceiptSchedule.Table_Name, id))
				.collect(Collectors.toList());
	}

	private static ReceiptScheduleQuery toReceiptScheduleQuery(@NonNull final QtyDemandQtySupply row)
	{
		return ReceiptScheduleQuery.builder()
				.warehouseId(row.getWarehouseId())
				.orgId(row.getOrgId())
				.productId(row.getProductId())
				.attributesKey(row.getAttributesKey())
				.onlyNonZeroQty(true)
				.build();
	}
}
