package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.IDunningCandidateProducer;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.exception.DunningException;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.util.Check;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

public class DefaultDunningCandidateProducerFactoryTest
{
	public static class DummyDunningCandidateProducer implements IDunningCandidateProducer
	{
		private final boolean isHandledReturnValue;

		public DummyDunningCandidateProducer(boolean isHandledReturnValue)
		{
			this.isHandledReturnValue = isHandledReturnValue;
		}

		@Override
		public boolean isHandled(IDunnableDoc sourceDoc)
		{
			return isHandledReturnValue;
		}

		@Override
		public I_C_Dunning_Candidate createDunningCandidate(IDunningContext context, IDunnableDoc sourceDoc)
		{
			throw new UnsupportedOperationException();
		}
	}

	public static class AlwaysHandledDunningCandidateProducer extends DummyDunningCandidateProducer
	{
		public AlwaysHandledDunningCandidateProducer()
		{
			super(true);
		}
	}

	public static class AlwaysHandledDunningCandidateProducer2 extends DummyDunningCandidateProducer
	{
		public AlwaysHandledDunningCandidateProducer2()
		{
			super(true);
		}
	}

	public static class AlwaysNotHandledDunningCandidateProducer extends DummyDunningCandidateProducer
	{
		public AlwaysNotHandledDunningCandidateProducer()
		{
			super(false);
		}
	}

	public static class InitializationErrorDunningCandidateProducer extends DummyDunningCandidateProducer
	{
		public InitializationErrorDunningCandidateProducer()
		{
			super(true);
			throw new RuntimeException("Initialization error thrown on purpose"); // NOPMD by tsa on 3/24/13 4:10 PM
		}
	}

	public static class EmptyDunnableDoc implements IDunnableDoc
	{

		@Override
		public int getAD_Client_ID()
		{
			return 0;
		}

		@Override
		public int getAD_Org_ID()
		{
			return 0;
		}

		@Override
		public int getC_BPartner_ID()
		{
			return 0;
		}

		@Override
		public int getC_BPartner_Location_ID()
		{
			return 0;
		}

		@Override
		public int getContact_ID()
		{
			return 0;
		}

		@Override
		public int getC_Currency_ID()
		{
			return 0;
		}

		@Override
		public BigDecimal getTotalAmt()
		{
			return null;
		}

		@Override
		public BigDecimal getOpenAmt()
		{
			return null;
		}

		@Override
		public Date getDueDate()
		{
			return null;
		}

		@Override
		public Date getGraceDate()
		{
			return null;
		}

		@Override
		public int getDaysDue()
		{
			return 0;
		}

		@Override
		public String getTableName()
		{
			return null;
		}

		@Override
		public int getRecordId()
		{
			return 0;
		}

		@Override
		public boolean isInDispute()
		{
			return false;
		}

		@Override
		public String getDocumentNo()
		{
			return null;
		}

	}

	private DefaultDunningCandidateProducerFactory factory;

	@BeforeEach
	public void createFactory()
	{
		factory = new DefaultDunningCandidateProducerFactory();
	}

	@Test
	public void registerDunningCandidateProducer_ClassInitializationError()
	{
		// shall throw exception because class cannot be initialized
		Assertions.assertThrows(DunningException.class, () -> factory.registerDunningCandidateProducer(InitializationErrorDunningCandidateProducer.class));
	}

	@Test
	public void registerDunningCandidateProducer_SameClassMultipleTimes()
	{
		factory.registerDunningCandidateProducer(AlwaysHandledDunningCandidateProducer.class);
		Assertions.assertEquals(1, factory.getProducerClasses().size(), "Invalid producer classes registered: " + factory.getProducerClasses());

		// registering second time, same class => nothing shall happen
		factory.registerDunningCandidateProducer(AlwaysHandledDunningCandidateProducer.class);
		Assertions.assertEquals(1, factory.getProducerClasses().size(), "Invalid producer classes registered: " + factory.getProducerClasses());
	}

	@Test
	public void getDunningCandidateProducer_NullParam()
	{
		Check.setDefaultExClass(AdempiereException.class);
		Assertions.assertThrows(AdempiereException.class, () -> factory.getDunningCandidateProducer(null));
	}

	@Test
	public void getDunningCandidateProducer()
	{
		factory.registerDunningCandidateProducer(AlwaysNotHandledDunningCandidateProducer.class);
		factory.registerDunningCandidateProducer(AlwaysHandledDunningCandidateProducer.class);

		// no producer found exception shall be thrown:
		final IDunningCandidateProducer producer = factory.getDunningCandidateProducer(new EmptyDunnableDoc());
		Assertions.assertTrue(producer instanceof AlwaysHandledDunningCandidateProducer, "Invalid producer: " + producer);
	}

	@Test
	public void getDunningCandidateProducer_NoHandlerFound()
	{
		factory.registerDunningCandidateProducer(AlwaysNotHandledDunningCandidateProducer.class);

		// no producer found exception shall be thrown:
		Assertions.assertThrows(DunningException.class, () -> factory.getDunningCandidateProducer(new EmptyDunnableDoc()));
	}

	@Test
	public void getDunningCandidateProducer_MultipleHandlersFound()
	{
		factory.registerDunningCandidateProducer(AlwaysHandledDunningCandidateProducer.class);
		factory.registerDunningCandidateProducer(AlwaysHandledDunningCandidateProducer2.class);

		// shall throw exception because we found multiple producers
		Assertions.assertThrows(DunningException.class, () -> factory.getDunningCandidateProducer(new EmptyDunnableDoc()));
	}

	@Test
	public void test_toString()
	{
		Assertions.assertNotNull(factory.toString());
	}
}
