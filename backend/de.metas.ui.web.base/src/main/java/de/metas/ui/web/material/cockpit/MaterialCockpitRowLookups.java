package de.metas.ui.web.material.cockpit;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.ui.web.window.model.lookup.zoom_into.DocumentZoomIntoInfo;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class MaterialCockpitRowLookups
{
	@NonNull private final LookupDataSource uomLookup;
	@NonNull private final LookupDataSource bpartnerLookup;
	@NonNull private final LookupDataSource productLookup;

	@Autowired
	public MaterialCockpitRowLookups(final @NonNull LookupDataSourceFactory lookupFactory)
	{
		this.uomLookup = lookupFactory.searchInTableLookup(I_C_UOM.Table_Name);
		this.bpartnerLookup = lookupFactory.searchInTableLookup(I_C_BPartner.Table_Name);
		this.productLookup = lookupFactory.searchInTableLookup(I_M_Product.Table_Name);
	}

	@VisibleForTesting
	@Builder
	private MaterialCockpitRowLookups(
			@NonNull final LookupDataSource uomLookup,
			@NonNull final LookupDataSource bpartnerLookup,
			@NonNull final LookupDataSource productLookup)
	{
		this.uomLookup = uomLookup;
		this.bpartnerLookup = bpartnerLookup;
		this.productLookup = productLookup;
	}

	@Nullable
	public LookupValue lookupUOMById(@Nullable final UomId uomId) {return uomLookup.findById(uomId);}

	@Nullable
	public LookupValue lookupBPartnerById(@Nullable final BPartnerId bpartnerId) {return bpartnerLookup.findById(bpartnerId);}

	@Nullable
	public LookupValue lookupProductById(@Nullable final ProductId productId) {return productLookup.findById(productId);}

	public DocumentZoomIntoInfo getZoomInto(@Nullable final ProductId productId) {return productLookup.getDocumentZoomInto(ProductId.toRepoId(productId));}
}
