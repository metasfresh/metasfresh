/**
 *
 */
package de.metas.invoicecandidate.process;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import de.metas.adempiere.form.IClientUI;
import de.metas.forex.ForexContractService;
import de.metas.forex.process.utils.ForexContractParameters;
import de.metas.forex.process.utils.ForexContracts;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueuer;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.payment.paymentterm.BaseLineType;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.impl.PaymentTerm;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.NestedParams;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult.ShowProcessLogs;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.ProcessPreconditionsResolution.ProcessCaptionMapper;
import de.metas.process.RunOutOfTrx;
import de.metas.security.permissions.Access;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.compiere.util.DB;
import org.compiere.util.Ini;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.Supplier;

public class C_Invoice_Candidate_EnqueueSelectionForInvoicing extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider, IProcessParametersCallout
{
	private static final String MSG_InvoiceCandidate_PerformEnqueuing = "C_InvoiceCandidate_PerformEnqueuing";

	//
	// Services
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final C_Invoice_Candidate_ProcessCaptionMapperHelper processCaptionMapperHelper = SpringContextHolder.instance.getBean(C_Invoice_Candidate_ProcessCaptionMapperHelper.class);
	private final ForexContractService forexContractService = SpringContextHolder.instance.getBean(ForexContractService.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final LookupDataSource forexContractLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_ForeignExchangeContract.Table_Name);
	private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@NestedParams
	private final ForexContractParameters p_FECParams = ForexContractParameters.newInstance();

	private final Supplier<ForexContracts> forexContractsSupplier = Suppliers.memoize(this::retrieveContracts);

	//
	//
	private BigDecimal totalNetAmtToInvoiceChecksum;

	private int selectionCount = 0;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final IQueryFilter<I_C_Invoice_Candidate> selectionFilter = context.getQueryFilter(I_C_Invoice_Candidate.class);

		return ProcessPreconditionsResolution.accept()
				.withCaptionMapper(processCaptionMapper(selectionFilter));
	}

	@Nullable
	private ForexContracts getContracts()
	{
		return forexContractsSupplier.get();
	}

	@Nullable
	private ForexContracts retrieveContracts()
	{
		final OrderId singleOrderId = getSingleOrderId();
		if (singleOrderId == null)
		{
			return null;
		}

		return ForexContracts.builder()
				.orderCurrencyId(orderBL.getCurrencyId(singleOrderId))
				.forexContracts(forexContractService.getContractsByOrderId(singleOrderId))
				.build();
	}

	@Nullable
	private OrderId getSingleOrderId()
	{
		final ImmutableList<OrderId> firstOrderIds = createICQueryBuilder()
				.addNotNull(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID)
				.create()
				.setLimit(QueryLimit.TWO)
				.listDistinct(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, OrderId.class);
		return firstOrderIds.size() == 1 ? firstOrderIds.get(0) : null;
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (InvoicingParams.PARA_OverrideDueDate.contentEquals(parameter.getColumnName()))
		{
			return computeOverrideDueDate(getInvoicingParams().getDateInvoiced());
		}

		return p_FECParams.getParameterDefaultValue(parameter.getColumnName(), getContracts());
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (InvoicingParams.PARA_DateInvoiced.contentEquals(parameterName))
		{
			getInvoicingParams().updateOnDateInvoicedParameterChanged(computeOverrideDueDate(getInvoicingParams().getDateInvoiced()));
		}

		p_FECParams.updateOnParameterChanged(parameterName, getContracts());
	}

	@ProcessParamLookupValuesProvider(parameterName = ForexContractParameters.PARAM_C_ForeignExchangeContract_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	public LookupValuesList getAvailableForexContracts()
	{
		final ForexContracts contracts = getContracts();
		return contracts != null
				? forexContractLookup.findByIdsOrdered(contracts.getForexContractIds())
				: LookupValuesList.EMPTY;
	}

	@Override
	@RunOutOfTrx
	protected void prepare()
	{
		// Display process logs only if the process failed.
		// NOTE: we do that because this process is called from window Gear and user shall only see how many ICs where enqueued, in status line,
		// and no popup shall be displayed.
		setShowProcessLogs(ShowProcessLogs.OnError);

		//
		// Create and check invoice candidate selection
		selectionCount = createSelection();
		if (selectionCount <= 0)
		{
			throw new AdempiereException(IInvoiceCandidateEnqueuer.MSG_INVOICE_GENERATE_NO_CANDIDATES_SELECTED_0P);
		}

		//
		// Ask user if we shall enqueue the invoice candidates.
		checkPerformEnqueuing();
	}

	/**
	 * Before enqueuing the candidates, check how many partners they have.
	 * In case there are more that one partner, ask the user if he really wants to invoice for so many partners.
	 *
	 * @throws ProcessCanceledException if user canceled
	 * @implSpec task 08961
	 */
	private void checkPerformEnqueuing() throws ProcessCanceledException
	{
		final int bpartnerCount = countBPartners();
		if (bpartnerCount <= 1)
		{
			return;
		}
		final boolean performEnqueuing;
		if (Ini.isSwingClient())
		{
			performEnqueuing = Services.get(IClientUI.class).ask()
					.setParentWindowNo(getProcessInfo().getWindowNo())
					.setAD_Message(MSG_InvoiceCandidate_PerformEnqueuing, selectionCount, bpartnerCount)
					.setDefaultAnswer(false)
					.getAnswer();
		}
		else
		{
			performEnqueuing = true;
		}

		// if the enqueuing was not accepted by the user, do nothing
		if (!performEnqueuing)
		{
			throw new ProcessCanceledException();
		}
	}

	private InvoicingParams getInvoicingParams() {return InvoicingParams.ofParams(getParameterAsIParams());}

	@Override
	protected String doIt()
	{
		final PInstanceId pinstanceId = getPinstanceId();

		final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL.enqueueForInvoicing()
				.setContext(getCtx())
				.setInvoicingParams(getInvoicingParams())
				.setFailIfNothingEnqueued(true) // If no workpackages were created, display error message that no selection was made (07666)
				.setTotalNetAmtToInvoiceChecksum(totalNetAmtToInvoiceChecksum)
				// .setFailOnChanges(true) // NOTE: use the standard settings (which will fallback on SysConfig)
				//
				.enqueueSelection(pinstanceId);

		return enqueueResult.getSummaryTranslated(getCtx());
	}

	/**
	 * @return count of selected items
	 */
	private int createSelection()
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = createICQueryBuilder();

		//
		// Create selection and return how many items were added
		final PInstanceId adPInstanceId = getPinstanceId();
		Check.assumeNotNull(adPInstanceId, "adPInstanceId is not null");

		DB.deleteT_Selection(adPInstanceId, ITrx.TRXNAME_ThreadInherited);

		return queryBuilder
				.create()
				.setRequiredAccess(Access.READ) // 04471: enqueue only those records on which user has access to
				.createSelection(adPInstanceId);
	}

	private int countBPartners()
	{
		return createICQueryBuilder()
				.create()
				.listDistinct(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID)
				.size();

	}

	private IQueryBuilder<I_C_Invoice_Candidate> createICQueryBuilder()
	{
		// Get the user selection filter (i.e. what user filtered in his window)
		final IQueryFilter<I_C_Invoice_Candidate> userSelectionFilter;
		if (Ini.isSwingClient())
		{
			// In case of Swing, preserve the old functionality, i.e. if no where clause then select all
			userSelectionFilter = getProcessInfo().getQueryFilterOrElseFalse();
		}
		else
		{
			userSelectionFilter = getProcessInfo().getQueryFilterOrElse(null);
			if (userSelectionFilter == null)
			{
				throw new AdempiereException("@NoSelection@");
			}
		}

		final InvoicingParams invoicingParams = getInvoicingParams();

		return createICQueryBuilder(userSelectionFilter, invoicingParams.isOnlyApprovedForInvoicing());
	}

	private IQueryBuilder<I_C_Invoice_Candidate> createICQueryBuilder(
			final IQueryFilter<I_C_Invoice_Candidate> userSelectionFilter,
			final boolean onlyApprovedForInvoicing)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class, getCtx(), ITrx.TRXNAME_None)
				.filter(userSelectionFilter)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				//
				// NOTE: we are not filtering by IsToClear, Processed etc
				// because we want to allow the enqueuer do do that
				// because enqueuer will also log an message about why it was excluded (=> transparant for user)
				// .addEqualsFilter(I_C_Invoice_Candidate.COLUMN_IsToClear, false)
				// .addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false) not filtering by processed, because the IC might be invalid (08343)
				// .addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsError, false)
				// .addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsInDispute, false)
				//
				;

		//
		// Consider only approved invoices (if we were asked to do so)
		if (onlyApprovedForInvoicing)
		{
			queryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing, true);
		}

		return queryBuilder;
	}

	@Nullable
	private ProcessCaptionMapper processCaptionMapper(final IQueryFilter<I_C_Invoice_Candidate> selectionFilter)
	{
		final IQuery<I_C_Invoice_Candidate> query = prepareNetAmountsToInvoiceForSelectionQuery(selectionFilter);
		return processCaptionMapperHelper.getProcessCaptionMapperForNetAmountsFromQuery(query);
	}

	private IQuery<I_C_Invoice_Candidate> prepareNetAmountsToInvoiceForSelectionQuery(final IQueryFilter<I_C_Invoice_Candidate> selectionFilter)
	{
		return createICQueryBuilder(selectionFilter, false)
				.addNotNull(I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID)
				.create();
	}

	private LocalDate computeOverrideDueDate(final @Nullable LocalDate manualDateInvoicedParam)
	{
		if (countPaymentTerms() != 1)
			return null;

		final I_C_Invoice_Candidate ic = createICQueryBuilder()
				.setLimit(QueryLimit.ONE)
				.create()
				.firstNotNull(I_C_Invoice_Candidate.class);

		final PaymentTermId paymentTermId = invoiceCandBL.getPaymentTermId(ic);
		final PaymentTerm paymentTerm = paymentTermRepository.getById(paymentTermId);
		final Timestamp baseLineDate;
		if (manualDateInvoicedParam != null && paymentTerm.isBaseLineTypeInvoiceDate())
		{
			baseLineDate = TimeUtil.asTimestamp(manualDateInvoicedParam);
		}
		else
		{
			baseLineDate = retrieveEarliestBaseLineDate(paymentTerm);
			if (baseLineDate == null)
			{
				return null;
			}
		}

		final Timestamp dueDate = paymentTerm.computeDueDate(baseLineDate);
		final ZoneId zoneId = orgDAO.getTimeZone(paymentTerm.getOrgId());

		return TimeUtil.asLocalDate(dueDate, zoneId);
	}

	private int countPaymentTerms()
	{
		return createICQueryBuilder()
				.create()
				.listDistinct(I_C_Invoice_Candidate.COLUMNNAME_C_PaymentTerm_ID)
				.size();

	}

	private Timestamp retrieveEarliestBaseLineDate(@NonNull final PaymentTerm paymentTerm)
	{
		final BaseLineType baseLineType = paymentTerm.getBaseLineType();

		final Timestamp earliestDate;

		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = createICQueryBuilder();

		switch (baseLineType)
		{

			case AfterDelivery:
				earliestDate = icQueryBuilder
						.orderBy(I_C_Invoice_Candidate.COLUMNNAME_DeliveryDate)
						.create()
						.firstNotNull(I_C_Invoice_Candidate.class)
						.getDeliveryDate();
				break;
			case AfterBillOfLanding:
				earliestDate = icQueryBuilder
						.orderBy(I_C_Invoice_Candidate.COLUMNNAME_ActualLoadingDate)
						.create()
						.firstNotNull(I_C_Invoice_Candidate.class)
						.getActualLoadingDate();
				break;
			case InvoiceDate:

				final I_C_Invoice_Candidate ic = icQueryBuilder
						.orderBy()
						.addColumn(I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Override)
						.addColumn(I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice)
						.endOrderBy()
						.create()
						.firstNotNull(I_C_Invoice_Candidate.class);

				earliestDate = invoiceCandBL.getDateToInvoiceTS(ic);
				break;
			default:
				throw new AdempiereException("Unknown base line type for payment term " + paymentTerm);
		}

		return earliestDate;
	}
}
