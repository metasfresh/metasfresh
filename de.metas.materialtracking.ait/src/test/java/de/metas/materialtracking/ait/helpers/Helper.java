package de.metas.materialtracking.ait.helpers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.model.I_M_Material_Tracking;

/**
 * Created by ts on 23.12.2015.
 */
public class Helper
{
	private Helper()
	{
	}

	public static PlainContextAware context = new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_None);

	public static void link(final I_M_Material_Tracking materialTracking, final Object model)
	{
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		materialTrackingBL.linkModelToMaterialTracking(
				MTLinkRequest.builder()
						.setModel(model)
						.setMaterialTracking(materialTracking)
						.build());
	}

	public static Timestamp parseTimestamp(final String date) throws ParseException
	{
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		final Date receiptDate = simpleDateFormat.parse(date);
		final Timestamp movementDate = new Timestamp(receiptDate.getTime());
		return movementDate;
	}

	public static boolean parseBoolean(final String string)
	{
		return "Y".equalsIgnoreCase(string) || Boolean.parseBoolean(string);
	}

	public static void storeFirstTime(final String name, final Object object)
	{
		store(name, object, true);
	}

	public static void storeOverride(final String name, final Object object)
	{
		store(name, object, false);
	}

	private static final Map<String, Object> store = new HashMap<>();

	private static void store(final String name, final Object object, final boolean assumeFirstTime)
	{
		final String key = InterfaceWrapperHelper.getModelTableName(object) + "_" + name;

		final Object o = store.get(key);
		Check.errorIf(assumeFirstTime && o != null, "The store already contains an object with key {}: {};\n:store={}", key, o, store);

		store.put(key, object);
	}

	public static <T> T retrieveExisting(final String name, final Class<T> clazz)
	{
		final boolean assumeExisting = true;
		return retrieve(name, clazz, assumeExisting);
	}

	public static <T> T retrieveExistingOrNull(final String name, final Class<T> clazz)
	{
		final boolean assumeExisting = false;
		return retrieve(name, clazz, assumeExisting);
	}

	private static <T> T retrieve(final String name, final Class<T> clazz, final boolean assumeExisting)
	{
		final String key = InterfaceWrapperHelper.getTableName(clazz) + "_" + name;

		final Object o = store.get(key);
		Check.errorIf(assumeExisting && o == null, "DataStore does not contain an object for key {};\n:store={}", key, store);
		final T result = InterfaceWrapperHelper.create(o, clazz);

		// this is uber-crucial. otherwise we will return a stale record
		if (o != null)
		{
			InterfaceWrapperHelper.refresh(result);
		}


		return result;
	}

	public static void clear()
	{
		store.clear();
		POJOLookupMap.resetAll();
	}

	public static void put(final String key, final Object value)
	{
		store.put(key, value);
	}

	public static Object get(final String key)
	{
		return store.get(key);
	}
}
