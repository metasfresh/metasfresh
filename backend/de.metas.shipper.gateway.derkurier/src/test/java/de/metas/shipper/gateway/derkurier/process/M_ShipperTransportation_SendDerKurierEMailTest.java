/*
 * #%L
 * de.metas.shipper.gateway.derkurier
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.shipper.gateway.derkurier.process;

import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.email.EMailAddress;
import de.metas.email.mailboxes.MailboxId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.SelectionSize;
import de.metas.shipper.gateway.derkurier.misc.DerKurierDeliveryOrderEmailer;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfig;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfigRepository;
import de.metas.shipper.gateway.derkurier.misc.ParcelNumberGenerator;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * {@code M_ShipperTransportation_SendDerKurierEMail} (the "Der Kurier" mail action) is a transport-order-only
 * action - it must not be offered on a delivery instruction, and a plain, otherwise-eligible transport order must
 * be completely unaffected (a no-op) by the added guard.
 */
class M_ShipperTransportation_SendDerKurierEMailTest
{
	private M_ShipperTransportation_SendDerKurierEMail process;
	private DerKurierShipperConfigRepository configRepository;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(ShipperTransportationDocSubTypeGuard.class, new ShipperTransportationDocSubTypeGuard());
		SpringContextHolder.registerJUnitBean(DerKurierDeliveryOrderEmailer.class, mock(DerKurierDeliveryOrderEmailer.class));

		// DerKurierShipperConfig's constructor builds a real ParcelNumberGenerator even when unused by
		// this test, which needs these two document-numbering services - stub them so building the
		// config below does not need Spring DI (getNextParcelNumber() is never called here)
		final IDocumentNoBuilder documentNoBuilder = mock(IDocumentNoBuilder.class, Mockito.RETURNS_SELF);
		final IDocumentNoBuilderFactory documentNoBuilderFactory = mock(IDocumentNoBuilderFactory.class);
		when(documentNoBuilderFactory.createDocumentNoBuilder()).thenReturn(documentNoBuilder);
		Services.registerService(IDocumentNoBuilderFactory.class, documentNoBuilderFactory);
		final IDocumentSequenceDAO documentSequenceDAO = mock(IDocumentSequenceDAO.class);
		when(documentSequenceDAO.retriveDocumentSequenceInfo(anyInt()))
				.thenReturn(DocumentSequenceInfo.builder().adSequenceId(ParcelNumberGenerator.NO_AD_SEQUENCE_ID_FOR_TESTING).name("test").build());
		Services.registerService(IDocumentSequenceDAO.class, documentSequenceDAO);

		// every selected transport order has a Der Kurier config with a recipient - the eligibility this
		// process checks for, kept constant so only DocSubType varies between the two scenarios below
		configRepository = mock(DerKurierShipperConfigRepository.class);
		final DerKurierShipperConfig config = DerKurierShipperConfig.builder()
				.restApiBaseUrl("https://derkurier.example.com")
				.customerNumber("CUST1")
				.collectorCode("COL1")
				.customerCode("CC1")
				.parcelNumberAdSequenceId(ParcelNumberGenerator.NO_AD_SEQUENCE_ID_FOR_TESTING)
				.desiredTimeFrom(LocalTime.of(8, 0))
				.desiredTimeTo(LocalTime.of(17, 0))
				.deliveryOrderMailBoxId(MailboxId.ofRepoId(1000000))
				.deliveryOrderRecipientEmailOrNull(EMailAddress.ofNullableString("kurier@example.com"))
				.build();
		when(configRepository.retrieveConfigForShipperIdOrNull(anyInt())).thenReturn(config);
		SpringContextHolder.registerJUnitBean(DerKurierShipperConfigRepository.class, configRepository);

		// bypass the real IDocumentBL (its default-discovered impl needs Spring-DI collaborators this
		// bare test does not boot) - every selected record here is stamped DocStatus='CO' anyway, so
		// "completed" is what the real impl would answer too
		final IDocumentBL documentBL = mock(IDocumentBL.class);
		when(documentBL.isDocumentCompleted(any())).thenReturn(true);
		Services.registerService(IDocumentBL.class, documentBL);

		process = new M_ShipperTransportation_SendDerKurierEMail();
	}

	private I_M_ShipperTransportation completedShipperTransportation(final String docSubType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setName("docType-" + docSubType);
		docType.setDocBaseType(DocBaseType.ShipperTransportation.getCode());
		docType.setDocSubType(docSubType);
		InterfaceWrapperHelper.save(docType);

		// kept purely in-memory (not saved): the guard and isCompleted() only read getters off this
		// instance, and saving a DocStatus='CO' record here would need the document-numbering machinery
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setC_DocType_ID(docType.getC_DocType_ID());
		record.setDocStatus(IDocument.STATUS_Completed);
		record.setM_Shipper_ID(1000000);
		return record;
	}

	private IProcessPreconditionsContext contextSelecting(final I_M_ShipperTransportation record)
	{
		final IProcessPreconditionsContext context = mock(IProcessPreconditionsContext.class);
		final SelectionSize selectionSize = SelectionSize.ofSize(1);
		when(context.getSelectionSize()).thenReturn(selectionSize);
		when(context.streamSelectedModels(I_M_ShipperTransportation.class)).thenReturn(Stream.of(record));
		return context;
	}

	@Test
	void completedTransportOrder_withDerKurierConfig_isANoOp_stillOffered()
	{
		final I_M_ShipperTransportation transportOrder = completedShipperTransportation(null);

		final ProcessPreconditionsResolution resolution = process.checkPreconditionsApplicable(contextSelecting(transportOrder));

		assertThat(resolution.isAccepted()).isTrue();
	}

	@Test
	void completedDeliveryInstruction_withDerKurierConfig_isHidden()
	{
		final I_M_ShipperTransportation deliveryInstruction = completedShipperTransportation(DocSubType.DeliveryInstruction.getCode());

		final ProcessPreconditionsResolution resolution = process.checkPreconditionsApplicable(contextSelecting(deliveryInstruction));

		assertThat(resolution.isAccepted()).isFalse();
		// belt-and-braces: the config lookup (the more expensive check) must never run for a delivery
		// instruction, because the cheap DocSubType filter runs first and short-circuits the stream
		Mockito.verify(configRepository, Mockito.never()).retrieveConfigForShipperIdOrNull(Mockito.anyInt());
	}
}
