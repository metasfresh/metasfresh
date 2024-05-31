package de.metas.contracts.invoicecandidate;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.impl.ContractsTestBase;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.location.adapter.ContractDocumentLocationAdapterFactory;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractComputingMethodHandlerRegistry;
import de.metas.contracts.modular.ModularContractPriceRepository;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.log.status.ModularLogCreateStatusRepository;
import de.metas.contracts.modular.log.status.ModularLogCreateStatusService;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.workpackage.ProcessModularLogsEnqueuer;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.location.DocumentLocation;
import de.metas.invoice.detail.InvoiceCandidateWithDetailsRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.lang.SOTrx;
import de.metas.lock.api.LockOwner;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.Collections;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

public class FlatrateTermHandlerTest extends ContractsTestBase
{
	private IProductActivityProvider productActivityProvider;
	private ITaxBL taxBL;

	private OrgId orgId;
	private ActivityId activityId;
	private UomId uomId;

	private DocTypeId invoiceDocTypeId;

	@Override
	protected void init()
	{
		SpringContextHolder.registerJUnitBean(new ModularContractSettingsDAO());
		SpringContextHolder.registerJUnitBean(new ModularContractLogDAO());
		SpringContextHolder.registerJUnitBean(new ModularContractSettingsBL(new ModularContractSettingsDAO()));
		final ModularContractLogService modularContractLogService = new ModularContractLogService(new ModularContractLogDAO(), new InvoiceCandidateWithDetailsRepository());
		SpringContextHolder.registerJUnitBean(modularContractLogService);
		SpringContextHolder.registerJUnitBean(new ModularContractComputingMethodHandlerRegistry(ImmutableList.of()));
		SpringContextHolder.registerJUnitBean(new ProcessModularLogsEnqueuer(new ModularLogCreateStatusService(new ModularLogCreateStatusRepository())));
		SpringContextHolder.registerJUnitBean(new ComputingMethodService(modularContractLogService));
		SpringContextHolder.registerJUnitBean(new ModularContractPriceRepository());
		SpringContextHolder.registerJUnitBean(new ModularContractService(new ModularContractComputingMethodHandlerRegistry(Collections.emptyList()),
																		 new ModularContractSettingsDAO(),
																		 new ProcessModularLogsEnqueuer(new ModularLogCreateStatusService(new ModularLogCreateStatusRepository())),
																		 new ComputingMethodService(modularContractLogService),
																		 new ModularContractPriceRepository()));
	}

	@BeforeAll
	public static void configure()
	{
		Adempiere.enableUnitTestMode();
		Check.setDefaultExClass(AdempiereException.class);
	}

	@BeforeEach
	public void before()
	{
		productActivityProvider = Mockito.mock(IProductActivityProvider.class);
		taxBL = Mockito.mock(ITaxBL.class);

		final I_AD_Org org = newInstance(I_AD_Org.class);
		save(org);
		orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final I_C_Activity activity = newInstance(I_C_Activity.class);
		save(activity);
		activityId = ActivityId.ofRepoId(activity.getC_Activity_ID());

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		save(uom);
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());

		final I_C_DocType invoiceDocType = createDocType("InvoiceDocType");
		invoiceDocTypeId = DocTypeId.ofRepoId(invoiceDocType.getC_DocType_ID());
	}

	@Test
	public void test_createCandidatesForTerm()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2013, 5, 28)); // today

		final I_M_Product product1 = createProduct();
		final ProductId productId1 = ProductId.ofRepoId(product1.getM_Product_ID());
		final I_C_Flatrate_Conditions conditions = newFlatrateConditionss()
				.build();
		final I_C_OrderLine orderLine = newOrderLine()
				.conditions(conditions)
				.product(product1)
				.build();

		@NonNull
		final BPartnerLocationAndCaptureId bPartnerLocationAndCaptureId = newBPartnerLocationAndCaptureId();

		final I_C_Flatrate_Term term1 = newFlatrateTerm()
				.bPartnerLocationAndCaptureId(bPartnerLocationAndCaptureId)
				.conditions(conditions)
				.product(product1)
				.orderLine(orderLine)
				.pricingSystemId(PricingSystemId.ofRepoId(10)) // note that while C_FlatrateTerm.C_PricingSystem_ID and C_TaxCategory_ID 
				.taxCategoryId(TaxCategoryId.ofRepoId(20)) // are not mandatory, every subscription term receives one
				.startDate(TimeUtil.getDay(2013, 5, 27)) // yesterday
				.build();

		Services.registerService(IProductActivityProvider.class, productActivityProvider);
		Services.registerService(ITaxBL.class, taxBL);

		Mockito.when(productActivityProvider.getActivityForAcct(
						clientId,
						orgId,
						productId1))
				.thenReturn(activityId);

		final TaxCategoryId taxCategoryId = null;
		Mockito.when(taxBL.getTaxNotNull(
						term1,
						taxCategoryId,
						term1.getM_Product_ID(),
						term1.getStartDate(),
						OrgId.ofRepoId(term1.getAD_Org_ID()),
						null,
						CoalesceUtil.coalesceSuppliersNotNull(
								() -> ContractLocationHelper.extractDropshipLocationId(term1),
								() -> ContractLocationHelper.extractBillToLocationId(term1)),
						SOTrx.SALES,
						null))
				.thenReturn(TaxId.ofRepoId(3));

		final FlatrateTerm_Handler flatrateTermHandler = new FlatrateTerm_Handler();
		final LockOwner lockOwner = LockOwner.newOwner(getClass().getSimpleName() + "#generateInvoiceCandidates");

		final InvoiceCandidateGenerateResult candidates = flatrateTermHandler.createCandidatesFor(InvoiceCandidateGenerateRequest.of(flatrateTermHandler, term1, lockOwner));
		assertInvoiceCandidates(candidates, term1);
	}

	private BPartnerLocationAndCaptureId newBPartnerLocationAndCaptureId()
	{

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName("Partner");
		save(partner);

		final I_C_Location location = newInstance(I_C_Location.class);
		save(location);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(partner.getC_BPartner_ID());
		bpLocation.setC_Location_ID(location.getC_Location_ID());
		save(bpLocation);

		return BPartnerLocationAndCaptureId.ofRepoIdOrNull(partner.getC_BPartner_ID(),
														   bpLocation.getC_BPartner_Location_ID(),
														   location.getC_Location_ID());
	}

	private I_M_Product createProduct()
	{
		final I_M_Product product1 = newInstance(I_M_Product.class);
		product1.setAD_Org_ID(orgId.getRepoId());
		product1.setC_UOM_ID(uomId.getRepoId());
		POJOWrapper.setInstanceName(product1, "product1");
		save(product1);
		return product1;
	}

	@Builder(builderMethodName = "newFlatrateTransition")
	private I_C_Flatrate_Transition createFlatrateTransition(final String termDurationUnit, final int termDuration,
			final String termOfNoticeUnit, final int termOfNotice)
	{
		final I_C_Flatrate_Transition transition = newInstance(I_C_Flatrate_Transition.class);
		transition.setTermDuration(termDuration);
		transition.setTermDurationUnit(termDurationUnit);
		transition.setTermOfNotice(termOfNotice);
		transition.setTermOfNoticeUnit(termOfNoticeUnit);
		transition.setName("Transition1");
		transition.setC_Calendar_Contract_ID(1000000);
		save(transition);
		return transition;
	}

	@Builder(builderMethodName = "newFlatrateConditionss")
	private I_C_Flatrate_Conditions createFlatrateConditions(final I_C_Flatrate_Transition transition)
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription);
		conditions.setAD_Org_ID(orgId.getRepoId());
		conditions.setC_Flatrate_Transition(transition);
		save(conditions);
		return conditions;
	}

	@Builder(builderMethodName = "newOrderLine")
	private I_C_OrderLine createOrderLine(@NonNull final I_M_Product product, @NonNull final I_C_Flatrate_Conditions conditions)
	{
		final I_C_DocType orderDocType = createDocType("OrderDocType");
		orderDocType.setC_DocTypeInvoice_ID(invoiceDocTypeId.getRepoId());
		save(orderDocType);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setAD_Org_ID(product.getAD_Org_ID());
		order.setDocStatus(DocStatus.Completed.getCode());
		order.setC_DocTypeTarget_ID(orderDocType.getC_DocType_ID());
		save(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setAD_Org_ID(product.getAD_Org_ID());
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		orderLine.setM_Product_ID(product.getM_Product_ID());
		save(orderLine);
		return orderLine;
	}

	private I_C_DocType createDocType(final String name)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setName(name);
		save(docType);

		return docType;
	}

	@Builder(builderMethodName = "newFlatrateTerm")
	private I_C_Flatrate_Term createFlatrateTerm(
			@NonNull final BPartnerLocationAndCaptureId bPartnerLocationAndCaptureId,
			@NonNull final I_C_Flatrate_Conditions conditions,
			final I_M_Product product,
			final PricingSystemId pricingSystemId,
			final TaxCategoryId taxCategoryId,
			final I_C_OrderLine orderLine,
			@NonNull final Timestamp startDate,
			final boolean isAutoRenew)
	{
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		POJOWrapper.setInstanceName(term, "term1");
		term.setAD_Org_ID(conditions.getAD_Org_ID());
		term.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		term.setC_Flatrate_Conditions(conditions);
		term.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription);
		term.setM_Product_ID(product.getM_Product_ID());
		term.setStartDate(startDate);
		term.setC_OrderLine_Term_ID(orderLine.getC_OrderLine_ID());
		term.setC_Order_Term_ID(orderLine.getC_Order_ID());
		term.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingSystemId));
		term.setC_TaxCategory_ID(TaxCategoryId.toRepoId(taxCategoryId));
		term.setIsAutoRenew(isAutoRenew);
		term.setC_UOM_ID(uomId.getRepoId());

		ContractDocumentLocationAdapterFactory.billLocationAdapter(term)
				.setFrom(DocumentLocation.builder()
								 .bpartnerId(BPartnerId.ofRepoIdOrNull(bPartnerLocationAndCaptureId.getBpartnerRepoId()))
								 .bpartnerLocationId(bPartnerLocationAndCaptureId.getBpartnerLocationId())
								 .locationId(bPartnerLocationAndCaptureId.getLocationCaptureId())
								 .build());

		save(term);
		return term;
	}

	private void assertInvoiceCandidates(final InvoiceCandidateGenerateResult candidates, final I_C_Flatrate_Term term1)
	{
		assertThat(candidates.getC_Invoice_Candidates()).hasSize(1);
		final I_C_Invoice_Candidate invoiceCandidate = candidates.getC_Invoice_Candidates().get(0);
		assertThat(invoiceCandidate.getM_Product_ID()).isEqualTo(term1.getM_Product_ID());
		assertThat(invoiceCandidate.getC_Order()).isNotNull();
		assertThat(invoiceCandidate.getC_OrderLine()).isEqualTo(term1.getC_OrderLine_Term());
		assertThat(invoiceCandidate.getC_DocTypeInvoice_ID()).isEqualTo(invoiceDocTypeId.getRepoId());
	}
}
