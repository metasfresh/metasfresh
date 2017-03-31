package de.metas.process.ui;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_AD_Form;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor;

public class AProcessModelTest
{
	public static class ProcessPreconditionReturnTrue implements IProcessPrecondition
	{

		@Override
		public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
		{
			return ProcessPreconditionsResolution.accept();
		}
	}

	public static class ProcessPreconditionReturnFalse implements IProcessPrecondition
	{

		@Override
		public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
		{
			return ProcessPreconditionsResolution.reject();
		}
	}

	public static class ProcessPreconditionThrowsException implements IProcessPrecondition
	{

		@Override
		public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
		{
			throw new RuntimeException("Test Exception");
		}
	}

	private AProcessModel model;
	private IProcessPreconditionsContext preconditionsContext;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		POJOWrapper.setDefaultStrictValues(false); // we will want to return "null"

		model = new AProcessModel();

		preconditionsContext = new IProcessPreconditionsContext()
		{

			@Override
			public String getTableName()
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public <T> T getSelectedModel(final Class<T> modelClass)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public <T> List<T> getSelectedModels(final Class<T> modelClass)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public int getSelectionSize()
			{
				throw new UnsupportedOperationException();
			}
		};
	}

	@Test
	public void test_isPreconditionApplicable_NoClass()
	{
		final String classname = null;
		final RelatedProcessDescriptor process = createProcess(classname, false);
		assertPreconditionsApplicable("When class is not set we shall return true", true, process);
	}

	@Test
	public void test_isPreconditionApplicable_ClassNotFound()
	{
		final RelatedProcessDescriptor process = createProcess("MissingClass", false);
		assertPreconditionsApplicable("When classname is set but not found we shall return false", false, process);
	}

	@Test
	public void test_isPreconditionApplicable_ProcessClassNotImplementingPreconditionsInterface()
	{
		final RelatedProcessDescriptor process = createProcess(String.class, false);
		assertPreconditionsApplicable("When class is not implementing our interface we shall return true", true, process);
	}

	@Test
	public void test_isPreconditionApplicable_ProcessClassReturnsFalse()
	{
		final RelatedProcessDescriptor process = createProcess(ProcessPreconditionReturnFalse.class, false);
		assertPreconditionsApplicable("Process class returns false", false, process);
	}

	@Test
	public void test_isPreconditionApplicable_ProcessClassReturnsFalse_But_FormClassReturnsTrue()
	{
		final RelatedProcessDescriptor process = createProcessAndForm(ProcessPreconditionReturnFalse.class.getName(), ProcessPreconditionReturnTrue.class.getName());
		assertPreconditionsApplicable("If there is a process class defined we shall not look for form class", false, process);
	}

	@Test
	public void test_isPreconditionApplicable_ProcessClassReturnsTrue()
	{
		final RelatedProcessDescriptor process = createProcess(ProcessPreconditionReturnTrue.class, false);
		assertPreconditionsApplicable("Process class returns true", true, process);
	}

	@Test
	public void test_isPreconditionApplicable_ProcessClassThrowsException()
	{
		final RelatedProcessDescriptor process = createProcess(ProcessPreconditionThrowsException.class, false);
		assertPreconditionsApplicable("In case preconditions are throwing exceptions we shall not include the process", false, process);
	}

	@Test
	public void test_isPreconditionApplicable_FormClassReturnsFalse()
	{
		final RelatedProcessDescriptor process = createProcess(ProcessPreconditionReturnFalse.class, true);
		assertPreconditionsApplicable("Form class returns false", false, process);
	}

	@Test
	public void test_isPreconditionApplicable_FormClassReturnsTrue()
	{
		final RelatedProcessDescriptor process = createProcess(ProcessPreconditionReturnTrue.class, true);
		assertPreconditionsApplicable("Form class returns true", true, process);
	}

	/**
	 * Creates a process and a form ready for testing.
	 *
	 * @param clazz
	 * @param isForm true if is a form class; in that case a form will be created and linked to process
	 * @return
	 */
	private RelatedProcessDescriptor createProcess(final Class<?> clazz, final boolean isForm)
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

		return createProcess(classname, isForm);
	}

	private RelatedProcessDescriptor createProcess(final String classname, final boolean isForm)
	{
		final I_AD_Process process = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_Process.class, ITrx.TRXNAME_None);
		if (!isForm)
		{
			process.setClassname(classname);
		}

		if (isForm)
		{
			final I_AD_Form form = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_Form.class, ITrx.TRXNAME_None);
			form.setClassname(classname);
			InterfaceWrapperHelper.save(form);

			process.setAD_Form_ID(form.getAD_Form_ID());
		}

		InterfaceWrapperHelper.save(process);

		return RelatedProcessDescriptor.ofAD_Process_ID(process.getAD_Process_ID());
	}

	private RelatedProcessDescriptor createProcessAndForm(final String processClassname, final String formClassname)
	{
		final I_AD_Process process = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_Process.class, ITrx.TRXNAME_None);
		process.setClassname(processClassname);

		// Form
		{
			final I_AD_Form form = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_Form.class, ITrx.TRXNAME_None);
			form.setClassname(formClassname);
			InterfaceWrapperHelper.save(form);
			process.setAD_Form_ID(form.getAD_Form_ID());
		}

		InterfaceWrapperHelper.save(process);

		return RelatedProcessDescriptor.ofAD_Process_ID(process.getAD_Process_ID());
	}

	private void assertPreconditionsApplicable(final String message, final boolean expectAccepted, final RelatedProcessDescriptor relatedProcess)
	{
		final ProcessPreconditionsResolution resolution = model.checkPreconditionApplicable(relatedProcess, preconditionsContext);
		final boolean actualAccepted = resolution.isAccepted();

		final String messageEffective = message + "\n Resolution: " + resolution;
		Assert.assertEquals(messageEffective, expectAccepted, actualAccepted);
	}
}
