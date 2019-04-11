package de.metas.edi.esb.pojo.common;

import com.google.common.collect.Maps;

import java.util.Map;

public enum MeasurementUnit
{
	KILO("KGM"),
	LITR("LTR"),
	METR("MTR"),
	SQMT("MTK"),
	CBMT("MTQ"),
	PIEC("PCE"),
	PACK("CNP"), //TODO not sure
	CART("KRT"),
	BAGS("Bund"); //TODO not sure

	private final String cuom;

	MeasurementUnit(String cuom)
	{
		this.cuom = cuom;
	}

	private static final Map<String, MeasurementUnit> cuomIndex =
			Maps.newHashMapWithExpectedSize(MeasurementUnit.values().length);

	static
	{
		for (MeasurementUnit uom : MeasurementUnit.values())
		{
			cuomIndex.put(uom.cuom, uom);
		}
	}

	public static MeasurementUnit fromCUOM(String name)
	{
		return cuomIndex.get(name);
	}

	public String getCuom()
	{
		return cuom;
	}
}
