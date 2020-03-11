/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.attachments.listener;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.i18n.AdMessageId;
import de.metas.javaclasses.JavaClassId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_Table_AttachmentListener;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

@Repository
public class TableAttachmentListenerRepository
{
	private final CCache<AdTableId, ImmutableList<AttachmentListenerSettings>> cache = CCache.<AdTableId, ImmutableList<AttachmentListenerSettings>> builder()
			.cacheName("listenersByAdTableId")
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(100)
			.tableName(I_AD_Table_AttachmentListener.Table_Name)
			.build();

	public ImmutableList<AttachmentListenerSettings> getById(final AdTableId adTableId)
	{
		return cache.getOrLoad(adTableId, this::retrieveAttachmentListenerSettings);
	}

	/**
	 * Queries {@link I_AD_Table_AttachmentListener} for listeners linked to the given {@link AdTableId}.
	 *
	 * @param adTableId DB identifier of the table.
	 * @return list of {@link AttachmentListenerSettings} ordered by {@link I_AD_Table_AttachmentListener#getSeqNo()}.
	 */
	private ImmutableList<AttachmentListenerSettings> retrieveAttachmentListenerSettings(final AdTableId adTableId )
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Table_AttachmentListener.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Table_AttachmentListener.COLUMNNAME_AD_Table_ID, adTableId.getRepoId())
				.orderBy(I_AD_Table_AttachmentListener.COLUMNNAME_SeqNo)
				.create()
				.list()
				.stream()
				.map(this::buildAttachmentListenerSettings)
				.collect(ImmutableList.toImmutableList());
	}

	private AttachmentListenerSettings buildAttachmentListenerSettings( final I_AD_Table_AttachmentListener record )
	{
		return AttachmentListenerSettings.builder()
				.isSendNotification(record.isSendNotification())
				.listenerJavaClassId(JavaClassId.ofRepoId(record.getAD_JavaClass_ID()))
				.adMessageId(AdMessageId.ofRepoIdOrNull(record.getAD_Message_ID()))
				.build();
	}
}
