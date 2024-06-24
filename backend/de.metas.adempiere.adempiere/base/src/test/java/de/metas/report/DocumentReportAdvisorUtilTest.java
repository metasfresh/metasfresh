/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.report;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPPrintFormatQuery;
import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.DocTypeId;
import de.metas.user.UserRepository;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_BP_PrintFormat;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.compiere.model.X_C_DocType.DOCBASETYPE_ARInvoice;
import static org.compiere.model.X_C_DocType.DOCBASETYPE_MaterialDelivery;

public class DocumentReportAdvisorUtilTest
{
	private static final AdTableId IN_OUT_TABLE_ID = AdTableId.ofRepoId(319);
	private static final AdTableId INVOICE_TABLE_ID = AdTableId.ofRepoId(318);

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Nested
	class getDocumentCopies
	{
		@Test
		public void useDocTypeCopiesIfNoMatchingPrintFormat()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			final I_C_DocType docType = createDocType(DOCBASETYPE_MaterialDelivery);
			final BPartnerLocationId bPartnerLocationId = createBPartnerLocation();
			final BPPrintFormatQuery bpPrintFormatQuery = createBPrintFormatQuery(docType, bPartnerLocationId);

			final I_C_BP_PrintFormat printFormatNotMatchingLocation = newInstance(I_C_BP_PrintFormat.class);
			printFormatNotMatchingLocation.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatNotMatchingLocation.setC_BPartner_Location_ID(createBPartnerLocation().getRepoId());
			printFormatNotMatchingLocation.setDocumentCopies_Override(2);
			save(printFormatNotMatchingLocation);

			final I_C_BP_PrintFormat printFormatNotMatchingTable = newInstance(I_C_BP_PrintFormat.class);
			printFormatNotMatchingTable.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatNotMatchingTable.setAD_Table_ID(INVOICE_TABLE_ID.getRepoId());
			printFormatNotMatchingTable.setDocumentCopies_Override(3);
			save(printFormatNotMatchingTable);

			final I_C_BP_PrintFormat printFormatNotMatchingDocType = newInstance(I_C_BP_PrintFormat.class);
			printFormatNotMatchingDocType.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatNotMatchingDocType.setC_DocType_ID(createDocType(DOCBASETYPE_ARInvoice).getC_DocType_ID());
			printFormatNotMatchingDocType.setDocumentCopies_Override(4);
			save(printFormatNotMatchingDocType);

			final I_C_BP_PrintFormat printFormatNotMatchingPartner = newInstance(I_C_BP_PrintFormat.class);
			printFormatNotMatchingPartner.setC_BPartner_ID(createBPartnerLocation().getBpartnerId().getRepoId());
			printFormatNotMatchingPartner.setDocumentCopies_Override(5);
			save(printFormatNotMatchingPartner);

			final PrintCopies printCopies = util.getDocumentCopies(docType, bpPrintFormatQuery);
			Assertions.assertThat(printCopies.toInt()).isEqualTo(1);
		}

		@Test
		public void useMatchingPrintFormatLocationCopies()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			final I_C_DocType docType = createDocType(DOCBASETYPE_MaterialDelivery);
			final BPartnerLocationId bPartnerLocationId = createBPartnerLocation();
			final BPPrintFormatQuery bpPrintFormatQuery = createBPrintFormatQuery(docType, bPartnerLocationId);

			final I_C_BP_PrintFormat printFormatMatchingLocation = newInstance(I_C_BP_PrintFormat.class);
			printFormatMatchingLocation.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatMatchingLocation.setC_BPartner_Location_ID(bPartnerLocationId.getRepoId());
			printFormatMatchingLocation.setDocumentCopies_Override(2);
			save(printFormatMatchingLocation);

			final PrintCopies printCopies = util.getDocumentCopies(docType, bpPrintFormatQuery);
			Assertions.assertThat(printCopies.toInt()).isEqualTo(2);
		}

		@Test
		public void useMatchingPrintFormatTableIdCopies()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			final I_C_DocType docType = createDocType(DOCBASETYPE_MaterialDelivery);
			final BPartnerLocationId bPartnerLocationId = createBPartnerLocation();
			final BPPrintFormatQuery bpPrintFormatQuery = createBPrintFormatQuery(docType, bPartnerLocationId);

			final I_C_BP_PrintFormat printFormatMatchingTable = newInstance(I_C_BP_PrintFormat.class);
			printFormatMatchingTable.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatMatchingTable.setAD_Table_ID(IN_OUT_TABLE_ID.getRepoId());
			printFormatMatchingTable.setDocumentCopies_Override(2);
			save(printFormatMatchingTable);

			final PrintCopies printCopies = util.getDocumentCopies(docType, bpPrintFormatQuery);
			Assertions.assertThat(printCopies.toInt()).isEqualTo(2);
		}

		@Test
		public void useMatchingPrintFormatDocTypeCopies()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			final I_C_DocType docType = createDocType(DOCBASETYPE_MaterialDelivery);
			final BPartnerLocationId bPartnerLocationId = createBPartnerLocation();
			final BPPrintFormatQuery bpPrintFormatQuery = createBPrintFormatQuery(docType, bPartnerLocationId);

			final I_C_BP_PrintFormat printFormatMatchingDocType = newInstance(I_C_BP_PrintFormat.class);
			printFormatMatchingDocType.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatMatchingDocType.setC_DocType_ID(docType.getC_DocType_ID());
			printFormatMatchingDocType.setDocumentCopies_Override(2);
			save(printFormatMatchingDocType);

			final PrintCopies printCopies = util.getDocumentCopies(docType, bpPrintFormatQuery);
			Assertions.assertThat(printCopies.toInt()).isEqualTo(2);
		}

		@Test
		public void useMatchingPrintFormatPartnerCopies()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			final I_C_DocType docType = createDocType(DOCBASETYPE_MaterialDelivery);
			final BPartnerLocationId bPartnerLocationId = createBPartnerLocation();
			final BPPrintFormatQuery bpPrintFormatQuery = createBPrintFormatQuery(docType, bPartnerLocationId);

			final I_C_BP_PrintFormat printFormatMatchingPartner = newInstance(I_C_BP_PrintFormat.class);
			printFormatMatchingPartner.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatMatchingPartner.setDocumentCopies_Override(2);
			save(printFormatMatchingPartner);

			final PrintCopies printCopies = util.getDocumentCopies(docType, bpPrintFormatQuery);
			Assertions.assertThat(printCopies.toInt()).isEqualTo(2);
		}

		@Test
		public void seqNoPriorityTest()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			final I_C_DocType docType = createDocType(DOCBASETYPE_MaterialDelivery);
			final BPartnerLocationId bPartnerLocationId = createBPartnerLocation();
			final BPPrintFormatQuery bpPrintFormatQuery = createBPrintFormatQuery(docType, bPartnerLocationId);

			final I_C_BP_PrintFormat printFormatMatchingLocation = newInstance(I_C_BP_PrintFormat.class);
			printFormatMatchingLocation.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatMatchingLocation.setC_BPartner_Location_ID(bPartnerLocationId.getRepoId());
			printFormatMatchingLocation.setDocumentCopies_Override(2);
			printFormatMatchingLocation.setSeqNo(10);
			save(printFormatMatchingLocation);

			final I_C_BP_PrintFormat printFormatMatchingTable = newInstance(I_C_BP_PrintFormat.class);
			printFormatMatchingTable.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatMatchingTable.setAD_Table_ID(IN_OUT_TABLE_ID.getRepoId());
			printFormatMatchingTable.setDocumentCopies_Override(3);
			printFormatMatchingTable.setSeqNo(20);
			save(printFormatMatchingTable);

			final I_C_BP_PrintFormat printFormatMatchingPartner = newInstance(I_C_BP_PrintFormat.class);
			printFormatMatchingPartner.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatMatchingPartner.setDocumentCopies_Override(4);
			printFormatMatchingPartner.setSeqNo(30);
			save(printFormatMatchingPartner);

			final I_C_BP_PrintFormat printFormatMatchingDocType = newInstance(I_C_BP_PrintFormat.class);
			printFormatMatchingDocType.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatMatchingDocType.setC_DocType_ID(docType.getC_DocType_ID());
			printFormatMatchingDocType.setDocumentCopies_Override(5);
			printFormatMatchingDocType.setSeqNo(40);
			save(printFormatMatchingDocType);

			Assertions.assertThat(util.getDocumentCopies(docType, bpPrintFormatQuery).toInt()).isEqualTo(2);

			delete(printFormatMatchingLocation);
			Assertions.assertThat(util.getDocumentCopies(docType, bpPrintFormatQuery).toInt()).isEqualTo(3);

			delete(printFormatMatchingTable);
			Assertions.assertThat(util.getDocumentCopies(docType, bpPrintFormatQuery).toInt()).isEqualTo(4);

			delete(printFormatMatchingPartner);
			Assertions.assertThat(util.getDocumentCopies(docType, bpPrintFormatQuery).toInt()).isEqualTo(5);

			delete(printFormatMatchingDocType);
			Assertions.assertThat(util.getDocumentCopies(docType, bpPrintFormatQuery).toInt()).isEqualTo(1);
		}

		@Test
		public void useMatchingByPartnerOnlyCopies()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			final I_C_DocType docType = createDocType(DOCBASETYPE_MaterialDelivery);
			final BPartnerLocationId bPartnerLocationId = createBPartnerLocation();
			final BPPrintFormatQuery bpPrintFormatQuery = BPPrintFormatQuery.builder()
					.bpartnerId(bPartnerLocationId.getBpartnerId())
					.onlyCopiesGreaterZero(true)
					.build();

			final I_C_BP_PrintFormat printFormatMatchingPartner1 = newInstance(I_C_BP_PrintFormat.class);
			printFormatMatchingPartner1.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatMatchingPartner1.setDocumentCopies_Override(2);
			save(printFormatMatchingPartner1);

			Assertions.assertThat(util.getDocumentCopies(docType, bpPrintFormatQuery).toInt()).isEqualTo(2);
		}
	}
	private I_C_DocType createDocType(@NonNull final String docBaseType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setDocBaseType(docBaseType);
		docType.setIsDefault(true);
		docType.setDocumentCopies(1);
		save(docType);
		return docType;
	}


	private BPartnerLocationId createBPartnerLocation()
	{
		final I_C_BPartner bPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		save(bPartner);
		final I_C_BPartner_Location bPartnerLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		save(bPartnerLocation);
		return BPartnerLocationId.ofRepoId(bPartner.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());
	}

	private BPPrintFormatQuery createBPrintFormatQuery (@NonNull final I_C_DocType docTypeRecord, @NonNull final BPartnerLocationId bPartnerLocationId)
	{
		return BPPrintFormatQuery.builder()
				.adTableId(IN_OUT_TABLE_ID)
				.docTypeId(DocTypeId.ofRepoId(docTypeRecord.getC_DocType_ID()))
				.bpartnerId(bPartnerLocationId.getBpartnerId())
				.bPartnerLocationId(bPartnerLocationId)
				.onlyCopiesGreaterZero(true)
				.build();
	}

	private DocumentReportAdvisorUtil createUtil()
	{
		return new DocumentReportAdvisorUtil(
				new BPartnerBL(new UserRepository()),
				new PrintFormatRepository(),
				new DefaultPrintFormatsRepository(), new BPartnerPrintFormatRepository());
	}
}
