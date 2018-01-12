package de.metas.material.cockpit.view;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Check;
import org.compiere.model.IQuery;

import de.metas.material.cockpit.model.I_MD_Cockpit_DocumentDetail;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class DetailDataRecordIdentifier
{
	public static DetailDataRecordIdentifier createForShipmentSchedule(
			MainDataRecordIdentifier mainDataRecordIdentifier,
			int shipmentScheduleId)
	{
		return new DetailDataRecordIdentifier(mainDataRecordIdentifier, shipmentScheduleId, 0);
	}

	public static DetailDataRecordIdentifier createForReceiptSchedule(
			MainDataRecordIdentifier mainDataRecordIdentifier,
			int receiptScheduleId)
	{
		return new DetailDataRecordIdentifier(mainDataRecordIdentifier, 0, receiptScheduleId);
	}

	int shipmentScheduleId;
	int receiptScheduleId;
	MainDataRecordIdentifier mainDataRecordIdentifier;

	private DetailDataRecordIdentifier(
			@NonNull final MainDataRecordIdentifier mainDataRecordIdentifier,
			final int shipmentScheduleId,
			final int receiptScheduleId)
	{
		this.receiptScheduleId = receiptScheduleId;
		this.shipmentScheduleId = shipmentScheduleId;
		this.mainDataRecordIdentifier = mainDataRecordIdentifier;

		final boolean shipmentScheduleIdSet = shipmentScheduleId > 0;
		final boolean receiptScheduleIdSet = receiptScheduleId > 0;
		Check.errorUnless(shipmentScheduleIdSet ^ receiptScheduleIdSet,
				"Either shipmentScheduleId or receipScheduleId (but not both!) needs to be < 0");
	}

	public IQuery<I_MD_Cockpit_DocumentDetail> createQuery()
	{
		final IQueryBuilder<I_MD_Cockpit_DocumentDetail> queryBuilder = mainDataRecordIdentifier.createQueryBuilder()
				.andCollectChildren(I_MD_Cockpit_DocumentDetail.COLUMN_MD_Cockpit_ID);
		if (shipmentScheduleId > 0)
		{
			queryBuilder.addEqualsFilter(I_MD_Cockpit_DocumentDetail.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleId);
		}
		if (receiptScheduleId > 0)
		{
			queryBuilder.addEqualsFilter(I_MD_Cockpit_DocumentDetail.COLUMN_M_ReceiptSchedule_ID, receiptScheduleId);
		}
		return queryBuilder.create();
	}
}
