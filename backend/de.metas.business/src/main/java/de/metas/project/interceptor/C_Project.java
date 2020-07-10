package de.metas.project.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.model.CopyRecordFactory;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Component;

import de.metas.document.sequence.IDocumentNoBuilderFactory;
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
@Callout(I_C_Project.class)
public class C_Project
{
	public C_Project()
	{
		// register ourselves
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
		CopyRecordFactory.enableForTableName(I_C_Project.Table_Name);
	}

	@CalloutMethod(columnNames = I_C_Project.COLUMNNAME_C_ProjectType_ID)
	public void updateValue(@NonNull final I_C_Project projectRecord)
	{
		if (projectRecord.getC_ProjectType_ID() <= 0)
		{
			return;
		}

		final IDocumentNoBuilderFactory documentNoBuilderFactory = Services.get(IDocumentNoBuilderFactory.class);
		final String value = documentNoBuilderFactory
				.createValueBuilderFor(projectRecord)
				.setFailOnError(false)
				.build();

		projectRecord.setValue(value);
	}
}
