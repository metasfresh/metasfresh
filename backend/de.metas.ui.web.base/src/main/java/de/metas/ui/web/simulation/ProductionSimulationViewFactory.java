/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.simulation;

import de.metas.i18n.ITranslatableString;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.process.IADProcessDAO;
import de.metas.ui.web.simulation.process.C_Order_ProductionSimulationView_Launcher;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;

import static de.metas.ui.web.simulation.process.C_Order_ProductionSimulationView_Launcher.VIEW_FACTORY_PARAM_DOCUMENT_LINE_DESCRIPTOR;

@ViewFactory(windowId = ProductionSimulationViewFactory.ProductionSimulationView_String)
public class ProductionSimulationViewFactory implements IViewFactory
{
	public static final String ProductionSimulationView_String = "simulation";
	public static final WindowId WINDOWID = WindowId.fromJson(ProductionSimulationView_String);

	private final  IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private final ProductionSimulationRowsRepository rowsRepo;

	public ProductionSimulationViewFactory(
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final PPOrderCandidateDAO ppOrderCandidateDAO)
	{
		this.rowsRepo = ProductionSimulationRowsRepository.builder()
				.candidateRepositoryRetrieval(candidateRepositoryRetrieval)
				.ppOrderCandidateDAO(ppOrderCandidateDAO)
				.build();
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOWID);

		final OrderLineDescriptor orderLineDescriptor = request.getParameterAs(VIEW_FACTORY_PARAM_DOCUMENT_LINE_DESCRIPTOR, OrderLineDescriptor.class);

		Check.assumeNotNull(orderLineDescriptor, VIEW_FACTORY_PARAM_DOCUMENT_LINE_DESCRIPTOR + " is mandatory!");

		final ProductionSimulationRows rows = rowsRepo.getByOrderLineDescriptor(orderLineDescriptor);

		return ProductionSimulationView.builder()
				.viewId(viewId)
				.rows(rows)
				.build();
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		final ITranslatableString caption = adProcessDAO.retrieveProcessNameByClassIfUnique(C_Order_ProductionSimulationView_Launcher.class)
				.orElse(null);

		return ViewLayout.builder()
				.setWindowId(WINDOWID)
				.setCaption(caption)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.setAllowOpeningRowDetails(false)
				.setTreeCollapsible(true)
				.setHasTreeSupport(true)
				.addElementsFromViewRowClass(ProductionSimulationRow.class, viewDataType)
				.build();
	}
}
