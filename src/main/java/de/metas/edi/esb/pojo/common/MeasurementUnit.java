package de.metas.edi.esb.pojo.common;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public enum MeasurementUnit
{
	KILO("KGM"), //
	LITR("LTR"), //
	METR("MTR"), //
	SQMT("MTK"), //
	CBMT("MTQ"), //
	PIEC("PCE"), //
	PACK("TU"), //
	COLI("COLI"), //
	DISP("DISP"), //
	CART("KRT"); //

	private final String metasfreshUOM;

	MeasurementUnit(String metasfreshUOM)
	{
		this.metasfreshUOM = metasfreshUOM;
	}

	private static final Map<String, MeasurementUnit> metasfresh2edi = ImmutableMap.<String, MeasurementUnit> builder()
			.put(KILO.metasfreshUOM, KILO)
			.put(LITR.metasfreshUOM, LITR)
			.put(METR.metasfreshUOM, METR)
			.put(SQMT.metasfreshUOM, SQMT)
			.put(CBMT.metasfreshUOM, CBMT)
			.put(PIEC.metasfreshUOM, PIEC)
			.put(PACK.metasfreshUOM, PACK)
			.put(CART.metasfreshUOM, CART)
			.build();

	private static final Map<MeasurementUnit, String> edi2metasfresh = ImmutableMap.<MeasurementUnit, String> builder()
			.put(KILO, KILO.metasfreshUOM)
			.put(LITR, LITR.metasfreshUOM)
			.put(METR, METR.metasfreshUOM)
			.put(SQMT, SQMT.metasfreshUOM)
			.put(CBMT, CBMT.metasfreshUOM)
			.put(PIEC, PIEC.metasfreshUOM)
			.put(PACK, PACK.metasfreshUOM)
			.put(COLI, PIEC.metasfreshUOM) // (!) currently we don't need to export "COLI" or "DISP"
			.put(DISP, PIEC.metasfreshUOM) // (!)
			.put(CART, CART.metasfreshUOM)
			.build();

	public static MeasurementUnit fromMetasfreshUOM(String metasfreshUOM)
	{
		return metasfresh2edi.get(metasfreshUOM);
	}

	public static String fromEdiUOM(MeasurementUnit metasfreshUOM)
	{
		return edi2metasfresh.get(metasfreshUOM);
	}
}
