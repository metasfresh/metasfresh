/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.document.archive.config;

import de.metas.document.DocBaseType;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class DocOutboundConfigService
{
	@NonNull private final DocOutboundConfigRepository docOutboundConfigRepository;
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	public static DocOutboundConfigService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new DocOutboundConfigService(DocOutboundConfigRepository.newInstanceForUnitTesting());
	}

	@Nullable
	public final DocOutboundConfig retrieveConfigForModel(@NonNull final Object model)
	{
		final DocBaseType docBaseType = documentBL.getDocTypeId(model)
				.map(docTypeDAO::getDocBaseTypeById)
				.orElse(null);

		return docOutboundConfigRepository.getByQuery(
				DocOutboundConfigQuery.builder()
						.tableId(InterfaceWrapperHelper.getTableIdOfModel(model))
						.docBaseType(docBaseType)
						.orgId(InterfaceWrapperHelper.getOrgId(model).orElseThrow(() -> new AdempiereException("No OrgId found for " + model)))
						.build()
		);
	}
}
