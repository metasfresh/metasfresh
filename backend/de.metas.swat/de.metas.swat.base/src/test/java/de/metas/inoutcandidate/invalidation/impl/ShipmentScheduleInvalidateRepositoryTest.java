package de.metas.inoutcandidate.invalidation.impl;

import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegmentBuilder;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

/**
 * Tests {@link ShipmentScheduleInvalidateRepository#buildShipmentScheduleWhereClause(String, IShipmentScheduleSegment, List)}
 * for the warehouse-derived segment support (warehouse branch + empty-locator guard).
 * <p>
 * The whole-product batching is performed by the {@code M_ShipmentSchedule_TagToRecompute(numeric, integer)}
 * DB function, invoked via
 * {@link ShipmentScheduleInvalidateRepository#markAllToRecomputeOutOfTrx(de.metas.process.PInstanceId, org.adempiere.ad.dao.QueryLimit)};
 * that batching is covered end-to-end by the {@code updateInvalidShipmentSchedulesRecomputeBatching} Cucumber scenario.
 */
public class ShipmentScheduleInvalidateRepositoryTest
{
	private static final String SS_ALIAS = I_M_ShipmentSchedule.Table_Name + ".";

	/** effective warehouse column, mirroring the production builder */
	private static final String WAREHOUSE_COLUMN =
			"COALESCE(" + SS_ALIAS + I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID
					+ ", " + SS_ALIAS + I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID + ")";

	/** the top-level warehouse predicate emitted by the warehouse branch for a single warehouse (DB.buildSqlList single value -> "col=?") */
	private static final String WAREHOUSE_BRANCH_PREDICATE = "(" + WAREHOUSE_COLUMN + "=?)";

	private static final String LOCATOR_SUBSELECT = "EXISTS (select 1 from " + org.compiere.model.I_M_Locator.Table_Name + " loc";

	private ShipmentScheduleInvalidateRepository repository;
	private Method buildShipmentScheduleWhereClause;

	@BeforeEach
	public void beforeEach() throws Exception
	{
		AdempiereTestHelper.get().init();

		repository = new ShipmentScheduleInvalidateRepository();

		buildShipmentScheduleWhereClause = ShipmentScheduleInvalidateRepository.class
				.getDeclaredMethod("buildShipmentScheduleWhereClause", String.class, IShipmentScheduleSegment.class, List.class);
		buildShipmentScheduleWhereClause.setAccessible(true);
	}

	private String buildWhereClause(final IShipmentScheduleSegment segment, final List<Object> sqlParams) throws Exception
	{
		return (String)buildShipmentScheduleWhereClause.invoke(repository, SS_ALIAS, segment, sqlParams);
	}

	@Test
	public void warehouseSegment_matchesByEffectiveWarehouseColumn_andNoLocatorSubselect() throws Exception
	{
		// note: bpartnerId(2) is required so the segment is not "invalid" (see IShipmentScheduleSegment.isInvalid())
		final ImmutableShipmentScheduleSegment segment = new ShipmentScheduleSegmentBuilder()
				.productId(1)
				.bpartnerId(2)
				.warehouseId(WarehouseId.ofRepoId(50))
				.build();

		final List<Object> sqlParams = new ArrayList<>();
		final String whereClause = buildWhereClause(segment, sqlParams);

		assertThat(whereClause)
				.as("warehouse-derived segment must match by the schedule's effective warehouse column")
				.contains(WAREHOUSE_BRANCH_PREDICATE);
		assertThat(whereClause)
				.as("the product branch must still emit its predicate")
				.contains(SS_ALIAS + I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID + "=?");
		assertThat(whereClause)
				.as("warehouse-derived segment must NOT emit the M_Locator EXISTS sub-select")
				.doesNotContain(LOCATOR_SUBSELECT);
		assertThat(sqlParams)
				.as("the warehouse repo-id must be collected as an SQL parameter")
				.contains(50);
	}

	@Test
	public void locatorSegment_stillUsesLocatorSubselect_andNoBareWarehousePredicate() throws Exception
	{
		final ImmutableShipmentScheduleSegment segment = new ShipmentScheduleSegmentBuilder()
				.productId(1)
				.bpartnerId(2)
				.locatorId(555)
				.build();

		final List<Object> sqlParams = new ArrayList<>();
		final String whereClause = buildWhereClause(segment, sqlParams);

		assertThat(whereClause)
				.as("locator segment must still use the M_Locator EXISTS sub-select")
				.contains(LOCATOR_SUBSELECT)
				.contains("loc." + org.compiere.model.I_M_Locator.COLUMNNAME_M_Locator_ID + "=?");
		assertThat(whereClause)
				.as("locator segment must NOT emit a top-level warehouse predicate (locator path unchanged)")
				.doesNotContain(WAREHOUSE_BRANCH_PREDICATE);
		assertThat(sqlParams)
				.as("the locator repo-id must be collected as an SQL parameter")
				.contains(555);
	}
}
