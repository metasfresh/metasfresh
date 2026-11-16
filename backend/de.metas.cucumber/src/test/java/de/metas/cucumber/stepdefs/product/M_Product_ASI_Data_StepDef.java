package de.metas.cucumber.stepdefs.product;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product_ASI_Data;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Step definitions for creating {@code M_Product_ASI_Data} records.
 * <p>
 * Provides the step:
 * <pre>{@code
 * Given metasfresh contains M_Product_ASI_Data:
 *   | Identifier | M_Product_ID | C_BPartner_ID | M_AttributeSetInstance_ID | SeqNo | GTIN          | ProductNo   |
 *   | asiData1   | product1     | customer1     | asi1                      | 10    | 0575095404663 | BUYER-12345 |
 * }</pre>
 *
 * <ul>
 * <li>{@code Identifier} — (required) step def identifier for cross-step references</li>
 * <li>{@code M_Product_ID} — (required) product identifier</li>
 * <li>{@code C_BPartner_ID} — (optional) business partner identifier; NULL = matches any BPartner</li>
 * <li>{@code M_AttributeSetInstance_ID} — (optional) ASI identifier; NULL = matches any ASI</li>
 * <li>{@code SeqNo} — (required) priority sequence number (lowest wins among matches)</li>
 * <li>{@code GTIN} — (optional) CU GTIN</li>
 * <li>{@code EAN_CU} — (optional) CU EAN</li>
 * <li>{@code UPC} — (optional) CU UPC</li>
 * <li>{@code ProductNo} — (optional) buyer product number</li>
 * <li>{@code ProductName} — (optional) product name</li>
 * <li>{@code ProductDescription} — (optional) product description</li>
 * <li>{@code EAN13_ProductCode} — (optional) EAN13 product code; used as {@code GTIN_CU} fallback when {@code GTIN} is empty</li>
 * </ul>
 */
public class M_Product_ASI_Data_StepDef
{
	private final M_Product_StepDefData productTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final M_AttributeSetInstance_StepDefData asiTable;
	private final M_Product_ASI_Data_StepDefData asiDataTable;

	public M_Product_ASI_Data_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final M_AttributeSetInstance_StepDefData asiTable,
			@NonNull final M_Product_ASI_Data_StepDefData asiDataTable)
	{
		this.productTable = productTable;
		this.bPartnerTable = bPartnerTable;
		this.asiTable = asiTable;
		this.asiDataTable = asiDataTable;
	}

	@Given("metasfresh contains M_Product_ASI_Data:")
	public void createProductASIData(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createRecord);
	}

	private void createRecord(@NonNull final DataTableRow row)
	{
		final I_M_Product_ASI_Data record = InterfaceWrapperHelper.newInstance(I_M_Product_ASI_Data.class);
		record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

		record.setM_Product_ID(row.getAsIdentifier(I_M_Product_ASI_Data.COLUMNNAME_M_Product_ID).lookupIdIn(productTable).getRepoId());

		row.getAsOptionalIdentifier(I_M_Product_ASI_Data.COLUMNNAME_C_BPartner_ID)
				.ifPresent(id -> {
					final de.metas.bpartner.BPartnerId bPartnerId = bPartnerTable.getIdOptional(id)
							.orElseGet(() -> id.getAsId(de.metas.bpartner.BPartnerId.class));
					record.setC_BPartner_ID(bPartnerId.getRepoId());
				});

		row.getAsOptionalIdentifier(I_M_Product_ASI_Data.COLUMNNAME_M_AttributeSetInstance_ID)
				.ifPresent(id -> record.setM_AttributeSetInstance_ID(asiTable.get(id).getM_AttributeSetInstance_ID()));

		record.setSeqNo(row.getAsInt(I_M_Product_ASI_Data.COLUMNNAME_SeqNo));

		row.getAsOptionalString(I_M_Product_ASI_Data.COLUMNNAME_GTIN).ifPresent(record::setGTIN);
		row.getAsOptionalString(I_M_Product_ASI_Data.COLUMNNAME_EAN_CU).ifPresent(record::setEAN_CU);
		row.getAsOptionalString(I_M_Product_ASI_Data.COLUMNNAME_UPC).ifPresent(record::setUPC);
		row.getAsOptionalString(I_M_Product_ASI_Data.COLUMNNAME_ProductNo).ifPresent(record::setProductNo);
		row.getAsOptionalString(I_M_Product_ASI_Data.COLUMNNAME_ProductName).ifPresent(record::setProductName);
		row.getAsOptionalString(I_M_Product_ASI_Data.COLUMNNAME_ProductDescription).ifPresent(record::setProductDescription);
		row.getAsOptionalString(I_M_Product_ASI_Data.COLUMNNAME_EAN13_ProductCode).ifPresent(record::setEAN13_ProductCode);

		saveRecord(record);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> asiDataTable.putOrReplace(identifier, record));
	}
}
