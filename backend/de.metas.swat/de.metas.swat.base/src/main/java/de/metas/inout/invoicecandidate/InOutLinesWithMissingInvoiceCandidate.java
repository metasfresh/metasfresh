package de.metas.inout.invoicecandidate;

import de.metas.document.engine.IDocument;
import de.metas.interfaces.I_C_DocType;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.QueryLimit;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * DAO to support {@link M_InOut_Handler} and {@link M_InOutLine_Handler}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class InOutLinesWithMissingInvoiceCandidate
{
	private final List<IQueryFilter<I_M_InOutLine>> additionalFilters = new ArrayList<>();

	/**
	 * Add additional filters to allow other modules restricting the set of order lines for which the system automatically creates invoice candidates.
	 */
	public void addAdditionalFilter(IQueryFilter<I_M_InOutLine> filter)
	{
		additionalFilters.add(filter);
	}

	/**
	 * Get all {@link I_M_InOutLine}s which are not linked to an {@link I_C_OrderLine} and there is no invoice candidate already generated for them.
	 * 
	 * NOTE: this method will be used to identify those inout lines for which {@link M_InOutLine_Handler} will generate invoice candidates.
	 */
	public Iterator<I_M_InOutLine> retrieveLinesThatNeedAnInvoiceCandidate(@NonNull final QueryLimit limit)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_M_InOutLine> filters = queryBL.createCompositeQueryFilter(I_M_InOutLine.class);
		filters.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID, null);
		filters.addEqualsFilter(I_M_InOutLine.COLUMNNAME_Processed, true); // also processing e.g. closed InOuts
		filters.addEqualsFilter(de.metas.invoicecandidate.model.I_M_InOutLine.COLUMNNAME_IsInvoiceCandidate, false); // which don't have invoice candidates already generated
		filters.addOnlyActiveRecordsFilter();

		//
		// Filter M_InOut
		{
			final IQueryBuilder<I_M_InOut> inoutQueryBuilder = queryBL.createQueryBuilder(I_M_InOut.class);

			// if the inout was reversed, and there is no IC yet, don't bother creating one
			inoutQueryBuilder.addNotEqualsFilter(I_M_InOut.COLUMNNAME_DocStatus, IDocument.STATUS_Reversed);

			// Exclude some DocTypes
			{
				final IQuery<I_C_DocType> validDocTypesQuery = queryBL.createQueryBuilder(I_C_DocType.class)
						// Don't create InvoiceCandidates for DocSubType Saldokorrektur (FRESH-454)
						.addNotEqualsFilter(I_C_DocType.COLUMNNAME_DocSubType, I_C_DocType.DOCSUBTYPE_InOutAmountCorrection)
						//
						.create();
				inoutQueryBuilder.addInSubQueryFilter(I_M_InOut.COLUMNNAME_C_DocType_ID, I_C_DocType.COLUMNNAME_C_DocType_ID, validDocTypesQuery);
			}

			filters.addInSubQueryFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, I_M_InOut.COLUMNNAME_M_InOut_ID, inoutQueryBuilder.create());
		}

		filters.addFilters(additionalFilters);
		
		final IQueryBuilder<I_M_InOutLine> queryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class)
				.filter(filters)
				.filterByClientId();
		queryBuilder.orderBy()
				.addColumn(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);
		queryBuilder.setLimit(limit);

		return queryBuilder.create()
				.iterate(I_M_InOutLine.class);
	}

}
