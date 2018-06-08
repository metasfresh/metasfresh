package de.metas.vertical.pharma.msv3.server.peer.metasfresh.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.vertical.pharma.msv3.server.model.I_MSV3_Customer_Config;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.services.MSV3CustomerConfigService;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer-metasfresh
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

@Interceptor(I_MSV3_Customer_Config.class)
@Component
public class MSV3_Customer_Config
{
	private MSV3CustomerConfigService getMSV3CustomerConfigService()
	{
		return Adempiere.getBean(MSV3CustomerConfigService.class);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void onCreated(final I_MSV3_Customer_Config configRecord)
	{
		if (configRecord.isActive())
		{
			final MSV3CustomerConfigService service = getMSV3CustomerConfigService();
			service.publishConfigChanged(configRecord);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void onUpdated(final I_MSV3_Customer_Config configRecord)
	{
		final MSV3CustomerConfigService service = getMSV3CustomerConfigService();
		service.publishConfigChanged(configRecord);

		if (InterfaceWrapperHelper.isValueChanged(configRecord, I_MSV3_Customer_Config.COLUMNNAME_UserID))
		{
			final I_MSV3_Customer_Config configRecordOld = InterfaceWrapperHelper.createOld(configRecord, I_MSV3_Customer_Config.class);
			service.publishConfigDeleted(configRecordOld.getUserID());
		}

	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void onDeleted(final I_MSV3_Customer_Config configRecord)
	{
		final MSV3CustomerConfigService service = getMSV3CustomerConfigService();
		service.publishConfigDeleted(configRecord.getUserID());
	}
}
