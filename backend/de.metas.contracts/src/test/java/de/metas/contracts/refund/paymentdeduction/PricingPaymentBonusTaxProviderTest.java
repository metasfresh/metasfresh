package de.metas.contracts.refund.paymentdeduction;

import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.product.ProductId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PricingPaymentBonusTaxProviderTest
{
	private PricingPaymentBonusTaxProvider taxProvider;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		taxProvider = new PricingPaymentBonusTaxProvider();
	}

	@Test
	void bonusProductWithoutPrice_failsWithATranslatedUserError()
	{
		final I_C_Invoice salesInvoice = createSalesInvoice();
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
		final I_M_Product_Category productCategory = newInstance(I_M_Product_Category.class);
		saveRecord(productCategory);
		final I_M_Product bonusProduct = newInstance(I_M_Product.class);
		bonusProduct.setC_UOM_ID(uom.getC_UOM_ID());
		bonusProduct.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		saveRecord(bonusProduct);

		assertThatThrownBy(() -> taxProvider.getTax(salesInvoice, ProductId.ofRepoId(bonusProduct.getM_Product_ID())))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final AdempiereException adempiereException = (AdempiereException)ex;
					assertThat(adempiereException.isUserValidationError()).isTrue();
					assertThat(adempiereException.getErrorCode()).isEqualTo(PricingPaymentBonusTaxProvider.MSG_BONUS_PRODUCT_HAS_NO_PRICE.toAD_Message());
				});
	}

	private I_C_Invoice createSalesInvoice()
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		saveRecord(country);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setC_Country_ID(country.getC_Country_ID());
		saveRecord(location);

		final I_C_BP_Group bpGroup = newInstance(I_C_BP_Group.class);
		saveRecord(bpGroup);

		final I_C_BPartner customer = newInstance(I_C_BPartner.class);
		customer.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
		saveRecord(customer);

		final I_C_BPartner_Location billLocation = newInstance(I_C_BPartner_Location.class);
		billLocation.setC_BPartner_ID(customer.getC_BPartner_ID());
		billLocation.setC_Location_ID(location.getC_Location_ID());
		billLocation.setIsBillTo(true);
		saveRecord(billLocation);

		final I_M_PricingSystem pricingSystem = newInstance(I_M_PricingSystem.class);
		saveRecord(pricingSystem);

		// the invoice's price list has no price for the bonus product
		final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
		priceList.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
		priceList.setIsSOPriceList(true);
		priceList.setC_Currency_ID(PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId().getRepoId());
		saveRecord(priceList);

		final I_C_Invoice salesInvoice = newInstance(I_C_Invoice.class);
		salesInvoice.setM_PriceList_ID(priceList.getM_PriceList_ID());
		salesInvoice.setAD_Org_ID(AdempiereTestHelper.createOrgWithTimeZone().getRepoId());
		salesInvoice.setIsSOTrx(true);
		salesInvoice.setC_BPartner_ID(customer.getC_BPartner_ID());
		salesInvoice.setC_BPartner_Location_ID(billLocation.getC_BPartner_Location_ID());
		salesInvoice.setDateInvoiced(TimeUtil.asTimestamp(LocalDate.parse("2026-07-15")));
		saveRecord(salesInvoice);
		return salesInvoice;
	}
}
