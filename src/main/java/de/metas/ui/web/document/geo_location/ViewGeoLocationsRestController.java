package de.metas.ui.web.document.geo_location;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.X_C_Location;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;

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
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@RestController
@RequestMapping(ViewGeoLocationsRestController.ENDPOINT)
public class ViewGeoLocationsRestController
{
	private static final String PARAM_WindowId = ViewRestController.PARAM_WindowId;
	private static final String PARAM_ViewId = "viewId";
	static final String ENDPOINT = ViewRestController.ENDPOINT + "/{" + PARAM_ViewId + "}/geoLocations";

	private static final Logger logger = LogManager.getLogger(ViewGeoLocationsRestController.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
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
		final int limitEffective = limit > 0 ? limit : 500;

		final IView view = viewsRepo.getView(viewId);
		final ImmutableSet<String> viewFieldNames = getViewFieldNames(view);
		final GeoLocationDocumentDescriptor geoLocationDescriptor = geoLocationDocumentService.getGeoLocationDocumentDescriptor(viewFieldNames);

		final ViewRowsOrderBy orderBy = ViewRowsOrderBy.of(view.getDefaultOrderBys(), newJsonOpts());
		final ViewResult rows = view.getPage(0, limitEffective, orderBy);
		final ImmutableList<RowIdAndLocationRelatedId> rowIdAndLocationRelatedIds = rows.getPage()
				.stream()
				.map(row -> extractRowIdAndLocationRelatedId(row, geoLocationDescriptor))
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());

		final List<JsonViewRowGeoLocation> geoLocations = retrieveGeoLocations(rowIdAndLocationRelatedIds, geoLocationDescriptor);

		return JsonViewGeoLocationsResult.builder()
				.locations(geoLocations)
				.build();
	}

	private ImmutableSet<String> getViewFieldNames(final IView view)
	{
		final ViewLayout viewLayout = viewsRepo.getViewLayout(view.getViewId().getWindowId(), view.getViewType(), view.getProfileId());
		return viewLayout.getElements()
				.stream()
				.flatMap(element -> element.getFields().stream())
				.map(DocumentLayoutElementFieldDescriptor::getField)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static RowIdAndLocationRelatedId extractRowIdAndLocationRelatedId(final IViewRow row, final GeoLocationDocumentDescriptor descriptor)
	{
		final String locationColumnName = descriptor.getLocationColumnName();
		final int locationRelatedId = row.getFieldValueAsInt(locationColumnName, -1);
		return locationRelatedId > 0
				? new RowIdAndLocationRelatedId(row.getId(), locationRelatedId)
				: null;
	}

	private List<JsonViewRowGeoLocation> retrieveGeoLocations(
			final ImmutableList<RowIdAndLocationRelatedId> locationRelatedIds,
			final GeoLocationDocumentDescriptor geoLocationDescriptor)
	{
		if (locationRelatedIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final LocationColumnNameType type = geoLocationDescriptor.getType();
		if (LocationColumnNameType.LocationId.equals(type))
		{
			return retrieveGeoLocationsForLocationId(locationRelatedIds);
		}
		else if (LocationColumnNameType.BPartnerLocationId.equals(type))
		{
			return retrieveGeoLocationsForBPartnerLocationId(locationRelatedIds);
		}
		else if (LocationColumnNameType.BPartnerId.equals(type))
		{
			return retrieveGeoLocationsForBPartnerId(locationRelatedIds);
		}
		else
		{
			throw new AdempiereException("Unknown " + type);
		}
	}

	private List<JsonViewRowGeoLocation> retrieveGeoLocationsForLocationId(final ImmutableList<RowIdAndLocationRelatedId> locationRelatedIds)
	{
		final ImmutableSet<Integer> locationRepoIds = locationRelatedIds
				.stream()
				.map(RowIdAndLocationRelatedId::getLocationRelatedId)
				.collect(ImmutableSet.toImmutableSet());

		return queryBL.createQueryBuilder(I_C_Location.class)
				.addInArrayFilter(I_C_Location.COLUMNNAME_C_Location_ID, locationRepoIds)
				.addEqualsFilter(I_C_Location.COLUMNNAME_GeocodingStatus, X_C_Location.GEOCODINGSTATUS_Resolved)
				.create()
				.listColumns(I_C_Location.COLUMNNAME_C_Location_ID, I_C_Location.COLUMNNAME_Latitude, I_C_Location.COLUMNNAME_Longitude)
				.stream()
				.map(row -> toJsonViewRowGeoLocationFromLocationRecord(row))
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());
	}

	private static JsonViewRowGeoLocation toJsonViewRowGeoLocationFromLocationRecord(final Map<String, Object> row)
	{
		try
		{
			final DocumentId rowId = DocumentId.of(NumberUtils.asInt(row.get(I_C_Location.COLUMNNAME_C_Location_ID), -1));
			final BigDecimal latitude = NumberUtils.asBigDecimal(row.get(I_C_Location.COLUMNNAME_Latitude), null);
			final BigDecimal longitude = NumberUtils.asBigDecimal(row.get(I_C_Location.COLUMNNAME_Longitude), null);
			return JsonViewRowGeoLocation.builder()
					.rowId(rowId)
					.latitude(latitude)
					.longitude(longitude)
					.build();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed converting {} to {}", row, JsonViewRowGeoLocation.class, ex);
			return null;
		}
	}

	private List<JsonViewRowGeoLocation> retrieveGeoLocationsForBPartnerLocationId(final ImmutableList<RowIdAndLocationRelatedId> locationRelatedIds)
	{
		final ImmutableSetMultimap<Integer, DocumentId> rowIdsByBPLocationId = locationRelatedIds
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						RowIdAndLocationRelatedId::getLocationRelatedId,
						RowIdAndLocationRelatedId::getRowId));

		final ImmutableSet<Integer> bpLocationRepoIds = rowIdsByBPLocationId.keySet();

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = "SELECT "
				+ " bpl." + I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID
				+ ", l." + I_C_Location.COLUMNNAME_Latitude
				+ ", l." + I_C_Location.COLUMNNAME_Longitude
				+ " FROM " + I_C_BPartner_Location.Table_Name + " bpl "
				+ " INNER JOIN " + I_C_Location.Table_Name + " l on l.C_Location_ID=bpl.C_Location_ID"
				+ " WHERE " + DB.buildSqlList("bpl." + I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, bpLocationRepoIds, sqlParams)
				+ " AND l." + I_C_Location.COLUMNNAME_GeocodingStatus + "=?";
		sqlParams.add(X_C_Location.GEOCODINGSTATUS_Resolved);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final List<JsonViewRowGeoLocation> geoLocations = new ArrayList<>();
			while (rs.next())
			{
				final int bpLocationRepoId = rs.getInt(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID);
				final BigDecimal latitude = rs.getBigDecimal(I_C_Location.COLUMNNAME_Latitude);
				final BigDecimal longitude = rs.getBigDecimal(I_C_Location.COLUMNNAME_Longitude);
				if (latitude == null || longitude == null)
				{
					// shall not happen
					logger.warn("Ignored location for bpartnerLocationId={} because the coordonate is not valid: lat={}, long={}", bpLocationRepoId, latitude, longitude);
					continue;
				}

				final ImmutableSet<DocumentId> rowIds = rowIdsByBPLocationId.get(bpLocationRepoId);
				if (rowIds.isEmpty())
				{
					// shall not happen
					logger.warn("Ignored unexpected bpartnerLocationId={}. We have no rows for it.", bpLocationRepoId);
					continue;
				}

				for (final DocumentId rowId : rowIds)
				{
					geoLocations.add(JsonViewRowGeoLocation.builder()
							.rowId(rowId)
							.latitude(latitude)
							.longitude(longitude)
							.build());
				}
			}

			return geoLocations;
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private List<JsonViewRowGeoLocation> retrieveGeoLocationsForBPartnerId(final ImmutableList<RowIdAndLocationRelatedId> locationRelatedIds)
	{
		final ImmutableSetMultimap<Integer, DocumentId> rowIdsByBPartnerId = locationRelatedIds
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						RowIdAndLocationRelatedId::getLocationRelatedId,
						RowIdAndLocationRelatedId::getRowId));

		final ImmutableSet<Integer> bpartnerRepoIds = rowIdsByBPartnerId.keySet();

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = "SELECT "
				+ " bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID
				+ ", l." + I_C_Location.COLUMNNAME_Latitude
				+ ", l." + I_C_Location.COLUMNNAME_Longitude
				+ " FROM " + I_C_BPartner.Table_Name + " bp "
				+ " INNER JOIN " + I_C_BPartner_Location.Table_Name + " bpl ON (bpl.C_BPartner_ID=bp.C_BPartner_ID)"
				+ " INNER JOIN " + I_C_Location.Table_Name + " l on (l.C_Location_ID=bpl.C_Location_ID)"
				+ " WHERE " + DB.buildSqlList("bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID, bpartnerRepoIds, sqlParams)
				+ " AND bpl.IsActive='Y' "
				+ " AND l." + I_C_Location.COLUMNNAME_GeocodingStatus + "=?";
		sqlParams.add(X_C_Location.GEOCODINGSTATUS_Resolved);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final List<JsonViewRowGeoLocation> geoLocations = new ArrayList<>();
			while (rs.next())
			{
				final int bpartnerRepoId = rs.getInt(I_C_BPartner.COLUMNNAME_C_BPartner_ID);
				final BigDecimal latitude = rs.getBigDecimal(I_C_Location.COLUMNNAME_Latitude);
				final BigDecimal longitude = rs.getBigDecimal(I_C_Location.COLUMNNAME_Longitude);
				if (latitude == null || longitude == null)
				{
					// shall not happen
					logger.warn("Ignored location for bpartnerId={} because the coordonate is not valid: lat={}, long={}", bpartnerRepoId, latitude, longitude);
					continue;
				}

				final ImmutableSet<DocumentId> rowIds = rowIdsByBPartnerId.get(bpartnerRepoId);
				if (rowIds.isEmpty())
				{
					// shall not happen
					logger.warn("Ignored unexpected bpartnerId={}. We have no rows for it.", bpartnerRepoId);
					continue;
				}

				for (final DocumentId rowId : rowIds)
				{
					geoLocations.add(JsonViewRowGeoLocation.builder()
							.rowId(rowId)
							.latitude(latitude)
							.longitude(longitude)
							.build());
				}
			}

			return geoLocations;
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Value
	private static class RowIdAndLocationRelatedId
	{
		@NonNull
		DocumentId rowId;

		int locationRelatedId;
	}
}
