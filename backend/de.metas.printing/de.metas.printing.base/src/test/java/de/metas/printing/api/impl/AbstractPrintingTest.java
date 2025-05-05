package de.metas.printing.api.impl;

import de.metas.document.archive.api.ArchiveFileNameService;
import de.metas.printing.HardwarePrinterRepository;
import de.metas.printing.api.IPrintPackageBL;
import de.metas.printing.printingdata.PrintingDataFactory;
import de.metas.workplace.WorkplaceRepository;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.WorkplaceUserAssignRepository;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;

import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.impl.PlainInvoiceDAO;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.spi.impl.DocumentPrintingQueueHandler;
import de.metas.util.Services;

public abstract class AbstractPrintingTest
{
	@Rule
	public TestName testName = new TestName();
	/**
	 * Watches current test and dumps the database to console in case of failure
	 */
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

		final ArchiveFileNameService archiveFileNameService = new ArchiveFileNameService();
		SpringContextHolder.registerJUnitBean(archiveFileNameService);

		Services.registerService(IPrintPackageBL.class, new PrintPackageBL(new PrintingDataFactory(new HardwarePrinterRepository(), archiveFileNameService)));
		SpringContextHolder.registerJUnitBean(new PrintingDataFactory(new HardwarePrinterRepository(), archiveFileNameService));

		final WorkplaceService workplaceService = new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository());
		SpringContextHolder.registerJUnitBean(workplaceService);

		afterSetup();
	}

	/**
	 * Called after {@link #setup()}.
	 * <p>
	 * To be implemented by extending classes.
	 */
	protected void afterSetup()
	{
		// nothing
	}
}
