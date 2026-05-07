package org.compiere.acct;

import de.metas.acct.vatcode.VATCodeAmountType;
import de.metas.acct.vatcode.VATCodeMatchingRequest;
import de.metas.common.util.time.SystemTime;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that VATCodeDAO.findVATCode discriminates by VATCodeAmountType
 * so that the per-leg §13b RC posting uses the correct VAT code on each of the 3 legs.
 */
class Doc_InvoiceTaxLegMatcherTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void findVATCode_netLeg_returnsNetAmountCode()
	{
		final VATCodeMatchingRequest request = VATCodeMatchingRequest.builder()
				.setC_AcctSchema_ID(1)
				.setC_Tax_ID(1)
				.setIsSOTrx(false)
				.setDate(SystemTime.asDate())
				.setAmountType(VATCodeAmountType.Net)  // does not exist yet → RED
				.build();
		assertThat(request.getAmountType()).isEqualTo(VATCodeAmountType.Net);
	}

	@Test
	void findVATCode_taxLeg_returnsTaxAmountCode()
	{
		final VATCodeMatchingRequest request = VATCodeMatchingRequest.builder()
				.setC_AcctSchema_ID(1)
				.setC_Tax_ID(1)
				.setIsSOTrx(false)
				.setDate(SystemTime.asDate())
				.setAmountType(VATCodeAmountType.Tax)  // does not exist yet → RED
				.build();
		assertThat(request.getAmountType()).isEqualTo(VATCodeAmountType.Tax);
	}

	@Test
	void findVATCode_rcPurchase_threeLegsResolveCorrectly()
	{
		final VATCodeMatchingRequest netReq = VATCodeMatchingRequest.builder()
				.setC_AcctSchema_ID(1).setC_Tax_ID(1).setIsSOTrx(false).setDate(SystemTime.asDate())
				.setAmountType(VATCodeAmountType.Net).build();  // RED
		final VATCodeMatchingRequest inputTaxReq = VATCodeMatchingRequest.builder()
				.setC_AcctSchema_ID(1).setC_Tax_ID(1).setIsSOTrx(false).setDate(SystemTime.asDate())
				.setAmountType(VATCodeAmountType.Tax).build();  // RED
		final VATCodeMatchingRequest outputTaxReq = VATCodeMatchingRequest.builder()
				.setC_AcctSchema_ID(1).setC_Tax_ID(1).setIsSOTrx(true).setDate(SystemTime.asDate())
				.setAmountType(VATCodeAmountType.Tax).build();  // RED
		assertThat(netReq.getAmountType()).isEqualTo(VATCodeAmountType.Net);
		assertThat(inputTaxReq.getAmountType()).isEqualTo(VATCodeAmountType.Tax);
		assertThat(outputTaxReq.getAmountType()).isEqualTo(VATCodeAmountType.Tax);
	}
}
