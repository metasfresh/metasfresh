package de.metas.cache.model;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.POWrapper;
import org.compiere.model.PO;

import javax.annotation.Nullable;

@UtilityClass
public class CacheSourceModelFactory
{
	public static ICacheSourceModel ofPO(@NonNull final PO po)
	{
		return new POCacheSourceModel(po);
	}

	public static ICacheSourceModel ofObject(@NonNull final Object obj)
	{
		final PO po = unwrapPOOrNull(obj);
		if (po != null)
		{
			return ofPO((PO)obj);
		}
		else
		{
			return new ModelCacheSourceModel(obj);
		}
	}

	@Nullable
	private static PO unwrapPOOrNull(@NonNull final Object obj)
	{
		if (obj instanceof PO)
		{
			return (PO)obj;
		}

		return POWrapper.getStrictPO(obj);
	}
}
