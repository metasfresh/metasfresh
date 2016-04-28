package de.metas.ui.web.vaadin.window.prototype.order.propertyDescriptor.gridWindowVO;

import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindowVO;
import org.compiere.util.Env;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor.Builder;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants;
import de.metas.ui.web.vaadin.window.prototype.order.propertyDescriptor.IPropertyDescriptorProvider;

/*
 * #%L
 * metasfresh-webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class VOPropertyDescriptorProvider implements IPropertyDescriptorProvider
{

	@Override
	public PropertyDescriptor provideEntryWindowdescriptor()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PropertyDescriptor provideForWindow(int AD_Window_ID)
	{
		final GridWindowVO gridWindowVO = GridWindowVO.create(Env.getCtx(), 0, AD_Window_ID);

		final Builder builder = PropertyDescriptor.builder().setPropertyName(WindowConstants.PROPERTYNAME_WindowRoot);

		for (final GridTabVO tab : gridWindowVO.getTabs())
		{

		}

		return builder.build();
	}

}
