package de.metas.procurement.base.process;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.process.SvrProcess;

import de.metas.adempiere.service.ICalendarDAO;
import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.interfaces.I_C_BPartner;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.process.Param;
import de.metas.process.Process;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
@Process(requiresCurrentRecordWhenCalledFromGear = false)
public class C_Flatrate_Term_Create_ProcurementContract extends SvrProcess
{
	@Param(mandatory = true, parameterName = "C_Flatrate_Conditions_ID")
	private I_C_Flatrate_Conditions p_C_Flatrate_Conditions;

	@Param(mandatory = true, parameterName = "C_BPartner_ID")
	private I_C_BPartner p_C_BPartner;

	@Param(mandatory = true, parameterName = "Dates")
	private Timestamp p_StartDate;

	@Param(mandatory = true, parameterName = "Dates", parameterTo = true)
	private Timestamp p_EndDate;

	@Param(mandatory = true, parameterName = "M_Product_ID")
	private I_M_Product p_M_Product;

	@Param(mandatory = true, parameterName = "C_UOM_ID")
	private I_C_UOM p_C_UOM;

	@Param(mandatory = false, parameterName = "AD_User_InCharge_ID")
	private I_AD_User p_AD_User_Incharge;

	@Param(mandatory = true, parameterName = "C_Currency_ID")
	private I_C_Currency p_C_Currency;

	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final ICalendarDAO calendarDAO = Services.get(ICalendarDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		// create the term
		final boolean completeIt = false;

		final I_C_Flatrate_Term term = flatrateBL.createTerm(p_C_BPartner, p_C_Flatrate_Conditions, p_StartDate, p_AD_User_Incharge, p_M_Product, completeIt);
		if (term == null)
		{
			return "@Success@"; // the process messages will display what went wrong
		}

		term.setC_Currency(p_C_Currency);
		term.setC_UOM(p_C_UOM);
		term.setM_Product(p_M_Product);
		term.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_Laufend);

		InterfaceWrapperHelper.save(term);

		// create the dataEntries
		final I_C_Calendar calendar = term.getC_Flatrate_Conditions().getC_Flatrate_Transition().getC_Calendar_Contract();

		final List<I_C_Period> periods = calendarDAO.retrievePeriods(getCtx(), calendar, p_StartDate, p_EndDate, ITrx.TRXNAME_None);
		for (final I_C_Period period : periods)
		{
			final I_C_Flatrate_DataEntry newDataEntry = InterfaceWrapperHelper.newInstance(I_C_Flatrate_DataEntry.class, this);
			newDataEntry.setC_Period(period);
			newDataEntry.setM_Product_DataEntry(term.getM_Product());
			newDataEntry.setC_Currency(term.getC_Currency());
			newDataEntry.setC_Flatrate_Term(term);
			newDataEntry.setC_UOM(term.getC_UOM());
			newDataEntry.setType(I_C_Flatrate_DataEntry.TYPE_Procurement_PeriodBased);
			InterfaceWrapperHelper.save(newDataEntry);
		}

		return "@Success@";
	}
}
