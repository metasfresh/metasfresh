package de.metas.distribution.workflows_api.json;

import com.google.common.collect.ImmutableList;
<<<<<<< HEAD
=======
import de.metas.ad_reference.ADRefList;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
<<<<<<< HEAD
import org.adempiere.ad.service.IADReferenceDAO;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonRejectReasonsList
{
	@NonNull List<JsonRejectReason> reasons;

<<<<<<< HEAD
	public static JsonRejectReasonsList of(@NonNull IADReferenceDAO.ADRefList adRefList, @NonNull JsonOpts jsonOpts)
=======
	public static JsonRejectReasonsList of(@NonNull final ADRefList adRefList, @NonNull final JsonOpts jsonOpts)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return builder()
				.reasons(adRefList.getItems()
						.stream()
						.map(item -> JsonRejectReason.of(item, jsonOpts))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
