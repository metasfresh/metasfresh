package de.metas.cucumber.stepdefs.accounting;

import de.metas.util.Services;
import io.cucumber.java.en.And;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;

import static org.assertj.core.api.Assertions.assertThat;

public class Accounting_Config
{
	private final transient IClientDAO clientDAO = Services.get(IClientDAO.class);

	@And("documents are accounted immediately")
	public void enablePostImmediate()
	{
		final ClientId clientId = Env.getClientId();
		assertThat(clientId)
				.as("Client shall be metasfresh")
				.isEqualTo(ClientId.METASFRESH); // just to make sure we are not configuring the wrong AD_Client

		final I_AD_Client client = clientDAO.getById(clientId);
		client.setIsPostImmediate(true);
		InterfaceWrapperHelper.save(client);
	}
}
