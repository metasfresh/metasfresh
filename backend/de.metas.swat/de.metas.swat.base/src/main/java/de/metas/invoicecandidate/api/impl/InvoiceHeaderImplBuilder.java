package de.metas.invoicecandidate.api.impl;

import com.google.common.annotations.VisibleForTesting;
import de.metas.auction.AuctionId;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.document.DocTypeId;
import de.metas.document.dimension.Dimension;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolId;
import de.metas.forex.ForexContractRef;
import de.metas.impex.InputDataSourceId;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.ExternalId;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * {@link InvoiceHeaderImpl} builder class used to collect invoice header values and make sure they match.
 * <p>
 * It creates the actual {@link InvoiceHeaderImpl} when calling {@link #build()}.
 *
 * @author tsa
 */
@ToString
public class InvoiceHeaderImplBuilder
{
	private static final int REPO_ID_UNSET_VALUE = Integer.MIN_VALUE;

	private DocTypeInvoicingPoolId docTypeInvoicingPoolId = null;

	@Setter
	@Getter
	private boolean takeDocTypeFromPool = false;

	private DocTypeId docTypeInvoiceId = null;

	private final Set<String> POReferences = new HashSet<>();

	private final Set<String> eMails = new HashSet<>();

	private LocalDate _dateInvoiced;
	private LocalDate _dateAcct;
	private LocalDate _overrideDueDate;

	@Getter
	private int AD_Org_ID;

	@Nullable
	private InputDataSourceId inputDataSourceId;

	private boolean inputDataSourceIdset;

	private final Set<Integer> C_Order_IDs = new LinkedHashSet<>();

	private final Set<Integer> M_PriceList_IDs = new LinkedHashSet<>();

	@Getter
	private BPartnerInfo billTo;

	@Getter
	private String paymentRule;

	@Getter
	private int Sales_BPartner_ID;

	private int SalesRep_User_ID = REPO_ID_UNSET_VALUE;

	// 03805: add attribute C_Currency_ID
	@Getter
	private int C_Currency_ID;

	// 04258
	private final Set<String> Descriptions = new LinkedHashSet<>();
	private final Set<String> DescriptionBottoms = new LinkedHashSet<>();

	private Boolean isSOTrx = null;

	// 06630
	private final Set<Integer> M_InOut_IDs = new LinkedHashSet<>();

	private ExternalId externalId = null;

	private Boolean taxIncluded = null;

	private int C_Incoterms_ID;

	@Nullable
	@Getter
	private String incotermLocation;

	private int M_SectionCode_ID;

	private int C_Project_ID;

	private int C_Activity_ID;

	@Nullable
	private ForexContractRef forexContractRef;

	@Nullable
	@Getter
	private String invoiceAdditionalText;
	
	@Setter
	@Getter
	private boolean notShowOriginCountry;

	@Setter
	@Getter
	private int C_PaymentInstruction_ID;

	private int auctionId;

	@Getter
	private int C_Harvesting_Calendar_ID = REPO_ID_UNSET_VALUE;
	@Getter
	private int Harvesting_Year_ID = REPO_ID_UNSET_VALUE;
	@Getter
	private int M_Warehouse_ID = REPO_ID_UNSET_VALUE;

	private Dimension dimension;

	@Getter
	private int C_Tax_Departure_Country_ID;

	@Getter
	private int bankAccountId;

	InvoiceHeaderImplBuilder()
	{
	}

	public InvoiceHeaderImpl build()
	{
		final InvoiceHeaderImpl invoiceHeader = new InvoiceHeaderImpl();
		invoiceHeader.setOrgId(OrgId.ofRepoId(getAD_Org_ID()));

		// Document Type
		invoiceHeader.setDocTypeInvoicingPoolId(getDocTypeInvoicingPoolId());
		invoiceHeader.setDocTypeInvoiceId(getDocTypeInvoiceId());
		invoiceHeader.setSoTrx(SOTrx.ofBoolean(isSOTrx()));
		invoiceHeader.setTakeDocTypeFromPool(isTakeDocTypeFromPool());

		// Pricing and currency
		invoiceHeader.setCurrencyId(CurrencyId.ofRepoId(getC_Currency_ID()));
		invoiceHeader.setForexContractRef(forexContractRef);
		invoiceHeader.setM_PriceList_ID(getM_PriceList_ID());

		// Tax
		invoiceHeader.setTaxIncluded(isTaxIncluded());

		// Dates
		invoiceHeader.setDateInvoiced(getDateInvoiced());
		invoiceHeader.setDateAcct(getDateAcct());
		invoiceHeader.setOverrideDueDate(getOverrideDueDate());

		// BPartner/Location/Contact
		invoiceHeader.setBillTo(getBillTo());
		invoiceHeader.setSalesPartnerId(BPartnerId.ofRepoIdOrNull(getSales_BPartner_ID()));

		invoiceHeader.setSalesRepId(UserId.ofRepoIdOrNull(get_SaleRep_ID()));

		// Descriptions
		invoiceHeader.setDescription(getDescription());
		invoiceHeader.setDescriptionBottom(getDescriptionBottom());

		// References
		invoiceHeader.setC_Order_ID(getC_Order_ID());
		invoiceHeader.setM_InOut_ID(getM_InOut_ID());
		invoiceHeader.setPOReference(getPOReference());
		invoiceHeader.setExternalId(getExternalId());
		invoiceHeader.setEMail(getEmail());
		
		invoiceHeader.setPaymentRule(getPaymentRule());

		invoiceHeader.setAD_InputDataSource_ID(getAD_InputDataSource_ID());

		//incoterms
		invoiceHeader.setC_Incoterms_ID(getC_Incoterms_ID());
		invoiceHeader.setIncotermLocation(getIncotermLocation());

		invoiceHeader.setSectionCodeId(SectionCodeId.ofRepoIdOrNull(getM_SectionCode_ID()));

		invoiceHeader.setProjectId(ProjectId.ofRepoIdOrNull(getC_Project_ID()));
		invoiceHeader.setActivityId(ActivityId.ofRepoIdOrNull(getC_Activity_ID()));

		invoiceHeader.setInvoiceAdditionalText(getInvoiceAdditionalText());
		invoiceHeader.setNotShowOriginCountry(isNotShowOriginCountry());
		invoiceHeader.setC_PaymentInstruction_ID(getC_PaymentInstruction_ID());
		invoiceHeader.setC_Tax_Departure_Country_ID(CountryId.ofRepoIdOrNull(getC_Tax_Departure_Country_ID()));
		invoiceHeader.setBankAccountId(BankAccountId.ofRepoIdOrNull(getBankAccountId()));

		invoiceHeader.setDimension(this.dimension);

		invoiceHeader.setCalendarId(CalendarId.ofRepoIdOrNull(getC_Harvesting_Calendar_ID()));
		invoiceHeader.setYearId(YearId.ofRepoIdOrNull(getHarvesting_Year_ID()));
		invoiceHeader.setWarehouseId(WarehouseId.ofRepoIdOrNull(getM_Warehouse_ID()));
		invoiceHeader.setAuctionId(AuctionId.ofRepoIdOrNull(auctionId));

		return invoiceHeader;
	}

	@Nullable
	@VisibleForTesting
	String getExternalId()
	{
		return ExternalId.isInvalid(externalId) ? null : ExternalId.toValue(externalId);
	}

	private int getC_Incoterms_ID()
	{
		return C_Incoterms_ID;
	}

	public void setC_Incoterms_ID(final int incoterms_id)
	{
		C_Incoterms_ID = checkOverrideID("C_Incoterms_ID", C_Incoterms_ID, incoterms_id);
	}

	public void setIncotermLocation(@Nullable final String incotermLocation)
	{
		this.incotermLocation = checkOverride("IncotermLocation", this.incotermLocation, incotermLocation);
	}

	@Nullable
	public DocTypeInvoicingPoolId getDocTypeInvoicingPoolId()
	{
		return docTypeInvoicingPoolId;
	}

	public void setDocTypeInvoicingPoolId(@NonNull final DocTypeInvoicingPoolId docTypeInvoicingPoolId)
	{
		if (this.docTypeInvoicingPoolId != null && !this.docTypeInvoicingPoolId.equals(docTypeInvoicingPoolId))
		{
			throw new AdempiereException("DocTypeInvoicingPoolIds do not match!")
					.appendParametersToMessage()
					.setParameter("this.docTypeInvoicingPoolId", this.docTypeInvoicingPoolId)
					.setParameter("docTypeInvoicingPoolId", docTypeInvoicingPoolId);
		}

		this.docTypeInvoicingPoolId = docTypeInvoicingPoolId;
	}

	@Nullable
	public DocTypeId getDocTypeInvoiceId()
	{
		return docTypeInvoiceId;
	}

	public void setDocTypeInvoiceId(final DocTypeId docTypeInvoiceId, final boolean isEnforceUnique)
	{
		if (this.docTypeInvoiceId != null && !DocTypeId.equals(this.docTypeInvoiceId, docTypeInvoiceId))
		{
			if (isEnforceUnique)
			{
				throw new AdempiereException("DocTypeInvoiceIds do not match!")
						.appendParametersToMessage()
						.setParameter("this.docTypeInvoiceId", this.docTypeInvoiceId)
						.setParameter("docTypeInvoiceId", docTypeInvoiceId);
			}

			else
			{
				this.takeDocTypeFromPool = true;
			}
		}

		else
		{
			this.docTypeInvoiceId = docTypeInvoiceId;
		}
	}

	@Nullable
	public String getPOReference()
	{
		return CollectionUtils.singleElementOrNull(POReferences);
	}

	public void setPOReference(final String poReference)
	{
		normalizeAndAddIfNotNull(POReferences, poReference);
	}

	public @Nullable String getEmail()
	{
		return CollectionUtils.singleElementOrNull(eMails);
	}

	public void setEmail(final String eMail)
	{
		normalizeAndAddIfNotNull(eMails, eMail);
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

	public LocalDate getOverrideDueDate()
	{
		return _overrideDueDate;
	}

	public void setPaymentRule(@Nullable final String paymentRule)
	{
		this.paymentRule = paymentRule;
	}

	public void setDateAcct(@Nullable final LocalDate dateAcct)
	{
		_dateAcct = checkOverride("DateAcct", this._dateAcct, dateAcct);
	}

	public void setOverrideDueDate(@Nullable final LocalDate overrideDueDate)
	{
		_overrideDueDate = checkOverride("OverrideDueDate", this._overrideDueDate, overrideDueDate);
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
			if (!BPartnerInfo.equals(this.billTo.withLocationId(null).withContactId(null), billTo.withLocationId(null).withContactId(null)))
			{
				throw new AdempiereException("BillTo not matching: new=" + billTo + ", previous=" + this.billTo);
			}
		}

		if (this.billTo.getContactId() != null && !BPartnerContactId.equals(billTo.getContactId(), this.billTo.getContactId()))
		{
			this.billTo = billTo.withContactId(null);
		}
	}

	public int get_SaleRep_ID()
	{
		return SalesRep_User_ID;
	}

	public void setC_BPartner_SalesRep_ID(final int sales_BPartner_ID)
	{
		Sales_BPartner_ID = checkOverrideID("Sales_BPartner_ID", Sales_BPartner_ID, sales_BPartner_ID);
	}

	public void setSalesRep_ID(final int salesRep_ID)
	{
		if (SalesRep_User_ID == REPO_ID_UNSET_VALUE)
		{
			SalesRep_User_ID = salesRep_ID;
		}
		else if (salesRep_ID != SalesRep_User_ID)
		{
			SalesRep_User_ID = -1;
		}
	}

	public void setC_Currency_ID(final int currencyId)
	{
		C_Currency_ID = checkOverrideID("C_Currency_ID", C_Currency_ID, currencyId);
	}

	@Nullable
	public InputDataSourceId getAD_InputDataSource_ID()
	{
		return inputDataSourceId;
	}

	public void setAD_InputDataSource_ID(@Nullable final InputDataSourceId inputDataSourceId)
	{
		if (this.inputDataSourceId == null && !inputDataSourceIdset)
		{
			this.inputDataSourceId = inputDataSourceId;
			inputDataSourceIdset = true;
		}
		else if (!Objects.equals(this.inputDataSourceId, inputDataSourceId))
		{
			this.inputDataSourceId = null;
			inputDataSourceIdset = true;
		}
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

	public void setTaxIncluded(final boolean taxIncluded)
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

	@Nullable
	private static <T> T checkOverride(final String name, final @Nullable T value, @Nullable final T valueNew)
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

	private static <T> @Nullable T checkOverrideModel(final String name, final T model, final T modelNew)
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

	public void setExternalId(@Nullable final String externalIdStr)
	{
		final ExternalId externalId = ExternalId.ofOrNull(externalIdStr);
		Check.errorIf(ExternalId.isInvalid(externalId), "Given externalId may not be invalid"); // might happen later, when we modernize the method signature

		if (ExternalId.isInvalid(this.externalId))
		{
			return;
		}

		if (this.externalId != null && !Objects.equals(this.externalId, externalId))
		{
			this.externalId = ExternalId.INVALID;
			return;
		}

		this.externalId = externalId;
	}

	private int getM_SectionCode_ID()
	{
		return M_SectionCode_ID;
	}

	public void setM_SectionCode_ID(final int sectionCodeId)
	{
		M_SectionCode_ID = checkOverrideID("M_SectionCode_ID", M_SectionCode_ID, sectionCodeId);
	}

	private int getC_Project_ID()
	{
		return C_Project_ID;
	}

	public void setC_Project_ID(final int projectId)
	{
		C_Project_ID = checkOverrideID("C_Project_ID", C_Project_ID, projectId);
	}

	private int getC_Activity_ID()
	{
		return C_Activity_ID;
	}

	public void setC_Activity_ID(final int activityId)
	{
		C_Activity_ID = checkOverrideID("C_Activity_ID", C_Activity_ID, activityId);
	}

	public void setForexContractRef(@Nullable final ForexContractRef forexContractRef)
	{
		this.forexContractRef = forexContractRef;
	}

	public void setInvoiceAdditionalText(final @Nullable String invoiceAdditionalText)
	{
		this.invoiceAdditionalText = invoiceAdditionalText;
	}

	public void setDimension(@NonNull final Dimension dimension)
	{
		this.dimension = dimension;
	}

	public void setAuctionId(final int auctionId)
	{
		this.auctionId = checkOverrideID("C_Auction_ID", this.auctionId, auctionId);
	}

	public void setC_Harvesting_Calendar_ID(final int calendarId)
	{
		if (C_Harvesting_Calendar_ID == REPO_ID_UNSET_VALUE)
		{
			C_Harvesting_Calendar_ID = calendarId;
		}
		else if (calendarId != C_Harvesting_Calendar_ID)
		{
			C_Harvesting_Calendar_ID = -1;
		}
	}

	public void setHarvesting_Year_ID(final int yearId)
	{
		if (Harvesting_Year_ID == REPO_ID_UNSET_VALUE)
		{
			Harvesting_Year_ID = yearId;
		}
		else if (yearId != Harvesting_Year_ID)
		{
			Harvesting_Year_ID = -1;
		}
	}

	public void setM_Warehouse_ID(final int warehouseId)
	{
		if (M_Warehouse_ID == REPO_ID_UNSET_VALUE)
		{
			M_Warehouse_ID = warehouseId;
		}
		else if (warehouseId != M_Warehouse_ID)
		{
			M_Warehouse_ID = -1;
		}
	}

	public void setC_Tax_Departure_Country_ID(final int C_Tax_Departure_Country_ID)
	{
		this.C_Tax_Departure_Country_ID = checkOverrideID("C_Tax_Departure_Country_ID", this.C_Tax_Departure_Country_ID, C_Tax_Departure_Country_ID);
	}

	public void setBankAccountId(final int bankAccountId)
	{
		this.bankAccountId = checkOverrideID("C_BP_BankAccount_ID", this.bankAccountId, bankAccountId);
	}
}
