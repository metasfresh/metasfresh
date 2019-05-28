package de.metas.location.geocoding.process;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerLocationRepository;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.provider.locationAreaSearch.LocationAreaSearchDocumentFilterConverter;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class C_BPartner_Window_AreaSearchProcess extends JavaProcess
{
	private final IViewsRepository viewsRepo = Adempiere.getBean(IViewsRepository.class);

	@Param(parameterName = "Distance", mandatory = true)
	private BigDecimal distance;

	@Override protected String doIt()
	{
		final Set<Integer> bpLocationIds = getSelectedIncludedRecordIds(I_C_BPartner_Location.class);

		final I_C_Location location;
		if (!bpLocationIds.isEmpty())
		{
			// retrieve the selected location
			final LocationId locationId = Adempiere.getBean(BPartnerLocationRepository.class).getByBPartnerLocationId(BPartnerLocationId.ofRepoId(getRecord_ID(), bpLocationIds.iterator().next())).getLocationId();
			location = Services.get(ILocationDAO.class).getById(LocationId.ofRepoId(locationId.getRepoId()));
		}
		else
		{
			// retrieve the first bpartner location available
			final List<I_C_BPartner_Location> partnerLocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(BPartnerId.ofRepoId(getRecord_ID()));
			if (partnerLocations.isEmpty())
			{
				return MSG_InvalidArguments;
			}
			else
			{
				location = Services.get(ILocationDAO.class).getById(LocationId.ofRepoId(partnerLocations.get(0).getC_Location_ID()));
			}
		}

		final ITranslatableString countryName = Services.get(ICountryDAO.class).getCountryNameById(CountryId.ofRepoId(location.getC_Country_ID()));

		final DocumentFilter filter = DocumentFilter.builder()
				.setFilterId(LocationAreaSearchDocumentFilterConverter.FILTER_ID)
				.addParameter(DocumentFilterParam.ofNameEqualsValue(LocationAreaSearchDocumentFilterConverter.PARAM_Address1, location.getAddress1()))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(LocationAreaSearchDocumentFilterConverter.PARAM_City, location.getCity()))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(LocationAreaSearchDocumentFilterConverter.PARAM_Postal, location.getPostal()))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(LocationAreaSearchDocumentFilterConverter.PARAM_CountryId, LookupValue.IntegerLookupValue.of(location.getC_Country_ID(), countryName, null)))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(LocationAreaSearchDocumentFilterConverter.PARAM_Distance, distance))
				.build();

		final WindowId windowId = WindowId.of(getProcessInfo().getAdWindowId().getRepoId());
		final IView view = viewsRepo.createView(CreateViewRequest.builder(windowId)
				.setFilters(ImmutableList.of(filter))
				.build());

		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
				.viewId(view.getViewId().toJson())
				.target(ProcessExecutionResult.ViewOpenTarget.NewBrowserTab)
				.build());

		return MSG_OK;
	}
}
