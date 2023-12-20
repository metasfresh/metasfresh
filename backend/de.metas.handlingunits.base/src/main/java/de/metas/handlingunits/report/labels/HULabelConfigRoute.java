package de.metas.handlingunits.report.labels;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuUnitType;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
public class HULabelConfigRoute
{
	@NonNull SeqNo seqNo;
	@Nullable HULabelSourceDocType sourceDocType;
	@NonNull ImmutableSet<HuUnitType> acceptHUUnitTypes;

	@Nullable BPartnerId bpartnerId;

	@NonNull HULabelConfig config;

	public boolean isMatching(@NonNull final HULabelConfigQuery query)
	{
		return isSourceDocTypeMatching(query.getSourceDocType())
				&& acceptHUUnitTypes.contains(query.getHuUnitType())
				&& isBPartnerMatching(query.getBpartnerId())
				;
	}

	private boolean isSourceDocTypeMatching(final HULabelSourceDocType querySourceDocType)
	{
		return this.sourceDocType == null || Objects.equals(this.sourceDocType, querySourceDocType);
	}

	private boolean isBPartnerMatching(final BPartnerId queryBPartnerId)
	{
		return this.bpartnerId == null || Objects.equals(this.bpartnerId, queryBPartnerId);
	}

}
