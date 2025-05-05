/**
 * 
 */
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

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.order.copy.C_OrderLine_CopyRecordSupport;
import de.metas.order.copy.C_Order_CopyRecordSupport;
import de.metas.order.copy.C_Order_Cost_CopyRecordSupport;
import de.metas.workflow.service.impl.AD_WF_Node_CopyRecordSupport;
import de.metas.workflow.service.impl.AD_Workflow_CopyRecordSupport;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_Cost;
import org.compiere.model.I_EXP_Format;

/**
 * @author Cristina Ghita, METAS.RO
 * 
 */
public class CopyValidator extends AbstractModelInterceptor
{
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		CopyRecordFactory.registerCopyRecordSupport(I_C_Order.Table_Name, C_Order_CopyRecordSupport.class);
		CopyRecordFactory.registerCopyRecordSupport(I_C_OrderLine.Table_Name, C_OrderLine_CopyRecordSupport.class);
		CopyRecordFactory.registerCopyRecordSupport(I_C_Order_Cost.Table_Name, C_Order_Cost_CopyRecordSupport.class);
		CopyRecordFactory.registerCopyRecordSupport(I_AD_WF_Node.Table_Name, AD_WF_Node_CopyRecordSupport.class);
		CopyRecordFactory.registerCopyRecordSupport(I_AD_Workflow.Table_Name, AD_Workflow_CopyRecordSupport.class);

		// Backward compatibility
		CopyRecordFactory.enableForTableName(I_C_Invoice.Table_Name);
		CopyRecordFactory.enableForTableName(I_EXP_Format.Table_Name);
		// NOTE: Please don't add more tables here, but add them in coresponding projects/model interceptors.
	}
}
