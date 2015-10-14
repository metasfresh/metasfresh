package org.eevolution.mrp.spi.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.mrp.api.IMRPSourceEvent;

/* package */class MRPSourceEvent implements IMRPSourceEvent
{
	private final Object model;
	private final boolean change;
	private final boolean delete;
	private final boolean released;
	private final boolean voided;
	private final boolean closed;
	private final boolean active;
	private final ModelChangeType changeType;

	public MRPSourceEvent(final Object model,
			final boolean change, final boolean delete,
			final boolean released,
			final boolean voided,
			final boolean closed,
			final boolean active,
			final ModelChangeType changeType)
	{
		super();
		this.model = model;

		this.change = change;
		this.delete = delete;

		this.released = released;
		this.voided = voided;
		this.closed = closed;

		this.active = active;

		this.changeType = changeType;
	}

	@Override
	public String toString()
	{
		return "MRPSourceEvent [model=" + model
				+ ", change=" + change
				+ ", delete=" + delete
				+ ", released=" + released
				+ ", voided=" + voided
				+ ", closed=" + closed
				+ ", active=" + active
				+ ", changeType=" + changeType
				+ "]";
	}

	@Override
	public Object getModel()
	{
		return model;
	}

	@Override
	public <T> T getModel(final Class<T> clazz)
	{
		return InterfaceWrapperHelper.create(getModel(), clazz);
	}

	@Override
	public <T> T getOldModel(final Class<T> clazz)
	{
		final boolean useOldValues = true;
		return InterfaceWrapperHelper.create(getModel(), clazz, useOldValues);
	}

	@Override
	public boolean isChange()
	{
		return change;
	}

	@Override
	public boolean isDelete()
	{
		return delete;
	}

	@Override
	public boolean isReleased()
	{
		return released;
	}

	@Override
	public boolean isVoided()
	{
		return voided;
	}

	@Override
	public boolean isClosed()
	{
		return closed;
	}

	@Override
	public boolean isModelActive()
	{
		return active;
	}

	@Override
	public ModelChangeType getChangeType()
	{
		return changeType;
	}

	@Override
	public boolean isNew()
	{
		final ModelChangeType changeType = getChangeType();
		return ModelChangeType.isNew(changeType);
	}
}
