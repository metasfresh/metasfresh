/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.bpartner.interceptor;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.bpartner.process.C_BPartner_MoveToAnotherOrg_PostalChange;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.location.PostalId;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONTriggerAction;
import de.metas.ui.web.window.model.lookup.zoom_into.DocumentZoomIntoService;
import de.metas.ui.web.window.model.lookup.zoom_into.DocumentZoomIntoInfo;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Interceptor(I_C_BPartner_Location.class)
@Component
@RequiredArgsConstructor
public class C_BPartner_Location
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@NonNull private final DocumentZoomIntoService documentZoomIntoService;

	private static final String SYSCONFIG_AskForOrgChangeOnRegionChange = "AskForOrgChangeOnRegionChange";

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_BPartner_Location.COLUMNNAME_C_Location_ID)
	public void afterChange(@NonNull final I_C_BPartner_Location bpLocation)
	{
		final LocationId newLocationId = LocationId.ofRepoId(bpLocation.getC_Location_ID());
		final I_C_BPartner_Location bpLocationOld = InterfaceWrapperHelper.createOld(bpLocation, I_C_BPartner_Location.class);
		final LocationId oldLocationId = LocationId.ofRepoIdOrNull(bpLocationOld.getC_Location_ID());

		showOrgChangeModalIfNeeded(bpLocation, newLocationId, oldLocationId);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_BPartner_Location.COLUMNNAME_Previous_ID)
	public void afterPreviousIdChange(@NonNull final I_C_BPartner_Location bpLocation)
	{
		final LocationId newLocationId = LocationId.ofRepoId(bpLocation.getC_Location_ID());
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(bpLocation.getC_BPartner_ID(), bpLocation.getPrevious_ID());
		if (bpartnerLocationId == null)
		{
			return;
		}

		final I_C_BPartner_Location bpLocationOld = bPartnerDAO.getBPartnerLocationByIdEvenInactive(bpartnerLocationId);
		if (bpLocationOld == null)
		{
			return;
		}
		final LocationId oldLocationId = LocationId.ofRepoIdOrNull(bpLocationOld.getC_Location_ID());

		showOrgChangeModalIfNeeded(bpLocation, newLocationId, oldLocationId);
	}

	private void showOrgChangeModalIfNeeded(final @NonNull I_C_BPartner_Location bpLocation, final LocationId newLocationId, @Nullable final LocationId oldLocationId)
	{
		if (Execution.isCurrentExecutionAvailable()
				&& isAskForOrgChangeOnRegionChange())
		{
			final I_C_Location newLocation = locationDAO.getById(newLocationId);
			final PostalId newPostalId = PostalId.ofRepoIdOrNull(newLocation.getC_Postal_ID());

			if (newPostalId == null)
			{
				// nothing to do
				return;
			}

			final I_C_Location oldLocation = oldLocationId != null ? locationDAO.getById(oldLocationId) : null;
			final PostalId oldPostalId = oldLocationId == null ? null : PostalId.ofRepoIdOrNull(oldLocation.getC_Postal_ID());

			if (newPostalId.equals(oldPostalId))
			{
				// nothing to do
				return;
			}

			final I_C_Postal newPostalRecord = locationDAO.getPostalById(newPostalId);
			final I_C_Postal oldPostalRecord = oldPostalId == null ? null : locationDAO.getPostalById(oldPostalId);

			if (oldPostalRecord == null ||
					newPostalRecord.getAD_Org_InCharge_ID() != oldPostalRecord.getAD_Org_InCharge_ID())
			{
				Execution.getCurrent().requestFrontendToTriggerAction(moveToAnotherOrgTriggerAction(bpLocation));
			}
		}
	}

	private JSONTriggerAction moveToAnotherOrgTriggerAction(final @NonNull I_C_BPartner_Location bpLocation)
	{
		return JSONTriggerAction.startProcess(
				getMoveToAnotherOrgProcessId(),
				getBPartnerDocumentPath(bpLocation));
	}

	private boolean isAskForOrgChangeOnRegionChange()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_AskForOrgChangeOnRegionChange, false);
	}

	private ProcessId getMoveToAnotherOrgProcessId()
	{
		final AdProcessId adProcessId = adProcessDAO.retrieveProcessIdByClass(C_BPartner_MoveToAnotherOrg_PostalChange.class);
		return ProcessId.ofAD_Process_ID(adProcessId);
	}

	private DocumentPath getBPartnerDocumentPath(@NonNull final I_C_BPartner_Location bpLocation)
	{
		return documentZoomIntoService.getDocumentPath(DocumentZoomIntoInfo.of(I_C_BPartner.Table_Name, bpLocation.getC_BPartner_ID()));
	}

}
