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

import java.util.List;

import org.compiere.model.MQuery;
import org.compiere.model.PO;

/**
 * 
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and
 *         RelationTypes
 */
public interface IZoomProvider {

	/**
	 * 
	 * @param po
	 *            the po we need zoom targets for
	 * @return a list of zoom targets. The {@link MQuery#getRecordCount()} of
	 *         the ZoomInfo's query member might be zero.
	 */
	List<ZoomInfoFactory.ZoomInfo> retrieveZoomInfos(PO po);
}
