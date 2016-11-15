package de.metas.adempiere.ait.helper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.service.IADPInstanceDAO;
import org.adempiere.model.GridTabWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Process;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;
import org.compiere.process.StateEngine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Trx;

@SuppressWarnings("unused")
public class ProcessHelper
{
	private final IHelper helper;
	private int processId;
	private Class<? extends SvrProcess> clazz;
	private int tableId = -1;
	private int recordId = 0;
	private final Map<String, Object> parameters = new Hashtable<String, Object>();
	private ProcessInfo pi;
	private SvrProcess svrProcess;
	private boolean result;
	private String resultMsg = null;
	private List<Integer> selection;

	private int expectedProcessId;
	private Class<? extends SvrProcess> expectedClass;
	
	public static final String DEFAULT_ExpectedWFState = StateEngine.STATE_Completed;
	private String expectedWFState = DEFAULT_ExpectedWFState;
	
	private final List<IProcessHelperListener> listeners = new ArrayList<IProcessHelperListener>();
	
	private I_AD_Process process;
	private I_AD_PInstance pinstance;

	private Class<? extends Exception> expectedException;
	
	private String trxName = null;

	public ProcessHelper(IHelper helper)
	{
		this.helper = helper;
		this.trxName = helper.getTrxName();
	}

	public void quickRun(Class<? extends SvrProcess> clazz, Object record)
	{
		setProcessClass(clazz)
				.setPO(record)
				.run();
	}
	
	public ProcessHelper setProcessClass(Class<? extends SvrProcess> clazz)
	{
		this.clazz = clazz;
		return this;
	}

	public ProcessHelper setProcessId(int processId)
	{
		this.processId = processId;
		this.expectedProcessId = processId;
		return this;
	}

	public ProcessHelper setTableId(String tableName)
	{
		this.tableId = MTable.getTable_ID(tableName);
		return this;
	}

	public ProcessHelper setTableId(int tableId)
	{
		this.tableId = tableId;
		return this;
	}

	public ProcessHelper setRecordId(int recordId)
	{
		this.recordId = recordId;
		return this;
	}

	public ProcessHelper setPO(Object o)
	{
		PO po = InterfaceWrapperHelper.getStrictPO(o);
		if (po != null)
		{
			setTableId(po.get_Table_ID());
			setRecordId(po.get_ID());
			return this;
		}

		GridTab gridTab = GridTabWrapper.getGridTab(o);
		if (gridTab != null)
		{
			setTableId(gridTab.get_TableName());
			setRecordId(gridTab.getKeyID(gridTab.getCurrentRow()));
			return this;
		}

		fail("Object " + o + " is not supported in setPO");
		return this;
	}

	public ProcessHelper setParameter(String name, Object value)
	{
		parameters.put(name, value);
		return this;
	}

	public ProcessHelper setSelection(int... ids)
	{
		this.selection = new ArrayList<Integer>();
		for (int id : ids)
			this.selection.add(id);
		return this;
	}

	public ProcessHelper setSelection(Collection<Integer> ids)
	{
		this.selection = new ArrayList<Integer>(ids);
		return this;
	}

	public ProcessHelper addSelection(int[] ids)
	{
		if (selection == null)
			selection = new ArrayList<Integer>();
		for (int id : ids)
			selection.add(id);
		return this;
	}

	public ProcessHelper addSelection(Collection<Integer> ids)
	{
		if (selection == null)
			selection = new ArrayList<Integer>();
		selection.addAll(ids);
		return this;
	}

	public void run(final String tabWhereClause)
	{
		throw new UnsupportedOperationException();
		
		// TODO: check/re-implemented if needed.
//		
//		assertNull("This helper was already run", svrProcess);
//		
//		// Call beforeRun listeners
//		for (IProcessHelperListener listener : listeners)
//		{
//			listener.beforeRun(this);
//		}
//		
//		// committing the helper's transaction to make sure that there are no locked rows and that intermediate results are avaialable 
//		// when the process/workflow transaction tries to do its work.
//		helper.commitTrx(false);
//		
//		final IProcessPA processPA = Services.get(IProcessPA.class);
//		if (processId <= 0)
//		{
//			processId = processPA.retrieveProcessId(clazz, null);
//			assertTrue("No process found for " + clazz, processId > 0);
//		}
//		this.process = MProcess.get(helper.getCtx(), processId);
//		assertNotNull("No process found for " + processId, process);
//
//		if (expectedProcessId > 0)
//		{
//			assertEquals("Wrong AD_Process_ID for " + this.process.getClassname(), expectedProcessId, processId);
//		}
//		if (expectedClass != null)
//		{
//			assertEquals("Wrong process class for AD_Process_ID=" + processId, expectedClass.getName(), this.process.getClassname());
//		}
//
//		createPInstance();
//		helper.createT_Selection(pinstance.getAD_PInstance_ID(), getSelectionIds());
//
//		this.pi = new ProcessInfo("TestRun_" + clazz + "_" + processId + "_" + pinstance.getAD_PInstance_ID(), processId);
//		pi.setAD_Client_ID(Env.getAD_Client_ID(helper.getCtx()));
//		pi.setAD_Org_ID(Env.getAD_Org_ID(helper.getCtx()));
//		pi.setAD_User_ID(Env.getAD_User_ID(helper.getCtx()));
//		pi.setAD_PInstance_ID(pinstance.getAD_PInstance_ID());
//		pi.setTable_ID(tableId);
//		pi.setRecord_ID(recordId);
//		pi.setIsBatch(false);
//		pi.setClassName(process.getClassname());
//		
//		if (!Check.isEmpty(tabWhereClause, true))
//		{
//			pi.setWhereClause(tabWhereClause);
//		}
//
//		if (!Check.isEmpty(pi.getClassName(), true))
//		{
//			Trx trx = getTrx();
//			if (trx != null)
//				this.result = ProcessUtil.startJavaProcessWithoutTrxClose(helper.getCtx(), pi, trx);
//			else
//			{
//				final String localTrxName = Services.get(ITrxManager.class).createTrxName(getClass().getSimpleName() + "_run", false);
//				DB.saveConstraints();
//				try
//				{
//					DB.getConstraints().addAllowedTrxNamePrefix(localTrxName);
//					trx = Trx.get(localTrxName, true);
//					this.result = ProcessUtil.startJavaProcess(helper.getCtx(), pi, trx);
//				}
//				finally
//				{
//					DB.restoreConstraints();
//					assertFalse("Trx " + trx + " is not closed", trx.isActive());
//					trx.rollback();
//					trx.close();
//					trx = null;
//				}
//			}
//		}
//		else if (process.getAD_Workflow_ID() > 0)
//		{
//			final MWFProcess wfp = ProcessUtil.startWorkFlow(helper.getCtx(), pi, process.getAD_Workflow_ID());
//			assertNotNull("WF Process not created", wfp);
//			this.resultMsg = wfp.getProcessMsg();
//
//			final StateEngine wfStateEngine = wfp.getState();
//			assertNotNull("No state engine found for wf process: " + wfp, wfStateEngine);
//
//			final String wfState = wfStateEngine.getState();
//			assertEquals("WF state not completed: State=" + wfState + "(" + wfStateEngine.getStateInfo() + "), ProcessMsg=" + resultMsg,
//					expectedWFState, wfState);
//
//			this.result = true;
//		}
//		else
//		{
//			fail("Running process " + process + " is not supported");
//		}
//		if (expectedException == null)
//		{
//			assertTrue("Process " + svrProcess + " returned 'false' with message: " + pi.getSummary(), result);
//		}
//		else
//		{
//			assertFalse("Process " + svrProcess + " was expected to fail, but it suceeded with message: " + pi.getSummary(), result);
//			assertThat("Process " + svrProcess + " was expected to fail with an exception", 
//					pi.getThrowable(), instanceOf(expectedException));
//		}
//		
//		// Call afterRun listeners
//		for (IProcessHelperListener listener : listeners)
//		{
//			listener.afterRun(this);
//		}
	}

	public void run()
	{
		run(null);
	}

	private void createPInstance()
	{
		DB.saveConstraints();
		try
		{
			DB.getConstraints().setOnlyAllowedTrxNamePrefixes(false);

			this.pinstance = Services.get(IADPInstanceDAO.class).createAD_PInstance(helper.getCtx(), processId, tableId, recordId);

			//
			// Add parameters:
			int seqNo = 10;
			for (Map.Entry<String, Object> e : parameters.entrySet())
			{
				final String name = e.getKey();
				final Object value = e.getValue();
				MPInstancePara para = new MPInstancePara(pinstance, seqNo);
				if (value == null)
					; // skip
				else if (value instanceof Integer)
					para.setParameter(name, (Integer)value);
				else if (value instanceof BigDecimal)
					para.setParameter(name, (BigDecimal)value);
				else if (value instanceof Boolean)
					para.setParameter(name, (Boolean)value);
				else if (value instanceof Timestamp)
					para.setParameter(name, (Timestamp)value);
				else if (value instanceof String)
					para.setParameter(name, (String)value);
				else
					fail("Parameter type " + value.getClass() + " not supported for " + name + "=" + value);
				para.saveEx();
				seqNo += 10;
			}
		}
		finally
		{
			DB.restoreConstraints();
		}
	}

	public SvrProcess getSvrProcess()
	{
		return svrProcess;
	}

	public ProcessInfo getProcessInfo()
	{
		return pi;
	}

	public int[] getSelectionIds()
	{
		if (selection == null || selection.isEmpty())
			return new int[] {};
		int[] ids = new int[selection.size()];
		int i = 0;
		for (int id : selection)
			ids[i++] = id;
		return ids;
	}

	public ProcessHelper setExpectedProcessId(int processId)
	{
		this.expectedProcessId = processId;
		return this;
	}

	public ProcessHelper setExpectedClass(Class<? extends SvrProcess> clazz)
	{
		this.expectedClass = clazz;
		return this;
	}
	
	public ProcessHelper setExpectedWFState(String expectedWFState)
	{
		assertNotNull("expectedWFState is null", expectedWFState);
		this.expectedWFState = expectedWFState;
		return this;
	}
	
	public ProcessHelper addListener(IProcessHelperListener listener)
	{
		if (!listeners.contains(listener))
			listeners.add(listener);
		
		return this;
	}
	
	/**
	 * Sets the trxName to be used when running the process.
	 * @param trxName
	 * @return
	 */
	public ProcessHelper setTrxName(String trxName)
	{
		this.trxName = trxName;
		return this;
	}
	
	private Trx getTrx()
	{
		return trxName == null ? null : Trx.get(trxName, false);
	}
	
		public Class<? extends Exception> getExpectedException()
	{
		return expectedException;
	}

	/**
	 * Use this setter if the process run is expected to <b>fail</b> with a certain exception.
	 * 
	 * @param expectedException
	 * @return
	 */
	public ProcessHelper setExpectedException(Class<? extends Exception> expectedException)
	{
		this.expectedException = expectedException;
		return this;
	}
}
