package de.metas.printing.process;

import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.Query;
import org.compiere.util.DB;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;

/**
 * Re-enqueue {@link I_C_Printing_Queue} items.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/04468_Belege_erneut_in_Druckerwarteschlange_%282013062710000119%29
 */
public class C_Printing_Queue_ReEnqueue extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public static final String PARAM_IsSelected = "IsSelected";
	private boolean p_IsSelected = false;

	public static final String PARAM_IsPrinted = "IsPrinted";
	private Boolean p_IsPrinted = null;

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

			if (PARAM_IsSelected.equals(name))
			{
				p_IsSelected = para.getParameterAsBoolean();
			}
			if (PARAM_IsPrinted.equals(name))
			{
				p_IsPrinted = para.getParameterAsBooleanOrNull();
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

					Services.get(IPrintingQueueBL.class).renqueue(item, p_IsRecreatePrintout);

					countOk++;
				}
				catch (Exception e)
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
		final Properties ctx = getCtx();

		final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
		final IPrintingQueueQuery queueQuery = printingQueueBL.createPrintingQueueQuery();

		queueQuery.setApplyAccessFilterRW(true);

		if (p_IsSelected)
		{
			final int selectionId = getAD_PInstance_ID();
			createWindowSelectionId(selectionId);
			queueQuery.setOnlyAD_PInstance_ID(selectionId);
		}

		if (p_IsPrinted != null)
		{
			queueQuery.setIsPrinted(p_IsPrinted);
		}

		queueQuery.setModelTableId(p_AD_Table_ID);
		queueQuery.setModelFromRecordId(p_Record_ID);
		queueQuery.setModelToRecordId(p_Record_ID_To);

		if (!Check.isEmpty(p_WhereClause, true))
		{
			final ISqlQueryFilter modelFilter = TypedSqlQueryFilter.<Object> of(p_WhereClause);
			queueQuery.setModelFilter(modelFilter);
		}

		log.info("Queue query: {}", queueQuery);

		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
		final IQuery<I_C_Printing_Queue> query = printingDAO.createQuery(ctx, queueQuery, ITrx.TRXNAME_None);
		query.setOption(Query.OPTION_IteratorBufferSize, null); // use standard IteratorBufferSize

		if (log.isInfoEnabled())
		{
			log.info("SQL Query: {}", query);
			log.info("SQL Query count: {}", query.count());
		}

		final Iterator<I_C_Printing_Queue> it = query.iterate(I_C_Printing_Queue.class);
		return it;
	}

	private int createWindowSelectionId(int selectionId)
	{
		final IQueryFilter<I_C_Printing_Queue> processQueryFilter = getProcessInfo().getQueryFilter();

		final IQuery<I_C_Printing_Queue> query = queryBL
				.createQueryBuilder(I_C_Printing_Queue.class, getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.filter(processQueryFilter)
				.create()
				.setApplyAccessFilterRW(true)
				.setClient_ID();

		final int count = query.createSelection(selectionId);

		log.info("Window Selection Query: {}", query);
		log.info("Window Selection Count: {}", count);

		return count;
	}

}
