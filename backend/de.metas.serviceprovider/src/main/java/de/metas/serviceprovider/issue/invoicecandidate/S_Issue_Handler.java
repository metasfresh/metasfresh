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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.document.location.DocumentLocation;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.lang.SOTrx;
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
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.tax.api.TaxQuery;
import de.metas.uom.UomId;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Project;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

public class S_Issue_Handler extends AbstractInvoiceCandidateHandler
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

	private final IssueService issueService = SpringContextHolder.instance.getBean(IssueService.class);
	private final ProjectRepository projectRepository = SpringContextHolder.instance.getBean(ProjectRepository.class);
	private final UserRepository userRepository = SpringContextHolder.instance.getBean(UserRepository.class);

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
			final IssueEntity issueWithInvoicingError = IssueRepository.ofRecord(issue)
					.toBuilder()
					.invoicingErrorMsg(throwable.getMessage())
					.isInvoicingError(true)
					.build();

			issueService.save(issueWithInvoicingError);

			return InvoiceCandidateGenerateResult.of(this);
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

		invoiceCandidate.setDateOrdered(SystemTime.asTimestamp());
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
		result.getC_Invoice_Candidates().forEach(this::postUpdate);
	}

	@Override
	public void postUpdate(final @NonNull I_C_Invoice_Candidate ic)
	{
		final IssueId issueId = IssueId.ofRepoId(ic.getRecord_ID());
		issueService.processIssue(issueId);
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
			throw new AdempiereException("Missing ProjectId on S_Issue!")
					.appendParametersToMessage()
					.setParameter("S_Issue_ID", record.getS_Issue_ID());
		}

		invoiceCandidate.setC_Project_ID(issue.getProjectId().getRepoId());

		if (issue.getCostCenterActivityId() == null)
		{
			throw new AdempiereException("Missing CostCenterId on S_Issue!")
					.appendParametersToMessage()
					.setParameter("S_Issue_ID", record.getS_Issue_ID());
		}

		invoiceCandidate.setC_Activity_ID(issue.getCostCenterActivityId().getRepoId());

		final I_C_Project project = Optional.ofNullable(projectRepository.getRecordById(issue.getProjectId()))
				//dev-note: not a real scenario
				.orElseThrow(() -> new AdempiereException("No C_Project found for S_Issue.C_Project_ID!")
						.appendParametersToMessage()
						.setParameter("S_Issue_ID", record.getS_Issue_ID()));

		final ProductId productId = ProductId.ofRepoIdOrNull(project.getM_Product_ID());
		if (productId == null)
		{
			throw new AdempiereException("Missing invoiceable ProductId on C_Project!")
					.appendParametersToMessage()
					.setParameter("S_Issue_ID", record.getS_Issue_ID())
					.setParameter("C_Project_ID", project.getC_Project_ID());
		}

		invoiceCandidate.setM_Product_ID(productId.getRepoId());
		invoiceCandidate.setDescription(getDescription(issue));

		setOrderedData(invoiceCandidate);

		final SOTrx soTrx = SOTrx.SALES;
		invoiceCandidate.setIsSOTrx(soTrx.toBoolean());

		setBPartnerData(invoiceCandidate, project);

		final IPricingResult pricingResult = calculatePrice(invoiceCandidate);

		invoiceCandidate.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Immediate);
		invoiceCandidate.setInvoicableQtyBasedOn(X_C_Invoice_Candidate.INVOICABLEQTYBASEDON_Nominal);

		invoiceCandidate.setM_PricingSystem_ID(pricingResult.getPricingSystemId().getRepoId());
		invoiceCandidate.setM_PriceList_Version_ID(pricingResult.getPriceListVersionId().getRepoId());
		invoiceCandidate.setC_Currency_ID(pricingResult.getCurrencyId().getRepoId());
		invoiceCandidate.setPriceActual(pricingResult.getPriceStd());
		invoiceCandidate.setPrice_UOM_ID(pricingResult.getPriceUomId().getRepoId());

		invoiceCandidate.setPriceEntered(pricingResult.getPriceStd());
		invoiceCandidate.setDiscount(pricingResult.getDiscount().toBigDecimal());

		final TaxId taxId = getTaxId(invoiceCandidate, pricingResult.getTaxCategoryId());
		invoiceCandidate.setC_Tax_ID(taxId.getRepoId());

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

	private void setBPartnerData(
			@NonNull final I_C_Invoice_Candidate invoiceCandidate,
			@NonNull final I_C_Project project)
	{
		final DocumentLocation billingDocumentLocation = getBillingBPartnerDetails(project);

		invoiceCandidate.setBill_BPartner_ID(BPartnerId.toRepoId(billingDocumentLocation.getBpartnerId()));
		invoiceCandidate.setBill_Location_ID(BPartnerLocationId.toRepoId(billingDocumentLocation.getBpartnerLocationId()));
		invoiceCandidate.setBill_User_ID(BPartnerContactId.toRepoId(billingDocumentLocation.getContactId()));
	}

	@NonNull
	private String getDescription(@NonNull final IssueEntity issueEntity)
	{
		return Stream.of(issueEntity.getExternalIssueNo(), issueEntity.getName())
				.map(String::valueOf)
				.collect(Collectors.joining("\n"));
	}

	@NonNull
	private IssueEntity getReferencedIssue(@NonNull final I_C_Invoice_Candidate ic)
	{
		return issueService.getById(IssueId.ofRepoId(ic.getRecord_ID()));
	}

	@NonNull
	private DocumentLocation getBillingBPartnerDetails(@NonNull final I_C_Project project)
	{
		final BPartnerLocationId billingLocationId = getBillToLocationId(project);

		final BPartnerContactId billContactId = getContactId(project, billingLocationId);

		return DocumentLocation.builder()
				.bpartnerId(billingLocationId.getBpartnerId())
				.bpartnerLocationId(billingLocationId)
				.contactId(billContactId)
				.build();
	}

	@NonNull
	private BPartnerLocationAndCaptureId getBpartnerLocationAndCapture(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(invoiceCandidate.getBill_BPartner_ID());
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bPartnerId, invoiceCandidate.getBill_Location_ID());

		return BPartnerLocationAndCaptureId.of(bPartnerLocationId);
	}

	@NonNull
	private IPricingResult calculatePrice(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		final OrgId orgId = OrgId.ofRepoId(invoiceCandidate.getAD_Org_ID());
		final ProductId productId = ProductId.ofRepoId(invoiceCandidate.getM_Product_ID());
		final SOTrx soTrx = SOTrx.ofBoolean(invoiceCandidate.isSOTrx());
		final BPartnerLocationAndCaptureId bPartnerLocationAndCaptureId = getBpartnerLocationAndCapture(invoiceCandidate);

		final PricingSystemId pricingSystemId = bPartnerDAO.retrievePricingSystemIdOrNull(bPartnerLocationAndCaptureId.getBpartnerId(), soTrx);

		final PriceListId priceListId = priceListDAO.retrievePriceListIdByPricingSyst(pricingSystemId, bPartnerLocationAndCaptureId, soTrx);

		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final IEditablePricingContext pricingContext = pricingBL
				.createInitialContext(
						orgId,
						productId,
						bPartnerLocationAndCaptureId.getBpartnerId(),
						Quantitys.create(invoiceCandidate.getQtyOrdered(), productId),
						soTrx)
				.setPriceListId(priceListId)
				.setPriceDate(TimeUtil.asLocalDate(invoiceCandidate.getDateOrdered(), timeZone))
				.setFailIfNotCalculated();

		return pricingBL.calculatePrice(pricingContext);
	}

	@NonNull
	private TaxId getTaxId(
			@NonNull final I_C_Invoice_Candidate invoiceCandidate,
			@NonNull final TaxCategoryId taxCategoryId)
	{
		final BPartnerLocationAndCaptureId bPartnerLocationAndCaptureId = getBpartnerLocationAndCapture(invoiceCandidate);
		final OrgId orgId = OrgId.ofRepoId(invoiceCandidate.getAD_Org_ID());
		final SOTrx soTrx = SOTrx.ofBoolean(invoiceCandidate.isSOTrx());

		final Tax tax = taxDAO.getBy(TaxQuery.builder()
											 .orgId(orgId)
											 .bPartnerLocationId(bPartnerLocationAndCaptureId)
											 .dateOfInterest(invoiceCandidate.getDeliveryDate())
											 .soTrx(soTrx)
											 .taxCategoryId(taxCategoryId)
											 .build());
		if (tax == null)
		{
			throw TaxNotFoundException.builder()
					.taxCategoryId(taxCategoryId)
					.isSOTrx(soTrx.toBoolean())
					.billDate(invoiceCandidate.getDeliveryDate())
					.orgId(orgId)
					.build()
					.appendParametersToMessage()
					.setParameter("S_Issue_ID", invoiceCandidate.getRecord_ID());
		}

		return tax.getTaxId();
	}

	@NonNull
	private BPartnerLocationId getBillToLocationId(@NonNull final I_C_Project project)
	{
		final BPartnerId projectBPartnerId = BPartnerId.ofRepoIdOrNull(project.getC_BPartner_ID());
		if (projectBPartnerId == null)
		{
			throw new AdempiereException("Missing business partner from C_Project")
					.appendParametersToMessage()
					.setParameter("C_Project_ID", project.getC_Project_ID());
		}

		final BPartnerLocationId projectBPartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(projectBPartnerId, project.getC_BPartner_Location_ID());

		// prefer the project's C_BPartner_Location_ID, but if it's not a billTolocation, then fall back to another bill-location of the bpartner
		final BPartnerLocationId billingLocationId = Optional.ofNullable(projectBPartnerLocationId)
				.map(bPartnerDAO::getBPartnerLocationByIdInTrx)
				.filter(I_C_BPartner_Location::isBillTo)
				.map(billToLocation -> BPartnerLocationId.ofRepoId(billToLocation.getC_BPartner_ID(), billToLocation.getC_BPartner_Location_ID()))
				.orElseGet(() -> bPartnerDAO.retrieveBPartnerLocationId(IBPartnerDAO.BPartnerLocationQuery.builder()
																				.type(IBPartnerDAO.BPartnerLocationQuery.Type.BILL_TO)
																				.bpartnerId(projectBPartnerId)
																				.build()));
		if (billingLocationId == null)
		{
			throw new AdempiereException("Fail to resolve a business partner billing location.")
					.appendParametersToMessage()
					.setParameter("C_Project_ID", project.getC_Project_ID())
					.setParameter("C_BPartner_ID", project.getC_BPartner_ID());
		}

		return billingLocationId;
	}

	@Nullable
	private BPartnerContactId getContactId(@NonNull final I_C_Project project, @NonNull final BPartnerLocationId billToLocationId)
	{
		final UserId projectContactId = UserId.ofRepoIdOrNull(project.getAD_User_ID());

		return Optional.ofNullable(projectContactId)
				.map(userId -> BPartnerContactId.ofRepoIdOrNull(billToLocationId.getBpartnerId(), userId.getRepoId()))
				.orElseGet(() -> {

					final User bpartnerBillingUser = bpartnerBL.retrieveContactOrNull(IBPartnerBL.RetrieveContactRequest.builder()
																							  .onlyActive(true)
																							  .contactType(IBPartnerBL.RetrieveContactRequest.ContactType.BILL_TO_DEFAULT)
																							  .bpartnerId(billToLocationId.getBpartnerId())
																							  .bPartnerLocationId(billToLocationId)
																							  .ifNotFound(IBPartnerBL.RetrieveContactRequest.IfNotFound.RETURN_NULL)
																							  .build());
					return Optional.ofNullable(bpartnerBillingUser)
							.flatMap(User::getBPartnerContactId)
							.orElse(null);
				});
	}
}
