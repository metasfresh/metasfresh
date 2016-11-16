package org.adempiere.user.process;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MUser;

import de.metas.adempiere.model.I_AD_User;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

public class UserAccountUnlock extends SvrProcess
{

	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
			}
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
	} // prepare

	@Override
	protected String doIt() throws Exception
	{
		MUser userPO = new MUser(getCtx(), getRecord_ID(), get_TrxName());
		final I_AD_User user = InterfaceWrapperHelper.create(userPO, I_AD_User.class);
		user.setLoginFailureCount(0);
		user.setIsAccountLocked(false);
		user.setLockedFromIP(null);
		user.setLoginFailureDate(null);
		userPO.saveEx();
		return "Ok";
	} // doIt

}
