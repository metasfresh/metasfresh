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

import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Properties;

/**
 * Extends {@link AbstractPOCacheLocal} by also holding a reference to the cached PO's "parent" (e.g. a C_BPartner's C_Order).
 * Allows to set e.g. a new C_BPartner_ID to that order.
 */
public final class POCacheLocal extends AbstractPOCacheLocal
{
	private final Reference<PO> parentPORef;

	public static POCacheLocal newInstance(@NonNull final PO parent, @NonNull final String parentColumnName, @NonNull final String tableName)
	{
		return new POCacheLocal(parent, parentColumnName, tableName);
	}

	private POCacheLocal(
			@NonNull final PO parent,
			@NonNull final String parentColumnName,
			@NonNull final String tableName)
	{
		super(parentColumnName, tableName);

		Check.assumeNotEmpty(parentColumnName, "parentColumnName is null");
		Check.assumeNotEmpty(tableName, "tableName");

		this.parentPORef = new WeakReference<>(parent);
	}

	private PO getParentPO()
	{
		final PO parentPO = parentPORef.get();
		if (parentPO == null)
		{
			// cleanup
			this.poRef = null;

			// throw exception
			throw new AdempiereException("Parent PO reference expired");
		}

		return parentPO;
	}

	@Override
	protected Properties getParentCtx()
	{
		return getParentPO().getCtx();
	}

	@Override
	protected String getParentTrxName()
	{
		return getParentPO().get_TrxName();
	}

	@Override
	protected int getId()
	{
		final PO parentPO = getParentPO();
		final String parentColumnName = getParentColumnName();
		return parentPO.get_ValueAsInt(parentColumnName);
	}

	@Override
	protected boolean setId(final int id)
	{
		final PO parentPO = getParentPO();
		final Integer value = id < 0 ? null : id;
		final String parentColumnName = getParentColumnName();
		final boolean ok = parentPO.set_ValueOfColumn(parentColumnName, value);
		if (!ok)
		{
			logger.warn("Cannot set " + parentColumnName + "=" + id + " to " + parentPO);
		}
		return ok;
	}

	public POCacheLocal copy(@NonNull final PO parentPO)
	{
		final POCacheLocal poCacheLocalNew = newInstance(parentPO, getParentColumnName(), getTableName());
		poCacheLocalNew.poRef = this.poRef;
		return poCacheLocalNew;
	}
}
