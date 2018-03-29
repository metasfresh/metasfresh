/**
 *
 */
package de.metas.invoicecandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;

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
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Calendar;
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
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IMDiscountSchemaBL;
import org.adempiere.pricing.api.IMDiscountSchemaDAO;
import org.adempiere.pricing.api.IPriceListBL;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.NullLoggable;
import org.adempiere.util.Services;
import org.adempiere.util.concurrent.AutoClosableThreadLocalBoolean;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_InvoiceCandidate_InOutLine;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.MInvoiceSchedule;
import org.compiere.model.MNote;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_DiscountSchemaBreak;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IStatefulWorkpackageProcessorFactory;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.currency.ICurrencyBL;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.spi.impl.IQtyAndQuality;
import de.metas.inoutcandidate.spi.impl.MutableQtyAndQuality;
import de.metas.interfaces.I_C_OrderLine;
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
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Detail;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Line_Alloc;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.tax.api.ITaxBL;
import lombok.NonNull;

/**
 * @author tsa
 *
 */
public class InvoiceCandBL implements IInvoiceCandBL
{
	private static final String MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_DATE_TO_INVOICE = "InvoiceCandBL_Invoicing_Skipped_DateToInvoice";
	private static final String MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_ERROR = "InvoiceCandBL_Invoicing_Skipped_Error";
	private static final String MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_PROCESSED = "InvoiceCandBL_Invoicing_Skipped_Processed";
	private static final String MSG_FixProblemDeleteWaitForRegeneration = "FixProblemDeleteWaitForRegeneration";
	private static final String MSG_INVOICE_CAND_BL_STATUS_INVOICE_SCHEDULE_MISSING_1P = "InvoiceCandBL_Status_InvoiceSchedule_Missing";
	private static final String MSG_INVOICE_CAND_BL_BELOW_INVOICE_MIN_AMT_5P = "InvoiceCandBL_Below_Invoice_Min_Amt";
	private static final String MSG_INVOICE_CAND_BL_STATUS_ORDER_NOT_CO_1P = "InvoiceCandBL_Status_Order_Not_CO";
	private static final String MSG_INVOICE_CAND_BL__UNABLE_TO_CONVERT_QTY_3P = "InvoiceCandBL_Unable_To_Convert_Qty";

	private static final String SYS_Config_C_Invoice_Candidate_Close_IsToClear = "C_Invoice_Candidate_Close_IsToClear";
	private static final String SYS_Config_C_Invoice_Candidate_Close_PartiallyInvoiced = "C_Invoice_Candidate_Close_PartiallyInvoiced";

	// task 08927
	/* package */static final ModelDynAttributeAccessor<org.compiere.model.I_C_Invoice, Boolean> DYNATTR_C_Invoice_Candidates_need_NO_ila_updating_on_Invoice_Complete = new ModelDynAttributeAccessor<>(
			Boolean.class);

	/**
	 *
	 * @task 08451
	 */
	private static final Timestamp DATE_TO_INVOICE_MAX_DATE = TimeUtil.getDay(9999, 12, 31);

	private final Logger logger = InvoiceCandidate_Constants.getLogger(InvoiceCandBL.class);

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
	 * @param ctx
	 * @param ic
	 * @task 08542
	 */
	void set_DateToInvoice(final Properties ctx, final I_C_Invoice_Candidate ic)
	{
		final Timestamp dateToInvoice;

		final String invoiceRule = getInvoiceRule(ic);
		if (X_C_Invoice_Candidate.INVOICERULE_Sofort.equals(invoiceRule))
		{
			dateToInvoice = ic.getDateOrdered();
		}
		else if (X_C_Invoice_Candidate.INVOICERULE_NachLieferung.equals(invoiceRule) || X_C_Invoice_Candidate.INVOICERULE_NachLieferungAuftrag.equals(invoiceRule))
		{
			// if there is no delivery yet, then we set the date to the far future
			dateToInvoice = ic.getDeliveryDate() != null ? ic.getDeliveryDate() : DATE_TO_INVOICE_MAX_DATE;
		}
		else if (X_C_Invoice_Candidate.INVOICERULE_KundenintervallNachLieferung.equals(invoiceRule))
		{
			if (ic.getC_InvoiceSchedule_ID() <= 0)        // that's a paddlin'
			{
				dateToInvoice = DATE_TO_INVOICE_MAX_DATE;
			}
			else
			{
				final I_C_InvoiceSchedule invoiceSched = ic.getC_InvoiceSchedule();

				final Timestamp deliveryDate = ic.getDeliveryDate(); // task 08451: when it comes to invoicing, the important date is not when it was ordered but when the delivery was made
				if (deliveryDate == null)
				{
					// task 08451: we have an invoice schedule, but no delivery yet. Set the date to the far future
					dateToInvoice = DATE_TO_INVOICE_MAX_DATE;
				}
				else
				{
					dateToInvoice = mkDateToInvoiceForInvoiceSchedule(invoiceSched, deliveryDate);
				}
			}
		}
		else
		{
			dateToInvoice = TimeUtil.getDay(ic.getCreated()); // shouldn't happen
		}
		ic.setDateToInvoice(dateToInvoice);
	}

	private Timestamp mkDateToInvoiceForInvoiceSchedule(final I_C_InvoiceSchedule invoiceSched, final Timestamp deliveryDate)
	{
		Check.assumeNotNull(invoiceSched, " param 'invoiceSched' not null");
		Check.assumeNotNull(deliveryDate, " param 'deliveryDate' not null");

		final Timestamp dateToInvoice;

		if (X_C_InvoiceSchedule.INVOICEFREQUENCY_Daily.equals(invoiceSched.getInvoiceFrequency()))
		{
			dateToInvoice = deliveryDate;
		}
		else if (X_C_InvoiceSchedule.INVOICEFREQUENCY_Weekly.equals(invoiceSched.getInvoiceFrequency()))
		{
			final Calendar calToday = Calendar.getInstance();
			calToday.setTime(deliveryDate);
			calToday.set(Calendar.DAY_OF_WEEK, MInvoiceSchedule.getCalendarDay(invoiceSched.getInvoiceWeekDay()));

			final Timestamp dateDayOfWeek = new Timestamp(calToday.getTimeInMillis());
			if (dateDayOfWeek.before(deliveryDate))
			{
				dateToInvoice = TimeUtil.addWeeks(dateDayOfWeek, 1);
			}
			else
			{
				dateToInvoice = dateDayOfWeek;
			}
		}
		else if (X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly.equals(invoiceSched.getInvoiceFrequency())
				|| X_C_InvoiceSchedule.INVOICEFREQUENCY_TwiceMonthly.equals(invoiceSched.getInvoiceFrequency()))
		{
			final Calendar calToday = Calendar.getInstance();
			calToday.setTime(deliveryDate);

			final Timestamp dateDayOfMonth = new Timestamp(calToday.getTimeInMillis());

			if (X_C_InvoiceSchedule.INVOICEFREQUENCY_TwiceMonthly.equals(invoiceSched.getInvoiceFrequency()))
			{
				dateToInvoice = computeDateToInvoice_TwiceMontlhy(dateDayOfMonth);
			}
			else
			{
				if (dateDayOfMonth.before(deliveryDate))
				{

					dateToInvoice = TimeUtil.addMonths(dateDayOfMonth, 1);

				}
				else
				{
					dateToInvoice = dateDayOfMonth;
				}
			}
		}
		else
		{
			throw new AdempiereException(invoiceSched + " has unsupported frequency '" + invoiceSched.getInvoiceFrequency() + "'");
		}
		return dateToInvoice;
	}

	/**
	 * @task 08484
	 */
	private Timestamp computeDateToInvoice_TwiceMontlhy(final Timestamp dateDayOfMonth)
	{
		final Timestamp middleDayOfMonth = TimeUtil.getMonthMiddleDay(dateDayOfMonth);

		if (dateDayOfMonth.compareTo(middleDayOfMonth) <= 0)                                // task 08869
		{
			return middleDayOfMonth;
		}

		final Timestamp lastDayOfMonth = TimeUtil.getMonthLastDay(dateDayOfMonth);

		return lastDayOfMonth;
	}

	void setInvoiceScheduleAmtStatus(final Properties ctx, final I_C_Invoice_Candidate ic)
	{
		//
		// services
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final String invoiceRule = getInvoiceRule(ic);
		if (!X_C_Invoice_Candidate.INVOICERULE_KundenintervallNachLieferung.equals(invoiceRule))
		{
			// Note: the field is supposed not to be displayed on status=OK, so we don't need to localize this
			ic.setInvoiceScheduleAmtStatus("OK");
			return;
		}

		final int invoiceSchedId = retrieveInvoiceScheduleId(ic);
		if (invoiceSchedId <= 0)
		{
			final String msg = msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_STATUS_INVOICE_SCHEDULE_MISSING_1P, new Object[] { ic.getBill_BPartner().getName() });
			ic.setInvoiceScheduleAmtStatus(msg);
			return;
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(ic);
		final I_C_InvoiceSchedule invoiceSched = InterfaceWrapperHelper.create(ctx, invoiceSchedId, I_C_InvoiceSchedule.class, trxName);

		if (invoiceSched.isAmount())
		{
			final Timestamp dateToday = getToday();

			final BigDecimal actualAmt = invoiceCandDAO.retrieveInvoicableAmount(ic.getBill_BPartner(), dateToday);

			if (actualAmt.compareTo(invoiceSched.getAmt()) < 0)
			{
				final I_C_Currency targetCurrency = currencyConversionBL.getBaseCurrency(ctx, ic.getAD_Client_ID(), ic.getAD_Org_ID());
				final String currSymbol = targetCurrency.getCurSymbol();

				final String msg = msgBL.getMsg(ctx,
						InvoiceCandBL.MSG_INVOICE_CAND_BL_BELOW_INVOICE_MIN_AMT_5P,
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
	 *
	 * @param ic
	 * @param oldQtyInvoiced
	 * @param factor
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
	 *
	 * @param ctx
	 * @param ic
	 */
	void set_QtyInvoiced_NetAmtInvoiced_Aggregation0(final Properties ctx, final I_C_Invoice_Candidate ic)
	{
		if (ic.isToClear())
		{
			logger.debug(ic + "is not directly invoiced. QtyInvoiced is handled by a specific module");
		}
		else
		{
			final IPair<BigDecimal, BigDecimal> qtyAndNetAmtInvoiced = sumupQtyInvoicedAndNetAmtInvoiced(ic);

			ic.setQtyInvoiced(qtyAndNetAmtInvoiced.getLeft());

			final BigDecimal netAmtInvoiced = qtyAndNetAmtInvoiced.getRight();
			ic.setNetAmtInvoiced(netAmtInvoiced);
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
	/* package */IPair<BigDecimal, BigDecimal> sumupQtyInvoicedAndNetAmtInvoiced(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);
		final List<I_C_Invoice_Line_Alloc> ilas = invoiceCandDB.retrieveIlaForIc(ic);

		BigDecimal qtyInvoiced = BigDecimal.ZERO;
		BigDecimal netAmtInvoiced = BigDecimal.ZERO;

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

			qtyInvoiced = qtyInvoiced.add(ila.getQtyInvoiced());

			//
			// 07202: We update the net amount invoice according to price UOM.
			final BigDecimal priceActual = ic.getPriceActual();
			final BigDecimal rawNetAmtInvoiced = ila.getQtyInvoiced().multiply(priceActual);

			final BigDecimal amountInvoiced = convertToPriceUOM(rawNetAmtInvoiced, ic);
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
		final IPair<BigDecimal, BigDecimal> qtyAndNetAmtInvoiced = ImmutablePair.of(qtyInvoiced, netAmtInvoiced);
		return qtyAndNetAmtInvoiced;
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
								InvoiceCandBL.MSG_INVOICE_CAND_BL_STATUS_ORDER_NOT_CO_1P,
								new Object[] {
										adReferenceDAO.retrieveListNameTrl(X_C_Order.DOCSTATUS_AD_Reference_ID,
												ol.getC_Order_ID() > 0 ? ol.getC_Order().getDocStatus() : "<null>") // "<null>" shouldn't happen
								}));

				ic.setDiscount(BigDecimal.ZERO);
			}
		}
	}

	/**
	 * If the ic's invoice rule is "CustomerScheduleAfterDelivery", this method tries to get the BPartner's invoice schedule id.
	 *
	 * @param ic
	 * @return
	 */
	private int retrieveInvoiceScheduleId(final I_C_Invoice_Candidate ic)
	{
		final String invoiceRule = getInvoiceRule(ic);
		Check.assume(X_C_Invoice_Candidate.INVOICERULE_KundenintervallNachLieferung.equals(invoiceRule),
				"Method is only called if invoice rule is " + X_C_Invoice_Candidate.INVOICERULE_KundenintervallNachLieferung);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);

		final int C_InvoiceSchedule_ID = ic.getC_InvoiceSchedule_ID();
		if (C_InvoiceSchedule_ID <= 0)
		{
			logger.info("BPartner has no Schedule");
			final String msg = Services.get(IMsgBL.class).getMsg(ctx, InvoiceCandBL.MSG_INVOICE_CAND_BL_STATUS_INVOICE_SCHEDULE_MISSING_1P, new Object[] { ic.getBill_BPartner().getName() });
			amendSchedulerResult(ic, msg);
			ic.setInvoiceScheduleAmtStatus(msg);
			return -1;
		}

		return C_InvoiceSchedule_ID;
	}

	private boolean isOrderFullyDelivered(final I_C_Invoice_Candidate ic)
	{
		if (ic.getC_Order_ID() <= 0)
		{
			return false;
		}
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

		for (final I_C_OrderLine oLine : orderDAO.retrieveOrderLines(ic.getC_Order()))
		{
			final BigDecimal toInvoice = oLine.getQtyOrdered().subtract(oLine.getQtyInvoiced());
			if (toInvoice.signum() == 0 && oLine.getM_Product_ID() > 0)
			{
				continue;
			}
			//
			final boolean fullyDelivered = oLine.getQtyOrdered().compareTo(oLine.getQtyDelivered()) == 0;
			if (!fullyDelivered)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public IInvoiceGenerator generateInvoices()
	{
		return new InvoiceCandBLCreateInvoices();
	}

	@Override
	public IInvoiceGenerateResult generateInvoicesFromSelection(
			final Properties ctx,
			final int AD_PInstance_ID,
			final boolean ignoreInvoiceSchedule,
			final ILoggable loggable,
			final String trxName)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final Iterator<I_C_Invoice_Candidate> candidates = invoiceCandDAO.retrieveIcForSelection(ctx, AD_PInstance_ID, trxName);

		return generateInvoices()
				.setContext(ctx, trxName)
				.setIgnoreInvoiceSchedule(ignoreInvoiceSchedule)
				.setLoggable(loggable)
				.generateInvoices(candidates);
	}

	@Override
	public void setNetAmtToInvoice(final I_C_Invoice_Candidate ic)
	{
		// If invoice candidate has IsToClear=Y then we shall not invoice it.
		// Also we shall set the NetAmtToInvoice to ZERO, to not affect the checksum (when invoicing).
		if (ic.isToClear())
		{
			ic.setNetAmtToInvoice(BigDecimal.ZERO);
			ic.setSplitAmt(BigDecimal.ZERO);
			return;
		}

		// If invoice candidate would be skipped when enqueueing to be invoiced then set the NetAmtToInvoice=0 (Mark request)
		// Reason: if the IC would be skipped we want to have the NetAmtToInvoice=0 because we don't want to affect the overall total that is displayed on window bottom.
		final boolean ignoreInvoiceSchedule = true; // yes, we ignore the DateToInvoice when checking because that's relative to Today
		if (isSkipCandidateFromInvoicing(ic, ignoreInvoiceSchedule, NullLoggable.instance))
		{
			ic.setNetAmtToInvoice(BigDecimal.ZERO);
			ic.setSplitAmt(BigDecimal.ZERO);
			return;
		}

		// Util.errorUnless(ic.isManual(), "Setting NetAmtToInvoice is only allowed for manual candidates, but {} is not manual", ic);
		Services.get(IInvoiceCandidateHandlerBL.class).setNetAmtToInvoice(ic);

		Services.get(IInvoiceCandidateHandlerBL.class).setLineNetAmt(ic);
	}

	@Override
	public void setPriceActualNet(final I_C_Invoice_Candidate ic)
	{
		//
		// Services
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
		final ITaxBL taxBL = Services.get(ITaxBL.class);

		final I_C_Tax tax = invoiceCandBL.getTaxEffective(ic);
		final BigDecimal priceActual = invoiceCandBL.getPriceActual(ic);
		final boolean taxIncluded = invoiceCandBL.isTaxIncluded(ic);
		final int scale = invoiceCandBL.getCurrencyPrecision(ic);

		final BigDecimal priceActualNet = taxBL.calculateBaseAmt(tax, priceActual, taxIncluded, scale);
		ic.setPriceActual_Net_Effective(priceActualNet);
	}

	@Override
	public BigDecimal getPriceActual(final I_C_Invoice_Candidate ic)
	{
		final BigDecimal priceActualOverride;
		if (InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_PriceActual_Override))
		{
			priceActualOverride = null;
		}
		else
		{
			priceActualOverride = ic.getPriceActual_Override();
		}

		if (priceActualOverride == null)
		{
			return ic.getPriceActual();
		}
		return priceActualOverride;
	}

	private BigDecimal getQtyToInvoice_OverrideOrNull(final I_C_Invoice_Candidate ic)
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
	public BigDecimal getDiscount(final I_C_Invoice_Candidate ic)
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
			return ic.getDiscount();
		}

		return discountOverride;
	}

	/**
	 * If the given invoice candidate has an override-value and it is below the given maximum qty, then use the override value.
	 *
	 * @param ic
	 * @param maxQtyToInvoicable
	 * @param factor multiplier for the final result. Expected to be 1 or -1.
	 * @return
	 */
	private BigDecimal getQtyToInvoice(
			final I_C_Invoice_Candidate ic,
			final BigDecimal maxQtyToInvoicable,
			final BigDecimal factor)
	{
		BigDecimal qtyInvoicable;

		final BigDecimal qtyOverride = getQtyToInvoice_OverrideOrNull(ic);
		if (qtyOverride == null || qtyOverride.multiply(factor).compareTo(maxQtyToInvoicable.multiply(factor)) > 0)
		{
			// there is no override value or that value is bigger than 'maxQtyToInvoicable' (i.e. the value that we could actually invoice).
			qtyInvoicable = maxQtyToInvoicable.subtract(ic.getQtyInvoiced()).multiply(factor);
		}
		else
		{
			// subtract the qty that has already been invoiced.
			qtyInvoicable = qtyOverride.subtract(ic.getQtyToInvoice_OverrideFulfilled()).multiply(factor);
		}

		if (ic.isInDispute()
				&& ic.getAD_Table_ID() == InterfaceWrapperHelper.getTableId(I_M_InventoryLine.class) // TODO HARDCODED, see ...
		)
		{
			qtyInvoicable = qtyInvoicable.subtract(ic.getQtyWithIssues_Effective());
		}

		// if the result is < 0, return zero instead.
		if (qtyInvoicable.signum() <= 0)
		{
			return BigDecimal.ZERO;
		}
		return qtyInvoicable.multiply(factor);
	}

	@Override
	public BigDecimal getQtyToInvoice(final I_C_Invoice_Candidate ic)
	{
		final BigDecimal qtyToInvoiceOverride = getQtyToInvoice_OverrideOrNull(ic);
		if (qtyToInvoiceOverride != null)
		{
			return qtyToInvoiceOverride;
		}

		return ic.getQtyToInvoice();
	}

	/**
	 * Gets Qty Ordered (target), considering the QtyDelivered too.
	 *
	 * @param ic
	 * @return
	 */
	private BigDecimal getQtyOrdered(final I_C_Invoice_Candidate ic)
	{
		final BigDecimal qtyOrdered = ic.getQtyOrdered();
		final BigDecimal qtyDelivered = getQtyDelivered_Effective(ic);
		final BigDecimal qtyOrderedActual = calculateMaxInvoicableQtyFromOrderedAndDelivered(qtyOrdered, qtyDelivered);
		return qtyOrderedActual;
	}

	@Override
	public BigDecimal convertToPriceUOM(final BigDecimal qty, final I_C_Invoice_Candidate ic)
	{
		Check.assumeNotNull(qty, "qty not null");
		Check.assumeNotNull(ic, "ic not null");

		if (ic.getM_Product_ID() <= 0)
		{
			logger.debug("returing param qty {} as result, because ic.getM_Product_ID() <= 0");
			return qty;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
		final I_M_Product product = ic.getM_Product();

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final BigDecimal qtyInPriceUOM = uomConversionBL.convertFromProductUOM(ctx, product, ic.getPrice_UOM(), qty);

		logger.debug("converted qty={} of product {} to qtyInPriceUOM={} for ic {}",
				new Object[] { qty, product.getValue(), qtyInPriceUOM, ic });

		if (qtyInPriceUOM == null)
		{
			logger.warn("Can't convert qty={} into price-UOM of ic={}; ", qty, ic);
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			amendSchedulerResult(ic,
					msgBL.getMsg(ctx, InvoiceCandBL.MSG_INVOICE_CAND_BL__UNABLE_TO_CONVERT_QTY_3P, new Object[] { qty, ic.getPrice_UOM(), product.getValue() }));
			ic.setIsError(true);
		}
		return qtyInPriceUOM;
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
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final String invoiceRule = invoiceCandBL.getInvoiceRule(ic);
		if (X_C_Invoice_Candidate.INVOICERULE_EFFECTIVE_KundenintervallNachLieferung.equals(invoiceRule)
				&& ic.getC_InvoiceSchedule_ID() > 0)
		{
			final I_C_InvoiceSchedule invoiceSchedule = ic.getC_InvoiceSchedule();
			if (invoiceSchedule.getAmt() != null && invoiceSchedule.getAmt().signum() != 0)
			{
				// we need to invalidate all the partner's ICs *if* there is an overall invoiceable sum-amount from which on the ICs shall be invoiced
				invoiceCandDAO.invalidateCandsForBPartnerInvoiceRule(ic.getBill_BPartner());
			}
		}
	}

	@Override
	public IInvoiceCandidateEnqueuer enqueueForInvoicing()
	{
		return new InvoiceCandidateEnqueuer();
	}

	@Override
	public boolean isSkipCandidateFromInvoicing(final I_C_Invoice_Candidate ic, final boolean ignoreInvoiceSchedule, final ILoggable loggable)
	{
		// 04533: ignore already processed candidates
		// task 08343: if the ic is processed (after the recent update), then skip it (this logic was in the where clause in C_Invoice_Candidate_EnqueueSelection)
		if (ic.isProcessed())
		{
			final String msg = Services.get(IMsgBL.class).getMsg(InterfaceWrapperHelper.getCtx(ic), MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_PROCESSED, new Object[] { ic.getC_Invoice_Candidate_ID() });
			logger.info(msg);
			// loggable.addLog(msg); don't log to the user, it's already shown to the user via field coloring *before* he/she enqueues, and otherwise usually filtered out by the user anyways
			return true;
		}

		// ignore "error" candidates
		if (ic.isError())
		{
			final String msg = new StringBuilder()
					.append(Services.get(IMsgBL.class).getMsg(InterfaceWrapperHelper.getCtx(ic), MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_ERROR, new Object[] { ic.getC_Invoice_Candidate_ID() }))
					.append(": ")
					.append(ic.getErrorMsg())
					.toString();

			logger.debug(msg);
			// loggable.addLog(msg); don't log to the user, it's already shown to the user via field coloring *before* he/she enqueues
			return true;
		}

		if (ic.isToClear())
		{
			// don't log (per Mark request) because those could be a lot and because user has no opportunity to react
			// final String msg = msgBL.getMsg(getCtx(), MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_IS_TO_CLEAR, new Object[] { ic.getC_Invoice_Candidate_ID() });
			// loggable.addLog(msg);
			return true;
		}

		if (ic.isInDispute())
		{
			// don't log (per Mark request) because those could be a lot and because user has no opportunity to react
			// final String msg = msgBL.getMsg(getCtx(), MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_IS_IN_DISPUTE, new Object[] { ic.getC_Invoice_Candidate_ID() });
			// loggable.addLog(msg);
			return true;
		}

		// flagged via field color
		// ignore candidates that can't be invoiced yet
		final Timestamp dateToInvoice = getDateToInvoice(ic);
		if (!ignoreInvoiceSchedule
				&& (dateToInvoice == null || dateToInvoice.after(getToday())))
		{
			final String msg = Services.get(IMsgBL.class).getMsg(InterfaceWrapperHelper.getCtx(ic), MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_DATE_TO_INVOICE,
					new Object[] { ic.getC_Invoice_Candidate_ID(), dateToInvoice, getToday() });
			logger.debug(msg);
			// loggable.addLog(msg); don't log to the user, it's already shown to the user via field coloring *before* he/she enqueues
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
	public BigDecimal calculateNetAmt(final I_C_Invoice_Candidate ic)
	{
		final BigDecimal candQtyToInvoice = convertToPriceUOM(getQtyToInvoice(ic), ic);
		final BigDecimal candPriceActual = getPriceActual(ic);
		final int precision = getPrecisionFromCurrency(ic);

		final BigDecimal candNetAmtToInvoiceCalc = calculateNetAmt(candQtyToInvoice, candPriceActual, precision);
		return candNetAmtToInvoiceCalc;
	}

	@Override
	public int getPrecisionFromCurrency(final I_C_Invoice_Candidate ic)
	{

		final I_C_Currency currency = ic.getC_Currency();
		if (currency == null)
		{
			// Case: currency was not set yet because we got some errors on
			// prices
			// => assume 2 (the most common one)
			return 2;
		}
		final int precision = currency.getStdPrecision();
		return precision;
	}

	@Override
	public int getPrecisionFromPricelist(final I_C_Invoice_Candidate ic)
	{

		// take the precision from the bpartner price list

		final I_M_PricingSystem pricingSystem = ic.getM_PricingSystem();
		final Timestamp date = ic.getDateOrdered();
		final boolean isSOTrx = ic.isSOTrx();
		final I_C_BPartner_Location partnerLocation = ic.getBill_Location();
		if (partnerLocation != null)
		{
			final I_M_PriceList pricelist = Services.get(IPriceListBL.class)
					.getCurrentPricelistOrNull(
							pricingSystem,
							partnerLocation.getC_Location().getC_Country(),
							date,
							isSOTrx);

			if (pricelist != null)
			{
				return pricelist.getPricePrecision();
			}
		}
		// fall back: get the precision from the currency
		return getPrecisionFromCurrency(ic);

	}

	@Override
	public BigDecimal calculateNetAmt(final BigDecimal qty, final BigDecimal price, final int currencyPrecision)
	{
		final BigDecimal netAmtToInvoiceCalc = qty.multiply(price).setScale(currencyPrecision, RoundingMode.HALF_UP);
		return netAmtToInvoiceCalc;
	}

	@Override
	public I_C_Invoice_Candidate splitCandidate(final I_C_Invoice_Candidate ic, final String trxName)
	{
		// services
		final IAggregationBL aggregationBL = Services.get(IAggregationBL.class);

		final BigDecimal splitAmt = ic.getSplitAmt();
		Check.assume(splitAmt.signum() != 0, "Split amount shall not be zero: {}", ic);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);

		final I_C_Invoice_Candidate splitCand = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate.class, trxName);

		// splitCand.setAD_Client_ID(ic.getAD_Client_ID());
		Check.assume(splitCand.getAD_Client_ID() == ic.getAD_Client_ID(), "Same AD_Client_ID (split's AD_Client_ID={}, IC's AD_Client_ID={}", splitCand.getAD_Client_ID(), ic.getAD_Client_ID());
		splitCand.setAD_Org_ID(ic.getAD_Org_ID());

		splitCand.setRecord_ID(ic.getRecord_ID()); // even if 0, we can't leave it empty, as the column is mandatory

		splitCand.setIsActive(true);
		splitCand.setProcessed(false);
		resetError(ic); // make sure there is no error set

		splitCand.setIsManual(ic.isManual());
		splitCand.setIsSOTrx(ic.isSOTrx());
		splitCand.setC_ILCandHandler(ic.getC_ILCandHandler());

		splitCand.setC_Invoice_Candidate_Agg_ID(ic.getC_Invoice_Candidate_Agg_ID());
		aggregationBL.setHeaderAggregationKey(splitCand);
		splitCand.setLineAggregationKey(null);
		splitCand.setLineAggregationKey_Suffix(ic.getLineAggregationKey_Suffix());
		splitCand.setDescription(ic.getDescription());

		splitCand.setBill_BPartner_ID(ic.getBill_BPartner_ID());
		splitCand.setBill_Location_ID(ic.getBill_Location_ID());
		splitCand.setBill_User_ID(ic.getBill_User_ID());

		splitCand.setInvoiceRule(ic.getInvoiceRule());
		splitCand.setInvoiceRule_Override(ic.getInvoiceRule_Override());

		splitCand.setM_PricingSystem_ID(ic.getM_PricingSystem_ID());
		splitCand.setM_Product_ID(ic.getM_Product_ID());
		splitCand.setIsPackagingMaterial(ic.isPackagingMaterial());
		splitCand.setC_Charge_ID(ic.getC_Charge_ID());

		splitCand.setQtyOrdered(BigDecimal.ONE);
		splitCand.setQtyDelivered(BigDecimal.ZERO);
		splitCand.setQtyToInvoice(BigDecimal.ONE);
		splitCand.setQtyToInvoice_Override(BigDecimal.ONE);

		splitCand.setC_Currency_ID(ic.getC_Currency_ID());
		splitCand.setC_ConversionType_ID(ic.getC_ConversionType_ID());
		splitCand.setPriceActual(splitAmt);
		splitCand.setIsTaxIncluded(false);
		splitCand.setPrice_UOM_ID(ic.getPrice_UOM_ID()); // 07090 when we set PiceActual, we shall also set PriceUOM.
		splitCand.setPriceEntered(splitAmt); // cg : task 04917 -- same as price actual
		splitCand.setDiscount(BigDecimal.ZERO);
		splitCand.setSplitAmt(BigDecimal.ZERO);

		splitCand.setDateInvoiced(ic.getDateInvoiced());
		splitCand.setDateOrdered(ic.getDateOrdered());
		splitCand.setDateToInvoice(ic.getDateToInvoice());
		splitCand.setDateToInvoice_Override(ic.getDateToInvoice_Override());

		// 07442
		// also set activity and tax
		splitCand.setC_Activity(ic.getC_Activity());

		// 07814: setting both tax and tax-override to get an exact copy
		splitCand.setC_Tax(ic.getC_Tax());
		splitCand.setC_Tax_Override(ic.getC_Tax_Override());

		InterfaceWrapperHelper.save(splitCand);
		return splitCand;
	}

	@Override
	public String getInvoiceRule(final I_C_Invoice_Candidate ic)
	{
		final String invoiceRuleOverride = ic.getInvoiceRule_Override();
		if (invoiceRuleOverride != null)
		{
			return invoiceRuleOverride;
		}

		return ic.getInvoiceRule();
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
			final BigDecimal qtyInvoiced,
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
			if (qtyInvoiced.signum() == 0)
			{
				return existingIla;
			}

			// 05475: If exists, update the qtyInvoiced.
			existingIla.setQtyInvoiced(qtyInvoiced);
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
		newIla.setQtyInvoiced(qtyInvoiced);
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
			final int precision = getPrecisionFromPricelist(ic);
			final BigDecimal priceEntered = getPriceEntered(ic);
			final BigDecimal discount = getDiscount(ic);
			final IOrderLineBL olBL = Services.get(IOrderLineBL.class);
			final BigDecimal priceActualOverride = olBL.subtractDiscount(priceEntered, discount, precision);
			ic.setPriceActual_Override(priceActualOverride);
		}
	}

	@Override
	public BigDecimal getPriceEntered(final I_C_Invoice_Candidate ic)
	{
		final BigDecimal priceEnteredOverride;
		if (InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override))
		{
			priceEnteredOverride = null;
		}
		else
		{
			priceEnteredOverride = ic.getPriceEntered_Override();
		}

		if (priceEnteredOverride == null)
		{
			return ic.getPriceEntered();
		}

		return priceEnteredOverride;
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
			creditedInvoiceIsReversed = Services.get(IDocumentBL.class).isDocumentStatusOneOf(originalInvoice, IDocument.STATUS_Reversed);
		}
		else
		{
			creditedInvoiceIsReversed = false;
		}
		// if we deal with a credit memo, we need to thread the qtys as negative
		final BigDecimal factor = creditMemo ? BigDecimal.ONE.negate() : BigDecimal.ONE;

		for (final I_C_InvoiceLine il : invoiceDAO.retrieveLines(invoice))
		{
			//
			// generate reversal C_Invoice_Line_Alloc records (the reversal MatchInv records are created by InvoiceBL)
			final I_C_InvoiceLine reversalLine = invoiceDAO.retrieveReversalLine(il, reversalInvoiceId);
			Check.assumeNotNull(reversalLine, "C_InvoiceLine {} is expected to have a reversal line, like every other line of C_Invoice {}", il, invoice);

			for (final I_C_Invoice_Line_Alloc ilaToReverse : invoiceCandDAO.retrieveIlaForIl(il))
			{
				final BigDecimal qtyInvoicedForIla;
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

				if (creditMemo && creditedInvoiceReinvoicable && !creditedInvoiceIsReversed)
				{
					// undo/reverse the full credit memo quantity. Note that when we handled the credit memo's completion we didn't care about any overlap either, but also created an ila with the full
					// credit memo's qty.
					qtyInvoicedForIla = il.getQtyInvoiced().multiply(factor).negate();
					note = "@C_InvoiceLine@  @QtyInvoiced@ = " + il.getQtyInvoiced() + " @IsCreditedInvoiceReinvoicable@='Y'; ignoring overlap, because credit memo";
				}
				else if (creditMemo && !creditedInvoiceReinvoicable)
				{
					// the original credit memo's ila also has QtyInvoiced=0
					qtyInvoicedForIla = BigDecimal.ZERO;
					note = "@C_InvoiceLine@  @QtyInvoiced@ = " + il.getQtyInvoiced() + " @IsCreditedInvoiceReinvoicable@='N'";
				}
				else
				{
					// default behavior: subtract something, but not more than what was originally invoiced.
					final BigDecimal reversalQtyInvoiced = reversalLine.getQtyInvoiced().multiply(factor);

					// task 08927: it could be that il's original qtyInvoiced was already subtracted (maybe partially)
					// we only want to subtract the qty that was not yet subtracted
					final IPair<BigDecimal, BigDecimal> qtyInvoicedAndNetAmtInvoiced = sumupQtyInvoicedAndNetAmtInvoiced(invoiceCandidate);
					final BigDecimal qtyInvoicedForIc = qtyInvoicedAndNetAmtInvoiced.getLeft();

					// examples:
					// reversalQtyInvoiced = -5, qtyInvoicedForIc = 3 (because of partial reinvoicable credit memo with qty 2) => overlap=-2 => create Ila with qty -5-(-2)=-3
					// reversalQtyInvoiced = -5, qtyInvoicedForIc = 11 (maybe because of some fuckup, idk) => overlap=6 => create Ila with qty -5-(+6)=-11
					// reversalQtyInvoiced = -5, qtyInvoicedForIc = -10 (maybe because of some fuckup, idk) => overlap=-15 => create ila with qty -5-(-15)=10

					// reversalQtyInvoiced = 5 (because we reverse a credit memo), qtyInvoicedForIc = 1 => overlap=2 => create ila with qty -5-(+2)=3
					final BigDecimal overlap = reversalQtyInvoiced.add(qtyInvoicedForIc);
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

		invoiceCandidate.setQtyToInvoice_OverrideFulfilled(BigDecimal.ZERO);

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

				final I_C_OrderLine ol = InterfaceWrapperHelper.create(il.getC_OrderLine(), I_C_OrderLine.class);
				final List<I_C_Invoice_Candidate> existingICs = invoiceCandDAO.fetchInvoiceCandidates(ctx, org.compiere.model.I_C_OrderLine.Table_Name, il.getC_OrderLine_ID(), trxName);
				if (existingICs.isEmpty())
				{
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
				final BigDecimal qtyInvoiced;
				final String note;
				if (isCreditMemo)
				{
					// task 08927
					note = "@C_DocType_ID@=" + invoice.getC_DocType().getName() + ", @IsCreditedInvoiceReinvoicable@=" + creditMemoReinvoicable;
					if (creditMemoReinvoicable)
					{
						qtyInvoiced = il.getQtyInvoiced().negate(); // this will allow the user to re-invoice, just as if the credit memo was a reversal
					}
					else
					{
						qtyInvoiced = BigDecimal.ZERO;
					}
				}
				else
				{
					note = "";
					qtyInvoiced = il.getQtyInvoiced(); // the standard case
				}
				createUpdateIla(icToLink, il, qtyInvoiced, note);
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
			processed = X_C_Invoice_Candidate.PROCESSED_OVERRIDE_Yes.equals(ic.getProcessed_Override());
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
			final IDocumentBL docActionBL = Services.get(IDocumentBL.class);

			//
			// if qtyInvoiced is >= qtyOrdered, then there is no further Qty to be invoiced
			final BigDecimal qtyOrdered = getQtyOrdered(ic);
			final boolean noOpenQty = qtyOrdered.abs().compareTo(ic.getQtyInvoiced().abs()) <= 0;

			//
			// we need to know if there are already any invoice lines which have not been reversed
			final List<I_C_Invoice_Line_Alloc> ilasForIc = invoiceCandDAO.retrieveIlaForIc(ic);
			int nonReversedIlas = 0;
			for (final I_C_Invoice_Line_Alloc ila : ilasForIc)
			{
				if (!docActionBL.isStatusStrOneOf(ila.getDocStatus(),
						X_C_Invoice_Line_Alloc.DOCSTATUS_Reversed))
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
			ic.setQtyToInvoiceInPriceUOM(BigDecimal.ZERO);
			ic.setQtyToInvoice(BigDecimal.ZERO);
		}
	}

	@Override
	public void resetError(final I_C_Invoice_Candidate ic)
	{
		ic.setIsError(false);
		ic.setAD_Note(null);
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
		else if (e instanceof org.adempiere.pricing.exceptions.ProductNotOnPriceListException)
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
		ic.setAD_Note(note);

		//
		// If we are running in batch update process, append the error message to SchedulerResult just to not lose it in case more issues are coming
		if (isUpdateProcessInProgress())
		{
			amendSchedulerResult(ic, errorMessageToUse);
		}
	}

	@Override
	public void updateQtyWithIssues(final I_C_Invoice_Candidate ic)
	{
		Check.assumeNotNull(ic, "ic not null");

		//
		// Calculate Qty and Quality values from linked InOutLines
		final IQtyAndQuality qtys = calculateQtysInOutLines(ic);

		// Update QtyWithIssues
		final BigDecimal qtyWithIssues = qtys.getQtyWithIssuesExact();
		ic.setQtyWithIssues(qtyWithIssues);

		//
		// Update QualityDiscountPercent
		final BigDecimal qualityDiscountPercentOld = ic.getQualityDiscountPercent();

		// check if QualityDiscountPercent from the inout lines equals the effective quality-percent which we currently have
		final BigDecimal qualityDiscountPercentNew = qtys.getQualityDiscountPercent();
		final boolean isQualityDiscountPercentChanged = qualityDiscountPercentOld.compareTo(qualityDiscountPercentNew) != 0;

		//
		// if QualityDiscountPercent ever changes (and is bigger than 0), then isInDispute is true
		// Note: the code originally related to task 06502 has partially been moved to de.metas.invoicecandidate.modelvalidator.M_InoutLine.
		//
		// Mark: "pls don't change that functionality, is important for purchase ppl"
		if (!isQualityDiscountPercentChanged)
		{
			return; // nothing more to do
		}

		//
		// so there was a change in th underlying inouts' qtysWithIssues => check if we need to set the InDispute-Flag back to true

		// update QualityDiscountPercent from the inout lines
		ic.setQualityDiscountPercent(qualityDiscountPercentNew);

		// reset the qualityDiscountPercent_Override value, because it needs to be negotiated anew
		ic.setQualityDiscountPercent_Override(null);

		if (qualityDiscountPercentNew.signum() > 0)
		{
			// the inOuts' indisputeqty changed and we (now) have effective qualityDiscountPercent > 0
			// set the IC to IsInDispute = true to make sure the qtywithissue-chage is dealt with
			ic.setIsInDispute(true);
		}

		// 07847
		// The Quality DIscount Percent Override shall only be zero when the user sets it this way
		// Make it null otherwise
		// 08634: If the discount percent override is higher than 0, do not change it
	}

	// package-visible for testing
	/* package */BigDecimal getQtyDelivered_Effective(final I_C_Invoice_Candidate ic)
	{

		final BigDecimal factor;
		if (ic.getQtyOrdered().signum() < 0)
		{
			factor = BigDecimal.ONE.negate();
		}
		else
		{
			factor = BigDecimal.ONE;
		}

		return ic.getQtyDelivered().subtract((ic.getQtyWithIssues_Effective()).multiply(factor));
	}

	/* package */void updateQtyWithIssues_Effective(final I_C_Invoice_Candidate ic)
	{
		final BigDecimal qualityDiscountPercent_Override = InterfaceWrapperHelper.getValueOrNull(ic, I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override);
		final BigDecimal qtyWithIssuesToUse;

		// Case: User did not explicitly set a QualityDiscountPercent_Override
		if (qualityDiscountPercent_Override == null)
		{
			// we use the qtyWithIssues value that was accumulated from our inoutLine
			qtyWithIssuesToUse = ic.getQtyWithIssues();
		}
		// Case: User explicitly set a QualityDiscountPercent_Override
		else
		{
			// don't use qtyWithIssues, but calculate QtyWithIssues_Override based on user-specified QualityDiscountPercent_Override
			// task 08507: Note that the base for this calculation is the whole delivered Qty (including the qtys with issues); it was QtyToInvoiceBeforeDiscount with was a moving target with every
			// invoice rule change an in particular with every invoicing.
			final IQtyAndQuality qtyInOutLines = calculateQtysInOutLines(ic);
			final BigDecimal qtyDelivered = qtyInOutLines.getQtyTotal();

			final MutableQtyAndQuality qtysOverride = new MutableQtyAndQuality();
			qtysOverride.addQtyAndQualityDiscountPercent(qtyDelivered, qualityDiscountPercent_Override);
			qtyWithIssuesToUse = qtysOverride.getQtyWithIssuesExact();
		}

		ic.setQtyWithIssues_Effective(qtyWithIssuesToUse);
	}

	private IQtyAndQuality calculateQtysInOutLines(final I_C_Invoice_Candidate ic)
	{
		// services
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final IInOutCandidateBL inOutCandidateBL = Services.get(IInOutCandidateBL.class);

		final MutableQtyAndQuality qtys = new MutableQtyAndQuality();
		final List<I_C_InvoiceCandidate_InOutLine> iciols = invoiceCandDAO.retrieveICIOLAssociationsExclRE(ic);
		for (final I_C_InvoiceCandidate_InOutLine iciol : iciols)
		{
			final org.compiere.model.I_M_InOutLine inoutLine = iciol.getM_InOutLine();
			final IQtyAndQuality inoutLineQtys = inOutCandidateBL.getQtyAndQuality(inoutLine);
			// TODO: handle UOM conversions

			qtys.add(inoutLineQtys);
		}

		return qtys;
	}

	@Override
	public I_C_Tax getTaxEffective(final I_C_Invoice_Candidate candidate)
	{
		final I_C_Tax taxOverride = candidate.getC_Tax_Override();

		if (taxOverride != null)
		{
			return taxOverride;
		}
		return candidate.getC_Tax();
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
	/* package */BigDecimal calculateMaxInvoicableQtyFromOrderedAndDelivered(final BigDecimal qtyOrdered, final BigDecimal qtyDelivered)
	{
		final BigDecimal maxInvoicableQty;
		final boolean negativeQtyOrdered = qtyOrdered.signum() <= 0;

		//
		// Case: we deal with a negative quantity ordered (e.g. returns, "reversals", manual invoice candidates with negative Qty)
		if (negativeQtyOrdered)
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
	public BigDecimal getQualityDiscountPercentEffective(final I_C_Invoice_Candidate candidate)
	{
		final BigDecimal qualitDiscountPercentOverride = InterfaceWrapperHelper.getValueOrNull(candidate, I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override);

		if (qualitDiscountPercentOverride != null)
		{
			return qualitDiscountPercentOverride;
		}

		return candidate.getQualityDiscountPercent();
	}

	@Override
	public int getCurrencyPrecision(final I_C_Invoice_Candidate candidate)
	{
		Check.assumeNotNull(candidate, "candidate not null");
		final I_C_Currency currency = candidate.getC_Currency();
		if (currency != null)
		{
			return currency.getStdPrecision();
		}

		// Fallback:
		return 2;
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

	private boolean isInvoiceRuleForce(final I_C_Invoice_Candidate ic)
	{
		final BigDecimal qtyToInvoiceOverride = getQtyToInvoice_OverrideOrNull(ic);
		return qtyToInvoiceOverride != null /* && qtyToInvoiceOverride.signum() != 0 */;
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

	BigDecimal computeQtyToInvoice(
			final Properties ctx,
			final I_C_Invoice_Candidate ic,
			final BigDecimal factor,
			final boolean useEffectiveQtyDeliviered)
	{
		final BigDecimal newQtyToInvoice;

		final String invoiceRule = getInvoiceRule(ic);

		final I_C_Order order = InterfaceWrapperHelper.create(ic.getC_Order(), I_C_Order.class);

		if (null != order && !(X_C_Order.DOCSTATUS_Completed.equals(order.getDocStatus()) || X_C_Order.DOCSTATUS_Closed.equals(order.getDocStatus())))
		{
			newQtyToInvoice = BigDecimal.ZERO;
		}
		else
		{
			final BigDecimal qtyDeliveredToUse = useEffectiveQtyDeliviered ? getQtyDelivered_Effective(ic) : ic.getQtyDelivered();

			if (isInvoiceRuleForce(ic))
			{
				final BigDecimal qtyToInvoiceOverride = getQtyToInvoice_OverrideOrNull(ic);
				Check.assumeNotNull(qtyToInvoiceOverride, "qtyToInvoiceOverride not null");
				newQtyToInvoice = qtyToInvoiceOverride;
			}
			else if (X_C_Invoice_Candidate.INVOICERULE_NachLieferung.equals(invoiceRule)
					|| X_C_Invoice_Candidate.INVOICERULE_KundenintervallNachLieferung.equals(invoiceRule))
			{
				// after Delivery

				// 04972: as of this task QtyDelivered also includes Qtys that were already invoiced, so we have to subtract the QtyInvoiced.
				// 08475: do not subtract the qtyInvoiced because it will be subtracted again in getQtyToInvoice()
				final BigDecimal maxInvoicableQty = qtyDeliveredToUse;// .subtract(ic.getQtyInvoiced());
				newQtyToInvoice = getQtyToInvoice(ic, maxInvoicableQty, factor);
			}
			else if (X_C_Invoice_Candidate.INVOICERULE_Sofort.equals(invoiceRule))                                // Immediate
			{
				newQtyToInvoice = computeQtyToInvoiceWhenRuleImmediate(ic, factor);
			}
			else if (X_C_Invoice_Candidate.INVOICERULE_NachLieferungAuftrag.equals(invoiceRule))
			{
				// AfterOrderDelivered
				if (isOrderFullyDelivered(ic))
				{
					newQtyToInvoice = getQtyToInvoice(ic, qtyDeliveredToUse, factor);
				}
				else
				{
					amendSchedulerResult(ic, Services.get(IMsgBL.class).getMsg(ctx, "InvoiceCandBL_Status_OrderComplete"));
					newQtyToInvoice = BigDecimal.ZERO;
				}
			}
			else
			{
				final String msg = "@NotFound@ @InvoiceRule@ "
						+ Services.get(IADReferenceDAO.class).retrieveListNameTrl(ctx, X_C_Invoice_Candidate.INVOICERULE_AD_Reference_ID, invoiceRule)
						+ " (" + invoiceRule + ")";
				amendSchedulerResult(ic, msg);
				newQtyToInvoice = BigDecimal.ZERO;
			}
		}
		return newQtyToInvoice;
	}

	@Override
	public BigDecimal computeOpenQty(@NonNull final I_C_Invoice_Candidate ic)
	{
		final BigDecimal factor;
		if (ic.getQtyOrdered().signum() < 0)
		{
			factor = BigDecimal.ONE.negate();
		}
		else
		{
			factor = BigDecimal.ONE;
		}
		return computeQtyToInvoiceWhenRuleImmediate(ic, factor);
	}

	private BigDecimal computeQtyToInvoiceWhenRuleImmediate(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final BigDecimal factor)
	{
		// 07847
		// Use the maximum between qtyOrdered and qtyDelivered
		final BigDecimal maxInvoicableQty = calculateMaxInvoicableQtyFromOrderedAndDelivered(ic.getQtyOrdered(), getQtyDelivered_Effective(ic));
		return getQtyToInvoice(ic, maxInvoicableQty, factor);
	}

	@Override
	public void setQualityDiscountPercent_Override(final I_C_Invoice_Candidate ic, final List<I_M_AttributeInstance> instances)
	{
		final IMDiscountSchemaDAO discountSchemaDAO = Services.get(IMDiscountSchemaDAO.class);
		final IMDiscountSchemaBL discountSchemaBL = Services.get(IMDiscountSchemaBL.class);
		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

		final I_C_BPartner partner = ic.getBill_BPartner();

		final I_M_DiscountSchema discountSchema = bPartnerDAO.retrieveDiscountSchemaOrNull(partner, ic.isSOTrx());
		if (discountSchema == null)
		{
			// do nothing
			return;
		}

		final I_M_Product product = ic.getM_Product();

		final List<org.compiere.model.I_M_DiscountSchemaBreak> breaks = discountSchemaDAO.retrieveBreaks(discountSchema);
		final boolean isQtyBased = discountSchema.isQuantityBased();
		final int productID = product.getM_Product_ID();
		final int categoryID = product.getM_Product_Category_ID();
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
		final BigDecimal amt = ic.getPriceActual().multiply(qty);

		final org.compiere.model.I_M_DiscountSchemaBreak appliedBreak = discountSchemaBL.pickApplyingBreak(
				breaks,
				instances,
				isQtyBased,
				productID,
				categoryID,
				qty,
				amt);

		final BigDecimal qualityDiscountPercentage;

		if (appliedBreak == null)
		{
			qualityDiscountPercentage = null;
		}
		else
		{
			final I_M_DiscountSchemaBreak discountSchemaBreak = InterfaceWrapperHelper.create(appliedBreak, I_M_DiscountSchemaBreak.class);
			qualityDiscountPercentage = discountSchemaBreak.getQualityIssuePercentage();
		}

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
				final de.metas.invoicecandidate.model.I_C_Invoice_Candidate candModel = InterfaceWrapperHelper.create(candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);

				if (candModel == null)
				{
					// shall not happen
					continue;
				}

				final String processedOverride = candModel.getProcessed_Override();

				if (processedOverride == null)
				{
					// nothing to do
					continue;
				}

				if (processedOverride.equals("Y"))
				{
					candModel.setProcessed_Override(null);
					InterfaceWrapperHelper.save(candModel);
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

}
