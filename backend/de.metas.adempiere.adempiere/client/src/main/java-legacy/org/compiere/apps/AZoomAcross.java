/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.apps;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.document.references.IZoomSource;
import de.metas.document.references.POZoomSource;
import de.metas.document.references.ZoomInfo;
import de.metas.document.references.ZoomInfoFactory;
import de.metas.document.references.ZoomInfoPermissions;
import de.metas.document.references.ZoomInfoPermissionsFactory;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;

/**
 * Application Zoom Across Launcher.
 * Called from APanel; Queries available Zoom Targets for Table.
 *
 * @author Jorg Janke
 * @version $Id: AZoomAcross.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL - FR [ 1762465 ]
 * @author afalcone - Bug Fix [ 1659420 ] Usability: zoom across
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and RelationTypes
 */
public class AZoomAcross
{
	/**
	 * Constructor
	 * 
	 * @param invoker component to display popup (optional)
	 * @param tableName zoom source table (i.e. the table we start from)
	 * @param query query that specifies the zoom source PO (i.e. the PO we start from)
	 */
	public AZoomAcross(JComponent invoker, String tableName, final AdWindowId windowID, MQuery query)
	{
		this(invoker, retrieveZoomSourceOrNull(tableName, query, windowID));
	}

	private static final IZoomSource retrieveZoomSourceOrNull(final String tableName, final MQuery query, final AdWindowId adWindowId)
	{
		final PO po = new Query(Env.getCtx(), tableName, query.getWhereClause(), ITrx.TRXNAME_None)
				.firstOnly();
		if (po == null)
		{
			return null;
		}
		return POZoomSource.of(po, adWindowId);
	}

	private AZoomAcross(final JComponent invoker, IZoomSource source)
	{
		super();
		logger.info("source={}", source);
		final String adLanguage = Env.getAD_Language(Env.getCtx());

		final List<ZoomInfo> zoomInfos = retrieveZoomTargets(source);
		for (final ZoomInfo zoomInfo : zoomInfos)
		{
			final String caption = zoomInfo.getCaption().translate(adLanguage);
			m_popup.add(caption).addActionListener(event -> launch(zoomInfo));
		}

		if (zoomInfos.isEmpty())
		{
			m_popup.add(Services.get(IMsgBL.class).getMsg(Env.getCtx(), "NoZoomTarget")); // Added
		}
		if (invoker.isShowing())
		{
			m_popup.show(invoker, 0, invoker.getHeight());
		}
	}

	private final JPopupMenu m_popup = new JPopupMenu("ZoomMenu");

	private static final Logger logger = LogManager.getLogger(AZoomAcross.class);

	private List<ZoomInfo> retrieveZoomTargets(final IZoomSource source)
	{
		if (source == null)
		{
			return ImmutableList.of(); // guard against NPE
		}

		final List<ZoomInfo> zoomInfos = new ArrayList<>();
		final ZoomInfoFactory zoomProvider = ZoomInfoFactory.get();
		zoomProvider.disableFactAcctZoomProvider(); // in Swing this is not needed because we have the Posted button
		final ZoomInfoPermissions permissions = ZoomInfoPermissionsFactory.ofRolePermissions(Env.getUserRolePermissions());
		for (final ZoomInfo zoomInfo : zoomProvider.retrieveZoomInfos(source, permissions))
		{
			zoomInfos.add(zoomInfo);
		}

		return ImmutableList.copyOf(zoomInfos);
	}

	/**
	 * Launch Zoom
	 *
	 * @param pp KeyPair
	 */
	private void launch(final ZoomInfo zoomInfo)
	{
		final AdWindowId adWindowId = zoomInfo.getAdWindowId();
		final MQuery query = zoomInfo.getQuery();

		logger.info("AD_Window_ID={} - {}", adWindowId, query);

		AWindow frame = new AWindow();
		if (!frame.initWindow(adWindowId, query))
		{
			return;
		}
		AEnv.addToWindowManager(frame);
		if (Ini.isPropertyBool(Ini.P_OPEN_WINDOW_MAXIMIZED))
		{
			AEnv.showMaximized(frame);
		}
		else
		{
			AEnv.showCenterScreen(frame);
		}
		frame = null;
	}	// launchZoom

}	// AZoom
