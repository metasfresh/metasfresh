/**
 *
 */
package de.metas.impexp.bpartner;

import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.impexp.IImportProcess;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import de.metas.interfaces.I_C_BPartner;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_I_BPartner;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Vendor_Config;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MSV3PharmaImportPartnerInterceptor implements IImportInterceptor
{
	public static final MSV3PharmaImportPartnerInterceptor instance = new MSV3PharmaImportPartnerInterceptor();

	private static final String MSV3_Constant = "MSV3";

	private static final String Password = "MSV3";

	private static final String UserID = "MSV3";

	private MSV3PharmaImportPartnerInterceptor()
	{

	}

	@Override
	public void onImport(IImportProcess<?> process, Object importModel, Object targetModel, int timing)
	{
		if (timing != IImportInterceptor.TIMING_AFTER_IMPORT )
		{
			return;
		}

		final I_I_BPartner ibpartner = InterfaceWrapperHelper.create(importModel, I_I_BPartner.class);

		if (targetModel instanceof org.compiere.model.I_C_BPartner)
		{
			final I_C_BPartner bpartner = InterfaceWrapperHelper.create(targetModel, I_C_BPartner.class);
			createMSV3_Vendor_ConfigIfNeeded(ibpartner, bpartner);
		}

	}



	private final void createMSV3_Vendor_ConfigIfNeeded(@NonNull final I_I_BPartner importRecord, @NonNull final I_C_BPartner bpartner)
	{
		if (!Check.isEmpty(importRecord.getMSV3_Vendor_Config_Name(), true)
				&& MSV3_Constant.equals(importRecord.getMSV3_Vendor_Config_Name()))
		{
			if (importRecord.getMSV3_Vendor_Config_ID() > 0)
			{
				return;
			}

			final I_MSV3_Vendor_Config MSV3VendorConfig = InterfaceWrapperHelper.newInstance(I_MSV3_Vendor_Config.class);
			MSV3VendorConfig.setC_BPartner(bpartner);
			MSV3VendorConfig.setMSV3_BaseUrl(importRecord.getMSV3_BaseUrl());
			MSV3VendorConfig.setPassword(Password);
			MSV3VendorConfig.setUserID(UserID);
			InterfaceWrapperHelper.save(MSV3VendorConfig);

			importRecord.setMSV3_Vendor_Config_ID(MSV3VendorConfig.getMSV3_Vendor_Config_ID());
		}
	}

}
