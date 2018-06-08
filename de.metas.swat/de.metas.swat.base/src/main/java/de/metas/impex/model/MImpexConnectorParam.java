package de.metas.impex.model;

/*
 * #%L
 * de.metas.swat.base
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


import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

public class MImpexConnectorParam extends X_Impex_ConnectorParam
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6887898374268570467L;

	public MImpexConnectorParam(Properties ctx, int C_ImpexConnectorParam_ID,			String trxName)
	{
		super(ctx, C_ImpexConnectorParam_ID, trxName);
	}

	public MImpexConnectorParam(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static List<MImpexConnectorParam> retrieve(final MImpExConnector connector)
	{
		final String whereClause = COLUMNNAME_ImpEx_Connector_ID + "=?";

		final Object[] parameters = { connector.get_ID() };

		final String orderBy = COLUMNNAME_SeqNo;

		return new Query(connector.getCtx(), Table_Name, whereClause, connector
				.get_TrxName()).setParameters(parameters).setOrderBy(orderBy)
				.list(MImpexConnectorParam.class);
	}

}
