package de.metas.invoicecandidate.modelvalidator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.bpartner.service.BPartnerCreditLimitRepository;
import org.adempiere.bpartner.service.BPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsBL;
import org.adempiere.bpartner.service.IBPartnerStatsBL.CalculateSOCreditStatusRequest;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_BPartner_Stats;
import org.compiere.model.X_C_Order;

import de.metas.adempiere.model.I_C_Order;
import de.metas.currency.ICurrencyBL;
import de.metas.document.IDocTypeDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import lombok.NonNull;

@Interceptor(I_C_Order.class)
public class C_Order
{
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE, ModelValidator.TIMING_AFTER_REACTIVATE, ModelValidator.TIMING_AFTER_CLOSE })
	public void invalidateInvoiceCandidates(final I_C_Order order)
	{
		final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
		invoiceCandidateHandlerBL.invalidateCandidatesFor(order);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_PREPARE})
	public void checkCreditLimit(@NonNull final I_C_Order order)
	{
		if (!isCheckCreditLimitNeeded(order))
		{
			return;
		}

		final IBPartnerStatsBL bpartnerStatsBL = Services.get(IBPartnerStatsBL.class);
		final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

		final I_C_BPartner partner = InterfaceWrapperHelper.load(order.getC_BPartner_ID(), I_C_BPartner.class);
		final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(partner);
		final BigDecimal crediUsed = stats.getSOCreditUsed();
		final String soCreditStatus = stats.getSOCreditStatus();
		final Timestamp dateOrdered = order.getDateOrdered();

		final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(order.getC_BPartner_ID(), dateOrdered);

		if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(soCreditStatus))
		{
			final String msg = "@BPartnerCreditStop@ - @SOCreditUsed@="
					+ crediUsed
					+ ", @SO_CreditLimit@=" + creditLimit;
			throw new AdempiereException(msg);
		}
		if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold.equals(soCreditStatus))
		{
			final String msg = "@BPartnerCreditHold@ - @SOCreditUsed@="
					+ crediUsed
					+ ", @SO_CreditLimit@=" + creditLimit;
			throw new AdempiereException(msg);
		}
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final BigDecimal grandTotal = Services.get(ICurrencyBL.class).convertBase(ctx,
				order.getGrandTotal(), order.getC_Currency_ID(), order.getDateOrdered(),
				order.getC_ConversionType_ID(), order.getAD_Client_ID(), order.getAD_Org_ID());

		final CalculateSOCreditStatusRequest request = CalculateSOCreditStatusRequest.builder()
				.stat(stats)
				.additionalAmt(grandTotal)
				.date(dateOrdered)
				.build();
		final String calculatedSOCreditStatus = bpartnerStatsBL.calculateSOCreditStatus(request);

		if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold.equals(calculatedSOCreditStatus))
		{
			final String msg = "@BPartnerOverOCreditHold@-@SO_CreditUsed@="
					+ crediUsed + ", @GrandTotal@=" + grandTotal
					+ ", @SO_CreditLimit@=" + creditLimit;
			throw new AdempiereException(msg);
		}
	}

	private boolean isCheckCreditLimitNeeded(@NonNull final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return false;
		}

		final I_C_DocType dt = Services.get(IDocTypeDAO.class).getById(order.getC_DocTypeTarget_ID());
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		if (MDocType.DOCSUBTYPE_POSOrder.equals(dt.getDocSubType())
				&& X_C_Order.PAYMENTRULE_Cash.equals(order.getPaymentRule())
				&& !sysConfigBL.getBooleanValue("CHECK_CREDIT_ON_CASH_POS_ORDER", true, order.getAD_Client_ID(), order.getAD_Org_ID()))
		{
			// ignore -- don't validate for Cash POS Orders depending on sysconfig parameter
			return false;
		}
		else if (MDocType.DOCSUBTYPE_PrepayOrder.equals(dt.getDocSubType())
				&& !sysConfigBL.getBooleanValue("CHECK_CREDIT_ON_PREPAY_ORDER", true, order.getAD_Client_ID(), order.getAD_Org_ID()))
		{
			// ignore -- don't validate Prepay Orders depending on sysconfig parameter
			return false;
		}

		return true;
	}
}
