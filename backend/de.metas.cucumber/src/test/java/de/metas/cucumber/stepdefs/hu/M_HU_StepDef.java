/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.hu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.common.handlingunits.JsonHU;
import de.metas.common.handlingunits.JsonHUAttributes;
import de.metas.common.handlingunits.JsonHUAttributesRequest;
import de.metas.common.handlingunits.JsonHUType;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.inventory.InventoryLineId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.HU_ATTR_LockNotice;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_HUStatus;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_M_HU_ID;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_M_HU_PI_Item_Product_ID;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_M_HU_PI_Version_ID;
import static de.metas.handlingunits.model.I_M_HU.COLUMN_M_HU_Item_Parent_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Item_ID;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_M_Locator.COLUMNNAME_M_Locator_ID;

public class M_HU_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final StepDefData<I_M_HU> huTable;
	private final StepDefData<I_M_HU_PI_Item_Product> huPiItemProductTable;
	private final StepDefData<I_M_HU_PI_Item> huPiItemTable;
	private final StepDefData<I_M_HU_PI_Version> huPiVersionTable;
	private final StepDefData<I_M_InventoryLine> inventoryLineTable;
	private final StepDefData<I_M_Locator> locatorTable;
	private final StepDefData<I_M_Warehouse> warehouseTable;
	private final TestContext testContext;

	public M_HU_StepDef(
			@NonNull final StepDefData<I_M_HU> huTable,
			@NonNull final StepDefData<I_M_HU_PI_Item_Product> huPiItemProductTable,
			@NonNull final StepDefData<I_M_HU_PI_Item> huPiItemTable,
			@NonNull final StepDefData<I_M_HU_PI_Version> huPiVersionTable,
			@NonNull final StepDefData<I_M_InventoryLine> inventoryLineTable,
			@NonNull final StepDefData<I_M_Locator> locatorTable,
			@NonNull final StepDefData<I_M_Warehouse> warehouseTable,
			@NonNull final TestContext testContext)
	{
		this.huTable = huTable;
		this.huPiItemProductTable = huPiItemProductTable;
		this.huPiItemTable = huPiItemTable;
		this.huPiVersionTable = huPiVersionTable;
		this.inventoryLineTable = inventoryLineTable;
		this.locatorTable = locatorTable;
		this.warehouseTable = warehouseTable;
		this.testContext = testContext;
	}

	@And("validate M_HUs:")
	public void validate_M_HUs(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);

			final String parentHuIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.M_HU_Parent." + TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(parentHuIdentifier))
			{
				final I_M_HU parentHU = huTable.get(parentHuIdentifier);

				final I_M_HU_Item huItem = queryBL.createQueryBuilder(I_M_HU_Item.class)
						.addEqualsFilter(I_M_HU_Item.COLUMNNAME_M_HU_ID, parentHU.getM_HU_ID())
						.orderByDescending(I_M_HU_Item.COLUMN_M_HU_Item_ID)
						.create()
						.firstNotNull(I_M_HU_Item.class);

				final I_M_HU currentHU = queryBL.createQueryBuilder(I_M_HU.class)
						.addEqualsFilter(COLUMN_M_HU_Item_Parent_ID, huItem.getM_HU_Item_ID())
						.orderByDescending(COLUMNNAME_M_HU_ID)
						.create()
						.firstNotNull(I_M_HU.class);

				huTable.putOrReplace(huIdentifier, currentHU);
			}

			final I_M_HU hu = huTable.get(huIdentifier);
			assertThat(hu).isNotNull();

			final String huPiVersionIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_Version_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU_PI_Version piVersion = huPiVersionTable.get(huPiVersionIdentifier);
			assertThat(piVersion).isNotNull();

			final String locatorIdentifier = DataTableUtil.extractStringForColumnName(row, "OPT." + COLUMNNAME_M_Locator_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(locatorIdentifier))
			{
				final I_M_Locator locator = locatorTable.get(locatorIdentifier);
				assertThat(locator).isNotNull();
				assertThat(hu.getM_Locator_ID()).isEqualTo(locator.getM_Locator_ID());
			}

			final String huPiItemProductIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(huPiItemProductIdentifier))
			{
				final I_M_HU_PI_Item_Product huPiItemProduct = huPiItemProductTable.get(huPiItemProductIdentifier);
				assertThat(huPiItemProduct).isNotNull();
				assertThat(hu.getM_HU_PI_Item_Product_ID()).isEqualTo(huPiItemProduct.getM_HU_PI_Item_Product_ID());
			}

			final String huStatus = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_HUStatus);

			assertThat(hu.getM_HU_PI_Version_ID()).isEqualTo(piVersion.getM_HU_PI_Version_ID());
			assertThat(hu.getHUStatus()).isEqualTo(huStatus);
		}
	}

	@And("^after not more than (.*)s, there are added M_HUs for inventory$")
	public void find_HUs(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String inventoryLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_InventoryLine.COLUMNNAME_M_InventoryLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String huIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_M_InventoryLine inventoryLine = inventoryLineTable.get(inventoryLineIdentifier);
			final InventoryLineId inventoryLineWithHUId = InventoryLineId.ofRepoId(inventoryLine.getM_InventoryLine_ID());

			final de.metas.handlingunits.model.I_M_InventoryLine inventoryLineWithHU = load(inventoryLineWithHUId, de.metas.handlingunits.model.I_M_InventoryLine.class);

			assertThat(inventoryLineWithHU).isNotNull();

			final HuId huId = HuId.ofRepoId(inventoryLineWithHU.getM_HU_ID());

			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadHU(LoadHURequest.builder()
																		 .huId(huId)
																		 .huIdentifier(huIdentifier)
																		 .build()));
		}
	}

	@And("^after not more than (.*)s, M_HUs should have$")
	public void wait_M_HUs_status(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String huIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU hu = huTable.get(huIdentifier);

			final LoadHURequest.LoadHURequestBuilder requestBuilder = LoadHURequest.builder()
					.huId(HuId.ofRepoId(hu.getM_HU_ID()))
					.huIdentifier(huIdentifier);

			final String huStatus = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_HUStatus);
			if (EmptyUtil.isNotBlank(huStatus))
			{
				requestBuilder.huStatus(huStatus);
			}

			final String huPiItemProductIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(huPiItemProductIdentifier))
			{
				final I_M_HU_PI_Item_Product huPiItemProduct = huPiItemProductTable.get(huPiItemProductIdentifier);
				requestBuilder.piItemProductId(HuId.ofRepoId(huPiItemProduct.getM_HU_PI_Item_Product_ID()));
			}

			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadHU(requestBuilder.build()));
		}
	}

	@And("transform CU to new TUs")
	public void transformCUtoNewTUs(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String sourceCuIdentifier = DataTableUtil.extractStringForColumnName(row, "sourceCU." + TABLECOLUMN_IDENTIFIER);
			final BigDecimal cuQty = DataTableUtil.extractBigDecimalForColumnName(row, "cuQty");
			final String huPIItemProductIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_M_HU cuHU = huTable.get(sourceCuIdentifier);
			assertThat(cuHU).isNotNull();

			final I_C_UOM uom = uomDAO.getById(StepDefConstants.PCE_UOM_ID);
			final Quantity cuQuantity = Quantity.of(cuQty, uom);

			final I_M_HU_PI_Item_Product huPiItemProduct = huPiItemProductTable.get(huPIItemProductIdentifier);
			assertThat(huPiItemProduct).isNotNull();

			final List<I_M_HU> resultedNewTUs = HUTransformService.newInstance().cuToNewTUs(cuHU, cuQuantity, huPiItemProduct, false);

			final String resultedNewTUsIdentifiers = DataTableUtil.extractStringForColumnName(row, "resultedNewTUs." + TABLECOLUMN_IDENTIFIER);
			final List<String> tuIdentifiers = StepDefUtil.splitIdentifiers(resultedNewTUsIdentifiers);

			final String resultedNewCUsIdentifiers = DataTableUtil.extractStringForColumnName(row, "resultedNewCUs." + TABLECOLUMN_IDENTIFIER);
			final List<String> cuIdentifiers = StepDefUtil.splitIdentifiers(resultedNewCUsIdentifiers);

			for (int index = 0; index < resultedNewTUs.size(); index++)
			{
				huTable.putOrReplace(tuIdentifiers.get(index), resultedNewTUs.get(index));

				final I_M_HU currentTU = resultedNewTUs.get(index);

				final I_M_HU_Item tuItem = queryBL.createQueryBuilder(I_M_HU_Item.class)
						.addEqualsFilter(I_M_HU_Item.COLUMNNAME_M_HU_ID, currentTU.getM_HU_ID())
						.orderByDescending(COLUMNNAME_M_HU_ID)
						.create()
						.firstNotNull(I_M_HU_Item.class);

				final I_M_HU includedCU = queryBL.createQueryBuilder(I_M_HU.class)
						.addEqualsFilter(COLUMN_M_HU_Item_Parent_ID, tuItem.getM_HU_Item_ID())
						.orderByDescending(COLUMNNAME_M_HU_ID)
						.create()
						.firstNotNull(I_M_HU.class);

				huTable.putOrReplace(cuIdentifiers.get(index), includedCU);
			}
		}
	}

	@And("transform TU to new LUs")
	public void transformTUtoNewLUs(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String sourceTUIdentifier = DataTableUtil.extractStringForColumnName(row, "sourceTU." + TABLECOLUMN_IDENTIFIER);
			final BigDecimal tuQty = DataTableUtil.extractBigDecimalForColumnName(row, "tuQty");
			final String huPIItemIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_Item_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_M_HU tuHU = huTable.get(sourceTUIdentifier);
			assertThat(tuHU).isNotNull();

			final I_M_HU_PI_Item huPiItem = huPiItemTable.get(huPIItemIdentifier);
			assertThat(huPiItem).isNotNull();

			final List<I_M_HU> resultedNewLUs = HUTransformService.newInstance().tuToNewLUs(tuHU, tuQty, huPiItem, false);

			final String resultedNewLUsIdentifiers = DataTableUtil.extractStringForColumnName(row, "resultedNewLUs." + TABLECOLUMN_IDENTIFIER);
			final List<String> identifiers = StepDefUtil.splitIdentifiers(resultedNewLUsIdentifiers);

			for (int index = 0; index < resultedNewLUs.size(); index++)
			{
				huTable.putOrReplace(identifiers.get(index), resultedNewLUs.get(index));
			}
		}
	}

	@And("store JsonHUAttributesRequest in context")
	public void store_JsonHUAttributesRequest_in_context(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String attrLockNotice = DataTableUtil.extractStringOrNullForColumnName(row, "attributes." + HU_ATTR_LockNotice);

		final I_M_HU hu = huTable.get(huIdentifier);
		assertThat(hu).isNotNull();

		final JsonHUAttributes attributes = new JsonHUAttributes();
		attributes.putAttribute(HU_ATTR_LockNotice, attrLockNotice);

		final JsonHUAttributesRequest jsonHUAttributesRequest = JsonHUAttributesRequest.builder()
				.huId(String.valueOf(hu.getM_HU_ID()))
				.attributes(attributes)
				.build();

		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();
		testContext.setRequestPayload(mapper.writeValueAsString(jsonHUAttributesRequest));
	}

	@And("^store HU endpointPath (.*) in context$")
	public void store_hu_endpointPath_in_context(@NonNull String endpointPath)
	{
		final String regex = ".*(:[a-zA-Z]+).*";

		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(endpointPath);

		while (matcher.find())
		{
			final String huIdentifierGroup = matcher.group(1);
			final String huIdentifier = huIdentifierGroup.replace(":", "");

			final I_M_HU hu = huTable.get(huIdentifier);
			assertThat(hu).isNotNull();

			endpointPath = endpointPath.replace(huIdentifierGroup, String.valueOf(hu.getM_HU_ID()));

			testContext.setEndpointPath(endpointPath);
		}
	}

	@Then("validate \"retrieve hu\" response:")
	public void validate_retrieve_HU_response(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonGetSingleHUResponse getHUResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonGetSingleHUResponse.class);
		final JsonHU topLevelHU = getHUResponse.getResult();
		assertThat(topLevelHU).isNotNull();

		final List<Map<String, String>> rows = dataTable.asMaps();

		final Map<String, Map<String, String>> identifierToRow = rows.stream()
				.collect(Collectors.toMap(row -> DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER),
										  Function.identity()));

		final Map<String, String> topRow = rows.get(0);
		final String huIdentifier = DataTableUtil.extractStringForColumnName(topRow, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);

		validateHU(ImmutableList.of(topLevelHU), ImmutableList.of(huIdentifier), identifierToRow);
	}

	private void validateHU(
			@NonNull final List<JsonHU> jsonHUs,
			@NonNull final List<String> husIdentifiers,
			@NonNull final Map<String, Map<String, String>> huIdentifierToRow)
	{
		assertThat(jsonHUs.size()).isEqualTo(husIdentifiers.size());

		for (final String huIdentifier : husIdentifiers)
		{
			final Map<String, String> row = huIdentifierToRow.get(huIdentifier);
			final I_M_HU hu = huTable.get(huIdentifier);

			final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

			final JsonHU jsonHU = jsonHUs.stream()
					.filter(jsonHu -> HuId.ofHUValue(jsonHu.getId()).equals(huId))
					.findFirst().orElseThrow(() -> new AdempiereException("No HU found for HuId:" + huId));

			final String jsonHUType = DataTableUtil.extractStringForColumnName(row, "jsonHUType");
			final String attrLockNotice = DataTableUtil.extractStringOrNullForColumnName(row, "attributes." + HU_ATTR_LockNotice);
			final String productName = DataTableUtil.extractStringForColumnName(row, "products.productName");
			final String productValue = DataTableUtil.extractStringForColumnName(row, "products.productValue");
			final String productQty = DataTableUtil.extractStringForColumnName(row, "products.qty");
			final String uom = DataTableUtil.extractStringOrNullForColumnName(row, "products.uom");
			final int numberOfAggregatedHUs = DataTableUtil.extractIntForColumnName(row, "numberOfAggregatedHUs");
			final String huStatus = DataTableUtil.extractStringForColumnName(row, "huStatus");

			final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(row, "warehouseValue." + TABLECOLUMN_IDENTIFIER);
			final I_M_Warehouse warehouse = warehouseTable.get(warehouseIdentifier);
			assertThat(warehouse).isNotNull();

			final String locatorIdentifier = DataTableUtil.extractStringForColumnName(row, "locatorValue." + TABLECOLUMN_IDENTIFIER);
			final I_M_Locator locator = locatorTable.get(locatorIdentifier);
			assertThat(locator).isNotNull();

			assertThat(jsonHU.getJsonHUType()).isEqualTo(JsonHUType.valueOf(jsonHUType));
			assertThat(jsonHU.getAttributes().getAttributes().get(HU_ATTR_LockNotice)).isEqualTo(attrLockNotice);
			assertThat(jsonHU.getProducts().get(0).getProductName()).isEqualTo(productName);
			assertThat(jsonHU.getProducts().get(0).getProductValue()).isEqualTo(productValue);
			assertThat(jsonHU.getProducts().get(0).getQty()).isEqualTo(productQty);
			assertThat(jsonHU.getProducts().get(0).getUom()).isEqualTo(uom);
			assertThat(jsonHU.getNumberOfAggregatedHUs()).isEqualTo(numberOfAggregatedHUs);
			assertThat(jsonHU.getHuStatus()).isEqualTo(huStatus);
			assertThat(jsonHU.getWarehouseValue()).isEqualTo(warehouse.getValue());
			assertThat(jsonHU.getLocatorValue()).isEqualTo(locator.getValue());

			final String includedHus = DataTableUtil.extractStringOrNullForColumnName(row, "includedHUs");
			if (EmptyUtil.isNotBlank(includedHus))
			{
				final List<String> includedHusIdentifiers = StepDefUtil.splitIdentifiers(includedHus);
				assertThat(jsonHU.getIncludedHUs()).isNotNull();
				assertThat(jsonHU.getIncludedHUs().size()).isEqualTo(includedHusIdentifiers.size());

				validateHU(jsonHU.getIncludedHUs(), includedHusIdentifiers, huIdentifierToRow);
			}
		}
	}

	@NonNull
	private Boolean loadHU(@NonNull final LoadHURequest request)
	{
		final IQueryBuilder<I_M_HU> queryBuilder = queryBL.createQueryBuilder(I_M_HU.class)
				.addEqualsFilter(COLUMNNAME_M_HU_ID, request.getHuId());

		if (EmptyUtil.isNotBlank(request.getHuStatus()))
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_HUStatus, request.getHuStatus());
		}

		if (request.getPiItemProductId() != null)
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_M_HU_PI_Item_Product_ID, request.getPiItemProductId());
		}

		final Optional<I_M_HU> hu = queryBuilder.create().firstOnlyOptional(I_M_HU.class);

		if (!hu.isPresent())
		{
			return false;
		}

		huTable.putOrReplace(request.getHuIdentifier(), hu.get());

		return true;
	}
}
