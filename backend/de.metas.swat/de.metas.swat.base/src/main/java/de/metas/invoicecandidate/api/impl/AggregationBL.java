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

import com.google.common.collect.ImmutableList;
import de.metas.aggregation.api.AggregationId;
import de.metas.aggregation.api.AggregationKey;
import de.metas.aggregation.api.IAggregationKeyBuilder;
import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IAggregationDAO;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation;
import de.metas.invoicecandidate.spi.IAggregator;
import de.metas.invoicecandidate.spi.impl.aggregator.standard.DefaultAggregator;
import de.metas.util.Check;
import de.metas.util.IProcessor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Util;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class AggregationBL implements IAggregationBL
{
	private final IAggregationKeyBuilder<I_C_Invoice_Candidate> headerAggregationKeyBuilder = new HeaderAggregationKeyBuilder();
	private final IAggregationKeyBuilder<I_C_Invoice_Candidate> lineAggregationKeyBuilder = new LineAggregationKeyBuilder();

	private final IProcessor<I_C_Invoice_Candidate> invoiceCandidateUpdater = ic -> {
		if (ic.isToClear())
		{
			ic.setC_Invoice_Candidate_Agg(null);
			resetHeaderAggregationKey(ic);
			resetLineAggregationKey(ic);
		}
		else
		{
			//
			// find our aggregator and set it
			final IAggregationDAO aggregationDAO = Services.get(IAggregationDAO.class);
			final I_C_Invoice_Candidate_Agg agg = aggregationDAO.retrieveAggregate(ic);
			ic.setC_Invoice_Candidate_Agg(agg);
			setHeaderAggregationKey(ic);

			setLineAggregationKey(ic);
		}
	};

	private final IAttributeDAO attributesDAO = Services.get(IAttributeDAO.class);

	@Override
	public IAggregator createInvoiceLineAggregatorInstance(final I_C_Invoice_Candidate ic)
	{
		final IAggregator result;

		if (ic.getC_Invoice_Candidate_Agg_ID() <= 0)
		{
			result = new DefaultAggregator();
		}
		else
		{
			final String classname = ic.getC_Invoice_Candidate_Agg().getClassname();
			if (Check.isEmpty(classname))
			{
				result = new DefaultAggregator();
			}
			else
			{
				result = Util.getInstance(IAggregator.class, classname);
			}
		}
		result.setContext(InterfaceWrapperHelper.getCtx(ic), InterfaceWrapperHelper.getTrxName(ic));
		return result;
	}

	@Override
	public void evalClassName(final I_C_Invoice_Candidate_Agg agg)
	{
		if (Check.isEmpty(agg.getClassname()))
		{
			return;
		}

		Util.getInstance(IAggregator.class, agg.getClassname());
	}

	@Override
	public IInvoiceLineRW mkInvoiceLine()
	{
		return new InvoiceLineImpl();
	}

	@Override
	public IInvoiceLineRW mkInvoiceLine(@NonNull final IInvoiceLineRW template)
	{
		final IInvoiceLineRW result = mkInvoiceLine();

		result.setC_Activity_ID(template.getC_Activity_ID());
		result.setC_Charge_ID(template.getC_Charge_ID());
		result.setC_OrderLine_ID(template.getC_OrderLine_ID());
		result.setC_Tax(template.getC_Tax());
		result.setDescription(template.getDescription());
		result.setDiscount(template.getDiscount());
		result.setInvoiceLineAttributes(template.getInvoiceLineAttributes());
		result.setLineNo(template.getLineNo());
		result.setM_Product_ID(template.getM_Product_ID());
		result.setNetLineAmt(template.getNetLineAmt());
		result.setPriceActual(template.getPriceActual());
		result.setPriceEntered(template.getPriceEntered());
		result.setPrinted(template.isPrinted());
		result.setQtysToInvoice(template.getQtysToInvoice());
		result.setC_PaymentTerm_ID(template.getC_PaymentTerm_ID());
		result.setC_VAT_Code_ID(template.getC_VAT_Code_ID());

		return result;
	}

	@Override
	public IInvoiceCandAggregate mkInvoiceCandAggregate()
	{
		return new InvoiceCandAggregateImpl();
	}

	@Override
	public IAggregationKeyBuilder<I_C_Invoice_Candidate> getHeaderAggregationKeyBuilder()
	{
		return headerAggregationKeyBuilder;
	}

	@Override
	public AggregationKey mkHeaderAggregationKey(final I_C_Invoice_Candidate ic)
	{
		return headerAggregationKeyBuilder.buildAggregationKey(ic);
	}

	@Override
	public AggregationKey mkLineAggregationKey(final I_C_Invoice_Candidate ic)
	{
		return lineAggregationKeyBuilder.buildAggregationKey(ic);
	}

	@Override
	public IProcessor<I_C_Invoice_Candidate> getUpdateProcessor()
	{
		return invoiceCandidateUpdater;
	}

	@Override
	public boolean isIolInDispute(final I_C_InvoiceCandidate_InOutLine iciol)
	{
		if (iciol == null)
		{
			return false; // no IC-IOL, no dispute
		}

		final de.metas.inout.model.I_M_InOutLine inOutLine = InterfaceWrapperHelper.create(iciol.getM_InOutLine(), de.metas.inout.model.I_M_InOutLine.class);
		if (inOutLine == null)
		{
			return false; // no inout line, no dispute
		}

		return inOutLine.isInDispute();
	}

	@Override
	@NonNull
	public List<IInvoiceLineAttribute> extractInvoiceLineAttributes(@Nullable final I_M_InOutLine inOutLine)
	{
		if (inOutLine == null || inOutLine.getM_AttributeSetInstance_ID() <= 0)
		{
			return ImmutableList.of();
		}

		final Predicate<I_M_AttributeInstance> filter = ai -> {
			final I_M_Attribute attribute = attributesDAO.getAttributeById(ai.getM_Attribute_ID());
			return attribute.isAttrDocumentRelevant();
		};
		return extractInvoiceLineAttributes(inOutLine.getM_AttributeSetInstance(), filter);
	}

	@Override
	@NonNull
	public List<IInvoiceLineAttribute> extractInvoiceLineAttributes(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		if (icRecord.getM_AttributeSetInstance_ID() <= 0)
		{
			return ImmutableList.of();
		}

		final Predicate<I_M_AttributeInstance> filter = ai -> true;
		return extractInvoiceLineAttributes(icRecord.getM_AttributeSetInstance(), filter);
	}


	private ImmutableList<IInvoiceLineAttribute> extractInvoiceLineAttributes(
			@NonNull final I_M_AttributeSetInstance attributeSetInstance, 
			@NonNull final Predicate<I_M_AttributeInstance> filter)
	{
		
		final List<I_M_AttributeInstance> attributeInstances = attributesDAO.retrieveAttributeInstances(attributeSetInstance);

		final ImmutableList.Builder<IInvoiceLineAttribute> result = ImmutableList.builder();
		for (final I_M_AttributeInstance attributeInstance : attributeInstances)
		{
			if (!filter.test(attributeInstance))
			{
				continue;
			}

			final IInvoiceLineAttribute invoiceLineAttribute = new InvoiceLineAttribute(attributeInstance);
			result.add(invoiceLineAttribute);
		}
		return result.build();
	}
	
	@Override
	public void setHeaderAggregationKey(@NonNull final I_C_Invoice_Candidate ic)
	{
		// If the invoice candidate is flagged as "IsToClear", we shall reset the header aggregation key and invoicing group ASAP (08637)
		if (ic.isToClear())
		{
			resetHeaderAggregationKey(ic);
			return;
		}

		//
		// Build and set the calculated Header Aggregation Key
		final AggregationKey headerAggregationKeyCalculated = mkHeaderAggregationKey(ic);
		ic.setHeaderAggregationKey_Calc(headerAggregationKeyCalculated.getAggregationKeyString());

		//
		// Update invoicing group (C_Invoice_Candidate_HeaderAggregation_ID) based on calculated header aggregation key
		final IAggregationDAO aggregationDAO = Services.get(IAggregationDAO.class);
		final int headerAggregationKeyId = aggregationDAO.findC_Invoice_Candidate_HeaderAggregationKey_ID(ic);
		ic.setC_Invoice_Candidate_HeaderAggregation_ID(headerAggregationKeyId); // Invoicing Group

		//
		// Set the effective Header Aggregation Key (override / calculated)
		// Set effective invoicing group (C_Invoice_Candidate_HeaderAggregation_Effective)
		final I_C_Invoice_Candidate_HeaderAggregation headerAggregationOverride = ic.getC_Invoice_Candidate_HeaderAggregation_Override();
		if (headerAggregationOverride != null)
		{
			ic.setHeaderAggregationKey(headerAggregationOverride.getHeaderAggregationKey()); // Effective HeaderAggregationKey
			ic.setC_Invoice_Candidate_HeaderAggregation_Effective(headerAggregationOverride); // Invoicing Group
			ic.setHeaderAggregationKeyBuilder_ID(headerAggregationOverride.getHeaderAggregationKeyBuilder_ID()); // C_Aggregation_ID
		}
		else
		{
			ic.setHeaderAggregationKey(headerAggregationKeyCalculated.getAggregationKeyString()); // Effective HeaderAggregationKey
			ic.setC_Invoice_Candidate_HeaderAggregation_Effective_ID(headerAggregationKeyId); // Invoicing Group
			ic.setHeaderAggregationKeyBuilder_ID(AggregationId.toRepoId(headerAggregationKeyCalculated.getAggregationId())); // C_Aggregation_ID
		}
	}

	@Override
	public void resetHeaderAggregationKey(final I_C_Invoice_Candidate ic)
	{
		ic.setHeaderAggregationKey_Calc(null);
		ic.setHeaderAggregationKey(null);

		//
		// Reset invoicing group
		ic.setC_Invoice_Candidate_HeaderAggregation(null); // own group
		// ic.setC_Invoice_Candidate_HeaderAggregation_Override(null); // override group - don't touch it
		ic.setC_Invoice_Candidate_HeaderAggregation_Effective(null); // effective
	}

	private void setLineAggregationKey(@NonNull final I_C_Invoice_Candidate ic)
	{
		final AggregationKey lineAggregationKey = mkLineAggregationKey(ic);
		ic.setLineAggregationKey(lineAggregationKey.getAggregationKeyString());
		ic.setLineAggregationKeyBuilder_ID(AggregationId.toRepoId(lineAggregationKey.getAggregationId()));

	}

	private void resetLineAggregationKey(@NonNull final I_C_Invoice_Candidate ic)
	{
		ic.setLineAggregationKey(null);
	}
}
