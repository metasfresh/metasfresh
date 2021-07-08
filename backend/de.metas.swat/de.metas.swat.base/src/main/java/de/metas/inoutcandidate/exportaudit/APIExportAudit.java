/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.exportaudit;

import com.google.common.collect.ImmutableList;
import de.metas.error.AdIssueId;
import de.metas.organization.OrgId;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @param <T> type of the audit items
 */
@Data
public class APIExportAudit<T extends APIExportAuditItem>
{
	private final String transactionId;

	private final OrgId orgId;

	private APIExportStatus exportStatus;

	private String forwardedData;

	private final int exportSequenceNumber;

	private AdIssueId issueId;

	private final Map<RepoIdAware, T> items;

	@Builder
	private APIExportAudit(
			@NonNull final OrgId orgId,
			@NonNull final String transactionId,
			@NonNull final int exportSequenceNumber,
			@NonNull final APIExportStatus exportStatus,
			@Nullable final String forwardedData,
			@Nullable final AdIssueId issueId,
			@NonNull @Singular final Map<RepoIdAware, T> items)
	{
		this.orgId = orgId;
		this.transactionId = transactionId;
		this.exportSequenceNumber = exportSequenceNumber;
		this.exportStatus = exportStatus;
		this.forwardedData = forwardedData;
		this.issueId = issueId;
		this.items = new HashMap<>(items);
	}

	public T getItemById(@NonNull final RepoIdAware id)
	{
		return items.get(id);
	}

	public ImmutableList<T> getItems()
	{
		return ImmutableList.copyOf(items.values());
	}

	public void addItem(@NonNull final T auditRecord)
	{
		items.put(auditRecord.getRepoIdAware(), auditRecord);
	}
}
