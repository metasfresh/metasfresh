package de.metas.report.xls.engine;

import com.google.common.collect.ImmutableMap;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import de.metas.util.Check;
import lombok.NonNull;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.compiere.util.Env;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.common.JxlsException;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.formula.FastFormulaProcessor;
import org.jxls.transform.TransformationConfig;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/*
 * #%L
 * de.metas.report.xls.client
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JXlsExporter
{
	public static JXlsExporter newInstance()
	{
		return new JXlsExporter();
	}

	static
	{
		XlsCommentAreaBuilder.addCommandMapping(HideColumnIfCommand.NAME, HideColumnIfCommand.class);
		XlsCommentAreaBuilder.addCommandMapping(LockSheetCommand.NAME, LockSheetCommand.class);
	}

	private static final String PROPERTY_Data = "data";
	private static final String PROPERTY_ResourceBundle = "r";
	private static final String PROPERTY_Org = "org";

	private static final transient Logger logger = LogManager.getLogger(JXlsExporter.class);

	private Properties _ctx;
	private ClassLoader _loader;
	private String _templateResourceName;
	private InputStream _template;
	private IXlsDataSource _dataSource;
	private String _adLanguage;
	private ResourceBundle _resourceBundle;
	private final Map<String, Object> _properties = new HashMap<>();

	private JXlsExporter()
	{
		super();
	}

	public ReportResult export()
	{
		try
		{
			try (final InputStream is = getTemplate())
			{
				final Context context = createJXlsContext();

				final ByteArrayOutputStream os = new ByteArrayOutputStream();
				final Transformer transformer = createTransformer(is, os);

				final JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator)transformer.getTransformationConfig().getExpressionEvaluator();

				//setting the evaluator to silent & lenient doesn't show warnings anymore for 0 or null values.
				evaluator.getJexlEngine().setSilent(true);
				evaluator.getJexlEngine().setLenient(true);

				processTemplate(transformer, context);

				return ReportResult.builder()
						.reportFilename(getDataSource().getSuggestedFilename().orElse(null))
						.outputType(OutputType.XLS)
						.reportContentBase64(org.compiere.util.Util.encodeBase64(os.toByteArray()))
						.build();
			}
		}
		catch (final Exception e)
		{
			throw new JXlsExporterException(e);
		}
	}

	private void processTemplate(final Transformer transformer, final Context context) throws IOException
	{

		//
		// Find Areas which we will need to process
		final AreaBuilder areaBuilder = new XlsCommentAreaBuilder();
		areaBuilder.setTransformer(transformer);
		final List<Area> xlsAreaList = areaBuilder.build();

		//
		// Process those areas
		for (final Area xlsArea : xlsAreaList)
		{
			// Process area
			xlsArea.applyAt(new CellRef(xlsArea.getStartCellRef().getCellName()), context);

			// Process formulas
			xlsArea.setFormulaProcessor(new FastFormulaProcessor());
			xlsArea.processFormulas();
		}

		//
		// Write the result
		transformer.write();
	}

	private Transformer createTransformer(final InputStream is, final ByteArrayOutputStream os) throws InvalidFormatException, IOException
	{
		final PoiTransformer transformer = PoiTransformer.createTransformer(is, os);
		transformer.setLastCommentedColumn(250);

		// make sure our custom jexl functions are registered
		final TransformationConfig config = transformer.getTransformationConfig();
		JexlCustomFunctions.registerIfNeeded(config.getExpressionEvaluator());

		return transformer;
	}

	private Context createJXlsContext()
	{
		final Context xlsContext = new Context();
		xlsContext.putVar(PROPERTY_Data, getDataSource().getRows());
		xlsContext.putVar(PROPERTY_ResourceBundle, getResourceBundleAsMap());
		xlsContext.putVar(PROPERTY_Org, OrgData.ofAD_Org_ID(getContext()));

		// Add custom properties
		for (final Map.Entry<String, Object> e : _properties.entrySet())
		{
			final String name = e.getKey();
			final Object value = e.getValue();
			final Object valueOld = xlsContext.getVar(name);
			if (valueOld != null)
			{
				throw new JxlsException("Cannot set context variable " + name + " because it was already defined"
						+ "\n Value to set: " + value
						+ "\n Previous value: " + valueOld);
			}
			xlsContext.putVar(name, value);
		}

		return xlsContext;
	}

	public JXlsExporter setContext(final Properties ctx)
	{
		this._ctx = ctx;
		return this;
	}

	private Properties getContext()
	{
		Check.assumeNotNull(_ctx, "ctx not null");
		return _ctx;
	}

	public JXlsExporter setLoader(final ClassLoader loader)
	{
		this._loader = loader;
		return this;
	}

	public ClassLoader getLoader()
	{
		return _loader == null ? getClass().getClassLoader() : _loader;
	}

	public JXlsExporter setAD_Language(final String adLanguage)
	{
		this._adLanguage = adLanguage;
		return this;
	}

	private Language getLanguage()
	{
		if (!Check.isEmpty(_adLanguage, true))
		{
			return Language.getLanguage(_adLanguage);
		}

		return Env.getLanguage(getContext());
	}

	public Locale getLocale()
	{
		final Language language = getLanguage();
		if (language != null)
		{
			return language.getLocale();
		}

		return Locale.getDefault();
	}

	public JXlsExporter setTemplateResourceName(final String templateResourceName)
	{
		this._templateResourceName = templateResourceName;
		return this;
	}

	private InputStream getTemplate()
	{
		if (_template != null)
		{
			return _template;
		}

		if (!Check.isEmpty(_templateResourceName, true))
		{
			final ClassLoader loader = getLoader();
			final InputStream template = loader.getResourceAsStream(_templateResourceName);
			if (template == null)
			{
				throw new JXlsExporterException("Could not find template for name: " + _templateResourceName + " using " + loader);
			}
			return template;
		}

		throw new JXlsExporterException("Template is not configured");
	}

	public JXlsExporter setTemplate(final InputStream template)
	{
		this._template = template;
		return this;
	}

	private IXlsDataSource getDataSource()
	{
		Check.assumeNotNull(_dataSource, "dataSource not null");
		return _dataSource;
	}

	public JXlsExporter setDataSource(final IXlsDataSource dataSource)
	{
		this._dataSource = dataSource;
		return this;
	}

	public JXlsExporter setResourceBundle(final ResourceBundle resourceBundle)
	{
		this._resourceBundle = resourceBundle;
		return this;
	}

	private ResourceBundle getResourceBundle()
	{
		if (_resourceBundle != null)
		{
			return _resourceBundle;
		}

		if (!Check.isEmpty(_templateResourceName, true))
		{
			String baseName = null;
			try
			{
				final int dotIndex = _templateResourceName.lastIndexOf('.');
				baseName = dotIndex <= 0 ? _templateResourceName : _templateResourceName.substring(0, dotIndex);

				return ResourceBundle.getBundle(baseName, getLocale(), getLoader());
			}
			catch (final MissingResourceException e)
			{
				logger.debug("No resource found for {}", baseName);
			}
		}

		return null;
	}

	public Map<String, String> getResourceBundleAsMap()
	{
		final ResourceBundle bundle = getResourceBundle();
		if (bundle == null)
		{
			return ImmutableMap.of();
		}
		return ResourceBundleMapWrapper.of(bundle);
	}

	public JXlsExporter setProperty(@NonNull final String name, @NonNull final Object value)
	{
		Check.assumeNotEmpty(name, "name not empty");

		_properties.put(name, value);

		return this;
	}
}
