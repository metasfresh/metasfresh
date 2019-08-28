package de.metas.impexp.processing;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.I_I_BPartner_GlobalID;
import org.compiere.model.I_I_DiscountSchema;
import org.compiere.model.I_I_Inventory;
import org.compiere.model.I_I_Postal;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_I_Replenish;
import org.compiere.model.I_I_Request;
import org.compiere.model.I_I_User;
import org.slf4j.Logger;

import de.metas.bpartner.impexp.BPartnerImportProcess;
import de.metas.dataentry.data.impexp.DataEntryRecordsImportProcess;
import de.metas.dataentry.model.I_I_DataEntry_Record;
import de.metas.globalid.impexp.BPartnerGlobalIDImportProcess;
import de.metas.impexp.processing.IAsyncImportProcessBuilderFactory;
import de.metas.impexp.processing.IImportProcess;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.impexp.processing.inventory.InventoryImportProcess;
import de.metas.impexp.processing.product.ProductImportProcess;
import de.metas.impexp.processing.request.RequestImportProcess;
import de.metas.impexp.processing.spi.IAsyncImportProcessBuilder;
import de.metas.impexp.processing.user.ADUserImportProcess;
import de.metas.location.impexp.PostalCodeImportProcess;
import de.metas.logging.LogManager;
import de.metas.pricing.impexp.DiscountSchemaImportProcess;
import de.metas.replenishment.impexp.ReplenishmentImportProcess;
import lombok.NonNull;

public class ImportProcessFactory implements IImportProcessFactory
{
	private static final Logger logger = LogManager.getLogger(ImportProcessFactory.class);

	private final ImportProcessDescriptorsMap importProcessDescriptorsMap = new ImportProcessDescriptorsMap();
	private final ImportTablesRelatedProcessesRegistry relatedProcessesRegistry = new ImportTablesRelatedProcessesRegistry();
	private IAsyncImportProcessBuilderFactory asyncImportProcessBuilderFactory;

	public ImportProcessFactory()
	{
		// Register standard import processes
		registerImportProcess(I_I_BPartner.class, BPartnerImportProcess.class);
		registerImportProcess(I_I_User.class, ADUserImportProcess.class);
		registerImportProcess(I_I_Product.class, ProductImportProcess.class);
		registerImportProcess(I_I_Request.class, RequestImportProcess.class);
		registerImportProcess(I_I_Inventory.class, InventoryImportProcess.class);
		registerImportProcess(I_I_DiscountSchema.class, DiscountSchemaImportProcess.class);
		registerImportProcess(I_I_BPartner_GlobalID.class, BPartnerGlobalIDImportProcess.class);
		registerImportProcess(I_I_Replenish.class, ReplenishmentImportProcess.class);
		registerImportProcess(I_I_Postal.class, PostalCodeImportProcess.class);
		registerImportProcess(I_I_DataEntry_Record.class, DataEntryRecordsImportProcess.class);
	}

	@Override
	public <ImportRecordType> void registerImportProcess(
			@NonNull final Class<ImportRecordType> modelImportClass,
			@NonNull final Class<? extends IImportProcess<ImportRecordType>> importProcessClass)
	{
		final ImportProcessDescriptor descriptor = importProcessDescriptorsMap.register(modelImportClass, importProcessClass);
		logger.info("Registered import process: {}", descriptor);

		relatedProcessesRegistry.registerImportTable(descriptor.getImportTableName());
	}

	@Override
	public void setDeleteImportDataProcessClass(@NonNull final Class<?> deleteImportDataProcessClass)
	{
		relatedProcessesRegistry.setDeleteImportDataProcessClass(deleteImportDataProcessClass);
	}

	@Override
	public <ImportRecordType> IImportProcess<ImportRecordType> newImportProcess(@NonNull final Class<ImportRecordType> modelImportClass)
	{
		final IImportProcess<ImportRecordType> importProcess = newImportProcessOrNull(modelImportClass);
		if (importProcess == null)
		{
			throw new AdempiereException("No import process found for " + modelImportClass);
		}
		return importProcess;
	}

	@Nullable
	@Override
	public <ImportRecordType> IImportProcess<ImportRecordType> newImportProcessOrNull(@NonNull final Class<ImportRecordType> modelImportClass)
	{
		final Class<?> importProcessClass = importProcessDescriptorsMap.getImportProcessClassByModelImportClassOrNull(modelImportClass);
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
		catch (final Exception e)
		{
			throw new AdempiereException("Failed instantiating " + importProcessClass, e);
		}
	}

	@Nullable
	@Override
	public <ImportRecordType> IImportProcess<ImportRecordType> newImportProcessForTableNameOrNull(@NonNull final String importTableName)
	{
		final Class<?> importProcessClass = importProcessDescriptorsMap.getImportProcessClassByImportTableNameOrNull(importTableName);
		if (importProcessClass == null)
		{
			return null;
		}

		return newInstance(importProcessClass);
	}

	@Override
	public <ImportRecordType> IImportProcess<ImportRecordType> newImportProcessForTableName(@NonNull final String importTableName)
	{
		final IImportProcess<ImportRecordType> importProcess = newImportProcessForTableNameOrNull(importTableName);
		if (importProcess == null)
		{
			throw new AdempiereException("No import process found for " + importTableName);
		}
		return importProcess;
	}

	@Override
	public IAsyncImportProcessBuilder newAsyncImportProcessBuilder()
	{
		final IAsyncImportProcessBuilderFactory asyncImportProcessBuilderFactory = this.asyncImportProcessBuilderFactory;
		if (asyncImportProcessBuilderFactory == null)
		{
			throw new AdempiereException("Async import is not configured");
		}

		final IAsyncImportProcessBuilder builder = asyncImportProcessBuilderFactory.newAsyncImportProcessBuilder();
		if (builder == null)
		{
			// shall not happen
			throw new AdempiereException("Got null builder from " + asyncImportProcessBuilderFactory);
		}

		return builder;
	}

	@Override
	public void setAsyncImportProcessBuilderFactory(@NonNull final IAsyncImportProcessBuilderFactory asyncImportProcessBuilderFactory)
	{
		this.asyncImportProcessBuilderFactory = asyncImportProcessBuilderFactory;
		logger.info("Set {}", asyncImportProcessBuilderFactory);
	}
}
