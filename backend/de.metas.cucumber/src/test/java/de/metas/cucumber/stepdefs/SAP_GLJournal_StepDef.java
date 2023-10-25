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

import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_C_ConversionType_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import static org.compiere.model.I_C_DocType.COLUMNNAME_DocBaseType;
import static org.compiere.model.I_C_DocType.COLUMNNAME_DocSubType;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_Description;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_PostingType;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_DocStatus;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_DateDoc;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_DateAcct;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_AD_Org_ID;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_C_AcctSchema_ID;
import static de.metas.acct.model.I_SAP_GLJournal.COLUMNNAME_SAP_GLJournal_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_DocType.COLUMNNAME_GL_Category_ID;

public class SAP_GLJournal_StepDef
{
	final SAPGLJournalService glJournalService = SpringContextHolder.instance.getBean(SAPGLJournalService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final SAP_GLJournal_StepDefData glJournalTable;

	private final C_AcctSchema_StepDefData acctSchemaTable;
	private final AD_Org_StepDefData orgTable;

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
	public void metasfresh_contains_sap_gljournal(@NonNull final DataTable dataTable)
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		// Description
		final String description = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Description);
		assertThat(description).as(String.format("%s column is mandatory in this context", COLUMNNAME_Description)).isNotBlank();

		// PostingType
		final String postingType = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_PostingType);
		assertThat(postingType).as(String.format("%s column is mandatory in this context", COLUMNNAME_PostingType)).isNotBlank();

		// DocStatus
		final String docStatus = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DocStatus);
		assertThat(docStatus).as(String.format("%s column is mandatory in this context", COLUMNNAME_DocStatus)).isNotBlank();

		// DocBaseType
		final String docBaseType = DataTableUtil.extractStringOrNullForColumnName(tableRow, COLUMNNAME_DocBaseType);
		assertThat(docBaseType).as(String.format("%s column is mandatory in this context", COLUMNNAME_DocBaseType)).isNotBlank();

		// DateDoc
		final Timestamp dateDoc = DataTableUtil.extractDateTimestampForColumnName(tableRow, COLUMNNAME_DateDoc);
		assertThat(dateDoc).as(String.format("%s column is mandatory in this context", COLUMNNAME_DateDoc)).isNotNull();

		// DateAcct
		final Timestamp dateAcct = DataTableUtil.extractDateTimestampForColumnName(tableRow, COLUMNNAME_DateAcct);
		assertThat(dateAcct).as(String.format("%s column is mandatory in this context", COLUMNNAME_DateAcct)).isNotNull();

		// C_AcctSchema_ID
		final String acctSchemaIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_AcctSchema_ID + "." + TABLECOLUMN_IDENTIFIER);
		assertThat(acctSchemaIdentifier).as(String.format("%s column is mandatory in this context", COLUMNNAME_C_AcctSchema_ID)).isNotBlank();

		// ad_org_id
		final int orgId = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER))
				.map(orgTable::get)
				.map(I_AD_Org::getAD_Org_ID)
				.orElse(StepDefConstants.ORG_ID.getRepoId());

		// DateDoc
		final Integer conversionType_ID = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_C_ConversionType_ID);
		assertThat(conversionType_ID).as(String.format("%s column is mandatory in this context", COLUMNNAME_C_ConversionType_ID)).isNotNull();

		// DateDoc
		final Integer gl_Category_ID = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_GL_Category_ID);
		assertThat(gl_Category_ID).as(String.format("%s column is mandatory in this context", COLUMNNAME_GL_Category_ID)).isNotNull();

		final I_SAP_GLJournal sap_glJournal = newInstance(I_SAP_GLJournal.class);

		// docBaseType
		final String docSubType = DataTableUtil.extractStringOrNullForColumnName(tableRow, COLUMNNAME_DocSubType);
		final I_C_DocType docType = queryBL.createQueryBuilder(I_C_DocType.class)
				.addEqualsFilter(COLUMNNAME_DocBaseType, docBaseType)
				.addEqualsFilter(COLUMNNAME_DocSubType, docSubType)
				.create()
				.firstOnlyNotNull(I_C_DocType.class);

		assertThat(docType).isNotNull();
		sap_glJournal.setC_DocType_ID(docType.getC_DocType_ID());

		final I_C_AcctSchema acctSchema = acctSchemaTable.get(acctSchemaIdentifier);
		assertThat(acctSchema).isNotNull();

		sap_glJournal.setDescription(description);
		sap_glJournal.setDocStatus(docStatus);
		sap_glJournal.setPostingType(postingType);
		sap_glJournal.setAD_Org_ID(orgId);
		sap_glJournal.setDateDoc(dateDoc);
		sap_glJournal.setDateAcct(dateAcct);
		sap_glJournal.setC_AcctSchema(acctSchema);
		sap_glJournal.setC_Currency_ID(acctSchema.getC_Currency_ID());
		sap_glJournal.setAcct_Currency_ID(acctSchema.getC_Currency_ID());
		sap_glJournal.setC_ConversionType_ID(conversionType_ID);
		sap_glJournal.setGL_Category_ID(gl_Category_ID);

		saveRecord(sap_glJournal);

		glJournalTable.putOrReplace(DataTableUtil.extractRecordIdentifier(tableRow, COLUMNNAME_SAP_GLJournal_ID), sap_glJournal);

	}

	@Given("regenerate tax lines:")
	public void regenerate_tax_lines(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);
		final String headerIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_SAP_GLJournal_ID + "." + TABLECOLUMN_IDENTIFIER);

		assertThat(headerIdentifier).as("%s is mandatory in this context", COLUMNNAME_SAP_GLJournal_ID).isNotBlank();

		final I_SAP_GLJournal sap_glJournal = glJournalTable.get(headerIdentifier);
		assertThat(sap_glJournal).as("No SAP_GLJournal record found").isNotNull();

		final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(sap_glJournal.getSAP_GLJournal_ID());

		// generate tax line, process main method
		glJournalService.regenerateTaxLines(glJournalId);

	}

}