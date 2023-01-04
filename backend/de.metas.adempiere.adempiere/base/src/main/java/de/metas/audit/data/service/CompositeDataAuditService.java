/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.audit.data.service;

import de.metas.audit.data.IMasterDataExportAuditService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompositeDataAuditService
{
	private final List<IMasterDataExportAuditService> masterDataExportAuditServices;

	public CompositeDataAuditService(@NonNull final List<IMasterDataExportAuditService> masterDataExportAuditServices)
	{
		this.masterDataExportAuditServices = masterDataExportAuditServices;
	}

	public void performDataAuditForRequest(@NonNull final GenericDataExportAuditRequest auditRequest)
	{
		for (final IMasterDataExportAuditService masterDataAuditService : masterDataExportAuditServices)
		{
			if (masterDataAuditService.isHandled(auditRequest))
			{
				masterDataAuditService.performDataAuditForRequest(auditRequest);
			}
		}
	}
}
