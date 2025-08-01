package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.acct.posting.DocumentPostingUserNotificationService;
import de.metas.dunning.DunningDocDocumentHandlerProvider;
import de.metas.dunning.DunningTestBase;
import de.metas.dunning.api.IDunningProducer;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.record.warning.RecordWarningRepository;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
		StartupListener.class,
		ShutdownListener.class,
		DunningDocDocumentHandlerProvider.class,
		RecordWarningRepository.class,
		DocumentPostingUserNotificationService.class,
})
public class DefaultDunningProducerTest extends DunningTestBase
{
	private I_C_DunningLevel dunningLevel1;
	private DefaultDunningProducer producer;

	@Override
	protected void createMasterData()
	{
		final I_C_Dunning dunning = createDunning("dunning");
		dunningLevel1 = createDunningLevel(dunning, 0, 10, 0);

		producer = new DefaultDunningProducer();
	}

	@Test
	public void test_OneDocumentForEachCandidate()
	{
		final LocalDate candidateDunningDate = LocalDate.of(2013, Month.JANUARY, 1);

		// NOTE: if we are using null execution DunningDate, the DunningDate from candidate shall be used
		// final Date executionDunningDate = TimeUtil.getDay(2013, 02, 01);
		final Date executionDunningDate = null;

		final PlainDunningContext context = createPlainDunningContext(executionDunningDate, dunningLevel1);
		context.setProperty(IDunningProducer.CONTEXT_ProcessDunningDoc, true); // auto-process dunning docs
		producer.setDunningContext(context);

		final I_C_Dunning_Candidate candidate1 = createCandidate(candidateDunningDate, dunningLevel1);
		producer.addCandidate(candidate1);

		final I_C_Dunning_Candidate candidate2 = createCandidate(candidateDunningDate, dunningLevel1);
		producer.addCandidate(candidate2);

		producer.finish();

		assertDunningDocValid(candidate1);
		assertDunningDocValid(candidate2);
	}

	private I_C_Dunning_Candidate createCandidate(final LocalDate dunningDate, final I_C_DunningLevel dunningLevel)
	{
		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		candidate.setAD_Org_ID(1);
		candidate.setC_DunningLevel(dunningLevel);
		candidate.setDunningDate(TimeUtil.asTimestamp(dunningDate));
		//
		candidate.setC_BPartner_ID(1);
		candidate.setC_BPartner_Location_ID(1);
		candidate.setC_Dunning_Contact_ID(1);
		//
		candidate.setTotalAmt(new BigDecimal("100"));
		candidate.setOpenAmt(new BigDecimal("100"));
		candidate.setDunningInterestAmt(BigDecimal.ZERO);
		candidate.setFeeAmt(BigDecimal.ZERO);
		candidate.setC_Currency_ID(currencyEUR.getRepoId());
		//
		candidate.setIsActive(true);
		candidate.setProcessed(false);
		candidate.setIsDunningDocProcessed(false);
		candidate.setDunningDateEffective(null);

		candidate.setIsWriteOff(false);

		InterfaceWrapperHelper.save(candidate);
		return candidate;
	}

	private void assertDunningDocValid(final I_C_Dunning_Candidate candidate)
	{
		InterfaceWrapperHelper.refresh(candidate);

		final I_C_DunningDoc dunningDoc = retrieveDunningDocForCandidate(candidate);
		assertThat(dunningDoc).as("No dunning doc found for " + candidate).isNotNull();
		assertValid(dunningDoc, candidate);

		final List<I_C_DunningDoc_Line> docLines = dao.retrieveDunningDocLines(dunningDoc);
		assertThat(docLines).as("One line expected for " + dunningDoc).hasSize(1);
		final I_C_DunningDoc_Line docLine = docLines.getFirst();
		assertValid(docLine, candidate);

		final List<I_C_DunningDoc_Line_Source> docLineSources = dao.retrieveDunningDocLineSources(docLine);
		assertThat(docLineSources).as("One source expected for " + docLine).hasSize(1);
		// final I_C_DunningDoc_Line_Source docLineSrc = docLineSources.get(0);

		// Validate back the candidate:
		assertThat(candidate.isProcessed()).as("Candidate - Invalid Processed: " + candidate).isTrue();
		assertThat(candidate.isDunningDocProcessed()).as("Candidate - Invalid DunningDocProcessed: " + candidate).isTrue();
		assertThat(candidate.getDunningDate()).as("Candidate - Invalid DunningDate: " + candidate).isEqualTo(dunningDoc.getDunningDate());
	}

	private void assertValid(final I_C_DunningDoc dunningDoc, final I_C_Dunning_Candidate candidate)
	{
		// this is valid only when execution dunningDate is not null
		// Assert.assertEquals("DunningDoc - DunningDate for execution context shall be used: " + dunningDoc, context.getDunningDate(), dunningDoc.getDunningDate());
		assertThat(dunningDoc.isProcessed()).as("DunningDoc - Invalid Processed").isTrue();

		assertThat(dunningDoc.getAD_Org_ID()).as("DunningDoc - Invalid AD_Org_ID").isEqualTo(candidate.getAD_Org_ID());
		assertThat(dunningDoc.getC_BPartner_ID()).as("DunningDoc - Invalid C_BPartner_ID").isEqualTo(candidate.getC_BPartner_ID());
		assertThat(dunningDoc.getC_BPartner_Location_ID()).as("DunningDoc - Invalid C_BPartner_Location_ID").isEqualTo(candidate.getC_BPartner_Location_ID());
		assertThat(dunningDoc.getC_Dunning_Contact_ID()).as("DunningDoc - Invalid C_Dunning_Contact_ID").isEqualTo(candidate.getC_Dunning_Contact_ID());
	}

	private void assertValid(final I_C_DunningDoc_Line docLine, final I_C_Dunning_Candidate candidate)
	{
		assertThat(docLine.isProcessed()).as("DunningDoc - Invalid Processed").isTrue();

		assertThat(docLine.getAD_Org_ID()).as("DunningDocLine - Invalid AD_Org_ID").isEqualTo(candidate.getAD_Org_ID());
		assertThat(docLine.getC_DunningLevel()).as("DunningDocLine - Invalid C_DunningLevel").isEqualTo(candidate.getC_DunningLevel());
		assertThat(docLine.getC_BPartner_ID()).as("DunningDocLine - Invalid C_BPartner_ID").isEqualTo(candidate.getC_BPartner_ID());
		assertThat(docLine.getC_Dunning_Contact_ID()).as("DunningDocLine - Invalid C_Dunning_Contact_ID").isEqualTo(candidate.getC_Dunning_Contact_ID());
		assertThat(docLine.getC_Currency_ID()).as("DunningDocLine - Invalid C_Dunning_Contact_ID").isEqualTo(candidate.getC_Currency_ID());
	}

	private I_C_DunningDoc retrieveDunningDocForCandidate(final I_C_Dunning_Candidate candidate)
	{
		final I_C_DunningDoc_Line_Source lineSrc = db.getFirstOnly(I_C_DunningDoc_Line_Source.class, pojo -> pojo.getC_Dunning_Candidate_ID() == candidate.getC_Dunning_Candidate_ID());
		if (lineSrc == null)
		{
			return null;
		}
		return lineSrc.getC_DunningDoc_Line().getC_DunningDoc();
	}
}
