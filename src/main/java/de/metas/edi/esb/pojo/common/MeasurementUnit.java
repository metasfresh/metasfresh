package de.metas.edi.esb.pojo.common;

import java.util.Map;

import com.google.common.collect.Maps;

public enum MeasurementUnit
{
	KILO("KGM"),
	LITR("LTR"),
	METR("MTR"),
	SQMT("MTK"),
	CBMT("MTQ"),
	PIEC("PCE"),
	PACK("TU"),
	//COLI("PCE"),
	//DISP("PCE"),
	CART("KRT");

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
