package org.adempiere.user.process;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.adempiere.model.I_AD_User;
import de.metas.process.JavaProcess;

public class UserAccountUnlock extends JavaProcess
{
	@Override
	protected String doIt() throws Exception
	{
		final I_AD_User user = getRecord(I_AD_User.class);
		user.setLoginFailureCount(0);
		user.setIsAccountLocked(false);
		user.setLockedFromIP(null);
		user.setLoginFailureDate(null);
		InterfaceWrapperHelper.save(user);
		return MSG_OK;
	} // doIt

}
