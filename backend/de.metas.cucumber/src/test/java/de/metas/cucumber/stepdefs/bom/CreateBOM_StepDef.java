/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.bom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.common.rest_api.v2.bom.JsonBOMCreateResponse;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.billofmaterial.PP_Product_BOMLine_StepDefData;
import de.metas.cucumber.stepdefs.billofmaterial.PP_Product_BOMVersions_StepDefData;
import de.metas.cucumber.stepdefs.billofmaterial.PP_Product_BOM_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMType;
import org.eevolution.api.BOMUse;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.api.impl.ProductBOMVersionsDAO;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.model.X_PP_Product_BOM;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID;
import static org.eevolution.model.I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOMLine_ID;

public class CreateBOM_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);
	private final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
	private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final ProductBOMVersionsDAO productBOMVersionsDAO = SpringContextHolder.instance.getBean(ProductBOMVersionsDAO.class);

	private final PP_Product_BOMVersions_StepDefData productBOMVersionsTable;
	private final PP_Product_BOM_StepDefData productBomTable;
	private final PP_Product_BOMLine_StepDefData productBomLineTable;
	private final M_Product_StepDefData productTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	private final TestContext testContext;

	private static final int DEFAULT_C_DOCTYPE_ID = 541027;

	final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	public CreateBOM_StepDef(
			@NonNull final PP_Product_BOMVersions_StepDefData productBOMVersionsTable,
			@NonNull final PP_Product_BOM_StepDefData productBomTable,
			@NonNull final PP_Product_BOMLine_StepDefData productBomLineTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable,
			@NonNull final TestContext testContext)
	{
		this.productBOMVersionsTable = productBOMVersionsTable;
		this.productBomTable = productBomTable;
		this.productBomLineTable = productBomLineTable;
		this.productTable = productTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
		this.testContext = testContext;
	}

	@Then("process metasfresh response:")
	public void processMetasfreshResponse(@NonNull final DataTable table) throws JsonProcessingException
	{
		final JsonBOMCreateResponse response = mapper.readValue(testContext.getApiResponse().getContent(), JsonBOMCreateResponse.class);
		assertThat(response).isNotNull();

		final DataTableRow row = DataTableRow.singleRow(table);
		final StepDefDataIdentifier bomIdentifier = row.getAsIdentifier("PP_Product_BOM_ID");
		final StepDefDataIdentifier bomVersionsIdentifier = row.getAsIdentifier("PP_Product_BOMVersions_ID");

		processResponse(response, bomIdentifier, bomVersionsIdentifier);
	}

	@And("verify that bomVersions was created for product")
	public void verifyThatBomVersionsWasCreatedForProduct(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final I_PP_Product_BOMVersions bomVersions = row.getAsIdentifier("PP_Product_BOMVersions_ID").lookupIn(productBOMVersionsTable);
			final I_M_Product product = row.getAsIdentifier("M_Product_ID").lookupIn(productTable);
			final String expectedName = row.getAsOptionalName("Name").orElseGet(product::getName);

			final SoftAssertions softly = new SoftAssertions();
			assertThat(bomVersions.getM_Product_ID()).as("M_Product_ID").isEqualTo(product.getM_Product_ID());
			assertThat(bomVersions.getName()).as("Name").isEqualTo(expectedName);
			softly.assertAll();
		});
	}

	@Then("verify that bom was created for product")
	public void verifyThatBomWasCreatedForProduct(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final I_PP_Product_BOM bom = row.getAsIdentifier("PP_Product_BOM_ID").lookupIn(productBomTable);
			final I_M_Product product = row.getAsIdentifier("M_Product_ID").lookupIn(productTable);
			final I_PP_Product_BOMVersions bomVersions = row.getAsIdentifier("PP_Product_BOMVersions_ID").lookupIn(productBOMVersionsTable);
			final I_C_UOM bomProductExpectedUOM = uomDao.getByX12DE355(X12DE355.ofCode(row.getAsString("UomCode")));
			final Instant validFrom = row.getAsInstant("ValidFrom");
			final String expectedProductValue = row.getAsOptionalString("ProductValue").orElseGet(product::getValue);

			final SoftAssertions softly = new SoftAssertions();
			softly.assertThat(bom.getM_Product_ID()).as("M_Product_ID").isEqualTo(product.getM_Product_ID());
			softly.assertThat(bom.getPP_Product_BOMVersions_ID()).as("PP_Product_BOMVersions_ID").isEqualTo(bomVersions.getPP_Product_BOMVersions_ID());
			softly.assertThat(bom.getC_UOM_ID()).as("C_UOM_ID").isEqualTo(bomProductExpectedUOM.getC_UOM_ID());
			softly.assertThat(bom.getValidFrom()).as("ValidFrom").isEqualTo(TimeUtil.asTimestamp(validFrom));
			softly.assertThat(bom.getValue()).as("BOMValue vs ProductValue").isEqualTo(expectedProductValue);
			softly.assertAll();

			row.getAsOptionalIdentifier(COLUMNNAME_M_AttributeSetInstance_ID)
					.ifPresent((asiIdentifier) -> {
						final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(bom.getM_AttributeSetInstance_ID());
						final I_M_AttributeSetInstance asi = attributeDAO.getAttributeSetInstanceById(asiId);
						attributeSetInstanceTable.put(asiIdentifier, asi);
					});
		});
	}

	@And("verify that bomLine was created for bom")
	public void verifyThatBomLineWasCreatedForBom(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> bomLinesTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : bomLinesTableList)
		{
			final String bomIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "PP_Product_BOM_ID.Identifier");
			final String productIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "M_Product_ID.Identifier");

			final I_PP_Product_BOM bom = productBomTable.get(bomIdentifier);
			final I_M_Product product = productTable.get(productIdentifier);

			final I_PP_Product_BOMLine bomLine = queryBL.createQueryBuilder(I_PP_Product_BOMLine.class)
					.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID, bom.getPP_Product_BOM_ID())
					.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
					.create()
					.firstOnly(I_PP_Product_BOMLine.class);

			assertThat(bomLine).isNotNull();

			final int line = DataTableUtil.extractIntForColumnName(dataTableRow, I_PP_Product_BOMLine.COLUMNNAME_Line);
			final Boolean isQtyPercentage = DataTableUtil.extractBooleanForColumnName(dataTableRow, I_PP_Product_BOMLine.COLUMNNAME_IsQtyPercentage);
			final String uomCode = DataTableUtil.extractStringForColumnName(dataTableRow, "UomCode");
			final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(dataTableRow, "Qty");
			final BigDecimal scrap = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT." + I_PP_Product_BOMLine.COLUMNNAME_Scrap);

			if (scrap == null)
			{
				assertThat(bomLine.getScrap()).isEqualTo(BigDecimal.ZERO);
			}
			else
			{
				assertThat(bomLine.getScrap()).isEqualTo(scrap);
			}

			final I_C_UOM expectedUOM = uomDao.getByX12DE355(X12DE355.ofCode(uomCode));

			if (isQtyPercentage)
			{
				assertThat(bomLine.getQtyBatch()).isEqualTo(qty);
			}
			else
			{
				assertThat(bomLine.getQtyBOM()).isEqualTo(qty);
			}

			assertThat(bomLine.getC_UOM_ID()).isEqualTo(expectedUOM.getC_UOM_ID());
			assertThat(bomLine.getLine()).isEqualTo(line);
			assertThat(bomLine.isQtyPercentage()).isEqualTo(isQtyPercentage);

			final String attributeSetInstanceIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(attributeSetInstanceIdentifier))
			{
				final I_M_AttributeSetInstance attributeSetInstance = InterfaceWrapperHelper.load(bomLine.getM_AttributeSetInstance_ID(), I_M_AttributeSetInstance.class);

				assertThat(attributeSetInstance).isNotNull();

				attributeSetInstanceTable.put(attributeSetInstanceIdentifier, attributeSetInstance);
			}

			final String bomLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_PP_Product_BOMLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bomLineIdentifier))
			{
				productBomLineTable.putOrReplace(bomLineIdentifier, bomLine);
			}
		}
	}

	@And("verify BOM for M_Product:")
	public void verify_BOM_for_M_Product(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> row : tableRows)
		{
			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).isNotNull();

			updateProductLLCAndMarkAsVerified(product);

			final I_PP_Product_BOM bom = productBOMDAO.getDefaultBOMByProductId(ProductId.ofRepoId(product.getM_Product_ID()))
					.orElse(null);

			assertThat(bom).isNotNull();

			for (final I_PP_Product_BOMLine tbomline : productBOMDAO.retrieveLines(bom))
			{
				final ProductId productId = ProductId.ofRepoId(tbomline.getM_Product_ID());
				final I_M_Product bomLineProduct = productBL.getById(productId);
				updateProductLLCAndMarkAsVerified(bomLineProduct);
			}
		}
	}

	private void updateProductLLCAndMarkAsVerified(final I_M_Product product)
	{
		final int lowLevelCode = productBOMBL.calculateProductLowestLevel(ProductId.ofRepoId(product.getM_Product_ID()));
		product.setLowLevel(lowLevelCode);
		product.setIsVerified(true);
		InterfaceWrapperHelper.save(product);
	}

	private void processResponse(
			@NonNull final JsonBOMCreateResponse response,
			@NonNull final StepDefDataIdentifier bomIdentifier,
			@NonNull final StepDefDataIdentifier bomVersionsIdentifier)
	{
		final ProductBOMId bomId = ProductBOMId.ofRepoId(response.getCreatedBOMProductId().getValue());
		final I_PP_Product_BOM bom = productBOMDAO.getById(bomId);
		productBomTable.put(bomIdentifier, bom);

		final I_PP_Product_BOMVersions bomVersions = productBOMVersionsDAO.getBOMVersions(ProductBOMVersionsId.ofRepoId(bom.getPP_Product_BOMVersions_ID()));
		productBOMVersionsTable.putOrReplace(bomVersionsIdentifier, bomVersions);
	}

	@And("metasfresh contains PP_Product_BOMVersions:")
	public void add_PP_Product_BOMVersions(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> row = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : row)
		{
			createPPProductBOMVersions(dataTableRow);
		}
	}

	private void createPPProductBOMVersions(final Map<String, String> tableRow)
	{
		final String bomIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOMVersions.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(bomIdentifier);

		final I_PP_Product_BOMVersions productBOMVersion = InterfaceWrapperHelper.newInstance(I_PP_Product_BOMVersions.class);

		productBOMVersion.setM_Product_ID(product.getM_Product_ID());
		productBOMVersion.setName(DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOMVersions.COLUMNNAME_Name));
		productBOMVersion.setIsActive(true);

		saveRecord(productBOMVersion);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_PP_Product_BOMVersions.Table_Name);
		productBOMVersionsTable.putOrReplace(recordIdentifier, productBOMVersion);
	}

	@And("metasfresh contains PP_Product_BOM:")
	public void add_PP_Product_BOM(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> row = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : row)
		{
			createPPProductBOM(dataTableRow);
		}
	}

	private void createPPProductBOM(final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productBom = productTable.get(productIdentifier);

		final String bomVersionsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOM.COLUMNNAME_PP_Product_BOMVersions_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_PP_Product_BOMVersions bomVersions = productBOMVersionsTable.get(bomVersionsIdentifier);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId productPriceUomId = uomDao.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final BOMUse bomUse = BOMUse.valueOf(DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOM.COLUMNNAME_BOMUse));
		final BOMType bomType = BOMType.valueOf(DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOM.COLUMNNAME_BOMType));

		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_PP_Product_BOM.COLUMNNAME_ValidFrom);

		final I_PP_Product_BOM productBOM = InterfaceWrapperHelper.newInstance(I_PP_Product_BOM.class);

		productBOM.setM_Product_ID(productBom.getM_Product_ID());
		productBOM.setPP_Product_BOMVersions_ID(bomVersions.getPP_Product_BOMVersions_ID());
		productBOM.setC_UOM_ID(productPriceUomId.getRepoId());
		productBOM.setBOMUse(bomUse.getCode());
		productBOM.setBOMType(bomType.getCode());
		productBOM.setName(DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOM.COLUMNNAME_Name));
		productBOM.setValidFrom(validFrom);
		productBOM.setIsActive(true);
		productBOM.setC_DocType_ID(DEFAULT_C_DOCTYPE_ID);
		productBOM.setDateDoc(TimeUtil.asTimestamp(Instant.now()));
		productBOM.setDocStatus(X_PP_Product_BOM.DOCSTATUS_Drafted);

		saveRecord(productBOM);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_PP_Product_BOM.Table_Name);
		productBomTable.putOrReplace(recordIdentifier, productBOM);
	}

	@And("metasfresh contains PP_Product_BOMLines:")
	public void add_PP_Product_BOMLines(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> row = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : row)
		{
			createPPProductBOMLines(dataTableRow);
		}
	}

	private void createPPProductBOMLines(final Map<String, String> tableRow)
	{
		final String bomIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_PP_Product_BOM bom = productBomTable.get(bomIdentifier);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOMLine.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productBom = productTable.get(productIdentifier);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId productPriceUomId = uomDao.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final BigDecimal qtyBOM = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_PP_Product_BOMLine.COLUMNNAME_QtyBOM);

		final I_PP_Product_BOMLine productBOMLine = InterfaceWrapperHelper.newInstance(I_PP_Product_BOMLine.class);

		productBOMLine.setPP_Product_BOM_ID(bom.getPP_Product_BOM_ID());
		productBOMLine.setM_Product_ID(productBom.getM_Product_ID());
		productBOMLine.setC_UOM_ID(productPriceUomId.getRepoId());
		productBOMLine.setQtyBOM(qtyBOM);
		productBOMLine.setComponentType(DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOMLine.COLUMNNAME_ComponentType));
		productBOMLine.setValidFrom(DataTableUtil.extractDateTimestampForColumnName(tableRow, I_PP_Product_BOMLine.COLUMNNAME_ValidFrom));
		productBOMLine.setLine(DataTableUtil.extractIntForColumnName(tableRow, I_PP_Product_BOMLine.COLUMNNAME_Line));
		productBOMLine.setIsActive(true);

		saveRecord(productBOMLine);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_PP_Product_BOMLine.Table_Name);
		productBomLineTable.putOrReplace(recordIdentifier, productBOMLine);
	}

}
