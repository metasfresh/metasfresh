/*
 * #%L
 * de.metas.business
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

package de.metas.bpartner.quick_input.service.attributes_record_adapter;

import de.metas.bpartner.attributes.BPartnerAttribute;
import de.metas.bpartner.attributes.service.attributes_record_adapter.AttributesRecordAdapter;
import de.metas.bpartner.quick_input.BPartnerQuickInputId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner_QuickInput_Attributes3;

public class BPQuickInputAttributes3RecordAdapter extends AttributesRecordAdapter<I_C_BPartner_QuickInput_Attributes3, BPartnerQuickInputId>
{
	@Override
	protected IQuery<I_C_BPartner_QuickInput_Attributes3> queryRecords(final @NonNull BPartnerQuickInputId bpartnerQuickInputId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_QuickInput_Attributes3.class)
				.addEqualsFilter(I_C_BPartner_QuickInput_Attributes3.COLUMNNAME_C_BPartner_QuickInput_ID, bpartnerQuickInputId)
				.orderBy(I_C_BPartner_QuickInput_Attributes3.COLUMNNAME_Created)
				.create();
	}

	@Override
	protected BPartnerAttribute extractAttribute(final @NonNull I_C_BPartner_QuickInput_Attributes3 record)
	{
		return BPartnerAttribute.ofCode(record.getAttributes3());
	}

	@Override
	protected I_C_BPartner_QuickInput_Attributes3 createNewRecord(final @NonNull BPartnerQuickInputId bpartnerQuickInputId, @NonNull final BPartnerAttribute attribute)
	{
		final I_C_BPartner_QuickInput_Attributes3 newRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_QuickInput_Attributes3.class);
		newRecord.setC_BPartner_QuickInput_ID(bpartnerQuickInputId.getRepoId());
		newRecord.setAttributes3(attribute.getCode());
		InterfaceWrapperHelper.saveRecord(newRecord);
		return newRecord;
	}
}
