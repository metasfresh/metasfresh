/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.references.related_documents;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.error.AdIssueZoomProvider;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and RelationTypes
 */
@Service
public class ZoomInfoFactory
{
	private static final Logger logger = LogManager.getLogger(ZoomInfoFactory.class);

	private final GenericZoomProvider genericZoomProvider;
	private final RelationTypeZoomProvidersFactory relationTypeZoomProvidersFactory;
	private boolean factAcctZoomProviderEnabled = true;

	public ZoomInfoFactory(
			@NonNull final GenericZoomProvider genericZoomProvider,
			@NonNull final RelationTypeZoomProvidersFactory relationTypeZoomProvidersFactory)
	{
		this.genericZoomProvider = genericZoomProvider;
		this.relationTypeZoomProvidersFactory = relationTypeZoomProvidersFactory;
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
		final ZoomTargetWindowEvaluationContext context = new ZoomTargetWindowEvaluationContext();

		return getZoomInfoCandidates(zoomOrigin, onlyTargetWindowId, permissions)
				.stream()
				.sequential()
				.flatMap(candidate -> candidate.evaluateAndStream(context))
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableList<ZoomInfoCandidateGroup> getZoomInfoCandidates(
			@NonNull final IZoomSource zoomSource,
			@NonNull final ZoomInfoPermissions permissions)
	{
		return getZoomInfoCandidates(zoomSource, null, permissions);
	}

	private ImmutableList<ZoomInfoCandidateGroup> getZoomInfoCandidates(
			@NonNull final IZoomSource zoomSource,
			@Nullable final AdWindowId onlyTargetWindowId,
			@NonNull final ZoomInfoPermissions permissions)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final String tableName = zoomSource.getTableName();
		final List<IZoomProvider> zoomProviders = retrieveZoomProviders(tableName);

		final ImmutableList.Builder<ZoomInfoCandidateGroup> result = ImmutableList.builder();
		for (final IZoomProvider zoomProvider : zoomProviders)
		{
			try
			{
				for (final ZoomInfoCandidateGroup zoomInfoCandidatesGroup : zoomProvider.retrieveZoomInfos(zoomSource, onlyTargetWindowId))
				{
					// If not our target window ID, skip it
					// This shall not happen because we asked the zoomProvider to return only those for our target window,
					// but if is happening (because of a bug zoom provider) we shall not be so fragile.
					final AdWindowId targetWindowId = zoomInfoCandidatesGroup.getTargetWindowId();
					if (onlyTargetWindowId != null && !AdWindowId.equals(onlyTargetWindowId, targetWindowId))
					{
						//noinspection ThrowableNotThrown
						new AdempiereException("Got a ZoomInfo which is not for our target window. Skipping it."
													   + "\n zoomInfoCandidatesGroup: " + zoomInfoCandidatesGroup
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
					result.add(zoomInfoCandidatesGroup);
				}
			}
			catch (final Exception ex)
			{
				logger.warn("Failed retrieving zoom infos from {} for {}. Skipped.", zoomProvider, zoomSource, ex);
			}
		}

		stopwatch.stop();
		logger.debug("Fetched zoom candidates for source={} in {}", zoomSource, stopwatch);

		return result.build();
	}

	/**
	 * Retrieves that {@link ZoomInfo} which is referencing our given <code>source</code> and has given target window.
	 * <p>
	 * NOTE: Records count is not checked
	 */
	public ZoomInfo retrieveZoomInfo(
			@NonNull final IZoomSource zoomSource,
			@NonNull final AdWindowId targetWindowId,
			@Nullable final ZoomInfoId zoomInfoId,
			@NonNull final ZoomInfoPermissions permissions)
	{
		// NOTE: we need to check the records count because in case there are multiple ZoomInfos for the same targetWindowId,
		// we shall pick the one which actually has some data. Usually there would be only one (see #1808)

		final ZoomTargetWindowEvaluationContext context = new ZoomTargetWindowEvaluationContext();
		context.setOnlyZoomInfoId(zoomInfoId);

		return getZoomInfoCandidates(zoomSource, targetWindowId, permissions)
				.stream()
				.filter(candidatesGroup -> zoomInfoId == null || candidatesGroup.isZoomInfoIdMatching(zoomInfoId))
				.flatMap(candidatesGroup -> candidatesGroup.evaluateAndStream(context))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No zoomInfo found for source=" + zoomSource + ", targetWindowId=" + targetWindowId));
	}

	private List<IZoomProvider> retrieveZoomProviders(@NonNull final String tableName)
	{
		// NOTE: Zoom providers order IS NOT important because each provider creates ZoomInfo with a priority.

		final ArrayList<IZoomProvider> zoomProviders = new ArrayList<>();
		zoomProviders.addAll(relationTypeZoomProvidersFactory.getZoomProvidersByZoomOriginTableName(tableName));
		zoomProviders.add(genericZoomProvider);
		if (factAcctZoomProviderEnabled)
		{
			zoomProviders.add(FactAcctZoomProvider.instance);
		}
		zoomProviders.add(new AdIssueZoomProvider());

		return zoomProviders;
	}

	/**
	 * Disable the {@link FactAcctZoomProvider} (which is enabled by default)
	 *
	 * @deprecated Needed only for Swing
	 */
	@Deprecated
	public void disableFactAcctZoomProvider()
	{
		factAcctZoomProviderEnabled = false;
		logger.info("Disabled FactAcctZoomProvider");
	}
}
