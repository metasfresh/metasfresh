package de.metas.ui.web.quickinput;

import org.adempiere.exceptions.AdempiereException;

import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import lombok.NonNull;
import lombok.Value;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value(staticConstructor = "of")
public class QuickInputDescriptor
{
	@NonNull
	private final DocumentEntityDescriptor entityDescriptor;
	@NonNull
	private final QuickInputLayoutDescriptor layout;
	@NonNull
	private final Class<? extends IQuickInputProcessor> processorClass;

	public IQuickInputProcessor createProcessor()
	{
		try
		{
			return processorClass.newInstance();
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed instantiating " + processorClass, ex);
		}
	}

	public DetailId getDetailId()
	{
		return entityDescriptor.getDetailId();
	}
}
