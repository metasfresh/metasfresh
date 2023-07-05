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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.location.LocationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;

import java.sql.Timestamp;

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

	/**
	 * Update {@link I_C_BPartner_Location#COLUMNNAME_Address} field right before new. Updating on TYPE_BEFORE_CHANGE is not needed because C_Location_ID is not changing and if user edits the address,
	 * then {@link de.metas.bpartner.callout.C_BPartner_Location#updateAddressString(I_C_BPartner_Location)} is managing the case.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void updateAddressString(final I_C_BPartner_Location bpLocation)
	{
		Services.get(IBPartnerBL.class).setAddress(bpLocation);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = { I_C_BPartner_Location.COLUMNNAME_Previous_ID })
	public void updateNextLocation(final I_C_BPartner_Location bpLocation)
	{
		Services.get(IBPartnerBL.class).updateFromPreviousLocationNoSave(bpLocation);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = { I_C_BPartner_Location.COLUMNNAME_ValidFrom })
	public void noTerminationInPast(final I_C_BPartner_Location bpLocation)
	{
		final Timestamp validFrom = bpLocation.getValidFrom();
		if (validFrom != null && validFrom.before(Env.getDate()))
		{
			throw new AdempiereException("@AddressTerminatedInThePast@");
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_BPartner_Location.COLUMNNAME_C_Location_ID)
	public void updateWarehouseLocation(@NonNull final I_C_BPartner_Location bpLocation)
	{
		final LocationId newLocationId = LocationId.ofRepoId(bpLocation.getC_Location_ID());

		final I_C_BPartner_Location bpLocationOld = InterfaceWrapperHelper.createOld(bpLocation, I_C_BPartner_Location.class);
		final LocationId oldLocationId = LocationId.ofRepoId(bpLocationOld.getC_Location_ID());

		warehouseBL.updateWarehouseLocation(oldLocationId, newLocationId);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void updateName(@NonNull final I_C_BPartner_Location bpLocation)
	{
		final String name = bpLocation.getName();
		if (!bpLocation.isNameReadWrite() || ".".equals(name))
		{
			updateBPLocationName(bpLocation);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void newName(@NonNull final I_C_BPartner_Location bpLocation)
	{
		updateBPLocationName(bpLocation);
	}

	private void updateBPLocationName(final @NonNull I_C_BPartner_Location bpLocation)
	{
		final int cBPartnerId = bpLocation.getC_BPartner_ID();

		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		bpLocation.setName(MakeUniqueNameCommand.builder()
								   .name(bpLocation.getName())
								   .address(bpLocation.getC_Location())
								   .companyName(bpartnerDAO.getBPartnerNameById(BPartnerId.ofRepoId(cBPartnerId)))
								   .existingNames(MakeUniqueNameCommand.getOtherLocationNames(cBPartnerId, bpLocation.getC_BPartner_Location_ID()))
								   .build()
								   .execute());
	}
}
