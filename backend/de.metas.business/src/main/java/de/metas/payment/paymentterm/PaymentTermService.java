package de.metas.payment.paymentterm;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.util.Util.ArrayKey;
import org.springframework.stereotype.Service;

import de.metas.cache.CCache;
import de.metas.util.Services;
import de.metas.util.lang.Percent;

import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class PaymentTermService
{
	private final CCache<ArrayKey, PaymentTermId> cache = CCache.newCache(I_C_PaymentTerm.Table_Name, 10, CCache.EXPIREMINUTES_Never);

	/**
	 * @param basePaymentTermId may be null
	 * @param discount may be null
	 */
	public PaymentTermId getOrCreateDerivedPaymentTerm(
			@Nullable final PaymentTermId basePaymentTermId,
			@Nullable final Percent discount)
	{
		if (basePaymentTermId == null)
		{
			return null; // the caller gave us no base to derive from
		}
		if (discount == null)
		{
			return basePaymentTermId; // the caller did not specify a change, so we return the base we got
		}

		final ArrayKey key = ArrayKey.of(basePaymentTermId, discount);
		return cache.getOrLoad(key, () -> getOrCreateDerivedPaymentTerm0(basePaymentTermId, discount));
	}

	private PaymentTermId getOrCreateDerivedPaymentTerm0(
			@NonNull final PaymentTermId basePaymentTermId,
			@NonNull final Percent discount)
	{
		final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
		final I_C_PaymentTerm basePaymentTermRecord = paymentTermRepository.getRecordById(basePaymentTermId);

		// see if the designed payment term already exists
		final I_C_PaymentTerm existingDerivedPaymentTermRecord = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_PaymentTerm.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_IsValid, true)
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_Discount, discount.toBigDecimal())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_AD_Client_ID, basePaymentTermRecord.getAD_Client_ID())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_AD_Org_ID, basePaymentTermRecord.getAD_Org_ID())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_Discount2, basePaymentTermRecord.getDiscount2())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_DiscountDays2, basePaymentTermRecord.getDiscountDays2())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_BaseLineType, basePaymentTermRecord.getBaseLineType())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_GraceDays, basePaymentTermRecord.getGraceDays())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_CalculationMethod, basePaymentTermRecord.getCalculationMethod())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_IsNextBusinessDay, basePaymentTermRecord.isNextBusinessDay())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_NetDay, basePaymentTermRecord.getNetDay())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_NetDays, basePaymentTermRecord.getNetDays())
				.orderBy().addColumn(I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID).endOrderBy()
				.create()
				.first();
		if (existingDerivedPaymentTermRecord != null)
		{
			return PaymentTermId.ofRepoId(existingDerivedPaymentTermRecord.getC_PaymentTerm_ID());
		}

		final I_C_PaymentTerm newPaymentTerm = newInstance(I_C_PaymentTerm.class);
		InterfaceWrapperHelper.copyValues(basePaymentTermRecord, newPaymentTerm);
		newPaymentTerm.setDiscount(discount.toBigDecimal());

		final String newName = basePaymentTermRecord.getName() + " (=>" + discount.toBigDecimal() + " %)";
		newPaymentTerm.setName(newName);
		saveRecord(newPaymentTerm);

		return PaymentTermId.ofRepoId(newPaymentTerm.getC_PaymentTerm_ID());
	}
}
