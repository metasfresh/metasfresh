package de.metas.product.impexp;

import de.metas.impexp.ImportRecordsAsyncExecutor;
import de.metas.impexp.format.ImportTableDescriptorRepository;
import de.metas.impexp.processing.DBFunctionsRepository;
import de.metas.product.ProductId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.Mutable;
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

public class ProductImportProcessTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new DBFunctionsRepository());
		SpringContextHolder.registerJUnitBean(new ImportTableDescriptorRepository());
		SpringContextHolder.registerJUnitBean(ImportRecordsAsyncExecutor.class, Mockito.mock(ImportRecordsAsyncExecutor.class));
	}

	private I_I_Product newImportRecord(final String value, final int orgId)
	{
		final I_I_Product importRecord = newInstance(I_I_Product.class);
		importRecord.setValue(value);
		importRecord.setName(value);
		importRecord.setAD_Org_ID(orgId);
		save(importRecord);
		return importRecord;
	}

	@Test
	void sameSearchKeyInDifferentOrgs_isImportedAsSeparateProducts()
	{
		final I_I_Product rowOrgA = newImportRecord("P1", 1000001);
		final I_I_Product rowOrgB = newImportRecord("P1", 1000002);

		final ProductImportProcess importProcess = new ProductImportProcess();
		final Mutable<Object> state = new Mutable<>();
		importProcess.importRecord(state, rowOrgA, false /* isInsertOnly */);
		importProcess.importRecord(state, rowOrgB, false /* isInsertOnly */);

		assertThat(rowOrgB.getM_Product_ID()).isNotEqualTo(rowOrgA.getM_Product_ID());
		assertThat(load(ProductId.ofRepoId(rowOrgA.getM_Product_ID()), I_M_Product.class).getAD_Org_ID()).isEqualTo(1000001);
		assertThat(load(ProductId.ofRepoId(rowOrgB.getM_Product_ID()), I_M_Product.class).getAD_Org_ID()).isEqualTo(1000002);
	}

	@Test
	void sameSearchKeyInSameOrg_isImportedAsOneProduct()
	{
		final I_I_Product row1 = newImportRecord("P1", 1000001);
		final I_I_Product row2 = newImportRecord("P1", 1000001);

		final ProductImportProcess importProcess = new ProductImportProcess();
		final Mutable<Object> state = new Mutable<>();
		importProcess.importRecord(state, row1, false /* isInsertOnly */);
		importProcess.importRecord(state, row2, false /* isInsertOnly */);

		assertThat(row2.getM_Product_ID()).isEqualTo(row1.getM_Product_ID());
	}
}
