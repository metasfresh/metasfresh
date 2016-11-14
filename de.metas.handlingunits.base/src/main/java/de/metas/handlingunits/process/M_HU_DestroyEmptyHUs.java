package de.metas.handlingunits.process;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.Iterator;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;

public class M_HU_DestroyEmptyHUs extends SvrProcess
{
	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	// Parameters
	private static final String PARAM_M_Warehouse_ID = "M_Warehouse_ID";
	private int p_M_Warehouse_ID;

	// Status
	private int countDestroyed = 0;
	private int countNotDestroyed = 0;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (PARAM_M_Warehouse_ID.equals(para.getParameterName()))
			{
				p_M_Warehouse_ID = para.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_M_HU> emptyHUs = retrieveEmptyHUs();

		while (emptyHUs.hasNext())
		{
			final I_M_HU emptyHU = emptyHUs.next();
			destroyEmptyHU(emptyHU);
		}

		return  countDestroyed + "@Destroyed@, " + countNotDestroyed +  " @NotDestroyed@: " ;
	}

	private void destroyEmptyHU(final I_M_HU emptyHU)
	{
		try
		{
			final boolean destroyed = handlingUnitsBL.destroyIfEmptyStorage(emptyHU);
			if (destroyed)
			{
				countDestroyed++;
			}
			else
			{
				countNotDestroyed++;
			}
		}
		catch (Exception e)
		{
			addLog("Failed destroying " + handlingUnitsBL.getDisplayName(emptyHU) + ": " + e.getLocalizedMessage());
			countNotDestroyed++;
		}
	}

	private Iterator<I_M_HU> retrieveEmptyHUs()
	{
		// Services
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		if (p_M_Warehouse_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_M_Warehouse_ID);
		}

		final IHUQueryBuilder huQueryBuilder = handlingUnitsDAO.createHUQueryBuilder()
				.setContext(getCtx(), ITrx.TRXNAME_None)
				.addOnlyInWarehouseId(p_M_Warehouse_ID)

				// we don't want to deal with e.g. Shipped HUs
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Planning)

				.setEmptyStorageOnly()
				.setOnlyTopLevelHUs();

		return huQueryBuilder
				.createQuery()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.iterate(I_M_HU.class);
	}

}
