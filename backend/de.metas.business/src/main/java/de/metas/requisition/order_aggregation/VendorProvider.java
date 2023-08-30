package de.metas.requisition.order_aggregation;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.NoVendorForProductException;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.compiere.model.MCharge;
import org.compiere.util.Env;

import java.util.HashMap;
import java.util.List;

class VendorProvider
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	private final HashMap<VendorKey, BPartnerId> vendorsCache = new HashMap<>();

	public BPartnerId getVendorId(final OrderCandidate candidate)
	{
		return vendorsCache.computeIfAbsent(extractVendorKey(candidate), this::findVendorId);
	}

	private static VendorKey extractVendorKey(final OrderCandidate candidate)
	{
		return VendorKey.builder()
				.presetVendorId(candidate.getPresetVendorId())
				.productId(candidate.getProductId())
				.C_Charge_ID(candidate.getC_Charge_ID())
				.build();
	}

	@NonNull
	private BPartnerId findVendorId(final VendorKey vendorKey)
	{
		final ProductId productId = vendorKey.getProductId();

		BPartnerId vendorId = vendorKey.getPresetVendorId();
		if (vendorId != null && productId != null && isVendorForProduct(vendorId, productId)) // // task 05914 : is is vendor, the partner is ok; can be used
		{
			return vendorId;
		}
		else if (vendorKey.getC_Charge_ID() > 0)
		{
			final MCharge charge = MCharge.get(Env.getCtx(), vendorKey.getC_Charge_ID());
			vendorId = BPartnerId.ofRepoIdOrNull(charge.getC_BPartner_ID());
			if (vendorId == null)
			{
				throw new AdempiereException("No Vendor for Charge " + charge.getName());
			}
			return vendorId;
		}
		else
		{
			vendorId = null; // reset partner, since the one form line is not vendor
			// Find Strategic Vendor for Product

			// task 05914: start
			// FRESH-334: Make sure the BP_Product if of the product's org or org *
			final I_M_Product product = productBL.getById(productId);
			final OrgId productOrgId = OrgId.ofRepoId(product.getAD_Org_ID());

			final List<I_C_BPartner_Product> partnerProducts = bpartnerProductDAO.retrieveBPartnerForProduct(
					Env.getCtx(),
					null,
					productId,
					productOrgId);

			if (!partnerProducts.isEmpty())
			{
				vendorId = BPartnerId.ofRepoIdOrNull(partnerProducts.get(0).getC_BPartner_ID());
			}
			if (vendorId == null)
			{
				throw new NoVendorForProductException(product.getName());
			}
			// task 05914: end

			return vendorId;
		}
	}

	/**
	 * check if the partner is vendor for specific product
	 */
	private boolean isVendorForProduct(@NonNull final BPartnerId C_BPartner_ID, @NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_Product.class)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, C_BPartner_ID)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, productId)
				.create()
				.anyMatch();
	}

}
