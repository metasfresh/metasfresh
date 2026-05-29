package de.metas.handlingunits.picking.candidate.commands;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.quantity.Capacity;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value(staticConstructor = "of")
@EqualsAndHashCode(doNotUseGetters = true)
public class HuPackingInstructionsIdAndCapacity
{
	@NonNull HuPackingInstructionsId huPackingInstructionsId;
	@Nullable Capacity capacity;

	public static HuPackingInstructionsIdAndCapacity of(@NonNull final HuPackingInstructionsId huPackingInstructionsId)
	{
		return new HuPackingInstructionsIdAndCapacity(huPackingInstructionsId, null);
	}

	@Nullable
	public Capacity getCapacityOrNull() {return capacity;}

	@NonNull
	public Optional<Capacity> getCapacity() {return Optional.ofNullable(capacity);}
}
