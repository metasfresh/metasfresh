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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import de.metas.document.engine.DocumentHandlerProvider;
import de.metas.dunning.DunningDocDocumentHandlerProvider;
import de.metas.dunning.DunningTestBase;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningProducer;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;

public class DefaultDunningProducerTest extends DunningTestBase
{
	private I_C_DunningLevel dunningLevel1;
	private DefaultDunningProducer producer;

	@Override
	protected void createMasterData()
	{
		SpringContextHolder.registerJUnitBean(DocumentHandlerProvider.class, new DunningDocDocumentHandlerProvider());

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

		assertDunningDocValid(context, candidate1);
		assertDunningDocValid(context, candidate2);
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

	private void assertDunningDocValid(final IDunningContext context, final I_C_Dunning_Candidate candidate)
	{
		InterfaceWrapperHelper.refresh(candidate);

		final I_C_DunningDoc dunningDoc = retrieveDunningDocForCandidate(candidate);
		Assert.assertNotNull("No dunning doc found for " + candidate, dunningDoc);
		assertValid(context, dunningDoc, candidate);

		final List<I_C_DunningDoc_Line> docLines = dao.retrieveDunningDocLines(dunningDoc);
		Assert.assertNotNull("No lines found for" + dunningDoc, docLines);
		Assert.assertEquals("One line expected for " + dunningDoc, 1, docLines.size());
		final I_C_DunningDoc_Line docLine = docLines.get(0);
		assertValid(context, docLine, candidate);

		final List<I_C_DunningDoc_Line_Source> docLineSources = dao.retrieveDunningDocLineSources(docLine);
		Assert.assertNotNull("No sources found for" + docLine, docLineSources);
		Assert.assertEquals("One source expected for " + docLine, 1, docLineSources.size());
		// final I_C_DunningDoc_Line_Source docLineSrc = docLineSources.get(0);

		// Validate back the candidate:
		Assert.assertTrue("Candidate - Invalid Processed: " + candidate, candidate.isProcessed());
		Assert.assertTrue("Candidate - Invalid DunningDocProcessed: " + candidate, candidate.isDunningDocProcessed());
		Assert.assertEquals("Candidate - Invalid DunningDate: " + candidate, dunningDoc.getDunningDate(), candidate.getDunningDate());
	}

	private void assertValid(final IDunningContext context, final I_C_DunningDoc dunningDoc, final I_C_Dunning_Candidate candidate)
	{
		// this is valid only when execution dunningDate is not null
		// Assert.assertEquals("DunningDoc - DunningDate for execution context shall be used: " + dunningDoc, context.getDunningDate(), dunningDoc.getDunningDate());
		Assert.assertEquals("DunningDoc - Invalid Processed", true, dunningDoc.isProcessed());

		Assert.assertEquals("DunningDoc - Invalid AD_Org_ID", candidate.getAD_Org_ID(), dunningDoc.getAD_Org_ID());
		Assert.assertEquals("DunningDoc - Invalid C_BPartner_ID", candidate.getC_BPartner_ID(), dunningDoc.getC_BPartner_ID());
		Assert.assertEquals("DunningDoc - Invalid C_BPartner_Location_ID", candidate.getC_BPartner_Location_ID(), dunningDoc.getC_BPartner_Location_ID());
		Assert.assertEquals("DunningDoc - Invalid C_Dunning_Contact_ID", candidate.getC_Dunning_Contact_ID(), dunningDoc.getC_Dunning_Contact_ID());
	}

	private void assertValid(final IDunningContext context, final I_C_DunningDoc_Line docLine, final I_C_Dunning_Candidate candidate)
	{
		Assert.assertEquals("DunningDoc - Invalid Processed", true, docLine.isProcessed());

		Assert.assertEquals("DunningDocLine - Invalid AD_Org_ID", candidate.getAD_Org_ID(), docLine.getAD_Org_ID());
		Assert.assertEquals("DunningDocLine - Invalid C_DunningLevel", candidate.getC_DunningLevel(), docLine.getC_DunningLevel());
		Assert.assertEquals("DunningDocLine - Invalid C_BPartner_ID", candidate.getC_BPartner_ID(), docLine.getC_BPartner_ID());
		Assert.assertEquals("DunningDocLine - Invalid C_Dunning_Contact_ID", candidate.getC_Dunning_Contact_ID(), docLine.getC_Dunning_Contact_ID());
		Assert.assertEquals("DunningDocLine - Invalid C_Dunning_Contact_ID", candidate.getC_Currency_ID(), docLine.getC_Currency_ID());
	}

	private I_C_DunningDoc retrieveDunningDocForCandidate(final I_C_Dunning_Candidate candidate)
	{
		final I_C_DunningDoc_Line_Source lineSrc = db.getFirstOnly(I_C_DunningDoc_Line_Source.class, pojo -> {
			if (pojo.getC_Dunning_Candidate_ID() != candidate.getC_Dunning_Candidate_ID())
			{
				return false;
			}
			return true;
		});
		if (lineSrc == null)
		{
			return null;
		}
		return lineSrc.getC_DunningDoc_Line().getC_DunningDoc();
	}
}
