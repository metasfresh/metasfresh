package de.metas.commission.aop;

import static org.junit.Assert.assertEquals;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.compiere.model.PO;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.test.POTest;
import de.metas.commission.model.MCAdvComRelevantPOType;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionRelevantPO;

public class POSaveDeleteTests {

	@Mocked
	MethodInvocation inv;

	@Mocked
	PO po;

	@Mocked
	MCAdvCommissionFactCand candUnused;

	@Mocked
	MCAdvCommissionRelevantPO relevantPO;

	@Mocked
	MCAdvComRelevantPOType relevantPOType;

	private final static int recordId = 3;
	private final static int tableId = 4;

	/**
	 * Deletion is not allowed for POs that might be commission relevant
	 * 
	 * @throws Throwable
	 */
	@Test
	public void deleteException() throws Throwable {

		POTest.recordGenericExpectations(relevantPO, 10);
		
		new NonStrictExpectations() {{
			
				MCAdvCommissionRelevantPO.retrieveIfRelevant(po); result = relevantPO;
		}};

		final Object result = new POSaveDelete().delete(inv);
		assertEquals(result, false);
	}

	@Test
	public void deleteOK() throws Throwable {

		new NonStrictExpectations() {
			
			@SuppressWarnings("unused")
			MCAdvCommissionFact fact;
			{
				MCAdvCommissionRelevantPO.retrieveIfRelevant(po);
				returns(null);

				MCAdvCommissionFact.isFactExists(po);
				returns(false);
				
				inv.getMethod();
				result = Object.class.getMethod("toString");
			}
		};
		new POSaveDelete().delete(inv);
	}

	@SuppressWarnings("unused")
	@Mocked
	private final MCAdvCommissionRelevantPO commissionRelevantPO = null;

	@Before
	public void setup() throws Throwable {

		POTest.recordGenericExpectations(po, recordId);

		new NonStrictExpectations() {
			{
				inv.invokeNext();
				returns(true);
				inv.getTargetObject();
				returns(po);

				po.get_ID();
				returns(recordId);
				po.get_Table_ID();
				returns(tableId);

				po.get_TableName();
				returns("C_Invoice");
			}
		};
	}
}
