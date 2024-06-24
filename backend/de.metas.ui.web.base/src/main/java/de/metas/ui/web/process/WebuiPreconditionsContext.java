package de.metas.ui.web.process;

import com.google.common.collect.ImmutableList;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface WebuiPreconditionsContext extends IProcessPreconditionsContext
{
	@Nullable
	DisplayPlace getDisplayPlace();

	default boolean isConsiderTableRelatedProcessDescriptors(@NonNull ProcessHandlerType processHandlerType) {return true;}

	default List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return ImmutableList.of();
	}
}
