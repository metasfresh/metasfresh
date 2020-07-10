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
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.error.AdIssueZoomProvider;
import de.metas.logging.LogManager;
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
			@NonNull final ZoomInfoPermissions permissions)
	{
		final AdWindowId onlyTargetWindowId = null;
		final ZoomTargetWindowEvaluationContext alreadySeenWindowIds = new ZoomTargetWindowEvaluationContext();

		return getZoomInfoCandidates(zoomOrigin, onlyTargetWindowId, permissions)
				.stream()
				.sequential()
				.map(candidate -> candidate.evaluate(alreadySeenWindowIds).orElse(null))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableList<ZoomInfoCandidate> getZoomInfoCandidates(
			@NonNull final IZoomSource zoomOrigin,
			@NonNull final ZoomInfoPermissions permissions)
	{
		final AdWindowId onlyTargetWindowId = null;
		return getZoomInfoCandidates(zoomOrigin, onlyTargetWindowId, permissions);
	}

	private ImmutableList<ZoomInfoCandidate> getZoomInfoCandidates(
			@NonNull final IZoomSource zoomSource,
			@Nullable final AdWindowId onlyTargetWindowId,
			@NonNull final ZoomInfoPermissions permissions)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final String tableName = zoomSource.getTableName();
		final List<IZoomProvider> zoomProviders = retrieveZoomProviders(tableName);

		final ImmutableList.Builder<ZoomInfoCandidate> zoomInfoCandidates = ImmutableList.builder();
		for (final IZoomProvider zoomProvider : zoomProviders)
		{
			try
			{
				for (final ZoomInfoCandidate zoomInfoCandidate : zoomProvider.retrieveZoomInfos(zoomSource, onlyTargetWindowId))
				{
					// If not our target window ID, skip it
					// This shall not happen because we asked the zoomProvider to return only those for our target window,
					// but if is happening (because of a bug zoom provider) we shall not be so fragile.
					final AdWindowId targetWindowId = zoomInfoCandidate.getTargetWindow().getAdWindowId();
					if (onlyTargetWindowId != null && !AdWindowId.equals(onlyTargetWindowId, targetWindowId))
					{
						new AdempiereException("Got a ZoomInfo which is not for our target window. Skipping it."
								+ "\n zoomInfo: " + zoomInfoCandidate
								+ "\n zoomProvider: " + zoomProvider
								+ "\n targetAD_Window_ID: " + onlyTargetWindowId
								+ "\n source: " + zoomSource)
										.throwIfDeveloperModeOrLogWarningElse(logger);
						continue;
					}

					//
					// Filter out those windows on given user does not have permissions
					if (!permissions.hasReadAccess(targetWindowId))
					{
						continue;
					}

					//
					// Collect eligible zoom info candidate
					zoomInfoCandidates.add(zoomInfoCandidate);
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
			@Nullable final ZoomInfoId zoomInfoId,
			@NonNull final ZoomInfoPermissions permissions)
	{
		// NOTE: we need to check the records count because in case there are multiple ZoomInfos for the same targetWindowId,
		// we shall pick the one which actually has some data. Usually there would be only one (see #1808)

		final ZoomTargetWindowEvaluationContext alreadySeenWindowIds = new ZoomTargetWindowEvaluationContext();

		return getZoomInfoCandidates(zoomSource, targetWindowId, permissions)
				.stream()
				.filter(candidate -> zoomInfoId == null || ZoomInfoId.equals(zoomInfoId, candidate.getId()))
				.map(candidate -> candidate.evaluate(alreadySeenWindowIds).orElse(null))
				.filter(Objects::nonNull)
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
		zoomProviders.add(new AdIssueZoomProvider());

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
