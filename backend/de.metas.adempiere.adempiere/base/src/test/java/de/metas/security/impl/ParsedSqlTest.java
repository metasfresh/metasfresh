package de.metas.security.impl;

import de.metas.security.impl.ParsedSql.SqlSelect;
import de.metas.security.impl.ParsedSql.TableNameAndAlias;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ParsedSqlTest
{
	@Test
	public void oneTableWithoutAlias()
	{
		final String sql = "SELECT AD_Table_ID, TableName FROM AD_Table WHERE IsActive='Y'";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql(sql) // the whole SQL
						.tableNameAndAlias(TableNameAndAlias.ofTableName("AD_Table"))
						.build())
				.mainSqlIndex(0)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void oneTableWithAlias()
	{
		final String sql = "SELECT t.AD_Table_ID, t.TableName FROM AD_Table alias WHERE t.IsActive='Y'";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql(sql) // the whole SQL
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Table", "alias"))
						.build())
				.mainSqlIndex(0)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void oneTableWithAliasUsingKeywordAS()
	{
		final String sql = "SELECT t.AD_Table_ID, t.TableName FROM AD_Table AS alias WHERE t.IsActive='Y'";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql(sql) // the whole SQL
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Table", "alias"))
						.build())
				.mainSqlIndex(0)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void twoTablesWithAlias()
	{
		final String sql = "SELECT t.AD_Table_ID, t.TableName, c.AD_Column_ID, c.ColumnName FROM AD_Table t, AD_Column c WHERE t.AD_Table_ID=c.AD_Table_ID AND t.IsActive='Y'";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql(sql) // the whole SQL
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Table", "t"))
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Column", "c"))
						.build())
				.mainSqlIndex(0)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void twoTablesWithAliasUsingKeywordAS()
	{
		final String sql = "SELECT t.AD_Table_ID, t.TableName, c.AD_Column_ID, c.ColumnName FROM AD_Table as t, AD_Column AS c WHERE t.AD_Table_ID=c.AD_Table_ID AND t.IsActive='Y'";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql(sql) // the whole SQL
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Table", "t"))
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Column", "c"))
						.build())
				.mainSqlIndex(0)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void innerJoin()
	{
		final String sql = "SELECT t.AD_Table_ID, t.TableName, c.AD_Column_ID, c.ColumnName "
				+ "FROM AD_Table t INNER JOIN AD_Column c ON (t.AD_Table_ID=c.AD_Table_ID) WHERE t.IsActive='Y'";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql(sql) // the whole SQL
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Table", "t"))
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Column", "c"))
						.build())
				.mainSqlIndex(0)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void outerJoin()
	{
		final String sql = "SELECT t.AD_Table_ID, t.TableName, c.AD_Column_ID, c.ColumnName "
				+ "FROM AD_Table t LEFT OUTER JOIN AD_Column c ON (t.AD_Table_ID=c.AD_Table_ID) WHERE t.IsActive='Y'";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql(sql) // the whole SQL
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Table", "t"))
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Column", "c"))
						.build())
				.mainSqlIndex(0)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void existsSubquery()
	{
		final String sql = "SELECT AD_Table.AD_Table_ID, AD_Table.TableName "
				+ "FROM AD_Table "
				+ "WHERE EXISTS (SELECT * FROM AD_Column c WHERE AD_Table.AD_Table_ID=c.AD_Table_ID)";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql("(SELECT * FROM AD_Column c WHERE AD_Table.AD_Table_ID=c.AD_Table_ID)")
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Column", "c"))
						.build())
				.sqlSelect(SqlSelect.builder()
						.sql("SELECT AD_Table.AD_Table_ID, AD_Table.TableName "
								+ "FROM AD_Table "
								+ "WHERE EXISTS (##)")
						.tableNameAndAlias(TableNameAndAlias.ofTableName("AD_Table"))
						.build())
				.mainSqlIndex(1)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void existsSubqueryUsingAliasForMainTable()
	{
		final String sql = "SELECT t.AD_Table_ID, t.TableName "
				+ "FROM AD_Table t "
				+ "WHERE EXISTS (SELECT * FROM AD_Column c WHERE t.AD_Table_ID=c.AD_Table_ID)";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql("(SELECT * FROM AD_Column c WHERE t.AD_Table_ID=c.AD_Table_ID)")
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Column", "c"))
						.build())
				.sqlSelect(SqlSelect.builder()
						.sql("SELECT t.AD_Table_ID, t.TableName "
								+ "FROM AD_Table t "
								+ "WHERE EXISTS (##)")
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Table", "t"))
						.build())
				.mainSqlIndex(1)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void subqueryInSELECT()
	{
		final String sql = "SELECT t.AD_Table_ID, t.TableName,"
				+ "(SELECT COUNT(c.ColumnName) FROM AD_Column c WHERE t.AD_Table_ID=c.AD_Table_ID) "
				+ "FROM AD_Table t WHERE t.IsActive='Y'";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql("(SELECT COUNT(c.ColumnName) FROM AD_Column c WHERE t.AD_Table_ID=c.AD_Table_ID)")
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Column", "c"))
						.build())
				.sqlSelect(SqlSelect.builder()
						.sql("SELECT t.AD_Table_ID, t.TableName,"
								+ "(##) "
								+ "FROM AD_Table t WHERE t.IsActive='Y'")
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Table", "t"))
						.build())
				.mainSqlIndex(1)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void subqueryInFROM()
	{
		final String sql = "SELECT t.AD_Table_ID, t.TableName, cc.CCount "
				+ "FROM AD_Table t,"
				+ "(SELECT COUNT(ColumnName) AS CCount FROM AD_Column) cc "
				+ "WHERE t.IsActive='Y'";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql("(SELECT COUNT(ColumnName) AS CCount FROM AD_Column)")
						.tableNameAndAlias(TableNameAndAlias.ofTableName("AD_Column"))
						.build())
				.sqlSelect(SqlSelect.builder()
						.sql("SELECT t.AD_Table_ID, t.TableName, cc.CCount "
								+ "FROM AD_Table t,"
								+ "(##) cc "
								+ "WHERE t.IsActive='Y'")
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("AD_Table", "t"))
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("(##)", "cc"))
						.build())
				.mainSqlIndex(1)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testProductInstanceAttributeQuery()
	{
		final String sql = "SELECT p.M_Product_ID, p.Discontinued, p.Value, p.Name, BOM_Qty_Available(p.M_Product_ID,?) AS QtyAvailable, bomQtyList(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceList, bomQtyStd(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceStd, BOM_Qty_OnHand(p.M_Product_ID,?) AS QtyOnHand, BOM_Qty_Reserved(p.M_Product_ID,?) AS QtyReserved, BOM_Qty_Ordered(p.M_Product_ID,?) AS QtyOrdered, bomQtyStd(p.M_Product_ID, pr.M_PriceList_Version_ID)-bomQtyLimit(p.M_Product_ID, pr.M_PriceList_Version_ID) AS Margin, bomQtyLimit(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceLimit, pa.IsInstanceAttribute FROM M_Product p INNER JOIN M_ProductPrice pr ON (p.M_Product_ID=pr.M_Product_ID) LEFT OUTER JOIN M_AttributeSet pa ON (p.M_AttributeSet_ID=pa.M_AttributeSet_ID) WHERE p.IsSummary='N' AND p.IsActive='Y' AND pr.IsActive='Y' AND pr.M_PriceList_Version_ID=? AND EXISTS (SELECT * FROM M_Storage s INNER JOIN M_AttributeSetInstance asi ON (s.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID) WHERE s.M_Product_ID=p.M_Product_ID AND asi.SerNo LIKE '33' AND asi.Lot LIKE '33' AND asi.M_Lot_ID=101 AND TRUNC(asi.GuaranteeDate)<TO_DATE('2003-10-16','YYYY-MM-DD') AND asi.M_AttributeSetInstance_ID IN (SELECT M_AttributeSetInstance_ID FROM M_AttributeInstance WHERE (M_Attribute_ID=103 AND Value LIKE '33') AND (M_Attribute_ID=102 AND M_AttributeValue_ID=106))) AND p.M_AttributeSetInstance_ID IN (SELECT M_AttributeSetInstance_ID FROM M_AttributeInstance WHERE (M_Attribute_ID=101 AND M_AttributeValue_ID=105) AND (M_Attribute_ID=100 AND M_AttributeValue_ID=102)) AND p.AD_Client_ID IN(0,11) AND p.AD_Org_ID IN(0,11,12) ORDER BY QtyAvailable DESC, Margin DESC";
		final ParsedSql actual = ParsedSql.parse(sql);
		assertThat(toString(actual))
				.isEqualTo("AccessSqlParser[M_AttributeInstance|M_Storage=s,M_AttributeSetInstance=asi|M_AttributeInstance|M_Product=p,M_ProductPrice=pr,M_AttributeSet=pa|3]");
	}

	/**
	 * Run the Product Attribute Query
	 */
	@Test
	public void testProductAttributeQuery()
	{
		final String sql = "SELECT p.M_Product_ID, p.Discontinued, p.Value, p.Name, BOM_Qty_Available(p.M_Product_ID,?) AS QtyAvailable, bomQtyList(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceList, bomQtyStd(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceStd, BOM_Qty_OnHand(p.M_Product_ID,?) AS QtyOnHand, BOM_Qty_Reserved(p.M_Product_ID,?) AS QtyReserved, BOM_Qty_Ordered(p.M_Product_ID,?) AS QtyOrdered, bomQtyStd(p.M_Product_ID, pr.M_PriceList_Version_ID)-bomQtyLimit(p.M_Product_ID, pr.M_PriceList_Version_ID) AS Margin, bomQtyLimit(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceLimit, pa.IsInstanceAttribute FROM M_Product p INNER JOIN M_ProductPrice pr ON (p.M_Product_ID=pr.M_Product_ID) LEFT OUTER JOIN M_AttributeSet pa ON (p.M_AttributeSet_ID=pa.M_AttributeSet_ID) WHERE p.IsSummary='N' AND p.IsActive='Y' AND pr.IsActive='Y' AND pr.M_PriceList_Version_ID=? AND p.M_AttributeSetInstance_ID IN (SELECT M_AttributeSetInstance_ID FROM M_AttributeInstance WHERE (M_Attribute_ID=100 AND M_AttributeValue_ID=101)) ORDER BY QtyAvailable DESC, Margin DESC";
		final ParsedSql actual = ParsedSql.parse(sql);
		assertThat(toString(actual))
				.isEqualTo("AccessSqlParser[M_AttributeInstance|M_Product=p,M_ProductPrice=pr,M_AttributeSet=pa|1]");
	}

	private static String toString(final ParsedSql sql)
	{
		return "AccessSqlParser["
				+ sql.getSqlSelects()
				.stream()
				.map(ParsedSqlTest::toString)
				.collect(Collectors.joining("|"))
				+ "|" + sql.getMainSqlIndex()
				+ "]";
	}

	private static String toString(final ParsedSql.SqlSelect sqlSelect)
	{
		return sqlSelect.getTableNameAndAliases()
				.stream()
				.map(ParsedSqlTest::toString)
				.collect(Collectors.joining(","));
	}

	private static String toString(final ParsedSql.TableNameAndAlias tableAndAlias)
	{
		final String tableName = tableAndAlias.getTableName();
		final String alias = tableAndAlias.getAlias();
		if (alias.isEmpty())
		{
			return tableName;
		}
		else
		{
			return tableName + "=" + alias;
		}
	}

	@Test
	public void tableNameParsing_1()
	{
		final String sql = "SELECT SUM(il.QtyInvoiced)\n"
				+ "FROM RV_C_Invoice\n"
				+ "C_Invoice\n"
				+ "INNER JOIN RV_C_InvoiceLine il ON (C_Invoice.C_Invoice_ID=il.C_Invoice_ID) WHERE\n"
				+ "C_Invoice.IsSOTrx='Y' AND C_Invoice.Processed='Y' AND C_Invoice.IsPaid='Y'";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql(ParsedSql.normalizeOriginalSql(sql))
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("RV_C_Invoice", "C_Invoice"))
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("RV_C_InvoiceLine", "il"))
						.build())
				.mainSqlIndex(0)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	/**
	 * <li>teo_sarca - [ 1964496 ] AccessSqlParser is not parsing well JOIN CLAUSE
	 */
	@Test
	public void tableNameParsing_2()
	{
		final String sql = "SELECT C_Invoice.*  FROM C_Invoice\n"
				+ "INNER JOIN C_BPartner bp ON (bp.C_BPartner_ID=C_Invoice.C_BPartner_ID) WHERE 1=0";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql(sql)
						.tableNameAndAlias(TableNameAndAlias.ofTableName("C_Invoice"))
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("C_BPartner", "bp"))
						.build())
				.mainSqlIndex(0)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	/**
	 * BF [ 2840157 ] AccessSqlParser is not parsing well ON keyword
	 *
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
	 * See https://sourceforge.net/tracker/?func=detail&aid=2840157&group_id=176962&atid=879332
	 */
	@Test
	public void test_BF2840157()
	{
		final String sql = "SELECT 1 FROM M_Product p"
				+ "\n" + "INNER JOIN M_Product_Category pc on (pc.M_Product_Category_ID=p.M_Product_Category_ID)"
				+ "\n" + "LEFT OUTER JOIN M_Product_PO mpo ON (mpo.M_Product_ID=p.M_Product_ID)"
				+ "\n" + " WHERE p.IsActive='Y' AND p.IsPurchased='Y'"
				+ "\n" + "AND COALESCE(mpo.DeliveryTime_Promised,0) <= 0";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql(sql)
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("M_Product", "p"))
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("M_Product_Category", "pc"))
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("M_Product_PO", "mpo"))
						.build())
				.mainSqlIndex(0)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void rewriteWhereClauseWithLowercaseKeyWords_adaptInnerWhereClause()
	{
		final String initialWhereClause = "noise WHERE noise NOISE Where noise"
				+ "\n"
				+ "WHERE noise FROM"
				+ "\n"
				+ " FROM noise frOm noise noiseFROM\t"
				+ "ON  noise on ON"
				+ " noise"
				+ "\nFROM\n"
				+ "\nWHERE\n"
				+ "\nON\n"
				+ " noise"
				+ "\tFROM\t"
				+ "\tWHERE\t"
				+ "\tON\t";

		final String adaptInnerWhereClause = ParsedSql.rewriteWhereClauseWithLowercaseKeyWords(initialWhereClause);

		final String expectedWhereClause = "noise where noise NOISE Where noise where noise from  from noise frOm noise noiseFROM on  noise on on noise from  where  on  noise from  where  on ";

		assertThat(expectedWhereClause).isEqualTo(adaptInnerWhereClause);
	}

	@Test
	public void queryContains_IS_NOT_DISTINCT_FROM()
	{
		final String sql = "SELECT"
				+ "\n ?"
				+ "\n , row_number() OVER (ORDER BY fts.Line, (master.Name) ASC NULLS LAST, (master.Lastname) ASC NULLS LAST, (##) ASC NULLS LAST, (master.C_BP_Contact_ID) ASC NULLS LAST)"
				+ "\n , master.C_BPartner_Location_ID, master.C_BP_Contact_ID"
				+ "\n FROM C_BPartner_Adv_Search_v master"
				+ "\n INNER JOIN T_ES_FTS_Search_Result fts ON (fts.Search_UUID=? AND master.C_BPartner_ID=fts.IntKey1 AND (fts.IntKey2 IS NULL OR master.C_BPartner_Location_ID IS NOT DISTINCT FROM fts.IntKey2) AND (fts.IntKey3 IS NULL OR master.C_BP_Contact_ID IS NOT DISTINCT FROM fts.IntKey3))"
				+ "\n WHERE 1=1";

		final ParsedSql actual = ParsedSql.parse(sql);

		final ParsedSql expected = ParsedSql.builder()
				.sqlSelect(SqlSelect.builder()
						.sql(sql)
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("C_BPartner_Adv_Search_v", "master"))
						.tableNameAndAlias(TableNameAndAlias.ofTableNameAndAlias("T_ES_FTS_Search_Result", "fts"))
						.build())
				.mainSqlIndex(0)
				.build();

		assertThat(actual).isEqualTo(expected);
	}

}
