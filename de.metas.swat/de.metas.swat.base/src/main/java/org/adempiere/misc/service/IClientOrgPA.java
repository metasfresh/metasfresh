package org.adempiere.misc.service;

import org.compiere.model.I_AD_Client;

import de.metas.util.ISingletonService;

public interface IClientOrgPA extends ISingletonService
{
	I_AD_Client retrieveClient(int clientId, String trxName);
}
