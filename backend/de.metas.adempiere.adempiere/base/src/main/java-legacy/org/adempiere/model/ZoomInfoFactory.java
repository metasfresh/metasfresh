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
package org.adempiere.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.IntSupplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_AD_Column;
import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
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

	public interface IZoomSource
	{
		Properties getCtx();

		Evaluatee createEvaluationContext();

		String getTrxName();

		AdWindowId getAD_Window_ID();

		String getTableName();

		int getAD_Table_ID();

		/**
		 * The name of the column from which the zoom originates.<br>
		 * In other words, the name of the column for which the system tries to load {@link IZoomProvider}s.
		 *
		 * Example: when looking for zoom-targets from a {@code C_Order} window, then this is {@code C_Order_ID}.
		 *
		 * @return The name of the single ID column which has {@link I_AD_Column#COLUMN_IsGenericZoomOrigin} {@code ='Y'}.
		 *         May also return {@code null}. In this case, expect no exception, but also no zoom providers to be created.
		 */
		String getKeyColumnNameOrNull();

		/**
		 * @return {@code true} if the zoom source shall be considered by {@link GenericZoomProvider}.
		 *         We have dedicated flag because the generic zoom provide can be very nice<br>
		 *         (no need to set up relation types) but also very performance intensive.
		 */
		boolean isGenericZoomOrigin();

		int getRecord_ID();

		default boolean isSOTrx()
		{
			return Env.isSOTrx(getCtx());
		}

		boolean hasField(String columnName);

		Object getFieldValue(String columnName);

		default boolean getFieldValueAsBoolean(final String columnName)
		{
			return DisplayType.toBoolean(getFieldValue(columnName));
		}
	}

	/**
	 * Note that webui records own source implementation.
	 */
	public static final class POZoomSource implements IZoomSource
	{
		public static POZoomSource of(final PO po, final AdWindowId adWindowId)
		{
			return new POZoomSource(po, adWindowId);
		}

		public static POZoomSource of(final PO po)
		{
			final AdWindowId adWindowId = null;
			return new POZoomSource(po, adWindowId);
		}

		private final PO po;
		private final AdWindowId adWindowId;
		private final String keyColumnName;

		@Getter
		private final boolean genericZoomOrigin;

		private POZoomSource(@NonNull final PO po, final AdWindowId adWindowId)
		{
			this.po = po;
			this.adWindowId = adWindowId;

			final IPair<String, Boolean> pair = extractKeyColumnNameOrNull(po);

			keyColumnName = pair.getLeft();
			genericZoomOrigin = pair.getRight();
		}

		/**
		 * @return the name of a key column that is also flagged as GenericZoomOrigin and {@code true},if there is exactly one such column.<br>
		 *         Otherwise it returns {@code null} and {@code false}.
		 */
		private static IPair<String, Boolean> extractKeyColumnNameOrNull(@NonNull final PO po)
		{
			final String[] keyColumnNamesArr = po.get_KeyColumns();
			if (keyColumnNamesArr == null)
			{
				return null;
			}

			final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

			final ArrayList<String> eligibleKeyColumnNames = new ArrayList<>();
			for (String element : keyColumnNamesArr)
			{
				final I_AD_Column column = adTableDAO.retrieveColumn(po.get_TableName(), element);
				if (column.isGenericZoomOrigin())
				{
					eligibleKeyColumnNames.add(element);
				}
			}

			if (eligibleKeyColumnNames.size() != 1)
			{
				return ImmutablePair.of(null, Boolean.FALSE);
			}

			return ImmutablePair.of(eligibleKeyColumnNames.get(0), Boolean.TRUE);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("po", po)
					.add("AD_Window_ID", adWindowId)
					.toString();
		}

		public PO getPO()
		{
			return po;
		}

		@Override
		public AdWindowId getAD_Window_ID()
		{
			return adWindowId;
		}

		@Override
		public String getTableName()
		{
			return po.get_TableName();
		}

		@Override
		public int getAD_Table_ID()
		{
			return po.get_Table_ID();
		}

		@Override
		public String getKeyColumnNameOrNull()
		{
			return keyColumnName;
		}

		@Override
		public int getRecord_ID()
		{
			return po.get_ID();
		}

		@Override
		public Properties getCtx()
		{
			return po.getCtx();
		}

		@Override
		public String getTrxName()
		{
			return po.get_TrxName();
		}

		@Override
		public Evaluatee createEvaluationContext()
		{
			final Properties privateCtx = Env.deriveCtx(getCtx());

			final PO po = getPO();
			final POInfo poInfo = po.getPOInfo();
			for (int i = 0; i < poInfo.getColumnCount(); i++)
			{
				final Object val;
				final int dispType = poInfo.getColumnDisplayType(i);
				if (DisplayType.isID(dispType))
				{
					// make sure we get a 0 instead of a null for foreign keys
					val = po.get_ValueAsInt(i);
				}
				else
				{
					val = po.get_Value(i);
				}

				if (val == null)
				{
					continue;
				}

				if (val instanceof Integer)
				{
					Env.setContext(privateCtx, "#" + po.get_ColumnName(i), (Integer)val);
				}
				else if (val instanceof String)
				{
					Env.setContext(privateCtx, "#" + po.get_ColumnName(i), (String)val);
				}
			}

			return Evaluatees.ofCtx(privateCtx, Env.WINDOW_None, false);
		}

		@Override
		public boolean hasField(final String columnName)
		{
			return po.getPOInfo().hasColumnName(columnName);
		}

		@Override
		public Object getFieldValue(final String columnName)
		{
			return po.get_Value(columnName);
		}
	}

	public static final class ZoomInfo
	{
		private final String zoomInfoId;
		private final String _internalName;
		private final ITranslatableString destinationDisplay;
		private final MQuery query;
		private final AdWindowId windowId;

		private final IntSupplier recordsCountSupplier;

		@Builder
		private ZoomInfo(
				@NonNull final String zoomInfoId,
				@NonNull final String internalName,
				@NonNull final AdWindowId windowId,
				@NonNull final MQuery query,
				@NonNull final ITranslatableString destinationDisplay,
				@NonNull final IntSupplier recordsCountSupplier)
		{
			this.zoomInfoId = Check.assumeNotEmpty(zoomInfoId, "zoomInfoId is not empty");
			this._internalName = Check.assumeNotEmpty(internalName, "internalName is not empty");

			this.windowId = windowId;

			this.query = query;
			this.destinationDisplay = destinationDisplay;

			this.recordsCountSupplier = recordsCountSupplier;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("zoomInfoId", zoomInfoId)
					.add("internalName", _internalName)
					.add("display", destinationDisplay)
					.add("AD_Window_ID", windowId)
					.add("RecordCount", query.getRecordCount())
					.toString();
		}

		public String getId()
		{
			return zoomInfoId;
		}

		public String getInternalName()
		{
			return _internalName;
		}

		public ITranslatableString getLabel()
		{
			final ITranslatableString postfix = TranslatableStrings.constant(" (#" + getRecordCount() + ")");
			return TranslatableStrings.join("", destinationDisplay, postfix);
		}

		public int getRecordCount()
		{
			return query.getRecordCount();
		}

		@Nullable
		public Duration getRecordCountDuration()
		{
			return query.getRecordCountDuration();
		}

		public void updateRecordsCount()
		{
			final Stopwatch stopwatch = Stopwatch.createStarted();
			final int recordsCount = recordsCountSupplier.getAsInt();
			final Duration recordsCountDuration = Duration.ofNanos(stopwatch.stop().elapsed(TimeUnit.NANOSECONDS));
			query.setRecordCount(recordsCount, recordsCountDuration);
			logger.info("Updated records count for {} in {}", this, stopwatch);
		}

		public AdWindowId getAdWindowId()
		{
			return windowId;
		}

		public MQuery getQuery()
		{
			return query;
		}
	}

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

	public Stream<ZoomInfo> streamZoomInfos(
			@NonNull final IZoomSource zoomOrigin,
			@Nullable final AdWindowId onlyTargetWindowId,
			@NonNull final IUserRolePermissions rolePermissions)
	{
		logger.debug("source={}", zoomOrigin);

		final ImmutableList<ZoomInfo> zoomInfoCandidates = getZoomInfoCandidates(zoomOrigin, onlyTargetWindowId);

		final HashSet<AdWindowId> alreadySeenWindowIds = new HashSet<>();

		return zoomInfoCandidates.stream()
				.sequential()
				.filter(zoomInfo -> isEligible(zoomInfo, rolePermissions, alreadySeenWindowIds));
	}

	private ImmutableList<ZoomInfo> getZoomInfoCandidates(
			@NonNull final IZoomSource zoomOrigin,
			@Nullable final AdWindowId onlyTargetWindowId)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final String tableName = zoomOrigin.getTableName();
		final List<IZoomProvider> zoomProviders = retrieveZoomProviders(tableName);

		final ImmutableList.Builder<ZoomInfo> zoomInfoCandidates = ImmutableList.builder();
		for (final IZoomProvider zoomProvider : zoomProviders)
		{
			try
			{
				final List<ZoomInfo> zoomInfos = zoomProvider.retrieveZoomInfos(zoomOrigin, onlyTargetWindowId);
				for (final ZoomInfo zoomInfo : zoomInfos)
				{
					// If not our target window ID, skip it
					// This shall not happen because we asked the zoomProvider to return only those for our target window,
					// but if is happening (because of a bug zoom provider) we shall not be so fragile.
					if (onlyTargetWindowId != null && !AdWindowId.equals(onlyTargetWindowId, zoomInfo.getAdWindowId()))
					{
						new AdempiereException("Got a ZoomInfo which is not for our target window. Skipping it."
								+ "\n zoomInfo: " + zoomInfo
								+ "\n zoomProvider: " + zoomProvider
								+ "\n targetAD_Window_ID: " + onlyTargetWindowId
								+ "\n source: " + zoomOrigin)
										.throwIfDeveloperModeOrLogWarningElse(logger);
						continue;
					}

					zoomInfoCandidates.add(zoomInfo);
				}
				zoomInfoCandidates.addAll(zoomInfos);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed retrieving zoom infos from {} for {}. Skipped.", zoomProvider, zoomOrigin, ex);
			}
		}

		stopwatch.stop();
		logger.info("Fetches zoom candidates for source={} in {}", zoomOrigin, stopwatch);

		return zoomInfoCandidates.build();
	}

	private boolean isEligible(
			@NonNull final ZoomInfo zoomInfo,
			@NonNull final IUserRolePermissions rolePermissions,
			@NonNull final HashSet<AdWindowId> alreadySeenWindowIds)
	{
		//
		// Filter out those windows on given user does not have permissions
		if (!rolePermissions.checkWindowPermission(zoomInfo.getAdWindowId()).hasReadAccess())
		{
			return false;
		}

		//
		// Only consider a window already seen if it actually has record count > 0 (task #1062)
		final AdWindowId adWindowId = zoomInfo.getAdWindowId();
		if (alreadySeenWindowIds.contains(adWindowId))
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
		alreadySeenWindowIds.add(adWindowId);
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

	private List<IZoomProvider> retrieveZoomProviders(final String tableName)
	{
		final List<IZoomProvider> zoomProviders = new ArrayList<>();

		// NOTE: Zoom providers order matter because in case it finds some duplicates (i.e. same window),
		// it will pick only the first one (i.e. the one from the first provider).
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
