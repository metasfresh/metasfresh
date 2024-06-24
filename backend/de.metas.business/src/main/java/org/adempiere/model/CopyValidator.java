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
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_OrderLine;
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
		CopyRecordFactory.registerCopyRecordSupport(I_C_OrderLine.Table_Name, C_OrderLine_CopyRecordSupport.class);

		// Backward compatibility
		CopyRecordFactory.enableForTableName(I_C_Invoice.Table_Name);
		CopyRecordFactory.enableForTableName(I_EXP_Format.Table_Name);
		// NOTE: Please don't add more tables here, but add them in coresponding projects/model interceptors.
	}
}
