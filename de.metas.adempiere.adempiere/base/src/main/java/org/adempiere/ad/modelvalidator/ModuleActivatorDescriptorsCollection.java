package org.adempiere.ad.modelvalidator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.util.GuavaCollectors;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public final class ModuleActivatorDescriptorsCollection implements Iterable<ModuleActivatorDescriptor>
{
	public static ModuleActivatorDescriptorsCollection of(@NonNull final List<ModuleActivatorDescriptor> descriptors)
	{
		return new ModuleActivatorDescriptorsCollection(descriptors);
	}

	private final ImmutableList<ModuleActivatorDescriptor> descriptorsOrdered;
	private final ImmutableMap<String, ModuleActivatorDescriptor> descriptorsByClassname;

	private ModuleActivatorDescriptorsCollection(@NonNull final List<ModuleActivatorDescriptor> descriptors)
	{
		this.descriptorsOrdered = descriptors.stream()
				.sorted(Comparator.comparing(ModuleActivatorDescriptor::getSeqNo)
						.thenComparing(ModuleActivatorDescriptor::getId))
				.collect(ImmutableList.toImmutableList());

		descriptorsByClassname = descriptors.stream()
				.collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(ModuleActivatorDescriptor::getClassname));
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("descriptorsCount", descriptorsOrdered.size())
				.toString();
	}

	@Override
	public Iterator<ModuleActivatorDescriptor> iterator()
	{
		return descriptorsOrdered.iterator();
	}

	public boolean containsClassname(@NonNull final String classname)
	{
		return descriptorsByClassname.containsKey(classname);
	}
}
