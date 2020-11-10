package de.metas.manufacturing.order.importaudit;

import javax.annotation.Nullable;

import org.eevolution.api.PPCostCollectorId;

import de.metas.error.AdIssueId;
import de.metas.material.planning.pporder.PPOrderId;
import lombok.Builder;
import lombok.Data;

/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Data
@Builder
public class ManufacturingOrderReportAuditItem
{
	@Nullable
	private final PPOrderId orderId;

	@Nullable
	private final PPCostCollectorId costCollectorId;

	@Nullable
	private final String jsonRequest;
	@Nullable
	private final String jsonResponse;

	public enum ImportStatus
	{
		SUCCESS, FAILED, SUCCESS_BUT_ROLLED_BACK
	}

	@Nullable
	ImportStatus importStatus;

	@Nullable
	private final String errorMsg;
	@Nullable
	private final AdIssueId adIssueId;

	public void markAsRolledBackIfSuccess()
	{
		if (importStatus == ImportStatus.SUCCESS)
		{
			importStatus = ImportStatus.SUCCESS_BUT_ROLLED_BACK;
		}
	}
}
