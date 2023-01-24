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
import de.metas.currency.CurrencyPrecision;
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
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.tax.api.ITaxBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_Tax;
import org.compiere.model.MTax;
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

	private final MoneyService moneyService;

	private final BPartnerCreditLimitRepository creditLimitRepo;

	private BPartnerStatsService(@NonNull final BPartnerCreditLimitRepository creditLimitRepo,
			@NonNull final MoneyService moneyService)
	{
		this.creditLimitRepo = creditLimitRepo;
		this.moneyService = moneyService;
	}

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

		if (!X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck.equals(bPartnerStats.getSoCreditStatus()))
		{
			return;
		}

		bPartnerStatsDAO.setSOCreditStatus(bPartnerStats, X_C_BPartner_Stats.SOCREDITSTATUS_CreditWatch);
	}

	public void updateBPartnerStatistics(BPartnerStats bpStats)
	{
		updateOpenItems(bpStats);
		updateActualLifeTimeValue(bpStats);
		updateSOCreditUsed(bpStats);
		updateDeliveryCreditUsed(bpStats);
		updateSOCreditStatus(bpStats);
		updateDeliveryCreditStatus(bpStats);
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

	private void updateDeliveryCreditUsed(@NonNull final BPartnerStats bpStats)
	{
		final I_C_BPartner_Stats stats = bPartnerStatsDAO.loadDataRecord(bpStats);

		final CurrencyId baseCurrencyId = moneyService.getBaseCurrencyId(ClientAndOrgId.ofClientAndOrg(stats.getAD_Client_ID(), stats.getAD_Org_ID()));
		Money deliveryCreditUsed = Money.zero(baseCurrencyId);

		final Iterator<I_M_ShippingPackage> shippingPackages = bPartnerStatsDAO.retrieveCompletedDeliveryInstructionLines(bpStats);

		while (shippingPackages.hasNext())
		{
			final I_M_ShippingPackage shippingPackage = shippingPackages.next();

			final Money creditUsedPerShippingPackageInBaseCurrency = computeCreditUsedPerShippingPackageInBaseCurrency(shippingPackage, baseCurrencyId);
			deliveryCreditUsed.add(creditUsedPerShippingPackageInBaseCurrency);
		}

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

			deliveryCreditUsed.subtract(creditGainedByPaymentInBaseCurrency);

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

	private Money computeCreditUsedPerShippingPackageInBaseCurrency(@NonNull final I_M_ShippingPackage shippingPackage,
			@NonNull final CurrencyId baseCurrencyId)
	{
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(shippingPackage.getC_OrderLine_ID());

		final Quantity actualLoadQty = Quantitys.create(shippingPackage.getActualLoadQty(), UomId.ofRepoId(shippingPackage.getC_UOM_ID()));

		final CurrencyId orderLineCurrencyId = CurrencyId.ofRepoId(orderLine.getC_Currency_ID());
		final Money qtyNetPriceFromOrderLine = Money.of(orderLineBL.computeQtyNetPriceFromOrderLine(orderLine, actualLoadQty),
														orderLineCurrencyId);

		final int taxId = orderLine.getC_Tax_ID();

		final Money taxAmtInfo;
		if (taxId <= 0)
		{
			taxAmtInfo = Money.zero(orderLineCurrencyId);
		}

		else
		{
			final boolean taxIncluded = orderLineBL.isTaxIncluded(orderLine);

			final CurrencyPrecision taxPrecision = orderLineBL.getTaxPrecision(orderLine);

			final I_C_Tax tax = MTax.get(Env.getCtx(), taxId);

			taxAmtInfo = Money.of(taxBL.calculateTax(tax, qtyNetPriceFromOrderLine.toBigDecimal(), taxIncluded, taxPrecision.toInt()), orderLineCurrencyId);
		}

		final CurrencyConversionContext currencyConversionContext = orderLineBL.extractCurrencyConversionContext(orderLine);

		return moneyService.convertMoneyToCurrency(taxAmtInfo.add(qtyNetPriceFromOrderLine), baseCurrencyId, currencyConversionContext);
	}

	private void updateSOCreditStatus(@NonNull final BPartnerStats bpStats)
	{
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
