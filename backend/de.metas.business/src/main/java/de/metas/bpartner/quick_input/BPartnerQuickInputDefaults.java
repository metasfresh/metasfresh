package de.metas.bpartner.quick_input;

import com.google.common.collect.ImmutableSet;
import de.metas.marketing.base.model.CampaignId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class BPartnerQuickInputDefaults
{
	@Nullable CampaignId campaignId;

	@Builder.Default
	@NonNull ImmutableSet<String> attributes3 = ImmutableSet.of();
}
