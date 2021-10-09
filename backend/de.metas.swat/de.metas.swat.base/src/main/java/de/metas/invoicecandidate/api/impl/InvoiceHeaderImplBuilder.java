package de.metas.invoicecandidate.api.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_DocType;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * {@link InvoiceHeaderImpl} builder class used to collect invoice header values and make sure they match.
 * <p>
 * It creates the actual {@link InvoiceHeaderImpl} when calling {@link #build()}.
 *
 * @author tsa
 */
public class InvoiceHeaderImplBuilder
{
	private I_C_DocType docTypeInvoice = null;

	private final Set<String> POReferences = new HashSet<>();

	private LocalDate _dateInvoiced;
	private LocalDate _dateAcct;

	private int AD_Org_ID;

	private final Set<Integer> C_Order_IDs = new LinkedHashSet<>();

	private final Set<Integer> M_PriceList_IDs = new LinkedHashSet<>();

	private BPartnerInfo billTo;

	private int Sales_BPartner_ID;

	// 03805: add attribute C_Currency_ID
	private int C_Currency_ID;

	// 04258
	private final Set<String> Descriptions = new LinkedHashSet<>();
	private final Set<String> DescriptionBottoms = new LinkedHashSet<>();

	private Boolean isSOTrx = null;

	// 06630
	private final Set<Integer> M_InOut_IDs = new LinkedHashSet<>();

	private String externalId;

	private Boolean taxIncluded = null;

	private int C_Async_Batch_ID;

	/* package */ InvoiceHeaderImplBuilder()
	{
		super();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public InvoiceHeaderImpl build()
	{
		final InvoiceHeaderImpl invoiceHeader = new InvoiceHeaderImpl();
		invoiceHeader.setOrgId(OrgId.ofRepoId(getAD_Org_ID()));
		invoiceHeader.setC_Async_Batch_ID(getC_Async_Batch_ID());

		// Document Type
		invoiceHeader.setC_DocTypeInvoice(getC_DocTypeInvoice());
		invoiceHeader.setIsSOTrx(isSOTrx());

		// Pricing and currency
		invoiceHeader.setCurrencyId(CurrencyId.ofRepoId(getC_Currency_ID()));
		invoiceHeader.setM_PriceList_ID(getM_PriceList_ID());

		// Tax
		invoiceHeader.setTaxIncluded(isTaxIncluded());

		// Dates
		invoiceHeader.setDateInvoiced(getDateInvoiced());
		invoiceHeader.setDateAcct(getDateAcct());

		// BPartner/Location/Contact
		invoiceHeader.setBillTo(getBillTo());
		invoiceHeader.setSalesPartnerId(BPartnerId.ofRepoIdOrNull(getSales_BPartner_ID()));

		// Descriptions
		invoiceHeader.setDescription(getDescription());
		invoiceHeader.setDescriptionBottom(getDescriptionBottom());

		// References
		invoiceHeader.setC_Order_ID(getC_Order_ID());
		invoiceHeader.setM_InOut_ID(getM_InOut_ID());
		invoiceHeader.setPOReference(getPOReference());
		invoiceHeader.setExternalId(getExternalId());

		return invoiceHeader;
	}

	private String getExternalId()
	{
		return externalId;
	}

	private int getC_Async_Batch_ID()
	{
		return C_Async_Batch_ID;
	}

	public void setC_Async_Batch_ID(final int asyncBatchId)
	{
		C_Async_Batch_ID = checkOverrideID("C_Async_Batch_ID", C_Async_Batch_ID, asyncBatchId);
	}

	public I_C_DocType getC_DocTypeInvoice()
	{
		return docTypeInvoice;
	}

	public void setC_DocTypeInvoice(final I_C_DocType docTypeInvoice)
	{
		this.docTypeInvoice = checkOverrideModel("DocTypeInvoice", this.docTypeInvoice, docTypeInvoice);
	}

	public String getPOReference()
	{
		return CollectionUtils.singleElementOrNull(POReferences);
	}

	public void setPOReference(final String poReference)
	{
		normalizeAndAddIfNotNull(POReferences, poReference);
	}

	public LocalDate getDateInvoiced()
	{
		Check.assumeNotNull(_dateInvoiced, "Parameter _dateInvoiced is not null");
		return _dateInvoiced;
	}

	public void setDateInvoiced(@Nullable final LocalDate dateInvoiced)
	{
		this._dateInvoiced = checkOverride("DateInvoiced", this._dateInvoiced, dateInvoiced);
	}

	public LocalDate getDateAcct()
	{
		// 08469 (mark): use DateInvoiced if DateAcct is not specified
		if (_dateAcct == null)
		{
			return getDateInvoiced();
		}

		return _dateAcct;
	}

	public void setDateAcct(@Nullable final LocalDate dateAcct)
	{
		_dateAcct = checkOverride("DateAcct", this._dateAcct, dateAcct);
	}

	public int getAD_Org_ID()
	{
		return AD_Org_ID;
	}

	public void setAD_Org_ID(final int adOrgId)
	{
		AD_Org_ID = checkOverrideID("AD_Org_ID", AD_Org_ID, adOrgId);
	}

	public int getC_Order_ID()
	{
		return CollectionUtils.singleElementOrDefault(C_Order_IDs, 0);
	}

	public void setC_Order_ID(final int orderId)
	{
		normalizeIDAndAddIfValid(C_Order_IDs, orderId);
	}

	/**
	 * @return the <code>M_PriceList_ID</code> to use.
	 * If different <code>M_PriceList_ID</code>s were added using {@link #setM_PriceList_ID(int)},
	 * then {@link IPriceListDAO#M_PriceList_ID_None} is returned instead.
	 */
	public int getM_PriceList_ID()
	{
		return CollectionUtils.singleElementOrDefault(M_PriceList_IDs, IPriceListDAO.M_PriceList_ID_None);
	}

	/**
	 * Sets/adds the given pricelist for this header. See {@link #getM_PriceList_ID()} for how conflicts are resolved.
	 */
	public void setM_PriceList_ID(final int priceListId)
	{
		normalizeIDAndAddIfValid(M_PriceList_IDs, priceListId);
	}

	public void setBillTo(@NonNull final BPartnerInfo billTo)
	{
		if (this.billTo == null)
		{
			this.billTo = billTo;
		}
		else if (!BPartnerInfo.equals(this.billTo, billTo))
		{
			if (!BPartnerInfo.equals(this.billTo.withLocationId(null), billTo.withLocationId(null)))
			{
				throw new AdempiereException("BillTo not matching: new=" + billTo + ", previous=" + this.billTo);
			}
		}
	}

	public BPartnerInfo getBillTo()
	{
		return billTo;
	}

	public int getSales_BPartner_ID()
	{
		return Sales_BPartner_ID;
	}

	public void setC_BPartner_SalesRep_ID(final int sales_BPartner_ID)
	{
		Sales_BPartner_ID = checkOverrideID("Sales_BPartner_ID", Sales_BPartner_ID, sales_BPartner_ID);
	}

	public int getC_Currency_ID()
	{
		return C_Currency_ID;
	}

	public void setC_Currency_ID(final int currencyId)
	{
		C_Currency_ID = checkOverrideID("C_Currency_ID", C_Currency_ID, currencyId);
	}

	public String getDescription()
	{
		return StringUtils.toString(Descriptions, "; ");
	}

	public void setDescription(final String description)
	{
		normalizeAndAddIfNotNull(Descriptions, description);
	}

	public String getDescriptionBottom()
	{
		return StringUtils.toString(DescriptionBottoms, "; ");
	}

	public void setDescriptionBottom(final String descriptionBottom)
	{
		normalizeAndAddIfNotNull(DescriptionBottoms, descriptionBottom);
	}

	public boolean isSOTrx()
	{
		Check.assumeNotNull(isSOTrx, "isSOTrx was set");
		return isSOTrx;
	}

	public void setIsSOTrx(final boolean isSOTrx)
	{
		this.isSOTrx = checkOverrideBoolean("IsSOTrx", this.isSOTrx, isSOTrx);
	}

	public int getM_InOut_ID()
	{
		return CollectionUtils.singleElementOrDefault(M_InOut_IDs, -1);
	}

	public void setM_InOut_ID(final int inOutId)
	{
		normalizeIDAndAddIfValid(M_InOut_IDs, inOutId);
	}

	public boolean isTaxIncluded()
	{
		Check.assumeNotNull(taxIncluded, "taxIncluded was set");
		return taxIncluded;
	}

	public void setTaxIncluded(boolean taxIncluded)
	{
		this.taxIncluded = checkOverrideBoolean("IsTaxIncluded", this.taxIncluded, taxIncluded);
	}

	private static void normalizeAndAddIfNotNull(final Set<String> collection, final String element)
	{
		if (element == null)
		{
			return;
		}

		final String elementNorm = element.trim();
		if (elementNorm.isEmpty())
		{
			return;
		}

		collection.add(elementNorm);
	}

	private static void normalizeIDAndAddIfValid(final Set<Integer> collection, final int id)
	{
		if (id <= 0)
		{
			return;
		}

		collection.add(id);
	}

	private static <T> T checkOverride(final String name, final T value, final T valueNew)
	{
		if (value == null)
		{
			return valueNew;
		}
		else if (valueNew == null)
		{
			return value;
		}
		else if (value.equals(valueNew))
		{
			return value;
		}
		else
		{
			throw new AdempiereException("Overriding field " + name + " not allowed"
												 + "\n Current value: " + value
												 + "\n New value: " + valueNew);
		}
	}

	private static int checkOverrideID(final String name, final int id, final int idNew)
	{
		if (id <= 0)
		{
			return idNew <= 0 ? -1 : idNew;
		}
		else if (idNew <= 0)
		{
			return id;
		}
		else if (id == idNew)
		{
			return id;
		}
		else
		{
			throw new AdempiereException("Overriding field " + name + " not allowed"
												 + "\n Current value: " + id
												 + "\n New value: " + idNew);
		}
	}

	private static <T> T checkOverrideModel(final String name, final T model, final T modelNew)
	{
		if (model == null)
		{
			return modelNew;
		}
		if (modelNew == null)
		{
			return model;
		}

		final int modelId = InterfaceWrapperHelper.getId(model);
		final int modelIdNew = InterfaceWrapperHelper.getId(modelNew);
		final int modelIdToUse = checkOverrideID(name, modelId, modelIdNew);
		if (modelIdToUse <= 0)
		{
			return null;
		}
		else if (modelIdToUse == modelId)
		{
			return model;
		}
		else if (modelIdToUse == modelIdNew)
		{
			return modelNew;
		}
		else
		{
			throw new IllegalStateException("Internal error: invalid ID " + modelIdToUse
													+ "\n Model: " + model
													+ "\n Model new: " + modelNew);
		}
	}

	private static boolean checkOverrideBoolean(final String name, final Boolean value, final boolean valueNew)
	{
		if (value == null)
		{
			return valueNew;
		}

		if (value == valueNew)
		{
			return value;
		}

		throw new AdempiereException("Overriding field " + name + " not allowed"
											 + "\n Current value: " + value
											 + "\n New value: " + valueNew);
	}

	public void setExternalId(final String externalId)
	{
		this.externalId = checkOverride("ExternalId", this.externalId, externalId);
	}
}
