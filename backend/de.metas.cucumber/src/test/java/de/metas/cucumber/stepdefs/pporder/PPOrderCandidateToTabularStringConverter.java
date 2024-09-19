package de.metas.cucumber.stepdefs.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.productplanning.PP_Product_Planning_StepDefData;
import de.metas.material.planning.ProductPlanningId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.text.tabular.Cell;
import de.metas.util.text.tabular.Row;
import de.metas.util.text.tabular.Table;
import lombok.Builder;
import lombok.NonNull;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Builder
class PPOrderCandidateToTabularStringConverter
{
	@NonNull private final PP_Order_Candidate_StepDefData ppOrderCandidateTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final PP_Product_Planning_StepDefData productPlanningTable;

	public String toTabularString(@NonNull final List<I_PP_Order_Candidate> list)
	{
		return toTable(list).toTabularString();
	}

	private Table toTable(@NonNull final List<I_PP_Order_Candidate> list)
	{
		final ImmutableList<Row> rows = list.stream().map(this::toRow).collect(ImmutableList.toImmutableList());
		return toTableFromRows(rows);
	}

	private static Table toTableFromRows(@NonNull final List<Row> rows)
	{
		final Table table = new Table();
		table.addRows(rows);
		table.removeColumnsWithBlankValues();
		table.updateHeaderFromRows();
		return table;
	}

	private Row toRow(@NonNull final I_PP_Order_Candidate candidate)
	{
		final Row row = new Row();
		row.put("Identifier", toCell(PPOrderCandidateId.ofRepoIdOrNull(candidate.getPP_Order_Candidate_ID()), ppOrderCandidateTable));
		row.put("M_Product_ID", toCell(ProductId.ofRepoId(candidate.getM_Product_ID()), productTable));
		row.put("QtyEntered", toQtyCell(candidate.getQtyEntered(), candidate.getC_UOM_ID()));
		row.put("QtyProcessed", toQtyCell(candidate.getQtyProcessed(), candidate.getC_UOM_ID()));
		row.put("DatePromised", candidate.getDatePromised().toInstant());
		row.put("DateStartSchedule", candidate.getDateStartSchedule().toInstant());
		row.put("PP_Product_Planning_ID", toCell(ProductPlanningId.ofRepoIdOrNull(candidate.getPP_Product_Planning_ID()), productPlanningTable));
		return row;
	}

	private static <T extends RepoIdAware> Cell toCell(
			@Nullable final T id,
			@NonNull StepDefDataGetIdAware<T, ?> lookupTable)
	{
		if (id == null)
		{
			return Cell.NULL;
		}

		final String value = lookupTable.getFirstIdentifierById(id)
				.map(StepDefDataIdentifier::getAsString)
				.orElseGet(() -> String.valueOf(id.getRepoId()));
		return Cell.ofNullable(value);
	}

	private Cell toQtyCell(final BigDecimal valueBD, final int uomRepoId)
	{
		if (uomRepoId <= 0)
		{
			return Cell.ofNullable(valueBD);
		}

		final Quantity qty = Quantitys.of(valueBD, UomId.ofRepoId(uomRepoId));
		return Cell.ofNullable(qty);
	}

}