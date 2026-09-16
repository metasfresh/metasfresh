package de.metas.manufacturing.acct;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.acct.Account;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.methods.CostAmountDetailed;
import de.metas.costing.methods.PPOrderCostDifferenceDistributor;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocBaseType;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.PPCostCollectorQuantities;
import org.eevolution.model.I_PP_Cost_Collector;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Post Cost Collector
 *
 * <pre>
 *  Table:              PP_Cost_Collector
 *  Document Types:     MOP
 * </pre>
 *
 * @author victor.perez@e-evolution.com <a href="http://www.e-evolution.com">...</a>
 */
public class Doc_PPCostCollector extends Doc<DocLine_CostCollector>
{
	private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

	/**
	 * Pseudo Line
	 */
	protected DocLine_CostCollector _line = null;

	public Doc_PPCostCollector(final AcctDocContext ctx)
	{
		super(ctx, DocBaseType.ManufacturingCostCollector);
	}

	@Override
	protected void loadDocumentDetails()
	{
		setNoCurrency();
		final I_PP_Cost_Collector cc = getModel(I_PP_Cost_Collector.class);
		setDateDoc(cc.getMovementDate());
		setDateAcct(cc.getMovementDate());

		// Pseudo Line
		_line = new DocLine_CostCollector(cc, this);
	}

	private DocLine_CostCollector getLine()
	{
		return _line;
	}

	private I_PP_Cost_Collector getPP_Cost_Collector()
	{
		return getModel(I_PP_Cost_Collector.class);
	}

	protected CostCollectorType getCostCollectorType()
	{
		return CostCollectorType.ofCode(getPP_Cost_Collector().getCostCollectorType());
	}

	private boolean isFloorStock()
	{
		final I_PP_Cost_Collector cc = getPP_Cost_Collector();
		return ppCostCollectorBL.isFloorStock(cc);
	}

	private Quantity getMovementQty()
	{
		return getQuantities().getMovementQty();
	}

	private PPCostCollectorQuantities getQuantities()
	{
		return ppCostCollectorBL.getQuantities(getPP_Cost_Collector());
	}

	/**
	 * @return zero (always balanced)
	 */
	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public List<Fact> createFacts(final AcctSchema as)
	{
		setC_Currency_ID(as.getCurrencyId());

		final ArrayList<Fact> facts = new ArrayList<>();

		final CostCollectorType costCollectorType = getCostCollectorType();
		if (CostCollectorType.MaterialReceipt.equals(costCollectorType))
		{
			facts.addAll(createFacts_MaterialReceipt(as));
		}
		else if (CostCollectorType.ComponentIssue.equals(costCollectorType))
		{
			facts.addAll(createFacts_ComponentIssue(as));
		}
		else if (CostCollectorType.MethodChangeVariance.equals(costCollectorType))
		{
			facts.addAll(createFacts_Variance(as, ProductAcctType.P_MethodChangeVariance_Acct));
		}
		else if (CostCollectorType.UsageVariance.equals(costCollectorType))
		{
			facts.addAll(createFacts_Variance(as, ProductAcctType.P_UsageVariance_Acct));
		}
		else if (CostCollectorType.RateVariance.equals(costCollectorType))
		{
			facts.addAll(createFacts_Variance(as, ProductAcctType.P_RateVariance_Acct));
		}
		else if (CostCollectorType.MixVariance.equals(costCollectorType))
		{
			// MixVariance is used EXCLUSIVELY for co/by-product receipts (CostCollectorType.isCoOrByProductReceipt()
			// returns true only for MixVariance; PPCostCollectorBL.extractCostCollectorTypeToUseForComponentIssue
			// assigns it only for a co/by-product BOM line). It is NOT a genuine mix variance, so the received
			// co/by-product must capitalize to inventory like the main product, not book to P_MixVariance (P&L).
			facts.addAll(createFacts_CoProductReceipt(as));
		}
		else if (CostCollectorType.ActivityControl.equals(costCollectorType))
		{
			facts.addAll(createFacts_ActivityControl(as));
		}
		else if (CostCollectorType.CostDifferenceDistribution.equals(costCollectorType))
		{
			facts.addAll(createFacts_CostDifferenceDistribution(as));
		}
		else
		{
			throw newPostingException().setDetailMessage("Unknown costCollectorType: " + costCollectorType);
		}

		//
		return facts;
	}

	@Nullable
	private Fact createFactLines(
			@NonNull final AcctSchema as,
			@NonNull final CostElement costElement,
			@NonNull final Account debit,
			@NonNull final Account credit,
			@NonNull final CostAmount cost,
			@NonNull final Quantity qty,
			final boolean alsoAddZeroLine)
	{
		final DocLine_CostCollector docLine = getLine();
		final String description = costElement.getName();
		final Fact fact = new Fact(this, as, PostingType.Actual);
		fact.createLine()
				.setDocLine(docLine)
				.setAccount(debit)
				.setAmtSource(cost.getCurrencyId(), cost.toBigDecimal(), null)
				.setQty(qty)
				.alsoAddZeroLineIf(alsoAddZeroLine) // caller controls whether a zero-amount-and-zero-qty line is still posted
				.additionalDescription(description)
				.projectId(docLine.getC_Project_ID())
				.activityId(docLine.getActivityId())
				.campaignId(docLine.getC_Campaign_ID())
				.locatorId(docLine.getM_Locator_ID())
				.buildAndAdd();
		fact.createLine()
				.setDocLine(docLine)
				.setAccount(credit)
				.setAmtSource(cost.getCurrencyId(), null, cost.toBigDecimal())
				.setQty(qty.negate())
				.alsoAddZeroLineIf(alsoAddZeroLine) // keep the symmetric credit leg together with its debit
				.additionalDescription(description)
				.projectId(docLine.getC_Project_ID())
				.activityId(docLine.getActivityId())
				.campaignId(docLine.getC_Campaign_ID())
				.locatorId(docLine.getM_Locator_ID())
				.buildAndAdd();

		return fact;
	}

	/**
	 * <pre>
	 * (for each cost element)
	 * WIP                       CR
	 * Product Asset      DR
	 * Scrap(expense)     DR
	 * </pre>
	 */
	private List<Fact> createFacts_MaterialReceipt(final AcctSchema as)
	{
		final DocLine_CostCollector docLine = getLine();
		final PPCostCollectorQuantities qtys = getQuantities();
		final Quantity qtyReceived = qtys.getMovementQty();
		final Quantity qtyScrapped = qtys.getScrappedQty();
		final Quantity qtyTotal = qtyReceived.add(qtyScrapped);
		if (qtyTotal.signum() == 0)
		{
			return ImmutableList.of();
		}

		final Account credit = docLine.getAccount(ProductAcctType.P_WIP_Acct, as);
		final AggregatedCostAmount costResult = docLine.getCreateCosts(as).orElseThrow();

		final ArrayList<Fact> facts = new ArrayList<>();
		for (final CostElement element : costResult.getCostElements())
		{
			if (!element.isAccountable(as.getCosting()))
			{
				continue;
			}

			final CostAmount costs = costResult.getCostAmountForCostElement(element).getMainAmt();
			final CostAmount costsReceived = costs.divide(qtyTotal, CurrencyPrecision.ofInt(12))
					.multiply(qtyReceived)
					.roundToPrecisionIfNeeded(as.getStandardPrecision());
			final CostAmount costsScrapped = costs.subtract(costsReceived);

			// Received leg: post when something was received, even at zero cost (e.g. a manufactured product freshly
			// on Moving Average Invoice). The received qty must reach P_Asset — the Lagerwert report sums
			// Fact_Acct.qty on P_Asset, so dropping a zero-cost receipt line silently loses the received stock.
			// alsoAddZeroLine=true is belt-and-suspenders here: the leg is already gated on a non-zero qty, so its
			// line survives regardless (the flag would only matter if this guard were ever relaxed away from a qty check).
			if (qtyReceived.signum() != 0)
			{
				final Account debit = docLine.getAccount(ProductAcctType.P_Asset_Acct, as);
				final Fact fact = createFactLines(as, element, debit, credit, costsReceived, qtyReceived, true);
				if (fact != null)
				{
					facts.add(fact);
				}
			}

			// Scrap leg: post on qty OR cost — qty carries the scrapped stock into valuation, and cost still posts a
			// sub-precision rounding remainder (the pre-fix behaviour) even when nothing was scrapped by qty.
			// alsoAddZeroLine=true is belt-and-suspenders here too: the leg is already gated above, so its line
			// survives regardless.
			if (qtyScrapped.signum() != 0 || costsScrapped.signum() != 0)
			{
				final Account debit = docLine.getAccount(ProductAcctType.P_Scrap_Acct, as);
				final Fact fact = createFactLines(as, element, debit, credit, costsScrapped, qtyScrapped, true);
				if (fact != null)
				{
					facts.add(fact);
				}
			}
		}

		return facts;
	}

	/**
	 * Co/by-product receipt (CostCollectorType.MixVariance). Mirrors {@link #createFacts_MaterialReceipt}:
	 * <pre>
	 * (for each cost element)
	 * WIP                       CR
	 * Product Asset      DR
	 * </pre>
	 * The received co/by-product must capitalize to inventory with the received qty on P_Asset — the Lagerwert
	 * report ({@code report_InventoryValue}) sums {@code Fact_Acct.qty} on P_Asset — so its value clears the
	 * order's WIP exactly like the main-product receipt. This replaces the former routing through
	 * {@link #createFacts_Variance} to {@code P_MixVariance_Acct} (a P&amp;L variance account) with the amount and
	 * qty negated, which never capitalized the value to inventory and left the order's per-order WIP un-cleared.
	 */
	private List<Fact> createFacts_CoProductReceipt(final AcctSchema as)
	{
		final DocLine_CostCollector docLine = getLine();
		final AggregatedCostAmount costResult = docLine.getCreateCosts(as).orElse(null);
		if (costResult == null)
		{
			// No cost details created (e.g. no accountable cost elements) — nothing to post.
			return ImmutableList.of();
		}

		// The raw PP_Cost_Collector.MovementQty of a co/by-product OUTPUT is stored negative; the received qty
		// that capitalizes to inventory is its positive counterpart (mirrors DocLine_CostCollector's negateIf,
		// which already turns it positive for getCreateCosts, so the cost amount here is already positive too).
		final Quantity qtyReceived = getMovementQty().negate();

		final Account debit = docLine.getAccount(ProductAcctType.P_Asset_Acct, as);
		final Account credit = docLine.getAccount(ProductAcctType.P_WIP_Acct, as);

		final ArrayList<Fact> facts = new ArrayList<>();
		for (final CostElement element : costResult.getCostElements())
		{
			if (!element.isAccountable(as.getCosting()))
			{
				continue;
			}

			final CostAmount costs = costResult.getCostAmountForCostElement(element).getMainAmt();
			// createFactLines puts +qty on the P_Asset debit leg and -qty on the P_WIP credit leg, so the
			// positive received qty is what reaches P_Asset. Do NOT negate the cost: it is already positive
			// (a by-product with a blank fixed price yields a zero-cost line — its qty still capitalizes).
			// alsoAddZeroLine=true: post even a zero-value receipt so the received qty always reaches P_Asset.
			final Fact fact = createFactLines(as, element, debit, credit, costs, qtyReceived, true);
			if (fact != null)
			{
				facts.add(fact);
			}
		}

		return facts;
	}

	/**
	 * <pre>
	 * (for each cost element)
	 * WIP                                 DR
	 * Product Asset / Floor Stock               CR
	 * </pre>
	 */
	private List<Fact> createFacts_ComponentIssue(final AcctSchema as)
	{
		final boolean isFloorStock = isFloorStock();

		final DocLine_CostCollector docLine = getLine();
		final Quantity qtyIssued = getMovementQty();

		final Account debit = docLine.getAccount(ProductAcctType.P_WIP_Acct, as);
		final Account credit = docLine.getAccount(isFloorStock ? ProductAcctType.P_FloorStock_Acct : ProductAcctType.P_Asset_Acct, as);
		final AggregatedCostAmount costResult = docLine.getCreateCosts(as).orElseThrow().retainOnlyAccountable(as);

		final ArrayList<Fact> facts = new ArrayList<>();
		for (final CostElement element : costResult.getCostElements())
		{
			if (!element.isAccountable(as.getCosting()))
			{
				continue;
			}

			final CostAmount costs = costResult.getCostAmountForCostElement(element).getMainAmt();
			// The doc line carries a negated qty (DocLine_CostCollector.setQty(movementQty, isSOTrx=true)), so
			// getCreateCosts returns a negative COST — negate it back. Do NOT negate the qty too: createFactLines
			// puts +qty on the debit leg and -qty on the credit leg, so the positive issued qty is what leaves
			// P_Asset_Acct with the -qty that report_InventoryValue sums as stock going out.
			// alsoAddZeroLine=true: a component issue always books, even a zero-cost/zero-qty line
			final Fact fact = createFactLines(as, element, debit, credit, costs.negate(), qtyIssued, true);
			if (fact != null)
			{
				facts.add(fact);
			}
		}

		return facts;
	}

	/**
	 * <pre>
	 * (for each cost element)
	 * WIP                        DR
	 * Product Asset/Labor/                        CR
	 * Burden/Overhead
	 * </pre>
	 */
	private List<Fact> createFacts_ActivityControl(final AcctSchema as)
	{
		final DocLine_CostCollector docLine = getLine();
		final AggregatedCostAmount costResult = docLine.getCreateCosts(as).orElse(null);
		if(costResult == null)
		{
			// NOTE: there is no need to fail if no cost details were created
			// because it might be that there are no cost elements defined for resource, which is acceptable
			return ImmutableList.of();
		}

		final Quantity qtyMoved = getMovementQty();
		final Account debit = docLine.getAccount(ProductAcctType.P_WIP_Acct, as);

		final ArrayList<Fact> facts = new ArrayList<>();
		for (final CostElement element : costResult.getCostElements())
		{
			final CostAmount costs = costResult.getCostAmountForCostElement(element).getMainAmt();
			final Account credit = docLine.getAccountForCostElement(as, element);
			final Fact fact = createFactLines(as, element, debit, credit, costs, qtyMoved, false);
			if (fact != null)
			{
				facts.add(fact);
			}
		}

		return facts;
	}

	/**
	 * <pre>
	 * (for each cost element)
	 * WIP                                       CR
	 * Method/Usage/Rate/Mix Variance    DR
	 * </pre>
	 */
	private List<Fact> createFacts_Variance(
			final AcctSchema as,
			final ProductAcctType varianceAcctType)
	{
		final DocLine_CostCollector docLine = getLine();
		final AggregatedCostAmount costResult = docLine.getCreateCosts(as).orElse(null);
		if(costResult == null)
		{
			// NOTE: there is no need to fail if no cost details were created
			// because it might be that there are no cost elements defined for resource, which is acceptable
			return ImmutableList.of();
		}

		final Account debit = docLine.getAccount(varianceAcctType, as);
		final Account credit = docLine.getAccount(ProductAcctType.P_WIP_Acct, as);
		final Quantity qty = getMovementQty();

		final ArrayList<Fact> facts = new ArrayList<>();
		for (final CostElement element : costResult.getCostElements())
		{
			if (!element.isAccountable(as.getCosting()))
			{
				continue;
			}

			final CostAmount costs = costResult.getCostAmountForCostElement(element).getMainAmt();
			final Fact fact = createFactLines(as, element, debit, credit, costs.negate(), qty.negate(), false);
			if (fact != null)
			{
				facts.add(fact);
			}
		}

		return facts;
	}

	/**
	 * Posts the WIP residual: DR Product Asset (capitalized) + DR COGS (shipped remainder) / CR WIP, each leg
	 * flipped when the residual is negative.
	 * <p>
	 * The main product's residual is posted from the single-cost-segment {@link AggregatedCostAmount} the collector
	 * line returns (unchanged). Each co-product's residual is posted as its OWN additional, self-balanced Fact,
	 * resolved against the co-product's own product accounts (AC8): {@link PPOrderCostDifferenceDistributor}
	 * persists a {@code CostDetail} row per co-product but deliberately keeps them OUT of that single-segment list
	 * (mixing segments would break {@code toAggregatedCostAmount}), so they are read back here per product instead.
	 * The co-product path runs even when the main product's own residual is zero - that case returns no main
	 * {@code AggregatedCostAmount}, and the co-product legs would otherwise be silently dropped.
	 */
	private List<Fact> createFacts_CostDifferenceDistribution(final AcctSchema as)
	{
		final DocLine_CostCollector docLine = getLine();
		// Also persists the per-co-product CostDetail rows this method reads back below (or replays them on reversal).
		final AggregatedCostAmount costResult = docLine.getCreateCosts(as).orElse(null);

		final ArrayList<Fact> facts = new ArrayList<>();

		if (costResult != null)
		{
			final ImmutableList<CostDifferenceDistributionLeg> legs = costDifferenceDistributionLegs(costResult.getTotalAmountToPost(as));
			if (!legs.isEmpty())
			{
				final Fact fact = new Fact(this, as, PostingType.Actual);
				for (final CostDifferenceDistributionLeg leg : legs)
				{
					addCostDifferenceFactLine(fact, docLine, docLine.getAccount(leg.getAcctType(), as), leg);
				}
				facts.add(fact);
			}
		}

		facts.addAll(createCoProductDifferenceFacts(as, docLine));

		return facts;
	}

	/**
	 * One additional, self-balanced Fact per co-product that carries a residual, resolved against the co-product's
	 * OWN product accounts. Reads the collector's persisted {@code CostDetail} rows grouped by product; the
	 * main-product rows are excluded because that leg is already posted above.
	 */
	private List<Fact> createCoProductDifferenceFacts(
			@NonNull final AcctSchema as,
			@NonNull final DocLine_CostCollector docLine)
	{
		final CostingDocumentRef documentRef = CostingDocumentRef.ofCostCollectorId(docLine.get_ID());
		final ImmutableMap<ProductId, CostAmountDetailed> amountsByProduct = getServices().getCostDetailAmountsToPostByProduct(documentRef, as);

		final ImmutableList<CoProductDistributionLegs> coProductLegs = coProductDistributionLegs(amountsByProduct, docLine.getProductId());
		if (coProductLegs.isEmpty())
		{
			return ImmutableList.of();
		}

		final ArrayList<Fact> facts = new ArrayList<>();
		for (final CoProductDistributionLegs coProduct : coProductLegs)
		{
			final Fact fact = new Fact(this, as, PostingType.Actual);
			for (final CostDifferenceDistributionLeg leg : coProduct.getLegs())
			{
				final Account account = docLine.getAccount(leg.getAcctType(), as, coProduct.getProductId());
				addCostDifferenceFactLine(fact, docLine, account, leg);
			}
			facts.add(fact);
		}

		return facts;
	}

	/**
	 * Turns each product's detailed residual into its balanced Dr/Cr leg-set, dropping the main product (already
	 * posted) and any product whose residual nets to zero (no leg-set, so no empty Fact). Pure so the co-product
	 * fact-emission gap can be tested without the accounting SQL that resolves the per-product accounts.
	 */
	@VisibleForTesting
	static ImmutableList<CoProductDistributionLegs> coProductDistributionLegs(
			@NonNull final ImmutableMap<ProductId, CostAmountDetailed> amountsToPostByProduct,
			@NonNull final ProductId mainProductId)
	{
		final ImmutableList.Builder<CoProductDistributionLegs> result = ImmutableList.builder();
		for (final Map.Entry<ProductId, CostAmountDetailed> entry : amountsToPostByProduct.entrySet())
		{
			final ProductId productId = entry.getKey();
			if (productId.equals(mainProductId))
			{
				continue;
			}

			final ImmutableList<CostDifferenceDistributionLeg> legs = costDifferenceDistributionLegs(entry.getValue());
			if (!legs.isEmpty())
			{
				result.add(new CoProductDistributionLegs(productId, legs));
			}
		}
		return result.build();
	}

	/**
	 * The line carries a ZERO qty: the receipt already accounted for the quantity, so a qty here would be
	 * counted a second time by the inventory valuation (Lagerwert) report.
	 */
	private void addCostDifferenceFactLine(
			@NonNull final Fact fact,
			@NonNull final DocLine_CostCollector docLine,
			@NonNull final Account account,
			@NonNull final CostDifferenceDistributionLeg leg)
	{
		final CostAmount absAmt = leg.getAbsAmt();
		fact.createLine()
				.setDocLine(docLine)
				.setAccount(account)
				.setAmtSource(absAmt.getCurrencyId(),
						leg.isDebit() ? absAmt.toBigDecimal() : null,
						leg.isDebit() ? null : absAmt.toBigDecimal())
				.setQty(getMovementQty().toZero())
				.additionalDescription("CostDifferenceDistribution")
				.projectId(docLine.getC_Project_ID())
				.activityId(docLine.getActivityId())
				.campaignId(docLine.getC_Campaign_ID())
				.locatorId(docLine.getM_Locator_ID())
				.buildAndAdd();
	}

	/**
	 * At most three legs — asset, COGS and the negated residual on WIP — dropping the zero ones. They always
	 * balance, because {@code capitalized + cogs == residual}.
	 */
	@VisibleForTesting
	static ImmutableList<CostDifferenceDistributionLeg> costDifferenceDistributionLegs(@NonNull final CostAmountDetailed split)
	{
		final CostAmount residual = split.getMainAmt();
		if (residual.isZero())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<CostDifferenceDistributionLeg> legs = ImmutableList.builder();
		addLegIfNotZero(legs, ProductAcctType.P_Asset_Acct, split.getCostAdjustmentAmt());
		addLegIfNotZero(legs, ProductAcctType.P_COGS_Acct, split.getAlreadyShippedAmt());
		addLegIfNotZero(legs, ProductAcctType.P_WIP_Acct, residual.negate());
		return legs.build();
	}

	private static void addLegIfNotZero(
			@NonNull final ImmutableList.Builder<CostDifferenceDistributionLeg> legs,
			@NonNull final ProductAcctType acctType,
			@NonNull final CostAmount amt)
	{
		if (!amt.isZero())
		{
			legs.add(new CostDifferenceDistributionLeg(acctType, amt));
		}
	}

	@Value
	static class CostDifferenceDistributionLeg
	{
		@NonNull ProductAcctType acctType;
		/** positive =&gt; debit; negative =&gt; credit. */
		@NonNull CostAmount amt;

		boolean isDebit()
		{
			return amt.signum() > 0;
		}

		CostAmount getAbsAmt()
		{
			return amt.negateIf(amt.signum() < 0);
		}
	}

	/** A single co-product's balanced residual leg-set, tagged with the product whose accounts each leg resolves against. */
	@Value
	static class CoProductDistributionLegs
	{
		@NonNull ProductId productId;
		@NonNull ImmutableList<CostDifferenceDistributionLeg> legs;
	}
}
