package org.adempiere.misc.service.impl;

import org.adempiere.misc.service.IClientOrgPA;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MClient;
import org.compiere.util.Env;

public final class ClientOrgPA implements IClientOrgPA
{
	@Override
	public I_AD_Client retrieveClient(int clientId, String trxName)
	{
		return new MClient(Env.getCtx(), clientId, trxName);
	}
}
