package de.metas.ui.web.window.descriptor.factory.standard;

<<<<<<< HEAD
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import org.compiere.model.I_AD_Window;
import org.springframework.stereotype.Service;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.cache.CCache;
import de.metas.ui.web.dataentry.window.descriptor.factory.DataEntrySubTabBindingDescriptorBuilder;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.exceptions.DocumentLayoutBuildException;
import lombok.NonNull;
<<<<<<< HEAD
=======
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_AD_Window;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.concurrent.CopyOnWriteArraySet;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
<<<<<<< HEAD
public class DefaultDocumentDescriptorFactory implements DocumentDescriptorFactory
{
	@NonNull
	final DataEntrySubTabBindingDescriptorBuilder dataEntrySubTabBindingDescriptorBuilder;

	private final CCache<WindowId, DocumentDescriptor> documentDescriptorsByWindowId = new CCache<>(I_AD_Window.Table_Name + "#DocumentDescriptor", 50);

	private final Set<WindowId> unsupportedWindowIds = new HashSet<>();

	/* package */ DefaultDocumentDescriptorFactory(
			@NonNull final DataEntrySubTabBindingDescriptorBuilder dataEntrySubTabBindingDescriptorBuilder)
	{
		this.dataEntrySubTabBindingDescriptorBuilder = dataEntrySubTabBindingDescriptorBuilder;
	}
=======
@RequiredArgsConstructor
public class DefaultDocumentDescriptorFactory implements DocumentDescriptorFactory
{
	@NonNull private final DataEntrySubTabBindingDescriptorBuilder dataEntrySubTabBindingDescriptorBuilder;

	private final CCache<WindowId, DocumentDescriptor> documentDescriptorsByWindowId = new CCache<>(I_AD_Window.Table_Name + "#DocumentDescriptor", 50);
	private final CopyOnWriteArraySet<WindowId> unsupportedWindowIds = new CopyOnWriteArraySet<>();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@Override
	public void invalidateForWindow(@NonNull final WindowId windowId)
	{
		documentDescriptorsByWindowId.remove(windowId);
	}

	@Override
	public DocumentDescriptor getDocumentDescriptor(@NonNull final WindowId windowId)
	{
		try
		{
<<<<<<< HEAD
			return documentDescriptorsByWindowId.getOrLoad(windowId, () -> createDocumentDescriptorLoader(windowId).load());
=======
			return documentDescriptorsByWindowId.getOrLoad(windowId, this::loadDocumentDescriptor);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		catch (final Exception e)
		{
			throw DocumentLayoutBuildException.wrapIfNeeded(e);
		}
	}

<<<<<<< HEAD
	private DefaultDocumentDescriptorLoader createDocumentDescriptorLoader(@NonNull final WindowId windowId)
	{
		return new DefaultDocumentDescriptorLoader(
				windowId.toAdWindowId(),
				dataEntrySubTabBindingDescriptorBuilder);
=======
	private DocumentDescriptor loadDocumentDescriptor(final WindowId windowId)
	{
		return newLoader(windowId).load();
	}

	private DefaultDocumentDescriptorLoader newLoader(@NonNull final WindowId windowId)
	{
		return new DefaultDocumentDescriptorLoader(windowId.toAdWindowId(), dataEntrySubTabBindingDescriptorBuilder);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/**
	 * @return {@code false} if the given {@code windowId} * <br>
<<<<<<< HEAD
	 *         is {@code null} <br>
	 *         or its {@link WindowId#isInt()} returns {@code false}
	 *         or it was declared unsupported via {@link #addUnsupportedWindowId(WindowId)}.
=======
	 * is {@code null} <br>
	 * or its {@link WindowId#isInt()} returns {@code false}
	 * or it was declared unsupported via {@link #addUnsupportedWindowId(WindowId)}.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	@Override
	public boolean isWindowIdSupported(@Nullable final WindowId windowId)
	{
<<<<<<< HEAD
		if (windowId == null || !windowId.isInt() || unsupportedWindowIds.contains(windowId))
		{
			return false;
		}
		return true;
=======
		return windowId != null && windowId.isInt() && !unsupportedWindowIds.contains(windowId);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/**
	 * Tell this instance that it shall not attempt to work with the given window ID.
	 */
	public void addUnsupportedWindowId(@NonNull final WindowId windowId)
	{
		unsupportedWindowIds.add(windowId);
	}
}
