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

import de.metas.inoutcandidate.ShipmentScheduleQuery;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.QtyDemandQtySupply;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public class QtyDemand_QtySupply_V_to_ShipmentSchedule extends QtyDemandQtySupplyJumpProcess
{
	private final ShipmentScheduleRepository shipmentScheduleRepository = SpringContextHolder.instance.getBean(ShipmentScheduleRepository.class);

	@Override
	protected boolean hasRecordsToOpen(@NonNull final QtyDemandQtySupply row)
	{
		return shipmentScheduleRepository.getIdByQuery(toQuery(row)).isPresent();
	}

	@Override
	protected List<TableRecordReference> findRecordsToOpen(@NonNull final QtyDemandQtySupply row)
	{
		return shipmentScheduleRepository.getIdsByQuery(toQuery(row))
				.stream()
				.map(id -> TableRecordReference.of(I_M_ShipmentSchedule.Table_Name, id))
				.collect(Collectors.toList());
	}

	private static ShipmentScheduleQuery toQuery(@NonNull final QtyDemandQtySupply row)
	{
		return ShipmentScheduleQuery.builder()
				.warehouseId(row.getWarehouseId())
				.orgId(row.getOrgId())
				.productId(row.getProductId())
				.attributesKey(row.getAttributesKey())
				.onlyNonZeroReservedQty(true)
				.build();
	}
}
