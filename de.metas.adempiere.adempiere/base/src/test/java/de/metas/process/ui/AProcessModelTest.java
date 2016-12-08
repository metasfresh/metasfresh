package de.metas.process.ui;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_AD_Form;
import de.metas.process.IProcessPrecondition;

public class AProcessModelTest
{
	public static class ProcessPreconditionReturnTrue implements IProcessPrecondition
	{

		@Override
		public boolean isPreconditionApplicable(final PreconditionsContext context)
		{
			return true;
		}
	}

	public static class ProcessPreconditionReturnFalse implements IProcessPrecondition
	{

		@Override
		public boolean isPreconditionApplicable(final PreconditionsContext context)
		{
			return false;
		}
	}

	public static class ProcessPreconditionThrowsException implements IProcessPrecondition
	{

		@Override
		public boolean isPreconditionApplicable(final PreconditionsContext context)
		{
			throw new RuntimeException("Test Exception");
		}
	}

	private AProcessModel model;
	private GridTab gridTab = null; // nothing

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		
		POJOWrapper.setDefaultStrictValues(false); // we will want to return "null"
		
		model = new AProcessModel();
	}

	@Test
	public void test_isPreconditionApplicable_NoClass()
	{
		final I_AD_Process process = createProcess(null, false);
		Assert.assertEquals(
				"When class is not set we shall return true",
				true, model.isPreconditionApplicable(process, gridTab));
	}

	@Test
	public void test_isPreconditionApplicable_ClassNotFound()
	{
		final I_AD_Process process = createProcess(null, false);
		process.setClassname("MissingClass");
		Assert.assertEquals(
				"When classname is set but not found we shall return false",
				false, model.isPreconditionApplicable(process, gridTab));
	}

	@Test
	public void test_isPreconditionApplicable_ProcessClassNotImplementingPreconditionsInterface()
	{
		final I_AD_Process process = createProcess(String.class, false);
		Assert.assertEquals(
				"When class is not implementing our interface we shall return true",
				true, model.isPreconditionApplicable(process, gridTab));
	}

	@Test
	public void test_isPreconditionApplicable_ProcessClassReturnsFalse()
	{
		final I_AD_Process process = createProcess(ProcessPreconditionReturnFalse.class, false);
		Assert.assertEquals(false, model.isPreconditionApplicable(process, gridTab));
	}

	@Test
	public void test_isPreconditionApplicable_ProcessClassReturnsFalse_But_FormClassReturnsTrue()
	{
		final I_AD_Process process = createProcess(ProcessPreconditionReturnTrue.class, true);
		process.setClassname(ProcessPreconditionReturnFalse.class.getName());
		Assert.assertEquals(
				"If there is a process class defined we shall not look for form class",
				false, model.isPreconditionApplicable(process, gridTab));
	}

	@Test
	public void test_isPreconditionApplicable_ProcessClassReturnsTrue()
	{
		final I_AD_Process process = createProcess(ProcessPreconditionReturnTrue.class, false);
		Assert.assertEquals(true, model.isPreconditionApplicable(process, gridTab));
	}

	@Test
	public void test_isPreconditionApplicable_ProcessClassThrowsException()
	{
		final I_AD_Process process = createProcess(ProcessPreconditionThrowsException.class, false);
		Assert.assertEquals(
				"In case preconditions are throwing exceptions we shall not include the process",
				false, model.isPreconditionApplicable(process, gridTab));
	}

	@Test
	public void test_isPreconditionApplicable_FormClassReturnsFalse()
	{
		final I_AD_Process process = createProcess(ProcessPreconditionReturnFalse.class, true);
		Assert.assertEquals(false, model.isPreconditionApplicable(process, gridTab));
	}

	@Test
	public void test_isPreconditionApplicable_FormClassReturnsTrue()
	{
		final I_AD_Process process = createProcess(ProcessPreconditionReturnTrue.class, true);
		Assert.assertEquals(true, model.isPreconditionApplicable(process, gridTab));
	}

	/**
	 * Creates a process and a form ready for testing {@link AProcessModel#isPreconditionApplicable(I_AD_Process, GridTab)}.
	 * 
	 * @param clazz
	 * @param isForm true if is a form class; in that case a form will be created and linked to process
	 * @return
	 */
	private I_AD_Process createProcess(final Class<?> clazz, final boolean isForm)
	{
		final String classname;
		if (clazz == null)
		{
			classname = null;
		}
		else
		{
			classname = clazz.getName();
		}

		final I_AD_Process process = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_Process.class, ITrx.TRXNAME_None);
		if (!isForm)
		{
			process.setClassname(classname);
		}
		InterfaceWrapperHelper.save(process);

		if (isForm)
		{
			final I_AD_Form form = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_Form.class, ITrx.TRXNAME_None);
			form.setClassname(classname);
			InterfaceWrapperHelper.save(form);

			process.setAD_Form_ID(form.getAD_Form_ID());
			InterfaceWrapperHelper.save(process);
		}

		return process;
	}
}
