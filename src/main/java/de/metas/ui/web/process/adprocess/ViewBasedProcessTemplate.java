package de.metas.ui.web.process.adprocess;

import java.util.stream.Stream;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.Check;
import org.compiere.Adempiere;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.process.ClientOnlyProcess;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;

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

/**
 * An {@link JavaProcess} implementation template to be used by processes which are called from views.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ClientOnlyProcess
public abstract class ViewBasedProcessTemplate extends JavaProcess
{
	@Autowired
	private transient IViewsRepository viewsRepo;

	//
	// View (internal) parameters
	static final String PARAM_ViewId = "$WEBUI_ViewId";
	@Param(parameterName = PARAM_ViewId, mandatory = true)
	private String p_WebuiViewId;
	//
	static final String PARAM_ViewSelectedIds = "$WEBUI_ViewSelectedIds";
	@Param(parameterName = PARAM_ViewSelectedIds, mandatory = true)
	private String p_WebuiViewSelectedIdsStr;
	//
	static final String PARAM_ParentViewId = "$WEBUI_ParentViewId";
	@Param(parameterName = PARAM_ParentViewId, mandatory = false)
	private String p_WebuiParentViewId;
	//
	public static final String PARAM_ParentViewSelectedIds = "$WEBUI_ParentViewSelectedIds";
	@Param(parameterName = PARAM_ParentViewSelectedIds, mandatory = false)
	private String p_WebuiParentViewSelectedIdsStr;
	//
	public static final String PARAM_ChildViewId = "$WEBUI_ChildViewId";
	@Param(parameterName = PARAM_ChildViewId, mandatory = false)
	private String p_WebuiChildViewId;
	//
	public static final String PARAM_ChildViewSelectedIds = "$WEBUI_ChildViewSelectedIds";
	@Param(parameterName = PARAM_ChildViewSelectedIds, mandatory = false)
	private String p_WebuiChildViewSelectedIdsStr;

	private IView _view;
	private ViewRowIdsSelection _viewRowIdsSelection;
	private ViewRowIdsSelection _parentViewRowIdsSelection;
	private ViewRowIdsSelection _childViewRowIdsSelection;

	protected ViewBasedProcessTemplate()
	{
		Adempiere.autowire(this);
	}

	/**
	 * Please implement {@link #checkPreconditionsApplicable()} instead of this.
	 *
	 * WARNING: The preconditions will be checked only if the extending class implements the {@link de.metas.process.IProcessPrecondition} interface.
	 *
	 * @param context
	 */
	public final ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		return checkPreconditionsApplicable();
	}

	/**
	 * WARNING: The preconditions will be checked only if the extending class implements the {@link de.metas.process.IProcessPrecondition} interface.
	 */
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected final void init(final IProcessPreconditionsContext context)
	{
		super.init(context);

		// Fetch and set view and view selected IDs from autowired process parameters
		setViewInfos(ViewAsPreconditionsContext.cast(context));
	}

	@Override
	protected void loadParametersFromContext(final boolean failIfNotValid)
	{
		super.loadParametersFromContext(failIfNotValid);

		Check.assumeNotEmpty(p_WebuiViewId, "Process parameter {} is set", PARAM_ViewId); // shall not happen
		final IView view = viewsRepo.getView(p_WebuiViewId);

		final ViewRowIdsSelection viewRowIdsSelection = ViewRowIdsSelection.of(view.getViewId(), DocumentIdsSelection.ofCommaSeparatedString(p_WebuiViewSelectedIdsStr));
		final ViewRowIdsSelection parentViewRowIdsSelection = ViewRowIdsSelection.ofNullableStrings(p_WebuiParentViewId, p_WebuiParentViewSelectedIdsStr);
		final ViewRowIdsSelection childViewRowIdsSelection = ViewRowIdsSelection.ofNullableStrings(p_WebuiChildViewId, p_WebuiChildViewSelectedIdsStr);

		setViewInfos(ViewAsPreconditionsContext.builder()
				.view(view)
				.viewRowIdsSelection(viewRowIdsSelection)
				.parentViewRowIdsSelection(parentViewRowIdsSelection)
				.childViewRowIdsSelection(childViewRowIdsSelection)
				.build());
	}

	protected final WindowId getWindowId()
	{
		return getView().getViewId().getWindowId();
	}

	private final void setViewInfos(@NonNull final ViewAsPreconditionsContext viewContext)
	{
		_view = viewContext.getView();
		_viewRowIdsSelection = viewContext.getViewRowIdsSelection();
		_parentViewRowIdsSelection = viewContext.getParentViewRowIdsSelection();
		_childViewRowIdsSelection = viewContext.getChildViewRowIdsSelection();

		// Update result from view
		// Do this only when view is not null to avoid reseting previous set info (shall not happen)
		final ProcessExecutionResult result = getResultOrNull();
		if (result != null // might be null when preconditions are checked (for example)
				&& _view != null)
		{
			result.setWebuiViewId(_view.getViewId().getViewId());
		}

	}

	protected final <T extends IView> T getView(@NonNull final Class<T> type)
	{
		Check.assumeNotNull(_view, "View loaded");
		return type.cast(_view);
	}

	@OverridingMethodsMustInvokeSuper
	protected IView getView()
	{
		Check.assumeNotNull(_view, "View loaded");
		return _view;
	}

	protected final <T extends IView> boolean isViewClass(@NonNull final Class<T> expectedViewClass)
	{
		final IView view = _view;
		if (view == null)
		{
			return false;
		}

		return expectedViewClass.isAssignableFrom(view.getClass());
	}

	protected final void invalidateView(@NonNull final ViewId viewId)
	{
		viewsRepo.invalidateView(viewId);
	}

	protected final void invalidateView()
	{
		final IView view = getView();
		invalidateView(view.getViewId());
	}

	protected final void invalidateParentView()
	{
		final IView view = getView();
		final ViewId parentViewId = view.getParentViewId();
		if (parentViewId != null)
		{
			invalidateView(parentViewId);
		}
	}

	protected final DocumentIdsSelection getSelectedRowIds()
	{
		Check.assumeNotNull(_viewRowIdsSelection, "View loaded");
		return _viewRowIdsSelection.getRowIds();
	}

	@OverridingMethodsMustInvokeSuper
	protected IViewRow getSingleSelectedRow()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		final DocumentId documentId = selectedRowIds.getSingleDocumentId();
		return getView().getById(documentId);
	}

	@OverridingMethodsMustInvokeSuper
	protected Stream<? extends IViewRow> streamSelectedRows()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		return getView().streamByIds(selectedRowIds);
	}

	protected final ViewRowIdsSelection getParentViewRowIdsSelection()
	{
		return _parentViewRowIdsSelection;
	}

	protected final ViewRowIdsSelection getChildViewRowIdsSelection()
	{
		return _childViewRowIdsSelection;
	}

	protected final <T extends IView> T getChildView(@NonNull final Class<T> viewType)
	{
		final ViewRowIdsSelection childViewRowIdsSelection = getChildViewRowIdsSelection();
		Check.assumeNotNull(childViewRowIdsSelection, "child view is set");
		final IView childView = viewsRepo.getView(childViewRowIdsSelection.getViewId());

		return viewType.cast(childView);
	}

	protected final DocumentIdsSelection getChildViewSelectedRowIds()
	{
		final ViewRowIdsSelection childViewRowIdsSelection = getChildViewRowIdsSelection();
		return childViewRowIdsSelection != null ? childViewRowIdsSelection.getRowIds() : DocumentIdsSelection.EMPTY;
	}

	protected IViewRow getChildViewSingleSelectedRow()
	{
		final DocumentIdsSelection selectedRowIds = getChildViewSelectedRowIds();
		final DocumentId rowId = selectedRowIds.getSingleDocumentId();
		return getChildView(IView.class).getById(rowId);
	}

}
