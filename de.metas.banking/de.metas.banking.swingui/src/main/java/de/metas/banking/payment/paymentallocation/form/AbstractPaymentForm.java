package de.metas.banking.payment.paymentallocation.form;

/*
 * #%L
 * de.metas.banking.swingui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.swing.JLabel;

import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Services;
import org.compiere.apps.form.FormFrame;
import org.compiere.model.GridField;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.Sets;

import de.metas.banking.payment.paymentallocation.model.PaymentAllocationTotals;
import de.metas.logging.LogManager;
import net.miginfocom.swing.MigLayout;

/* package */abstract class AbstractPaymentForm
{
	/** Logger */
	protected final transient Logger logger = LogManager.getLogger(getClass());

	protected final DecimalFormat decimalFormat = DisplayType.getNumberFormat(DisplayType.Amount);

	private int _windowNo;
	private int _adOrgId = 0;
	private int _bpartnerId = 0;
	private int _currencyId = 0;
	private Timestamp _date;

	private String _paymentReference = null;

	/** FormFrame */
	protected FormFrame m_frame;

	// allocationPanel
	protected final CPanel allocationPanel = new CPanel();
	/**
	 * Allocation panel layout remains "MigLayout" because the allocate Button is supposed to be displayed in the middle. VPanel cannot cover this.
	 */
	protected final MigLayout allocationLayout = new MigLayout();
	protected final JLabel differenceLabel = new JLabel();
	protected final CTextField differenceField = new CTextField();
	protected final JLabel paymentSumLabel = new JLabel();
	protected final CTextField paymentSumField = new CTextField();
	protected final JLabel paymentCandidateSumLabel = new JLabel();
	protected final CTextField paymentCandidateSumField = new CTextField(); // 07500 Taken after "Summe Zahlschein" is read from the payment form
	protected final JLabel invoiceSumLabel = new JLabel();
	protected final CTextField invoiceSumField = new CTextField();

	// parameterPanel
	protected GridField bpartnerField = null;
	protected GridField dateField = null;
	protected GridField organisationField = null;
	protected GridField currencyField = null;

	protected final Properties getCtx()
	{
		return Env.getCtx();
	}

	protected final int getWindowNo()
	{
		return this._windowNo;
	}

	protected final void setWindowNo(final int windowNo)
	{
		this._windowNo = windowNo;
	}

	/**
	 * Load Business Partner Info - Payments - Invoices
	 */
	protected final void checkBPartner()
	{
		final int bpartnerId = getC_BPartner_ID();
		final int currencyId = getC_Currency_ID();
		logger.info("C_BPartner_ID={}, C_Currency_ID=", new Object[] { bpartnerId, currencyId });
		// Need to have both values
		if (bpartnerId <= 0 || currencyId <= 0)
		{
			return;
		}

		// Async BPartner Test
		if (bpartnerId > 0 && _bpartnerCheck.add(bpartnerId))
		{
			final Properties ctx = getCtx();
			Services.get(ITaskExecutorService.class).submit(new Runnable()
			{
				@Override
				public void run()
				{
					MPayment.setIsAllocated(ctx, bpartnerId, ITrx.TRXNAME_None);
					MInvoice.setIsPaid(ctx, bpartnerId, ITrx.TRXNAME_None);
					_bpartnerCheck.remove(bpartnerId);
				}
			},
					this.getClass().getSimpleName());
		}
	}

	/** Set of C_BPartner_IDs which are currently checked by {@link #checkBPartner()} */
	private final Set<Integer> _bpartnerCheck = Sets.newConcurrentHashSet();

	protected final void setAD_Org_ID(final int value)
	{
		if (this._adOrgId == value)
		{
			return;
		}
		this._adOrgId = value;
		setIntValueOrNull(organisationField, value);
	}

	protected final int getAD_Org_ID()
	{
		return _adOrgId;
	}

	protected final void setC_Currency_ID(final int value)
	{
		if (this._currencyId == value)
		{
			return;
		}
		this._currencyId = value;
		setIntValueOrNull(currencyField, value);
	}

	protected final int getC_Currency_ID()
	{
		return _currencyId;
	}

	/**
	 * @param bpartnerId
	 * @return true if value was changed, false otherwise
	 */
	@OverridingMethodsMustInvokeSuper
	protected boolean setC_BPartner_ID(final int bpartnerId)
	{
		setIntValueOrNull(bpartnerField, bpartnerId);
		if (this._bpartnerId == bpartnerId)
		{
			return false;
		}
		this._bpartnerId = bpartnerId;

		return true;
	}

	protected final int getC_BPartner_ID()
	{
		return _bpartnerId;
	}

	protected final void setDate(final Timestamp value)
	{
		if (Objects.equals(this._date, value))
		{
			return;
		}
		this._date = value;
		dateField.setValue(value, false); // inserting=false
	}

	protected final Timestamp getDate()
	{
		return _date;
	}

	private static final void setIntValueOrNull(final GridField field, final int value)
	{
		final boolean inserting = false;
		if (value > 0)
		{
			field.setValue(value, inserting);
		}
		else
		{
			//
			// ID not set; don't use it
			field.setValue(null, inserting);
		}
	}

	/**
	 * Updates Totals (Invoiced, Paid etc) and their difference
	 */
	protected final void updateTotals()
	{
		final PaymentAllocationTotals totals = getTotals();
		final BigDecimal totalInv = totals.getInvoicedAmt();
		final BigDecimal totalPay = totals.getPaymentExistingAmt();
		final BigDecimal totalPayCand = totals.getPaymentCandidatesAmt();
		final BigDecimal totalDiffPayMinusInvoice = totals.getDiffInvoiceMinusPay().negate();

		// Set sum and difference fields
		paymentCandidateSumField.setText(decimalFormat.format(totalPayCand));
		paymentSumField.setText(decimalFormat.format(totalPay));
		invoiceSumField.setText(decimalFormat.format(totalInv));
		differenceField.setText(decimalFormat.format(totalDiffPayMinusInvoice));

		// Set AllocationDate
		final Date allocDate = getAllocDate();
		dateField.setValue(allocDate, false);

		// Set Difference Field color
		if (totalDiffPayMinusInvoice.signum() == 0)
		{
			differenceField.setForeground(AdempierePLAF.getTextColor_Normal());
		}
		else
		{
			differenceField.setForeground(AdempierePLAF.getTextColor_Issue());
		}
	}

	protected final void setPaymentReference(final String paymentReference)
	{
		this._paymentReference = paymentReference;
	}

	protected final String getPaymentReference()
	{
		return this._paymentReference;
	}

	/**
	 * @return allocation total amounts
	 */
	protected abstract PaymentAllocationTotals getTotals();

	/** @return suggested allocation date */
	protected final Date getAllocDate()
	{
		final Date allocationDate = calculateAllocationDate();
		if (allocationDate != null)
		{
			return allocationDate;
		}

		// If no allocation date, use the login date
		return Env.getDate(getCtx());
	}

	/**
	 * task 09643: This date will be used, from now on, only as transaction date
	 * 
	 * @return suggested accounting date
	 */
	protected final Date getDateAcct()
	{
		final Date dateAcct = calculateDateAcct();
		if (dateAcct != null)
		{
			return dateAcct;
		}

		// If no allocation date, use the login date
		return Env.getDate(getCtx());
	}

	/** @return suggested allocation date or <code>null</code> */
	protected abstract Date calculateAllocationDate();

	/**
	 * task 09643: separate accounting date from the transaction date
	 * 
	 * @return suggested accounting date or <code>null</code>
	 */
	protected abstract Date calculateDateAcct();
}
