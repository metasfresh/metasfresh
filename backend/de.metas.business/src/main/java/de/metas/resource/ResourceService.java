/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.resource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductType;
import de.metas.product.ResourceId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.OldAndNewValues;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.I_S_Resource_Group;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Service
public class ResourceService
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final ResourceRepository resourceRepository;
	private final ResourceGroupRepository resourceGroupRepository;
	private final ResourceAssignmentRepository resourceAssignmentRepository;
	private final ResourceGroupAssignmentRepository resourceGroupAssignmentRepository;
	private final ResourceTypeRepository resourceTypeRepository;

	public ResourceService(
			@NonNull final ResourceRepository resourceRepository,
			@NonNull final ResourceGroupRepository resourceGroupRepository,
			@NonNull final ResourceAssignmentRepository resourceAssignmentRepository,
			@NonNull final ResourceGroupAssignmentRepository resourceGroupAssignmentRepository,
			@NonNull final ResourceTypeRepository resourceTypeRepository)
	{
		this.resourceRepository = resourceRepository;
		this.resourceGroupRepository = resourceGroupRepository;
		this.resourceAssignmentRepository = resourceAssignmentRepository;
		this.resourceGroupAssignmentRepository = resourceGroupAssignmentRepository;
		this.resourceTypeRepository = resourceTypeRepository;
	}

	public static ResourceService newInstanceForJUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ResourceService(
				new ResourceRepository(),
				new ResourceGroupRepository(),
				new ResourceAssignmentRepository(),
				new ResourceGroupAssignmentRepository(),
				new ResourceTypeRepository());
	}

	public Resource getResourceById(@NonNull final ResourceId resourceId)
	{
		return resourceRepository.getById(resourceId);
	}

	public List<Resource> getResourcesByIds(@NonNull final Set<ResourceId> resourceIds)
	{
		return resourceRepository.getByIds(resourceIds);
	}

	public ImmutableList<Resource> getAllActiveResources()
	{
		return resourceRepository.getAllActive();
	}

	public ImmutableSet<ResourceId> getActiveResourceIdsByGroupIds(@NonNull final Set<ResourceGroupId> groupIds)
	{
		return resourceRepository.getActiveResourceIdsByGroupIds(groupIds);
	}

	public ImmutableSet<ResourceGroupId> getGroupIdsByResourceIds(@NonNull final Set<ResourceId> resourceIds)
	{
		return resourceRepository.getGroupIdsByResourceIds(resourceIds);
	}

	public Stream<ResourceAssignment> queryResourceAssignments(final ResourceAssignmentQuery query)
	{
		return resourceAssignmentRepository.query(query);
	}

	public ResourceAssignment createResourceAssignment(final ResourceAssignmentCreateRequest request)
	{
		return resourceAssignmentRepository.create(request);
	}

	public OldAndNewValues<ResourceAssignment> changeResourceAssignment(
			@NonNull final ResourceAssignmentId id,
			@NonNull final UnaryOperator<ResourceAssignment> mapper)
	{
		return resourceAssignmentRepository.changeById(id, mapper);
	}

	public void deleteResourceAssignment(final ResourceAssignmentId id)
	{
		resourceAssignmentRepository.deleteById(id);
	}

	public ImmutableList<ResourceGroup> getAllActiveGroups()
	{
		return resourceGroupRepository.getAllActive();
	}

	public ImmutableList<ResourceGroup> getGroupsByIds(@NonNull final Set<ResourceGroupId> ids)
	{
		return resourceGroupRepository.getByIds(ids);
	}

	public ResourceGroup getGroupById(@NonNull final ResourceGroupId id)
	{
		return resourceGroupRepository.getById(id);
	}

	public Stream<ResourceGroupAssignment> queryResourceGroupAssignments(final ResourceGroupAssignmentQuery query)
	{
		return resourceGroupAssignmentRepository.query(query);
	}

	public ResourceGroupAssignment createResourceGroupAssignment(final ResourceGroupAssignmentCreateRequest request)
	{
		return resourceGroupAssignmentRepository.create(request);
	}

	public OldAndNewValues<ResourceGroupAssignment> changeResourceGroupAssignmentById(
			@NonNull final ResourceGroupAssignmentId id,
			@NonNull final UnaryOperator<ResourceGroupAssignment> mapper)
	{
		return resourceGroupAssignmentRepository.changeById(id, mapper);
	}

	public void deleteResourceGroupAssignment(final ResourceGroupAssignmentId id)
	{
		resourceGroupAssignmentRepository.deleteById(id);
	}

	public ResourceType getResourceTypeById(@NonNull final ResourceTypeId resourceTypeId)
	{
		return resourceTypeRepository.getById(resourceTypeId);
	}

	public ResourceType getResourceTypeByResourceId(@NonNull final ResourceId resourceId)
	{
		final Resource resource = resourceRepository.getById(resourceId);
		return resourceTypeRepository.getById(resource.getResourceTypeId());
	}

	public void validateResourceBeforeSave(final @NonNull I_S_Resource resourceRecord)
	{
		//
		// Validate Manufacturing Resource
		if (resourceRecord.isManufacturingResource()
				&& ManufacturingResourceType.isPlant(resourceRecord.getManufacturingResourceType())
				&& resourceRecord.getPlanningHorizon() <= 0)
		{
			throw new FillMandatoryException(I_S_Resource.COLUMNNAME_PlanningHorizon);
		}
	}

	public void onResourceChanged(@NonNull final I_S_Resource resourceRecord)
	{
		createOrUpdateProductFromResource(ResourceRepository.toResource(resourceRecord));
	}

	@NonNull
	public Optional<ResourceId> getResourceIdByValue(@NonNull final String value, @NonNull final OrgId orgId)
	{
		return resourceRepository.getResourceIdByValue(value, orgId);
	}

	private void createOrUpdateProductFromResource(final @NonNull Resource resource)
	{
		productDAO.updateProductsByResourceIds(
				ImmutableSet.of(resource.getResourceId()),
				(resourceId, product) -> {
					if (InterfaceWrapperHelper.isNew(product))
					{
						final ResourceType fromResourceType = getResourceTypeById(resource.getResourceTypeId());
						updateProductFromResourceType(product, fromResourceType);
					}

					updateProductFromResource(product, resource);
				});
	}

	private static void updateProductFromResource(@NonNull final I_M_Product product, @NonNull final Resource from)
	{
		product.setProductType(ProductType.Resource.getCode());
		product.setS_Resource_ID(from.getResourceId().getRepoId());
		product.setIsActive(from.isActive());

		product.setValue("PR" + from.getValue()); // the "PR" is a QnD solution to the possible problem that if the production resource's value is set to its ID (like '1000000") there is probably already a product with the same value.
		product.setName(from.getName().getDefaultValue());
		product.setDescription(from.getDescription());
	}

	public void onResourceBeforeDelete(@NonNull final ResourceId resourceId)
	{
		productDAO.deleteProductByResourceId(resourceId);
	}

	public void onResourceTypeChanged(final I_S_ResourceType resourceTypeRecord)
	{
		final ResourceType resourceType = ResourceTypeRepository.fromRecord(resourceTypeRecord);

		final Set<ResourceId> resourceIds = resourceRepository.getActiveResourceIdsByResourceTypeId(resourceType.getId());
		if (resourceIds.isEmpty())
		{
			return;
		}

		productDAO.updateProductsByResourceIds(resourceIds, product -> updateProductFromResourceType(product, resourceType));
	}

	@NonNull
	public Optional<ResourceGroupId> getResourceGroupId(@Nullable final ResourceId resourceId)
	{
		return Optional.ofNullable(resourceId)
				.map(this::getResourceById)
				.map(Resource::getResourceGroupId);
	}

	private static void updateProductFromResourceType(final I_M_Product product, final ResourceType from)
	{
		product.setProductType(ProductType.Resource.getCode());
		product.setC_UOM_ID(from.getDurationUomId().getRepoId());
		product.setM_Product_Category_ID(from.getProductCategoryId().getRepoId());
	}

	public void onResourceGroupChanged(final I_S_Resource_Group record)
	{
		createOrUpdateProductFromResourceGroup(ResourceGroupRepository.toResourceGroup(record));
	}

	private void createOrUpdateProductFromResourceGroup(final ResourceGroup resourceGroup)
	{
		productDAO.updateProductByResourceGroupId(
				resourceGroup.getId(),
				product -> {
					if (InterfaceWrapperHelper.isNew(product))
					{
						final UomId uomId = uomDAO.getUomIdByTemporalUnit(resourceGroup.getDurationUnit());
						product.setProductType(ProductType.Resource.getCode());
						product.setC_UOM_ID(uomId.getRepoId());
						product.setM_Product_Category_ID(resourceGroup.getProductCategoryId().getRepoId());
					}

					product.setProductType(ProductType.Resource.getCode());
					product.setS_Resource_Group_ID(resourceGroup.getId().getRepoId());
					product.setIsActive(resourceGroup.isActive());

					product.setValue("RG" + resourceGroup.getId().getRepoId());
					product.setName(resourceGroup.getName().getDefaultValue());
					product.setDescription(resourceGroup.getDescription());
				});
	}

	public void onResourceGroupBeforeDelete(final ResourceGroupId resourceGroupId)
	{
		productDAO.deleteProductByResourceGroupId(resourceGroupId);
	}

	public ImmutableSet<ResourceId> getResourceIdsByUserId(@NonNull final UserId userId)
	{
		return resourceRepository.getResourceIdsByUserId(userId);
	}

	public ResourceTypeId getResourceTypeIdByResourceId(final ResourceId resourceId)
	{
		return resourceRepository.getResourceTypeIdByResourceId(resourceId);
	}

	public ImmutableSet<ResourceId> getResourceIdsByResourceTypeIds(final ImmutableSet<ResourceTypeId> resourceTypeIds)
	{
		return resourceRepository.getResourceIdsByResourceTypeIds(resourceTypeIds);
	}

	public ImmutableSet<ResourceId> getActivePlantIds() {return resourceRepository.getActivePlantIds();}

	//
	//
	// ------------------------------------------------------------------------
	//
	//
	@UtilityClass
	@Deprecated
	public static class Legacy
	{
		public static String getResourceName(@NonNull final ResourceId resourceId)
		{
			final I_S_Resource resourceRecord = ResourceRepository.retrieveRecordById(resourceId);
			return resourceRecord.getName();
		}
	}

}
