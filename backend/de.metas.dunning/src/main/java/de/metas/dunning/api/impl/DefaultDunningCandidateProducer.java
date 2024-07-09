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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningCandidateProducer;
import de.metas.dunning.api.IDunningCandidateProducerFactory;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.api.IDunningEventDispatcher;
import de.metas.dunning.api.IDunningUtil;
import de.metas.dunning.exception.InconsistentDunningCandidateStateException;
import de.metas.dunning.exception.NotImplementedDunningException;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation.
 * <p>
 * <b>IMPORTANT:</b><br>
 * Currently, this implementation is supposed to handle <b>all</b> <code>sourceDoc</code>'s, so its {@link #isHandled(IDunnableDoc)} method always returns <code>true</code>. This means, that
 * currently, no other implementation may be registered in the {@link IDunningCandidateProducerFactory}.
 *
 * @author ts
 */
public class DefaultDunningCandidateProducer implements IDunningCandidateProducer
{
	private final Logger logger = LogManager.getLogger(getClass());

	protected static final int DAYS_NotAvailable = Integer.MIN_VALUE;

	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	public boolean isHandled(final IDunnableDoc sourceDoc)
	{
		return true; // we handle them all :-)
	}

	@Override
	public I_C_Dunning_Candidate createDunningCandidate(final IDunningContext context, final IDunnableDoc sourceDoc)
	{
		if (!isEligible(context, sourceDoc))
		{
			return null;
		}

		final I_C_Dunning_Candidate[] candidate = new I_C_Dunning_Candidate[] { null };
		Services.get(ITrxManager.class).run(context.getTrxName(), context.getTrxRunnerConfig(), (TrxRunnable)localTrxName -> {
			final IDunningContext localContext = Services.get(IDunningBL.class).createDunningContext(context, localTrxName);
			candidate[0] = createDunningCandidate0(localContext, sourceDoc);
		});

		return candidate[0];
	}

	private I_C_Dunning_Candidate createDunningCandidate0(final IDunningContext context, final IDunnableDoc sourceDoc)
	{
		final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
		final I_C_DunningLevel dunningLevel = context.getC_DunningLevel();

		final int tableId = sourceDoc.getTableId();

		I_C_Dunning_Candidate candidate = dunningDAO.retrieveDunningCandidate(
				context,
				tableId,
				sourceDoc.getRecordId(),
				dunningLevel);
		if (candidate == null)
		{
			// Create a new one
			candidate = dunningDAO.newInstance(context, I_C_Dunning_Candidate.class);
			candidate.setAD_Table_ID(tableId);
			candidate.setRecord_ID(sourceDoc.getRecordId());
			candidate.setDocumentNo(sourceDoc.getDocumentNo());
			candidate.setC_DunningLevel(dunningLevel);
			candidate.setProcessed(false);
			candidate.setIsDunningDocProcessed(false);
			candidate.setDunningDateEffective(null); // will be set when the dunning document will be generated and processed

			candidate.setM_SectionCode_ID(sourceDoc.getM_SectionCode_ID());

			// We cannot set the candidate's AD_Client_ID, but at least we can validate that we are using the right AD_Client
			Check.assume(sourceDoc.getAD_Client_ID() == candidate.getAD_Client_ID(), "Invalid context AD_Client_ID");
		}
		else
		{
			// Candidate already exist

			if (candidate.isProcessed())
			{
				logger.info("There is a processed candidate for " + sourceDoc + ". Skip");
				return null;
			}

			if (candidate.isDunningDocProcessed())
			{
				throw new InconsistentDunningCandidateStateException("Inconsistent state: candidate is not processed but IsDunningDocProcessed=Y");
			}

		}

		candidate.setAD_Org_ID(sourceDoc.getAD_Org_ID());
		candidate.setDunningDate(LocalDateAndOrgId.toTimestamp(context.getDunningDate(), orgDAO::getTimeZone));
		candidate.setC_BPartner_ID(sourceDoc.getC_BPartner_ID());
		candidate.setC_BPartner_Location_ID(sourceDoc.getC_BPartner_Location_ID());
		candidate.setC_Dunning_Contact_ID(bPartnerBL.getDefaultDunningContact(BPartnerId.ofRepoId(sourceDoc.getC_BPartner_ID()))
												  .map(UserId::getRepoId)
												  .orElse(sourceDoc.getContact_ID()));
		candidate.setDueDate(sourceDoc.getDueDate().toTimestamp(orgDAO::getTimeZone));
		candidate.setDunningGrace(LocalDateAndOrgId.toTimestamp(sourceDoc.getGraceDate(), orgDAO::getTimeZone));
		candidate.setDaysDue(sourceDoc.getDaysDue());
		candidate.setPOReference(sourceDoc.getPoReference());
		candidate.setIsWriteOff(dunningLevel.isWriteOff());

		final I_C_Dunning dunning = dunningLevel.getC_Dunning();
		if (dunning.getC_Currency_ID() > 0)
		{
			candidate.setC_Currency_ID(dunning.getC_Currency_ID());
		}
		else
		{
			candidate.setC_Currency_ID(sourceDoc.getC_Currency_ID());
		}

		final IDunningUtil util = Services.get(IDunningUtil.class);
		final BigDecimal totalAmtSrc = sourceDoc.getTotalAmt();
		final BigDecimal totalAmt = util.currencyConvert(
				totalAmtSrc, // Amount(src)
				sourceDoc.getC_Currency_ID(), candidate.getC_Currency_ID(), // Currency From -> To
				candidate.getDunningDate(), // Conversion Date
				util.getDefaultCurrencyConvertionTypeId(),
				candidate.getAD_Client_ID(), candidate.getAD_Org_ID());
		candidate.setTotalAmt(totalAmt);

		final BigDecimal openAmtSrc = sourceDoc.getOpenAmt();
		final BigDecimal openAmt = util.currencyConvert(
				openAmtSrc, // Amount(src)
				sourceDoc.getC_Currency_ID(), candidate.getC_Currency_ID(), // Currency From -> To
				candidate.getDunningDate(), // Conversion Date
				util.getDefaultCurrencyConvertionTypeId(),
				candidate.getAD_Client_ID(), candidate.getAD_Org_ID());
		candidate.setOpenAmt(openAmt);

		final BigDecimal interestAmtSrc = dunningLevel.getInterestPercent().divide(Env.ONEHUNDRED).multiply(sourceDoc.getOpenAmt());
		final BigDecimal interestAmt = util.currencyConvert(
				interestAmtSrc, // Amount(src)
				sourceDoc.getC_Currency_ID(), candidate.getC_Currency_ID(), // Currency From -> To
				candidate.getDunningDate(), // Conversion Date
				util.getDefaultCurrencyConvertionTypeId(),
				candidate.getAD_Client_ID(), candidate.getAD_Org_ID());
		candidate.setDunningInterestAmt(interestAmt);
		candidate.setFeeAmt(dunningLevel.getFeeAmt());

		Services.get(IDunningEventDispatcher.class).fireDunningCandidateEvent(IDunningBL.EVENT_NewDunningCandidate, candidate);

		dunningDAO.save(candidate);
		return candidate;
	}

	/**
	 * Check if given <code>sourceDoc</code> is eligible for creating {@link I_C_Dunning_Candidate}
	 *
	 * @param context
	 * @param sourceDoc
	 * @return true if eligible
	 */
	protected boolean isEligible(final IDunningContext context, final IDunnableDoc sourceDoc)
	{
		final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);

		final int tableId = sourceDoc.getTableId();
		final int recordId = sourceDoc.getRecordId();
		final I_C_DunningLevel dunningLevel = context.getC_DunningLevel();

		List<I_C_Dunning_Candidate> previousCandidates = null;

		//
		// Validate DunningGrace
		if (sourceDoc.getGraceDate() != null && sourceDoc.getGraceDate().compareTo(context.getDunningDate()) >= 0)
		{
			logger.info("Skip because DunnableDoc's grace date is >= context DunningDate");
			return false;
		}

		//
		// If sequentially we must check for other levels with smaller days for
		// which this invoice is not yet included!
		if (dunningLevel.getC_Dunning().isCreateLevelsSequentially())
		{
			final List<I_C_DunningLevel> previousLevels = Services.get(IDunningBL.class).getPreviousLevels(dunningLevel);
			if (previousLevels.isEmpty())
			{
				// This is the first Level on which we dunn => consider it eligible
				return true;
			}

			previousCandidates = dunningDAO.retrieveDunningCandidates(context, tableId, recordId, previousLevels);
			for (final I_C_DunningLevel previousLevel : previousLevels)
			{
				// Search if we already have a candidate for previousLevel
				boolean found = false;
				for (final I_C_Dunning_Candidate candidate : previousCandidates)
				{
					if (!candidate.isDunningDocProcessed())
					{
						logger.info("Skip creating dunning candidate for " + sourceDoc + " because we have an unprocessed previous level");
						return false;
					}

					if (candidate.getC_DunningLevel_ID() == previousLevel.getC_DunningLevel_ID())
					{
						found = true;
						break;
					}
				}

				if (!found)
				{
					logger.info("Skip creating dunning candidate for " + sourceDoc + " because previous sequential level not found: " + previousLevel);
					return false;
				}
			}
		}

		//
		// Make sure we respect the days between dunnings
		final int requiredDaysBetweenDunnings = getRequiredDaysBetweenDunnings(context);
		if (requiredDaysBetweenDunnings > 0)
		{
			if (previousCandidates == null)
			{
				// Previous candidates are loaded if we have sequential dunning.
				// Because we haven't sequential dunning, here we are loading all candidates
				// previousCandidates = dunningDAO.retrieveDunningCandidates(context, tableId, recordId);
				throw new NotImplementedDunningException(NotImplementedDunningException.FEATURE_NonSequentialDunning);
			}
			if (!isDaysBetweenDunningsRespected(context.getDunningDate(), requiredDaysBetweenDunnings, previousCandidates))
			{
				return false;
			}

			logger.info("Skip because days between dunning is not respected");
		}

		return true;
	}

	/**
	 * Gets required days between dunnings
	 *
	 * @param context
	 * @return required days between dunnings or ZERO if not enforced
	 */
	protected int getRequiredDaysBetweenDunnings(final IDunningContext context)
	{
		final I_C_DunningLevel dunningLevel = context.getC_DunningLevel();
		if (dunningLevel.isShowAllDue())
		{
			return 0;
		}
		if (dunningLevel.isShowNotDue())
		{
			return 0;
		}

		final int requiredDaysBetweenDunnings = dunningLevel.getDaysBetweenDunning();
		if (requiredDaysBetweenDunnings <= 0)
		{
			// no days between dunnings specified => rule not required
			return 0;
		}

		return requiredDaysBetweenDunnings;
	}

	protected boolean isDaysBetweenDunningsRespected(
			@Nullable final LocalDateAndOrgId dunningDate,
			final int requiredDaysBetweenDunnings,
			@NonNull final List<I_C_Dunning_Candidate> candidates)
	{
		if (candidates.isEmpty())
		{
			// no previous candidates => of course is respected
			return true;
		}

		final int daysAfterLast = getDaysAfterLastDunningEffective(dunningDate, candidates);
		if (daysAfterLast == DAYS_NotAvailable)
		{
			// TODO if days after last dunning could not be calculated (e.g. all candidates are not processed) then consider it "Respected")
			// NOTE: Discussion with Mark: if sequentially (see there) this case cannot happen => internal error
			throw new InconsistentDunningCandidateStateException("Inconsistent state: DaysAfterLast is not available for (DunningDate=" + dunningDate + ", candidates=" + candidates + ")");
		}
		else if (daysAfterLast < 0)
		{
			// We have future dunning candidates (relatively to dunningDate) => rule not respected
			// metas-mo: don't create candidates!

			logger.info("Negative DaysAfterLast={}. Consider rule not respected", daysAfterLast);
			return false;
		}
		else if (daysAfterLast >= requiredDaysBetweenDunnings)
		{
			// it passed more days after last dunning then required, defenetelly we can dun again
			return true;
		}

		return false;
	}

	@Nullable
	protected LocalDateAndOrgId getLastDunningDateEffective(
			@NonNull final List<I_C_Dunning_Candidate> candidates,
			@NonNull final OrgId orgId)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		LocalDate lastDunningDateEffective = null;
		for (final I_C_Dunning_Candidate candidate : candidates)
		{
			// When we are calculating the effective date, we consider only candidates that have processed dunning docs
			if (!candidate.isDunningDocProcessed())
			{
				continue;
			}

			final LocalDate dunningDateEffective = TimeUtil.asLocalDate(candidate.getDunningDateEffective(), zoneId);
			Check.assumeNotNull(dunningDateEffective, "DunningDateEffective shall be available for candidate with dunning docs processed: {}", candidate);

			if (lastDunningDateEffective == null)
			{
				lastDunningDateEffective = dunningDateEffective;
			}
			else if (lastDunningDateEffective.isBefore(dunningDateEffective))
			{
				lastDunningDateEffective = dunningDateEffective;
			}
		}

		return LocalDateAndOrgId.ofNullableLocalDate(lastDunningDateEffective, orgId);
	}

	/**
	 * Gets Days after last DunningDateEffective
	 *
	 * @param dunningDate
	 * @param candidates
	 * @return days after DunningDateEffective or {@link #DAYS_NotAvailable} if not available
	 */
	protected int getDaysAfterLastDunningEffective(
			@Nullable final LocalDateAndOrgId dunningDate,
			@NonNull final List<I_C_Dunning_Candidate> candidates)
	{
		if (dunningDate == null)
		{
			return DAYS_NotAvailable; 
		}
		
		final LocalDateAndOrgId lastDunningDate = getLastDunningDateEffective(candidates, dunningDate.getOrgId());
		if (lastDunningDate == null)
		{
			return DAYS_NotAvailable;
		}

		return TimeUtil.getDaysBetween(lastDunningDate, dunningDate);
	}
}
