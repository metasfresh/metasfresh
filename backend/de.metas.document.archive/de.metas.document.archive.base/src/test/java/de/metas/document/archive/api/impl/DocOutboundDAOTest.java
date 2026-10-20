package de.metas.document.archive.api.impl;

import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.engine.DocStatus;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link DocOutboundDAO#updateLogHeaderDocStatus(Object, TableRecordReference, DocStatus)} against the in-memory database.
 */
public class DocOutboundDAOTest
{
	private final IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void header_becomes_in_progress()
	{
		final TableRecordReference document = createDocument();
		final I_C_Doc_Outbound_Log log = createLog(document, DocStatus.Completed);

		docOutboundDAO.updateLogHeaderDocStatus(record(document), document, DocStatus.InProgress);

		InterfaceWrapperHelper.refresh(log);
		assertThat(log.getDocStatus()).isEqualTo(DocStatus.InProgress.getCode());
	}

	@Test
	public void lines_are_untouched()
	{
		final TableRecordReference document = createDocument();
		final I_C_Doc_Outbound_Log log = createLog(document, DocStatus.Completed);
		final I_C_Doc_Outbound_Log_Line line = InterfaceWrapperHelper.newInstance(I_C_Doc_Outbound_Log_Line.class);
		line.setC_Doc_Outbound_Log_ID(log.getC_Doc_Outbound_Log_ID());
		line.setAD_Table_ID(document.getAD_Table_ID());
		line.setRecord_ID(document.getRecord_ID());
		line.setDocStatus(DocStatus.Completed.getCode());
		InterfaceWrapperHelper.saveRecord(line);

		docOutboundDAO.updateLogHeaderDocStatus(record(document), document, DocStatus.InProgress);

		InterfaceWrapperHelper.refresh(line);
		assertThat(line.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());
	}

	@Test
	public void no_log_creates_nothing_and_does_not_fail()
	{
		final TableRecordReference document = createDocument();

		docOutboundDAO.updateLogHeaderDocStatus(record(document), document, DocStatus.InProgress);

		assertThat(queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class).create().count()).isZero();
		assertThat(queryBL.createQueryBuilder(I_C_Doc_Outbound_Log_Line.class).create().count()).isZero();
	}

	@Test
	public void only_the_log_of_the_given_record_is_changed()
	{
		final I_C_Doc_Outbound_Log otherLog = createLog(createDocument(), DocStatus.Completed);
		final TableRecordReference document = createDocument();
		createLog(document, DocStatus.Completed);

		docOutboundDAO.updateLogHeaderDocStatus(record(document), document, DocStatus.InProgress);

		InterfaceWrapperHelper.refresh(otherLog);
		assertThat(otherLog.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());
	}

	private static Object record(@NonNull final TableRecordReference document)
	{
		return document.getModel(PlainContextAware.newWithThreadInheritedTrx(), I_Test.class);
	}

	@NonNull
	private static TableRecordReference createDocument()
	{
		final I_Test record = InterfaceWrapperHelper.create(Env.getCtx(), I_Test.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.saveRecord(record);
		return TableRecordReference.of(record);
	}

	@NonNull
	private static I_C_Doc_Outbound_Log createLog(@NonNull final TableRecordReference document, @NonNull final DocStatus docStatus)
	{
		final I_C_Doc_Outbound_Log log = InterfaceWrapperHelper.newInstance(I_C_Doc_Outbound_Log.class);
		log.setAD_Table_ID(document.getAD_Table_ID());
		log.setRecord_ID(document.getRecord_ID());
		log.setDocStatus(docStatus.getCode());
		InterfaceWrapperHelper.saveRecord(log);
		return log;
	}
}
