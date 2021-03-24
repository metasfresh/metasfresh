package de.metas.user.process;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.process.JavaProcess;
import org.compiere.model.I_AD_User;

public class AD_User_UnLock extends JavaProcess
{
	@Override
	protected String doIt() throws Exception
	{
		final org.compiere.model.I_AD_User user = getRecord(I_AD_User.class);
		user.setLoginFailureCount(0);
		user.setIsAccountLocked(false);
		user.setLockedFromIP(null);
		user.setLoginFailureDate(null);
		InterfaceWrapperHelper.save(user);
		return MSG_OK;
	} // doIt

}
