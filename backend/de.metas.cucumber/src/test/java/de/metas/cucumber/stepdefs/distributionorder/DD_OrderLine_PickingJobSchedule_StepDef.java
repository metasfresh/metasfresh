/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.distributionorder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.picking.M_Picking_Job_Schedule_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.replenishment.alloc.DDOrderLineContributor;
import de.metas.distribution.ddorder.replenishment.alloc.DDOrderLineContributorRepository;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.model.I_DD_OrderLine_PickingJobSchedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.groups.Tuple;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Step definitions for {@code DD_OrderLine_PickingJobSchedule} — which workstation assignments a consolidated
 * {@code DD_OrderLine} serves, and with how much each.
 *
 * <p>Covers the product-group view of the picking replenishment: the single live DD_Order of a product group, the
 * complete contributor set of one of its lines, and the contributor → DD_Order navigation. All of these are
 * group-level concepts that no single assignment owns, which is why they live here rather than on
 * {@code DD_Order_StepDef} (whose steps are keyed on one owning {@code M_Picking_Job_Schedule}).</p>
 */
@RequiredArgsConstructor
public class DD_OrderLine_PickingJobSchedule_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final DDOrderLineContributorRepository contributorRepository = SpringContextHolder.instance.getBean(DDOrderLineContributorRepository.class);
	@NonNull private final DDOrderService ddOrderService = SpringContextHolder.instance.getBean(DDOrderService.class);

	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Locator_StepDefData locatorTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final DD_Order_StepDefData ddOrderTable;
	@NonNull private final DD_OrderLine_StepDefData ddOrderLineTable;
	@NonNull private final M_Picking_Job_Schedule_StepDefData pickingJobScheduleTable;

	/**
	 * @cucumber.stepdef Polls until EXACTLY ONE live (DocStatus != Voided) DD_Order exists for the given product
	 * group and validates its header plus its single line.
	 * <p>
	 * The group is resolved through the line's own {@code M_Product_ID} + {@code M_LocatorTo_ID}, never through an
	 * owning {@code M_Picking_Job_Schedule}: an order that serves several assignments has no single owner to key on.
	 * <p>
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the group's product<br>
	 *   <b>M_LocatorTo_ID</b> — (required, identifier-ref) the group's target locator, i.e. the workstation's
	 *     pick-from locator<br>
	 *   <b>DD_Order_ID</b> — (optional) stores the found DD_Order under this identifier<br>
	 *   <b>DD_OrderLine_ID</b> — (optional) stores the found DD_Order's single line under this identifier<br>
	 *   <b>DocStatus</b> — (optional) expected header doc status (e.g. {@code CO})<br>
	 *   <b>M_Warehouse_From_ID</b> — (optional, identifier-ref) expected header source warehouse<br>
	 *   <b>QtyEntered</b> — (optional) expected line quantity, i.e. the group's summed demand<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData, M_Locator_StepDefData, M_Warehouse_StepDefData,
	 * DD_Order_StepDefData, DD_OrderLine_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 120s, exactly one live DD_Order exists for the product group:
	 *   | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
	 *   | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
	 * </pre>
	 */
	@Then("^after not more than (.*)s, exactly one live DD_Order exists for the product group:$")
	public void assertExactlyOneLiveDDOrderForProductGroup(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> assertExactlyOneLiveDDOrderForProductGroup(timeoutSec, row));
	}

	private void assertExactlyOneLiveDDOrderForProductGroup(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final ProductId productId = row.getAsIdentifier(I_DD_OrderLine.COLUMNNAME_M_Product_ID).lookupNotNullIdIn(productTable);
		final LocatorId locatorToId = row.getAsIdentifier(I_DD_OrderLine.COLUMNNAME_M_LocatorTo_ID).lookupNotNullIdIn(locatorTable);

		// The whole group picture is polled (order count AND the line quantity), not just the count: an in-flight
		// reconcile can transiently show one order whose quantity is not yet the group's sum, and binding on the
		// count alone would grab that stale record.
		final List<I_DD_Order> ddOrders = StepDefUtil.<List<I_DD_Order>>tryAndWaitForData(() -> liveDDOrdersOfProductGroup(productId, locatorToId))
				.validateUsingConsumer(liveDDOrders -> validateSingleDDOrderOfProductGroup(liveDDOrders, row))
				.maxWaitSeconds(timeoutSec)
				.checkingIntervalMs(1000L)
				.execute();

		final I_DD_Order ddOrder = ddOrders.get(0);
		row.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_DD_Order_ID)
				.ifPresent(identifier -> ddOrderTable.putOrReplace(identifier, ddOrder));
		row.getAsOptionalIdentifier(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID)
				.ifPresent(identifier -> ddOrderLineTable.putOrReplace(identifier, singleLineOf(ddOrder)));
	}

	private void validateSingleDDOrderOfProductGroup(@NonNull final List<I_DD_Order> liveDDOrders, @NonNull final DataTableRow expected)
	{
		// The group's demand is served by ONE order — not one per contributing assignment.
		assertThat(liveDDOrders)
				.as("live (DocStatus != Voided) DD_Orders of the product group")
				.hasSize(1);

		final I_DD_Order ddOrder = liveDDOrders.get(0);
		final I_DD_OrderLine line = singleLineOf(ddOrder);
		final SoftAssertions softly = new SoftAssertions();

		expected.getAsOptionalEnum(I_DD_Order.COLUMNNAME_DocStatus, DocStatus.class)
				.ifPresent(expectedDocStatus -> softly.assertThat(DocStatus.ofNullableCodeOrUnknown(ddOrder.getDocStatus()))
						.as("DD_Order.DocStatus")
						.isEqualTo(expectedDocStatus));

		expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_M_Warehouse_From_ID)
				.ifPresent(identifier -> softly.assertThat(WarehouseId.ofRepoIdOrNull(ddOrder.getM_Warehouse_From_ID()))
						.as("DD_Order.M_Warehouse_From_ID")
						.isEqualTo(identifier.lookupNotNullIdIn(warehouseTable)));

		expected.getAsOptionalBigDecimal(I_DD_OrderLine.COLUMNNAME_QtyEntered)
				.ifPresent(expectedQtyEntered -> softly.assertThat(line.getQtyEntered())
						.as("DD_OrderLine.QtyEntered (the group's summed demand)")
						.isEqualByComparingTo(expectedQtyEntered));

		softly.assertAll();
	}

	/**
	 * The live (DocStatus != Voided) DD_Orders serving the given product group, ordered by id so the result is
	 * stable across polls.
	 * <p>
	 * Deliberately NOT expressed through {@code DDOrderService.streamDDOrders(DDOrderQuery)}, even though that query
	 * has both a {@code productIds} and a {@code locatorToIds} field: it applies them as TWO INDEPENDENT
	 * {@code DD_Order_ID IN (…)} sub-queries over {@code DD_OrderLine} ({@code DDOrderLowLevelDAO.toSqlQuery}, the
	 * "Locator To" block and the "Line level filters" block), so it matches an order that has ONE line with the
	 * product and ANOTHER line to the target locator. The group predicate needs a SINGLE line carrying both, which
	 * is what the sub-query below expresses. Its {@code docStatus} field is likewise a single-value equals and
	 * cannot say "not Voided". Fold this into the domain query only once {@code DDOrderQuery} can state a combined
	 * line-level predicate — replacing it as-is would silently widen the group.
	 */
	private List<I_DD_Order> liveDDOrdersOfProductGroup(@NonNull final ProductId productId, @NonNull final LocatorId locatorToId)
	{
		final IQuery<I_DD_OrderLine> groupLinesQuery = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_M_LocatorTo_ID, locatorToId.getRepoId())
				.create();

		return queryBL.createQueryBuilder(I_DD_Order.class)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
				.addInSubQueryFilter(I_DD_Order.COLUMNNAME_DD_Order_ID, I_DD_OrderLine.COLUMNNAME_DD_Order_ID, groupLinesQuery)
				.orderBy(I_DD_Order.COLUMNNAME_DD_Order_ID)
				.create()
				.list(I_DD_Order.class);
	}

	private I_DD_OrderLine singleLineOf(@NonNull final I_DD_Order ddOrder)
	{
		final List<I_DD_OrderLine> lines = ddOrderService.retrieveLines(ddOrder);

		// One consolidated line carries the whole group's demand for one source locator; more than one line would
		// mean the demand was split per contributor again, which is what the aggregation removes.
		assertThat(lines)
				.as("lines of DD_Order_ID=%s", ddOrder.getDD_Order_ID())
				.hasSize(1);

		return lines.get(0);
	}

	/**
	 * @cucumber.stepdef Asserts the COMPLETE {@code DD_OrderLine_PickingJobSchedule} contributor set of the given
	 * line(s) — every contributing workstation assignment with its own quantity, and no other row.
	 * <p>
	 * The assertion is exact-set: a contributor silently dropped from a consolidated line (its demand then has no
	 * document at all) and a contributor invented for a line it does not feed both fail it.
	 * <p>
	 * Rows are grouped by {@code DD_OrderLine_ID}, so one invocation can assert several lines of a multi-line group.
	 * <p>
	 * @cucumber.columns
	 *   <b>DD_OrderLine_ID</b> — (required, identifier-ref) the consolidated line whose contributors are asserted<br>
	 *   <b>M_Picking_Job_Schedule_ID</b> — (required, identifier-ref) the contributing workstation assignment<br>
	 *   <b>Qty</b> — (required) that contributor's share of the line's quantity<br>
	 * @cucumber.depends StepDefData: DD_OrderLine_StepDefData, M_Picking_Job_Schedule_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the DD_OrderLine contributors are found:
	 *   | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
	 *   | groupDDOrderLine | jobScheduleA              | 10  |
	 *   | groupDDOrderLine | jobScheduleB              | 5   |
	 * </pre>
	 */
	@Then("^the DD_OrderLine contributors are found:$")
	public void assertDDOrderLineContributors(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.groupBy(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID)
				.forEach((lineIdentifier, rows) -> {
					final DDOrderLineId lineId = rows.getFirstRow()
							.getAsIdentifier(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID)
							.lookupNotNullIdIn(ddOrderLineTable);

					final ImmutableList<Tuple> expectedContributors = rows.stream()
							.map(row -> tuple(
									row.getAsIdentifier(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID).lookupNotNullIdIn(pickingJobScheduleTable),
									asComparableQty(row.getAsBigDecimal(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_Qty))))
							.collect(ImmutableList.toImmutableList());

					assertThat(contributorRepository.getContributors(lineId))
							.as("complete DD_OrderLine_PickingJobSchedule contributor set of DD_OrderLine %s (DD_OrderLine_ID=%s)",
									lineIdentifier, lineId.getRepoId())
							.extracting(
									DDOrderLineContributor::getPickingJobScheduleId,
									contributor -> asComparableQty(contributor.getQty().toBigDecimal()))
							.containsExactlyInAnyOrderElementsOf(expectedContributors);
				});
	}

	/**
	 * Renders a quantity so that two {@code BigDecimal}s differing only in scale compare equal inside an AssertJ
	 * {@code extracting(...)} tuple (which uses {@code equals}, and {@code BigDecimal.equals} is scale-sensitive:
	 * the DB returns {@code 10.000000} where the DataTable says {@code 10}).
	 */
	private static String asComparableQty(@NonNull final BigDecimal qty)
	{
		return qty.stripTrailingZeros().toPlainString();
	}

	/**
	 * @cucumber.stepdef Asserts that each of the given workstation assignments is served by EXACTLY the given
	 * DD_Order — resolved through its {@code DD_OrderLine_PickingJobSchedule} rows, i.e. the navigation that
	 * replaces the single-owner foreign key.
	 * <p>
	 * Both directions of the settlement are covered: an assignment that resolves to no order was left dangling, and
	 * one that resolves to a second order is served twice.
	 * <p>
	 * Params: a comma-separated list of {@code M_Picking_Job_Schedule} identifiers, and the DD_Order identifier all
	 * of them must resolve to.
	 * @cucumber.depends StepDefData: M_Picking_Job_Schedule_StepDefData, DD_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And each of jobScheduleA, jobScheduleB resolves to the DD_Order identified by groupDDOrder
	 * </pre>
	 */
	@Then("^each of (.*) resolves to the DD_Order identified by (.*)$")
	public void assertContributorsResolveToDDOrder(
			@NonNull final String pickingJobScheduleIdentifiers,
			@NonNull final String ddOrderIdentifier)
	{
		final DDOrderId expectedDDOrderId = DDOrderId.ofRepoId(ddOrderTable.get(ddOrderIdentifier).getDD_Order_ID());

		for (final StepDefDataIdentifier identifier : StepDefUtil.extractIdentifiers(pickingJobScheduleIdentifiers))
		{
			final PickingJobScheduleId jobScheduleId = pickingJobScheduleTable.getId(identifier);

			final ImmutableSet<DDOrderId> ddOrderIds = contributorRepository.getLineIdsByPickingJobScheduleId(jobScheduleId)
					.stream()
					.map(lineId -> DDOrderId.ofRepoId(ddOrderService.getLineById(lineId).getDD_Order_ID()))
					.collect(ImmutableSet.toImmutableSet());

			assertThat(ddOrderIds)
					.as("DD_Orders reachable from M_Picking_Job_Schedule %s (M_Picking_Job_Schedule_ID=%s) through its contributor rows",
							identifier, jobScheduleId.getRepoId())
					.containsExactly(expectedDDOrderId);
		}
	}

	/**
	 * @cucumber.stepdef Voids every live (DocStatus != Voided) DD_Order of the given product group, outside the
	 * reconcile flow, and asserts none is left.
	 * <p>
	 * Real-world trigger: the drift the {@code DD_Order_Picking_Rebuild} watchdog exists for — a reconcile event
	 * lost between commit and publish, a node restart, or an order voided by hand. The group-scoped form is needed
	 * because a consolidated order belongs to no single shipment schedule, so the per-schedule
	 * {@code the DD_Order linked to M_ShipmentSchedule <x> is voided directly} cannot address it.
	 * <p>
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the group's product<br>
	 *   <b>M_LocatorTo_ID</b> — (required, identifier-ref) the group's target locator<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData, M_Locator_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When every live DD_Order for the product group is voided directly:
	 *   | M_Product_ID | M_LocatorTo_ID |
	 *   | product      | packingLocator |
	 * </pre>
	 */
	@When("^every live DD_Order for the product group is voided directly:$")
	public void voidEveryLiveDDOrderOfProductGroup(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final ProductId productId = row.getAsIdentifier(I_DD_OrderLine.COLUMNNAME_M_Product_ID).lookupNotNullIdIn(productTable);
			final LocatorId locatorToId = row.getAsIdentifier(I_DD_OrderLine.COLUMNNAME_M_LocatorTo_ID).lookupNotNullIdIn(locatorTable);

			final List<I_DD_Order> liveDDOrders = liveDDOrdersOfProductGroup(productId, locatorToId);

			// Voiding nothing would leave the group served, and the rebuild that follows would then be asserted
			// against the orders the interceptor had already created instead of the ones the rebuild produces.
			assertThat(liveDDOrders)
					.as("live DD_Orders of the product group (must exist before the drift can be simulated)")
					.isNotEmpty();

			liveDDOrders.forEach(ddOrder -> ddOrderService.voidIt(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID())));

			assertThat(liveDDOrdersOfProductGroup(productId, locatorToId))
					.as("live DD_Orders of the product group after voiding them all")
					.isEmpty();
		});
	}
}
