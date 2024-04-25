/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.externalsystem.pcm;

import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.pcm.source.PCMContentSourceLocalFile;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class ExternalSystemPCMConfig implements IExternalSystemChildConfig
{
	@NonNull
	OrgId orgId;
	
	@NonNull
	ExternalSystemPCMConfigId id;

	@NonNull
	ExternalSystemParentConfigId parentId;

	@NonNull
	String value;

	@Nullable
	PCMContentSourceLocalFile contentSourceLocalFile;

	@NonNull
	List<TaxCategoryPCMMapping> taxCategoryPCMMappingList;
	
	@NonNull
	public static ExternalSystemPCMConfig cast(@NonNull final IExternalSystemChildConfig childCondig)
	{
		return (ExternalSystemPCMConfig)childCondig;
	}
}
