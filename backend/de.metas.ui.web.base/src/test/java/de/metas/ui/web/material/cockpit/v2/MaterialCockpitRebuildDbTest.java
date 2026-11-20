package de.metas.ui.web.material.cockpit.v2;

/*
 * #%L
 * de.metas.ui.web.base
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

import de.metas.util.StringUtils;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Proves that {@code M_MaterialCockpit_rebuild()} still works after migration
 * {@code 5832320_sys_gh33275_MaterialCockpit_Base_V_perf.sql}, with and without a customer override view
 * ({@code ZZ_MaterialCockpit_V}) and with a view depending on {@code QtyDemand_QtySupply_V}.
 * <p>
 * Each test runs against its own throwaway DB container; the migration file is applied by plain JDBC, the shared
 * migration registry is not involved.
 * <p>
 * Like {@code RunMigrationScriptsTest} this test needs Docker and is skipped (inert) when
 * {@code SKIP_MIGRATION_SCRIPTS_TEST=true} (system property or env) - which is the case in the CI path, so it is a
 * local-run-only check. The DB image must contain the pre-migration {@code M_MaterialCockpit_Base_V},
 * {@code M_MaterialCockpit_rebuild()} and {@code db_alter_view()}; override it with env {@code DB_IMAGE_NAME}.
 */
public class MaterialCockpitRebuildDbTest
{
	private static final String ENV_SKIP_MIGRATION_SCRIPTS_TEST = "SKIP_MIGRATION_SCRIPTS_TEST";
	private static final String ENV_DB_IMAGE_NAME = "DB_IMAGE_NAME";
	private static final String DB_IMAGE_NAME_DEFAULT = "metasfresh/metas-db:5.175-intensive-care-release.47590-preloaded";

	private static final String MIGRATION_FILENAME = "5832320_sys_gh33275_MaterialCockpit_Base_V_perf.sql";
	private static final String MIGRATION_PATH = "src/main/sql/postgresql/system/41-de.metas.ui.web.base/" + MIGRATION_FILENAME;

	private static final List<String> EXPECTED_COLUMNS = Arrays.asList(
			"ad_client_id", "ad_org_id", "productname", "productvalue", "m_product_id", "m_product_category_id", "c_uom_id",
			"attributeskey", "m_warehouse_id", "qtyreserved", "qtytomove", "qtytoproduce", "qtyforecasted", "qtystock",
			"qtyconfirmedbysupplier", "qtyunconfirmedbysupplier", "qtydemand_qtysupply_v_id", "lastcostprice");

	@Test
	void rebuild_withoutOverrideView()
	{
		assumeNotSkipped();

		try (final GenericContainer<?> db = startDb(); final Connection c = connect(db))
		{
			executeScript(c, readMigration());

			assertThat(viewExists(c, "zz_materialcockpit_v")).isFalse();
			execute(c, "CREATE VIEW zz_dependent_v AS SELECT m_product_id, qtystock FROM QtyDemand_QtySupply_V");

			execute(c, "SELECT M_MaterialCockpit_rebuild()");

			assertApiViewHealthy(c);
			assertThat(viewDefinition(c, "qtydemand_qtysupply_v")).containsIgnoringCase("m_materialcockpit_base_v");
		}
		catch (final SQLException e)
		{
			throw new AssertionError("Unexpected SQL failure: " + e.getMessage(), e);
		}
	}

	@Test
	void rebuild_withOverrideView_createdAfterMigration()
	{
		assumeNotSkipped();

		try (final GenericContainer<?> db = startDb(); final Connection c = connect(db))
		{
			executeScript(c, readMigration());

			execute(c, "CREATE VIEW ZZ_MaterialCockpit_V AS SELECT * FROM M_MaterialCockpit_Base_V");
			execute(c, "CREATE VIEW zz_dependent_v AS SELECT m_product_id, qtystock FROM QtyDemand_QtySupply_V");

			execute(c, "SELECT M_MaterialCockpit_rebuild()");

			assertApiViewHealthy(c);
			assertThat(viewDefinition(c, "qtydemand_qtysupply_v")).containsIgnoringCase("zz_materialcockpit_v");
		}
		catch (final SQLException e)
		{
			throw new AssertionError("Unexpected SQL failure: " + e.getMessage(), e);
		}
	}

	/**
	 * The real-life order: the customer override view already exists on the instance when the migration runs
	 * (the migration itself ends with {@code after_migration_M_MaterialCockpit_rebuild()}).
	 */
	@Test
	void migration_withOverrideViewAlreadyPresent()
	{
		assumeNotSkipped();

		try (final GenericContainer<?> db = startDb(); final Connection c = connect(db))
		{
			execute(c, "CREATE VIEW ZZ_MaterialCockpit_V AS SELECT * FROM M_MaterialCockpit_Base_V");
			execute(c, "SELECT M_MaterialCockpit_rebuild()"); // QtyDemand_QtySupply_V now selects from the override
			execute(c, "CREATE VIEW zz_dependent_v AS SELECT m_product_id, qtystock FROM QtyDemand_QtySupply_V");

			executeScript(c, readMigration());

			assertApiViewHealthy(c);
			assertThat(viewDefinition(c, "qtydemand_qtysupply_v")).containsIgnoringCase("zz_materialcockpit_v");

			execute(c, "SELECT M_MaterialCockpit_rebuild()");
			assertApiViewHealthy(c);
		}
		catch (final SQLException e)
		{
			throw new AssertionError("Unexpected SQL failure: " + e.getMessage(), e);
		}
	}

	private static void assertApiViewHealthy(final Connection c) throws SQLException
	{
		assertThat(viewExists(c, "qtydemand_qtysupply_v")).as("QtyDemand_QtySupply_V exists").isTrue();
		assertThat(columnsOf(c, "qtydemand_qtysupply_v")).as("QtyDemand_QtySupply_V columns").containsExactlyElementsOf(EXPECTED_COLUMNS);
		assertThat(columnsOf(c, "m_materialcockpit_base_v")).as("M_MaterialCockpit_Base_V columns").containsExactlyElementsOf(EXPECTED_COLUMNS);
		assertThat(viewExists(c, "zz_dependent_v")).as("dependent view survived the rebuild").isTrue();

		// the whole chain (view -> view -> function) must be executable
		execute(c, "SELECT * FROM QtyDemand_QtySupply_V");
		execute(c, "SELECT * FROM zz_dependent_v");
	}

	//
	// ---- helpers ----
	//

	private static void assumeNotSkipped()
	{
		Assumptions.assumeFalse(isSkipTest(), "`" + ENV_SKIP_MIGRATION_SCRIPTS_TEST + "` system property/env is set");
	}

	private static boolean isSkipTest()
	{
		final String propValue = StringUtils.trimBlankToNull(System.getProperty(ENV_SKIP_MIGRATION_SCRIPTS_TEST));
		if (propValue != null)
		{
			return StringUtils.toBoolean(propValue);
		}

		final String envValue = StringUtils.trimBlankToNull(System.getenv(ENV_SKIP_MIGRATION_SCRIPTS_TEST));
		if (envValue != null)
		{
			return StringUtils.toBoolean(envValue);
		}

		return false;
	}

	private static GenericContainer<?> startDb()
	{
		final String envImage = StringUtils.trimBlankToNull(System.getenv(ENV_DB_IMAGE_NAME));
		final DockerImageName imageName = DockerImageName.parse(envImage != null ? envImage : DB_IMAGE_NAME_DEFAULT);

		//noinspection resource
		final GenericContainer<?> db = new GenericContainer<>(imageName)
				.withEnv("POSTGRES_PASSWORD", "password")
				.withStartupTimeout(Duration.ofMinutes(3))
				.withExposedPorts(5432);
		db.start();
		return db;
	}

	/** The container may accept TCP connections before postgres is ready, so retry for a while. */
	private static Connection connect(final GenericContainer<?> db) throws SQLException
	{
		final String url = "jdbc:postgresql://" + db.getHost() + ":" + db.getFirstMappedPort() + "/metasfresh";
		final long deadline = System.currentTimeMillis() + Duration.ofMinutes(2).toMillis();
		SQLException lastError;
		do
		{
			try
			{
				return DriverManager.getConnection(url, "metasfresh", "metasfresh");
			}
			catch (final SQLException e)
			{
				lastError = e;
				try
				{
					Thread.sleep(2000);
				}
				catch (final InterruptedException ie)
				{
					Thread.currentThread().interrupt();
					throw new SQLException("Interrupted while waiting for the DB", ie);
				}
			}
		}
		while (System.currentTimeMillis() < deadline);
		throw lastError;
	}

	private static String readMigration()
	{
		final Path file = Paths.get(MIGRATION_PATH);
		try
		{
			return new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
		}
		catch (final java.io.IOException e)
		{
			throw new AssertionError("Cannot read " + file.toAbsolutePath() + " (run the test from the module directory)", e);
		}
	}

	/** Runs the whole script in one go (pgjdbc splits statements itself and respects dollar quoting). */
	private static void executeScript(final Connection c, final String sql) throws SQLException
	{
		execute(c, sql);
	}

	private static void execute(final Connection c, final String sql) throws SQLException
	{
		try (final Statement st = c.createStatement())
		{
			st.execute(sql);
		}
	}

	private static boolean viewExists(final Connection c, final String lowerCaseViewName) throws SQLException
	{
		try (final Statement st = c.createStatement();
			 final ResultSet rs = st.executeQuery("SELECT 1 FROM pg_views WHERE schemaname='public' AND viewname='" + lowerCaseViewName + "'"))
		{
			return rs.next();
		}
	}

	private static String viewDefinition(final Connection c, final String lowerCaseViewName) throws SQLException
	{
		try (final Statement st = c.createStatement();
			 final ResultSet rs = st.executeQuery("SELECT definition FROM pg_views WHERE schemaname='public' AND viewname='" + lowerCaseViewName + "'"))
		{
			assertThat(rs.next()).as("view " + lowerCaseViewName + " exists").isTrue();
			return rs.getString(1);
		}
	}

	private static List<String> columnsOf(final Connection c, final String lowerCaseViewName) throws SQLException
	{
		final List<String> columns = new ArrayList<>();
		try (final Statement st = c.createStatement();
			 final ResultSet rs = st.executeQuery("SELECT column_name FROM information_schema.columns WHERE table_schema='public' AND table_name='" + lowerCaseViewName + "' ORDER BY ordinal_position"))
		{
			while (rs.next())
			{
				columns.add(rs.getString(1));
			}
		}
		return columns;
	}
}
