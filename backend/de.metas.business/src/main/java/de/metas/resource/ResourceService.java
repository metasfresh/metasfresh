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
import de.metas.product.IProductDAO;
import de.metas.product.ProductType;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.X_M_Product;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Service
public class ResourceService
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
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

	public ImmutableList<Resource> getAllActiveResources()
	{
		return resourceRepository.getAllActive();
	}

	public Stream<ResourceAssignment> queryResourceAssignments(final ResourceAssignmentQuery query)
	{
		return resourceAssignmentRepository.query(query);
	}

	public ResourceAssignment createResourceAssignment(final ResourceAssignmentCreateRequest request)
	{
		return resourceAssignmentRepository.create(request);
	}

	public ResourceAssignment changeResourceAssignment(
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

	public Stream<ResourceGroupAssignment> queryResourceGroupAssignments(final ResourceGroupAssignmentQuery query)
	{
		return resourceGroupAssignmentRepository.query(query);
	}

	public ResourceGroupAssignment createResourceGroupAssignment(final ResourceGroupAssignmentCreateRequest request)
	{
		return resourceGroupAssignmentRepository.create(request);
	}

	public ResourceGroupAssignment changeResourceGroupAssignmentById(
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
		final ResourceType resourceType = resourceTypeRepository.toResourceType(resourceTypeRecord);

		final Set<ResourceId> resourceIds = resourceRepository.getActiveResourceIdsByResourceTypeId(resourceType.getId());
		if (resourceIds.isEmpty())
		{
			return;
		}

		productDAO.updateProductsByResourceIds(resourceIds, product -> updateProductFromResourceType(product, resourceType));
	}

	private static void updateProductFromResourceType(final I_M_Product product, final ResourceType from)
	{
		product.setProductType(ProductType.Resource.getCode());
		product.setC_UOM_ID(from.getDurationUomId().getRepoId());
		product.setM_Product_Category_ID(from.getProductCategoryId().getRepoId());
	}

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
