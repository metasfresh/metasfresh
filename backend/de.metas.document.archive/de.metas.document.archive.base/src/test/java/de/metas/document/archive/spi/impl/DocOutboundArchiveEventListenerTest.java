package de.metas.document.archive.spi.impl;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.attachments.AttachmentEntryService;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRegistry;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.engine.DocStatus;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Properties;

public class DocOutboundArchiveEventListenerTest
{
	private DocOutboundArchiveEventListener archiveBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		archiveBL = new DocOutboundArchiveEventListener(
				AttachmentEntryService.createInstanceForUnitTesting(),
				new DocOutboundLogMailRecipientRegistry(Optional.empty()));
		Services.get(IArchiveEventManager.class).registerArchiveEventListener(archiveBL);
	}

	/**
	 * @implSpec  <a href="http://dewiki908/mediawiki/index.php/03918_Massendruck_f%C3%BCr_Mahnungen_%282013021410000132%29#IT2_-_G01_-_Mass_Printing">task</a>
	 */
	@Test
	public void createLogLine_DocumentNo_ForDocuments()
	{
		final String documentNoExpected = "DocumentNotToBeUsed";

		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(Env.getCtx(), I_C_BPartner.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(bpartner);
		final I_AD_User user = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_User.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(user);

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice.setDocumentNo(documentNoExpected);
		invoice.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		invoice.setAD_User_ID(user.getAD_User_ID());
		invoice.setDocStatus(DocStatus.Reversed.getCode());
		invoice.setC_Async_Batch_ID(1);
		InterfaceWrapperHelper.save(invoice);

		final I_AD_Archive archive = createArchive(invoice);

		final I_C_Doc_Outbound_Log_Line docExchangeLine = archiveBL.createLogLine(archive);

		Assertions.assertEquals(documentNoExpected, docExchangeLine.getDocumentNo(), "Invalid DocumentNo");

		final I_C_Doc_Outbound_Log docOutboundLog = docExchangeLine.getC_Doc_Outbound_Log();
		Assertions.assertEquals(docOutboundLog.getC_Async_Batch_ID(),invoice.getC_Async_Batch_ID());
	}

	/**
	 * @implSpec <a href="http://dewiki908/mediawiki/index.php/03918_Massendruck_f%C3%BCr_Mahnungen_%282013021410000132%29#IT2_-_G01_-_Mass_Printing">task</a>
	 */
	@Test
	public void createLogLine_DocumentNo_ForNonDocuments()
	{
		final I_Test record = InterfaceWrapperHelper.create(Env.getCtx(), I_Test.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);

		final I_AD_Archive archive = createArchive(record);

		final I_C_Doc_Outbound_Log_Line docExchangeLine = archiveBL.createLogLine(archive);

		// Expected document is record ID because record does not have any DocumentNo, Value or Name fields completed
		final String documentNoExpected = String.valueOf(record.getTest_ID());
		Assertions.assertEquals(documentNoExpected, docExchangeLine.getDocumentNo(), "Log line's DocumentNo shall be record's ID");
	}

	private I_AD_Archive createArchive(@NonNull final Object model)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final int adTableId = adTableDAO.retrieveTableId(tableName);

		final int recordId = InterfaceWrapperHelper.getId(model);

		final I_AD_Archive archive = InterfaceWrapperHelper.create(ctx, I_AD_Archive.class, trxName);
		archive.setAD_Table_ID(adTableId);
		archive.setRecord_ID(recordId);
		archive.setName("Dummy_" + model);
		archive.setIsActive(true);
		archive.setIsReport(false);

		InterfaceWrapperHelper.getValueOptional(model, I_C_Async_Batch.COLUMNNAME_C_Async_Batch_ID)
				.map(asyncBatchId -> (Integer)asyncBatchId)
				.ifPresent(archive::setC_Async_Batch_ID);

		InterfaceWrapperHelper.save(archive);
		return archive;
	}
}
