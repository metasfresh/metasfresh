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

package de.metas.document.interceptor;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.ICountryIdProvider;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_DocType_Sequence;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Interceptor(I_C_DocType_Sequence.class)
@Component
public class C_DocType_Sequence
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private static final AdMessageKey MSG_COUNTRY_ID_PROVIDER_DOESNT_EXIST_FOR_DOCBASETYPE = AdMessageKey.of("C_DocType_Sequence_countryId_provider_missing");

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE},
			ifColumnsChanged = I_C_DocType_Sequence.COLUMNNAME_C_Country_ID)
	public void isHandledByCountryIdProvider(final I_C_DocType_Sequence doctypeSequenceRecord)
	{
		if(doctypeSequenceRecord.getC_Country_ID() == 0)
		{
			return;
		}

		final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
		final List<ICountryIdProvider> countryIdProviders = documentNoFactory.getCountryIdProviders();
		final DocTypeId docTypeId = DocTypeId.ofRepoId(doctypeSequenceRecord.getC_DocType_ID());
		final DocBaseAndSubType docBaseAndSubType = docTypeDAO.getDocBaseAndSubTypeById(docTypeId);

		for(final ICountryIdProvider countryIdProvider : countryIdProviders)
		{
			if(countryIdProvider.isHandled(docBaseAndSubType))
			{
				return;
			}
		}

		throw new AdempiereException(MSG_COUNTRY_ID_PROVIDER_DOESNT_EXIST_FOR_DOCBASETYPE).markAsUserValidationError();
	}

}
