/*
 * #%L
 * metasfresh-material-cockpit
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

package de.metas.material.cockpit.view.ddorderdetail;

import de.metas.material.cockpit.model.I_MD_Cockpit_DDOrder_Detail;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static de.metas.util.NumberUtils.stripTrailingDecimalZeros;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Component
public class DDOrderDetailRequestHandler
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void handleInsertDetailRequest(@NonNull final InsertDDOrderDetailRequest insertRequest)
	{
		final I_MD_Cockpit_DDOrder_Detail ddOrderDetail = newInstance(I_MD_Cockpit_DDOrder_Detail.class);

		final DDOrderDetailIdentifier ddOrderDetailIdentifier = insertRequest.getDdOrderDetailIdentifier();

		final int cockpitId = retrieveDataId(ddOrderDetailIdentifier.getMainDataRecordIdentifier());

		ddOrderDetail.setMD_Cockpit_ID(cockpitId);
		ddOrderDetail.setQtyPending(stripTrailingDecimalZeros(insertRequest.getQtyPending()));

		ddOrderDetail.setDD_OrderLine_ID(ddOrderDetailIdentifier.getDdOrderDetailIdentifier().getDdOrderLineId());
		ddOrderDetail.setM_Warehouse_ID(ddOrderDetailIdentifier.getDdOrderDetailIdentifier().getWarehouseId().getRepoId());

		ddOrderDetail.setDDOrderDetailType(insertRequest.getDetailType().getCode());

		save(ddOrderDetail);
	}

	private int retrieveDataId(@NonNull final MainDataRecordIdentifier identifier)
	{
		final int result = identifier
				.createQueryBuilder()
				.create()
				.firstIdOnly();

		Check.errorIf(result <= 0, "Found no I_MD_Cockpit record for identifier={}", identifier);
		return result;
	}

	public int handleUpdateDetailRequest(@NonNull final UpdateDDOrderDetailRequest updateRequest)
	{
		final ICompositeQueryUpdater<I_MD_Cockpit_DDOrder_Detail> updater = queryBL
				.createCompositeQueryUpdater(I_MD_Cockpit_DDOrder_Detail.class)
				.addSetColumnValue(
						I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_QtyPending,
						stripTrailingDecimalZeros(updateRequest.getQtyPending()));

		return updateRequest.getDdOrderDetailIdentifier()
				.createQuery()
				.update(updater);
	}

	public int handleRemoveDetailRequest(@NonNull final RemoveDDOrderDetailRequest removeDDOrderDetailRequest)
	{
		return removeDDOrderDetailRequest
				.getDdOrderDetailIdentifier()
				.createQuery()
				.delete();
	}

	@NonNull
	public Optional<I_MD_Cockpit_DDOrder_Detail> retrieveDDOrderDetail(@NonNull final DDOrderDetailIdentifier identifier)
	{
		return identifier.createQuery()
				.firstOnlyOptional(I_MD_Cockpit_DDOrder_Detail.class);
	}
}
