package de.metas.elasticsearch;

import org.adempiere.model.ModelColumn;

/*
 * #%L
 * de.metas.elasticsearch
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

public interface IESModelIndexerBuilder
{
	/**
	 * Builds the {@link IESModelIndexer} and registers it to {@link IESModelIndexingService}.
	 */
	IESModelIndexer buildAndRegister();

	/**
	 * Sets the index type.
	 * 
	 * By default, the index type is the same as the table name.
	 * 
	 * @param indexType
	 */
	IESModelIndexerBuilder setIndexType(String indexType);

	/**
	 * Trigger indexing when the given document type changes
	 * 
	 * @param documentClass class of documents that we track
	 * @param modelIdsExtractor function which returns the model IDs which are linked to a particular document
	 */
	<DocumentType, ModelType> IESModelIndexerBuilder triggerOnDocumentChanged(Class<DocumentType> documentClass, ModelColumn<ModelType, DocumentType> modelParentColumn);

	/**
	 * Trigger "remove document from index" when the record is deleted from database.
	 */
	IESModelIndexerBuilder triggerOnDelete();
}