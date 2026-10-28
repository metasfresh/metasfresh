package de.metas.cucumber.stepdefs.ddordercandidate;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
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
import de.metas.cucumber.stepdefs.rabbitMQ.RabbitMQ_StepDef;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
import de.metas.distribution.ddordercandidate.DDOrderCandidateQuery;
import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
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

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class DD_Order_Candidate_StepDef
{
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final DDOrderCandidateService ddOrderCandidateService = SpringContextHolder.instance.getBean(DDOrderCandidateService.class);
	@NonNull private final DD_Order_Candidate_StepDefData ddOrderCandidateTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final PP_Order_Candidate_StepDefData ppOrderCandidateTable;
	@NonNull private final PP_OrderLine_Candidate_StepDefData ppOrderLineCandidateTable;
	@NonNull private final PP_Order_StepDefData ppOrderTable;
	@NonNull private final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable;
	@NonNull private final RabbitMQ_StepDef rabbitMQStepDef;

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
			return attemptMatchByQty(candidates, row);
		}

		return validateDDOrderLineCandidate(row, candidates.get(0));
	}

	private ItemProvider.ProviderResult<DDOrderCandidate> attemptMatchByQty(@NonNull final List<DDOrderCandidate> candidates, @NonNull final DataTableRow row)
	{
		final Quantity qtyEntered = row.getAsOptionalQuantity("Qty", uomDAO::getByX12DE355).orElse(null);

		final List<DDOrderCandidate> filteredByQtyList = candidates.stream()
				.filter(candidate -> qtyEntered != null && qtyEntered.compareTo(candidate.getQtyEntered()) == 0)
				.collect(Collectors.toList());

		if (filteredByQtyList.size() != 1)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("More than one candidate found"
					+ "\n\trow=" + row
					+ "\n\tcandidates=" + candidates);
		}
		return validateDDOrderLineCandidate(row, filteredByQtyList.get(0));
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
		if (qtyEntered != null && qtyEntered.compareTo(actual.getQtyEntered()) != 0)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("qty not matching, expected " + qtyEntered + " but found " + actual.getQtyEntered()
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

		final StepDefDataIdentifier salesOrderIdentifier = expected.getAsOptionalIdentifier("C_OrderSO_ID").orElse(null);
		if (salesOrderIdentifier != null)
		{
			final OrderId salesOrderId = salesOrderIdentifier.lookupIdIn(orderTable);
			final OrderAndLineId actualSalesOrderAndLineId = actual.getSalesOrderLineId();
			final OrderId actualSalesOrderId = actualSalesOrderAndLineId != null ? actualSalesOrderAndLineId.getOrderId() : null;
			if (!OrderId.equals(salesOrderId, actualSalesOrderId))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("C_OrderSO_ID not matching, expected " + salesOrderId + " but found " + actualSalesOrderId
						+ "\n\trow=" + expected
						+ "\n\tcandidate=" + actual);
			}
		}

		final StepDefDataIdentifier salesOrderLineIdentifier = expected.getAsOptionalIdentifier("C_OrderLineSO_ID").orElse(null);
		if (salesOrderLineIdentifier != null)
		{
			final OrderLineId salesOrderLineId = salesOrderLineIdentifier.lookupIdIn(orderLineTable);
			final OrderAndLineId actualSalesOrderAndLineId = actual.getSalesOrderLineId();
			final OrderLineId actualSalesOrderLineId = actualSalesOrderAndLineId != null ? actualSalesOrderAndLineId.getOrderLineId() : null;
			if (!OrderLineId.equals(salesOrderLineId, actualSalesOrderLineId))
			{
				return ItemProvider.ProviderResult.resultWasNotFound("C_OrderLineSO_ID not matching, expected " + salesOrderLineId + " but found " + actualSalesOrderLineId
						+ "\n\trow=" + expected
						+ "\n\tcandidate=" + actual);
			}
		}

		final StepDefDataIdentifier ppOrderCandidateIdentifier = expected.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_Forward_PP_Order_Candidate_ID).orElse(null);
		if (ppOrderCandidateIdentifier != null)
		{
			final PPOrderCandidateId expectedPPOrderCandidateId = ppOrderCandidateIdentifier.lookupIdIn(ppOrderCandidateTable);
			final PPOrderRef ppOrderRef = actual.getForwardPPOrderRef();
			final PPOrderCandidateId actualPPOrderCandidateId = ppOrderRef != null ? ppOrderRef.getPpOrderCandidateId() : null;
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

	/**
	 * Exact-set ("has only") variant of {@link #validateDDOrderLineCandidates(int, DataTable)}: after the async chain settles,
	 * the ONLY DD_Order_Candidate records for the product(s) referenced in the table are exactly the listed rows - no more, no fewer.
	 * Each row must match a distinct record: candidates already matched by earlier rows are excluded,
	 * so that two identical candidates are asserted one-to-one.
	 * Rows are matched in table order, so a more specific row (more optional columns) must come before a less specific one.
	 * Drains the rabbitMQ queues first, so that the candidate set is settled before it is compared.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (optional) stores the matched candidate; if already known, the row is matched to that candidate<br>
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the distributed product<br>
	 *   <b>M_Warehouse_From_ID</b> — (required, identifier-ref) the source warehouse<br>
	 *   <b>M_WarehouseTo_ID</b> — (required, identifier-ref) the target warehouse<br>
	 *   <b>Qty</b> — (optional) qty entered with UOM, e.g. "10 PCE"<br>
	 *   <b>QtyProcessed</b>, <b>QtyToProcess</b> — (optional) with UOM<br>
	 *   <b>Processed</b>, <b>IsSimulated</b> — (optional) Y/N<br>
	 *   <b>C_OrderSO_ID</b>, <b>C_OrderLineSO_ID</b> — (optional, identifier-ref) the sales order / line<br>
	 *   <b>Forward_PP_Order_Candidate_ID</b>, <b>Forward_PP_OrderLine_Candidate_ID</b> — (optional, identifier-ref)<br>
	 *   <b>Forward_PP_Order_ID</b>, <b>Forward_PP_Order_BOMLine_ID</b> — (optional, identifier-ref) the supplied PP_Order / BOM line<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData, M_Warehouse_StepDefData, PP_Order_StepDefData, PP_Order_BOMLine_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s, the DD_Order_Candidate table has only the following records
	 *   | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
	 *   | ddoc1      | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrder             | ppOrderBOMLine              |
	 *   | ddoc2      | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrder             | ppOrderBOMLine              |
	 * </pre>
	 */
	@And("^after not more than (.*)s, the DD_Order_Candidate table has only the following records$")
	public void validateDDOrderCandidates_hasOnly(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		rabbitMQStepDef.wait_empty_all_queues();

		final DataTableRows rows = DataTableRows.of(dataTable);

		final Set<DDOrderCandidateId> alreadyMatchedIds = new HashSet<>();
		rows.forEach(row -> {
			final DDOrderCandidate candidate = StepDefUtil.<DDOrderCandidate>tryAndWaitForItem()
					.worker(() -> findFirstMatchingCandidate(row, alreadyMatchedIds))
					.maxWaitSeconds(timeoutSec)
					.execute();
			alreadyMatchedIds.add(candidate.getIdNotNull());
			row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderCandidateTable.putOrReplace(identifier, candidate));
		});

		final ImmutableSet<ProductId> productIds = rows.stream()
				.map(row -> row.getAsIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_Product_ID).lookupIdIn(productTable))
				.collect(ImmutableSet.toImmutableSet());
		final int expectedCount = rows.toList().size();

		StepDefUtil.<Boolean>tryAndWaitForItem()
				.worker(() -> {
					final List<DDOrderCandidate> actual = productIds.stream()
							.flatMap(productId -> ddOrderCandidateService.list(DDOrderCandidateQuery.builder().productId(productId).build()).stream())
							.collect(Collectors.toList());
					if (actual.size() != expectedCount)
					{
						return ItemProvider.ProviderResult.resultWasNotFound("Expected " + expectedCount + " DD_Order_Candidate(s) but found " + actual.size()
								+ " for products " + productIds
								+ "\n\tcandidates=" + actual);
					}
					return ItemProvider.ProviderResult.resultWasFound(true);
				})
				.maxWaitSeconds(timeoutSec)
				.execute();
	}

	private ItemProvider.ProviderResult<DDOrderCandidate> findFirstMatchingCandidate(@NonNull final DataTableRow row, @NonNull final Set<DDOrderCandidateId> excludeIds)
	{
		final List<DDOrderCandidate> candidates = ddOrderCandidateService.list(toDDOrderCandidateQuery(row))
				.stream()
				.filter(candidate -> !excludeIds.contains(candidate.getIdNotNull()))
				.collect(Collectors.toList());

		for (final DDOrderCandidate candidate : candidates)
		{
			final ItemProvider.ProviderResult<DDOrderCandidate> result = validateDDOrderLineCandidate(row, candidate);
			if (result.isResultFound())
			{
				return result;
			}
		}

		return ItemProvider.ProviderResult.resultWasNotFound("No not yet matched candidate found"
				+ "\n\trow=" + row
				+ "\n\tcandidates=" + candidates);
	}

	/**
	 * Waits until at least one DD_Order_Candidate exists for the product and every one of them supplies the given PP_Order_BOMLine.
	 * Unlike the "has only" step it does not pin the candidates' count.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_Product_StepDefData, PP_Order_BOMLine_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s, all DD_Order_Candidates of product component supply PP_Order_BOMLine newBOMLine
	 * </pre>
	 */
	@And("^after not more than (.*)s, all DD_Order_Candidates of product (\\S+) supply PP_Order_BOMLine (\\S+)$")
	public void assertAllCandidatesSupplyBOMLine(final int timeoutSec, @NonNull final String productIdentifierStr, @NonNull final String bomLineIdentifierStr) throws InterruptedException
	{
		assertAllCandidatesSupplyBOMLine(timeoutSec, productIdentifierStr, bomLineIdentifierStr, null);
	}

	/**
	 * Same as {@code all DD_Order_Candidates of product X supply PP_Order_BOMLine Y}, and additionally the candidates' qtys must sum up to the given qty (count stays free).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_Product_StepDefData, PP_Order_BOMLine_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s, all DD_Order_Candidates of product component totalling 20 supply PP_Order_BOMLine newBOMLine
	 * </pre>
	 */
	@And("^after not more than (.*)s, all DD_Order_Candidates of product (\\S+) totalling (\\S+) supply PP_Order_BOMLine (\\S+)$")
	public void assertAllCandidatesTotallingQtySupplyBOMLine(final int timeoutSec, @NonNull final String productIdentifierStr, @NonNull final String expectedTotalQtyStr, @NonNull final String bomLineIdentifierStr) throws InterruptedException
	{
		assertAllCandidatesSupplyBOMLine(timeoutSec, productIdentifierStr, bomLineIdentifierStr, new BigDecimal(expectedTotalQtyStr));
	}

	private void assertAllCandidatesSupplyBOMLine(final int timeoutSec, @NonNull final String productIdentifierStr, @NonNull final String bomLineIdentifierStr, @Nullable final BigDecimal expectedTotalQty) throws InterruptedException
	{
		final ProductId productId = StepDefDataIdentifier.ofString(productIdentifierStr).lookupIdIn(productTable);
		final PPOrderBOMLineId expectedBOMLineId = StepDefDataIdentifier.ofString(bomLineIdentifierStr).lookupIdIn(ppOrderBOMLineTable);

		StepDefUtil.<Boolean>tryAndWaitForItem()
				.worker(() -> {
					final List<DDOrderCandidate> candidates = ddOrderCandidateService.list(DDOrderCandidateQuery.builder().productId(productId).build());
					if (candidates.isEmpty())
					{
						return ItemProvider.ProviderResult.resultWasNotFound("No DD_Order_Candidate found for product " + productIdentifierStr);
					}
					final List<DDOrderCandidate> notSupplyingBOMLine = candidates.stream()
							.filter(candidate -> candidate.getForwardPPOrderRef() == null
									|| !PPOrderBOMLineId.equals(candidate.getForwardPPOrderRef().getPpOrderBOMLineId(), expectedBOMLineId))
							.collect(Collectors.toList());
					if (!notSupplyingBOMLine.isEmpty())
					{
						return ItemProvider.ProviderResult.resultWasNotFound("DD_Order_Candidate(s) not supplying the expected PP_Order_BOMLine " + expectedBOMLineId
								+ "\n\tcandidates=" + notSupplyingBOMLine);
					}
					if (expectedTotalQty != null)
					{
						final BigDecimal actualTotalQty = candidates.stream()
								.map(candidate -> candidate.getQtyEntered().toBigDecimal())
								.reduce(BigDecimal.ZERO, BigDecimal::add);
						if (actualTotalQty.compareTo(expectedTotalQty) != 0)
						{
							return ItemProvider.ProviderResult.resultWasNotFound("Expected the DD_Order_Candidates to total " + expectedTotalQty + " but they total " + actualTotalQty
									+ "\n\tcandidates=" + candidates);
						}
					}
					return ItemProvider.ProviderResult.resultWasFound(true);
				})
				.maxWaitSeconds(timeoutSec)
				.execute();
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
