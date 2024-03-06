package de.metas.report.jasper;

import de.metas.i18n.Language;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfoParameter;
import de.metas.report.server.OutputType;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import net.sf.jasperreports.engine.JRParameter;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

@EqualsAndHashCode
@ToString
class JRParametersCollector
{
	/**
	 * Desired output type.
	 *
	 * @see OutputType
	 */
	private static final String PARAM_OUTPUTTYPE = "OUTPUTTYPE";
	private static final String PARAM_AD_PINSTANCE_ID = "AD_PINSTANCE_ID";
	private static final String PARAM_REPORT_LANGUAGE = "REPORT_LANGUAGE";
	private static final String PARAM_RECORD_ID = "RECORD_ID";
	private static final String PARAM_BARCODE_URL = "barcodeURL";

	private final HashMap<String, Object> map = new HashMap<>();

	public Map<String, Object> toMap()
	{
		return new HashMap<>(map);
	}

	@Nullable
	public Object get(final String name)
	{
		return map.get(name);
	}

	public void put(final String name, final Object value)
	{
		map.put(name, value);
	}

	public void putAll(@NonNull final Properties ctx)
	{
		for (final Map.Entry<Object, Object> e : ctx.entrySet())
		{
			final Object key = e.getKey();
			put(key == null ? null : key.toString(), e.getValue());
		}
	}

	public void putAll(@NonNull final Map<String, Object> fromMap)
	{
		this.map.putAll(fromMap);
	}

	public void putAllProcessInfoParameters(@Nullable final List<ProcessInfoParameter> processParams)
	{
		if (processParams == null || processParams.isEmpty())
		{
			return;
		}

		for (final ProcessInfoParameter processParam : processParams)
		{
			putProcessInfoParameter(processParam);
		}
	}

	private void putProcessInfoParameter(final ProcessInfoParameter processParam)
	{
		if (processParam == null)
		{
			return;
		}

		// NOTE: just to be on the safe side, for each process info parameter we are setting the ParameterName even if it's a range parameter
		final Object parameterValue = normalizeParameterValue(processParam.getParameter());
		put(processParam.getParameterName(), parameterValue);

		// If we are dealing with a range parameter we are setting ParameterName1 and ParameterName2 too.
		final Object parameterValueTo = normalizeParameterValue(processParam.getParameter_To());
		if (parameterValueTo != null)
		{
			put(processParam.getParameterName() + "1", parameterValue);
			put(processParam.getParameterName() + "2", parameterValueTo);
		}
	}

	private static Object normalizeParameterValue(final Object value)
	{
		if (TimeUtil.isDateOrTimeObject(value))
		{
			return TimeUtil.asTimestamp(value);
		}
		else
		{
			return value;
		}
	}

	public void putPInstanceId(final PInstanceId pinstanceId)
	{
		map.put(PARAM_AD_PINSTANCE_ID, PInstanceId.toRepoId(pinstanceId));
	}

	public void putOutputType(final OutputType outputType)
	{
		put(PARAM_OUTPUTTYPE, outputType);
	}

	/**
	 * Gets desired output type
	 *
	 * @return {@link OutputType}; never returns null
	 */
	public OutputType getOutputTypeEffective()
	{
		final Object outputTypeObj = get(PARAM_OUTPUTTYPE);
		if (outputTypeObj instanceof OutputType)
		{
			return (OutputType)outputTypeObj;
		}
		else if (outputTypeObj instanceof String)
		{
			return OutputType.valueOf(outputTypeObj.toString());
		}
		else
		{
			return OutputType.JasperPrint;
		}
	}

	public void putReportLanguage(String adLanguage)
	{
		put(PARAM_REPORT_LANGUAGE, adLanguage);
	}

	/**
	 * Extracts {@link Language} parameter
	 *
	 * @return {@link Language}; never returns null
	 */
	public Language getReportLanguageEffective()
	{
		final Object languageObj = get(PARAM_REPORT_LANGUAGE);
		Language currLang = null;
		if (languageObj instanceof String)
		{
			currLang = Language.getLanguage((String)languageObj);
		}
		else if (languageObj instanceof Language)
		{
			currLang = (Language)languageObj;
		}

		if (currLang == null)
		{
			currLang = Env.getLanguage(Env.getCtx());
		}

		return currLang;
	}

	public void putLocale(@Nullable final Locale locale)
	{
		put(JRParameter.REPORT_LOCALE, locale);
	}

	@Nullable
	public Locale getLocale()
	{
		return (Locale)get(JRParameter.REPORT_LOCALE);
	}

	public void putRecordId(final int recordId)
	{
		put(PARAM_RECORD_ID, recordId);
	}

	public void putBarcodeURL(final String barcodeURL)
	{
		put(PARAM_BARCODE_URL, barcodeURL);
	}

	@Nullable
	public String getBarcodeUrl()
	{
		final Object barcodeUrl = get(PARAM_BARCODE_URL);
		return barcodeUrl != null ? barcodeUrl.toString() : null;
	}
}
