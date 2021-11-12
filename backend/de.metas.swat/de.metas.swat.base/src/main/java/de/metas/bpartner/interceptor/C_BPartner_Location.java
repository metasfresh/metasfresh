package de.metas.bpartner.interceptor;

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

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.location.LocationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;

@Validator(I_C_BPartner_Location.class)
public class C_BPartner_Location
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@Init
	public void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.bpartner.callout.C_BPartner_Location());
	}
	// metas-ts: Commenting out de.metas.terminable related code, because it assumes that the columns C_BPartner_Location.Next_ID and C_BPartner_Location.ValidTo exist
	/*
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = { I_C_BPartner_Location.COLUMNNAME_Next_ID })
	public void updateNextLocation(I_C_BPartner_Location bpLocation)
	{
		Services.get(IBPartnerBL.class).updateNextLocation(bpLocation);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = { I_C_BPartner_Location.COLUMNNAME_ValidTo })
	public void noTerminationInPast(I_C_BPartner_Location bpLocation)
	{
		if (Services.get(IBPartnerBL.class).isTerminatedInThePast(bpLocation))
		{
			throw new AdempiereException("@AddressTerminatedInThePast@");
		}
	}
	*/

	/**
	 * Update {@link I_C_BPartner_Location#COLUMNNAME_Address} field right before new. Updating on TYPE_BEFORE_CHANGE is not needed because C_Location_ID is not changing and if user edits the address,
	 * then {@link org.adempiere.bpartner.callout.BPartnerLocation#evalInput(GridTab)} is managing the case.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void updateAddressString(I_C_BPartner_Location bpLocation)
	{
		Services.get(IBPartnerBL.class).setAddress(bpLocation);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_BPartner_Location.COLUMNNAME_C_Location_ID)
	public void updateWarehouseLocation(@NonNull final I_C_BPartner_Location bpLocation)
	{
		final LocationId newLocationId = LocationId.ofRepoId(bpLocation.getC_Location_ID());

		final I_C_BPartner_Location bpLocationOld = InterfaceWrapperHelper.createOld(bpLocation, I_C_BPartner_Location.class);
		final LocationId oldLocationId = LocationId.ofRepoIdOrNull(bpLocationOld.getC_Location_ID());

		warehouseBL.updateWarehouseLocation(oldLocationId, newLocationId);
	}
}
