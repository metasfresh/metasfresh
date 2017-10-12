package de.metas.handlingunits.picking.pickingCandidateCommands;

import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.slf4j.Logger;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.logging.LogManager;
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

public class SetCandidatesProcessed
{
	private static final Logger logger = LogManager.getLogger(SetCandidatesProcessed.class);

	private final HuId2SourceHUsService sourceHUsRepository;

	public SetCandidatesProcessed(@NonNull final HuId2SourceHUsService sourceHUsRepository)
	{
		this.sourceHUsRepository = sourceHUsRepository;

	}

	public int perform(@NonNull final List<Integer> huIds)
	{
		if (huIds.isEmpty())
		{
			return 0;
		}

		final Collection<I_M_HU> sourceHuIds = sourceHUsRepository.retrieveActualSourceHUs(huIds);
		destroyEmptySourceHUs(sourceHuIds);

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Picking_Candidate> query = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_IP)
				.addInArrayFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huIds)
				.create();

		final ICompositeQueryUpdater<I_M_Picking_Candidate> updater = queryBL.createCompositeQueryUpdater(I_M_Picking_Candidate.class)
				.addSetColumnValue(I_M_Picking_Candidate.COLUMNNAME_Status, X_M_Picking_Candidate.STATUS_PR);

		return query.updateDirectly(updater);
	}

	private void destroyEmptySourceHUs(@NonNull final Collection<I_M_HU> sourceHus)
	{
		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();

		// clean up and unselect used up source HUs
		for (final I_M_HU sourceHu : sourceHus)
		{
			if (!storageFactory.getStorage(sourceHu).isEmpty())
			{
				return;
			}
			takeSnapShotAndDestroyHu(sourceHu);
		}
	}

	private void takeSnapShotAndDestroyHu(@NonNull final I_M_HU sourceHu)
	{
		final SourceHUsService sourceHuService = SourceHUsService.get();
		sourceHuService.snapshotHuIfMarkedAsSourceHu(sourceHu);

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsBL.destroyIfEmptyStorage(sourceHu);

		Check.errorUnless(handlingUnitsBL.isDestroyed(sourceHu), "We invoked IHandlingUnitsBL.destroyIfEmptyStorage on an HU with empty storage, but its not destroyed; hu={}", sourceHu);
		logger.info("Source M_HU with M_HU_ID={} is now destroyed", sourceHu.getM_HU_ID());
	}

}
