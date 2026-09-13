package de.metas.distribution.ddorder.lowlevel;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderQuery;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.IQuery;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

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
	private final DDOrderLowLevelDAO ddOrderLowLevelDAO = new DDOrderLowLevelDAO();

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
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
				ddOrderLowLevelDAO.toSqlQuery(query, ImmutableList.of(restriction));

		final String sql = ((ISqlQueryFilter)queryBuilder.getCompositeFilter()).getSql();

		assertThat(sql).contains("EXISTS (");
		assertThat(sql).doesNotContain(" IN (SELECT");
	}
}
