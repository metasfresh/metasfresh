/*
 * #%L
 * de.metas.salescandidate.base
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

package de.metas.ordercandidate.location;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.api.IBPRelationDAO;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.interfaces.I_C_BP_Relation;
import de.metas.logging.TableRecordMDC;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Properties;

@Component
public class OLCandLocationsUpdaterService
{
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
	private final IBPRelationDAO bpRelationDAO = Services.get(IBPRelationDAO.class);
	private final IDocumentLocationBL documentLocationBL;

	public OLCandLocationsUpdaterService(@NonNull final IDocumentLocationBL documentLocationBL)
	{
		this.documentLocationBL = documentLocationBL;
	}

	public void updateCapturedLocations(final I_C_OLCand olCand)
	{
		OLCandLocationsUpdater.builder()
				.documentLocationBL(documentLocationBL)
				.record(olCand)
				.build()
				.updateAllIfNeeded();
	}

	public void updateBPartnerLocationOverride(final I_C_OLCand olCand)
	{
		try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(olCand))
		{
			setBPLocationOverride(olCand, computeBPLocationOverride(olCand));
		}
	}

	@Nullable
	private I_C_BPartner_Location computeBPLocationOverride(final I_C_OLCand olCand)
	{
		final BPartnerId bpartnerOverrideId = BPartnerId.ofRepoIdOrNull(olCand.getC_BPartner_Override_ID());
		if (bpartnerOverrideId == null)
		{
			// in case the bpartner Override was deleted, also delete the bpartner Location Override
			return null;
		}
		else
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
			final String trxName = InterfaceWrapperHelper.getTrxName(olCand);
			return bPartnerDAO.retrieveShipToLocation(ctx, bpartnerOverrideId.getRepoId(), trxName);
		}
	}

	private static void setBPLocationOverride(final I_C_OLCand olCand, final I_C_BPartner_Location bpLocationOverride)
	{
		olCand.setC_BP_Location_Override_ID(bpLocationOverride != null ? bpLocationOverride.getC_BPartner_Location_ID() : -1);
		olCand.setC_BP_Location_Override_Value_ID(bpLocationOverride != null ? bpLocationOverride.getC_Location_ID() : -1);
	}

	public void updateDropShipLocationOverride(final I_C_OLCand olCand)
	{
		try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(olCand))
		{
			setDropShipLocationOverride(olCand, computeDropShipLocationOverride(olCand));
		}
	}

	private I_C_BPartner_Location computeDropShipLocationOverride(final I_C_OLCand olCand)
	{
		final BPartnerId dropShipPartnerOverrideId = BPartnerId.ofRepoIdOrNull(olCand.getDropShip_BPartner_Override_ID());
		if (dropShipPartnerOverrideId == null)
		{
			// in case the drop-ship bpartner Override was deleted, also delete the drop-ship Location Override
			return null;
		}
		else
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
			final String trxName = InterfaceWrapperHelper.getTrxName(olCand);
			return bPartnerDAO.retrieveShipToLocation(ctx, dropShipPartnerOverrideId.getRepoId(), trxName);
		}
	}

	private static void setDropShipLocationOverride(@NonNull final I_C_OLCand olCand, @Nullable final I_C_BPartner_Location dropShipLocation)
	{
		olCand.setDropShip_Location_Override_ID(dropShipLocation != null ? dropShipLocation.getC_BPartner_Location_ID() : -1);
		olCand.setDropShip_Location_Override_Value_ID(dropShipLocation != null ? dropShipLocation.getC_Location_ID() : -1);
	}

	public void updateHandoverLocationOverride(@NonNull final I_C_OLCand olCand)
	{
		try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(olCand))
		{
			setHandOverLocationOverride(olCand, computeHandoverLocationOverride(olCand));
		}
	}

	private I_C_BPartner_Location computeHandoverLocationOverride(@NonNull final I_C_OLCand olCand)
	{
		final BPartnerId handOverPartnerOverrideId = BPartnerId.ofRepoIdOrNull(olCand.getHandOver_Partner_Override_ID());
		if (handOverPartnerOverrideId == null)
		{
			// in case the handover bpartner Override was deleted, also delete the handover Location Override
			return null;
		}
		else
		{
			final I_C_BPartner partner = olCandEffectiveValuesBL.getC_BPartner_Effective(olCand);
			final I_C_BPartner handoverPartnerOverride = bPartnerDAO.getById(handOverPartnerOverrideId);
			final I_C_BP_Relation handoverRelation = bpRelationDAO.retrieveHandoverBPRelation(partner, handoverPartnerOverride);
			if (handoverRelation == null)
			{
				// this shall never happen, since both Handover_BPartner and Handover_BPartner_Override must come from such a bpp relation.
				// but I will leave this condition here as extra safety
				return null;
			}
			else
			{
				final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(handoverRelation.getC_BPartnerRelation_ID(), handoverRelation.getC_BPartnerRelation_Location_ID());
				return bPartnerDAO.getBPartnerLocationByIdEvenInactive(bPartnerLocationId);
			}
		}
	}

	private static void setHandOverLocationOverride(
			@NonNull final I_C_OLCand olCand,
			@Nullable final I_C_BPartner_Location handOverLocation)
	{
		olCand.setHandOver_Location_Override_ID(handOverLocation != null ? handOverLocation.getC_BPartner_Location_ID() : -1);
		olCand.setHandOver_Location_Override_Value_ID(handOverLocation != null ? handOverLocation.getC_Location_ID() : -1);
	}
}
