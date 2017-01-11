package de.metas.document.impl;

/*
 * #%L
 * de.metas.swat.base
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


import static org.adempiere.util.CustomColNames.C_Invoice_BPARTNERADDRESS;
import static org.compiere.model.I_C_DocType.COLUMNNAME_AD_Org_ID;
import static org.compiere.model.I_C_DocType.COLUMNNAME_DocBaseType;
import static org.compiere.model.I_C_DocType.COLUMNNAME_DocSubType;
import static org.compiere.model.I_C_DocType.COLUMNNAME_IsDefault;
import static org.compiere.model.I_C_DocType.Table_Name;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.db.IDBService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.MDocType;
import org.compiere.model.MSequence;
import org.compiere.model.Query;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.util.CacheCtx;
import de.metas.document.IDocumentPA;
import de.metas.inout.model.I_M_InOut;
import de.metas.interfaces.I_C_DocType;

public final class DocumentPA implements IDocumentPA
{

	// private static final Logger logger = CLogMgt.getLogger(DocumentPA.class);
	private static final String ERR_DocTypeNotSelected = "DocTypeNotSelected";

	public static final String UPDATE_ORDERS_BILL_LOCATION = //
	"UPDATE C_Order o" //
			+ " SET " + I_C_Order.COLUMNNAME_BillToAddress
			+ "= l."
			+ I_C_BPartner_Location.COLUMNNAME_Address //
			+ " FROM C_BPartner_Location l " //
			+ " WHERE "//
			+ "    o.Bill_Location_ID=? " //
			+ "    AND o.Bill_Location_ID=l.C_BPartner_Location_ID " //
			+ "    AND o." + I_C_Order.COLUMNNAME_IsUseBillToAddress + "='N'";

	public static final String UPDATE_ORDERS_BPARTNER_LOCATION = //
	"UPDATE C_Order o" //
			+ " SET " + I_C_Order.COLUMNNAME_BPartnerAddress
			+ "= l."
			+ I_C_BPartner_Location.COLUMNNAME_Address //
			+ " FROM C_BPartner_Location l " //
			+ " WHERE "//
			+ "    o.C_BPartner_Location_ID=? " //
			+ "    AND o.C_BPartner_Location_ID=l.C_BPartner_Location_ID " //
			+ "    AND o." + I_C_Order.COLUMNNAME_IsUseBPartnerAddress + "='N'";

	public static final String UPDATE_INVOICE_BPARTNER_LOCATION = //
	"UPDATE C_Invoice i" //
			+ " SET " + C_Invoice_BPARTNERADDRESS
			+ "= l."
			+ I_C_BPartner_Location.COLUMNNAME_Address //
			+ " FROM C_BPartner_Location l " //
			+ " WHERE "//
			+ "    i.C_BPartner_Location_ID=? " //
			+ "    AND i.C_BPartner_Location_ID=l.C_BPartner_Location_ID " //
			+ "    AND i." + I_C_Order.COLUMNNAME_IsUseBPartnerAddress + "='N'";

	public static final String UPDATE_INOUT_BPARTNER_LOCATION = //
	"UPDATE M_InOut i" //
			+ " SET " + I_M_InOut.COLUMNNAME_BPartnerAddress
			+ "= l."
			+ I_C_BPartner_Location.COLUMNNAME_Address //
			+ " FROM C_BPartner_Location l " //
			+ " WHERE "//
			+ "    i.C_BPartner_Location_ID=? " //
			+ "    AND i.C_BPartner_Location_ID=l.C_BPartner_Location_ID " //
			+ "    AND i." + I_C_Order.COLUMNNAME_IsUseBPartnerAddress + "='N'";

	@Override
	public String retrieveDocBaseType(final int docTypeId, final String trxName)
	{

		final MDocType docType = new MDocType(Env.getCtx(), docTypeId, trxName);

		if (docType.getC_DocType_ID() == 0)
		{
			throw new IllegalArgumentException(
					"Found no C_DocType record for C_DocType_ID " + docTypeId);
		}

		return docType.getDocBaseType();
	}

	@Override
	public int retriveDocTypeId(final Properties ctx, final int adOrgId, final String docBaseType)
	{

		final MDocType[] docTypes = MDocType.getOfDocBaseType(ctx,
				docBaseType);
		if (docTypes.length == 1)
		{
			return docTypes[0].getC_DocType_ID();
		}

		for (final MDocType docType : docTypes)
		{
			if (docType.getAD_Org_ID() == adOrgId)
			{
				return docType.getC_DocType_ID();
			}
		}

		throw new IllegalArgumentException(
				"Expecting one C_DocType record for base type " + docBaseType
						+ ", but there are " + docTypes.length + ": " + docTypes);
	}

	@Override
	public int retriveDocTypeIdForDocSubtype(final Properties ctx, final String docBaseType, final String docSubType)
	{

		final MDocType[] docTypes = MDocType.getOfDocBaseType(ctx,
				docBaseType);

		for (final MDocType docType : docTypes)
		{
			if (docSubType.equals(docType.getDocSubType()))
			{
				return docType.getC_DocType_ID();
			}
		}

		throw new AdempiereException("@" + ERR_DocTypeNotSelected + "@");
	}

	@Override
	public int updateDocumentAddresses(final int bPartnerLocationId,
			final String trxName)
	{

		int result = performUpdate(bPartnerLocationId,
				UPDATE_ORDERS_BILL_LOCATION, trxName);
		result += performUpdate(bPartnerLocationId,
				UPDATE_ORDERS_BPARTNER_LOCATION, trxName);

		result += performUpdate(bPartnerLocationId,
				UPDATE_INOUT_BPARTNER_LOCATION, trxName);

		result += performUpdate(bPartnerLocationId,
				UPDATE_INVOICE_BPARTNER_LOCATION, trxName);

		return result;

	}

	private int performUpdate(final int bPartnerLocationId, final String sql,
			final String trxName)
	{

		final IDBService db = Services.get(IDBService.class);
		final PreparedStatement pstmt = db.mkPstmt(sql, trxName);

		try
		{

			pstmt.setInt(1, bPartnerLocationId);
			return pstmt.executeUpdate();

		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			db.close(pstmt);
		}
	}

	@Cached
	@Override
	public I_C_DocType retrieve(
			final @CacheCtx Properties ctx,
			final int adOrgId,
			final String docBaseType,
			final String docSubType,
			final boolean throwEx,
			final String trxName)
	{
		final String wc = COLUMNNAME_AD_Org_ID + " IN (0, ?) AND " + COLUMNNAME_DocBaseType + "=? AND " + COLUMNNAME_DocSubType + "=?";

		final String orderBy = COLUMNNAME_AD_Org_ID + " DESC, " + COLUMNNAME_IsDefault + " DESC";

		final I_C_DocType type =
				new Query(ctx, Table_Name, wc, trxName)
						.setParameters(adOrgId, docBaseType, docSubType)
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.setOrderBy(orderBy)
						.first(I_C_DocType.class);

		if (type == null && throwEx)
		{
			throw new AdempiereException("Found no C_DocType with AD_Org_ID in (0," + adOrgId + "), DocBaseType=" + docBaseType + ", DocSubType=" + docSubType);
		}

		return type;
	}

	@Override
	public I_C_DocType retrieve(
			final Properties ctx,
			final int adOrgId,
			final String docBaseType,
			final String docSubType,
			final String trxName)
	{
		final boolean throwEx = true;
		return retrieve(ctx, adOrgId, docBaseType, docSubType, throwEx, trxName);
	}

	@Override
	public I_C_DocType createDocType(
			final Properties ctx,
			final String entityType,
			final String Name,
			final String PrintName,
			final String DocBaseType,
			final String DocSubType,
			final int C_DocTypeShipment_ID,
			final int C_DocTypeInvoice_ID,
			final int StartNo,
			final int GL_Category_ID,
			final String trxName)
	{

		final MSequence sequence;
		if (StartNo != 0)
		{
			sequence = new MSequence(ctx, Env.getAD_Client_ID(ctx), Name, StartNo, trxName);
			sequence.saveEx();
		}
		else
		{
			sequence = null;
		}

		final MDocType dt = new MDocType(ctx, DocBaseType, Name, trxName);

		if (PrintName != null && PrintName.length() > 0)
		{
			dt.setPrintName(PrintName); // Defaults to Name
		}
		if (DocSubType != null)
		{
			dt.setDocSubType(DocSubType);
		}
		if (C_DocTypeShipment_ID != 0)
		{
			dt.setC_DocTypeShipment_ID(C_DocTypeShipment_ID);
		}
		if (C_DocTypeInvoice_ID != 0)
		{
			dt.setC_DocTypeInvoice_ID(C_DocTypeInvoice_ID);
		}
		if (GL_Category_ID != 0)
		{
			dt.setGL_Category_ID(GL_Category_ID);
		}
		if (sequence == null)
		{
			dt.setIsDocNoControlled(false);
		}
		else
		{
			dt.setIsDocNoControlled(true);
			dt.setDocNoSequence_ID(sequence.getAD_Sequence_ID());
		}
		dt.setIsSOTrx();

		final I_C_DocType result = InterfaceWrapperHelper.create(dt, I_C_DocType.class);
		result.setEntityType(entityType);

		return result;
	} // createDocType
}
