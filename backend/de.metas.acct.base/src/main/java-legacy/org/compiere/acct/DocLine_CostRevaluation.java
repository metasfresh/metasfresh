package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostAmountAndQty;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.methods.CostAmountType;
import de.metas.costrevaluation.CostRevaluationLine;
import de.metas.costrevaluation.CostRevaluationRepository;
import de.metas.costrevaluation.CostRevaluationService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_CostRevaluationLine;

import java.util.List;

public class DocLine_CostRevaluation extends DocLine<Doc_CostRevaluation>
{
	private final ICostDetailRepository costDetailRepository = SpringContextHolder.instance.getBean(ICostDetailRepository.class);
	private final CostRevaluationService costRevaluationService = SpringContextHolder.instance.getBean(CostRevaluationService.class);
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
			// Only the revaluation's own cost element is revalued.
			final CostElement costElement = services.getCostElementById(costSegmentAndElement.getCostElementId());
			final CostingDocumentRef documentRef = CostingDocumentRef.ofCostRevaluationLineId(costRevaluationLine.getId());
			// The amount booked is determined by the costing handler from the stock on hand at posting, not from the request's amount.
			final CostDetailCreateResultsList costDetailResults = services.createCostDetail(
					CostDetailCreateRequest.builder()
							.acctSchemaId(costSegmentAndElement.getAcctSchemaId())
							.clientId(costSegmentAndElement.getClientId())
							.orgId(costSegmentAndElement.getOrgId())
							.productId(costSegmentAndElement.getProductId())
							.attributeSetInstanceId(costSegmentAndElement.getAttributeSetInstanceId())
							.costElement(costElement)
							.documentRef(documentRef)
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
				return costDetailResults.getAmtAndQtyToPost(CostAmountType.MAIN, as)
						.map(CostAmountAndQty::getAmt)
						.orElseGet(() -> CostAmount.zero(as.getCurrencyId()));
			}

			// The line and its before-row show what was booked (stock on hand at posting × price difference).
			costRevaluationService.writeBookedValues(costRevaluationLine.getId(), getRevaluationCostDetail(documentRef, costSegmentAndElement));

			// A revaluation of a cost element which is not posted by the accounting schema changes that cost only, with no GL impact.
			if (!costElement.isAccountable(as.getCosting()))
			{
				return CostAmount.zero(as.getCurrencyId());
			}

			return costDetailResults.getMainAmountToPost(as);
		}
	}

	private CostDetail getRevaluationCostDetail(@NonNull final CostingDocumentRef documentRef, @NonNull final CostSegmentAndElement costSegmentAndElement)
	{
		final List<CostDetail> costDetails = costDetailRepository.listByDocumentRefAndAcctSchemaId(documentRef, costSegmentAndElement.getAcctSchemaId())
				.stream()
				.filter(costDetail -> CostElementId.equals(costDetail.getCostElementId(), costSegmentAndElement.getCostElementId()))
				.filter(costDetail -> costDetail.getAmtType() == CostAmountType.MAIN)
				.collect(ImmutableList.toImmutableList());
		if (costDetails.size() != 1)
		{
			throw new AdempiereException("Expected exactly one cost detail for " + documentRef + " but got " + costDetails);
		}
		return costDetails.get(0);
	}

}
