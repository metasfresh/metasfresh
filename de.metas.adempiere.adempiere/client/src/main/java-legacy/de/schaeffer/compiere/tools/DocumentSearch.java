/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Jan Roessler - Schaeffer                              *
* Copyright (C) Contributors                                          *
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
**********************************************************************/

package de.schaeffer.compiere.tools;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import org.adempiere.util.AbstractDocumentSearch;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.model.MQuery;

/**
 * Executes search and opens windows for defined transaction codes
 *
 * @author Jan Roessler, jr@schaeffer-ag.de
 *
 */
public class DocumentSearch extends AbstractDocumentSearch {

	@Override
	protected boolean openWindow(int windowId, MQuery query)
	{
		final AWindow frame = new AWindow();
		AEnv.addToWindowManager(frame);
		final boolean ok = frame.initWindow(windowId, query);
		frame.pack();
		AEnv.showCenterScreen(frame);
		return ok;
	}

}
