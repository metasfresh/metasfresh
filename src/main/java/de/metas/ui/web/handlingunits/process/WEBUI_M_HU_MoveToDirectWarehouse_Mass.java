package de.metas.ui.web.handlingunits.process;

import java.sql.Timestamp;
import java.util.Iterator;

import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IRangeAwareParams;
import org.compiere.model.IQuery;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.window.model.DocumentCollection;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * HU Editor: Move selected HUs to direct warehouse (aka Materialentnahme)
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_M_HU_MoveToDirectWarehouse_Mass extends HUEditorProcessTemplate
{
	// services
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@Autowired
	private DocumentCollection documentsCollection;

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
		p_Description = parameterAsIParams.getParameterAsString("Description");
	}

	@Override
	@RunOutOfTrx
	protected final String doIt()
	{
		HUMoveToDirectWarehouseService.newInstance()
				.setDocumentsCollection(documentsCollection)
				.setHUView(getView())
				.setMovementDate(p_MovementDate)
				.setDescription(p_Description)
				.setFailOnFirstError(false)
				.setLoggable(this)
				.move(retrieveHUs());

		return MSG_OK;
	}

	/**
	 * @return HUs that will be moved
	 */
	protected Iterator<I_M_HU> retrieveHUs()
	{
		final IHUQueryBuilder huQueryBuilder = handlingUnitsDAO.createHUQueryBuilder()
				.setContext(getCtx(), ITrx.TRXNAME_None);

		// Only top level HUs
		huQueryBuilder.setOnlyTopLevelHUs();
		
		// Only Active HUs
		huQueryBuilder.addHUStatusToInclude(X_M_HU.HUSTATUS_Active);

		// Only for preselected warehouse
		if (p_M_Warehouse_ID > 0)
		{
			huQueryBuilder.addOnlyInWarehouseId(p_M_Warehouse_ID);
		}

		// Only for given SQL where clause
		if (!Check.isEmpty(p_huWhereClause, true))
		{
			huQueryBuilder.addFilter(TypedSqlQueryFilter.of(p_huWhereClause));
		}

		// Fetch the HUs iterator
		return huQueryBuilder
				.createQuery()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true) // because we might change the hu's locator
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterate(I_M_HU.class);
	}
}
