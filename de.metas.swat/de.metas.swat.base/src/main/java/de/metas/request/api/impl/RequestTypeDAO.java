package de.metas.request.api.impl;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Services;
import org.compiere.model.I_R_RequestType;

import de.metas.request.api.IRequestTypeDAO;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class RequestTypeDAO implements IRequestTypeDAO
{

	public static final String InternalName_CustomerComplaint = "A_CustomerComplaint";
	public static final String InternalName_VendorComplaint = "B_VendorComplaint";
	
	@Override
	public I_R_RequestType retrieveVendorRequestType()
	{
		return retrieveRequestTypeByInternalName(InternalName_VendorComplaint);
	}
	
	@Override
	public I_R_RequestType retrieveCustomerRequestType()
	{
		return retrieveRequestTypeByInternalName(InternalName_CustomerComplaint);
	}
	
	private I_R_RequestType retrieveRequestTypeByInternalName(final String internalName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		
		final IQueryBuilder<I_R_RequestType> queryBuilder = queryBL.createQueryBuilder(I_R_RequestType.class);
		
		queryBuilder.addOnlyActiveRecordsFilter()
		.addEqualsFilter(I_R_RequestType.COLUMNNAME_InternalName, internalName);
		
		return queryBuilder
				.create()
				.firstOnly(I_R_RequestType.class)//covered by unique index
				;
	}
}
