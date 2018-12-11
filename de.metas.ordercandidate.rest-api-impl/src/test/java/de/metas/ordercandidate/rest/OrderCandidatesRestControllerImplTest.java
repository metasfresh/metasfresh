package de.metas.ordercandidate.rest;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.rest.SyncAdvise.IfNotExists;
import mockit.Mocked;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class OrderCandidatesRestControllerImplTest
{

	private OrderCandidatesRestControllerImpl orderCandidatesRestControllerImpl;

	@Mocked
	private PermissionService permissionService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		{ // create the master data requested to process the data from our json file
			final I_C_Country countryRecord = newInstance(I_C_Country.class);
			countryRecord.setCountryCode("CH");
			saveRecord(countryRecord);

			final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
			uomRecord.setX12DE355("MJ");
			saveRecord(uomRecord);

			final I_C_DocType docTypeRecord = newInstance(I_C_DocType.class);
			docTypeRecord.setDocBaseType("ARI");
			docTypeRecord.setDocSubType("KV");
			saveRecord(docTypeRecord);

			final I_AD_InputDataSource dataSourceRecord = newInstance(I_AD_InputDataSource.class);
			dataSourceRecord.setInternalName("SOURCE.de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.ImportInvoice440RestController");
			saveRecord(dataSourceRecord);

			final I_AD_InputDataSource dataDestRecord = newInstance(I_AD_InputDataSource.class);
			dataDestRecord.setInternalName("DEST.de.metas.invoicecandidate");
			saveRecord(dataDestRecord);
		}

		final MasterdataProviderFactory masterdataProviderFactory = MasterdataProviderFactory
				.builder()
				.permissionService(permissionService).build();

		orderCandidatesRestControllerImpl = new OrderCandidatesRestControllerImpl(
				masterdataProviderFactory,
				new JsonConverters(),
				new OLCandRepository());
	}

	@Test
	public void extractTags()
	{
		assertThat(invokeWith(ImmutableList.of())).isEmpty();

		assertThat(invokeWith(ImmutableList.of("n1"))).isEmpty();

		assertThat(invokeWith(ImmutableList.of("n1", "v1"))).containsEntry("n1", "v1");

		assertThat(invokeWith(ImmutableList.of("n1", "v1", "n2"))).containsEntry("n1", "v1");

		assertThat(invokeWith(ImmutableList.of("n1", "v1", "n2", "v2"))).containsEntry("n1", "v1").containsEntry("n2", "v2");
	}

	private ImmutableMap<String, String> invokeWith(final ImmutableList<String> of)
	{
		return orderCandidatesRestControllerImpl.extractTags(of);
	}

	@Test
	public void createOrderLineCandidates()
	{
		final JsonOLCandCreateBulkRequest bulkRequestFromFile = JsonOLCandUtil.fromResource("/JsonOLCandCreateBulkRequest.json");
		assertThat(bulkRequestFromFile.getRequests()).hasSize(21);

		final SyncAdvise ifNotExistsCreateAdvise = SyncAdvise.builder().ifNotExists(IfNotExists.CREATE).build();

		final JsonOLCandCreateBulkRequest bulkRequest = bulkRequestFromFile
				.withBPartnersSyncAdvise(ifNotExistsCreateAdvise)
				.withProductsSyncAdvise(ifNotExistsCreateAdvise);

		final JsonOLCandCreateBulkResponse response = orderCandidatesRestControllerImpl
				.createOrderLineCandidates(bulkRequest)
				.getBody();
		assertThat(response.getResult()).hasSize(21);
	}
}
