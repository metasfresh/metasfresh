package de.metas.cucumber.stepdefs.ddordercandidate;

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.pporder.PP_OrderLine_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_BOMLine_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateQuery;
import de.metas.distribution.ddordercandidate.DDOrderCandidateRepository;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.PPOrderLineCandidateId;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class DD_Order_Candidate_StepDef
{
	@NonNull private final DDOrderCandidateRepository ddOrderCandidateRepository = SpringContextHolder.instance.getBean(DDOrderCandidateRepository.class);
	@NonNull private final DD_Order_Candidate_StepDefData ddOrderCandidateTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final PP_Order_Candidate_StepDefData ppOrderCandidateTable;
	@NonNull private final PP_OrderLine_Candidate_StepDefData ppOrderLineCandidateTable;
	@NonNull private final PP_Order_StepDefData ppOrderTable;
	@NonNull private final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable;

	@And("^after not more than (.*)s, following DD_Order_Candidates are found$")
	public void validateDDOrderLineCandidates(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final DDOrderCandidate candidate = StepDefUtil.tryAndWaitForItem(timeoutSec, 1000, () -> validateDDOrderLineCandidate(row));
			row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderCandidateTable.put(identifier, candidate));
		});
	}

	private ItemProvider.ProviderResult<DDOrderCandidate> validateDDOrderLineCandidate(final DataTableRow row)
	{
		final DDOrderCandidateQuery query = toDDOrderCandidateQuery(row);
		SharedTestContext.put("query", query);

		final List<DDOrderCandidate> candidates = ddOrderCandidateRepository.list(query);
		SharedTestContext.put("candidates", candidates);

		if (candidates.isEmpty())
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No candidate found"
					+ "\n\trow=" + row
					+ "\n\tquery=" + query);
		}
		else if (candidates.size() > 1)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("More than one candidate found"
					+ "\n\trow=" + row
					+ "\n\tcandidates=" + candidates);
		}

		final DDOrderCandidate candidate = candidates.get(0);

		final BigDecimal qtyEntered = row.getAsOptionalBigDecimal("Qty").orElse(null);
		if (qtyEntered != null && qtyEntered.compareTo(candidate.getQty().toBigDecimal()) != 0)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("qty not matching, expected " + qtyEntered + " but found " + candidate.getQty().toBigDecimal()
					+ "\n\trow=" + row
					+ "\n\tcandidate=" + candidate);
		}

		final Boolean isSimulated = row.getAsOptionalBoolean("IsSimulated").toBooleanOrNull();
		if (isSimulated != null && isSimulated != candidate.isSimulated())
		{
			return ItemProvider.ProviderResult.resultWasNotFound("IsSimulated not matching, expected " + isSimulated + " but found " + candidate.isSimulated()
					+ "\n\trow=" + row
					+ "\n\tcandidate=" + candidate);
		}

		final StepDefDataIdentifier salesOrderLineIdentifier = row.getAsOptionalIdentifier("C_OrderLineSO_ID").orElse(null);
		if (salesOrderLineIdentifier != null)
		{
			final OrderLineId salesOrderLineId = salesOrderLineIdentifier.lookupIdIn(orderLineTable);
			if (!OrderLineId.equals(salesOrderLineId, candidate.getSalesOrderLineId()))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("IsSimulated not matching, expected " + salesOrderLineId + " but found " + candidate.getSalesOrderLineId()
						+ "\n\trow=" + row
						+ "\n\tcandidate=" + candidate);
			}
		}

		final StepDefDataIdentifier ppOrderCandidateIdentifier = row.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_Forward_PP_Order_Candidate_ID).orElse(null);
		if (ppOrderCandidateIdentifier != null)
		{
			final PPOrderCandidateId expectedPPOrderCandidateId = ppOrderCandidateIdentifier.lookupIdIn(ppOrderCandidateTable);
			final PPOrderRef ppOrderRef = candidate.getPpOrderRef();
			final PPOrderCandidateId actualPPOrderCandidateId = ppOrderRef != null ? PPOrderCandidateId.ofRepoIdOrNull(ppOrderRef.getPpOrderCandidateId()) : null;
			if (!PPOrderCandidateId.equals(actualPPOrderCandidateId, expectedPPOrderCandidateId))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("Forward_PP_Order_Candidate_ID not matching, expected " + expectedPPOrderCandidateId + " but found " + actualPPOrderCandidateId
						+ "\n\trow=" + row
						+ "\n\tcandidate=" + candidate);
			}
		}

		final StepDefDataIdentifier ppOrderLineCandidateIdentifier = row.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_Forward_PP_OrderLine_Candidate_ID).orElse(null);
		if (ppOrderLineCandidateIdentifier != null)
		{
			final PPOrderLineCandidateId expectedPPOrderLineCandidateId = ppOrderLineCandidateIdentifier.lookupIdIn(ppOrderLineCandidateTable);
			final PPOrderRef ppOrderRef = candidate.getPpOrderRef();
			final PPOrderLineCandidateId actualPPOrderLineCandidateId = ppOrderRef != null ? PPOrderLineCandidateId.ofRepoIdOrNull(ppOrderRef.getPpOrderLineCandidateId()) : null;
			if (!PPOrderLineCandidateId.equals(actualPPOrderLineCandidateId, expectedPPOrderLineCandidateId))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("Forward_PP_OrderLine_Candidate_ID not matching, expected " + expectedPPOrderLineCandidateId + " but found " + actualPPOrderLineCandidateId
						+ "\n\trow=" + row
						+ "\n\tcandidate=" + candidate);
			}
		}

		final StepDefDataIdentifier ppOrderIdentifier = row.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_Forward_PP_Order_ID).orElse(null);
		if (ppOrderIdentifier != null)
		{
			final PPOrderId expectedPPOrderId = ppOrderIdentifier.lookupIdIn(ppOrderTable);
			final PPOrderRef ppOrderRef = candidate.getPpOrderRef();
			final PPOrderId actualPPOrderId = ppOrderRef != null ? ppOrderRef.getPpOrderId() : null;
			if (!PPOrderId.equals(actualPPOrderId, expectedPPOrderId))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("Forward_PP_Order_ID not matching, expected " + expectedPPOrderId + " but found " + actualPPOrderId
						+ "\n\trow=" + row
						+ "\n\tcandidate=" + candidate);
			}
		}

		final StepDefDataIdentifier ppOrderBOMLineIdentifier = row.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_Forward_PP_Order_BOMLine_ID).orElse(null);
		if (ppOrderBOMLineIdentifier != null)
		{
			final PPOrderBOMLineId expectedPPOrderBOMLineId = ppOrderBOMLineIdentifier.lookupIdIn(ppOrderBOMLineTable);
			final PPOrderRef ppOrderRef = candidate.getPpOrderRef();
			final PPOrderBOMLineId actualPPOrderBOMLineId = ppOrderRef != null ? ppOrderRef.getPpOrderBOMLineId() : null;
			if (!PPOrderBOMLineId.equals(actualPPOrderBOMLineId, expectedPPOrderBOMLineId))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("Forward_PP_Order_BOMLine_ID not matching, expected " + expectedPPOrderBOMLineId + " but found " + actualPPOrderBOMLineId
						+ "\n\trow=" + row
						+ "\n\tcandidate=" + candidate);
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
