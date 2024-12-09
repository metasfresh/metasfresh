package de.metas.manufacturing.workflows_api.activity_handlers.issue.json;

<<<<<<< HEAD
=======
import de.metas.ad_reference.ADRefListItem;
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

@Value
@Builder
@Jacksonized
public class JsonRejectReason
{
	@NonNull String key;
	@NonNull String caption;

<<<<<<< HEAD
	public static JsonRejectReason of(@NonNull IADReferenceDAO.ADRefListItem item, @NonNull JsonOpts jsonOpts)
=======
	public static JsonRejectReason of(@NonNull final ADRefListItem item, @NonNull final JsonOpts jsonOpts)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return builder()
				.key(item.getValue())
				.caption(item.getName().translate(jsonOpts.getAdLanguage()))
				.build();
	}
}
