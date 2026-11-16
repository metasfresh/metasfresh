/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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


package de.metas.cucumber.stepdefs.costing;

import com.google.common.collect.ImmutableSet;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingMethod;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.costs.OrderCostCreateRequest;
import de.metas.order.costs.OrderCostService;
import de.metas.order.costs.OrderCostTypeId;
import de.metas.order.costs.calculation_methods.FixedAmountCostCalculationMethodParams;
import de.metas.order.costs.inout.InOutCost;
import de.metas.order.costs.invoice.CreateMatchInvoiceRequest;
import de.metas.inout.InOutId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Cost_Type;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.X_C_Cost_Type;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for order costs (e.g. freight added to a purchase order): the cost type, the cost on an order line,
 * and matching the in/out costs of a material receipt to a cost invoice line, as the "in/out costs" action of the invoice window does.
 *
 * <p><b>Gherkin usage example</b>:
 * <pre>{@code
 * And metasfresh contains C_Cost_Type:
 *   | Identifier |
 *   | freight    |
 * And metasfresh contains order costs:
 *   | C_OrderLine_ID | C_Cost_Type_ID | C_BPartner_ID | Amount |
 *   | po_l1          | freight        | vendor        | 20     |
 * ... order completed, material receipt completed (creates the in/out cost), cost invoice completed ...
 * When the in/out costs of material receipt "receipt" are matched to the line of invoice "costInvoice"
 * }</pre>
 */
@RequiredArgsConstructor
public class OrderCost_StepDef
{
	@NonNull private final OrderCostService orderCostService = SpringContextHolder.instance.getBean(OrderCostService.class);

	@NonNull private final C_Cost_Type_StepDefData costTypeTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final M_InOut_StepDefData inoutTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;

	/**
	 * Creates order cost types ({@code C_Cost_Type}), each with its own non-material cost element (a material cost element would add a second cost amount to every stocked product).
	 * <b>Columns</b>: {@code Identifier} (required),
	 * {@code CostCalculationMethod} (optional, default {@code F} = fixed amount), {@code CostDistributionMethod} (optional, default {@code A} = by amount).
	 */
	@Given("metasfresh contains C_Cost_Type:")
	public void createCostTypes(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier identifier = row.getAsIdentifier();

			final I_C_Cost_Type record = InterfaceWrapperHelper.newInstance(I_C_Cost_Type.class);
			record.setAD_Org_ID(0);
			record.setValue("cucumber-" + identifier.getAsString() + "-" + System.nanoTime());
			record.setName("cucumber cost type " + identifier.getAsString());
			record.setM_CostElement_ID(createNonMaterialCostElement(identifier).getM_CostElement_ID());
			record.setCostCalculationMethod(row.getAsOptionalString(I_C_Cost_Type.COLUMNNAME_CostCalculationMethod).orElse(X_C_Cost_Type.COSTCALCULATIONMETHOD_FixedAmount));
			record.setCostDistributionMethod(row.getAsOptionalString(I_C_Cost_Type.COLUMNNAME_CostDistributionMethod).orElse(X_C_Cost_Type.COSTDISTRIBUTIONMETHOD_Amount));
			InterfaceWrapperHelper.saveRecord(record);

			costTypeTable.putOrReplace(identifier, record);
		});
	}

	private I_M_CostElement createNonMaterialCostElement(@NonNull final StepDefDataIdentifier identifier)
	{
		final I_M_CostElement costElement = InterfaceWrapperHelper.newInstance(I_M_CostElement.class);
		costElement.setAD_Org_ID(0);
		costElement.setName("cucumber cost element " + identifier.getAsString() + " " + System.nanoTime());
		costElement.setCostElementType(CostElementType.Overhead.getCode());
		costElement.setCostingMethod(CostingMethod.AveragePO.getCode());
		costElement.setIsCalculated(false);
		InterfaceWrapperHelper.saveRecord(costElement);
		return costElement;
	}

	/**
	 * Adds a fixed-amount order cost to the (drafted) order of the given order line, as the "Create cost" action of the order window does.
	 * <b>Columns</b>: {@code C_OrderLine_ID} (required, the order line the cost is distributed on), {@code C_Cost_Type_ID} (required),
	 * {@code C_BPartner_ID} (required, the partner who invoices the cost), {@code Amount} (required, in the currency of the order).
	 */
	@And("metasfresh contains order costs:")
	public void createOrderCosts(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_OrderLine orderLine = row.getAsIdentifier(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID).lookupNotNullIn(orderLineTable);
			final I_C_Order order = InterfaceWrapperHelper.load(orderLine.getC_Order_ID(), I_C_Order.class);
			final OrderCostTypeId costTypeId = OrderCostTypeId.ofRepoId(row.getAsIdentifier(I_C_Cost_Type.COLUMNNAME_C_Cost_Type_ID).lookupNotNullIn(costTypeTable).getC_Cost_Type_ID());
			final BPartnerId bpartnerId = row.getAsIdentifier("C_BPartner_ID").lookupNotNullIdIn(bpartnerTable);
			final Money amount = Money.of(row.getAsBigDecimal("Amount"), CurrencyId.ofRepoId(order.getC_Currency_ID()));

			orderCostService.createOrderCost(OrderCostCreateRequest.builder()
					.bpartnerId(bpartnerId)
					.costTypeId(costTypeId)
					.costCalculationMethodParams(FixedAmountCostCalculationMethodParams.builder().fixedAmount(amount).build())
					.orderAndLineIds(ImmutableSet.of(OrderAndLineId.ofRepoIds(orderLine.getC_Order_ID(), orderLine.getC_OrderLine_ID())))
					.build());
		});
	}

	/**
	 * Matches all in/out costs of the material receipt to the (single) line of the cost invoice, as the "in/out costs" action of the invoice window does.
	 */
	@When("the in\\/out costs of material receipt {string} are matched to the line of invoice {string}")
	public void matchInOutCosts(@NonNull final String inoutIdentifier, @NonNull final String invoiceIdentifier)
	{
		final I_M_InOut inout = inoutTable.get(inoutIdentifier);
		final List<InOutCost> inoutCosts = orderCostService.getByInOutId(InOutId.ofRepoId(inout.getM_InOut_ID()));
		assertThat(inoutCosts).as("in/out costs of material receipt %s", inoutIdentifier).isNotEmpty();

		final List<de.metas.adempiere.model.I_C_InvoiceLine> invoiceLines = Services.get(IInvoiceDAO.class).retrieveLines(invoiceTable.get(invoiceIdentifier));
		assertThat(invoiceLines).as("lines of invoice %s", invoiceIdentifier).hasSize(1);

		orderCostService.createMatchInvoice(CreateMatchInvoiceRequest.builder()
				.invoiceAndLineId(InvoiceAndLineId.ofRepoId(invoiceLines.get(0).getC_Invoice_ID(), invoiceLines.get(0).getC_InvoiceLine_ID()))
				.inoutCostIds(inoutCosts.stream().map(InOutCost::getId).collect(ImmutableSet.toImmutableSet()))
				.build());
	}
}
