/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.document.invoicingpool;

import com.google.common.annotations.VisibleForTesting;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

@Service
public class DocTypeInvoicingPoolService
{
	@NonNull
	private final DocTypeInvoicingPoolRepository docTypeInvoicingPoolRepository;

	public DocTypeInvoicingPoolService(@NonNull final DocTypeInvoicingPoolRepository docTypeInvoicingPoolRepository)
	{
		this.docTypeInvoicingPoolRepository = docTypeInvoicingPoolRepository;
	}

	@VisibleForTesting
	public static DocTypeInvoicingPoolService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new DocTypeInvoicingPoolService(new DocTypeInvoicingPoolRepository());
	}

	@NonNull
	public DocTypeInvoicingPool getById(@NonNull final DocTypeInvoicingPoolId docTypeInvoicingPoolId)
	{
		return docTypeInvoicingPoolRepository.getById(docTypeInvoicingPoolId);
	}

	@NonNull
	public DocTypeInvoicingPool create(@NonNull final DocTypeInvoicingPoolCreateRequest request)
	{
		return docTypeInvoicingPoolRepository.create(request);
	}
}
