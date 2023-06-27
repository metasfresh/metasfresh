package de.metas.handlingunits.report.labels;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuUnitType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class HULabelConfigQuery
{
	@NonNull HULabelSourceDocType sourceDocType;
	@NonNull HuUnitType huUnitType;
	@Nullable BPartnerId bpartnerId;
}
