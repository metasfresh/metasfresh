package org.eevolution.process;

import java.util.concurrent.atomic.AtomicInteger;

import de.metas.product.IProductBL;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

/**
 * Title: Check BOM Structure (free of cycles) Description: Tree cannot contain BOMs which are already referenced
 *
 * @author Tony Snook (tspc)
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class PP_Product_BOM_Check extends JavaProcess implements IProcessPrecondition
{
	private final transient IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
	private final transient IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	@Param(parameterName = I_M_Product.COLUMNNAME_M_Product_Category_ID, mandatory = false)
	private int p_M_Product_Category_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!(I_M_Product.Table_Name.equals(context.getTableName()) || I_PP_Product_BOM.Table_Name.equals(context.getTableName())))
		{
			return ProcessPreconditionsResolution.reject();
		}

		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		if (p_M_Product_Category_ID > 0)
		{
			final IQueryBuilder<I_M_Product> queryBuilder = Services.get(IQueryBL.class)
					.createQueryBuilder(I_M_Product.class)
					.addEqualsFilter(I_M_Product.COLUMNNAME_IsBOM, true)
					.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_Category_ID, p_M_Product_Category_ID)
					.orderBy()
					.addColumn(I_M_Product.COLUMNNAME_Name)
					.endOrderBy();

			final AtomicInteger counter = new AtomicInteger(0);

			queryBuilder.create()
					.stream()
					.forEach(product -> {

						try
						{
							validateProduct(product);
							counter.incrementAndGet();
						}
						catch (final Exception ex)
						{
							log.warn("Product is not valid: {}", product, ex);
						}
					});

			return "#" + counter.get();
		}
		else
		{
			final I_M_Product product = InterfaceWrapperHelper.load(getM_Product_ID(), I_M_Product.class);
			validateProduct(product);
			return MSG_OK;
		}
	}

	private int getM_Product_ID()
	{
		final String tableName = getTableName();
		if (I_M_Product.Table_Name.equals(tableName))
		{
			return getRecord_ID();
		}
		else if (I_PP_Product_BOM.Table_Name.equals(tableName))
		{
			final ProductBOMId bomId = ProductBOMId.ofRepoId(getRecord_ID());
			final I_PP_Product_BOM bom = productBOMDAO.getById(bomId);
			return bom.getM_Product_ID();
		}
		else
		{
			throw new AdempiereException(StringUtils.formatMessage("Table {} has not yet been implemented to support BOM validation.", tableName));
		}

	}

	private void validateProduct(@NonNull final I_M_Product product)
	{
		try
		{
			trxManager.runInNewTrx(() -> checkProductById(product));
		}
		catch (final Exception ex)
		{
			product.setIsVerified(false);
			InterfaceWrapperHelper.save(product);
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private void checkProductById(@NonNull final I_M_Product product)
	{
		if (!product.isBOM())
		{
			log.info("Product is not a BOM");
			// No BOM - should not happen, but no problem
			return;
		}

		// Check this level
		updateProductLLCAndMarkAsVerified(product);

		// Get Default BOM from this product
		final I_PP_Product_BOM bom = productBOMDAO.getDefaultBOM(product).orElse(null);
		if (bom == null)
		{
			throw new AdempiereException("No Default BOM found for " + product.getValue() + "_" + product.getName());
		}

		// Check All BOM Lines
		for (final I_PP_Product_BOMLine tbomline : productBOMDAO.retrieveLines(bom))
		{
			final ProductId productId = ProductId.ofRepoId(tbomline.getM_Product_ID());
			final I_M_Product bomLineProduct = productBL.getById(productId);
			updateProductLLCAndMarkAsVerified(bomLineProduct);
		}
	}

	private void updateProductLLCAndMarkAsVerified(final I_M_Product product)
	{
		// NOTE: when LLC is calculated, the BOM cycles are also checked
		final int lowLevelCode = productBOMBL.calculateProductLowestLevel(ProductId.ofRepoId(product.getM_Product_ID()));
		product.setLowLevel(lowLevelCode);
		product.setIsVerified(true);
		InterfaceWrapperHelper.save(product);
	}
}
