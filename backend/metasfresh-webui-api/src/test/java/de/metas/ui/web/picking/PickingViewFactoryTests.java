package de.metas.ui.web.picking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.ui.web.picking.packageable.PackageableRowsRepository;
import de.metas.ui.web.picking.packageable.PackageableViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.json.JSONViewDataType;

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

public class PickingViewFactoryTests
{
	/**
	 * Verifies that {@link PackageableViewFactory#createView(de.metas.ui.web.view.CreateViewRequest)} still works,<br>
	 * because when adding certain stuff one might break the builder.
	 */
	@Test
	public void testCreateView()
	{
		final PackageableRowsRepository pickingViewRepo = new PackageableRowsRepository();
		final PickingCandidateService pickingCandidateService = Mockito.mock(PickingCandidateService.class);

		final PackageableViewFactory pickingViewFactory = new PackageableViewFactory(pickingViewRepo, pickingCandidateService);
		final IView view = pickingViewFactory.createView(CreateViewRequest.builder(PickingConstants.WINDOWID_PickingView, JSONViewDataType.grid).build());
		assertThat(view).isNotNull();
	}
}
