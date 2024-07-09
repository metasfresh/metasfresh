package de.metas.invoicecandidate.api;

import de.metas.money.Money;
import de.metas.product.ProductPrice;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.tax.api.Tax;
import de.metas.util.lang.Percent;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * Invoice Line predecessor which is returned by {@link IAggregationBL#mkInvoiceLine()}.
 * <p/>
 * One or more invoice candidates are aggregated in one instance of this class
 */
public interface IInvoiceLineRW
{
	Money getNetLineAmt();

	void setNetLineAmt(Money newLineAmt);

	ProductPrice getPriceActual();

	void setPriceActual(ProductPrice priceActual);

	StockQtyAndUOMQty getQtysToInvoice();

	void setQtysToInvoice(StockQtyAndUOMQty qtyToInvoice);

	void addQtysToInvoice(StockQtyAndUOMQty qtyToInvoiceToAdd);

	int getC_OrderLine_ID();

	void setC_OrderLine_ID(int C_OrderLine_ID);

	int getC_Charge_ID();

	void setC_Charge_ID(int C_Charge_ID);

	int getM_Product_ID();

	void setM_Product_ID(int M_Product_ID);

	ProductPrice getPriceEntered();

	void setPriceEntered(ProductPrice priceEntered);

	Percent getDiscount();

	void setDiscount(Percent discount);

	// 03439 add description
	@Nullable String getDescription();

	void setDescription(@Nullable String description);

	// end of 03439 add description

	/**
	 *
	 * @return returns a mutable collection. never returns <code>null</code>.
	 */
	Collection<Integer> getC_InvoiceCandidate_InOutLine_IDs();

	/**
	 * Negate PriceActual and LineNetAmount
	 */
	void negateAmounts();

	// 07442
	// add tax and activity
	int getC_Activity_ID();

	void setC_Activity_ID(int activityID);

	Tax getC_Tax();

	void setC_Tax(Tax tax);

	/**
	 * @return true if this invoice line shall be printed
	 */
	boolean isPrinted();

	void setPrinted(final boolean printed);

	int getLineNo();

	void setLineNo(int lineNo);

	void setInvoiceLineAttributes(List<IInvoiceLineAttribute> invoiceLineAttributes);

	/** @return product attributes */
	List<IInvoiceLineAttribute> getInvoiceLineAttributes();

	List<InvoiceCandidateInOutLineToUpdate> getInvoiceCandidateInOutLinesToUpdate();

	int getC_PaymentTerm_ID();

	void setC_PaymentTerm_ID(int paymentTermId);

	int getC_VAT_Code_ID();
	void setC_VAT_Code_ID(int vatCodeId);
}
