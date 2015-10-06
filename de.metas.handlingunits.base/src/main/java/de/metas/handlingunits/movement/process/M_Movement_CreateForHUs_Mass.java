package de.metas.handlingunits.movement.process;

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


import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Warehouse;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.interfaces.I_M_Movement;

/**
 * Move selected HUs to to {@link IHUMovementBL#getDirectMove_Warehouse(java.util.Properties, boolean)}.
 *
 * @author tsa
 *
 */
public class M_Movement_CreateForHUs_Mass extends SvrProcess
{
	// services
	private final transient IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	// parameters
	private int p_M_Warehouse_ID = -1;
	private String p_huWhereClause = null;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter param : getParameter())
		{
			final String parameterName = param.getParameterName();

			if (param.getParameter() == null)
			{
				continue;
			}
			else if ("M_Warehouse_ID".equals(parameterName))
			{
				p_M_Warehouse_ID = param.getParameterAsInt();
			}
			else if ("WhereClause".equals(parameterName))
			{
				p_huWhereClause = param.getParameterAsString();
			}
		}
	}

	@Override
	@RunOutOfTrx
	protected final String doIt() throws Exception
	{
		final Iterator<I_M_HU> hus = retrieveHUs();
		while (hus.hasNext())
		{
			final I_M_HU hu = hus.next();
			generateMovement(hu);
		}

		return MSG_OK;
	}

	/**
	 * @return target warehouse where the HUs will be moved to.
	 */
	protected I_M_Warehouse getTargetWarehouse()
	{
		if (_targetWarehouse == null)
		{
			final boolean exceptionIfNull = true;
			_targetWarehouse = huMovementBL.getDirectMove_Warehouse(getCtx(), exceptionIfNull);
		}
		return _targetWarehouse;
	}

	private I_M_Warehouse _targetWarehouse;

	/**
	 * @return HUs that will be moved
	 */
	protected Iterator<I_M_HU> retrieveHUs()
	{
		final IHUQueryBuilder huQueryBuilder = handlingUnitsDAO.createHUQueryBuilder()
				.setContext(getCtx(), ITrx.TRXNAME_None);

		// Only top level HUs
		huQueryBuilder.setOnlyTopLevelHUs();

		// Only for preselected warehouse
		if (p_M_Warehouse_ID > 0)
		{
			huQueryBuilder.addOnlyInWarehouseId(p_M_Warehouse_ID);
		}

		// Only for given SQL where clause
		if (!Check.isEmpty(p_huWhereClause, true))
		{
			huQueryBuilder.addFilter(new TypedSqlQueryFilter<I_M_HU>(p_huWhereClause));
		}

		// Fetch the HUs iterator
		return huQueryBuilder.createQuery().iterate(I_M_HU.class);
	}

	/**
	 * Generate a movement which will move given HU to {@link #getTargetWarehouse()}.
	 *
	 * @param hu
	 */
	private final void generateMovement(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");
		final I_M_Warehouse targetWarehouse = getTargetWarehouse();

		try
		{
			final IContextAware context = PlainContextAware.createUsingOutOfTransaction(getCtx());
			final List<I_M_Movement> movement = huMovementBL.generateMovementsToWarehouse(targetWarehouse, Collections.singleton(hu), context);
			Check.assume(movement.size() <= 1, "We added one hu, so there shall be max 1 movement");
			if (movement.isEmpty())
			{
				throw new AdempiereException("No Movement created");
			}

			addLog("@Created@ @M_Movement_ID@: " + movement.get(0).getDocumentNo());
		}
		catch (final Exception e)
		{
			final String errmsg = "Error on " + hu.getValue() + ": " + e.getLocalizedMessage();

			addLog(errmsg);
			log.log(Level.WARNING, errmsg, e);
		}
	}
}
