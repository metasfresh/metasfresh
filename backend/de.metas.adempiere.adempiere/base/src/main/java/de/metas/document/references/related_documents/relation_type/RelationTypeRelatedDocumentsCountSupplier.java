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

package de.metas.document.references.related_documents.relation_type;

import de.metas.document.references.related_documents.RelatedDocumentsCountSupplier;
import de.metas.document.references.related_documents.RelatedDocumentsPermissions;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.MQuery;
import org.compiere.util.DB;

@ToString
class RelationTypeRelatedDocumentsCountSupplier implements RelatedDocumentsCountSupplier
{
	private final MQuery query;
	private final String targetTableName;
	private final String sqlCount;
	private final String sqlFirstKey;

	RelationTypeRelatedDocumentsCountSupplier(@NonNull final MQuery query)
	{
		this.query = query;
		this.targetTableName = query.getZoomTableName();
		final String sqlCommon = " FROM " + targetTableName + " WHERE (" + query.getWhereClause(false) + ")";
		this.sqlCount = "SELECT COUNT(1) " + sqlCommon;
		this.sqlFirstKey = "SELECT " + query.getZoomColumnName() + sqlCommon;

	}

	@Override
	public int getRecordsCount(final RelatedDocumentsPermissions permissions)
	{
		final int recordsCount = DB.getSQLValueEx(
				ITrx.TRXNAME_None,
				permissions.addAccessSQL(sqlCount, targetTableName));

		// FIXME: side effect to set MQuery.zoomValue, needed only in Swing
		if (recordsCount > 0)
		{
			final int firstKey = DB.getSQLValueEx(
					ITrx.TRXNAME_None,
					permissions.addAccessSQL(sqlFirstKey, targetTableName));
			query.setZoomValue(firstKey);
		}

		return recordsCount;

	}
}
