package de.metas.product.impexp;

import de.metas.impexp.ImportRecordsAsyncExecutor;
import de.metas.impexp.format.ImportTableDescriptorRepository;
import de.metas.impexp.processing.DBFunctionsRepository;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the import mapping I_Product.BestBeforeDays -> M_Product.BestBeforeDays for both the insert and the update path.
 */
public class ProductImportHelperTest
{
	private IProductDAO productsRepo;
	private ProductImportHelper helper;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new DBFunctionsRepository());
		SpringContextHolder.registerJUnitBean(new ImportTableDescriptorRepository());
		SpringContextHolder.registerJUnitBean(ImportRecordsAsyncExecutor.class, Mockito.mock(ImportRecordsAsyncExecutor.class));
		productsRepo = Services.get(IProductDAO.class);
		helper = ProductImportHelper.builder().process(new ProductImportProcess()).build();
	}

	private ProductImportContext newContext()
	{
		return ProductImportContext.builder()
				.productsCache(ProductsCache.builder().productsRepo(productsRepo).build())
				.build();
	}

	private I_I_Product newImportRecord(final String value, final int bestBeforeDays)
	{
		final I_I_Product importRecord = newInstance(I_I_Product.class);
		importRecord.setValue(value);
		importRecord.setName(value);
		importRecord.setBestBeforeDays(bestBeforeDays);
		save(importRecord);
		return importRecord;
	}

	@Test
	void newProduct_takesBestBeforeDaysFromImportRecord()
	{
		final ProductImportContext context = newContext();
		context.setCurrentImportRecord(newImportRecord("P1", 42));

		helper.importRecord(context);

		final I_M_Product product = load(ProductId.ofRepoId(context.getCurrentImportRecord().getM_Product_ID()), I_M_Product.class);
		assertThat(product.getBestBeforeDays()).isEqualTo(42);
	}

	@Test
	void existingProduct_isUpdatedWithBestBeforeDaysFromImportRecord()
	{
		final I_M_Product existing = newInstance(I_M_Product.class);
		existing.setValue("P2");
		existing.setName("P2");
		existing.setBestBeforeDays(10);
		save(existing);

		final I_I_Product importRecord = newImportRecord("P2", 99);
		importRecord.setM_Product_ID(existing.getM_Product_ID());
		save(importRecord);
		final ProductImportContext context = newContext();
		context.setCurrentImportRecord(importRecord);

		helper.importRecord(context);

		assertThat(load(existing.getM_Product_ID(), I_M_Product.class).getBestBeforeDays()).isEqualTo(99);
	}

	@Test
	void existingProduct_keepsBestBeforeDaysWhenImportRecordHasNone()
	{
		final I_M_Product existing = newInstance(I_M_Product.class);
		existing.setValue("P3");
		existing.setName("P3");
		existing.setBestBeforeDays(10);
		save(existing);

		final I_I_Product importRecord = newImportRecord("P3", 0);
		importRecord.setM_Product_ID(existing.getM_Product_ID());
		save(importRecord);
		final ProductImportContext context = newContext();
		context.setCurrentImportRecord(importRecord);

		helper.importRecord(context);

		assertThat(load(existing.getM_Product_ID(), I_M_Product.class).getBestBeforeDays()).isEqualTo(10);
	}
}
