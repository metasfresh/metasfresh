/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.order;

import de.metas.common.util.Check;
import de.metas.common.util.StringUtils;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.IdentifierIds_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.attribute.M_Attribute_StepDefData;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Conditions_StepDefData;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.cucumber.stepdefs.pricing.C_TaxCategory_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersEvaluatee;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.IOrderLineBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Evaluatees;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.DataTableUtil.NULL_STRING;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_C_TaxCategory_ID;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_DateOrdered;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID;

@RequiredArgsConstructor
public class C_OrderLine_StepDef
{
	private static final String COLUMNNAME_PREFIX_ATTRIBUTE = "attribute:";

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final C_BPartner_StepDefData partnerTable;
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	@NonNull private final C_Flatrate_Conditions_StepDefData flatrateConditionsTable;
	@NonNull private final C_Flatrate_Term_StepDefData contractTable;
	@NonNull private final C_TaxCategory_StepDefData taxCategoryTable;
	@NonNull private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;
	@NonNull private final M_Attribute_StepDefData attributeTable;
	@NonNull private final C_Tax_StepDefData taxTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final IdentifierIds_StepDefData identifierIdsTable;

	@Given("metasfresh contains C_OrderLines:")
	public void metasfresh_contains_c_order_lines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID)
				.forEach(this::createOrderLine);
	}

	public void createOrderLine(final DataTableRow tableRow)
	{
		final de.metas.handlingunits.model.I_C_OrderLine orderLine = newInstance(de.metas.handlingunits.model.I_C_OrderLine.class);

		// make sure all defaults are set. If not, this method might be called later, when the orderLine is saved and might then override things set in this stepdef
		final I_C_Order orderRecord = tableRow.getAsIdentifier(I_C_OrderLine.COLUMNNAME_C_Order_ID).lookupNotNullIn(orderTable);
		orderLine.setC_Order_ID(orderRecord.getC_Order_ID());
		orderLineBL.setOrder(orderLine, orderRecord);

		final StepDefDataIdentifier productIdentifier = tableRow.getAsIdentifier(COLUMNNAME_M_Product_ID);
		final ProductId productId = productTable.getIdOptional(productIdentifier)
				.orElseGet(() -> productIdentifier.getAsId(ProductId.class));

		orderLine.setM_Product_ID(productId.getRepoId());
		orderLine.setQtyEntered(tableRow.getAsBigDecimal(I_C_OrderLine.COLUMNNAME_QtyEntered));

		tableRow.getAsOptionalIdentifier(I_C_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID)
				.map(attributeSetInstanceTable::getId)
				.ifPresent(asiId -> orderLine.setM_AttributeSetInstance_ID(asiId.getRepoId()));

		tableRow.getAsOptionalIdentifier(I_C_OrderLine.COLUMNNAME_C_BPartner_ID)
				.map(partnerTable::getId)
				.ifPresent(bpartnerId -> orderLine.setC_BPartner_ID(bpartnerId.getRepoId()));

		final String flatrateConditionsIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(flatrateConditionsIdentifier))
		{
			final I_C_Flatrate_Conditions flatrateConditions = flatrateConditionsTable.get(flatrateConditionsIdentifier);
			orderLine.setC_Flatrate_Conditions_ID(flatrateConditions.getC_Flatrate_Conditions_ID());
		}

		tableRow.getAsOptionalIdentifier(de.metas.handlingunits.model.I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.ifPresent(itemProductIdentifier -> {
					if (itemProductIdentifier.isNullPlaceholder())
					{
						orderLine.setM_HU_PI_Item_Product_ID(-1);
					}
					else
					{
						final HUPIItemProductId huPiItemProductId = huPiItemProductTable.getIdOptional(itemProductIdentifier)
								.orElseGet(() -> itemProductIdentifier.getAsId(HUPIItemProductId.class));

						orderLine.setM_HU_PI_Item_Product_ID(huPiItemProductId.getRepoId());
					}
				});

		final BigDecimal qtyEnteredTU = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, de.metas.handlingunits.model.I_C_OrderLine.COLUMNNAME_QtyEnteredTU);
		if (qtyEnteredTU != null)
		{
			orderLine.setQtyEnteredTU(qtyEnteredTU);
		}

		final BigDecimal qtyItemCapacity = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_C_OrderLine.COLUMNNAME_QtyItemCapacity);
		if (qtyItemCapacity != null)
		{
			orderLine.setQtyItemCapacity(qtyItemCapacity);
		}

		final String warehouseIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_OrderLine.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(warehouseIdentifier))
		{
			final I_M_Warehouse warehouse = warehouseTable.get(warehouseIdentifier);
			assertThat(warehouse).isNotNull();

			orderLine.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		}

		final String uomX12DE355 = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_OrderLine.COLUMNNAME_C_UOM_ID + "." + I_C_UOM.COLUMNNAME_X12DE355);
		if (Check.isNotBlank(uomX12DE355))
		{
			final UomId uomId = queryBL.createQueryBuilder(I_C_UOM.class)
					.addEqualsFilter(I_C_UOM.COLUMNNAME_X12DE355, uomX12DE355)
					.addOnlyActiveRecordsFilter()
					.create()
					.firstIdOnly(UomId::ofRepoIdOrNull);
			assertThat(uomId).as("Found no C_UOM with X12DE355=%s", uomX12DE355).isNotNull();
			orderLine.setC_UOM_ID(UomId.toRepoId(uomId));
		}

		tableRow.getAsOptionalBigDecimal("Price")
				.ifPresent(price -> {
					orderLine.setIsManualPrice(true);
					orderLine.setPriceEntered(price);
					orderLine.setPriceActual(price);
				});

		tableRow.getAsOptionalString(I_C_OrderLine.COLUMNNAME_Description)
				.ifPresent(orderLine::setDescription);

		tableRow.getAsOptionalString(I_C_OrderLine.COLUMNNAME_ExternalId)
				.ifPresent(orderLine::setExternalId);

		saveRecord(orderLine);

		tableRow.getAsOptionalIdentifier()
				.ifPresent(identifier -> orderLineTable.putOrReplace(identifier, orderLine));
	}

	@Then("the purchase order with document subtype {string} linked to order {string} has lines:")
	public void thePurchaseOrderLinkedToOrderO_HasLines(@Nullable final String docSubType, @NonNull final String linkedOrderIdentifier, @NonNull final DataTable dataTable)
	{
		final I_C_Order purchaseOrder = queryBL
				.createQueryBuilder(I_C_Order.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order.COLUMNNAME_Link_Order_ID, orderTable.get(linkedOrderIdentifier).getC_Order_ID())
				.create().firstOnly(I_C_Order.class);

		assertThat(purchaseOrder).isNotNull();

		final I_C_DocType docType = queryBL
				.createQueryBuilder(I_C_DocType.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_DocType.COLUMN_C_DocType_ID, purchaseOrder.getC_DocTypeTarget_ID())
				.create().firstOnly(I_C_DocType.class);

		assertThat(docType).isNotNull();
		if (Check.isNotBlank(docSubType))
		{
			assertThat(docType.getDocSubType()).isEqualTo(docSubType);
		}

		final List<I_C_OrderLine> purchaseOrderLines = queryBL
				.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, purchaseOrder.getC_Order_ID())
				.create()
				.list(I_C_OrderLine.class);

		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_QtyOrdered);
			final BigDecimal netAmt = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_LineNetAmt);
			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + ".Identifier");
			final String partnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_OrderLine.COLUMNNAME_C_BPartner_ID + ".Identifier");
			final int partnerId = Check.isBlank(partnerIdentifier) ? 0 : partnerTable.get(partnerIdentifier).getC_BPartner_ID();

			boolean linePresent = false;

			for (final I_C_OrderLine orderLine : purchaseOrderLines)
			{
				linePresent = orderLine.getLineNetAmt().compareTo(netAmt) == 0
						&& orderLine.getQtyOrdered().compareTo(qtyOrdered) == 0
						&& orderLine.getM_Product_ID() == productTable.get(productIdentifier).getM_Product_ID();
				if (partnerId > 0)
				{
					linePresent = linePresent && orderLine.getC_BPartner_ID() == partnerId;
				}

				if (linePresent)
				{
					break;
				}
			}

			assertThat(linePresent).isTrue();
		}
	}

	@And("validate the created order lines")
	public void validate_created_order_lines(@NonNull final DataTable table)
	{
		DataTableRows.of(table)
				.setAdditionalRowIdentifierColumnName(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID)
				.forEach(row -> {
					final I_C_Order orderRecord = orderTable.get(row.getAsIdentifier(I_C_OrderLine.COLUMNNAME_C_Order_ID));

					final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_C_OrderLine.COLUMNNAME_M_Product_ID);
					final Integer expectedProductId = productTable.getOptional(productIdentifier)
							.map(I_M_Product::getM_Product_ID)
							.orElseGet(productIdentifier::getAsInt);

					final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalForColumnName(row, I_C_OrderLine.COLUMNNAME_QtyOrdered);

					//dev-note: we assume the tests are not using the same product on different lines
					final I_C_OrderLine orderLineRecord = queryBL.createQueryBuilder(I_C_OrderLine.class)
							.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderRecord.getC_Order_ID())
							.addEqualsFilter(I_C_OrderLine.COLUMNNAME_M_Product_ID, expectedProductId)
							.addEqualsFilter(I_C_OrderLine.COLUMNNAME_QtyOrdered, qtyOrdered)
							.create()
							.firstOnlyNotNull(I_C_OrderLine.class);

					validateOrderLine(orderLineRecord, row);

					row.getAsOptionalIdentifier()
							.ifPresent(identifier -> orderLineTable.putOrReplaceIfSameId(identifier, orderLineRecord));
				});
	}

	@And("validate C_OrderLine:")
	public void validate_C_OrderLine(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID)
				.forEach(row -> {
					final I_C_OrderLine orderLine = orderLineTable.get(row.getAsIdentifier());
					InterfaceWrapperHelper.refresh(orderLine);
					validateOrderLine(orderLine, row);

				});
	}

	@And("update C_OrderLine:")
	public void update_C_OrderLine(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> table = dataTable.asMaps();
		for (final Map<String, String> row : table)
		{
			final String olIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OrderLine.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final de.metas.handlingunits.model.I_C_OrderLine orderLine = InterfaceWrapperHelper.create(orderLineTable.get(olIdentifier), de.metas.handlingunits.model.I_C_OrderLine.class);

			final String contractIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);

			if (Check.isNotBlank(contractIdentifier))
			{
				final I_C_Flatrate_Term contract = contractTable.get(contractIdentifier);

				orderLine.setC_Flatrate_Term_ID(contract.getC_Flatrate_Term_ID());
			}

			final BigDecimal updatedQtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_QtyEntered);
			if (updatedQtyEntered != null)
			{
				orderLine.setQtyEntered(updatedQtyEntered);
			}

			final String piItemProductIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + de.metas.handlingunits.model.I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID);
			if (Check.isNotBlank(piItemProductIdentifier))
			{
				final Integer piItemProductId = huPiItemProductTable.getOptional(piItemProductIdentifier)
						.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID)
						.orElseGet(() -> Integer.parseInt(piItemProductIdentifier));

				orderLine.setM_HU_PI_Item_Product_ID(piItemProductId);
			}

			final String attributeSetInstanceIdentifier = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			if (de.metas.util.Check.isNotBlank(attributeSetInstanceIdentifier))
			{
				final String asiIdentifierValue = DataTableUtil.nullToken2Null(attributeSetInstanceIdentifier);
				if (asiIdentifierValue == null)
				{
					orderLine.setM_AttributeSetInstance_ID(-1);
				}
				else
				{
					final I_M_AttributeSetInstance attributeSetInstance = attributeSetInstanceTable.get(attributeSetInstanceIdentifier);
					assertThat(attributeSetInstance).isNotNull();

					orderLine.setM_AttributeSetInstance_ID(attributeSetInstance.getM_AttributeSetInstance_ID());
				}
			}

			final BigDecimal updatedQtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_QtyOrdered);
			if (updatedQtyOrdered != null)
			{
				orderLine.setQtyOrdered(updatedQtyOrdered);
			}

			final String asiIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);

			if (Check.isNotBlank(asiIdentifier))
			{
				final Integer asiId = attributeSetInstanceTable.getOptional(asiIdentifier)
						.map(I_M_AttributeSetInstance::getM_AttributeSetInstance_ID)
						.orElseGet(() -> Integer.parseInt(asiIdentifier));

				orderLine.setM_AttributeSetInstance_ID(asiId);
			}

			saveRecord(orderLine);

			orderLineTable.putOrReplace(olIdentifier, orderLine);
		}
	}

	@And("^delete C_OrderLine identified by (.*), but keep its id into identifierIds table$")
	public void delete_orderLine(@NonNull final String orderLineIdentifier)
	{
		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
		identifierIdsTable.put(orderLineIdentifier, orderLine.getC_OrderLine_ID());
		InterfaceWrapperHelper.delete(orderLine);
	}

	@And("load C_Order from C_OrderLine")
	public void loadC_Order(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> table = dataTable.asMaps();
		for (final Map<String, String> row : table)
		{
			loadC_Order(row);
		}
	}

	private void loadC_Order(@NonNull final Map<String, String> row)
	{
		final String olIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OrderLine.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine orderLine = orderLineTable.get(olIdentifier);
		assertThat(orderLine).isNotNull();

		final I_C_Order orderRecord = InterfaceWrapperHelper.load(orderLine.getC_Order_ID(), I_C_Order.class);
		assertThat(orderRecord).isNotNull();

		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OrderLine.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		orderTable.putOrReplace(orderIdentifier, orderRecord);
	}

	@Given("metasfresh contains C_OrderLine expecting error:")
	public void metasfresh_contains_c_order_lines_expecting_error(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		if (tableRows.size() > 1)
		{
			throw new IllegalArgumentException("Multiple rows are not supported!");
		}

		try
		{
			metasfresh_contains_c_order_lines(dataTable);

			Assertions.fail("An Exception should have been thrown !");
		}
		catch (final AdempiereException exception)
		{
			final String errorCode = DataTableUtil.extractStringOrNullForColumnName(tableRows.get(0), "ErrorCode");

			assertThat(exception.getErrorCode()).isEqualTo(errorCode);
		}
	}

	private void validateOrderLine(@NonNull final I_C_OrderLine orderLine, @NonNull final DataTableRow row)
	{
		final SoftAssertions softly = new SoftAssertions();

		final String uomBPartner355Code = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_C_UOM_BPartner_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(uomBPartner355Code))
		{
			final UomId bPartnerUOMId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(uomBPartner355Code));
			softly.assertThat(orderLine.getC_UOM_BPartner_ID()).isEqualTo(bPartnerUOMId.getRepoId());
		}

		final String isManualPriceStr = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_IsManualPrice);
		if (Check.isNotBlank(isManualPriceStr))
		{
			final boolean isManualPrice = StringUtils.toBoolean(isManualPriceStr);
			softly.assertThat(orderLine.isManualPrice()).isEqualTo(isManualPrice);
		}

		final String bPartnerQtyItemCapacity = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_BPartner_QtyItemCapacity);
		if (Check.isNotBlank(bPartnerQtyItemCapacity))
		{
			softly.assertThat(orderLine.getBPartner_QtyItemCapacity()).isEqualByComparingTo(bPartnerQtyItemCapacity);
		}

		final BigDecimal qtyEnteredInBPartnerUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_QtyEnteredInBPartnerUOM);
		if (qtyEnteredInBPartnerUOM != null)
		{
			softly.assertThat(orderLine.getQtyEnteredInBPartnerUOM()).isEqualByComparingTo(qtyEnteredInBPartnerUOM);
		}

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_QtyEntered);
		if (qtyEntered != null)
		{
			softly.assertThat(orderLine.getQtyEntered()).isEqualByComparingTo(qtyEntered);
		}

		final String uomCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());

		if (Check.isNotBlank(uomCode))
		{
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(uomCode));
			softly.assertThat(orderLine.getC_UOM_ID()).isEqualTo(uomId.getRepoId());
		}

		final BigDecimal qtyItemCapacity = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_QtyItemCapacity);

		if (qtyItemCapacity != null)
		{
			softly.assertThat(orderLine.getQtyItemCapacity()).isEqualByComparingTo(qtyItemCapacity);
		}

		row.getAsOptionalLocalDateTimestamp(I_C_OrderLine.COLUMNNAME_DateOrdered)
				.ifPresent(dateOrdered -> softly.assertThat(orderLine.getDateOrdered()).as(COLUMNNAME_DateOrdered).isEqualTo(dateOrdered));

		final Optional<StepDefDataIdentifier> taxCategoryIdentifier = row.getAsOptionalIdentifier(COLUMNNAME_C_TaxCategory_ID);
		if (taxCategoryIdentifier.isPresent())
		{
			final Integer taxCategoryId = taxCategoryIdentifier.flatMap(taxCategoryTable::getOptional)
					.map(I_C_TaxCategory::getC_TaxCategory_ID)
					.orElseGet(() -> taxCategoryIdentifier.map(StepDefDataIdentifier::getAsInt).orElse(null));
			softly.assertThat(orderLine.getC_TaxCategory_ID()).as("C_TaxCategory_ID").isEqualTo(taxCategoryId);
		}

		row.getAsOptionalIdentifier(I_C_OrderLine.COLUMNNAME_C_Order_ID)
				.ifPresent(orderIdentifier -> softly.assertThat(orderLine.getC_Order_ID()).as("C_Order_ID").isEqualTo(orderTable.get(orderIdentifier).getC_Order_ID()));

		row.getAsOptionalBigDecimal("qtydelivered")
				.ifPresent(qtyDelivered -> softly.assertThat(orderLine.getQtyDelivered()).as("QtyDelivered").isEqualByComparingTo(qtyDelivered));

		row.getAsOptionalBigDecimal("price")
				.ifPresent(price -> softly.assertThat(orderLine.getPriceEntered()).as("PriceEntered").isEqualByComparingTo(price));

		row.getAsOptionalBigDecimal("discount")
				.ifPresent(discount -> softly.assertThat(orderLine.getDiscount()).as("Discount").isEqualByComparingTo(discount));

		row.getAsOptionalBoolean("processed")
				.ifPresent(processed -> softly.assertThat(orderLine.isProcessed()).as("Processed").isEqualTo(processed));

		row.getAsOptionalIdentifier(I_C_OLCand.COLUMNNAME_M_Product_ID)
				.map(productIdentifier -> productTable.getIdOptional(productIdentifier)
						.orElseGet(() -> productIdentifier.getAsId(ProductId.class)))
				.ifPresent(productId -> softly.assertThat(orderLine.getM_Product_ID()).as("M_Product_ID").isEqualTo(productId.getRepoId()));

		row.getAsOptionalBigDecimal(I_C_OrderLine.COLUMNNAME_QtyOrdered)
				.ifPresent(qtyOrdered -> softly.assertThat(orderLine.getQtyOrdered()).as("QtyOrdered").isEqualByComparingTo(qtyOrdered));

		row.getAsOptionalBigDecimal("qtyinvoiced")
				.ifPresent(qtyInvoiced -> softly.assertThat(orderLine.getQtyInvoiced()).as("QtyInvoiced").isEqualByComparingTo(qtyInvoiced));

		row.getAsOptionalString("currencyCode")
				.map(CurrencyCode::ofThreeLetterCode)
				.ifPresent(currencyCode -> {
					final Currency currency = currencyDAO.getByCurrencyCode(currencyCode);
					softly.assertThat(orderLine.getC_Currency_ID()).isEqualTo(currency.getId().getRepoId());
				});

		final String flatrateConditionsIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(flatrateConditionsIdentifier))
		{
			final I_C_Flatrate_Conditions flatrateConditions = flatrateConditionsTable.get(flatrateConditionsIdentifier);
			softly.assertThat(orderLine.getC_Flatrate_Conditions_ID()).isEqualTo(flatrateConditions.getC_Flatrate_Conditions_ID());
		}

		final String x12de355StockCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(x12de355StockCode))
		{
			final UomId stockUomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355StockCode));
			softly.assertThat(orderLine.getC_UOM_ID()).isEqualTo(stockUomId.getRepoId());
		}

		final String x12de355PriceCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_Price_UOM_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(x12de355PriceCode))
		{
			final UomId productPriceUomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355PriceCode));
			softly.assertThat(orderLine.getPrice_UOM_ID()).isEqualTo(productPriceUomId.getRepoId());
		}

		final String productDescription = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_ProductDescription);
		if (de.metas.util.Check.isNotBlank(productDescription))
		{
			softly.assertThat(orderLine.getProductDescription()).isEqualTo(productDescription);
		}

		row.getAsOptionalIdentifier(COLUMNNAME_M_AttributeSetInstance_ID)
				.ifPresent(attributeSetInstanceIdentifier -> {
					final I_M_AttributeSetInstance expectedASI = attributeSetInstanceTable.get(attributeSetInstanceIdentifier);
					assertThat(expectedASI).isNotNull();

					final AttributesKey expectedASIKey = AttributesKeys
							.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(expectedASI.getM_AttributeSetInstance_ID()))
							.orElse(AttributesKey.NONE);

					final AttributesKey orderLineAttributesKeys = AttributesKeys
							.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(orderLine.getM_AttributeSetInstance_ID()))
							.orElse(AttributesKey.NONE);

					softly.assertThat(orderLineAttributesKeys).isEqualTo(expectedASIKey);
				});

		row.getAsOptionalString(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID + ":" + I_M_AttributeInstance.Table_Name + "." + I_M_AttributeInstance.COLUMNNAME_Value)
				.ifPresent(asiValues -> {
					final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNone(orderLine.getM_AttributeSetInstance_ID());

					final String asiValuesParsed = resolveASIValueExpectationContextVars(orderLine, asiValues);

					Arrays.asList(asiValuesParsed.split(","))
							.forEach(expectation -> validateAttributeValue(softly, attributeSetInstanceId, AttributeIdentifierAndValue.parse(expectation)));
				});

		row.getColumnNames()
				.stream()
				.filter(columnName -> columnName.startsWith(COLUMNNAME_PREFIX_ATTRIBUTE))
				.forEach(columnName -> {
					final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNone(orderLine.getM_AttributeSetInstance_ID());
					final StepDefDataIdentifier attributeIdentifier = StepDefDataIdentifier.ofString(columnName.substring(COLUMNNAME_PREFIX_ATTRIBUTE.length()));
					@NonNull final String value = row.getAsString(columnName);
					final String valueResolved = resolveASIValueExpectationContextVars(orderLine, value);

					validateAttributeValue(softly, attributeSetInstanceId, AttributeIdentifierAndValue.of(attributeIdentifier, valueResolved));
				});

		final String huPiItemProductIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + de.metas.handlingunits.model.I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(huPiItemProductIdentifier))
		{
			final I_M_HU_PI_Item_Product huPiItemProduct = huPiItemProductTable.get(huPiItemProductIdentifier);
			final de.metas.handlingunits.model.I_C_OrderLine orderLineHU = InterfaceWrapperHelper.load(orderLine.getC_OrderLine_ID(), de.metas.handlingunits.model.I_C_OrderLine.class);
			softly.assertThat(huPiItemProduct.getM_HU_PI_Item_Product_ID()).isEqualTo(orderLineHU.getM_HU_PI_Item_Product_ID());
		}

		final BigDecimal qtyReserved = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_QtyReserved);
		if (qtyReserved != null)
		{
			softly.assertThat(orderLine.getQtyReserved()).isEqualByComparingTo(qtyReserved);
		}

		final String taxIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OrderLine.COLUMNNAME_C_Tax_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(taxIdentifier))
		{
			final TaxId taxId = taxTable.getId(taxIdentifier);
			softly.assertThat(orderLine.getC_Tax_ID()).isEqualTo(taxId.getRepoId());
		}

		softly.assertAll();
	}

	private String resolveASIValueExpectationContextVars(final @NotNull I_C_OrderLine orderLine, final String asiValues)
	{
		return IStringExpression.compile(asiValues)
				.evaluate(
						Evaluatees.compose(
								InterfaceWrapperHelper.getEvaluatee(orderLine),
								IdentifiersEvaluatee.builder()
										.table(productTable)
										.table(orderTable)
										.build()
						),
						IExpressionEvaluator.OnVariableNotFound.Preserve);
	}

	private void validateAttributeValue(
			@NonNull final SoftAssertions softly,
			@NonNull final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final AttributeIdentifierAndValue expectation)
	{
		if (attributeSetInstanceId.isNone())
		{
			throw new AdempiereException("No ASI set on C_OrderLine");
		}

		final Attribute attribute = attributeTable.get(expectation.getAttributeIdentifier());

		final I_M_AttributeInstance attributeInstance = queryBL.createQueryBuilder(I_M_AttributeInstance.class)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID, attributeSetInstanceId)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID, attribute.getAttributeId())
				.create()
				.firstOnlyOptional(I_M_AttributeInstance.class)
				.orElseThrow(() -> new AdempiereException("No M_AttributeInstance found for M_Attribute_ID and ASI")
						.appendParametersToMessage()
						.setParameter("M_Attribute_ID", attribute)
						.setParameter("M_AttributeSetInstance_ID", attributeSetInstanceId));

		attribute.getValueType().apply(new AttributeValueType.CaseConsumer()
		{
			@Override
			public void string()
			{
				if (expectation.isNullPlaceholder())
				{
					softly.assertThat(attributeInstance.getValue()).as(expectation.toString()).isNullOrEmpty();
				}
				else
				{
					softly.assertThat(attributeInstance.getValue()).as(expectation.toString()).isEqualTo(expectation.getValueAsString());
				}
			}

			@Override
			public void number()
			{
				if (expectation.isNullPlaceholder())
				{
					softly.assertThat(attributeInstance.getValueNumber()).as(expectation.toString()).isNotNull();
				}
				else
				{
					softly.assertThat(attributeInstance.getValueNumber()).as(expectation.toString()).isEqualTo(expectation.getValueAsBigDecimal());
				}
			}

			@Override
			public void date()
			{
				if (expectation.isNullPlaceholder())
				{
					softly.assertThat(attributeInstance.getValueDate()).as(expectation.toString()).isNotNull();
				}
				else
				{
					final LocalDate actualValue = TimeUtil.asLocalDate(attributeInstance.getValueDate());
					softly.assertThat(actualValue).as(expectation.toString()).isEqualTo(expectation.getValueAsLocalDate());
				}
			}

			@Override
			public void list()
			{
				if (expectation.isNullPlaceholder())
				{
					softly.assertThat(attributeInstance.getValue()).as(expectation.toString()).isNullOrEmpty();
				}
				else
				{
					softly.assertThat(attributeInstance.getValue()).as(expectation.toString()).isEqualTo(expectation.getValueAsString());
				}
			}
		});
	}

	//
	//
	// ------------------------------------------------------------------------
	//
	//
	@Value(staticConstructor = "of")
	private static class AttributeIdentifierAndValue
	{
		@NonNull StepDefDataIdentifier attributeIdentifier;
		@NonNull @Getter(AccessLevel.NONE) String value;

		private static final String SEPARATOR = ":";

		public static AttributeIdentifierAndValue parse(@NonNull final String string)
		{
			try
			{
				final int separatorIdx = string.indexOf(SEPARATOR);
				final StepDefDataIdentifier attributeIdentifier = StepDefDataIdentifier.ofString(string.substring(0, separatorIdx));
				final String expectedAttrValue = string.substring(separatorIdx + 1);
				return of(attributeIdentifier, expectedAttrValue);
			}
			catch (final Exception ex)
			{
				throw new AdempiereException("String not matching attributeIdentifier" + SEPARATOR + "value format: " + string, ex);
			}
		}

		@Override
		public String toString()
		{
			return attributeIdentifier + SEPARATOR + value;
		}

		public boolean isNullPlaceholder()
		{
			return value.isEmpty() || NULL_STRING.equals(value);
		}

		@Nullable
		public String getValueAsString()
		{
			return isNullPlaceholder() ? null : value;
		}

		@Nullable
		public BigDecimal getValueAsBigDecimal()
		{
			return isNullPlaceholder() ? null : NumberUtils.asBigDecimal(value);
		}

		@Nullable
		public LocalDate getValueAsLocalDate()
		{
			return isNullPlaceholder() ? null : LocalDate.parse(value);
		}

	}
}
