package org.compiere.acct;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostAmountAndQty;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResultsList;
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
			// Not reachable for this document type: M_CostRevaluationLine has no Reversal_ID, so no reversal line is
			// ever posted. Reversal of a CopyFromCostElement switch is value-neutral and handled in-place by
			// CostRevaluationDocumentHandler#reverseCorrectIt, not through posting. Fail fast if ever hit.
			throw new UnsupportedOperationException("Posting a M_CostRevaluation reversal line is not supported");
		}
		else
		{
			final CostDetailCreateResultsList results = services.createCostDetail(
					CostDetailCreateRequest.builder()
							.acctSchemaId(costSegmentAndElement.getAcctSchemaId())
							.clientId(costSegmentAndElement.getClientId())
							.orgId(costSegmentAndElement.getOrgId())
							.costElement(services.getCostElementById(costSegmentAndElement.getCostElementId()))
							.productId(costSegmentAndElement.getProductId())
							.attributeSetInstanceId(costSegmentAndElement.getAttributeSetInstanceId())
							.documentRef(CostingDocumentRef.ofCostRevaluationLineId(costRevaluationLine.getId()))
							.qty(costRevaluationLine.getCurrentQty().toZero())
							.amt(costRevaluationLine.getDeltaAmountToBook())
							.explicitCostPrice(costRevaluationLine.getNewCostPrice())
							.date(getDateAcctAsInstant())
							.build());

			if (getDoc().isCopyFromCostElementSource())
			{
				// Value-neutral switch: the target element (e.g. MovingAverageInvoice) is intentionally not yet the
				// acct-schema's accountable method (seed first, activate later), so there is no accountable amount to
				// post and the copy books nothing. Tolerating the empty result is scoped to this source ONLY.
				return results.getAmtAndQtyToPost(CostAmountType.MAIN, as)
						.map(CostAmountAndQty::getAmt)
						.orElseGet(() -> CostAmount.zero(as.getCurrencyId()));
			}

			// Calculated (history-replay): the target element must be the acct-schema's accountable method;
			// fail loud (getMainAmountToPost throws) if it is not, rather than silently book zero to the GL.
			return results.getMainAmountToPost(as);
		}
	}

}
