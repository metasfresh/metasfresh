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
import de.metas.order.restart.VoidOrderWithRelatedDocsHandler.RecordsToHandleKey;
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

public class OrderWillBeVoidedHandlerRegistryTest
{
	private static final String RELATED_TABLE_NAME = "relatedRecordsTable";

	private static final String SOURCE_TABLE_NAME = "sourceTable";

	SourceTableRecordsProvider sourceTableRecordsProvider;

	SourceTableOrderWillBeVoidedHandler sourceTableOrderWillBeVoidedHandler;

	RelatedTableOrderWillBeVoidedHandler relatedTableOrderWillBeVoidedHandler;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		sourceTableOrderWillBeVoidedHandler = new SourceTableOrderWillBeVoidedHandler();
		relatedTableOrderWillBeVoidedHandler = new RelatedTableOrderWillBeVoidedHandler();
	}

	/**
	 * Two handler, one provider. The first handler is invoked with the "original" request.
	 * Then the provider is invoked, and the second handler is invoked with the second request that is based on the provider's return value.
	 */
	@Test
	public void invokeHandlers()
	{
		final TableRecordReference sourceTableReference = TableRecordReference.of(SOURCE_TABLE_NAME, 20);
		final IPair<RecordsToHandleKey, List<ITableRecordReference>> sourceTableRecordsToHandle = ImmutablePair.of(
				RecordsToHandleKey.of(SOURCE_TABLE_NAME),
				ImmutableList.of(sourceTableReference));

		final TableRecordReference relatedReference = TableRecordReference.of(RELATED_TABLE_NAME, 30);

		final IPair<SourceRecordsKey, List<ITableRecordReference>> provideRelatedRecordsResult = ImmutablePair.of(
				SourceRecordsKey.of(RELATED_TABLE_NAME),
				ImmutableList.of(relatedReference));
		sourceTableRecordsProvider = new SourceTableRecordsProvider(provideRelatedRecordsResult);

		final VoidOrderWithRelatedDocsService orderWillBeVoidedHandlerRegistry = new VoidOrderWithRelatedDocsService(
				Optional.of(ImmutableList.of(sourceTableOrderWillBeVoidedHandler, relatedTableOrderWillBeVoidedHandler)),
				Optional.of(ImmutableList.of(sourceTableRecordsProvider)));

		final VoidOrderWithRelatedDocsRequest sourceRequest = VoidOrderWithRelatedDocsRequest
				.builder()
				.orderId(OrderId.ofRepoId(10))
				.recordsToHandle(sourceTableRecordsToHandle)
				.build();

		// invoke method under test
		orderWillBeVoidedHandlerRegistry.invokeHandlers(sourceRequest);

		final IPair<RecordsToHandleKey, List<ITableRecordReference>> relatedTableRecordsToHandle = ImmutablePair.of(
				RecordsToHandleKey.of(RELATED_TABLE_NAME),
				ImmutableList.of(relatedReference));
		final VoidOrderWithRelatedDocsRequest relatedRequest = VoidOrderWithRelatedDocsRequest
				.builder()
				.orderId(OrderId.ofRepoId(10))
				.recordsToHandle(relatedTableRecordsToHandle)
				.build();

		assertThat(sourceTableOrderWillBeVoidedHandler.getInvokeParameters()).containsOnly(sourceRequest);
		assertThat(relatedTableOrderWillBeVoidedHandler.getInvokeParameters())
				.as("relatedTableOrderWillBeVoidedHandler was retrieved for 'relatedRequest' via its RecordsToHandleKey and was its handle method was invoked with the request")
				.containsOnly(relatedRequest);

		assertThat(sourceTableRecordsProvider.getInvokeParameters()).containsOnly(ImmutableList.of(sourceTableReference));
	}

	@ToString
	private static class SourceTableOrderWillBeVoidedHandler implements VoidOrderWithRelatedDocsHandler
	{
		@Getter
		private List<VoidOrderWithRelatedDocsRequest> invokeParameters = new ArrayList<>();

		@Override
		public RecordsToHandleKey getRecordsToHandleKey()
		{
			return RecordsToHandleKey.of(SOURCE_TABLE_NAME);
		}

		@Override
		public void handleOrderVoided(VoidOrderWithRelatedDocsRequest request)
		{
			invokeParameters.add(request);
		}
	}

	@ToString
	private static class RelatedTableOrderWillBeVoidedHandler implements VoidOrderWithRelatedDocsHandler
	{
		@Getter
		private List<VoidOrderWithRelatedDocsRequest> invokeParameters = new ArrayList<>();

		@Override
		public RecordsToHandleKey getRecordsToHandleKey()
		{
			return RecordsToHandleKey.of(RELATED_TABLE_NAME);
		}

		@Override
		public void handleOrderVoided(VoidOrderWithRelatedDocsRequest request)
		{
			invokeParameters.add(request);
		}
	}

	@ToString
	private static class SourceTableRecordsProvider implements RelatedRecordsProvider
	{
		private final IPair<SourceRecordsKey, List<ITableRecordReference>> provideRelatedRecordsResult;

		private SourceTableRecordsProvider(@NonNull final IPair<SourceRecordsKey, List<ITableRecordReference>> provideRelatedRecordsResult)
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
