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

import java.sql.Timestamp;
import java.util.Iterator;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IRangeAwareParams;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Warehouse;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.movement.api.impl.HUMovementBuilder;
import de.metas.interfaces.I_M_Movement;
import de.metas.process.RunOutOfTrx;
import de.metas.process.SvrProcess;

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
	private int p_M_Warehouse_ID = -1; // the source warehouse
	private String p_huWhereClause = null;
	private Timestamp p_MovementDate = null;
	private String p_Description = null;
	
	@Override
	protected void prepare()
	{
		final IRangeAwareParams parameterAsIParams = getParameterAsIParams();
		p_M_Warehouse_ID = parameterAsIParams.getParameterAsInt("M_Warehouse_ID");
		p_huWhereClause = parameterAsIParams.getParameterAsString("WhereClause");
		p_MovementDate = parameterAsIParams.getParameterAsTimestamp("MovementDate");
		p_Description =  parameterAsIParams.getParameterAsString("Description");
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
		return huQueryBuilder
				.createQuery()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true) // because we might change the hu's locator
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterate(I_M_HU.class);
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
			final HUMovementBuilder movementBuilder = new HUMovementBuilder()
					.setContextInitial(context)
					.setWarehouseFrom(hu.getM_Locator().getM_Warehouse())
					.setWarehouseTo(targetWarehouse)
					.setMovementDate(p_MovementDate)
					.setDescription(p_Description)
					.addHU(hu);

			final I_M_Movement movement = movementBuilder.createMovement();
			if (movement == null)
			{
				throw new AdempiereException("No Movement created");
			}

			addLog("@Created@ @M_Movement_ID@: " + movement.getDocumentNo());
		}
		catch (final Exception e)
		{
			final String errmsg = "Error on " + hu.getValue() + ": " + e.getLocalizedMessage();

			addLog(errmsg);
			log.warn(errmsg, e);
		}
	}
}
