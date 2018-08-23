package de.metas.vertical.pharma.vendor.gateway.msv3.config;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.util.CCache;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Vendor_Config;
import lombok.NonNull;

/*
 * #%L
 * de.metas.vendor.gateway.msv3
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

@Repository
@DependsOn(Adempiere.BEAN_NAME)
public class MSV3ClientConfigRepository
{
	private final CCache<BPartnerId, Optional<MSV3ClientConfig>> configDataRecords = CCache.newCache(
			I_MSV3_Vendor_Config.Table_Name + "#by#" + I_MSV3_Vendor_Config.COLUMNNAME_C_BPartner_ID,
			10,
			CCache.EXPIREMINUTES_Never);

	/**
	 * @param vendorId the C_BPartner_ID of the vendor we wish to order from
	 *
	 * @return never returns {@code null}.
	 */
	public MSV3ClientConfig getByVendorId(final BPartnerId vendorId)
	{
		final MSV3ClientConfig config = getByVendorIdOrNull(vendorId);
		if (config == null)
		{
			throw new AdempiereException("Missing MSV3ClientConfig for vendorId=" + vendorId);
		}
		return config;
	}

	public MSV3ClientConfig getByVendorIdOrNull(final BPartnerId vendorId)
	{
		return configDataRecords.getOrLoad(vendorId, this::retrieveByVendorIdOrNull)
				.orElse(null);

	}

	private Optional<MSV3ClientConfig> retrieveByVendorIdOrNull(final BPartnerId vendorId)
	{
		final I_MSV3_Vendor_Config result = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MSV3_Vendor_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MSV3_Vendor_Config.COLUMN_C_BPartner_ID, vendorId)
				.create()
				.firstOnly(I_MSV3_Vendor_Config.class);
		if (result == null)
		{
			return Optional.empty();
		}
		return Optional.of(MSV3ClientConfig.ofdataRecord(result));
	}

	public MSV3ClientConfig save(@NonNull final MSV3ClientConfig config)
	{
		final I_MSV3_Vendor_Config configRecord = createOrUpdateRecord(config);
		saveRecord(config);

		return config.toBuilder()
				.configId(MSV3ClientConfigId.ofRepoId(configRecord.getMSV3_Vendor_Config_ID()))
				.build();
	}

	private I_MSV3_Vendor_Config createOrUpdateRecord(@NonNull final MSV3ClientConfig config)
	{
		final I_MSV3_Vendor_Config configRecord;
		if (config.getConfigId() != null)
		{
			final int repoId = config.getConfigId().getRepoId();
			configRecord = load(repoId, I_MSV3_Vendor_Config.class);
		}
		else
		{
			configRecord = newInstance(I_MSV3_Vendor_Config.class);
		}

		configRecord.setC_BPartner_ID(config.getBpartnerId().getRepoId());
		configRecord.setMSV3_BaseUrl(config.getBaseUrl().toExternalForm());
		configRecord.setPassword(config.getAuthPassword());
		configRecord.setUserID(config.getAuthUsername());

		return configRecord;
	}

}
