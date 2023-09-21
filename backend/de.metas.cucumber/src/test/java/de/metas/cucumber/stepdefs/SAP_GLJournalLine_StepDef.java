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
import de.metas.acct.gljournal_sap.service.SAPGLJournalLoaderAndSaver;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.acct.model.I_SAP_GLJournalLine;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.sectioncode.M_SectionCode_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_SectionCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_Amount;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_C_Tax_ID;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_C_ValidCombination_ID;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_IsTaxIncluded;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_Line;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_M_SectionCode_ID;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_Parent_ID;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_SAP_GLJournal_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_PostingSign;

public class SAP_GLJournalLine_StepDef
{
	private final SAP_GLJournal_StepDefData glJournalTable;
	private final SAP_GLJournalLine_StepDefData glJournalLineTable;
	private final C_Tax_StepDefData taxTable;
	private final M_SectionCode_StepDefData sectionCodeTable;

	public SAP_GLJournalLine_StepDef(
			@NonNull final SAP_GLJournal_StepDefData glJournalTable,
			@NonNull final SAP_GLJournalLine_StepDefData glJournalLineTable,
			@NonNull final C_Tax_StepDefData taxTable,
			@NonNull final M_SectionCode_StepDefData sectionCodeTable)
	{
		this.glJournalTable = glJournalTable;
		this.glJournalLineTable = glJournalLineTable;
		this.taxTable = taxTable;
		this.sectionCodeTable = sectionCodeTable;
	}

	@Given("metasfresh contains SAP_GLJournalLines:")
	public void metasfresh_contains_sap_gl_journal_lines(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final I_SAP_GLJournalLine glJournalLine = newInstance(I_SAP_GLJournalLine.class);

			glJournalLine.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			// Line
			final Integer line = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_Line);
			assertThat(line).isNotNull();

			glJournalLine.setLine(line);

			// SAP_GLJournal_ID.Identifier
			final String sapGlJournalIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_SAP_GLJournal_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertThat(sapGlJournalIdentifier).isNotBlank();

			final I_SAP_GLJournal header = glJournalTable.get(sapGlJournalIdentifier);
			glJournalLine.setSAP_GLJournal(header);

			// PostingSign
			final String postingSign = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_PostingSign);
			assertThat(postingSign).isNotBlank();

			glJournalLine.setPostingSign(postingSign);

			// C_ValidCombination_ID
			final String c_ValidCombination_ID = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_ValidCombination_ID);
			assertThat(c_ValidCombination_ID).isNotBlank();

			glJournalLine.setC_ValidCombination_ID(Integer.valueOf(c_ValidCombination_ID));

			// Amount
			final BigDecimal amount = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_Amount);
			assertThat(amount).isNotNull();

			glJournalLine.setAmount(amount);
			glJournalLine.setAmtAcct(amount);  // same amount, as same currency is used

			// M_SectionCode_ID.Identifier
			final String sectionCodeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_SectionCode_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertThat(sectionCodeIdentifier).isNotBlank();

			final I_M_SectionCode sectionCode = sectionCodeTable.get(sectionCodeIdentifier);
			assertThat(sectionCode).isNotNull();

			glJournalLine.setM_SectionCode(sectionCode);

			// OPT.C_Tax_ID.Identifier
			final String taxIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_C_Tax_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertThat(taxIdentifier).isNotBlank();

			final I_C_Tax tax = taxTable.get(taxIdentifier);
			assertThat(tax).isNotNull();

			glJournalLine.setC_Tax_ID(tax.getC_Tax_ID());

			// OPT.IsTaxIncluded
			final boolean isTaxIncluded = DataTableUtil.extractBooleanForColumnName(tableRow, "OPT." + COLUMNNAME_IsTaxIncluded);
			glJournalLine.setIsTaxIncluded(isTaxIncluded);

			// OPT.Parent_ID
			final String parentIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_Parent_ID);
			if (EmptyUtil.isNotBlank(parentIdentifier))
			{
				final I_SAP_GLJournalLine parent = glJournalLineTable.get(parentIdentifier);
				assertThat(parent).isNotNull();

				glJournalLine.setParent(parent);
			}

			saveRecord(glJournalLine);

			glJournalLineTable.putOrReplace(DataTableUtil.extractRecordIdentifier(tableRow, I_SAP_GLJournalLine.COLUMNNAME_SAP_GLJournalLine_ID), glJournalLine);
		}
	}

	@Given("validate generated lines:")
	public void validate_tax_lines(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final I_SAP_GLJournal header = extractHeader(row);
		assertThat(header).as("Record not found").isNotNull();

		// Amount
		final BigDecimal amount = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_Amount);
		assertThat(amount).as("Please provide a valid amount").isNotNull();

		// ParentID
		final String parentLineIdentifier = DataTableUtil.extractStringForColumnName(row, "OPT." + COLUMNNAME_Parent_ID);
		assertThat(parentLineIdentifier).as("%s is mandatory in this context", COLUMNNAME_Parent_ID).isNotBlank();

		// PostingSign
		final String postingSign = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_PostingSign);
		assertThat(postingSign).as("%s is mandatory in this context", COLUMNNAME_PostingSign).isNotBlank();

		final I_SAP_GLJournalLine parentLine = glJournalLineTable.get(parentLineIdentifier);
		assertThat(parentLine).as("No record found").isNotNull();

		// validate
		final SAPGLJournalLoaderAndSaver loaderAndSaver = new SAPGLJournalLoaderAndSaver();

		final SAPGLJournalId sapglJournalId = SAPGLJournalId.ofRepoId(header.getSAP_GLJournal_ID());
		final ArrayList<I_SAP_GLJournalLine> lineRecords = loaderAndSaver.getLineRecords(sapglJournalId);

		// extract generated line by parent
		final I_SAP_GLJournalLine generatedLine = lineRecords.stream()
				.filter(line -> line.getParent_ID() == parentLine.getSAP_GLJournalLine_ID())
				.findFirst()
				.get();

		assertThat(generatedLine).as("No generated line").isNotNull();
		assertThat(generatedLine.getPostingSign()).isEqualTo(postingSign);
		assertThat(generatedLine.getAmount()).isEqualByComparingTo(amount);
	}

	@Given("base tax line updated:")
	public void base_tax_line_updated(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final I_SAP_GLJournal header = extractHeader(row);
		assertThat(header).as("Record not found").isNotNull();

		// PostingSign
		final String postingSign = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_PostingSign);
		assertThat(postingSign).as("%s is mandatory in this context", COLUMNNAME_PostingSign).isNotBlank();

		final BigDecimal amount = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_Amount);
		assertThat(amount).as("Please provide a valid amount").isNotNull();

		final boolean isTaxIncluded = DataTableUtil.extractBooleanForColumnName(row, "OPT." + COLUMNNAME_IsTaxIncluded);

		//
		// validate
		final SAPGLJournalLoaderAndSaver loaderAndSaver = new SAPGLJournalLoaderAndSaver();

		final SAPGLJournalId sapglJournalId = SAPGLJournalId.ofRepoId(header.getSAP_GLJournal_ID());
		final ArrayList<I_SAP_GLJournalLine> lineRecords = loaderAndSaver.getLineRecords(sapglJournalId);

		// extract generated line by parent = null
		final I_SAP_GLJournalLine baseLine = lineRecords.stream()
				.filter(line -> line.getParent_ID() <= 0)
				.findFirst()
				.get();

		assertThat(baseLine.getPostingSign()).isEqualTo(postingSign);
		assertThat(baseLine.getAmount()).isEqualByComparingTo(amount);
		assertThat(baseLine.isTaxIncluded()).isEqualTo(isTaxIncluded);
	}

	private I_SAP_GLJournal extractHeader(@NonNull final Map<String, String> row)
	{
		final String headerIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_SAP_GLJournal_ID + "." + TABLECOLUMN_IDENTIFIER);

		assertThat(headerIdentifier).as("%s is mandatory in this context", COLUMNNAME_SAP_GLJournal_ID).isNotBlank();

		return glJournalTable.get(headerIdentifier);
	}

}