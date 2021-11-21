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
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_BOMVersions;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class CreateBOM_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);

	private final StepDefData<I_PP_Product_BOM> bomTable;
	private final StepDefData<I_PP_Product_BOMVersions> productBOMVersionsTable;
	private final StepDefData<I_M_Product> productTable;
	private final TestContext testContext;

	final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	public CreateBOM_StepDef(
			@NonNull final StepDefData<I_PP_Product_BOM> bomTable,
			@NonNull final StepDefData<I_PP_Product_BOMVersions> productBOMVersionsTable,
			@NonNull final StepDefData<I_M_Product> productTable,
			@NonNull final TestContext testContext)
	{
		this.bomTable = bomTable;
		this.productBOMVersionsTable = productBOMVersionsTable;
		this.productTable = productTable;
		this.testContext = testContext;
	}

	@Then("process metasfresh response:")
	public void processMetasfreshResponse(@NonNull final DataTable table) throws JsonProcessingException
	{
		final JsonBOMCreateResponse response = mapper.readValue(testContext.getApiResponse().getContent(), JsonBOMCreateResponse.class);
		assertThat(response).isNotNull();

		final Map<String, String> row = table.asMaps().get(0);
		final String bomIdentifier = DataTableUtil.extractStringForColumnName(row, "PP_Product_BOM_ID.Identifier");
		final String bomVersionsIdentifier = DataTableUtil.extractStringForColumnName(row, "PP_Product_BOMVersions_ID.Identifier");

		processResponse(response, bomIdentifier, bomVersionsIdentifier);
	}

	@And("verify that bomVersions was created for product")
	public void verifyThatBomVersionsWasCreatedForProduct(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> bomVersionsTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : bomVersionsTableList)
		{
			final String bomVersionsIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "PP_Product_BOMVersions_ID.Identifier");
			final String productIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "M_Product_ID.Identifier");
			final String name = DataTableUtil.extractStringForColumnName(dataTableRow, "Name");

			final I_PP_Product_BOMVersions bomVersions = productBOMVersionsTable.get(bomVersionsIdentifier);
			final I_M_Product product = productTable.get(productIdentifier);

			assertThat(bomVersions.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
			assertThat(bomVersions.getName()).isEqualTo(name);
		}
	}

	@Then("verify that bom was created for product")
	public void verifyThatBomWasCreatedForProduct(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> bomTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : bomTableList)
		{
			final String bomIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "PP_Product_BOM_ID.Identifier");
			final String productIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "M_Product_ID.Identifier");
			final String bomVersionsIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "PP_Product_BOMVersions_ID.Identifier");
			final String productValue = DataTableUtil.extractStringForColumnName(dataTableRow, "ProductValue");
			final String uomCode = DataTableUtil.extractStringForColumnName(dataTableRow, "UomCode");
			final Instant validFrom = DataTableUtil.extractInstantForColumnName(dataTableRow, "ValidFrom");

			final I_C_UOM bomProductExpectedUOM = uomDao.getByX12DE355(X12DE355.ofCode(uomCode));
			final I_PP_Product_BOM bom = bomTable.get(bomIdentifier);
			final I_M_Product product = productTable.get(productIdentifier);
			final I_PP_Product_BOMVersions bomVersions = productBOMVersionsTable.get(bomVersionsIdentifier);

			assertThat(bom.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
			assertThat(bom.getPP_Product_BOMVersions_ID()).isEqualTo(bomVersions.getPP_Product_BOMVersions_ID());
			assertThat(bom.getValue()).isEqualTo(productValue);
			assertThat(bom.getC_UOM_ID()).isEqualTo(bomProductExpectedUOM.getC_UOM_ID());
			assertThat(bom.getValidFrom()).isEqualTo(TimeUtil.asTimestamp(validFrom));
		}
	}

	@And("verify that bomLine was created for bom")
	public void verifyThatBomLineWasCreatedForBom(@NonNull final DataTable dataTable)
	{

		final List<Map<String, String>> bomLinesTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : bomLinesTableList)
		{
			final String bomIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "PP_Product_BOM_ID.Identifier");
			final String productIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "M_Product_ID.Identifier");

			final int line = DataTableUtil.extractIntForColumnName(dataTableRow, "Line");
			final Boolean isQtyPercentage = DataTableUtil.extractBooleanForColumnName(dataTableRow, "IsQtyPercentage");
			final String uomCode = DataTableUtil.extractStringForColumnName(dataTableRow, "UomCode");
			final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(dataTableRow, "Qty");
			final BigDecimal scrap = DataTableUtil.extractBigDecimalForColumnName(dataTableRow, "Scrap");

			final I_PP_Product_BOM bom = bomTable.get(bomIdentifier);

			final List<I_PP_Product_BOMLine> bomLines = queryBL.createQueryBuilder(I_PP_Product_BOMLine.class)
					.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID, bom.getPP_Product_BOM_ID())
					.create()
					.list();

			assertThat(bomLines.size()).isEqualTo(1);

			final I_PP_Product_BOMLine bomLine = bomLines.get(0);
			final I_M_Product product = productTable.get(productIdentifier);
			final I_C_UOM expectedUOM = uomDao.getByX12DE355(X12DE355.ofCode(uomCode));

			assertThat(bomLine.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
			assertThat(bomLine.getQtyBOM()).isEqualTo(qty);
			assertThat(bomLine.getC_UOM_ID()).isEqualTo(expectedUOM.getC_UOM_ID());
			assertThat(bomLine.getScrap()).isEqualTo(scrap);
			assertThat(bomLine.getLine()).isEqualTo(line);
			assertThat(bomLine.isQtyPercentage()).isEqualTo(isQtyPercentage);
		}
	}

	private void processResponse(
			@NonNull final JsonBOMCreateResponse response,
			@NonNull final String bomIdentifier,
			@NonNull final String bomVersionsIdentifier)
	{
		final ProductBOMId bomId = ProductBOMId.ofRepoId(response.getCreatedBOMProductId().getValue());

		final I_PP_Product_BOM bom = InterfaceWrapperHelper.load(bomId, I_PP_Product_BOM.class);

		bomTable.put(bomIdentifier, bom);

		final I_PP_Product_BOMVersions bomVersions = InterfaceWrapperHelper.load(bom.getPP_Product_BOMVersions_ID(), I_PP_Product_BOMVersions.class);

		productBOMVersionsTable.putOrReplace(bomVersionsIdentifier, bomVersions);
	}
}
