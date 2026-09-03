package de.metas.acct.gljournal_sap.select_open_items;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.FactAcctId;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.NonNull;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class OIRowUserInputParts
{
	public static final OIRowUserInputParts EMPTY = new OIRowUserInputParts(ImmutableMap.of());
	private final ImmutableMap<DocumentId, OIRowUserInputPart> byRowId;

	private OIRowUserInputParts(@NonNull final ImmutableMap<DocumentId, OIRowUserInputPart> byRowId)
	{
		this.byRowId = byRowId;
	}

	public static OIRowUserInputParts ofStream(Stream<OIRow> rows)
	{
		ImmutableMap<DocumentId, OIRowUserInputPart> byRowId = rows.filter(OIRow::isSelected)
				.map(OIRow::getUserInputPart)
				.filter(Objects::nonNull)
				.collect(ImmutableMap.toImmutableMap(OIRowUserInputPart::getRowId, Function.identity()));

		return !byRowId.isEmpty()
				? new OIRowUserInputParts(byRowId)
				: EMPTY;
	}

	public Set<FactAcctId> getFactAcctIds()
	{
		return byRowId.values()
				.stream()
				.map(OIRowUserInputPart::getFactAcctId)
				.collect(Collectors.toSet());
	}

	public OIRow applyToRow(@NonNull final OIRow row)
	{
		final OIRowUserInputPart userInput = byRowId.get(row.getId());
		return row.withUserInput(userInput);
	}
}
