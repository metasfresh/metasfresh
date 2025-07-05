package de.metas.impex.model;

import java.io.Serial;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

public class MImpexConnectorParam extends X_Impex_ConnectorParam
{
	/**
	 * 
	 */
	@Serial
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
