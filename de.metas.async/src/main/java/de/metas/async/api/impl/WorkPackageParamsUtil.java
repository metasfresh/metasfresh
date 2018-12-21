package de.metas.async.api.impl;

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Param;
import lombok.NonNull;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class WorkPackageParamsUtil
{
	public static I_C_Queue_WorkPackage_Param createWorkPackageParamRecord(
			@NonNull final I_C_Queue_WorkPackage workpackage,
			@NonNull final String parameterName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);
		final String trxName = InterfaceWrapperHelper.getTrxName(workpackage);

		final I_C_Queue_WorkPackage_Param workpackageParam = InterfaceWrapperHelper.create(ctx, I_C_Queue_WorkPackage_Param.class, trxName);
		workpackageParam.setAD_Org_ID(workpackage.getAD_Org_ID());
		workpackageParam.setC_Queue_WorkPackage(workpackage);
		workpackageParam.setIsActive(true);

		workpackageParam.setParameterName(parameterName);
		return workpackageParam;
	}

}
