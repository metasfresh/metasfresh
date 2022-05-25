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
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Service
public class ResourceService
{
	private final ResourceRepository resourceRepository;
	private final ResourceGroupRepository resourceGroupRepository;
	private final ResourceAssignmentRepository resourceAssignmentRepository;
	private final ResourceGroupAssignmentRepository resourceGroupAssignmentRepository;

	public ResourceService(
			@NonNull final ResourceRepository resourceRepository,
			@NonNull final ResourceGroupRepository resourceGroupRepository,
			@NonNull final ResourceAssignmentRepository resourceAssignmentRepository,
			@NonNull final ResourceGroupAssignmentRepository resourceGroupAssignmentRepository)
	{
		this.resourceRepository = resourceRepository;
		this.resourceGroupRepository = resourceGroupRepository;
		this.resourceAssignmentRepository = resourceAssignmentRepository;
		this.resourceGroupAssignmentRepository = resourceGroupAssignmentRepository;
	}

	public ImmutableList<Resource> getResources()
	{
		return resourceRepository.getResources();
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
}
