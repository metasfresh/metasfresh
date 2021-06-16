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

package de.metas.bpartner.attributes.service.attributes_record_adapter;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.attributes.BPartnerAttribute;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_User_Attribute;

public class BPContactAttributesRecordAdapter extends AttributesRecordAdapter<I_AD_User_Attribute, BPartnerContactId>
{
	@Override
	protected IQuery<I_AD_User_Attribute> queryRecords(final @NonNull BPartnerContactId contactId)
	{
		return queryBL.createQueryBuilder(I_AD_User_Attribute.class)
				.addEqualsFilter(I_AD_User_Attribute.COLUMNNAME_AD_User_ID, contactId)
				.orderBy(I_AD_User_Attribute.COLUMNNAME_Created)
				.create();
	}

	@Override
	protected BPartnerAttribute extractAttribute(final @NonNull I_AD_User_Attribute record)
	{
		return BPartnerAttribute.ofCode(record.getAttribute());
	}

	@Override
	protected I_AD_User_Attribute createNewRecord(final @NonNull BPartnerContactId contactId, @NonNull final BPartnerAttribute attribute)
	{
		final I_AD_User_Attribute newRecord = InterfaceWrapperHelper.newInstance(I_AD_User_Attribute.class);
		newRecord.setAD_User_ID(contactId.getRepoId());
		newRecord.setAttribute(attribute.getCode());
		InterfaceWrapperHelper.saveRecord(newRecord);
		return newRecord;
	}
}
