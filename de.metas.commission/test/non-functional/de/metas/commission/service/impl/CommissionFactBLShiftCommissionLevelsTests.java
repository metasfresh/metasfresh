package de.metas.commission.service.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.compiere.model.MBPartner;
import org.compiere.model.MEntityType;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.service.IParameterizable;
import de.metas.adempiere.test.POTest;
import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionInstance;
import de.metas.commission.model.I_C_AdvCommissionRelevantPO;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionInstance;
import de.metas.commission.model.MCSponsor;
import de.metas.commission.model.X_C_AdvComSystem_Type;

/**
 * Tests for {@link CommissionFactBL#updateCommissionLevels(ICommissionTerm, MCAdvCommissionFactCand, java.util.List)} .
 * 
 * @author ts
 * 
 */
public class CommissionFactBLShiftCommissionLevelsTests
{

	private static final int LEVEL_1 = 1;
	private static final int LEVEL_2 = 2;
	private static final int LEVEL_3 = 3;

	@Mocked
	I_C_AdvCommissionRelevantPO iAdvCommissionRelevantPO;

	MockType term;

	@Mocked
	MEntityType type;

	@Mocked
	MTable table;

	@Mocked
	MCAdvCommissionInstance inst1;

	@Mocked
	MCAdvCommissionInstance inst2;

	@Mocked
	MCAdvCommissionInstance inst3;

	@Mocked
	MBPartner bPartner1;

	@Mocked
	MCSponsor sponsor1;

	@Mocked
	MBPartner bPartner2;

	@Mocked
	MCSponsor sponsor2;

	@Mocked
	MBPartner bPartner3;
	@Mocked
	MCSponsor sponsor3;

	@Test
	public void updateSingleCompress()
	{
		new NonStrictExpectations()
		{
			MCSponsor sponsor1;
			{
				onInstance(inst1).getC_Sponsor_SalesRep();
				returns(sponsor1);

				onInstance(inst1).getLevelForecast();
				returns(1);

				onInstance(inst1).getLevelCalculation();
				returns(1);
			}
		};

		term = new MockType(true);

		new CommissionFactBL().updateLevelCalculation(
				term,
				X_C_AdvComSystem_Type.DYNAMICCOMPRESSION_NachObenVerschShift,
				POTest.DATE, 
				Collections.singletonList(inst1));

		new Verifications()
		{
			{
				inst1.setLevelCalculation(-1);
				inst1.saveEx();
			}
		};
	}

	@Test
	public void updateSingleNoCompress()
	{

		assertSingleOK(false, -1, LEVEL_1);
	}

	/**
	 * One instance that already has LevelCommission==-1 and keeps it. Thus no update of the instance is expected.
	 */
	@Test
	public void updateSingleOnlyOnChange()
	{

		setupInst(inst1, LEVEL_1, -1);

		term = new MockType(true);

		new CommissionFactBL().updateLevelCalculation(term,
				X_C_AdvComSystem_Type.DYNAMICCOMPRESSION_NachObenVerschShift,
				POTest.DATE, Collections.singletonList(inst1));

		new Verifications()
		{
			{
				inst1.setLevelCalculation(-1);
				maxTimes = 0;
			}
		};
	}

	private void assertSingleOK(final boolean isCompress,
			final int levelCommission, final int levelExp)
	{

		setupInst(inst1, LEVEL_1, -1);

		term = new MockType(isCompress);

		new CommissionFactBL().updateLevelCalculation(term,
				X_C_AdvComSystem_Type.DYNAMICCOMPRESSION_NachObenVerschShift,
				POTest.DATE, Collections.singletonList(inst1));

		new Verifications()
		{
			{
				inst1.setLevelCalculation(levelExp);
				inst1.saveEx();
			}
		};
	}

	private void setupInst(final MCAdvCommissionInstance instToSetup,
			final int level, final int levelCommission)
	{

		new NonStrictExpectations()
		{
			MCSponsor sponsor1;
			{
				onInstance(instToSetup).getC_Sponsor_SalesRep();
				returns(sponsor1);

				onInstance(instToSetup).getLevelCalculation();
				returns(levelCommission);

				onInstance(instToSetup).getLevelHierarchy();
				returns(level);
			}
		};
	}

	/**
	 * Three instances. The first one is compressed.
	 */
	@Test
	public void update3Compress1st()
	{

		final MockType term = new MockType(true, false, false);

		final List<CommissionFactBL.CommissionInstanceBean> instances = invokeShiftLevels(
				term, LEVEL_1, LEVEL_2, LEVEL_3);

		assertEquals(-1, instances.get(0).getLevelCalc());
		assertEquals(1, instances.get(1).getLevelCalc());
		assertEquals(2, instances.get(2).getLevelCalc());
	}

	/**
	 * Three instances. The second one is compressed.
	 */
	@Test
	public void update3Compress2nd()
	{

		final MockType term = new MockType(false, true, false);

		final List<CommissionFactBL.CommissionInstanceBean> instances = invokeShiftLevels(
				term, LEVEL_1, LEVEL_2, LEVEL_3);

		assertEquals(1, instances.get(0).getLevelCalc());
		assertEquals(-1, instances.get(1).getLevelCalc());
		assertEquals(2, instances.get(2).getLevelCalc());
	}

	/**
	 * Three instances. The levels are 2, 3, 5. The second one is compressed, so we expect the last level to be shifted
	 * to 4
	 */
	@Test
	public void update3Compress2ndNonStdLevel()
	{

		final MockType term = new MockType(false, true, false);

		final List<CommissionFactBL.CommissionInstanceBean> instances = invokeShiftLevels(
				term, 2, 3, 5);

		assertEquals(2, instances.get(0).getLevelCalc());
		assertEquals(-1, instances.get(1).getLevelCalc());
		assertEquals(4, instances.get(2).getLevelCalc());
	}

	private List<CommissionFactBL.CommissionInstanceBean> invokeShiftLevels(
			final MockType term, final int level1, final int level2,
			final int level3)
	{
		final CommissionFactBL.CommissionInstanceBean instBean1 = new CommissionFactBL.CommissionInstanceBean();
		instBean1.setLevelForecast(level1);
		instBean1.setSponsorSalesRep(sponsor1);

		final CommissionFactBL.CommissionInstanceBean instBean2 = new CommissionFactBL.CommissionInstanceBean();
		instBean2.setLevelForecast(level2);
		instBean2.setSponsorSalesRep(sponsor2);

		final CommissionFactBL.CommissionInstanceBean instBean3 = new CommissionFactBL.CommissionInstanceBean();
		instBean3.setLevelForecast(level3);
		instBean3.setSponsorSalesRep(sponsor3);

		new NonStrictExpectations()
		{
			{
			}
		};

		final List<CommissionFactBL.CommissionInstanceBean> instances =
				new ArrayList<CommissionFactBL.CommissionInstanceBean>(3);
		instances.add(instBean1);
		instances.add(instBean2);
		instances.add(instBean3);

		final CommissionFactBL commissionFactBL = new CommissionFactBL();
		commissionFactBL.updateLevelsCompressionShift(POTest.DATE, null, instances);
		return instances;
	}

	@Before
	public void setup()
	{
		term = new MockType();
	}

	class MockType implements ICommissionType
	{
		int count = 0;

		final boolean[] results;

		MockType(boolean... results)
		{
			this.results = results;
		}

		@Override
		public BigDecimal getFactor()
		{
			return null;
		}

		@Override
		public BigDecimal getPercent(I_C_AdvCommissionInstance inst,
				String status, Timestamp date)
		{
			return null;
		}

		@Override
		public BigDecimal getCommissionPoints(I_C_AdvCommissionInstance inst,
				String status, Timestamp date, PO po)
		{
			return null;
		}

		@Override
		public void evaluateCandidate(MCAdvCommissionFactCand candidate, String status, int adPinstanceId)
		{
		}

		@Override
		public boolean isCommissionCalulated()
		{
			return false;
		}

		@Override
		public IParameterizable getInstanceParams(Properties ctx, I_C_AdvComSystem system, String trxName)
		{
			return null;
		}

		@Override
		public IParameterizable getSponsorParams(Properties ctx, I_C_AdvCommissionCondition contract, String trxName)
		{
			return null;
		}

		@Override
		public I_C_AdvComSystem_Type getComSystemType()
		{
			return null;
		}

		@Override
		public void setComSystemType(I_C_AdvComSystem_Type comSystemType)
		{
		}
	}
}
