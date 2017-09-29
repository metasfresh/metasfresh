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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *	AccessSqlParserTest tests the class
 *	AccessSqlParser
 *	
 *  @author Jorg Janke
 *  @version $Id: AccessSqlParserTest.java,v 1.2 2006/07/30 00:58:04 jjanke Exp $
 */
public class AccessSqlParserTest
{


	/**
	 * Run the oneTable test
	 */
	@Test
	public void testOneTable()
	{
		String sql = "SELECT AD_Table_ID, TableName FROM AD_Table WHERE IsActive='Y'";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[AD_Table|0]", fixture.toString());
	}

	/**
	 * Run the oneTableSyn test
	 */
	@Test
	public void testOneTableSyn()
	{
		String sql = "SELECT t.AD_Table_ID, t.TableName FROM AD_Table t WHERE t.IsActive='Y'";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[AD_Table=t|0]", fixture.toString());
	}

	/**
	 * Run the oneTableSyn test
	 */
	@Test
	public void testOneTableSynAS()
	{
		String sql = "SELECT t.AD_Table_ID, t.TableName FROM AD_Table AS t WHERE t.IsActive='Y'";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[AD_Table=t|0]", fixture.toString());
	}

	/**
	 * Run the twoTable test
	 */
	@Test
	public void testTwoTable()
	{
		String sql = "SELECT t.AD_Table_ID, t.TableName, c.AD_Column_ID, c.ColumnName FROM AD_Table t, AD_Column c WHERE t.AD_Table_ID=c.AD_Table_ID AND t.IsActive='Y'";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[AD_Table=t,AD_Column=c|0]", fixture.toString());
	}

	/**
	 * Run the twoTableSyn test
	 */
	@Test
	public void testTwoTableSyn()
	{
		String sql = "SELECT t.AD_Table_ID, t.TableName, c.AD_Column_ID, c.ColumnName FROM AD_Table as t, AD_Column AS c WHERE t.AD_Table_ID=c.AD_Table_ID AND t.IsActive='Y'";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[AD_Table=t,AD_Column=c|0]", fixture.toString());
	}

	/**
	 * Run the joinInner test
	 */
	@Test
	public void testJoinInner()
	{
		String sql = "SELECT t.AD_Table_ID, t.TableName, c.AD_Column_ID, c.ColumnName "
			+ "FROM AD_Table t INNER JOIN AD_Column c ON (t.AD_Table_ID=c.AD_Table_ID) WHERE t.IsActive='Y'";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[AD_Table=t,AD_Column=c|0]", fixture.toString());
	}

	/**
	 * Run the joinOuter test
	 */
	@Test
	public void testJoinOuter()
	{
		String sql = "SELECT t.AD_Table_ID, t.TableName, c.AD_Column_ID, c.ColumnName "
			+ "FROM AD_Table t LEFT OUTER JOIN AD_Column c ON (t.AD_Table_ID=c.AD_Table_ID) WHERE t.IsActive='Y'";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[AD_Table=t,AD_Column=c|0]", fixture.toString());
	}

	/**
	 * Run the exists test
	 */
	@Test
	public void testExists()
	{
		String sql = "SELECT AD_Table.AD_Table_ID, AD_Table.TableName "
			+ "FROM AD_Table "
			+ "WHERE EXISTS (SELECT * FROM AD_Column c WHERE AD_Table.AD_Table_ID=c.AD_Table_ID)";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[AD_Column=c|AD_Table|1]", fixture.toString());
	}

	/**
	 * Run the exists test with syn
	 */
	@Test
	public void testExistsSyn()
	{
		String sql = "SELECT t.AD_Table_ID, t.TableName "
			+ "FROM AD_Table t "
			+ "WHERE EXISTS (SELECT * FROM AD_Column c WHERE t.AD_Table_ID=c.AD_Table_ID)";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[AD_Column=c|AD_Table=t|1]", fixture.toString());
	}

	/**
	 * Run the embeddedSelect test
	 */
	@Test
	public void testEmbeddedSelect()
	{
		String sql = "SELECT t.AD_Table_ID, t.TableName,"
			+ "(SELECT COUNT(c.ColumnName) FROM AD_Column c WHERE t.AD_Table_ID=c.AD_Table_ID) "
			+ "FROM AD_Table t WHERE t.IsActive='Y'";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[AD_Column=c|AD_Table=t|1]", fixture.toString());
	}

	/**
	 * Run the embeddedFrom test
	 */
	@Test
	public void testEmbeddedFrom()
	{
		String sql = "SELECT t.AD_Table_ID, t.TableName, cc.CCount "
			+ "FROM AD_Table t,"
			+ "(SELECT COUNT(ColumnName) AS CCount FROM AD_Column) cc "
			+ "WHERE t.IsActive='Y'";

		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[AD_Column|AD_Table=t,(##)=cc|1]", fixture.toString());
	}

	/**
	 * Run the Product & Instance Attribute Query
	 */
	@Test
	public void testProductInstanceAttributeQuery()
	{
		String sql = "SELECT p.M_Product_ID, p.Discontinued, p.Value, p.Name, BOM_Qty_Available(p.M_Product_ID,?) AS QtyAvailable, bomQtyList(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceList, bomQtyStd(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceStd, BOM_Qty_OnHand(p.M_Product_ID,?) AS QtyOnHand, BOM_Qty_Reserved(p.M_Product_ID,?) AS QtyReserved, BOM_Qty_Ordered(p.M_Product_ID,?) AS QtyOrdered, bomQtyStd(p.M_Product_ID, pr.M_PriceList_Version_ID)-bomQtyLimit(p.M_Product_ID, pr.M_PriceList_Version_ID) AS Margin, bomQtyLimit(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceLimit, pa.IsInstanceAttribute FROM M_Product p INNER JOIN M_ProductPrice pr ON (p.M_Product_ID=pr.M_Product_ID) LEFT OUTER JOIN M_AttributeSet pa ON (p.M_AttributeSet_ID=pa.M_AttributeSet_ID) WHERE p.IsSummary='N' AND p.IsActive='Y' AND pr.IsActive='Y' AND pr.M_PriceList_Version_ID=? AND EXISTS (SELECT * FROM M_Storage s INNER JOIN M_AttributeSetInstance asi ON (s.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID) WHERE s.M_Product_ID=p.M_Product_ID AND asi.SerNo LIKE '33' AND asi.Lot LIKE '33' AND asi.M_Lot_ID=101 AND TRUNC(asi.GuaranteeDate)<TO_DATE('2003-10-16','YYYY-MM-DD') AND asi.M_AttributeSetInstance_ID IN (SELECT M_AttributeSetInstance_ID FROM M_AttributeInstance WHERE (M_Attribute_ID=103 AND Value LIKE '33') AND (M_Attribute_ID=102 AND M_AttributeValue_ID=106))) AND p.M_AttributeSetInstance_ID IN (SELECT M_AttributeSetInstance_ID FROM M_AttributeInstance WHERE (M_Attribute_ID=101 AND M_AttributeValue_ID=105) AND (M_Attribute_ID=100 AND M_AttributeValue_ID=102)) AND p.AD_Client_ID IN(0,11) AND p.AD_Org_ID IN(0,11,12) ORDER BY QtyAvailable DESC, Margin DESC";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[M_AttributeInstance|M_Storage=s,M_AttributeSetInstance=asi|M_AttributeInstance|M_Product=p,M_ProductPrice=pr,M_AttributeSet=pa|3]", fixture.toString());
	}
	
	/**
	 * Run the Product Attribute Query
	 */
	@Test
	public void testProductAttributeQuery()
	{
		String sql = "SELECT p.M_Product_ID, p.Discontinued, p.Value, p.Name, BOM_Qty_Available(p.M_Product_ID,?) AS QtyAvailable, bomQtyList(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceList, bomQtyStd(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceStd, BOM_Qty_OnHand(p.M_Product_ID,?) AS QtyOnHand, BOM_Qty_Reserved(p.M_Product_ID,?) AS QtyReserved, BOM_Qty_Ordered(p.M_Product_ID,?) AS QtyOrdered, bomQtyStd(p.M_Product_ID, pr.M_PriceList_Version_ID)-bomQtyLimit(p.M_Product_ID, pr.M_PriceList_Version_ID) AS Margin, bomQtyLimit(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceLimit, pa.IsInstanceAttribute FROM M_Product p INNER JOIN M_ProductPrice pr ON (p.M_Product_ID=pr.M_Product_ID) LEFT OUTER JOIN M_AttributeSet pa ON (p.M_AttributeSet_ID=pa.M_AttributeSet_ID) WHERE p.IsSummary='N' AND p.IsActive='Y' AND pr.IsActive='Y' AND pr.M_PriceList_Version_ID=? AND p.M_AttributeSetInstance_ID IN (SELECT M_AttributeSetInstance_ID FROM M_AttributeInstance WHERE (M_Attribute_ID=100 AND M_AttributeValue_ID=101)) ORDER BY QtyAvailable DESC, Margin DESC";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[M_AttributeInstance|M_Product=p,M_ProductPrice=pr,M_AttributeSet=pa|1]", fixture.toString());
	}

	
	/**
	 * <li>teo_sarca - [ 1652623 ] AccessSqlParser.getTableInfo(String) - tablename parsing bug
	 * <li>teo_sarca - [ 1964496 ] AccessSqlParser is not parsing well JOIN CLAUSE
	 */
	@Test
	public void testTableNameParsing()
	{
		//
		// BF [ 1652623 ] AccessSqlParser.getTableInfo(String) - tablename parsing bug
		String sql = 
			"SELECT SUM(il.QtyInvoiced)\n"
			+ "FROM RV_C_Invoice\n"
			+ "C_Invoice\n"
			+ "INNER JOIN RV_C_InvoiceLine il ON (C_Invoice.C_Invoice_ID=il.C_Invoice_ID) WHERE\n"
			+ "C_Invoice.IsSOTrx='Y' AND C_Invoice.Processed='Y' AND C_Invoice.IsPaid='Y'";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[RV_C_Invoice=C_Invoice,RV_C_InvoiceLine=il|0]", fixture.toString());
		//
		// BF [ 1964496 ] AccessSqlParser is not parsing well JOIN CLAUSE
		sql = 
			"SELECT C_Invoice.*  FROM C_Invoice\n"
			+"INNER JOIN C_BPartner bp ON (bp.C_BPartner_ID=C_Invoice.C_BPartner_ID) WHERE 1=0";
		;
		fixture = new AccessSqlParser(sql);
		assertEquals("AccessSqlParser[C_Invoice,C_BPartner=bp|0]", fixture.toString());
	}
	
	/**
	 * BF [ 2840157 ] AccessSqlParser is not parsing well ON keyword
	 * <pre>
	 * Following query is generating OutOfMemoryException:
	 * SELECT 1
	 * FROM M_Product p
	 * INNER JOIN M_Product_Category pc on
	 * (pc.M_Product_Category_ID=p.M_Product_Category_ID)
	 * LEFT OUTER JOIN M_Product_PO mpo ON (mpo.M_Product_ID=p.M_Product_ID)
	 *
	 * (please note the lower case "on")
	 * </pre>
	 * 
	 * @see https://sourceforge.net/tracker/?func=detail&aid=2840157&group_id=176962&atid=879332
	 */
	@Test
	public void test_BF2840157()
	{
		final String sql = 
			"SELECT 1 FROM M_Product p"
			+"\n"+"INNER JOIN M_Product_Category pc on (pc.M_Product_Category_ID=p.M_Product_Category_ID)"
			+"\n"+"LEFT OUTER JOIN M_Product_PO mpo ON (mpo.M_Product_ID=p.M_Product_ID)"
			+"\n"+" WHERE p.IsActive='Y' AND p.IsPurchased='Y'"
			+"\n"+"AND COALESCE(mpo.DeliveryTime_Promised,0) <= 0"
		;
		final String expected = "AccessSqlParser[M_Product=p,M_Product_Category=pc,M_Product_PO=mpo|0]";
		AccessSqlParser fixture = new AccessSqlParser(sql);
		assertEquals(expected, fixture.toString());
	}

}