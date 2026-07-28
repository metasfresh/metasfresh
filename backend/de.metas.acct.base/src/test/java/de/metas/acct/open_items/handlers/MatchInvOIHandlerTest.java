package de.metas.acct.open_items.handlers;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.accounts.BPartnerGroupAccountType;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxInfoComputeRequest;
import de.metas.elementvalue.ElementValueService;
import de.metas.acct.open_items.FAOpenItemTrxType;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_MatchInv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class MatchInvOIHandlerTest
{
	private static final AccountConceptualName NotInvoicedReceipts_Acct = BPartnerGroupAccountType.NotInvoicedReceipts.getAccountConceptualName();
	private static final AccountConceptualName P_InventoryClearing_Acct = ProductAcctType.P_InventoryClearing_Acct.getAccountConceptualName();

	private MatchInvOIHandler handler;

	@BeforeEach
	void setup()
	{
		AdempiereTestHelper.get().init();
		handler = new MatchInvOIHandler(ElementValueService.newInstanceForUnitTesting(), new MatchInvoiceRepository());
	}

	/**
	 * Verifies that the opening key produced for a receipt (M_InOut) matches the clearing key
	 * produced for the corresponding M_MatchInv — so the open-items reconciliation can close the entry.
	 */
	@Test
	void notInvoicedReceipts_openingAndClearingKeyMatch()
	{
		// given
		final ElementValueId elementValueId = createOpenItemElementValue();

		final I_M_MatchInv matchInv = createMinimalMatchInv();
		matchInv.setM_InOut_ID(100);
		matchInv.setM_InOutLine_ID(200);
		matchInv.setC_Invoice_ID(300); // required by MatchInvoiceRepository.fromRecord
		matchInv.setC_InvoiceLine_ID(400); // required by MatchInvoiceRepository.fromRecord
		saveRecord(matchInv);

		// when — opening key (from M_InOut)
		final FAOpenItemTrxInfoComputeRequest openingRequest = FAOpenItemTrxInfoComputeRequest.builder()
				.accountConceptualName(NotInvoicedReceipts_Acct)
				.elementValueId(elementValueId)
				.tableName(I_M_InOut.Table_Name)
				.recordId(100)
				.lineId(200)
				.build();
		final FAOpenItemTrxInfo openingInfo = handler.computeTrxInfo(openingRequest).orElseThrow(AssertionError::new);

		// when — clearing key (from M_MatchInv)
		final FAOpenItemTrxInfoComputeRequest clearingRequest = FAOpenItemTrxInfoComputeRequest.builder()
				.accountConceptualName(NotInvoicedReceipts_Acct)
				.elementValueId(elementValueId)
				.tableName(I_M_MatchInv.Table_Name)
				.recordId(matchInv.getM_MatchInv_ID())
				.build();
		final FAOpenItemTrxInfo clearingInfo = handler.computeTrxInfo(clearingRequest).orElseThrow(AssertionError::new);

		// then — keys must match so reconciliation can close the open item
		assertThat(openingInfo.getTrxType()).isEqualTo(FAOpenItemTrxType.OPEN_ITEM);
		assertThat(clearingInfo.isClearing()).isTrue();
		assertThat(clearingInfo.getKey()).isEqualTo(openingInfo.getKey());
	}

	/**
	 * Verifies that the opening key produced for an invoice (C_Invoice) matches the clearing key
	 * produced for the corresponding M_MatchInv — so the open-items reconciliation can close the entry.
	 */
	@Test
	void inventoryClearing_openingAndClearingKeyMatch()
	{
		// given
		final ElementValueId elementValueId = createOpenItemElementValue();

		final I_M_MatchInv matchInv = createMinimalMatchInv();
		matchInv.setC_Invoice_ID(300);
		matchInv.setC_InvoiceLine_ID(400);
		matchInv.setM_InOut_ID(100); // required by MatchInvoiceRepository.fromRecord
		matchInv.setM_InOutLine_ID(200); // required by MatchInvoiceRepository.fromRecord
		saveRecord(matchInv);

		// when — opening key (from C_Invoice)
		final FAOpenItemTrxInfoComputeRequest openingRequest = FAOpenItemTrxInfoComputeRequest.builder()
				.accountConceptualName(P_InventoryClearing_Acct)
				.elementValueId(elementValueId)
				.tableName(I_C_Invoice.Table_Name)
				.recordId(300)
				.lineId(400)
				.build();
		final FAOpenItemTrxInfo openingInfo = handler.computeTrxInfo(openingRequest).orElseThrow(AssertionError::new);

		// when — clearing key (from M_MatchInv)
		final FAOpenItemTrxInfoComputeRequest clearingRequest = FAOpenItemTrxInfoComputeRequest.builder()
				.accountConceptualName(P_InventoryClearing_Acct)
				.elementValueId(elementValueId)
				.tableName(I_M_MatchInv.Table_Name)
				.recordId(matchInv.getM_MatchInv_ID())
				.build();
		final FAOpenItemTrxInfo clearingInfo = handler.computeTrxInfo(clearingRequest).orElseThrow(AssertionError::new);

		// then — keys must match so reconciliation can close the open item
		assertThat(openingInfo.getTrxType()).isEqualTo(FAOpenItemTrxType.OPEN_ITEM);
		assertThat(clearingInfo.isClearing()).isTrue();
		assertThat(clearingInfo.getKey()).isEqualTo(openingInfo.getKey());
	}

	@Test
	void computeTrxInfo_whenAccountNotOpenItem_returnsEmpty()
	{
		// given
		final ElementValueId elementValueId = createClosedItemElementValue();

		final I_M_MatchInv matchInv = createMinimalMatchInv();
		matchInv.setM_InOut_ID(100);
		matchInv.setM_InOutLine_ID(200);
		matchInv.setC_Invoice_ID(300); // required by MatchInvoiceRepository.fromRecord
		matchInv.setC_InvoiceLine_ID(400); // required by MatchInvoiceRepository.fromRecord
		saveRecord(matchInv);

		final FAOpenItemTrxInfoComputeRequest request = FAOpenItemTrxInfoComputeRequest.builder()
				.accountConceptualName(NotInvoicedReceipts_Acct)
				.elementValueId(elementValueId)
				.tableName(I_M_MatchInv.Table_Name)
				.recordId(matchInv.getM_MatchInv_ID())
				.build();

		// when
		final Optional<FAOpenItemTrxInfo> result = handler.computeTrxInfo(request);

		// then
		assertThat(result).isEmpty();
	}

	private ElementValueId createOpenItemElementValue()
	{
		return createElementValue(true);
	}

	private ElementValueId createClosedItemElementValue()
	{
		return createElementValue(false);
	}

	private ElementValueId createElementValue(final boolean isOpenItem)
	{
		final I_C_ElementValue ev = newInstance(I_C_ElementValue.class);
		ev.setC_Element_ID(1);
		ev.setValue("1000");
		ev.setName("Test Account");
		ev.setAccountSign("N");
		ev.setAccountType("A");
		ev.setIsOpenItem(isOpenItem);
		saveRecord(ev);
		return ElementValueId.ofRepoId(ev.getC_ElementValue_ID());
	}

	/**
	 * Creates a minimal but valid {@link I_M_MatchInv} record with all mandatory fields
	 * required by {@link MatchInvoiceRepository#fromRecord}.
	 * Callers should set the relevant foreign-key IDs (M_InOut_ID, C_Invoice_ID, etc.) after calling this.
	 */
	private I_M_MatchInv createMinimalMatchInv()
	{
		final int productId = createProduct();

		final I_M_MatchInv matchInv = newInstance(I_M_MatchInv.class);
		matchInv.setType(X_M_MatchInv.TYPE_Material);
		matchInv.setDateTrx(Timestamp.from(Instant.parse("2024-01-01T00:00:00Z")));
		matchInv.setDateAcct(Timestamp.from(Instant.parse("2024-01-01T00:00:00Z")));
		matchInv.setM_Product_ID(productId);
		matchInv.setQty(java.math.BigDecimal.ONE);
		return matchInv;
	}

	private int createProduct()
	{
		final int uomId = createUOM();

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue("TEST");
		product.setName("Test Product");
		product.setC_UOM_ID(uomId);
		saveRecord(product);
		return product.getM_Product_ID();
	}

	private int createUOM()
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Each");
		uom.setUOMSymbol("Ea");
		uom.setX12DE355("EA");
		saveRecord(uom);
		return uom.getC_UOM_ID();
	}
}
