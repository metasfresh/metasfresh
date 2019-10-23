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
package org.compiere.model;

import java.util.Properties;


/**
 *	Client Callout
 *	
 *  @author Karsten Thiemann - kthiemann@adempiere.org
 */
public class CalloutClient extends CalloutEngine
{

	/**
	 *	Shows a warning message if the attachment storing method is changed.
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String storeAttachmentOnFileSystem (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		return "StoreAttachmentWarning";
	}	//	storeAttachmentOnFilesystem
	
	/**
	 *	Shows a warning message if the archive storing method is changed.
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String storeArchiveOnFileSystem (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		return "StoreArchiveWarning";
	}	//	storeArchiveOnFileSystem
	
}	//	CalloutClient
