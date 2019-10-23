package org.adempiere.ui.sideactions.model;

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


import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Base {@link ISideAction} implementation which implements general methods and features.
 * 
 * @author tsa
 *
 */
public abstract class AbstractSideAction implements ISideAction
{
	private static final transient String GENERATED_ID_Prefix = "ID_" + UUID.randomUUID() + "_";
	private static final transient AtomicLong GENERATED_ID_Next = new AtomicLong(0);

	private final String id;

	public AbstractSideAction()
	{
		this((String)null); // id=null => generate
	}

	public AbstractSideAction(final String id)
	{
		super();

		//
		// Set provided ID or generate a new one
		if (id == null)
		{
			this.id = GENERATED_ID_Prefix + GENERATED_ID_Next.incrementAndGet();
		}
		else
		{
			this.id = id;
		}
	}

	@Override
	public final String getId()
	{
		return id;
	}
}
