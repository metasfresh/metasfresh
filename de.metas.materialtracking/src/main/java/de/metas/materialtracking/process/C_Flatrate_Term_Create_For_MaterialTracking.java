package de.metas.materialtracking.process;

import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_BPartner;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.process.C_Flatrate_Term_Create;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.ch.lagerkonf.interfaces.I_C_Flatrate_Conditions;
import de.metas.materialtracking.ch.lagerkonf.interfaces.I_M_Material_Tracking;
import de.metas.process.ISvrProcessPrecondition;

/*
 * #%L
 * de.metas.materialtracking
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

public class C_Flatrate_Term_Create_For_MaterialTracking extends C_Flatrate_Term_Create
		implements ISvrProcessPrecondition
{

	private int p_flatrateconditionsID;

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		if(I_M_Material_Tracking.Table_Name.equals(context.getTableName()))
		{
			final I_M_Material_Tracking materialTracking = context.getModel(I_M_Material_Tracking.class);

			// no need to create a new flatrate term if it is already set
			if (materialTracking.getC_Flatrate_Term_ID() > 0)
			{
				return false;
			}

			final List<I_C_Flatrate_Term> existingTerm = Services.get(IMaterialTrackingDAO.class).retrieveC_Flatrate_Terms_For_MaterialTracking(materialTracking);

			// the term exists. It just has to be set
			if (!existingTerm.isEmpty())
			{
				return false;
			}

			// create the flatrate term only if it doesn't exist
			return true;

		}
		return false;
	}

	@Override
	protected void prepare()
	{
		final IParams para = getParameterAsIParams();

		p_flatrateconditionsID = para.getParameterAsInt(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID);
		final int materialTrackingID = getRecord_ID();
		final I_M_Material_Tracking materialTracking = InterfaceWrapperHelper.create(getCtx(), materialTrackingID, I_M_Material_Tracking.class, getTrxName());

		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.create(getCtx(), p_flatrateconditionsID, I_C_Flatrate_Conditions.class, getTrxName());

		setConditions(conditions);
		setProduct(materialTracking.getM_Product());
		setStartDate(materialTracking.getValidFrom());
		setEndDate(materialTracking.getValidTo());
		setUserInCharge(materialTracking.getSalesRep());
	}

	@Override
	public Iterator<I_C_BPartner> getBPartners()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final I_M_Material_Tracking materialTracking = getProcessInfo().getRecord(I_M_Material_Tracking.class);

		// made this iterator to fit he superclass method
		final I_C_BPartner partner = materialTracking.getC_BPartner();
		return queryBL.createQueryBuilder(I_C_BPartner.class, materialTracking)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID())
				.create()
				.iterate(I_C_BPartner.class);

	}

}
