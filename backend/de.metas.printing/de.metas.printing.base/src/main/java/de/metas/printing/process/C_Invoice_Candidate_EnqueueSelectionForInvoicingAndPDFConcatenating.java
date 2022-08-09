/**
 *
 */
package de.metas.printing.process;

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

import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueuer;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.api.impl.InvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.organization.OrgId;
import de.metas.printing.async.spi.impl.InvoiceEnqueueingWorkpackageProcessor;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductCategoryId;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IParams;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.DB;

import java.util.Properties;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_InvoiceCandidate_Processing;

public class C_Invoice_Candidate_EnqueueSelectionForInvoicingAndPDFConcatenating extends JavaProcess
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);
	//
	// Services
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	// Parameters
	private IInvoicingParams invoicingParams;

	@Param(parameterName = I_C_Invoice_Candidate.COLUMNNAME_AD_Org_ID, mandatory = true)
	private OrgId p_OrgId;

	@Param(parameterName = I_M_Product.COLUMNNAME_M_Product_Category_ID, mandatory = true)
	private ProductCategoryId p_ProductCategoryId;

	@Override
	@RunOutOfTrx
	protected void prepare()
	{
		final IParams params = getParameterAsIParams();
		this.invoicingParams = new InvoicingParams(params);

		int selectionCount = createSelection();

		if (selectionCount <= 0)
		{
			final Properties ctx = getCtx();
			throw new AdempiereException(msgBL.getMsg(ctx, IInvoiceCandidateEnqueuer.MSG_INVOICE_GENERATE_NO_CANDIDATES_SELECTED_0P));
		}

	}

	private AsyncBatchId createAsyncBatch()
	{
		// Create Async Batch for tracking
		final I_C_Async_Batch asyncBatch = asyncBatchBL.newAsyncBatch()
				.setContext(getCtx())
				.setAD_PInstance_Creator_ID(getPinstanceId())
				.setOrgId(p_OrgId)
				.setC_Async_Batch_Type(C_Async_Batch_InternalName_InvoiceCandidate_Processing)
				.setName(C_Async_Batch_InternalName_InvoiceCandidate_Processing)
				.build();

		return AsyncBatchId.ofRepoId(asyncBatch.getC_Async_Batch_ID());
	}

	@Override
	protected String doIt() throws Exception
	{
		final AsyncBatchId asyncBatchId = createAsyncBatch();
		final I_C_Async_Batch asyncBatch = asyncBatchBL.getAsyncBatchById(asyncBatchId);
		asyncBatchDAO.setPInstance_IDAndSave(asyncBatch, getPinstanceId());

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), InvoiceEnqueueingWorkpackageProcessor.class);
		queue
				.newWorkPackage()
				.setC_Async_Batch(asyncBatchBL.getAsyncBatchById(asyncBatchId))
				.parameters(invoicingParams.asMap())
				.buildAndEnqueue();

		return MSG_OK;
	}

	private int createSelection()
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = createICQueryBuilder();

		//
		// Create selection and return how many items were added
		final PInstanceId adPInstanceId = getPinstanceId();
		Check.assumeNotNull(adPInstanceId, "adPInstanceId is not null");

		DB.deleteT_Selection(adPInstanceId, ITrx.TRXNAME_ThreadInherited);

		final int selectionCount = queryBuilder
				.create()
				.setRequiredAccess(Access.READ)
				.createSelection(adPInstanceId);

		return selectionCount;
	}

	private IQueryBuilder<I_C_Invoice_Candidate> createICQueryBuilder()
	{
		final IQuery<I_M_Product_Category> subQuery_ProductcategFilter = queryBL
				.createQueryBuilder(I_M_Product_Category.class)
				.addEqualsFilter(I_M_Product_Category.COLUMNNAME_M_Product_Category_ID, p_ProductCategoryId)
				.setJoinOr()
				.addEqualsFilter(I_M_Product_Category.COLUMNNAME_M_Product_Category_Parent_ID, p_ProductCategoryId)
				.create();

		final IQuery<I_M_Product> subQuery_Product = queryBL
				.createQueryBuilder(I_M_Product.class)
				.addInSubQueryFilter()
				.matchingColumnNames(I_M_Product.COLUMNNAME_M_Product_Category_ID, I_M_Product_Category.COLUMNNAME_M_Product_Category_ID)
				.subQuery(subQuery_ProductcategFilter)
				.end()
				.create();

		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class, getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Org_ID, p_OrgId)
				.addInSubQueryFilter()
				.matchingColumnNames(I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID, I_M_Product.COLUMNNAME_M_Product_ID)
				.subQuery(subQuery_Product)
				.end()
				.addOnlyContextClient();

		return queryBuilder;
	}
}
