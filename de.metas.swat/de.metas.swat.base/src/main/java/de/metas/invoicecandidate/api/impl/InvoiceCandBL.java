/**
 *
 */
package de.metas.invoicecandidate.api.impl;

import static de.metas.util.Check.assumeGreaterThanZero;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.getValueOrNull;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.concurrent.AutoClosableThreadLocalBoolean;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.MNote;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.model.I_C_Order;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IStatefulWorkpackageProcessorFactory;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.IInOutBL;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceScheduleRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.InvoiceCandidateIds;
import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandInvalidUpdater;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueuer;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateListeners;
import de.metas.invoicecandidate.api.IInvoiceGenerator;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.async.spi.impl.InvoiceCandWorkpackageProcessor;
import de.metas.invoicecandidate.exceptions.InconsistentUpdateExeption;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Detail;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderDAO;
import de.metas.order.InvoiceRule;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.pricing.service.IPriceListBL;
import de.metas.process.PInstanceId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;

public class InvoiceCandBL implements IInvoiceCandBL
{
	private static final String MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_IS_TO_CLEAR = "InvoiceCandBL_Invoicing_Skipped_IsToClear";
	private static final String MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_IS_IN_DISPUTE = "InvoiceCandBL_Invoicing_Skipped_IsInDispute";
	private static final String MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_DATE_TO_INVOICE = "InvoiceCandBL_Invoicing_Skipped_DateToInvoice";
	private static final String MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_ERROR = "InvoiceCandBL_Invoicing_Skipped_Error";
	private static final String MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_PROCESSED = "InvoiceCandBL_Invoicing_Skipped_Processed";
	private static final String MSG_FixProblemDeleteWaitForRegeneration = "FixProblemDeleteWaitForRegeneration";
	private static final String MSG_INVOICE_CAND_BL_STATUS_INVOICE_SCHEDULE_MISSING_1P = "InvoiceCandBL_Status_InvoiceSchedule_Missing";
	private static final String MSG_INVOICE_CAND_BL_BELOW_INVOICE_MIN_AMT_5P = "InvoiceCandBL_Below_Invoice_Min_Amt";
	private static final String MSG_INVOICE_CAND_BL_STATUS_ORDER_NOT_CO_1P = "InvoiceCandBL_Status_Order_Not_CO";

	private static final String SYS_Config_C_Invoice_Candidate_Close_IsToClear = "C_Invoice_Candidate_Close_IsToClear";
	private static final String SYS_Config_C_Invoice_Candidate_Close_PartiallyInvoiced = "C_Invoice_Candidate_Close_PartiallyInvoiced";

	// task 08927
	/* package */static final ModelDynAttributeAccessor<org.compiere.model.I_C_Invoice, Boolean> DYNATTR_C_Invoice_Candidates_need_NO_ila_updating_on_Invoice_Complete = new ModelDynAttributeAccessor<>(Boolean.class);

	/** @task 08451 */
	private static final LocalDate DATE_TO_INVOICE_MAX_DATE = LocalDate.of(9999, Month.DECEMBER, 31); // NOTE: not using LocalDate.MAX because we want to convert it to Timestamp

	private final Logger logger = InvoiceCandidate_Constants.getLogger(InvoiceCandBL.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	@Override
	public IInvoiceCandInvalidUpdater updateInvalid()
	{
		return new InvoiceCandInvalidUpdater(this);
	}

	/**
	 * Sets the given IC's <code>DateToInvoice</code> value:
	 * <ul>
	 * <li>{@link X_C_Invoice_Candidate#INVOICERULE_NachLieferung} or {@link X_C_Invoice_Candidate#INVOICERULE_NachLieferungAuftrag}: <code>DeliveryDate</code> or {@link #DATE_TO_INVOICE_MAX_DATE} if
	 * there was no delivery yet
	 * <li>{@link X_C_Invoice_Candidate#INVOICERULE_KundenintervallNachLieferung}: basically the result of {@link #mkDateToInvoiceForInvoiceSchedule(I_C_InvoiceSchedule, Timestamp)} or or
	 * {@link #DATE_TO_INVOICE_MAX_DATE} if there was no delivery yet
	 * <li>{@link X_C_Invoice_Candidate#INVOICERULE_Sofort} : <code>DateOrdered</code>
	 * <li>else (which should not happen, unless a new invoice rule is introduced): <code>Created</code>
	 * </ul>
	 *
	 * @task 08542
	 */
	@Override
	public void set_DateToInvoice_DefaultImpl(final I_C_Invoice_Candidate ic)
	{
		final LocalDate dateToInvoice = computeDateToInvoice(ic);
		ic.setDateToInvoice(TimeUtil.asTimestamp(dateToInvoice));
	}

	private LocalDate computeDateToInvoice(final I_C_Invoice_Candidate ic)
	{
		final InvoiceRule invoiceRule = getInvoiceRule(ic);
		switch (invoiceRule)
		{
			case Immediate:
				return TimeUtil.asLocalDate(ic.getDateOrdered());

			case AfterDelivery:
				return computedateToInvoiceBasedOnDeliveryDate(ic);

			case AfterOrderDelivered:
				return computedateToInvoiceBasedOnDeliveryDate(ic);

			case CustomerScheduleAfterDelivery:
				if (ic.getC_InvoiceSchedule_ID() <= 0)        // that's a paddlin'
				{
					return DATE_TO_INVOICE_MAX_DATE;
				}
				else
				{

					final LocalDate deliveryDate = TimeUtil.asLocalDate(ic.getDeliveryDate()); // task 08451: when it comes to invoicing, the important date is not when it was ordered but when the delivery was made
					if (deliveryDate == null)
					{
						// task 08451: we have an invoice schedule, but no delivery yet. Set the date to the far future
						return DATE_TO_INVOICE_MAX_DATE;
					}
					else
					{
						final InvoiceScheduleRepository invoiceScheduleRepository = SpringContextHolder.instance.getBean(InvoiceScheduleRepository.class);
						final InvoiceSchedule invoiceSchedule = invoiceScheduleRepository.ofRecord(ic.getC_InvoiceSchedule());
						return invoiceSchedule.calculateNextDateToInvoice(deliveryDate);
					}
				}
			default:
				throw new AdempiereException("Unexpected invoicerule=" + invoiceRule);
		}
	}

	private LocalDate computedateToInvoiceBasedOnDeliveryDate(@NonNull final I_C_Invoice_Candidate ic)
	{
		// if there is no delivery yet, then we set the date to the far future
		final LocalDate deliveryDate = TimeUtil.asLocalDate(ic.getDeliveryDate());
		return deliveryDate != null ? deliveryDate : DATE_TO_INVOICE_MAX_DATE;
	}

	void setInvoiceScheduleAmtStatus(final Properties ctx, final I_C_Invoice_Candidate ic)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		//
		// services
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final InvoiceRule invoiceRule = getInvoiceRule(ic);
		if (!InvoiceRule.CustomerScheduleAfterDelivery.equals(invoiceRule))
		{
			// Note: the field is supposed not to be displayed on status=OK, so we don't need to localize this
			ic.setInvoiceScheduleAmtStatus("OK");
			return;
		}

		final I_C_BPartner partner = bpartnerDAO.getById(ic.getBill_BPartner_ID());

		final int invoiceSchedId = retrieveInvoiceScheduleId(ic);
		if (invoiceSchedId <= 0)
		{

			final String msg = msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_STATUS_INVOICE_SCHEDULE_MISSING_1P, new Object[] { partner.getName() });
			ic.setInvoiceScheduleAmtStatus(msg);
			return;
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(ic);
		final I_C_InvoiceSchedule invoiceSched = InterfaceWrapperHelper.create(ctx, invoiceSchedId, I_C_InvoiceSchedule.class, trxName);

		if (invoiceSched.isAmount())
		{
			final Timestamp dateToday = getToday();

			final BigDecimal actualAmt = invoiceCandDAO.retrieveInvoicableAmount(partner, dateToday);

			if (actualAmt.compareTo(invoiceSched.getAmt()) < 0)
			{
				final Currency targetCurrency = currencyConversionBL.getBaseCurrency(
						ClientId.ofRepoId(ic.getAD_Client_ID()),
						OrgId.ofRepoId(ic.getAD_Org_ID()));
				final ITranslatableString currSymbol = targetCurrency.getSymbol();

				final String msg = msgBL.getMsg(ctx,
						MSG_INVOICE_CAND_BL_BELOW_INVOICE_MIN_AMT_5P,
						new Object[] { dateToday, actualAmt, currSymbol, invoiceSched.getAmt(), currSymbol });
				ic.setInvoiceScheduleAmtStatus(msg);
				return;
			}
		}

		// Note: the field is supposed not to be displayed on status=OK, so we don't need to localize this
		ic.setInvoiceScheduleAmtStatus("OK");
	}

	/**
	 * Updates 'QtyToInvoice_OverrideFulfilled'. If <code>QtyToInvoice_Override</code> is empty, it does nothing besides also resetting QtyToInvoice_OverrideFulfilled.
	 * <p>
	 * If is turns out that the fulfillment (i.e. QtyInvoiced) is now sufficient, it resets both 'QtyToInvoice_Override' and 'QtyToInvoice_OverrideFulfilled'.
	 */
	void set_QtyToInvoiceOverrideFulfilled(
			final I_C_Invoice_Candidate ic,
			final BigDecimal oldQtyInvoiced,
			final BigDecimal factor)
	{
		final BigDecimal qtyInvoicedDiff = ic.getQtyInvoiced().multiply(factor).subtract(oldQtyInvoiced);

		final BigDecimal newQtyToInvoiceOverrideFulfilled = ic.getQtyToInvoice_OverrideFulfilled().multiply(factor).add(qtyInvoicedDiff);

		final BigDecimal qtyToInvoiceOverride = getQtyToInvoice_OverrideOrNull(ic);
		if (qtyToInvoiceOverride == null)
		{
			// basically this is just for good measure. The model interceptor C_Invoice_Candidate.resetQtyToInvoiceFulFilled() actually does the job.
			ic.setQtyToInvoice_OverrideFulfilled(null);
		}
		else
		{
			if (qtyToInvoiceOverride.multiply(factor).compareTo(newQtyToInvoiceOverrideFulfilled) <= 0)
			{

				ic.setQtyToInvoice_Override(null);
				ic.setQtyToInvoice_OverrideFulfilled(null);
			}
			else
			{
				ic.setQtyToInvoice_OverrideFulfilled(newQtyToInvoiceOverrideFulfilled.multiply(factor));
			}
		}
	}

	@Override
	public void set_QtyInvoiced_NetAmtInvoiced_Aggregation(final Properties ctx, final I_C_Invoice_Candidate ic)
	{
		Check.assume(ic.isManual(), ic + " has IsManual='Y'");
		set_QtyInvoiced_NetAmtInvoiced_Aggregation0(ctx, ic);
	}

	/**
	 * Updates the 'QtyInvoiced', 'NetAmtInvoiced', 'C_Invoice_Candidate_Agg_ID', 'LineAggregationKey' values of the given 'ic'.
	 * <p>
	 * <b>Also invokes {@link #updateProcessedFlag(I_C_Invoice_Candidate)}</b>
	 */
	void set_QtyInvoiced_NetAmtInvoiced_Aggregation0(final Properties ctx, @NonNull final I_C_Invoice_Candidate ic)
	{
		if (ic.isToClear())
		{
			logger.debug(ic + "is not directly invoiced. QtyInvoiced is handled by a specific module");
		}
		else
		{
			final IPair<StockQtyAndUOMQty, Money> qtyAndNetAmtInvoiced = sumupQtyInvoicedAndNetAmtInvoiced(ic);

			final StockQtyAndUOMQty qtysInvoiced = qtyAndNetAmtInvoiced.getLeft();
			final Quantity stockQty = qtysInvoiced.getStockQty();

			ic.setQtyInvoiced(stockQty.toBigDecimal());
			ic.setQtyInvoicedInUOM(qtysInvoiced.getUOMQtyOpt().orElse(stockQty).toBigDecimal());

			final Money netAmtInvoiced = qtyAndNetAmtInvoiced.getRight();
			ic.setNetAmtInvoiced(netAmtInvoiced.toBigDecimal());
		}

		updateProcessedFlag(ic); // #243: also update the processed flag if isToClear=Y. It might be the case that Processed_Override was set

		//
		// Update aggregation info
		Services.get(IAggregationBL.class)
				.getUpdateProcessor()
				.process(ic);
	}

	/**
	 * Sum up 'QtyInvoiced' and 'NetAmtInvoiced'.
	 * Note that QtyInvoiced is in the <code>M_Product.C_UOM</code>'s and <code>NetAmtInvoiced</code> in <code>C_Invoice_Candidate.Price_UOM</code>.
	 *
	 * @param ilas
	 * @return
	 */
	/* package */IPair<StockQtyAndUOMQty, Money> sumupQtyInvoicedAndNetAmtInvoiced(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);
		final List<I_C_Invoice_Line_Alloc> ilas = invoiceCandDB.retrieveIlaForIc(InvoiceCandidateIds.ofRecord(ic));

		final ProductId productId = ProductId.ofRepoId(ic.getM_Product_ID());

		final UomId icUomId = UomId.ofRepoId(ic.getC_UOM_ID());
		StockQtyAndUOMQty qtyInvoiced = StockQtyAndUOMQtys.createZero(productId, icUomId);
		final CurrencyId icCurrencyId = CurrencyId.ofRepoId(ic.getC_Currency_ID());

		Money netAmtInvoiced = Money.zero(icCurrencyId);

		for (final I_C_Invoice_Line_Alloc ila : ilas)
		{
			// we don't need to check the invoice's DocStatus. If the ila is there, we count it.
// @formatter:off
//			final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(ila.getC_InvoiceLine(), I_C_InvoiceLine.class);
//			final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
//
//			if (docActionBL.isStatusOneOf(invoiceLine.getC_Invoice(),
//					DocAction.STATUS_Completed,
//					DocAction.STATUS_Closed,
//					DocAction.STATUS_Reversed,
//					DocAction.STATUS_InProgress))                     // 06162 InProgress invoices shall also be processed
//			{
// @formatter:on

			final StockQtyAndUOMQty qtysInvoiced = StockQtyAndUOMQtys.create(
					ila.getQtyInvoiced(),
					productId,
					ila.getQtyInvoicedInUOM(),
					UomId.ofRepoIdOrNull(ila.getC_UOM_ID()));
			qtyInvoiced = qtyInvoiced.add(qtysInvoiced);

			//
			// 07202: We update the net amount invoice according to price UOM.
			// final BigDecimal priceActual = ic.getPriceActual();
			final ProductPrice priceActual = ProductPrice.builder()
					.money(Money.of(ic.getPriceActual(), icCurrencyId))
					.productId(productId)
					.uomId(icUomId)
					.build();

			final Quantity qtyInvoicedInUOM = extractQtyInvoiced(ila);

			final Money amountInvoiced = SpringContextHolder.instance.getBean(MoneyService.class)
					.multiply(qtyInvoicedInUOM, priceActual);

			if (amountInvoiced == null)
			{
				netAmtInvoiced = null;
			}

			if (netAmtInvoiced != null)
			{
				netAmtInvoiced = netAmtInvoiced.add(amountInvoiced);
			}
// @formatter:off
//			}
// @formatter:on
		}
		final IPair<StockQtyAndUOMQty, Money> qtyAndNetAmtInvoiced = ImmutablePair.of(qtyInvoiced, netAmtInvoiced);
		return qtyAndNetAmtInvoiced;
	}

	private static void setQtyInvoiced(
			@NonNull final I_C_Invoice_Line_Alloc ila,
			@NonNull final StockQtyAndUOMQty qtysInvoiced)
	{
		ila.setQtyInvoiced(qtysInvoiced.getStockQty().toBigDecimal());
		if (qtysInvoiced.isUOMQtySet())
		{
			final Quantity qty = qtysInvoiced.getUOMQtyNotNull();
			ila.setQtyInvoicedInUOM(qty.toBigDecimal());
			ila.setC_UOM_ID(qty.getUomId().getRepoId());
		}
	}

	private Quantity extractQtyInvoiced(@NonNull final I_C_Invoice_Line_Alloc ila)
	{
		final UomId uomId = UomId.ofRepoIdOrNull(ila.getC_UOM_ID());
		if (uomId != null)
		{
			final I_C_UOM uom = uomsRepo.getById(uomId);
			return Quantity.of(ila.getQtyInvoicedInUOM(), uom);
		}
		else
		{
			return null;
		}
	}

	/* package */void set_Discount(final Properties ctx, final I_C_Invoice_Candidate ic)
	{
		if (ic.getC_OrderLine_ID() > 0)
		{
			final org.compiere.model.I_C_OrderLine ol = ic.getC_OrderLine();

			if (ol.isProcessed())
			{
				ic.setDiscount(ol.getDiscount());
			}
			else
			{
				final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
				final IMsgBL msgBL = Services.get(IMsgBL.class);

				amendSchedulerResult(ic,
						msgBL.getMsg(ctx,
								MSG_INVOICE_CAND_BL_STATUS_ORDER_NOT_CO_1P,
								new Object[] {
										adReferenceDAO.retrieveListNameTrl(
												DocStatus.AD_REFERENCE_ID,
												ol.getC_Order_ID() > 0 ? ol.getC_Order().getDocStatus() : "<null>") // "<null>" shouldn't happen
								}));

				ic.setDiscount(ZERO);
			}
		}
	}

	/**
	 * If the ic's invoice rule is "CustomerScheduleAfterDelivery", this method tries to get the BPartner's invoice schedule id.
	 */
	private int retrieveInvoiceScheduleId(final I_C_Invoice_Candidate ic)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		final InvoiceRule invoiceRule = getInvoiceRule(ic);
		Check.assume(InvoiceRule.CustomerScheduleAfterDelivery.equals(invoiceRule),
				"Method is only called if invoice rule is " + InvoiceRule.CustomerScheduleAfterDelivery);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);

		final int C_InvoiceSchedule_ID = ic.getC_InvoiceSchedule_ID();
		if (C_InvoiceSchedule_ID <= 0)
		{
			logger.info("BPartner has no Schedule");

			final I_C_BPartner billBPartner = bpartnerDAO.getById(ic.getBill_BPartner_ID());
			final String msg = Services.get(IMsgBL.class).getMsg(ctx, InvoiceCandBL.MSG_INVOICE_CAND_BL_STATUS_INVOICE_SCHEDULE_MISSING_1P, new Object[] { billBPartner.getName() });
			amendSchedulerResult(ic, msg);
			ic.setInvoiceScheduleAmtStatus(msg);
			return -1;
		}

		return C_InvoiceSchedule_ID;
	}

	@Override
	public IInvoiceGenerator generateInvoices()
	{
		return new InvoiceCandBLCreateInvoices();
	}

	@Override
	public IInvoiceGenerateResult generateInvoicesFromSelection(
			final Properties ctx,
			final PInstanceId AD_PInstance_ID,
			final boolean ignoreInvoiceSchedule,
			final String trxName)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final Iterator<I_C_Invoice_Candidate> candidates = invoiceCandDAO.retrieveIcForSelection(ctx, AD_PInstance_ID, trxName);

		return generateInvoices()
				.setContext(ctx, trxName)
				.setIgnoreInvoiceSchedule(ignoreInvoiceSchedule)
				.generateInvoices(candidates);
	}

	@Override
	public void setNetAmtToInvoice(@NonNull final I_C_Invoice_Candidate ic)
	{
		// If invoice candidate has IsToClear=Y then we shall not invoice it.
		// Also we shall set the NetAmtToInvoice to ZERO, to not affect the checksum (when invoicing).
		if (ic.isToClear())
		{
			ic.setNetAmtToInvoice(ZERO);
			ic.setSplitAmt(ZERO);
			return;
		}

		// If invoice candidate would be skipped when enqueueing to be invoiced then set the NetAmtToInvoice=0 (Mark request)
		// Reason: if the IC would be skipped we want to have the NetAmtToInvoice=0 because we don't want to affect the overall total that is displayed on window bottom.
		final boolean ignoreInvoiceSchedule = true; // yes, we ignore the DateToInvoice when checking because that's relative to Today
		if (isSkipCandidateFromInvoicing(ic, ignoreInvoiceSchedule))
		{
			ic.setNetAmtToInvoice(ZERO);
			ic.setSplitAmt(ZERO);
			return;
		}

		final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);

		invoiceCandidateHandlerBL.setNetAmtToInvoice(ic);
		invoiceCandidateHandlerBL.setLineNetAmt(ic);
	}

	@Override
	public void setPriceActualNet(final I_C_Invoice_Candidate ic)
	{
		//
		// Services
		final ITaxBL taxBL = Services.get(ITaxBL.class);

		final I_C_Tax tax = getTaxEffective(ic);
		final ProductPrice priceActual = getPriceActual(ic);
		final boolean taxIncluded = isTaxIncluded(ic);
		final CurrencyPrecision precision = getPrecisionFromCurrency(ic);

		final BigDecimal priceActualNet = taxBL.calculateBaseAmt(
				tax,
				priceActual.toMoney().toBigDecimal(),
				taxIncluded,
				precision.toInt());
		ic.setPriceActual_Net_Effective(priceActualNet);
	}

	@Override
	public ProductPrice getPriceActual(@NonNull final I_C_Invoice_Candidate ic)
	{
		final BigDecimal priceActual = InterfaceWrapperHelper.getValueOverrideOrValue(ic, I_C_Invoice_Candidate.COLUMNNAME_PriceActual);

		return ProductPrice.builder()
				.money(Money.of(priceActual, CurrencyId.ofRepoId(ic.getC_Currency_ID())))
				.uomId(UomId.ofRepoId(ic.getC_UOM_ID()))
				.productId(ProductId.ofRepoId(ic.getM_Product_ID()))
				.build();
	}

	private BigDecimal getQtyToInvoice_OverrideOrNull(@NonNull final I_C_Invoice_Candidate ic)
	{
		if (InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override))
		{
			return null;
		}
		else
		{
			return ic.getQtyToInvoice_Override();
		}
	}

	@Override
	public Percent getDiscount(final I_C_Invoice_Candidate ic)
	{
		final BigDecimal discountOverride;
		if (InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_Discount_Override))
		{
			discountOverride = null;
		}
		else
		{
			discountOverride = ic.getDiscount_Override();
		}

		if (discountOverride == null)
		{
			return Percent.of(ic.getDiscount());
		}

		return Percent.of(discountOverride);
	}

	@Override
	public Quantity getQtyToInvoiceStockUOM(@NonNull final I_C_Invoice_Candidate ic)
	{
		final UomId stockUomId = Services.get(IProductBL.class).getStockUOMId(ProductId.ofRepoId(ic.getM_Product_ID()));

		final BigDecimal qtyToInvoiceOverride = getQtyToInvoice_OverrideOrNull(ic);
		if (qtyToInvoiceOverride != null)
		{
			return Quantitys.create(qtyToInvoiceOverride, stockUomId);
		}

		return Quantitys.create(ic.getQtyToInvoice(), stockUomId);
	}

	/**
	 * Gets Qty Ordered (target), considering the QtyDelivered too.
	 */
	private BigDecimal getQtyOrderedInStockUOM(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_UOM uomRecord = Services.get(IProductBL.class).getStockUOM(ic.getM_Product_ID());

		final Quantity qtyOrdered = Quantity.of(ic.getQtyOrdered(), uomRecord);
		final Quantity qtyDelivered = Quantity.of(getQtyDelivered_Effective(ic), uomRecord);

		final Quantity qtyOrderedActual = calculateMaxInvoicableQtyFromEnteredAndDelivered(qtyOrdered, qtyDelivered);

		return qtyOrderedActual.toBigDecimal();
	}

	/**
	 * Adds the given <code>amendment</code> to the given <code>ic</code>'s <code>SchedulerResult</code> value, <b>unless</b> the given string is already part of the <code>SchedulerResult</code>.
	 * <p>
	 * Note: the given <code>ic</code> might already contain the string because one method might be called multiple times and always try to add the same error-message.
	 *
	 * @param ic
	 * @param amendment
	 */
	private void amendSchedulerResult(final I_C_Invoice_Candidate ic, final String amendment)
	{
		if (Check.isEmpty(amendment))
		{
			logger.debug("there is nothing to amend");
			return;
		}

		final String currentVal = ic.getSchedulerResult();
		if (Check.isEmpty(currentVal))
		{
			ic.setSchedulerResult(amendment);
		}
		else if (!currentVal.contains(amendment))   // this IC might already contain the given amendment
		{
			ic.setSchedulerResult(currentVal + "\n" + amendment);
		}
	}

	@Override
	public void invalidateForInvoiceSchedule(final I_C_InvoiceSchedule invoiceSchedule)
	{
		final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);
		final List<I_C_Invoice_Candidate> candsForBPartner = invoiceCandDB.retrieveForInvoiceSchedule(invoiceSchedule);

		for (final I_C_Invoice_Candidate ic : candsForBPartner)
		{
			invoiceCandDB.invalidateCand(ic);
		}
	}

	@Override
	public void invalidateForPartnerIfInvoiceRuleDemandsIt(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final InvoiceRule invoiceRule = getInvoiceRule(ic);
		if (InvoiceRule.CustomerScheduleAfterDelivery.equals(invoiceRule)
				&& ic.getC_InvoiceSchedule_ID() > 0)
		{
			final I_C_InvoiceSchedule invoiceSchedule = ic.getC_InvoiceSchedule();
			if (invoiceSchedule.getAmt() != null && invoiceSchedule.getAmt().signum() != 0)
			{
				// we need to invalidate all the partner's ICs *if* there is an overall invoiceable sum-amount from which on the ICs shall be invoiced
				final BPartnerId bpartnerId = BPartnerId.ofRepoId(ic.getBill_BPartner_ID());
				invoiceCandDAO.invalidateCandsForBPartnerInvoiceRule(bpartnerId);
			}
		}
	}

	@Override
	public IInvoiceCandidateEnqueuer enqueueForInvoicing()
	{
		return new InvoiceCandidateEnqueuer();
	}

	@Override
	public boolean isSkipCandidateFromInvoicing(final I_C_Invoice_Candidate ic, final boolean ignoreInvoiceSchedule)
	{
		// 04533: ignore already processed candidates
		// task 08343: if the ic is processed (after the recent update), then skip it (this logic was in the where clause in C_Invoice_Candidate_EnqueueSelection)
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
		if (ic.isProcessed())
		{
			final String msg = msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_PROCESSED, new Object[] { ic.getC_Invoice_Candidate_ID() });
			Loggables.withLogger(logger, Level.INFO).addLog(msg);
			return true;
		}

		// ignore "error" candidates
		if (ic.isError())
		{
			final String msg = new StringBuilder()
					.append(msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_ERROR, new Object[] { ic.getC_Invoice_Candidate_ID() }))
					.append(": ")
					.append(ic.getErrorMsg())
					.toString();
			Loggables.withLogger(logger, Level.DEBUG).addLog(msg);
			return true;
		}

		if (ic.isToClear())
		{
			// don't log (per Mark request) because those could be a lot and because user has no opportunity to react
			final String msg = msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_IS_TO_CLEAR,
					new Object[] { ic.getC_Invoice_Candidate_ID() });
			Loggables.withLogger(logger, Level.DEBUG).addLog(msg);
			return true;
		}

		if (ic.isInDispute())
		{
			// don't log (per Mark request) because those could be a lot and because user has no opportunity to react
			final String msg = msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_IS_IN_DISPUTE,
					new Object[] { ic.getC_Invoice_Candidate_ID() });
			Loggables.withLogger(logger, Level.DEBUG).addLog(msg);
			return true;
		}

		// flagged via field color
		// ignore candidates that can't be invoiced yet
		final Timestamp dateToInvoice = getDateToInvoice(ic);
		if (!ignoreInvoiceSchedule
				&& (dateToInvoice == null || dateToInvoice.after(getToday())))
		{
			final String msg = msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_DATE_TO_INVOICE,
					new Object[] { ic.getC_Invoice_Candidate_ID(), dateToInvoice, getToday() });
			Loggables.withLogger(logger, Level.DEBUG).addLog(msg);
			return true;
		}

		return false; // Don't skip!
	}

	@Override
	public IInvoiceGenerateResult generateInvoicesFromQueue(final Properties ctx)
	{
		// note that we don't want to store the actual invoices in the result to omit memory-problems
		final InvoiceGenerateResult result = new InvoiceGenerateResult(false);
		final IWorkpackageProcessor packageProcessor = new InvoiceCandWorkpackageProcessor(result);

		// We use a stateful package processor factory because we don't want to create a new instance of InvoiceCandWorkpackageProcessor for each work package
		// This is because we want to preserve InvoiceGenerateResult state
		final IStatefulWorkpackageProcessorFactory packageProcessorFactory = Services.get(IStatefulWorkpackageProcessorFactory.class);
		packageProcessorFactory.registerWorkpackageProcessor(packageProcessor);

		final IWorkPackageQueue packageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, packageProcessor.getClass());

		final IQueueProcessor queueProcessor = Services.get(IQueueProcessorFactory.class).createSynchronousQueueProcessor(packageQueue);
		queueProcessor.setWorkpackageProcessorFactory(packageProcessorFactory);
		queueProcessor.run();

		return result;
	}

	@Override
	public boolean isCreditMemo(final I_C_Invoice_Candidate cand)
	{
		return cand.isManual() && cand.getPriceActual_Override().signum() < 0;
	}

	@Override
	public Money calculateNetAmt(final I_C_Invoice_Candidate ic)
	{
		final ProductPrice candPriceActual = getPriceActual(ic);

		final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

		final Money candNetAmtToInvoiceCalc = moneyService.multiply(Quantitys.create(ic.getQtyToInvoiceInUOM(), UomId.ofRepoId(ic.getC_UOM_ID())), candPriceActual);

		return candNetAmtToInvoiceCalc;
	}

	@Override
	public CurrencyPrecision getPrecisionFromCurrency(@NonNull final I_C_Invoice_Candidate ic)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(ic.getC_Currency_ID());
		return currencyId != null
				? Services.get(ICurrencyDAO.class).getStdPrecision(currencyId)
				: CurrencyPrecision.TWO;
	}

	@Override
	public CurrencyPrecision getPrecisionFromPricelist(final I_C_Invoice_Candidate ic)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		// take the precision from the bpartner price list
		final I_C_BPartner_Location partnerLocation = bpartnerDAO.getBPartnerLocationById(
				BPartnerLocationId.ofRepoIdOrNull(ic.getBill_BPartner_ID(), ic.getBill_Location_ID()));

		if (partnerLocation != null)
		{
			final LocalDate date = TimeUtil.asLocalDate(ic.getDateOrdered());
			final SOTrx soTrx = SOTrx.ofBoolean(ic.isSOTrx());

			final I_M_PriceList pricelist = Services.get(IPriceListBL.class)
					.getCurrentPricelistOrNull(
							PricingSystemId.ofRepoIdOrNull(ic.getM_PricingSystem_ID()),
							CountryId.ofRepoId(partnerLocation.getC_Location().getC_Country_ID()),
							date,
							soTrx);

			if (pricelist != null)
			{
				return CurrencyPrecision.ofInt(pricelist.getPricePrecision());
			}
		}

		// fall back: get the precision from the currency
		return getPrecisionFromCurrency(ic);
	}

	@Override
	public I_C_Invoice_Candidate splitCandidate(@NonNull final I_C_Invoice_Candidate ic)
	{
		// services
		final IAggregationBL aggregationBL = Services.get(IAggregationBL.class);

		final BigDecimal splitAmt = ic.getSplitAmt();
		// splitAmt may be zero, if we are going to compute&set priceactual etc later.
		// Check.assume(splitAmt.signum() != 0, "Split amount shall not be zero: {}", ic);

		final I_C_Invoice_Candidate splitCand = newInstance(I_C_Invoice_Candidate.class, ic);

		Check.assume(splitCand.getAD_Client_ID() == ic.getAD_Client_ID(), "Same AD_Client_ID (split's AD_Client_ID={}, IC's AD_Client_ID={}", splitCand.getAD_Client_ID(), ic.getAD_Client_ID());
		splitCand.setAD_Org_ID(ic.getAD_Org_ID());

		splitCand.setAD_Table_ID(ic.getAD_Table_ID());
		splitCand.setRecord_ID(ic.getRecord_ID()); // even if 0, we can't leave it empty, as the column is mandatory

		splitCand.setIsActive(true);
		splitCand.setProcessed(false);
		resetError(ic); // make sure there is no error set

		splitCand.setIsManual(ic.isManual());
		splitCand.setIsSOTrx(ic.isSOTrx());
		splitCand.setC_ILCandHandler(ic.getC_ILCandHandler());

		splitCand.setBill_BPartner_ID(ic.getBill_BPartner_ID());
		splitCand.setBill_Location_ID(ic.getBill_Location_ID());
		splitCand.setBill_User_ID(ic.getBill_User_ID());

		splitCand.setC_Invoice_Candidate_Agg_ID(ic.getC_Invoice_Candidate_Agg_ID());
		aggregationBL.setHeaderAggregationKey(splitCand);
		splitCand.setLineAggregationKey(null);
		splitCand.setLineAggregationKey_Suffix(ic.getLineAggregationKey_Suffix());
		splitCand.setDescription(ic.getDescription());

		splitCand.setInvoiceRule(ic.getInvoiceRule());
		splitCand.setInvoiceRule_Override(ic.getInvoiceRule_Override());

		splitCand.setM_PricingSystem_ID(ic.getM_PricingSystem_ID());
		splitCand.setM_Product_ID(ic.getM_Product_ID());
		splitCand.setIsPackagingMaterial(ic.isPackagingMaterial());
		splitCand.setC_Charge_ID(ic.getC_Charge_ID());

		splitCand.setInvoicableQtyBasedOn(ic.getInvoicableQtyBasedOn());

		splitCand.setQtyOrdered(ONE);
		splitCand.setQtyEntered(ONE); // "QtyOrderedInUOM"
		splitCand.setQtyDelivered(ZERO);
		splitCand.setQtyDeliveredInUOM(ZERO);
		splitCand.setQtyToInvoice(ONE);
		splitCand.setQtyToInvoiceInUOM_Calc(ONE);
		splitCand.setQtyToInvoice_Override(ONE);

		splitCand.setQtyInvoiced(ZERO);
		splitCand.setQtyInvoicedInUOM(ZERO);

		splitCand.setC_Currency_ID(ic.getC_Currency_ID());
		splitCand.setC_ConversionType_ID(ic.getC_ConversionType_ID());
		splitCand.setPriceActual(splitAmt);
		splitCand.setIsTaxIncluded(false);
		splitCand.setPrice_UOM_ID(ic.getPrice_UOM_ID()); // 07090 when we set PiceActual, we shall also set PriceUOM.
		splitCand.setPriceEntered(splitAmt); // cg : task 04917 -- same as price actual
		splitCand.setDiscount(ZERO);
		splitCand.setSplitAmt(ZERO);

		splitCand.setPresetDateInvoiced(ic.getPresetDateInvoiced());
		splitCand.setDateInvoiced(ic.getDateInvoiced());
		splitCand.setDateOrdered(ic.getDateOrdered());
		splitCand.setDateToInvoice(ic.getDateToInvoice());
		splitCand.setDateToInvoice_Override(ic.getDateToInvoice_Override());

		// 07442
		// also set activity and tax
		splitCand.setC_Activity_ID(ic.getC_Activity_ID());

		// 07814: setting both tax and tax-override to get an exact copy
		splitCand.setC_Tax_ID(ic.getC_Tax_ID());
		splitCand.setC_Tax_Override_ID(ic.getC_Tax_Override_ID());

		splitCand.setExternalId(ic.getExternalId());

		return splitCand;
	}

	@Override
	public InvoiceRule getInvoiceRule(@NonNull final I_C_Invoice_Candidate ic)
	{
		final InvoiceRule invoiceRuleOverride = InvoiceRule.ofNullableCode(ic.getInvoiceRule_Override());
		return invoiceRuleOverride != null
				? invoiceRuleOverride
				: InvoiceRule.ofCode(ic.getInvoiceRule());
	}

	@Override
	public Timestamp getDateToInvoice(final I_C_Invoice_Candidate ic)
	{
		final Timestamp dateToInvoiceOverride = ic.getDateToInvoice_Override();
		if (dateToInvoiceOverride != null)
		{
			return dateToInvoiceOverride;
		}

		return ic.getDateToInvoice();
	}

	@Override
	public IInvoiceGenerateResult createInvoiceGenerateResult(final boolean shallStoreInvoices)
	{
		return new InvoiceGenerateResult(shallStoreInvoices);
	}

	private final AutoClosableThreadLocalBoolean updateProcessInProgress = AutoClosableThreadLocalBoolean.newInstance();

	@Override
	public boolean isUpdateProcessInProgress()
	{
		return updateProcessInProgress.booleanValue();
	}

	@Override
	public IAutoCloseable setUpdateProcessInProgress()
	{
		return updateProcessInProgress.enable();
	}

	@Override
	public I_C_Invoice_Line_Alloc createUpdateIla(
			final I_C_Invoice_Candidate invoiceCand,
			final I_C_InvoiceLine invoiceLine,
			final StockQtyAndUOMQty qtysInvoiced,
			final String note)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceCand);
		Check.assume(Env.getAD_Client_ID(ctx) == invoiceCand.getAD_Client_ID(), "AD_Client_ID of " + invoiceCand + " and of its CTX are the same");

		final I_C_Invoice_Line_Alloc existingIla = Services.get(IInvoiceCandDAO.class).retrieveIlaForIcAndIl(invoiceCand, invoiceLine);

		if (existingIla != null)
		{
			translateAndPrependNote(existingIla, note);

			//
			// FIXME in follow-up task! (06162)
			if (qtysInvoiced.signum() == 0)
			{
				return existingIla;
			}

			// 05475: If exists, update the qtyInvoiced.
			setQtyInvoiced(existingIla, qtysInvoiced);
			InterfaceWrapperHelper.save(existingIla);
			return existingIla;
		}

		//
		// Create new invoice line allocation
		final Object contextProvider = invoiceCand;
		final I_C_Invoice_Line_Alloc newIla = InterfaceWrapperHelper.newInstance(I_C_Invoice_Line_Alloc.class, contextProvider);
		newIla.setAD_Org_ID(invoiceCand.getAD_Org_ID());
		newIla.setC_Invoice_Candidate(invoiceCand);
		newIla.setC_InvoiceLine(invoiceLine);
		setQtyInvoiced(newIla, qtysInvoiced);
		newIla.setC_Invoice_Candidate_Agg_ID(invoiceCand.getC_Invoice_Candidate_Agg_ID());

		// #870
		// Set Qty and Price Override into the invoice line alloc:
		// Make sure the numbers are correctly taken from the database, and null is not replaced by 0
		newIla.setQtyToInvoice_Override(InterfaceWrapperHelper.getValueOrNull(invoiceCand, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override));
		newIla.setPriceEntered_Override(InterfaceWrapperHelper.getValueOrNull(invoiceCand, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override));

		translateAndPrependNote(newIla, note);

		InterfaceWrapperHelper.save(newIla); // model validator C_Invoice_Line_Alloc will invalidate 'invoiceCand'

		//
		// Assign Invoice Candidate's Details to Invoice Line
		assignInvoiceDetailsToInvoiceLine(newIla);
		return newIla;
	}

	private void translateAndPrependNote(final I_C_Invoice_Line_Alloc ila, final String note)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ila);

		if (!Check.isEmpty(note, true))
		{
			final String translatedNote = Services.get(IMsgBL.class).parseTranslation(ctx, note);
			ila.setNote(translatedNote + "; " + ila.getNote());
		}
	}

	/**
	 * Updates all {@link I_C_Invoice_Detail} records linked to {@link I_C_Invoice_Candidate} and sets {@link I_C_Invoice} and {@link I_C_InvoiceLine}.
	 *
	 * @param ila
	 */
	private void assignInvoiceDetailsToInvoiceLine(final I_C_Invoice_Line_Alloc ila)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final org.compiere.model.I_C_InvoiceLine invoiceLine = ila.getC_InvoiceLine();
		final I_C_Invoice_Candidate invoiceCand = ila.getC_Invoice_Candidate();

		final ICompositeQueryUpdater<I_C_Invoice_Detail> queryUpdater = queryBL.createCompositeQueryUpdater(I_C_Invoice_Detail.class)
				.addSetColumnValue(org.adempiere.model.I_C_Invoice_Detail.COLUMNNAME_C_InvoiceLine_ID, invoiceLine.getC_InvoiceLine_ID())
				.addSetColumnValue(org.adempiere.model.I_C_Invoice_Detail.COLUMNNAME_C_Invoice_ID, invoiceLine.getC_Invoice_ID());
		queryBL
				.createQueryBuilder(I_C_Invoice_Detail.class, ila)
				.addEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCand.getC_Invoice_Candidate_ID())
				.create()
				.updateDirectly(queryUpdater);
	}

	// 0917
	@Override
	public void setPriceActual_Override(final I_C_Invoice_Candidate ic)
	{
		// task 08338: check for null, not for 0, because they are different (0 means zero, null means "not set")
		if (InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override)
				&& InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_Discount_Override))
		{
			ic.setPriceActual_Override(null);
		}
		else
		{
			final CurrencyPrecision precision = getPrecisionFromPricelist(ic);
			final ProductPrice priceEntered = getPriceEntered(ic);
			final Percent discount = getDiscount(ic);

			final BigDecimal priceActualOverride = discount
					.subtractFromBase(
							priceEntered.toMoney().toBigDecimal(),
							precision.toInt());
			ic.setPriceActual_Override(priceActualOverride);
		}
	}

	@Override
	public ProductPrice getPriceEntered(@NonNull final I_C_Invoice_Candidate ic)
	{
		final BigDecimal priceAmount = InterfaceWrapperHelper.getValueOverrideOrValue(ic, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered);

		return ProductPrice.builder()
				.money(Money.of(priceAmount, CurrencyId.ofRepoId(ic.getC_Currency_ID())))
				.uomId(UomId.ofRepoId(ic.getC_UOM_ID()))
				.productId(ProductId.ofRepoId(ic.getM_Product_ID()))
				.build();
	}

	@Override
	public boolean isTaxIncluded(final I_C_Invoice_Candidate ic)
	{
		final Boolean taxIncludedOverride;
		if (InterfaceWrapperHelper.isNullOrEmpty(ic, I_C_Invoice_Candidate.COLUMNNAME_IsTaxIncluded_Override))                 // note: currently, "not set" translates to the empty string, not to null
		{
			taxIncludedOverride = null;
		}
		else
		{
			taxIncludedOverride = X_C_Invoice_Candidate.ISTAXINCLUDED_OVERRIDE_Yes.equals(ic.getIsTaxIncluded_Override());
		}

		if (taxIncludedOverride == null)
		{
			return ic.isTaxIncluded();
		}

		return taxIncludedOverride.booleanValue();
	}

	@Override
	public void handleReversalForInvoice(final org.compiere.model.I_C_Invoice invoice)
	{
		final int reversalInvoiceId = invoice.getReversal_ID();
		Check.assume(reversalInvoiceId > invoice.getC_Invoice_ID(), "Invoice {} shall be the original invoice and not it's reversal", invoice);

		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final I_C_Invoice invoiceExt = InterfaceWrapperHelper.create(invoice, I_C_Invoice.class);

		final boolean creditMemo = Services.get(IInvoiceBL.class).isCreditMemo(invoice);
		final boolean creditedInvoiceReinvoicable = invoiceExt.isCreditedInvoiceReinvoicable(); // task 08927: this is only relevant if isCreditMemo, see below
		final boolean creditedInvoiceIsReversed;

		final Iterator<I_C_Invoice> creditMemosForInvoice = invoiceDAO.retrieveCreditMemosForInvoice(invoiceExt);
		if (creditMemo && creditMemosForInvoice.hasNext())
		{
			final org.compiere.model.I_C_Invoice originalInvoice = creditMemosForInvoice.next();
			final DocStatus originalInvoiceDocStatus = DocStatus.ofCode(originalInvoice.getDocStatus());
			creditedInvoiceIsReversed = originalInvoiceDocStatus.isReversed();
		}
		else
		{
			creditedInvoiceIsReversed = false;
		}
		// if we deal with a credit memo, we need to thread the qtys as negative
		final BigDecimal factor = creditMemo ? ONE.negate() : ONE;

		for (final I_C_InvoiceLine il : invoiceDAO.retrieveLines(invoice))
		{
			//
			// generate reversal C_Invoice_Line_Alloc records (the reversal MatchInv records are created by InvoiceBL)
			final I_C_InvoiceLine reversalLine = invoiceDAO.retrieveReversalLine(il, reversalInvoiceId);
			Check.assumeNotNull(reversalLine, "C_InvoiceLine {} is expected to have a reversal line, like every other line of C_Invoice {}", il, invoice);

			for (final I_C_Invoice_Line_Alloc ilaToReverse : invoiceCandDAO.retrieveIlaForIl(il))
			{
				final StockQtyAndUOMQty qtyInvoicedForIla;
				final String note;

				final I_C_Invoice_Candidate invoiceCandidate = ilaToReverse.getC_Invoice_Candidate();
				invoiceCandidate.setProcessed_Override(null); // reset processed_override, because now that the invoice was reversed, the users might want to do something new with the IC.

				// #870
				// Make sure that, when an invoice is reversed, the QtyToInvoice_Override and PriceEntered_Override are set back in the invoice candidate based on the values in the allocations
				{
					final int invoiceCandidateId = invoiceCandidate.getC_Invoice_Candidate_ID();

					// Make sure the numbers are correctly taken from the database, and null is not replaced by 0
					final BigDecimal qtyToInvoice_Override = InterfaceWrapperHelper.getValueOrNull(ilaToReverse, I_C_Invoice_Line_Alloc.COLUMNNAME_QtyToInvoice_Override);
					final BigDecimal priceEntered_Override = InterfaceWrapperHelper.getValueOrNull(ilaToReverse, I_C_Invoice_Line_Alloc.COLUMNNAME_PriceEntered_Override);

					Services.get(ITrxManager.class)
							.getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited)
							.newEventListener(TrxEventTiming.AFTER_COMMIT)
							.registerWeakly(false) // register "hard", because that's how it was before
							.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
							.registerHandlingMethod(transaction -> setQtyAndPriceOverride(invoiceCandidateId, qtyToInvoice_Override, priceEntered_Override));
				}

				final ProductId productId = ProductId.ofRepoId(il.getM_Product_ID());
				final UomId uomId = UomId.ofRepoId(il.getC_UOM_ID());

				if (creditMemo && creditedInvoiceReinvoicable && !creditedInvoiceIsReversed)
				{
					// undo/reverse the full credit memo quantity. Note that when we handled the credit memo's completion we didn't care about any overlap either, but also created an ila with the full
					// credit memo's qty.
					qtyInvoicedForIla = StockQtyAndUOMQtys
							.create(il.getQtyInvoiced().multiply(factor),
									productId,
									il.getQtyEntered().multiply(factor),
									uomId)
							.negate();

					note = "@C_InvoiceLine@  @QtyInvoiced@ = " + il.getQtyInvoiced() + " @IsCreditedInvoiceReinvoicable@='Y'; ignoring overlap, because credit memo";
				}
				else if (creditMemo && !creditedInvoiceReinvoicable)
				{
					// the original credit memo's ila also has QtyInvoiced=0
					qtyInvoicedForIla = StockQtyAndUOMQtys.createZero(productId, uomId);
					note = "@C_InvoiceLine@  @QtyInvoiced@ = " + il.getQtyInvoiced() + " @IsCreditedInvoiceReinvoicable@='N'";
				}
				else
				{
					// default behavior: subtract something, but not more than what was originally invoiced.
					final StockQtyAndUOMQty reversalQtyInvoiced = StockQtyAndUOMQtys
							.create(reversalLine.getQtyInvoiced().multiply(factor),
									productId,
									reversalLine.getQtyEntered().multiply(factor),
									uomId);

					// task 08927: it could be that il's original qtyInvoiced was already subtracted (maybe partially)
					// we only want to subtract the qty that was not yet subtracted
					final IPair<StockQtyAndUOMQty, Money> qtyInvoicedAndNetAmtInvoiced = sumupQtyInvoicedAndNetAmtInvoiced(invoiceCandidate);
					final StockQtyAndUOMQty qtyInvoicedForIc = qtyInvoicedAndNetAmtInvoiced.getLeft();

					// examples:
					// reversalQtyInvoiced = -5, qtyInvoicedForIc = 3 (because of partial reinvoicable credit memo with qty 2) => overlap=-2 => create Ila with qty -5-(-2)=-3
					// reversalQtyInvoiced = -5, qtyInvoicedForIc = 11 (maybe because of some fuckup, idk) => overlap=6 => create Ila with qty -5-(+6)=-11
					// reversalQtyInvoiced = -5, qtyInvoicedForIc = -10 (maybe because of some fuckup, idk) => overlap=-15 => create ila with qty -5-(-15)=10

					// reversalQtyInvoiced = 5 (because we reverse a credit memo), qtyInvoicedForIc = 1 => overlap=2 => create ila with qty -5-(+2)=3
					final StockQtyAndUOMQty overlap = reversalQtyInvoiced.add(qtyInvoicedForIc);
					qtyInvoicedForIla = reversalQtyInvoiced.subtract(overlap);

					note = "@C_InvoiceLine@  @QtyInvoiced@ = " + reversalQtyInvoiced
							+ ", @C_Invoice_Candidate@ @QtyInvoiced@ = " + qtyInvoicedForIc
							+ ", (=>overlap=" + overlap + ")";
				}

				createUpdateIla(
						invoiceCandidate,
						reversalLine,
						qtyInvoicedForIla,
						note);
			}
		}
	}

	/**
	 * Set the qtyToInvoice_Override and Price_Entered_Override in the invoice candidate given by its ID
	 *
	 * @param invoiceCandidateId
	 * @param qtyToInvoiceOverride
	 * @param priceEnteredOverride
	 */
	private static void setQtyAndPriceOverride(final int invoiceCandidateId, final BigDecimal qtyToInvoiceOverride, final BigDecimal priceEnteredOverride)
	{
		final I_C_Invoice_Candidate invoiceCandidate = InterfaceWrapperHelper.create(Env.getCtx(), invoiceCandidateId, I_C_Invoice_Candidate.class, ITrx.TRXNAME_ThreadInherited);

		invoiceCandidate.setQtyToInvoice_Override(qtyToInvoiceOverride);

		invoiceCandidate.setPriceEntered_Override(priceEnteredOverride);

		invoiceCandidate.setQtyToInvoice_OverrideFulfilled(ZERO);

		InterfaceWrapperHelper.save(invoiceCandidate);
	}

	@Override
	public void handleCompleteForInvoice(final org.compiere.model.I_C_Invoice invoice)
	{
		if (!DYNATTR_C_Invoice_Candidates_need_NO_ila_updating_on_Invoice_Complete.isNull(invoice)
				&& DYNATTR_C_Invoice_Candidates_need_NO_ila_updating_on_Invoice_Complete.getValue(invoice))
		{
			return; // nothing to do for us
		}

		//
		// Skip reversal invoice completion because we handle the reversal case in "handleReversalForInvoice"
		final int reversalInvoiceId = invoice.getReversal_ID();
		final boolean isReversal = reversalInvoiceId > 0 && invoice.getC_Invoice_ID() > reversalInvoiceId;
		if (isReversal)
		{
			// This is the actual reversal invoice. Skip it. It shall be dealt with in the handleReversalForInvoice() method
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final boolean isCreditMemo = Services.get(IInvoiceBL.class).isCreditMemo(invoice);
		final de.metas.invoicecandidate.model.I_C_Invoice invoiceExt = InterfaceWrapperHelper.create(invoice, de.metas.invoicecandidate.model.I_C_Invoice.class);
		final boolean creditMemoReinvoicable = invoiceExt.isCreditedInvoiceReinvoicable(); // task 08927: this is only relevant if isCreditMemo, see below

		for (final I_C_InvoiceLine il : Services.get(IInvoiceDAO.class).retrieveLines(invoice))
		{
			//
			// Invalidate existing candidates that belong to 'il'
			// NOTE: in case invoice was not created from invoice candidates (e.g. Kommissionierung or Credit memo)
			// here we will get ZERO candidates
			{
				final List<I_C_Invoice_Candidate> invoiceCands = invoiceCandDAO.retrieveIcForIl(il);
				invoiceCandDAO.invalidateCands(invoiceCands);
			}

			final Set<I_C_Invoice_Candidate> toLinkAgainstIl = new HashSet<>();

			if (il.getC_OrderLine_ID() > 0)
			{
				//
				// If 'il' has an order line, make sure that this order line also has invoice candidates and that those candidates also refer 'il'

				final TableRecordReference olReference = TableRecordReference.of(I_C_OrderLine.Table_Name, il.getC_OrderLine_ID()); // no need to load the OL just yet
				final List<I_C_Invoice_Candidate> existingICs = invoiceCandDAO.retrieveReferencing(olReference);
				if (existingICs.isEmpty())
				{
					final I_C_OrderLine ol = InterfaceWrapperHelper.create(il.getC_OrderLine(), I_C_OrderLine.class);
					// NOTE: in case 'invoice' was not created from invoice candidates, it's a big chance here to get zero existing ICs
					// so we need to create them now

					logger.debug("Current C_InvoiceLine {} has a C_OrderLine {} which is not referenced by any C_Invoice_Candidate", new Object[] { il, ol });
					final IInvoiceCandidateHandlerBL creatorBL = Services.get(IInvoiceCandidateHandlerBL.class);
					final List<I_C_Invoice_Candidate> invoiceCandsNew = creatorBL.createMissingCandidatesFor(ol);

					logger.debug("Created C_Invoice_Candidates for C_OrderLine {}: {}", new Object[] { ol, invoiceCandsNew });
					toLinkAgainstIl.addAll(invoiceCandsNew);

					//
					// Make sure the invoice candidates are valid before we link to them, because their C_Invoice_Candidate_Agg_ID is copied to the ila
					updateInvalid()
							.setContext(ctx, trxName)
							.setTaggedWithAnyTag()
							.setOnlyC_Invoice_Candidates(invoiceCandsNew)
							.update();
				}
				else
				{
					// task 04868, G03T010: even if invoice candidates exist, there might be no link in case the invoice has been created "old school", like new MInvoice(...), or by crediting an
					// invoice
					toLinkAgainstIl.addAll(existingICs);
				}
			}

			if (il.getRef_InvoiceLine_ID() > 0)                                // note: this is (also) the case for credit memos, see IInvoiceBL.creditInvoice() and the invocations it makes
			{
				//
				// task 08927: if il e.g. belongs to the credit memo of an inoutLine or a quality inspection, still get the invoice candidate
				// note that if there is already an invoice, then we assume that the invoice line either has ICs, or that it shall not have any, so we don't try to create missing ICs here
				toLinkAgainstIl.addAll(invoiceCandDAO.retrieveIcForIl(il.getRef_InvoiceLine()));
			}

			for (final I_C_Invoice_Candidate icToLink : toLinkAgainstIl)
			{
				//
				// Make sure invoice candidate is fresh before we actually use it (task 06162, also see javadoc of updateInvalid method)
				InterfaceWrapperHelper.refresh(icToLink);

				// final BigDecimal qtyInvoiced = getQtyToInvoice(icToLink); // why on earth should we use the ic's QtyToInvoice, as opposed to the Qty that *was* actually invoiced to start with?
				final StockQtyAndUOMQty qtysInvoiced;
				final UomId ilUomId = UomId.ofRepoId(il.getC_UOM_ID());
				final ProductId ilProductId = ProductId.ofRepoId(il.getM_Product_ID());

				final String note;
				if (isCreditMemo)
				{
					// task 08927
					note = "@C_DocType_ID@=" + invoice.getC_DocType().getName() + ", @IsCreditedInvoiceReinvoicable@=" + creditMemoReinvoicable;
					if (creditMemoReinvoicable)
					{
						qtysInvoiced = StockQtyAndUOMQtys
								.create(il.getQtyInvoiced(), ilProductId, il.getQtyEntered(), ilUomId)
								.negate(); // this will allow the user to re-invoice, just as if the credit memo was a reversal
					}
					else
					{
						qtysInvoiced = StockQtyAndUOMQtys.createZero(ilProductId, ilUomId);
					}
				}
				else
				{
					note = "";
					qtysInvoiced = StockQtyAndUOMQtys
							.create(il.getQtyInvoiced(), ilProductId, il.getQtyEntered(), ilUomId); // the standard case
				}
				createUpdateIla(icToLink, il, qtysInvoiced, note);
				// note: if an ILA is created, the icToLink is automatically invalidated via C_Invoice_Line_Alloc model validator

			}
		}
	}

	@Override
	public void updateProcessedFlag(final I_C_Invoice_Candidate ic)
	{
		Boolean processed = null;
		if (!InterfaceWrapperHelper.isNullOrEmpty(ic, I_C_Invoice_Candidate.COLUMNNAME_Processed_Override))
		{
			// #243: if it is set, then always go with the processed-override value; even if the IC has an error
			processed = extractProcessedOverride(ic).isTrue();
		}

		final boolean processedCalc;

		// If invoice candidate has errors, don't update the Processed_Calc value until the error is solved.
		if (ic.isError())
		{
			// ...unless (gh #428) the ic is currently processed, and there is an error, then we might want to unset the processed flag. Otherwise, the users might not notice the error.
			// if processedCalc is currently set, we want to unset it.
			// note that we still leave
			processedCalc = false;
		}
		else
		{
			// services
			final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

			//
			// if qtyInvoiced is >= qtyOrdered, then there is no further Qty to be invoiced
			final BigDecimal qtyOrdered = getQtyOrderedInStockUOM(ic);
			final boolean noOpenQty = qtyOrdered.abs().compareTo(ic.getQtyInvoiced().abs()) <= 0;

			//
			// we need to know if there are already any invoice lines which have not been reversed
			final List<I_C_Invoice_Line_Alloc> ilasForIc = invoiceCandDAO.retrieveIlaForIc(InvoiceCandidateIds.ofRecord(ic));
			int nonReversedIlas = 0;
			for (final I_C_Invoice_Line_Alloc ila : ilasForIc)
			{
				final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(ila.getDocStatus());
				if (!docStatus.isReversed())
				{
					nonReversedIlas++;
				}
			}

			// task 08567: add a way to override "processed" by the user
			processedCalc = noOpenQty && nonReversedIlas > 0;
		}

		if (processed == null)
		{
			processed = processedCalc; // processed wasn't set via Processed_Override
		}

		ic.setProcessed_Calc(processedCalc);
		ic.setProcessed(processed);

		// 08459
		// If the IC is processed, the qtyToInvoice must turn 0
		if (processed)
		{
			ic.setQtyToInvoiceInPriceUOM(ZERO);
			ic.setQtyToInvoice(ZERO);
		}
	}

	@Override
	public void resetError(final I_C_Invoice_Candidate ic)
	{
		ic.setIsError(false);
		ic.setAD_Note_ID(-1);
		ic.setErrorMsg(null);
	}

	/* package */void discardChangesAndSetError(final I_C_Invoice_Candidate ic, final Exception e)
	{
		//
		// Backup fields that we want to keep
		final String resultMsgBkp = ic.getSchedulerResult();

		//
		// Discard all changes that we did so far in our invoice candidate
		InterfaceWrapperHelper.refresh(ic, true); // discardChanges=true

		//
		// Set back the fields we wanted to keep
		ic.setSchedulerResult(resultMsgBkp);

		//
		// Update error status
		setError(ic, e);
	}

	@Override
	public void setError(final I_C_Invoice_Candidate ic, final Throwable e)
	{
		Check.assumeNotNull(e, "e not null");

		I_AD_Note note = null;

		// 02817: Don't only handle InconsistentUpdateExeptions, but handle RuntimeExceptions in general
		// (albeit less user-friendly)
		// Reason: The system should be able to mark the problematic record and go on
		if (e instanceof InconsistentUpdateExeption)
		{
			final InconsistentUpdateExeption iue = (InconsistentUpdateExeption)e;
			final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
			note = new MNote(ctx,
					iue.getAD_Message_Value(),
					iue.getAdUserToNotifyId(),
					ITrx.TRXNAME_None // save it out-of-trx
			);
			note.setTextMsg(e.getMessage());
			note.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice_Candidate.Table_Name));
			note.setRecord_ID(ic.getC_Invoice_Candidate_ID());
			InterfaceWrapperHelper.save(note);
		}

		final boolean askForRegeneration;
		if (e instanceof ProductNotOnPriceListException)
		{
			askForRegeneration = true;
		}
		else if (e instanceof de.metas.pricing.exceptions.ProductNotOnPriceListException)
		{
			askForRegeneration = true;
		}
		else
		{
			askForRegeneration = false;
		}

		String errorMsg = e.getLocalizedMessage();
		if (Check.isEmpty(errorMsg) || errorMsg.length() < 4)
		{
			errorMsg = e.toString();
		}

		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			logger.warn("Failed processing invoice candidate BUT GOING FORWARD: " + ic, e);
		}

		setError(ic, errorMsg, note, askForRegeneration);
	}

	@Override
	public void setError(final I_C_Invoice_Candidate ic, final String errorMsg, final I_AD_Note note)
	{
		final boolean askForDeleteRegeneration = false;
		setError(ic, errorMsg, note, askForDeleteRegeneration);
	}

	@Override
	public void setError(final I_C_Invoice_Candidate ic, final String errorMsg, final I_AD_Note note, final boolean askForDeleteRegeneration)
	{
		final String errorMessageToUse;
		if (!askForDeleteRegeneration)
		{
			errorMessageToUse = errorMsg;
		}
		else
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
			final String msgAskForRegeneration = Services.get(IMsgBL.class).translate(ctx, MSG_FixProblemDeleteWaitForRegeneration);
			errorMessageToUse = new StringBuilder(errorMsg)
					.append("\n").append(msgAskForRegeneration)
					.toString();
		}

		ic.setIsError(true);
		ic.setErrorMsg(errorMessageToUse);
		ic.setAD_Note_ID(note != null ? note.getAD_Note_ID() : -1);

		//
		// If we are running in batch update process, append the error message to SchedulerResult just to not lose it in case more issues are coming
		if (isUpdateProcessInProgress())
		{
			amendSchedulerResult(ic, errorMessageToUse);
		}
	}

	// package-visible for testing
	/* package */BigDecimal getQtyDelivered_Effective(final I_C_Invoice_Candidate ic)
	{

		final BigDecimal factor;
		if (ic.getQtyOrdered().signum() < 0)
		{
			factor = ONE.negate();
		}
		else
		{
			factor = ONE;
		}

		return ic.getQtyDelivered().subtract((ic.getQtyWithIssues_Effective()).multiply(factor));
	}

	@Override
	public I_C_Tax getTaxEffective(final I_C_Invoice_Candidate candidate)
	{
		final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

		final I_C_Tax taxOverride = taxDAO.getTaxByIdOrNull(candidate.getC_Tax_Override_ID());

		if (taxOverride != null)
		{
			return taxOverride;
		}
		return taxDAO.getTaxById(candidate.getC_Tax_ID());
	}

	/**
	 * Calculate Maximum invoiceable quantity by considering ordered qty and delivered qty.
	 *
	 * Normally the returning value is <code>qtyOrdered</code>, but in case we have a over delivery, then we shall consider <code>qtyDelivered</code> as invoiceable quantity.
	 *
	 * @param qtyOrdered
	 * @param qtyDelivered
	 * @return maximum invoiceable quantity
	 * @task 07847
	 */
	// package level for testing purposes
	/* package */Quantity calculateMaxInvoicableQtyFromEnteredAndDelivered(
			@NonNull final Quantity qtyOrdered,
			@NonNull final Quantity qtyDelivered)
	{
		final Quantity maxInvoicableQty;
		final boolean negativeQtyEntered = qtyOrdered.signum() <= 0;

		//
		// Case: we deal with a negative quantity ordered (e.g. returns, "reversals", manual invoice candidates with negative Qty)
		if (negativeQtyEntered)
		{
			maxInvoicableQty = qtyOrdered.min(qtyDelivered);
		}
		//
		// Standard Case: we deal with positive quantity ordered
		else
		{
			maxInvoicableQty = qtyOrdered.max(qtyDelivered);
		}

		return maxInvoicableQty;
	}

	@Override
	public Percent getQualityDiscountPercentEffective(final I_C_Invoice_Candidate candidate)
	{
		final BigDecimal qualitDiscountPercentOverride = InterfaceWrapperHelper.getValueOrNull(candidate, I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override);

		if (qualitDiscountPercentOverride != null)
		{
			return Percent.of(qualitDiscountPercentOverride);
		}

		return Percent.of(candidate.getQualityDiscountPercent());
	}

	@Override
	public void updatePOReferenceFromOrder(final I_C_Invoice_Candidate candidate)
	{
		if (candidate.isProcessed() == true)
		{
			return; // do nothing in case of processed ICs
		}
		if (candidate.getC_Order_ID() <= 0)
		{
			return;
		}

		final I_C_Order order = InterfaceWrapperHelper.create(candidate.getC_Order(), I_C_Order.class);

		// In case the order is not completed (i.e. it was reactivated) and if it also wasn't modified by anyone,
		// then null the POReference
		if (Objects.equals(order.getPOReference(), candidate.getPOReference())
				&& !IDocument.STATUS_Completed.equals(order.getDocStatus()))
		{
			candidate.setPOReference(null);
			return;
		}

		if (!Check.isEmpty(candidate.getPOReference(), true))
		{
			// do not change an already set POReference in an invoice candidate
			return;
		}
		candidate.setPOReference(order.getPOReference());
	}

	@Override
	public Timestamp getToday()
	{
		// NOTE: use login date if available
		final Properties ctx = Env.getCtx();
		final Timestamp today = Env.getDate(ctx);
		return today;
		// return SystemTime.asDayTimestamp();
	}

	@Override
	public void setQualityDiscountPercent_Override(final I_C_Invoice_Candidate ic, final ImmutableAttributeSet attributes)
	{
		final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final IProductDAO productsRepo = Services.get(IProductDAO.class);

		final int discountSchemaId = bpartnerBL.getDiscountSchemaId(BPartnerId.ofRepoId(ic.getBill_BPartner_ID()), SOTrx.ofBoolean(ic.isSOTrx()));
		if (discountSchemaId <= 0)
		{
			// do nothing
			return;
		}

		BigDecimal qty = ic.getQtyToInvoice();
		if (qty.signum() < 0)
		{
			final org.compiere.model.I_M_InOut inout = ic.getM_InOut();

			if (inout != null)
			{
				if (Services.get(IInOutBL.class).isReturnMovementType(inout.getMovementType()))
				{
					qty = qty.negate();
				}
			}
		}

		final BigDecimal priceActual = ic.getPriceActual();
		final ProductId productId = ProductId.ofRepoId(ic.getM_Product_ID());
		final ProductAndCategoryAndManufacturerId product = productsRepo.retrieveProductAndCategoryAndManufacturerByProductId(productId);

		final PricingConditions pricingConditions = pricingConditionsRepo.getPricingConditionsById(discountSchemaId);
		final PricingConditionsBreak appliedBreak = pricingConditions.pickApplyingBreak(PricingConditionsBreakQuery.builder()
				.attributes(attributes)
				.product(product)
				.qty(qty)
				.price(priceActual)
				.build());

		final BigDecimal qualityDiscountPercentage = appliedBreak != null ? appliedBreak.getQualityDiscountPercentage() : null;
		ic.setQualityDiscountPercent_Override(qualityDiscountPercentage);
	}

	@Override
	public void closeInvoiceCandidates(
			@NonNull final Iterator<I_C_Invoice_Candidate> candidatesToClose)
	{
		while (candidatesToClose.hasNext())
		{
			closeInvoiceCandidate(candidatesToClose.next());
		}
	}

	@Override
	public void closeInvoiceCandidate(final I_C_Invoice_Candidate candidate)
	{
		final IInvoiceCandidateListeners invoiceCandidateListeners = Services.get(IInvoiceCandidateListeners.class);
		invoiceCandidateListeners.onBeforeClosed(candidate);
		candidate.setProcessed_Override("Y");
		candidate.setQtyToInvoice(ZERO);

		if (!InterfaceWrapperHelper.hasChanges(candidate))
		{
			return; // https://github.com/metasfresh/metasfresh/issues/3216
		}

		Services.get(IInvoiceCandDAO.class).invalidateCand(candidate);
		InterfaceWrapperHelper.save(candidate);
	}

	@Override
	public boolean isCloseIfIsToClear()
	{
		final boolean isCloseIfIsToClear = Services.get(ISysConfigBL.class)
				.getBooleanValue(SYS_Config_C_Invoice_Candidate_Close_IsToClear, false);

		return isCloseIfIsToClear;
	}

	@Override
	public boolean isCloseIfPartiallyInvoiced()
	{
		final boolean isCloseIfPartiallyInvoiced = Services.get(ISysConfigBL.class)
				.getBooleanValue(SYS_Config_C_Invoice_Candidate_Close_PartiallyInvoiced, false);

		return isCloseIfPartiallyInvoiced;
	}

	@Override
	public void closePartiallyInvoiced_InvoiceCandidates(final I_C_Invoice invoice)
	{
		if (!isCloseIfPartiallyInvoiced())
		{
			return;
		}

		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		for (final I_C_InvoiceLine il : invoiceDAO.retrieveLines(invoice))
		{
			for (final I_C_Invoice_Candidate candidate : invoiceCandDAO.retrieveIcForIl(il))
			{
				if (candidate.getQtyToInvoice().compareTo(candidate.getQtyOrdered()) < 0)
				{
					closeInvoiceCandidate(candidate);
				}
			}
		}
	}

	@Override
	public void candidates_unProcess(final I_C_Invoice invoice)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		for (final I_C_InvoiceLine il : invoiceDAO.retrieveLines(invoice))
		{
			for (final I_C_Invoice_Candidate candidate : invoiceCandDAO.retrieveIcForIl(il))
			{
				if (candidate == null)
				{
					// shall not happen
					continue;
				}

				final OptionalBoolean processedOverride = extractProcessedOverride(candidate);
				if (processedOverride.isUnknown())
				{
					// nothing to do
					continue;
				}

				if (processedOverride.isTrue())
				{
					candidate.setProcessed_Override(null);
					InterfaceWrapperHelper.save(candidate);
				}
			}
		}
	}

	@Override
	public void markInvoiceCandInDisputeForReceiptLine(final I_M_InOutLine receiptLine)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final IInOutBL inoutBL = Services.get(IInOutBL.class);

		if (receiptLine.getM_InOut().isSOTrx())
		{
			// not interesting. Do nothing
			return;
		}

		if (inoutBL.isReversal(receiptLine))
		{
			// not interesting, Do nothing
			return;
		}

		invoiceCandDAO.retrieveInvoiceCandidatesForInOutLine(receiptLine)
				.stream()
				.forEach(cand -> setCandInDispute(cand));
	}

	private void setCandInDispute(final I_C_Invoice_Candidate cand)
	{
		cand.setIsInDispute(true);
		save(cand);
	}

	public void setAmountAndDateForFreightCost(final I_C_Invoice_Candidate ic)
	{
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		if (!ic.isFreightCost())
		{
			// nothing to do
			return;
		}
		final OrderId orderId = OrderId.ofRepoIdOrNull(ic.getC_Order_ID());

		if (orderId == null)
		{
			// nothing to do;
			return;
		}
		// boolean hasInvoiceableInvoiceCands = invoiceCandDAO.hasInvoiceableInvoiceCands(orderId);

		final InvoiceCandidateId firstInvoiceableInvoiceCandId = invoiceCandDAO.getFirstInvoiceableInvoiceCandId(orderId);

		if (firstInvoiceableInvoiceCandId != null)
		{
			final I_C_OrderLine orderLine = orderDAO.getOrderLineById(ic.getC_OrderLine_ID());

			if (orderLine == null)
			{
				// nothing to do
				return;
			}

			final I_C_Invoice_Candidate firstInvoiceableCandRecord = invoiceCandDAO.getById(firstInvoiceableInvoiceCandId);

			ic.setDeliveryDate(firstInvoiceableCandRecord.getDeliveryDate());
			ic.setQtyToInvoice(ONE);
			ic.setQtyDelivered(ONE);
			ic.setQtyToInvoiceInUOM(ONE);
			set_DateToInvoice_DefaultImpl(ic);
		}

		else
		{
			ic.setQtyToInvoice(ZERO);
			ic.setQtyDelivered(ZERO);
			ic.setQtyToInvoiceInUOM(ZERO);
			set_DateToInvoice_DefaultImpl(ic);
		}
	}

	@Override
	public OptionalBoolean extractProcessedOverride(@NonNull final I_C_Invoice_Candidate candidate)
	{
		return OptionalBoolean.ofNullableString(candidate.getProcessed_Override());
	}

	@Override
	public void updateICIOLAssociationFromIOL(
			@NonNull final I_C_InvoiceCandidate_InOutLine iciol,
			@NonNull final org.compiere.model.I_M_InOutLine inOutLine)
	{
		iciol.setAD_Org_ID(inOutLine.getAD_Org_ID());
		iciol.setM_InOutLine(inOutLine);
		// iciol.setQtyInvoiced(QtyInvoiced); // will be set during invoicing to keep track of which movementQty is already invoiced in case of partial invoicing

		iciol.setQtyDelivered(inOutLine.getMovementQty());

		if (inOutLine.getCatch_UOM_ID() > 0)
		{
			iciol.setC_UOM_ID(inOutLine.getCatch_UOM_ID());

			// if inOutLine.QtyDeliveredCatch is null, then iciol.QtyDeliveredInUOM_Catch must also be null and not zero.
			final BigDecimal catchQtyOrNull = getValueOrNull(inOutLine, I_M_InOutLine.COLUMNNAME_QtyDeliveredCatch);
			iciol.setQtyDeliveredInUOM_Catch(catchQtyOrNull);

			// make sure that both quantities have the same UOM
			final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

			final BigDecimal nominalQty = uomConversionBL.convertQty(
					UOMConversionContext.of(ProductId.ofRepoId(inOutLine.getM_Product_ID())),
					inOutLine.getQtyEntered(),
					UomId.ofRepoId(inOutLine.getC_UOM_ID()) /* uomFrom */,
					UomId.ofRepoId(inOutLine.getCatch_UOM_ID()) /* uomTo */
			);
			iciol.setQtyDeliveredInUOM_Nominal(nominalQty);
		}
		else
		{
			iciol.setC_UOM_ID(assumeGreaterThanZero(inOutLine.getC_UOM_ID(), "inOutLine.getC_UOM_ID()"));
			iciol.setQtyDeliveredInUOM_Nominal(inOutLine.getQtyEntered());
		}
		saveRecord(iciol);
	}
}
