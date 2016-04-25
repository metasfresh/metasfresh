package de.metas.commission.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;

import mockit.Expectations;
import mockit.Mocked;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.MPeriod;
import org.junit.Before;
import org.junit.Test;

import de.metas.commission.custom.type.HierarchyFixCommission;
import de.metas.commission.custom.type.ICommissionType;

public class MCAdvCommissionTypeTests {

	final int calendarId = 20;

	@Mocked
	MPeriod period;

	@Mocked(methods = "getBusinessLogic", inverse = true)
	MCAdvCommissionType type;

	final int termId = 10;

	final Timestamp time = SystemTime.asTimestamp();

	@Test
	public void getBusinessLogicClassNoCommissionTerm() {

		new Expectations() {
			{
		

				type.getClassname();
				returns(String.class.getName());
			}
		};

		try {
			type.getBusinessLogic(null);
			fail("expected AdempiereException because class is no ICommissionTerm");
		} catch (AdempiereException e) {
		}
	}

	@Test
	public void getBusinessLogicClassNotFound() {

		new Expectations() {
			{
				type.getClassname();
				returns("no.such.class");
			}
		};

		try {
			type.getBusinessLogic(null);
			fail("expected AdempiereException because class doesn't exist");
		} catch (AdempiereException e) {
		}
	}

	@Test
	public void getBusinessLogicOk() {

		new Expectations() {
			{
				type.getClassname();
				returns(HierarchyFixCommission.class.getName());
			}
		};

		final ICommissionType result = type.getBusinessLogic(null);
		assertTrue(result instanceof HierarchyFixCommission);
	}

	@Before
	public void setup() {

		
	}

}
