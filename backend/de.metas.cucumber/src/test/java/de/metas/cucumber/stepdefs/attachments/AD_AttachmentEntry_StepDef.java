/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.attachments;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.attachments.AttachmentEntryId;
import de.metas.common.rest_api.v2.attachment.JsonAttachment;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentResponse;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentSourceType;
import de.metas.common.rest_api.v2.attachment.JsonExternalReferenceTarget;
import de.metas.common.rest_api.v2.attachment.JsonTableRecordReference;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.I_AD_Attachment_MultiRef;
import org.compiere.model.I_AD_Table;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class AD_AttachmentEntry_StepDef
{
	private final TestContext testContext;
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

	private final AD_Attachment_Entry_StepDefData attachmentEntryTable;
	private final File_StepDefData fileTable;

	public AD_AttachmentEntry_StepDef(
			@NonNull final TestContext testContext,
			@NonNull final AD_Attachment_Entry_StepDefData attachmentEntryTable,
			@NonNull final File_StepDefData fileTable)
	{
		this.testContext = testContext;
		this.attachmentEntryTable = attachmentEntryTable;
		this.fileTable = fileTable;
	}

	@Then("process attachment response")
	public void process_attachment_response(@NonNull final DataTable table) throws JsonProcessingException
	{
		final JsonAttachmentResponse attachmentResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonAttachmentResponse.class);
		assertThat(attachmentResponse).isNotNull();

		final Map<String, String> row = table.asMaps().get(0);
		final String attachmentEntryIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_AttachmentEntry.COLUMNNAME_AD_AttachmentEntry_ID + ".Identifier");

		processAttachmentResponse(attachmentResponse, attachmentEntryIdentifier);
	}

	@And("validate the created attachment entry")
	public void validate_created_attachment_entry(@NonNull final DataTable table)
	{
		final Map<String, String> row = table.asMaps().get(0);

		final String identifier = DataTableUtil.extractStringForColumnName(row, I_AD_AttachmentEntry.COLUMNNAME_AD_AttachmentEntry_ID + ".Identifier");
		final String type = DataTableUtil.extractStringForColumnName(row, I_AD_AttachmentEntry.COLUMNNAME_Type);
		final String fileName = DataTableUtil.extractStringForColumnName(row, I_AD_AttachmentEntry.COLUMNNAME_FileName);
		final String dataString = DataTableUtil.extractStringOrNullForColumnName(row, I_AD_AttachmentEntry.COLUMNNAME_BinaryData);
		final String contentType = DataTableUtil.extractStringOrNullForColumnName(row, I_AD_AttachmentEntry.COLUMNNAME_ContentType);
		final String URL = DataTableUtil.extractStringOrNullForColumnName(row, I_AD_AttachmentEntry.COLUMNNAME_URL);
		final String fileIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.File.Identifier");

		final I_AD_AttachmentEntry attachmentEntry = attachmentEntryTable.get(identifier);

		assertThat(attachmentEntry.getType()).isEqualTo(type);
		assertThat(attachmentEntry.getFileName()).isEqualTo(fileName);

		if (Check.isNotBlank(dataString))
		{
			final byte[] binaryData = dataString.getBytes();
			assertThat(attachmentEntry.getBinaryData()).isEqualTo(binaryData);
		}

		if (Check.isNotBlank(contentType))
		{
			assertThat(attachmentEntry.getContentType()).isEqualTo(contentType);
		}

		if (Check.isNotBlank(URL))
		{
			assertThat(attachmentEntry.getURL()).isEqualTo(URL);
		}

		if (Check.isNotBlank(fileIdentifier))
		{
			final File file = fileTable.get(fileIdentifier);

			assertThat(file.toPath().toUri().toString()).isEqualTo(attachmentEntry.getURL());
		}
	}

	@And("validate the created attachment multiref")
	public void validate_created_attachment_multiref(@NonNull final DataTable table) throws IOException
	{
		for (final Map<String, String> row : table.asMaps())
		{
			validateAttachmentEntry_MultiRef(row);
		}
	}

	private void processAttachmentResponse(
			@NonNull final JsonAttachmentResponse response,
			@NonNull final String attachmentEntryIdentifier)
	{
		final AttachmentEntryId attachmentEntryId = AttachmentEntryId.ofRepoId(Integer.parseInt(response.getAttachmentId()));

		final I_AD_AttachmentEntry attachmentEntry = queryBL.createQueryBuilder(I_AD_AttachmentEntry.class)
				.addInArrayFilter(I_AD_AttachmentEntry.COLUMNNAME_AD_AttachmentEntry_ID, attachmentEntryId)
				.create()
				.firstOnly(I_AD_AttachmentEntry.class);

		assertThat(attachmentEntry).isNotNull();

		attachmentEntryTable.putOrReplace(attachmentEntryIdentifier, attachmentEntry);
	}

	@Given("an existing local file")
	public void anExistingLocalFile(@NonNull final DataTable table) throws IOException
	{
		final Map<String, String> row = table.asMaps().get(0);
		final String fileName = DataTableUtil.extractStringForColumnName(row, "FileName");
		final String fileIdentifier = DataTableUtil.extractStringForColumnName(row, "File.Identifier");

		final File file = new File(fileName);

		file.createNewFile();

		fileTable.put(fileIdentifier, file);
	}

	@And("store JsonAttachmentRequest in context")
	public void storeJsonAttachmentRequestInContext(@NonNull final DataTable table) throws JsonProcessingException
	{
		final Map<String, String> row = table.asMaps().get(0);
		final String orgCode = DataTableUtil.extractStringForColumnName(row, "orgCode");
		final String type = DataTableUtil.extractStringForColumnName(row, I_AD_AttachmentEntry.Table_Name + "." + I_AD_AttachmentEntry.COLUMNNAME_Type);
		final String fileIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.File.Identifier");

		final JsonAttachment.JsonAttachmentBuilder jsonAttachmentBuilder = JsonAttachment.builder();

		if (Check.isNotBlank(fileIdentifier))
		{
			final File file = fileTable.get(fileIdentifier);
			assertThat(file).isNotNull();

			jsonAttachmentBuilder.fileName(file.getName())
					.data(file.toPath().toUri().toString());
		}
		else
		{
			final String fileName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_AD_AttachmentEntry.COLUMNNAME_FileName);
			Optional.ofNullable(fileName)
					.ifPresent(jsonAttachmentBuilder::fileName);

			final String data = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_AD_AttachmentEntry.COLUMNNAME_BinaryData);
			if (Check.isNotBlank(data))
			{
				final byte[] fileData = data.getBytes();
				final String encodedData = Base64.getEncoder().encodeToString(fileData);
				jsonAttachmentBuilder.data(encodedData);
			}
		}

		final JsonAttachmentSourceType jsonAttachmentSourceType = JsonAttachmentSourceType.valueOf(type);

		final JsonAttachment jsonAttachment = jsonAttachmentBuilder
				.type(jsonAttachmentSourceType)
				.build();

		final JsonAttachmentRequest.JsonAttachmentRequestBuilder jsonAttachmentRequestBuilder = JsonAttachmentRequest.builder()
				.attachment(jsonAttachment)
				.orgCode(orgCode);

		final String targets = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.targets");
		if (Check.isNotBlank(targets))
		{
			final List<JsonExternalReferenceTarget> targetList = JsonObjectMapperHolder.newJsonObjectMapper()
					.readValue(targets, new TypeReference<List<JsonExternalReferenceTarget>>() {});

			jsonAttachmentRequestBuilder.targets(targetList);
		}

		final String references = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.references");
		if (Check.isNotBlank(references))
		{
			final List<JsonTableRecordReference> referenceList = JsonObjectMapperHolder.newJsonObjectMapper()
					.readValue(references, new TypeReference<List<JsonTableRecordReference>>() {});

			jsonAttachmentRequestBuilder.references(referenceList);
		}

		final String payload = mapper.writeValueAsString(jsonAttachmentRequestBuilder.build());

		testContext.setRequestPayload(payload);
	}

	private void validateAttachmentEntry_MultiRef(final Map<String, String> row)
	{
		final String attachmentEntryIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_AttachmentEntry.COLUMNNAME_AD_AttachmentEntry_ID + ".Identifier");

		final int recordId = DataTableUtil.extractIntForColumnName(row, I_AD_Attachment_MultiRef.COLUMNNAME_Record_ID);

		final String tableName = DataTableUtil.extractStringForColumnName(row, I_AD_Table.COLUMNNAME_TableName);
		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		final AttachmentEntryId attachmentEntryId = AttachmentEntryId.ofRepoId(attachmentEntryTable.get(attachmentEntryIdentifier).getAD_AttachmentEntry_ID());

		final I_AD_Attachment_MultiRef attachmentMultiRef = queryBL.createQueryBuilder(I_AD_Attachment_MultiRef.class)
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMNNAME_AD_AttachmentEntry_ID, attachmentEntryId)
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMN_Record_ID, recordId)
				.create()
				.firstOnly(I_AD_Attachment_MultiRef.class);

		assertThat(attachmentMultiRef).isNotNull();
	}
}
 