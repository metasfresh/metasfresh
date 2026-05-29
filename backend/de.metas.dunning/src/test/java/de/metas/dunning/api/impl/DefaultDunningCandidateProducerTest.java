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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.dunning.DunningTestBase;
import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.IDunningCandidateProducer;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.exception.InconsistentDunningCandidateStateException;
import de.metas.dunning.exception.NotImplementedDunningException;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.invoice.api.impl.DunnableDocBuilder;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * NOTE:
 * <ul>
 * <li>all tests are running using sequential dunning
 * </ul>
 *
 * @author tsa
 */
public class DefaultDunningCandidateProducerTest extends DunningTestBase
{
	private final DefaultDunningCandidateProducer producer = new DefaultDunningCandidateProducer();

	private I_C_Dunning dunning;
	private I_C_DunningLevel dunningLevel1_10;
	private I_C_DunningLevel dunningLevel2_20;
	private I_C_DunningLevel dunningLevel3_30;

	@Override
	protected void createMasterData()
	{
		//
		// Setup Levels
		dunning = createDunning("Dunning");
		dunning.setCreateLevelsSequentially(true);
		InterfaceWrapperHelper.save(dunning);

		// dunning, DaysBetweenDunning, DaysAfterDue, InterestPercent
		dunningLevel1_10 = createDunningLevel(dunning, 0, 10, 0);
		dunningLevel1_10.setName("Level1");
		dunningLevel1_10.setIsWriteOff(false);
		InterfaceWrapperHelper.save(dunningLevel1_10);

		dunningLevel2_20 = createDunningLevel(dunning, 0, 20, 0);
		dunningLevel2_20.setName("Level2");
		dunningLevel2_20.setIsWriteOff(false);
		InterfaceWrapperHelper.save(dunningLevel2_20);

		dunningLevel3_30 = createDunningLevel(dunning, 0, 30, 0);
		dunningLevel3_30.setName("Level3");
		dunningLevel3_30.setIsWriteOff(false);
		InterfaceWrapperHelper.save(dunningLevel3_30);
	}

	@Test
	public void test_isHandled()
	{
		final IDunnableDoc dunnableDoc = null; // not important
		assertThat(producer.isHandled(dunnableDoc)).as("Shall always return true").isTrue();
	}

	/**
	 * Shall throw an exception because we are creating the candidate on a different client then is set in {@link IDunnableDoc}.
	 */
	@Test
	public void test_createDunningCandidate_Wrong_AD_Client_ID()
	{
		final Date dunningDate = TimeUtil.getDay(2013, 02, 01);
		final PlainDunningContext context = createPlainDunningContext(dunningDate, dunningLevel1_10);

		Env.setContext(context.getCtx(), "#AD_Client_ID", 1);

		final IDunnableDoc sourceDoc = mkDunnableDocBuilder()
				.setDaysDue(15) // daysDue,
				.setAD_Client_ID(12345) // invalid AD_Client_ID
				.create();

		assertThatThrownBy(() -> producer.createDunningCandidate(context, sourceDoc))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	public void test_createDunningCandidate_CandidateAlreadyProcessed()
	{
		final Date dunningDate = TimeUtil.getDay(2013, 02, 01);
		final PlainDunningContext context = createPlainDunningContext(dunningDate, dunningLevel1_10);

		final IDunnableDoc sourceDoc = mkDunnableDocBuilder()
				.setDaysDue(15) // daysDue,
				.create();

		final I_C_Dunning_Candidate candidate1 = producer.createDunningCandidate(context, sourceDoc);
		assertThat(candidate1).as("Candidate shall be generated").isNotNull();
		assertDunningCandidateValid(candidate1, context, sourceDoc);

		candidate1.setProcessed(true);
		InterfaceWrapperHelper.save(candidate1);

		final I_C_Dunning_Candidate candidate2 = producer.createDunningCandidate(context, sourceDoc);
		assertThat(candidate2).as("No candidate shall be generated because we already have a processed one: " + candidate1).isNull();
	}

	@Test
	public void test_createDunningCandidate_NotProcessed_but_DunningDocProcessed()
	{
		final Date dunningDate = TimeUtil.getDay(2013, 02, 01);
		final PlainDunningContext context = createPlainDunningContext(dunningDate, dunningLevel1_10);

		final IDunnableDoc sourceDoc = mkDunnableDocBuilder()
				.setDaysDue(15) // daysDue,
				.create();

		final I_C_Dunning_Candidate candidate1 = producer.createDunningCandidate(context, sourceDoc);
		assertThat(candidate1).as("Candidate shall be generated").isNotNull();

		// this state shall never be reached... but anyway, it shall be handled
		candidate1.setProcessed(false);
		candidate1.setIsDunningDocProcessed(true);
		InterfaceWrapperHelper.save(candidate1);

		// shall throw exception
		assertThatThrownBy(() -> producer.createDunningCandidate(context, sourceDoc))
				.isInstanceOf(InconsistentDunningCandidateStateException.class);
	}

	@Test
	public void test_createDunningCandidate_FullUpdate()
	{
		final Date dunningDate = TimeUtil.getDay(2013, 02, 01);
		final PlainDunningContext context = createPlainDunningContext(dunningDate, dunningLevel1_10);

		final IDunnableDoc sourceDoc = mkDunnableDocBuilder()
				.setDaysDue(15) // daysDue,
				.create();

		final I_C_Dunning_Candidate candidate = producer.createDunningCandidate(context, sourceDoc);
		assertThat(candidate).as("Candidate shall be generated").isNotNull();
		assertDunningCandidateValid(candidate, context, sourceDoc);

		{
			context.setProperty(IDunningCandidateProducer.CONTEXT_FullUpdate, false);
			dao.setStaled(candidate, false);
			final I_C_Dunning_Candidate candidate2 = producer.createDunningCandidate(context, sourceDoc);
			assertThat(candidate2).as("No candidate shall be produced (staled=false, fullUpdate=false)").isNull();
		}
		{
			context.setProperty(IDunningCandidateProducer.CONTEXT_FullUpdate, false);
			dao.setStaled(candidate, true);
			final I_C_Dunning_Candidate candidate2 = producer.createDunningCandidate(context, sourceDoc);
			assertThat(candidate2).as("Candidate shall be produced (staled=true, fullUpdate=false)").isNotNull();
			assertDunningCandidateValid(candidate2, context, sourceDoc);
		}
		{
			context.setProperty(IDunningCandidateProducer.CONTEXT_FullUpdate, true);
			dao.setStaled(candidate, false);
			final I_C_Dunning_Candidate candidate2 = producer.createDunningCandidate(context, sourceDoc);
			assertThat(candidate2).as("Candidate shall be produced (staled=false, fullUpdate=true)").isNotNull();
			assertDunningCandidateValid(candidate2, context, sourceDoc);
		}
		{
			context.isProperty(IDunningCandidateProducer.CONTEXT_FullUpdate, true);
			dao.setStaled(candidate, true);
			final I_C_Dunning_Candidate candidate2 = producer.createDunningCandidate(context, sourceDoc);
			assertThat(candidate2).as("Candidate shall be produced (staled=true, fullUpdate=true)").isNotNull();
			assertDunningCandidateValid(candidate2, context, sourceDoc);
		}
	}

	@Test
	public void test_createDunningCandidate_DunningCurrencyShallBeUsedIfSpecified()
	{
		final Date dunningDate = TimeUtil.getDay(2013, 02, 01);
		final PlainDunningContext context = createPlainDunningContext(dunningDate, dunningLevel1_10);

		dunningUtil.setRate(currencyEUR, currencyCHF, new BigDecimal("1.5"));

		dunning.setC_Currency_ID(currencyCHF.getRepoId());
		InterfaceWrapperHelper.save(dunning);

		final IDunnableDoc sourceDoc = mkDunnableDocBuilder()
				.setDaysDue(15) // daysDue,
				.setC_Currency_ID(currencyEUR.getRepoId())
				.setTotalAmt(new BigDecimal("100"))
				.setOpenAmt(new BigDecimal("100"))
				.create();

		final I_C_Dunning_Candidate candidate = producer.createDunningCandidate(context, sourceDoc);
		assertThat(candidate).as("Candidate shall be generated").isNotNull();
		assertThat(candidate.getC_Currency_ID()).as("Candidate - Invalid Currency").isEqualTo(currencyCHF.getRepoId());
		assertThat(candidate.getTotalAmt()).as("Candidate - Invalid TotalAmt").isEqualByComparingTo(new BigDecimal("150"));
		assertThat(candidate.getOpenAmt()).as("Candidate - Invalid OpenAmt").isEqualByComparingTo(new BigDecimal("150"));
	}

	@Test
	public void test_isEligible_PreviousLevel_missingCandidate()
	{
		//
		// Setup dunning context
		final Date dunningDate = TimeUtil.getDay(2012, 02, 01);
		final PlainDunningContext context = createPlainDunningContext(dunningDate, dunningLevel2_20);

		final IDunnableDoc sourceDoc = mkDunnableDocBuilder()
				.setDaysDue(25) // daysDue, suitable for level 2
				.setC_Currency_ID(currencyEUR.getRepoId())
				.setTotalAmt(new BigDecimal("100"))
				.setOpenAmt(new BigDecimal("100"))
				.create();

		final I_C_Dunning_Candidate candidate = producer.createDunningCandidate(context, sourceDoc);
		assertThat(candidate).as("No candidate shall be created").isNull();
	}

	/**
	 * Case:
	 * <ul>
	 * <li>there exists a candidate for level1 but is not DunningDocProcessed
	 * </ul>
	 */
	@Test
	public void test_isEligible_PreviousLevel_NotDunningDocProcessed()
	{
		final IDunnableDoc sourceDoc = mkDunnableDocBuilder()
				.setDaysDue(25) // daysDue, suitable for level 2
				.setC_Currency_ID(currencyEUR.getRepoId())
				.setTotalAmt(new BigDecimal("100"))
				.setOpenAmt(new BigDecimal("100"))
				.create();

		final PlainDunningContext context1 = createPlainDunningContext("2012-02-01", dunningLevel1_10);
		final I_C_Dunning_Candidate candidate1 = producer.createDunningCandidate(context1, sourceDoc);
		assertThat(candidate1).as("Candidate for level 1 shall be generated").isNotNull();

		final PlainDunningContext context2 = createPlainDunningContext("2012-02-20", dunningLevel2_20);
		final I_C_Dunning_Candidate candidate2 = producer.createDunningCandidate(context2, sourceDoc);
		assertThat(candidate2).as("Candidate for level 2 shall not be generated").isNull();
	}

	@Test
	public void test_isEligible_PreviousLevel_DunningDocProcessed()
	{
		final IDunnableDoc sourceDoc = mkDunnableDocBuilder()
				.setDaysDue(35) // daysDue, suitable for level 3
				.setC_Currency_ID(currencyEUR.getRepoId())
				.setTotalAmt(new BigDecimal("100"))
				.setOpenAmt(new BigDecimal("100"))
				.create();

		final PlainDunningContext context1 = createPlainDunningContext("2012-02-01", dunningLevel1_10);
		final I_C_Dunning_Candidate candidate1 = producer.createDunningCandidate(context1, sourceDoc);
		assertThat(candidate1).as("Candidate for level 1 shall be generated").isNotNull();

		candidate1.setIsDunningDocProcessed(true);
		InterfaceWrapperHelper.save(candidate1);

		final PlainDunningContext context2 = createPlainDunningContext("2012-02-20", dunningLevel2_20);
		final I_C_Dunning_Candidate candidate2 = producer.createDunningCandidate(context2, sourceDoc);
		assertThat(candidate2).as("Candidate for level 2 shall be generated").isNotNull();

		candidate2.setIsDunningDocProcessed(true);
		InterfaceWrapperHelper.save(candidate2);

		final PlainDunningContext context3 = createPlainDunningContext("2012-03-01", dunningLevel3_30);
		final I_C_Dunning_Candidate candidate3 = producer.createDunningCandidate(context3, sourceDoc);
		assertThat(candidate3).as("Candidate for level 3 shall be generated").isNotNull();

		candidate3.setIsDunningDocProcessed(true);
		InterfaceWrapperHelper.save(candidate3);
	}

	@Test
	public void test_isEligible_DaysBetweenDunnings()
	{
		dunningLevel2_20.setDaysBetweenDunning(10);

		final IDunnableDoc sourceDoc = mkDunnableDocBuilder()
				.setDaysDue(25) // daysDue, suitable for level 2
				.setC_Currency_ID(currencyEUR.getRepoId())
				.setTotalAmt(new BigDecimal("100"))
				.setOpenAmt(new BigDecimal("100"))
				.create();

		final PlainDunningContext context1 = createPlainDunningContext("2012-02-01", dunningLevel1_10);
		final I_C_Dunning_Candidate candidate1 = producer.createDunningCandidate(context1, sourceDoc);
		assertThat(candidate1).as("Candidate for level 1 shall be generated").isNotNull();

		candidate1.setIsDunningDocProcessed(true);
		candidate1.setDunningDateEffective(candidate1.getDunningDate());
		InterfaceWrapperHelper.save(candidate1);

		{
			final PlainDunningContext context2 = createPlainDunningContext("2012-02-05", dunningLevel2_20);
			final I_C_Dunning_Candidate candidate2 = producer.createDunningCandidate(context2, sourceDoc);
			assertThat(candidate2).as("Candidate for level 2 shall NOT be generated").isNull();
		}

		{
			final PlainDunningContext context2 = createPlainDunningContext("2012-02-11", dunningLevel2_20);
			final I_C_Dunning_Candidate candidate2 = producer.createDunningCandidate(context2, sourceDoc);
			assertThat(candidate2).as("Candidate for level 2 shall be generated").isNotNull();
		}

		{
			final PlainDunningContext context2 = createPlainDunningContext("2012-02-20", dunningLevel2_20);
			final I_C_Dunning_Candidate candidate2 = producer.createDunningCandidate(context2, sourceDoc);
			assertThat(candidate2).as("Candidate for level 2 shall be generated").isNotNull();
		}

	}

	@Test
	public void test_isEligible_NonSequentialDunnings()
	{
		dunning.setCreateLevelsSequentially(false);
		InterfaceWrapperHelper.save(dunning);
		dunningLevel2_20.setDaysBetweenDunning(10);
		InterfaceWrapperHelper.save(dunningLevel2_20);

		final IDunnableDoc sourceDoc = mkDunnableDocBuilder()
				.setDaysDue(25) // daysDue, suitable for level 2
				.setC_Currency_ID(currencyEUR.getRepoId())
				.setTotalAmt(new BigDecimal("100"))
				.setOpenAmt(new BigDecimal("100"))
				.create();

		final PlainDunningContext context1 = createPlainDunningContext("2012-02-01", dunningLevel1_10);
		final I_C_Dunning_Candidate candidate1 = producer.createDunningCandidate(context1, sourceDoc);
		// shall be created anyway because works the same way when sequentially or not
		assertThat(candidate1).as("Candidate for level 1 shall be generated").isNotNull();
		candidate1.setIsDunningDocProcessed(true);
		candidate1.setDunningDateEffective(candidate1.getDunningDate());

		final PlainDunningContext context2 = createPlainDunningContext("2012-02-05", dunningLevel2_20);

		// shall throw exception because not sequential dunnings are not supported
		assertThatThrownBy(() -> producer.createDunningCandidate(context2, sourceDoc))
				.isInstanceOf(NotImplementedDunningException.class);
	}

	@Test
	public void test_isEligible_GraceDate()
	{
		final DunnableDocBuilder builder = mkDunnableDocBuilder()
				.setDaysDue(25) // daysDue, suitable for level 2
				.setC_Currency_ID(currencyEUR.getRepoId())
				.setTotalAmt(new BigDecimal("100"))
				.setOpenAmt(new BigDecimal("100"));

		final Timestamp dunningDate = TimeUtil.getDay(2012, 02, 01);
		final PlainDunningContext context = createPlainDunningContext(dunningDate, dunningLevel1_10);

		// GraceDate null
		{
			final I_C_Dunning_Candidate candidate = producer.createDunningCandidate(context,
					builder.setGraceDate(null)
							.create()
			);
			assertThat(candidate).as("GraceDate null - candidate shall be generated").isNotNull();
		}
		// GraceDate before context DunningDate
		{
			final I_C_Dunning_Candidate candidate = producer.createDunningCandidate(context,
					builder.setGraceDate(TimeUtil.addDays(dunningDate, -1))
							.create()
			);
			assertThat(candidate).as("GraceDate before context DunningDate - candidate shall be generated").isNotNull();
		}
		// GraceDate equals context DunningDate
		{
			final I_C_Dunning_Candidate candidate = producer.createDunningCandidate(context,
					builder.setGraceDate(dunningDate)
							.create()
			);
			assertThat(candidate).as("GraceDate equals with context DunningDate - candidate shall NOT be generated").isNull();
		}
		// GraceDate after context DunningDate
		{
			final I_C_Dunning_Candidate candidate = producer.createDunningCandidate(context,
					builder.setGraceDate(TimeUtil.addDays(dunningDate, 1))
							.create()
			);
			assertThat(candidate).as("GraceDate after context DunningDate - candidate shall NOT be generated").isNull();
		}
	}

	@Test
	public void test_getLastDunningDateEffective_emptyCollection()
	{
		final List<I_C_Dunning_Candidate> candidates = Collections.emptyList();
		final Date lastDate = producer.getLastDunningDateEffective(candidates);
		assertThat(lastDate).as("Last DunningDateEffective shall be null for an empty collection").isNull();
	}

	@Test
	public void test_getLastDunningDateEffective()
	{
		final List<I_C_Dunning_Candidate> candidates = new ArrayList<>();
		{
			final I_C_Dunning_Candidate c = db.newInstance(I_C_Dunning_Candidate.class);
			c.setIsDunningDocProcessed(true);
			c.setDunningDateEffective(TimeUtil.getDay(2013, 01, 01));
			InterfaceWrapperHelper.save(c);
			candidates.add(c);
		}
		{
			final I_C_Dunning_Candidate c = db.newInstance(I_C_Dunning_Candidate.class);
			c.setIsDunningDocProcessed(true);
			c.setDunningDateEffective(TimeUtil.getDay(2013, 01, 02));
			InterfaceWrapperHelper.save(c);
			candidates.add(c);
		}
		{
			// this one shall be skipped because is not doc processed, even if is the biggest one
			final I_C_Dunning_Candidate c = db.newInstance(I_C_Dunning_Candidate.class);
			c.setIsDunningDocProcessed(false);
			c.setDunningDateEffective(TimeUtil.getDay(2014, 01, 01));
			InterfaceWrapperHelper.save(c);
			candidates.add(c);
		}
		{
			final I_C_Dunning_Candidate c = db.newInstance(I_C_Dunning_Candidate.class);
			c.setIsDunningDocProcessed(true);
			c.setDunningDateEffective(TimeUtil.getDay(2013, 01, 04)); // this is the maximum
			InterfaceWrapperHelper.save(c);
			candidates.add(c);
		}
		{
			final I_C_Dunning_Candidate c = db.newInstance(I_C_Dunning_Candidate.class);
			c.setIsDunningDocProcessed(true);
			c.setDunningDateEffective(TimeUtil.getDay(2013, 01, 03));
			InterfaceWrapperHelper.save(c);
			candidates.add(c);
		}

		final Date lastDate = producer.getLastDunningDateEffective(candidates);
		assertThat(lastDate).as("Last DunningDateEffective shall be null for an empty collection").isEqualTo(TimeUtil.getDay(2013, 01, 04));
	}

	@Test
	public void test_getLastDunningDateEffective_null_DunningDateEffective()
	{
		final List<I_C_Dunning_Candidate> candidates = new ArrayList<>();
		{
			final I_C_Dunning_Candidate c = db.newInstance(I_C_Dunning_Candidate.class);
			c.setIsDunningDocProcessed(true);
			c.setDunningDateEffective(null);
			InterfaceWrapperHelper.save(c);
			candidates.add(c);
		}

		// shall throw an exception because candidate's DunningDateEffective is null
		assertThatThrownBy(() -> producer.getLastDunningDateEffective(candidates))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	public void test_getDaysAfterLastDunningEffective_NotAvailable()
	{
		final List<I_C_Dunning_Candidate> candidates = Collections.emptyList();

		final Timestamp dunningDate = TimeUtil.getDay(2013, 03, 01);
		final int daysAfterLast = producer.getDaysAfterLastDunningEffective(dunningDate, candidates);
		assertThat(daysAfterLast).as("daysAfterLast shall be not available").isEqualTo(DefaultDunningCandidateProducer.DAYS_NotAvailable);
	}

	@Test
	public void test_getDaysAfterLastDunningEffective()
	{
		final List<I_C_Dunning_Candidate> candidates = new ArrayList<>();
		{
			final I_C_Dunning_Candidate c = db.newInstance(I_C_Dunning_Candidate.class);
			c.setIsDunningDocProcessed(true);
			c.setDunningDateEffective(TimeUtil.getDay(2013, 01, 02));
			InterfaceWrapperHelper.save(c);
			candidates.add(c);
		}

		//
		{
			final Timestamp dunningDate = TimeUtil.getDay(2013, 01, 02);
			final int daysAfterLast = producer.getDaysAfterLastDunningEffective(dunningDate, candidates);
			assertThat(daysAfterLast).as("Invalid DaysAfterLast").isEqualTo(0);
		}
		{
			final Timestamp dunningDate = TimeUtil.getDay(2013, 01, 03);
			final int daysAfterLast = producer.getDaysAfterLastDunningEffective(dunningDate, candidates);
			assertThat(daysAfterLast).as("Invalid DaysAfterLast").isEqualTo(1);
		}
		{
			final Timestamp dunningDate = TimeUtil.getDay(2013, 01, 10);
			final int daysAfterLast = producer.getDaysAfterLastDunningEffective(dunningDate, candidates);
			assertThat(daysAfterLast).as("Invalid DaysAfterLast").isEqualTo(8);
		}
	}

	@Test
	public void test_getRequiredDaysBetweenDunnings() // NOPMD by tsa on 3/22/13 7:57 PM
	{
		// expectedDaysBetweenDunnings / ShowAllDue / ShowNotDue / levelDaysBetweenDunnings
		test_getRequiredDaysBetweenDunnings(0, false, false, -10);
		test_getRequiredDaysBetweenDunnings(0, false, false, 0);
		test_getRequiredDaysBetweenDunnings(5, false, false, 5);

		test_getRequiredDaysBetweenDunnings(0, false, true, 10);
		test_getRequiredDaysBetweenDunnings(0, true, false, 10);
		test_getRequiredDaysBetweenDunnings(0, true, true, 10);
	}

	private void test_getRequiredDaysBetweenDunnings(int expectedDaysBetweenDunnings, boolean ShowAllDue, boolean ShowNotDue, int levelDaysBetweenDunnings) // NOPMD by tsa on 3/22/13 7:57 PM
	{
		final Date dunningDate = null; // not needed
		final PlainDunningContext context = createPlainDunningContext(dunningDate, dunningLevel1_10);
		dunningLevel1_10.setIsShowAllDue(ShowAllDue);
		dunningLevel1_10.setIsShowNotDue(ShowNotDue);
		dunningLevel1_10.setDaysBetweenDunning(levelDaysBetweenDunnings);

		final int actualDaysBetweenDunnings = producer.getRequiredDaysBetweenDunnings(context);
		assertThat(actualDaysBetweenDunnings).as("Invalid DaysBetweenDunnings (level=" + dunningLevel1_10 + ")")
				.isEqualTo(expectedDaysBetweenDunnings);
	}

	@Test
	public void test_isDaysBetweenDunningsRespected_NoCandidates()
	{
		final Date dunningDate = null; // not required
		final int requiredDaysBetweenDunnings = 1000;
		final List<I_C_Dunning_Candidate> candidates = Collections.emptyList();
		final boolean respected = producer.isDaysBetweenDunningsRespected(dunningDate, requiredDaysBetweenDunnings, candidates);
		assertThat(respected).as("Shall be true for an empty collection").isTrue();
	}

	@Test
	public void test_isDaysBetweenDunningsRespected_LastDunningDateEffective_NotAvailable()
	{
		final List<I_C_Dunning_Candidate> candidates = new ArrayList<>();
		{
			final I_C_Dunning_Candidate c = db.newInstance(I_C_Dunning_Candidate.class);
			c.setIsDunningDocProcessed(false);
			c.setDunningDateEffective(TimeUtil.getDay(2013, 01, 01));
			InterfaceWrapperHelper.save(c);
			candidates.add(c);
		}

		final Date dunningDate = TimeUtil.getDay(2013, 01, 10); // not required
		final int requiredDaysBetweenDunnings = 5;

		// Shall throw an exception because Last Dunning Efective Date is not available
		assertThatThrownBy(() -> producer.isDaysBetweenDunningsRespected(dunningDate, requiredDaysBetweenDunnings, candidates))
				.isInstanceOf(InconsistentDunningCandidateStateException.class);
	}

	@Test
	public void test_isDaysBetweenDunningsRespected_DaysAfterLast_FutureCandidates()
	{
		final List<I_C_Dunning_Candidate> candidates = new ArrayList<>();
		{
			final I_C_Dunning_Candidate c = db.newInstance(I_C_Dunning_Candidate.class);
			c.setIsDunningDocProcessed(true);
			c.setDunningDateEffective(TimeUtil.getDay(2013, 01, 10));
			InterfaceWrapperHelper.save(c);
			candidates.add(c);
		}

		final Date dunningDate = TimeUtil.getDay(2013, 01, 01);
		final int requiredDaysBetweenDunnings = 3;

		final boolean respected = producer.isDaysBetweenDunningsRespected(dunningDate, requiredDaysBetweenDunnings, candidates);
		assertThat(respected).as("Shall be false because we have candidates in the future").isFalse();
	}

	@Test
	public void test_isDaysBetweenDunningsRespected_DaysAfterLast_NotPassedYet()
	{
		final List<I_C_Dunning_Candidate> candidates = new ArrayList<>();
		{
			final I_C_Dunning_Candidate c = db.newInstance(I_C_Dunning_Candidate.class);
			c.setIsDunningDocProcessed(true);
			c.setDunningDateEffective(TimeUtil.getDay(2013, 01, 01));
			InterfaceWrapperHelper.save(c);
			candidates.add(c);
		}

		final Date dunningDate = TimeUtil.getDay(2013, 01, 10);
		final int requiredDaysBetweenDunnings = 15;

		final boolean respected = producer.isDaysBetweenDunningsRespected(dunningDate, requiredDaysBetweenDunnings, candidates);
		assertThat(respected).as("Shall be false because interval is to narrow").isFalse();
	}

	@Test
	public void test_isDaysBetweenDunningsRespected_DaysAfterLast_Satisfied()
	{
		final List<I_C_Dunning_Candidate> candidates = new ArrayList<>();
		{
			final I_C_Dunning_Candidate c = db.newInstance(I_C_Dunning_Candidate.class);
			c.setIsDunningDocProcessed(true);
			c.setDunningDateEffective(TimeUtil.getDay(2013, 01, 01));
			InterfaceWrapperHelper.save(c);
			candidates.add(c);
		}

		final Date dunningDate = TimeUtil.getDay(2013, 01, 10);
		final int requiredDaysBetweenDunnings = 7;

		final boolean respected = producer.isDaysBetweenDunningsRespected(dunningDate, requiredDaysBetweenDunnings, candidates);
		assertThat(respected).as("Shall be true because everything is respected").isTrue();
	}

	private DunnableDocBuilder mkDunnableDocBuilder()
	{
		return new DunnableDocBuilder()
				.setTableName(I_C_Invoice.Table_Name)
				.setRecord_ID(1)
				.setC_BPartner_ID(1)
				.setC_BPartner_Location_ID(1)
				.setContact_ID(1)
				.setC_Currency_ID(currencyEUR.getRepoId())
				.setInDispute(false) // isInDispute
				//
				.setTotalAmt(BigDecimal.valueOf(100)) // totalAmt,
				.setOpenAmt(BigDecimal.valueOf(100)) // openAmt,
				//
				.setDueDate(TimeUtil.getDay(2013, 01, 01)) // dueDate,
				.setGraceDate(null);
	}

	private void assertDunningCandidateValid(final I_C_Dunning_Candidate candidate, final IDunningContext context, final IDunnableDoc fromDoc)
	{
		final I_C_DunningLevel candidateDunningLevel = InterfaceWrapperHelper.create(candidate.getC_DunningLevel(), I_C_DunningLevel.class);

		Assertions.assertEquals(fromDoc.getAD_Client_ID(), candidate.getAD_Client_ID(), "Invalid candidate - AD_Client_ID: " + candidate);
		Assertions.assertEquals(fromDoc.getAD_Org_ID(), candidate.getAD_Org_ID(), "Invalid candidate - AD_Org_ID: " + candidate);

		assertThat(fromDoc.getTableName()).as("Invalid candidate - AD_Table_ID: " + candidate).
				isEqualToIgnoringCase(Services.get(IADTableDAO.class).retrieveTableName(candidate.getAD_Table_ID()));

		Assertions.assertEquals(fromDoc.getRecordId(), candidate.getRecord_ID(), "Invalid candidate - Record_ID: " + candidate);
		Assertions.assertEquals(context.getC_DunningLevel(), candidate.getC_DunningLevel(), "Invalid candidate - C_DunningLevel: " + candidate);
		Assertions.assertFalse(candidate.isProcessed(), "Invalid candidate - Processed: " + candidate);
		Assertions.assertFalse(candidate.isDunningDocProcessed(), "Invalid candidate - IsDunningDocProcessed: " + candidate);
		Assertions.assertNull(candidate.getDunningDateEffective(), "Invalid candidate - DunningDateEffective: " + candidate);

		Assertions.assertEquals(context.getDunningDate(), candidate.getDunningDate(), "Invalid candidate - DunningDate: " + candidate);
		Assertions.assertEquals(fromDoc.getC_BPartner_ID(), candidate.getC_BPartner_ID(), "Invalid candidate - C_BPartner_ID: " + candidate);
		Assertions.assertEquals(fromDoc.getC_BPartner_Location_ID(), candidate.getC_BPartner_Location_ID(), "Invalid candidate - C_BPartner_Location_ID: " + candidate);
		Assertions.assertEquals(fromDoc.getContact_ID(), candidate.getC_Dunning_Contact_ID(), "Invalid candidate - C_Dunning_Contact_ID: " + candidate);
		Assertions.assertEquals(fromDoc.getDueDate(), candidate.getDueDate(), "Invalid candidate - DueDate: " + candidate);
		Assertions.assertEquals(fromDoc.getGraceDate(), candidate.getDunningGrace(), "Invalid candidate - DunningGrace: " + candidate);
		Assertions.assertEquals(fromDoc.getDaysDue(), candidate.getDaysDue(), "Invalid candidate - DaysDue: " + candidate);
		Assertions.assertEquals(candidateDunningLevel.isWriteOff(), candidate.isWriteOff(), "Invalid candidate - IsWriteOff: " + candidate);

		// NOTE: amounts and currency are not validated here
	}
}