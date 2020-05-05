/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package org.compiere.dbPort;

import static org.junit.Assert.assertEquals;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.junit.Test;

/**
 * Unit testing for Convert_PostgreSQL.
 * 
 * @author Low Heng Sin
 * @version 20061225
 */
public final class Convert_PostgreSQLTest
{
	private Convert_PostgreSQL convert = new Convert_PostgreSQL();
	String sql;
	String sqe;
	List<String> convertResult;

	public Convert_PostgreSQLTest()
	{
	}

	@Test
	public void test1807657()
	{
		sql = "UPDATE A_Asset a "
				+ "SET (Name, Description)="
				+ "(SELECT SUBSTR((SELECT bp.Name FROM C_BPartner bp WHERE bp.C_BPartner_ID=a.C_BPartner_ID) || ' - ' || p.Name,1,60), p.Description "
				+ "FROM M_Product p "
				+ "WHERE p.M_Product_ID=a.M_Product_ID) "
				+ "WHERE IsActive='Y' "
				+ "AND M_Product_ID=0";

		sqe = "UPDATE A_Asset "
				+ "SET Name=SUBSTR((SELECT bp.Name FROM C_BPartner bp WHERE bp.C_BPartner_ID=A_Asset.C_BPartner_ID) || ' - ' || p.Name,1,60),"
				+ "Description=p.Description "
				+ "FROM M_Product p "
				+ "WHERE p.M_Product_ID=A_Asset.M_Product_ID "
				+ "AND A_Asset.IsActive='Y' AND A_Asset.M_Product_ID=0";

		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void test1751966()
	{
		sql = "UPDATE I_ReportLine i "
				+ "SET (Description, SeqNo, IsSummary, IsPrinted, LineType, CalculationType, AmountType, PostingType)="
				+ " (SELECT Description, SeqNo, IsSummary, IsPrinted, LineType, CalculationType, AmountType, PostingType"
				+ " FROM I_ReportLine ii WHERE i.Name=ii.Name AND i.PA_ReportLineSet_ID=ii.PA_ReportLineSet_ID"
				+ " AND ii.I_ReportLine_ID=(SELECT MIN(I_ReportLine_ID) FROM I_ReportLine iii"
				+ " WHERE i.Name=iii.Name AND i.PA_ReportLineSet_ID=iii.PA_ReportLineSet_ID)) "
				+ "WHERE EXISTS (SELECT *"
				+ " FROM I_ReportLine ii WHERE i.Name=ii.Name AND i.PA_ReportLineSet_ID=ii.PA_ReportLineSet_ID"
				+ " AND ii.I_ReportLine_ID=(SELECT MIN(I_ReportLine_ID) FROM I_ReportLine iii"
				+ " WHERE i.Name=iii.Name AND i.PA_ReportLineSet_ID=iii.PA_ReportLineSet_ID))"
				+ " AND I_IsImported='N' AND AD_Client_ID=0";
		sqe = "UPDATE I_ReportLine SET Description=ii.Description,SeqNo=ii.SeqNo,IsSummary=ii.IsSummary,"
				+ "IsPrinted=ii.IsPrinted,LineType=ii.LineType,CalculationType=ii.CalculationType,AmountType=ii.AmountType,"
				+ "PostingType=ii.PostingType FROM I_ReportLine ii WHERE I_ReportLine.Name=ii.Name "
				+ "AND I_ReportLine.PA_ReportLineSet_ID=ii.PA_ReportLineSet_ID "
				+ "AND ii.I_ReportLine_ID=(SELECT MIN(I_ReportLine_ID) FROM I_ReportLine iii WHERE "
				+ "I_ReportLine.Name=iii.Name AND I_ReportLine.PA_ReportLineSet_ID=iii.PA_ReportLineSet_ID) "
				+ "AND EXISTS (SELECT * FROM I_ReportLine ii WHERE I_ReportLine.Name=ii.Name AND "
				+ "I_ReportLine.PA_ReportLineSet_ID=ii.PA_ReportLineSet_ID AND "
				+ "ii.I_ReportLine_ID=(SELECT MIN(I_ReportLine_ID) FROM I_ReportLine iii "
				+ "WHERE I_ReportLine.Name=iii.Name AND I_ReportLine.PA_ReportLineSet_ID=iii.PA_ReportLineSet_ID)) "
				+ "AND I_ReportLine.I_IsImported='N' AND I_ReportLine.AD_Client_ID=0";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	// [ 1707959 ] Copy from other PrintFormat doesn't work anymore
	@Test
	public void test1707959()
	{
		sql = "UPDATE AD_PrintFormatItem_Trl new " +
				"SET (PrintName, PrintNameSuffix, IsTranslated) = (" +
				"SELECT PrintName, PrintNameSuffix, IsTranslated " +
				"FROM AD_PrintFormatItem_Trl old " +
				"WHERE old.AD_Language=new.AD_Language" +
				" AND AD_PrintFormatItem_ID =0) " +
				"WHERE  AD_PrintFormatItem_ID=1" +
				" AND EXISTS (SELECT AD_PrintFormatItem_ID " +
				" FROM AD_PrintFormatItem_trl old" +
				" WHERE old.AD_Language=new.AD_Language" +
				" AND AD_PrintFormatItem_ID =2)";
		sqe = "UPDATE AD_PrintFormatItem_Trl SET PrintName=\"old\".PrintName,PrintNameSuffix=\"old\".PrintNameSuffix,IsTranslated=\"old\".IsTranslated FROM AD_PrintFormatItem_Trl \"old\" WHERE \"old\".AD_Language=AD_PrintFormatItem_Trl.AD_Language AND \"old\".AD_PrintFormatItem_ID =0 AND AD_PrintFormatItem_Trl.AD_PrintFormatItem_ID=1 AND EXISTS (SELECT AD_PrintFormatItem_ID FROM AD_PrintFormatItem_trl \"old\" WHERE \"old\".AD_Language=AD_PrintFormatItem_Trl.AD_Language AND AD_PrintFormatItem_ID =2)";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	// [ 1707540 ] Dependency problem when modifying AD Columns and Sync.
	// [ 1707611 ] Column synchronization for mandatory columns doesn't work
	@Test
	public void testAlterColumn()
	{
		sql = "ALTER TABLE Test MODIFY T_Integer NUMBER(10) NOT NULL";
		// sqe = "ALTER TABLE Test ALTER COLUMN T_Integer TYPE NUMERIC(10); ALTER TABLE Test ALTER COLUMN T_Integer SET NOT NULL;";
		sqe = "INSERT INTO t_alter_column values('test','T_Integer','NUMERIC(10)','NOT NULL',null)";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		sql = "ALTER TABLE Test MODIFY T_Integer NUMBER(10) NULL";
		// sqe = "ALTER TABLE Test ALTER COLUMN T_Integer TYPE NUMERIC(10); ALTER TABLE Test ALTER COLUMN T_Integer DROP NOT NULL;";
		sqe = "INSERT INTO t_alter_column values('test','T_Integer','NUMERIC(10)','NULL',null)";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		sql = "ALTER TABLE Test MODIFY T_Integer NOT NULL";
		sqe = "INSERT INTO t_alter_column values('test','T_Integer',null,'NOT NULL',null)";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		// Line 407 of ImportProduct.java
		sql = "ALTER TABLE LPI_Publication MODIFY AD_Client_ID NUMERIC(10) DEFAULT NULL";
		// sqe = "ALTER TABLE LPI_Publication ALTER COLUMN AD_Client_ID TYPE NUMERIC(10); ALTER TABLE LPI_Publication ALTER COLUMN AD_Client_ID SET DEFAULT NULL; ";
		sqe = "INSERT INTO t_alter_column values('lpi_publication','AD_Client_ID','NUMERIC(10)',null,'NULL')";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		// add column with default fail in libero 2pack import
		sql = "ALTER TABLE XX ADD A VARCHAR2(10) DEFAULT --";
		sqe = "ALTER TABLE XX ADD COLUMN A VARCHAR(10) DEFAULT '--'";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0).trim());

		// [ adempiere-Bugs-1746266 ]
		sql = "ALTER TABLE someTableName MODIFY someColumnName NVARCHAR2(64)";
		sqe = "INSERT INTO t_alter_column values('sometablename','someColumnName','VARCHAR(64)',null,null)";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0).trim());

		sql = "ALTER TABLE S_Resource MODIFY IsActive CHAR(1) DEFAULT 'Y'";
		sqe = "INSERT INTO t_alter_column values('s_resource','IsActive','CHAR(1)',null,'Y')";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0).trim());

		sql = "ALTER TABLE PP_Order_NodeNext MODIFY PP_Order_NodeNext_ID NULL";
		sqe = "INSERT INTO t_alter_column values('pp_order_nodenext','PP_Order_NodeNext_ID',null,'NULL',null)";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0).trim());
	}

	// Convert.recoverQuotedStrings() error on strings with "<-->" - teo_sarca [ 1705768 ]
	// http://sourceforge.net/tracker/index.php?func=detail&aid=1705768&group_id=176962&atid=879332
	@Test
	public void test1705768()
	{
		sql = "SELECT 'Partner <--> Organization', 's2\\$', 's3' FROM DUAL";
		sqe = "SELECT 'Partner <--> Organization', E's2\\\\$', 's3'";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void test1704261()
	{
		// [ 1704261 ] can not import currency rate
		sql = "UPDATE I_Conversion_Rate i SET MultiplyRate = 1 / DivideRate WHERE (MultiplyRate IS NULL OR MultiplyRate = 0) AND DivideRate IS NOT NULL AND DivideRate<>0 AND I_IsImported<>'Y' AND AD_Client_ID=1000000";
		sqe = "UPDATE I_Conversion_Rate SET MultiplyRate = 1 / DivideRate WHERE (MultiplyRate IS NULL OR MultiplyRate = 0) AND DivideRate IS NOT NULL AND DivideRate<>0 AND I_IsImported<>'Y' AND AD_Client_ID=1000000";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void testAlterTable()
	{
		// [ 1668720 ] Convert failing in alter table
		sql = "ALTER TABLE GT_TaxBase ADD CONSTRAINT GT_TaxBase_Key PRIMARY KEY (GT_TaxBase_ID)";
		sqe = "ALTER TABLE GT_TaxBase ADD CONSTRAINT GT_TaxBase_Key PRIMARY KEY (GT_TaxBase_ID)";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		// [ 1668720 ] Convert failing in alter table
		sql = "ALTER TABLE GT_TaxBase ADD GT_TaxBase_ID NUMBER(10) NOT NULL";
		sqe = "ALTER TABLE GT_TaxBase ADD COLUMN GT_TaxBase_ID NUMERIC(10) NOT NULL";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void test1662983()
	{
		// [ 1662983 ] Convert cutting backslash from string
		sql = "SELECT 'C:\\Documentos\\Test' FROM DUAL";
		sqe = "SELECT E'C:\\\\Documentos\\\\Test'";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		sql = "SELECT 'C:Document' FROM DUAL";
		sqe = "SELECT 'C:Document'";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void testMultiColumnAssignment()
	{
		// Line 407 of ImportProduct.java
		sql = "UPDATE M_PRODUCT SET (Value,Name,Description,DocumentNote,Help,UPC,SKU,C_UOM_ID,M_Product_Category_ID,Classification,ProductType,Volume,Weight,ShelfWidth,ShelfHeight,ShelfDepth,UnitsPerPallet,Discontinued,DiscontinuedBy,Updated,UpdatedBy)= (SELECT Value,Name,Description,DocumentNote,Help,UPC,SKU,C_UOM_ID,M_Product_Category_ID,Classification,ProductType,Volume,Weight,ShelfWidth,ShelfHeight,ShelfDepth,UnitsPerPallet,Discontinued,DiscontinuedBy,SysDate,UpdatedBy FROM I_Product WHERE I_Product_ID=?) WHERE M_Product_ID=?";
		sqe = "UPDATE M_PRODUCT SET Value=I_Product.Value,Name=I_Product.Name,Description=I_Product.Description,DocumentNote=I_Product.DocumentNote,Help=I_Product.Help,UPC=I_Product.UPC,SKU=I_Product.SKU,C_UOM_ID=I_Product.C_UOM_ID,M_Product_Category_ID=I_Product.M_Product_Category_ID,Classification=I_Product.Classification,ProductType=I_Product.ProductType,Volume=I_Product.Volume,Weight=I_Product.Weight,ShelfWidth=I_Product.ShelfWidth,ShelfHeight=I_Product.ShelfHeight,ShelfDepth=I_Product.ShelfDepth,UnitsPerPallet=I_Product.UnitsPerPallet,Discontinued=I_Product.Discontinued,DiscontinuedBy=I_Product.DiscontinuedBy,Updated=CURRENT_TIMESTAMP,UpdatedBy=I_Product.UpdatedBy FROM I_Product WHERE I_Product.I_Product_ID=? AND M_PRODUCT.M_Product_ID=?";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		// FinReport, test inner join in multi column update
		sql = "UPDATE T_Report r SET (Name,Description)=("
				+ "SELECT e.Name, fa.Description "
				+ "FROM Fact_Acct fa"
				+ " INNER JOIN AD_Table t ON (fa.AD_Table_ID=t.AD_Table_ID)"
				+ " INNER JOIN AD_Element e ON (t.TableName||'_ID'=e.ColumnName) "
				+ "WHERE r.Fact_Acct_ID=fa.Fact_Acct_ID) "
				+ "WHERE Fact_Acct_ID <> 0 AND AD_PInstance_ID=0";
		sqe = "UPDATE T_Report SET Name=e.Name,Description=fa.Description FROM Fact_Acct fa INNER JOIN AD_Table t ON (fa.AD_Table_ID=t.AD_Table_ID) INNER JOIN AD_Element e ON (t.TableName||'_ID'=e.ColumnName) WHERE T_Report.Fact_Acct_ID=fa.Fact_Acct_ID AND T_Report.Fact_Acct_ID <> 0 AND T_Report.AD_PInstance_ID=0";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		// https://sourceforge.net/forum/message.php?msg_id=4083672
		sql = " 	UPDATE AD_COLUMN c"
				+ " 		SET	(ColumnName, Name, Description, Help) ="
				+ " 	           (SELECT ColumnName, Name, Description, Help"
				+ " 	            FROM AD_ELEMENT e WHERE c.AD_Element_ID=e.AD_Element_ID),"
				+ " 			Updated = SYSDATE"
				+ " 	WHERE EXISTS (SELECT 1 FROM AD_ELEMENT e "
				+ " 				WHERE c.AD_Element_ID=e.AD_Element_ID"
				+ " 				  AND (c.ColumnName <> e.ColumnName OR c.Name <> e.Name "
				+ " 					OR NVL(c.Description,' ') <> NVL(e.Description,' ') OR NVL(c.Help,' ') <> NVL(e.Help,' ')))";
		sqe = "UPDATE AD_COLUMN SET ColumnName=e.ColumnName,Name=e.Name,Description=e.Description,Help=e.Help, Updated = CURRENT_TIMESTAMP FROM AD_ELEMENT e WHERE AD_COLUMN.AD_Element_ID=e.AD_Element_ID AND EXISTS (SELECT 1 FROM AD_ELEMENT e WHERE AD_COLUMN.AD_Element_ID=e.AD_Element_ID AND (AD_COLUMN.ColumnName <> e.ColumnName OR AD_COLUMN.Name <> e.Name OR COALESCE(AD_COLUMN.Description,' ') <> COALESCE(e.Description,' ') OR COALESCE(AD_COLUMN.Help,' ') <> COALESCE(e.Help,' ')))";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		sql = "UPDATE AD_WF_NODE n"
				+ " SET (Name, Description, Help) = (SELECT f.Name, f.Description, f.Help"
				+ " 		FROM AD_PROCESS f"
				+ " 		WHERE f.AD_Process_ID=n.AD_Process_ID)"
				+ " WHERE n.IsCentrallyMaintained = 'Y'"
				+ " AND EXISTS  (SELECT 1 FROM AD_PROCESS f"
				+ " 		WHERE f.AD_Process_ID=n.AD_Process_ID"
				+ " 		  AND (f.Name <> n.Name OR NVL(f.Description,' ') <> NVL(n.Description,' ') OR NVL(f.Help,' ') <> NVL(n.Help,' ')))";
		sqe = "UPDATE AD_WF_NODE SET Name=f.Name,Description=f.Description,Help=f.Help FROM AD_PROCESS f WHERE f.AD_Process_ID=AD_WF_NODE.AD_Process_ID AND AD_WF_NODE.IsCentrallyMaintained = 'Y' AND EXISTS (SELECT 1 FROM AD_PROCESS f WHERE f.AD_Process_ID=AD_WF_NODE.AD_Process_ID AND (f.Name <> AD_WF_NODE.Name OR COALESCE(f.Description,' ') <> COALESCE(AD_WF_NODE.Description,' ') OR COALESCE(f.Help,' ') <> COALESCE(AD_WF_NODE.Help,' ')))";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

	}

	@Test
	public void testReservedWordInQuote()
	{
		// test conversion of reserved words inside quotes
		sql = "UPDATE AD_Message_Trl SET MsgText='{} Linea(s) {1,number,#,##0.00}  - Total: {2,number,#,##0.00}',MsgTip=NULL,Updated=TO_DATE('2007-01-12 21:44:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=828 AND AD_Language='es_MX'";
		sqe = "UPDATE AD_Message_Trl SET MsgText='{} Linea(s) {1,number,#,##0.00}  - Total: {2,number,#,##0.00}',MsgTip=NULL,Updated=TO_TIMESTAMP('2007-01-12 21:44:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=828 AND AD_Language='es_MX'";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void test1580231()
	{
		// financial report, bug [ 1580231 ]
		sql = "UPDATE t_report"
				+ " SET (NAME, description) = (SELECT VALUE, NAME "
				+ " FROM c_elementvalue"
				+ " WHERE c_elementvalue_id = t_report.record_id) "
				+ " WHERE record_id <> 0 " + " AND ad_pinstance_id = 1000024 "
				+ " AND pa_reportline_id = 101 " + " AND fact_acct_id = 0 ";
		sqe = "UPDATE t_report SET NAME=c_elementvalue.VALUE,description=c_elementvalue.NAME FROM c_elementvalue WHERE c_elementvalue.c_elementvalue_id = t_report.record_id AND t_report.record_id <> 0 AND t_report.ad_pinstance_id = 1000024 AND t_report.pa_reportline_id = 101 AND t_report.fact_acct_id = 0";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void testAliasInUpdate()
	{
		// test alias and column list update
		sql = "UPDATE I_Order o SET (C_BPartner_ID,AD_User_ID)=(SELECT C_BPartner_ID,AD_User_ID FROM AD_User u WHERE o.ContactName=u.Name AND o.AD_Client_ID=u.AD_Client_ID AND u.C_BPartner_ID IS NOT NULL) WHERE C_BPartner_ID IS NULL AND ContactName IS NOT NULL AND EXISTS (SELECT Name FROM AD_User u WHERE o.ContactName=u.Name AND o.AD_Client_ID=u.AD_Client_ID AND u.C_BPartner_ID IS NOT NULL GROUP BY Name HAVING COUNT(*)=1) AND I_IsImported<>'Y' AND AD_Client_ID=11";
		sqe = "UPDATE I_Order SET C_BPartner_ID=u.C_BPartner_ID,AD_User_ID=u.AD_User_ID FROM AD_User u WHERE I_Order.ContactName=u.Name AND I_Order.AD_Client_ID=u.AD_Client_ID AND u.C_BPartner_ID IS NOT NULL AND I_Order.C_BPartner_ID IS NULL AND I_Order.ContactName IS NOT NULL AND EXISTS (SELECT Name FROM AD_User u WHERE I_Order.ContactName=u.Name AND I_Order.AD_Client_ID=u.AD_Client_ID AND u.C_BPartner_ID IS NOT NULL GROUP BY Name HAVING COUNT(*)=1) AND I_Order.I_IsImported<>'Y' AND I_Order.AD_Client_ID=11";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void test1580226()
	{
		// from bug [ 1580226 ] - test alias and trunc
		sql = "INSERT INTO Fact_Acct_Balance ab "
				+ "(AD_Client_ID, AD_Org_ID, C_AcctSchema_ID, DateAcct,"
				+ " Account_ID, PostingType, M_Product_ID, C_BPartner_ID,"
				+ "	C_Project_ID, AD_OrgTrx_ID,	C_SalesRegion_ID,C_Activity_ID,"
				+ " C_Campaign_ID, C_LocTo_ID, C_LocFrom_ID, User1_ID, User2_ID, GL_Budget_ID,"
				+ " AmtAcctDr, AmtAcctCr, Qty) "
				+ "SELECT AD_Client_ID, AD_Org_ID, C_AcctSchema_ID, TRUNC(DateAcct),"
				+ " Account_ID, PostingType, M_Product_ID, C_BPartner_ID,"
				+ " C_Project_ID, AD_OrgTrx_ID, C_SalesRegion_ID,C_Activity_ID,"
				+ " C_Campaign_ID, C_LocTo_ID, C_LocFrom_ID, User1_ID, User2_ID, GL_Budget_ID,"
				+ " COALESCE(SUM(AmtAcctDr),0), COALESCE(SUM(AmtAcctCr),0), COALESCE(SUM(Qty),0) "
				+ "FROM Fact_Acct a "
				+ "WHERE C_AcctSchema_ID=0"
				+ " GROUP BY AD_Client_ID,AD_Org_ID, C_AcctSchema_ID, TRUNC(DateAcct),"
				+ " Account_ID, PostingType, M_Product_ID, C_BPartner_ID,"
				+ " C_Project_ID, AD_OrgTrx_ID, C_SalesRegion_ID, C_Activity_ID,"
				+ " C_Campaign_ID, C_LocTo_ID, C_LocFrom_ID, User1_ID, User2_ID, GL_Budget_ID";
		sqe = "INSERT INTO Fact_Acct_Balance "
				+ "(AD_Client_ID, AD_Org_ID, C_AcctSchema_ID, DateAcct,"
				+ " Account_ID, PostingType, M_Product_ID, C_BPartner_ID,"
				+ " C_Project_ID, AD_OrgTrx_ID, C_SalesRegion_ID,C_Activity_ID,"
				+ " C_Campaign_ID, C_LocTo_ID, C_LocFrom_ID, User1_ID, User2_ID, GL_Budget_ID,"
				+ " AmtAcctDr, AmtAcctCr, Qty) "
				+ "SELECT AD_Client_ID, AD_Org_ID, C_AcctSchema_ID, TRUNC(DateAcct),"
				+ " Account_ID, PostingType, M_Product_ID, C_BPartner_ID,"
				+ " C_Project_ID, AD_OrgTrx_ID, C_SalesRegion_ID,C_Activity_ID,"
				+ " C_Campaign_ID, C_LocTo_ID, C_LocFrom_ID, User1_ID, User2_ID, GL_Budget_ID,"
				+ " COALESCE(SUM(AmtAcctDr),0), COALESCE(SUM(AmtAcctCr),0), COALESCE(SUM(Qty),0) "
				+ "FROM Fact_Acct a "
				+ "WHERE C_AcctSchema_ID=0"
				+ " GROUP BY AD_Client_ID,AD_Org_ID, C_AcctSchema_ID, TRUNC(DateAcct),"
				+ " Account_ID, PostingType, M_Product_ID, C_BPartner_ID,"
				+ " C_Project_ID, AD_OrgTrx_ID, C_SalesRegion_ID, C_Activity_ID,"
				+ " C_Campaign_ID, C_LocTo_ID, C_LocFrom_ID, User1_ID, User2_ID, GL_Budget_ID";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void testTrunc()
	{
		// From bug [ 1576358 ] and [ 1577055 ]
		sql = "SELECT TRUNC(TO_DATE('2006-10-13','YYYY-MM-DD'),'Q') FROM DUAL";
		sqe = "SELECT TRUNC(TO_TIMESTAMP('2006-10-13','YYYY-MM-DD'),'Q')";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void testSubQuery()
	{
		// MLanguage.addTable
		sql = "INSERT INTO " + "AD_Column_Trl"
				+ "(AD_Language,IsTranslated, AD_Client_ID,AD_Org_ID, "
				+ "Createdby,UpdatedBy, "
				+ "AD_Column_ID,Name) "
				+ "SELECT '" + "es_MX" + "','N', AD_Client_ID,AD_Org_ID, "
				+ 100 + "," + 100 + ", "
				+ "AD_Column_ID,Name"
				+ " FROM " + "AD_Column"
				+ " WHERE " + "AD_Column_ID" + " NOT IN (SELECT " + "AD_Column_ID"
				+ " FROM " + "AD_Column_Trl"
				+ " WHERE AD_Language='" + "es_MX" + "')";
		sqe = "INSERT INTO AD_Column_Trl(AD_Language,IsTranslated, AD_Client_ID,AD_Org_ID, Createdby,UpdatedBy, AD_Column_ID,Name) SELECT 'es_MX','N', AD_Client_ID,AD_Org_ID, 100,100, AD_Column_ID,Name FROM AD_Column WHERE AD_Column_ID NOT IN (SELECT AD_Column_ID FROM AD_Column_Trl WHERE AD_Language='es_MX')";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void test1622302()
	{
		// MInOutLineMa bug [ 1622302 ]
		sql = "DELETE FROM M_InOutLineMA ma WHERE EXISTS "
				+ "(SELECT * FROM M_InOutLine l WHERE l.M_InOutLine_ID=ma.M_InOutLine_ID"
				+ " AND M_InOut_ID=0)";
		sqe = "DELETE FROM M_InOutLineMA WHERE EXISTS (SELECT * FROM M_InOutLine l WHERE l.M_InOutLine_ID=M_InOutLineMA.M_InOutLine_ID AND M_InOut_ID=0)";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	public void test1638046()
	{
		// bug [ 1638046 ]
		sql = "UPDATE GL_JournalBatch jb"
				+ " SET (TotalDr, TotalCr) = (SELECT COALESCE(SUM(TotalDr),0), COALESCE(SUM(TotalCr),0)"
				+ " FROM GL_Journal j WHERE j.IsActive='Y' AND jb.GL_JournalBatch_ID=j.GL_JournalBatch_ID) "
				+ "WHERE GL_JournalBatch_ID=0";
		convertResult = convert.convert(sql);
		sqe = "UPDATE GL_JournalBatch SET TotalDr="
				+ "( SELECT COALESCE(SUM(TotalDr),0) "
				+ "FROM GL_Journal j WHERE j.IsActive='Y' AND "
				+ "GL_JournalBatch.GL_JournalBatch_ID=j.GL_JournalBatch_ID ) ,"
				+ "TotalCr=( SELECT COALESCE(SUM(TotalCr),0) FROM GL_Journal j "
				+ "WHERE j.IsActive='Y' AND GL_JournalBatch.GL_JournalBatch_ID=j.GL_JournalBatch_ID ) "
				+ " WHERE GL_JournalBatch_ID=0";
		assertEquals(sqe, convertResult.get(0));
	}

	// [ 1727193 ] Convert failed with decode in quoted string
	@Test
	public void test1727193()
	{
		sql = "UPDATE a set a.ten_decode = 'b'";
		sqe = "UPDATE a set a.ten_decode = 'b'";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		sql = "UPDATE a set a.b = 'ten_decode'";
		sqe = "UPDATE a set a.b = 'ten_decode'";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void testDecode()
	{
		sql = "SELECT supplier_name, decode(supplier_id, 10000, 'IBM', 10001, 'Microsoft', 10002, 'Hewlett Packard', 'Gateway') FROM suppliers";
		sqe = "SELECT supplier_name, CASE WHEN supplier_id=10000 THEN 'IBM' WHEN supplier_id=10001 THEN 'Microsoft' WHEN supplier_id=10002 THEN 'Hewlett Packard' ELSE 'Gateway' END FROM suppliers";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		// doc_matchinv update average cost, bug [ 1742835 ]
		sql = "UPDATE SomeOldCostingTable "
				+ "SET CostAverage = CostAverageCumAmt/DECODE(CostAverageCumQty, 0,1, CostAverageCumQty) "
				+ "WHERE C_AcctSchema_ID=0"
				+ " AND M_Product_ID=0";
		sqe = "UPDATE SomeOldCostingTable "
				+ "SET CostAverage = CostAverageCumAmt/CASE WHEN CostAverageCumQty=0 THEN 1 ELSE CostAverageCumQty END "
				+ "WHERE C_AcctSchema_ID=0"
				+ " AND M_Product_ID=0";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	@Test
	public void test2371805_GetDate()
	{
		sql = "SELECT getdate() FROM DUAL";
		sqe = "SELECT CURRENT_TIMESTAMP";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		sql = "SELECT SYSDATE FROM DUAL";
		sqe = "SELECT CURRENT_TIMESTAMP";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	/**
	 * Test BF [ 1824256 ] Convert sql casts
	 */
	@Test
	public void testCasts()
	{
		String sql_begin = "SELECT ";
		String[][] sql_tests = new String[][] {
				// Oracle vs PostgreSQL
				{ "cast('N' as char)", "cast('N' as char)" },
				{ "cast('this is a string' as nvarchar2(40))", "cast('this is a string' as VARCHAR)" },
				{ "cast('this is a string as a ''string''' as nvarchar2(40))", "cast('this is a string as a ''string''' as VARCHAR)" },
				{ "cast(tbl.IsView as char)", "cast(tbl.IsView as char)" },
				{ "cast(trunc(tbl.Updated,'MONTH') as date)", "cast(trunc(tbl.Updated,'MONTH') as TIMESTAMP WITHOUT TIME ZONE)" },
				{ "cast(NULL as nvarchar2(255))", "cast(NULL as VARCHAR)" },
				{ "cast(NULL as number(10))", "cast(NULL as NUMERIC)" },
		};
		String sql_end = " FROM AD_Table tbl";
		StringBuffer sql = new StringBuffer(sql_begin);
		StringBuffer sqle = new StringBuffer(sql_begin);
		for (int i = 0; i < sql_tests.length; i++)
		{
			if (i > 0)
			{
				sql.append(",");
				sqle.append(",");
			}
			sql.append(sql_tests[i][0]);
			sqle.append(sql_tests[i][1]);
		}
		sql.append(sql_end);
		sqle.append(sql_end);
		//
		List<String> r = convert.convert(sql.toString());
		assertEquals(sqle.toString(), r.get(0));
	}

	/**
	 * Test BF [ 2521586 ] Postgres conversion error
	 */
	@Test
	public void test2521586()
	{
		sql = "INSERT INTO M_Forecast (M_Forecast_ID) VALUES (1000000)";
		sqe = "INSERT INTO M_Forecast (M_Forecast_ID) VALUES (1000000)";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	/**
	 * Test BF [3137355 ] PG query not valid when contains quotes and backslashes. https://sourceforge.net/tracker/?func=detail&aid=3137355&group_id=176962&atid=879332
	 */
	@Test
	public void test3137355()
	{
		sql = "INSERT INTO MyTable (a, b, c, d, xml) VALUES ('val1', 'val2', 'this ''is'' a string with ''quotes'' and backslashes ''\\''', 'val4')";
		sqe = "INSERT INTO MyTable (a, b, c, d, xml) VALUES ('val1', 'val2', E'this ''is'' a string with ''quotes'' and backslashes ''\\\\''', 'val4')";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));

		sql = "SELECT AD_Tab.AD_Tab_ID,NULL,COALESCE(AD_Tab.Name,'-1') ||'_'|| COALESCE((SELECT COALESCE(AD_Window.Name,'') FROM AD_Window WHERE AD_Tab.AD_Window_ID=AD_Window.AD_Window_ID),'-1'),AD_Tab.IsActive"
				+ " FROM AD_Tab WHERE AD_Tab.AD_Tab_ID=?";
		sqe = sql;
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/03306:_ADempiere_time_values_don%27t_know_their_timezone_%282012091910000041%29
	 */
	@Test
	public void testDDL_With_Timestamp_With_TimeZone()
	{
		sql = "ALTER TABLE Test MODIFY Date1 TIMESTAMP WITH TIME ZONE DEFAULT NULL";
		sqe = "INSERT INTO t_alter_column values('test','Date1','TIMESTAMP WITH TIME ZONE',null,'NULL')";
		convertResult = convert.convert(sql);
		assertEquals(sqe.trim(), convertResult.get(0).trim());

		sql = "ALTER TABLE Test MODIFY Date1 TIMESTAMP WITH TIME ZONE";
		sqe = "INSERT INTO t_alter_column values('test','Date1','TIMESTAMP WITH TIME ZONE',null,null)";
		convertResult = convert.convert(sql);
		assertEquals(sqe.trim(), convertResult.get(0).trim());

		sql = "ALTER TABLE Test ADD Date1 TIMESTAMP WITH TIME ZONE DEFAULT NULL";
		sqe = "ALTER TABLE Test ADD COLUMN Date1 TIMESTAMP WITH TIME ZONE DEFAULT NULL";
		convertResult = convert.convert(sql);
		assertEquals(sqe.trim(), convertResult.get(0).trim());

	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/03306:_ADempiere_time_values_don%27t_know_their_timezone_%282012091910000041%29
	 */
	public void testDDL_With_Timestamp_Without_TimeZone()
	{
		sql = "ALTER TABLE Test MODIFY Date1 DATE DEFAULT NULL";
		sqe = "INSERT INTO t_alter_column values('test','Date1','TIMESTAMP WITHOUT TIME ZONE',null,'NULL')";
		convertResult = convert.convert(sql);
		assertEquals(sqe.trim(), convertResult.get(0).trim());

		sql = "ALTER TABLE Test ADD Date1 DATE DEFAULT NULL";
		sqe = "ALTER TABLE Test ADD COLUMN Date1 TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL";
		convertResult = convert.convert(sql);
		assertEquals(sqe.trim(), convertResult.get(0).trim());
	}

	@Test
	public void testJoinWithPlus()
	{
		sql = "SELECT d.HasCharges,'N',d.IsDocNoControlled,s.CurrentNext, d.DocBaseType, s.StartNewYear, s.DateColumn, s.AD_Sequence_ID FROM C_DocType d, AD_Sequence s WHERE C_DocType_ID=? AND d.DocNoSequence_ID=s.AD_Sequence_ID(+)";
		sqe = "SELECT d.HasCharges,'N',d.IsDocNoControlled,s.CurrentNext, d.DocBaseType, s.StartNewYear, s.DateColumn, s.AD_Sequence_ID FROM C_DocType d LEFT OUTER JOIN AD_Sequence s ON (d.DocNoSequence_ID=s.AD_Sequence_ID) WHERE C_DocType_ID=?";
		convertResult = convert.convert(sql);
		assertEquals(sqe, convertResult.get(0));
	}

}
