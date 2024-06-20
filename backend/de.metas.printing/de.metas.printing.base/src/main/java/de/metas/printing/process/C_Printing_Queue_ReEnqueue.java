package de.metas.printing.process;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfoParameter;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.IQuery;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.lang.Nullable;

import java.util.Iterator;

/**
 * Re-enqueue {@link I_C_Printing_Queue} items.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/04468_Belege_erneut_in_Druckerwarteschlange_%282013062710000119%29
 */
public class C_Printing_Queue_ReEnqueue extends JavaProcess
{

	private static final Logger logger = LogManager.getLogger(C_Printing_Queue_ReEnqueue.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
	private final IPrintingQueueQuery queueQuery = printingQueueBL.createPrintingQueueQuery();
	private final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

	public static final String PARAM_FilterBySelectedQueueItems = "FilterBySelectedQueueItems";
	private boolean p_FilterBySelectedQueueItems = false;

	public static final String PARAM_FilterByProcessedQueueItems = "FilterByProcessedQueueItems";

	@Nullable
	private Boolean p_FilterByProcessedQueueItems = null;

	public static final String PARAM_AD_Table_ID = "AD_Table_ID";
	private int p_AD_Table_ID = -1;

	public static final String PARAM_Record_ID = "Record_ID";
	private int p_Record_ID = -1;
	private int p_Record_ID_To = -1;

	public static final String PARAM_WhereClause = "WhereClause";
	private String p_WhereClause = null;

	public static final String PARAM_IsRecreatePrintout = "IsRecreatePrintout";
	private boolean p_IsRecreatePrintout = true;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				continue;
			}

			if (PARAM_FilterBySelectedQueueItems.equals(name))
			{
				p_FilterBySelectedQueueItems = para.getParameterAsBoolean();
			}
			if (PARAM_FilterByProcessedQueueItems.equals(name))
			{
				p_FilterByProcessedQueueItems = para.getParameterAsBooleanOrNull();
			}
			else if (PARAM_AD_Table_ID.equals(name))
			{
				p_AD_Table_ID = para.getParameterAsInt();
			}
			else if (PARAM_Record_ID.equals(name))
			{
				p_Record_ID = para.getParameterAsInt();
				p_Record_ID_To = para.getParameter_ToAsInt();
			}
			else if (PARAM_WhereClause.equals(name))
			{
				p_WhereClause = (String)para.getParameter();
			}
			else if (PARAM_IsRecreatePrintout.equals(name))
			{
				p_IsRecreatePrintout = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		int countAll = 0;
		int countOk = 0;
		int countError = 0;

		final Iterator<I_C_Printing_Queue> queueItems = retrievePrintingQueueItems();

		DB.saveConstraints();
		try
		{
			DB.getConstraints().addAllowedTrxNamePrefix("POSave");

			while (queueItems.hasNext())
			{
				final I_C_Printing_Queue item = queueItems.next();

				try
				{
					countAll++;
					printingQueueBL.renqueue(item, p_IsRecreatePrintout);
					countOk++;
				}
				catch (final Exception e)
				{
					countError++;
					log.warn(e.getLocalizedMessage(), e);
					addLog("Error on @C_Printing_Queue@#" + item.getC_Printing_Queue_ID() + ": " + e.getLocalizedMessage());
				}
			}
		}
		finally
		{
			DB.restoreConstraints();
		}

		return "@IsPrinted@ (All/OK/Error): " + countAll + "/" + countOk + "/" + countError;
	}

	private Iterator<I_C_Printing_Queue> retrievePrintingQueueItems()
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
		queueQuery.setRequiredAccess(Access.WRITE);

		if (p_FilterBySelectedQueueItems)
		{
			final PInstanceId selectionId = getPinstanceId();
			createWindowSelectionId(selectionId);
			queueQuery.setOnlyAD_PInstance_ID(selectionId);
		}

		if (p_FilterByProcessedQueueItems != null)
		{
			queueQuery.setFilterByProcessedQueueItems(p_FilterByProcessedQueueItems);
		}

		queueQuery.setModelTableId(p_AD_Table_ID);
		queueQuery.setModelFromRecordId(p_Record_ID);
		queueQuery.setModelToRecordId(p_Record_ID_To);

		if (!Check.isEmpty(p_WhereClause, true))
		{
			final ISqlQueryFilter modelFilter = TypedSqlQueryFilter.of(p_WhereClause);
			queueQuery.setModelFilter(modelFilter);
		}

		loggable.addLog("Queue query: {}", queueQuery);

		final IQuery<I_C_Printing_Queue> query = printingDAO.createQuery(getCtx(), queueQuery, ITrx.TRXNAME_None);
		query.setOption(IQuery.OPTION_IteratorBufferSize, null); // use standard IteratorBufferSize

		if (log.isInfoEnabled())
		{
			loggable.addLog("The query matches {} C_Printing_Queue records; query={}",query.count(), query);
		}

		return query.iterate(I_C_Printing_Queue.class);
	}

	private int createWindowSelectionId(final PInstanceId selectionId)
	{
		final IQueryFilter<I_C_Printing_Queue> processQueryFilter = getProcessInfo().getQueryFilterOrElseFalse();

		final IQuery<I_C_Printing_Queue> query = queryBL
				.createQueryBuilder(I_C_Printing_Queue.class, getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.filter(processQueryFilter)
				.create()
				.setRequiredAccess(Access.WRITE)
				.setClient_ID();

		final int count = query.createSelection(selectionId);
		Loggables.withLogger(logger, Level.DEBUG).addLog("Restricting C_Printing_Queue records to selection of {} items; query={}", count, query);
		return count;
	}

}
