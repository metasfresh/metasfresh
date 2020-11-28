package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.Adempiere;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.model.X_C_BPartner_Stats;
import org.compiere.util.Env;

import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerStatsBL;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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

public class BPartnerStatsBL implements IBPartnerStatsBL
{
	@Override
	public String calculateProjectedSOCreditStatus(@NonNull final CalculateSOCreditStatusRequest request)
	{
		final BPartnerStats bpStats = request.getStat();
		final BigDecimal additionalAmt = request.getAdditionalAmt();
		final Timestamp date = request.getDate();

		final String initialCreditStatus = bpStats.getSOCreditStatus();

		if (!request.isForceCheckCreditStatus() && additionalAmt.signum() == 0)
		{
			return initialCreditStatus;
		}

		// get credit limit from BPartner
		final I_C_BPartner partner = Services.get(IBPartnerDAO.class).getById(bpStats.getBpartnerId());
		final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
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
		final BigDecimal so_creditUsed = bpStats.getSOCreditUsed();
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
		final BPartnerStats bpartnerStats = Services.get(IBPartnerStatsDAO.class).getCreateBPartnerStats(bpartner);
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

}
