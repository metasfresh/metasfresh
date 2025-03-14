package de.metas.frontend_testing.masterdata.resource;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_S_Resource;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Builder
public class ResourceCommand
{
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonResourceRequest request;
	@NonNull private final Identifier identifier;

	public JsonResourceResponse execute()
	{
		final String value = identifier.toUniqueString();

		final I_S_Resource resource = InterfaceWrapperHelper.newInstance(I_S_Resource.class);
		resource.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		resource.setValue(value);
		resource.setName(value);
		resource.setManufacturingResourceType(request.getManufacturingResourceTypeCode());
		resource.setS_ResourceType_ID(MasterdataContext.DEFAULT_RESOURCE_TYPE_ID.getRepoId());

		saveRecord(resource);

		final ResourceId resourceId = ResourceId.ofRepoId(resource.getS_Resource_ID());
		context.putIdentifier(identifier, resourceId);

		return JsonResourceResponse.builder()
				.code(value)
				.build();
	}
}
