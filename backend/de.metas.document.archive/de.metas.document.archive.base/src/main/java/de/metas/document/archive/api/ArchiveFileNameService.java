/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

import com.google.common.annotations.VisibleForTesting;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.organization.Org;
import de.metas.organization.OrgId;
import de.metas.organization.OrgRepository;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.compiere.Adempiere;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Table;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ArchiveFileNameService
{
	@NonNull private final OrgRepository orgRepository;
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	@NonNull private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);

	private static final Logger logger = LogManager.getLogger(ArchiveFileNameService.class);
	private static final String PDF_EXTENSION = ".pdf";
	// Bounded length + possessive quantifier — guards against polynomial backtracking on pathological input
	// (CodeQL alert: polynomial regular expression on uncontrolled data). Placeholder names are short.
	private static final Pattern PLACEHOLDER_TOKEN = Pattern.compile("\\{([a-z]{1,32}+)}");
	// Date expression inspired by Camel's Simple language, e.g. ${date:yyyyMMdd_HHmmss}. Format string is
	// anything before the closing brace; it's passed to DateTimeFormatter. Resolves to "now" formatted in the
	// archive's org timezone (falls back to JVM default if the archive has no regular org).
	// Bounded length + possessive quantifier — guards against polynomial backtracking on pathological input.
	private static final Pattern DATE_TOKEN = Pattern.compile("\\$\\{date:([^}]{1,100}+)}");

	@VisibleForTesting
	public static ArchiveFileNameService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				ArchiveFileNameService.class,
				() -> new ArchiveFileNameService(OrgRepository.newInstanceForUnitTesting()));
	}

	public String computePdfFileName(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final ComputeFileNameRequest request = ComputeFileNameRequest.builder()
				.docTypeId(DocTypeId.ofRepoIdOrNull(docOutboundLogRecord.getC_DocType_ID()))
				.documentNo(docOutboundLogRecord.getDocumentNo())
				.orgId(OrgId.ofRepoIdOrNull(docOutboundLogRecord.getAD_Org_ID()))
				.recordReference(TableRecordReference.ofReferenced(docOutboundLogRecord))
				.build();

		return computeFileName(request);
	}

	/**
	 * Decides between {@code AD_Process.FilenamePattern} resolution (when set on the archive's process), the
	 * outbound-log overload (richer customer-facing data when a log exists), and the archive's legacy fallback
	 * — in that order. Callers with both an archive and its outbound log should prefer this overload so the
	 * pattern lookup is done once.
	 */
	public String computePdfFileName(
			@NonNull final I_AD_Archive archiveRecord,
			@Nullable final I_C_Doc_Outbound_Log outboundLogRecord)
	{
		// Per-process filename pattern wins when set on AD_Process.FilenamePattern
		final AdProcessId processId = AdProcessId.ofRepoIdOrNull(archiveRecord.getAD_Process_ID());
		if (processId != null)
		{
			final String pattern = adProcessDAO.getFilenamePattern(processId).orElse(null);
			if (pattern != null)
			{
				return resolveFilenamePattern(pattern, archiveRecord);
			}
		}

		// Legacy: prefer the outbound-log overload when available — it carries DocType + DocumentNo verbatim.
		if (outboundLogRecord != null)
		{
			return computePdfFileName(outboundLogRecord);
		}

		final TableRecordReference recordReference = TableRecordReference.ofReferenced(archiveRecord);
		final Object referencedRecord = archiveDAO.retrieveReferencedModel(archiveRecord, Object.class);
		final IDocument documentNorNull = referencedRecord != null ? documentBL.getDocumentOrNull(referencedRecord) : null;

		final ComputeFileNameRequest.ComputeFileNameRequestBuilder request = ComputeFileNameRequest.builder();

		if (documentNorNull != null)
		{
			request.documentNo(documentNorNull.getDocumentNo());

			final I_C_DocType docType = documentBL.getDocTypeOrNull(referencedRecord);
			if (docType != null)
			{
				request.docTypeId(DocTypeId.ofRepoIdOrNull(docType.getC_DocType_ID()));
			}

		}

		request.orgId(OrgId.ofRepoIdOrNull(archiveRecord.getAD_Org_ID()))
				.recordReference(recordReference);

		return computeFileName(request.build());
	}

	/**
	 * Resolves {@code AD_Process.FilenamePattern} against the given archive's context. Supported placeholders:
	 * {@code {orgname}}, {@code {orgvalue}}, {@code {doctype}}, {@code {tablename}}, {@code {processname}},
	 * {@code {processvalue}}, {@code {documentno}}, {@code {recordid}}, {@code {pinstanceid}}, plus the
	 * date expression {@code ${date:<DateTimeFormatter-pattern>}} (e.g. {@code ${date:yyyyMMdd_HHmmss}}) which
	 * formats the current time in the archive's org timezone. Unresolvable placeholders are left unchanged in
	 * the output (matching {@code ExternalSystem_Endpoint.SftpFilenamePattern}'s behaviour). The {@code .pdf}
	 * extension is appended if missing.
	 */
	@VisibleForTesting
	String resolveFilenamePattern(@NonNull final String pattern, @NonNull final I_AD_Archive archive)
	{
		final Set<String> placeholders = extractPlaceholderNames(pattern);
		final boolean hasDateToken = DATE_TOKEN.matcher(pattern).find();
		if (placeholders.isEmpty() && !hasDateToken)
		{
			// Without a placeholder, every print of this process produces the exact same filename, so each new
			// PDF overwrites the previous one at the destination. Almost always a misconfiguration.
			logger.warn("AD_Process.FilenamePattern='{}' (AD_Process_ID={}) has no placeholders — every print will produce the same filename and overwrite the previous output. Add a uniqueness placeholder such as '${date:yyyyMMdd_HHmmss}' or '{pinstanceid}'.",
					pattern, archive.getAD_Process_ID());
		}
		final String dateExpanded = hasDateToken
				? resolveDateTokens(pattern, resolveZoneFor(archive))
				: pattern;
		return resolvePatternWith(dateExpanded, buildPlaceholderMap(archive, placeholders));
	}

	@NonNull
	private ZoneId resolveZoneFor(@NonNull final I_AD_Archive archive)
	{
		final OrgId orgId = OrgId.ofRepoIdOrNull(archive.getAD_Org_ID());
		if (orgId != null)
		{
			return orgRepository.getById(orgId).getTimeZone();
		}
		return ZoneId.systemDefault();
	}

	/**
	 * Replaces every {@code ${date:<format>}} occurrence with "now" formatted via {@link DateTimeFormatter} in
	 * the given {@code zone}. "Now" comes from {@link SystemTime#asInstant()} (testable). Invalid format
	 * strings are left as literal text in the output and logged at warn level.
	 */
	@VisibleForTesting
	static String resolveDateTokens(@NonNull final String pattern, @NonNull final ZoneId zone)
	{
		final Matcher matcher = DATE_TOKEN.matcher(pattern);
		final ZonedDateTime now = SystemTime.asInstant().atZone(zone);
		final StringBuffer sb = new StringBuffer();
		while (matcher.find())
		{
			final String fmt = matcher.group(1);
			String replacement;
			try
			{
				replacement = DateTimeFormatter.ofPattern(fmt).format(now);
			}
			catch (final IllegalArgumentException ex)
			{
				logger.warn("AD_Process.FilenamePattern contains invalid date format '{}': {}", fmt, ex.getMessage());
				replacement = matcher.group(0); // keep the original ${date:<bad>} literal
			}
			matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	@VisibleForTesting
	static Set<String> extractPlaceholderNames(@NonNull final String pattern)
	{
		final Set<String> names = new HashSet<>();
		final Matcher matcher = PLACEHOLDER_TOKEN.matcher(pattern);
		while (matcher.find())
		{
			names.add(matcher.group(1));
		}
		return names;
	}

	@VisibleForTesting
	static String resolvePatternWith(@NonNull final String pattern, @NonNull final Map<String, String> placeholders)
	{
		String result = pattern;
		for (final Map.Entry<String, String> entry : placeholders.entrySet())
		{
			final String value = entry.getValue();
			if (value == null)
			{
				continue; // leave the literal {name} in place
			}
			result = result.replace("{" + entry.getKey() + "}", value);
		}
		if (!result.toLowerCase().endsWith(PDF_EXTENSION))
		{
			result = result + PDF_EXTENSION;
		}
		return result;
	}

	/**
	 * Builds only the placeholder values the pattern actually references.
	 * Loads (org, table, referenced record, process) are gated on the relevant placeholder appearing
	 * in {@code required}, so a pattern like {@code {orgname}-{processname}-{pinstanceid}} doesn't
	 * touch the AD_Table or referenced-record paths.
	 */
	private Map<String, String> buildPlaceholderMap(@NonNull final I_AD_Archive archive, @NonNull final Set<String> required)
	{
		final Map<String, String> map = new LinkedHashMap<>();

		// archive.AD_Language is nullable; fall back to the runtime context language.
		final String adLanguage = CoalesceUtil.coalesceNotNull(
				archive.getAD_Language(),
				Env.getAD_Language(InterfaceWrapperHelper.getCtx(archive)));

		// {orgname} / {orgvalue} — both come from the same Org record; load once via the cached map.
		// ANY (id=0) resolves to Name="*" / Value="0" — well-formed and informative; better than leaving the
		// literal {orgname}/{orgvalue} token in the output.
		if (required.contains("orgname") || required.contains("orgvalue"))
		{
			final OrgId orgId = OrgId.ofRepoIdOrNull(archive.getAD_Org_ID());
			if (orgId != null)
			{
				final Org org = orgRepository.getById(orgId);
				if (required.contains("orgname"))
				{
					map.put("orgname", org.getName());
				}
				if (required.contains("orgvalue"))
				{
					map.put("orgvalue", org.getValue());
				}
			}
		}

		// {documentno}
		if (required.contains("documentno") && Check.isNotBlank(archive.getDocumentNo()))
		{
			map.put("documentno", archive.getDocumentNo());
		}

		// {pinstanceid}
		if (required.contains("pinstanceid"))
		{
			final PInstanceId pInstanceId = PInstanceId.ofRepoIdOrNull(archive.getAD_PInstance_ID());
			if (pInstanceId != null)
			{
				map.put("pinstanceid", Integer.toString(pInstanceId.getRepoId()));
			}
		}

		// {tablename} — translated table name (doesn't require a valid record id)
		if (required.contains("tablename"))
		{
			final AdTableId tableId = AdTableId.ofRepoIdOrNull(archive.getAD_Table_ID());
			if (tableId != null)
			{
				map.put("tablename", tableDAO.getTableNameTrl(tableId).translate(adLanguage));
			}
		}

		// {recordid} and {doctype} — both need a valid (table, record) pair; {doctype} additionally loads the referenced model
		if (required.contains("recordid") || required.contains("doctype"))
		{
			final TableRecordReference recordRef = TableRecordReference.ofReferencedOrNull(archive);
			if (recordRef != null)
			{
				if (required.contains("recordid"))
				{
					map.put("recordid", Integer.toString(recordRef.getRecord_ID()));
				}
				if (required.contains("doctype"))
				{
					final Object referencedRecord = archiveDAO.retrieveReferencedModel(archive, Object.class);
					if (referencedRecord != null)
					{
						final I_C_DocType docType = documentBL.getDocTypeOrNull(referencedRecord);
						if (docType != null)
						{
							final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(docType.getC_DocType_ID());
							if (docTypeId != null)
							{
								map.put("doctype", docTypeDAO.getDocTypeNameTrl(docTypeId).translate(adLanguage));
							}
						}
					}
				}
			}
		}

		// {processname} (translated AD_Process.Name) / {processvalue} (AD_Process.Value — stable, untranslated)
		// Admin's responsibility to keep both filename-safe.
		if (required.contains("processname") || required.contains("processvalue"))
		{
			final AdProcessId processId = AdProcessId.ofRepoIdOrNull(archive.getAD_Process_ID());
			if (processId != null)
			{
				// load once — used by both the translated-name overload and the .Value() read
				final I_AD_Process processRecord = adProcessDAO.getById(processId);
				if (required.contains("processname"))
				{
					map.put("processname", adProcessDAO.getProcessName(processRecord).translate(adLanguage));
				}
				if (required.contains("processvalue"))
				{
					map.put("processvalue", processRecord.getValue());
				}
			}
		}

		return map;
	}

	public String computeFileName(@NonNull final ComputeFileNameRequest computeFileNameRequest)
	{
		final StringJoiner fileNameParts = new StringJoiner("-");

		if (computeFileNameRequest.getOrgId() != null && computeFileNameRequest.getOrgId().isRegular())
		{
			fileNameParts.add(orgRepository.getById(computeFileNameRequest.getOrgId()).getName());
		}

		if (computeFileNameRequest.getDocTypeId() != null)
		{
			final I_C_DocType docTypeRecord = docTypeDAO.getById(computeFileNameRequest.getDocTypeId());
			final I_C_DocType docTypeRecordTrl = InterfaceWrapperHelper.translate(docTypeRecord, I_C_DocType.class);
			fileNameParts.add(docTypeRecordTrl.getName());
		}
		else
		{
			final I_AD_Table tableRecord = tableDAO.retrieveTable(computeFileNameRequest.getRecordReference().getAdTableId());
			final I_AD_Table tableRecordTrl = InterfaceWrapperHelper.translate(tableRecord, I_AD_Table.class);
			fileNameParts.add(tableRecordTrl.getName());
		}

		if (Check.isNotBlank(computeFileNameRequest.getDocumentNo()))
		{
			fileNameParts.add(computeFileNameRequest.getDocumentNo());
		}
		else
		{
			fileNameParts.add(Integer.toString(computeFileNameRequest.getRecordReference().getRecord_ID()));
		}

		if (Check.isNotBlank(computeFileNameRequest.getSuffix()))
		{
			fileNameParts.add(computeFileNameRequest.getSuffix());
		}

		final String fileName = fileNameParts.toString();
		return fileName + computeFileNameRequest.fileExtension;
	}

	@Value
	public static class ComputeFileNameRequest
	{
		private static final String DEFAULT_PDF_EXTENSION = ".pdf";

		@Nullable
		OrgId orgId;

		@Nullable
		DocTypeId docTypeId;

		@NonNull TableRecordReference recordReference;

		@Nullable
		String documentNo;

		@NonNull
		String fileExtension;

		@Nullable
		String suffix;

		@Builder
		public ComputeFileNameRequest(
				@Nullable final OrgId orgId,
				@Nullable final DocTypeId docTypeId,
				@NonNull final TableRecordReference recordReference,
				@Nullable final String documentNo,
				@Nullable final String fileExtension,
				@Nullable final String suffix)
		{
			this.orgId = orgId;
			this.docTypeId = docTypeId;
			this.recordReference = recordReference;
			this.documentNo = documentNo;
			this.fileExtension = CoalesceUtil.coalesceNotNull(fileExtension, DEFAULT_PDF_EXTENSION);
			this.suffix = suffix;
		}
	}
}



