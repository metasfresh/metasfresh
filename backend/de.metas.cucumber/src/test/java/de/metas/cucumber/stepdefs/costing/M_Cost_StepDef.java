package de.metas.cucumber.stepdefs.costing;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostElementId;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingLevel;
import de.metas.costing.CurrentCost;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.money.Money;
import de.metas.money.MoneyService;
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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Cost;
import org.compiere.util.Env;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class M_Cost_StepDef
{
	@NonNull private final CurrentCostsRepository currentCostsRepository = SpringContextHolder.instance.getBean(CurrentCostsRepository.class);
	@NonNull private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	@NonNull private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	@NonNull private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
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

			row.getAsOptionalMoney(I_M_Cost.COLUMNNAME_CurrentCostPrice, moneyService::getCurrencyIdByCurrencyCode)
					.ifPresent(currentCostPriceExpected -> {
						final Money currentCostPriceActual = currentCost.getCostPrice().toCostAmount().toMoney();
						assertThat(currentCostPriceActual).isEqualTo(currentCostPriceExpected);
					});

			row.getAsOptionalQuantity(I_M_Cost.COLUMNNAME_CurrentQty, uomDAO::getByX12DE355)
					.ifPresent(currentQtyExpected -> {
						final Quantity currentQtyActual = currentCost.getCurrentQty();
						assertThat(currentQtyActual).isEqualTo(currentQtyExpected);
					});
		});
	}

}
