package de.metas.acct.gljournal;

import de.metas.acct.Account;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_GL_JournalLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class GLJournalLineSide
{
	@NonNull PostingSign postingSign;
	@NonNull Account account;
	@NonNull Money amtSource;
	@NonNull BigDecimal amtAcct;
	@Nullable CurrencyConversionTypeId conversionTypeId;
	@Nullable ProductId productId;
	@Nullable LocatorId locatorId;
	@Nullable OrderId salesOrderId;

	public void assertMandatoryFieldsSet()
	{
		final AccountConceptualName accountConceptualName = account.getAccountConceptualName();
		if (accountConceptualName == null)
		{
			return;
		}

		if (accountConceptualName.isProductMandatory() && productId == null)
		{
			throw new FillMandatoryException(postingSign.isDebit() ? I_GL_JournalLine.COLUMNNAME_DR_M_Product_ID : I_GL_JournalLine.COLUMNNAME_CR_M_Product_ID);
		}
		if (accountConceptualName.isWarehouseLocatorMandatory() && locatorId == null)
		{
			throw new FillMandatoryException(postingSign.isDebit() ? I_GL_JournalLine.COLUMNNAME_DR_Locator_ID : I_GL_JournalLine.COLUMNNAME_CR_Locator_ID);
		}
	}
}
