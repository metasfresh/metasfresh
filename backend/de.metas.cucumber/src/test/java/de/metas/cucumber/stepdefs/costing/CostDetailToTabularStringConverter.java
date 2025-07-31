package de.metas.cucumber.stepdefs.costing;

import de.metas.costing.CostDetail;
import de.metas.costing.CostElementId;
import de.metas.costing.CostingDocumentRef;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.util.text.tabular.Row;
import de.metas.util.text.tabular.Table;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.List;

@RequiredArgsConstructor
public class CostDetailToTabularStringConverter
{
	@NonNull private final M_CostElement_StepDefData costElementTable;
	@NonNull private final CostingDocumentRefResolver costingDocumentRefResolver;

	@NonNull private static final ImmutableList<String> DEFAULT_COLUMN_NAMES = ImmutableList.of(
			"TableName",
			"Record_ID",
			"IsSOTrx",
			"IsChangingCosts",
			"AmtType",
			"Qty",
			"Amt",
			"DateAcct",
			"M_CostElement_ID"
	);

	public Table toTabular(
			@NonNull final List<CostDetail> records,
			@Nullable final Integer startLineNo,
			@Nullable final List<String> columnNames)
	{
		final List<String> columnNamesEffective = computeColumnNamesEffective(columnNames);

		final Table table = new Table();

		for (int i = 0; i < records.size(); i++)
		{
			final CostDetail record = records.get(i);
			final Integer lineNo = startLineNo != null ? startLineNo + i : null;
			table.addRow(toRow(record, lineNo, columnNamesEffective));
		}
		table.updateHeaderFromRows();
		table.removeColumnsWithBlankValues();
		return table;
	}

	@NonNull
	private static List<String> computeColumnNamesEffective(@Nullable final List<String> columnNames)
	{
		if (columnNames == null || columnNames.isEmpty())
		{
			return DEFAULT_COLUMN_NAMES;
		}

		final LinkedHashSet<String> columnNamesEffective = new LinkedHashSet<>(columnNames.size() + DEFAULT_COLUMN_NAMES.size());
		columnNamesEffective.addAll(columnNames);
		columnNamesEffective.addAll(DEFAULT_COLUMN_NAMES);
		return ImmutableList.copyOf(columnNamesEffective);
	}

	private Row toRow(
			@NonNull final CostDetail record,
			@Nullable final Integer lineNo,
			@NonNull final List<String> columnNames)
	{
		final Row row = new Row();

		if (lineNo != null)
		{
			row.put("#", lineNo);
		}

		columnNames.forEach(columnName -> {
			final Object valueObj = extractCellValue(record, columnName);
			if (valueObj != null)
			{
				row.put(columnName, valueObj);
			}
		});

		row.put("Raw", record);

		return row;
	}

	private Object extractCellValue(final CostDetail record, final String columnName)
	{
		switch (columnName)
		{
			case "TableName":
			{
				return record.getDocumentRef().getTableName();
			}
			case "Record_ID":
			{
				return extractCellValue_Record_ID(record);
			}
			case "IsSOTrx":
			{
				return record.getDocumentRef().getOutboundTrx();
			}
			case "Amt":
			{
				return record.getAmt();
			}
			case "Qty":
			{
				return record.getQty();
			}
			case "IsChangingCosts":
			{
				return record.isChangingCosts();
			}
			case "AmtType":
			{
				return record.getAmtType();
			}
			case "DateAcct":
			{
				return record.getDateAcct();
			}
			case "M_CostElement_ID":
			{
				return extractCellValue_M_CostElement_ID(record);
			}
			default:
			{
				return null;
			}
		}
	}

	private @NonNull String extractCellValue_Record_ID(final CostDetail record)
	{
		final CostingDocumentRef documentRef = record.getDocumentRef();
		return costingDocumentRefResolver.resolveToIdentifier(documentRef)
				.map(StepDefDataIdentifier::getAsString)
				.orElseGet(() -> "<" + documentRef.getId().getRepoId() + ">");
	}

	@NonNull
	private String extractCellValue_M_CostElement_ID(final CostDetail record)
	{
		final CostElementId costElementId = record.getCostElementId();
		return costElementTable.getFirstIdentifierById(costElementId)
				.map(StepDefDataIdentifier::getAsString)
				.orElseGet(() -> "<" + costElementId.getRepoId() + ">");
	}

}
