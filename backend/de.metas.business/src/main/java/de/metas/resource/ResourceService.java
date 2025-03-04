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

import com.google.common.collect.ImmutableSet;
import de.metas.product.ResourceId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.Adempiere;
import org.compiere.model.I_S_Resource;
import org.springframework.stereotype.Service;

@Service
public class ResourceService
{
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