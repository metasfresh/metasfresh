package org.adempiere.ad.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.document.engine.DocStatus;
import de.metas.process.Param;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.GenericPO;

import java.util.Iterator;

public class ProcessDocuments extends AbstractProcessDocumentsTemplate
{
	// services
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	public static final String PARAM_AD_Table_ID = "AD_Table_ID";
	@Param(parameterName = PARAM_AD_Table_ID, mandatory = true)
	private int p_AD_Table_ID = -1;

	public static final String PARAM_DocStatus = "DocStatus";
	@Param(parameterName = PARAM_DocStatus)
	private DocStatus p_DocStatus;

	public static final String PARAM_WhereClause = "WhereClause";
	@Param(parameterName = PARAM_WhereClause)
	private String p_WhereClause;

	public static final String PARAM_DocAction = "DocAction";
	@Param(parameterName = PARAM_DocAction)
	private String p_DocAction;

	@Override
	protected Iterator<GenericPO> retrieveDocumentsToProcess()
	{
		if (p_AD_Table_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_AD_Table_ID);
		}

		final String tableName = adTableDAO.retrieveTableName(this.p_AD_Table_ID);

		final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilder(tableName)
				.addOnlyActiveRecordsFilter();

		if (p_DocStatus != null)
		{
			queryBuilder.addEqualsFilter(PARAM_DocStatus, p_DocStatus);
		}
		if (!Check.isBlank(p_WhereClause))
		{
			queryBuilder.filter(TypedSqlQueryFilter.of(p_WhereClause));
		}

		return queryBuilder.create().iterate(GenericPO.class);
	}

	@Override
	protected String getDocAction() {return p_DocAction;}
}
