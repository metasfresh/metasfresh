package de.metas.inoutcandidate.api.impl;

import de.metas.inoutcandidate.api.IShipmentConstraintsDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_Shipment_Constraint;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ShipmentConstraintsDAO implements IShipmentConstraintsDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	@Nullable
	public I_M_Shipment_Constraint retrieveManualConstraint(final int billBPartnerId)
	{
		// Deliberately NOT using addOnlyActiveRecordsFilter(): re-toggling IsDeliveryStop on the BPartner
		// must re-activate the existing constraint row instead of creating a parallel duplicate.
		return queryBL
				.createQueryBuilder(I_M_Shipment_Constraint.class)
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_Bill_BPartner_ID, billBPartnerId)
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_IsManual, true)
				.create()
				.firstOnly(I_M_Shipment_Constraint.class);
	}

	@Override
	public boolean hasActiveDeliveryStopConstraint(final int billBPartnerId)
	{
		// Trx-aware (thread-inherited) and uncached: must see constraint rows just saved in the current
		// transaction (e.g. by the C_BPartner_DeliveryStop interceptor) so the M_Shipment_Constraint
		// model interceptor can propagate the correct IsDeliveryStop value to M_ReceiptSchedule.
		// IShipmentConstraintsBL#getDeliveryStopShipmentConstraintId(int) uses newOutOfTrx() + @Cached
		// and therefore cannot be used from inside that interceptor — it would miss the in-flight row.
		return queryBL
				.createQueryBuilder(I_M_Shipment_Constraint.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_Bill_BPartner_ID, billBPartnerId)
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_IsDeliveryStop, true)
				.create()
				.anyMatch();
	}

	@Override
	public int updateReceiptScheduleDeliveryStopForBPartner(final int bpartnerId, final boolean isDeliveryStop)
	{
		return queryBL
				.createQueryBuilder(I_M_ReceiptSchedule.class)
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_Processed, false)
				.addNotEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_IsDeliveryStop, isDeliveryStop)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_M_ReceiptSchedule.COLUMNNAME_IsDeliveryStop, isDeliveryStop)
				.execute();
	}
}
