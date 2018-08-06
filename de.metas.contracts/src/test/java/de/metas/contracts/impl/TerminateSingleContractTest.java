package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.contracts
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

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.model.C_Aggregation_Builder;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.aggregation.model.X_C_AggregationItem;
import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IContractChangeBL.ContractChangeParameters;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateBL.ContractExtendingRequest;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.impl.ContractsTestBase.FixedTimeSource;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.spi.impl.FlatrateTermInvoiceCandidateListener;
import de.metas.invoicecandidate.agg.key.impl.ICHeaderAggregationKeyBuilder_OLD;
import de.metas.invoicecandidate.agg.key.impl.ICLineAggregationKeyBuilder_OLD;
import de.metas.invoicecandidate.api.IAggregationDAO;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateListeners;
import de.metas.invoicecandidate.api.impl.PlainAggregationDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.invoicecandidate.spi.impl.OrderAndInOutInvoiceCandidateListener;
import de.metas.invoicecandidate.spi.impl.aggregator.standard.DefaultAggregator;
import lombok.NonNull;

public class TerminateSingleContractTest extends AbstractFlatrateTermTest
{
	final private IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);
	final private IContractsDAO contractsDAO = Services.get(IContractsDAO.class);
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	final private static Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");
	final private static FixedTimeSource today = new FixedTimeSource(2017, 11, 10);

	@Before
	public void before()
	{
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(C_Flatrate_Term.INSTANCE);

		final IInvoiceCandidateListeners invoiceCandidateListeners = Services.get(IInvoiceCandidateListeners.class);
		invoiceCandidateListeners.addListener(OrderAndInOutInvoiceCandidateListener.instance);
		invoiceCandidateListeners.addListener(FlatrateTermInvoiceCandidateListener.instance);

		final IAggregationFactory aggregationFactory = Services.get(IAggregationFactory.class);
		aggregationFactory.setDefaultAggregationKeyBuilder(I_C_Invoice_Candidate.class, X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header, ICHeaderAggregationKeyBuilder_OLD.instance);

		//
		// Setup Header & Line aggregation
		{
			// Header
			config_InvoiceCand_HeaderAggregation();
			Services.get(IAggregationFactory.class).setDefaultAggregationKeyBuilder(
					I_C_Invoice_Candidate.class,
					X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header,
					ICHeaderAggregationKeyBuilder_OLD.instance);

			// Line
			config_InvoiceCand_LineAggregation(helper.getCtx(), helper.getTrxName());
			Services.get(IAggregationFactory.class).setDefaultAggregationKeyBuilder(
					I_C_Invoice_Candidate.class,
					X_C_Aggregation.AGGREGATIONUSAGELEVEL_Line,
					ICLineAggregationKeyBuilder_OLD.instance);
		}

		SystemTime.setTimeSource(today);
	}

	@Test
	public void assertThrowingException_When_terminatingOneSingleContract_which_was_extended()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);

		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(1)
				.contract(contract)
				.forceExtend(true)
				.forceComplete(true)
				.nextTermStartDate(null)
				.build();

		Services.get(IFlatrateBL.class).extendContract(context);

		final I_C_Flatrate_Term extendedContract = contract.getC_FlatrateTerm_Next();
		assertThat(extendedContract).isNotNull();

		final ContractChangeParameters contractChangeParameters = ContractChangeParameters.builder()
				.changeDate(SystemTime.asDayTimestamp())
				.isCloseInvoiceCandidate(true)
				.action(IContractChangeBL.ChangeTerm_ACTION_VoidSingleContract)
				.build();

		assertThatThrownBy(() -> {
			contractChangeBL.cancelContract(contract, contractChangeParameters);
		}).hasMessageContaining(ContractChangeBL.MSG_IS_NOT_ALLOWED_TO_TERMINATE_CURRENT_CONTRACT);

	}

	@Test
	public void terminateOneSingleContract()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);
		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(1)
				.contract(contract)
				.forceExtend(true)
				.forceComplete(true)
				.nextTermStartDate(null)
				.build();

		Services.get(IFlatrateBL.class).extendContract(context);

		final I_C_Flatrate_Term extendedContract = contract.getC_FlatrateTerm_Next();
		assertThat(extendedContract).isNotNull();

		createInvoiceCandidates(extendedContract);

		// update invalids
		Services.get(IInvoiceCandBL.class).updateInvalid()
				.setContext(helper.getCtx(), helper.getTrxName())
				.setTaggedWithAnyTag()
				.update();

		final ContractChangeParameters contractChangeParameters = ContractChangeParameters.builder()
				.changeDate(SystemTime.asDayTimestamp())
				.isCloseInvoiceCandidate(true)
				.action(IContractChangeBL.ChangeTerm_ACTION_VoidSingleContract)
				.build();

		contractChangeBL.cancelContract(extendedContract, contractChangeParameters);

		// update invalids
		Services.get(IInvoiceCandBL.class).updateInvalid()
				.setContext(helper.getCtx(), helper.getTrxName())
				.setTaggedWithAnyTag()
				.update();

		assertVoidedFlatrateTerm(extendedContract);
		assertInvoiceCandidate(extendedContract);
		assertSubscriptionProgress(extendedContract, 0);
	}

	private void config_InvoiceCand_HeaderAggregation()
	{
		//@formatter:off
		new C_Aggregation_Builder()
			.setAD_Table_ID(I_C_Invoice_Candidate.Table_Name)
			.setIsDefault(true)
			.setAggregationUsageLevel(X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header)
			.setName("Default")
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_Bill_BPartner_ID)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_Bill_Location_ID)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_C_Currency_ID)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_AD_Org_ID)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_IsSOTrx)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_IsTaxIncluded)
				.end()
			.build();
		//@formatter:on
	}

	protected void config_InvoiceCand_LineAggregation(final Properties ctx, final String trxName)
	{
		final I_C_Invoice_Candidate_Agg defaultLineAgg = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate_Agg.class, trxName);
		defaultLineAgg.setAD_Org_ID(0);
		defaultLineAgg.setSeqNo(0);
		defaultLineAgg.setName("Default");
		defaultLineAgg.setClassname(DefaultAggregator.class.getName());
		defaultLineAgg.setIsActive(true);
		defaultLineAgg.setC_BPartner(null);
		defaultLineAgg.setM_ProductGroup(null);
		save(defaultLineAgg);

		final PlainAggregationDAO aggregationDAO = (PlainAggregationDAO)Services.get(IAggregationDAO.class);
		aggregationDAO.setDefaultAgg(defaultLineAgg);
	}

	private void assertVoidedFlatrateTerm(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		assertThat(flatrateTerm.getDocStatus()).isEqualTo(X_C_Flatrate_Term.DOCSTATUS_Closed);
		assertThat(flatrateTerm.getContractStatus()).isEqualTo(X_C_Flatrate_Term.CONTRACTSTATUS_Voided);
		assertThat(flatrateTerm.getMasterStartDate()).isNull();
		assertThat(flatrateTerm.getMasterEndDate()).isNull();
		assertThat(flatrateTerm.isAutoRenew()).isFalse();
		assertThat(flatrateTerm.getC_FlatrateTerm_Next()).isNull();
		assertThat(flatrateTerm.getAD_PInstance_EndOfTerm()).isNull();

		final I_C_Flatrate_Term ancestor = Services.get(IFlatrateDAO.class).retrieveAncestorFlatrateTerm(flatrateTerm);

		assertThat(ancestor).isNull();
	}

	private void assertInvoiceCandidate(final I_C_Flatrate_Term flatrateTerm)
	{
		final List<I_C_Invoice_Candidate> candsForTerm = invoiceCandDAO.retrieveReferencing(flatrateTerm);
		assertThat(candsForTerm).hasSize(1);
		final I_C_Invoice_Candidate invoiceCandidate = candsForTerm.get(0);
		assertThat(invoiceCandidate.getQtyInvoiced()).isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(invoiceCandidate.getProcessed_Override()).isEqualToIgnoringCase("Y");
	}

	private void assertSubscriptionProgress(@NonNull final I_C_Flatrate_Term flatrateTerm, final int expected)
	{
		final List<I_C_SubscriptionProgress> subscriptionProgress = contractsDAO.getSubscriptionProgress(flatrateTerm);
		assertThat(subscriptionProgress).hasSize(expected);

		subscriptionProgress.stream()
				.filter(progress -> progress.getEventDate().before(flatrateTerm.getMasterEndDate()))
				.peek(progress -> assertThat(progress.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Quit))
				.filter(progress -> progress.getEventDate().after(flatrateTerm.getMasterEndDate()))
				.peek(progress -> assertThat(progress.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Running));
	}

}
