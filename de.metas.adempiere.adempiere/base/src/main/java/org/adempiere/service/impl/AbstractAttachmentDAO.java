package org.adempiere.service.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.IAttachmentDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Attachment;

public class AbstractAttachmentDAO implements IAttachmentDAO
{
	@Override
	public I_AD_Attachment retrieveAttachment(final Properties ctx, final int adTableId, final int recordId, final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Attachment.class)
				.setContext(ctx, trxName)
				.addEqualsFilter(I_AD_Attachment.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_AD_Attachment.COLUMNNAME_Record_ID, recordId)
				.create()
				.firstOnly(I_AD_Attachment.class);
	}
}
