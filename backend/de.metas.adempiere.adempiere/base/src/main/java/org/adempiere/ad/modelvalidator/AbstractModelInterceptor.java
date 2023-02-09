package org.adempiere.ad.modelvalidator;

import de.metas.security.IUserLoginListener;
import org.adempiere.ad.session.MFSession;
import org.compiere.model.I_AD_Client;

/**
 * Template class to be used when implementing custom {@link IModelInterceptor}s.
 *
 * @author tsa
 *
 */
public abstract class AbstractModelInterceptor implements IModelInterceptor, IUserLoginListener
{
	private int adClientId = -1;

	@Override
	public final void initialize(final IModelValidationEngine engine, final I_AD_Client client)
	{
		adClientId = client == null ? -1 : client.getAD_Client_ID();
		onInit(engine, client);
	}

	/**
	 * Called when interceptor is registered and needs to be initialized
	 */
	protected abstract void onInit(final IModelValidationEngine engine, final I_AD_Client client);

	@Override
	public final int getAD_Client_ID()
	{
		return adClientId;
	}

	// NOTE: method signature shall be the same as org.adempiere.ad.security.IUserLoginListener.onUserLogin(int, int, int)
	@Override
	public void onUserLogin(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing
	}

	@Override
	public void beforeLogout(final MFSession session)
	{
		// nothing
	}

	@Override
	public void afterLogout(final MFSession session)
	{
		// nothing
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
	{
		// nothing
	}

	@Override
	public void onDocValidate(final Object model, final DocTimingType timing) throws Exception
	{
		// nothing
	}

}
