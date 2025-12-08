package de.metas.handlingunits;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class HuPackingInstructionsIdAndCaption
{
	@NonNull HuPackingInstructionsId id;
	@NonNull String caption;
}
