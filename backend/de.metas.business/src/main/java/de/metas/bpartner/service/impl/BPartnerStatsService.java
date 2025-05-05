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
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.PaymentQuery;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_BPartner_Stats;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.Iterator;
import java.util.Locale;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Service
public class BPartnerStatsService
{
	private final IBPartnerStatsDAO bPartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IShipperTransportationBL shipperTransportationBL = Services.get(IShipperTransportationBL.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final BPartnerCreditLimitRepository creditLimitRepo;
	private final MoneyService moneyService;
	public static final String SYS_CONFIG_C_Order_EnforceSOCreditStatus = "de.metas.bpartner.service.impl.BPartnerStatsService.C_Order_EnforceSOCreditStatus";

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

		return X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(CreditStatus.toCodeOrNull(futureCreditStatus));
	}

	public BigDecimal getCreditWatchRatio(final BPartnerStats stats)
	{
		// bp group will be taken from the stats' bpartner
		final I_C_BPartner partner = partnerDAO.getById(stats.getBpartnerId());

		final I_C_BP_Group bpGroup = partner.getC_BP_Group();
		final BigDecimal creditWatchPercent = bpGroup.getCreditWatchPercent();

		if (creditWatchPercent.signum() == 0)
		{
			return new BigDecimal("0.90");
		}

		return creditWatchPercent.divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
	}

	public void resetSOCreditStatusFromBPGroup(@NonNull final I_C_BPartner bpartner)
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

		if (!CreditStatus.NoCreditCheck.equals(bPartnerStats.getDeliveryCreditStatus()))
		{
			return;
		}

		bPartnerStatsDAO.setSOCreditStatus(bPartnerStats, CreditStatus.CreditWatch);
		bPartnerStatsDAO.setDeliveryCreditStatus(bPartnerStats, CreditStatus.CreditWatch);
	}

	public void updateBPartnerStatistics(@NonNull final BPartnerStats bpStats)
	{
		updateOpenItems(bpStats);
		updateActualLifeTimeValue(bpStats);
		updateSOCreditUsed(bpStats);
		updateDeliveryCreditUsed(bpStats);
		updateSOCreditStatus(bpStats);
		updateDeliveryCreditStatus(bpStats);
		updateSOCreditLimitIndicator(bpStats);
		updateDeliveryCreditLimitIndicator(bpStats);
		updateSectionCode(bpStats);
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

	private void updateActualLifeTimeValue(@NonNull final BPartnerStats bpStats)
	{
		final BigDecimal actualLifeTimeValue = bPartnerStatsDAO.computeActualLifeTimeValue(bpStats.getBpartnerId());

		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);
		stats.setActualLifeTimeValue(actualLifeTimeValue);
		saveRecord(stats);
	}

	private void updateSOCreditUsed(@NonNull final BPartnerStats bpStats)
	{
		final BigDecimal SO_CreditUsed = bPartnerStatsDAO.retrieveSOCreditUsed(bpStats);
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);
		stats.setSO_CreditUsed(SO_CreditUsed);
		saveRecord(stats);
	}

	private void updateDeliveryCreditUsed(@NonNull final BPartnerStats bpStats)
	{
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);

		final CurrencyId baseCurrencyId = currencyBL.getBaseCurrencyId(ClientId.ofRepoId(stats.getAD_Client_ID()), OrgId.ofRepoId(stats.getAD_Org_ID()));
		Money deliveryCreditUsed = Money.zero(baseCurrencyId);

		final Money creditUsedPerShippingPackageInBaseCurrency = shipperTransportationBL.getCreditUsedByOutgoingDeliveryInstructionsInCurrency(bpStats.getBpartnerId(), baseCurrencyId);

		deliveryCreditUsed = deliveryCreditUsed.add(creditUsedPerShippingPackageInBaseCurrency);

		final Money creditGainedByPayments = getCreditGainedByPaymentsInCurrency(bpStats.getBpartnerId(), baseCurrencyId);

		deliveryCreditUsed = deliveryCreditUsed.subtract(creditGainedByPayments);

		stats.setDelivery_CreditUsed(deliveryCreditUsed.toBigDecimal());
		saveRecord(stats);
	}

	private void updateSOCreditStatus(@NonNull final BPartnerStats bpStats)
	{
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);

		final CreditStatus initialCreditStatus = bpStats.getSoCreditStatus();

		final CreditStatus creditStatusToSet = computeCreditStatus(bpStats, initialCreditStatus, bpStats.getSoCreditUsed());

		stats.setSOCreditStatus(CreditStatus.toCodeOrNull(creditStatusToSet));
		saveRecord(stats);
	}

	private void updateDeliveryCreditStatus(@NonNull final BPartnerStats bpStats)
	{
		final CreditStatus initialCreditStatus = bpStats.getDeliveryCreditStatus();

		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);

		final CreditStatus creditStatusToSet = computeCreditStatus(bpStats, initialCreditStatus, bpStats.getDeliveryCreditUsed());

		stats.setDelivery_CreditStatus(CreditStatus.toCodeOrNull(creditStatusToSet));
		saveRecord(stats);
	}

	private CreditStatus computeCreditStatus(@NonNull final BPartnerStats bpStats, final CreditStatus initialCreditStatus, final BigDecimal creditUsed)
	{
		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bpStats.getBpartnerId().getRepoId(), SystemTime.asDayTimestamp());

		// Nothing to do
		if (CreditStatus.NoCreditCheck.equals(initialCreditStatus)
				|| CreditStatus.CreditStop.equals(initialCreditStatus)
				|| BigDecimal.ZERO.compareTo(creditLimit) == 0)
		{
			return initialCreditStatus;
		}

		final String creditStatusToSet;
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

		return CreditStatus.ofCode(creditStatusToSet);
	}

	private void updateSOCreditLimitIndicator(@NonNull final BPartnerStats bstats)
	{
		// load the statistics
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bstats);
		final BigDecimal soCreditUsed = stats.getSO_CreditUsed();

		final String percentString = computeCreditLimitIndicator(soCreditUsed, stats.getC_BPartner_ID());

		stats.setCreditLimitIndicator(percentString);

		saveRecord(stats);
	}

	private void updateDeliveryCreditLimitIndicator(@NonNull final BPartnerStats bstats)
	{
		// load the statistics
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bstats);
		final BigDecimal deliveryCreditUsed = stats.getDelivery_CreditUsed();

		final String percentString = computeCreditLimitIndicator(deliveryCreditUsed, stats.getC_BPartner_ID());

		stats.setDeliveryCreditLimitIndicator(percentString);

		saveRecord(stats);
	}

	private String computeCreditLimitIndicator(@NonNull final BigDecimal creditUsed, final int bpartnerId)
	{
		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bpartnerId, SystemTime.asDayTimestamp());

		final BigDecimal percent = creditLimit.signum() == 0 ? BigDecimal.ZERO : creditUsed.divide(creditLimit, 2, RoundingMode.HALF_UP);
		final Locale locale = Locale.getDefault();
		final NumberFormat fmt = NumberFormat.getPercentInstance(locale);
		fmt.setMinimumFractionDigits(1);
		fmt.setMaximumFractionDigits(1);
		return fmt.format(percent);
	}

	private void updateSectionCode(@NonNull final BPartnerStats bpStats)
	{
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);

		final I_C_BPartner partner = partnerDAO.getById(bpStats.getBpartnerId());
		stats.setM_SectionCode_ID(partner.getM_SectionCode_ID());

		saveRecord(stats);
	}

	public void checkPaymentCreditLimit(@NonNull final PaymentId paymentId)
	{
		final I_C_Payment payment = paymentBL.getById(paymentId);

		final boolean isEnforceCreditStatus = isEnforceCreditStatus(ClientAndOrgId.ofClientAndOrg(payment.getAD_Client_ID(), payment.getAD_Org_ID()));
		if (!isEnforceCreditStatus)
		{
			// nothing to do
			return;
		}

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

	public Money getCreditGainedByPaymentsInCurrency(final BPartnerId bpartnerId, final CurrencyId currencyId)
	{
		final Iterator<PaymentId> paymentIdIterator = paymentBL.getPaymentIds(PaymentQuery.builder()
																					  .docStatus(DocStatus.Completed)
																					  .bpartnerId(bpartnerId)
																					  .direction(PaymentDirection.INBOUND)
																					  .build())
				.stream().iterator();

		Money creditGainesInPayments = Money.zero(currencyId);

		while (paymentIdIterator.hasNext())
		{
			final PaymentId paymentId = paymentIdIterator.next();
			final Money creditGainedByPaymentInCurrency = computeCreditGainedByPaymentInCurrency(paymentId, currencyId);

			creditGainesInPayments = creditGainesInPayments.add(creditGainedByPaymentInCurrency);
		}

		return creditGainesInPayments;
	}

	private Money computeCreditGainedByPaymentInCurrency(@NonNull final PaymentId paymentId, @NonNull final CurrencyId baseCurrencyId)
	{
		final I_C_Payment payment = paymentBL.getById(paymentId);

		final Money creditUsageGainedByPayment = Money.of(payment.getPayAmt(), CurrencyId.ofRepoId(payment.getC_Currency_ID()));

		final CurrencyConversionContext currencyConversionContext = paymentBL.extractCurrencyConversionContext(payment);

		return moneyService.convertMoneyToCurrency(creditUsageGainedByPayment, baseCurrencyId, currencyConversionContext);

	}

	public BigDecimal getDeliveryOpenBalance(@NonNull final BPartnerStats bPartnerStats, @NonNull final Instant date)
	{
		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bPartnerStats.getBpartnerId().getRepoId(), TimeUtil.asTimestamp(date));
		return creditLimit.subtract(bPartnerStats.getDeliveryCreditUsed());
	}

	public boolean isEnforceCreditStatus(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return sysConfigBL.getBooleanValue(SYS_CONFIG_C_Order_EnforceSOCreditStatus, true, clientAndOrgId);
	}

}
