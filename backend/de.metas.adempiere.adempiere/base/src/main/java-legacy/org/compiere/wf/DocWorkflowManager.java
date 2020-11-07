package org.compiere.wf;

import de.metas.document.workflow.WorkflowExecutor;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.Workflow;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.compiere.model.DocWorkflowMgr;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Evaluator;
import de.metas.workflow.service.IADWorkflowDAO;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Document Workflow Manager
 *
 * @author Jorg Janke
 * @version $Id: DocWorkflowManager.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
@ToString
public class DocWorkflowManager implements DocWorkflowMgr
{
	public static DocWorkflowManager get()
	{
		return instance;
	}

	private static final Logger log = LogManager.getLogger(DocWorkflowManager.class);
	private static final DocWorkflowManager instance = new DocWorkflowManager();

	private final AtomicInteger countCalled = new AtomicInteger(0);
	private final AtomicInteger countStarted = new AtomicInteger(0);

	private DocWorkflowManager()
	{
	}

	@Override
	public void fireDocValueWorkflows(final PO document)
	{
		countCalled.incrementAndGet();

		final ClientId clientId = ClientId.ofRepoId(document.getAD_Client_ID());
		final AdTableId adTableId = AdTableId.ofRepoId(document.get_Table_ID());
		final Collection<Workflow> workflows = Services.get(IADWorkflowDAO.class).getDocValueWorkflows(clientId, adTableId);
		if (workflows.isEmpty())
		{
			return;
		}

		for (final Workflow workflow : workflows)
		{
			//	We have a Document Workflow
			final String logic = workflow.getDocValueWorkflowTriggerLogic();
			if (Check.isBlank(logic))
			{
				log.error("Workflow has no Logic - {}", workflow);
				continue;
			}

			//	Re-check: Document must be same Client as workflow
			if (workflow.getClientId().getRepoId() != document.getAD_Client_ID())
			{
				continue;
			}

			//	Check Logic
			final boolean sql = logic.startsWith("SQL=");
			if (sql && !isSQLStartLogicMatches(workflow, document))
			{
				log.debug("SQL Logic evaluated to false ({})", logic);
				continue;
			}
			if (!sql && !Evaluator.evaluateLogic(document, logic))
			{
				log.debug("Logic evaluated to false ({})", logic);
				continue;
			}

			startWorkflow(workflow, document);
		}
	}    //	process

	private void startWorkflow(
			@NonNull final Workflow workflow,
			@NonNull final PO document)
	{
		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(document.getCtx())
				.setAD_Process_ID(305) // FIXME HARDCODED
				.setAD_Client_ID(document.getAD_Client_ID())
				.setTitle(workflow.getName().getDefaultValue())
				.setRecord(document.get_Table_ID(), document.get_ID())
				.build();

		final MWFProcess wfProcess = WorkflowExecutor.builder()
				.workflow(workflow)
				.processInfo(pi)
				.build()
				.start();
		final boolean started = wfProcess != null;
		if (started)
		{
			log.info("Workflow {} started for {}", workflow, document);
			countStarted.incrementAndGet();
		}
	}

	/**
	 * Test Start condition
	 *
	 * @return true if workflow should be started
	 */
	private boolean isSQLStartLogicMatches(final Workflow workflow, final PO document)
	{
		String logic = workflow.getDocValueWorkflowTriggerLogic();
		logic = logic.substring(4);        //	"SQL="
		//
		final String tableName = document.get_TableName();
		final String[] keyColumns = document.get_KeyColumns();
		if (keyColumns.length != 1)
		{
			log.error("Tables with more then one key column not supported - " + tableName + " = " + keyColumns.length);
			return false;
		}

		final String keyColumn = keyColumns[0];
		final StringBuilder sql = new StringBuilder("SELECT ")
				.append(keyColumn).append(" FROM ").append(tableName)
				.append(" WHERE AD_Client_ID=? AND ")        //	#1
				.append(keyColumn).append("=? AND ")    //	#2
				.append(logic)
				//	Duplicate Open Workflow test
				.append(" AND NOT EXISTS (SELECT 1 FROM AD_WF_Process wfp ")
				.append("WHERE wfp.AD_Table_ID=? AND wfp.Record_ID=")    //	#3
				.append(tableName).append(".").append(keyColumn)
				.append(" AND wfp.AD_Workflow_ID=?")    //	#4
				.append(" AND SUBSTR(wfp.WFState,1,1)='O')");
		final Object[] sqlParams = new Object[] {
				workflow.getClientId(),
				document.get_ID(),
				document.get_Table_ID(),
				workflow.getId()
		};
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), document.get_TrxName());
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			return rs.next();
		}
		catch (final Exception ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
