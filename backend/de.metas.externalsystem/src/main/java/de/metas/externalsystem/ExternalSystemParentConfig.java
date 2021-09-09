/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem;

import de.metas.externalsystem.model.I_ExternalSystem_Config;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;

@Value
public class ExternalSystemParentConfig
{
	ExternalSystemParentConfigId id;
	ExternalSystemType type;
	String name;
	IExternalSystemChildConfig childConfig;
	Boolean isActive;

	@Builder
	public ExternalSystemParentConfig(
			@NonNull final ExternalSystemParentConfigId id,
			@NonNull final ExternalSystemType type,
			@NonNull final String name,
			@NonNull final IExternalSystemChildConfig childConfig,
			@NonNull final Boolean isActive)
	{
		this.id = id;
		this.type = type;
		this.name = name;
		this.childConfig = childConfig;
		this.isActive = isActive;
	}

	public ITableRecordReference getTableRecordReference()
	{
		return TableRecordReference.of(I_ExternalSystem_Config.Table_Name, this.id);
	}
}
