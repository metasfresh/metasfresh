package de.metas.distribution.ddorder.lowlevel;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderQuery;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.eevolution.model.I_DD_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Guards the limit push-down: the {@code limit} carried by {@link DDOrderQuery} must be pushed down into the SQL
 * query, so the {@code T_Query_Selection} INSERT built by the guaranteed iterator stays bounded. Without the
 * push-down, the mobile Distribution launchers WebSocket producer (polled every second) materializes the whole
 * DD_Order backlog on every poll, pinning JDBC connections.
 */
class DDOrderLowLevelDAOStreamLimitTest
{
	private DDOrderLowLevelDAO ddOrderLowLevelDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		ddOrderLowLevelDAO = new DDOrderLowLevelDAO();
	}

	private void createDDOrders(final int count)
	{
		for (int i = 0; i < count; i++)
		{
			final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
			InterfaceWrapperHelper.save(ddOrder);
		}
	}

	private static DDOrderQuery queryWithLimit(final QueryLimit limit)
	{
		return DDOrderQuery.builder()
				.orderBys(ImmutableList.of())
				.limit(limit)
				.build();
	}

	private long streamCount(final DDOrderQuery query)
	{
		try (final Stream<I_DD_Order> stream = ddOrderLowLevelDAO.streamDDOrders(query))
		{
			return stream.count();
		}
	}

	@Test
	void streamDDOrders_appliesLimit()
	{
		createDDOrders(5);

		assertThat(streamCount(queryWithLimit(QueryLimit.ofInt(3)))).isEqualTo(3);
	}

	@Test
	void streamDDOrders_noLimit_returnsAll()
	{
		createDDOrders(5);

		assertThat(streamCount(queryWithLimit(QueryLimit.NO_LIMIT))).isEqualTo(5);
	}
}
