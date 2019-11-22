package de.metas.edi.esb.pojo.common;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;

public enum MeasurementUnit
{
	KILO("KGM", false), //
	LITR("LTR", false), //
	METR("MTR", false), //
	SQMT("MTK", false), //
	CBMT("MTQ", false), //
	PIEC("PCE", false), //
	PACK("TU", true), //
	COLI("COLI", true), //
	DISP("DISP", true), //
	CART("KRT", true); //

	private final String metasfreshUOM;

	@Getter
	private final boolean tuUOM;

	MeasurementUnit(
			String metasfreshUOM,
			boolean tuUOM)
	{
		this.metasfreshUOM = metasfreshUOM;
		this.tuUOM = tuUOM;
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
			.put(COLI, COLI.metasfreshUOM)
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
