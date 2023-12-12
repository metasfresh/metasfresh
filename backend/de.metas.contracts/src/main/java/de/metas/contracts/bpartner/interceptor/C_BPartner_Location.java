/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.bpartner.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.bpartner.command.BPartnerLocationReplaceCommand;
import de.metas.contracts.bpartner.command.ContractLocationReplaceCommand;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

import static de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type.BILL_TO_DEFAULT;
import static de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO_DEFAULT;

@Component
@Callout(I_C_BPartner_Location.class)
@Interceptor(I_C_BPartner_Location.class)
public class C_BPartner_Location
{
	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String SYSCONFIG_UpdateFlatrateTerms = "Update_Flatrate_Term_Locations_Based_On_Default_Addresses";

	@Init
	public void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.contracts.bpartner.interceptor.C_BPartner_Location());
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = { I_C_BPartner_Location.COLUMNNAME_Previous_ID })
	public void updateNextLocation(final I_C_BPartner_Location bpLocation)
	{
		if (bpLocation.getPrevious_ID() > 0)
		{
			final BPartnerLocationId oldBPLocationId = BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getPrevious_ID());
			final BPartnerLocationId newBPLocationId = BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());

			BPartnerLocationReplaceCommand.builder()
					.oldBPLocationId(oldBPLocationId)
					.newBPLocationId(newBPLocationId)
					.newLocation(bpLocation)
					.saveNewLocation(false) // because it will be saved after validator is triggered
					.build()
					.execute();
		}
	}

	/**
	 * Prevents that a (the preceeding) location is terminated in the past.
	 * This is done by preventing that the current location's validFrom is set int he past.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_BPartner_Location.COLUMNNAME_ValidFrom })
	@CalloutMethod(columnNames = { I_C_BPartner_Location.COLUMNNAME_ValidFrom })
	public void noTerminationInPast(final I_C_BPartner_Location bpLocation)
	{
		final Timestamp validFrom = bpLocation.getValidFrom();
		if (validFrom != null && validFrom.before(Env.getDate()))
		{
			throw new AdempiereException("@AddressTerminatedInThePast@")
					.appendParametersToMessage()
					.setParameter("C_BPartner_Location.ValidFrom", validFrom)
					.setParameter("Env.getDate()", Env.getDate())
					.setParameter("C_BPartner_Location", bpLocation);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = { I_C_BPartner_Location.COLUMNNAME_IsShipToDefault, I_C_BPartner_Location.COLUMNNAME_IsBillToDefault })
	public void updateAddresses(@NonNull final I_C_BPartner_Location bpLocation)
	{
		updateExistingFlatrateTerms(bpLocation);
	}

	private void updateExistingFlatrateTerms(@NonNull final I_C_BPartner_Location bpLocation)
	{
		if (!bpLocation.isBillToDefault() && !bpLocation.isShipToDefault())
		{
			return;
		}

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(bpLocation.getC_BPartner_ID());

		if (bpLocation.isBillToDefault())
		{
			final Optional<I_C_BPartner_Location> oldDefaultRecord = bPartnerBL.retrieveBillToDefaultLocation(bPartnerId);

			if (isUpdateFlatrateTerms() && oldDefaultRecord.isPresent())
			{
				updateFlatrateTerms(BPartnerLocationId.ofRepoId(bPartnerId, bpLocation.getC_BPartner_Location_ID()),
									BPartnerLocationId.ofRepoId(bPartnerId, oldDefaultRecord.get().getC_BPartner_Location_ID()),
									BILL_TO_DEFAULT);
			}
		}

		if (bpLocation.isShipToDefault())
		{
			final Optional<I_C_BPartner_Location> oldDefaultRecord = bPartnerBL.retrieveShipToDefaultLocation(bPartnerId);

			if (isUpdateFlatrateTerms() && oldDefaultRecord.isPresent())
			{
				updateFlatrateTerms(BPartnerLocationId.ofRepoId(bPartnerId, bpLocation.getC_BPartner_Location_ID()),
									BPartnerLocationId.ofRepoId(bPartnerId, oldDefaultRecord.get().getC_BPartner_Location_ID()),
									SHIP_TO_DEFAULT);
			}
		}
	}

	private void updateFlatrateTerms(
			@NonNull final BPartnerLocationId newBPLocationId,
			@NonNull final BPartnerLocationId oldBPLocationId,
			@NonNull final IBPartnerDAO.BPartnerLocationQuery.Type type)
	{
		ContractLocationReplaceCommand.builder()
				.newBPLocationId(newBPLocationId)
				.oldBPLocationId(oldBPLocationId)
				.updateBillLocation(type == BILL_TO_DEFAULT)
				.updateDropShipLocation(type == SHIP_TO_DEFAULT)
				.build()
				.execute();
	}

	private boolean isUpdateFlatrateTerms()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_UpdateFlatrateTerms, false);
	}
}
