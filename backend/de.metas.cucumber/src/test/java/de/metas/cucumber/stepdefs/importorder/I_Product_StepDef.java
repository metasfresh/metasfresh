package de.metas.cucumber.stepdefs.importorder;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_M_Product;
import org.compiere.process.ImportProduct;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

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
	 * <p>
	 * Value and Name are auto-generated from the Identifier when not provided,
	 * ensuring unique data across test runs and scenarios.
	 *
	 * @cucumber.columns
	 * <b>Identifier</b> — (required) alias for cross-step reference<br>
	 * <b>Value</b> — (optional) product search key; auto-generated if not provided<br>
	 * <b>Name</b> — (optional) product name; auto-generated if not provided<br>
	 * <b>ProductType</b> — (optional) product type: I=Item, S=Service, etc.<br>
	 * <b>IsStocked</b> — (optional) is stocked flag (Y/N); when omitted, NULL means "not provided"<br>
	 * <b>X12DE355</b> — (optional) UOM code (e.g. PCE, KGM)<br>
	 * <b>ProductCategory_Value</b> — (optional) product category search key<br>
	 * <b>BPartner_Value</b> — (optional) BPartner search key to resolve<br>
	 * <b>M_Product_Identifier</b> — (optional) copies Value from the referenced M_Product (for upsert tests)<br>
	 */
	@Given("metasfresh contains I_Product:")
	public void metasfresh_contains_I_Product(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createIProduct);
	}

	private void createIProduct(@NonNull final DataTableRow row)
	{
		final I_I_Product record = InterfaceWrapperHelper.newInstance(I_I_Product.class);
		record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

		// Value/Name: copy from referenced M_Product, or use explicit value, or auto-generate
		final StepDefDataIdentifier mProductIdentifier = row.getAsOptionalIdentifier("M_Product_Identifier").orElse(null);
		if (mProductIdentifier != null)
		{
			final I_M_Product product = mProductIdentifier.lookupNotNullIn(productTable);
			record.setValue(product.getValue());
			record.setName(product.getName());
		}
		else
		{
			final ValueAndName valueAndName = row.suggestValueAndName();
			record.setValue(valueAndName.getValue());
			record.setName(valueAndName.getName());
		}

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

		// The PO framework auto-fills C_BPartner_ID from context; null it out so the import resolves it from BPartner_Value.
		// This cannot be done via InterfaceWrapperHelper because the generated setter takes int (not nullable).
		record.setC_BPartner_ID(0);
		InterfaceWrapperHelper.saveRecord(record);

		row.getAsOptionalIdentifier().ifPresent(identifier -> iProductTable.putOrReplace(identifier, record));
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
	 * Validates the M_Product record created/updated by the import process.
	 * Uses {@link InterfaceWrapperHelper#loadOutOfTrx} because the import process commits in its own transaction.
	 *
	 * @cucumber.columns
	 * <b>I_Product_Identifier</b> — (required) alias referencing I_Product staging record<br>
	 * <b>IsStocked</b> — (optional) expected IsStocked value (Y/N)<br>
	 * <b>IsActive</b> — (optional) expected IsActive value (Y/N)<br>
	 * <b>ProductType</b> — (optional) expected ProductType (I/S/R/E)<br>
	 * <b>M_Product_Identifier</b> — (optional) store resulting M_Product for later reference<br>
	 */
	@Then("validate M_Product for I_Product:")
	public void validate_M_Product_for_I_Product(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::validateMProductForIProduct);
	}

	private void validateMProductForIProduct(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier iProductIdentifier = row.getAsIdentifier("I_Product_Identifier");
		final I_I_Product iProduct = iProductIdentifier.lookupNotNullIn(iProductTable);

		// Reload I_Product out of trx to see the M_Product_ID set by the import process (committed in its own trx)
		final I_I_Product freshIProduct = InterfaceWrapperHelper.loadOutOfTrx(iProduct.getI_Product_ID(), I_I_Product.class);
		final int mProductId = freshIProduct.getM_Product_ID();

		assertThat(mProductId)
				.as("I_Product[%s] should have M_Product_ID set after import", iProductIdentifier)
				.isGreaterThan(0);

		final I_M_Product product = InterfaceWrapperHelper.loadOutOfTrx(mProductId, I_M_Product.class);

		row.getAsOptionalBoolean("IsStocked").ifPresent(expected ->
				assertThat(product.isStocked())
						.as("M_Product.IsStocked for I_Product[%s]", iProductIdentifier)
						.isEqualTo(expected));

		row.getAsOptionalBoolean("IsActive").ifPresent(expected ->
				assertThat(product.isActive())
						.as("M_Product.IsActive for I_Product[%s]", iProductIdentifier)
						.isEqualTo(expected));

		row.getAsOptionalString("ProductType").ifPresent(expected ->
				assertThat(product.getProductType())
						.as("M_Product.ProductType for I_Product[%s]", iProductIdentifier)
						.isEqualTo(expected));

		// Optionally store the M_Product for later reference
		row.getAsOptionalIdentifier("M_Product_Identifier").ifPresent(productIdentifier ->
				productTable.putOrReplace(productIdentifier, product));
	}
}
