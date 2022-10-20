/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.serviceprovider.issue.invoicecandidate;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.project.service.ProjectRepository;
import de.metas.quantity.Quantitys;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.issue.IssueService;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.tax.api.TaxQuery;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Project;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

public class S_Issue_Handler extends AbstractInvoiceCandidateHandler
{
	private static final Logger log = LogManager.getLogger(S_Issue_Handler.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);

	private final IssueService issueService = SpringContextHolder.instance.getBean(IssueService.class);
	private final ProjectRepository projectRepository = SpringContextHolder.instance.getBean(ProjectRepository.class);

	@Override
	public CandidatesAutoCreateMode getGeneralCandidatesAutoCreateMode()
	{
		return CandidatesAutoCreateMode.DONT;
	}

	@Override
	public CandidatesAutoCreateMode getSpecificCandidatesAutoCreateMode(final Object model)
	{
		final I_S_Issue issue = create(model, I_S_Issue.class);

		return recordHasAnInvoiceCandidate(issue) ? CandidatesAutoCreateMode.DONT : CandidatesAutoCreateMode.CREATE_CANDIDATES;
	}

	@Override
	public Iterator<?> retrieveAllModelsWithMissingCandidates(@NonNull final QueryLimit limit)
	{
		//we don't want to create invoice candidates for issues that were not manually enqueued
		return ImmutableList.of().iterator();
	}

	@Override
	@NonNull
	public InvoiceCandidateGenerateResult createCandidatesFor(final InvoiceCandidateGenerateRequest request)
	{
		final I_S_Issue issue = request.getModel(I_S_Issue.class);

		try
		{
			return createCandidateFor(issue);
		}
		catch (final Throwable throwable)
		{
			throw throwable;//FIXME
		}
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		invoiceCandDAO.invalidateCandsThatReference(TableRecordReference.of(model));
	}

	@Override
	public String getSourceTable()
	{
		return I_S_Issue.Table_Name;
	}

	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	@Override
	public void setOrderedData(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		final IssueEntity issueEntity = getReferencedIssue(invoiceCandidate);

		final UomId uomId = productBL.getStockUOMId(invoiceCandidate.getM_Product_ID());

		invoiceCandidate.setQtyEntered(issueEntity.getInvoiceableHours());
		invoiceCandidate.setC_UOM_ID(uomId.getRepoId());
		invoiceCandidate.setQtyOrdered(issueEntity.getInvoiceableHours());

		final Instant todayDate = Instant.now();
		invoiceCandidate.setDateOrdered(TimeUtil.asTimestamp(todayDate));
	}

	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		//	do nothing
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		final IssueEntity issue = getReferencedIssue(ic);

		if (issue.getProjectId() == null)
		{
			throw new AdempiereException("S_Issue.C_Project_ID cannot be missing on an invoiceable S_Issue! S_Issue_ID = " + issue.getIssueId());
		}

		final I_C_Project project = Optional.ofNullable(projectRepository.getRecordById(issue.getProjectId()))
				//dev-note: not a real scenario
				.orElseThrow(() -> new AdempiereException("NO C_Project found for S_Issue.C_Project_ID! S_Issue_ID = " + issue.getIssueId()));

		setBPartnerData(ic, project);
	}

	@Override
	public void postSave(final @NonNull InvoiceCandidateGenerateResult result)
	{
		result.getC_Invoice_Candidates()
				.stream()
				.map(candidate -> IssueId.ofRepoId(candidate.getRecord_ID()))
				.forEach(issueService::processIssue);
	}

	@NonNull
	private InvoiceCandidateGenerateResult createCandidateFor(@NonNull final I_S_Issue record)
	{
		final IssueEntity issue = IssueRepository.ofRecord(record);
		final TableRecordReference recordReference = TableRecordReference.of(I_S_Issue.Table_Name, record.getS_Issue_ID());

		final I_C_Invoice_Candidate invoiceCandidate = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);

		invoiceCandidate.setC_ILCandHandler_ID(getHandlerRecord().getC_ILCandHandler_ID());

		invoiceCandidate.setAD_Table_ID(recordReference.getAD_Table_ID());
		invoiceCandidate.setRecord_ID(recordReference.getRecord_ID());
		invoiceCandidate.setAD_Org_ID(OrgId.toRepoId(issue.getOrgId()));

		if (issue.getProjectId() == null)
		{
			final String projectIdErrorMsg = "IssueId: " + issue.getIssueId() + " skipped! Missing projectId on issue record.";
			return InvoiceCandidateGenerateResult.of(this, ImmutableMap.of(recordReference, projectIdErrorMsg));
		}

		invoiceCandidate.setC_Project_ID(issue.getProjectId().getRepoId());

		if (issue.getCostCenterActivityId() == null)
		{
			final String costCenterIdErrorMsg = "IssueId: " + issue.getIssueId() + " skipped! Missing costCenterId on issue record.";
			return InvoiceCandidateGenerateResult.of(this, ImmutableMap.of(recordReference, costCenterIdErrorMsg));
		}

		invoiceCandidate.setC_Activity_ID(issue.getCostCenterActivityId().getRepoId());

		final I_C_Project project = projectRepository.getRecordById(issue.getProjectId());
		if (project == null)
		{
			final String projectDataErrorMsg = "IssueId: " + issue.getIssueId() + " skipped! Missing project for ProjectId: " + issue.getProjectId();
			return InvoiceCandidateGenerateResult.of(this, ImmutableMap.of(recordReference, projectDataErrorMsg));
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(project.getM_Product_ID());
		if (productId == null)
		{
			final String productIdErrorMsg = "IssueId: " + issue.getIssueId() + " skipped! Missing ProductId from ProjectId: " + issue.getProjectId();
			return InvoiceCandidateGenerateResult.of(this, ImmutableMap.of(recordReference, productIdErrorMsg));
		}

		invoiceCandidate.setM_Product_ID(productId.getRepoId());
		invoiceCandidate.setDescription(getDescription(issue));

		setOrderedData(invoiceCandidate);

		final SOTrx soTrx = SOTrx.SALES;
		invoiceCandidate.setIsSOTrx(soTrx.toBoolean());

		final String bpartnerErrorMsg = setBPartnerData(invoiceCandidate, project);
		if (Check.isNotBlank(bpartnerErrorMsg))
		{
			return InvoiceCandidateGenerateResult.of(this, ImmutableMap.of(recordReference, bpartnerErrorMsg));
		}

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(invoiceCandidate.getBill_BPartner_ID());
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bPartnerId, invoiceCandidate.getBill_Location_ID());

		final PricingSystemId pricingSystemId = bPartnerDAO.retrievePricingSystemIdOrNull(bPartnerId, soTrx);

		final BPartnerLocationAndCaptureId bPartnerLocationAndCaptureId = BPartnerLocationAndCaptureId.of(bPartnerLocationId);

		final PriceListId priceListId = priceListDAO.retrievePriceListIdByPricingSyst(pricingSystemId, bPartnerLocationAndCaptureId, soTrx);

		final ZoneId timeZone = orgDAO.getTimeZone(issue.getOrgId());

		final IEditablePricingContext pricingContext = pricingBL
				.createInitialContext(
						issue.getOrgId(),
						productId,
						bPartnerId,
						Quantitys.create(BigDecimal.ONE, productId),
						soTrx)
				.setPriceListId(priceListId)
				.setPriceDate(TimeUtil.asLocalDate(invoiceCandidate.getDateOrdered(), timeZone))
				.setFailIfNotCalculated();

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContext);

		invoiceCandidate.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Immediate);
		invoiceCandidate.setInvoicableQtyBasedOn(X_C_Invoice_Candidate.INVOICABLEQTYBASEDON_Nominal);

		invoiceCandidate.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingSystemId));
		invoiceCandidate.setM_PriceList_Version_ID(pricingResult.getPriceListVersionId().getRepoId());
		invoiceCandidate.setC_Currency_ID(pricingResult.getCurrencyId().getRepoId());
		invoiceCandidate.setPriceActual(pricingResult.getPriceStd());
		invoiceCandidate.setPrice_UOM_ID(pricingResult.getPriceUomId().getRepoId());

		invoiceCandidate.setPriceEntered(pricingResult.getPriceStd());
		invoiceCandidate.setDiscount(pricingResult.getDiscount().toBigDecimal());

		final Tax tax = taxDAO.getBy(TaxQuery.builder()
											 .orgId(issue.getOrgId())
											 .bPartnerLocationId(bPartnerLocationAndCaptureId)
											 .dateOfInterest(invoiceCandidate.getDeliveryDate())
											 .soTrx(soTrx)
											 .taxCategoryId(pricingResult.getTaxCategoryId())
											 .build());
		if (tax == null)
		{
			throw TaxNotFoundException.builder()
					.taxCategoryId(pricingResult.getTaxCategoryId())
					.isSOTrx(soTrx.toBoolean())
					.billDate(invoiceCandidate.getDeliveryDate())
					.orgId(issue.getOrgId())
					.build()
					.appendParametersToMessage()
					.setParameter("S_Issue_ID", issue.getIssueId());
		}

		invoiceCandidate.setC_Tax_ID(tax.getTaxId().getRepoId());

		return InvoiceCandidateGenerateResult.of(this, invoiceCandidate);
	}

	public boolean recordHasAnInvoiceCandidate(@NonNull final I_S_Issue issueRecord)
	{
		return retrieveICsThatReferenceQueryBuilder()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Record_ID, issueRecord.getS_Issue_ID())
				.create()
				.anyMatch();
	}

	@NonNull
	private IQueryBuilder<I_C_Invoice_Candidate> retrieveICsThatReferenceQueryBuilder()
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, getTableId(I_S_Issue.class));
	}

	@Nullable
	private String setBPartnerData(
			@NonNull final I_C_Invoice_Candidate invoiceCandidate,
			@NonNull final I_C_Project project)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoIdOrNull(project.getC_BPartner_ID());
		if (bPartnerId == null)
		{
			return "Missing business partner from ProjectId: " + project.getC_Project_ID();
		}

		invoiceCandidate.setBill_BPartner_ID(bPartnerId.getRepoId());

		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(bPartnerId, project.getC_BPartner_Location_ID());
		if (bPartnerLocationId == null)
		{
			Loggables.withLogger(log, Level.DEBUG).addLog("Missing business partner location from ProjectId: {}", project.getC_Project_ID());
			return "Missing business partner location from ProjectId: " + project.getC_Project_ID();
		}

		invoiceCandidate.setBill_Location_ID(bPartnerLocationId.getRepoId());
		invoiceCandidate.setBill_User_ID(project.getAD_User_ID());

		return null;
	}

	@NonNull
	private String getDescription(@NonNull final IssueEntity issueEntity)
	{
		final StringBuilder sb = new StringBuilder();
		Optional.ofNullable(issueEntity.getExternalIssueNo())
				.map(sb::append);
		sb.append("\n").append(issueEntity.getName());

		return sb.toString();
	}

	@NonNull
	private IssueEntity getReferencedIssue(@NonNull final I_C_Invoice_Candidate ic)
	{
		return issueService.getById(IssueId.ofRepoId(ic.getRecord_ID()));
	}
}
