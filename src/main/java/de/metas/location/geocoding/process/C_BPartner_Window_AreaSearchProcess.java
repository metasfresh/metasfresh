package de.metas.location.geocoding.process;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

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
import de.metas.bpartner.service.BPartnerLocationInfoRepository;
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
import de.metas.ui.web.document.geo_location.GeoLocationAwareDescriptor;
import de.metas.ui.web.document.geo_location.LocationAreaSearchDocumentFilterConverter;
import de.metas.ui.web.document.geo_location.LocationAreaSearchDocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.Services;
import lombok.NonNull;

public class C_BPartner_Window_AreaSearchProcess extends JavaProcess
{
	private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);
	private final DocumentCollection documentCollection = SpringContextHolder.instance.getBean(DocumentCollection.class);
	private final BPartnerLocationInfoRepository bpartnerLocationInfoRepo = SpringContextHolder.instance.getBean(BPartnerLocationInfoRepository.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final ICountryDAO countriesRepo = Services.get(ICountryDAO.class);
	private final ILocationDAO locationsRepo = Services.get(ILocationDAO.class);

	@Param(parameterName = "Distance", mandatory = true)
	private BigDecimal distance;

	@Param(parameterName = "VisitorsAddress")
	private boolean visitorsAddress;

	@Override
	protected String doIt()
	{
		final I_C_Location location = getSelectedLocationOrFirstAvailable();

		final DocumentFilter filter = createAreaSearchFilter(location);

		final IView view = createViewWithFilter(filter);

		getResult().setWebuiViewToOpen(
				ProcessExecutionResult.WebuiViewToOpen.builder()
						.viewId(view.getViewId().toJson())
						.target(ProcessExecutionResult.ViewOpenTarget.NewBrowserTab)
						.build());

		return MSG_OK;
	}

	private IView createViewWithFilter(final DocumentFilter filter)
	{
		final WindowId windowId = getWindowId();
		return viewsRepo.createView(CreateViewRequest.builder(windowId)
				.setFilters(ImmutableList.of(filter))
				.build());
	}

	@NonNull
	private WindowId getWindowId()
	{
		return WindowId.of(getProcessInfo().getAdWindowId().getRepoId());
	}

	@NonNull
	private DocumentFilter createAreaSearchFilter(final I_C_Location location)
	{
		final ITranslatableString countryName = countriesRepo.getCountryNameById(CountryId.ofRepoId(location.getC_Country_ID()));

		// this descriptor applies the filter when the view is opened instead of needing to press the search button 1 time
		final DocumentEntityDescriptor bpartnerEntityDescriptor = documentCollection.getDocumentEntityDescriptor(getWindowId());
		final GeoLocationAwareDescriptor descriptor = LocationAreaSearchDocumentFilterDescriptorsProviderFactory.createGeoLocationAwareDescriptorOrNull(
				bpartnerEntityDescriptor.getTableName(),
				bpartnerEntityDescriptor.getFields());

		return DocumentFilter.builder()
				.setFilterId(LocationAreaSearchDocumentFilterConverter.FILTER_ID)
				.addInternalParameter(DocumentFilterParam.ofNameEqualsValue(LocationAreaSearchDocumentFilterConverter.PARAM_LocationAreaSearchDescriptor, Objects.requireNonNull(descriptor)))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(LocationAreaSearchDocumentFilterConverter.PARAM_Address1, location.getAddress1()))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(LocationAreaSearchDocumentFilterConverter.PARAM_City, location.getCity()))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(LocationAreaSearchDocumentFilterConverter.PARAM_Postal, location.getPostal()))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(LocationAreaSearchDocumentFilterConverter.PARAM_CountryId, LookupValue.IntegerLookupValue.of(location.getC_Country_ID(), countryName, null)))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(LocationAreaSearchDocumentFilterConverter.PARAM_Distance, distance))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(LocationAreaSearchDocumentFilterConverter.PARAM_VisitorsAddress, visitorsAddress))
				.build();
	}

	private I_C_Location getSelectedLocationOrFirstAvailable()
	{
		final Set<Integer> bpLocationIds = getSelectedIncludedRecordIds(I_C_BPartner_Location.class);
		if (!bpLocationIds.isEmpty())
		{
			// retrieve the selected location
			final LocationId locationId = bpartnerLocationInfoRepo.getByBPartnerLocationId(BPartnerLocationId.ofRepoId(getRecord_ID(), bpLocationIds.iterator().next())).getLocationId();
			return locationsRepo.getById(LocationId.ofRepoId(locationId.getRepoId()));
		}
		else
		{
			// retrieve the first bpartner location available
			final List<I_C_BPartner_Location> partnerLocations = bpartnersRepo.retrieveBPartnerLocations(BPartnerId.ofRepoId(getRecord_ID()));
			if (!partnerLocations.isEmpty())
			{
				return locationsRepo.getById(LocationId.ofRepoId(partnerLocations.get(0).getC_Location_ID()));
			}
		}

		throw new AdempiereException("@NotFound@ @C_Location_ID@");
	}
}
