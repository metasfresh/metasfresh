package de.metas.contracts.impl;

import de.metas.contracts.IContractsDAO;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.interceptor.M_ShipmentSchedule;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.order.ContractOrderService;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.tax.api.Tax;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Tax;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class ContractChangePriceQtyTest extends AbstractFlatrateTermTest
{
	private ContractChangePriceQtyService contractsRepository;
	private final IContractsDAO contractsDAO = Services.get(IContractsDAO.class);

	private final static Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");

	@BeforeEach
	public void before()
	{
		SpringContextHolder.registerJUnitBean(PerformanceMonitoringService.class, new NoopPerformanceMonitoringService());

		contractsRepository = new ContractChangePriceQtyService();
		final ContractOrderService contractOrderService = new ContractOrderService();

		final IModelInterceptorRegistry interceptorRegistry = Services.get(IModelInterceptorRegistry.class);
		interceptorRegistry.addModelInterceptor(new C_Flatrate_Term(contractOrderService));
		interceptorRegistry.addModelInterceptor(M_ShipmentSchedule.INSTANCE);

		final I_C_Tax taxNotFoundRecord = newInstance(I_C_Tax.class);
		taxNotFoundRecord.setC_Tax_ID(Tax.C_TAX_ID_NO_TAX_FOUND);
		saveRecord(taxNotFoundRecord);
	}

	@Test
	public void changeQty()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);
		final BigDecimal newQty = BigDecimal.valueOf(5);
		contractsRepository.changeQtyIfNeeded(contract, newQty);

		assertThat(contract.getPlannedQtyPerUnit()).isEqualTo(newQty);
		assertInvoiceCandidates(contract);
		assertSubscriptionProgress(contract);
	}

	@Test
	public void changeQtyAndDeliver()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);
		deliverFirstSubscriptionProgress(contract);
		final BigDecimal newQty = BigDecimal.valueOf(5);
		contractsRepository.changeQtyIfNeeded(contract, newQty);

		assertThat(contract.getPlannedQtyPerUnit()).isEqualTo(newQty);
		assertInvoiceCandidates(contract);
		assertSubscriptionProgress(contract);
	}

	@Test
	public void changePrice()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);
		final BigDecimal newPrice = BigDecimal.valueOf(5);
		contractsRepository.changePriceIfNeeded(contract, newPrice);

		assertThat(contract.getPriceActual()).isEqualTo(newPrice);
		assertInvoiceCandidates(contract);
		assertSubscriptionProgress(contract);
	}

	private void deliverFirstSubscriptionProgress(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		final List<I_C_SubscriptionProgress> subscriptionProgress = contractsDAO.getSubscriptionProgress(flatrateTerm);
		final I_C_SubscriptionProgress firstSubscription = subscriptionProgress.get(0);
		firstSubscription.setStatus(X_C_SubscriptionProgress.STATUS_Open);
		save(firstSubscription);

		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setQtyDelivered(flatrateTerm.getPlannedQtyPerUnit());
		shipmentSchedule.setRecord_ID(firstSubscription.getC_SubscriptionProgress_ID());
		shipmentSchedule.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_SubscriptionProgress.Table_Name));
		save(shipmentSchedule);

		InterfaceWrapperHelper.refresh(firstSubscription);
		firstSubscription.setM_ShipmentSchedule(shipmentSchedule);
		save(firstSubscription);
	}

	private void assertInvoiceCandidates(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		final List<I_C_Invoice_Candidate> candidates = createInvoiceCandidates(flatrateTerm);
		assertThat(candidates).isNotEmpty();
		candidates.forEach(invoiceCand -> assertInvoiceCandidate(invoiceCand, flatrateTerm));
	}

	private void assertInvoiceCandidate(I_C_Invoice_Candidate invoiceCand, final I_C_Flatrate_Term flatrateTerm)
	{
		assertThat(invoiceCand.getQtyEntered()).isEqualTo(flatrateTerm.getPlannedQtyPerUnit());
		assertThat(invoiceCand.getPriceActual()).isEqualTo(flatrateTerm.getPriceActual());
	}

	private void assertSubscriptionProgress(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		final List<I_C_SubscriptionProgress> subscriptionProgress = contractsDAO.getSubscriptionProgress(flatrateTerm);
		assertThat(subscriptionProgress).isNotEmpty();

		subscriptionProgress.stream()
				.filter(progress -> progress.getStatus().equals(X_C_SubscriptionProgress.STATUS_Done) || progress.getStatus().equals(X_C_SubscriptionProgress.STATUS_Delivered))
				.peek(progress -> assertThat(progress.getQty()).isEqualTo(QTY_ONE))
				.filter(progress -> progress.getStatus().equals(X_C_SubscriptionProgress.STATUS_Planned) || progress.getStatus().equals(X_C_SubscriptionProgress.STATUS_Open))
				.peek(progress -> assertThat(progress.getQty()).isEqualTo(flatrateTerm.getPlannedQtyPerUnit()));
	}

}
