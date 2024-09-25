package de.metas.cucumber.stepdefs.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.billofmaterial.PP_Product_BOM_StepDefData;
import de.metas.cucumber.stepdefs.productplanning.PP_Product_Planning_StepDefData;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.material.planning.ProductPlanningId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.text.tabular.Row;
import de.metas.util.text.tabular.Table;
import lombok.Builder;
import lombok.NonNull;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;

import java.util.List;

import static de.metas.cucumber.stepdefs.TabularStringConverterUtil.toBooleanCell;
import static de.metas.cucumber.stepdefs.TabularStringConverterUtil.toCell;
import static de.metas.cucumber.stepdefs.TabularStringConverterUtil.toQtyCell;
import static de.metas.cucumber.stepdefs.TabularStringConverterUtil.toTableFromRows;

@Builder
class PPOrderCandidateToTabularStringConverter
{
	@NonNull private final PP_Order_Candidate_StepDefData ppOrderCandidateTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final PP_Product_BOM_StepDefData productBOMTable;
	@NonNull private final PP_Product_Planning_StepDefData productPlanningTable;
	@NonNull private final S_Resource_StepDefData resourceTable;

	public String toTabularString(@NonNull final List<I_PP_Order_Candidate> list)
	{
		return toTable(list).toTabularString();
	}

	private Table toTable(@NonNull final List<I_PP_Order_Candidate> list)
	{
		final ImmutableList<Row> rows = list.stream().map(this::toRow).collect(ImmutableList.toImmutableList());
		return toTableFromRows(rows);
	}

	private Row toRow(@NonNull final I_PP_Order_Candidate candidate)
	{
		final Row row = new Row();
		row.put("Identifier", toCell(PPOrderCandidateId.ofRepoIdOrNull(candidate.getPP_Order_Candidate_ID()), ppOrderCandidateTable));
		row.put("M_Product_ID", toCell(ProductId.ofRepoId(candidate.getM_Product_ID()), productTable));
		row.put("PP_Product_BOM_ID", toCell(ProductBOMId.ofRepoId(candidate.getPP_Product_BOM_ID()), productBOMTable));
		row.put("S_Resource_ID", toCell(ResourceId.ofRepoId(candidate.getS_Resource_ID()), resourceTable));
		row.put("QtyEntered", toQtyCell(candidate.getQtyEntered(), candidate.getC_UOM_ID()));
		row.put("QtyToProcess", toQtyCell(candidate.getQtyToProcess(), candidate.getC_UOM_ID()));
		row.put("QtyProcessed", toQtyCell(candidate.getQtyProcessed(), candidate.getC_UOM_ID()));
		row.put("DatePromised", candidate.getDatePromised().toInstant());
		row.put("DateStartSchedule", candidate.getDateStartSchedule().toInstant());
		row.put("PP_Product_Planning_ID", toCell(ProductPlanningId.ofRepoIdOrNull(candidate.getPP_Product_Planning_ID()), productPlanningTable));
		row.put("IsClosed", toBooleanCell(candidate.isClosed()));
		row.put("Processed", toBooleanCell(candidate.isProcessed()));
		return row;
	}
}