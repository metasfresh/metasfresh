package de.metas.cucumber.stepdefs.importorder;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_M_Product;
import org.compiere.process.ImportProduct;
import org.compiere.util.DB;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for I_Product staging table operations and ImportProduct process execution.
 */
public class I_Product_StepDef
{
	private final I_Product_StepDefData iProductTable;
	private final M_Product_StepDefData productTable;
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	public I_Product_StepDef(
			@NonNull final I_Product_StepDefData iProductTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.iProductTable = iProductTable;
		this.productTable = productTable;
	}

	/**
	 * Creates I_Product staging records for import testing.
	 *
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>Value</b> — (optional) product search key<br>
	 *   <b>Name</b> — (optional) product name<br>
	 *   <b>ProductType</b> — (optional) product type: I=Item, S=Service, etc.<br>
	 *   <b>IsStocked</b> — (optional) is stocked flag (Y/N)<br>
	 *   <b>ProductCategory_Value</b> — (optional) product category search key<br>
	 *   <b>X12DE355</b> — (optional) UOM code (e.g. PCE, KGM)<br>
	 *   <b>BPartner_Value</b> — (optional) BPartner search key to resolve<br>
	 */
	@Given("metasfresh contains I_Product:")
	public void metasfresh_contains_I_Product(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_Product record = InterfaceWrapperHelper.newInstance(I_I_Product.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalString(I_I_Product.COLUMNNAME_Value)
					.ifPresent(record::setValue);
			row.getAsOptionalString(I_I_Product.COLUMNNAME_Name)
					.ifPresent(record::setName);
			row.getAsOptionalString(I_I_Product.COLUMNNAME_ProductType)
					.ifPresent(record::setProductType);
			row.getAsOptionalString(I_I_Product.COLUMNNAME_ProductCategory_Value)
					.ifPresent(record::setProductCategory_Value);
			row.getAsOptionalString(I_I_Product.COLUMNNAME_X12DE355)
					.ifPresent(record::setX12DE355);
			row.getAsOptionalString(I_I_Product.COLUMNNAME_BPartner_Value)
					.ifPresent(record::setBPartner_Value);
			row.getAsOptionalBoolean(I_I_Product.COLUMNNAME_IsStocked)
					.ifPresent(record::setIsStocked);

			record.setI_IsImported(false);
			InterfaceWrapperHelper.saveRecord(record);

			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_Product SET C_BPartner_ID = NULL WHERE I_Product_ID = " + record.getI_Product_ID(),
					ITrx.TRXNAME_None);

			row.getAsOptionalIdentifier().ifPresent(identifier -> iProductTable.putOrReplace(identifier, record));
		});
	}

	/**
	 * Runs the ImportProduct process with the current client context.
	 */
	@When("the ImportProduct process is invoked")
	public void the_ImportProduct_process_is_invoked()
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(ImportProduct.class);

		final int adClientId = StepDefConstants.CLIENT_ID.getRepoId();

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.addParameter("AD_Client_ID", BigDecimal.valueOf(adClientId))
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();
	}

	/**
	 * Validates I_Product staging records after import process execution.
	 * Uses direct SQL to read fields because the import process commits in its own transaction.
	 *
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias referencing a previously created I_Product record<br>
	 *   <b>I_ErrorMsg</b> — (optional) expected error message substring<br>
	 *   <b>I_IsImported</b> — (optional) expected import status (Y/E/N)<br>
	 *   <b>IsResolved</b> — (optional, boolean) if true, asserts C_BPartner_ID &gt; 0<br>
	 */
	@Then("validate I_Product:")
	public void validate_I_Product(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_Product record = rowIdentifier.lookupNotNullIn(iProductTable);
			InterfaceWrapperHelper.refresh(record);

			row.getAsOptionalString(I_I_Product.COLUMNNAME_I_ErrorMsg).ifPresent(expectedErr -> {
				final String dbErrorMsg = DB.getSQLValueStringEx(ITrx.TRXNAME_None,
						"SELECT I_ErrorMsg FROM I_Product WHERE I_Product_ID=?", record.getI_Product_ID());
				final String dbImported = DB.getSQLValueStringEx(ITrx.TRXNAME_None,
						"SELECT I_IsImported FROM I_Product WHERE I_Product_ID=?", record.getI_Product_ID());
				assertThat(dbErrorMsg)
						.as("I_Product[%s].I_ErrorMsg should contain '%s'", rowIdentifier, expectedErr)
						.contains(expectedErr);
				assertThat(dbImported)
						.as("I_Product[%s].I_IsImported should be 'E'", rowIdentifier)
						.isEqualTo("E");
				assertThat(record.getC_BPartner_ID())
						.as("I_Product[%s].C_BPartner_ID should be 0 (NULL)", rowIdentifier)
						.isEqualTo(0);
			});

			row.getAsOptionalString(I_I_Product.COLUMNNAME_I_IsImported + "_expected").ifPresent(expectedStatus -> {
				final String dbImported = DB.getSQLValueStringEx(ITrx.TRXNAME_None,
						"SELECT I_IsImported FROM I_Product WHERE I_Product_ID=?", record.getI_Product_ID());
				assertThat(dbImported)
						.as("I_Product[%s].I_IsImported", rowIdentifier)
						.isEqualTo(expectedStatus);
			});

			if (row.getAsOptionalBoolean("IsResolved").orElseFalse())
			{
				assertThat(record.getC_BPartner_ID())
						.as("I_Product[%s].C_BPartner_ID should be resolved (non-zero)", rowIdentifier)
						.isGreaterThan(0);
			}
		});
	}

	/**
	 * Validates the M_Product record created/updated by the import process.
	 * Uses direct SQL because the import process commits in its own transaction.
	 *
	 * @cucumber.columns
	 *   <b>I_Product_Identifier</b> — (required) alias referencing I_Product staging record<br>
	 *   <b>IsStocked</b> — (optional) expected IsStocked value (Y/N)<br>
	 *   <b>IsActive</b> — (optional) expected IsActive value (Y/N)<br>
	 *   <b>ProductType</b> — (optional) expected ProductType (I/S/R/E)<br>
	 *   <b>M_Product_Identifier</b> — (optional) store resulting M_Product for later reference<br>
	 */
	@Then("validate M_Product for I_Product:")
	public void validate_M_Product_for_I_Product(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier iProductIdentifier = row.getAsIdentifier("I_Product_Identifier");
			final I_I_Product iProduct = iProductIdentifier.lookupNotNullIn(iProductTable);

			// Read M_Product_ID from staging table via SQL (import commits in own trx)
			final int mProductId = DB.getSQLValueEx(ITrx.TRXNAME_None,
					"SELECT M_Product_ID FROM I_Product WHERE I_Product_ID=?", iProduct.getI_Product_ID());

			assertThat(mProductId)
					.as("I_Product[%s] should have M_Product_ID set after import", iProductIdentifier)
					.isGreaterThan(0);

			row.getAsOptionalString("IsStocked").ifPresent(expected -> {
				final String actual = DB.getSQLValueStringEx(ITrx.TRXNAME_None,
						"SELECT IsStocked FROM M_Product WHERE M_Product_ID=?", mProductId);
				assertThat(actual)
						.as("M_Product.IsStocked for I_Product[%s]", iProductIdentifier)
						.isEqualTo(expected);
			});

			row.getAsOptionalString("IsActive").ifPresent(expected -> {
				final String actual = DB.getSQLValueStringEx(ITrx.TRXNAME_None,
						"SELECT IsActive FROM M_Product WHERE M_Product_ID=?", mProductId);
				assertThat(actual)
						.as("M_Product.IsActive for I_Product[%s]", iProductIdentifier)
						.isEqualTo(expected);
			});

			row.getAsOptionalString("ProductType").ifPresent(expected -> {
				final String actual = DB.getSQLValueStringEx(ITrx.TRXNAME_None,
						"SELECT ProductType FROM M_Product WHERE M_Product_ID=?", mProductId);
				assertThat(actual)
						.as("M_Product.ProductType for I_Product[%s]", iProductIdentifier)
						.isEqualTo(expected);
			});

			// Optionally store the M_Product for later reference
			row.getAsOptionalIdentifier("M_Product_Identifier").ifPresent(productIdentifier -> {
				final I_M_Product product = InterfaceWrapperHelper.load(mProductId, I_M_Product.class);
				productTable.putOrReplace(productIdentifier, product);
			});
		});
	}
}
