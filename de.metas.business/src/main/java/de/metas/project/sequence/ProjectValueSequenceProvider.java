package de.metas.project.sequence;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.isInstanceOf;

import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectType;
import org.springframework.stereotype.Component;

import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.ValueSequenceInfoProvider;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class ProjectValueSequenceProvider implements ValueSequenceInfoProvider
{
	@Override
	public ProviderResult computeValueInfo(@NonNull final Object modelRecord)
	{
		if (!isInstanceOf(modelRecord, I_C_Project.class))
		{
			return ProviderResult.EMPTY;
		}
		final I_C_Project project = create(modelRecord, I_C_Project.class);

		if (project.getC_ProjectType_ID() <= 0)
		{
			return ProviderResult.EMPTY;
		}

		final I_C_ProjectType projectType = project.getC_ProjectType();

		final int adSequenceId = projectType.getAD_Sequence_ProjectValue_ID();
		if (adSequenceId <= 0)
		{
			return ProviderResult.EMPTY;
		}

		final IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);
		final DocumentSequenceInfo documentSequenceInfo = documentSequenceDAO.retriveDocumentSequenceInfo(adSequenceId);

		return ProviderResult.of(documentSequenceInfo);
	}
}
