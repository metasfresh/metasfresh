package de.metas.cucumber.stepdefs.ddordercandidate;

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateQuery;
import de.metas.distribution.ddordercandidate.DDOrderCandidateRepository;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.eevolution.model.I_DD_Order_Candidate;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class DD_Order_Candidate_StepDef
{
	@NonNull private final DDOrderCandidateRepository ddOrderCandidateRepository = SpringContextHolder.instance.getBean(DDOrderCandidateRepository.class);
	@NonNull private final DD_Order_Candidate_StepDefData ddOrderCandidateTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;

	@And("^after not more than (.*)s, following DD_Order_Candidates are found$")
	public void validateDDOrderLineCandidates(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final DDOrderCandidate candidate = StepDefUtil.tryAndWaitForItem(timeoutSec, 1000, () -> validateDDOrderLineCandidate(row), (Supplier<String>)null);
			row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderCandidateTable.put(identifier, candidate));
		});
	}

	private ItemProvider.ProviderResult<DDOrderCandidate> validateDDOrderLineCandidate(final DataTableRow row)
	{
		final List<DDOrderCandidate> candidates = ddOrderCandidateRepository.list(toDDOrderCandidateQuery(row));
		SharedTestContext.put("candidates", candidates);

		if (candidates.isEmpty())
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No candidate found");
		}
		else if (candidates.size() > 1)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("More than one candidate found");
		}

		final DDOrderCandidate candidate = candidates.get(0);

		final BigDecimal qtyEntered = row.getAsOptionalBigDecimal("Qty").orElse(null);
		if (qtyEntered != null && qtyEntered.compareTo(candidate.getQty().toBigDecimal()) != 0)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("qty not matching, expected " + qtyEntered + " but found " + candidate.getQty().toBigDecimal());
		}

		final Boolean isSimulated = row.getAsOptionalBoolean("IsSimulated").toBooleanOrNull();
		if (isSimulated != null && isSimulated != candidate.isSimulated())
		{
			return ItemProvider.ProviderResult.resultWasNotFound("IsSimulated not matching, expected " + isSimulated + " but found " + candidate.isSimulated());
		}

		final StepDefDataIdentifier salesOrderLineIdentifier = row.getAsOptionalIdentifier("C_OrderLineSO_ID").orElse(null);
		if (salesOrderLineIdentifier != null)
		{
			final OrderLineId salesOrderLineId = salesOrderLineIdentifier.lookupIdIn(orderLineTable);
			if (!OrderLineId.equals(salesOrderLineId, candidate.getSalesOrderLineId()))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("IsSimulated not matching, expected " + salesOrderLineId + " but found " + candidate.getSalesOrderLineId());
			}
		}

		return ItemProvider.ProviderResult.resultWasFound(candidate);
	}

	private DDOrderCandidateQuery toDDOrderCandidateQuery(final DataTableRow row)
	{
		return DDOrderCandidateQuery.builder()
				.productId(row.getAsIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_Product_ID).lookupIdIn(productTable))
				.sourceWarehouseId(row.getAsIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_Warehouse_From_ID).lookupIdIn(warehouseTable))
				.targetWarehouseId(row.getAsIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_WarehouseTo_ID).lookupIdIn(warehouseTable))
				.processed(row.getAsOptionalBoolean(I_DD_Order_Candidate.COLUMNNAME_Processed).toBooleanOrNull())
				.build();
	}

	@And("^no DD_Order_Candidates found for product (.*)$")
	public void assertNoCandidatesFoundByProduct(@NonNull final String productIdentifierStr)
	{
		final ProductId productId = StepDefDataIdentifier.ofString(productIdentifierStr).lookupIdIn(productTable);

		final List<DDOrderCandidate> candidates = ddOrderCandidateRepository.list(DDOrderCandidateQuery.builder()
				.productId(productId)
				.build());

		assertThat(candidates).isEmpty();
	}
}
