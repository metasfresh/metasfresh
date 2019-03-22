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
	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	String clientId;
	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	String clientSecret;
	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	String executionMode;

	public PayPalProperties()
	{
		File file = new File("config.properties");
		URL[] urls = new URL[0];
		try
		{
			urls = new URL[] { file.toURI().toURL() };
			ClassLoader loader = new URLClassLoader(urls);
			ResourceBundle rb = ResourceBundle.getBundle("config", Locale.getDefault(), loader);

			clientId = rb.getString("CLIENT_ID");
			clientSecret = rb.getString("CLIENT_SECRET");
			executionMode = rb.getString("EXECUTION_MODE");
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

	}
}
