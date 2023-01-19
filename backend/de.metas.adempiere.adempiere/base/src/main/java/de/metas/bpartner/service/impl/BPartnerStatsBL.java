package de.metas.bpartner.service.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerStatsBL;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.model.X_C_BPartner_Stats;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Locale;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Service
public class BPartnerStatsBL implements IBPartnerStatsBL
{
	private final IBPartnerStatsDAO bPartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);
	final BPartnerCreditLimitRepository creditLimitRepo;

	private BPartnerStatsBL(@NonNull final BPartnerCreditLimitRepository creditLimitRepo)
	{
		this.creditLimitRepo = creditLimitRepo;
	}

	@Override
	public String calculateProjectedSOCreditStatus(@NonNull final CalculateSOCreditStatusRequest request)
	{
		final BPartnerStats bpStats = request.getStat();
		final BigDecimal additionalAmt = request.getAdditionalAmt();
		final Timestamp date = request.getDate();

		final String initialCreditStatus = bpStats.getSoCreditStatus();

		if (!request.isForceCheckCreditStatus() && additionalAmt.signum() == 0)
		{
			return initialCreditStatus;
		}

		// get credit limit from BPartner
		final I_C_BPartner partner = Services.get(IBPartnerDAO.class).getById(bpStats.getBpartnerId());

		BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(partner.getC_BPartner_ID(), date);

		// Nothing to do
		if (X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck.equals(initialCreditStatus)
				|| (X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(initialCreditStatus) && !request.isForceCheckCreditStatus())
				|| creditLimit.signum() == 0)
		{
			return initialCreditStatus;
		}

		// Above (reduced) Credit Limit
		creditLimit = creditLimit.subtract(additionalAmt);
		final BigDecimal so_creditUsed = bpStats.getSoCreditUsed();
		if (creditLimit.compareTo(so_creditUsed) < 0)
		{
			return X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold;
		}

		// Above Watch Limit
		final BigDecimal watchAmt = creditLimit.multiply(getCreditWatchRatio(bpStats));
		if (watchAmt.compareTo(so_creditUsed) < 0)
		{
			return X_C_BPartner_Stats.SOCREDITSTATUS_CreditWatch;
		}

		// is OK
		return X_C_BPartner_Stats.SOCREDITSTATUS_CreditOK;
	}

	@Override
	public boolean isCreditStopSales(@NonNull final BPartnerStats stat, @NonNull final BigDecimal grandTotal, @NonNull final Timestamp date)
	{
		final CalculateSOCreditStatusRequest request = CalculateSOCreditStatusRequest.builder()
				.stat(stat)
				.additionalAmt(grandTotal)
				.date(date)
				.build();
		final String futureCreditStatus = calculateProjectedSOCreditStatus(request);

		if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(futureCreditStatus))
		{
			return true;
		}

		return false;
	}

	@Override
	public BigDecimal getCreditWatchRatio(final BPartnerStats stats)
	{
		// bp group will be taken from the stats' bpartner
		final I_C_BPartner partner = Services.get(IBPartnerDAO.class).getById(stats.getBpartnerId());

		final I_C_BP_Group bpGroup = partner.getC_BP_Group();
		final BigDecimal creditWatchPercent = bpGroup.getCreditWatchPercent();

		if (creditWatchPercent.signum() == 0)
		{
			return new BigDecimal(0.90);
		}

		return creditWatchPercent.divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public void resetCreditStatusFromBPGroup(@NonNull final I_C_BPartner bpartner)
	{
		final BPartnerStats bpartnerStats = bPartnerStatsDAO.getCreateBPartnerStats(bpartner);
		final I_C_BP_Group bpGroup = bpartner.getC_BP_Group();
		final String creditStatus = bpGroup.getSOCreditStatus();

		if (Check.isEmpty(creditStatus, true))
		{
			return;
		}

		final I_C_BPartner_Stats stats = load(bpartnerStats.getRepoId(), I_C_BPartner_Stats.class);
		stats.setSOCreditStatus(creditStatus);
		save(stats);
	}

	public void enableCreditLimitCheck(@NonNull final BPartnerId bPartnerId)
	{
		final BPartnerStats bPartnerStats = bPartnerStatsDAO.getCreateBPartnerStats(bPartnerId);

		if (!X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck.equals(bPartnerStats.getSoCreditUsed()))
		{
			return;
		}

		bPartnerStatsDAO.setSOCreditStatus(bPartnerStats, X_C_BPartner_Stats.SOCREDITSTATUS_CreditWatch);
	}

	@Override
	public void updateBPartnerStatistics(BPartnerStats bpStats)
	{
		updateOpenItems(bpStats);
		updateActualLifeTimeValue(bpStats);
		updateSOCreditUsed(bpStats);
		updateSOCreditStatus(bpStats);
		updateDeliveryCreditUsed(bpStats);
		updateCreditLimitIndicator(bpStats);
	}

	private void updateOpenItems(@NonNull final BPartnerStats bpStats)
	{
		// load the statistics
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);

		final BigDecimal openItems = bPartnerStatsDAO.retrieveOpenItems(bpStats);

		// update the statistics with the up tp date openItems
		stats.setOpenItems(openItems);

		// save in db
		saveRecord(stats);
	}

	private void updateActualLifeTimeValue(final BPartnerStats bpStats)
	{
		BigDecimal actualLifeTimeValue = bPartnerStatsDAO.computeActualLifeTimeValue(bpStats.getBpartnerId());

		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);
		stats.setActualLifeTimeValue(actualLifeTimeValue);
		saveRecord(stats);
	}

	private void updateSOCreditUsed(final BPartnerStats bpStats)
	{
		final BigDecimal SO_CreditUsed = bPartnerStatsDAO.retrieveSOCreditUsed(bpStats);
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);
		stats.setSO_CreditUsed(SO_CreditUsed);
		saveRecord(stats);
	}

	private void updateSOCreditStatus(@NonNull final BPartnerStats bpStats)
	{
		final IBPartnerStatsBL bpartnerStatsBL = Services.get(IBPartnerStatsBL.class);

		// load the statistics
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);
		final BigDecimal creditUsed = stats.getSO_CreditUsed();

		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bpStats.getBpartnerId().getRepoId(), SystemTime.asDayTimestamp());

		final String initialCreditStatus = bpStats.getSoCreditStatus();

		String creditStatusToSet;

		// Nothing to do
		if (X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck.equals(initialCreditStatus)
				|| X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(initialCreditStatus)
				|| BigDecimal.ZERO.compareTo(creditLimit) == 0)
		{
			return;
		}

		// Above Credit Limit
		if (creditLimit.compareTo(creditUsed) < 0)
		{
			creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold;
		}
		else
		{
			// Above Watch Limit
			final BigDecimal watchAmt = creditLimit.multiply(bpartnerStatsBL.getCreditWatchRatio(bpStats));

			if (watchAmt.compareTo(creditUsed) < 0)
			{
				creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditWatch;
			}
			else
			{
				// is OK
				creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditOK;
			}
		}

		stats.setSOCreditStatus(creditStatusToSet);
	}

	private void updateDeliveryCreditUsed(@NonNull final BPartnerStats bpStats)
	{
		final IBPartnerStatsBL bpartnerStatsBL = Services.get(IBPartnerStatsBL.class);

		// in accounting schema currency
		// todo
		final String initialCreditStatus = bpStats.getSoCreditStatus();

		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bpStats.getBpartnerId().getRepoId(), SystemTime.asDayTimestamp());

		// Nothing to do
		if (X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck.equals(initialCreditStatus)
				|| X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(initialCreditStatus)
				|| BigDecimal.ZERO.compareTo(creditLimit) == 0)
		{
			return;
		}

		String creditStatusToSet;

		// load the statistics
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);
		final BigDecimal creditUsed = stats.getSO_CreditUsed();

		// Above Credit Limit
		if (creditLimit.compareTo(creditUsed) < 0)
		{
			creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold;
		}
		else
		{
			// Above Watch Limit
			final BigDecimal watchAmt = creditLimit.multiply(bpartnerStatsBL.getCreditWatchRatio(bpStats));

			if (watchAmt.compareTo(creditUsed) < 0)
			{
				creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditWatch;
			}
			else
			{
				// is OK
				creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditOK;
			}
		}

		stats.setSOCreditStatus(creditStatusToSet);
	}

	private void updateCreditLimitIndicator(@NonNull final BPartnerStats bstats)
	{
		// load the statistics
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bstats);
		final BigDecimal creditUsed = stats.getSO_CreditUsed();

		final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(stats.getC_BPartner_ID(), SystemTime.asDayTimestamp());

		final BigDecimal percent = creditLimit.signum() == 0 ? BigDecimal.ZERO : creditUsed.divide(creditLimit, 2, BigDecimal.ROUND_HALF_UP);
		final Locale locale = Locale.getDefault();
		final NumberFormat fmt = NumberFormat.getPercentInstance(locale);
		fmt.setMinimumFractionDigits(1);
		fmt.setMaximumFractionDigits(1);
		final String percentSring = fmt.format(percent);

		stats.setCreditLimitIndicator(percentSring);

		saveRecord(stats);
	}
}
