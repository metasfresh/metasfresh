/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): Victor Perez www.e-evolution.com *
 *****************************************************************************/
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

import java.math.BigDecimal;
import java.util.List;

import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.X_C_DocType;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.model.I_PP_Cost_Collector;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.ProductAcctType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.currency.CurrencyPrecision;
import de.metas.util.Services;

/**
 * Post Cost Collector
 *
 * <pre>
 *  Table:              PP_Cost_Collector
 *  Document Types:     MOP
 * </pre>
 *
 * @author victor.perez@e-evolution.com http://www.e-evolution.com
 */
public class Doc_PPCostCollector extends Doc<DocLine_CostCollector>
{
	private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

	/** Pseudo Line */
	protected DocLine_CostCollector _line = null;

	public Doc_PPCostCollector(final AcctDocContext ctx)
	{
		super(ctx, X_C_DocType.DOCBASETYPE_ManufacturingCostCollector);
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

	private CostCollectorType getCostCollectorType()
	{
		return CostCollectorType.ofCode(getPP_Cost_Collector().getCostCollectorType());
	}

	private boolean isFloorStock()
	{
		final I_PP_Cost_Collector cc = getPP_Cost_Collector();
		return ppCostCollectorBL.isFloorStock(cc);
	}

	private BigDecimal getMovementQty()
	{
		return getPP_Cost_Collector().getMovementQty();
	}

	private BigDecimal getScrappedQty()
	{
		return getPP_Cost_Collector().getScrappedQty();
	}

	/** @return zero (always balanced) */
	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public List<Fact> createFacts(final AcctSchema as)
	{
		setC_Currency_ID(as.getCurrencyId());

		final Fact fact;
		final CostCollectorType costCollectorType = getCostCollectorType();
		if (CostCollectorType.MaterialReceipt.equals(costCollectorType))
		{
			fact = createFacts_MaterialReceipt(as);
		}
		else if (CostCollectorType.ComponentIssue.equals(costCollectorType))
		{
			fact = createFacts_ComponentIssue(as);
		}
		else if (CostCollectorType.MethodChangeVariance.equals(costCollectorType))
		{
			fact = createFacts_Variance(as, ProductAcctType.MethodChangeVariance);
		}
		else if (CostCollectorType.UsageVariance.equals(costCollectorType))
		{
			fact = createFacts_Variance(as, ProductAcctType.UsageVariance);
		}
		else if (CostCollectorType.UsageVariance.equals(costCollectorType))
		{
			fact = createFacts_Variance(as, ProductAcctType.UsageVariance);
		}
		else if (CostCollectorType.RateVariance.equals(costCollectorType))
		{
			fact = createFacts_Variance(as, ProductAcctType.RateVariance);
		}
		else if (CostCollectorType.MixVariance.equals(costCollectorType))
		{
			fact = createFacts_Variance(as, ProductAcctType.MixVariance);
		}
		else if (CostCollectorType.ActivityControl.equals(costCollectorType))
		{
			fact = createFacts_ActivityControl(as);
		}
		else
		{
			throw newPostingException().setDetailMessage("Unknown costCollectorType: " + costCollectorType);
		}

		//
		return ImmutableList.of(fact);
	}

	private void createFactLines(
			final CostElement costElement,
			final Fact fact,
			final MAccount debit,
			final MAccount credit,
			final CostAmount cost,
			final BigDecimal qty)
	{
		if (debit == null || credit == null || cost == null || cost.signum() == 0)
		{
			return;
		}

		final DocLine_CostCollector docLine = getLine();

		final String desc = costElement.getName();

		final FactLine dr = fact.createLine(docLine, debit, cost.getCurrencyId(), cost.getValue(), null);
		dr.setQty(qty);
		dr.addDescription(desc);
		dr.setC_Project_ID(docLine.getC_Project_ID());
		dr.setC_Activity_ID(docLine.getActivityId());
		dr.setC_Campaign_ID(docLine.getC_Campaign_ID());
		dr.setM_Locator_ID(docLine.getM_Locator_ID());

		final FactLine cr = fact.createLine(docLine, credit, cost.getCurrencyId(), null, cost.getValue());
		cr.setQty(qty);
		cr.addDescription(desc);
		cr.setC_Project_ID(docLine.getC_Project_ID());
		cr.setC_Activity_ID(docLine.getActivityId());
		cr.setC_Campaign_ID(docLine.getC_Campaign_ID());
		cr.setM_Locator_ID(docLine.getM_Locator_ID());
	}

	/**
	 * <pre>
	 * (for each cost element)
	 * WIP                       CR
	 * Product Asset      DR
	 * Scrap(expense)     DR
	 * </pre>
	 */
	protected Fact createFacts_MaterialReceipt(final AcctSchema as)
	{
		final Fact fact = new Fact(this, as, PostingType.Actual);

		final DocLine_CostCollector docLine = getLine();
		final BigDecimal qtyReceived = getMovementQty();
		final BigDecimal qtyScrapped = getScrappedQty();
		final BigDecimal qtyTotal = qtyReceived.add(qtyScrapped);
		if (qtyTotal.signum() == 0)
		{
			return fact;
		}

		final MAccount credit = docLine.getAccount(ProductAcctType.WorkInProcess, as);

		final AggregatedCostAmount costResult = docLine.getCreateCosts(as);
		for (final CostElement element : costResult.getCostElements())
		{
			final CostAmount costs = costResult.getCostAmountForCostElement(element);
			final CostAmount costsReceived = costs.divide(qtyTotal, CurrencyPrecision.ofInt(12))
					.multiply(qtyReceived)
					.roundToPrecisionIfNeeded(as.getStandardPrecision());
			final CostAmount costsScrapped = costs.subtract(costsReceived);

			if (costsReceived.signum() != 0)
			{
				final MAccount debit = docLine.getAccount(ProductAcctType.Asset, as);
				createFactLines(element, fact, debit, credit, costsReceived, qtyReceived);
			}

			if (costsScrapped.signum() != 0)
			{
				final MAccount debit = docLine.getAccount(ProductAcctType.Scrap, as);
				createFactLines(element, fact, debit, credit, costsScrapped, qtyScrapped);
			}
		}
		return fact;
	}

	/**
	 * <pre>
	 * (for each cost element)
	 * WIP                                 DR
	 * Product Asset / Floor Stock               CR
	 * </pre>
	 */
	private Fact createFacts_ComponentIssue(final AcctSchema as)
	{
		final Fact fact = new Fact(this, as, PostingType.Actual);
		final boolean isFloorStock = isFloorStock();

		final DocLine_CostCollector docLine = getLine();
		final BigDecimal qtyIssued = getMovementQty();

		final MAccount debit = docLine.getAccount(ProductAcctType.WorkInProcess, as);
		final MAccount credit = docLine.getAccount(isFloorStock ? ProductAcctType.FloorStock : ProductAcctType.Asset, as);

		final AggregatedCostAmount costResult = docLine.getCreateCosts(as);
		for (final CostElement element : costResult.getCostElements())
		{
			final CostAmount costs = costResult.getCostAmountForCostElement(element);
			createFactLines(element, fact, debit, credit, costs, qtyIssued);
		}

		return fact;
	}

	/**
	 * <pre>
	 * (for each cost element)
	 * WIP                        DR
	 * Product Asset/Labor/                        CR
	 * Burden/Overhead
	 * </pre>
	 */
	private Fact createFacts_ActivityControl(final AcctSchema as)
	{
		final Fact fact = new Fact(this, as, PostingType.Actual);

		final DocLine_CostCollector docLine = getLine();
		final BigDecimal qtyMoved = getMovementQty();

		final MAccount debit = docLine.getAccount(ProductAcctType.WorkInProcess, as);

		final AggregatedCostAmount costResult = docLine.getCreateCosts(as);
		for (final CostElement element : costResult.getCostElements())
		{
			final CostAmount costs = costResult.getCostAmountForCostElement(element);
			final MAccount credit = docLine.getAccountForCostElement(as, element);
			createFactLines(element, fact, debit, credit, costs, qtyMoved);
		}

		return fact;
	}

	/**
	 * <pre>
	 * (for each cost element)
	 * WIP                                       CR
	 * Method/Usage/Rate/Mix Variance    DR
	 * </pre>
	 */
	private Fact createFacts_Variance(final AcctSchema as, final ProductAcctType varianceAcctType)
	{
		final Fact fact = new Fact(this, as, PostingType.Actual);

		final DocLine_CostCollector docLine = getLine();
		final MAccount debit = docLine.getAccount(varianceAcctType, as);
		final MAccount credit = docLine.getAccount(ProductAcctType.WorkInProcess, as);

		final BigDecimal qty = getMovementQty();

		final AggregatedCostAmount costResult = docLine.getCreateCosts(as);
		for (final CostElement element : costResult.getCostElements())
		{
			final CostAmount costs = costResult.getCostAmountForCostElement(element);
			createFactLines(element, fact, debit, credit, costs.negate(), qty.negate());
		}
		return fact;
	}
}
