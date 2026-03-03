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
import de.metas.cucumber.stepdefs.sectioncode.M_SectionCode_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_SectionCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_Amount;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_C_Tax_ID;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_C_ValidCombination_ID;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_Description;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_IsTaxIncluded;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_Line;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_M_SectionCode_ID;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_Parent_ID;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_PostingSign;
import static de.metas.acct.model.I_SAP_GLJournalLine.COLUMNNAME_SAP_GLJournal_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

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
		for (final DataTableRow row : DataTableRow.toRows(dataTable))
		{
			createGLJournalLine(row);
		}
	}

	private void createGLJournalLine(@NonNull final DataTableRow row)
	{
		final I_SAP_GLJournalLine glJournalLine = newInstance(I_SAP_GLJournalLine.class);

		glJournalLine.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

		// Line
		glJournalLine.setLine(row.getAsInt(COLUMNNAME_Line));

		// SAP_GLJournal_ID
		final I_SAP_GLJournal header = glJournalTable.get(row.getAsIdentifier(COLUMNNAME_SAP_GLJournal_ID));
		glJournalLine.setSAP_GLJournal(header);

		// PostingSign
		glJournalLine.setPostingSign(row.getAsString(COLUMNNAME_PostingSign));

		// C_ValidCombination_ID
		glJournalLine.setC_ValidCombination_ID(row.getAsInt(COLUMNNAME_C_ValidCombination_ID));

		// Amount
		final BigDecimal amount = row.getAsBigDecimal(COLUMNNAME_Amount);
		glJournalLine.setAmount(amount);
		glJournalLine.setAmtAcct(amount);  // same amount, as same currency is used

		// M_SectionCode_ID
		row.getAsOptionalIdentifier(COLUMNNAME_M_SectionCode_ID).ifPresent(sectionCodeIdentifier -> {
			final I_M_SectionCode sectionCode = sectionCodeTable.get(sectionCodeIdentifier);
			assertThat(sectionCode).isNotNull();
			glJournalLine.setM_SectionCode(sectionCode);
		});

		// C_Tax_ID
		row.getAsOptionalIdentifier(COLUMNNAME_C_Tax_ID).ifPresent(taxIdentifier -> {
			final I_C_Tax tax = taxTable.get(taxIdentifier);
			assertThat(tax).isNotNull();
			glJournalLine.setC_Tax_ID(tax.getC_Tax_ID());
		});

		// IsTaxIncluded
		row.getAsOptionalBoolean(COLUMNNAME_IsTaxIncluded)
				.ifTrue(() -> glJournalLine.setIsTaxIncluded(true))
				.ifFalse(() -> glJournalLine.setIsTaxIncluded(false));

		// Description
		row.getAsOptionalString(COLUMNNAME_Description)
				.ifPresent(glJournalLine::setDescription);

		// Parent_ID
		row.getAsOptionalIdentifier(COLUMNNAME_Parent_ID).ifPresent(parentIdentifier -> {
			final I_SAP_GLJournalLine parent = glJournalLineTable.get(parentIdentifier);
			assertThat(parent).isNotNull();
			glJournalLine.setParent(parent);
		});

		saveRecord(glJournalLine);

		glJournalLineTable.putOrReplace(row.getAsIdentifier(), glJournalLine);
	}

	@Given("validate generated lines:")
	public void validate_tax_lines(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		final I_SAP_GLJournal header = glJournalTable.get(row.getAsIdentifier(COLUMNNAME_SAP_GLJournal_ID));
		assertThat(header).as("Record not found").isNotNull();

		// Amount
		final BigDecimal amount = row.getAsBigDecimal(COLUMNNAME_Amount);

		// ParentID
		final StepDefDataIdentifier parentLineIdentifier = row.getAsIdentifier(COLUMNNAME_Parent_ID);
		final I_SAP_GLJournalLine parentLine = glJournalLineTable.get(parentLineIdentifier);
		assertThat(parentLine).as("No record found").isNotNull();

		// PostingSign
		final String postingSign = row.getAsString(COLUMNNAME_PostingSign);

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

		// Description
		row.getAsOptionalString(COLUMNNAME_Description).ifPresent(expectedDescription -> {
			if (!expectedDescription.isEmpty())
			{
				assertThat(generatedLine.getDescription()).isEqualTo(expectedDescription);
			}
			else
			{
				assertThat(generatedLine.getDescription()).isNullOrEmpty();
			}
		});
	}

	@Given("base tax line updated:")
	public void base_tax_line_updated(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		final I_SAP_GLJournal header = glJournalTable.get(row.getAsIdentifier(COLUMNNAME_SAP_GLJournal_ID));
		assertThat(header).as("Record not found").isNotNull();

		// PostingSign
		final String postingSign = row.getAsString(COLUMNNAME_PostingSign);

		final BigDecimal amount = row.getAsBigDecimal(COLUMNNAME_Amount);

		final boolean isTaxIncluded = row.getAsOptionalBoolean(COLUMNNAME_IsTaxIncluded).toBooleanOrNull();

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
}
