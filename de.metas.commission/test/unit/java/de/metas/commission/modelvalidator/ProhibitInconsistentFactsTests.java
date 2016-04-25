package de.metas.commission.modelvalidator;

import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Properties;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.compiere.model.POInfo;
import org.junit.Before;
import org.junit.Test;

import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionInstance;
import de.metas.commission.model.X_C_AdvCommissionFact;

/**
 * 
 * @author ts
 * @see http://dewiki908/mediawiki/index.php/02219:_Provision_-_Mehrfachberechnung_verhindern_%282011101110000082%29#Unit_Test
 */
public class ProhibitInconsistentFactsTests
{
	private static final int FACT_TABLE_ID = 83;

	@Mocked
	MCAdvCommissionInstance instance;

	@Mocked
	MCAdvCommissionFact fact;

	@Mocked POInfo poInfo;
	
	/**
	 * Case 1.
	 * The model validator returns null if <code>fact</code> is not a counter fact.
	 */
	@Test
	public void pointsSumChangeNotCounterEntry()
	{
		factIsCounterEntry(false);
		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}

	/**
	 * Case 2.
	 * The model validator returns null if <code>fact</code> has status "calculated".
	 */
	@Test
	public void pointsSumChangeStatusCalculated()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_Berechnet);
		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}

	/**
	 * Case 3.
	 * The model validator returns null if <code>fact</code> has status "to pay".
	 */
	@Test
	public void pointsSumChangeStatusToPay()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_Auszuzahlen);
		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}

	/**
	 * Case 4.
	 * The model validator returns null if <code>instance</code> has a sum of 2 and <code>fact</code> deducts 2. 
	 */
	@Test
	public void pointsSumChangeStatusPredictedOK()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_Prognostiziert);
		factHasPoints(new BigDecimal("-2"));
		instanceHasPoints_Predicted(new BigDecimal("2"));
		
		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}

	/**
	 * Case 5.1.
	 * The model validator returns null if <code>fact</code> deducts 0. 
	 */
	@Test
	public void pointsSumChangeStatusPredictedZeroOK()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_Prognostiziert);
		factHasPoints(new BigDecimal("0"));
		instanceHasPoints_Predicted(new BigDecimal("2"));

		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}

	/**
	 * Case 5.2.
	 * The model validator returns null if <code>fact</code> deducts 0. 
	 */
	@Test
	public void pointsSumChangeStatusPredictedZeroOK2()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_Prognostiziert);
		factHasPoints(new BigDecimal("0"));
		instanceHasPoints_Predicted(new BigDecimal("-2"));
		
		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}
	
	/**
	 * Case 6.
	 * The model validator returns null if <code>instance</code> has a sum of -2 and <code>fact</code> deducts -1.
	 * Note: This can be the case with credit memos. 
	 */
	@Test
	public void pointsSumChangeStatusPredictedNegativeOK()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_Prognostiziert);
		factHasPoints(new BigDecimal("1"));
		instanceHasPoints_Predicted(new BigDecimal("-2"));
		
		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}
	
	/**
	 * Case 7.
	 * The model validator throws an AdempiereException if <code>instance</code> has a sum of 1, but <code>fact</code>
	 * deducts 2.
	 * Note: allowed maximum to deduct is 1.
	 */
	@Test(expected = AdempiereException.class)
	public void pointsSumChangeStatusPredictedError()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_Prognostiziert);
		factHasPoints(new BigDecimal("-2"));
		instanceHasPoints_Predicted(new BigDecimal("1"));
		
		new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW);
	}

	/**
	 * Case 8.
	 * The model validator throws an AdempiereException if <code>instance</code> has a sum of -1, but <code>fact</code>
	 * deducts -2.
	 * Note: allowed maximum to deduct is -1.
	 */
	@Test(expected = AdempiereException.class)
	public void pointsSumChangeStatusPredictedNegativeError()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_Prognostiziert);
		factHasPoints(new BigDecimal("2"));
		instanceHasPoints_Predicted(new BigDecimal("-1"));
		
		new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW);
	}
	
	/**
	 * Case 9.
	 * The model validator returns null if <code>instance</code> has a sum of 2 and <code>fact</code> deducts 2. 
	 */
	@Test
	public void pointsSumChangeStatusToCalcOK()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen);
		factHasPoints(new BigDecimal("-2"));
		instanceHasPoints_ToCalculate(new BigDecimal("2"));
		factCommissionAmtBase(new BigDecimal("22.33"));
		
		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}
	
	/**
	 * Case 10.
	 * The model validator returns null if <code>fact</code> deducts 0. 
	 */
	@Test
	public void pointsSumChangeStatusToCalcZeroOK()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen);
		factHasPoints(new BigDecimal("0"));
		instanceHasPoints_ToCalculate(new BigDecimal("2"));
		factCommissionAmtBase(new BigDecimal("22.33"));
		
		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}
	
	/**
	 * Case 11.
	 * The model validator returns null if <code>instance</code> has a sum of -2 and <code>fact</code> deducts -1.
	 * Note: This can be the case with credit memos. 
	 */
	@Test
	public void pointsSumChangeStatusToCalcNegativeOK()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen);
		factHasPoints(new BigDecimal("1"));
		instanceHasPoints_ToCalculate(new BigDecimal("-2"));
		factCommissionAmtBase(new BigDecimal("22.33"));
		
		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}
		
	/**
	 * Case 12.
	 * The model validator throws an AdempiereException if <code>instance</code> has a sum of 1, but <code>fact</code>
	 * deducts 2. Note: allowed maximum to deduct is 1.
	 */
	@Test(expected = AdempiereException.class)
	public void pointsSumChangeStatusToCalcError()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen);
		factHasPoints(new BigDecimal("-2"));
		instanceHasPoints_ToCalculate(new BigDecimal("1"));
		factCommissionAmtBase(new BigDecimal("22.33"));
		
		new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW);
	}

	/**
	 * Case 13.
	 * The model validator throws an AdempiereException if <code>instance</code> has a sum of -1, but <code>fact</code>
	 * deducts -2. Note: allowed maximum to deduct is -1.
	 */
	@Test(expected = AdempiereException.class)
	public void pointsSumChangeStatusToCalcNegativeError()
	{
		factIsCounterEntry(true);
		factHasStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen);
		factHasPoints(new BigDecimal("2"));
		instanceHasPoints_ToCalculate(new BigDecimal("-1"));
		factCommissionAmtBase(new BigDecimal("22.33"));
		
		new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW);
	}
	
	/**
	 * Case 14.
	 * The model validator returns null if <code>fact</code> has 'CommissionAmtBase' != 0 and != null.
	 */
	@Test
	public void baseAmtOK()
	{
		factIsCounterEntry(false);
		factHasStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen);
		factCommissionAmtBase(new BigDecimal("22.33"));
		
		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}
	
	/**
	 * Case 15.
	 * The model validator returns null if <code>fact</code> has 'CommissionAmtBase' = null, but status is != 'CP'
	 */
	@Test
	public void baseAmtOK2()
	{
		factIsCounterEntry(false);
		factHasStatus(X_C_AdvCommissionFact.STATUS_Prognostiziert);
		factCommissionAmtBase(null);
		
		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}
	
	/**
	 * Case 16.
	 * The model validator returns null if <code>fact</code> has 'CommissionAmtBase' = 0 (but not null!).
	 */
	@Test
	public void baseAmtOK3()
	{
		factIsCounterEntry(false);
		factHasStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen);
		factCommissionAmtBase(new BigDecimal("000.000"));
		
		assertNull(new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW));
	}
	
	/**
	 * Case 17.
	 * The model validator throws an AdempiereException if <code>fact</code> has 'CommissionAmtBase' = null.
	 */
	@Test(expected = AdempiereException.class)
	public void baseAmtNull()
	{
		factIsCounterEntry(false);
		factHasStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen);
		factCommissionAmtBase(null);
		
		new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW);
	}
	
	/**
	 * Case 18.
	 * The model validator throws an AdempiereException if COLUMNNAME_CommissionPoints is updatable.
	 */
	@Test(expected = AdempiereException.class)
	public void columnBaseAmtUpdatable()
	{
		columnIsUpdatable(true, false);
		new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW);
	}

	/**
	 * Case 19.
	 * The model validator throws an AdempiereException if COLUMNNAME_CommissionAmtBase is updatable.
	 */
	@Test(expected = AdempiereException.class)
	public void columnCommissionPointsUpdatable()
	{
		columnIsUpdatable(false, true);
		new ProhibitInconsistentFacts().modelChange(fact, ModelValidator.TYPE_BEFORE_NEW);
	}
	
	private void factCommissionAmtBase(final BigDecimal commissionAmtBase)
	{
		new NonStrictExpectations()
		{{
			fact.get_Value(I_C_AdvCommissionFact.COLUMNNAME_CommissionAmtBase);
			result = commissionAmtBase;
		}};
	}

	// Helper methods ----------------------------------
	
	private void factIsCounterEntry(final boolean isCounterEntry)
	{
		new NonStrictExpectations()
		{{
				fact.isCounterEntry();
				result = isCounterEntry;
		}};
	}

	private void factHasStatus(final String status)
	{
		new NonStrictExpectations()
		{{
				fact.getStatus();
				result = status;
		}};
	}

	private void factHasPoints(final BigDecimal points)
	{
		new NonStrictExpectations()
		{{
				fact.getCommissionPoints();
				result = points;
		}};
	}

	private void instanceHasPoints_ToCalculate(final BigDecimal points)
	{
		new NonStrictExpectations()
		{{
				instance.getPoints_ToCalculate();
				result = points;
		}};
	}

	private void instanceHasPoints_Predicted(final BigDecimal points)
	{
		new NonStrictExpectations()
		{{
				instance.getPoints_Predicted();
				result = points;
		}};
	}

	private void columnIsUpdatable(final boolean commissionPointsUpdatable, final boolean commissionAmtBaseUpdatable)
	{
		new NonStrictExpectations()
		{{
			poInfo.getColumnIndex(I_C_AdvCommissionFact.COLUMNNAME_CommissionPoints); result = 24;
			poInfo.isColumnUpdateable(24); result = commissionPointsUpdatable;
			
			poInfo.getColumnIndex(I_C_AdvCommissionFact.COLUMNNAME_CommissionAmtBase); result = 21;
			poInfo.isColumnUpdateable(21); result = commissionAmtBaseUpdatable;
		}};
	}
		
	@Before
	public void setup()
	{
		new NonStrictExpectations()
		{{
				fact.getC_AdvCommissionInstance(); result = instance;
				
				fact.get_Table_ID(); result = FACT_TABLE_ID;
				POInfo.getPOInfo((Properties)any, FACT_TABLE_ID, (String)any); result = poInfo;
		}};
	}
}
