package de.metas.tourplanning.model.validator;

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


import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;

import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.tourplanning.api.ITourInstanceBL;
import de.metas.tourplanning.api.ITourInstanceDAO;
import de.metas.tourplanning.api.ITourInstanceQueryParams;
import de.metas.tourplanning.api.impl.PlainTourInstanceQueryParams;
import de.metas.tourplanning.model.I_M_Tour_Instance;
import de.metas.util.Services;

@Interceptor(I_M_ShipperTransportation.class)
public class M_ShipperTransportation
{
	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void processTourInstance(final I_M_ShipperTransportation shipperTransportation)
	{
		final I_M_Tour_Instance tourInstance = retrieveTourInstanceOrNull(shipperTransportation);

		// No Tour Instance => nothing to do
		if (tourInstance == null)
		{
			return;
		}

		Services.get(ITourInstanceBL.class).process(tourInstance);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE })
	public void unprocessTourInstance(final I_M_ShipperTransportation shipperTransportation)
	{
		final I_M_Tour_Instance tourInstance = retrieveTourInstanceOrNull(shipperTransportation);

		// No Tour Instance => nothing to do
		if (tourInstance == null)
		{
			return;
		}

		Services.get(ITourInstanceBL.class).unprocess(tourInstance);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT })
	public void prohibitVoidingIfTourInstanceExists(final I_M_ShipperTransportation shipperTransportation)
	{
		final I_M_Tour_Instance tourInstance = retrieveTourInstanceOrNull(shipperTransportation);

		// No Tour Instance => nothing to do
		if (tourInstance == null)
		{
			return;
		}

		throw new AdempiereException("@NotAllowed@ (@M_Tour_Instance_ID@: " + tourInstance + ")");
	}

	private I_M_Tour_Instance retrieveTourInstanceOrNull(final I_M_ShipperTransportation shipperTransportation)
	{
		final Object contextProvider = shipperTransportation;
		final ITourInstanceQueryParams params = new PlainTourInstanceQueryParams();
		params.setM_ShipperTransportation_ID(shipperTransportation.getM_ShipperTransportation_ID());
		final I_M_Tour_Instance tourInstance = Services.get(ITourInstanceDAO.class).retrieveTourInstance(contextProvider, params);
		return tourInstance;
	}
}
