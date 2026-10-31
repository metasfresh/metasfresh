package de.metas.frontend_testing.expectations;

import com.google.common.base.Stopwatch;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostElementId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_Product_Acct;
import org.eevolution.api.CostCollectorType;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import de.metas.frontend_testing.expectations.request.JsonManufacturingExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderId;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.fail;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

@Builder
public class AssertManufacturingExpectationsCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(AssertPickingExpectationsCommand.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;

	@NonNull private final Map<String, JsonManufacturingExpectation> expectations;

	void execute()
	{
		for (final Map.Entry<String, JsonManufacturingExpectation> expectationEntry : expectations.entrySet())
		{
			final String matcherStr = expectationEntry.getKey();
			final JsonManufacturingExpectation expectation = expectationEntry.getValue();
			assertManufacturing(matcherStr, expectation);
		}

	}

	private void assertManufacturing(final String matcherStr, final JsonManufacturingExpectation expectation)
	{
		final PPOrderId ppOrderId = getPPOrderIdByMatcherString(matcherStr);

		// first: the cost assertions below read the postings, so they have to be there
		if (expectation.getWaitUntilPostedSeconds() != null)
		{
			waitUntilPosted(ppOrderId, expectation.getWaitUntilPostedSeconds());
		}

		assertCostExpectations(ppOrderId, expectation);

		if (expectation.getReceivedHUs() != null)
		{
			final ArrayList<I_PP_Order_Qty> actuals = new ArrayList<>(services.getPPOrderQtyForFinishedGoodsReceive(ppOrderId));
			assertReceivedHUs(expectation.getReceivedHUs(), actuals);
		}

		if (expectation.getReceivedCoProductHUs() != null)
		{
			final ArrayList<I_PP_Order_Qty> actuals = new ArrayList<>(services.getPPOrderQtyForCoProductReceive(ppOrderId));
			assertReceivedHUs(expectation.getReceivedCoProductHUs(), actuals);
		}

		if (expectation.getIssuedHUs() != null)
		{
			final ArrayList<I_PP_Order_Qty> actuals = new ArrayList<>(services.getPPOrderQtyForComponentIssue(ppOrderId));
			assertIssuedHUs(expectation.getIssuedHUs(), actuals);
		}
	}

	private void assertCostExpectations(final PPOrderId ppOrderId, final JsonManufacturingExpectation expectation)
	{
		softly(() -> {
			softlyPutContext("ppOrderId", ppOrderId);

			if (expectation.getDocStatus() != null || expectation.getCostDifference() != null)
			{
				final I_PP_Order order = Services.get(IPPOrderDAO.class).getById(ppOrderId);
				if (expectation.getDocStatus() != null)
				{
					assertThat(order.getDocStatus()).as("docStatus").isEqualTo(expectation.getDocStatus());
				}
				if (expectation.getCostDifference() != null)
				{
					assertThat(order.getCostDifference()).as("costDifference").isEqualByComparingTo(expectation.getCostDifference());
				}
			}

			if (expectation.getCostCollectors() != null)
			{
				final List<I_PP_Cost_Collector> collectors = retrieveCostCollectors(ppOrderId);
				for (final JsonManufacturingExpectation.CostCollector expected : expectation.getCostCollectors())
				{
					assertCostCollector(expected, collectors);
				}
			}

			if (expectation.getWipAcctBalance() != null)
			{
				assertThat(computeAcctBalance(ppOrderId, I_M_Product_Acct.COLUMNNAME_P_WIP_Acct)).as("wipAcctBalance").isEqualByComparingTo(expectation.getWipAcctBalance());
			}
			if (expectation.getCogsAcctBalance() != null)
			{
				assertThat(computeAcctBalance(ppOrderId, I_M_Product_Acct.COLUMNNAME_P_COGS_Acct)).as("cogsAcctBalance").isEqualByComparingTo(expectation.getCogsAcctBalance());
			}

			if (expectation.getOrderFactAcctPresent() != null)
			{
				final int factCount = queryBL.createQueryBuilder(I_Fact_Acct.class)
						.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_PP_Order.class))
						.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, ppOrderId.getRepoId())
						.create()
						.count();
				assertThat(factCount > 0).as("orderFactAcctPresent (Fact_Acct rows on the PP_Order itself: " + factCount + ")").isEqualTo(expectation.getOrderFactAcctPresent());
			}

			if (expectation.getCurrentCostPrices() != null)
			{
				expectation.getCurrentCostPrices().forEach(this::assertCurrentCostPrice);
			}
		});
	}

	private void assertCostCollector(final JsonManufacturingExpectation.CostCollector expected, final List<I_PP_Cost_Collector> collectors)
	{
		final CostCollectorType type = parseCostCollectorType(expected.getType());
		final ProductId productId = context.getId(expected.getProduct(), ProductId.class);
		final boolean mustBePresent = !Boolean.FALSE.equals(expected.getPresent());

		final List<I_PP_Cost_Collector> matching = collectors.stream()
				.filter(cc -> type.getCode().equals(cc.getCostCollectorType()) && cc.getM_Product_ID() == productId.getRepoId())
				.collect(Collectors.toList());

		softlyPutContext("costCollector", expected);
		if (!mustBePresent)
		{
			assertThat(matching).as("costCollectors: no " + type + " expected for " + expected.getProduct()).isEmpty();
			return;
		}

		assertThat(matching).as("costCollectors: " + type + " for " + expected.getProduct()).isNotEmpty();
		if (expected.getQty() != null && !matching.isEmpty())
		{
			final BigDecimal actualQty = matching.stream().map(I_PP_Cost_Collector::getMovementQty).reduce(BigDecimal.ZERO, BigDecimal::add);
			assertThat(actualQty).as("costCollectors: qty of " + type + " for " + expected.getProduct()).isEqualByComparingTo(expected.getQty());
		}
	}

	private static CostCollectorType parseCostCollectorType(final String name)
	{
		for (final CostCollectorType type : CostCollectorType.values())
		{
			if (type.name().equalsIgnoreCase(name))
			{
				return type;
			}
		}
		throw new AdempiereException("Unknown cost collector type `" + name + "`. Expected one of " + Arrays.toString(CostCollectorType.values()));
	}

	private List<I_PP_Cost_Collector> retrieveCostCollectors(final PPOrderId ppOrderId)
	{
		return queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, ppOrderId)
				.create()
				.list();
	}

	/**
	 * Polls (every second) until every cost collector of the order is posted.
	 */
	private void waitUntilPosted(final PPOrderId ppOrderId, final int timeoutSeconds)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		while (true)
		{
			final List<I_PP_Cost_Collector> collectors = retrieveCostCollectors(ppOrderId);
			final List<I_PP_Cost_Collector> withError = collectors.stream().filter(cc -> cc.getPostingError_Issue_ID() > 0).collect(Collectors.toList());
			if (!withError.isEmpty())
			{
				fail("waitUntilPostedSeconds: posting error on cost collectors " + withError.stream().map(cc -> cc.getPP_Cost_Collector_ID() + "(AD_Issue " + cc.getPostingError_Issue_ID() + ")").collect(Collectors.toList()));
				return;
			}

			final List<Integer> unposted = collectors.stream().filter(cc -> !cc.isPosted()).map(I_PP_Cost_Collector::getPP_Cost_Collector_ID).collect(Collectors.toList());
			if (unposted.isEmpty())
			{
				logger.info("All {} cost collectors of {} posted after {}", collectors.size(), ppOrderId, stopwatch);
				return;
			}

			if (stopwatch.elapsed().getSeconds() >= timeoutSeconds)
			{
				fail("waitUntilPostedSeconds: timeout after " + timeoutSeconds + "s waiting for the posting of cost collectors " + unposted + " of " + ppOrderId);
				return;
			}

			try
			{
				Thread.sleep(1000);
			}
			catch (final InterruptedException e)
			{
				Thread.currentThread().interrupt();
				throw new AdempiereException("Interrupted while waiting for the posting of " + ppOrderId, e);
			}
		}
	}

	/**
	 * Σ(AmtAcctDr - AmtAcctCr) of the order's cost collectors' Fact_Acct rows booked on the given account conceptual name (a {@code M_Product_Acct} column name)
	 * (identified by the {@code AccountConceptualName} the posting stamps on each row).
	 */
	private BigDecimal computeAcctBalance(final PPOrderId ppOrderId, final String accountConceptualName)
	{
		final Set<Integer> collectorIds = retrieveCostCollectors(ppOrderId).stream().map(I_PP_Cost_Collector::getPP_Cost_Collector_ID).collect(Collectors.toSet());
		if (collectorIds.isEmpty())
		{
			return BigDecimal.ZERO;
		}

		return queryBL.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_PP_Cost_Collector.class))
				.addInArrayFilter(I_Fact_Acct.COLUMNNAME_Record_ID, collectorIds)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AccountConceptualName, accountConceptualName)
				.create()
				.stream()
				.map(fact -> fact.getAmtAcctDr().subtract(fact.getAmtAcctCr()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private void assertCurrentCostPrice(final String productIdentifier, final BigDecimal expectedPrice)
	{
		final ProductId productId = context.getId(Identifier.ofString(productIdentifier), ProductId.class);
		final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
		final AcctSchema acctSchema = acctSchemaDAO.getByClientAndOrg(MasterdataContext.CLIENT_ID, MasterdataContext.ORG_ID);

		final List<CostElementId> costElementIds = queryBL.createQueryBuilder(I_M_CostElement.class)
				.addEqualsFilter(I_M_CostElement.COLUMNNAME_CostingMethod, acctSchema.getCosting().getCostingMethod().getCode())
				.create()
				.listIds(CostElementId::ofRepoId);

		final List<I_M_Cost> costs = queryBL.createQueryBuilder(I_M_Cost.class)
				.addEqualsFilter(I_M_Cost.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_M_Cost.COLUMNNAME_C_AcctSchema_ID, acctSchema.getId())
				.addInArrayFilter(I_M_Cost.COLUMNNAME_M_CostElement_ID, costElementIds)
				.create()
				.list();

		softlyPutContext("currentCostPrices." + productIdentifier, costs);
		assertThat(costs).as("currentCostPrices: M_Cost rows of " + productIdentifier + " for costing method " + acctSchema.getCosting().getCostingMethod()).isNotEmpty();
		for (final I_M_Cost cost : costs)
		{
			assertThat(cost.getCurrentCostPrice()).as("currentCostPrices." + productIdentifier).isEqualByComparingTo(expectedPrice);
		}
	}

	private void assertIssuedHUs(final List<JsonManufacturingExpectation.IssuedHU> expectations, final ArrayList<I_PP_Order_Qty> actuals)
	{
		assertThat(actuals).hasSameSize(expectations);

		// same ordering workaround as the receive side: the candidate id does not follow the issue order
		actuals.sort(Comparator.comparing(I_PP_Order_Qty::getM_HU_ID));

		softly(() -> {
			softlyPutContext("expectations", expectations);
			softlyPutContext("actuals", actuals);
			for (int i = 0; i < expectations.size(); i++)
			{
				softlyPutContext("index", i);
				assertIssuedHU(expectations.get(i), actuals.get(i));
			}
		});
	}

	private void assertIssuedHU(final JsonManufacturingExpectation.IssuedHU expectation, final I_PP_Order_Qty actual)
	{
		softly(() -> {
			softlyPutContext("expectation", expectation);
			softlyPutContext("actual", actual);

			if (expectation.getQty() != null)
			{
				assertThat(getQty(actual)).as("qty").isEqualTo(expectation.getQty().toQuantity());
			}

			if (expectation.getAttributes() != null)
			{
				HUAttributeAssertions.assertAttributes(services, expectation.getAttributes(), HuId.ofRepoId(actual.getM_HU_ID()));
			}
		});
	}

	private PPOrderId getPPOrderIdByMatcherString(@NotNull final String matcherStr)
	{
		final Identifier ppOrderIdentifier = Identifier.ofString(matcherStr);
		return context.getOptionalId(ppOrderIdentifier, PPOrderId.class)
				.orElseGet(() -> ppOrderIdentifier.toId(PPOrderId.class));
	}

	public void assertReceivedHUs(final List<JsonManufacturingExpectation.ReceivedHU> expectations, final ArrayList<I_PP_Order_Qty> actuals)
	{
		assertThat(actuals).hasSameSize(expectations);

		// To have a predictable order
		// NOTE: in some cases (don't know why) the PP_Order_Qty_ID is not respecting the order they were actually received ?!
		// so, as a workaround we are ordering them in order the HUs were created.
		actuals.sort(Comparator.comparing(I_PP_Order_Qty::getM_HU_ID));

		softly(() -> {
			softlyPutContext("expectations", expectations);
			softlyPutContext("actuals", actuals);
			for (int i = 0; i < expectations.size(); i++)
			{
				softlyPutContext("index", i);

				final JsonManufacturingExpectation.ReceivedHU expectation = expectations.get(i);
				final I_PP_Order_Qty actual = actuals.get(i);
				assertReceivedHU(expectation, actual);
			}
		});
	}

	private void assertReceivedHU(final JsonManufacturingExpectation.ReceivedHU expectation, final I_PP_Order_Qty actual)
	{
		softly(() -> {
			softlyPutContext("expectation", expectation);
			softlyPutContext("actual", actual);

			if (expectation.getHu() != null)
			{
				context.putSameOrMissingId("hu", expectation.getHu(), HuId.ofRepoId(actual.getM_HU_ID()), HuId.class);
			}
			if (expectation.getLu() != null)
			{
				final HuId luId = getLuId(actual);
				context.putSameOrMissingId("lu", expectation.getLu(), luId, HuId.class);
			}
			if (expectation.getTu() != null)
			{
				final HuId tuId = getTuId(actual);
				context.putSameOrMissingId("tu", expectation.getTu(), tuId, HuId.class);
			}

			if (Boolean.TRUE.equals(expectation.getLooseCUs()))
			{
				assertThat(getLuId(actual)).as("loose CUs are not on an LU").isNull();
				assertThat(getTuId(actual)).as("loose CUs are not in a TU").isNull();
			}

			if (expectation.getQty() != null)
			{
				assertThat(getQty(actual)).as("qty").isEqualTo(expectation.getQty().toQuantity());
			}
		});
	}

	private HuId getLuId(final I_PP_Order_Qty record)
	{
		final HuId huId = HuId.ofRepoId(record.getM_HU_ID());
		return getLuId(huId);
	}

	@Nullable
	private HuId getLuId(final HuId huId)
	{
		final IHandlingUnitsBL handlingUnitsBL = services.handlingUnitsBL;
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final I_M_HU lu = handlingUnitsBL.getLoadingUnitHU(hu);
		return lu != null ? HuId.ofRepoId(lu.getM_HU_ID()) : null;
	}

	private HuId getTuId(final I_PP_Order_Qty record)
	{
		final HuId huId = HuId.ofRepoId(record.getM_HU_ID());
		return getTuId(huId);
	}

	@Nullable
	private HuId getTuId(final HuId huId)
	{
		final IHandlingUnitsBL handlingUnitsBL = services.handlingUnitsBL;
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final I_M_HU tu = handlingUnitsBL.getTransportUnitHU(hu);
		return tu != null ? HuId.ofRepoId(tu.getM_HU_ID()) : null;
	}

	private Quantity getQty(final I_PP_Order_Qty record)
	{
		return Quantitys.of(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID()));
	}
}
