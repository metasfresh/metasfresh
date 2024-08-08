package de.metas.cucumber.stepdefs.material.dispo;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.product.ProductId;
import de.metas.util.text.tabular.Cell;
import de.metas.util.text.tabular.Row;
import de.metas.util.text.tabular.Table;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import java.util.List;

@RequiredArgsConstructor
@Builder
public class CandidatesToTabularStringConverter
{
	@NonNull private final MaterialDispoRecordRepository materialDispoRecordRepository;
	@NonNull private final MD_Candidate_StepDefData mdCandidateTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	public String toTabularStringFromCandidates(@NonNull final List<Candidate> candidates)
	{
		return toTableFromCandidates(candidates).toTabularString();
	}

	private Table toTableFromCandidates(@NonNull final List<Candidate> candidates)
	{
		final ImmutableList<Row> rows = candidates.stream().map(this::toRow).collect(ImmutableList.toImmutableList());
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

	private Row toRow(@NonNull final Candidate candidate)
	{
		final Candidate stockCandidate = materialDispoRecordRepository.getStockCandidate(candidate).orElse(null);

		final Row row = new Row();
		row.put("Identifier", toCell(candidate.getId()));
		row.put("MD_Candidate_Type", candidate.getType());
		row.put("MD_Candidate_BusinessCase", candidate.getBusinessCase());
		row.put("M_Product_ID", toCell(candidate.getProductId()));
		row.put("DateProjected", candidate.getDate());
		row.put("Qty", candidate.getQuantity());

		if (stockCandidate != null)
		{
			row.put("ATP", stockCandidate.getQuantity());
		}

		row.put("M_AttributeSetInstance_ID", toCell(candidate.getAttributeSetInstanceId()));

		return row;
	}

	private Cell toCell(@NonNull final CandidateId candidateId)
	{
		final String value = mdCandidateTable.getFirstIdentifierById(candidateId)
				.map(StepDefDataIdentifier::getAsString)
				.orElseGet(() -> String.valueOf(candidateId.getRepoId()));
		return Cell.ofNullable(value);
	}

	private Cell toCell(@NonNull final ProductId productId)
	{
		final String value = productTable.getFirstIdentifierById(productId)
				.map(StepDefDataIdentifier::getAsString)
				.orElseGet(() -> String.valueOf(productId.getRepoId()));
		return Cell.ofNullable(value);
	}

	private Cell toCell(@NonNull final AttributeSetInstanceId asiId)
	{
		if (asiId.isNone())
		{
			return Cell.NULL;
		}

		final String value = attributeSetInstanceTable.getFirstIdentifierById(asiId)
				.map(StepDefDataIdentifier::getAsString)
				.orElseGet(() -> String.valueOf(asiId.getRepoId()));
		return Cell.ofNullable(value);
	}

	//
	//
	//
	//
	//
	//

	public String toTabularStringFromCandidateRecords(@NonNull final List<I_MD_Candidate> candidates)
	{
		return toTableFromCandidateRecords(candidates).toTabularString();
	}

	private Table toTableFromCandidateRecords(@NonNull final List<I_MD_Candidate> candidates)
	{
		final ImmutableList<Row> rows = candidates.stream().map(this::toRow).collect(ImmutableList.toImmutableList());
		return toTableFromRows(rows);
	}

	private Row toRow(@NonNull final I_MD_Candidate candidate)
	{
		final Row row = new Row();
		row.put("Identifier", toCell(CandidateId.ofRepoId(candidate.getMD_Candidate_ID())));
		row.put("MD_Candidate_Type", candidate.getMD_Candidate_Type());
		row.put("MD_Candidate_BusinessCase", candidate.getMD_Candidate_BusinessCase());
		row.put("M_Product_ID", toCell(ProductId.ofRepoId(candidate.getM_Product_ID())));
		row.put("DateProjected", candidate.getDateProjected().toInstant());
		row.put("Qty", candidate.getQty());
		row.put("ATP", "?");
		row.put("M_AttributeSetInstance_ID", toCell(AttributeSetInstanceId.ofRepoId(candidate.getM_AttributeSetInstance_ID())));
		return row;
	}

	//
	//
	//
	//
	//
	//

	public static String toTabularStringFromCandidateRows(@NonNull final MD_Candidate_StepDefTable table)
	{
		return toTableFromCandidateRows(table).toTabularString();
	}

	private static Table toTableFromCandidateRows(@NonNull final MD_Candidate_StepDefTable table)
	{
		final ImmutableList<Row> rows = table.stream().map(CandidatesToTabularStringConverter::toRow).collect(ImmutableList.toImmutableList());
		return toTableFromRows(rows);
	}

	private static Row toRow(@NonNull final MD_Candidate_StepDefTable.MaterialDispoTableRow from)
	{
		final Row row = new Row();
		row.putAll(from.getRawValues().asMap());
		return row;
	}

}
