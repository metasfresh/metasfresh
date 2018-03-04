package de.metas.ui.web.handlingunits.process;



import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.X_M_InOut;

import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class WEBUI_M_InOut_Shipment_SelectHUs extends JavaProcess implements IProcessPrecondition
{

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		
			if (context.isNoSelection())
			{
				return ProcessPreconditionsResolution.rejectBecauseNoSelection();
			}
			if (!context.isSingleSelection())
			{
				return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
			}

			final I_M_InOut shipment = context.getSelectedModel(I_M_InOut.class);

			// guard against null (might happen if the selected ID is not valid)
			if (shipment == null)
			{
				return ProcessPreconditionsResolution.rejectBecauseNoSelection();
			}

			return checkEligibleForHUsSelection(shipment);
		}
	
	/** @return true if given shipment is eligible for HU selection */
	public static final ProcessPreconditionsResolution checkEligibleForHUsSelection(final I_M_InOut shipment)
	{
		// shipment must be completed or closed closed
		final String docStatus = shipment.getDocStatus();
		if(!(docStatus.equals(X_M_InOut.DOCSTATUS_Completed)  || docStatus.equals(X_M_InOut.DOCSTATUS_Closed)))
		{
			return ProcessPreconditionsResolution.reject("shipment not completed");
		}

		final List<I_M_HU> shipmentHandlingUnits = Services.get(IHUInOutDAO.class).retrieveShippedHandlingUnits(shipment);
		
		if(shipmentHandlingUnits.isEmpty())
		{
			return ProcessPreconditionsResolution.reject("shipment has no handling units assigned");
		}

		return ProcessPreconditionsResolution.accept();

	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_InOut shipment = getRecord(I_M_InOut.class);
		final List<I_M_HU> shipmentHandlingUnits = Services.get(IHUInOutDAO.class).retrieveShippedHandlingUnits(shipment);
		
		getResult().setRecordsToOpen(TableRecordReference.ofCollection(shipmentHandlingUnits));

		return MSG_OK;
	}

}
