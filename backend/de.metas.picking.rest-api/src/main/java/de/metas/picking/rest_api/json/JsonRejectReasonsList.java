package de.metas.picking.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import de.metas.ad_reference.ADRefList;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonRejectReasonsList
{
	@NonNull List<JsonRejectReason> reasons;

	public static JsonRejectReasonsList of(@NonNull final ADRefList adRefList, @NonNull final JsonOpts jsonOpts)
	{
		return builder()
				.reasons(adRefList.getItems()
						.stream()
						.map(item -> JsonRejectReason.of(item, jsonOpts))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
