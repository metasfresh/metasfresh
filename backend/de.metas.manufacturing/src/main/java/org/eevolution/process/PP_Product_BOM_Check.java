package org.eevolution.process;

import com.google.common.annotations.VisibleForTesting;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.collections.IteratorUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.exceptions.BOMCycleException;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Title: Check BOM Structure (free of cycles) Description: Tree cannot contain BOMs which are already referenced
 *
 * @author Tony Snook (tspc)
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class PP_Product_BOM_Check extends JavaProcess implements IProcessPrecondition
{
	public static final AdMessageKey NO_DEFAULT_PP_PRODUCT_BOM_FOR_PRODUCT_MESSAGE_KEY = AdMessageKey.of("NO_Default_PP_Product_BOM_For_Product");
	private static final AdMessageKey MSG_CATEGORY_RUN_OK = AdMessageKey.of("org.eevolution.process.PP_Product_BOM_Check.CategoryRunOK");
	private static final AdMessageKey MSG_CATEGORY_RUN_FAILED = AdMessageKey.of("org.eevolution.process.PP_Product_BOM_Check.CategoryRunFailed");
	private static final int MAX_FAILED_LIST_LENGTH = 100;

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

			final Iterator<I_M_Product> products = queryBuilder.create()
					.setOption(IQuery.OPTION_IteratorBufferSize, 500)
					.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
					.iterate(I_M_Product.class);
			int totalCount = 0;
			final List<String> failedValues = new ArrayList<>();

			try
			{
				for (final I_M_Product product : IteratorUtils.asIterable(products))
				{
					totalCount++;
					// the process log gets exactly one line per failed product (below); without this, e.g. the TrxManager adds its own log line for the same exception
					try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(Loggables.nop()))
					{
						validateProduct(product);
					}
					catch (final Exception ex)
					{
						failedValues.add(product.getValue());
						addLog("{}_{}: {}", product.getValue(), product.getName(), AdempiereException.extractMessage(ex));
						if (AdempiereException.isUserValidationError(ex))
						{
							log.warn("Product is not valid: {}", product, ex);
						}
						else
						{
							log.error("Product is not valid: {}", product, ex);
						}
					}
				}
			}
			finally
			{
				IteratorUtils.close(products);
			}

			// a category without BOM products also ends here: "All 0 products ... verified, none failed" is truthful
			if (failedValues.isEmpty())
			{
				return msgBL.getMsg(getCtx(), MSG_CATEGORY_RUN_OK, new Object[] { totalCount });
			}
			return msgBL.getMsg(getCtx(), MSG_CATEGORY_RUN_FAILED, failedMessageParams(failedValues, totalCount));
		}
		else
		{
			final I_M_Product product = InterfaceWrapperHelper.load(getM_Product_ID(), I_M_Product.class);
			validateProduct(product);
			return MSG_OK;
		}
	}

	/**
	 * @return the parameters of {@link #MSG_CATEGORY_RUN_FAILED}: failed count, total count, capped value list
	 */
	@VisibleForTesting
	static Object[] failedMessageParams(@NonNull final List<String> failedValues, final int totalCount)
	{
		return new Object[] { failedValues.size(), totalCount, cappedList(failedValues) };
	}

	/**
	 * @return the values joined by ", ": always the first value (even if longer than {@link #MAX_FAILED_LIST_LENGTH} characters),
	 * then as many further values as fit in {@link #MAX_FAILED_LIST_LENGTH} characters in total, then ", …" if more follow
	 */
	@VisibleForTesting
	static String cappedList(@NonNull final List<String> values)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.size(); i++)
		{
			final String value = values.get(i);
			final int newLength = sb.length() + (i > 0 ? 2 : 0) + value.length();
			if (i > 0 && newLength > MAX_FAILED_LIST_LENGTH)
			{
				sb.append(", …");
				break;
			}
			if (i > 0)
			{
				sb.append(", ");
			}
			sb.append(value);
		}
		return sb.toString();
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
		checkProductBOMCyclesAndMarkAsVerified(product);

		// Get Default BOM from this product
		final I_PP_Product_BOM bom = productBOMDAO.getDefaultBOMByProductId(ProductId.ofRepoId(product.getM_Product_ID()))
				.orElseThrow(() ->
						new AdempiereException(NO_DEFAULT_PP_PRODUCT_BOM_FOR_PRODUCT_MESSAGE_KEY,
								product.getValue() + "_" + product.getName()));

		// Check All BOM Lines
		for (final I_PP_Product_BOMLine tbomline : productBOMDAO.retrieveLines(bom))
		{
			final ProductId productId = ProductId.ofRepoId(tbomline.getM_Product_ID());
			final I_M_Product bomLineProduct = productBL.getById(productId);
			checkProductBOMCyclesAndMarkAsVerified(bomLineProduct);
		}
	}

	private void checkProductBOMCyclesAndMarkAsVerified(final I_M_Product product)
	{
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		productBOMBL.checkCycles(productId);
		product.setIsVerified(true);
		InterfaceWrapperHelper.save(product);
	}
}
