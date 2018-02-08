/**
 *
 */
package org.adempiere.bpartner.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Locale;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.springframework.stereotype.Repository;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Repository
public class BPartnerCreditLimiRepository
{
	public BigDecimal retrieveCreditLimitByBPartnerId(final int bpartnerId)
	{
		final Timestamp today = SystemTime.asDayTimestamp();

		final I_C_BPartner_CreditLimit bpCreditLimit = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_CreditLimit.class)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addValidFromToMatchesFilter(I_C_BPartner_CreditLimit.COLUMN_DateFrom, I_C_BPartner_CreditLimit.COLUMN_DateTo, today)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_C_BPartner_CreditLimit.COLUMNNAME_Type)
				.orderBy(I_C_BPartner_CreditLimit.COLUMNNAME_DateTo)
				.create()
				.first();

		if (bpCreditLimit == null)
		{
			return BigDecimal.ZERO;
		}

		return bpCreditLimit.getAmount();

	}

	public void updateCreditLimitIndicator(final I_C_BPartner bpartner, final IBPartnerStats stats)
	{
		final  BigDecimal creditUsed = stats.getSOCreditUsed();
		final BPartnerCreditLimiRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimiRepository.class);
		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bpartner.getC_BPartner_ID());
		final BigDecimal percent = creditLimit.signum() == 0 ? BigDecimal.ZERO : creditUsed.divide(creditLimit, 2, BigDecimal.ROUND_HALF_UP);

		final Locale locale = Locale.getDefault();
		final NumberFormat fmt = NumberFormat.getPercentInstance(locale);
		fmt.setMinimumFractionDigits(1);
		fmt.setMaximumFractionDigits(1);

		final String percentSring = fmt.format(percent);
		bpartner.setCreditLimitIndicator(percentSring);
		InterfaceWrapperHelper.save(bpartner);
	}
}
