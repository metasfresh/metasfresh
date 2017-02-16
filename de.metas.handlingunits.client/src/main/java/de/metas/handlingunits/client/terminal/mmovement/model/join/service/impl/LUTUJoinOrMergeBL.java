package de.metas.handlingunits.client.terminal.mmovement.model.join.service.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.allocation.transfer.IHUJoinBL;
import de.metas.handlingunits.allocation.transfer.ITUMergeBuilder;
import de.metas.handlingunits.allocation.transfer.impl.TUMergeBuilder;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;
import de.metas.handlingunits.client.terminal.mmovement.model.join.ILUTUJoinKey;
import de.metas.handlingunits.client.terminal.mmovement.model.join.service.ILUTUJoinOrMergeBL;
import de.metas.handlingunits.exceptions.NoCompatibleHUItemParentFoundException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;

public class LUTUJoinOrMergeBL implements ILUTUJoinOrMergeBL
{
	@Override
	public List<I_M_HU> joinOrMergeHUs(final ITerminalContext terminalCtx, final ILUTUJoinKey luKey, final List<? extends ILUTUJoinKey> tuKeys)
	{
		final List<I_M_HU> joinedHUs = new ArrayList<I_M_HU>();

		Services.get(IHUTrxBL.class)
				.createHUContextProcessorExecutor(terminalCtx)
				.run(new IHUContextProcessor()
				{

					@Override
					public IMutableAllocationResult process(final IHUContext huContext)
					{
						final List<I_M_HU> joinedHUsInTrx = new ArrayList<>();

						final I_M_HU luHU = luKey.getM_HU();
						// pre-add the LU-HU to the result
						joinedHUsInTrx.add(luHU);

						for (final ILUTUJoinKey tuKey : tuKeys)
						{
							final I_M_HU tuHU = tuKey.getM_HU();
							try
							{
								joinOrMergeHU0(huContext, luHU, tuHU);

								// Add processed TU-HU to the result
								joinedHUsInTrx.add(tuHU);
							}
							catch (final NoCompatibleHUItemParentFoundException ncipe)
							{
								throw new MaterialMovementException("@" + NoCompatibleHUItemParentFoundException.ERR_TU_NOT_ALLOWED_ON_LU + "@ " + tuKey.getName(), ncipe);
							}
						}

						//
						// Make created HUs to be out-of-transaction to be used elsewhere
						for (final I_M_HU joinedHU : joinedHUsInTrx)
						{
							InterfaceWrapperHelper.setTrxName(joinedHU, ITrx.TRXNAME_None);
							joinedHUs.add(joinedHU);

						}

						return NULL_RESULT;
					}
				});

		return joinedHUs;
	}

	@VisibleForTesting
	/* package */ boolean joinOrMergeHU0(final IHUContext huContext, final I_M_HU luHU, final I_M_HU tuHU)
	{
		final IHUJoinBL huJoinBL = Services.get(IHUJoinBL.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		if (handlingUnitsBL.isAggregateHU(tuHU))
		{
			final IHUStorage huStorage = huContext.getHUStorageFactory().getStorage(tuHU);

			final List<IHUProductStorage> storages = huStorage.getProductStorages();

			// merge for each product storage.
			// note: right now I can't imagine having multiple storages in an aggregate HU, but if we have, it shall not be a problem in here
			for (final IHUProductStorage storage : storages)
			{
				final ITUMergeBuilder mergeBuilder = new TUMergeBuilder(huContext);
				mergeBuilder.setSourceHUs(ImmutableList.of(tuHU));
				mergeBuilder.setTargetTU(luHU);

				mergeBuilder.setCUProduct(storage.getM_Product());
				mergeBuilder.setCUUOM(storage.getC_UOM());
				mergeBuilder.setCUQty(storage.getQty());
				mergeBuilder.setCUTrxReferencedModel(storage.getM_HU());

				mergeBuilder.mergeTUs();
			}
			return false;
		}
		else
		{
			// Move TU inside the LU
			huJoinBL.assignTradingUnitToLoadingUnit(huContext, luHU, tuHU);
			return true;
		}
	}
}
