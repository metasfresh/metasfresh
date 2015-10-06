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
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;

import de.metas.impex.spi.IConnector;
import de.metas.impex.spi.IExportConnector;
import de.metas.impex.spi.IImportConnector;

public class MImpExConnectorType extends X_ImpEx_ConnectorType
{
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		final IConnector connector = Util.getInstance(IConnector.class, getClassname());

		if (connector instanceof IImportConnector)
		{
			if (!DIRECTION_Import.equals(getDirection()))
			{
				throw new AdempiereException("This in an export type, but the implementation is for import");
			}

		}
		else if (connector instanceof IExportConnector)
		{
			if (!DIRECTION_Export.equals(getDirection()))
			{
				throw new AdempiereException("This in an import type, but the implementation is for export");
			}
		}
		else
		{
			throw new AdempiereException("Connector must be an import or export connector");
		}

		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6990832919448186931L;

	public MImpExConnectorType(Properties ctx, int C_ImpExConnectorType_ID, String trxName)
	{
		super(ctx, C_ImpExConnectorType_ID, trxName);
	}

	public MImpExConnectorType(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

}
