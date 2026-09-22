package de.metas.tax.api;

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_Location;
import org.compiere.model.MLocation;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Date;

/**
 * Throw by Tax Engine where no tax found for given criteria
 */
public class TaxNotFoundException extends AdempiereException
{
	private static final AdMessageKey MSG_TaxNotFound = AdMessageKey.of("TaxNotFound");

	private final ProductId productId;
	private final int chargeId;

	private final TaxCategoryId taxCategoryId;
	/** The identifier an API caller sent for {@link #taxCategoryId}; {@code null} if the category was not resolved from a caller-supplied identifier. */
	@Nullable private final String taxCategoryIdentifier;
	/** The tax rate that was explicitly searched for; {@code null} if the caller did not constrain the rate. */
	@Nullable private final Percent rate;
	private final Boolean isSOTrx;
	private final Boolean isTaxExempt;

	private final OrgId orgId;

	private final LocalDate shipDate;
	private final CountryId shipFromCountryId;
	private final LocationId shipFromC_Location_ID;

	private final CountryId shipToCountryId;
	private final BPartnerLocationAndCaptureId shipToC_Location_ID;

	private final LocalDate billDate;
	private final CountryId billFromCountryId;
	private final LocationId billFromC_Location_ID;
	private final LocationId billToC_Location_ID;

	@Builder
	private TaxNotFoundException(
			final ProductId productId,
			final int chargeId,
			final TaxCategoryId taxCategoryId,
			@Nullable final String taxCategoryIdentifier,
			@Nullable final Percent rate,
			final Boolean isSOTrx,
			final Boolean isTaxExempt,
			//
			final OrgId orgId,
			//
			final Date shipDate,
			final LocationId shipFromC_Location_ID,
			final CountryId shipFromCountryId,
			final CountryId shipToCountryId,
			final BPartnerLocationAndCaptureId shipToC_Location_ID,
			//
			final Date billDate,
			final LocationId billFromC_Location_ID,
			final CountryId billFromCountryId,
			final LocationId billToC_Location_ID)
	{
		super(TranslatableStrings.empty());

		this.productId = productId;
		setParameter("productId", productId);
		this.chargeId = chargeId;
		setParameter("chargeId", chargeId > 0 ? chargeId : null);

		this.taxCategoryId = taxCategoryId;
		setParameter("taxCategoryId", taxCategoryId);
		this.taxCategoryIdentifier = taxCategoryIdentifier;
		setParameter("taxCategoryIdentifier", taxCategoryIdentifier);
		this.rate = rate;
		setParameter("rate", rate);
		this.isSOTrx = isSOTrx;
		setParameter("isSOTrx", isSOTrx);
		this.isTaxExempt = isTaxExempt;
		setParameter("isTaxExempt", isTaxExempt);

		this.orgId = orgId;
		setParameter("orgId", orgId);

		this.shipDate = TimeUtil.asLocalDate(shipDate);
		setParameter("shipDate", shipDate);

		this.shipFromC_Location_ID = shipFromC_Location_ID;
		setParameter("shipFromC_Location_ID", shipFromC_Location_ID != null ? shipFromC_Location_ID.getRepoId() : null);
		this.shipFromCountryId = shipFromCountryId;
		setParameter("shipFromCountryId", shipFromCountryId);

		this.shipToC_Location_ID = shipToC_Location_ID;
		setParameter("shipToC_Location_ID", shipToC_Location_ID);
		this.shipToCountryId = shipToCountryId;
		setParameter("shipToCountryId", shipToCountryId);

		this.billDate = TimeUtil.asLocalDate(billDate);
		setParameter("billDate", billDate);
		this.billFromC_Location_ID = billFromC_Location_ID;
		setParameter("billFromC_Location_ID", billFromC_Location_ID != null ? billFromC_Location_ID.getRepoId() : null);
		this.billFromCountryId = billFromCountryId;
		setParameter("billFromCountryId", billFromCountryId);
		this.billToC_Location_ID = billToC_Location_ID;
		setParameter("billToC_Location_ID", billToC_Location_ID != null ? billToC_Location_ID.getRepoId() : null);
	}

	/**
	 * The fields both {@code ofQuery} overloads render. Kept in one place so a field added here reaches both, while
	 * each overload stays free to add only what is distinctly its own (see {@link #ofQuery(TaxQuery, String)}).
	 */
	private static TaxNotFoundExceptionBuilder builderOfQuery(@NonNull final TaxQuery taxQuery)
	{
		return TaxNotFoundException.builder()
				.taxCategoryId(taxQuery.getTaxCategoryId())
				.rate(taxQuery.getRate())
				.isSOTrx(taxQuery.getSoTrx().isSales())
				.isTaxExempt(taxQuery.getIsTaxExempt())
				.billDate(taxQuery.getDateOfInterest())
				.billFromCountryId(taxQuery.getFromCountryId());
	}

	public static TaxNotFoundException ofQuery(@NonNull final TaxQuery taxQuery)
	{
		return builderOfQuery(taxQuery).build();
	}

	/**
	 * Unlike {@link #ofQuery(TaxQuery)}, this overload also renders the caller's own tax-category identifier and the
	 * query's scope (org, ship-to location and country) into the message, so an API caller can tell which search came
	 * up empty. Each scope element costs a DB lookup while the message is built, which is why the 1-arg overload
	 * leaves them out.
	 *
	 * @param taxCategoryIdentifier the identifier an API caller sent for the queried tax category, to be echoed next to the resolved category name.
	 */
	public static TaxNotFoundException ofQuery(
			@NonNull final TaxQuery taxQuery,
			@Nullable final String taxCategoryIdentifier)
	{
		return builderOfQuery(taxQuery)
				.taxCategoryIdentifier(taxCategoryIdentifier)
				// the scope fields the 1-arg overload deliberately leaves out - each costs a DB lookup while the
				// message is rendered
				.orgId(taxQuery.getOrgId())
				.shipToCountryId(taxQuery.getShippingCountryId())
				.shipToC_Location_ID(taxQuery.getBPartnerLocationId())
				.build();
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		final TranslatableStringBuilder message = TranslatableStrings.builder();
		message.appendADMessage(MSG_TaxNotFound);

		//
		if (productId != null)
		{
			final String productName = Services.get(IProductBL.class).getProductValueAndName(productId);
			message.append(" - ").appendADElement("M_Product_ID").append(": ").append(productName);
		}
		if (chargeId > 0)
		{
			final String chargeName = InterfaceWrapperHelper.create(Env.getCtx(), chargeId, I_C_Charge.class, ITrx.TRXNAME_None).getName();
			message.append(" - ").appendADElement("C_Charge_ID").append(": ").append(chargeName);
		}

		//
		if (taxCategoryId != null)
		{
			final ITranslatableString taxCategoryName = Services.get(ITaxDAO.class).getTaxCategoryNameById(taxCategoryId);
			message.append(" - ").appendADElement("C_TaxCategory_ID").append(": ").append(taxCategoryName);
			if (taxCategoryIdentifier != null)
			{
				message.append(" (").append(taxCategoryIdentifier).append(")");
			}
		}

		if (rate != null)
		{
			message.append(" - ").appendADElement("Rate").append(": ").append(rate.toBigDecimal().toPlainString());
		}

		if (isSOTrx != null)
		{
			message.append(" - ").appendADElement("IsSOTrx").append(": ").append(isSOTrx);
		}

		if (isTaxExempt != null)
		{
			message.append(" - ").appendADElement("IsTaxExempt").append(": ").append(isTaxExempt);
		}

		if (orgId != null)
		{
			final String orgName = Services.get(IOrgDAO.class).retrieveOrgName(orgId);
			message.append(" - ").appendADElement("AD_Org_ID").append(": ").append(orgName);
		}

		//
		// Ship info
		if (shipDate != null)
		{
			message.append(" - ").appendADElement("ShipDate").append(": ").appendDate(shipDate);
		}
		if (shipFromC_Location_ID != null || shipFromCountryId != null)
		{
			final ITranslatableString locationString = getLocationString(shipFromC_Location_ID, shipFromCountryId);
			message.append(" - ").appendADElement("ShipFrom").append(": ").append(locationString);
		}

		if (shipToC_Location_ID != null)
		{
			final String locationString = getLocationString(shipToC_Location_ID);
			message.append(" - ").appendADElement("ShipTo").append(": ").append(locationString);
		}
		else if (shipToCountryId != null)
		{
			message.append(" - ").appendADElement("ShipTo").append(": ").append(getCountryName(shipToCountryId));
		}

		//
		// Bill info
		if (billDate != null)
		{
			message.append(" - ").appendADElement("BillDate").append(": ").appendDate(billDate);
		}
		if (billFromC_Location_ID != null || billFromCountryId != null)
		{
			final ITranslatableString locationString = getLocationString(billFromC_Location_ID, billFromCountryId);
			message.append(" - ").appendADElement("BillFrom").append(": ").append(locationString);
		}
		if (billToC_Location_ID != null)
		{
			final String locationString = getLocationString(billToC_Location_ID);
			message.append(" - ").appendADElement("BillTo").append(": ").append(locationString);
		}

		//
		//
		return message.build();
	}

	private static ITranslatableString getLocationString(final LocationId locationId, final CountryId fallbackCountryId)
	{
		if (locationId != null)
		{
			return TranslatableStrings.anyLanguage(getLocationString(locationId));
		}
		if (fallbackCountryId != null)
		{
			return getCountryName(fallbackCountryId);
		}

		return TranslatableStrings.anyLanguage("?");
	}

	private static ITranslatableString getCountryName(final CountryId countryId)
	{
		return countryId != null
				? Services.get(ICountryDAO.class).getCountryNameById(countryId)
				: TranslatableStrings.anyLanguage("?");
	}

	private static String getLocationString(@Nullable final LocationId locationId)
	{
		if (locationId == null)
		{
			return "?";
		}

		final MLocation loc = MLocation.get(Env.getCtx(), locationId.getRepoId(), ITrx.TRXNAME_ThreadInherited);
		if (loc == null || loc.getC_Location_ID() != locationId.getRepoId())
		{
			return "?";
		}

		return loc.toString();
	}

	private static String getLocationString(final BPartnerLocationAndCaptureId bpLocationId)
	{
		if (bpLocationId == null)
		{
			return "?";
		}

		final LocationId locationId;
		if (bpLocationId.getLocationCaptureId() != null)
		{
			locationId = bpLocationId.getLocationCaptureId();
		}
		else
		{
			final I_C_BPartner_Location bpLocation = Services.get(IBPartnerDAO.class).getBPartnerLocationByIdEvenInactive(bpLocationId.getBpartnerLocationId());
			locationId = bpLocation != null ? LocationId.ofRepoIdOrNull(bpLocation.getC_Location_ID()) : null;
		}

		if (locationId == null)
		{
			return "?";
		}

		final I_C_Location location = Services.get(ILocationDAO.class).getById(locationId);
		return location != null ? location.toString() : locationId.toString();
	}

}
