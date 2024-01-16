package de.metas.async;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2021 metas GmbH
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

import de.metas.async.event.WorkpackageProcessedEvent;
import de.metas.async.event.WorkpackagesProcessedWaiter;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.event.Topic;
import de.metas.event.Type;

public final class Async_Constants
{
	public static final String MSG_WORKPACKAGES_CREATED = "de.metas.async.C_QueueC_WorkPackages_Created_1P";

	public static final String ENTITY_TYPE = "de.metas.async";

	/**
	 * See {@link de.metas.async.api.IWorkPackageBuilder#setCorrelationId(java.util.UUID)}
	 */
	public static final String ASYNC_PARAM_CORRELATION_UUID = "CorrelationUUID";

	/**
	 * If a package has been skipped by a {@link IWorkpackageProcessor}, then this constant is the default timeout
	 * before that work package is once again processed.
	 */
	public static final int DEFAULT_RETRY_TIMEOUT_MILLIS = 5000;

	public static final String C_Async_Batch = "C_Async_Batch";

	public static final Topic WORKPACKAGE_ERROR_USER_NOTIFICATIONS_TOPIC = Topic.builder()
			.name("de.metas.async.UserNotifications.WorkpackageProcessingErrors")
			.type(Type.REMOTE)
			.build();

	/**
	 * metasfresh posts {@link WorkpackageProcessedEvent}s to this topic when a workpackage that has a corelation-id set was processed. Also see {@link WorkpackagesProcessedWaiter}.
	 */
	public static final Topic WORKPACKAGE_LIFECYCLE_TOPIC = Topic.builder()
			.name("de.metas.async.WorkpackageLifeCycle")
			.type(Type.REMOTE)
			.build();

	public static final int C_OlCandProcessor_ID_Default = 1000003;

	public static final String C_Async_Batch_InternalName_Default = "Default";

	public static final String C_Async_Batch_InternalName_OLCand_Processing = "OLCand_Processing";
	public static final String C_Async_Batch_InternalName_ShipmentSchedule = "ShipmentSchedule_Processing";
	public static final String C_Async_Batch_InternalName_InvoiceCandidate_Processing = "InvoiceCandidate_Processing";
	public static final String C_Async_Batch_InternalName_EnqueueScheduleForOrder = "EnqueueScheduleForOrder";
	public static final String C_Async_Batch_InternalName_EnqueueInvoiceCandidateCreation = "EnqueueInvoiceCandidateCreation";
	public static final String C_Async_Batch_InternalName_ProcessOLCands = "ProcessOLCands";
	public static final String C_Async_Batch_InternalName_AutomaticallyInvoicePdfPrinting = "AutomaticallyInvoicePdfPrinting";

	public static final String SYS_Config_SKIP_WP_PROCESSOR_FOR_AUTOMATION = "SKIP_WP_PROCESSOR_FOR_AUTOMATION";
	public static final String SYS_Config_WaitTimeOutMS = "de.metas.async.AsyncBatchObserver.WaitTimeOutMS";
	public static final int SYS_Config_WaitTimeOutMS_DEFAULT_VALUE = 1000 * 60 * 5;
}
