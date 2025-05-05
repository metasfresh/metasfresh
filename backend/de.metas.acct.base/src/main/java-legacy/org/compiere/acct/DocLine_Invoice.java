package org.compiere.acct;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.acct.Account;
import de.metas.acct.accounts.InvoiceAccountProviderExtension;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.currency.CurrencyPrecision;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_InvoiceLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DocLine_Invoice extends DocLine<Doc_Invoice>
{
	// services
	private static final Logger logger = LogManager.getLogger(DocLine_Invoice.class);
	private final MatchInvoiceService matchInvoiceService;
	private final transient IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final transient IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final transient IProductDAO productDAO = Services.get(IProductDAO.class);

	private final OrderGroupRepository orderGroupRepo;

	private BigDecimal _includedTaxAmt = BigDecimal.ZERO;
	private Quantity _qtyInvoiced = null; // lazy
	private Quantity _qtyReceivedInStockUOM = null; // lazy
	private Money _costAmountMatched = null; // lazy

	private static final String SYS_CONFIG_M_Product_Acct_Consider_CompensationSchema = "M_Product_Acct_Consider_CompensationSchema";

	public DocLine_Invoice(
			@NonNull final OrderGroupRepository orderGroupRepo,
			@NonNull final I_C_InvoiceLine invoiceLine,
			@NonNull final Doc_Invoice doc)
	{
		super(InterfaceWrapperHelper.getPO(invoiceLine), doc);
		this.orderGroupRepo = orderGroupRepo;
		this.matchInvoiceService = doc.getServices().getMatchInvoiceService();

		setIsTaxIncluded(invoiceBL.isTaxIncluded(invoiceLine));

		// Qty
		final Quantity qtyInvoiced = Quantity.of(invoiceLine.getQtyInvoiced(), getProductStockingUOM());
		setQty(qtyInvoiced.negateIf(doc.isCreditMemo()), doc.isSOTrx());

		//
		BigDecimal lineNetAmt = invoiceLine.getLineNetAmt();
		BigDecimal priceList = invoiceLine.getPriceList();

		// Correct included Tax
		final TaxId taxId = getTaxId().orElse(null);
		if (taxId != null && isTaxIncluded())
		{
			final Tax tax = services.getTaxById(taxId);
			if (!tax.isZeroTax())
			{
				final CurrencyPrecision taxPrecision = doc.getStdPrecision();
				final BigDecimal lineTaxAmt = tax.calculateTax(lineNetAmt, true, taxPrecision.toInt()).getTaxAmount();
				logger.debug("LineNetAmt={} - LineTaxAmt={}", lineNetAmt, lineTaxAmt);
				lineNetAmt = lineNetAmt.subtract(lineTaxAmt);

				final BigDecimal priceListTax = tax.calculateTax(priceList, true, taxPrecision.toInt()).getTaxAmount();
				priceList = priceList.subtract(priceListTax);

				_includedTaxAmt = lineTaxAmt;
			}
		}    // correct included Tax

		setAmount(lineNetAmt, priceList, qtyInvoiced.toBigDecimal());    // qty for discount calc
	}

	@Nullable
	@Override
	protected InvoiceAccountProviderExtension createAccountProviderExtension()
	{
		final InvoiceAndLineId invoiceAndLineId = getInvoiceAndLineId();
		return getDoc().createInvoiceAccountProviderExtension(invoiceAndLineId);
	}

	public InvoiceAndLineId getInvoiceAndLineId()
	{
		final InvoiceId invoiceId = getDoc().getInvoiceId();
		return InvoiceAndLineId.ofRepoId(invoiceId, get_ID());
	}

	public final I_C_InvoiceLine getC_InvoiceLine()
	{
		return getModel(I_C_InvoiceLine.class);
	}

	/**
	 * @return true if this invoice line is part of a credit memo invoice
	 */
	public final boolean isCreditMemo()
	{
		return getDoc().isCreditMemo();
	}

	/**
	 * Adjusts the sign of given relative <code>qty</code> using the credit memo and SOTrx flags.
	 * <p>
	 * Mainly it is:
	 * <ul>
	 * <li>if {@link #isCreditMemo()}, negate the quantity
	 * <li>if {@link #isSOTrx()}, negate the quantity
	 * </ul>
	 *
	 * @return quantity (absolute value)
	 */
	private Quantity adjustQtySignByCreditMemoAndSOTrx(final Quantity qty)
	{
		if (qty == null || qty.signum() == 0)
		{
			return qty;
		}

		Quantity qtyAdjusted = qty;
		if (isCreditMemo())
		{
			qtyAdjusted = qtyAdjusted.negate();
		}
		if (isSOTrx())
		{
			qtyAdjusted = qtyAdjusted.negate();
		}

		return qtyAdjusted;
	}

	/**
	 * @return amount of the included tax or ZERO if this line has not included tax.
	 */
	public BigDecimal getIncludedTaxAmt()
	{
		return _includedTaxAmt;
	}

	private Quantity getQtyReceivedInStockUOM()
	{
		if (_qtyReceivedInStockUOM == null)
		{
			this._qtyReceivedInStockUOM = matchInvoiceService.getMaterialQtyMatched(getC_InvoiceLine()).getStockQty();
		}
		return _qtyReceivedInStockUOM;
	}

	/**
	 * @return quantity received (absolute value)
	 */
	Quantity getQtyReceivedAbs()
	{
		return adjustQtySignByCreditMemoAndSOTrx(getQtyReceivedInStockUOM());
	}

	private Quantity getQtyInvoiced()
	{
		final I_C_InvoiceLine invoiceLine = getC_InvoiceLine();
		if (_qtyInvoiced == null)
		{
			_qtyInvoiced = invoiceBL.getQtyInvoicedStockUOM(invoiceLine);
		}
		return _qtyInvoiced;
	}

	/**
	 * @return quantity invoiced but not received
	 */
	public final Quantity getQtyNotReceived()
	{
		return getQtyInvoiced().subtract(getQtyReceivedInStockUOM());
	}

	/**
	 * @return quantity invoiced but not received (absolute value)
	 */
	final Quantity getQtyNotReceivedAbs()
	{
		return adjustQtySignByCreditMemoAndSOTrx(getQtyNotReceived());
	}

	/**
	 * Calculate the net amount of quantity received using <code>lineNetAmt</code> as total amount.
	 *
	 * @return quantity received invoiced amount
	 */
	public Money calculateAmtOfQtyReceived(final Money lineNetAmt)
	{
		if (lineNetAmt.signum() == 0)
		{
			return lineNetAmt.toZero();
		}

		final Quantity qtyReceived = getQtyReceivedAbs();
		if (qtyReceived.signum() == 0)
		{
			return lineNetAmt.toZero();
		}

		final Quantity qtyInvoiced = getQtyInvoiced();
		if (qtyInvoiced.signum() == 0)
		{
			// shall not happen
			return lineNetAmt.toZero();
		}

		// If it was fully received, there is no need to divide and then multiply the lineNetAmt.
		// We can return it right away.
		if (qtyInvoiced.compareTo(qtyReceived) == 0)
		{
			return lineNetAmt;
		}

		final BigDecimal qtyReceivedMultiplier = qtyReceived.toBigDecimal().divide(qtyInvoiced.toBigDecimal(), 12, RoundingMode.HALF_UP);

		return lineNetAmt.multiply(qtyReceivedMultiplier).round(getStdPrecision());
	}

	Money getCostAmountMatched()
	{
		if (_costAmountMatched == null)
		{
			this._costAmountMatched = matchInvoiceService.getCostAmountMatched(getInvoiceAndLineId())
					.orElseGet(() -> Money.zero(getCurrencyId()));
		}
		return _costAmountMatched;
	}

	@Override
	@NonNull
	// This is a workaround for a very specific case. Please, don't use it, if possible.
	public Account getAccount(@NonNull final ProductAcctType acctType, @NonNull final AcctSchema as)
	{
		if (acctType == ProductAcctType.P_Revenue_Acct && isConsiderCompensationSchema())
		{
			final Account account = getRevenueAccountFromCompensationSchema(as);
			if (account != null)
			{
				return account;
			}
		}

		return super.getAccount(acctType, as);
	}

	private boolean isConsiderCompensationSchema()
	{
		return sysConfigBL.getBooleanValue(SYS_CONFIG_M_Product_Acct_Consider_CompensationSchema,
				false,
				ClientId.toRepoId(getClientId()),
				OrgId.toRepoId(getOrgId()));
	}

	@Nullable
	private Account getRevenueAccountFromCompensationSchema(@NonNull final AcctSchema as)
	{
		final ProductCategoryId productCategoryId = getProductCategoryForGroupTemplateId();
		if (productCategoryId == null)
		{
			return null;
		}

		return getAccountProvider()
				.getProductCategoryAccount(as.getId(), productCategoryId, ProductAcctType.P_Revenue_Acct)
				.orElse(null);
	}

	@Nullable
	private ProductCategoryId getProductCategoryForGroupTemplateId()
	{
		final I_C_InvoiceLine invoiceLine = getC_InvoiceLine();
		final int orderLineRecordId = invoiceLine.getC_OrderLine_ID();
		if (orderLineRecordId <= 0)
		{
			return null;
		}

		final I_C_OrderLine orderLineRecord = orderDAO.getOrderLineById(orderLineRecordId);
		final GroupId groupId = OrderGroupRepository.extractGroupIdOrNull(orderLineRecord);
		if (groupId == null)
		{
			return null;
		}

		final Group group = orderGroupRepo.retrieveGroupIfExists(groupId);
		if (group == null)
		{
			return null;
		}

		final GroupTemplateId groupTemplateId = group.getGroupTemplateId();
		if (groupTemplateId == null)
		{
			return null;
		}

		return productDAO.retrieveProductCategoryForGroupTemplateId(groupTemplateId);
	}

	@Override
	protected OrderId getSalesOrderId()
	{
		final I_C_InvoiceLine invoiceLine = getC_InvoiceLine();
		return OrderId.ofRepoIdOrNull(invoiceLine.getC_OrderSO_ID());
	}
}
