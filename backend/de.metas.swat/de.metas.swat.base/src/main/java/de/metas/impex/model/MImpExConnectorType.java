package de.metas.impex.model;

import java.io.Serial;
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
	@Serial
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
