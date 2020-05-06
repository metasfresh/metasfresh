package de.metas.dataentry.layout.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.dataentry.model.I_DataEntry_SubTab;
import de.metas.dataentry.model.I_DataEntry_Tab;
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
@Interceptor(I_DataEntry_Tab.class)
@Callout(I_DataEntry_Tab.class)
public class DataEntry_Tab
{
	public DataEntry_Tab()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteChildRecords(@NonNull final I_DataEntry_Tab dataEntryTabRecord)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_SubTab.class)
				.addEqualsFilter(I_DataEntry_SubTab.COLUMN_DataEntry_Tab_ID, dataEntryTabRecord.getDataEntry_Tab_ID())
				.create()
				.delete();
	}

	@CalloutMethod(columnNames = I_DataEntry_Tab.COLUMNNAME_DataEntry_TargetWindow_ID)
	public void setSeqNo(@NonNull final I_DataEntry_Tab dataEntryTabRecord)
	{
		if (dataEntryTabRecord.getDataEntry_TargetWindow_ID() <= 0)
		{
			return;
		}
		dataEntryTabRecord.setSeqNo(maxSeqNo(dataEntryTabRecord) + 10);
	}

	private int maxSeqNo(@NonNull final I_DataEntry_Tab dataEntryTabRecord)
	{
		return Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Tab.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Tab.COLUMN_DataEntry_TargetWindow_ID, dataEntryTabRecord.getDataEntry_TargetWindow_ID())
				.create()
				.maxInt(I_DataEntry_Tab.COLUMNNAME_SeqNo);
	}
}
