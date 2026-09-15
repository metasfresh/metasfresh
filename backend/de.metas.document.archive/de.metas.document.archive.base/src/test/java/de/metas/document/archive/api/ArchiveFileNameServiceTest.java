/*
 * #%L
 * de.metas.document.archive.base
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

package de.metas.document.archive.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_Process;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the {@code AD_Process.FilenamePattern} resolver in {@link ArchiveFileNameService}:
 * placeholder vocabulary, unresolvable behaviour, and the {@code .pdf} extension rule.
 */
class ArchiveFileNameServiceTest
{
	@Test
	void allPlaceholdersPresent()
	{
		final Map<String, String> values = ImmutableMap.of(
				"orgname", "OrgA",
				"processname", "ProcessA",
				"recordid", "42",
				"pinstanceid", "999");

		final String result = ArchiveFileNameService.resolvePatternWith(
				"{orgname}-{processname}-{recordid}-{pinstanceid}", values);

		assertThat(result).isEqualTo("OrgA-ProcessA-42-999.pdf");
	}

	@Test
	void seedPattern_orgname_processname_pinstanceid()
	{
		// Pattern combining org name, process name, and pinstance id — guarantees a unique filename per run.
		final Map<String, String> values = ImmutableMap.of(
				"orgname", "OrgA",
				"processname", "ProcessA",
				"pinstanceid", "999");

		final String result = ArchiveFileNameService.resolvePatternWith(
				"{orgname}-{processname}-{pinstanceid}", values);

		assertThat(result).isEqualTo("OrgA-ProcessA-999.pdf");
	}

	@Test
	void unknownPlaceholderRemainsLiteral()
	{
		final Map<String, String> values = ImmutableMap.of(
				"orgname", "OrgA",
				"recordid", "42");

		final String result = ArchiveFileNameService.resolvePatternWith(
				"{orgname}-{foo}-{recordid}", values);

		// {foo} is not in the map → left as literal in the output
		assertThat(result).isEqualTo("OrgA-{foo}-42.pdf");
	}

	@Test
	void knownPlaceholderWithNullValueRemainsLiteral()
	{
		// {documentno} is known to the map but its resolved value is null
		// (e.g. master-data archive with no DocumentNo) → must stay literal.
		final Map<String, String> values = new HashMap<>();
		values.put("orgname", "OrgA");
		values.put("documentno", null);
		values.put("recordid", "42");

		final String result = ArchiveFileNameService.resolvePatternWith(
				"{orgname}-{documentno}-{recordid}", values);

		assertThat(result).isEqualTo("OrgA-{documentno}-42.pdf");
	}

	@Test
	void pdfExtensionIsAppendedWhenMissing()
	{
		final String result = ArchiveFileNameService.resolvePatternWith(
				"x", ImmutableMap.of());

		assertThat(result).isEqualTo("x.pdf");
	}

	@Test
	void pdfExtensionIsNotDoubled_lowercase()
	{
		final String result = ArchiveFileNameService.resolvePatternWith(
				"x.pdf", ImmutableMap.of());

		assertThat(result).isEqualTo("x.pdf");
	}

	@Test
	void pdfExtensionIsNotDoubled_mixedCase()
	{
		final String result = ArchiveFileNameService.resolvePatternWith(
				"x.PDF", ImmutableMap.of());

		// already ends in .pdf (case-insensitive) → not double-appended
		assertThat(result).isEqualTo("x.PDF");
	}

	@Test
	void placeholderAppearingMultipleTimesIsReplacedEverywhere()
	{
		final Map<String, String> values = ImmutableMap.of("orgname", "OrgA");

		final String result = ArchiveFileNameService.resolvePatternWith(
				"{orgname}-foo-{orgname}", values);

		assertThat(result).isEqualTo("OrgA-foo-OrgA.pdf");
	}

	@Test
	void emptyMapLeavesAllPlaceholdersLiteral()
	{
		final String result = ArchiveFileNameService.resolvePatternWith(
				"{orgname}-{processname}-{pinstanceid}", ImmutableMap.of());

		assertThat(result).isEqualTo("{orgname}-{processname}-{pinstanceid}.pdf");
	}

	@Test
	void extractPlaceholderNames_capturesEachDistinctTokenOnce()
	{
		final Set<String> names = ArchiveFileNameService.extractPlaceholderNames(
				"{orgname}-{processname}-{pinstanceid}-{orgname}");

		assertThat(names).isEqualTo(ImmutableSet.of("orgname", "processname", "pinstanceid"));
	}

	@Test
	void extractPlaceholderNames_emptyForPatternWithoutPlaceholders()
	{
		final Set<String> names = ArchiveFileNameService.extractPlaceholderNames("plain-filename");

		assertThat(names).isEmpty();
	}

	@Test
	void dateToken_replacedWithCurrentTimeInGivenFormat()
	{
		final String result = ArchiveFileNameService.resolveDateTokens("prefix-${date:yyyy}-suffix", ZoneId.of("UTC"));

		assertThat(result).matches("prefix-\\d{4}-suffix");
	}

	@Test
	void dateToken_multipleOccurrencesAllResolve()
	{
		final String result = ArchiveFileNameService.resolveDateTokens(
				"${date:yyyy}-${date:MM}-${date:dd}", ZoneId.of("UTC"));

		assertThat(result).matches("\\d{4}-\\d{2}-\\d{2}");
	}

	@Test
	void dateToken_invalidFormatStaysLiteral()
	{
		// Unmatched single quote — DateTimeFormatter rejects it; the token is preserved verbatim in the output.
		final String pattern = "stamp-${date:'unterminated}-end";

		final String result = ArchiveFileNameService.resolveDateTokens(pattern, ZoneId.of("UTC"));

		assertThat(result).isEqualTo(pattern);
	}

	@Test
	void dateToken_returnsInputUnchangedWhenNoToken()
	{
		final String pattern = "plain-{orgname}-text";

		final String result = ArchiveFileNameService.resolveDateTokens(pattern, ZoneId.of("UTC"));

		// resolveDateTokens only touches ${date:...} — regular {name} placeholders pass through untouched.
		assertThat(result).isEqualTo(pattern);
	}

	@Test
	void resolveFilenamePattern_readsOrgAndProcessValuesFromRecords()
	{
		AdempiereTestHelper.get().init();

		// createOrgWithTimeZone sets Name=Value=same; update Name afterward so {orgname} and {orgvalue} can be
		// told apart in the result.
		final OrgId orgId = AdempiereTestHelper.createOrgWithTimeZone("OrgValue", ZoneId.of("Europe/Berlin"));
		final I_AD_Org org = load(orgId.getRepoId(), I_AD_Org.class);
		org.setName("OrgName");
		saveRecord(org);

		final I_AD_Process process = newInstance(I_AD_Process.class);
		process.setName("ProcessName");
		process.setValue("ProcessValue");
		saveRecord(process);

		final I_AD_Archive archive = newInstance(I_AD_Archive.class);
		archive.setAD_Org_ID(orgId.getRepoId());
		archive.setAD_Process_ID(process.getAD_Process_ID());
		saveRecord(archive);

		final ArchiveFileNameService service = ArchiveFileNameService.newInstanceForUnitTesting();

		final String result = service.resolveFilenamePattern(
				"{orgname}-{orgvalue}-{processname}-{processvalue}", archive);

		assertThat(result).isEqualTo("OrgName-OrgValue-ProcessName-ProcessValue.pdf");
	}

	@Test
	void resolveFilenamePattern_dateTokenUsesOrgTimezone()
	{
		AdempiereTestHelper.get().init();
		// 12:00 UTC. Europe/Berlin is UTC+2 in May (CEST) — local hour at 14, dated the same calendar day.
		SystemTime.setFixedTimeSource(ZonedDateTime.parse("2026-05-11T12:00:00Z"));
		try
		{
			final OrgId orgId = AdempiereTestHelper.createOrgWithTimeZone("BerlinOrg", ZoneId.of("Europe/Berlin"));

			final I_AD_Archive archive = newInstance(I_AD_Archive.class);
			archive.setAD_Org_ID(orgId.getRepoId());
			saveRecord(archive);

			final ArchiveFileNameService service = ArchiveFileNameService.newInstanceForUnitTesting();

			final String result = service.resolveFilenamePattern("${date:yyyy-MM-dd_HH}", archive);

			assertThat(result).isEqualTo("2026-05-11_14.pdf");
		}
		finally
		{
			SystemTime.resetTimeSource();
		}
	}
}
