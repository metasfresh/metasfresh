/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.order.products_proposal.view;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.metas.cache.CCache;
import de.metas.i18n.ITranslatableString;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.JavaProcess;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowsData;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowsLoader;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

abstract class ProductsProposalViewFactoryTemplate implements IViewFactory, IViewsIndexStorage
{
	private static final String PARAM_RecordRef = "recordRef";

	private final CCache<ViewLayoutKey, ViewLayout> viewLayoutCache = CCache.<ViewLayoutKey, ViewLayout> builder()
			.cacheName(ProductsProposalViewFactoryTemplate.class.getName() + "#ViewLayout")
			.initialCapacity(1)
			.build();

	private final Cache<ViewId, ProductsProposalView> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.build();

	private final WindowId windowId;

	protected ProductsProposalViewFactoryTemplate(@NonNull final WindowId windowId)
	{
		this.windowId = windowId;
	}

	@Override
	public final void setViewsRepository(final IViewsRepository viewsRepository)
	{
	}

	@Override
	public final WindowId getWindowId()
	{
		return windowId;
	}

	@Override
	public final ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		final ViewLayoutKey key = ViewLayoutKey.of(windowId, viewDataType);
		return viewLayoutCache.getOrLoad(key, this::createViewLayout);
	}

	protected abstract ViewLayout createViewLayout(final ViewLayoutKey key);

	protected <T extends JavaProcess> Optional<ITranslatableString> getProcessCaption(@NonNull final Class<T> processClass)
	{
		return Services.get(IADProcessDAO.class)
				.retrieveProcessNameByClassIfUnique(processClass);
	}

	public final CreateViewRequest createViewRequest(final TableRecordReference recordRef)
	{
		return CreateViewRequest.builder(getWindowId())
				.setParameter(PARAM_RecordRef, recordRef)
				.build();
	}

	@Override
	public final ProductsProposalView createView(@NonNull final CreateViewRequest request)
	{
		final ProductsProposalRowsData rowsData = loadRowsData(request);

		return ProductsProposalView.builder()
				.windowId(getWindowId())
				.rowsData(rowsData)
				.processes(getRelatedProcessDescriptors())
				.build();
	}

	private ProductsProposalRowsData loadRowsData(final CreateViewRequest request)
	{
		final TableRecordReference recordRef = getRecordReference(request);
		return createRowsLoaderFromRecord(recordRef)
				.load();
	}

	private TableRecordReference getRecordReference(final CreateViewRequest request)
	{
		final TableRecordReference recordRef = request.getParameterAs(PARAM_RecordRef, TableRecordReference.class);
		if (recordRef == null)
		{
			throw new AdempiereException("Invalid request, parameter " + PARAM_RecordRef + " is not set: " + request);
		}
		return recordRef;
	}

	protected abstract ProductsProposalRowsLoader createRowsLoaderFromRecord(TableRecordReference recordRef);

	protected abstract List<RelatedProcessDescriptor> getRelatedProcessDescriptors();

	protected final RelatedProcessDescriptor createProcessDescriptor(@NonNull final Class<?> processClass)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(processClass);
		if (processId == null)
		{
			throw new AdempiereException("No processId found for " + processClass);
		}

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.anyTable().anyWindow()
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();
	}

	@Override
	public final void put(final IView view)
	{
		views.put(view.getViewId(), ProductsProposalView.cast(view));
	}

	@Nullable
	@Override
	public final ProductsProposalView getByIdOrNull(final ViewId viewId)
	{
		return views.getIfPresent(viewId);
	}

	protected final ProductsProposalView getById(final ViewId viewId)
	{
		final ProductsProposalView view = getByIdOrNull(viewId);
		if (view == null)
		{
			throw new EntityNotFoundException("View not found: " + viewId.toJson());
		}
		return view;
	}

	@Override
	public final void closeById(@NonNull final ViewId viewId, @NonNull final ViewCloseAction closeAction)
	{
		beforeViewClose(viewId, closeAction);

		views.invalidate(viewId);
		views.cleanUp();
	}

	protected void beforeViewClose(@NonNull final ViewId viewId, @NonNull final ViewCloseAction closeAction)
	{
		// nothing on this level
	}

	@Override
	public final Stream<IView> streamAllViews()
	{
		return Stream.empty();
	}

	@Override
	public final void invalidateView(final ViewId viewId)
	{
		final ProductsProposalView view = getById(viewId);
		view.invalidateAll();
	}

	@lombok.Value(staticConstructor = "of")
	protected static class ViewLayoutKey
	{
		WindowId windowId;
		JSONViewDataType viewDataType;
	}
}
