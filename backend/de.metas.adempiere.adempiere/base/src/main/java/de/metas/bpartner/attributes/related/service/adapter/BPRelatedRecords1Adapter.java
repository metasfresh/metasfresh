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

package de.metas.bpartner.attributes.related.service.adapter;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.attributes.BPartnerRelatedRecordId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner_RelatedRecord1;

public class BPRelatedRecords1Adapter extends RelatedRecordsAdapter<I_C_BPartner_RelatedRecord1, BPartnerId>
{
	@Override
	protected IQuery<I_C_BPartner_RelatedRecord1> queryRecords(final @NonNull BPartnerId bpartnerId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_RelatedRecord1.class)
				.addEqualsFilter(I_C_BPartner_RelatedRecord1.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.orderBy(I_C_BPartner_RelatedRecord1.COLUMNNAME_Record_ID)
				.create();
	}

	@Override
	protected BPartnerRelatedRecordId extractRecordId(final @NonNull I_C_BPartner_RelatedRecord1 record)
	{
		return BPartnerRelatedRecordId.ofRepoId(record.getRecord_ID());
	}

	@Override
	protected I_C_BPartner_RelatedRecord1 createNewRecord(final @NonNull BPartnerId bpartnerId, @NonNull final BPartnerRelatedRecordId recordId)
	{
		final I_C_BPartner_RelatedRecord1 newRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_RelatedRecord1.class);
		newRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		newRecord.setRecord_ID(recordId.getRepoId());
		InterfaceWrapperHelper.saveRecord(newRecord);
		return newRecord;
	}
}
