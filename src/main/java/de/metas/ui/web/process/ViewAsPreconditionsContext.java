package de.metas.ui.web.process;

import java.util.List;
import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Functions;
import org.adempiere.util.Functions.MemoizingFunction;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.Builder;
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

@Value
public class ViewAsPreconditionsContext implements WebuiPreconditionsContext
{
	public static final ViewAsPreconditionsContext cast(final IProcessPreconditionsContext context)
	{
		return (ViewAsPreconditionsContext)context;
	}

	public static final ViewAsPreconditionsContext castOrNull(final IProcessPreconditionsContext context)
	{
		if (context instanceof ViewAsPreconditionsContext)
		{
			return (ViewAsPreconditionsContext)context;
		}
		else
		{
			return null;
		}
	}

	private static final Logger logger = LogManager.getLogger(ViewAsPreconditionsContext.class);

	private final IView view;
	private final String tableName;
	private final WindowId windowId;

	private final ViewRowIdsSelection viewRowIdsSelection;
	private final ViewRowIdsSelection parentViewRowIdsSelection;
	private final ViewRowIdsSelection childViewRowIdsSelection;

	private final MemoizingFunction<Class<?>, SelectedModelsList> _selectedModelsSupplier = Functions.memoizingFirstCall(this::retrieveSelectedModels);

	@Builder
	private ViewAsPreconditionsContext(
			@NonNull final IView view,
			@NonNull final ViewRowIdsSelection viewRowIdsSelection,
			final ViewRowIdsSelection parentViewRowIdsSelection,
			final ViewRowIdsSelection childViewRowIdsSelection)
	{
		Check.assumeNotNull(view, "Parameter view is not null");
		this.view = view;
		this.windowId = view.getViewId().getWindowId();

		this.viewRowIdsSelection = viewRowIdsSelection;
		this.parentViewRowIdsSelection = parentViewRowIdsSelection;
		this.childViewRowIdsSelection = childViewRowIdsSelection;

		final DocumentIdsSelection selectedRowIds = viewRowIdsSelection.getRowIds();
		if (selectedRowIds.isSingleDocumentId())
		{
			this.tableName = view.getTableNameOrNull(selectedRowIds.getSingleDocumentId());
		}
		else
		{
			this.tableName = view.getTableNameOrNull(null);
		}
	}

	public DocumentIdsSelection getSelectedRowIds()
	{
		return viewRowIdsSelection.getRowIds();
	}

	public <T extends IView> T getView(final Class<T> viewType)
	{
		@SuppressWarnings("unchecked")
		final T viewCasted = (T)view;
		return viewCasted;
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return view.getAdditionalRelatedProcessDescriptors();
	}

	@Override
	public int getAD_Window_ID()
	{
		return windowId.toIntOr(-1);
	}

	@Override
	public int getSingleSelectedRecordId()
	{
		final DocumentId rowId = getSelectedRowIds().getSingleDocumentId();
		final TableRecordReference recordRef = view.getTableRecordReferenceOrNull(rowId);
		if (recordRef == null)
		{
			throw new AdempiereException("Cannot extract Record_ID from single selected rowId: " + rowId);
		}
		return recordRef.getRecord_ID();
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
		return getSelectedRowIds().size();
	}

	@Override
	public boolean isNoSelection()
	{
		return getSelectedRowIds().isEmpty();
	}

	@Override
	public boolean isMoreThanOneSelected()
	{
		return getSelectedRowIds().isMoreThanOneDocumentId();
	}

	private final SelectedModelsList retrieveSelectedModels(final Class<?> modelClass)
	{
		final List<?> models = view.retrieveModelsByIds(getSelectedRowIds(), modelClass);
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
