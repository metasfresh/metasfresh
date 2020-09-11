/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
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
package org.eevolution.form;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import org.compiere.apps.wf.WFPanel;
import org.compiere.util.DB;
import org.compiere.wf.MWorkflow;

/**
 * Manufacturing WorkFlow Editor
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class WFPanelManufacturing extends WFPanel {
	private static final long serialVersionUID = 1L;
	
	private static final String WF_WhereClause = MWorkflow.COLUMNNAME_WorkflowType+" IN ("
														+    DB.TO_STRING(MWorkflow.WORKFLOWTYPE_Manufacturing)
														+","+DB.TO_STRING(MWorkflow.WORKFLOWTYPE_Quality)
														+")";
	
	private static final int WF_Window_ID = 53005; // TODO: HARDCODED (Manufacturing Workflows)
	/*
SELECT AD_Window_Id
FROM AD_Window
WHERE Name = 'Manufacturing Workflows'
	 */
	
	/**
	 * Default constructor  
	 */
	public WFPanelManufacturing() {
		super(null, WF_WhereClause, WF_Window_ID);
	}
}
