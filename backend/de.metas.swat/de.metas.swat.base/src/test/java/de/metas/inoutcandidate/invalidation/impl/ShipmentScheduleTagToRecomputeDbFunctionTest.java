package de.metas.inoutcandidate.invalidation.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * Exercises the real {@code M_ShipmentSchedule_TagToRecompute(numeric, integer)} Postgres function
 * (migration {@code 5814390_sys_M_ShipmentSchedule_TagToRecompute_function.sql}) -- the whole-product
 * batching logic that used to live in {@code ShipmentScheduleInvalidateRepository#buildMarkAllToRecomputeSql}
 * (a Java-built SQL string) now lives in the DB function, so it can only be verified by actually calling it.
 * <p>
 * This module's normal JUnit tests run against {@link org.adempiere.test.AdempiereTestHelper}'s in-memory
 * {@code POJOWrapper} store, which does not back raw SQL / DB functions -- there is no existing "real DB"
 * JUnit harness in this module (see also the removed {@code buildMarkAllToRecomputeSql}-based tests this
 * class replaces). This test therefore opens a plain JDBC connection to a real local Postgres stack that
 * already has the migration applied.
 * <p>
 * <b>Skips (never fails) when no such DB is reachable</b> -- e.g. on CI, which does not run this specific
 * dev stack -- so it never breaks the build. Run it locally (see the {@code metasfresh-db} /
 * {@code metasfresh-local-infra} skills for applying the migration) to get real coverage.
 * <p>
 * <b>No test data is ever persisted:</b> every test inserts its fixture rows (fabricated
 * {@code M_ShipmentSchedule} / {@code M_ShipmentSchedule_Recompute} rows, well above any id the seeded DB
 * uses) inside the class-wide connection's transaction and rolls back to a per-test {@link Savepoint}
 * in {@link #rollbackToSavepoint()} -- nothing is ever committed. All FK columns on
 * {@code M_ShipmentSchedule} (M_Product_ID, C_BPartner_ID, M_Warehouse_ID, ...) are declared
 * {@code DEFERRABLE INITIALLY DEFERRED}, so the FK check would only run at COMMIT time, which never
 * happens here -- fabricated ids are fine.
 */
class ShipmentScheduleTagToRecomputeDbFunctionTest
{
	private static final String DB_URL = System.getProperty("de.metas.test.db.url", "jdbc:postgresql://localhost:35991/metasfresh");
	private static final String DB_USER = System.getProperty("de.metas.test.db.user", "metasfresh");
	private static final String DB_PASSWORD = System.getProperty("de.metas.test.db.password", "metasfresh");

	/** test-only id range, well above anything the seeded DB uses -- never actually committed (see class javadoc) */
	private static final long SCHED_ID_BASE = 900_000_001L;
	private static final long PRODUCT_ID_BASE = 900_100_001L;

	private static Connection connection;

	private Savepoint savepoint;

	@BeforeAll
	static void connectOrSkip()
	{
		try
		{
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			connection.setAutoCommit(false);
		}
		catch (final SQLException ex)
		{
			Assumptions.assumeTrue(false,
					"No local DB reachable at " + DB_URL + " (" + ex.getMessage() + ") -- skipping DB-function test. "
							+ "Run locally against a stack with migration 5814390 applied to get real coverage.");
		}
	}

	@AfterAll
	static void disconnect() throws SQLException
	{
		if (connection != null)
		{
			connection.rollback(); // belt-and-suspenders: never persist anything, even if a test forgot to clean up
			connection.close();
		}
	}

	@BeforeEach
	void openSavepoint() throws SQLException
	{
		savepoint = connection.setSavepoint();
	}

	@AfterEach
	void rollbackToSavepoint() throws SQLException
	{
		connection.rollback(savepoint);
	}

	/**
	 * Inserts a minimal, fabricated {@code M_ShipmentSchedule} row. Only genuinely {@code NOT NULL}
	 * columns (verified against the local schema) are populated; FK columns are fabricated ids that are
	 * never actually checked because this connection never commits (see class javadoc).
	 */
	private void insertShipmentSchedule(final long shipmentScheduleId, final long productId) throws SQLException
	{
		// Record_ID + AD_Table_ID have a unique constraint (m_shipmentschedule_record_id_ad_table_id) --
		// use the schedule id itself as Record_ID so each row gets its own, distinct pair.
		final String sql = "INSERT INTO M_ShipmentSchedule ("
				+ "  M_ShipmentSchedule_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy,"
				+ "  M_Product_ID, M_Warehouse_ID, C_BPartner_ID, C_BPartner_Location_ID, Bill_BPartner_ID,"
				+ "  DeliveryRule, DeliveryViaRule, BPartnerAddress, AD_Table_ID, Record_ID"
				+ ") VALUES (?, 1000000, 0, now(), 100, now(), 100, ?, 0, 0, 0, 0, 'F', 'D', 'test', 0, ?)";
		try (final PreparedStatement ps = connection.prepareStatement(sql))
		{
			ps.setLong(1, shipmentScheduleId);
			ps.setLong(2, productId);
			ps.setLong(3, shipmentScheduleId);
			ps.executeUpdate();
		}
	}

	private void insertRecomputeMarker(final long shipmentScheduleId) throws SQLException
	{
		try (final PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO M_ShipmentSchedule_Recompute (M_ShipmentSchedule_ID) VALUES (?)"))
		{
			ps.setLong(1, shipmentScheduleId);
			ps.executeUpdate();
		}
	}

	private int callTagToRecompute(final long selectionId, final Integer batchSize) throws SQLException
	{
		try (final PreparedStatement ps = connection.prepareStatement("SELECT M_ShipmentSchedule_TagToRecompute(?, ?)"))
		{
			ps.setLong(1, selectionId);
			if (batchSize == null)
			{
				ps.setNull(2, java.sql.Types.INTEGER);
			}
			else
			{
				ps.setInt(2, batchSize);
			}
			try (final ResultSet rs = ps.executeQuery())
			{
				assertThat(rs.next()).as("function must return exactly one row").isTrue();
				return rs.getInt(1);
			}
		}
	}

	private List<Long> fetchDistinctTaggedScheduleIds(final long selectionId) throws SQLException
	{
		final List<Long> result = new ArrayList<>();
		try (final PreparedStatement ps = connection.prepareStatement(
				"SELECT DISTINCT M_ShipmentSchedule_ID FROM M_ShipmentSchedule_Recompute WHERE AD_PInstance_ID = ? ORDER BY M_ShipmentSchedule_ID"))
		{
			ps.setLong(1, selectionId);
			try (final ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					result.add(rs.getLong(1));
				}
			}
		}
		return result;
	}

	private int countUntagged() throws SQLException
	{
		try (final PreparedStatement ps = connection.prepareStatement(
				"SELECT count(*) FROM M_ShipmentSchedule_Recompute WHERE AD_PInstance_ID IS NULL");
				final ResultSet rs = ps.executeQuery())
		{
			rs.next();
			return rs.getInt(1);
		}
	}

	/**
	 * Fixture used by most tests: 3 products, ascending by id, with a distinct schedule count each --
	 * P1 has 2 schedules (one of them double-marked, to prove duplicate markers of a selected schedule
	 * are ALL tagged), P2 has 3, P3 has 1. Running totals (per the function's window-function cutoff):
	 * P1: running_total-count=0 (always qualifies); P2: 2; P3: 5.
	 */
	private void insertThreeProductFixture() throws SQLException
	{
		// also defensively clear any pre-existing untagged markers (none expected on a clean stack,
		// but this keeps the test deterministic regardless of stack state; never committed, see class javadoc)
		try (final PreparedStatement ps = connection.prepareStatement(
				"DELETE FROM M_ShipmentSchedule_Recompute WHERE AD_PInstance_ID IS NULL"))
		{
			ps.executeUpdate();
		}

		// P1: 2 schedules (sched #1 duplicated: 2 marker rows for the same schedule id)
		insertShipmentSchedule(SCHED_ID_BASE + 1, PRODUCT_ID_BASE + 1);
		insertShipmentSchedule(SCHED_ID_BASE + 2, PRODUCT_ID_BASE + 1);
		insertRecomputeMarker(SCHED_ID_BASE + 1);
		insertRecomputeMarker(SCHED_ID_BASE + 1); // duplicate marker
		insertRecomputeMarker(SCHED_ID_BASE + 2);

		// P2: 3 schedules
		insertShipmentSchedule(SCHED_ID_BASE + 3, PRODUCT_ID_BASE + 2);
		insertShipmentSchedule(SCHED_ID_BASE + 4, PRODUCT_ID_BASE + 2);
		insertShipmentSchedule(SCHED_ID_BASE + 5, PRODUCT_ID_BASE + 2);
		insertRecomputeMarker(SCHED_ID_BASE + 3);
		insertRecomputeMarker(SCHED_ID_BASE + 4);
		insertRecomputeMarker(SCHED_ID_BASE + 5);

		// P3: 1 schedule
		insertShipmentSchedule(SCHED_ID_BASE + 6, PRODUCT_ID_BASE + 3);
		insertRecomputeMarker(SCHED_ID_BASE + 6);
	}

	@Test
	void batchSizeLessOrEqualZero_tagsEverythingUnbounded() throws SQLException
	{
		insertThreeProductFixture();

		final int tagged = callTagToRecompute(1, 0);

		// 6 distinct schedules + 1 duplicate marker for sched #1 = 7 rows tagged
		assertThat(tagged).as("p_batchsize<=0 must tag every untagged marker, including duplicates").isEqualTo(7);
		assertThat(countUntagged()).as("nothing must remain untagged").isZero();
		assertThat(fetchDistinctTaggedScheduleIds(1))
				.as("all 6 distinct schedules across all 3 products must be tagged")
				.containsExactly(SCHED_ID_BASE + 1, SCHED_ID_BASE + 2, SCHED_ID_BASE + 3, SCHED_ID_BASE + 4, SCHED_ID_BASE + 5, SCHED_ID_BASE + 6);
	}

	@Test
	void batchSizeNull_tagsEverythingUnbounded() throws SQLException
	{
		insertThreeProductFixture();

		final int tagged = callTagToRecompute(2, null);

		assertThat(tagged).as("NULL p_batchsize must behave like <= 0 (unbounded)").isEqualTo(7);
		assertThat(countUntagged()).isZero();
	}

	@Test
	void tightestBound_stillTagsAtLeastOneWholeProduct_evenThoughItExceedsN() throws SQLException
	{
		insertThreeProductFixture();

		// N=1: P1 alone has 2 schedules (> N), but must still be tagged whole (running_total-count=0 < 1 for the
		// first product, always true) -- proves "at least one whole product, even if it alone exceeds N".
		final int tagged = callTagToRecompute(3, 1);

		assertThat(tagged).as("P1's 2 distinct schedules + its 1 duplicate marker = 3 rows").isEqualTo(3);
		assertThat(fetchDistinctTaggedScheduleIds(3))
				.as("only P1's whole product (2 schedules) is tagged -- never split, never a partial product")
				.containsExactly(SCHED_ID_BASE + 1, SCHED_ID_BASE + 2);
		assertThat(countUntagged()).as("P2 (3) + P3 (1) = 4 schedules remain untagged").isEqualTo(4);
	}

	@Test
	void boundedBatch_neverSplitsAProductAcrossTheBoundary() throws SQLException
	{
		insertThreeProductFixture();

		// N=4: P1 qualifies (0<4), P2 qualifies (2<4), P3 does NOT (5<4 is false) -> P1+P2 tagged whole, P3 untouched.
		final int tagged = callTagToRecompute(4, 4);

		assertThat(tagged).as("P1 (2 scheds + 1 dup) + P2 (3 scheds) = 6 rows").isEqualTo(6);
		assertThat(fetchDistinctTaggedScheduleIds(4))
				.as("P1 and P2 are tagged WHOLE; P3's single schedule must not be split in")
				.containsExactly(SCHED_ID_BASE + 1, SCHED_ID_BASE + 2, SCHED_ID_BASE + 3, SCHED_ID_BASE + 4, SCHED_ID_BASE + 5);
		assertThat(countUntagged()).as("P3's 1 schedule remains untagged").isEqualTo(1);
	}

	@Test
	void boundedBatch_accumulatesWholeProductsUntilCumulativeCountWouldReachN() throws SQLException
	{
		insertThreeProductFixture();

		// N=10 (>= total distinct schedule count of 6): every product qualifies, all tagged.
		final int tagged = callTagToRecompute(5, 10);

		assertThat(tagged).as("all 6 distinct schedules + 1 duplicate marker = 7 rows").isEqualTo(7);
		assertThat(countUntagged()).as("the whole backlog is drained in one bounded pass once N covers it").isZero();
	}

	@Test
	void alreadyTaggedMarkers_areNeverReTagged() throws SQLException
	{
		insertThreeProductFixture();

		// first pass tags P1 only (N=1)
		callTagToRecompute(6, 1);
		// second pass with a DIFFERENT selection id must only see the still-untagged P2/P3 markers
		final int taggedSecondPass = callTagToRecompute(7, 100);

		assertThat(fetchDistinctTaggedScheduleIds(6))
				.as("first pass's tagged schedules keep their original selection id")
				.containsExactly(SCHED_ID_BASE + 1, SCHED_ID_BASE + 2);
		assertThat(taggedSecondPass).as("P2 (3) + P3 (1) = 4 rows, none of P1's already-tagged rows").isEqualTo(4);
		assertThat(fetchDistinctTaggedScheduleIds(7))
				.containsExactly(SCHED_ID_BASE + 3, SCHED_ID_BASE + 4, SCHED_ID_BASE + 5, SCHED_ID_BASE + 6);
	}
}
