package de.metas.cucumber.stepdefs.ddordercandidate;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateQuery;
import de.metas.distribution.ddordercandidate.DDOrderCandidateRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.eevolution.model.I_DD_Order_Candidate;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class DD_Order_Candidate_StepDef
{
	@NonNull private final DDOrderCandidateRepository ddOrderCandidateRepository = SpringContextHolder.instance.getBean(DDOrderCandidateRepository.class);
	@NonNull private final DD_Order_Candidate_StepDefData ddOrderCandidateTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;

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

}
