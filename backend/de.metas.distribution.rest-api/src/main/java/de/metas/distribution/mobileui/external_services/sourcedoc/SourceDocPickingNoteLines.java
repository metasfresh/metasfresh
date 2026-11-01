package de.metas.distribution.mobileui.external_services.sourcedoc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.i18n.ITranslatableString;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

/**
 * Caption lines "source document + picking note", grouped by DD_Order (see {@link SourceDocPickingNotesLoader}).
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SourceDocPickingNoteLines
{
	public static final SourceDocPickingNoteLines EMPTY = new SourceDocPickingNoteLines(ImmutableListMultimap.of());

	@NonNull ImmutableListMultimap<DDOrderId, ITranslatableString> linesByDDOrderId;

	public static SourceDocPickingNoteLines of(@NonNull final ImmutableListMultimap<DDOrderId, ITranslatableString> linesByDDOrderId)
	{
		return linesByDDOrderId.isEmpty() ? EMPTY : new SourceDocPickingNoteLines(linesByDDOrderId);
	}

	@NonNull
	public ImmutableList<ITranslatableString> getLines(@NonNull final DDOrderId ddOrderId)
	{
		return linesByDDOrderId.get(ddOrderId);
	}
}
