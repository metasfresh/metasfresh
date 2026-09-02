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

import de.metas.bpartner.BPartnerId;
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
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

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
		public void useCopiesOverrideEvenWhenPrintFormatIsSet()
		{
			// bug reproduction: a C_BP_PrintFormat row can carry BOTH an AD_PrintFormat_ID (the print-format
			// hook) and a DocumentCopies_Override. The advisor's non-exact query (no printFormatId given,
			// as it queries just by table/docType/bpartner) must still find this row's copies override,
			// not fall back to the doc-type default.
			final DocumentReportAdvisorUtil util = createUtil();

			final I_C_DocType docType = createDocType(DOCBASETYPE_MaterialDelivery);
			final BPartnerLocationId bPartnerLocationId = createBPartnerLocation();
			final BPPrintFormatQuery bpPrintFormatQuery = createBPrintFormatQuery(docType, bPartnerLocationId);

			final I_C_BP_PrintFormat printFormatWithHookAndOverride = newInstance(I_C_BP_PrintFormat.class);
			printFormatWithHookAndOverride.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormatWithHookAndOverride.setC_BPartner_Location_ID(bPartnerLocationId.getRepoId());
			printFormatWithHookAndOverride.setAD_Table_ID(IN_OUT_TABLE_ID.getRepoId());
			printFormatWithHookAndOverride.setC_DocType_ID(docType.getC_DocType_ID());
			printFormatWithHookAndOverride.setAD_PrintFormat_ID(540001); // print-format hook set
			printFormatWithHookAndOverride.setDocumentCopies_Override(3);
			save(printFormatWithHookAndOverride);

			final PrintCopies printCopies = util.getDocumentCopies(docType, bpPrintFormatQuery);
			Assertions.assertThat(printCopies.toInt()).isEqualTo(3);
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

	@Nested
	class isDropShip
	{
		@Test
		public void dropShipTrueOnlyWhenFlagSetAndTargetDeviatesFromSoldTo()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			final BPartnerLocationId soldTo = createBPartnerLocation();
			final BPartnerLocationId otherRecipient = createBPartnerLocation();

			// realistic drop-ship: DropShip_BPartner_ID + DropShip_Location_ID both set and deviating + IsDropShip=Y -> true
			final I_C_Order deviatingRecipient = createOrder(soldTo, true, otherRecipient.getBpartnerId().getRepoId(), otherRecipient.getRepoId());
			Assertions.assertThat(util.isDropShip(deviatingRecipient)).isTrue();

			// the full-key comparison must incorporate the bpartner component, not only the location repoId:
			// DropShip_BPartner_ID deviates while the DropShip_Location_ID repoId is identical to the sold-to
			// location -> still a drop-ship (true). (an impl comparing only the location repoId returns false here.)
			final BPartnerId otherBPartner = createBPartnerLocation().getBpartnerId();
			final I_C_Order deviatingBPartnerSameLocationRepoId = createOrder(soldTo, true, otherBPartner.getRepoId(), soldTo.getRepoId());
			Assertions.assertThat(util.isDropShip(deviatingBPartnerSameLocationRepoId)).isTrue();

			// deviating recipient but IsDropShip=N -> false (flag gates it)
			final I_C_Order flagNotSet = createOrder(soldTo, false, otherRecipient.getBpartnerId().getRepoId(), otherRecipient.getRepoId());
			Assertions.assertThat(util.isDropShip(flagNotSet)).isFalse();

			// IsDropShip=Y but the drop-ship recipient equals the order's own sold-to (same BP + same location) -> false
			final I_C_Order notDeviating = createOrder(soldTo, true, soldTo.getBpartnerId().getRepoId(), soldTo.getRepoId());
			Assertions.assertThat(util.isDropShip(notDeviating)).isFalse();

			// NUANCE: a partial drop-ship state is not a realistic recipient key. With the full natural-key
			// (bpartner, location) comparison, an order whose DropShip_Location_ID is unset (0) -- even with a
			// deviating DropShip_BPartner_ID -- yields a null recipient key and is treated as non-deviating (false).
			// A real drop-ship always sets BOTH DropShip_BPartner_ID and DropShip_Location_ID.
			final I_C_Order onlyBPartnerSet = createOrder(soldTo, true, otherRecipient.getBpartnerId().getRepoId(), 0);
			Assertions.assertThat(util.isDropShip(onlyBPartnerSet)).isFalse();
		}

		@Test
		public void manualShipmentWithoutOrderIsNotDropShip()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			// a manual shipment has no C_Order_ID at all
			final I_M_InOut manualShipment = newInstance(I_M_InOut.class);
			manualShipment.setC_Order_ID(0);
			save(manualShipment);

			final I_C_Order order = resolveOrderOrNull(manualShipment);
			Assertions.assertThat(order).isNull();
			Assertions.assertThat(util.isDropShip(order)).isFalse();
		}
	}

	@Nested
	class resolveSuppressAutoPrint
	{
		@Test
		public void suppressesWhenMatchingDropShipRowHasAutoPrintNo()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			final I_C_DocType docType = createDocType(DOCBASETYPE_MaterialDelivery);
			final BPartnerLocationId bPartnerLocationId = createBPartnerLocation();
			final BPPrintFormatQuery dropShipQuery = createDropShipQuery(docType, bPartnerLocationId, true);

			final I_C_BP_PrintFormat printFormat = newInstance(I_C_BP_PrintFormat.class);
			printFormat.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormat.setC_BPartner_Location_ID(bPartnerLocationId.getRepoId());
			printFormat.setAD_Table_ID(IN_OUT_TABLE_ID.getRepoId());
			printFormat.setC_DocType_ID(docType.getC_DocType_ID());
			printFormat.setIsDropShip("Y");
			printFormat.setIsAutoPrint("N");
			save(printFormat);

			Assertions.assertThat(util.resolveSuppressAutoPrint(dropShipQuery)).isTrue();
		}

		@Test
		public void doesNotSuppressWhenAutoPrintIsYes()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			final I_C_DocType docType = createDocType(DOCBASETYPE_MaterialDelivery);
			final BPartnerLocationId bPartnerLocationId = createBPartnerLocation();
			final BPPrintFormatQuery dropShipQuery = createDropShipQuery(docType, bPartnerLocationId, true);

			final I_C_BP_PrintFormat printFormat = newInstance(I_C_BP_PrintFormat.class);
			printFormat.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormat.setC_BPartner_Location_ID(bPartnerLocationId.getRepoId());
			printFormat.setAD_Table_ID(IN_OUT_TABLE_ID.getRepoId());
			printFormat.setC_DocType_ID(docType.getC_DocType_ID());
			printFormat.setIsDropShip("Y");
			printFormat.setIsAutoPrint("Y");
			save(printFormat);

			Assertions.assertThat(util.resolveSuppressAutoPrint(dropShipQuery)).isFalse();
		}

		@Test
		public void doesNotSuppressWhenAutoPrintIsNullReadNullAware()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			final I_C_DocType docType = createDocType(DOCBASETYPE_MaterialDelivery);
			final BPartnerLocationId bPartnerLocationId = createBPartnerLocation();
			final BPPrintFormatQuery dropShipQuery = createDropShipQuery(docType, bPartnerLocationId, true);

			final I_C_BP_PrintFormat printFormat = newInstance(I_C_BP_PrintFormat.class);
			printFormat.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
			printFormat.setC_BPartner_Location_ID(bPartnerLocationId.getRepoId());
			printFormat.setAD_Table_ID(IN_OUT_TABLE_ID.getRepoId());
			printFormat.setC_DocType_ID(docType.getC_DocType_ID());
			printFormat.setIsDropShip("Y");
			// IsAutoPrint intentionally left unset (null) -- must NOT collapse to "suppress"
			save(printFormat);

			Assertions.assertThat(printFormat.getIsAutoPrint()).isNull();
			Assertions.assertThat(util.resolveSuppressAutoPrint(dropShipQuery)).isFalse();
		}

		@Test
		public void doesNotSuppressWhenNoMatchingRow()
		{
			final DocumentReportAdvisorUtil util = createUtil();

			final I_C_DocType docType = createDocType(DOCBASETYPE_MaterialDelivery);
			final BPartnerLocationId bPartnerLocationId = createBPartnerLocation();
			final BPPrintFormatQuery dropShipQuery = createDropShipQuery(docType, bPartnerLocationId, true);

			// no C_BP_PrintFormat row created at all for this bpartner
			Assertions.assertThat(util.resolveSuppressAutoPrint(dropShipQuery)).isFalse();
		}
	}

	@Nullable
	private I_C_Order resolveOrderOrNull(@NonNull final I_M_InOut inout)
	{
		final int orderRepoId = inout.getC_Order_ID();
		return orderRepoId > 0 ? InterfaceWrapperHelper.load(orderRepoId, I_C_Order.class) : null;
	}

	private I_C_Order createOrder(
			@NonNull final BPartnerLocationId soldTo,
			final boolean isDropShip,
			final int dropShipBPartnerId,
			final int dropShipLocationId)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(soldTo.getBpartnerId().getRepoId());
		order.setC_BPartner_Location_ID(soldTo.getRepoId());
		order.setIsDropShip(isDropShip);
		order.setDropShip_BPartner_ID(dropShipBPartnerId);
		order.setDropShip_Location_ID(dropShipLocationId);
		save(order);
		return order;
	}

	private BPPrintFormatQuery createDropShipQuery(
			@NonNull final I_C_DocType docTypeRecord,
			@NonNull final BPartnerLocationId bPartnerLocationId,
			final boolean isDropShip)
	{
		return BPPrintFormatQuery.builder()
				.adTableId(IN_OUT_TABLE_ID)
				.docTypeId(DocTypeId.ofRepoId(docTypeRecord.getC_DocType_ID()))
				.bpartnerId(bPartnerLocationId.getBpartnerId())
				.bPartnerLocationId(bPartnerLocationId)
				.isDropShip(isDropShip)
				.build();
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
