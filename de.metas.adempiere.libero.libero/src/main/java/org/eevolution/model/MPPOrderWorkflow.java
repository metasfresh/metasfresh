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
package org.eevolution.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * PP Order WorkFlow Model
 *
 * @author Jorg Janke
 * @version $Id: MPPOrderWorkflow.java,v 1.4 2006/07/30 00:51:05 jjanke Exp $
 *
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class MPPOrderWorkflow extends X_PP_Order_Workflow
{
	private static final long serialVersionUID = 1L;

	public MPPOrderWorkflow(final Properties ctx, final int PP_Order_Workflow_ID, final String trxName)
	{
		super(ctx, PP_Order_Workflow_ID, trxName);
		if (is_new())
		{
			// setPP_Order_Workflow_ID (0);
			// setValue (null);
			// setName (null);
			setAccessLevel(ACCESSLEVEL_Organization);
			setAuthor(".");
			setDurationUnit(DURATIONUNIT_Day);
			setDuration(1);
			setEntityType(ENTITYTYPE_UserMaintained);	// U
			setIsDefault(false);
			setPublishStatus(PUBLISHSTATUS_UnderRevision);	// U
			setVersion(0);
			setCost(BigDecimal.ZERO);
			setWaitingTime(0);
			setWorkingTime(0);
		}
	}

	public MPPOrderWorkflow(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

}
