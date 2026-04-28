package de.metas.cucumber.stepdefs.costing;

import com.google.common.collect.ImmutableList;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.methods.CostAmountType;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.costing.CostDetailValidator.CostDetailValidatorBuilder;
import de.metas.money.MoneyService;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;

@RequiredArgsConstructor
public class M_CostDetail_StepDef
{
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final CostDetailRepository costDetailRepository = SpringContextHolder.instance.getBean(CostDetailRepository.class);
	@NonNull private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	@NonNull private final M_CostElement_StepDefData costElementTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final CostingDocumentRefResolver costingDocumentRefResolver;
	@NonNull private final CostDetailToTabularStringConverter costDetailTabularStringConverter;

	@And("^after not more than (.*)s, M_CostDetails are found for product (.*) and cost element (.*)$")
	public void validateCostDetails(
			final int timeoutSec,
			@NonNull final String productIdentifierStr,
			@NonNull final String costElementStr,
			@NonNull final DataTable table) throws Throwable
	{
		final DataTableRows rows = DataTableRows.of(table);

		newCostDetailValidatorBuilder()
				.productId(productTable.getId(productIdentifierStr))
				.costElementIds(costElementTable.getIdsOfCommaSeparatedString(costElementStr))
				.matchers(toCostDetailMatchers(rows))
				.columnNamesInOrder(rows.getColumnNames())
				.timeoutSec(timeoutSec)
				.validate();
	}

	private CostDetailValidatorBuilder newCostDetailValidatorBuilder()
	{
		return CostDetailValidator.builder()
				.costDetailRepository(costDetailRepository)
				.tabularStringConverter(costDetailTabularStringConverter)
				.costingDocumentRefResolver(costingDocumentRefResolver);
	}

	private CostDetailMatchers toCostDetailMatchers(@NonNull final DataTableRows rows)
	{
		return new CostDetailMatchers(
				rows.stream()
						.map(this::toCostDetailMatcher)
						.collect(ImmutableList.toImmutableList())
		);
	}

	private CostDetailMatcher toCostDetailMatcher(final DataTableRow row)
	{
		return CostDetailMatcher.builder()
				.row(row)
				.documentRef(costingDocumentRefResolver.resolveToCostingDocumentRef(row))
				.amtType(row.getAsOptionalString("AmtType").map(CostAmountType::ofCodeOrName).orElse(CostAmountType.MAIN))
				.amt(row.getAsOptionalMoney("Amt", moneyService::getCurrencyIdByCurrencyCode).orElse(null))
				.qty(row.getAsOptionalQuantity("Qty", uomDAO::getByX12DE355).orElse(null))
				.build();
	}
}
