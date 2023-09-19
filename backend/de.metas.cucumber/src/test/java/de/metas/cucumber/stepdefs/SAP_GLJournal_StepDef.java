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

package de.metas.cucumber.stepdefs;

import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_DocType;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_C_AcctSchema_ID;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_SAP_GLJournal_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_DocType.COLUMNNAME_DocBaseType;
import static org.compiere.model.I_C_DocType.COLUMNNAME_DocSubType;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_Description;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_PostingType;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_DocStatus;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_DateDoc;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_DateAcct;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_AD_Org_ID;

public class SAP_GLJournal_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final SAP_GLJournal_StepDefData glJournalTable;

	private final C_AcctSchema_StepDefData acctSchemaTable;
	private final AD_Org_StepDefData orgTable;

	final SAPGLJournalService glJournalService = SpringContextHolder.instance.getBean(SAPGLJournalService.class);

	public SAP_GLJournal_StepDef(
			@NonNull final SAP_GLJournal_StepDefData glJournalTable,
			@NonNull final C_AcctSchema_StepDefData acctSchemaTable,
			@NonNull final AD_Org_StepDefData orgTable)
	{
		this.glJournalTable = glJournalTable;
		this.acctSchemaTable = acctSchemaTable;
		this.orgTable = orgTable;
	}

	@Given("metasfresh contains SAP_GLJournal:")
	public void metasfresh_contains_gljournal(@NonNull final DataTable dataTable)
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String description = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Description);
		final String postingType = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_PostingType);
		final String docStatus = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DocStatus);
		final String docBaseType = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DocBaseType);
		final Timestamp dateDoc = DataTableUtil.extractDateTimestampForColumnName(tableRow, COLUMNNAME_DateDoc);
		final Timestamp dateAcct = DataTableUtil.extractDateTimestampForColumnName(tableRow, COLUMNNAME_DateAcct);
		final String acctSchemaIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_AcctSchema_ID + "." + TABLECOLUMN_IDENTIFIER);

		// validate inputs
		assertThat(description).isNotBlank();
		assertThat(postingType).isNotBlank();
		assertThat(docStatus).isNotBlank();
		assertThat(docBaseType).isNotBlank();
		assertThat(dateDoc).isNotNull();
		assertThat(dateAcct).isNotNull();
		assertThat(acctSchemaIdentifier).isNotBlank();

		// ad_org_id
		final int orgId = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER))
				.map(orgTable::get)
				.map(I_AD_Org::getAD_Org_ID)
				.orElse(StepDefConstants.ORG_ID.getRepoId());

		final I_SAP_GLJournal sapGlJournal = newInstance(I_SAP_GLJournal.class);

		// docBaseType
		if (EmptyUtil.isNotBlank(docBaseType))
		{
			final String docSubType = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DocSubType);
			final I_C_DocType docType = queryBL.createQueryBuilder(I_C_DocType.class)
					.addEqualsFilter(COLUMNNAME_DocBaseType, docBaseType)
					.addEqualsFilter(COLUMNNAME_DocSubType, docSubType)
					.create()
					.firstOnlyNotNull(I_C_DocType.class);

			assertThat(docType).isNotNull();
			sapGlJournal.setC_DocType_ID(docType.getC_DocType_ID());
		}

		final I_C_AcctSchema acctSchema = acctSchemaTable.get(acctSchemaIdentifier);
		assertThat(acctSchema).isNotNull();

		sapGlJournal.setDescription(description);
		sapGlJournal.setDocStatus(docStatus);
		sapGlJournal.setPostingType(postingType);
		sapGlJournal.setAD_Org_ID(orgId);
		sapGlJournal.setDateDoc(dateDoc);
		sapGlJournal.setDateAcct(dateAcct);
		sapGlJournal.setC_AcctSchema(acctSchema);
		sapGlJournal.setC_Currency_ID(acctSchema.getC_Currency_ID());
		sapGlJournal.setAcct_Currency_ID(acctSchema.getC_Currency_ID());
		sapGlJournal.setC_ConversionType_ID(114); // Spot
		sapGlJournal.setGL_Category_ID(1000001); // None

		saveRecord(sapGlJournal);

		glJournalTable.putOrReplace(DataTableUtil.extractRecordIdentifier(tableRow, COLUMNNAME_SAP_GLJournal_ID), sapGlJournal);

	}

	@Given("regenerate tax lines:")
	public void regenerate_tax_lines(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);
		final String glJournalIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_SAP_GLJournal_ID + "." + TABLECOLUMN_IDENTIFIER);

		assertThat(glJournalIdentifier).isNotBlank();

		final I_SAP_GLJournal sapGlJournal = glJournalTable.get(glJournalIdentifier);
		final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(sapGlJournal.getSAP_GLJournal_ID());

		assertThat(glJournalId).isNotNull();

		// generate tax line
		glJournalService.regenerateTaxLines(glJournalId);

	}

}