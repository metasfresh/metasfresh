/**
 * 
 */
package de.metas.tourplanning.process;

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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;
import de.metas.tourplanning.api.IDeliveryDayGenerator;
import de.metas.tourplanning.api.ITourBL;
import de.metas.tourplanning.model.I_M_Tour;

/**
 * @author cg
 */
public class GenerateDeliveryDays extends JavaProcess
{
	//
	// Services
	private final ITourBL tourBL = Services.get(ITourBL.class);

	//
	// Parameters
	private static final String PARAM_DateFrom = "DateFrom";
	private static final String PARAM_DateTo = "DateTo";
	private Timestamp p_DateFrom = null;
	private Timestamp p_DateTo = null;
	private I_M_Tour p_tour = null;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (PARAM_DateFrom.equals(name))
			{
				p_DateFrom = (Timestamp)para.getParameter();
			}
			else if (PARAM_DateTo.equals(name))
			{
				p_DateTo = (Timestamp)para.getParameter();
			}
		}

		if (I_M_Tour.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			p_tour = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_M_Tour.class, ITrx.TRXNAME_None);
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		Check.assumeNotNull(p_DateFrom, "p_DateFrom not null");
		Check.assumeNotNull(p_DateTo, "p_DateTo not null");
		Check.assumeNotNull(p_tour, "p_tour not null");

		//
		// Create generator instance and configure it
		final IDeliveryDayGenerator generator = tourBL.createDeliveryDayGenerator(this);
		generator.setDateFrom(p_DateFrom);
		generator.setDateTo(p_DateTo);
		generator.setM_Tour(p_tour);

		//
		// Generate delivery days
		generator.generate(getTrxName());
		final int countGeneratedDeliveryDays = generator.getCountGeneratedDeliveryDays();

		//
		// Return result
		return "@Created@ #" + countGeneratedDeliveryDays;
	}
}
