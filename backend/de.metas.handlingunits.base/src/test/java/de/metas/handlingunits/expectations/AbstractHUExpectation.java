package de.metas.handlingunits.expectations;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.util.Services;
import org.adempiere.util.test.AbstractExpectation;
import org.adempiere.util.text.annotation.ToStringBuilder;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.impl.HUStorageDAO;

public class AbstractHUExpectation<ParentExpectationType> extends AbstractExpectation<ParentExpectationType>
{
	// services
	@ToStringBuilder(skip = true)
	protected final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@ToStringBuilder(skip = true)
	protected final IHUStorageDAO huStorageDAO = new HUStorageDAO();

	public AbstractHUExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	/** No parent constructor */
	protected AbstractHUExpectation()
	{
		super();
	}

}
