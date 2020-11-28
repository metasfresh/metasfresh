package de.metas.dataentry.layout.interceptor;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.dataentry.model.I_DataEntry_Section;
import de.metas.dataentry.model.I_DataEntry_SubTab;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Interceptor(I_DataEntry_SubTab.class)
public class DataEntry_SubTab
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteChildRecords(@NonNull final I_DataEntry_SubTab dataEntrySubGroupRecord)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Section.class)
				.addEqualsFilter(I_DataEntry_Section.COLUMN_DataEntry_SubTab_ID, dataEntrySubGroupRecord.getDataEntry_SubTab_ID())
				.create()
				.delete();
	}
}
