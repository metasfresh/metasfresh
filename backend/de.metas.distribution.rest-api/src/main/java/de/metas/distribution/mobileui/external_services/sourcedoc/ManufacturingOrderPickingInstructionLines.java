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
 * Caption lines "manufacturing order + picking instruction", grouped by DD_Order.
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ManufacturingOrderPickingInstructionLines
{
	public static final ManufacturingOrderPickingInstructionLines EMPTY = new ManufacturingOrderPickingInstructionLines(ImmutableListMultimap.of());

	@NonNull ImmutableListMultimap<DDOrderId, ITranslatableString> linesByDDOrderId;

	public static ManufacturingOrderPickingInstructionLines of(@NonNull final ImmutableListMultimap<DDOrderId, ITranslatableString> linesByDDOrderId)
	{
		return linesByDDOrderId.isEmpty() ? EMPTY : new ManufacturingOrderPickingInstructionLines(linesByDDOrderId);
	}

	@NonNull
	public ImmutableList<ITranslatableString> getLines(@NonNull final DDOrderId ddOrderId)
	{
		return linesByDDOrderId.get(ddOrderId);
	}
}
