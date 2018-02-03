package de.metas.vertical.pharma.vendor.gateway.mvs3.config;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.util.function.Supplier;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.Adempiere;
import org.compiere.util.CCache;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Vendor_Config;

/*
 * #%L
 * de.metas.vendor.gateway.mvs3
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
	/**
	 * {@code null} object, used to store "no config for this vendor" in the {@link #configDataRecords} cache.
	 * <p>
	 * Notes: beside this bean being annotated to depending on {@link Adempiere#BEAN_NAME},<br>
	 * there might not yet be a DB connection when this repository is initialized (in the swing client!).<br>
	 * That's why we have the supplier.
	 */
	private final static Supplier<I_MSV3_Vendor_Config> NULL_CONFIG_RECORD = //
			ExtendedMemorizingSupplier.of(() -> newInstance(I_MSV3_Vendor_Config.class));

	private final CCache<Integer, I_MSV3_Vendor_Config> configDataRecords = CCache.newCache(
			I_MSV3_Vendor_Config.Table_Name + "#by#" + I_MSV3_Vendor_Config.COLUMNNAME_C_BPartner_ID,
			10,
			CCache.EXPIREMINUTES_Never);

	/**
	 * @param vendorId the C_BPartner_ID of the vendor we wish to order from
	 *
	 * @return never returns {@code null}.
	 */
	public MSV3ClientConfig retrieveByVendorId(final int vendorId)
	{
		final MSV3ClientConfig config = getretrieveByVendorIdOrNull(vendorId);
		Check.errorIf(config == null, "Missing MSV3ClientConfig for vendorId={}", vendorId);
		return config;
	}

	public MSV3ClientConfig getretrieveByVendorIdOrNull(final int vendorId)
	{
		final Supplier<I_MSV3_Vendor_Config> recordLoader = () -> retrieveRecordFromDB(vendorId);

		final I_MSV3_Vendor_Config configDataRecord = configDataRecords.get(vendorId, recordLoader);
		if (configDataRecord == NULL_CONFIG_RECORD.get())
		{
			return null;
		}

		return MSV3ClientConfig.ofdataRecord(configDataRecord);
	}

	private I_MSV3_Vendor_Config retrieveRecordFromDB(final int vendorId)
	{
		final I_MSV3_Vendor_Config result = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MSV3_Vendor_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MSV3_Vendor_Config.COLUMN_C_BPartner_ID, vendorId)
				.create()
				.firstOnly(I_MSV3_Vendor_Config.class);

		if (result == null)
		{
			return NULL_CONFIG_RECORD.get();
		}
		return result;
	}

}
