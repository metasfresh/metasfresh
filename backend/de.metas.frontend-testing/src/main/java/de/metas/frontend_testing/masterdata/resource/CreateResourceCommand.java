package de.metas.frontend_testing.masterdata.resource;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.material.planning.ManufacturingResourceType;
import de.metas.product.ResourceId;
import de.metas.resource.ResourceTypeId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_S_Resource;

import java.math.BigDecimal;

@Builder
public class CreateResourceCommand
{
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCreateResourceRequest request;
	@NonNull private final Identifier identifier;

	public JsonCreateResourceResponse execute()
	{
		final ResourceTypeId resourceTypeId = request.getResourceTypeId() != null ? request.getResourceTypeId() : MasterdataContext.DEFAULT_MANUFACTURING_RESOURCE_TYPE_ID;
		final String value = identifier.toUniqueString();
		final ManufacturingResourceType manufacturingResourceType = ManufacturingResourceType.ofCode(request.getType());

		final I_S_Resource record = InterfaceWrapperHelper.newInstance(I_S_Resource.class);
		record.setValue(value);
		record.setName(value);
		record.setS_ResourceType_ID(resourceTypeId.getRepoId());
		record.setIsAvailable(true);
		record.setPercentUtilization(BigDecimal.valueOf(100));

		record.setIsManufacturingResource(true);
		record.setManufacturingResourceType(manufacturingResourceType.getCode());
		record.setPlanningHorizon(365);

		InterfaceWrapperHelper.save(record);
		final ResourceId resourceId = ResourceId.ofRepoId(record.getS_Resource_ID());
		context.putIdentifier(identifier, resourceId);

		return JsonCreateResourceResponse.builder()
				.build();
	}
}
