/**********************************************************************
 * This file is part of Adempiere ERP Bazaar *
 * http://www.adempiere.org *
 * *
 * Copyright (C) Contributors *
 * *
 * This program is free software; you can redistribute it and/or *
 * modify it under the terms of the GNU General Public License *
 * as published by the Free Software Foundation; either version 2 *
 * of the License, or (at your option) any later version. *
 * *
 * This program is distributed in the hope that it will be useful, *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the *
 * GNU General Public License for more details. *
 * *
 * You should have received a copy of the GNU General Public License *
 * along with this program; if not, write to the Free Software *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, *
 * MA 02110-1301, USA. *
 * *
 * Contributors: *
 * - Carlos Ruiz - globalqss *
 ***********************************************************************/
package org.adempiere.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.compiere.model.I_I_PriceList;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.X_I_PriceList;
import org.compiere.model.X_M_ProductPriceVendorBreak;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

import de.metas.pricing.service.ProductPrices;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.ProductId;

/**
 * Import Price Lists from I_PriceList
 *
 * @author Carlos Ruiz
 */
public class ImportPriceList extends JavaProcess
{
	/** Client to be imported to */
	private int m_AD_Client_ID = 0;
	/** Delete old Imported */
	private boolean m_deleteOldImported = false;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("AD_Client_ID"))
				m_AD_Client_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DeleteOldImported"))
				m_deleteOldImported = "Y".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);
		}
	}	// prepare

	/**
	 * Perform process.
	 *
	 * @return Message
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + m_AD_Client_ID;

		int m_discountschema_id = DB.getSQLValue(get_TrxName(),
				"SELECT MIN(M_DiscountSchema_ID) FROM M_DiscountSchema WHERE DiscountType='P' AND IsActive='Y' AND AD_Client_ID=?",
				m_AD_Client_ID);
		if (m_discountschema_id <= 0)
			throw new AdempiereUserError("Price List Schema not configured");

		// **** Prepare ****

		// Delete Old Imported
		if (m_deleteOldImported)
		{
			sql = new StringBuffer("DELETE FROM I_PriceList "
					+ "WHERE I_IsImported='Y'").append(clientCheck);
			no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
			log.info("Deleted Old Imported =" + no);
		}

		// Set Client, Org, IsActive, Created/Updated, EnforcePriceLimit, IsSOPriceList, IsTaxIncluded, PricePrecision
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET AD_Client_ID = COALESCE (AD_Client_ID, ").append(m_AD_Client_ID).append("),"
						+ " AD_Org_ID = COALESCE (AD_Org_ID, 0),"
						+ " IsActive = COALESCE (IsActive, 'Y'),"
						+ " Created = COALESCE (Created, now()),"
						+ " CreatedBy = COALESCE (CreatedBy, 0),"
						+ " Updated = COALESCE (Updated, now()),"
						+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
						+ " EnforcePriceLimit = COALESCE (EnforcePriceLimit, 'N'),"
						+ " IsSOPriceList = COALESCE (IsSOPriceList, 'N'),"
						+ " IsTaxIncluded = COALESCE (IsTaxIncluded, 'N'),"
						+ " PricePrecision = COALESCE (PricePrecision, 2),"
						+ " I_ErrorMsg = ' ',"
						+ " I_IsImported = 'N' "
						+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		log.info("Reset=" + no);

		// Set Optional BPartner
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p"
				+ " WHERE I_PriceList.BPartner_Value=p.Value AND I_PriceList.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		log.info("BPartner=" + no);
		//
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid BPartner,' "
				+ "WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid BPartner=" + no);

		// Product
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET M_Product_ID=(SELECT MAX(M_Product_ID) FROM M_Product p"
				+ " WHERE I_PriceList.ProductValue=p.Value AND I_PriceList.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE M_Product_ID IS NULL AND ProductValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		log.debug("Set Product from Value=" + no);
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Product, ' "
				+ "WHERE M_Product_ID IS NULL AND (ProductValue IS NOT NULL)"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Product=" + no);

		// **** Find Price List
		// Name
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET M_PriceList_ID=(SELECT M_PriceList_ID FROM M_PriceList p"
				+ " WHERE I_PriceList.Name=p.Name AND I_PriceList.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE M_PriceList_ID IS NULL"
				+ " AND I_IsImported='N'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		log.info("Price List Existing Value=" + no);

		// **** Find Price List Version
		// List Name (ID) + ValidFrom
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET M_PriceList_Version_ID=(SELECT M_PriceList_Version_ID FROM M_PriceList_Version p"
				+ " WHERE I_PriceList.ValidFrom=p.ValidFrom AND I_PriceList.M_PriceList_ID=p.M_PriceList_ID) "
				+ "WHERE M_PriceList_ID IS NOT NULL AND M_PriceList_Version_ID IS NULL"
				+ " AND I_IsImported='N'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		log.info("Price List Version Existing Value=" + no);

		/*
		 * UOM For Future USE
		 * // Set UOM (System/own)
		 * sql = new StringBuffer ("UPDATE I_PriceList "
		 * + "SET X12DE355 = "
		 * + "(SELECT MAX(X12DE355) FROM C_UOM u WHERE u.IsDefault='Y' AND u.AD_Client_ID IN (0,I_PriceList.AD_Client_ID)) "
		 * + "WHERE X12DE355 IS NULL AND C_UOM_ID IS NULL"
		 * + " AND I_IsImported<>'Y'").append(clientCheck);
		 * no = DB.executeUpdate(sql.toString(), get_TrxName());
		 * log.debug("Set UOM Default=" + no);
		 * //
		 * sql = new StringBuffer ("UPDATE I_PriceList "
		 * + "SET C_UOM_ID = (SELECT C_UOM_ID FROM C_UOM u WHERE u.X12DE355=I_PriceList.X12DE355 AND u.AD_Client_ID IN (0,I_PriceList.AD_Client_ID)) "
		 * + "WHERE C_UOM_ID IS NULL"
		 * + " AND I_IsImported<>'Y'").append(clientCheck);
		 * no = DB.executeUpdate(sql.toString(), get_TrxName());
		 * log.info("Set UOM=" + no);
		 * //
		 * sql = new StringBuffer ("UPDATE I_PriceList "
		 * + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid UOM, ' "
		 * + "WHERE C_UOM_ID IS NULL"
		 * + " AND I_IsImported<>'Y'").append(clientCheck);
		 * no = DB.executeUpdate(sql.toString(), get_TrxName());
		 * if (no != 0)
		 * log.warn("Invalid UOM=" + no);
		 */

		// Set Currency
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET ISO_Code=(SELECT ISO_Code FROM C_Currency c"
				+ " INNER JOIN C_AcctSchema a ON (a.C_Currency_ID=c.C_Currency_ID)"
				+ " INNER JOIN AD_ClientInfo ci ON (a.C_AcctSchema_ID=ci.C_AcctSchema1_ID)"
				+ " WHERE ci.AD_Client_ID=I_PriceList.AD_Client_ID) "
				+ "WHERE C_Currency_ID IS NULL AND ISO_Code IS NULL"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		log.debug("Set Currency Default=" + no);
		//
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET C_Currency_ID=(SELECT C_Currency_ID FROM C_Currency c"
				+ " WHERE I_PriceList.ISO_Code=c.ISO_Code AND c.AD_Client_ID IN (0,I_PriceList.AD_Client_ID)) "
				+ "WHERE C_Currency_ID IS NULL"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		log.info("doIt- Set Currency=" + no);
		//
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Currency,' "
				+ "WHERE C_Currency_ID IS NULL"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Currency=" + no);

		// Mandatory Name
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Mandatory Name,' "
				+ "WHERE Name IS NULL"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("No Mandatory Name=" + no);

		// Mandatory ValidFrom
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Mandatory ValidFrom,' "
				+ "WHERE ValidFrom IS NULL"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("No Mandatory ValidFrom=" + no);

		// Mandatory BreakValue if BPartner set
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Mandatory BreakValue,' "
				+ "WHERE BreakValue IS NULL AND (C_BPartner_ID IS NOT NULL OR BPartner_Value IS NOT NULL)"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("No Mandatory BreakValue=" + no);

		commitEx();

		// -------------------------------------------------------------------
		int noInsertpp = 0;
		int noUpdatepp = 0;
		int noInsertppvb = 0;
		int noUpdateppvb = 0;
		int noInsertpl = 0;
		int noInsertplv = 0;

		// Go through Records
		log.debug("start inserting/updating ...");
		sql = new StringBuffer("SELECT * FROM I_PriceList WHERE I_IsImported='N'")
				.append(clientCheck);
		PreparedStatement pstmt_setImported = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			// Set Imported = Y
			pstmt_setImported = DB.prepareStatement("UPDATE I_PriceList SET I_IsImported='Y', M_PriceList_ID=?, "
					+ "Updated=now(), Processed='Y' WHERE I_PriceList_ID=?", get_TrxName());

			//
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				X_I_PriceList imp = new X_I_PriceList(getCtx(), rs, get_TrxName());
				int I_PriceList_ID = imp.getI_PriceList_ID();
				int M_PriceList_ID = imp.getM_PriceList_ID();
				if (M_PriceList_ID == 0)
				{
					// try to obtain the ID directly from DB
					M_PriceList_ID = DB.getSQLValue(get_TrxName(), "SELECT M_PriceList_ID FROM M_PriceList WHERE AD_Client_ID=? AND Name=?", m_AD_Client_ID, imp.getName());
					if (M_PriceList_ID < 0)
						M_PriceList_ID = 0;
				}
				boolean newPriceList = M_PriceList_ID == 0;
				log.debug("I_PriceList_ID=" + I_PriceList_ID + ", M_PriceList_ID=" + M_PriceList_ID);

				I_M_PriceList pricelist = null;
				// PriceList
				if (newPriceList)			// Insert new Price List
				{
					pricelist = newPriceList(imp);
					try
					{
						save(pricelist);
						M_PriceList_ID = pricelist.getM_PriceList_ID();
						log.trace("Insert Price List");
						noInsertpl++;
					}
					catch(Exception ex)
					{
						StringBuffer sql0 = new StringBuffer("UPDATE I_PriceList i "
								+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||").append(DB.TO_STRING("Insert Price List failed"))
								.append("WHERE I_PriceList_ID=").append(I_PriceList_ID);
						DB.executeUpdateAndSaveErrorOnFail(sql0.toString(), get_TrxName());
						continue;
					}
				}
				else
				{
					// NOTE no else clause - if the price list already exists it's not updated
					pricelist = load(M_PriceList_ID, I_M_PriceList.class);
				}

				int M_PriceList_Version_ID = imp.getM_PriceList_Version_ID();
				if (M_PriceList_Version_ID == 0)
				{
					// try to obtain the ID directly from DB
					M_PriceList_Version_ID = DB.getSQLValue(get_TrxName(), "SELECT M_PriceList_Version_ID FROM M_PriceList_Version WHERE ValidFrom=? AND M_PriceList_ID=?", new Object[] { imp.getValidFrom(), M_PriceList_ID });
					if (M_PriceList_Version_ID < 0)
						M_PriceList_Version_ID = 0;
				}
				boolean newPriceListVersion = M_PriceList_Version_ID == 0;
				log.debug("I_PriceList_ID=" + I_PriceList_ID + ", M_PriceList_Version_ID=" + M_PriceList_Version_ID);

				I_M_PriceList_Version pricelistversion = null;
				// PriceListVersion
				if (newPriceListVersion)			// Insert new Price List Version
				{
					pricelistversion = newPriceListVersion(pricelist);
					pricelistversion.setValidFrom(imp.getValidFrom());
					pricelistversion.setName(pricelist.getName() + " " + imp.getValidFrom());
					pricelistversion.setM_DiscountSchema_ID(m_discountschema_id);

					try
					{
						save(pricelistversion);
						M_PriceList_Version_ID = pricelistversion.getM_PriceList_Version_ID();
						log.trace("Insert Price List Version");
						noInsertplv++;
					}
					catch(Exception ex)
					{
						StringBuffer sql0 = new StringBuffer("UPDATE I_PriceList i "
								+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||").append(DB.TO_STRING("Insert Price List Version failed"))
								.append("WHERE I_PriceList_ID=").append(I_PriceList_ID);
						DB.executeUpdateAndSaveErrorOnFail(sql0.toString(), get_TrxName());
						continue;
					}
				}
				else
				{
					// NOTE no else clause - if the price list version already exists it's not updated
					pricelistversion = load(M_PriceList_Version_ID, I_M_PriceList_Version.class);
				}

				// @TODO: C_UOM is intended for future USE - not useful at this moment

				// If bpartner then insert/update into M_ProductPriceVendorBreak, otherwise insert/update M_ProductPrice
				if (imp.getC_BPartner_ID() > 0)
				{
					// M_ProductPriceVendorBreak
					int M_ProductPriceVendorBreak_ID = DB.getSQLValue(get_TrxName(),
							"SELECT M_ProductPriceVendorBreak_ID " +
									"FROM M_ProductPriceVendorBreak " +
									"WHERE M_PriceList_Version_ID=? AND " +
									"C_BPartner_ID=? AND " +
									"M_Product_ID=? AND " +
									"BreakValue=?",
							new Object[] { pricelistversion.getM_PriceList_Version_ID(), imp.getC_BPartner_ID(), imp.getM_Product_ID(), imp.getBreakValue() });
					if (M_ProductPriceVendorBreak_ID < 0)
						M_ProductPriceVendorBreak_ID = 0;
					X_M_ProductPriceVendorBreak ppvb = new X_M_ProductPriceVendorBreak(getCtx(), M_ProductPriceVendorBreak_ID, get_TrxName());
					boolean isInsert = false;
					if (M_ProductPriceVendorBreak_ID == 0)
					{
						ppvb.setM_PriceList_Version_ID(pricelistversion.getM_PriceList_Version_ID());
						ppvb.setC_BPartner_ID(imp.getC_BPartner_ID());
						ppvb.setM_Product_ID(imp.getM_Product_ID());
						ppvb.setBreakValue(imp.getBreakValue());
						isInsert = true;
					}
					ppvb.setPriceLimit(imp.getPriceLimit());
					ppvb.setPriceList(imp.getPriceList());
					ppvb.setPriceStd(imp.getPriceStd());
					if (ppvb.save())
					{
						if (isInsert)
							noInsertppvb++;
						else
							noUpdateppvb++;
						log.trace("Insert/Update Product Price Vendor Break");
					}
					else
					{
						StringBuffer sql0 = new StringBuffer("UPDATE I_PriceList i "
								+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||").append(DB.TO_STRING("Insert/Update Product Price Vendor Break Version failed"))
										.append("WHERE I_PriceList_ID=").append(I_PriceList_ID);
						DB.executeUpdateAndSaveErrorOnFail(sql0.toString(), get_TrxName());
						continue;
					}
				}
				else
				{
					// M_ProductPrice
					// I_M_ProductPrice pp = MProductPrice.get(getCtx(), pricelistversion.getM_PriceList_Version_ID(), imp.getM_Product_ID(), get_TrxName());
					I_M_ProductPrice pp = ProductPrices.retrieveMainProductPriceOrNull(pricelistversion, ProductId.ofRepoId(imp.getM_Product_ID()));

					final boolean isInsert;
					if (pp != null)
					{
						pp.setPriceLimit(imp.getPriceLimit());
						pp.setPriceList(imp.getPriceList());
						pp.setPriceStd(imp.getPriceStd());
						isInsert = false;
					}
					else
					{
						pp = newProductPrice(pricelistversion, imp.getM_Product_ID(), imp.getPriceList(), imp.getPriceStd(), imp.getPriceLimit());
						isInsert = true;
					}

					try
					{
						save(pp);
						log.trace("Insert/Update Product Price");
						if (isInsert)
							noInsertpp++;
						else
							noUpdatepp++;
					}
					catch (Exception e)
					{
						StringBuffer sql0 = new StringBuffer("UPDATE I_PriceList i "
								+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||").append(DB.TO_STRING("Insert/Update Product Price failed: " + e.getLocalizedMessage()))
										.append("WHERE I_PriceList_ID=").append(I_PriceList_ID);
						DB.executeUpdateAndSaveErrorOnFail(sql0.toString(), get_TrxName());
						continue;
					}
				}

				// Update I_PriceList
				pstmt_setImported.setInt(1, M_PriceList_ID);
				pstmt_setImported.setInt(2, I_PriceList_ID);
				no = pstmt_setImported.executeUpdate();
				//
				commitEx();
			}	// for all I_PriceList
			//
		}
		catch (SQLException e)
		{
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
			DB.close(pstmt_setImported);
			pstmt_setImported = null;
		}

		// Set Error to indicator to not imported
		sql = new StringBuffer("UPDATE I_PriceList "
				+ "SET I_IsImported='N', Updated=now() "
				+ "WHERE I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
		addLog(0, null, new BigDecimal(no), "@Errors@");
		addLog(0, null, new BigDecimal(noInsertpl), "@M_PriceList_ID@: @Inserted@");
		addLog(0, null, new BigDecimal(noInsertplv), "@M_PriceList_Version_ID@: @Inserted@");
		addLog(0, null, new BigDecimal(noInsertpp), "Product Price: @Inserted@");
		addLog(0, null, new BigDecimal(noUpdatepp), "Product Price: @Updated@");
		addLog(0, null, new BigDecimal(noInsertppvb), "@M_ProductPriceVendorBreak_ID@: @Inserted@");
		addLog(0, null, new BigDecimal(noUpdateppvb), "@M_ProductPriceVendorBreak_ID@: @Updated@");
		return "";
	}	// doIt

	private I_M_ProductPrice newProductPrice(I_M_PriceList_Version plv, int m_Product_ID, BigDecimal priceList, BigDecimal priceStd, BigDecimal priceLimit)
	{
		final I_M_ProductPrice pp = newInstance(I_M_ProductPrice.class, plv);
		pp.setAD_Org_ID(plv.getAD_Org_ID());
		pp.setM_PriceList_Version_ID(plv.getM_PriceList_Version_ID());
		pp.setM_Product_ID(m_Product_ID);
		pp.setPriceLimit (priceLimit);
		pp.setPriceList (priceList);
		pp.setPriceStd (priceStd);
		return pp;
	}

	private I_M_PriceList_Version newPriceListVersion(final I_M_PriceList pl)
	{
		final I_M_PriceList_Version plv = newInstance(I_M_PriceList_Version.class, pl);
		plv.setAD_Org_ID(pl.getAD_Org_ID());
		plv.setM_PriceList_ID(pl.getM_PriceList_ID());
		return plv;
	}

	private static I_M_PriceList newPriceList(final I_I_PriceList impPL)
	{
		final I_M_PriceList pl = newInstance(I_M_PriceList.class, impPL);
		pl.setAD_Org_ID(impPL.getAD_Org_ID());
		// pl.setUpdatedBy(impPL.getUpdatedBy());
		//
		pl.setName(impPL.getName());
		pl.setDescription(impPL.getDescription());
		pl.setC_Currency_ID(impPL.getC_Currency_ID());
		pl.setPricePrecision(impPL.getPricePrecision());
		pl.setIsSOPriceList(impPL.isSOPriceList());
		pl.setIsTaxIncluded(impPL.isTaxIncluded());
		pl.setEnforcePriceLimit(impPL.isEnforcePriceLimit());

		return pl;
	}

}	// ImportProduct
