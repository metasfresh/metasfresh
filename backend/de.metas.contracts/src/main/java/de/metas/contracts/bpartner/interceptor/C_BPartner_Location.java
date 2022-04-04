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

import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.bpartner.command.BPartnerLocationReplaceCommand;
import de.metas.util.Services;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;

import java.sql.Timestamp;

@Validator(I_C_BPartner_Location.class)
public class C_BPartner_Location
{
	@Init
	public void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.contracts.bpartner.interceptor.C_BPartner_Location());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_BPartner_Location.COLUMNNAME_Previous_ID }, afterCommit = true)
	public void updateNextLocation(final I_C_BPartner_Location bpLocation)
	{
		if (bpLocation.getPrevious_ID() >= 0)
		{
			final BPartnerLocationId oldBPLocationId = BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getPrevious_ID());
			final BPartnerLocationId newBPLocationId = BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());

			BPartnerLocationReplaceCommand.builder()
					.oldBPLocationId(oldBPLocationId)
					.newBPLocationId(newBPLocationId)
					.build()
					.execute();
		}
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
}
