package org.compiere.swing.table;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

import javax.swing.JComponent;

import org.jdesktop.swingx.JXTable;

import com.google.common.base.Function;

public class AnnotatedJXTable extends JXTable
{
	private static final long serialVersionUID = 1L;
	private Function<Integer, Integer> convertRowIndexToModelFunction;

	AnnotatedJXTable()
	{
		super();
	}

	@Override
	protected final JComponent createDefaultColumnControl()
	{
		return new AnnotatedColumnControlButton(this);
	}

	public final Function<Integer, Integer> getConvertRowIndexToModelFunction()
	{
		if (convertRowIndexToModelFunction == null)
		{
			convertRowIndexToModelFunction = new Function<Integer, Integer>()
			{
				@Override
				public Integer apply(final Integer viewRowIndex)
				{
					return convertRowIndexToModel(viewRowIndex);
				}
			};
		}
		return convertRowIndexToModelFunction;

	}
}
