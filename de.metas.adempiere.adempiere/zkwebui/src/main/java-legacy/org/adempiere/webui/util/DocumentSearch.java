/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Heng Sin Low                                          *
* Copyright (C) Idalica Corporation                                   *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Jan Roessler                                                      *
* - Heng Sin Low                                                      *
*                                                                     *
* Sponsors:                                                           *
* - Schaeffer                                                         *
* - Idalica Corporation                                               *
**********************************************************************/
package org.adempiere.webui.util;

import org.adempiere.util.AbstractDocumentSearch;
import org.adempiere.webui.session.SessionManager;
import org.compiere.model.MQuery;

/**
 *
 * @author hengsin
 *
 */
public class DocumentSearch extends AbstractDocumentSearch {

	@Override
	protected boolean openWindow(int windowId, MQuery query) {
		query.setRecordCount(1);
		SessionManager.getAppDesktop().showWindow(windowId, query);
		return true;
	}

}
