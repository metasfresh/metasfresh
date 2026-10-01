package de.metas.cucumber.stepdefs.costing;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingLevel;
import de.metas.costing.CurrentCost;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Cost;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class M_Cost_StepDef
{
	@NonNull private final CostElementRepository costElementRepository = SpringContextHolder.instance.getBean(CostElementRepository.class);
	@NonNull private final CurrentCostsRepository currentCostsRepository = SpringContextHolder.instance.getBean(CurrentCostsRepository.class);
	@NonNull private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	@NonNull private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	@NonNull private final IProductDAO productDAO = Services.get(IProductDAO.class);
	@NonNull private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;
	@NonNull private final M_CostElement_StepDefData costElementTable;
	@NonNull private final M_Product_StepDefData productTable;

	@And("^validate current costs")
	public void validateCurrentCosts(DataTable table)
	{
		DataTableRows.of(table).forEach(this::validateCurrentCost);
	}

	public void validateCurrentCost(DataTableRow row) throws Throwable
	{
		final AcctSchemaId acctSchemaId = row.getAsIdentifier(I_M_Cost.COLUMNNAME_C_AcctSchema_ID).lookupIdIn(acctSchemaTable);
		final AcctSchema acctSchema = acctSchemaDAO.getById(acctSchemaId);
		final ProductId productId = row.getAsIdentifier(I_M_Cost.COLUMNNAME_M_Product_ID).lookupIdIn(productTable);
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(productId, acctSchema);
		final Set<CostElementId> costElementIds = costElementTable.getIdsOfCommaSeparatedString(row.getAsString(I_M_Cost.COLUMNNAME_M_CostElement_ID));

		assertThat(costElementIds).isNotEmpty();

		SharedTestContext.forEach(costElementIds, "costElement", costElementId -> {
			final CostSegmentAndElement costSegmentAndElement = CostSegmentAndElement.builder()
					.costingLevel(costingLevel)
					.acctSchemaId(acctSchema.getId())
					.costTypeId(acctSchema.getCosting().getCostTypeId())
					.clientId(ClientId.METASFRESH)
					.orgId(Env.getOrgId())
					.productId(Objects.requireNonNull(productId))
					.attributeSetInstanceId(AttributeSetInstanceId.NONE)
					.costElementId(costElementId)
					.build();
			SharedTestContext.put("costSegmentAndElement", costSegmentAndElement);

			final CurrentCost currentCost = currentCostsRepository.getOrNull(costSegmentAndElement);
			assertThat(currentCost).isNotNull();
			SharedTestContext.put("currentCost", currentCost);

			final SoftAssertions softly = new SoftAssertions();

			row.getAsOptionalMoney(I_M_Cost.COLUMNNAME_CurrentCostPrice, moneyService::getCurrencyIdByCurrencyCode)
					.ifPresent(currentCostPriceExpected -> {
						final Money currentCostPriceActual = currentCost.getCostPrice().toCostAmount().toMoney();
						softly.assertThat(currentCostPriceActual).as("CurrentCostPrice").isEqualTo(currentCostPriceExpected);
					});
			row.getAsOptionalMoney(I_M_Cost.COLUMNNAME_CumulatedAmt, moneyService::getCurrencyIdByCurrencyCode)
					.ifPresent(cumulatedAmtExpected -> {
						final Money cumulatedAmtActual = currentCost.getCumulatedAmt().toMoney();
						softly.assertThat(cumulatedAmtActual).as("CumulatedAmt").isEqualTo(cumulatedAmtExpected);
					});
			row.getAsOptionalQuantity(I_M_Cost.COLUMNNAME_CurrentQty, uomDAO::getByX12DE355)
					.ifPresent(currentQtyExpected -> {
						final Quantity currentQtyActual = currentCost.getCurrentQty();
						softly.assertThat(currentQtyActual).as("CurrentQty").isEqualTo(currentQtyExpected);
					});

			softly.assertAll();
		});
	}

	/**
	 * Asserts that the product's {@code P_Asset_Acct} balance up to {@code DateAcct} equals its current cost price times its current quantity
	 * ({@code M_Cost} of the given cost element), within one unit of the last decimal place of the schema currency's standard precision:
	 * the posted amounts are rounded to that precision, the cost price is not. The failure message reports expected, actual, delta and epsilon.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_AcctSchema_ID</b> — (required, identifier-ref)<br>
	 *   <b>M_Product_ID</b> — (required, identifier-ref)<br>
	 *   <b>M_CostElement_ID</b> — (required) the cost element, e.g. MovingAverageInvoice<br>
	 *   <b>DateAcct</b> — (required) the last posting date included in the balance<br>
	 * @cucumber.example
	 * <pre>
	 * And expect P_Asset balance for product equals its current cost price times quantity
	 *   | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | DateAcct   |
	 *   | acctSchema      | product      | MovingAverageInvoice | 2024-03-07 |
	 * </pre>
	 */
	@And("^expect P_Asset balance for product equals its current cost price times quantity$")
	public void assertAssetBalanceEqualsCurrentCostPriceTimesQty(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(row -> {
			final AcctSchemaId acctSchemaId = row.getAsIdentifier(I_M_Cost.COLUMNNAME_C_AcctSchema_ID).lookupIdIn(acctSchemaTable);
			final AcctSchema acctSchema = acctSchemaDAO.getById(acctSchemaId);
			final ProductId productId = row.getAsIdentifier(I_M_Cost.COLUMNNAME_M_Product_ID).lookupIdIn(productTable);
			final CostElementId costElementId = costElementTable.getSingleId(row.getAsString(I_M_Cost.COLUMNNAME_M_CostElement_ID));
			final LocalDate dateAcct = row.getAsLocalDate("DateAcct");

			final CurrentCost currentCost = currentCostsRepository.getOrNull(CostSegmentAndElement.builder()
					.costingLevel(productCostingBL.getCostingLevel(productId, acctSchema))
					.acctSchemaId(acctSchemaId)
					.costTypeId(acctSchema.getCosting().getCostTypeId())
					.clientId(ClientId.METASFRESH)
					.orgId(Env.getOrgId())
					.productId(productId)
					.attributeSetInstanceId(AttributeSetInstanceId.NONE)
					.costElementId(costElementId)
					.build());
			assertThat(currentCost).as("M_Cost of %s", row.getAsString(I_M_Cost.COLUMNNAME_M_Product_ID)).isNotNull();

			final BigDecimal expected = currentCost.getCostPrice().toCostAmount().toBigDecimal().multiply(currentCost.getCurrentQty().toBigDecimal());
			final BigDecimal actual = AccountingCucumberHelper.getProductAssetBalance(productId, acctSchemaId, dateAcct);
			final BigDecimal delta = actual.subtract(expected).abs();
			final CurrencyPrecision precision = currencyBL.getStdPrecision(acctSchema.getCurrencyId());
			final BigDecimal epsilon = BigDecimal.ONE.movePointLeft(precision.toInt());

			assertThat(delta)
					.as("P_Asset_Acct balance of %s up to %s vs. CurrentCostPrice x CurrentQty: expected=%s, actual=%s, delta=%s, epsilon=%s (standard precision %s)",
							row.getAsString(I_M_Cost.COLUMNNAME_M_Product_ID), dateAcct, expected, actual, delta, epsilon, precision.toInt())
					.isLessThanOrEqualTo(epsilon);
		});
	}

	/**
	 * Seeds / updates a product's current cost ({@code M_Cost}) for the primary accounting schema.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the product whose current cost is seeded<br>
	 *   <b>M_CostElement_ID</b> — (optional) target cost element as a costing-method name (e.g. AveragePO). When present,
	 *     seeds that specific element (e.g. a prior costing method); when absent, uses the primary acct-schema's own
	 *     costing-method element (behaviour-preserving default)<br>
	 *   <b>CurrentCostPrice</b> — (optional) the own cost price to set, e.g. "12.50" (currency defaults to the acct-schema currency)<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData, M_CostElement_StepDefData, C_AcctSchema_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update current costs
	 *   | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
	 *   | product      | AveragePO        | 12.50            |
	 * </pre>
	 */
	@And("^update current costs$")
	public void updateCurrentCosts(DataTable table)
	{
		DataTableRows.of(table).forEach(this::updateCurrentCost);
	}

	/**
	 * Removes all {@code M_Cost} rows of the given product(s), like a migrated product created without a cost record.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the product whose current-cost records to remove<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And remove current costs
	 *   | M_Product_ID  |
	 *   | productNoCost |
	 * </pre>
	 */
	@And("^remove current costs$")
	public void removeCurrentCosts(DataTable table)
	{
		DataTableRows.of(table).forEach(row -> {
			final ProductId productId = row.getAsIdentifier(I_M_Cost.COLUMNNAME_M_Product_ID).lookupIdIn(productTable);
			currentCostsRepository.deleteForProduct(productDAO.getById(productId));
		});
	}

	private void updateCurrentCost(@NonNull final DataTableRow row)
	{
		final AcctSchemaId acctSchemaId = acctSchemaDAO.getPrimaryAcctSchemaId(StepDefConstants.CLIENT_ID);
		final AcctSchema acctSchema = this.acctSchemaDAO.getById(acctSchemaId);
		final ProductId productId = row.getAsIdentifier(I_M_Cost.COLUMNNAME_M_Product_ID).lookupIdIn(productTable);
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(productId, acctSchema);
		// When M_CostElement_ID is given, seed that specific element's cost (e.g. a prior costing method
		// AveragePO); otherwise default to the acct-schema's own costing-method element (behaviour-preserving).
		final CostElementId costElementId = row.getAsOptionalString(I_M_Cost.COLUMNNAME_M_CostElement_ID)
				.map(costElementTable::getSingleId)
				.orElseGet(() -> costElementRepository.getOrCreateMaterialCostElement(StepDefConstants.CLIENT_ID, acctSchema.getCosting().getCostingMethod()).getId());
		final CostSegmentAndElement costSegmentAndElement = CostSegmentAndElement.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(acctSchema.getId())
				.costTypeId(acctSchema.getCosting().getCostTypeId())
				.clientId(ClientId.METASFRESH)
				.orgId(Env.getOrgId())
				.productId(Objects.requireNonNull(productId))
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElementId(costElementId)
				.build();
		SharedTestContext.put("costSegmentAndElement", costSegmentAndElement);

		final CurrentCost currentCost = currentCostsRepository.getOrCreateForUpdate(costSegmentAndElement);

		row.getAsOptionalMoney(
						I_M_Cost.COLUMNNAME_CurrentCostPrice,
						() -> moneyService.getCurrencyCodeByCurrencyId(acctSchema.getCurrencyId()),
						moneyService::getCurrencyIdByCurrencyCode
				)
				.ifPresent(costPrice -> {
					assertThat(currentCost.getCurrencyId()).as("C_Currency_ID").isEqualTo(costPrice.getCurrencyId());
					currentCost.setOwnCostPrice(CostAmount.ofMoney(costPrice));
				});

		currentCostsRepository.save(currentCost);
		SharedTestContext.put("currentCost", currentCost);
	}

}
