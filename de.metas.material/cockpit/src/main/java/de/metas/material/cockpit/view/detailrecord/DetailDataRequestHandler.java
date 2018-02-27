package de.metas.material.cockpit.view.detailrecord;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.springframework.stereotype.Service;

import de.metas.material.cockpit.model.I_MD_Cockpit_DocumentDetail;
import de.metas.material.cockpit.view.DetailDataRecordIdentifier;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-cockpit
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class DetailDataRequestHandler
{
	public void handleInsertDetailRequest(@NonNull final InsertDetailRequest insertDetailRequest)
	{
		final I_MD_Cockpit_DocumentDetail documentDetailRecord = newInstance(I_MD_Cockpit_DocumentDetail.class);
		final DetailDataRecordIdentifier detailDataRecordIdentifier = insertDetailRequest
				.getDetailDataRecordIdentifier();

		final int cockpitId = retrieveDataId(detailDataRecordIdentifier
				.getMainDataRecordIdentifier());

		documentDetailRecord.setMD_Cockpit_ID(cockpitId);
		documentDetailRecord.setM_ShipmentSchedule_ID(detailDataRecordIdentifier.getShipmentScheduleId());
		documentDetailRecord.setM_ReceiptSchedule_ID(detailDataRecordIdentifier.getReceiptScheduleId());

		documentDetailRecord.setC_BPartner_ID(insertDetailRequest.getBPartnerId());
		documentDetailRecord.setQtyOrdered(insertDetailRequest.getQtyOrdered());
		documentDetailRecord.setQtyReserved(insertDetailRequest.getQtyReserved());

		documentDetailRecord.setC_Order_ID(insertDetailRequest.getOrderId());
		documentDetailRecord.setC_OrderLine_ID(insertDetailRequest.getOrderLineId());

		documentDetailRecord.setC_Flatrate_Term_ID(insertDetailRequest.getSubscriptionId());
		documentDetailRecord.setC_SubscriptionProgress_ID(insertDetailRequest.getSubscriptionLineId());

		if (insertDetailRequest.getDocTypeId() > 0)
		{ // don't set it to 0, because C_DocType_ID = 0 won't end up as "none" but as "new"
			documentDetailRecord.setC_DocType_ID(insertDetailRequest.getDocTypeId());
		}

		save(documentDetailRecord);
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

	public int handleUpdateDetailRequest(@NonNull final UpdateDetailRequest updateDetailRequest)
	{
		final ICompositeQueryUpdater<I_MD_Cockpit_DocumentDetail> updater = Services.get(IQueryBL.class)
				.createCompositeQueryUpdater(I_MD_Cockpit_DocumentDetail.class)
				.addSetColumnValue(I_MD_Cockpit_DocumentDetail.COLUMNNAME_QtyOrdered, updateDetailRequest.getQtyOrdered())
				.addSetColumnValue(I_MD_Cockpit_DocumentDetail.COLUMNNAME_QtyReserved, updateDetailRequest.getQtyReserved());

		return updateDetailRequest.getDetailDataRecordIdentifier()
				.createQuery()
				.update(updater);
	}

	public int handleRemoveDetailRequest(@NonNull final RemoveDetailRequest removeDetailsRequest)
	{
		final int deletedCount = removeDetailsRequest
				.getDetailDataRecordIdentifier()
				.createQuery()
				.delete();

		return deletedCount;
	}
}
