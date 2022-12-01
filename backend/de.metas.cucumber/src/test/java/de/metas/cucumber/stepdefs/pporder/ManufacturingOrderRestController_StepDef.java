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

package de.metas.cucumber.stepdefs.productionorder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_BOMLine;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.eevolution.model.I_PP_Product_BOMLine.COLUMNNAME_ComponentType;
import static org.eevolution.model.I_PP_Product_BOMLine.COLUMNNAME_QtyBatch;

public class ManufacturingOrderRestController_StepDef
{
	private final M_Product_StepDefData productTable;
	private final PP_Order_StepDefData ppOrderTable;

	private final TestContext testContext;

	public ManufacturingOrderRestController_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final PP_Order_StepDefData ppOrderTable,
			@NonNull final TestContext testContext)
	{
		this.productTable = productTable;
		this.ppOrderTable = ppOrderTable;
		this.testContext = testContext;
	}

	@And("^store PP_Order endpointPath (.*) in context$")
	public void store_pp_order_endpointPath_in_context(@NonNull String endpointPath)
	{
		final String regex = ".*(:[a-zA-Z]+)/?.*";

		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(endpointPath);

		while (matcher.find())
		{
			final String ppOrderIdentifierGroup = matcher.group(1);
			final String ppOrderIdentifier = ppOrderIdentifierGroup.replace(":", "");

			final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);
			assertThat(ppOrder).isNotNull();

			endpointPath = endpointPath.replace(ppOrderIdentifierGroup, String.valueOf(ppOrder.getPP_Order_ID()));

			testContext.setEndpointPath(endpointPath);
		}
	}

	@And("validate retrieve pp_order response:")
	public void validate_retrieve_pp_order_response(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonResponseManufacturingOrder ppOrderResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonResponseManufacturingOrder.class);
		assertThat(ppOrderResponse).isNotNull();

		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> row : tableRows)
		{
			validatePPOrder(row, ppOrderResponse);
		}
	}

	private void validatePPOrder(@NonNull final Map<String, String> row, @NonNull final JsonResponseManufacturingOrder ppOrderResponse)
	{
		final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String orgCode = DataTableUtil.extractStringForColumnName(row, "OrgCode");
		final ZonedDateTime dateOrdered = DataTableUtil.extractZonedDateTimeForColumnName(row, I_PP_Order.COLUMNNAME_DateOrdered);
		final ZonedDateTime datePromised = DataTableUtil.extractZonedDateTimeForColumnName(row, I_PP_Order.COLUMNNAME_DatePromised);
		final ZonedDateTime dateStartSchedule = DataTableUtil.extractZonedDateTimeForColumnName(row, I_PP_Order.COLUMNNAME_DateStartSchedule);
		final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(row, I_PP_Order.COLUMNNAME_QtyEntered);
		final String uomCode = DataTableUtil.extractStringForColumnName(row, "UomCode");
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String componentIdentifier = DataTableUtil.extractStringForColumnName(row, "components" + "." + I_PP_Product_BOMLine.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final BigDecimal componentQty = DataTableUtil.extractBigDecimalForColumnName(row, "components" + "." + COLUMNNAME_QtyBatch);
		final String componentUomCode = DataTableUtil.extractStringForColumnName(row, "components.UomCode");
		final String componentType = DataTableUtil.extractStringForColumnName(row, "components" + "." + COLUMNNAME_ComponentType);

		final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);
		assertThat(ppOrder).isNotNull();

		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(product).isNotNull();

		assertThat(ppOrderResponse.getOrderId().getValue()).isEqualTo(ppOrder.getPP_Order_ID());
		assertThat(ppOrderResponse.getOrgCode()).isEqualTo(orgCode);
		assertThat(ppOrderResponse.getDateOrdered()).isEqualTo(dateOrdered);
		assertThat(ppOrderResponse.getDatePromised()).isEqualTo(datePromised);
		assertThat(ppOrderResponse.getDateStartSchedule()).isEqualTo(dateStartSchedule);
		assertThat(ppOrderResponse.getQtyToProduce().getQty()).isEqualTo(qty);
		assertThat(ppOrderResponse.getQtyToProduce().getUomCode()).isEqualTo(uomCode);
		assertThat(ppOrderResponse.getProductId().getValue()).isEqualTo(product.getM_Product_ID());

		final I_M_Product component = productTable.get(componentIdentifier);
		assertThat(component).isNotNull();
		assertThat(ppOrderResponse.getComponents().get(0).getProduct().getProductNo()).isEqualTo(component.getValue());
		assertThat(ppOrderResponse.getComponents().get(0).getQty().getQty()).isEqualTo(componentQty);
		assertThat(ppOrderResponse.getComponents().get(0).getQty().getUomCode()).isEqualTo(componentUomCode);
		assertThat(ppOrderResponse.getComponents().get(0).getComponentType()).isEqualTo(componentType);
	}
}
