package de.metas.cucumber.stepdefs.costing;

import com.google.common.collect.ImmutableList;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostElementId;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.waitUtilPosted;
import static org.assertj.core.api.Assertions.assertThat;

@Builder
public class CostDetailValidator
{
	@NonNull private final CostDetailRepository costDetailRepository;
	@NonNull final CostDetailToTabularStringConverter tabularStringConverter;
	@NonNull private final CostingDocumentRefResolver costingDocumentRefResolver;

	@NonNull private final ProductId productId;
	@NonNull private final Set<CostElementId> costElementIds;
	@NonNull private final CostDetailMatchers matchers;
	@Nullable private final List<String> columnNamesInOrder;
	final int timeoutSec;

	@SuppressWarnings("unused")
	public static class CostDetailValidatorBuilder
	{
		public void validate() throws Throwable {build().validate();}
	}

	public void validate() throws Throwable
	{
		assertThat(costElementIds).isNotEmpty();

		waitUtilPosted(costingDocumentRefResolver.resolveToDocumentHeaderRefs(matchers.getTableRecordReferences()));

		SharedTestContext.forEach(costElementIds, "costElement", costElementId -> {
			final CostDetailQuery costDetailQuery = CostDetailQuery.builder()
					.productId(productId)
					.costElementId(costElementId)
					.orderBy(CostDetailQuery.OrderBy.ID_ASC)
					.build();
			SharedTestContext.put("costDetailQuery", costDetailQuery);

			StepDefUtil.tryAndWaitForData(() -> retrieveActualRecords(costDetailQuery))
					.validateUsingFunction(matchers::checkValid)
					.maxWaitSeconds(timeoutSec)
					.execute();
		});

	}

	private CostDetailRecords retrieveActualRecords(final CostDetailQuery costDetailQuery)
	{
		return CostDetailRecords.builder()
				.list(costDetailRepository.stream(costDetailQuery).collect(ImmutableList.toImmutableList()))
				.tabularStringConverter(tabularStringConverter)
				.columnNamesInOrder(columnNamesInOrder)
				.build();
	}
}
