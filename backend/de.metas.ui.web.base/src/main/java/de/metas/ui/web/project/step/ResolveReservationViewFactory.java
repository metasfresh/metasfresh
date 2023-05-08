/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.project.step;

import de.metas.i18n.ITranslatableString;
import de.metas.process.IADProcessDAO;
import de.metas.project.ProjectId;
import de.metas.project.workorder.stepresource.WOProjectStepResourceService;
import de.metas.ui.web.project.step.process.C_Project_WO_Step_ResolveReservationView_Launcher;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.DefaultViewsRepositoryStorage;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.ui.web.project.step.process.C_Project_WO_Step_ResolveReservationView_Launcher.VIEW_FACTORY_PARAM_WO_PROJECT_ID;

@ViewFactory(windowId = ResolveReservationViewFactory.ResolveReservationView_String)
public class ResolveReservationViewFactory implements IViewFactory, IViewsIndexStorage
{
	public static final String ResolveReservationView_String = "resolveReservation";
	public static final WindowId WINDOWID = WindowId.fromJson(ResolveReservationView_String);

	@NonNull
	private final DefaultViewsRepositoryStorage views = new DefaultViewsRepositoryStorage(Duration.ofHours(1));

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	@NonNull
	private final WOProjectStepResourceService woProjectStepResourceService;
	@NonNull
	private final WOProjectStepRowInvalidateService woProjectStepRowInvalidateService;

	public ResolveReservationViewFactory(
			@NonNull final WOProjectStepResourceService woProjectStepResourceService,
			@NonNull final WOProjectStepRowInvalidateService woProjectStepRowInvalidateService)
	{
		this.woProjectStepResourceService = woProjectStepResourceService;
		this.woProjectStepRowInvalidateService = woProjectStepRowInvalidateService;
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOWID);

		final ProjectId projectId = Optional.ofNullable(request.getParameterAs(VIEW_FACTORY_PARAM_WO_PROJECT_ID, ProjectId.class))
				.orElseThrow(() -> new AdempiereException("C_Project_ID cannot be missing at this stage!"));

		final WOProjectStepResourceRows rows = WOProjectStepResourceRowsLoader.builder()
				.woProjectStepResourceService(woProjectStepResourceService)
				.woProjectStepRowInvalidateService(woProjectStepRowInvalidateService)
				.projectId(projectId)
				.build()
				.loadRows();

		return ResolveReservationView.builder()
				.viewId(viewId)
				.rows(rows)
				.processes(request.getAdditionalRelatedProcessDescriptors())
				.build();
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		final ITranslatableString caption = adProcessDAO.retrieveProcessNameByClassIfUnique(C_Project_WO_Step_ResolveReservationView_Launcher.class)
				.orElse(null);

		return ViewLayout.builder()
				.setWindowId(WINDOWID)
				.setCaption(caption)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.setAllowOpeningRowDetails(false)
				.setTreeCollapsible(true)
				.setHasTreeSupport(false)
				.addElementsFromViewRowClass(WOProjectStepResourceRow.class, viewDataType)
				.build();
	}

	@Override
	public void setViewsRepository(final IViewsRepository viewsRepository)
	{
		// nothing
	}

	@Override
	public WindowId getWindowId()
	{
		return WINDOWID;
	}

	@Override
	public void put(final IView view)
	{
		views.put(view);
	}

	@Nullable
	@Override
	public IView getByIdOrNull(final ViewId viewId)
	{
		return ResolveReservationView.cast(views.getByIdOrNull(viewId));
	}

	@Override
	public void closeById(final ViewId viewId, final ViewCloseAction closeAction)
	{
		views.closeById(viewId, closeAction);
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return views.streamAllViews();
	}

	@Override
	public void invalidateView(final ViewId viewId)
	{
		views.invalidateView(viewId);
	}
}
