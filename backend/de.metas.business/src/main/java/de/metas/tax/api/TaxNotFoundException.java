package de.metas.tax.api;

import java.time.LocalDate;
import java.util.Date;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Charge;
import org.compiere.model.MLocation;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;

/**
 * Throw by Tax Engine where no tax found for given criteria
 */
@SuppressWarnings("serial")
public class TaxNotFoundException extends AdempiereException
{
	private static final String MSG_TaxNotFound = "TaxNotFound";

	private final ProductId productId;
	private final int chargeId;

	private final TaxCategoryId taxCategoryId;
	private final Boolean isSOTrx;

	private final OrgId orgId;

	private final LocalDate shipDate;
	private final CountryId shipFromCountryId;
	private final int shipFromC_Location_ID;
	private final int shipToC_Location_ID;

	private final LocalDate billDate;
	private final CountryId billFromCountryId;
	private final int billFromC_Location_ID;
	private final int billToC_Location_ID;

	@Builder
	private TaxNotFoundException(
			final String adMessage,
			final ProductId productId,
			final int chargeId,
			final TaxCategoryId taxCategoryId,
			final Boolean isSOTrx,
			//
			final OrgId orgId,
			//
			final Date shipDate,
			final int shipFromC_Location_ID,
			final CountryId shipFromCountryId,
			final int shipToC_Location_ID,
			//
			final Date billDate,
			final int billFromC_Location_ID,
			final CountryId billFromCountryId,
			final int billToC_Location_ID)
	{
		super(TranslatableStrings.empty());

		this.productId = productId;
		setParameter("productId", productId);
		this.chargeId = chargeId;
		setParameter("chargeId", chargeId > 0 ? chargeId : null);

		this.taxCategoryId = taxCategoryId;
		setParameter("taxCategoryId", taxCategoryId);
		this.isSOTrx = isSOTrx;
		setParameter("isSOTrx", isSOTrx);

		this.orgId = orgId;
		setParameter("orgId", orgId);

		this.shipDate = TimeUtil.asLocalDate(shipDate);
		setParameter("shipDate", shipDate);
		this.shipFromC_Location_ID = shipFromC_Location_ID;
		setParameter("shipFromC_Location_ID", shipFromC_Location_ID > 0 ? shipFromC_Location_ID : null);
		this.shipFromCountryId = shipFromCountryId;
		setParameter("shipFromCountryId", shipFromCountryId);
		this.shipToC_Location_ID = shipToC_Location_ID;
		setParameter("shipToC_Location_ID", shipToC_Location_ID > 0 ? shipToC_Location_ID : null);

		this.billDate = TimeUtil.asLocalDate(billDate);
		setParameter("billDate", billDate);
		this.billFromC_Location_ID = billFromC_Location_ID;
		setParameter("billFromC_Location_ID", billFromC_Location_ID > 0 ? billFromC_Location_ID : null);
		this.billFromCountryId = billFromCountryId;
		setParameter("billFromCountryId", billFromCountryId);
		this.billToC_Location_ID = billToC_Location_ID;
		setParameter("billToC_Location_ID", billToC_Location_ID > 0 ? billToC_Location_ID : null);
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
		}

		if (isSOTrx != null)
		{
			message.append(" - ").appendADElement("IsSOTrx").append(": ").append(isSOTrx);
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
		if (shipFromC_Location_ID > 0 || shipFromCountryId != null)
		{
			final String locationString = getLocationString(shipFromC_Location_ID, shipFromCountryId);
			message.append(" - ").appendADElement("ShipFrom").append(": ").append(locationString);
		}
		if (shipToC_Location_ID > 0)
		{
			final String locationString = getLocationString(shipToC_Location_ID);
			message.append(" - ").appendADElement("ShipTo").append(": ").append(locationString);
		}

		//
		// Bill info
		if (billDate != null)
		{
			message.append(" - ").appendADElement("BillDate").append(": ").appendDate(billDate);
		}
		if (billFromC_Location_ID > 0 || billFromCountryId != null)
		{
			final String locationString = getLocationString(billFromC_Location_ID, billFromCountryId);
			message.append(" - ").appendADElement("BillFrom").append(": ").append(locationString);
		}
		if (billToC_Location_ID > 0)
		{
			final String locationString = getLocationString(billToC_Location_ID);
			message.append(" - ").appendADElement("BillTo").append(": ").append(locationString);
		}

		//
		//
		return message.build();
	}

	private static final String getLocationString(final int locationId, final CountryId fallbackCountryId)
	{
		if (locationId > 0)
		{
			return getLocationString(locationId);
		}
		if (fallbackCountryId != null)
		{
			return Services.get(ICountryDAO.class).getCountryNameById(fallbackCountryId).getDefaultValue();
		}

		return "?";
	}

	private static final String getLocationString(final int locationId)
	{
		if (locationId <= 0)
		{
			return "?";
		}

		final MLocation loc = MLocation.get(Env.getCtx(), locationId, null);
		if (loc == null || loc.get_ID() != locationId)
		{
			return "?";
		}

		return loc.toString();
	}

}
