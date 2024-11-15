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
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ResourceService
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final ResourceRepository resourceRepository;

	public ResourceService(
			@NonNull final ResourceRepository resourceRepository)
	{
		this.resourceRepository = resourceRepository;
	}

	public static ResourceService newInstanceForJUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ResourceService(
				new ResourceRepository());
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

	@NonNull
	public Optional<ResourceId> getResourceIdByValue(@NonNull final String value, @NonNull final OrgId orgId)
	{
		return resourceRepository.getResourceIdByValue(value, orgId);
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