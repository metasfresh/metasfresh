package de.metas.handlingunits;

import java.time.ZonedDateTime;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;

/**
 * Query VO to be used when filtering on {@link I_M_HU_PI_Item_Product}.
 *
 * @author tsa
 *
 */
public interface IHUPIItemProductQuery
{
	int getM_HU_PI_Item_ID();

	void setM_HU_PI_Item_ID(final int huPIItemId);

	int getM_Product_ID();

	void setM_Product_ID(final int productId);

	default void setProductId(final ProductId productId)
	{
		setM_Product_ID(ProductId.toRepoId(productId));
	}

	int getC_BPartner_ID();

	/**
	 * Match only those {@link I_M_HU_PI_Item_Product}s which are about this partner or any bpartner.
	 *
	 * @param bpartnerId
	 */
	void setC_BPartner_ID(final int bpartnerId);

	default void setBPartnerId(final BPartnerId bpartnerId)
	{
		setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
	}

	void setDate(ZonedDateTime date);

	ZonedDateTime getDate();

	boolean isAllowAnyProduct();

	void setAllowAnyProduct(final boolean allowAnyProduct);

	boolean isAllowInfiniteCapacity();

	void setAllowInfiniteCapacity(final boolean allowInfiniteCapacity);

	String getHU_UnitType();

	void setHU_UnitType(final String huUnitType);

	boolean isAllowVirtualPI();

	void setAllowVirtualPI(final boolean allowVirtualPI);

	boolean isOneConfigurationPerPI();

	/**
	 * If true, it will retain only one configuration (i.e. {@link I_M_HU_PI_Item_Product}) for each distinct {@link I_M_HU_PI} found.
	 *
	 * @param oneConfigurationPerPI
	 */
	void setOneConfigurationPerPI(final boolean oneConfigurationPerPI);

	boolean isAllowDifferentCapacities();

	/**
	 * In case {@link #isOneConfigurationPerPI()}, if <code>allowDifferentCapacities</code> is true, it will retain one configuration for each distinct {@link I_M_HU_PI} <b>AND</b>
	 * {@link I_M_HU_PI_Item_Product#getQty()}.
	 * </ul>
	 *
	 * @param allowDifferentCapacities
	 */
	void setAllowDifferentCapacities(boolean allowDifferentCapacities);

	// 08393
	// the possibility to create the query for any partner (not just for a given one or null)

	boolean isAllowAnyPartner();

	void setAllowAnyPartner(final boolean allowAnyPartner);

	/**
	 * @param packagingProductId
	 * @task https://metasfresh.atlassian.net/browse/FRESH-386
	 */
	// @formatter:off
	void setM_Product_Packaging_ID(int packagingProductId);
	int getM_Product_Packaging_ID();
	// @formatter:on
}
