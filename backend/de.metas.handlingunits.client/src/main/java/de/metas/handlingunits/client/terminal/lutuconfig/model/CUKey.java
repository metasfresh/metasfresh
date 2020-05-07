package de.metas.handlingunits.client.terminal.lutuconfig.model;

import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;

public class CUKey extends AbstractLUTUCUKey
{
	private final I_M_Product product;
	private String id = null;

	private Quantity totalQtyCU = null;

	private I_M_HU_LUTU_Configuration lutuConfiguration = null;

	public CUKey(final ITerminalContext terminalContext, @NonNull final ProductId productId)
	{
		super(terminalContext);

		this.product = Services.get(IProductDAO.class).getById(productId);
	}

	@Override
	public final String getId()
	{
		if (id == null)
		{
			id = createId();
		}
		return id;
	}

	protected String createId()
	{
		final int productId = getM_Product().getM_Product_ID();
		final String id =
				"CUKey" // we use CUKey instead of "getClass().getName()" because we want to match between multiple instances
						+ "#M_Product_ID=" + productId;
		return id;
	}

	@Override
	public final KeyNamePair getValue()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final I_M_HU_PI getM_HU_PI()
	{
		return null;
	}

	public final I_M_Product getM_Product()
	{
		return product;
	}

	@Override
	protected String createName()
	{
		return product.getName();
	}

	/**
	 * Gets suggested Total Qty CU.
	 *
	 * If not null, this total Qty CU will be distributed tu TUs and LUs.
	 *
	 * @return total Qty CU or null
	 */
	public final Quantity getTotalQtyCU()
	{
		return totalQtyCU;
	}

	public final void setTotalQtyCU(final Quantity totalQtyCU)
	{
		this.totalQtyCU = totalQtyCU;
	}

	/**
	 * Sets suggested LU/TU Configuration.
	 *
	 * If not null, this configuration will be used to preselect the TU and LU keys when user presses on this CU key.
	 *
	 * @return suggested LU/TU Configuration or null
	 */
	public void setDefaultLUTUConfiguration(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		this.lutuConfiguration = lutuConfiguration;
	}

	/**
	 * Gets suggested LU/TU Configuration.
	 *
	 * If not null, this configuration will be used to preselect the TU and LU keys when user presses on this CU key.
	 *
	 * @return suggested LU/TU Configuration or null
	 */
	public I_M_HU_LUTU_Configuration getDefaultLUTUConfiguration()
	{
		return lutuConfiguration;
	}
}
