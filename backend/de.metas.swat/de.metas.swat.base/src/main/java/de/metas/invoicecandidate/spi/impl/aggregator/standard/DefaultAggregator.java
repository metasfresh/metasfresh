package de.metas.invoicecandidate.spi.impl.aggregator.standard;

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

import de.metas.aggregation.api.AggregationId;
import de.metas.aggregation.api.AggregationKey;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceLineAggregationRequest;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.impl.AggregationKeyEvaluationContext;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IAggregator;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOutLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Default aggregator implementation. It is used if a given {@link I_C_Invoice_Candidate} record has no {@link I_C_Invoice_Candidate#COLUMNNAME_C_Invoice_Candidate_Agg_ID} or if it has one without an
 * explicit classname value.
 * <p>
 * This implementation sums up QtyToInvoice, PriceActual, PriceEntered and Discount of the candidates that it aggregates.
 *
 * @author ts
 */
@ToString
public class DefaultAggregator implements IAggregator
{
	// Services
	private final transient IAggregationBL aggregationBL = Services.get(IAggregationBL.class);
	private transient MatchInvoiceService matchInvoiceService;

	/**
	 * Map: Invoice Line Aggregation Key to List of candidates, that needs to be aggregated. Using a {@link LinkedHashMap} so that the order in which the {@link InvoiceCandidateWithInOutLine}'s are
	 * aggregated (see {@link #aggregate()}) is the same order in which they were added (see {@link #addInvoiceCandidate(IInvoiceLineAggregationRequest)}.
	 * <p>
	 * After aggregation this map will be cleared.
	 */
	private final Map<String, List<InvoiceCandidateWithInOutLine>> aggKey2iciol = new LinkedHashMap<>();

	@Override
	public void setMatchInvoiceService(final MatchInvoiceService matchInvoiceService)
	{
		this.matchInvoiceService = matchInvoiceService;
	}

	@Override
	public void addInvoiceCandidate(@NonNull final IInvoiceLineAggregationRequest request)
	{
		final I_C_InvoiceCandidate_InOutLine icIol = request.getC_InvoiceCandidate_InOutLine();
		if (icIol != null && aggregationBL.isIolInDispute(icIol))
		{
			return; // this implementation does not deal with iols which are in dispute.
			// Note that generally, those iols shall not be invoiced.
			// However, but not dealing with them at all as this aggregator does is just one variant.
			// That's why the decision is made here in the aggregator and not in the aggregation engine.
		}

		final String aggregationKeyToUse = mkLineAggregationKeyToUse(request);

		List<InvoiceCandidateWithInOutLine> icsPool = aggKey2iciol.get(aggregationKeyToUse);
		if (icsPool == null)
		{
			icsPool = new ArrayList<>();
			aggKey2iciol.put(aggregationKeyToUse, icsPool);
		}

		//
		// Create InvoiceCandidate with InOutLine and add it to the pool
		final InvoiceCandidateWithInOutLine ics = new InvoiceCandidateWithInOutLine(matchInvoiceService, request);
		icsPool.add(ics);
	}

	private String mkLineAggregationKeyToUse(@NonNull final IInvoiceLineAggregationRequest request)
	{
		final I_C_Invoice_Candidate ic = request.getC_Invoice_Candidate();
		InterfaceWrapperHelper.refresh(ic); // make sure it's up to date
		//
		final String lineAggregationKeyStr = ic.getLineAggregationKey();
		Check.assume(!Check.isEmpty(lineAggregationKeyStr) || Services.get(IInvoiceCandDAO.class).isToRecompute(ic),
				"LineAggregationKey may not be empty, except when the ic is new and thus needs recomputation: {}", ic);

		final StringBuilder aggregationKeyToUse = new StringBuilder();

		if (Check.isEmpty(lineAggregationKeyStr))
		{
			// 'ic' has an empty LineAggregationKey;
			// don't aggregate it with any other candidate
			// Note: we don't care for the LineAggregationKey_Suffix
			aggregationKeyToUse.append("UniqueIC_");
			aggregationKeyToUse.append(ic.getC_Invoice_Candidate_ID());
		}
		else
		{
			//
			// Parse IC's LineAggregationKey
			{
				final AggregationId lineAggregationKeyBuilderId = AggregationId.ofRepoIdOrNull(ic.getLineAggregationKeyBuilder_ID());
				final AggregationKey lineAggregationKeyUnparsed = new AggregationKey(lineAggregationKeyStr, lineAggregationKeyBuilderId);

				final I_C_InvoiceCandidate_InOutLine iciol = request.getC_InvoiceCandidate_InOutLine();
				final I_M_InOutLine inoutLine = iciol == null ? null : iciol.getM_InOutLine();
				final AggregationKeyEvaluationContext evalCtx = AggregationKeyEvaluationContext.builder()
						.invoiceCandidate(request.getC_Invoice_Candidate())
						.inoutLine(inoutLine)
						.invoiceLineAttributes(request.getAttributesFromInoutLines())
						.build();
				final AggregationKey lineAggregationKey = lineAggregationKeyUnparsed.parse(evalCtx);
				aggregationKeyToUse.append(lineAggregationKey.getAggregationKeyString());
			}

			final String lineAggregationKey_Suffix = ic.getLineAggregationKey_Suffix();
			if (!Check.isEmpty(lineAggregationKey_Suffix))
			{
				aggregationKeyToUse.append("_");
				aggregationKeyToUse.append(lineAggregationKey_Suffix);
			}
		}

		//
		// Append aggregation keys from invoice line attributes
		// NOTE: shall be configured from line aggregation.
		// In case of legacy key builder, we moved the logic to de.metas.invoicecandidate.agg.key.impl.ICLineAggregationKeyBuilder_OLD.buildAggregationKey(I_C_Invoice_Candidate)
		// for (final IInvoiceLineAttribute invoiceLineAttribute : request.getInvoiceLineAttributes())
		// {
		// aggregationKeyToUse.append("#");
		// aggregationKeyToUse.append(invoiceLineAttribute.toAggregationKey());
		// }

		//
		// Append additional aggregation key identifiers, used for grouping candidates
		// NOTE: basically this shall be always empty because everything which is related to line aggregation
		// shall be configured from aggregation definition,
		// but we are also leaving this door open in case we need to implement some quick/hot fixes.
		for (final Object aggregationKeyElement : request.getLineAggregationKeyElements())
		{
			aggregationKeyToUse.append("#");
			aggregationKeyToUse.append(aggregationKeyElement);
		}

		return aggregationKeyToUse.toString();
	}

	/**
	 * Note that this implementation can be relied on to create n:1 relations between {@link I_C_Invoice_Candidate} and {@link IInvoiceLineRW}.<br>
	 * Each {@link IInvoiceCandAggregate} has exactly one <code>IInvoiceLineRW</code> instance in it.
	 */
	@Override
	public List<IInvoiceCandAggregate> aggregate()
	{
		final List<IInvoiceCandAggregate> invoiceCandAggregates = new ArrayList<>();

		// ic2QtyInvoiceable keeps track of the qty that we have left to invoice,
		// to make sure that we don't invoice more that the invoice candidate allows us to
		final HashMap<InvoiceCandidateId, StockQtyAndUOMQty> ic2QtyInvoiceable = createInvoiceableQtysMap();

		for (final String aggKey : new ArrayList<>(aggKey2iciol.keySet()))
		{
			final List<InvoiceCandidateWithInOutLine> icsForKey = aggKey2iciol.remove(aggKey);
			if (icsForKey == null || icsForKey.isEmpty())
			{
				// shall not happen, but we can safely ignore it
				continue;
			}

			final InvoiceCandidateWithInOutLineAggregator icsAggregator = new InvoiceCandidateWithInOutLineAggregator();
			icsAggregator.setInvoiceableQtys(ic2QtyInvoiceable);

			icsAggregator.addInvoiceCandidateWithInOutLines(icsForKey);

			//
			// We will skip any IInvoiceLineRW creation if there is no valid ICS found on this key
			if (!icsAggregator.hasAtLeastOneValidICS())
			{
				continue;
			}

			//
			// Create the invoice candidate aggregate and collect it
			final IInvoiceCandAggregate invoiceCandAggregate = icsAggregator.aggregate();
			invoiceCandAggregates.add(invoiceCandAggregate);
		}

		return invoiceCandAggregates;
	}

	/**
	 * @return a map of {@link I_C_Invoice_Candidate} to stockQty that could be invoiced
	 */
	private HashMap<InvoiceCandidateId, StockQtyAndUOMQty> createInvoiceableQtysMap()
	{
		// ic2QtyInvoiceable keeps track of the stockQty that we have left to invoice, to make sure that we don't invoice more that the invoice candidate allows us to
		final HashMap<InvoiceCandidateId, StockQtyAndUOMQty> ic2QtyInvoicable = new HashMap<>();

		// we initialize the map with all ICs' qtyToInvoice values
		for (final List<InvoiceCandidateWithInOutLine> icsForKey : aggKey2iciol.values())
		{
			for (final InvoiceCandidateWithInOutLine ics : icsForKey)
			{

				final I_C_Invoice_Candidate ic = ics.getC_Invoice_Candidate();
				if (!ic2QtyInvoicable.containsKey(ics.getInvoicecandidateId()))
				{
					// Initialize, if necessary

					// task 08507: ic.getQtyToInvoice() is already the "effective" Qty.
					// Even if QtyToInvoice_Override is set, the system will decide what to invoice (e.g. based on RnvoiceRule and QtyDelivered)
					// and update QtyToInvoice accordingly, possibly to a value that is different from QtyToInvoice_Override.
					final StockQtyAndUOMQty qtyToInvoice = StockQtyAndUOMQtys.create(
							ic.getQtyToInvoice(), ics.getProductId(),
							ic.getQtyToInvoiceInUOM(), ics.getIcUomId());
					ic2QtyInvoicable.put(ics.getInvoicecandidateId(), qtyToInvoice);
				}
			}
		}

		return ic2QtyInvoicable;
	}

	@Override
	public void setContext(final Properties ctx, final String trxName)
	{
		// this implementation has no use for 'ctx' and 'trxName'
	}
}
