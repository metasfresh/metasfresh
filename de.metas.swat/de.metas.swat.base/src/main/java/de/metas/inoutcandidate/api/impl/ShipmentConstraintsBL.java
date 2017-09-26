package de.metas.inoutcandidate.api.impl;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;

import com.google.common.base.Preconditions;

import de.metas.inoutcandidate.api.IShipmentConstraintsBL;
import de.metas.inoutcandidate.api.ShipmentConstraintCreateRequest;
import de.metas.inoutcandidate.model.I_M_Shipment_Constraint;

/*
 * #%L
 * de.metas.swat.base
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

public class ShipmentConstraintsBL implements IShipmentConstraintsBL
{
	@Override
	public void createConstraint(final ShipmentConstraintCreateRequest request)
	{
		final I_M_Shipment_Constraint constraintPO = InterfaceWrapperHelper.newInstance(I_M_Shipment_Constraint.class);
		constraintPO.setBill_BPartner_ID(request.getBillPartnerId());
		constraintPO.setSourceDoc_Table_ID(request.getSourceDocRef().getAD_Table_ID());
		constraintPO.setSourceDoc_Record_ID(request.getSourceDocRef().getRecord_ID());

		//
		// Constraints
		if (request.isDeliveryStop())
		{
			constraintPO.setIsDeliveryStop(true);
		}

		InterfaceWrapperHelper.save(constraintPO);

		// NOTE: affected shipment schedules will be invalidated by a model intercepter.
		// We are doing it there and not here because we also want to have a window where user can change those constraints (e.g. inactivate some of those),
		// and in that case we shall also react and invalidate.
	}

	@Override
	@Cached(cacheName = I_M_Shipment_Constraint.Table_Name + "#IsDeliveryStop")
	public int getDeliveryStopShipmentConstraintId(final int billBPartnerId)
	{
		Preconditions.checkArgument(billBPartnerId > 0, "bpartnerId > 0");

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Shipment_Constraint.class, PlainContextAware.newOutOfTrx())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMN_Bill_BPartner_ID, billBPartnerId)
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMN_IsDeliveryStop, true)
				.orderBy()
				.addColumn(I_M_Shipment_Constraint.COLUMN_M_Shipment_Constraint_ID)
				.endOrderBy()
				.create()
				.firstId();
	}
}
