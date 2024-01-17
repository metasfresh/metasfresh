/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.document.geo_location;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GeographicalCoordinatesWithBPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.location.GeographicalCoordinatesWithLocationId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.location.geocoding.GeographicalCoordinates;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.geo_location.GeoLocationDocumentDescriptor.LocationColumnNameType;
import de.metas.ui.web.document.geo_location.json.JsonViewGeoLocationsResult;
import de.metas.ui.web.document.geo_location.json.JsonViewRowGeoLocation;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRestController;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.ViewRowsOrderBy;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(ViewGeoLocationsRestController.ENDPOINT)
public class ViewGeoLocationsRestController
{
	private static final String PARAM_WindowId = ViewRestController.PARAM_WindowId;
	private static final String PARAM_ViewId = "viewId";
	static final String ENDPOINT = ViewRestController.ENDPOINT + "/{" + PARAM_ViewId + "}/geoLocations";

	private static final int DEFAULT_LIMIT = 500;

	private static final Logger logger = LogManager.getLogger(ViewGeoLocationsRestController.class);
	private final ILocationDAO locationsRepo = Services.get(ILocationDAO.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final UserSession userSession;
	private final GeoLocationDocumentService geoLocationDocumentService;
	private final IViewsRepository viewsRepo;

	public ViewGeoLocationsRestController(
			@NonNull final UserSession userSession,
			@NonNull final GeoLocationDocumentService geoLocationDocumentService,
			@NonNull final IViewsRepository viewsRepo)
	{
		this.userSession = userSession;
		this.geoLocationDocumentService = geoLocationDocumentService;
		this.viewsRepo = viewsRepo;
	}

	private JSONOptions newJsonOpts()
	{
		return JSONOptions.of(userSession);
	}

	@GetMapping
	public JsonViewGeoLocationsResult getAll(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@RequestParam(value = "limit", required = false, defaultValue = "0") final int limit)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);
		final int limitEffective = limit > 0 ? limit : DEFAULT_LIMIT;

		final IView view = viewsRepo.getView(viewId);
		final ImmutableSet<String> viewFieldNames = getViewFieldNames(view);
		final GeoLocationDocumentDescriptor geoLocationDescriptor = geoLocationDocumentService.getGeoLocationDocumentDescriptor(viewFieldNames);

		final ViewRowsOrderBy orderBy = ViewRowsOrderBy.of(view.getDefaultOrderBys(), newJsonOpts());
		final ViewResult rows = view.getPage(0, limitEffective, orderBy);

		final List<JsonViewRowGeoLocation> geoLocations = retrieveGeoLocations(rows, geoLocationDescriptor);

		return JsonViewGeoLocationsResult.builder()
				.viewId(viewId.toJson())
				.locations(geoLocations)
				.build();
	}

	private ImmutableSet<String> getViewFieldNames(final IView view)
	{
		final ViewLayout viewLayout = viewsRepo.getViewLayout(view.getViewId().getWindowId(), view.getViewType(), view.getProfileId(), userSession.getUserRolePermissionsKey());
		final ImmutableSet<String> fieldNames = viewLayout.getElements()
				.stream()
				.flatMap(element -> element.getFields().stream())
				.map(DocumentLayoutElementFieldDescriptor::getField)
				.collect(ImmutableSet.toImmutableSet());

		// bpartner window doesn't have a field for "c_bpartner_id", so i'm adding it like this
		return ImmutableSet.<String>builder()
				.addAll(fieldNames)
				.add(viewLayout.getIdFieldName())
				.build();
	}

	private List<JsonViewRowGeoLocation> retrieveGeoLocations(
			final ViewResult rows,
			final GeoLocationDocumentDescriptor descriptor)
	{
		if (rows.isEmpty())
		{
			return ImmutableList.of();
		}

		final LocationColumnNameType type = descriptor.getType();
		if (LocationColumnNameType.LocationId.equals(type))
		{
			final ImmutableSet<LocationId> locationIds = extractLocationIds(rows, descriptor);
			return retrieveGeoLocationsForLocationId(locationIds);
		}
		else if (LocationColumnNameType.BPartnerLocationId.equals(type))
		{
			final ImmutableSetMultimap<Integer, DocumentId> rowIdsByBPartnerLocationRepoId = extractBPartnerLocationRepoIds(rows, descriptor);
			return retrieveGeoLocationsForBPartnerLocationId(rowIdsByBPartnerLocationRepoId);
		}
		else if (LocationColumnNameType.BPartnerId.equals(type))
		{
			final ImmutableSetMultimap<BPartnerId, DocumentId> rowIdsByBPartnerId = extractBPartnerIds(rows, descriptor);
			return retrieveGeoLocationsForBPartnerId(rowIdsByBPartnerId);
		}
		else
		{
			throw new AdempiereException("Unknown " + type);
		}
	}

	private static ImmutableSet<LocationId> extractLocationIds(final ViewResult rows, final GeoLocationDocumentDescriptor descriptor)
	{
		final String locationColumnName = descriptor.getLocationColumnName();

		return rows.getPage()
				.stream()
				.map(row -> LocationId.ofRepoIdOrNull(row.getFieldValueAsInt(locationColumnName, -1)))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private List<JsonViewRowGeoLocation> retrieveGeoLocationsForLocationId(final ImmutableSet<LocationId> locationIds)
	{
		return locationsRepo.streamGeoCoordinatesByIds(locationIds)
				.map(geoCoordinatesAndLocationId -> toJsonViewRowGeoLocation(geoCoordinatesAndLocationId))
				.collect(ImmutableList.toImmutableList());
	}

	private static JsonViewRowGeoLocation toJsonViewRowGeoLocation(final GeographicalCoordinatesWithLocationId geoCoordinatesAndLocationId)
	{
		return JsonViewRowGeoLocation.builder()
				.rowId(DocumentId.of(geoCoordinatesAndLocationId.getLocationId()))
				.latitude(geoCoordinatesAndLocationId.getCoordinate().getLatitude())
				.longitude(geoCoordinatesAndLocationId.getCoordinate().getLongitude())
				.build();
	}

	private static ImmutableSetMultimap<Integer, DocumentId> extractBPartnerLocationRepoIds(final ViewResult rows, final GeoLocationDocumentDescriptor descriptor)
	{
		final String locationColumnName = descriptor.getLocationColumnName();

		final ImmutableSetMultimap.Builder<Integer, DocumentId> rowIdsByBPartnerLocationRepoId = ImmutableSetMultimap.builder();
		for (final IViewRow row : rows.getPage())
		{
			final int bpartnerLocationRepoId = row.getFieldValueAsInt(locationColumnName, -1);
			if (bpartnerLocationRepoId <= 0)
			{
				continue;
			}

			final DocumentId rowId = row.getId();

			rowIdsByBPartnerLocationRepoId.put(bpartnerLocationRepoId, rowId);
		}

		return rowIdsByBPartnerLocationRepoId.build();
	}

	private List<JsonViewRowGeoLocation> retrieveGeoLocationsForBPartnerLocationId(final ImmutableSetMultimap<Integer, DocumentId> rowIdsByBPartnerLocationRepoId)
	{
		if (rowIdsByBPartnerLocationRepoId.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<JsonViewRowGeoLocation> result = new ArrayList<>();

		final ImmutableSet<Integer> bpartnerLocationRepoIds = rowIdsByBPartnerLocationRepoId.keySet();
		for (final GeographicalCoordinatesWithBPartnerLocationId bplCoordinates : bpartnersRepo.getGeoCoordinatesByBPartnerLocationIds(bpartnerLocationRepoIds))
		{
			final int bpartnerLocationRepoId = bplCoordinates.getBpartnerLocationId().getRepoId();
			final ImmutableSet<DocumentId> rowIds = rowIdsByBPartnerLocationRepoId.get(bpartnerLocationRepoId);
			if (rowIds.isEmpty())
			{
				// shall not happen
				logger.warn("Ignored unexpected bpartnerLocationId={}. We have no rows for it.", bpartnerLocationRepoId);
				continue;
			}

			final GeographicalCoordinates coordinate = bplCoordinates.getCoordinate();

			for (final DocumentId rowId : rowIds)
			{
				result.add(JsonViewRowGeoLocation.builder()
						.rowId(rowId)
						.latitude(coordinate.getLatitude())
						.longitude(coordinate.getLongitude())
						.build());
			}
		}

		return result;
	}

	private static final ImmutableSetMultimap<BPartnerId, DocumentId> extractBPartnerIds(final ViewResult rows, final GeoLocationDocumentDescriptor descriptor)
	{
		final String locationColumnName = descriptor.getLocationColumnName();

		final ImmutableSetMultimap.Builder<BPartnerId, DocumentId> rowIdsByBPartnerRepoId = ImmutableSetMultimap.builder();
		for (final IViewRow row : rows.getPage())
		{
			final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(row.getFieldValueAsInt(locationColumnName, -1));
			if (bpartnerId == null)
			{
				continue;
			}

			final DocumentId rowId = row.getId();

			rowIdsByBPartnerRepoId.put(bpartnerId, rowId);
		}

		return rowIdsByBPartnerRepoId.build();
	}

	private List<JsonViewRowGeoLocation> retrieveGeoLocationsForBPartnerId(final ImmutableSetMultimap<BPartnerId, DocumentId> rowIdsByBPartnerId)
	{
		if (rowIdsByBPartnerId.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<JsonViewRowGeoLocation> result = new ArrayList<>();

		final ImmutableSet<BPartnerId> bpartnerIds = rowIdsByBPartnerId.keySet();
		for (final GeographicalCoordinatesWithBPartnerLocationId bplCoordinates : bpartnersRepo.getGeoCoordinatesByBPartnerIds(bpartnerIds))
		{
			final BPartnerId bpartnerId = bplCoordinates.getBPartnerId();
			final ImmutableSet<DocumentId> rowIds = rowIdsByBPartnerId.get(bpartnerId);
			if (rowIds.isEmpty())
			{
				// shall not happen
				logger.warn("Ignored unexpected bpartnerId={}. We have no rows for it.", bpartnerId);
				continue;
			}

			final GeographicalCoordinates coordinate = bplCoordinates.getCoordinate();

			for (final DocumentId rowId : rowIds)
			{
				result.add(JsonViewRowGeoLocation.builder()
						.rowId(rowId)
						.latitude(coordinate.getLatitude())
						.longitude(coordinate.getLongitude())
						.build());
			}
		}

		return result;
	}
}
