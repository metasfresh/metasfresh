/*
 * #%L
 * procurement-webui-backend
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.procurement.webui.sync;

import de.metas.common.procurement.sync.protocol.dto.SyncBPartner;
import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQ;
import de.metas.common.procurement.sync.protocol.dto.SyncUser;
import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.repository.BPartnerRepository;
import de.metas.procurement.webui.repository.ContractLineRepository;
import de.metas.procurement.webui.repository.ContractRepository;
import de.metas.procurement.webui.repository.ProductRepository;
import de.metas.procurement.webui.repository.ProductSupplyRepository;
import de.metas.procurement.webui.repository.RfqRepository;
import de.metas.procurement.webui.repository.UserProductRepository;
import de.metas.procurement.webui.repository.UserRepository;
import de.metas.procurement.webui.repository.WeekSupplyRepository;
import de.metas.procurement.webui.service.impl.ProductSuppliesService;
import de.metas.procurement.webui.service.impl.RfQService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = {
		Application.class,
		SyncBPartnerImportServiceIntegrationTest.TestConfiguration.class,
		SyncBPartnerImportService.class, BPartnerRepository.class,
		SyncProductImportService.class, ProductRepository.class,
		SyncContractListImportService.class, SyncContractImportService.class, ContractRepository.class, ContractLineRepository.class,
		SyncUserImportService.class, UserRepository.class,
		SyncRfqImportService.class, RfQService.class, RfqRepository.class,
		ProductSuppliesService.class, UserProductRepository.class, ProductSupplyRepository.class, WeekSupplyRepository.class })
class SyncBPartnerImportServiceIntegrationTest
{
	@Autowired
	SyncBPartnerImportService syncBPartnerImportService;
	@Autowired
	SyncProductImportService syncProductImportService;
	@Autowired
	BPartnerRepository bpartnerRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ContractRepository contractRepository;
	@Autowired
	ContractLineRepository contractLineRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	RfqRepository rfqRepository;

	@Configuration
	public static class TestConfiguration
	{
		@Bean
		public ISenderToMetasfreshService mockedSenderToMetasfreshService()
		{
			return Mockito.mock(ISenderToMetasfreshService.class);
		}
	}

	private void createDeletedProduct(final SyncProduct syncProduct)
	{
		syncProductImportService.importProduct(syncProduct.toBuilder().deleted(false).build());
		syncProductImportService.importProduct(syncProduct.toBuilder().deleted(true).build());
	}

	@Test
	public void rfqWithAlreadyDeletedProduct()
	{
		final SyncProduct syncProduct = SyncProduct.builder()
				.uuid("product_UUID")
				.deleted(true)
				.name("product")
				.nameTrl("de_CH", "product")
				.packingInfo("packingInfo")
				.build();
		createDeletedProduct(syncProduct);

		syncBPartnerImportService.importBPartner(
				SyncBPartner.builder()
						.uuid("bpartner_UUID")
						.deleted(false)
						.name("testBP")
						.user(SyncUser.builder()
									  .uuid("user_UUID")
									  .email("user@email.com")
									  .password("1234")
									  .language("de_CH")
									  .build())
						.rfq(SyncRfQ.builder()
									 .uuid("rfq_UUID")
									 .dateStart(LocalDate.parse("2020-01-01"))
									 .dateEnd(LocalDate.parse("2020-01-31"))
									 .dateClose(LocalDate.parse("2020-01-31"))
									 .bpartner_uuid("bpartner_UUID")
									 .product(syncProduct)
									 .qtyRequested(new BigDecimal("100"))
									 .qtyCUInfo("Stk")
									 .currencyCode("CHF")
									 .build())
						.build());

		// System.out.println(bpartnerRepository.findAll());
		// System.out.println(userRepository.findAll());
		// System.out.println(contractRepository.findAll());
		// System.out.println(contractLineRepository.findAll());
		// System.out.println(rfqRepository.findAll());

		Assertions.assertThat(rfqRepository.findAll()).hasSize(1);
	}
}