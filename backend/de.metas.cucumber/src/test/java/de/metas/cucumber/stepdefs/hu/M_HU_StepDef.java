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
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.handlingunits.JsonClearanceStatus;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.common.handlingunits.JsonHU;
import de.metas.common.handlingunits.JsonHUAttributeCodeAndValues;
import de.metas.common.handlingunits.JsonHUAttributesRequest;
import de.metas.common.handlingunits.JsonHUType;
import de.metas.common.handlingunits.JsonSetClearanceStatusRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.inventory.M_InventoryLine_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.LULoader;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.inventory.internaluse.HUInternalUseInventoryCreateRequest;
import de.metas.handlingunits.inventory.internaluse.HUInternalUseInventoryCreateResponse;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_QRCode;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.rest_api.HandlingUnitsService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
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
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.HU_ATTR_LOT_NUMBER;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_ClearanceNote;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_ClearanceStatus;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_HUStatus;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_IsActive;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_M_HU_ID;
import static de.metas.handlingunits.model.I_M_HU.COLUMN_M_HU_Item_Parent_ID;
import static de.metas.handlingunits.model.I_M_HU_Item_Storage.COLUMNNAME_Qty;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Item_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_Version_ID;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_Inventory.COLUMNNAME_MovementDate;
import static org.compiere.model.I_M_Locator.COLUMNNAME_M_Locator_ID;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID;

@RequiredArgsConstructor
public class M_HU_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final InventoryService inventoryService = SpringContextHolder.instance.getBean(InventoryService.class);
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

	private final M_Product_StepDefData productTable;
	private final M_HU_StepDefData huTable;
	private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;
	private final M_HU_PI_Item_StepDefData huPiItemTable;
	private final M_HU_PI_Version_StepDefData huPiVersionTable;
	private final M_HU_PI_StepDefData huPiTable;
	private final M_InventoryLine_StepDefData inventoryLineTable;
	private final M_Locator_StepDefData locatorTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final M_HU_QRCode_StepDefData qrCodesTable;
	private final TestContext restTestContext;

	private final HandlingUnitsService handlingUnitsService = SpringContextHolder.instance.getBean(HandlingUnitsService.class);

	@And("all the hu data is reset")
	public void reset_data()
	{
		DB.executeUpdateEx("TRUNCATE TABLE m_hu cascade", ITrx.TRXNAME_None);
	}

	@And("validate M_HUs:")
	public void validate_M_HUs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final StepDefDataIdentifier huIdentifier = row.getAsIdentifier(COLUMNNAME_M_HU_ID);

			row.getAsOptionalIdentifier("M_HU_Parent")
					.ifPresent(parentHuIdentifier -> {
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
					});

			final I_M_HU hu = huTable.get(huIdentifier);
			assertThat(hu).isNotNull();

			row.getAsOptionalIdentifier(COLUMNNAME_M_HU_PI_Version_ID)
					.map(huPiVersionTable::get)
					.ifPresent(piVersion -> assertThat(hu.getM_HU_PI_Version_ID()).isEqualTo(piVersion.getM_HU_PI_Version_ID()));

			row.getAsOptionalIdentifier(COLUMNNAME_M_Locator_ID)
					.ifPresent(locatorIdentifier -> {
						final I_M_Locator locator = locatorTable.get(locatorIdentifier);
						assertThat(locator).isNotNull();
						assertThat(hu.getM_Locator_ID()).isEqualTo(locator.getM_Locator_ID());
					});

			row.getAsOptionalIdentifier(COLUMNNAME_M_HU_PI_Item_Product_ID)
					.ifPresent(huPiItemProductIdentifier -> {
						final I_M_HU_PI_Item_Product huPiItemProduct = huPiItemProductTable.get(huPiItemProductIdentifier);
						assertThat(huPiItemProduct).isNotNull();
						assertThat(hu.getM_HU_PI_Item_Product_ID()).isEqualTo(huPiItemProduct.getM_HU_PI_Item_Product_ID());
					});

			final String huStatus = row.getAsString(COLUMNNAME_HUStatus);

			assertThat(hu.getHUStatus()).isEqualTo(huStatus);

			final String clearanceStatus = row.getAsOptionalString(COLUMNNAME_ClearanceStatus).orElse(null);
			if (Check.isNotBlank(clearanceStatus))
			{
				assertThat(hu.getClearanceStatus()).isEqualTo(clearanceStatus);
			}

			final String clearanceNote = row.getAsOptionalString(COLUMNNAME_ClearanceNote).orElse(null);
			if (Check.isNotBlank(clearanceNote))
			{
				assertThat(hu.getClearanceNote()).isEqualTo(clearanceNote);
			}
		});
	}

	@And("^after not more than (.*)s, there are added M_HUs for inventory$")
	public void find_HUs(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final InventoryLineId inventoryLineId = inventoryLineTable.getId(row.getAsIdentifier(I_M_InventoryLine.COLUMNNAME_M_InventoryLine_ID));
			final StepDefDataIdentifier huIdentifier = row.getAsIdentifier(COLUMNNAME_M_HU_ID);

			final I_M_InventoryLine inventoryLine = inventoryDAO.getLineById(inventoryLineId, I_M_InventoryLine.class);
			assertThat(inventoryLine).isNotNull();
			final HuId huId = HuId.ofRepoId(inventoryLine.getM_HU_ID());

			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadHU(LoadHURequest.builder()
					.huId(huId)
					.huIdentifier(huIdentifier)
					.build()));

			restTestContext.setIdVariableFromRow(row, huId);
		});
	}

	@And("^after not more than (.*)s, M_HUs should have$")
	public void wait_M_HUs_status(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final StepDefDataIdentifier huIdentifier = row.getAsIdentifier(COLUMNNAME_M_HU_ID);
			final HuId huId = huTable.getId(huIdentifier);

			final LoadHURequest.LoadHURequestBuilder requestBuilder = LoadHURequest.builder()
					.huId(huId)
					.huIdentifier(huIdentifier);

			row.getAsOptionalString(COLUMNNAME_HUStatus).ifPresent(requestBuilder::huStatus);
			row.getAsOptionalIdentifier(COLUMNNAME_M_HU_PI_Item_Product_ID)
					.map(huPiItemProductTable::getId)
					.ifPresent(requestBuilder::piItemProductId);

			final LoadHURequest request = requestBuilder.build();

			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadHU(request));
		});
	}

	@And("transform CU to new TUs")
	public void transformCUtoNewTUs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final StepDefDataIdentifier sourceCuIdentifier = row.getAsIdentifier("sourceCU");
			final BigDecimal cuQty = row.getAsBigDecimal("cuQty");
			final StepDefDataIdentifier huPIItemProductIdentifier = row.getAsIdentifier(COLUMNNAME_M_HU_PI_Item_Product_ID);

			final I_M_HU cuHU = huTable.get(sourceCuIdentifier);
			assertThat(cuHU).isNotNull();

			final I_C_UOM uom = uomDAO.getById(StepDefConstants.PCE_UOM_ID);
			final Quantity cuQuantity = Quantity.of(cuQty, uom);

			final I_M_HU_PI_Item_Product huPiItemProduct = huPiItemProductTable.get(huPIItemProductIdentifier);
			assertThat(huPiItemProduct).isNotNull();

			final List<I_M_HU> resultedNewTUs = HUTransformService.newInstance().cuToNewTUs(cuHU, cuQuantity, huPiItemProduct, false);

			final List<StepDefDataIdentifier> tuIdentifiers = row.getAsOptionalIdentifier("resultedNewTUs")
					.map(StepDefDataIdentifier::toCommaSeparatedList)
					.orElse(null);
			if (tuIdentifiers != null)
			{
				assertThat(tuIdentifiers).hasSameSizeAs(resultedNewTUs);
			}

			final List<StepDefDataIdentifier> cuIdentifiers = row.getAsOptionalIdentifier("resultedNewCUs")
					.map(StepDefDataIdentifier::toCommaSeparatedList)
					.orElse(null);
			if (cuIdentifiers != null)
			{
				assertThat(cuIdentifiers).hasSameSizeAs(resultedNewTUs);
			}

			for (int index = 0; index < resultedNewTUs.size(); index++)
			{
				final I_M_HU currentTU = resultedNewTUs.get(index);

				if (tuIdentifiers != null)
				{
					huTable.putOrReplace(tuIdentifiers.get(index), currentTU);
				}

				if (cuIdentifiers != null)
				{
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
		});
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

	@And("aggregate TUs to new LU")
	public void aggregateTUsToNewLU(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			huTrxBL.process(huContext -> {
				final LULoader luLoader = new LULoader(huContext);

				@NonNull final List<StepDefDataIdentifier> sourceTUIdentifiers = row.getAsIdentifier("sourceTUs").toCommaSeparatedList();
				for (StepDefDataIdentifier sourceTUIdentifier : sourceTUIdentifiers)
				{
					final I_M_HU sourceTU = huTable.get(sourceTUIdentifier);
					luLoader.addTU(sourceTU);
				}

				luLoader.close();

				row.getAsOptionalIdentifier("newLUs")
						.map(StepDefDataIdentifier::toCommaSeparatedList)
						.ifPresent(newLUIdentifiers -> {
							final List<I_M_HU> newLUs = luLoader.getLU_HUs();
							assertThat(newLUs).hasSameSizeAs(newLUIdentifiers);
							for (int index = 0; index < newLUs.size(); index++)
							{
								huTable.put(newLUIdentifiers.get(index), newLUs.get(index));
							}
						});
			});
		});
	}

	@And("transform CU to new LU")
	public void transformCUtoNewLUs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			huTrxBL.process(huContext -> {
				transformCUtoNewLU(row, huContext);
			});
		});
	}

	private void transformCUtoNewLU(final DataTableRow row, final IHUContext huContext)
	{
		final I_M_HU sourceCU = row.getAsIdentifier("sourceCU").lookupIn(huTable);

		final IHUProductStorage sourceCUProductStorage = handlingUnitsBL.getStorageFactory().getStorage(sourceCU).getSingleHUProductStorage();
		final ProductId productId = sourceCUProductStorage.getProductId();
		final I_C_UOM uom = sourceCUProductStorage.getC_UOM();

		final LUTUProducerDestination producer = new LUTUProducerDestination();

		producer.setHUStatus(sourceCU.getHUStatus());
		producer.setLocatorId(IHandlingUnitsBL.extractLocatorId(sourceCU));
		final BPartnerLocationId bpartnerLocationId = IHandlingUnitsBL.extractBPartnerLocationIdOrNull(sourceCU);
		if (bpartnerLocationId != null)
		{
			producer.setBPartnerAndLocationId(bpartnerLocationId);
		}

		final I_M_HU_PI tuPI = row.getAsIdentifier("TU_PI_ID").lookupIn(huPiTable);
		final BigDecimal qtyCUsPerTU = row.getAsBigDecimal("QtyCUsPerTU");
		final QtyTU qtyTUs = QtyTU.ofInt(row.getAsInt("QtyTUsPerLU"));
		producer.setTUPI(tuPI);
		producer.addCUPerTU(productId, qtyCUsPerTU, uom);
		producer.setMaxTUsPerLU(qtyTUs.toInt());

		final BPartnerId bpartnerId = bpartnerLocationId != null ? bpartnerLocationId.getBpartnerId() : null;
		final I_M_HU_PI_Item luPIItem = handlingUnitsDAO.retrieveDefaultParentPIItem(tuPI, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, bpartnerId);
		assertThat(luPIItem).as(() -> "LU PI Item for tuPI=" + tuPI + ", bpartnerId=" + bpartnerId).isNotNull();
		producer.setLUItemPI(luPIItem);
		producer.setLUPI(luPIItem.getM_HU_PI_Version().getM_HU_PI());
		producer.setCreateTUsForRemainingQty(false);
		producer.setMaxLUs(1);
		HULoader.builder()
				.source(HUListAllocationSourceDestination.of(sourceCU))
				.destination(producer)
				.load(AllocationUtils.builder()
						.setHUContext(huContext)
						.setProduct(productId)
						.setQuantity(Quantity.of(qtyCUsPerTU.multiply(qtyTUs.toBigDecimal()), uom))
						.setDateAsToday()
						.setForceQtyAllocation(true)
						.create());

		final I_M_HU newLU = producer.getSingleCreatedHU().orElseThrow(() -> new AdempiereException("No LU was created"));
		row.getAsIdentifier("newLU").put(huTable, newLU);
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
		restTestContext.setRequestPayload(mapper.writeValueAsString(jsonHUAttributesRequest));
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

			restTestContext.setEndpointPath(endpointPath);
		}
	}

	@Then("validate \"retrieve hu\" response:")
	public void validate_retrieve_HU_response(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final JsonGetSingleHUResponse getHUResponse = restTestContext.getApiResponseBodyAs(JsonGetSingleHUResponse.class);
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

	@And("M_HU_Storage are validated")
	public void validate_HU_Storage(@NonNull final DataTable table)
	{
		for (final Map<String, String> row : table.asMaps())
		{
			validateHUStorage(row);
		}
	}

	@And("M_HU are validated:")
	public void validateHUs(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(this::validateHU);
	}

	@Given("M_HU are disposed:")
	public void dispose_HU(@NonNull final DataTable table)
	{
		for (final Map<String, String> row : table.asMaps())
		{
			disposeHU(row);
		}
	}

	@And("store JsonSetClearanceStatusRequest in context")
	public void store_JsonSetClearanceStatusRequest_in_context(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String clearanceStatus = DataTableUtil.extractStringForColumnName(row, I_M_HU.COLUMNNAME_ClearanceStatus);
		final String clearanceNote = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_HU.COLUMNNAME_ClearanceNote);

		final JsonClearanceStatus jsonClearanceStatus = JsonClearanceStatus.valueOf(clearanceStatus);

		final JsonSetClearanceStatusRequest jsonSetClearanceStatusRequest = JsonSetClearanceStatusRequest.builder()
				.huIdentifier(getHUIdentifier(row))
				.clearanceStatus(jsonClearanceStatus)
				.clearanceNote(clearanceNote)
				.build();

		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();
		restTestContext.setRequestPayload(mapper.writeValueAsString(jsonSetClearanceStatusRequest));
	}

	@And("load M_HU by QR code:")
	public void load_hu_by_qr_code(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String qrCodeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_QRCode.COLUMNNAME_M_HU_QRCode_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_M_HU_QRCode qrCode = qrCodesTable.get(qrCodeIdentifier);
			InterfaceWrapperHelper.refresh(qrCode);

			final I_M_HU hu = load(qrCode.getM_HU_ID(), I_M_HU.class);

			assertThat(hu).isNotNull();

			huTable.putOrReplace(huIdentifier, hu);
			qrCodesTable.putOrReplace(qrCodeIdentifier, qrCode);
		}
	}

	@And("update HU clearance status")
	public void update_clearance_status(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU hu = huTable.get(huIdentifier);

			final String clearanceStatus = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_ClearanceStatus);
			final String clearanceNote = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_ClearanceNote);

			final JsonSetClearanceStatusRequest.JsonHUIdentifier identifier = JsonSetClearanceStatusRequest.JsonHUIdentifier
					.ofJsonMetasfreshId(JsonMetasfreshId.of(hu.getM_HU_ID()));

			final JsonSetClearanceStatusRequest request = JsonSetClearanceStatusRequest.builder()
					.huIdentifier(identifier)
					.clearanceNote(clearanceNote)
					.clearanceStatus(JsonClearanceStatus.valueOf(clearanceStatus))
					.build();

			handlingUnitsService.setClearanceStatus(request);

			huTable.putOrReplace(huIdentifier, hu);
		}
	}

	@And("destroy existing M_HUs")
	public void destroy_existing_hus()
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IHUContext huContext = huContextFactory.createMutableHUContextForProcessing(PlainContextAware.newOutOfTrx());

		final List<I_M_HU> availableHUs = queryBL.createQueryBuilder(I_M_HU.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_HU.class);

		handlingUnitsBL.markDestroyed(huContext, availableHUs);
	}

	@And("^after not more than (.*)s, load newly created M_HU record based on SourceHU$")
	public void load_newly_created_M_HU(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String vhuSourceIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_Trace.COLUMNNAME_VHU_Source_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU vhuSourceHU = huTable.get(vhuSourceIdentifier);
			assertThat(vhuSourceHU).isNotNull();

			final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(row, I_M_HU_Trace.COLUMNNAME_Qty);
			final String huTraceType = DataTableUtil.extractStringForColumnName(row, I_M_HU_Trace.COLUMNNAME_HUTraceType);

			final Supplier<Optional<I_M_HU>> huSupplier = () -> queryBL.createQueryBuilder(I_M_HU_Trace.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_HU_Trace.COLUMNNAME_VHU_Source_ID, vhuSourceHU.getM_HU_ID())
					.addEqualsFilter(I_M_HU_Trace.COLUMNNAME_Qty, qty)
					.addEqualsFilter(I_M_HU_Trace.COLUMN_HUTraceType, huTraceType)
					.orderByDescending(I_M_HU_Trace.COLUMNNAME_Created)
					.create()
					.stream()
					.map(I_M_HU_Trace::getM_HU_ID)
					.findFirst()
					.map(id -> InterfaceWrapperHelper.load(id, I_M_HU.class));

			final I_M_HU newHU = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, huSupplier);

			final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			huTable.putOrReplace(huIdentifier, newHU);
		}
	}

	private void validateHU(@NonNull final DataTableRow row)
	{
		for (final StepDefDataIdentifier huIdentifier : row.getAsIdentifier("M_HU_ID").toCommaSeparatedList())
		{
			final HuId huId = huIdentifier.lookupIdIn(huTable);
			SharedTestContext.put("huIdentifier", huIdentifier);
			final I_M_HU huRecord = handlingUnitsBL.getById(huId); // load it fresh!
			SharedTestContext.put("hu", huRecord);

			final SoftAssertions softly = new SoftAssertions();

			row.getAsOptionalString(COLUMNNAME_HUStatus).ifPresent(huStatus -> softly.assertThat(huRecord.getHUStatus()).as("HUStatus").isEqualTo(huStatus));
			row.getAsOptionalBoolean(COLUMNNAME_IsActive).ifPresent(isActive -> softly.assertThat(huRecord.isActive()).as("IsActive").isEqualTo(isActive));
			row.getAsOptionalIdentifier(COLUMNNAME_M_Locator_ID)
					.map(locatorTable::getId)
					.ifPresent(locatorId -> softly.assertThat(huRecord.getM_Locator_ID()).as("M_Locator_ID").isEqualTo(locatorId.getRepoId()));
			row.getAsOptionalBoolean("IsTopLevel")
					.ifPresent(isTopLevel -> softly.assertThat(handlingUnitsBL.isTopLevel(huRecord)).as("IsTopLevel").isEqualTo(isTopLevel));
			row.getAsOptionalIdentifier("Parent")
					.ifPresent(parentHUIdentifier -> {
						final HuId expectedParentId = parentHUIdentifier.isNullPlaceholder() ? null : huTable.getId(parentHUIdentifier);
						final HuId actualParentId = HuId.ofRepoIdOrNull(handlingUnitsDAO.retrieveParentId(huRecord));
						softly.assertThat(actualParentId).as("Parent").isEqualTo(expectedParentId);
					});

			softly.assertAll();
		}
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

		final I_M_HU hu = queryBuilder.create().firstOnlyOptional(I_M_HU.class).orElse(null);
		if (hu == null)
		{
			return false;
		}

		huTable.putOrReplace(request.getHuIdentifier(), hu);

		return true;
	}

	@NonNull
	private JsonSetClearanceStatusRequest.JsonHUIdentifier getHUIdentifier(@NonNull final Map<String, String> row)
	{
		final JsonSetClearanceStatusRequest.JsonHUIdentifier.JsonHUIdentifierBuilder builder = JsonSetClearanceStatusRequest.JsonHUIdentifier.builder();

		final String qrCodeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_HU_QRCode.COLUMNNAME_M_HU_QRCode_ID + "." + TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(qrCodeIdentifier))
		{
			final I_M_HU_QRCode qrCode = qrCodesTable.get(qrCodeIdentifier);

			return builder.qrCode(qrCode.getRenderedQRCode())
					.build();
		}

		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_M_HU hu = huTable.get(huIdentifier);
		return builder
				.metasfreshId(JsonMetasfreshId.of(hu.getM_HU_ID()))
				.build();
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

	@And("load M_HU from REST response JSON path")
	public void loadHUByRestResponseJSONPath(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String jsonPath = row.getAsString("JsonPath");
			final StepDefDataIdentifier huIdentifier = row.getAsIdentifier("M_HU_ID");

			final int huRepoId = restTestContext.getApiResponse().getByJsonPathAsInt(jsonPath);
			final I_M_HU hu = handlingUnitsBL.getById(HuId.ofRepoId(huRepoId));
			huTable.put(huIdentifier, hu);
		});
	}

}
