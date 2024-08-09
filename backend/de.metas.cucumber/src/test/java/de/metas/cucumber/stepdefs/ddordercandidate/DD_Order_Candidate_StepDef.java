package de.metas.cucumber.stepdefs.ddordercandidate;

import com.google.common.collect.ImmutableSet;
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
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
import de.metas.distribution.ddordercandidate.DDOrderCandidateQuery;
import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.PPOrderLineCandidateId;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class DD_Order_Candidate_StepDef
{
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final DDOrderCandidateService ddOrderCandidateService = SpringContextHolder.instance.getBean(DDOrderCandidateService.class);
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
			final DDOrderCandidate candidate = StepDefUtil.<DDOrderCandidate>tryAndWaitForItem()
					.worker(() -> validateDDOrderLineCandidate(row))
					.maxWaitSeconds(timeoutSec)
					.execute();
			row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderCandidateTable.putOrReplace(identifier, candidate));
		});
	}

	private ItemProvider.ProviderResult<DDOrderCandidate> validateDDOrderLineCandidate(final DataTableRow row)
	{
		final DDOrderCandidateQuery query = toDDOrderCandidateQuery(row);
		SharedTestContext.put("query", query);

		final List<DDOrderCandidate> candidates = ddOrderCandidateService.list(query);
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
		return validateDDOrderLineCandidate(row, candidate);
	}

	private ItemProvider.@NonNull ProviderResult<DDOrderCandidate> validateDDOrderLineCandidate(final DataTableRow expected, final DDOrderCandidate actual)
	{
		final ProductId productId = expected.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_Product_ID).map(productTable::getId).orElse(null);
		if (productId != null && !ProductId.equals(actual.getProductId(), productId))
		{
			return ItemProvider.ProviderResult.resultWasNotFound("product not matching, expected " + productId + " but found " + actual.getProductId()
					+ "\n\trow=" + expected
					+ "\n\tcandidate=" + actual);
		}

		final WarehouseId sourceWarehouseId = expected.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_Warehouse_From_ID).map(warehouseTable::getId).orElse(null);
		if (sourceWarehouseId != null && !WarehouseId.equals(actual.getSourceWarehouseId(), sourceWarehouseId))
		{
			return ItemProvider.ProviderResult.resultWasNotFound("sourceWarehouseId not matching, expected " + sourceWarehouseId + " but found " + actual.getSourceWarehouseId()
					+ "\n\trow=" + expected
					+ "\n\tcandidate=" + actual);
		}

		final WarehouseId targetWarehouseId = expected.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_WarehouseTo_ID).map(warehouseTable::getId).orElse(null);
		if (targetWarehouseId != null && !WarehouseId.equals(actual.getTargetWarehouseId(), targetWarehouseId))
		{
			return ItemProvider.ProviderResult.resultWasNotFound("targetWarehouseId not matching, expected " + targetWarehouseId + " but found " + actual.getTargetWarehouseId()
					+ "\n\trow=" + expected
					+ "\n\tcandidate=" + actual);
		}

		final Quantity qtyEntered = expected.getAsOptionalQuantity("Qty", uomDAO::getByX12DE355).orElse(null);
		if (qtyEntered != null && qtyEntered.compareTo(actual.getQty()) != 0)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("qty not matching, expected " + qtyEntered + " but found " + actual.getQty()
					+ "\n\trow=" + expected
					+ "\n\tcandidate=" + actual);
		}

		final Quantity qtyProcessed = expected.getAsOptionalQuantity("QtyProcessed", uomDAO::getByX12DE355).orElse(null);
		if (qtyProcessed != null && qtyProcessed.compareTo(actual.getQtyProcessed()) != 0)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("qtyProcessed not matching, expected " + qtyProcessed + " but found " + actual.getQtyProcessed()
					+ "\n\trow=" + expected
					+ "\n\tcandidate=" + actual);
		}

		final Quantity qtyToProcess = expected.getAsOptionalQuantity("QtyToProcess", uomDAO::getByX12DE355).orElse(null);
		if (qtyToProcess != null && qtyToProcess.compareTo(actual.getQtyToProcess()) != 0)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("qtyToProcess not matching, expected " + qtyToProcess + " but found " + actual.getQtyToProcess()
					+ "\n\trow=" + expected
					+ "\n\tcandidate=" + actual);
		}

		final Boolean processed = expected.getAsOptionalBoolean(I_DD_Order_Candidate.COLUMNNAME_Processed).toBooleanOrNull();
		if (processed != null && actual.isProcessed() != processed)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("processed not matching, expected " + processed + " but found " + actual.isProcessed()
					+ "\n\trow=" + expected
					+ "\n\tcandidate=" + actual);
		}

		final Boolean isSimulated = expected.getAsOptionalBoolean("IsSimulated").toBooleanOrNull();
		if (isSimulated != null && isSimulated != actual.isSimulated())
		{
			return ItemProvider.ProviderResult.resultWasNotFound("IsSimulated not matching, expected " + isSimulated + " but found " + actual.isSimulated()
					+ "\n\trow=" + expected
					+ "\n\tcandidate=" + actual);
		}

		final StepDefDataIdentifier salesOrderLineIdentifier = expected.getAsOptionalIdentifier("C_OrderLineSO_ID").orElse(null);
		if (salesOrderLineIdentifier != null)
		{
			final OrderLineId salesOrderLineId = salesOrderLineIdentifier.lookupIdIn(orderLineTable);
			if (!OrderLineId.equals(salesOrderLineId, actual.getSalesOrderLineId()))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("IsSimulated not matching, expected " + salesOrderLineId + " but found " + actual.getSalesOrderLineId()
						+ "\n\trow=" + expected
						+ "\n\tcandidate=" + actual);
			}
		}

		final StepDefDataIdentifier ppOrderCandidateIdentifier = expected.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_Forward_PP_Order_Candidate_ID).orElse(null);
		if (ppOrderCandidateIdentifier != null)
		{
			final PPOrderCandidateId expectedPPOrderCandidateId = ppOrderCandidateIdentifier.lookupIdIn(ppOrderCandidateTable);
			final PPOrderRef ppOrderRef = actual.getForwardPPOrderRef();
			final PPOrderCandidateId actualPPOrderCandidateId = ppOrderRef != null ? PPOrderCandidateId.ofRepoIdOrNull(ppOrderRef.getPpOrderCandidateId()) : null;
			if (!PPOrderCandidateId.equals(actualPPOrderCandidateId, expectedPPOrderCandidateId))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("Forward_PP_Order_Candidate_ID not matching, expected " + expectedPPOrderCandidateId + " but found " + actualPPOrderCandidateId
						+ "\n\trow=" + expected
						+ "\n\tcandidate=" + actual);
			}
		}

		final StepDefDataIdentifier ppOrderLineCandidateIdentifier = expected.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_Forward_PP_OrderLine_Candidate_ID).orElse(null);
		if (ppOrderLineCandidateIdentifier != null)
		{
			final PPOrderLineCandidateId expectedPPOrderLineCandidateId = ppOrderLineCandidateIdentifier.lookupIdIn(ppOrderLineCandidateTable);
			final PPOrderRef ppOrderRef = actual.getForwardPPOrderRef();
			final PPOrderLineCandidateId actualPPOrderLineCandidateId = ppOrderRef != null ? PPOrderLineCandidateId.ofRepoIdOrNull(ppOrderRef.getPpOrderLineCandidateId()) : null;
			if (!PPOrderLineCandidateId.equals(actualPPOrderLineCandidateId, expectedPPOrderLineCandidateId))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("Forward_PP_OrderLine_Candidate_ID not matching, expected " + expectedPPOrderLineCandidateId + " but found " + actualPPOrderLineCandidateId
						+ "\n\trow=" + expected
						+ "\n\tcandidate=" + actual);
			}
		}

		final StepDefDataIdentifier ppOrderIdentifier = expected.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_Forward_PP_Order_ID).orElse(null);
		if (ppOrderIdentifier != null)
		{
			final PPOrderId expectedPPOrderId = ppOrderIdentifier.lookupIdIn(ppOrderTable);
			final PPOrderRef ppOrderRef = actual.getForwardPPOrderRef();
			final PPOrderId actualPPOrderId = ppOrderRef != null ? ppOrderRef.getPpOrderId() : null;
			if (!PPOrderId.equals(actualPPOrderId, expectedPPOrderId))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("Forward_PP_Order_ID not matching, expected " + expectedPPOrderId + " but found " + actualPPOrderId
						+ "\n\trow=" + expected
						+ "\n\tcandidate=" + actual);
			}
		}

		final StepDefDataIdentifier ppOrderBOMLineIdentifier = expected.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_Forward_PP_Order_BOMLine_ID).orElse(null);
		if (ppOrderBOMLineIdentifier != null)
		{
			final PPOrderBOMLineId expectedPPOrderBOMLineId = ppOrderBOMLineIdentifier.lookupIdIn(ppOrderBOMLineTable);
			final PPOrderRef ppOrderRef = actual.getForwardPPOrderRef();
			final PPOrderBOMLineId actualPPOrderBOMLineId = ppOrderRef != null ? ppOrderRef.getPpOrderBOMLineId() : null;
			if (!PPOrderBOMLineId.equals(actualPPOrderBOMLineId, expectedPPOrderBOMLineId))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("Forward_PP_Order_BOMLine_ID not matching, expected " + expectedPPOrderBOMLineId + " but found " + actualPPOrderBOMLineId
						+ "\n\trow=" + expected
						+ "\n\tcandidate=" + actual);
			}
		}

		return ItemProvider.ProviderResult.resultWasFound(actual);
	}

	private DDOrderCandidateQuery toDDOrderCandidateQuery(final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsOptionalIdentifier().orElse(null);
		if (identifier != null && ddOrderCandidateTable.isPresent(identifier))
		{
			return DDOrderCandidateQuery.builder()
					.ddOrderCandidateId(ddOrderCandidateTable.getId(identifier))
					.build();
		}

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

		final List<DDOrderCandidate> candidates = ddOrderCandidateService.list(DDOrderCandidateQuery.builder()
				.productId(productId)
				.build());

		assertThat(candidates).isEmpty();
	}

	@And("the following DD_Order_Candidates are enqueued for generating DD_Orders")
	public void enqueueDD_Order_Candidates(@NonNull final DataTable dataTable)
	{
		ddOrderCandidateService.enqueueToProcess(getDDOrderCandidateIds(dataTable));
	}

	private Set<DDOrderCandidateId> getDDOrderCandidateIds(final @NonNull DataTable table)
	{
		return DataTableRows.of(table).stream().map(this::getDDOrderCandidateId).collect(ImmutableSet.toImmutableSet());
	}

	private DDOrderCandidateId getDDOrderCandidateId(@NonNull final DataTableRow row)
	{
		return row.getAsIdentifier("DD_Order_Candidate_ID").lookupIdIn(ddOrderCandidateTable);
	}

}
