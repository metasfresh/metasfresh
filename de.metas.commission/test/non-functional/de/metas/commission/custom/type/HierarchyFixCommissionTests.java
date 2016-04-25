package de.metas.commission.custom.type;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.compiere.model.MBPartner;
import org.junit.Test;

import de.metas.adempiere.test.POTest;
import de.metas.commission.model.I_C_AdvCommissionInstance;
import de.metas.commission.model.MCAdvComSalesRepFact;
import de.metas.commission.model.MCAdvCommissionInstance;
import de.metas.commission.model.MCSponsor;
import de.metas.commission.model.X_C_AdvComSalesRepFact;

public class HierarchyFixCommissionTests
{

	@Mocked
	MCAdvCommissionInstance instance;

	@Mocked
	MCSponsor sponsor;

	/**
	 * If an instance's sales-rep-sponsor has no associated bPartner at a given date, getPercent returns
	 * <code>BigDecimal.ZERO</code>
	 */
	@Test
	public void getPercentNonSalesRep()
	{

		new NonStrictExpectations()
		{
			{
				instance.getC_Sponsor_SalesRep();
				returns(sponsor);
			}
		};

		final BigDecimal result = new HierarchyFixCommission().getPercent(
				instance, null, POTest.DATE);
		assertEquals(BigDecimal.ZERO, result);
	}

	/**
	 * If an instance's {@link I_C_AdvCommissionInstance#COLUMNNAME_LevelCommission} is less than zero and the actual
	 * status ist required (not forecast), getPercent returns <code>BigDecimal.ZERO</code>.
	 */
	@Test
	public void getPercentLevelLessThan0()
	{

		new Expectations()
		{
			{
				instance.getLevelCalculation();
				returns(-1);
			}
		};

		final BigDecimal result = new HierarchyFixCommission().getPercent(
				instance, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant, POTest.DATE);
		assertEquals(BigDecimal.ZERO, result);
	}

	/**
	 * If a sales rep doesn't have a sales rep instance, getPercent returns <code>BigDecimal.ZERO</code>.
	 */
	@Test
	public void getPercentNoSDalesRepInstance()
	{

		new Expectations()
		{

			MBPartner salesRep;

			{
				instance.getLevelCalculation();
				returns(1);

				instance.getC_Sponsor_SalesRep();
				returns(sponsor);
			}
		};

		final BigDecimal result = new HierarchyFixCommission().getPercent(
				instance, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant, POTest.DATE);
		assertEquals(BigDecimal.ZERO, result);
	}

	/**
	 * If a sales rep doesn't have a record of salary group changes (i.e. never been assigned a salary group yet)
	 * getPercent returns <code>BigDecimal.ZERO</code>.
	 */
	@Test
	public void getPercentNoSGFact()
	{

		new Expectations()
		{

			MBPartner salesRep;

			@SuppressWarnings("unused")
			MCAdvComSalesRepFact salesRepFact;

			{
				instance.getLevelCalculation();
				returns(1);

				instance.getC_Sponsor_SalesRep();
				returns(sponsor);

				MCAdvComSalesRepFact.retrieveLatestAtDate(sponsor,
						null,
							X_C_AdvComSalesRepFact.NAME_VG_Aenderung, POTest.DATE,
							X_C_AdvComSalesRepFact.STATUS_Prov_Relevant);
				returns(null);
			}
		};

		final BigDecimal result = new HierarchyFixCommission().getPercent(
				instance, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant, POTest.DATE);
		assertEquals(BigDecimal.ZERO, result);
	}
}
