package de.metas.invoicecandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.StringUtils;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MPriceList;
import org.compiere.util.TimeUtil;

/**
 * {@link InvoiceHeaderImpl} builder class used to collect invoice header values and make sure they match.
 *
 * It creates the actual {@link InvoiceHeaderImpl} when calling {@link #build()}.
 *
 * @author tsa
 *
 */
public class InvoiceHeaderImplBuilder
{
	private I_C_DocType docTypeInvoice = null;

	private final Set<String> POReferences = new HashSet<>();

	private Timestamp _today;
	private Timestamp _dateInvoiced;
	private Timestamp _dateAcct;

	private int AD_Org_ID;

	private final Set<Integer> C_Order_IDs = new LinkedHashSet<>();

	private final Set<Integer> M_PriceList_IDs = new LinkedHashSet<>();

	private int Bill_BPartner_ID;
	private int Bill_Location_ID;
	private Set<Integer> Bill_User_IDs = new LinkedHashSet<Integer>(); // avoid NPE

	// 03805: add attribute C_Currency_ID
	private int C_Currency_ID;

	// 04258
	private final Set<String> Descriptions = new LinkedHashSet<>();
	private final Set<String> DescriptionBottoms = new LinkedHashSet<>();

	private Boolean isSOTrx = null;

	// 06630
	private final Set<Integer> M_InOut_IDs = new LinkedHashSet<>();

	private Boolean taxIncluded = null;

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
		invoiceHeader.setAD_Org_ID(getAD_Org_ID());

		// Document Type
		invoiceHeader.setC_DocTypeInvoice(getC_DocTypeInvoice());
		invoiceHeader.setIsSOTrx(isSOTrx());

		// Pricing and currency
		invoiceHeader.setC_Currency_ID(getC_Currency_ID());
		invoiceHeader.setM_PriceList_ID(getM_PriceList_ID());

		// Tax
		invoiceHeader.setTaxIncluded(isTaxIncluded());

		// Dates
		invoiceHeader.setDateInvoiced(getDateInvoiced());
		invoiceHeader.setDateAcct(getDateAcct());

		// BPartner/Location/User
		invoiceHeader.setBill_BPartner_ID(getBill_BPartner_ID());
		invoiceHeader.setBill_Location_ID(getBill_Location_ID());
		invoiceHeader.setBill_User_ID(getBill_User_ID());

		// Descriptions
		invoiceHeader.setDescription(getDescription());
		invoiceHeader.setDescriptionBottom(getDescriptionBottom());

		// References
		invoiceHeader.setC_Order_ID(getC_Order_ID());
		invoiceHeader.setM_InOut_ID(getM_InOut_ID());
		invoiceHeader.setPOReference(getPOReference());

		return invoiceHeader;
	}

	public void setToday(final Timestamp today)
	{
		this._today = today;
	}

	public final Timestamp getToday()
	{
		return _today;
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
		return ListUtils.singleElementOrNull(POReferences);
	}

	public void setPOReference(final String poReference)
	{
		normalizeAndAddIfNotNull(POReferences, poReference);
	}

	public Timestamp getDateInvoiced()
	{
		if (_dateInvoiced == null)
		{
			return getToday();
		}
		return _dateInvoiced;
	}

	public void setDateInvoiced(final Timestamp dateInvoiced)
	{
		final Timestamp dateInvoicedNorm = dateInvoiced == null ? null : TimeUtil.trunc(dateInvoiced, TimeUtil.TRUNC_DAY);
		this._dateInvoiced = checkOverride("DateInvoiced", this._dateInvoiced, dateInvoicedNorm);
	}

	public Timestamp getDateAcct()
	{
		// 08469 (mark): use DateInvoiced if DateAcct is not specified
		if (_dateAcct == null)
		{
			return getDateInvoiced();
		}

		return _dateAcct;
	}

	public void setDateAcct(final Timestamp dateAcct)
	{
		final Timestamp dateAcctNorm = dateAcct == null ? null : TimeUtil.trunc(dateAcct, TimeUtil.TRUNC_DAY);
		_dateAcct = checkOverride("DateAcct", this._dateAcct, dateAcctNorm);
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
		return ListUtils.singleElementOrDefault(C_Order_IDs, -1);
	}

	public void setC_Order_ID(final int orderId)
	{
		normalizeIDAndAddIfValid(C_Order_IDs, orderId);
	}

	/**
	 *
	 * @return the <code>M_PriceList_ID</code> to use.
	 *         If different <code>M_PriceList_ID</code>s were added using {@link #setM_PriceList_ID(int)},
	 *         then {@link MPriceList#M_PriceList_ID_None} is returned instead.
	 */
	public int getM_PriceList_ID()
	{
		return ListUtils.singleElementOrDefault(M_PriceList_IDs, MPriceList.M_PriceList_ID_None);
	}

	/**
	 * Sets/adds the given pricelist for this header. See {@link #getM_PriceList_ID()} for how conflicts are resolved.
	 *
	 * @param priceListId
	 */
	public void setM_PriceList_ID(final int priceListId)
	{
		normalizeIDAndAddIfValid(M_PriceList_IDs, priceListId);
	}

	public int getBill_Location_ID()
	{
		return Bill_Location_ID;
	}

	public void setBill_Location_ID(final int billBPLocationID)
	{
		Bill_Location_ID = checkOverrideID("Bill_Location_ID", Bill_Location_ID, billBPLocationID);
	}

	public int getBill_BPartner_ID()
	{
		return Bill_BPartner_ID;
	}

	public void setBill_BPartner_ID(final int bill_BPartner_ID)
	{
		Bill_BPartner_ID = checkOverrideID("Bill_BPartner_ID", Bill_BPartner_ID, bill_BPartner_ID);
	}

	public int getBill_User_ID()
	{
		return ListUtils.singleElementOrDefault(Bill_User_IDs, -1);
	}

	public void setBill_User_ID(final int bill_User_ID)
	{
		normalizeIDAndAddIfValid(Bill_User_IDs, bill_User_ID);
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
		return ListUtils.singleElementOrDefault(M_InOut_IDs, -1);
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

	private static final void normalizeAndAddIfNotNull(final Set<String> collection, final String element)
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

	private static final void normalizeIDAndAddIfValid(final Set<Integer> collection, final int id)
	{
		if (id <= 0)
		{
			return;
		}

		collection.add(id);
	}

	private static final <T> T checkOverride(final String name, final T value, final T valueNew)
	{
		if (value == null)
		{
			return valueNew;
		}

		if (valueNew == null)
		{
			return value;
		}

		if (value.equals(valueNew))
		{
			return value;
		}

		throw new AdempiereException("Overriding field " + name + " not allowed"
				+ "\n Current value: " + value
				+ "\n New value: " + valueNew);
	}

	private static final int checkOverrideID(final String name, final int id, final int idNew)
	{
		if (id <= 0)
		{
			return idNew <= 0 ? -1 : idNew;
		}

		if (idNew <= 0)
		{
			return id <= 0 ? -1 : id;
		}

		if (id == idNew)
		{
			return id;
		}

		throw new AdempiereException("Overriding field " + name + " not allowed"
				+ "\n Current value: " + id
				+ "\n New value: " + idNew);
	}

	private static final <T> T checkOverrideModel(final String name, final T model, final T modelNew)
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

	private static final boolean checkOverrideBoolean(final String name, final Boolean value, final boolean valueNew)
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
}
