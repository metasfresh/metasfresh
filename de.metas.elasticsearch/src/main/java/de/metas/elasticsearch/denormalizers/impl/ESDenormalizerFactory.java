package de.metas.elasticsearch.denormalizers.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
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
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_SalesRegion;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.CCache;

import de.metas.adempiere.model.I_M_Product;
import de.metas.elasticsearch.denormalizers.IESDenormalizerFactory;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.types.ESIndexType;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ESDenormalizerFactory implements IESDenormalizerFactory
{
	private final CCache<String, IESModelDenormalizer> tableDenormalizers = new CCache<>(ESDenormalizerFactory.class.getSimpleName() + "#TableDenormalizers", 20);

	private final ConcurrentHashMap<String, IESModelDenormalizer> valueModelDenormalizers = new ConcurrentHashMap<>();

	private ESDenormalizerFactory()
	{
		super();
		setupStandardDenormalizers();
	}

	private final void setupStandardDenormalizers()
	{
		//
		// Location
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_C_Country.class)
				.includeColumn(I_C_Country.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_C_Location.class)
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
				.build());

		//
		// BPartner related
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_C_BPartner_Location.class)
				.includeColumn(I_C_BPartner_Location.COLUMNNAME_Name)
				.includeColumn(I_C_BPartner_Location.COLUMNNAME_Phone)
				.includeColumn(I_C_BPartner_Location.COLUMNNAME_Phone2)
				.includeColumn(I_C_BPartner_Location.COLUMNNAME_Fax)
				.includeColumn(I_C_BPartner_Location.COLUMNNAME_C_Location_ID)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_AD_User.class)
				.includeColumn(I_AD_User.COLUMNNAME_Name)
				.includeColumn(I_AD_User.COLUMNNAME_EMail)
				.includeColumn(I_AD_User.COLUMNNAME_Phone)
				.includeColumn(I_AD_User.COLUMNNAME_Phone2)
				.includeColumn(I_AD_User.COLUMNNAME_Fax)
				.excludeColumn(I_AD_User.COLUMNNAME_C_BPartner_ID)
				.excludeColumn(I_AD_User.COLUMNNAME_C_BPartner_Location_ID)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_C_BP_Group.class)
				.includeColumn(I_C_BP_Group.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
				// NOTE: C_BPartner is defined in "standard dimensions part"

		//
		// Product related
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_M_Product_Category.class)
				.includeColumn(I_M_Product_Category.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_M_Product_Category.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
				// NOTE: M_Product is defined in "standard dimensions part"

		//
		// C_DocType related
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_C_DocType.class)
				.includeColumn(I_C_DocType.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_DocType.COLUMNNAME_DocBaseType)
				.includeColumn(I_C_DocType.COLUMNNAME_DocSubType)
				.excludeStandardColumns()
				.build());

		//
		// C_Currency related
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_C_Currency.class)
				.includeColumn(I_C_Currency.COLUMNNAME_ISO_Code).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());

		//
		// Standard dimensions
		//
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_AD_Org.class)
				.includeColumn(I_AD_Org.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_AD_Org.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_M_Product.class)
				.includeColumn(org.compiere.model.I_M_Product.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(org.compiere.model.I_M_Product.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.includeColumn(org.compiere.model.I_M_Product.COLUMNNAME_M_Product_Category_ID)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_C_BPartner.class)
				.includeColumn(I_C_BPartner.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_BPartner.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_BPartner.COLUMNNAME_C_BP_Group_ID)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_C_SalesRegion.class)
				.includeColumn(I_C_SalesRegion.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_SalesRegion.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_C_Project.class)
				.includeColumn(I_C_Project.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_Project.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_C_Campaign.class)
				.includeColumn(I_C_Campaign.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_Campaign.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
		registerModelValueDenormalizer(newModelDenormalizerBuilder(I_C_Activity.class)
				.includeColumn(I_C_Activity.COLUMNNAME_Value).index(ESIndexType.NotAnalyzed)
				.includeColumn(I_C_Activity.COLUMNNAME_Name).index(ESIndexType.NotAnalyzed)
				.excludeStandardColumns()
				.build());
	}

	@Override
	public IESModelDenormalizer getModelDenormalizer(final Class<?> modelClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		return tableDenormalizers.getOrLoad(tableName, () -> createModelDenormalizer(modelClass));
	}

	private IESModelDenormalizer createModelDenormalizer(final Class<?> modelClass)
	{
		return newModelDenormalizerBuilder(modelClass)
				.excludeStandardColumns()
				.excludeColumn("DocAction") // FIXME: hardcoded
				.build();
	}

	@Override
	public IESModelDenormalizer getModelValueDenormalizer(final String tableName)
	{
		return valueModelDenormalizers.get(tableName);
	}

	public void registerModelValueDenormalizer(final IESModelDenormalizer valueModelDenormalizer)
	{
		Check.assumeNotNull(valueModelDenormalizer, "Parameter valueModelDenormalizer is not null");
		final Class<?> modelClass = valueModelDenormalizer.getModelClass();
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		valueModelDenormalizers.put(tableName, valueModelDenormalizer);
	}

	public ESModelDenormalizer.Builder newModelDenormalizerBuilder(final Class<?> modelClass)
	{
		return ESModelDenormalizer.builder(this, modelClass);
	}
}
