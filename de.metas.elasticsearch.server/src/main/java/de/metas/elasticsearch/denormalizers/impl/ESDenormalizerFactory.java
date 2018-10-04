package de.metas.elasticsearch.denormalizers.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Campaign;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_SalesRegion;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.CCache;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_Product;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.denormalizers.IESDenormalizerFactory;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.types.ESIndexType;
import de.metas.logging.LogManager;
import de.metas.util.Check;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class ESDenormalizerFactory implements IESDenormalizerFactory
{
	private static final Logger logger = LogManager.getLogger(ESDenormalizerFactory.class);

	private final CCache<ESModelDenormalizerKey, IESModelDenormalizer> tableDenormalizers = new CCache<>(ESDenormalizerFactory.class.getSimpleName() + "#TableDenormalizers", 20);
	private final ConcurrentHashMap<ESModelDenormalizerKey, IESModelDenormalizer> valueModelDenormalizers = new ConcurrentHashMap<>();

	private ESDenormalizerFactory()
	{
		setupKPIStandardDenormalizers();
	}

	private final void setupKPIStandardDenormalizers()
	{
		//
		// Location
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_C_Country.class)
				.includeColumn(I_C_Country.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_Country.COLUMNNAME_CountryCode).index(ESIndexType.NotAnalyzed)
				.build());
		registerModelValueDenormalizer(createLocationDenormalizer(ESModelIndexerProfile.KPI));
		registerModelValueDenormalizer(createLocationDenormalizer(ESModelIndexerProfile.FULL_TEXT_SEARCH));

		//
		// BPartner related
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_C_BPartner_Location.class)
				.includeColumn(I_C_BPartner_Location.COLUMNNAME_Name)
				.includeColumn(I_C_BPartner_Location.COLUMNNAME_Phone)
				.includeColumn(I_C_BPartner_Location.COLUMNNAME_Phone2)
				.includeColumn(I_C_BPartner_Location.COLUMNNAME_Fax)
				.includeColumn(I_C_BPartner_Location.COLUMNNAME_C_Location_ID)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_AD_User.class)
				.includeColumn(I_AD_User.COLUMNNAME_Name)
				.includeColumn(I_AD_User.COLUMNNAME_EMail)
				.includeColumn(I_AD_User.COLUMNNAME_Phone)
				.includeColumn(I_AD_User.COLUMNNAME_Phone2)
				.includeColumn(I_AD_User.COLUMNNAME_Fax)
				.excludeColumn(I_AD_User.COLUMNNAME_C_BPartner_ID)
				.excludeColumn(I_AD_User.COLUMNNAME_C_BPartner_Location_ID)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_C_BP_Group.class)
				.includeColumn(I_C_BP_Group.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
		// NOTE: C_BPartner is defined in "standard dimensions part"

		//
		// Product related
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_M_Product_Category.class)
				.includeColumn(I_M_Product_Category.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_M_Product_Category.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
		// NOTE: M_Product is defined in "standard dimensions part"

		//
		// C_DocType related
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_C_DocType.class)
				.includeColumn(I_C_DocType.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_DocType.COLUMNNAME_DocBaseType)
				.includeColumn(I_C_DocType.COLUMNNAME_DocSubType)
				.excludeStandardColumns()
				.build());

		//
		// C_Currency related
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_C_Currency.class)
				.includeColumn(I_C_Currency.COLUMNNAME_ISO_Code).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());

		//
		// Standard dimensions
		//
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_AD_Org.class)
				.includeColumn(I_AD_Org.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_AD_Org.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_M_Product.class)
				.includeColumn(org.compiere.model.I_M_Product.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(org.compiere.model.I_M_Product.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.includeColumn(org.compiere.model.I_M_Product.COLUMNNAME_M_Product_Category_ID)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_C_BPartner.class)
				.includeColumn(I_C_BPartner.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_BPartner.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_BPartner.COLUMNNAME_C_BP_Group_ID)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_C_SalesRegion.class)
				.includeColumn(I_C_SalesRegion.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_SalesRegion.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_C_Project.class)
				.includeColumn(I_C_Project.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_Project.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_C_Campaign.class)
				.includeColumn(I_C_Campaign.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_Campaign.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_C_Activity.class)
				.includeColumn(I_C_Activity.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_Activity.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());

		//
		// FIXME: hardcoded C_Order model value (to be used when indexing C_OrderLines)
		registerModelValueDenormalizer(newModelDenormalizerBuilder(ESModelIndexerProfile.KPI, I_C_Order.class)
				.excludeStandardColumns()
				//
				.includeColumn(I_C_Order.COLUMNNAME_C_BPartner_ID)
				.includeColumn(I_C_Order.COLUMNNAME_C_BPartner_Location_ID)
				.includeColumn(I_C_Order.COLUMNNAME_AD_User_ID)
				//
				.includeColumn(I_C_Order.COLUMNNAME_SalesRep_ID)
				//
				//
				.includeColumn(I_C_Order.COLUMNNAME_DateOrdered)
				.includeColumn(I_C_Order.COLUMNNAME_DatePromised)
				.includeColumn(I_C_Order.COLUMNNAME_PreparationDate)
				.includeColumn(I_C_Order.COLUMNNAME_C_Currency_ID)
				.includeColumn(I_C_Order.COLUMNNAME_DocumentNo)
				.includeColumn(I_C_Order.COLUMNNAME_IsSOTrx)
				//
				.build());
	}

	private ESPOModelDenormalizer createLocationDenormalizer(ESModelIndexerProfile profile)
	{
		return newModelDenormalizerBuilder(profile, I_C_Location.class)
				.includeColumn(I_C_Location.COLUMNNAME_Address1).index(ESIndexType.Analyzed)
				.includeColumn(I_C_Location.COLUMNNAME_Address2).index(ESIndexType.Analyzed)
				.includeColumn(I_C_Location.COLUMNNAME_Address3).index(ESIndexType.Analyzed)
				.includeColumn(I_C_Location.COLUMNNAME_Address4).index(ESIndexType.Analyzed)
				.includeColumn(I_C_Location.COLUMNNAME_Postal).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_Location.COLUMNNAME_Postal_Add).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_Location.COLUMNNAME_CareOf).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_Location.COLUMNNAME_POBox).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_Location.COLUMNNAME_City).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_Location.COLUMNNAME_C_Country_ID)
				.excludeStandardColumns()
				.build();
	}

	@Override
	public IESModelDenormalizer getModelDenormalizer(final ESModelIndexerProfile profile, final String tableName)
	{
		final ESModelDenormalizerKey key = ESModelDenormalizerKey.of(profile, tableName);
		return tableDenormalizers.getOrLoad(key, this::createDefaultModelDenormalizer);
	}

	private IESModelDenormalizer createDefaultModelDenormalizer(final ESModelDenormalizerKey key)
	{
		final ESPOModelDenormalizer denormalizer = newModelDenormalizerBuilder(key.getProfile(), key.getModelTableName())
				.excludeStandardColumns()
				.build();
		logger.debug("Generated denormalizer for {}: {}", key, denormalizer);
		return denormalizer;
	}

	@Override
	public IESModelDenormalizer getModelValueDenormalizer(final ESModelIndexerProfile profile, final String tableName)
	{
		final ESModelDenormalizerKey key = ESModelDenormalizerKey.of(profile, tableName);
		return valueModelDenormalizers.get(key);
	}

	private void registerModelValueDenormalizer(final IESModelDenormalizer valueModelDenormalizer)
	{
		Check.assumeNotNull(valueModelDenormalizer, "Parameter valueModelDenormalizer is not null");
		ESModelDenormalizerKey key = ESModelDenormalizerKey.of(valueModelDenormalizer.getProfile(), valueModelDenormalizer.getModelTableName());
		valueModelDenormalizers.put(key, valueModelDenormalizer);

		logger.info("Registered value denormalizer {}: {}", key, valueModelDenormalizer);
	}

	private ESPOModelDenormalizerBuilder newModelDenormalizerBuilder(final ESModelIndexerProfile profile, final Class<?> modelClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		return newModelDenormalizerBuilder(profile, tableName);
	}

	private ESPOModelDenormalizerBuilder newModelDenormalizerBuilder(final ESModelIndexerProfile profile, final String tableName)
	{
		return ESPOModelDenormalizer.builder(this, profile, tableName);
	}

	@lombok.Value(staticConstructor = "of")
	private static final class ESModelDenormalizerKey
	{
		@lombok.NonNull
		final ESModelIndexerProfile profile;
		@lombok.NonNull
		final String modelTableName;
	}
}
