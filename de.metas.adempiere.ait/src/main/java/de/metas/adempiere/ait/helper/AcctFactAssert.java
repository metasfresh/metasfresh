package de.metas.adempiere.ait.helper;

/*
 * #%L
 * de.metas.adempiere.ait
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


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.acct.api.ClientAccountingStatus;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.db.CConnection;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MClient;
import org.compiere.model.PO;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.junit.Assert;

public class AcctFactAssert
{
	protected static final Logger logger = LogManager.getLogger(AcctFactAssert.class);

	private IHelper helper;

	private int firstFactAcctId = -1;

	public AcctFactAssert(IHelper helper)
	{
		this.helper = helper;
	}

	public void init()
	{
		setFirstFactAcctId();
		clearAllFacts();
		
		//
		// Configure immediate posting:
		final MClient client = MClient.get(helper.getCtx());
		boolean isPostImmediateOld = client.isPostImmediate();
		client.setIsPostImmediate(true);
		logger.info("Config: IsPostImmediate: " + isPostImmediateOld + "->" + client.isPostImmediate());
		client.saveEx();

		//
		// Configure embedded server:
		final String serverEmbeddedOld = System.getProperty(CConnection.SERVER_EMBEDDED_PROPERTY);
		System.setProperty(CConnection.SERVER_EMBEDDED_PROPERTY, "true");
		logger.info("Config: " + CConnection.SERVER_EMBEDDED_PROPERTY + ": " + serverEmbeddedOld + "->" + System.getProperty(CConnection.SERVER_EMBEDDED_PROPERTY));

		//
		// Enable client accounting
		final IPostingService postingService = Services.get(IPostingService.class);
		postingService.setClientAccountingStatus(ClientAccountingStatus.Immediate);
		Assert.assertEquals("Immediate Client Accounting not activated",
				ClientAccountingStatus.Immediate, // expected
				postingService.getClientAccountingStatus() // actual
		);
	}

	/**
	 * Finds out which is the latest Fact_Acct_ID. This happens only once. If this method is called a second time, it doesn't do anything.
	 * That way the result from the first call (i.e. start of tests) is preserved.
	 */
	public AcctFactAssert setFirstFactAcctId()
	{
		if(firstFactAcctId > 0)
		{
			return this; // nothing to do
		}
		
		String sql = "SELECT COALESCE(MAX(" + I_Fact_Acct.COLUMNNAME_Fact_Acct_ID + "), 0) FROM Fact_Acct"
				+ " WHERE AD_Client_ID=" + Env.getAD_Client_ID(helper.getCtx());

		this.firstFactAcctId = DB.getSQLValueEx(null, sql) + 1;
		logger.info("First Fact_Acct_ID that will be used: " + firstFactAcctId);

		return this;
	}

	/**
	 * WARNING: Clear all facts with Fact_Acct_ID>(the result of #setFirstFactAcctId) from Fact_Acct table.
	 * 
	 * It is supported be used on a test database. It is supposed to be called before each test.
	 * 
	 * @return
	 */
	public AcctFactAssert clearAllFacts()
	{
		Check.assume(this.firstFactAcctId > 0, "setFirstFactAcctId() has been called");
		
		final String sql = "DELETE FROM Fact_Acct WHERE "
				+ I_Fact_Acct.COLUMNNAME_AD_Client_ID + "=" + Env.getAD_Client_ID(helper.getCtx()) + " AND "
				+ I_Fact_Acct.COLUMNNAME_Fact_Acct_ID + ">" + this.firstFactAcctId;
		
		int no = DB.executeUpdateEx(sql, null);
		logger.info("Clean Fact_Acct (#" + no + " records)");
		return this;
	}

	public AcctFactAssert assertPosted(Object model)
	{
		assertPosted(model, true);
		return this;
	}

	public AcctFactAssert assertNotPosted(Object model)
	{
		return assertPosted(model, false);
	}

	private AcctFactAssert assertPosted(Object model, boolean expectedPosted)
	{
		Assert.assertNotNull("model is null", model);

		PO po = InterfaceWrapperHelper.getPO(model);
		Assert.assertNotNull("no PO found for model " + model, po);

		assertPostedFlag(po, true);

		// final Properties ctx = po.getCtx();
		// final String trxName = po.get_TrxName();
		final int tableId = po.get_Table_ID();
		final int recordId = po.get_ID();

		final AcctDimension dimension = new AcctDimension()
				.setAD_Table_ID(tableId)
				.setRecord_ID(recordId);
		final Balance bal = getBalance(dimension);
		boolean hasFacts = bal.getRecordsCount() > 0;

		Assert.assertEquals("Document " + po + " has " + (expectedPosted ? "no" : "") + " facts (" + bal + ")",
				expectedPosted, hasFacts);

		return this;
	}

	public AcctFactAssert assertPostedFlag(Object model, boolean isPosted)
	{
		Assert.assertNotNull("model is null", model);

		PO po = InterfaceWrapperHelper.getPO(model);
		Assert.assertNotNull("no PO found for model " + model, po);

		final int idx = po.get_ColumnIndex("Posted");
		Assert.assertTrue("Object " + po + " has no Posted column", idx >= 0);

		final boolean isPostedActual;
		final Object postedObj = po.get_Value(idx);
		if (postedObj instanceof Boolean)
			isPostedActual = ((Boolean)postedObj).booleanValue();
		else if (postedObj instanceof String)
			isPostedActual = "Y".equals(postedObj);
		else
			throw new AdempiereException("Unsupported Posted field type "+postedObj+" for model "+model);

		Assert.assertEquals("Document's " + po + " Posted flag does not match expectation", isPosted, isPostedActual);

		return this;
	}

	public AcctFactAssert assertAccountBalanced(String elementValue)
	{
		AcctDimension dim = new AcctDimension()
				.setElementValue(elementValue);
		Balance bal = getBalance(dim);

		assertThat("Not balanced: " + bal + " for " + dim, bal.getAmtAcctCr(), comparesEqualTo(bal.getAmtAcctDr()));
		return this;
	}

	public AcctFactAssert assertAccountAcctAmounts(String elementValue, BigDecimal amtDr, BigDecimal amtCr)
	{
		AcctDimension dim = new AcctDimension()
				.setElementValue(elementValue);
		return assertAcctAmounts(amtDr, amtCr, dim);
	}

	public AcctFactAssert assertAcctAmounts(BigDecimal amtDr, BigDecimal amtCr, AcctDimension dim)
	{
		Balance bal = getBalance(dim);
		assertThat("Wrong Acct DR: " + bal + " for " + dim, bal.getAmtAcctDr(), comparesEqualTo(amtDr));
		assertThat("Wrong Acct CR: " + bal + " for " + dim, bal.getAmtAcctCr(), comparesEqualTo(amtCr));
		return this;
	}

	private Balance getBalance(AcctDimension dimension)
	{
		final List<Object> params = new ArrayList<Object>();
		final StringBuffer whereClause = new StringBuffer();

		if (firstFactAcctId > 0)
		{
			if (whereClause.length() > 0)
				whereClause.append(" AND ");
			whereClause.append("fa.Fact_Acct_ID >= ").append(firstFactAcctId);
		}

		if (dimension.getElementValue() != null)
		{
			if (whereClause.length() > 0)
				whereClause.append(" AND ");
			whereClause.append(" fa.Account_ID IN ("
					+ " select vc.Account_ID"
					+ " from C_AcctSchema_Default asd"
					+ " inner join C_ValidCombination vc on (vc.C_ValidCombination_ID=asd." + dimension.getElementValue() + ")"
					+ " where asd.AD_Client_ID=" + Env.getAD_Client_ID(helper.getCtx())
					+ ")");
		}
		if (dimension.getC_BPartner_ID() > 0)
		{
			if (whereClause.length() > 0)
				whereClause.append(" AND ");
			whereClause.append("fa.").append(I_Fact_Acct.COLUMNNAME_C_BPartner_ID).append("=").append(dimension.getC_BPartner_ID());
		}
		if (dimension.getM_Product_ID() > 0)
		{
			if (whereClause.length() > 0)
				whereClause.append(" AND ");
			whereClause.append("fa.").append(I_Fact_Acct.COLUMNNAME_M_Product_ID).append("=").append(dimension.getM_Product_ID());
		}
		if (dimension.getAD_Table_ID() > 0)
		{
			if (whereClause.length() > 0)
				whereClause.append(" AND ");
			whereClause.append("fa.").append(I_Fact_Acct.COLUMNNAME_AD_Table_ID).append("=").append(dimension.getAD_Table_ID());
		}
		if (dimension.getRecord_ID() > 0)
		{
			if (whereClause.length() > 0)
				whereClause.append(" AND ");
			whereClause.append("fa.").append(I_Fact_Acct.COLUMNNAME_Record_ID).append("=").append(dimension.getRecord_ID());
		}
		Assert.assertTrue("Invalid dimension " + dimension, whereClause.length() > 0);

		final Balance balance = new Balance();

		String sql = "SELECT"
				+ "  COALESCE(SUM(fa.AmtAcctDr),0) AS AmtAcctDr"
				+ " , COALESCE(SUM(fa.AmtAcctCr),0) AS AmtAcctCr"
				+ ", COUNT(*) AS RecordsCount"
				+ " FROM Fact_Acct fa"
				+ " WHERE " + whereClause;
		logger.debug("sql: " + sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, helper.getTrxName());
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				balance.setAmtAcctDr(rs.getBigDecimal("AmtAcctDr"));
				balance.setAmtAcctCr(rs.getBigDecimal("AmtAcctCr"));
				balance.setRecordsCount(rs.getInt("RecordsCount"));
			}
			if (rs.next())
			{
				Assert.fail("Internal error: query " + sql + " returned more then one row");
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, params);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return balance;
	}

	public static class Balance
	{
		private BigDecimal amtAcctDr = BigDecimal.ZERO;
		private BigDecimal amtAcctCr = BigDecimal.ZERO;
		private int recordsCount = -1;

		public BigDecimal getAmtAcctDr()
		{
			return amtAcctDr;
		}

		public void setAmtAcctDr(BigDecimal amtAcctDr)
		{
			this.amtAcctDr = amtAcctDr;
		}

		public BigDecimal getAmtAcctCr()
		{
			return amtAcctCr;
		}

		public void setAmtAcctCr(BigDecimal amtAcctCr)
		{
			this.amtAcctCr = amtAcctCr;
		}

		public int getRecordsCount()
		{
			return recordsCount;
		}

		public void setRecordsCount(int recordsCount)
		{
			this.recordsCount = recordsCount;
		}

		@Override
		public String toString()
		{
			return "Balance [amtAcctDr=" + amtAcctDr + ", amtAcctCr=" + amtAcctCr
					+ ", records=#" + recordsCount
					+ "]";
		}

	}

	public static class AcctDimension
	{
		private String elementValue;
		private int C_BPartner_ID;
		private int M_Product_ID;
		private int AD_Table_ID;
		private int Record_ID;

		public String getElementValue()
		{
			return elementValue;
		}

		public AcctDimension setElementValue(String elementValue)
		{
			this.elementValue = elementValue;
			return this;
		}

		public int getC_BPartner_ID()
		{
			return C_BPartner_ID;
		}

		public AcctDimension setC_BPartner_ID(int c_BPartner_ID)
		{
			C_BPartner_ID = c_BPartner_ID;
			return this;
		}

		public int getM_Product_ID()
		{
			return M_Product_ID;
		}

		public AcctDimension setM_Product_ID(int m_Product_ID)
		{
			M_Product_ID = m_Product_ID;
			return this;
		}

		public int getAD_Table_ID()
		{
			return AD_Table_ID;
		}

		public AcctDimension setAD_Table_ID(int aD_Table_ID)
		{
			AD_Table_ID = aD_Table_ID;
			return this;
		}

		public int getRecord_ID()
		{
			return Record_ID;
		}

		public AcctDimension setRecord_ID(int record_ID)
		{
			Record_ID = record_ID;
			return this;
		}

		@Override
		public AcctDimension clone()
		{
			AcctDimension d = new AcctDimension();
			d.elementValue = this.elementValue;
			d.C_BPartner_ID = this.C_BPartner_ID;
			d.M_Product_ID = this.M_Product_ID;
			d.AD_Table_ID = this.AD_Table_ID;
			d.Record_ID = this.Record_ID;
			return d;
		}

		@Override
		public String toString()
		{
			return "AcctDimension ["
					+ "ev=" + elementValue
					+ ", bp=" + C_BPartner_ID
					+ ", product=" + M_Product_ID
					+ ", table=" + AD_Table_ID + ", recordId=" + Record_ID
					+ "]";
		}

	}
}
