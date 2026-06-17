package de.metas.material.cockpit.availableforsales;

import de.metas.JsonObjectMapperHolder;
import de.metas.event.Event;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.util.Env;

@UtilityClass
class EnqueueAvailableForSalesConverter
{
	private static final String EVENT_NAME = "EnqueueAvailableForSales";
	private static final String PROPERTY_Query = "query";

	public static Event toEvent(final @NonNull EnqueueAvailableForSalesRequest request)
	{
		@NonNull final AvailableForSalesQuery query = request.getAvailableForSalesQuery();

		return Event.builder()
				.setEventName(EVENT_NAME)
				.putProperty(PROPERTY_Query, JsonObjectMapperHolder.toJson(query))
				.putProperty(Env.CTXNAME_AD_User_ID, UserId.toRepoId(request.getContextUserId()))
				.putProperty(Env.CTXNAME_AD_Role_ID, RoleId.toRepoId(request.getContextRoleId()))
				.shallBeLogged()
				.build();
	}

	public EnqueueAvailableForSalesRequest fromEvent(final @NonNull Event event)
	{
		//noinspection DataFlowIssue
		return EnqueueAvailableForSalesRequest.builder()
				.availableForSalesQuery(JsonObjectMapperHolder.fromJsonNonNull(event.getPropertyAsString(PROPERTY_Query), AvailableForSalesQuery.class))
				.contextUserId(UserId.ofRepoIdOrNull(event.getPropertyAsInt(Env.CTXNAME_AD_User_ID, -1)))
				.contextRoleId(RoleId.ofRepoId(event.getPropertyAsInt(Env.CTXNAME_AD_Role_ID, -1)))
				.build();
	}

}
