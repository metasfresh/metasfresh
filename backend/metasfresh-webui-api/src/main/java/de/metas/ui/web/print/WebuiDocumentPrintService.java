/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.print;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.process.AdProcessId;
import de.metas.report.DocumentPrintOptionDescriptor;
import de.metas.report.DocumentPrintOptionValue;
import de.metas.report.DocumentPrintOptionsIncludingDescriptors;
import de.metas.report.DocumentReportFlavor;
import de.metas.report.DocumentReportRequest;
import de.metas.report.DocumentReportResult;
import de.metas.report.DocumentReportService;
import de.metas.report.ReportResultData;
import de.metas.ui.web.print.json.JSONDocumentPrintingOption;
import de.metas.ui.web.print.json.JSONDocumentPrintingOptions;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WebuiDocumentPrintService
{
	private static final AdMessageKey MSG_PrintOptions_Caption = AdMessageKey.of("webui.window.Print.caption");
	private static final AdMessageKey MSG_PrintOptions_OKButtonCaption = AdMessageKey.of("Print");

	private final DocumentCollection documentCollection;
	private final DocumentReportService documentReportService;

	public WebuiDocumentPrintService(
			@NonNull final DocumentCollection documentCollection,
			@NonNull final DocumentReportService documentReportService)
	{
		this.documentCollection = documentCollection;
		this.documentReportService = documentReportService;
	}

	public ReportResultData createDocumentPrint(@NonNull final WebuiDocumentPrintRequest request)
	{
		final DocumentPath documentPath = request.getDocumentPath();
		final Document document = documentCollection.getDocumentReadonly(documentPath);
		final int windowNo = document.getWindowNo();
		final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();

		final AdProcessId printProcessId = entityDescriptor.getPrintProcessId();
		final TableRecordReference recordRef = documentCollection.getTableRecordReference(documentPath);

		final DocumentReportResult result = documentReportService.createReport(DocumentReportRequest.builder()
				.flavor(request.getFlavor())
				.reportProcessId(printProcessId)
				.documentRef(recordRef)
				.clientId(document.getClientId())
				.orgId(document.getOrgId())
				.userId(request.getUserId())
				.roleId(request.getRoleId())
				.windowNo(windowNo) // important: required for ProcessInfo.findReportingLanguage
				.printPreview(true)
				.printOptions(request.getPrintOptions())
				//.setJRDesiredOutputType(OutputType.PDF)
				.build());

		return result.getData();
	}

	public JSONDocumentPrintingOptions getPrintingOptions(
			@NonNull final DocumentPath documentPath,
			@NonNull final String adLanguage)
	{
		final DocumentEntityDescriptor entityDescriptor = documentCollection.getDocumentEntityDescriptor(documentPath.getWindowId());
		final AdProcessId printProcessId = entityDescriptor.getPrintProcessId();
		final TableRecordReference recordRef = documentCollection.getTableRecordReference(documentPath);

		final DocumentPrintOptionsIncludingDescriptors printOptions = documentReportService.getDocumentPrintOptionsIncludingDescriptors(
				printProcessId,
				recordRef,
				DocumentReportFlavor.PRINT);

		return toJSONDocumentPrintingOptions(printOptions, adLanguage);
	}

	private static JSONDocumentPrintingOptions toJSONDocumentPrintingOptions(
			@NonNull final DocumentPrintOptionsIncludingDescriptors printOptions,
			@NonNull final String adLanguage)
	{
		final ArrayList<JSONDocumentPrintingOption> jsonOptionsList = new ArrayList<>();

		for (final DocumentPrintOptionDescriptor option : printOptions.getOptionDescriptors())
		{
			final String caption = option.getName().translate(adLanguage);
			final String description = option.getDescription().translate(adLanguage);
			final String internalName = option.getInternalName();
			final DocumentPrintOptionValue value = printOptions.getOptionValue(internalName);

			jsonOptionsList.add(JSONDocumentPrintingOption.builder()
					.caption(caption)
					.description(description)
					.internalName(internalName)
					.value(value.isTrue())
					.debugSourceName(value.getSourceName())
					.build());
		}

		return JSONDocumentPrintingOptions.builder()
				.caption(TranslatableStrings.adMessage(MSG_PrintOptions_Caption).translate(adLanguage))
				.okButtonCaption(TranslatableStrings.adMessage(MSG_PrintOptions_OKButtonCaption).translate(adLanguage))
				.options(jsonOptionsList)
				.build();
	}
}
