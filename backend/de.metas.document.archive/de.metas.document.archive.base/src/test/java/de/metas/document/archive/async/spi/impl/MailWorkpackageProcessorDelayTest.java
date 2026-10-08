package de.metas.document.archive.async.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.common.util.time.SystemTime;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.notification.delay.DocOutboundNotificationDelayHandler;
import de.metas.document.archive.notification.delay.DocOutboundNotificationDelayService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MailWorkpackageProcessorDelayTest
{
	private DocOutboundNotificationDelayService service;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		// Build service with a handler that delays invoices
		final DocOutboundNotificationDelayHandler invoiceHandler = new DocOutboundNotificationDelayHandler()
		{
			@Override
			public String getTableName() { return I_C_Invoice.Table_Name; }

			@Override
			public boolean shouldDelaySending(final I_C_Doc_Outbound_Log log) { return true; }
		};
		service = new DocOutboundNotificationDelayService(Optional.of(ImmutableList.of(invoiceHandler)));
	}

	@AfterEach
	void resetTime()
	{
		SystemTime.resetTimeSource();
	}

	private List<I_C_Doc_Outbound_Log_Line> buildLogLines()
	{
		final I_C_Doc_Outbound_Log log = InterfaceWrapperHelper.newInstance(I_C_Doc_Outbound_Log.class);
		log.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Invoice.class));
		log.setRecord_ID(1);
		InterfaceWrapperHelper.save(log);

		final I_C_Doc_Outbound_Log_Line logLine = InterfaceWrapperHelper.newInstance(I_C_Doc_Outbound_Log_Line.class);
		logLine.setC_Doc_Outbound_Log_ID(log.getC_Doc_Outbound_Log_ID());
		InterfaceWrapperHelper.save(logLine);

		return ImmutableList.of(logLine);
	}

	/**
	 * Case A: workpackage was just created (elapsed ~0ms), delay handler says true
	 * => must throw WorkpackageSkipRequestException
	 */
	@Test
	void caseA_withinTimeout_throws()
	{
		final long nowMillis = System.currentTimeMillis();
		// Use the same millis for both Created and the fixed time source so elapsed == 0 exactly
		SystemTime.setFixedTimeSource(ZonedDateTime.ofInstant(java.time.Instant.ofEpochMilli(nowMillis), ZoneId.systemDefault()));

		final I_C_Queue_WorkPackage workpackage = InterfaceWrapperHelper.newInstance(I_C_Queue_WorkPackage.class);
		InterfaceWrapperHelper.save(workpackage);
		// Set Created to "now" so elapsed time is ~0ms
		InterfaceWrapperHelper.setValue(workpackage, I_C_Queue_WorkPackage.COLUMNNAME_Created, new Timestamp(nowMillis));

		final List<I_C_Doc_Outbound_Log_Line> logLines = buildLogLines();

		assertThatThrownBy(() -> MailWorkpackageProcessor.assertNotificationReadyOrSkip(service, workpackage, logLines, 60_000))
				.isInstanceOf(WorkpackageSkipRequestException.class);
	}

	/**
	 * Case B: workpackage was created 61 seconds ago (past 60s timeout)
	 * => must NOT throw (sends anyway)
	 */
	@Test
	void caseB_pastTimeout_doesNotThrow()
	{
		final long nowMillis = System.currentTimeMillis();
		SystemTime.setFixedTimeSource(ZonedDateTime.ofInstant(java.time.Instant.ofEpochMilli(nowMillis), ZoneId.systemDefault()));

		final I_C_Queue_WorkPackage workpackage = InterfaceWrapperHelper.newInstance(I_C_Queue_WorkPackage.class);
		InterfaceWrapperHelper.save(workpackage);
		// Set Created to 61 seconds ago => elapsed >= 60_000ms timeout
		InterfaceWrapperHelper.setValue(workpackage, I_C_Queue_WorkPackage.COLUMNNAME_Created, new Timestamp(nowMillis - 61_000L));

		final List<I_C_Doc_Outbound_Log_Line> logLines = buildLogLines();

		assertThatCode(() -> MailWorkpackageProcessor.assertNotificationReadyOrSkip(service, workpackage, logLines, 60_000))
				.doesNotThrowAnyException();
	}

	/**
	 * Case C: handler returns false (no delay needed) => does not throw regardless of time
	 */
	@Test
	void caseC_noDelayNeeded_doesNotThrow()
	{
		final DocOutboundNotificationDelayService noDelayService =
				new DocOutboundNotificationDelayService(Optional.empty());

		final I_C_Queue_WorkPackage workpackage = InterfaceWrapperHelper.newInstance(I_C_Queue_WorkPackage.class);
		InterfaceWrapperHelper.save(workpackage);

		final List<I_C_Doc_Outbound_Log_Line> logLines = buildLogLines();

		assertThatCode(() -> MailWorkpackageProcessor.assertNotificationReadyOrSkip(noDelayService, workpackage, logLines, 60_000))
				.doesNotThrowAnyException();
	}

	/**
	 * Case D: handler IS registered for I_C_Invoice but returns false (no delay) => does not throw.
	 * Covers the "handler exists but says no-delay" path that Case C (no handlers at all) did not.
	 */
	@Test
	void caseD_handlerReturnsFalse_doesNotThrow()
	{
		final DocOutboundNotificationDelayHandler noDelayHandler = new DocOutboundNotificationDelayHandler()
		{
			@Override
			public String getTableName() { return I_C_Invoice.Table_Name; }

			@Override
			public boolean shouldDelaySending(final I_C_Doc_Outbound_Log log) { return false; }
		};
		final DocOutboundNotificationDelayService serviceWithNoDelayHandler =
				new DocOutboundNotificationDelayService(Optional.of(ImmutableList.of(noDelayHandler)));

		final I_C_Queue_WorkPackage workpackage = InterfaceWrapperHelper.newInstance(I_C_Queue_WorkPackage.class);
		InterfaceWrapperHelper.save(workpackage);

		final List<I_C_Doc_Outbound_Log_Line> logLines = buildLogLines();

		assertThatCode(() -> MailWorkpackageProcessor.assertNotificationReadyOrSkip(serviceWithNoDelayHandler, workpackage, logLines, 60_000))
				.doesNotThrowAnyException();
	}

	/**
	 * Case E: maxDelayMillis = 0 (SysConfig mailNotificationMaxDelayMillis=0, feature off) => never
	 * delays, even though the handler says true and the workpackage was just created.
	 */
	@Test
	void caseE_maxDelayZero_doesNotThrow()
	{
		final long nowMillis = System.currentTimeMillis();
		SystemTime.setFixedTimeSource(ZonedDateTime.ofInstant(java.time.Instant.ofEpochMilli(nowMillis), ZoneId.systemDefault()));

		final I_C_Queue_WorkPackage workpackage = InterfaceWrapperHelper.newInstance(I_C_Queue_WorkPackage.class);
		InterfaceWrapperHelper.save(workpackage);
		InterfaceWrapperHelper.setValue(workpackage, I_C_Queue_WorkPackage.COLUMNNAME_Created, new Timestamp(nowMillis));

		final List<I_C_Doc_Outbound_Log_Line> logLines = buildLogLines();

		// service delays (handler returns true) and elapsed ~0, but maxDelayMillis=0 disables delaying entirely
		assertThatCode(() -> MailWorkpackageProcessor.assertNotificationReadyOrSkip(service, workpackage, logLines, 0))
				.doesNotThrowAnyException();
	}
}
