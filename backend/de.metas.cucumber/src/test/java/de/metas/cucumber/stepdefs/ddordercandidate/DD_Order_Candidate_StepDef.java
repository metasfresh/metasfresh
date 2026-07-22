package de.metas.cucumber.stepdefs.ddordercandidate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
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
import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateAlloc;
import de.metas.distribution.ddordercandidate.DDOrderCandidateAllocList;
import de.metas.distribution.ddordercandidate.DDOrderCandidateAllocRepository;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
import de.metas.distribution.ddordercandidate.DDOrderCandidateQuery;
import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.document.engine.DocStatus;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.impexp.InputDataSourceId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.PPOrderLineCandidateId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class DD_Order_Candidate_StepDef
{
	/** Fixed order/supply/demand date shared by all directly-created candidates, so they land in the same header aggregation key. */
	private static final Instant GROUND_REPLENISH_DATE = Instant.parse("2021-04-14T08:00:00Z");

	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final DDOrderCandidateService ddOrderCandidateService = SpringContextHolder.instance.getBean(DDOrderCandidateService.class);
	@NonNull private final DDOrderCandidateAllocRepository ddOrderCandidateAllocRepository = SpringContextHolder.instance.getBean(DDOrderCandidateAllocRepository.class);
	@NonNull private final DDOrderLowLevelDAO ddOrderLowLevelDAO = SpringContextHolder.instance.getBean(DDOrderLowLevelDAO.class);
	@NonNull private final DD_Order_Candidate_StepDefData ddOrderCandidateTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Locator_StepDefData locatorTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final M_Shipper_StepDefData shipperTable;
	@NonNull private final C_Order_StepDefData orderTable;
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

		final LocatorId sourceLocatorId = expected.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_LocatorFrom_ID).map(locatorTable::getId).orElse(null);
		if (sourceLocatorId != null && !LocatorId.equals(actual.getSourceLocatorId(), sourceLocatorId))
		{
			return ItemProvider.ProviderResult.resultWasNotFound("M_LocatorFrom_ID not matching, expected " + sourceLocatorId + " but found " + actual.getSourceLocatorId()
					+ "\n\trow=" + expected
					+ "\n\tcandidate=" + actual);
		}

		final LocatorId targetLocatorId = expected.getAsOptionalIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_LocatorTo_ID).map(locatorTable::getId).orElse(null);
		if (targetLocatorId != null && !LocatorId.equals(actual.getTargetLocatorId(), targetLocatorId))
		{
			return ItemProvider.ProviderResult.resultWasNotFound("M_LocatorTo_ID not matching, expected " + targetLocatorId + " but found " + actual.getTargetLocatorId()
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

	@And("^no DD_Order_Candidates found for product (.*)$")
	public void assertNoCandidatesFoundByProduct(@NonNull final String productIdentifierStr)
	{
		final ProductId productId = StepDefDataIdentifier.ofString(productIdentifierStr).lookupIdIn(productTable);

		final List<DDOrderCandidate> candidates = ddOrderCandidateService.list(DDOrderCandidateQuery.builder()
				.productId(productId)
				.build());

		assertThat(candidates).isEmpty();
	}

	/**
	 * Deletes all not-processed {@code DD_Order_Candidate} rows that belong to the given
	 * {@code AD_InputDataSource} (matched by its {@code Value}). Use this in a {@code Background} to
	 * establish a clean slate before a scenario that generates candidates from that source, so the
	 * exhaustive assertion below is not polluted by candidates left over from a sibling scenario on the
	 * same executor.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And all not-processed DD_Order_Candidates for AD_InputDataSource 'DD_AutoGroundReplenish' are deleted
	 * </pre>
	 *
	 * @param inputDataSourceValue the {@code AD_InputDataSource.Value}
	 */
	@And("all not-processed DD_Order_Candidates for AD_InputDataSource {string} are deleted")
	public void delete_not_processed_candidates_for_source(@NonNull final String inputDataSourceValue)
	{
		final InputDataSourceId inputDataSourceId = getInputDataSourceIdByValue(inputDataSourceValue);

		queryBL.createQueryBuilder(I_DD_Order_Candidate.class)
				.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_AD_InputDataSource_ID, inputDataSourceId)
				.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_Processed, false)
				.create()
				.delete();
	}

	/**
	 * Exhaustive, data-source-scoped assertion: the not-processed {@code DD_Order_Candidate}s for the given
	 * {@code AD_InputDataSource} (matched by {@code Value}) are EXACTLY the rows of the DataTable — no more, no
	 * fewer. Each expected row is matched to exactly one candidate; a leftover (extra) candidate fails the step.
	 * Column matching reuses the same logic as {@code following DD_Order_Candidates are found} (see that step's columns,
	 * e.g. {@code M_LocatorFrom_ID}, {@code M_LocatorTo_ID}, {@code M_Product_ID}, {@code Qty}, {@code Processed}, {@code IsSimulated}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_LocatorFrom_ID</b> / <b>M_LocatorTo_ID</b> — (optional, identifier-ref) source / target locator<br>
	 *   <b>M_Product_ID</b> — (optional, identifier-ref)<br>
	 *   <b>Qty</b> / <b>QtyProcessed</b> / <b>QtyToProcess</b> — (optional)<br>
	 *   <b>Processed</b> / <b>IsSimulated</b> — (optional, Y/N)<br>
	 * @cucumber.depends StepDefData: DD_Order_Candidate_StepDefData, M_Locator_StepDefData, M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the only not-processed DD_Order_Candidates for AD_InputDataSource 'DD_AutoGroundReplenish' are:
	 *   | M_LocatorFrom_ID | M_LocatorTo_ID | M_Product_ID | Qty | Processed | IsSimulated |
	 *   | reserveLoc       | groundLoc      | product      | 100 | N         | N           |
	 * </pre>
	 *
	 * @param inputDataSourceValue the {@code AD_InputDataSource.Value}
	 */
	@And("the only not-processed DD_Order_Candidates for AD_InputDataSource {string} are:")
	public void assert_only_not_processed_candidates_for_source(@NonNull final String inputDataSourceValue, @NonNull final DataTable dataTable)
	{
		final InputDataSourceId inputDataSourceId = getInputDataSourceIdByValue(inputDataSourceValue);

		final List<DDOrderCandidate> remaining = new ArrayList<>(ddOrderCandidateService.list(DDOrderCandidateQuery.builder()
				.inputDataSourceId(inputDataSourceId)
				.processed(false)
				.build()));
		SharedTestContext.put("candidates", remaining);

		DataTableRows.of(dataTable).forEach(row -> {
			final DDOrderCandidate match = remaining.stream()
					.filter(candidate -> validateDDOrderLineCandidate(row, candidate).isResultFound())
					.findFirst()
					.orElse(null);

			assertThat(match)
					.as("No not-processed DD_Order_Candidate for AD_InputDataSource '%s' matches expected row %s\n\tunmatched candidates=%s",
							inputDataSourceValue, row, remaining)
					.isNotNull();

			remaining.remove(match);
			row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderCandidateTable.putOrReplace(identifier, match));
		});

		assertThat(remaining)
				.as("Unexpected extra not-processed DD_Order_Candidate(s) for AD_InputDataSource '%s'", inputDataSourceValue)
				.isEmpty();
	}

	private InputDataSourceId getInputDataSourceIdByValue(@NonNull final String value)
	{
		final I_AD_InputDataSource dataSource = queryBL.createQueryBuilder(I_AD_InputDataSource.class)
				.addEqualsFilter(I_AD_InputDataSource.COLUMNNAME_Value, value)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyNotNull(I_AD_InputDataSource.class);

		return InputDataSourceId.ofRepoId(dataSource.getAD_InputDataSource_ID());
	}

	/**
	 * Directly creates planning-less {@link DDOrderCandidate}s (no product planning, no distribution network,
	 * no sales order) and stores each by its {@code Identifier}.
	 *
	 * <p>Stands in for the {@code DD_AutoGroundReplenish} ground-replenishment source, which is the real-world
	 * trigger that produces distribution-order candidates carrying only a source/target warehouse, a shipper and
	 * a quantity. That source is a scheduled batch driven by live warehouse stock levels and cannot be reproduced
	 * from within a cucumber scenario, so the candidates are created directly here.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>M_Product_ID</b> — (required, identifier-ref) product to replenish<br>
	 *   <b>M_Warehouse_From_ID</b> — (required, identifier-ref) source warehouse<br>
	 *   <b>M_WarehouseTo_ID</b> — (required, identifier-ref) target warehouse<br>
	 *   <b>M_Shipper_ID</b> — (required, identifier-ref) shipper<br>
	 *   <b>Qty</b> — (required) quantity with UOM, e.g. "2 PCE"<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData, M_Warehouse_StepDefData, M_Shipper_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains DD_Order_Candidates:
	 *   | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | M_Shipper_ID | Qty   |
	 *   | cand_p1    | p_1          | sourceWH            | targetWH         | shipper      | 2 PCE |
	 * </pre>
	 */
	@And("metasfresh contains DD_Order_Candidates:")
	public void metasfresh_contains_DD_Order_Candidates(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createDDOrderCandidate);
	}

	private void createDDOrderCandidate(@NonNull final DataTableRow row)
	{
		final ProductId productId = row.getAsIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_Product_ID).lookupIdIn(productTable);
		final WarehouseId sourceWarehouseId = row.getAsIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_Warehouse_From_ID).lookupIdIn(warehouseTable);
		final WarehouseId targetWarehouseId = row.getAsIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_WarehouseTo_ID).lookupIdIn(warehouseTable);
		final ShipperId shipperId = row.getAsIdentifier(I_DD_Order_Candidate.COLUMNNAME_M_Shipper_ID).lookupIdIn(shipperTable);
		final Quantity qtyEntered = row.getAsQuantity("Qty", null, uomDAO::getByX12DE355);

		final DDOrderCandidate candidate = DDOrderCandidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(Env.getClientId(), Env.getOrgId()))
				.dateOrdered(GROUND_REPLENISH_DATE)
				.supplyDate(GROUND_REPLENISH_DATE)
				.demandDate(GROUND_REPLENISH_DATE)
				.productId(productId)
				.qtyEntered(qtyEntered)
				.sourceWarehouseId(sourceWarehouseId)
				.targetWarehouseId(targetWarehouseId)
				.shipperId(shipperId)
				// planning-less: no productPlanningId, distributionNetworkAndLineId, salesOrderLineId or forwardPPOrderRef
				.build();

		ddOrderCandidateService.save(candidate);

		row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderCandidateTable.putOrReplace(identifier, candidate));
	}

	@And("the following DD_Order_Candidates are enqueued for generating DD_Orders")
	public void enqueueDD_Order_Candidates(@NonNull final DataTable dataTable)
	{
		ddOrderCandidateService.enqueueToProcess(getDDOrderCandidateIds(dataTable));
	}

	/**
	 * Asserts how the enqueued {@link DDOrderCandidate}s were aggregated into generated {@code DD_Order}s, using the
	 * real {@code DD_Order_Candidate_DDOrder} allocation link the processing writes back onto each candidate.
	 *
	 * <p>For each row the candidate's allocation is resolved (polling until processing completed) and its generated
	 * {@code DD_Order} is grouped under the {@code DD_Order} label: rows sharing a label must resolve to the SAME
	 * {@code DD_Order}, rows with different labels to DIFFERENT ones. Each generated {@code DD_Order} is then asserted
	 * to be Completed and to hold exactly as many {@code DD_OrderLine}s as candidates grouped under its label. This is
	 * the assertion that proves the {@code DDOrderAggregation.header.byProductId} sysconfig both ways.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>DD_Order_Candidate_ID</b> — (required, identifier-ref) a candidate previously enqueued<br>
	 *   <b>DD_Order</b> — (required) grouping label; same label ⇒ same generated DD_Order, different label ⇒ different one<br>
	 * @cucumber.depends StepDefData: DD_Order_Candidate_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 30s, the DD_Order_Candidates are aggregated into DD_Orders:
	 *   | DD_Order_Candidate_ID | DD_Order |
	 *   | cand_p1               | ddo      |
	 *   | cand_p2               | ddo      |
	 * </pre>
	 */
	@And("^after not more than (.*)s, the DD_Order_Candidates are aggregated into DD_Orders:$")
	public void validateDDOrderCandidateAggregation(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<DataTableRow> rows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());

		final Map<StepDefDataIdentifier, List<DDOrderCandidateAlloc>> allocsByCandidate = StepDefUtil.<Map<StepDefDataIdentifier, List<DDOrderCandidateAlloc>>>tryAndWaitForItem()
				.worker(() -> resolveCandidateAllocations(rows))
				.maxWaitSeconds(timeoutSec)
				.execute();

		assertAggregation(rows, allocsByCandidate);
	}

	private ItemProvider.ProviderResult<Map<StepDefDataIdentifier, List<DDOrderCandidateAlloc>>> resolveCandidateAllocations(@NonNull final List<DataTableRow> rows)
	{
		final LinkedHashMap<StepDefDataIdentifier, List<DDOrderCandidateAlloc>> result = new LinkedHashMap<>();
		for (final DataTableRow row : rows)
		{
			final StepDefDataIdentifier candidateIdentifier = row.getAsIdentifier("DD_Order_Candidate_ID");
			final DDOrderCandidateId candidateId = candidateIdentifier.lookupIdIn(ddOrderCandidateTable);

			final DDOrderCandidateAllocList allocList = ddOrderCandidateAllocRepository.getByCandidateIds(ImmutableSet.of(candidateId));
			final List<DDOrderCandidateAlloc> allocs = ImmutableList.copyOf(allocList);
			if (allocs.isEmpty())
			{
				return ItemProvider.ProviderResult.resultWasNotFound("No generated DD_Order yet for candidate " + candidateIdentifier);
			}

			result.put(candidateIdentifier, allocs);
		}

		return ItemProvider.ProviderResult.resultWasFound(result);
	}

	private void assertAggregation(
			@NonNull final List<DataTableRow> rows,
			@NonNull final Map<StepDefDataIdentifier, List<DDOrderCandidateAlloc>> allocsByCandidate)
	{
		final LinkedHashMap<String, DDOrderId> ddOrderIdByLabel = new LinkedHashMap<>();
		final List<DDOrderId> ddOrderIdByCandidate = new ArrayList<>();

		for (final DataTableRow row : rows)
		{
			final StepDefDataIdentifier candidateIdentifier = row.getAsIdentifier("DD_Order_Candidate_ID");
			final String label = row.getAsString("DD_Order");

			final List<DDOrderCandidateAlloc> allocs = allocsByCandidate.get(candidateIdentifier);
			assertThat(allocs)
					.as("candidate %s must be allocated to exactly one DD_OrderLine", candidateIdentifier)
					.hasSize(1);

			final DDOrderId ddOrderId = allocs.get(0).getDdOrderId();
			ddOrderIdByCandidate.add(ddOrderId);

			final DDOrderId existing = ddOrderIdByLabel.get(label);
			if (existing == null)
			{
				ddOrderIdByLabel.put(label, ddOrderId);
			}
			else
			{
				assertThat(ddOrderId)
						.as("candidates grouped under DD_Order '%s' must share the same generated DD_Order", label)
						.isEqualTo(existing);
			}
		}

		assertThat(ImmutableSet.copyOf(ddOrderIdByLabel.values()))
				.as("each distinct DD_Order label must map to a distinct generated DD_Order")
				.hasSize(ddOrderIdByLabel.size());

		final Map<DDOrderId, Long> candidatesPerDDOrder = ddOrderIdByCandidate.stream()
				.collect(Collectors.groupingBy(id -> id, Collectors.counting()));

		candidatesPerDDOrder.forEach((ddOrderId, candidateCount) -> {
			final I_DD_Order ddOrder = ddOrderLowLevelDAO.getById(ddOrderId);
			final List<I_DD_OrderLine> lines = ddOrderLowLevelDAO.retrieveLines(ddOrder);
			assertThat(lines)
					.as("generated DD_Order %s must have one DD_OrderLine per aggregated candidate", ddOrderId)
					.hasSize(candidateCount.intValue());

			assertThat(DocStatus.ofNullableCodeOrUnknown(ddOrder.getDocStatus()))
					.as("generated DD_Order %s DocStatus", ddOrderId)
					.isEqualTo(DocStatus.Completed);
		});
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
