package org.compiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Properties;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;

public abstract class AbstractPOCacheLocal
{
	protected static final Logger logger = LogManager.getLogger(AbstractPOCacheLocal.class);

	private final String parentColumnName;
	private final String tableName;
	private final String idColumnName;

	private final String loadWhereClause;

	protected Reference<PO> poRef = null;

	protected AbstractPOCacheLocal(final String parentColumnName, final String tableName)
	{
		super();

		Check.assumeNotEmpty(parentColumnName, "parentColumnName is null");
		Check.assumeNotEmpty(tableName, "tableName");

		this.parentColumnName = parentColumnName;
		this.tableName = tableName;

		final POInfo poInfo = POInfo.getPOInfo(tableName);
		idColumnName = poInfo.getKeyColumnName();
		if (idColumnName == null)
		{
			throw new IllegalStateException("Table " + tableName + " does not have a simple primary key");
		}

		loadWhereClause = idColumnName + "=?";
	}

	public final <T> T get(final Class<T> clazz)
	{
		final boolean requery = false;
		return get(clazz, requery);
	}

	public final Object get()
	{
		final boolean requery = false;
		return get(Object.class, requery);
	}

	protected abstract Properties getParentCtx();

	protected abstract String getParentTrxName();

	private final Reference<PO> createPOReference(final PO po)
	{
		if (po == null)
		{
			return null;
		}

		return new SoftReference<PO>(po);
	}

	private final <T> T get(final Class<T> clazz, final boolean requery)
	{
		PO po = poRef == null ? null : poRef.get();
		final int id = getId();
		if (id <= 0)
		{
			//
			// Corner case: there is no ID set but our cached PO is not new => reset PO, return null
			if (po != null
					&& !po.is_new()
					&& po.get_ID() != id // NOTE: we do this checking because we could have a valid PO with the ID=0 (e.g. M_AttributeSetInstance.M_AttributeSet_ID)
			)
			{
				poRef = null;
				return null;
			}

			if (po != null)
			{
				// Case: we have a new object => we just return it
				return InterfaceWrapperHelper.create(po, clazz);
			}
			else
			{
				// No object set, just return null
				return null;
			}
		}

		if (requery || po == null || !isValidPO(po))
		{
			final Properties ctx = getParentCtx();
			final String trxName = getParentTrxName();
			po = load(ctx, id, trxName);
			poRef = createPOReference(po);
		}
		if (po == null)
		{
			return null;
		}

		return InterfaceWrapperHelper.create(po, clazz);
	}

	public final void set(final Object obj)
	{
		final PO po = InterfaceWrapperHelper.getPO(obj);
		if (po == null && obj != null)
		{
			throw new AdempiereException("Invalid PO: " + obj);
		}

		//
		//
		if (po == null)
		{
			setId(-1);
			poRef = null;
			return;
		}

		final int id = po.get_ID();
		setId(id);

		if (isValidPO(po))
		{
			poRef = createPOReference(po);
		}
	}

	private final boolean isValidPO(final PO po)
	{
		final String parentTrxName = getParentTrxName();
		if (!isSameTrxName(parentTrxName, po.get_TrxName()))
		{
			return false;
		}

		final String tableName = getTableName();
		if (!po.get_TableName().equals(tableName))
		{
			logger.warn("PO " + po + " does not expected table: " + tableName);
			return false;
		}

		final int id = getId();
		if (id < 0)
		{
			return false;
		}

		if (po.get_ID() != id)
		{
			return false;
		}

		return true;
	}

	private boolean isSameTrxName(final String trxName1, final String trxName2)
	{
		return Services.get(ITrxManager.class).isSameTrxName(trxName1, trxName2);
	}

	private PO load(final Properties ctx, final int id, final String trxName)
	{
		if (id < 0)
		{
			return null;
		}

		// NOTE: we call MTable.getPO because we want to hit the ModelCacheService.
		// If we are using Query directly then cache won't be asked to retrieve (but it will just be asked to put to cache)

		if (id == 0)
		{
			// FIXME: this is a special case because the system will consider we want a new record. Fix this workaround
			final PO po = new Query(ctx, tableName, loadWhereClause, trxName)
					.setParameters(id)
					.firstOnly();
			return po;
		}
		else
		{
			final PO po = TableModelLoader.instance.getPO(ctx, tableName, id, trxName);
			return po;
		}
	}

	protected abstract int getId();

	protected abstract boolean setId(int id);

	public final String getTableName()
	{
		return tableName;
	}

	protected final String getParentColumnName()
	{
		return parentColumnName;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("parentColumnName", parentColumnName)
				.add("tableName", tableName)
				.add("idColumnName", idColumnName)
				.add("loadWhereClause", loadWhereClause)
				.add("po", poRef)
				.toString();
	}
}
