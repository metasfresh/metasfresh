package de.metas.distribution.ddorder.lowlevel;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderQuery;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The DAO overload that takes a list of opaque line-id restrictions ({@code lineIdRestrictions}) must apply each
 * one on the order-to-line join ({@code I_DD_Order.DD_Order_ID -> I_DD_OrderLine.DD_Order_ID}), never line-id to
 * line-id: parent and sub table must differ so {@link org.adempiere.ad.dao.impl.InSubQueryFilter} renders a
 * correlated {@code EXISTS} rather than an {@code IN} (which it emits when parent and sub table are the same —
 * {@code InSubQueryFilter#buildSql}).
 * <p>
 * The restriction is built directly via {@link TypedSqlQuery} rather than through {@code IQueryBL}, so the emitted
 * SQL can be inspected deterministically: unit-test mode routes {@code IQueryBuilder#create()} to the in-memory POJO
 * engine, which has no SQL to read. Reading the composite filter's SQL does not go through that engine, so the
 * assertion holds either way.
 */
class DDOrderLowLevelDAOTest
{
	private static final WarehouseId WH_A = WarehouseId.ofRepoId(101);
	private static final WarehouseId WH_B = WarehouseId.ofRepoId(102);
	private static final WarehouseId WH_C = WarehouseId.ofRepoId(103);
	private static final WarehouseId WH_D = WarehouseId.ofRepoId(104);

	private static final LocatorId A_L1 = LocatorId.ofRepoId(WH_A.getRepoId(), 201);
	private static final LocatorId A_L2 = LocatorId.ofRepoId(WH_A.getRepoId(), 202);
	private static final LocatorId B_L1 = LocatorId.ofRepoId(WH_B.getRepoId(), 211);

	private DDOrderLowLevelDAO dao;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		dao = new DDOrderLowLevelDAO();
	}

	private DDOrderId createDDOrder(final WarehouseId from, final WarehouseId to, @Nullable final LocatorId lineLocatorTo)
	{
		final I_DD_Order order = newInstance(I_DD_Order.class);
		order.setM_Warehouse_From_ID(from.getRepoId());
		order.setM_Warehouse_To_ID(to.getRepoId());
		save(order);

		if (lineLocatorTo != null)
		{
			final I_DD_OrderLine line = newInstance(I_DD_OrderLine.class);
			line.setDD_Order_ID(order.getDD_Order_ID());
			line.setM_LocatorTo_ID(lineLocatorTo.getRepoId());
			save(line);
		}

		return DDOrderId.ofRepoId(order.getDD_Order_ID());
	}

	private Set<DDOrderId> queryWorkplace(final WarehouseId workplaceWarehouseId, @Nullable final LocatorId workplacePickFromLocatorId)
	{
		final DDOrderQuery query = DDOrderQuery.builder()
				.orderBys(ImmutableList.of())
				.workplaceWarehouseId(workplaceWarehouseId)
				.workplacePickFromLocatorId(workplacePickFromLocatorId)
				.build();
		return dao.streamDDOrders(query)
				.map(o -> DDOrderId.ofRepoId(o.getDD_Order_ID()))
				.collect(Collectors.toSet());
	}

	@Test
	void workplaceWarehouse_withoutLocator_matchesFromOrTo()
	{
		final DDOrderId aToB = createDDOrder(WH_A, WH_B, B_L1);
		final DDOrderId cToA = createDDOrder(WH_C, WH_A, A_L1);

		// no pick-from locator => warehouse matches on either the from- or the to-side
		assertThat(queryWorkplace(WH_A, null)).containsExactlyInAnyOrder(aToB, cToA); // A is the 'from' of aToB and the 'to' of cToA
		assertThat(queryWorkplace(WH_B, null)).containsExactly(aToB);
		assertThat(queryWorkplace(WH_C, null)).containsExactly(cToA);
		assertThat(queryWorkplace(WH_D, null)).isEmpty();
	}

	@Test
	void workplacePickFromLocator_narrowsToSideOnly_sourceSideUnaffected()
	{
		// delivered TO A, into locator A_L1
		final DDOrderId toA_l1 = createDDOrder(WH_C, WH_A, A_L1);
		// delivered TO A, into a DIFFERENT locator A_L2
		final DDOrderId toA_l2 = createDDOrder(WH_C, WH_A, A_L2);
		// shipped FROM A (destination locator is irrelevant to the source-side match)
		final DDOrderId fromA = createDDOrder(WH_A, WH_B, B_L1);

		// workplace on A with pick-from locator A_L1:
		//  - to-side match requires the delivery locator to equal A_L1  -> toA_l1 only (toA_l2 excluded)
		//  - from-side match (ships from A) is NOT gated by the locator  -> fromA included
		assertThat(queryWorkplace(WH_A, A_L1)).containsExactlyInAnyOrder(toA_l1, fromA);

		// without the locator, both to-A orders are visible plus the from-A order
		assertThat(queryWorkplace(WH_A, null)).containsExactlyInAnyOrder(toA_l1, toA_l2, fromA);

		// unrelated warehouse: nothing
		assertThat(queryWorkplace(WH_D, A_L1)).isEmpty();
	}

	@Test
	void restrictionsRenderAsExists()
	{
		// an arbitrary, directly-built query over I_DD_OrderLine standing in for a real demand-side restriction
		// (e.g. DDOrderLineDemandSqlHelper#byCarrierProductIds); what matters here is only the join the DAO applies it on.
		final IQuery<I_DD_OrderLine> restriction =
				new TypedSqlQuery<>(new Properties(), I_DD_OrderLine.class, "IsActive='Y'", ITrx.TRXNAME_None);

		final DDOrderQuery query = DDOrderQuery.builder()
				.orderBys(ImmutableList.of())
				.build();

		final IQueryBuilder<I_DD_Order> queryBuilder =
				dao.toSqlQuery(query, ImmutableList.of(restriction));

		final String sql = ((ISqlQueryFilter)queryBuilder.getCompositeFilter()).getSql();

		assertThat(sql).contains("EXISTS (");
		assertThat(sql).doesNotContain(" IN (SELECT");
	}
}
