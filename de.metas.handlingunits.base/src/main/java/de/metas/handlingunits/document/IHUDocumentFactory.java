package de.metas.handlingunits.document;

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


import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import de.metas.process.ProcessInfo;

public interface IHUDocumentFactory
{
	List<IHUDocument> createHUDocuments(final Properties ctx, final String tableName, final int recordId);

	<T> List<IHUDocument> createHUDocuments(final Properties ctx, Class<T> modelClass, Iterator<T> records);

	List<IHUDocument> createHUDocuments(ProcessInfo pi);

	List<IHUDocument> createHUDocumentsFromModel(Object model);

}
