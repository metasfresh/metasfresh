package de.metas.order.restart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.order.OrderId;
import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsHandler;
import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsHandler.RecordsToHandleKey;
import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsRequest;
import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsService;
import de.metas.util.RelatedRecordsProvider;
import de.metas.util.RelatedRecordsProvider.SourceRecordsKey;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.business
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

public class VoidOrderAndRelatedDocsServiceTest
{
	private static final String RELATED_TABLE_NAME = "relatedRecordsTable";

	private static final String SOURCE_TABLE_NAME = "sourceTable";

	private RelatedTableForSourceTableProvider relatedTableForSourceTableProvider;

	private VoidSourceTableRelatedToOrderHandler voidSourceTableRelatedToOrderHandler;

	private VoidTableRelatedToOrderHandler voidTableRelatedToOrderHandler;

	private TableRecordReference sourceTableReference;

	private IPair<RecordsToHandleKey, List<ITableRecordReference>> sourceTableRecordsToHandle;

	private TableRecordReference relatedReference;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		voidSourceTableRelatedToOrderHandler = new VoidSourceTableRelatedToOrderHandler();
		voidTableRelatedToOrderHandler = new VoidTableRelatedToOrderHandler();

		sourceTableReference = TableRecordReference.of(SOURCE_TABLE_NAME, 20);
		sourceTableRecordsToHandle = ImmutablePair.of(
				RecordsToHandleKey.of(SOURCE_TABLE_NAME),
				ImmutableList.of(sourceTableReference));

		relatedReference = TableRecordReference.of(RELATED_TABLE_NAME, 30);

		final IPair<SourceRecordsKey, List<ITableRecordReference>> provideRelatedRecordsResult = ImmutablePair.of(
				SourceRecordsKey.of(RELATED_TABLE_NAME),
				ImmutableList.of(relatedReference));

		relatedTableForSourceTableProvider = new RelatedTableForSourceTableProvider(provideRelatedRecordsResult);
	}

	/**
	 * Two handler, one provider. The first handler is invoked with the "original" request.
	 * Then the provider is invoked, and the second handler is invoked with the second request that is based on the provider's return value.
	 */
	@Test
	public void invokeHandlers()
	{
		final VoidOrderAndRelatedDocsService orderWillBeVoidedHandlerRegistry = new VoidOrderAndRelatedDocsService(
				Optional.of(ImmutableList.of(voidSourceTableRelatedToOrderHandler, voidTableRelatedToOrderHandler)),
				Optional.of(ImmutableList.of(relatedTableForSourceTableProvider)));

		final VoidOrderAndRelatedDocsRequest sourceRequest = VoidOrderAndRelatedDocsRequest
				.builder()
				.orderId(OrderId.ofRepoId(10))
				.recordsToHandle(sourceTableRecordsToHandle)
				.build();

		// invoke method under test
		orderWillBeVoidedHandlerRegistry.invokeHandlers(sourceRequest);

		final IPair<RecordsToHandleKey, List<ITableRecordReference>> relatedTableRecordsToHandle = ImmutablePair.of(
				RecordsToHandleKey.of(RELATED_TABLE_NAME),
				ImmutableList.of(relatedReference));
		final VoidOrderAndRelatedDocsRequest relatedRequest = VoidOrderAndRelatedDocsRequest
				.builder()
				.orderId(OrderId.ofRepoId(10))
				.recordsToHandle(relatedTableRecordsToHandle)
				.build();

		assertThat(voidSourceTableRelatedToOrderHandler.getInvokeParameters()).containsOnly(sourceRequest);
		assertThat(voidTableRelatedToOrderHandler.getInvokeParameters())
				.as("relatedTableOrderWillBeVoidedHandler was retrieved for 'relatedRequest' via its RecordsToHandleKey and was its handle method was invoked with the request")
				.containsOnly(relatedRequest);

		assertThat(relatedTableForSourceTableProvider.getInvokeParameters()).containsOnly(ImmutableList.of(sourceTableReference));
	}

	/**
	 * One handler, one provider. The first handler is invoked with the "original" request.
	 * Then the provider is invoked, but there is no registered handler for the provider's records. Should be OK
	 */
	@Test
	public void invokeHandlers_single_Handler()
	{
		final VoidOrderAndRelatedDocsService orderWillBeVoidedHandlerRegistry = new VoidOrderAndRelatedDocsService(
				Optional.of(ImmutableList.of(voidSourceTableRelatedToOrderHandler)),
				Optional.of(ImmutableList.of(relatedTableForSourceTableProvider)));

		final VoidOrderAndRelatedDocsRequest sourceRequest = VoidOrderAndRelatedDocsRequest
				.builder()
				.orderId(OrderId.ofRepoId(10))
				.recordsToHandle(sourceTableRecordsToHandle)
				.build();

		// invoke method under test
		orderWillBeVoidedHandlerRegistry.invokeHandlers(sourceRequest);

		assertThat(voidSourceTableRelatedToOrderHandler.getInvokeParameters()).containsOnly(sourceRequest);
		assertThat(relatedTableForSourceTableProvider.getInvokeParameters()).containsOnly(ImmutableList.of(sourceTableReference));
	}

	@ToString
	private static class VoidSourceTableRelatedToOrderHandler implements VoidOrderAndRelatedDocsHandler
	{
		@Getter
		private List<VoidOrderAndRelatedDocsRequest> invokeParameters = new ArrayList<>();

		@Override
		public RecordsToHandleKey getRecordsToHandleKey()
		{
			return RecordsToHandleKey.of(SOURCE_TABLE_NAME);
		}

		@Override
		public void handleOrderVoided(VoidOrderAndRelatedDocsRequest request)
		{
			invokeParameters.add(request);
		}
	}

	@ToString
	private static class VoidTableRelatedToOrderHandler implements VoidOrderAndRelatedDocsHandler
	{
		@Getter
		private List<VoidOrderAndRelatedDocsRequest> invokeParameters = new ArrayList<>();

		@Override
		public RecordsToHandleKey getRecordsToHandleKey()
		{
			return RecordsToHandleKey.of(RELATED_TABLE_NAME);
		}

		@Override
		public void handleOrderVoided(VoidOrderAndRelatedDocsRequest request)
		{
			invokeParameters.add(request);
		}
	}

	@ToString // Related
	private static class RelatedTableForSourceTableProvider implements RelatedRecordsProvider
	{
		private final IPair<SourceRecordsKey, List<ITableRecordReference>> provideRelatedRecordsResult;

		private RelatedTableForSourceTableProvider(@NonNull final IPair<SourceRecordsKey, List<ITableRecordReference>> provideRelatedRecordsResult)
		{
			this.provideRelatedRecordsResult = provideRelatedRecordsResult;
		}

		@Getter
		private List<List<ITableRecordReference>> invokeParameters = new ArrayList<>();

		@Override
		public SourceRecordsKey getSourceRecordsKey()
		{
			return SourceRecordsKey.of(SOURCE_TABLE_NAME);
		}

		@Override
		public IPair<SourceRecordsKey, List<ITableRecordReference>> provideRelatedRecords(@NonNull final List<ITableRecordReference> records)
		{
			invokeParameters.add(records);
			return provideRelatedRecordsResult;
		}
	}
}
