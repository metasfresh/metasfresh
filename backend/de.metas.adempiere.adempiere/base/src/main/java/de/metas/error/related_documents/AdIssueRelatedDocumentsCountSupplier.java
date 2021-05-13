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

package de.metas.error.related_documents;

import de.metas.document.references.related_documents.RelatedDocumentsCountSupplier;
import de.metas.document.references.related_documents.RelatedDocumentsPermissions;
import de.metas.error.IssueCategory;
import de.metas.error.IssueCountersByCategory;
import lombok.NonNull;
import lombok.ToString;

import java.util.function.Supplier;

@ToString
class AdIssueRelatedDocumentsCountSupplier implements RelatedDocumentsCountSupplier
{
	private final Supplier<IssueCountersByCategory> issueCountersSupplier;
	private final IssueCategory issueCategory;

	public AdIssueRelatedDocumentsCountSupplier(
			@NonNull final Supplier<IssueCountersByCategory> issueCountersSupplier,
			@NonNull final IssueCategory issueCategory)
	{
		this.issueCountersSupplier = issueCountersSupplier;
		this.issueCategory = issueCategory;
	}

	@Override
	public int getRecordsCount(final RelatedDocumentsPermissions permissions)
	{
		return issueCountersSupplier.get().getCountOrZero(issueCategory);
	}
}
