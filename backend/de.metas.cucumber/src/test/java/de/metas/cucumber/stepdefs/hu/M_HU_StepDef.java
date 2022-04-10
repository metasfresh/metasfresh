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
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.handlingunits.JsonClearanceStatus;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.common.handlingunits.JsonHU;
import de.metas.common.handlingunits.JsonHUAttributeCodeAndValues;
import de.metas.common.handlingunits.JsonHUAttributesRequest;
import de.metas.common.handlingunits.JsonHUType;
import de.metas.common.handlingunits.JsonSetClearanceStatusRequest;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.inventory.M_InventoryLine_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.inventory.internaluse.HUInternalUseInventoryCreateRequest;
import de.metas.handlingunits.inventory.internaluse.HUInternalUseInventoryCreateResponse;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.inventory.InventoryLineId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.HU_ATTR_LOT_NUMBER;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_ClearanceStatus;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_HUStatus;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_IsActive;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_M_HU_ID;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_M_Locator_ID;
import static de.metas.handlingunits.model.I_M_HU.COLUMN_M_HU_Item_Parent_ID;
import static de.metas.handlingunits.model.I_M_HU_Item_Storage.COLUMNNAME_Qty;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Item_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_Version_ID;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_Inventory.COLUMNNAME_MovementDate;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID;

public class M_HU_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final InventoryService inventoryService = SpringContextHolder.instance.getBean(InventoryService.class);

	private final M_Product_StepDefData productTable;
	private final M_HU_StepDefData huTable;
	private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;
	private final M_HU_PI_Item_StepDefData huPiItemTable;
	private final M_HU_PI_Version_StepDefData huPiVersionTable;
	private final M_InventoryLine_StepDefData inventoryLineTable;
	private final M_Locator_StepDefData locatorTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final TestContext testContext;

	public M_HU_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final M_HU_PI_Item_Product_StepDefData huPiItemProductTable,
			@NonNull final M_HU_PI_Item_StepDefData huPiItemTable,
			@NonNull final M_HU_PI_Version_StepDefData huPiVersionTable,
			@NonNull final M_InventoryLine_StepDefData inventoryLineTable,
			@NonNull final M_Locator_StepDefData locatorTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final TestContext testContext)
	{
		this.productTable = productTable;
		this.huTable = huTable;
		this.huPiItemProductTable = huPiItemProductTable;
		this.huPiItemTable = huPiItemTable;
		this.huPiVersionTable = huPiVersionTable;
		this.inventoryLineTable = inventoryLineTable;
		this.locatorTable = locatorTable;
		this.warehouseTable = warehouseTable;
		this.testContext = testContext;
	}

	@And("all the hu data is reset")
	public void reset_data()
	{
		DB.executeUpdateEx("TRUNCATE TABLE m_hu cascade", ITrx.TRXNAME_None);
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
		final String attrLotNo = DataTableUtil.extractStringOrNullForColumnName(row, "attributes." + HU_ATTR_LOT_NUMBER);

		final I_M_HU hu = huTable.get(huIdentifier);
		assertThat(hu).isNotNull();

		final JsonHUAttributeCodeAndValues attributes = new JsonHUAttributeCodeAndValues();
		attributes.putAttribute(HU_ATTR_LOT_NUMBER, attrLotNo);

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
		final String regex = ".*(:[a-zA-Z]+)/?.*";

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

	@And("^after not more than (.*)s, M_HU are found:$")
	public void is_HU_found(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		for (final Map<String, String> row : table.asMaps())
		{
			findHU(row, timeoutSec);
		}
	}

	@And("M_HU_Storage are validated")
	public void validate_HU_Storage(@NonNull final DataTable table)
	{
		for (final Map<String, String> row : table.asMaps())
		{
			validateHUStorage(row);
		}
	}

	@And("M_HU are validated:")
	public void validate_HU(@NonNull final DataTable table)
	{
		for (final Map<String, String> row : table.asMaps())
		{
			validateHU(row);
		}
	}

	@And("M_HU are disposed:")
	public void dispose_HU(@NonNull final DataTable table)
	{
		for (final Map<String, String> row : table.asMaps())
		{
			disposeHU(row);
		}
	}

	private void validateHU(@NonNull final Map<String, String> row)
	{
		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_M_HU huRecord = InterfaceWrapperHelper.load(huTable.get(huIdentifier).getM_HU_ID(), I_M_HU.class);

		final String huStatus = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_HUStatus);
		final boolean isActive = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsActive);

		assertThat(huRecord).isNotNull();
		assertThat(huRecord.getHUStatus()).isEqualTo(huStatus);
		assertThat(huRecord.isActive()).isEqualTo(isActive);
	}

	private void validateHUStorage(@NonNull final Map<String, String> row)
	{
		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_M_HU huRecord = huTable.get(huIdentifier);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final Optional<I_M_HU_Storage> huStorageRecord = getHuStorageRecord(huRecord);

		final String qty = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Qty);

		assertThat(huStorageRecord).isPresent();
		assertThat(huStorageRecord.get().getM_Product_ID()).isEqualTo(productRecord.getM_Product_ID());
		assertThat(huStorageRecord.get().getQty()).isEqualTo(qty);
	}

	private void findHU(@NonNull final Map<String, String> row, @NonNull final Integer timeoutSec) throws InterruptedException
	{
		final String huStatus = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_HUStatus);
		final boolean isActive = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsActive);

		StepDefUtil.tryAndWait(timeoutSec, 500, this::isHUFound);

		final Optional<I_M_HU> huOptional = getHuRecord();

		assertThat(huOptional).isPresent();
		assertThat(huOptional.get().getHUStatus()).isEqualTo(huStatus);
		assertThat(huOptional.get().isActive()).isEqualTo(isActive);

		huTable.putOrReplace(DataTableUtil.extractRecordIdentifier(row, I_M_HU.COLUMNNAME_M_HU_ID), huOptional.get());
	}

	private Optional<I_M_HU> getHuRecord()
	{
		return queryBL.createQueryBuilder(I_M_HU.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOptional(I_M_HU.class);
	}

	private boolean isHUFound()
	{
		return getHuRecord().isPresent();
	}

	private Optional<I_M_HU_Storage> getHuStorageRecord(@NonNull final I_M_HU huRecord)
	{
		return queryBL.createQueryBuilder(I_M_HU_Storage.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Storage.COLUMNNAME_M_HU_ID, huRecord.getM_HU_ID())
				.create()
				.firstOnlyOptional(I_M_HU_Storage.class);
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
			final String attrLotNo = DataTableUtil.extractStringOrNullForColumnName(row, "attributes." + HU_ATTR_LOT_NUMBER);
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

			final String clearanceNote = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_HU.COLUMNNAME_ClearanceNote);
			if (Check.isNotBlank(clearanceNote))
			{
				assertThat(jsonHU.getClearanceNote()).isEqualTo(clearanceNote);
			}

			final String clearanceStatusKey = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_ClearanceStatus + "." + "key");
			if (Check.isNotBlank(clearanceStatusKey))
			{
				assertThat(jsonHU.getClearanceStatus()).isNotNull();
				assertThat(jsonHU.getClearanceStatus().getKey()).isEqualTo(clearanceStatusKey);
			}

			final String clearanceStatusCaption = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_ClearanceStatus + "." + "caption");
			if (Check.isNotBlank(clearanceStatusCaption))
			{
				assertThat(jsonHU.getClearanceStatus()).isNotNull();
				assertThat(jsonHU.getClearanceStatus().getCaption()).isEqualTo(clearanceStatusCaption);
			}

			assertThat(jsonHU.getJsonHUType()).isEqualTo(JsonHUType.valueOf(jsonHUType));
			assertThat(jsonHU.getAttributes().getAttributes().get(HU_ATTR_LOT_NUMBER)).isEqualTo(attrLotNo);
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

	@And("store JsonSetClearanceStatusRequest in context")
	public void store_JsonSetClearanceStatusRequest_in_context(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String clearanceNote = DataTableUtil.extractStringForColumnName(row, I_M_HU.COLUMNNAME_ClearanceNote);
		final String clearanceStatus = DataTableUtil.extractStringForColumnName(row, I_M_HU.COLUMNNAME_ClearanceStatus);

		final JsonClearanceStatus jsonClearanceStatus = JsonClearanceStatus.valueOf(clearanceStatus);

		final JsonSetClearanceStatusRequest jsonSetClearanceStatusRequest = JsonSetClearanceStatusRequest.builder()
				.clearanceStatus(jsonClearanceStatus)
				.clearanceNote(clearanceNote)
				.build();

		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();
		testContext.setRequestPayload(mapper.writeValueAsString(jsonSetClearanceStatusRequest));
	}

	private void disposeHU(@NonNull final Map<String, String> row)
	{
		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final ZonedDateTime movementDate = DataTableUtil.extractZonedDateTimeForColumnName(row, COLUMNNAME_MovementDate);

		final I_M_HU huRecord = huTable.get(huIdentifier);

		assertThat(huRecord).isNotNull();

		final HUInternalUseInventoryCreateRequest huInternalUseInventoryCreateRequest = HUInternalUseInventoryCreateRequest.builder()
				.hus(ImmutableList.of(huRecord))
				.movementDate(movementDate)
				.completeInventory(true)
				.moveEmptiesToEmptiesWarehouse(true)
				.build();

		final HUInternalUseInventoryCreateResponse result = inventoryService.moveToGarbage(huInternalUseInventoryCreateRequest);

		final boolean somethingWasProcessed = !result.getInventories().isEmpty();
		assertThat(somethingWasProcessed).isTrue();
	}
}
