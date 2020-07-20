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

import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @param <T> type of the audit items
 */
@Data
public class APIExportAudit<T extends APIExportAuditItem>
{
	private final String transactionId;

	private final Map<RepoIdAware, T> items;

	@Builder
	private APIExportAudit(
			@NonNull final String transactionId,
			@NonNull @Singular final Map<RepoIdAware, T> items)
	{
		this.transactionId = transactionId;
		this.items = new HashMap<>(items);
	}

	public T getItemById(@NonNull final RepoIdAware id )
	{
		return items.get(id);
	}

	public void addItem(@NonNull final T auditRecord)
	{
		items.put(auditRecord.getRepoIdAware(), auditRecord);
	}
}
