package de.metas.ui.web.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.Util.ArrayKey;

import de.metas.ui.web.handlingunits.HUDocumentViewAttributesProvider;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.window.datatypes.DocumentType;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class DocumentViewAttributesProviderFactory
{
	public static final transient DocumentViewAttributesProviderFactory instance = new DocumentViewAttributesProviderFactory();

	private final Map<ArrayKey, Class<? extends IDocumentViewAttributesProvider>> providerClasses = new ConcurrentHashMap<>();

	private DocumentViewAttributesProviderFactory()
	{
		super();

		// FIXME: hardcoded factories
		registerProvider(DocumentType.Window, WEBUI_HU_Constants.WEBUI_HU_Window_ID, HUDocumentViewAttributesProvider.class);
	}

	public void registerProvider(final DocumentType documentType, final int documentTypeId, final Class<? extends IDocumentViewAttributesProvider> providerClass)
	{
		Check.assumeNotNull(providerClass, "Parameter providerClass is not null");
		providerClasses.put(mkKey(documentType, documentTypeId), providerClass);
	}

	public IDocumentViewAttributesProvider createProviderOrNull(final DocumentType documentType, final int documentTypeId)
	{
		final Class<? extends IDocumentViewAttributesProvider> providerClass = getProviderClassOrNull(documentType, documentTypeId);
		if (providerClass == null)
		{
			return null;
		}

		try
		{
			return providerClass.newInstance();
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Cannot instantiate " + providerClass, e);
		}
	}
	
	public boolean hasProvider(final DocumentType documentType, final int documentTypeId)
	{
		return getProviderClassOrNull(documentType, documentTypeId) != null;
	}
	
	private final Class<? extends IDocumentViewAttributesProvider> getProviderClassOrNull(final DocumentType documentType, final int documentTypeId)
	{
		return providerClasses.get(mkKey(documentType, documentTypeId));
	}

	private static final ArrayKey mkKey(final DocumentType documentType, final int documentTypeId)
	{
		return ArrayKey.of(documentType, documentTypeId);
	}
}
