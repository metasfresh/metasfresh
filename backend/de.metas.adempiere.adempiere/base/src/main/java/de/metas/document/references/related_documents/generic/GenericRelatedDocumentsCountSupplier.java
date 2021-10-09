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

package de.metas.document.references.related_documents.generic;

import de.metas.document.references.related_documents.RelatedDocumentsCountSupplier;
import de.metas.document.references.related_documents.RelatedDocumentsPermissions;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import lombok.ToString;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_M_RMA;
import org.compiere.model.MQuery;
import org.compiere.util.DB;
import org.slf4j.Logger;

@ToString
class GenericRelatedDocumentsCountSupplier implements RelatedDocumentsCountSupplier
{
	private static final Logger logger = LogManager.getLogger(GenericRelatedDocumentsCountSupplier.class);

	private final GenericRelatedDocumentDescriptor descriptor;
	private final String sourceTableName;
	private final String targetTableName;

	private final String sqlCount;

	GenericRelatedDocumentsCountSupplier(
			final MQuery query,
			final GenericRelatedDocumentDescriptor descriptor,
			final String sourceTableName)
	{
		this.descriptor = descriptor;
		this.sourceTableName = sourceTableName;
		this.targetTableName = query.getTableName();

		this.sqlCount = buildCountSQL(query, descriptor, sourceTableName);
	}

	private static String buildCountSQL(
			final MQuery query,
			final GenericRelatedDocumentDescriptor descriptor,
			final String sourceTableName)
	{
		String sqlCount = "SELECT COUNT(1) FROM " + query.getTableName() + " WHERE " + query.getWhereClause(false);

		SOTrx soTrx = descriptor.getSoTrx();
		if (soTrx != null && descriptor.isTargetHasIsSOTrxColumn())
		{
			//
			// For RMA, Material Receipt window should be loaded for
			// IsSOTrx=true and Shipment for IsSOTrx=false
			// TODO: fetch the additional SQL from window's first tab where clause
			final AdWindowId AD_Window_ID = descriptor.getTargetWindowId();
			if (I_M_RMA.Table_Name.equals(sourceTableName) && (AD_Window_ID.getRepoId() == 169 || AD_Window_ID.getRepoId() == 184))
			{
				soTrx = soTrx.invert();
			}

			// TODO: handle the case when IsSOTrx is a virtual column

			sqlCount += " AND IsSOTrx=" + DB.TO_BOOLEAN(soTrx.toBoolean());
		}

		return sqlCount;
	}

	@Override
	public int getRecordsCount(final RelatedDocumentsPermissions permissions)
	{
		try
		{
			final String sqlCountWithPermissions = permissions.addAccessSQL(sqlCount, targetTableName);
			return DB.getSQLValueEx(ITrx.TRXNAME_None, sqlCountWithPermissions);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed counting records in {} for {} using SQL: {}", sourceTableName, descriptor, sqlCount, ex);
			return 0;
		}
	}
}
