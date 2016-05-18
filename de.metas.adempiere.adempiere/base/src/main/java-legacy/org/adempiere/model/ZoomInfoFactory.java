/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2009 www.metas.de                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.Check;
import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

/**
 * 
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and RelationTypes
 */
public class ZoomInfoFactory implements IZoomProvider
{
	public static final ZoomInfoFactory get()
	{
		return instance; 
	}
	
	private static final transient ZoomInfoFactory instance = new ZoomInfoFactory();
	
	public static interface IZoomSource
	{
		Properties getCtx();

		String getTrxName();

		int getAD_Window_ID();

		String getTableName();

		int getAD_Table_ID();

		String getKeyColumnName();

		List<String> getKeyColumnNames();

		int getRecord_ID();
	}

	public static final class POZoomSource implements IZoomSource
	{
		public static final POZoomSource of(final PO po, final int adWindowId)
		{
			return new POZoomSource(po, adWindowId);
		}

		public static final POZoomSource of(final PO po)
		{
			final int adWindowId = 0;
			return new POZoomSource(po, adWindowId);
		}

		private final PO po;
		private final int adWindowId;
		private final List<String> keyColumnNames;

		private POZoomSource(final PO po, final int adWindowId)
		{
			super();
			Check.assumeNotNull(po, "Parameter po is not null");
			this.po = po;
			this.adWindowId = adWindowId;

			final String[] keyColumnNamesArr = po.get_KeyColumns();
			if (keyColumnNamesArr == null)
			{
				this.keyColumnNames = ImmutableList.of();
			}
			else
			{
				this.keyColumnNames = ImmutableList.copyOf(keyColumnNamesArr);
			}
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
		public int getAD_Window_ID()
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
		public String getKeyColumnName()
		{
			if (keyColumnNames.size() != 1)
			{
				return null;
			}
			return keyColumnNames.get(0);
		}

		@Override
		public List<String> getKeyColumnNames()
		{
			return keyColumnNames;
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
	}

	/**
	 * Simple class that contains zoom information. Currently used by
	 * {@link org.compiere.apps.AZoomAcross}.
	 * 
	 * @author ts
	 * 
	 */
	public static final class ZoomInfo
	{
		public static final ZoomInfo of(int windowId, MQuery query, String destinationDisplay)
		{
			return new ZoomInfo(windowId, query, destinationDisplay);
		}

		private final String _destinationDisplay;
		private final MQuery _query;
		private final int _windowId;

		private ZoomInfo(int windowId, MQuery query, String destinationDisplay)
		{
			super();
			this._windowId = windowId;
			this._query = query;
			this._destinationDisplay = destinationDisplay;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("display", _destinationDisplay)
					.add("AD_Window_ID", _windowId)
					.add("RecordCount", _query == null ? "<no query>" : _query.getRecordCount())
					.toString();
		}

		public String getLabel()
		{
			return _destinationDisplay + " (#" + getRecordCount() + ")";
		}

		public String getId()
		{
			return _destinationDisplay;
		}

		public int getRecordCount()
		{
			return _query == null ? 0 : _query.getRecordCount();
		}

		public int getAD_Window_ID()
		{
			return _windowId;
		}

		public MQuery getQuery()
		{
			return _query;
		}
	}

	private static final Logger logger = LogManager.getLogger(ZoomInfoFactory.class);
	
	private ZoomInfoFactory()
	{
		super();
	}

	/**
	 * Retrieves all {@link ZoomInfo}s for given {@link IZoomSource}.
	 */
	@Override
	public List<ZoomInfo> retrieveZoomInfos(final IZoomSource source)
	{
		logger.info("source={}", source);

		final ImmutableList.Builder<ZoomInfo> result = ImmutableList.builder();
		final Set<String> alreadySeenIds = new HashSet<String>();

		final List<IZoomProvider> zoomProviders = retrieveZoomProviders(source);
		for (final IZoomProvider zoomProvider : zoomProviders)
		{
			logger.debug("Checking zoom provider: {}", zoomProvider);

			try
			{
				for (final ZoomInfo zoomInfo : zoomProvider.retrieveZoomInfos(source))
				{
					final String zoomInfoId = zoomInfo.getId();
					if (alreadySeenIds.add(zoomInfoId))
					{
						logger.debug("Adding zoomInfo {} from {}", zoomInfo, zoomProvider);
						result.add(zoomInfo);
	
					}
					else
					{
						logger.debug("Skipping zoomInfo {} from {} because there is already one for destination '{}'", zoomInfo, zoomProvider, zoomInfoId);
					}
				}
			}
			catch (Exception e)
			{
				logger.warn("Failed retrieving zoom infos from {} for {}. Skipped.", zoomProvider, source);
			}
		}

		return result.build();
	}

	private static List<IZoomProvider> retrieveZoomProviders(final IZoomSource source)
	{
		final List<IZoomProvider> zoomProviders = new ArrayList<>();

		zoomProviders.addAll(MRelationType.retrieveZoomProviders(source));
		zoomProviders.add(GenericZoomProvider.instance);

		return zoomProviders;
	}
}
