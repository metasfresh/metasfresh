package de.metas.document.archive.interceptor;

import de.metas.document.DocBaseType;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.impl.PlainDocumentBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_Test;
import org.compiere.model.ModelValidationEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the reactivation hook of {@link DocOutboundProducerValidator}: a document the producer's configuration accepts
 * sets its outbound log header to in-progress and leaves the lines alone; a document of another base type is not touched.
 * A document of a table without any outbound configuration has no validator at all (one is registered per
 * {@link I_C_Doc_Outbound_Config}), so nothing is called for it.
 */
public class DocOutboundProducerValidatorReactivateTest
{
	private DocOutboundProducerValidator validator;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		PlainDocumentBL.isDocumentTableResponse = true;

		final I_C_Doc_Outbound_Config config = InterfaceWrapperHelper.newInstance(I_C_Doc_Outbound_Config.class);
		config.setAD_Table_ID(TableRecordReference.of(I_C_Order.Table_Name, 1).getAD_Table_ID());
		config.setDocBaseType(DocBaseType.SalesOrder.getCode());
		InterfaceWrapperHelper.saveRecord(config);

		validator = new DocOutboundProducerValidator(ModelValidationEngine.get(), config);
	}

	@AfterEach
	public void cleanup()
	{
		PlainDocumentBL.isDocumentTableResponse = null;
	}

	@Test
	public void accepted_document_sets_header_to_in_progress_and_keeps_lines()
	{
		final I_C_Order order = createOrder(DocBaseType.SalesOrder);
		final I_C_Doc_Outbound_Log log = createLog(order);
		final I_C_Doc_Outbound_Log_Line line = createLine(log, order);

		validator.onDocumentReactivated(order);

		InterfaceWrapperHelper.refresh(log);
		InterfaceWrapperHelper.refresh(line);
		assertThat(log.getDocStatus()).isEqualTo(DocStatus.InProgress.getCode());
		assertThat(line.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());
	}

	@Test
	public void document_of_another_base_type_is_not_touched()
	{
		final I_C_Order order = createOrder(DocBaseType.PurchaseOrder);
		final I_C_Doc_Outbound_Log log = createLog(order);
		final I_C_Doc_Outbound_Log_Line line = createLine(log, order);

		validator.onDocumentReactivated(order);

		InterfaceWrapperHelper.refresh(log);
		InterfaceWrapperHelper.refresh(line);
		assertThat(log.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());
		assertThat(line.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());
	}

	@Test
	public void document_of_another_table_is_not_touched()
	{
		final I_Test other = InterfaceWrapperHelper.newInstance(I_Test.class);
		InterfaceWrapperHelper.saveRecord(other);
		final TableRecordReference ref = TableRecordReference.of(other);
		final I_C_Doc_Outbound_Log log = InterfaceWrapperHelper.newInstance(I_C_Doc_Outbound_Log.class);
		log.setAD_Table_ID(ref.getAD_Table_ID());
		log.setRecord_ID(ref.getRecord_ID());
		log.setDocStatus(DocStatus.Completed.getCode());
		InterfaceWrapperHelper.saveRecord(log);

		validator.onDocumentReactivated(other);

		InterfaceWrapperHelper.refresh(log);
		assertThat(log.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());
	}

	@Test
	public void accepted_document_without_log_creates_nothing()
	{
		final I_C_Order order = createOrder(DocBaseType.SalesOrder);

		validator.onDocumentReactivated(order);

		assertThat(Services.get(IQueryBL.class).createQueryBuilder(I_C_Doc_Outbound_Log.class).create().count()).isZero();
	}

	@NonNull
	private static I_C_Order createOrder(@NonNull final DocBaseType docBaseType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setDocBaseType(docBaseType.getCode());
		InterfaceWrapperHelper.saveRecord(docType);

		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_DocType_ID(docType.getC_DocType_ID());
		order.setC_DocTypeTarget_ID(docType.getC_DocType_ID());
		order.setDocStatus(DocStatus.Completed.getCode());
		InterfaceWrapperHelper.saveRecord(order);
		return order;
	}

	@NonNull
	private static I_C_Doc_Outbound_Log createLog(@NonNull final I_C_Order order)
	{
		final TableRecordReference ref = TableRecordReference.of(order);
		final I_C_Doc_Outbound_Log log = InterfaceWrapperHelper.newInstance(I_C_Doc_Outbound_Log.class);
		log.setAD_Table_ID(ref.getAD_Table_ID());
		log.setRecord_ID(ref.getRecord_ID());
		log.setDocStatus(DocStatus.Completed.getCode());
		InterfaceWrapperHelper.saveRecord(log);
		return log;
	}

	@NonNull
	private static I_C_Doc_Outbound_Log_Line createLine(@NonNull final I_C_Doc_Outbound_Log log, @NonNull final I_C_Order order)
	{
		final I_C_Doc_Outbound_Log_Line line = InterfaceWrapperHelper.newInstance(I_C_Doc_Outbound_Log_Line.class);
		line.setC_Doc_Outbound_Log_ID(log.getC_Doc_Outbound_Log_ID());
		line.setAD_Table_ID(log.getAD_Table_ID());
		line.setRecord_ID(log.getRecord_ID());
		line.setDocStatus(DocStatus.Completed.getCode());
		InterfaceWrapperHelper.saveRecord(line);
		return line;
	}
}
