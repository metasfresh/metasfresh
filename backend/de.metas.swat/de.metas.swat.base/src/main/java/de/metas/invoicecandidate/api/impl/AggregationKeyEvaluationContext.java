package de.metas.invoicecandidate.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Set;

import org.adempiere.util.Check;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.ArrayKeyBuilder;
import org.compiere.util.Evaluatee2;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;

import de.metas.inout.invoicecandidate.M_InOutLine_Handler;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Evaluation context used to parse the header and line aggregation key right before aggregating to invoice or invoice lines
 * 
 * @author tsa
 */
public class AggregationKeyEvaluationContext implements Evaluatee2
{
	public static final Builder builder()
	{
		return new Builder();
	}

	//
	// Supporterted aggregation attributes
	// NOTE: keep them in sync with the values from I_C_Aggregation_Attribute.Code
	@VisibleForTesting
	public static final String ATTRIBUTE_CODE_AggregatePer_M_InOut_ID = "M_InOut_ID";
	@VisibleForTesting
	public static final String ATTRIBUTE_CODE_AggregatePer_InOut_BPLocation_ID = "InOut_BPL_ID";
	@VisibleForTesting
	public static final String ATTRIBUTE_CODE_AggregatePer_ProductAttributes = "ProductAttributes";

	/** All supported attribute codes */
	private static final Set<String> ATTRIBUTE_CODES = ImmutableSet.<String> builder()
			.add(ATTRIBUTE_CODE_AggregatePer_M_InOut_ID)
			.add(ATTRIBUTE_CODE_AggregatePer_InOut_BPLocation_ID)
			.add(ATTRIBUTE_CODE_AggregatePer_ProductAttributes)
			.build();

	private final I_C_Invoice_Candidate invoiceCandidate;
	//
	private I_M_InOutLine inoutLine;
	private boolean inoutLineSet = false;
	private Integer shipReceiptBPLocationId = null;
	//
	private final Set<IInvoiceLineAttribute> invoiceLineAttributes;
	private ArrayKey invoiceLineAttributesKey;

	private AggregationKeyEvaluationContext(final Builder builder)
	{
		super();

		Check.assumeNotNull(builder, "builder not null");
		this.invoiceCandidate = builder.getC_Invoice_Candidate();
		this.inoutLine = builder.getM_InOutLine();
		this.invoiceLineAttributes = builder.getInvoiceLineAttributes();
	}

	@Override
	public String get_ValueAsString(final String variableName)
	{
		if (ATTRIBUTE_CODE_AggregatePer_M_InOut_ID.equals(variableName))
		{
			final int inoutId = inoutLine == null ? -1 : inoutLine.getM_InOut_ID();
			return inoutId <= 0 ? "-" : String.valueOf(inoutId);
		}
		else if (ATTRIBUTE_CODE_AggregatePer_InOut_BPLocation_ID.equals(variableName))
		{
			return String.valueOf(getShipReceipt_BPLocation_ID());
		}
		else if (ATTRIBUTE_CODE_AggregatePer_ProductAttributes.equals(variableName))
		{
			return getInvoiceLineAttributesKey().toString();
		}

		return null;
	}

	@Override
	public boolean has_Variable(final String variableName)
	{
		return ATTRIBUTE_CODES.contains(variableName);
	}

	@Override
	public String get_ValueOldAsString(final String variableName)
	{
		return null;
	}

	/**
	 * Gets current shipment/receipt line. It evaluates in following order:
	 * <ul>
	 * <li>current shipment/receipt line if any
	 * <li>IC's shipment/receipt line, if the IC was created from a shipment/receipt line
	 * </ul>
	 * 
	 * @return current shipment/receipt line or <code>null</code>
	 */
	private final I_M_InOutLine getM_InOutLine()
	{
		if (inoutLine != null || inoutLineSet)
		{
			return inoutLine;
		}

		inoutLine = M_InOutLine_Handler.getM_InOutLine(invoiceCandidate);
		inoutLineSet = true;
		return inoutLine;
	}

	/**
	 * Gets actual shipment/receipt C_BPartner_Location_ID. It evaluates:
	 * <ul>
	 * <li>current shipment/receipt line ({@link #getM_InOutLine()}) and takes the BP location from there
	 * <li>IC's order if any
	 * <li>returns <code>-1</code>
	 * </ul>
	 * 
	 * @return actual shipment/receipt C_BPartner_Location_ID or <code>-1</code>
	 */
	private final int getShipReceipt_BPLocation_ID()
	{
		if (shipReceiptBPLocationId != null)
		{
			return shipReceiptBPLocationId;
		}

		final I_M_InOutLine inoutLine = getM_InOutLine();
		if (inoutLine != null)
		{
			final I_M_InOut inout = inoutLine.getM_InOut();
			shipReceiptBPLocationId = inout.getC_BPartner_Location_ID();
			return shipReceiptBPLocationId;
		}

		final I_C_Order order = invoiceCandidate.getC_Order();
		if (order != null)
		{
			shipReceiptBPLocationId = order.getC_BPartner_Location_ID();
			return shipReceiptBPLocationId;
		}

		shipReceiptBPLocationId = -1;
		return -shipReceiptBPLocationId;
	}

	public ArrayKey getInvoiceLineAttributesKey()
	{
		if (invoiceLineAttributesKey == null)
		{
			final ArrayKeyBuilder keyBuilder = Util.mkKey();
			for (final IInvoiceLineAttribute invoiceLineAttribute : invoiceLineAttributes)
			{
				keyBuilder.append(invoiceLineAttribute.toAggregationKey());
			}

			invoiceLineAttributesKey = keyBuilder.build();
		}

		return invoiceLineAttributesKey;
	}

	public static class Builder
	{
		private I_C_Invoice_Candidate invoiceCandidate;
		private I_M_InOutLine inoutLine;
		private Set<IInvoiceLineAttribute> invoiceLineAttributes = ImmutableSet.of();

		private Builder()
		{
			super();
		}

		public AggregationKeyEvaluationContext build()
		{
			return new AggregationKeyEvaluationContext(this);
		}

		public Builder setC_Invoice_Candidate(final I_C_Invoice_Candidate invoiceCandidate)
		{
			this.invoiceCandidate = invoiceCandidate;
			return this;
		}

		private I_C_Invoice_Candidate getC_Invoice_Candidate()
		{
			Check.assumeNotNull(invoiceCandidate, "invoiceCandidate not null");
			return this.invoiceCandidate;
		}

		public Builder setM_InOutLine(final I_M_InOutLine inoutLine)
		{
			this.inoutLine = inoutLine;
			return this;
		}

		private I_M_InOutLine getM_InOutLine()
		{
			return inoutLine;
		}

		public Builder setInvoiceLineAttributes(final Set<IInvoiceLineAttribute> invoiceLineAttributes)
		{
			this.invoiceLineAttributes = ImmutableSet.copyOf(invoiceLineAttributes);
			return this;
		}

		private Set<IInvoiceLineAttribute> getInvoiceLineAttributes()
		{
			return this.invoiceLineAttributes;
		}

	}
}