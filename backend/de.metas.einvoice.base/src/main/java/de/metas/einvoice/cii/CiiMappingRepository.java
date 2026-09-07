package de.metas.einvoice.cii;

import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceId;
import de.metas.location.CountryId;
import de.metas.location.LocationId;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;

/**
 * Repository for FK lookups performed during CII mapping.
 *
 * <p>Repository Tables: C_DocType, M_Product, C_UOM, C_Tax, C_Location, C_Country,
 * C_Currency, C_Invoice.
 *
 * <p>Repository Cluster: CiiMappingRepository only — these are read-only FK lookups for CII
 * serialisation; write operations on these tables belong to their authoritative DAOs
 * (IDocTypeDAO, IProductDAO, ITaxDAO, etc.).
 *
 * <p>{@link InterfaceWrapperHelper#load} is the correct primitive here — it works against both
 * the live DB and the in-memory test environment provided by {@code AdempiereTestHelper}.
 */
class CiiMappingRepository
{
	@Nullable
	I_C_DocType getDocType(final int docTypeId)
	{
		if (docTypeId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.load(DocTypeId.ofRepoId(docTypeId), I_C_DocType.class);
	}

	@Nullable
	I_M_Product getProduct(final int productId)
	{
		if (productId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.load(ProductId.ofRepoId(productId), I_M_Product.class);
	}

	@Nullable
	I_C_UOM getUOM(final int uomId)
	{
		if (uomId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.load(UomId.ofRepoId(uomId), I_C_UOM.class);
	}

	@Nullable
	I_C_Tax getTax(final int taxId)
	{
		if (taxId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.load(TaxId.ofRepoId(taxId), I_C_Tax.class);
	}

	@Nullable
	I_C_Location getLocation(final int locationId)
	{
		if (locationId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.load(LocationId.ofRepoId(locationId), I_C_Location.class);
	}

	@Nullable
	I_C_Country getCountry(final int countryId)
	{
		if (countryId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.load(CountryId.ofRepoId(countryId), I_C_Country.class);
	}

	@Nullable
	I_C_Currency getCurrency(final int currencyId)
	{
		if (currencyId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.load(CurrencyId.ofRepoId(currencyId), I_C_Currency.class);
	}

	@Nullable
	I_C_Invoice getInvoice(final InvoiceId invoiceId)
	{
		if (invoiceId == null)
		{
			return null;
		}
		return InterfaceWrapperHelper.load(invoiceId, I_C_Invoice.class);
	}
}
