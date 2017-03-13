package de.metas.ui.web.process;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.Check;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.lang.MutableInt;
import org.compiere.Adempiere;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.process.ClientOnlyProcess;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUDocumentView;
import de.metas.ui.web.view.IDocumentView;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.IDocumentViewsRepository;
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
	private transient IDocumentViewsRepository documentViewsRepo;

	//
	// View (internal) parameters
	@Param(parameterName = ProcessInstance.PARAM_ViewId, mandatory = true)
	private String p_WebuiViewId;
	@Param(parameterName = ProcessInstance.PARAM_ViewSelectedIds, mandatory = true)
	private String p_WebuiViewSelectedIdsStr;

	private IDocumentViewSelection _view;
	private transient Set<DocumentId> _selectedDocumentIds;

	protected ViewBasedProcessTemplate()
	{
		Adempiere.autowire(this);
	}

	/**
	 * Please implement {@link #checkPreconditionsApplicable()} instead of this.
	 *
	 * WARNING: The preconditions will be checked only if the extending class will implement IProcessPrecondition class.
	 *
	 * @param context
	 */
	public final ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		return checkPreconditionsApplicable();
	}

	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected final void init(IProcessPreconditionsContext context)
	{
		super.init(context);

		// Fetch and set view and view selected IDs from autowired process parameters
		final DocumentViewAsPreconditionsContext viewContext = DocumentViewAsPreconditionsContext.cast(context);
		setView(viewContext.getView(), viewContext.getSelectedDocumentIds());
	}

	@Override
	protected void loadParametersFromContext(boolean failIfNotValid)
	{
		super.loadParametersFromContext(failIfNotValid);

		// Fetch and set view and view selected IDs from autowired process parameters
		// NOTE: we assume a view ID is always provided
		final IDocumentViewSelection view = documentViewsRepo.getView(p_WebuiViewId);
		final Set<DocumentId> selectedDocumentIds = DocumentId.ofCommaSeparatedString(p_WebuiViewSelectedIdsStr);
		setView(view, selectedDocumentIds);
	}

	@OverridingMethodsMustInvokeSuper
	protected void setView(final IDocumentViewSelection view, final Set<DocumentId> selectedDocumentIds)
	{
		_view = view;
		_selectedDocumentIds = selectedDocumentIds;
	}

	protected final <T extends IDocumentViewSelection> T getView(final Class<T> type)
	{
		Check.assumeNotNull(_view, "View loaded");
		return type.cast(_view);
	}

	@OverridingMethodsMustInvokeSuper
	protected IDocumentViewSelection getView()
	{
		Check.assumeNotNull(_view, "View loaded");
		return _view;
	}

	protected final Set<DocumentId> getSelectedDocumentIds()
	{
		Check.assumeNotNull(_selectedDocumentIds, "View loaded");
		return _selectedDocumentIds;
	}

	@OverridingMethodsMustInvokeSuper
	protected IDocumentView getSingleSelectedRow()
	{
		final Set<DocumentId> selectedDocumentIds = getSelectedDocumentIds();
		final DocumentId documentId = ListUtils.singleElement(selectedDocumentIds);
		return getView().getById(documentId);
	}

	protected static <T extends IDocumentView> ProcessPreconditionsResolution checkRowsEligible(final Stream<T> rows, final Predicate<T> isEligible)
	{
		final MutableInt countNotEligible = MutableInt.zero();

		final long countAll = rows
				.map(HUDocumentView::cast)
				.peek(row -> countNotEligible.incrementIf(!row.isCU()))
				.count();
		if (countAll <= 0)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (countNotEligible.isGreaterThanZero())
		{
			return ProcessPreconditionsResolution.reject(countNotEligible + " not CU(s) selected");
		}

		return ProcessPreconditionsResolution.accept();
	}
}
