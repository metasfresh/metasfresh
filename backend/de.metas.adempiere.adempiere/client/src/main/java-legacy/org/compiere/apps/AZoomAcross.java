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

import com.google.common.collect.ImmutableList;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.POZoomSource;
import de.metas.document.references.related_documents.RelatedDocuments;
import de.metas.document.references.related_documents.RelatedDocumentsFactory;
import de.metas.document.references.related_documents.RelatedDocumentsPermissions;
import de.metas.document.references.related_documents.RelatedDocumentsPermissionsFactory;
import de.metas.document.references.related_documents.fact_acct.FactAcctRelatedDocumentsProvider;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import javax.swing.*;
import java.util.List;

/**
 * Application Zoom Across Launcher.
 * Called from APanel; Queries available Zoom Targets for Table.
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL - FR [ 1762465 ]
 * @author afalcone - Bug Fix [ 1659420 ] Usability: zoom across
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and RelationTypes
 * @version $Id: AZoomAcross.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class AZoomAcross
{
	/**
	 * Constructor
	 *
	 * @param invoker   component to display popup (optional)
	 * @param tableName zoom source table (i.e. the table we start from)
	 * @param query     query that specifies the zoom source PO (i.e. the PO we start from)
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
		logger.info("source={}", source);
		final String adLanguage = Env.getAD_Language(Env.getCtx());

		final List<RelatedDocuments> relatedDocumentsList = retrieveZoomTargets(source);
		for (final RelatedDocuments relatedDocuments : relatedDocumentsList)
		{
			final String caption = relatedDocuments.getCaption().translate(adLanguage);
			m_popup.add(caption).addActionListener(event -> launch(relatedDocuments));
		}

		if (relatedDocumentsList.isEmpty())
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

	private List<RelatedDocuments> retrieveZoomTargets(final IZoomSource source)
	{
		if (source == null)
		{
			return ImmutableList.of(); // guard against NPE
		}

		// in Swing this is not needed because we have the Posted button
		SpringContextHolder.instance.getBean(FactAcctRelatedDocumentsProvider.class).disable();

		final RelatedDocumentsFactory relatedDocumentsFactory = SpringContextHolder.instance.getBean(RelatedDocumentsFactory.class);
		final RelatedDocumentsPermissions permissions = RelatedDocumentsPermissionsFactory.ofRolePermissions(Env.getUserRolePermissions());
		return relatedDocumentsFactory.retrieveRelatedDocuments(source, permissions);
	}

	private void launch(final RelatedDocuments relatedDocuments)
	{
		final AdWindowId adWindowId = relatedDocuments.getAdWindowId();
		final MQuery query = relatedDocuments.getQuery();

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
	}    // launchZoom

}    // AZoom
