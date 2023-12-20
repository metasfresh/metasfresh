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
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Value
public class ExternalSystemParentConfig
{
	ExternalSystemParentConfigId id;
	ExternalSystemType type;
	String name;
	boolean active;
	OrgId orgId;
	IExternalSystemChildConfig childConfig;
	Boolean writeAudit;

	@Getter(AccessLevel.NONE)
	String auditFileFolder;

	@Builder(toBuilder = true)
	public ExternalSystemParentConfig(
			@NonNull final ExternalSystemParentConfigId id,
			@NonNull final ExternalSystemType type,
			@NonNull final String name,
			@NonNull final Boolean active,
			@NonNull final OrgId orgId,
			@NonNull final IExternalSystemChildConfig childConfig,
			@NonNull final Boolean writeAudit,
			@Nullable final String auditFileFolder)
	{
		if (!type.equals(childConfig.getId().getType()))
		{
			throw new AdempiereException("Child and parent ExternalSystemType don't match!")
					.appendParametersToMessage()
					.setParameter("childConfig", childConfig)
					.setParameter("parentType", type);
		}

		this.id = id;
		this.type = type;
		this.name = name;
		this.orgId = orgId;
		this.childConfig = childConfig;
		this.active = active;
		this.writeAudit = writeAudit;

		this.auditFileFolder = writeAudit
				? Check.assumeNotNull(auditFileFolder, "If writeAudit==true, then auditFileFolder is set")
				: null;
	}

	public ITableRecordReference getTableRecordReference()
	{
		return TableRecordReference.of(I_ExternalSystem_Config.Table_Name, this.id);
	}

	@Nullable
	public String getAuditEndpointIfEnabled()
	{
		if (writeAudit)
		{
			return auditFileFolder;
		}

		return null;
	}
}
