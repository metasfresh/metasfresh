package de.metas.invoicecandidate.api.impl;

import java.util.Optional;

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

import java.util.Set;

import javax.annotation.Nullable;

import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.ArrayKeyBuilder;
import org.compiere.util.Evaluatee2;
import org.compiere.util.Util.ArrayKey;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.inout.invoicecandidate.M_InOutLine_Handler;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.Builder;
import lombok.NonNull;

/**
 * Evaluation context used to parse the header and line aggregation key right before aggregating to invoice or invoice lines
 * 
 * @author tsa
 */
public class AggregationKeyEvaluationContext implements Evaluatee2
{
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
	private final ImmutableSet<IInvoiceLineAttribute> invoiceLineAttributes;

	//
	private Optional<I_M_InOutLine> _inoutLine; // lazy
	private Optional<BPartnerLocationId> _shipReceiptBPLocationId = null; // lazy
	private ArrayKey _invoiceLineAttributesKey; // lazy

	@Builder
	private AggregationKeyEvaluationContext(
			@NonNull final I_C_Invoice_Candidate invoiceCandidate,
			@Nullable final I_M_InOutLine inoutLine,
			@Nullable final Set<IInvoiceLineAttribute> invoiceLineAttributes)
	{
		this.invoiceCandidate = invoiceCandidate;
		this._inoutLine = inoutLine != null ? Optional.of(inoutLine) : null;
		this.invoiceLineAttributes = invoiceLineAttributes != null
				? ImmutableSet.copyOf(invoiceLineAttributes)
				: ImmutableSet.of();
	}

	@Override
	public String get_ValueAsString(final String variableName)
	{
		if (ATTRIBUTE_CODE_AggregatePer_M_InOut_ID.equals(variableName))
		{
			return getInOutLine()
					.map(I_M_InOutLine::getM_InOut_ID)
					.map(String::valueOf)
					.orElse("-");
		}
		else if (ATTRIBUTE_CODE_AggregatePer_InOut_BPLocation_ID.equals(variableName))
		{
			return getShipReceiptBPLocationId()
					.map(BPartnerLocationId::getRepoId)
					.map(String::valueOf)
					.orElse("-1");
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

	private final Optional<I_M_InOutLine> getInOutLine()
	{
		if (_inoutLine == null)
		{
			_inoutLine = Optional.ofNullable(M_InOutLine_Handler.getM_InOutLineOrNull(invoiceCandidate));
		}
		return _inoutLine;
	}

	private final Optional<BPartnerLocationId> getShipReceiptBPLocationId()
	{
		Optional<BPartnerLocationId> shipReceiptBPLocationId = this._shipReceiptBPLocationId;
		if (shipReceiptBPLocationId == null)
		{
			shipReceiptBPLocationId = this._shipReceiptBPLocationId = computeShipReceiptBPLocationId();
		}
		return shipReceiptBPLocationId;
	}

	private final Optional<BPartnerLocationId> computeShipReceiptBPLocationId()
	{
		final I_M_InOutLine inoutLine = getInOutLine().orElse(null);
		if (inoutLine != null)
		{
			final I_M_InOut inout = inoutLine.getM_InOut();
			return Optional.of(BPartnerLocationId.ofRepoId(inout.getC_BPartner_ID(), inout.getC_BPartner_Location_ID()));
		}

		final I_C_Order order = invoiceCandidate.getC_Order();
		if (order != null)
		{
			return Optional.of(BPartnerLocationId.ofRepoId(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID()));
		}

		return Optional.empty();
	}

	public ArrayKey getInvoiceLineAttributesKey()
	{
		ArrayKey invoiceLineAttributesKey = _invoiceLineAttributesKey;
		if (invoiceLineAttributesKey == null)
		{
			invoiceLineAttributesKey = _invoiceLineAttributesKey = computeInvoiceLineAttributesKey();
		}
		return invoiceLineAttributesKey;
	}

	private ArrayKey computeInvoiceLineAttributesKey()
	{
		final ArrayKeyBuilder keyBuilder = ArrayKey.builder();
		for (final IInvoiceLineAttribute invoiceLineAttribute : invoiceLineAttributes)
		{
			keyBuilder.append(invoiceLineAttribute.toAggregationKey());
		}

		return keyBuilder.build();
	}
}
