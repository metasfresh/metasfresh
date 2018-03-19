package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

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

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.StartupListener;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.interceptor.M_ShipmentSchedule;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ContractChangePriceQtyService.class })
public class ContractChangePriceQtyTest extends AbstractFlatrateTermTest
{
	final private IContractsDAO contractsDAO = Services.get(IContractsDAO.class);
	final private static Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");

	@Before
	public void before()
	{
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(C_Flatrate_Term.INSTANCE);
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(M_ShipmentSchedule.INSTANCE);
	}

	@Test
	public void changeQty()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);
		final ContractChangePriceQtyService contractsRepository = Adempiere.getBean(ContractChangePriceQtyService.class);
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
		final ContractChangePriceQtyService contractsRepository = Adempiere.getBean(ContractChangePriceQtyService.class);
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
		final ContractChangePriceQtyService contractsRepository = Adempiere.getBean(ContractChangePriceQtyService.class);
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
		candidates.forEach(invoiceCand -> {
			assertThat(invoiceCand.getQtyToInvoice()).isEqualTo(flatrateTerm.getPlannedQtyPerUnit());
			assertThat(invoiceCand.getPriceActual()).isEqualTo(flatrateTerm.getPriceActual());
		});
	}

	private void assertSubscriptionProgress(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		final List<I_C_SubscriptionProgress> subscriptionProgress = contractsDAO.getSubscriptionProgress(flatrateTerm);

		subscriptionProgress.stream()
				.filter(progress -> progress.getStatus().equals(X_C_SubscriptionProgress.STATUS_Done) || progress.getStatus().equals(X_C_SubscriptionProgress.STATUS_Delivered))
				.peek(progress -> assertThat(progress.getQty()).isEqualTo(QTY_ONE))
				.filter(progress -> progress.getStatus().equals(X_C_SubscriptionProgress.STATUS_Planned) || progress.getStatus().equals(X_C_SubscriptionProgress.STATUS_Open))
				.peek(progress -> assertThat(progress.getQty()).isEqualTo(flatrateTerm.getPlannedQtyPerUnit()));
	}

}
