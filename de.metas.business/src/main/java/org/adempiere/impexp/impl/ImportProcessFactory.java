package org.adempiere.impexp.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.HashMap;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.ADUserImportProcess;
import org.adempiere.impexp.IImportProcess;
import org.adempiere.impexp.IImportProcessFactory;
import org.adempiere.impexp.RequestImportProcess;
import org.adempiere.impexp.bpartner.BPartnerImportProcess;
import org.adempiere.impexp.inventory.InventoryImportProcess;
import org.adempiere.impexp.product.ProductImportProcess;
import org.adempiere.impexp.spi.IAsyncImportProcessBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.I_I_DiscountSchema;
import org.compiere.model.I_I_Inventory;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_I_Request;
import org.compiere.model.I_I_User;

import com.google.common.base.Supplier;

import de.metas.pricing.impexp.DiscountSchemaImportProcess;

public class ImportProcessFactory implements IImportProcessFactory
{
	private final Map<Class<?>, Class<?>> modelImportClass2importProcessClasses = new HashMap<>();
	private final Map<String, Class<?>> tableName2importProcessClasses = new HashMap<>();
	private Supplier<IAsyncImportProcessBuilder> asyncImportProcessBuilderSupplier;

	public ImportProcessFactory()
	{
		// Register standard import processes
		registerImportProcess(I_I_BPartner.class, BPartnerImportProcess.class);
		registerImportProcess(I_I_User.class, ADUserImportProcess.class);
		registerImportProcess(I_I_Product.class, ProductImportProcess.class);
		registerImportProcess(I_I_Request.class, RequestImportProcess.class);
		registerImportProcess(I_I_Inventory.class, InventoryImportProcess.class);
		registerImportProcess(I_I_DiscountSchema.class, DiscountSchemaImportProcess.class);
	}

	@Override
	public <ImportRecordType> void registerImportProcess(final Class<ImportRecordType> modelImportClass, final Class<? extends IImportProcess<ImportRecordType>> importProcessClass)
	{
		Check.assumeNotNull(modelImportClass, "modelImportClass not null");
		Check.assumeNotNull(importProcessClass, "importProcessClass not null");

		modelImportClass2importProcessClasses.put(modelImportClass, importProcessClass);

		final String tableName = InterfaceWrapperHelper.getTableName(modelImportClass);
		tableName2importProcessClasses.put(tableName, importProcessClass);
	}

	@Override
	public <ImportRecordType> IImportProcess<ImportRecordType> newImportProcess(final Class<ImportRecordType> modelImportClass)
	{
		final IImportProcess<ImportRecordType> importProcess = newImportProcessOrNull(modelImportClass);
		Check.assumeNotNull(importProcess, "importProcess not null for {}", modelImportClass);
		return importProcess;
	}

	@Override
	public <ImportRecordType> IImportProcess<ImportRecordType> newImportProcessOrNull(final Class<ImportRecordType> modelImportClass)
	{
		Check.assumeNotNull(modelImportClass, "modelImportClass not null");
		final Class<?> importProcessClass = modelImportClass2importProcessClasses.get(modelImportClass);
		if (importProcessClass == null)
		{
			return null;
		}

		return newInstance(importProcessClass);
	}

	private <ImportRecordType> IImportProcess<ImportRecordType> newInstance(final Class<?> importProcessClass)
	{
		try
		{
			@SuppressWarnings("unchecked")
			final IImportProcess<ImportRecordType> importProcess = (IImportProcess<ImportRecordType>)importProcessClass.newInstance();
			return importProcess;
		}
		catch (Exception e)
		{
			throw new AdempiereException("Failed instantiating " + importProcessClass, e);
		}
	}

	@Override
	public <ImportRecordType> IImportProcess<ImportRecordType> newImportProcessForTableNameOrNull(final String tableName)
	{
		Check.assumeNotNull(tableName, "tableName not null");
		final Class<?> importProcessClass = tableName2importProcessClasses.get(tableName);
		if (importProcessClass == null)
		{
			return null;
		}

		return newInstance(importProcessClass);
	}

	@Override
	public <ImportRecordType> IImportProcess<ImportRecordType> newImportProcessForTableName(final String tableName)
	{
		final IImportProcess<ImportRecordType> importProcess = newImportProcessForTableNameOrNull(tableName);
		Check.assumeNotNull(importProcess, "importProcess not null for {}", tableName);
		return importProcess;
	}

	@Override
	public IAsyncImportProcessBuilder newAsyncImportProcessBuilder()
	{
		Check.assumeNotNull(asyncImportProcessBuilderSupplier, "A supplier for {} shall be registered first", IAsyncImportProcessBuilder.class);
		return asyncImportProcessBuilderSupplier.get();
	}

	@Override
	public void setAsyncImportProcessBuilderSupplier(Supplier<IAsyncImportProcessBuilder> asyncImportProcessBuilderSupplier)
	{
		Check.assumeNotNull(asyncImportProcessBuilderSupplier, "asyncImportProcessBuilderSupplier not null");
		this.asyncImportProcessBuilderSupplier = asyncImportProcessBuilderSupplier;
	}
}
