package de.metas.inoutcandidate.process;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.sql.Timestamp;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

public class M_ShipmentSchedule_ChangeDeliveryDate extends SvrProcess
{

	public static final String PARAM_DeliveryDate = "DeliveryDate";

	public static final String PARAM_PreparationDate = "PreparationDate";

	private Timestamp p_deliveryDate;
	private Timestamp p_preparationDate = null;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				continue;
			}
			final String parameterName = para.getParameterName();

			if (PARAM_DeliveryDate.equals(parameterName))
			{
				p_deliveryDate = para.getParameterAsTimestamp();
			}
			if (PARAM_PreparationDate.equals(parameterName))
			{
				p_preparationDate = para.getParameterAsTimestamp();
			}
		}

		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		final IQueryFilter<I_M_ShipmentSchedule> userSelectionFilter = getProcessInfo().getQueryFilter();
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilderForShipmentSchedulesSelection = shipmentSchedulePA.createQueryForShipmentScheduleSelection(getCtx(), userSelectionFilter);
		
		final int adPInstance_ID = getAD_PInstance_ID();
		//
		// Create selection and return how many items were added
		Check.assume(adPInstance_ID > 0, "adPInstanceId > 0");
	
		final int selectionCount =  queryBuilderForShipmentSchedulesSelection
				.create()
				.createSelection(adPInstance_ID);
		
		if (selectionCount <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}

	}

	@Override
	protected String doIt() throws Exception
	{
		final int adPInstanceId = getAD_PInstance_ID();
		Check.assume(adPInstanceId > 0, "adPInstanceId > 0");

		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		final String trxName = getTrxName();

		Check.assume(adPInstanceId > 0, "adPInstanceId > 0");

		// update delivery date
		// no invalidation
		shipmentSchedulePA.updateDeliveryDate_Override(p_deliveryDate, adPInstanceId, trxName);

		// In case preparation date is set as parameter, update the preparation date too

		if (p_preparationDate != null)
		{
			// no invalidation. Just set the preparation date that was given
			shipmentSchedulePA.updatePreparationDate_Override(p_preparationDate, adPInstanceId, trxName);
		}
		else
		{
			// Initially, set null in preparation date. It will be calculated later, during the updating process.
			// This update will invalidate the selected schedules.
			shipmentSchedulePA.updatePreparationDate_Override(null, adPInstanceId, trxName);
		}

		return MSG_OK;

	}
}
