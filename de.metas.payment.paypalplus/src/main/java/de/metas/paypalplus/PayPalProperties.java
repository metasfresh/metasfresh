package de.metas.paypalplus;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NonNull;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

@Getter
public class PayPalProperties
{
	public static final String CONFIG_SANDBOX_PROPERTIES = "config_sandbox";
	public static final String CONFIG_LIVE_PROPERTIES = "config_live";
	public static final String CLIENT_ID = "CLIENT_ID";
	public static final String CLIENT_SECRET = "CLIENT_SECRET";
	public static final String EXECUTION_MODE = "EXECUTION_MODE";
	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	String clientId;
	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	String clientSecret;
	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	String executionMode;

	public PayPalProperties(String configurationFile)
	{
		File file = new File(configurationFile + ".properties");
		URL[] urls = new URL[0];
		try
		{
			urls = new URL[] { file.toURI().toURL() };
			ClassLoader loader = new URLClassLoader(urls);
			ResourceBundle rb = ResourceBundle.getBundle(configurationFile, Locale.getDefault(), loader);

			clientId = rb.getString(CLIENT_ID);
			clientSecret = rb.getString(CLIENT_SECRET);
			executionMode = rb.getString(EXECUTION_MODE);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

	}
}
