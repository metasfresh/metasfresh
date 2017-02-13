package de.metas.ui.web.process;

import java.util.Properties;

import org.compiere.util.Env;

import com.google.common.base.MoreObjects;

import de.metas.process.IProcessDefaultParameter;
import de.metas.ui.web.window.model.IDocumentFieldView;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/* package */ final class DocumentFieldAsProcessDefaultParameter implements IProcessDefaultParameter
{
	public static final DocumentFieldAsProcessDefaultParameter of(final IDocumentFieldView field)
	{
		return new DocumentFieldAsProcessDefaultParameter(field);
	}

	private final IDocumentFieldView field;

	private DocumentFieldAsProcessDefaultParameter(final IDocumentFieldView field)
	{
		super();
		this.field = field;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(field).toString();
	}

	@Override
	public String getColumnName()
	{
		return field.getFieldName();
	}

	@Override
	public Properties getCtx()
	{
		return Env.getCtx();
	}

	@Override
	public int getWindowNo()
	{
		return field.getDocumentPath().getAD_Window_ID();
	}
}
