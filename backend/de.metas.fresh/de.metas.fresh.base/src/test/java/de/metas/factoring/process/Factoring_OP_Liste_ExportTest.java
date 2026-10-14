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

import com.google.common.collect.ImmutableList;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link Factoring_OP_Liste_Export} JavaProcess.
 *
 * <p>Exercises the package-private {@code runExport(orgIdRepo, currencyIdRepo)} method — the
 * same code path {@code doIt()} runs, but without needing a full {@code ProcessInfo}. The
 * service is mocked with Mockito; the writer runs for real so byte-level CSV shape is
 * validated end-to-end.
 *
 * <p>The service's own AC6 error paths (missing/ambiguous factorer configuration) are exercised
 * by configuring the mock to throw {@link AdempiereException} with the appropriate {@code @key@}
 * marker — the process passes the exception through unchanged.
 */
class Factoring_OP_Liste_ExportTest
{
	private static final int AD_ORG_ID_REPO = 100;
	private static final OrgId AD_ORG_ID = OrgId.ofRepoId(AD_ORG_ID_REPO);
	private static final int EUR_CURRENCY_ID_REPO = 102;
	private static final CurrencyId EUR = CurrencyId.ofRepoId(EUR_CURRENCY_ID_REPO);
	private static final LocalDate UPLOAD_DATE = LocalDate.of(2026, 7, 23);
	private static final String UPLOAD_DATE_STR = "23.07.2026";
	private static final String UPLOAD_DATE_FILENAME = "20260723";

	private FactoringOpListeService serviceMock;
	private Factoring_OP_Liste_Export process;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		serviceMock = mock(FactoringOpListeService.class);
		process = new Factoring_OP_Liste_Export();
		process.setServiceForTesting(serviceMock);
	}

	// -------------------------------------------------------------------------
	// Role-scope validation (in the process itself)
	// -------------------------------------------------------------------------

	@Test
	void role_scope_all_orgs_is_rejected()
	{
		assertThatThrownBy(() -> process.runExport(0, EUR_CURRENCY_ID_REPO))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Factoring_OP_Liste_EXT_RoleScopeAllOrgs");
	}

	// -------------------------------------------------------------------------
	// AC6 error paths (surface via the service throwing AdempiereException with @key@)
	// -------------------------------------------------------------------------

	@Test
	void no_factorer_yields_AdempiereException()
	{
		when(serviceMock.buildExportData(any(OrgId.class), any(CurrencyId.class)))
				.thenThrow(new AdempiereException("@Factoring_OP_Liste_EXT_NoFactorer@ Test-Org")
						.markAsUserValidationError());

		assertThatThrownBy(() -> process.runExport(AD_ORG_ID_REPO, EUR_CURRENCY_ID_REPO))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Factoring_OP_Liste_EXT_NoFactorer");
	}

	@Test
	void multiple_factorers_yields_AdempiereException()
	{
		when(serviceMock.buildExportData(any(OrgId.class), any(CurrencyId.class)))
				.thenThrow(new AdempiereException(
						"@Factoring_OP_Liste_EXT_MultipleFactorers@ Test-Org: Factor A, Factor B")
						.markAsUserValidationError());

		assertThatThrownBy(() -> process.runExport(AD_ORG_ID_REPO, EUR_CURRENCY_ID_REPO))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Factoring_OP_Liste_EXT_MultipleFactorers")
				.hasMessageContaining("Factor A")
				.hasMessageContaining("Factor B");
	}

	@Test
	void missing_contract_no_yields_AdempiereException()
	{
		when(serviceMock.buildExportData(any(OrgId.class), any(CurrencyId.class)))
				.thenThrow(new AdempiereException("@Factoring_OP_Liste_EXT_MissingContractNo@ Test-Factor GmbH")
						.markAsUserValidationError());

		assertThatThrownBy(() -> process.runExport(AD_ORG_ID_REPO, EUR_CURRENCY_ID_REPO))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Factoring_OP_Liste_EXT_MissingContractNo")
				.hasMessageContaining("Test-Factor GmbH");
	}

	@Test
	void missing_client_account_id_yields_AdempiereException()
	{
		when(serviceMock.buildExportData(any(OrgId.class), any(CurrencyId.class)))
				.thenThrow(new AdempiereException(
						"@Factoring_OP_Liste_EXT_MissingClientAccountId@ Test-Factor GmbH")
						.markAsUserValidationError());

		assertThatThrownBy(() -> process.runExport(AD_ORG_ID_REPO, EUR_CURRENCY_ID_REPO))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Factoring_OP_Liste_EXT_MissingClientAccountId")
				.hasMessageContaining("Test-Factor GmbH");
	}

	// -------------------------------------------------------------------------
	// Happy paths
	// -------------------------------------------------------------------------

	@Test
	void produces_expected_csv_byte_for_byte() throws Exception
	{
		final ImmutableList<FactoringOpListeDetailRow> detail = ImmutableList.of(
				row("CUST-AA3", "Alpha Kunde GmbH", "INV-AA3-001",
						LocalDate.of(2025, 9, 1), LocalDate.of(2025, 10, 1),
						new BigDecimal("1000.00"), new BigDecimal("750.00"),
						FactoringOpListeDetailRow.DebitCreditFlag.D),
				row("CUST-AA3", "Alpha Kunde GmbH", "CR-AA3-001",
						LocalDate.of(2025, 9, 5), LocalDate.of(2025, 10, 1),
						new BigDecimal("200.00"), new BigDecimal("200.00"),
						FactoringOpListeDetailRow.DebitCreditFlag.C),
				row("CUST-BB3", "Beta Kunde AG", "INV-BB3-001",
						LocalDate.of(2025, 9, 1), LocalDate.of(2025, 10, 1),
						new BigDecimal("500.00"), new BigDecimal("500.00"),
						FactoringOpListeDetailRow.DebitCreditFlag.D),
				row("CUST-BB3", "Beta Kunde AG", "CR-BB3-001",
						LocalDate.of(2025, 9, 5), LocalDate.of(2025, 10, 1),
						new BigDecimal("100.00"), new BigDecimal("100.00"),
						FactoringOpListeDetailRow.DebitCreditFlag.C));

		final FactoringOpListeExportData data = new FactoringOpListeExportData(
				"DE00001", "2500000000", "EUR", UPLOAD_DATE, detail,
				new BigDecimal("1500.00"), new BigDecimal("300.00"));
		when(serviceMock.buildExportData(AD_ORG_ID, EUR)).thenReturn(data);

		final Factoring_OP_Liste_Export.ExportResult result =
				process.runExport(AD_ORG_ID_REPO, EUR_CURRENCY_ID_REPO);

		assertThat(result.getFilename()).isEqualTo("DE00001_INH_" + UPLOAD_DATE_FILENAME + ".csv");
		assertThat(result.getDataRowCount()).isEqualTo(4);

		final byte[] bytes = result.getBytes();
		assertThat(bytes[0]).as("BOM 0").isEqualTo((byte) 0xEF);
		assertThat(bytes[1]).as("BOM 1").isEqualTo((byte) 0xBB);
		assertThat(bytes[2]).as("BOM 2").isEqualTo((byte) 0xBF);

		for (int i = 0; i < bytes.length; i++)
		{
			if (bytes[i] == (byte) 0x0A)
			{
				assertThat(bytes[i - 1]).as("LF at " + i + " preceded by CR").isEqualTo((byte) 0x0D);
			}
		}

		final String content = new String(bytes, 3, bytes.length - 3, java.nio.charset.StandardCharsets.UTF_8);
		final String[] lines = content.split("\r\n", -1);
		assertThat(lines).hasSize(6);
		assertThat(lines[5]).isEmpty();

		assertThat(lines[0]).isEqualTo(
				"01;SAF;EFAG;DE00001;2500000000;EUR;;" + UPLOAD_DATE_STR + ";5,00;1500,00;300,00");
		assertThat(lines[1]).isEqualTo(
				"02;CUST-AA3;Alpha Kunde GmbH;INV-AA3-001;01.09.2025;01.10.2025;EUR;1000,00;750,00;D;");
		assertThat(lines[2]).isEqualTo(
				"02;CUST-AA3;Alpha Kunde GmbH;CR-AA3-001;05.09.2025;01.10.2025;EUR;200,00;200,00;C;");
		assertThat(lines[3]).isEqualTo(
				"02;CUST-BB3;Beta Kunde AG;INV-BB3-001;01.09.2025;01.10.2025;EUR;500,00;500,00;D;");
		assertThat(lines[4]).isEqualTo(
				"02;CUST-BB3;Beta Kunde AG;CR-BB3-001;05.09.2025;01.10.2025;EUR;100,00;100,00;C;");
	}

	@Test
	void produces_header_only_csv_when_no_matching_invoices() throws Exception
	{
		final FactoringOpListeExportData data = new FactoringOpListeExportData(
				"DE00001", "2500000000", "EUR", UPLOAD_DATE,
				ImmutableList.of(), BigDecimal.ZERO, BigDecimal.ZERO);
		when(serviceMock.buildExportData(AD_ORG_ID, EUR)).thenReturn(data);

		final Factoring_OP_Liste_Export.ExportResult result =
				process.runExport(AD_ORG_ID_REPO, EUR_CURRENCY_ID_REPO);

		final byte[] bytes = result.getBytes();
		final String content = new String(bytes, 3, bytes.length - 3, java.nio.charset.StandardCharsets.UTF_8);
		final String[] lines = content.split("\r\n", -1);
		assertThat(lines).hasSize(2);
		assertThat(lines[0]).isEqualTo("01;SAF;EFAG;DE00001;2500000000;EUR;;" + UPLOAD_DATE_STR + ";1,00;0,00;0,00");
		assertThat(result.getDataRowCount()).isZero();
	}

	private static FactoringOpListeDetailRow row(final String value, final String name, final String documentNo,
			final LocalDate dateInvoiced, final LocalDate dueDate,
			final BigDecimal grandTotal, final BigDecimal openAmount,
			final FactoringOpListeDetailRow.DebitCreditFlag dcFlag)
	{
		return FactoringOpListeDetailRow.builder()
				.debitorNo(value)
				.debitorName(name)
				.documentNo(documentNo)
				.dateInvoiced(dateInvoiced)
				.dueDate(dueDate)
				.grandTotal(grandTotal)
				.openAmount(openAmount)
				.debitCreditFlag(dcFlag)
				.build();
	}
}
