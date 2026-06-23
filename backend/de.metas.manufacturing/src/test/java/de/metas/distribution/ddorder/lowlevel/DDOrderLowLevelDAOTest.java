package de.metas.distribution.ddorder.lowlevel;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderQuery;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.model.I_DD_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

class DDOrderLowLevelDAOTest
{
	private static final WarehouseId WH_A = WarehouseId.ofRepoId(101);
	private static final WarehouseId WH_B = WarehouseId.ofRepoId(102);
	private static final WarehouseId WH_C = WarehouseId.ofRepoId(103);
	private static final WarehouseId WH_D = WarehouseId.ofRepoId(104);

	private DDOrderLowLevelDAO dao;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		dao = new DDOrderLowLevelDAO();
	}

	private DDOrderId createDDOrder(final WarehouseId from, final WarehouseId to)
	{
		final I_DD_Order order = newInstance(I_DD_Order.class);
		order.setM_Warehouse_From_ID(from.getRepoId());
		order.setM_Warehouse_To_ID(to.getRepoId());
		save(order);
		return DDOrderId.ofRepoId(order.getDD_Order_ID());
	}

	private Set<DDOrderId> queryFromOrTo(final WarehouseId warehouseId)
	{
		final DDOrderQuery query = DDOrderQuery.builder()
				.orderBys(ImmutableList.of())
				.fromOrToWarehouseId(warehouseId)
				.build();
		return dao.streamDDOrders(query)
				.map(o -> DDOrderId.ofRepoId(o.getDD_Order_ID()))
				.collect(Collectors.toSet());
	}

	@Test
	void fromOrToWarehouse_matchesBothSides()
	{
		final DDOrderId aToB = createDDOrder(WH_A, WH_B);
		final DDOrderId cToA = createDDOrder(WH_C, WH_A);

		assertThat(queryFromOrTo(WH_A)).containsExactlyInAnyOrder(aToB, cToA); // A is the 'from' of aToB and the 'to' of cToA
		assertThat(queryFromOrTo(WH_B)).containsExactly(aToB);                 // B only the 'to' of aToB
		assertThat(queryFromOrTo(WH_C)).containsExactly(cToA);                 // C only the 'from' of cToA
		assertThat(queryFromOrTo(WH_D)).isEmpty();                            // D unrelated
	}
}
