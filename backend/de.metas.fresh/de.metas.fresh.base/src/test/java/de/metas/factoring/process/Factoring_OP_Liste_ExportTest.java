/*
 * #%L
 * de.metas.fresh.base
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

package de.metas.factoring.process;

import de.metas.organization.OrgId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link Factoring_OP_Liste_Export}.
 *
 * <p>Uses the in-memory {@link AdempiereTestHelper} framework so no live database is required —
 * every metasfresh service call resolves against the POJO-backed test infra. The SQL function
 * ({@link FactoringOpListeRepository#loadOpListRows}) is mocked because {@code AdempiereTestHelper}
 * cannot execute Postgres plpgsql. The two SQL-function-native tests live in
 * {@code Factoring_OP_Liste_SqlFunctionIT} which does require a local Postgres.
 */
class Factoring_OP_Liste_ExportTest
{
	private static final int AD_CLIENT_ID = 1;
	private static final int AD_ORG_ID_REPO = 100;
	private static final OrgId AD_ORG_ID = OrgId.ofRepoId(AD_ORG_ID_REPO);
	private static final int EUR_CURRENCY_ID = 102;

	private FactoringOpListeRepository repoMock;
	private Factoring_OP_Liste_Export process;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repoMock = mock(FactoringOpListeRepository.class);
		process = new Factoring_OP_Liste_Export();
		process.setFactoringRepoForTesting(repoMock);

		// Seed the org referenced by AD_ORG_ID so resolveOrgName has a real name to return.
		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setAD_Org_ID(AD_ORG_ID_REPO);
		org.setValue("TSTORG");
		org.setName("Test Organisation");
		save(org);
	}

	/** Creates + saves a factorer BPartner with the given field values. */
	private I_C_BPartner insertFactorer(final String name, final String contractNo, final String clientAccountId)
	{
		final I_C_BPartner bp = newInstance(I_C_BPartner.class);
		bp.setAD_Org_ID(AD_ORG_ID_REPO);
		bp.setName(name);
		bp.setValue(name);
		bp.setIsFactorer(true);
		bp.setFactoringContractNo(contractNo);
		bp.setFactoringClientAccountId(clientAccountId);
		save(bp);
		return bp;
	}

	// -------------------------------------------------------------------------
	// AC6 error paths — 5 tests
	// -------------------------------------------------------------------------

	@Test
	void process_fails_when_role_scope_is_all_orgs()
	{
		// orgId = 0 → role scope '*' (all orgs). Refused before any BP lookup.
		assertThatThrownBy(() -> process.runExport(0, AD_CLIENT_ID, EUR_CURRENCY_ID))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Factoring_OP_Liste_EXT_RoleScopeAllOrgs");
	}

	@Test
	void process_fails_when_no_factorer_bp_for_org()
	{
		// No factorer inserted. The DAO returns an empty list → AdempiereException.
		assertThatThrownBy(() -> process.runExport(AD_ORG_ID_REPO, AD_CLIENT_ID, EUR_CURRENCY_ID))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Factoring_OP_Liste_EXT_NoFactorer");
	}

	@Test
	void process_fails_when_multiple_factorer_bps_for_org()
	{
		insertFactorer("Faktoring Alpha GmbH", "DE-A001", "1111111111");
		insertFactorer("Faktoring Beta GmbH", "DE-B001", "2222222222");

		assertThatThrownBy(() -> process.runExport(AD_ORG_ID_REPO, AD_CLIENT_ID, EUR_CURRENCY_ID))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Factoring_OP_Liste_EXT_MultipleFactorers")
				.hasMessageContaining("Faktoring Alpha GmbH")
				.hasMessageContaining("Faktoring Beta GmbH");
	}

	@Test
	void process_fails_when_factorer_has_empty_contract_no()
	{
		insertFactorer("Faktoring Ohne Vertrag", null, "9999999999");

		assertThatThrownBy(() -> process.runExport(AD_ORG_ID_REPO, AD_CLIENT_ID, EUR_CURRENCY_ID))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Factoring_OP_Liste_EXT_MissingContractNo")
				.hasMessageContaining("Faktoring Ohne Vertrag");
	}

	@Test
	void process_fails_when_factorer_has_empty_client_account_id()
	{
		insertFactorer("Faktoring Ohne Konto", "DE-NOACCT", null);

		assertThatThrownBy(() -> process.runExport(AD_ORG_ID_REPO, AD_CLIENT_ID, EUR_CURRENCY_ID))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Factoring_OP_Liste_EXT_MissingClientAccountId")
				.hasMessageContaining("Faktoring Ohne Konto");
	}

	// -------------------------------------------------------------------------
	// Happy path: byte-exact CSV
	// -------------------------------------------------------------------------

	@Test
	void process_produces_expected_csv_byte_for_byte() throws Exception
	{
		insertFactorer("Test-Factor GmbH", "DE00001", "2500000000");

		final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

		// Repository is mocked — return the exact row-set the SQL function would produce for the fixture.
		final List<String[]> fixtureRows = Arrays.asList(
				// Header row: row_type + col_1..col_11
				new String[] {"01", "SAF", "EFAG", "DE00001", "2500000000", "EUR", "", today, "5,00", "1500,00", "300,00", ""},
				// 4 detail rows: row_type + col_1..col_11
				new String[] {"02", "CUST-AA3", "Alpha Kunde GmbH", "INV-AA3-001", "01.09.2025", "01.10.2025", "EUR", "1000,00", "750,00", "D", "", ""},
				new String[] {"02", "CUST-AA3", "Alpha Kunde GmbH", "CR-AA3-001", "05.09.2025", "01.10.2025", "EUR", "200,00", "200,00", "C", "", ""},
				new String[] {"02", "CUST-BB3", "Beta Kunde AG", "INV-BB3-001", "01.09.2025", "01.10.2025", "EUR", "500,00", "500,00", "D", "", ""},
				new String[] {"02", "CUST-BB3", "Beta Kunde AG", "CR-BB3-001", "05.09.2025", "01.10.2025", "EUR", "100,00", "100,00", "C", "", ""}
		);
		when(repoMock.loadOpListRows(EUR_CURRENCY_ID, AD_ORG_ID_REPO, AD_CLIENT_ID)).thenReturn(fixtureRows);

		final Factoring_OP_Liste_Export.ExportResult result =
				process.runExport(AD_ORG_ID_REPO, AD_CLIENT_ID, EUR_CURRENCY_ID);

		final String todayForFilename = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		assertThat(result.filename)
				.as("filename convention: <ContractNo>_INH_<yyyyMMdd>.csv")
				.isEqualTo("DE00001_INH_" + todayForFilename + ".csv");

		final byte[] actualBytes = result.bytes;

		// BOM check
		assertThat(actualBytes).as("file has at least 3 bytes (BOM)").hasSizeGreaterThanOrEqualTo(3);
		assertThat(actualBytes[0]).as("BOM byte 0 = 0xEF").isEqualTo((byte) 0xEF);
		assertThat(actualBytes[1]).as("BOM byte 1 = 0xBB").isEqualTo((byte) 0xBB);
		assertThat(actualBytes[2]).as("BOM byte 2 = 0xBF").isEqualTo((byte) 0xBF);

		// Every LF must be paired with a preceding CR
		for (int i = 0; i < actualBytes.length; i++)
		{
			if (actualBytes[i] == (byte) 0x0A)
			{
				assertThat(i).as("LF at position " + i + " must be preceded by CR").isGreaterThan(0);
				assertThat(actualBytes[i - 1]).as("LF at position " + i + " preceded by CR").isEqualTo((byte) 0x0D);
			}
		}

		// Line-by-line content check
		final String content = new String(actualBytes, 3, actualBytes.length - 3, java.nio.charset.StandardCharsets.UTF_8);
		final String[] lines = content.split("\r\n", -1);
		assertThat(lines).as("5 content lines + empty trailing element").hasSize(6);
		assertThat(lines[5]).as("last element empty (trailing CRLF)").isEmpty();

		// Header line — 11 tokens; header field 11 = sum C (300,00) so no trailing ';'
		assertThat(lines[0]).as("header line").isEqualTo(
				"01;SAF;EFAG;DE00001;2500000000;EUR;;" + today + ";5,00;1500,00;300,00");

		// Detail rows — 11 tokens; detail field 11 = '' so trailing ';' after D/C flag
		assertThat(lines[1]).as("detail 1")
				.isEqualTo("02;CUST-AA3;Alpha Kunde GmbH;INV-AA3-001;01.09.2025;01.10.2025;EUR;1000,00;750,00;D;");
		assertThat(lines[2]).as("detail 2")
				.isEqualTo("02;CUST-AA3;Alpha Kunde GmbH;CR-AA3-001;05.09.2025;01.10.2025;EUR;200,00;200,00;C;");
		assertThat(lines[3]).as("detail 3")
				.isEqualTo("02;CUST-BB3;Beta Kunde AG;INV-BB3-001;01.09.2025;01.10.2025;EUR;500,00;500,00;D;");
		assertThat(lines[4]).as("detail 4")
				.isEqualTo("02;CUST-BB3;Beta Kunde AG;CR-BB3-001;05.09.2025;01.10.2025;EUR;100,00;100,00;C;");

		assertThat(result.rowCount).as("data row count = total rows − 1 header").isEqualTo(4);
	}

	@Test
	void process_produces_header_only_csv_when_no_matching_rows() throws Exception
	{
		insertFactorer("Test-Factor GmbH", "DE00001", "2500000000");

		final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		final List<String[]> headerOnly = Collections.singletonList(
				new String[] {"01", "SAF", "EFAG", "DE00001", "2500000000", "EUR", "", today, "1,00", "0,00", "0,00", ""}
		);
		when(repoMock.loadOpListRows(EUR_CURRENCY_ID, AD_ORG_ID_REPO, AD_CLIENT_ID)).thenReturn(headerOnly);

		final Factoring_OP_Liste_Export.ExportResult result =
				process.runExport(AD_ORG_ID_REPO, AD_CLIENT_ID, EUR_CURRENCY_ID);

		final String content = new String(result.bytes, 3, result.bytes.length - 3, java.nio.charset.StandardCharsets.UTF_8);
		final String[] lines = content.split("\r\n", -1);
		assertThat(lines).hasSize(2); // 1 header + 1 empty trailing
		assertThat(lines[0]).isEqualTo("01;SAF;EFAG;DE00001;2500000000;EUR;;" + today + ";1,00;0,00;0,00");
		assertThat(lines[1]).isEmpty();
		assertThat(result.rowCount).as("data row count = 0 when only header").isZero();
	}
}
