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

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 * 
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and
 *         RelationTypes
 */
public class ZoomInfoFactory {

	/**
	 * Simple class that contains zoom information. Currently used by
	 * {@link org.compiere.apps.AZoomAcross}.
	 * 
	 * @author ts
	 * 
	 */
	public static final class ZoomInfo {

		public final String destinationDisplay;
		public final MQuery query;
		public final int windowId;

		public ZoomInfo(int windowId, MQuery query, String destinationDisplay) {
			this.windowId = windowId;
			this.query = query;
			this.destinationDisplay = destinationDisplay;
		}

		@Override
		public String toString() {

			final StringBuffer sb = new StringBuffer();
			sb.append("ZoomInfo[");
			sb.append("Display=");
			sb.append(destinationDisplay);
			sb.append(", AD_Window_ID=").append(windowId);
			sb.append(", RecordCount=").append(
					query == null ? "<no query>" : query.getRecordCount());
			sb.append("]");
			return sb.toString();
		}

		public String getLabel() {
			return destinationDisplay + " (#" + query.getRecordCount() + ")";
		}
	}

	private static final Logger logger = LogManager.getLogger(ZoomInfoFactory.class);

	/**
	 * Adds {@link ZoomInfo} instances from {@link MRelationType}s and
	 * {@link GenericZoomProvider}.
	 * 
	 * First it looks for matching {@link MRelationType} instances and adds
	 * their {@link MRelationType#retrieveZoomInfos(PO)} results. Then it adds
	 * the {@link GenericZoomProvider}'s results unless there is already one
	 * from a MRelationType that has an quals
	 * {@link ZoomInfo#destinationDisplay} value.
	 * 
	 * @param po
	 * @param windowID
	 * @return
	 */
	public static List<ZoomInfoFactory.ZoomInfo> retrieveZoomInfos(final PO po,
			final int windowID) {

		logger.info("PO=" + po + " - AD_Window_ID=" + windowID);

		final List<ZoomInfoFactory.ZoomInfo> result = new ArrayList<ZoomInfoFactory.ZoomInfo>();

		final Set<String> alreadySeen = new HashSet<String>();

		for (final ZoomInfo zoomInfo : MRelationType.retrieveZoomInfos(po,
				windowID)) {

			logger.debug("Adding zoomInfo " + zoomInfo);

			alreadySeen.add(zoomInfo.destinationDisplay);
			result.add(zoomInfo);
		}

		final GenericZoomProvider genericZoomProvider = new GenericZoomProvider();

		for (final ZoomInfo zoomInfo : genericZoomProvider
				.retrieveZoomInfos(po)) {

			if (alreadySeen.add(zoomInfo.destinationDisplay)) {

				logger.debug("Adding zoomInfo " + zoomInfo + " from "
						+ GenericZoomProvider.class.getSimpleName());
				result.add(zoomInfo);

			} else {

				logger.debug("Skipping zoomInfo " + zoomInfo + " from "
						+ GenericZoomProvider.class.getSimpleName()
						+ " because there is already one for destination '"
						+ zoomInfo.destinationDisplay + "'");
			}
		}

		return result;
	}
}
