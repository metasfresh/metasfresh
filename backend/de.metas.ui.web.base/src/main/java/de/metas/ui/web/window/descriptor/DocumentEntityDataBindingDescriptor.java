package de.metas.ui.web.window.descriptor;

<<<<<<< HEAD
import javax.annotation.Nullable;

import de.metas.ui.web.window.model.DocumentsRepository;

=======
import de.metas.ui.web.window.model.DocumentsRepository;

import javax.annotation.Nullable;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface DocumentEntityDataBindingDescriptor
{
	/**
	 * @return repository or might throw exception if the repository is not configured
	 */
	DocumentsRepository getDocumentsRepository();

	/**
	 * @return true if repository versioning is supported for this entity
	 */
	default boolean isVersioningSupported()
	{
		return false;
	}

	@FunctionalInterface
<<<<<<< HEAD
	public interface DocumentEntityDataBindingDescriptorBuilder
	{
		final DocumentEntityDataBindingDescriptorBuilder NULL = () -> null;
=======
	interface DocumentEntityDataBindingDescriptorBuilder
	{
		DocumentEntityDataBindingDescriptorBuilder NULL = () -> null;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		@Nullable
		DocumentEntityDataBindingDescriptor getOrBuild();
	}
}
