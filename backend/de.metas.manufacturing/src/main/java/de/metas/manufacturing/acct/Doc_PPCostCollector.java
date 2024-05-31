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

import com.google.common.collect.ImmutableList;
import de.metas.acct.Account;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocBaseType;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
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
			facts.addAll(createFacts_Variance(as, ProductAcctType.P_MixVariance_Acct));
		}
		else if (CostCollectorType.ActivityControl.equals(costCollectorType))
		{
			facts.addAll(createFacts_ActivityControl(as));
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
			@NonNull final Quantity qty)
	{
		final DocLine_CostCollector docLine = getLine();
		final String description = costElement.getName();
		final Fact fact = new Fact(this, as, PostingType.Actual);
		fact.createLine()
				.setDocLine(docLine)
				.setAccount(debit)
				.setAmtSource(cost.getCurrencyId(), cost.toBigDecimal(), null)
				.setQty(qty)
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

			if (costsReceived.signum() != 0)
			{
				final Account debit = docLine.getAccount(ProductAcctType.P_Asset_Acct, as);
				final Fact fact = createFactLines(as, element, debit, credit, costsReceived, qtyReceived);
				if (fact != null)
				{
					facts.add(fact);
				}
			}

			if (costsScrapped.signum() != 0)
			{
				final Account debit = docLine.getAccount(ProductAcctType.P_Scrap_Acct, as);
				final Fact fact = createFactLines(as, element, debit, credit, costsScrapped, qtyScrapped);
				if (fact != null)
				{
					facts.add(fact);
				}
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
			final Fact fact = createFactLines(as, element, debit, credit, costs, qtyIssued);
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
		if (costResult == null)
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
			final Fact fact = createFactLines(as, element, debit, credit, costs, qtyMoved);
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
		if (costResult == null)
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
			final Fact fact = createFactLines(as, element, debit, credit, costs.negate(), qty.negate());
			if (fact != null)
			{
				facts.add(fact);
			}
		}

		return facts;
	}
}
