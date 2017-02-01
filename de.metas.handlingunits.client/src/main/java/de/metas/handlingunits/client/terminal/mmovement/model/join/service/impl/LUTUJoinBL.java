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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.allocation.transfer.IHUJoinBL;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;
import de.metas.handlingunits.client.terminal.mmovement.model.join.ILUTUJoinKey;
import de.metas.handlingunits.client.terminal.mmovement.model.join.service.ILUTUJoinBL;
import de.metas.handlingunits.exceptions.NoCompatibleHUItemParentFoundException;
import de.metas.handlingunits.model.I_M_HU;

public class LUTUJoinBL implements ILUTUJoinBL
{
	private final transient IHUJoinBL huJoinBL = Services.get(IHUJoinBL.class);

	@Override
	public List<I_M_HU> joinHUs(final ITerminalContext terminalCtx, final ILUTUJoinKey luKey, final List<? extends ILUTUJoinKey> tuKeys)
	{
		final List<I_M_HU> joinedHUs = new ArrayList<I_M_HU>();

		Services.get(IHUTrxBL.class)
				.createHUContextProcessorExecutor(terminalCtx)
				.run(new IHUContextProcessor()
				{

					@Override
					public IMutableAllocationResult process(final IHUContext huContext)
					{
						final List<I_M_HU> joinedHUsInTrx = joinHUs0(huContext, luKey, tuKeys);

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

	private List<I_M_HU> joinHUs0(final IHUContext huContext, final ILUTUJoinKey luKey, final List<? extends ILUTUJoinKey> tuKeys)
	{
		final List<I_M_HU> result = new ArrayList<I_M_HU>();

		final I_M_HU luHU = luKey.getM_HU();

		//
		// pre-add the LU-HU to the result
		result.add(luHU);

		for (final ILUTUJoinKey tuKey : tuKeys)
		{
			try
			{
				final I_M_HU tuHU = tuKey.getM_HU();
				//
				// Move TU inside the LU
				huJoinBL.assignTradingUnitToLoadingUnit(huContext, luHU, tuHU);
				//
				// Add processed TU-HU to the result
				result.add(tuHU);
			}
			catch (final NoCompatibleHUItemParentFoundException ncipe)
			{
				throw new MaterialMovementException("@" + NoCompatibleHUItemParentFoundException.ERR_TU_NOT_ALLOWED_ON_LU + "@ " + tuKey.getName(), ncipe);
			}
		}
		return result;
	}
}
