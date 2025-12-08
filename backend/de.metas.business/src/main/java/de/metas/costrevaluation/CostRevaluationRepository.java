package de.metas.costrevaluation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailId;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CurrentCost;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.I_M_CostRevaluationLine;
import org.compiere.model.I_M_CostRevaluation_Detail;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class CostRevaluationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public CostRevaluation getById(@NonNull final CostRevaluationId costRevaluationId)
	{
		final I_M_CostRevaluation record = InterfaceWrapperHelper.load(costRevaluationId, I_M_CostRevaluation.class);
		if (record == null)
		{
			throw new AdempiereException("No record found for " + costRevaluationId);
		}
		return fromRecord(record);
	}

	public static CostRevaluation fromRecord(@NonNull final I_M_CostRevaluation record)
	{
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());

		return CostRevaluation.builder()
				.costRevaluationId(CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID()))
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.costElementId(CostElementId.ofRepoId(record.getM_CostElement_ID()))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(orgId)
				.evaluationStartDate(record.getEvaluationStartDate().toInstant())
				.dateAcct(InstantAndOrgId.ofTimestamp(record.getDateAcct(), orgId))
				.docStatus(DocStatus.ofCode(record.getDocStatus()))
				.build();
	}

	public void createLinesForCurrentCosts(
			@NonNull final CostRevaluationId costRevaluationId,
			@NonNull final List<CurrentCost> currentCosts)
	{
		final HashMap<CostRevaluationLineKey, I_M_CostRevaluationLine> existingRecords = streamAllLineRecordsByCostRevaluationId(costRevaluationId)
				.collect(GuavaCollectors.toHashMapByKey(CostRevaluationRepository::extractCostRevaluationLineKey));

		for (final CurrentCost currentCost : currentCosts)
		{
			final CostRevaluationLineKey key = extractCostRevaluationLineKey(currentCost);
			I_M_CostRevaluationLine existingRecord = existingRecords.remove(key);
			if (existingRecord == null)
			{
				existingRecord = InterfaceWrapperHelper.newInstance(I_M_CostRevaluationLine.class);
				existingRecord.setM_CostRevaluation_ID(costRevaluationId.getRepoId());
				existingRecord.setAD_Org_ID(key.getClientAndOrgId().getOrgId().getRepoId());
				existingRecord.setIsRevaluated(false);
			}

			// Skip evaluated lines
			if (existingRecord.isRevaluated())
			{
				continue;
			}

			updateRecordFrom(existingRecord, currentCost);
			InterfaceWrapperHelper.save(existingRecord);
		}

		final Collection<I_M_CostRevaluationLine> linesToDelete = existingRecords.values();
		if (!linesToDelete.isEmpty())
		{
			final ImmutableSet<CostRevaluationLineId> lineIds = linesToDelete.stream()
					.map(CostRevaluationRepository::extractCostRevaluationLineId)
					.collect(ImmutableSet.toImmutableSet());

			deleteDetailsByLineIds(lineIds);
			InterfaceWrapperHelper.deleteAll(linesToDelete);
		}
	}

	@NonNull
	private static CostRevaluationLineId extractCostRevaluationLineId(final I_M_CostRevaluationLine line)
	{
		return CostRevaluationLineId.ofRepoId(line.getM_CostRevaluation_ID(), line.getM_CostRevaluationLine_ID());
	}

	private static CostRevaluationLineKey extractCostRevaluationLineKey(@NonNull final CurrentCost currentCost)
	{
		final CostSegment costSegment = currentCost.getCostSegment();

		return CostRevaluationLineKey.builder()
				.productId(costSegment.getProductId())
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(costSegment.getClientId(), costSegment.getOrgId()))
				.build();
	}

	private static CostRevaluationLineKey extractCostRevaluationLineKey(@NonNull final I_M_CostRevaluationLine record)
	{
		return CostRevaluationLineKey.builder()
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.build();
	}

	@Value
	@Builder
	private static class CostRevaluationLineKey
	{
		@NonNull ProductId productId;
		@NonNull ClientAndOrgId clientAndOrgId;
	}

	private static void updateRecordFrom(@NonNull final I_M_CostRevaluationLine record, @NonNull final CurrentCost from)
	{
		record.setIsActive(true);

		updateRecordFrom(record, from.getCostSegment());
		record.setM_CostElement_ID(from.getCostElementId().getRepoId());

		final CostPrice costPrice = from.getCostPrice();
		record.setCurrentCostPrice(costPrice.getOwnCostPrice().toBigDecimal());
		if (record.getM_CostRevaluationLine_ID() <= 0)
		{
			record.setNewCostPrice(costPrice.getOwnCostPrice().toBigDecimal());
		}
		record.setC_Currency_ID(costPrice.getCurrencyId().getRepoId());

		record.setC_UOM_ID(costPrice.getUomId().getRepoId());
		record.setCurrentQty(from.getCurrentQty().toBigDecimal());
	}

	public Stream<I_M_CostRevaluationLine> streamAllLineRecordsByCostRevaluationId(@NonNull final CostRevaluationId costRevaluationId)
	{
		return queryBL.createQueryBuilder(I_M_CostRevaluationLine.class)
				.addEqualsFilter(I_M_CostRevaluationLine.COLUMN_M_CostRevaluation_ID, costRevaluationId)
				.orderBy(I_M_CostRevaluationLine.COLUMN_M_CostRevaluationLine_ID)
				.stream();
	}

	public boolean hasActiveLines(@NonNull final CostRevaluationId costRevaluationId)
	{
		return queryBL.createQueryBuilder(I_M_CostRevaluationLine.class)
				.addEqualsFilter(I_M_CostRevaluationLine.COLUMN_M_CostRevaluation_ID, costRevaluationId)
				.addOnlyActiveRecordsFilter()
				.anyMatch();
	}

	public List<CostRevaluationLine> getLinesByCostRevaluationId(@NonNull final CostRevaluationId costRevaluationId)
	{
		return streamAllLineRecordsByCostRevaluationId(costRevaluationId)
				.filter(I_M_CostRevaluationLine::isActive)
				.map(CostRevaluationRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public static CostRevaluationLine fromRecord(@NonNull final I_M_CostRevaluationLine record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		return CostRevaluationLine.builder()
				.id(extractCostRevaluationLineId(record))
				.costSegmentAndElement(extractCostSegmentAndElement(record))
				.currentQty(Quantitys.of(record.getCurrentQty(), UomId.ofRepoId(record.getC_UOM_ID())))
				.currentCostPrice(CostAmount.of(record.getCurrentCostPrice(), currencyId))
				.newCostPrice(CostAmount.of(record.getNewCostPrice(), currencyId))
				.isRevaluated(record.isRevaluated())
				.deltaAmountToBook(CostAmount.of(record.getDeltaAmt(), currencyId))
				.build();
	}

	private static CostSegmentAndElement extractCostSegmentAndElement(@NonNull final I_M_CostRevaluationLine record)
	{
		return CostSegmentAndElement.of(
				extractCostSegment(record),
				CostElementId.ofRepoId(record.getM_CostElement_ID())
		);
	}

	private static CostSegment extractCostSegment(@NonNull final I_M_CostRevaluationLine record)
	{
		return CostSegment.builder()
				.costingLevel(CostingLevel.ofCode(record.getCostingLevel()))
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.costTypeId(CostTypeId.ofRepoId(record.getM_CostType_ID()))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.build();
	}

	private static void updateRecordFrom(
			@NonNull final I_M_CostRevaluationLine record,
			@NonNull final CostSegment from)
	{
		record.setCostingLevel(from.getCostingLevel().getCode());
		record.setC_AcctSchema_ID(from.getAcctSchemaId().getRepoId());
		record.setM_CostType_ID(from.getCostTypeId().getRepoId());
		Check.assumeEquals(record.getAD_Client_ID(), from.getClientId().getRepoId(), "Record {} shall match {}", record, from.getClientId());
		record.setAD_Org_ID(from.getOrgId().getRepoId());
		record.setM_Product_ID(from.getProductId().getRepoId());
		record.setM_AttributeSetInstance_ID(from.getAttributeSetInstanceId().getRepoId());
	}

	public void createDetail(@NonNull final CostRevaluationDetailCreateRequest request)
	{
		final I_M_CostRevaluation_Detail record = InterfaceWrapperHelper.newInstance(I_M_CostRevaluation_Detail.class);

		record.setM_CostRevaluation_ID(request.getLineId().getCostRevaluationId().getRepoId());
		record.setM_CostRevaluationLine_ID(request.getLineId().getRepoId());
		record.setSeqNo(request.getSeqNo().toInt());
		record.setRevaluationType(request.getType().getCode());

		updateRecordFrom(record, request.getCostSegmentAndElement());

		record.setC_UOM_ID(request.getQty().getUomId().getRepoId());
		record.setQty(request.getQty().toBigDecimal());

		record.setC_Currency_ID(request.getCurrencyId().getRepoId());
		record.setOldCostPrice(request.getOldCostPrice().toBigDecimal());
		record.setNewCostPrice(request.getNewCostPrice().toBigDecimal());
		record.setOldAmt(request.getOldAmount().toBigDecimal());
		record.setNewAmt(request.getNewAmount().toBigDecimal());
		record.setDeltaAmt(request.getDeltaAmount().toBigDecimal());

		record.setM_CostDetail_ID(CostDetailId.toRepoId(request.getCostDetailId()));

		InterfaceWrapperHelper.save(record);
	}

	private static void updateRecordFrom(
			@NonNull final I_M_CostRevaluation_Detail record,
			@NonNull final CostSegmentAndElement from)
	{
		record.setCostingLevel(from.getCostingLevel().getCode());
		record.setC_AcctSchema_ID(from.getAcctSchemaId().getRepoId());
		record.setM_CostType_ID(from.getCostTypeId().getRepoId());
		Check.assumeEquals(record.getAD_Client_ID(), from.getClientId().getRepoId(), "Record {} shall match {}", record, from.getClientId());
		record.setAD_Org_ID(from.getOrgId().getRepoId());
		record.setM_Product_ID(from.getProductId().getRepoId());
		record.setM_AttributeSetInstance_ID(from.getAttributeSetInstanceId().getRepoId());

		record.setM_CostElement_ID(from.getCostElementId().getRepoId());
	}

	public void deleteLinesByRevaluationId(@NonNull final CostRevaluationId costRevaluationId)
	{
		queryBL.createQueryBuilder(I_M_CostRevaluationLine.class)
				.addEqualsFilter(I_M_CostRevaluationLine.COLUMNNAME_M_CostRevaluation_ID, costRevaluationId)
				.create()
				.delete();
	}

	public void deleteDetailsByRevaluationId(@NonNull final CostRevaluationId costRevaluationId)
	{
		queryBL.createQueryBuilder(I_M_CostRevaluation_Detail.class)
				.addEqualsFilter(I_M_CostRevaluation_Detail.COLUMNNAME_M_CostRevaluation_ID, costRevaluationId)
				.create()
				.delete();
	}

	public void deleteDetailsByLineId(@NonNull final CostRevaluationLineId lineId)
	{
		deleteDetailsByLineIds(ImmutableSet.of(lineId));
	}

	public void deleteDetailsByLineIds(@NonNull final Collection<CostRevaluationLineId> lineIds)
	{
		if (lineIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_M_CostRevaluation_Detail.class)
				.addInArrayFilter(I_M_CostRevaluation_Detail.COLUMNNAME_M_CostRevaluationLine_ID, lineIds)
				.create()
				.delete();
	}

	public void save(@NonNull final CostRevaluationLine line)
	{
		final I_M_CostRevaluationLine record = InterfaceWrapperHelper.load(line.getId(), I_M_CostRevaluationLine.class);
		record.setIsRevaluated(line.isRevaluated());
		record.setDeltaAmt(line.getDeltaAmountToBook().toBigDecimal());
		InterfaceWrapperHelper.save(record);
	}

}
