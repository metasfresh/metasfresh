package de.metas.banking.payment.paymentallocation.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import de.metas.util.Check;
import de.metas.util.IProcessor;

/**
 * Context of payment allocation, used by DAO methods and calculation methods.
 *
 * @author tsa
 *
 */
public final class PaymentAllocationContext
{
	private final int AD_Org_ID;
	private final int C_BPartner_ID;
	private final int C_Currency_ID;
	private final boolean multiCurrency;
	private final Date date;
	private final int filterPaymentId;
	private final String filterPOReference;
	private final Multimap<AllocableDocType, Integer> documentIds;

	//
	private final ImmutableSet<InvoiceWriteOffAmountType> allowedWriteOffTypes;
	//
	private static final BigDecimal DEFAULT_WriteOffExceedOpenAmtTolerance = new BigDecimal(0.3);
	private final BigDecimal writeOffExceedOpenAmtTolerance;

	private final IProcessor<Exception> warningsConsumer;

	private final boolean allowPurchaseSalesInvoiceCompensation;

	public static Builder builder()
	{
		return new Builder();
	}

	private PaymentAllocationContext(final Builder builder)
	{
		AD_Org_ID = builder.AD_Org_ID;
		C_BPartner_ID = builder.C_BPartner_ID;
		C_Currency_ID = builder.C_Currency_ID;
		multiCurrency = builder.multiCurrency;
		date = builder.date;
		documentIds = ImmutableMultimap.copyOf(builder.documentIds);
		filterPaymentId = builder.filterPaymentId;
		filterPOReference = builder.filterPOReference;
		//
		allowedWriteOffTypes = ImmutableSet.copyOf(builder.allowedWriteOffTypes);
		//
		writeOffExceedOpenAmtTolerance = DEFAULT_WriteOffExceedOpenAmtTolerance;
		//
		warningsConsumer = builder.warningsConsumer;

		allowPurchaseSalesInvoiceCompensation = builder.allowPurchaseSalesInvoiceCompensation;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public Properties getCtx()
	{
		return Env.getCtx();
	}

	public int getAD_Client_ID()
	{
		return Env.getAD_Client_ID(getCtx());
	}

	public int getAD_Org_ID()
	{
		return AD_Org_ID;
	}

	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}

	public int getC_Currency_ID()
	{
		return C_Currency_ID;
	}

	public Boolean isMultiCurrency()
	{
		return multiCurrency;
	}

	public Date getDate()
	{
		return date;
	}

	public Collection<Integer> getDocumentIdsToIncludeWhenQuering(final AllocableDocType docType)
	{
		return documentIds.get(docType);
	}

	public int getFilter_Payment_ID()
	{
		return filterPaymentId;
	}

	public String getFilterPOReference()
	{
		return filterPOReference;
	}

	/**
	 * @return allowed write-off types, in the same order they were enabled
	 */
	public Set<InvoiceWriteOffAmountType> getAllowedWriteOffTypes()
	{
		return allowedWriteOffTypes;
	}

	public Set<InvoiceWriteOffAmountType> getNotAllowedWriteOffTypes()
	{
		if (allowedWriteOffTypes.isEmpty())
		{
			return EnumSet.allOf(InvoiceWriteOffAmountType.class);
		}
		return EnumSet.complementOf(EnumSet.copyOf(allowedWriteOffTypes));
	}

	public InvoiceWriteOffAmountType getFirstAllowedWriteOffTypeOrNull()
	{
		if (allowedWriteOffTypes.isEmpty())
		{
			return null;
		}
		return allowedWriteOffTypes.iterator().next();
	}

	public boolean isAllowedWriteOffType(final InvoiceWriteOffAmountType type)
	{
		return allowedWriteOffTypes.contains(type);
	}

	/**
	 * @return true if at least one write-off amount is allowed
	 * @see #getAllowedWriteOffTypes()
	 */
	public boolean isAllowAnyWriteOffAmount()
	{
		return !allowedWriteOffTypes.isEmpty();
	}

	/**
	 * @return
	 *         <ul>
	 *         <li>how much from an invoice open amount are we allowed to write-off (percentage between 0...1).
	 *         <li>zero if the tolerance checking is disabled
	 *         </ul>
	 */
	public BigDecimal getWriteOffExceedOpenAmtTolerance()
	{
		return writeOffExceedOpenAmtTolerance;
	}

	public void addWarning(final Exception e)
	{
		if (e == null)
		{
			return;
		}
		if (warningsConsumer == null)
		{
			return;
		}

		warningsConsumer.process(e);
	}

	/**
	 * @return true if compensating customer invoices with purchase invoices shall be allowed
	 * @task 09451
	 */
	public boolean isAllowPurchaseSalesInvoiceCompensation()
	{
		return allowPurchaseSalesInvoiceCompensation;
	}

	//
	//
	// ------------------------------------------------------------------------------------------------------------------------
	//
	//
	public static final class Builder
	{
		private int AD_Org_ID;
		private int C_BPartner_ID;
		private int C_Currency_ID;
		private Boolean multiCurrency;
		private Date date;
		//
		// NOTE: order is important because some BL wants to apply the types in the same order they were enabled!
		private final LinkedHashSet<InvoiceWriteOffAmountType> allowedWriteOffTypes = new LinkedHashSet<>(); // NOTE: order is important

		private IProcessor<Exception> warningsConsumer = null;
		private Multimap<AllocableDocType, Integer> documentIds;
		private int filterPaymentId = 0;
		private String filterPOReference;
		private boolean allowPurchaseSalesInvoiceCompensation;

		private Builder()
		{
			super();
		}

		public PaymentAllocationContext build()
		{
			return new PaymentAllocationContext(this);
		}

		public Builder setAD_Org_ID(final int AD_Org_ID)
		{
			this.AD_Org_ID = AD_Org_ID;
			return this;
		}

		public Builder setC_BPartner_ID(final int bpartnerId)
		{
			C_BPartner_ID = bpartnerId;
			return this;
		}

		public Builder setC_Currency_ID(final int C_Currency_ID)
		{
			this.C_Currency_ID = C_Currency_ID;
			return this;
		}

		public Builder setMultiCurrency(final boolean multiCurrency)
		{
			this.multiCurrency = multiCurrency;
			return this;
		}

		public Builder setDate(final Date date)
		{
			this.date = date;
			return this;
		}

		/**
		 * @param allowedWriteOffTypes allowed write-off types, in the same order they were enabled
		 */
		public Builder setAllowedWriteOffTypes(final Set<InvoiceWriteOffAmountType> allowedWriteOffTypes)
		{
			this.allowedWriteOffTypes.clear();
			if (allowedWriteOffTypes != null)
			{
				this.allowedWriteOffTypes.addAll(allowedWriteOffTypes);
			}
			return this;
		}

		public Builder addAllowedWriteOffType(final InvoiceWriteOffAmountType allowedWriteOffType)
		{
			Check.assumeNotNull(allowedWriteOffType, "allowedWriteOffType not null");
			allowedWriteOffTypes.add(allowedWriteOffType);
			return this;
		}

		public Builder setWarningsConsumer(final IProcessor<Exception> warningsConsumer)
		{
			this.warningsConsumer = warningsConsumer;
			return this;
		}

		public Builder setDocumentIdsToIncludeWhenQuering(final Multimap<AllocableDocType, Integer> documentIds)
		{
			this.documentIds = documentIds;
			return this;
		}

		public Builder setFilter_Payment_ID(int filter_Payment_ID)
		{
			this.filterPaymentId = filter_Payment_ID;
			return this;
		}

		public Builder setFilter_POReference(String filterPOReference)
		{
			this.filterPOReference = filterPOReference;
			return this;
		}

		public Builder allowPurchaseSalesInvoiceCompensation(final boolean allowPurchaseSalesInvoiceCompensation)
		{
			this.allowPurchaseSalesInvoiceCompensation = allowPurchaseSalesInvoiceCompensation;
			return this;
		}
	}
}
