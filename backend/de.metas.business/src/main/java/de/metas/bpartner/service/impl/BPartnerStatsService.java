/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.bpartner.service.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.ICurrencyBL;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.PaymentQuery;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.tax.api.ITaxBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_BPartner_Stats;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Service
public class BPartnerStatsService
{
	private final IBPartnerStatsDAO bPartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	private final IShipperTransportationBL shipperTransportationBL = Services.get(IShipperTransportationBL.class);
	private IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

	private final MoneyService moneyService;
	private final BPartnerCreditLimitRepository creditLimitRepo;

	private BPartnerStatsService(@NonNull final BPartnerCreditLimitRepository creditLimitRepo,
			@NonNull final MoneyService moneyService)
	{
		this.creditLimitRepo = creditLimitRepo;
		this.moneyService = moneyService;
	}

	public CreditStatus calculateProjectedSOCreditStatus(@NonNull final CalculateCreditStatusRequest request)
	{
		final BPartnerStats bpStats = request.getStat();
		return calculateProjectedCreditStatus(request, bpStats.getSoCreditStatus(), bpStats.getSoCreditUsed());

	}

	public CreditStatus calculateProjectedDeliveryCreditStatus(final CalculateCreditStatusRequest request)
	{
		final BPartnerStats bpStats = request.getStat();
		return calculateProjectedCreditStatus(request, bpStats.getDeliveryCreditStatus(), bpStats.getDeliveryCreditUsed());
	}

	private CreditStatus calculateProjectedCreditStatus(@NonNull final CalculateCreditStatusRequest request,
			final CreditStatus initialCreditStatus,
			final BigDecimal creditUsed)
	{
		final BPartnerStats bpStats = request.getStat();
		final BigDecimal additionalAmt = request.getAdditionalAmt();
		final Timestamp date = request.getDate();
		if (!request.isForceCheckCreditStatus() && additionalAmt.signum() == 0)
		{
			return initialCreditStatus;
		}

		// get credit limit from BPartner
		BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bpStats.getBpartnerId().getRepoId(), date);

		// Nothing to do
		if (CreditStatus.NoCreditCheck.equals(initialCreditStatus)
				|| (CreditStatus.CreditStop.equals(initialCreditStatus) && !request.isForceCheckCreditStatus())
				|| creditLimit.signum() == 0)
		{
			return initialCreditStatus;
		}

		// Above (reduced) Credit Limit
		creditLimit = creditLimit.subtract(additionalAmt);
		if (creditLimit.compareTo(creditUsed) < 0)
		{
			return CreditStatus.CreditHold;
		}

		// Above Watch Limit
		final BigDecimal watchAmt = creditLimit.multiply(getCreditWatchRatio(bpStats));
		if (watchAmt.compareTo(creditUsed) < 0)
		{
			return CreditStatus.CreditWatch;
		}

		// is OK
		return CreditStatus.CreditOK;
	}

	public boolean isCreditStopSales(@NonNull final BPartnerStats stat, @NonNull final BigDecimal grandTotal, @NonNull final Timestamp date)
	{
		final CalculateCreditStatusRequest request = CalculateCreditStatusRequest.builder()
				.stat(stat)
				.additionalAmt(grandTotal)
				.date(date)
				.build();

		final CreditStatus futureCreditStatus = calculateProjectedSOCreditStatus(request);

		if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(CreditStatus.toCodeOrNull(futureCreditStatus)))
		{
			return true;
		}

		return false;
	}

	public BigDecimal getCreditWatchRatio(final BPartnerStats stats)
	{
		// bp group will be taken from the stats' bpartner
		final I_C_BPartner partner = partnerDAO.getById(stats.getBpartnerId());

		final I_C_BP_Group bpGroup = partner.getC_BP_Group();
		final BigDecimal creditWatchPercent = bpGroup.getCreditWatchPercent();

		if (creditWatchPercent.signum() == 0)
		{
			return new BigDecimal(0.90);
		}

		return creditWatchPercent.divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP);
	}

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

		if (!CreditStatus.NoCreditCheck.equals(bPartnerStats.getSoCreditStatus()))
		{
			return;
		}

		bPartnerStatsDAO.setSOCreditStatus(bPartnerStats, CreditStatus.CreditWatch);
	}

	public void updateBPartnerStatistics(BPartnerStats bpStats)
	{
		updateOpenItems(bpStats);
		updateActualLifeTimeValue(bpStats);
		updateSOCreditUsed(bpStats);
		updateDeliveryCreditUsed(bpStats);
		updateSOCreditStatus(bpStats);
		updateDeliveryCreditStatus(bpStats);
		updateSOCreditLimitIndicator(bpStats);
		updateDeliveryCreditLimitIndicator(bpStats);
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

	private void updateDeliveryCreditUsed(@NonNull final BPartnerStats bpStats)
	{
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);

		final CurrencyId baseCurrencyId = moneyService.getBaseCurrencyId(ClientAndOrgId.ofClientAndOrg(stats.getAD_Client_ID(), stats.getAD_Org_ID()));
		Money deliveryCreditUsed = Money.zero(baseCurrencyId);

		final Money creditUsedPerShippingPackageInBaseCurrency = shipperTransportationBL.getCreditUsedByDeliveryInstructionsInCurrency(bpStats.getBpartnerId(), baseCurrencyId);

		deliveryCreditUsed = deliveryCreditUsed.add(creditUsedPerShippingPackageInBaseCurrency);

		final Iterator<PaymentId> paymentIdIterator = paymentBL.getPaymentIds(PaymentQuery.builder()
																					  .docStatus(DocStatus.Completed)
																					  .bpartnerId(bpStats.getBpartnerId())
																					  .direction(PaymentDirection.INBOUND)
																					  .build())
				.stream().iterator();
		while (paymentIdIterator.hasNext())
		{
			final PaymentId paymentId = paymentIdIterator.next();
			final Money creditGainedByPaymentInBaseCurrency = computeCreditGainedByPaymentInBaseCurrency(baseCurrencyId, paymentId);

			deliveryCreditUsed = deliveryCreditUsed.subtract(creditGainedByPaymentInBaseCurrency);

		}

		stats.setDelivery_CreditUsed(deliveryCreditUsed.toBigDecimal());
		saveRecord(stats);
	}

	private Money computeCreditGainedByPaymentInBaseCurrency(final CurrencyId baseCurrencyId, final PaymentId paymentId)
	{
		final I_C_Payment payment = paymentBL.getById(paymentId);

		final Money creditUsageGainedByPayment = Money.of(payment.getPayAmt(), CurrencyId.ofRepoId(payment.getC_Currency_ID()));

		final CurrencyConversionContext currencyConversionContext = paymentBL.extractCurrencyConversionContext(payment);

		return moneyService.convertMoneyToCurrency(creditUsageGainedByPayment, baseCurrencyId, currencyConversionContext);

	}

	private void updateSOCreditStatus(@NonNull final BPartnerStats bpStats)
	{
		// load the statistics
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);
		final BigDecimal creditUsed = stats.getSO_CreditUsed();

		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bpStats.getBpartnerId().getRepoId(), SystemTime.asDayTimestamp());

		final CreditStatus initialCreditStatus = bpStats.getSoCreditStatus();

		String creditStatusToSet;

		// Nothing to do
		if (CreditStatus.NoCreditCheck.equals(initialCreditStatus)
				|| CreditStatus.CreditStop.equals(initialCreditStatus)
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
			final BigDecimal watchAmt = creditLimit.multiply(getCreditWatchRatio(bpStats));

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
		saveRecord(stats);
	}

	private void updateDeliveryCreditStatus(@NonNull final BPartnerStats bpStats)
	{
		final CreditStatus initialCreditStatus = bpStats.getSoCreditStatus();

		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bpStats.getBpartnerId().getRepoId(), SystemTime.asDayTimestamp());

		// Nothing to do
		if (CreditStatus.NoCreditCheck.equals(initialCreditStatus)
				|| CreditStatus.CreditStop.equals(initialCreditStatus)
				|| BigDecimal.ZERO.compareTo(creditLimit) == 0)
		{
			return;
		}

		String creditStatusToSet;

		// load the statistics
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);
		final BigDecimal creditUsed = stats.getDelivery_CreditUsed();

		// Above Credit Limit
		if (creditLimit.compareTo(creditUsed) < 0)
		{
			creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold;
		}
		else
		{
			// Above Watch Limit
			final BigDecimal watchAmt = creditLimit.multiply(getCreditWatchRatio(bpStats));

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

		stats.setDelivery_CreditStatus(creditStatusToSet);
		saveRecord(stats);
	}

	private void updateSOCreditLimitIndicator(@NonNull final BPartnerStats bstats)
	{
		// load the statistics
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bstats);
		final BigDecimal creditUsed = stats.getSO_CreditUsed();

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


	private void updateDeliveryCreditLimitIndicator(@NonNull final BPartnerStats bstats)
	{
		// load the statistics
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bstats);
		final BigDecimal deliveryCreditUsed = stats.getDelivery_CreditUsed();

		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(stats.getC_BPartner_ID(), SystemTime.asDayTimestamp());

		final BigDecimal percent = creditLimit.signum() == 0 ? BigDecimal.ZERO : deliveryCreditUsed.divide(creditLimit, 2, BigDecimal.ROUND_HALF_UP);
		final Locale locale = Locale.getDefault();
		final NumberFormat fmt = NumberFormat.getPercentInstance(locale);
		fmt.setMinimumFractionDigits(1);
		fmt.setMaximumFractionDigits(1);
		final String percentSring = fmt.format(percent);

		stats.setDeliveryCreditLimitIndicator(percentSring);

		saveRecord(stats);
	}
	public void checkPaymentCreditLimit(@NonNull final PaymentId paymentId)
	{
		final I_C_Payment payment = paymentBL.getById(paymentId);

		if (payment.isReceipt())
		{
			return;
		}

		final BPartnerStats stats = bPartnerStatsDAO.getCreateBPartnerStats(payment.getC_BPartner_ID());
		final CreditStatus soCreditStatus = stats.getSoCreditStatus();
		if (CreditStatus.NoCreditCheck.equals(soCreditStatus))
		{
			return;
		}

		final BigDecimal soCreditUsed = stats.getSoCreditUsed();
		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(payment.getC_BPartner_ID(), payment.getDateTrx());

		if (isCreditStopSales(stats, payment.getPayAmt().negate() // because it's not receipt !
				, payment.getDateTrx()))
		{
			throw new AdempiereException("@BPartnerCreditStop@ - @SO_CreditUsed@="
												 + stats.getSoCreditUsed()
												 + ", @SO_CreditLimit@=" + creditLimit);
		}

		if (CreditStatus.CreditHold.equals(soCreditStatus))
		{
			throw new AdempiereException("@BPartnerCreditHold@ - @SO_CreditUsed@="
												 + soCreditUsed
												 + ", @SO_CreditLimit@=" + creditLimit);
		}
	}

	public BPartnerStats getCreateBPartnerStats(final BPartnerId bPartnerId)
	{
		return bPartnerStatsDAO.getCreateBPartnerStats(bPartnerId);
	}

}
