package de.metas.handlingunits.attribute.impl;

import org.compiere.model.I_M_Attribute;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;

public class HUTransactionAttributeBuilderTest extends AbstractHUTest
{
	IHUAttributeTransferRequest request;
	IHUAttributeTransferStrategy transferStrategy;
	I_M_Attribute attribute;

	@Test
	public void testTransferCalled()
	{
		final IHUTransactionAttributeBuilder trxAttributeBuilder = new HUTransactionAttributeBuilder(helper.getHUContext());

		createExpectations(true, true); // targetHasAttribute, transferable

		trxAttributeBuilder.transferAttributes(request);

		createVerifications(1); // noOfTimesTransferCalled
	}

	@Test
	public void testTransferNotCalled()
	{
		final IHUTransactionAttributeBuilder trxAttributeBuilder = new HUTransactionAttributeBuilder(helper.getHUContext());

		createExpectations(true, false); // targetHasAttribute, transferable

		trxAttributeBuilder.transferAttributes(request);

		createVerifications(0); // noOfTimesTransferCalled
	}

	@Test
	public void testTargetDoesNotHaveAttribute()
	{
		final IHUTransactionAttributeBuilder trxAttributeBuilder = new HUTransactionAttributeBuilder(helper.getHUContext());

		createExpectations(false, true); // targetHasAttribute, transferable

		trxAttributeBuilder.transferAttributes(request);

		createVerifications(0); // noOfTimesTransferCalled
	}

	private void createExpectations(final boolean targetHasAttribute, final boolean transferable)
	{
		attribute = Mockito.mock(I_M_Attribute.class);

		transferStrategy = Mockito.mock(IHUAttributeTransferStrategy.class);

		final IAttributeStorage attributesFrom = Mockito.mock(IAttributeStorage.class);
		Mockito.doReturn(ImmutableList.of(attribute)).when(attributesFrom).getAttributes();
		Mockito.doReturn(transferStrategy).when(attributesFrom).retrieveTransferStrategy(attribute);

		final IAttributeStorage attributesTo = Mockito.mock(IAttributeStorage.class);
		Mockito.doReturn(targetHasAttribute).when(attributesTo).hasAttribute(attribute);

		request = Mockito.mock(IHUAttributeTransferRequest.class);
		Mockito.doReturn(attributesFrom).when(request).getAttributesFrom();
		Mockito.doReturn(attributesTo).when(request).getAttributesTo();

		Mockito.doReturn(transferable).when(transferStrategy).isTransferable(request, attribute);
	}

	private void createVerifications(final int noOfTimesTransferCalled)
	{
		Mockito.verify(transferStrategy, Mockito.times(noOfTimesTransferCalled))
				.transferAttribute(request, attribute);
	}

	@Override
	protected void initialize()
	{
		// nothing
	}
}
