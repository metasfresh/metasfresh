package de.metas.impex.model;

import java.io.Serial;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.util.Util;

import de.metas.adempiere.util.Parameter;
import de.metas.impex.spi.IConnector;

public class MImpExConnector extends X_ImpEx_Connector
{
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -4915263677859779046L;

	public MImpExConnector(Properties ctx, int C_ImpExConnector_ID, String trxName)
	{
		super(ctx, C_ImpExConnector_ID, trxName);
	}

	public MImpExConnector(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	private IConnector getConnector()
	{

		final String className = getImpEx_ConnectorType().getClassname();
		final IConnector connector = Util.getInstance(IConnector.class, className);
		return connector;
	}

	public IConnector useConnector()
	{

		final IConnector connector = getConnector();

		final List<MImpexConnectorParam> mParams = MImpexConnectorParam.retrieve(this);

		final List<Parameter> connectorParams = new ArrayList<Parameter>(mParams.size());

		for (final MImpexConnectorParam mParam : mParams)
		{

			final Parameter connectorParam = new Parameter(
					mParam.getName(),
					mParam.getParamName(), mParam.getDescription(), mParam
							.getAD_Reference_ID(), mParam.getSeqNo());

			connectorParam.setValue(mParam.getParamValue());
			connectorParams.add(connectorParam);
		}

		connector.open(connectorParams);

		return connector;
	}

	public void createParameters()
	{
		deleteParameters();

		for (final Parameter param : getConnector().getParameters())
		{

			final MImpexConnectorParam mParam = new MImpexConnectorParam(
					getCtx(), 0, get_TrxName());

			mParam.setParamName(param.displayName);
			mParam.setDescription(param.description);
			mParam.setAD_Reference_ID(param.displayType);

			mParam.setName(param.name);
			mParam.setSeqNo(param.seqNo);
			mParam.setImpEx_Connector_ID(this.get_ID());

			mParam.saveEx();
		}
	}

	private void deleteParameters()
	{

		for (final MImpexConnectorParam mParam : MImpexConnectorParam.retrieve(this))
		{
			mParam.deleteEx(false);
		}
	}

	@Override
	protected boolean beforeDelete()
	{
		deleteParameters();
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (success
				&& (newRecord || is_ValueChanged(COLUMNNAME_ImpEx_ConnectorType_ID)))
		{
			createParameters();
		}
		return success;
	}
}
