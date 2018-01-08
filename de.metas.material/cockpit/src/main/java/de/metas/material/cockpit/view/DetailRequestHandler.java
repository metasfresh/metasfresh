package de.metas.material.cockpit.view;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.material.cockpit.model.I_MD_Cockpit_DocumentDetail;
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

public class DetailRequestHandler
{
	public void handleAddDetailRequest(@NonNull final AddDetailRequest addDetailRequest)
	{
		final I_MD_Cockpit_DocumentDetail documentDetailRecord = newInstance(I_MD_Cockpit_DocumentDetail.class);
		final int cockpitId = retrieveOrCreateDataId(addDetailRequest.getIdentifier());

		documentDetailRecord.setMD_Cockpit_ID(cockpitId);

		documentDetailRecord.setC_Order_ID(addDetailRequest.getOrderId());
		documentDetailRecord.setC_OrderLine_ID(addDetailRequest.getOrderLineId());

		documentDetailRecord.setC_Flatrate_Term_ID(addDetailRequest.getSubscriptionId());
		documentDetailRecord.setC_SubscriptionProgress_ID(addDetailRequest.getSubscriptionLineId());

		save(documentDetailRecord);
	}

	public int handleRemoveDetailsRequest(@NonNull final RemoveDetailsRequest removeDetailsRequest)
	{
		final DataRecordIdentifier identifier = removeDetailsRequest.getIdentifier();
		final int cockpitId = retrieveOrCreateDataId(identifier);

		final int deletedCount = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Cockpit_DocumentDetail.class)
				.addEqualsFilter(I_MD_Cockpit_DocumentDetail.COLUMN_MD_Cockpit_ID, cockpitId)
				.create()
				.delete();
		return deletedCount;
	}

	private int retrieveOrCreateDataId(@NonNull final DataRecordIdentifier identifier)
	{
		final int result = identifier
				.createQuery()
				.firstIdOnly();

		Check.errorIf(result <= 0, "Found no I_MD_Cockpit record for identifier={}", identifier);
		return result;
	}
}
