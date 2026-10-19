package org.adempiere.ad.persistence.custom_columns;

import de.metas.cache.CacheMgt;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.QueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Columns are flagged as REST-API custom columns while another thread is loading the custom-column map.
 * After the cache reset, the repository must see the new flag, not the map that the in-flight load read before the change.
 */
class CustomColumnRepositoryInFlightLoadTest
{
	private final ExecutorService executor = Executors.newCachedThreadPool();

	/**
	 * When set, the repository's AD_Table query (its second query, run after the AD_Column query) waits for {@link #releaseLoad}.
	 */
	@Nullable private volatile CountDownLatch adColumnsRead = null;
	@Nullable private volatile CountDownLatch releaseLoad = null;

	private CustomColumnRepository repository;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IQueryBL.class, new QueryBL()
		{
			@Override
			public <T> IQueryBuilder<T> createQueryBuilder(final Class<T> modelClass, final Properties ctx, @Nullable final String trxName)
			{
				final CountDownLatch read = adColumnsRead;
				final CountDownLatch release = releaseLoad;
				if (modelClass == I_AD_Table.class && read != null && release != null)
				{
					read.countDown();
					try
					{
						assertThat(release.await(10, TimeUnit.SECONDS)).isTrue();
					}
					catch (final InterruptedException e)
					{
						throw new RuntimeException(e);
					}
				}
				return super.createQueryBuilder(modelClass, ctx, trxName);
			}
		});
		repository = new CustomColumnRepository();
	}

	@AfterEach
	void shutdownExecutor()
	{
		executor.shutdownNow();
	}

	@Test
	void columnFlaggedWhileMapLoadIsInFlight_isVisibleAfterCacheReset() throws Exception
	{
		final int orderTableId = createTable("C_Order");
		final int resourceTypeTableId = createTable("S_ResourceType");
		createColumn(orderTableId, "DeliveryInfo", true);
		final I_AD_Column chargeableQty = createColumn(resourceTypeTableId, "ChargeableQty", false);

		// a concurrent request (e.g. a REST call) starts loading the map; its AD_Column query already ran
		adColumnsRead = new CountDownLatch(1);
		releaseLoad = new CountDownLatch(1);
		final CompletableFuture<RESTApiTableInfo> concurrentRead = CompletableFuture.supplyAsync(() -> repository.getByTableNameOrNull("S_ResourceType"), executor);
		assertThat(adColumnsRead.await(10, TimeUnit.SECONDS)).as("concurrent load read AD_Column").isTrue();
		adColumnsRead = null;

		// the user flags the column, the change is committed, and the cache is reset
		chargeableQty.setIsRestAPICustomColumn(true);
		saveRecord(chargeableQty);
		CacheMgt.get().reset();

		releaseLoad.countDown();
		assertThat(concurrentRead.get(10, TimeUnit.SECONDS)).as("the concurrent load read S_ResourceType before the change").isNull();

		final RESTApiTableInfo resourceTypeInfo = repository.getByTableNameOrNull("S_ResourceType");
		assertThat(resourceTypeInfo).as("S_ResourceType info after the reset").isNotNull();
		assertThat(resourceTypeInfo.isCustomRestAPIColumn("ChargeableQty")).isTrue();
		assertThat(repository.getByTableNameOrNull("C_Order")).isNotNull();
	}

	private static int createTable(final String tableName)
	{
		final I_AD_Table table = newInstance(I_AD_Table.class);
		table.setTableName(tableName);
		saveRecord(table);
		return table.getAD_Table_ID();
	}

	private static I_AD_Column createColumn(final int adTableId, final String columnName, final boolean isRestAPICustomColumn)
	{
		final I_AD_Column column = newInstance(I_AD_Column.class);
		column.setAD_Table_ID(adTableId);
		column.setColumnName(columnName);
		column.setIsRestAPICustomColumn(isRestAPICustomColumn);
		saveRecord(column);
		return column;
	}
}
