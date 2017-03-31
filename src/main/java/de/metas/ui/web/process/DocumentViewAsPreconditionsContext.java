package de.metas.ui.web.process;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Functions;
import org.adempiere.util.Functions.MemoizingFunction;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.window.datatypes.DocumentId;

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

public class DocumentViewAsPreconditionsContext implements WebuiPreconditionsContext
{
	public static final DocumentViewAsPreconditionsContext cast(final IProcessPreconditionsContext context)
	{
		return (DocumentViewAsPreconditionsContext)context;
	}

	public static final DocumentViewAsPreconditionsContext castOrNull(final IProcessPreconditionsContext context)
	{
		if (context instanceof DocumentViewAsPreconditionsContext)
		{
			return (DocumentViewAsPreconditionsContext)context;
		}
		else
		{
			return null;
		}
	}

	public static final DocumentViewAsPreconditionsContext newInstance(final IDocumentViewSelection view, final String tableName, final Collection<DocumentId> selectedDocumentIds)
	{
		return new DocumentViewAsPreconditionsContext(view, tableName, selectedDocumentIds);
	}

	private static final Logger logger = LogManager.getLogger(DocumentViewAsPreconditionsContext.class);

	private final IDocumentViewSelection view;
	private final String tableName;
	private final Set<DocumentId> selectedDocumentIds;

	private final MemoizingFunction<Class<?>, SelectedModelsList> _selectedModelsSupplier = Functions.memoizingFirstCall(this::retrieveSelectedModels);

	private DocumentViewAsPreconditionsContext(final IDocumentViewSelection view, final String tableName, final Collection<DocumentId> selectedDocumentIds)
	{
		Check.assumeNotNull(view, "Parameter view is not null");
		this.view = view;
		this.tableName = tableName;
		this.selectedDocumentIds = ImmutableSet.copyOf(selectedDocumentIds);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", tableName)
				.add("view", view)
				.add("selectedDocumentIds", selectedDocumentIds)
				.toString();
	}

	public IDocumentViewSelection getView()
	{
		return view;
	}

	public <T extends IDocumentViewSelection> T getView(final Class<T> viewType)
	{
		@SuppressWarnings("unchecked")
		final T viewCasted = (T)view;
		return viewCasted;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	public Set<DocumentId> getSelectedDocumentIds()
	{
		return selectedDocumentIds;
	}

	@Override
	public <T> T getSelectedModel(final Class<T> modelClass)
	{
		final List<T> models = getSelectedModels(modelClass);
		if (models.isEmpty())
		{
			return null;
		}
		else
		{
			if (models.size() > 1)
			{
				logger.warn("More then one selected model found for view but only one was expected: {}", view);
			}
			return models.get(0);
		}
	}

	@Override
	public <T> List<T> getSelectedModels(final Class<T> modelClass)
	{
		return _selectedModelsSupplier.apply(modelClass).getModels(modelClass);
	}

	@Override
	public int getSelectionSize()
	{
		return getSelectedDocumentIds().size();
	}

	private final SelectedModelsList retrieveSelectedModels(final Class<?> modelClass)
	{
		final List<?> models = view.retrieveModelsByIds(getSelectedDocumentIds(), modelClass);
		return SelectedModelsList.of(models, modelClass);
	}

	private static final class SelectedModelsList
	{
		private static final SelectedModelsList of(final List<?> models, final Class<?> modelClass)
		{
			if (models == null || models.isEmpty())
			{
				return EMPTY;
			}
			return new SelectedModelsList(models, modelClass);
		}

		private static final SelectedModelsList EMPTY = new SelectedModelsList();

		private final ImmutableList<?> models;
		private final Class<?> modelClass;

		/** empty constructor */
		private SelectedModelsList()
		{
			models = ImmutableList.of();
			modelClass = null;
		}

		private SelectedModelsList(final List<?> models, final Class<?> modelClass)
		{
			super();
			this.models = ImmutableList.copyOf(models);
			this.modelClass = modelClass;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("modelClass", modelClass)
					.add("models", models)
					.toString();
		}

		public <T> List<T> getModels(final Class<T> modelClass)
		{
			// If loaded models list is empty, we can return an empty list directly
			if (models.isEmpty())
			{
				return ImmutableList.of();
			}

			// If loaded models have the same model class as the requested one
			// we can simple cast & return them
			if (Objects.equals(modelClass, this.modelClass))
			{
				@SuppressWarnings("unchecked")
				final List<T> modelsCasted = (List<T>)models;
				return modelsCasted;
			}
			// If not the same class, we have to wrap them fist.
			else
			{
				return InterfaceWrapperHelper.wrapToImmutableList(models, modelClass);
			}
		}
	}
}
