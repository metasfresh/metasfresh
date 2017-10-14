package de.metas.handlingunits.picking.pickingCandidateCommands;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.util.Env;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class SetCandidatesInProgress
{
	private static final String MSG_WEBUI_PICKING_WRONG_HU_STATUS_3P = "WEBUI_Picking_Wrong_HU_Status";
	private static final String MSG_WEBUI_PICKING_ALREADY_SHIPPED_2P = "WEBUI_Picking_Already_Shipped";

	private final HuId2SourceHUsService sourceHUsRepository;

	public SetCandidatesInProgress(@NonNull final HuId2SourceHUsService sourceHUsRepository)
	{
		this.sourceHUsRepository = sourceHUsRepository;
	}

	public void perform(final int huId)
	{
		Preconditions.checkArgument(huId > 0, "huId > 0");

		final I_M_HU hu = load(huId, I_M_HU.class);
		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList = retrieveQtyPickedRecords(hu);

		assertHUIsNotShipped(hu, qtyPickedList);
		assertHUIsPicked(hu);

		// if everything is OK, go ahead
		qtyPickedList.forEach(InterfaceWrapperHelper::delete);
		updateHUStatusToActive(hu);

		restoreHUsFromSourceHUs(huId);

		markCandidatesAsInProgress(huId);
	}

	private List<I_M_ShipmentSchedule_QtyPicked> retrieveQtyPickedRecords(@NonNull final I_M_HU hu)
	{
		final List<I_M_ShipmentSchedule> scheds = Services.get(IQueryBL.class).createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.andCollect(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID)
				.create()
				.list();

		final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);

		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList = new ArrayList<>();
		for (final I_M_ShipmentSchedule shipmentSchedule : scheds)
		{
			qtyPickedList.addAll(huShipmentScheduleDAO.retrieveSchedsQtyPickedForTU(shipmentSchedule, hu, ITrx.TRXNAME_ThreadInherited));
		}
		return qtyPickedList;
	}

	/**
	 * Check if there is already a shipment.
	 * 
	 * @param hu
	 * @param qtyPickedList
	 */
	private static void assertHUIsNotShipped(
			@NonNull final I_M_HU hu,
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList)
	{
		for (final I_M_ShipmentSchedule_QtyPicked qtyPicked : qtyPickedList)
		{
			if (qtyPicked.getM_InOutLine_ID() > 0)
			{
				throw new AdempiereException(MSG_WEBUI_PICKING_ALREADY_SHIPPED_2P, new Object[] { hu.getValue(), qtyPicked.getM_InOutLine().getM_InOut().getDocumentNo() });
			}
		}
	}

	/**
	 * Generally verify the HU's status.
	 * 
	 * @param hu
	 */
	private void assertHUIsPicked(@NonNull final I_M_HU hu)
	{
		if (X_M_HU.HUSTATUS_Picked.equals(hu.getHUStatus()))
		{
			return;
		}

		final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
		final Properties ctx = Env.getCtx();
		final String currentStatusTrl = adReferenceDAO.retrieveListNameTrl(ctx, X_M_HU.HUSTATUS_AD_Reference_ID, hu.getHUStatus());
		final String pickedStatusTrl = adReferenceDAO.retrieveListNameTrl(ctx, X_M_HU.HUSTATUS_AD_Reference_ID, X_M_HU.HUSTATUS_Picked);
		throw new AdempiereException(MSG_WEBUI_PICKING_WRONG_HU_STATUS_3P, new Object[] { hu.getValue(), currentStatusTrl, pickedStatusTrl });
	}

	private void updateHUStatusToActive(@NonNull final I_M_HU hu)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final IMutableHUContext huContext = huContextFactory.createMutableHUContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
		handlingUnitsBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Active);
		handlingUnitsDAO.saveHU(hu);
	}

	private void restoreHUsFromSourceHUs(final int huId)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final Collection<I_M_HU> sourceHUs = sourceHUsRepository.retrieveActualSourceHUs(ImmutableList.of(huId));
		for (final I_M_HU sourceHU : sourceHUs)
		{
			if (!handlingUnitsBL.isDestroyed(sourceHU))
			{
				continue;
			}
			SourceHUsService.get().restoreHuFromSourceHuMarkerIfPossible(sourceHU);
		}
	}

	private int markCandidatesAsInProgress(final int huId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Picking_Candidate> query = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_PR)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.create();

		final ICompositeQueryUpdater<I_M_Picking_Candidate> updater = queryBL.createCompositeQueryUpdater(I_M_Picking_Candidate.class)
				.addSetColumnValue(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_IP);

		return query.updateDirectly(updater);
	}
}
