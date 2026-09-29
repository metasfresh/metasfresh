package org.compiere.acct;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostAmountAndQty;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.methods.CostAmountType;
import de.metas.costrevaluation.CostRevaluationLine;
import de.metas.costrevaluation.CostRevaluationRepository;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_CostRevaluationLine;

public class DocLine_CostRevaluation extends DocLine<Doc_CostRevaluation>
{
	private final CostRevaluationLine costRevaluationLine;

	public DocLine_CostRevaluation(final @NonNull I_M_CostRevaluationLine lineRecord, final @NonNull Doc_CostRevaluation doc)
	{
		super(InterfaceWrapperHelper.getPO(lineRecord), doc);

		costRevaluationLine = CostRevaluationRepository.fromRecord(lineRecord);
	}

	public CostAmount getCreateCosts(@NonNull final AcctSchema as)
	{
		final CostSegmentAndElement costSegmentAndElement = costRevaluationLine.getCostSegmentAndElement();
		if (!AcctSchemaId.equals(costSegmentAndElement.getAcctSchemaId(), as.getId()))
		{
			throw new AdempiereException("Accounting schema not matching: " + costRevaluationLine + ", " + as);
		}

		if (isReversalLine())
		{
			throw new UnsupportedOperationException(); // TODO impl
			// return services.createReversalCostDetails(CostDetailReverseRequest.builder()
			// 				.acctSchemaId(as.getId())
			// 				.reversalDocumentRef(CostingDocumentRef.ofCostRevaluationLineId(get_ID()))
			// 				.initialDocumentRef(CostingDocumentRef.ofCostRevaluationLineId(getReversalLine_ID()))
			// 				.date(getDateAcctAsInstant())
			// 				.build())
			// 		.getTotalAmountToPost(as);
		}
		else
		{
			// Only the revaluation's own cost element is revalued: without an explicit cost element, the request would be
			// exploded to every material cost element of the client and each of them would get the new cost price.
			// A revaluation of a cost element which is not the accounting schema's costing method changes that cost only, with no GL impact.
			return services.createCostDetail(
							CostDetailCreateRequest.builder()
									.acctSchemaId(costSegmentAndElement.getAcctSchemaId())
									.clientId(costSegmentAndElement.getClientId())
									.orgId(costSegmentAndElement.getOrgId())
									.productId(costSegmentAndElement.getProductId())
									.attributeSetInstanceId(costSegmentAndElement.getAttributeSetInstanceId())
									.costElement(services.getCostElementById(costSegmentAndElement.getCostElementId()))
									.documentRef(CostingDocumentRef.ofCostRevaluationLineId(costRevaluationLine.getId()))
									.qty(costRevaluationLine.getCurrentQty().toZero())
									.amt(costRevaluationLine.getDeltaAmountToBook())
									.explicitCostPrice(costRevaluationLine.getNewCostPrice())
									.date(getDateAcctAsInstant())
									.build())
					.getAmtAndQtyToPost(CostAmountType.MAIN, as)
					.map(CostAmountAndQty::getAmt)
					.orElseGet(() -> CostAmount.zero(as.getCurrencyId()));
		}
	}

}
