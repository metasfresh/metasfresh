package de.metas.cucumber.stepdefs.costing;

import de.metas.costing.CostElementId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_Cost;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies {@code PP_Order_Cost} rows (the per-production-order cost carried per product/cost-element/trx-type).
 */
@RequiredArgsConstructor
public class PP_Order_Cost_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	@NonNull private final PP_Order_StepDefData orderTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_CostElement_StepDefData costElementTable;

	/**
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>PP_Order_ID</b> — (required, identifier-ref) production order the cost row belongs to<br>
	 *   <b>M_Product_ID</b> — (required, identifier-ref) product the cost row is valued for<br>
	 *   <b>M_CostElement_ID</b> — (required, identifier-ref) cost element, e.g. "AveragePO" (a production order carries one PP_Order_Cost row per active cost element, so the trx-type alone does not identify a unique row)<br>
	 *   <b>PP_Order_Cost_TrxType</b> — (required) one of MI/MR/CO/BY/RU (see {@code X_PP_Order_Cost.PP_ORDER_COST_TRXTYPE_*})<br>
	 *   <b>CurrentCostPrice</b> — (optional) expected own cost price, e.g. "12 EUR"<br>
	 *   <b>CurrentCostPriceLL</b> — (optional) expected low-level (components) cost price, e.g. "12 EUR"<br>
	 * @cucumber.depends StepDefData: PP_Order_StepDefData, M_Product_StepDefData, M_CostElement_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And PP_Order_Cost are found:
	 *   | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID | PP_Order_Cost_TrxType | CurrentCostPrice |
	 *   | ppOrder                | finProd                 | AveragePO        | MR                    | 10 CHF           |
	 * </pre>
	 */
	@And("^PP_Order_Cost are found:$")
	public void pp_order_cost_are_found(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::validatePPOrderCost);
	}

	private void validatePPOrderCost(@NonNull final DataTableRow row)
	{
		final PPOrderId orderId = row.getAsIdentifier(I_PP_Order_Cost.COLUMNNAME_PP_Order_ID).lookupIdIn(orderTable);
		final ProductId productId = row.getAsIdentifier(I_PP_Order_Cost.COLUMNNAME_M_Product_ID).lookupIdIn(productTable);
		final Set<CostElementId> costElementIds = costElementTable.getIdsOfCommaSeparatedString(row.getAsString(I_PP_Order_Cost.COLUMNNAME_M_CostElement_ID));
		assertThat(costElementIds).as("M_CostElement_ID must resolve to exactly one cost element").hasSize(1);
		final CostElementId costElementId = costElementIds.iterator().next();
		final String trxType = row.getAsString(I_PP_Order_Cost.COLUMNNAME_PP_Order_Cost_TrxType);

		final I_PP_Order_Cost record = queryBL.createQueryBuilder(I_PP_Order_Cost.class)
				.addEqualsFilter(I_PP_Order_Cost.COLUMNNAME_PP_Order_ID, orderId)
				.addEqualsFilter(I_PP_Order_Cost.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_PP_Order_Cost.COLUMNNAME_M_CostElement_ID, costElementId)
				.addEqualsFilter(I_PP_Order_Cost.COLUMNNAME_PP_Order_Cost_TrxType, trxType)
				.create()
				.firstOnlyOptional(I_PP_Order_Cost.class)
				.orElseThrow(() -> new AdempiereException(
						"No PP_Order_Cost found for PP_Order_ID=" + orderId + ", M_Product_ID=" + productId
								+ ", M_CostElement_ID=" + costElementId + ", PP_Order_Cost_TrxType=" + trxType));

		row.getAsOptionalMoney(
						I_PP_Order_Cost.COLUMNNAME_CurrentCostPrice,
						moneyService::getCurrencyIdByCurrencyCode)
				.ifPresent(currentCostPriceExpected -> {
					final Money currentCostPriceActual = Money.of(record.getCurrentCostPrice(), currentCostPriceExpected.getCurrencyId());
					assertThat(currentCostPriceActual).as("CurrentCostPrice").isEqualTo(currentCostPriceExpected);
				});

		row.getAsOptionalMoney(
						I_PP_Order_Cost.COLUMNNAME_CurrentCostPriceLL,
						moneyService::getCurrencyIdByCurrencyCode)
				.ifPresent(currentCostPriceLLExpected -> {
					final Money currentCostPriceLLActual = Money.of(record.getCurrentCostPriceLL(), currentCostPriceLLExpected.getCurrencyId());
					assertThat(currentCostPriceLLActual).as("CurrentCostPriceLL").isEqualTo(currentCostPriceLLExpected);
				});
	}
}
