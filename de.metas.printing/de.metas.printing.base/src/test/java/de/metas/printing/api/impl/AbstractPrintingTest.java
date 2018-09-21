package de.metas.printing.api.impl;

/*
 * #%L
 * de.metas.printing.base
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


import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.invoice.service.impl.PlainInvoiceDAO;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;

import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.spi.impl.DocumentPrintingQueueHandler;
import de.metas.util.Services;

public abstract class AbstractPrintingTest
{
	@Rule
	public TestName testName = new TestName();
	/** Watches current test and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	public Helper helper;

	protected PrintJobBL printJobBL;

	@BeforeClass
	public static void staticInit()
	{
		Helper.staticInit();
	}

	@Before
	public final void setup()
	{
		helper = new Helper(testName);
		helper.setup();

		printJobBL = new PrintJobBL();
		Services.registerService(IPrintJobBL.class, printJobBL);

		// Configure printing like we do in de.metas.printing.model.validator.Main
		Services.get(IPrintingQueueBL.class).registerHandler(DocumentPrintingQueueHandler.instance);

		Services.registerService(IInvoiceDAO.class, new PlainInvoiceDAO());

		afterSetup();
	}

	/**
	 * Called after {@link #setup()}.
	 * 
	 * To be implemented by extending classes.
	 */
	protected void afterSetup()
	{
		// nothing
	}
}
