/**
 *
 */
package de.metas.vertical.pharma.vendor.gateway.msv3.interceptor;

import java.net.MalformedURLException;
import java.net.URL;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;

import de.metas.bpartner.BPartnerId;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.IImportProcess;
import de.metas.interfaces.I_C_BPartner;
import de.metas.util.Check;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfigRepository;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_I_BPartner;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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

	// default values for user and password MSV3_Vendor_ConfigIfNeeded
	// the values are per user and there will be changed by the user
	private static final String DEFAULT_UserID = "MSV3";
	private static final String DEFAULT_Password = "MSV3";

	private MSV3PharmaImportPartnerInterceptor()
	{

	}

	@Override
	public void onImport(IImportProcess<?> process, Object importModel, Object targetModel, int timing)
	{
		if (timing != IImportInterceptor.TIMING_AFTER_IMPORT)
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

			final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
			final MSV3ClientConfigRepository configRepo = Adempiere.getBean(MSV3ClientConfigRepository.class);
			MSV3ClientConfig config = configRepo.getByVendorIdOrNull(bpartnerId);

			if (config == null)
			{
				config = configRepo.newMSV3ClientConfig()
						.baseUrl(toURL(importRecord))
						.authUsername(DEFAULT_UserID)
						.authPassword(DEFAULT_Password)
						.bpartnerId(de.metas.vertical.pharma.msv3.protocol.types.BPartnerId.of(bpartnerId.getRepoId()))
						.version(MSV3ClientConfig.VERSION_1)
						.build();
			}

			config = configRepo.save(config);

			importRecord.setMSV3_Vendor_Config_ID(config.getConfigId().getRepoId());
		}
	}

	private URL toURL(@NonNull final I_I_BPartner importRecord)
	{
		try
		{
			return new URL(importRecord.getMSV3_BaseUrl());
		}
		catch (MalformedURLException e)
		{
			throw new AdempiereException("The MSV3_BaseUrl value of the given I_BPartner can't be parsed as URL", e)
					.appendParametersToMessage()
					.setParameter("MSV3_BaseUrl", importRecord.getMSV3_BaseUrl())
					.setParameter("I_I_BPartner", importRecord);
		}
	}

}
