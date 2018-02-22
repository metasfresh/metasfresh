package de.metas.handlingunits.client.terminal.editor.model.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.handlingunits.client
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


import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.handlingunits.client.terminal.editor.model.filter.IHUKeyFilter;
import de.metas.handlingunits.model.I_C_POS_HUEditor_Filter;
import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.model.I_AD_JavaClass;

/* package */class HUFilterModel
{
	//
	// Services
	private final transient IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);

	private final I_C_POS_HUEditor_Filter filter;
	private final IHUKeyFilter huKeyFilter;
	private Object value = null;

	public HUFilterModel(final I_C_POS_HUEditor_Filter filter)
	{
		Check.assumeNotNull(filter, "filter not null");
		this.filter = filter;

		final int javaClassId = filter.getAD_JavaClass_ID();
		final I_AD_JavaClass jc = loadOutOfTrx(javaClassId, I_AD_JavaClass.class);
		huKeyFilter = javaClassBL.newInstance(jc);
	}

	public IHUKeyFilter getHUKeyFilter()
	{
		return huKeyFilter;
	}

	public void setValue(final Object value)
	{
		this.value = value;
	}

	public Object getValue()
	{
		return value;
	}

	public int getDisplayType()
	{
		return filter.getAD_Reference_ID();
	}
}
