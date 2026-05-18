package de.metas.acct.open_items.handlers;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.accounts.BPartnerGroupAccountType;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxInfoComputeRequest;
import de.metas.elementvalue.ElementValueService;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_M_MatchInv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		handler = new MatchInvOIHandler(ElementValueService.newInstanceForUnitTesting());
	}

	@Test
	void computeTrxInfo_notInvoicedReceipts_returnsClearingKeyPointingToInOut()
	{
		// given
		final I_C_ElementValue elementValue = newInstance(I_C_ElementValue.class);
		elementValue.setC_Element_ID(1); // required: ChartOfAccountsId must be > 0
		elementValue.setValue("1000");
		elementValue.setName("Test Account");
		elementValue.setAccountSign("N");
		elementValue.setAccountType("A");
		elementValue.setIsOpenItem(true);
		saveRecord(elementValue);

		final I_M_MatchInv matchInv = newInstance(I_M_MatchInv.class);
		matchInv.setM_InOut_ID(100);
		matchInv.setM_InOutLine_ID(200);
		saveRecord(matchInv);

		final FAOpenItemTrxInfoComputeRequest request = FAOpenItemTrxInfoComputeRequest.builder()
				.accountConceptualName(NotInvoicedReceipts_Acct)
				.elementValueId(ElementValueId.ofRepoId(elementValue.getC_ElementValue_ID()))
				.tableName(I_M_MatchInv.Table_Name)
				.recordId(matchInv.getM_MatchInv_ID())
				.build();

		// when
		final FAOpenItemTrxInfo result = handler.computeTrxInfo(request).orElseThrow(() -> new AssertionError("Expected a non-empty result"));

		// then
		assertThat(result.isClearing()).isTrue();
		assertThat(result.getKey().getAsString())
				.isEqualTo(NotInvoicedReceipts_Acct.getAsString() + "#M_InOut#100#200");
	}

	@Test
	void computeTrxInfo_inventoryClearing_returnsClearingKeyPointingToInvoice()
	{
		// given
		final I_C_ElementValue elementValue = newInstance(I_C_ElementValue.class);
		elementValue.setC_Element_ID(1); // required: ChartOfAccountsId must be > 0
		elementValue.setValue("1000");
		elementValue.setName("Test Account");
		elementValue.setAccountSign("N");
		elementValue.setAccountType("A");
		elementValue.setIsOpenItem(true);
		saveRecord(elementValue);

		final I_M_MatchInv matchInv = newInstance(I_M_MatchInv.class);
		matchInv.setC_Invoice_ID(300);
		matchInv.setC_InvoiceLine_ID(400);
		saveRecord(matchInv);

		final FAOpenItemTrxInfoComputeRequest request = FAOpenItemTrxInfoComputeRequest.builder()
				.accountConceptualName(P_InventoryClearing_Acct)
				.elementValueId(ElementValueId.ofRepoId(elementValue.getC_ElementValue_ID()))
				.tableName(I_M_MatchInv.Table_Name)
				.recordId(matchInv.getM_MatchInv_ID())
				.build();

		// when
		final FAOpenItemTrxInfo result = handler.computeTrxInfo(request).orElseThrow(() -> new AssertionError("Expected a non-empty result"));

		// then
		assertThat(result.isClearing()).isTrue();
		assertThat(result.getKey().getAsString())
				.isEqualTo(P_InventoryClearing_Acct.getAsString() + "#C_Invoice#300#400");
	}

	@Test
	void computeTrxInfo_whenAccountNotOpenItem_returnsEmpty()
	{
		// given
		final I_C_ElementValue elementValue = newInstance(I_C_ElementValue.class);
		elementValue.setC_Element_ID(1); // required: ChartOfAccountsId must be > 0
		elementValue.setValue("1000");
		elementValue.setName("Test Account");
		elementValue.setAccountSign("N");
		elementValue.setAccountType("A");
		elementValue.setIsOpenItem(false);
		saveRecord(elementValue);

		final I_M_MatchInv matchInv = newInstance(I_M_MatchInv.class);
		matchInv.setM_InOut_ID(100);
		matchInv.setM_InOutLine_ID(200);
		saveRecord(matchInv);

		final FAOpenItemTrxInfoComputeRequest request = FAOpenItemTrxInfoComputeRequest.builder()
				.accountConceptualName(NotInvoicedReceipts_Acct)
				.elementValueId(ElementValueId.ofRepoId(elementValue.getC_ElementValue_ID()))
				.tableName(I_M_MatchInv.Table_Name)
				.recordId(matchInv.getM_MatchInv_ID())
				.build();

		// when
		final Optional<FAOpenItemTrxInfo> result = handler.computeTrxInfo(request);

		// then
		assertThat(result).isEmpty();
	}
}
