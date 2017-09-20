package de.metas.handlingunits.picking.pickingCandidateCommands;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.IQuery;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.handlingunits.picking.SourceHUsRepository;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
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

	private final SourceHUsRepository sourceHUsRepository;

	public SetCandidatesInProgress(@NonNull final SourceHUsRepository sourceHUsRepository)
	{
		this.sourceHUsRepository = sourceHUsRepository;
	}

	public int perform(@NonNull final List<Integer> huIds)
	{
		if (huIds.isEmpty())
		{
			return 0;
		}
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final Collection<I_M_HU> sourceHUs = sourceHUsRepository.retrieveSourceHUsViaTracing(huIds);
		for (final I_M_HU sourceHU : sourceHUs)
		{
			if (!handlingUnitsBL.isDestroyed(sourceHU))
			{
				continue;
			}
			restoreHu(sourceHU);
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Picking_Candidate> query = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_PR)
				.addInArrayFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huIds)
				.create();

		final ICompositeQueryUpdater<I_M_Picking_Candidate> updater = queryBL.createCompositeQueryUpdater(I_M_Picking_Candidate.class)
				.addSetColumnValue(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_IP);

		return query.updateDirectly(updater);
	}

	/**
	 * Restores the given destroyed HU from the snapshot ID stored in its {@link I_M_Source_HU} record.
	 * 
	 * @param sourceHU
	 */
	private void restoreHu(@NonNull final I_M_HU sourceHU)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IHUSnapshotDAO huSnapshotDAO = Services.get(IHUSnapshotDAO.class);

		final I_M_Source_HU sourceHuRecord = queryBL.createQueryBuilder(I_M_Source_HU.class)
				.addEqualsFilter(I_M_Source_HU.COLUMN_M_HU_ID, sourceHU.getM_HU_ID())
				.create()
				.firstOnly(I_M_Source_HU.class);
		if (sourceHuRecord == null)
		{
			return;
		}

		huSnapshotDAO.restoreHUs()
				.addModel(sourceHU)
				.setContext(PlainContextAware.newWithThreadInheritedTrx())
				.setDateTrx(SystemTime.asDate())
				.setSnapshotId(sourceHuRecord.getPreDestroy_Snapshot_UUID())
				.restoreFromSnapshot();

		sourceHuRecord.setPreDestroy_Snapshot_UUID(null);
		save(sourceHuRecord);

	}

}
