package de.metas.impexp.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_ImpFormat;
import org.compiere.model.I_AD_ImpFormat_Row;

import de.metas.impexp.format.ImpFormatId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Copy Import Format Rows
 */
public class AD_ImpFormat_CopyLinesFrom extends JavaProcess
{
	private ImpFormatId from_AD_ImpFormat_ID;
	private ImpFormatId to_AD_ImpFormat_ID;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParameters())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{

			}
			else if ("AD_ImpFormat_ID".equals(name))
			{
				from_AD_ImpFormat_ID = ImpFormatId.ofRepoId(para.getParameterAsInt());
			}
		}
		to_AD_ImpFormat_ID = ImpFormatId.ofRepoId(getRecord_ID());
	}	// prepare

	@Override
	protected String doIt()
	{
		if (from_AD_ImpFormat_ID.equals(to_AD_ImpFormat_ID))
		{
			throw new AdempiereException("Copying into same import format is not allowed");
		}

		final I_AD_ImpFormat from = retrieveImpFormatRecord(from_AD_ImpFormat_ID);
		final I_AD_ImpFormat target = retrieveImpFormatRecord(to_AD_ImpFormat_ID);
		if (from.getAD_Table_ID() != target.getAD_Table_ID())
		{
			throw new AdempiereException("From-To do Not have same Format Table");
		}

		final ClientId targetClientId = ClientId.ofRepoId(target.getAD_Client_ID());
		if (!targetClientId.equals(getClientId()))
		{
			throw new AdempiereException("No permissions");
		}

		final List<I_AD_ImpFormat_Row> fromRows = retrieveRows(from_AD_ImpFormat_ID);
		for (final I_AD_ImpFormat_Row row : fromRows)
		{
			copyRow(row, target);
		}

		final String msg = "#" + fromRows.size();
		if (!from.getFormatType().equals(target.getFormatType()))
		{
			return msg + " - Note: Format Type different!";
		}
		return msg;
	}	// doIt

	private I_AD_ImpFormat retrieveImpFormatRecord(@NonNull final ImpFormatId id)
	{
		final I_AD_ImpFormat from = load(id, I_AD_ImpFormat.class);
		Check.assumeNotNull(from, "Parameter from is not null");
		return from;
	}

	private List<I_AD_ImpFormat_Row> retrieveRows(@NonNull final ImpFormatId impFormatId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_ImpFormat_Row.class)
				.addEqualsFilter(I_AD_ImpFormat_Row.COLUMN_AD_ImpFormat_ID, impFormatId)
				// .addOnlyActiveRecordsFilter() // ALL!
				.orderBy(I_AD_ImpFormat_Row.COLUMN_SeqNo)
				.create()
				.list();
	}

	private void copyRow(final I_AD_ImpFormat_Row fromRow, final I_AD_ImpFormat target)
	{
		final I_AD_ImpFormat_Row toRow = newInstance(I_AD_ImpFormat_Row.class);
		InterfaceWrapperHelper.copyValues(fromRow, toRow);
		toRow.setAD_Org_ID(fromRow.getAD_Org_ID()); // usually "*"
		toRow.setAD_ImpFormat_ID(target.getAD_ImpFormat_ID());
		saveRecord(toRow);
	}
}
