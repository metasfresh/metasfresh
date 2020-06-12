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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.tourplanning.api.IDeliveryDayGenerator;
import de.metas.tourplanning.api.ITourBL;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;

/**
 * @author cg
 */
public class GenerateDeliveryDays extends JavaProcess implements IProcessPrecondition
{
	//
	// Services
	private final ITourBL tourBL = Services.get(ITourBL.class);

	@Param(parameterName = "DateFrom")
	private Timestamp p_DateFrom;
	@Param(parameterName = "DateTo")
	private Timestamp p_DateTo;

	private I_M_Tour p_tour = null;
	private I_M_TourVersion p_tourVersion = null;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected void prepare()
	{
		if (I_M_Tour.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			p_tour = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_M_Tour.class, ITrx.TRXNAME_None);
		}
		if (I_M_TourVersion.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			p_tourVersion = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_M_TourVersion.class, ITrx.TRXNAME_None);
			p_tour = p_tourVersion.getM_Tour();
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
		generator.setDateFrom(TimeUtil.asLocalDate(p_DateFrom));
		generator.setDateTo(TimeUtil.asLocalDate(p_DateTo));
		generator.setM_Tour(p_tour);

		//
		// Generate delivery days
		if (p_tourVersion == null)
		{
			generator.generate(getTrxName());
		}
		else
		{
			generator.generateForTourVersion(getTrxName(), p_tourVersion);
		}
		final int countGeneratedDeliveryDays = generator.getCountGeneratedDeliveryDays();
		//
		// Return result
		return "@Created@ #" + countGeneratedDeliveryDays;
	}
}
