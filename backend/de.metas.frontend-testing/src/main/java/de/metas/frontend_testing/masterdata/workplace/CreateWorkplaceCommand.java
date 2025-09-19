package de.metas.frontend_testing.masterdata.workplace;

import com.google.common.collect.ImmutableMap;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.frontend_testing.masterdata.user.JsonLoginUserRequest;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceCreateRequest;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.qrcode.WorkplaceQRCode;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.Map;

@Builder
public class CreateWorkplaceCommand
{
	@NonNull public final WorkplaceService workplaceService;
	@NonNull private final MasterdataContext context;
	@NonNull private final Map<String, JsonWorkplaceRequest> createWorkplaceRequests;
	@Nullable Map<String, JsonLoginUserRequest> loginRequests;

	public ImmutableMap<String, JsonWorkplaceResponse> execute()
	{
		final ImmutableMap<String, JsonWorkplaceResponse> responses = createWorkplaces();
		createUserAssignments();

		return responses;
	}

	public ImmutableMap<String, JsonWorkplaceResponse> createWorkplaces()
	{
		return CollectionUtils.mapValues(ImmutableMap.copyOf(createWorkplaceRequests), this::createWorkplace);
	}

	public JsonWorkplaceResponse createWorkplace(final String identifierStr, final JsonWorkplaceRequest request)
	{
		final Identifier identifier = Identifier.ofString(identifierStr);
		final WarehouseId warehouseId = request.getWarehouse() != null
				? context.getId(request.getWarehouse(), WarehouseId.class)
				: context.getIdOfType(WarehouseId.class);
		final Workplace workplace = workplaceService.create(WorkplaceCreateRequest.builder()
				.name(identifier.toUniqueString())
				.warehouseId(warehouseId)
				.build());
		context.putIdentifier(identifier, workplace.getId());

		return JsonWorkplaceResponse.builder()
				.name(workplace.getName())
				.qrCode(WorkplaceQRCode.ofWorkplace(workplace).toGlobalQRCodeJsonString())
				.build();
	}

	private void createUserAssignments()
	{
		if (loginRequests == null || loginRequests.isEmpty()) {return;}
		loginRequests.forEach(this::createUserAssignment);
	}

	private void createUserAssignment(final String userIdentifierStr, final JsonLoginUserRequest userRequest)
	{
		if (userRequest.getWorkplace() == null) {return;}

		final WorkplaceId workplaceId = context.getId(userRequest.getWorkplace(), WorkplaceId.class);
		final Identifier userIdentifier = Identifier.ofString(userIdentifierStr);
		final UserId userId = context.getId(userIdentifier, UserId.class);
		workplaceService.assignWorkplace(userId, workplaceId);
	}

}
