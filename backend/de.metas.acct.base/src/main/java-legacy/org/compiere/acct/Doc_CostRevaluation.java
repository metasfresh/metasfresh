package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.costing.CostAmount;
import de.metas.costrevaluation.CostRevaluation;
import de.metas.costrevaluation.CostRevaluationRepository;
import de.metas.document.DocBaseType;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.I_M_CostRevaluationLine;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Doc_CostRevaluation extends Doc<DocLine_CostRevaluation>
{
	private final CostRevaluationRepository costRevaluationRepository = SpringContextHolder.instance.getBean(CostRevaluationRepository.class);
	private final CostRevaluation costRevaluation;

	public Doc_CostRevaluation(final AcctDocContext ctx)
	{
		super(ctx, DocBaseType.CostRevaluation);

		final I_M_CostRevaluation costRevaluationRecord = ctx.getDocumentModel().unboxAs(I_M_CostRevaluation.class);
		this.costRevaluation = CostRevaluationRepository.fromRecord(costRevaluationRecord);
	}

	@Override
	protected void loadDocumentDetails()
	{
		setDateDoc(costRevaluation.getDateAcct());
		setDateAcct(costRevaluation.getDateAcct());
		setDocLines(loadDocLines());
	}

	private ImmutableList<DocLine_CostRevaluation> loadDocLines()
	{
		return costRevaluationRepository.streamAllLineRecordsByCostRevaluationId(costRevaluation.getCostRevaluationId())
				.filter(I_M_CostRevaluationLine::isActive)
				.sorted(Comparator.comparing(I_M_CostRevaluationLine::getM_CostRevaluationLine_ID))
				.map(lineRecord -> new DocLine_CostRevaluation(lineRecord, this))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	protected BigDecimal getBalance() {return BigDecimal.ZERO;}

	@Override
	protected List<Fact> createFacts(final AcctSchema as)
	{
		if (!AcctSchemaId.equals(as.getId(), costRevaluation.getAcctSchemaId()))
		{
			return ImmutableList.of();
		}

		setC_Currency_ID(as.getCurrencyId());

		final Fact fact = new Fact(this, as, PostingType.Actual);
		getDocLines().forEach(line -> createFactsForLine(fact, line));
		return ImmutableList.of(fact);
	}

	private void createFactsForLine(@NonNull final Fact fact, @NonNull final DocLine_CostRevaluation docLine)
	{
		final AcctSchema acctSchema = fact.getAcctSchema();
		final CostAmount costs = docLine.getCreateCosts(acctSchema);

		//
		// Revenue
		// -------------------
		// Product Asset DR
		// Revenue               CR
		if (costs.signum() >= 0)
		{
			fact.createLine()
					.setDocLine(docLine)
					.setAccount(docLine.getAccount(ProductAcctType.P_Asset_Acct, acctSchema))
					.setAmtSource(costs, null)
					// .locatorId(line.getM_Locator_ID()) // N/A atm
					.buildAndAdd();

			fact.createLine()
					.setDocLine(docLine)
					.setAccount(docLine.getAccount(ProductAcctType.P_Revenue_Acct, acctSchema))
					.setAmtSource(null, costs)
					// .locatorId(line.getM_Locator_ID()) // N/A atm
					.buildAndAdd();
		}
		//
		// Expense
		// ------------------------------------
		// Product Asset            CR
		// Expense          DR
		else // deltaAmountToBook.signum() < 0
		{
			fact.createLine()
					.setDocLine(docLine)
					.setAccount(docLine.getAccount(ProductAcctType.P_Asset_Acct, acctSchema))
					.setAmtSource(null, costs.negate())
					// .locatorId(line.getM_Locator_ID()) // N/A atm
					.buildAndAdd();

			fact.createLine()
					.setDocLine(docLine)
					.setAccount(docLine.getAccount(ProductAcctType.P_Asset_Acct, acctSchema))
					.setAmtSource(costs.negate(), null)
					// .locatorId(line.getM_Locator_ID()) // N/A atm
					.buildAndAdd();

		}
	}
}
