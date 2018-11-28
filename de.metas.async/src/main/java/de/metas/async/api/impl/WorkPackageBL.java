package de.metas.async.api.impl;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_User;

import de.metas.async.api.IWorkPackageBL;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Log;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

public class WorkPackageBL implements IWorkPackageBL
{
	@Override
	public ILoggable createLoggable(@NonNull final I_C_Queue_WorkPackage workPackage)
	{
		return new ILoggable()
		{
			@Override
			public ILoggable addLog(final String msg, final Object... msgParamters)
			{
				// NOTE: always create the logs out of transaction because we want them to be persisted even if the workpackage processing fails
				final Properties ctx = InterfaceWrapperHelper.getCtx(workPackage);

				final I_C_Queue_WorkPackage_Log logRecord = InterfaceWrapperHelper.create(ctx, I_C_Queue_WorkPackage_Log.class, ITrx.TRXNAME_None);
				logRecord.setC_Queue_WorkPackage(workPackage);
				logRecord.setMsgText(StringUtils.formatMessage(msg, msgParamters));
				InterfaceWrapperHelper.save(logRecord);
				
				return this;
			}
		};
	}

	@Override
	public I_AD_User getUserInChargeOrNull(@NonNull final I_C_Queue_WorkPackage workPackage)
	{
		if (workPackage.getAD_User_InCharge_ID() > 0 && workPackage.getAD_User_InCharge() != null)
		{
			return workPackage.getAD_User_InCharge();
		}

		final I_C_Queue_Block block = workPackage.getC_Queue_Block();
		Check.assumeNotNull(block, "C_Queue_Block is not null for param 'workPackage'={}", workPackage);

		final I_C_Queue_PackageProcessor packageProcessor = block.getC_Queue_PackageProcessor();
		Check.assumeNotNull(packageProcessor, "C_Queue_PackageProcessor is not null for block={} of param 'workPackage'={}",
				block, workPackage);

		if (Check.isEmpty(packageProcessor.getInternalName()))
		{
			return null;
		}

		final String internalName = packageProcessor.getInternalName();

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final int userInChargeID = sysConfigBL.getIntValue("de.metas.async.api." + internalName + ".AD_User_InCharge_ID", -1,
				workPackage.getAD_Client_ID(),
				workPackage.getAD_Org_ID());
		if (userInChargeID < 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(InterfaceWrapperHelper.getCtx(workPackage), userInChargeID, I_AD_User.class, InterfaceWrapperHelper.getTrxName(workPackage));
	}
}
