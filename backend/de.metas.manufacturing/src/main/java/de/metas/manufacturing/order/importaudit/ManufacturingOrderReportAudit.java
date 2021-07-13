package de.metas.manufacturing.order.importaudit;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.error.AdIssueId;
import de.metas.manufacturing.order.exportaudit.APITransactionId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;

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
public class ManufacturingOrderReportAudit
{
	@Getter
	private final APITransactionId transactionId;

	private String jsonRequest;
	private String jsonResponse;

	public enum ImportStatus
	{
		SUCCESS, FAILED,
	}

	@Setter(AccessLevel.NONE)
	private ImportStatus importStatus;
	@Setter(AccessLevel.NONE)
	private String errorMsg;
	@Setter(AccessLevel.NONE)
	private AdIssueId adIssueId;

	@Setter(AccessLevel.NONE)
	private final ArrayList<ManufacturingOrderReportAuditItem> items;

	public static ManufacturingOrderReportAudit newInstance(@NonNull final APITransactionId transactionId)
	{
		return ManufacturingOrderReportAudit.builder()
				.transactionId(transactionId)
				.build();
	}

	@Builder
	private ManufacturingOrderReportAudit(
			final APITransactionId transactionId,
			final String jsonRequest,
			final String jsonResponse,
			final ImportStatus importStatus,
			final String errorMsg,
			final AdIssueId adIssueId,
			@Singular final List<ManufacturingOrderReportAuditItem> items)
	{
		this.transactionId = transactionId;
		this.jsonRequest = jsonRequest;
		this.jsonResponse = jsonResponse;
		this.importStatus = importStatus;
		this.errorMsg = errorMsg;
		this.adIssueId = adIssueId;
		this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
	}

	public void addItem(@NonNull final ManufacturingOrderReportAuditItem item)
	{
		items.add(item);
	}

	public ImmutableList<ManufacturingOrderReportAuditItem> getItems()
	{
		return ImmutableList.copyOf(items);
	}

	public void markAsSuccess()
	{
		this.importStatus = ImportStatus.SUCCESS;
		this.errorMsg = null;
		this.adIssueId = null;
	}

	public void markAsFailure(@NonNull final String errorMsg, @NonNull final AdIssueId adIssueId)
	{
		this.importStatus = ImportStatus.FAILED;
		this.errorMsg = errorMsg;
		this.adIssueId = adIssueId;

		items.forEach(ManufacturingOrderReportAuditItem::markAsRolledBackIfSuccess);
	}
}
