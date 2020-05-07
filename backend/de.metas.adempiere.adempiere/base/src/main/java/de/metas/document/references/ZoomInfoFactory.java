/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 2009 www.metas.de *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package de.metas.document.references;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.util.lang.Priority;
import lombok.NonNull;

/**
 *
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and RelationTypes
 */
public class ZoomInfoFactory
{
	public static final ZoomInfoFactory get()
	{
		return instance;
	}

	private static final transient ZoomInfoFactory instance = new ZoomInfoFactory();

	private static final Logger logger = LogManager.getLogger(ZoomInfoFactory.class);

	private boolean factAcctZoomProviderEnabled = true;

	private ZoomInfoFactory()
	{
	}

	/**
	 * @param zoomOrigin the source we need zoom targets for
	 * @return a list of zoom targets. The {@link ZoomInfo#getRecordCount()} of the ZoomInfo's query member might be zero.
	 */
	public List<ZoomInfo> retrieveZoomInfos(
			@NonNull final IZoomSource zoomOrigin,
			@NonNull final IUserRolePermissions rolePermissions)
	{
		final AdWindowId onlyTargetWindowId = null;
		return streamZoomInfos(zoomOrigin, onlyTargetWindowId, rolePermissions)
				.collect(ImmutableList.toImmutableList());
	}

	public Stream<ZoomInfo> streamZoomInfos(
			@NonNull final IZoomSource zoomOrigin,
			@NonNull final IUserRolePermissions rolePermissions)
	{
		final AdWindowId onlyTargetWindowId = null;
		return streamZoomInfos(zoomOrigin, onlyTargetWindowId, rolePermissions);
	}

	private Stream<ZoomInfo> streamZoomInfos(
			@NonNull final IZoomSource zoomSource,
			@Nullable final AdWindowId onlyTargetWindowId,
			@NonNull final IUserRolePermissions rolePermissions)
	{
		logger.debug("source={}", zoomSource);

		final ImmutableList<ZoomInfo> zoomInfoCandidates = getZoomInfoCandidates(zoomSource, onlyTargetWindowId, rolePermissions);

		final ConcurrentHashMap<AdWindowId, Priority> alreadySeenWindowIds = new ConcurrentHashMap<>();

		return zoomInfoCandidates.stream()
				.sequential()
				.filter(zoomInfo -> isEligible(zoomInfo, alreadySeenWindowIds));
	}

	private ImmutableList<ZoomInfo> getZoomInfoCandidates(
			@NonNull final IZoomSource zoomSource,
			@Nullable final AdWindowId onlyTargetWindowId,
			@NonNull final IUserRolePermissions rolePermissions)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final String tableName = zoomSource.getTableName();
		final List<IZoomProvider> zoomProviders = retrieveZoomProviders(tableName);

		final ImmutableList.Builder<ZoomInfo> zoomInfoCandidates = ImmutableList.builder();
		for (final IZoomProvider zoomProvider : zoomProviders)
		{
			try
			{
				final List<ZoomInfo> zoomInfos = zoomProvider.retrieveZoomInfos(zoomSource, onlyTargetWindowId);
				for (final ZoomInfo zoomInfo : zoomInfos)
				{
					// If not our target window ID, skip it
					// This shall not happen because we asked the zoomProvider to return only those for our target window,
					// but if is happening (because of a bug zoom provider) we shall not be so fragile.
					if (onlyTargetWindowId != null
							&& !AdWindowId.equals(onlyTargetWindowId, zoomInfo.getAdWindowId()))
					{
						new AdempiereException("Got a ZoomInfo which is not for our target window. Skipping it."
								+ "\n zoomInfo: " + zoomInfo
								+ "\n zoomProvider: " + zoomProvider
								+ "\n targetAD_Window_ID: " + onlyTargetWindowId
								+ "\n source: " + zoomSource)
										.throwIfDeveloperModeOrLogWarningElse(logger);
						continue;
					}

					//
					// Filter out those windows on given user does not have permissions
					if (!rolePermissions.checkWindowPermission(zoomInfo.getAdWindowId()).hasReadAccess())
					{
						continue;
					}

					//
					// Collect eligible zoom info candidate
					zoomInfoCandidates.add(zoomInfo);
				}
			}
			catch (final Exception ex)
			{
				logger.warn("Failed retrieving zoom infos from {} for {}. Skipped.", zoomProvider, zoomSource, ex);
			}
		}

		stopwatch.stop();
		logger.debug("Fetched zoom candidates for source={} in {}", zoomSource, stopwatch);

		return zoomInfoCandidates.build();
	}

	private static boolean isEligible(
			@NonNull final ZoomInfo zoomInfo,
			@NonNull final ConcurrentHashMap<AdWindowId, Priority> alreadySeenWindowIds)
	{
		//
		// Only consider a window already seen if it actually has record count > 0 (task #1062)
		final AdWindowId adWindowId = zoomInfo.getAdWindowId();
		final Priority alreadySeenZoomInfoPriority = alreadySeenWindowIds.get(adWindowId);
		if (alreadySeenZoomInfoPriority != null
				&& alreadySeenZoomInfoPriority.isHigherThan(zoomInfo.getPriority()))
		{
			logger.debug("Skipping zoomInfo {} because there is already one for destination '{}'", zoomInfo, adWindowId);
			return false;
		}

		//
		// Make sure records count is computed
		zoomInfo.updateRecordsCount();

		//
		// Filter out those ZoomInfos which have ZERO records (if requested)
		if (zoomInfo.getRecordCount() <= 0)
		{
			logger.debug("No target records for destination {}", zoomInfo);
			return false;
		}

		//
		// We got a valid zoom info
		// => accept it
		alreadySeenWindowIds.put(adWindowId, zoomInfo.getPriority());
		return true;
	}

	/**
	 * Retrieves that {@link ZoomInfo} which is referencing our given <code>source</code> and has given target window.
	 *
	 * NOTE: Records count is not checked
	 *
	 * @param zoomSource
	 * @param targetWindowId target AD_Window_ID (must be provided)
	 * @return ZoomInfo; never returns <code>null</code>
	 */
	public ZoomInfo retrieveZoomInfo(
			@NonNull final IZoomSource zoomSource,
			@NonNull final AdWindowId targetWindowId,
			@NonNull final IUserRolePermissions rolePermissions)
	{
		// NOTE: we need to check the records count because in case there are multiple ZoomInfos for the same targetWindowId,
		// we shall pick the one which actually has some data. Usually there would be only one (see #1808)
		return streamZoomInfos(zoomSource, targetWindowId, rolePermissions)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No zoomInfo found for source=" + zoomSource + ", targetWindowId=" + targetWindowId));
	}

	private List<IZoomProvider> retrieveZoomProviders(@NonNull final String tableName)
	{
		// NOTE: Zoom providers order IS NOT important because each provider creates ZoomInfo with a priority.

		final ArrayList<IZoomProvider> zoomProviders = new ArrayList<>();
		zoomProviders.addAll(RelationTypeZoomProvidersFactory.instance.getZoomProvidersByZoomOriginTableName(tableName));
		zoomProviders.add(GenericZoomProvider.instance);
		if (factAcctZoomProviderEnabled)
		{
			zoomProviders.add(FactAcctZoomProvider.instance);
		}

		return zoomProviders;
	}

	/**
	 * Disable the {@link FactAcctZoomProvider} (which is enabled by default
	 *
	 * @deprecated Needed only for Swing
	 */
	@Deprecated
	public void disableFactAcctZoomProvider()
	{
		factAcctZoomProviderEnabled = false;
	}
}
