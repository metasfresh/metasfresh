/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.invoicecandidate.approvedforinvoice;

import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class ApprovedForInvoicingService
{
	@NonNull
	private final Map<String, List<IApprovedForInvoicingFinder>> tableName2Finders;

	public ApprovedForInvoicingService(@NonNull final Collection<IApprovedForInvoicingFinder> finders)
	{
		tableName2Finders = CollectionUtils.groupMultiValueByKey(finders, IApprovedForInvoicingFinder::getTableName);
	}

	public boolean areAnyCandidatesApprovedForInvoice(@NonNull final TableRecordReference documentRecordReference)
	{
		final List<IApprovedForInvoicingFinder> finders = tableName2Finders.get(documentRecordReference.getTableName());

		return finders.stream()
				.map(finder -> finder.findApprovedForReference(documentRecordReference))
				.anyMatch(invoiceCandidates -> !invoiceCandidates.isEmpty());
	}
}