/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.adempiere.webui;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.ZoomInfoFactory;
import org.adempiere.webui.apps.AEnv;
import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

/**
 *	Application Zoom Across Launcher.
 *  Called from APanel; Queries available Zoom Targets for Table.
 *
 *  @author Jorg Janke
 *  @version $Id: AZoomAcross.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL - FR [ 1762465 ]
 *
 * ZK Port
 * @author Low Heng Sin
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194  ] Advanced Zoom and RelationTypes
 */
public class WZoomAcross
{
	/**
	 *	Constructor
	 *  @param invoker component to display popup (optional)
	 *  @param tableName zoom source table (i.e. the table we start from)
	 *  @param query query that specifies the zoom source PO (i.e. the PO we start from)
	 */
	public WZoomAcross (Component invoker, String tableName, final int windowID, MQuery query)
	{
		this(invoker, retrievePO(tableName, query), windowID);
	}

	private static final PO retrievePO(final String tableName, final MQuery query)
	{
		final PO po = new Query(Env.getCtx(), tableName, query.getWhereClause(), null)
				.firstOnly();
		return po;
	}

	public WZoomAcross(Component invoker, PO po, final int windowID) {
		
		log.info("PO=" + po+", WindowID="+windowID);
		
		mkZoomTargets(po, windowID);
				
		for (final ZoomInfoFactory.ZoomInfo zoomInfo : zoomInfos) {


			final String label = zoomInfo.destinationDisplay + " (#"
					+ zoomInfo.query.getRecordCount() + ")";

			final Menuitem menuItem = new Menuitem(label);
			menuItem.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event event) throws Exception {
					launchZoom(zoomInfo);
				}
			});
			m_popup.appendChild(menuItem);
		}

		if (zoomInfos.isEmpty()) {
			final Menuitem menuItem = new Menuitem(Msg.getMsg(Env.getCtx(), "NoZoomTarget"));
			m_popup.appendChild(menuItem);  // Added
		}
		m_popup.setPage(invoker.getPage());
		m_popup.open(invoker);
	}

	private Menupopup 	m_popup = new Menupopup(); //"ZoomMenu"
	
	private static final Logger log = LogManager.getLogger(WZoomAcross.class);

	private final List<ZoomInfoFactory.ZoomInfo> zoomInfos = new ArrayList<ZoomInfoFactory.ZoomInfo>();

	private void mkZoomTargets(final PO po, final int windowID) {
	
		for (final ZoomInfoFactory.ZoomInfo zoomInfo : ZoomInfoFactory.retrieveZoomInfos(po,
				windowID)) {

			if (zoomInfo.query.getRecordCount() == 0) {
				log.debug("No target records for destination "
						+ zoomInfo.destinationDisplay);
				continue;
			}
			zoomInfos.add(zoomInfo);
		}
	}
	
	/**
	 * 	Launch Zoom
	 *	@param pp KeyPair
	 */
	private void launchZoom (final ZoomInfoFactory.ZoomInfo zoomInfo)
	{
		final int AD_Window_ID = zoomInfo.windowId;
		final MQuery query = zoomInfo.query;
		
		log.info("AD_Window_ID=" + AD_Window_ID 
			+ " - " + query); 
		
		AEnv.zoom(AD_Window_ID, query);
	}	//	launchZoom

}	//	AZoom
