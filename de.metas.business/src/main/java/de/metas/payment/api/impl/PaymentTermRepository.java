package de.metas.payment.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.util.Env;

import de.metas.lang.Percent;
import de.metas.payment.api.IPaymentTermRepository;
import de.metas.payment.api.PaymentTermId;
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

public class PaymentTermRepository implements IPaymentTermRepository
{
	private I_C_PaymentTerm getById(final PaymentTermId paymentTermId)
	{
		return loadOutOfTrx(paymentTermId.getRepoId(), I_C_PaymentTerm.class);
	}

	@Override
	public Percent getPaymentTermDiscount(@Nullable final PaymentTermId paymentTermId)
	{
		if (paymentTermId == null)
		{
			return Percent.ZERO;
		}

		final I_C_PaymentTerm paymentTerm = getById(paymentTermId);
		if (paymentTerm == null)
		{
			return Percent.ZERO;
		}

		return Percent.of(paymentTerm.getDiscount());
	}

	// this method is implemented after a code block from MOrder.beforeSave()
	@Override
	public PaymentTermId getDefaultPaymentTermIdOrNull()
	{
		final int contextPaymentTerm = Env.getContextAsInt(Env.getCtx(), "#C_PaymentTerm_ID");
		if (contextPaymentTerm > 0)
		{
			return PaymentTermId.ofRepoId(contextPaymentTerm);
		}

		final int dbPaymentTermId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PaymentTerm.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_IsDefault, true)
				.addOnlyContextClient(Env.getCtx())
				.create()
				.firstId();
		if (dbPaymentTermId > 0)
		{
			return PaymentTermId.ofRepoId(dbPaymentTermId);
		}

		return null;
	}

	@Override
	public PaymentTermId getOrCreateDerivedPaymentTerm(
			@NonNull final PaymentTermId basePaymentTermId,
			@NonNull final Percent discount)
	{
		final I_C_PaymentTerm basePaymentTermRecord = getById(basePaymentTermId);

		// see if the designed payment term already exists
		final I_C_PaymentTerm existingDerivedPaymentTermRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PaymentTerm.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_IsValid, true)
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_Discount, discount.getValueAsBigDecimal())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_AD_Client_ID, basePaymentTermRecord.getAD_Client_ID())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_AD_Org_ID, basePaymentTermRecord.getAD_Org_ID())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_Discount2, basePaymentTermRecord.getDiscount2())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_DiscountDays2, basePaymentTermRecord.getDiscountDays2())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_AfterDelivery, basePaymentTermRecord.isAfterDelivery())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_FixMonthCutoff, basePaymentTermRecord.getFixMonthCutoff())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_FixMonthDay, basePaymentTermRecord.getFixMonthDay())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_FixMonthOffset, basePaymentTermRecord.getFixMonthOffset())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_GraceDays, basePaymentTermRecord.getGraceDays())
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_IsDueFixed, basePaymentTermRecord.isDueFixed())
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
		InterfaceWrapperHelper.copyValues(basePaymentTermId, newPaymentTerm);
		newPaymentTerm.setDiscount(discount.getValueAsBigDecimal());

		final String newName = basePaymentTermRecord.getName() + " ( =>" + discount.getValueAsBigDecimal() + ")";
		newPaymentTerm.setName(newName);
		saveRecord(newPaymentTerm);

		return PaymentTermId.ofRepoId(newPaymentTerm.getC_PaymentTerm_ID());
	}
}
